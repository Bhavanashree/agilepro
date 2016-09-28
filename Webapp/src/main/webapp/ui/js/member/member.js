$.application.controller('memberController', ["$scope", "crudController", "actionHelper", "utils", 
                                        function($scope, crudController, actionHelper, utils){
	
	// For enter key
	$scope.checkKey = function(event){
		
		var key = event.keyCode ? event.keyCode : event.which;
		
		if(key == 13)
		{
			$scope.searchEmployee($scope.ngemployeeName);
		}
	};
	
	// After reading the existing project member init the url photo
	initPhoto = function(objArr){
		
		var index;
		
		var fileId;
		
		for(index in objArr)
		{
			// for manager single object
			if(!(Array.isArray(objArr)))
			{
				fileId = objArr.photo.fileId;
				console.log(fileId)
				objArr.photoUrl = actionHelper.actionUrl("files.fetch", {"id": fileId});
				return;
			}
			
			fileId = objArr[index].photo.fileId;
			objArr[index].photoUrl = actionHelper.actionUrl("files.fetch", {"id": fileId});
		}

	};
	
	// Call back method after searching the employees
	var readEmpcallBack = function(readResponse, respConfig){

		$scope.employees = readResponse.model;
		
		if($scope.employees.length == 0)
		{
			if(searchName)
			{
				utils.alert("The name provided doesnt match with any employee");
			}
			else
			{
				utils.alert("There are currently no employee");
			}
		}
		
		initPhoto($scope.employees);
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
	};
		
	// Search employees
	$scope.searchEmployee = function(employeeName){
		
		if(employeeName)
		{
			searchName = employeeName;
			
			actionHelper.invokeAction("employee.readAll", null, {"employeeName" : employeeName}, readEmpcallBack);
			return;
		}
		
		actionHelper.invokeAction("employee.readAll", null, null, readEmpcallBack);
	};
	
	var employeeIds = [];
	
	var searchName;
	
	var empObj;
	
	var projectId;
	
	// Call back method for reading the existing project members
	var initMemCallBack = function(readResponse, respConfig){
		
		var model = readResponse.model;
		
		if(model.length == 0)
		{
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}
			
			return;
		}
		
		if(model)
		{
			for(index in model)
			{
				employeeIds.push(model[index].employeeId);
				
				switch(model[index].projectMemberRole)
				{
					case 'PROJECT_ADMIN':
						$scope.admins.push(model[index]);
						break;
					case 'PROJECT_MEMBER':
						$scope.members.push(model[index]);
						break;
					case 'PROJECT_MANAGER':
						$scope.manager = model[index];
				}
			}
			
			if($scope.admins)
			{
				initPhoto($scope.admins);
			}
			if($scope.members)
			{
				initPhoto($scope.members);
			}
			if($scope.manager)
			{
				initPhoto($scope.manager);
			}
		}
		
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}
	};
	
	// Fetch existing project member
	$scope.initProMem = function(){

		$scope.members = [];
		
		$scope.admins = [];
		
		$scope.manager = {};
		
		employeeIds = []
		
		projectId = $scope.getActiveProjectId();
		
		console.log("PROJECT ID = " + projectId);
		if(!projectId || projectId == 0)
		{
			return;
		}
		
		actionHelper.invokeAction("projectMember.readAll", null, {"projectId" : projectId}, initMemCallBack);
	};
	
	// Invoked on drag
	$scope.dragEmployees = function(event){
		
		if(!projectId || projectId == 0)
		{
			utils.alert("Please select a project");
			return;
		}
		
		empObj = JSON.parse(event.target.id);
	
	};
	
	// Invoked while dragging over the drop area
	 $scope.allowDrop = function(event) {
		 
	    event.preventDefault();
	}
	
	 // Invoked when element is dropped in member
	 $scope.dropMembers = function(event){
		 
		    event.preventDefault();
		    
		    var index = employeeIds.indexOf(empObj.id);
			
			if(index == -1)
			{
				saveProjectMembers(empObj.id, "PROJECT_MEMBER");
			}
			else if(index >= 0)
			{
				utils.alert("He or she is already a member of the team please remove it from one of the group");
				return;
			}
		}
	 
	 // Invoked when element is dropped in admin 
	 $scope.dropAdmins = function(event){
		 
		    event.preventDefault();
		    
		    var index = employeeIds.indexOf(empObj.id);
			
			if(index == -1)
			{
				saveProjectMembers(empObj.id, "PROJECT_ADMIN");
			}
			else if(index >= 0)
			{
				utils.alert("He or she is already a member of the team please remove it from one of the group");
				return;
			}
	 };
	
	 // Invoked when element is dropped in manager
	 $scope.dropManager = function(event){
		 	
		    event.preventDefault();
		    
		    var index = employeeIds.indexOf(empObj.id);
			
			if(index == -1)
			{
				if($scope.manager.employeeName)
				{
					deleteProjectMembers($scope.manager.employeeId);
				}
				
				saveProjectMembers(empObj.id, "PROJECT_MANAGER");
			}
			else if(index >= 0)
			{
				utils.alert("He or she is already a member of the team please remove it from one of the group");
				return;
			}
	 };
	 
	 // Invoked on remove project members
	$scope.removeProjectMembers = function(data){
			
		deleteProjectMembers(data.id);
	};
	
	var callBack = function(readResponse, respConfig){
		
		$scope.initProMem();
		
	};
	
	// Common function get invoked for saving members
	saveProjectMembers = function(employeeId, projectMemberRole){
		
		model = {"employeeId": employeeId, "projectId" : projectId, "projectMemberRole" : projectMemberRole};
		
		actionHelper.invokeAction("projectMember.save", model, null, callBack);
		
	};
	
	// Common function get invoked for delete members
	deleteProjectMembers = function(id){
		
		actionHelper.invokeAction("projectMember.delete", null, {"id" : id}, callBack);
		
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		$scope.initProMem();
	   
	});
	
}]);

