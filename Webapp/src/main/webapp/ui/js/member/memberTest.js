$.application.controller('memberTestController', ["$scope", "crudController", "actionHelper", "utils", 
                                        function($scope, crudController, actionHelper, utils){
	
	
	$scope.fetchTeamByProjectId = function(){
		
	};
	
	$scope.addNewTeam = function(){
		
		utils.openModal("projectTeamModelDialog");
	};
	
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
				objArr.photoUrl = actionHelper.actionUrl("files.fetch", {"id": fileId});
				return;
			}
			
			if(!objArr[index].photo)
			{
				objArr[index].photoUrl = "/webutils/Services/src/main/resources/webutils/img/employee/default_female.jpg";
			}else
			{
				fileId = objArr[index].photo.fileId;
				objArr[index].photoUrl = actionHelper.actionUrl("files.fetch", {"id": fileId});
			}
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
			
			return;
		}
		
		initPhoto($scope.employees);
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
		var index;
		var obj;
		for(index in $scope.employees)
		{
			obj = $scope.employees[index];
			
			$scope.empIdObjMap[obj.id] = obj 
		}
		
	};
		
	// Search employees
	$scope.searchEmployee = function(employeeName){
		
		$scope.empIdObjMap = {};
		
		if(employeeName)
		{
			searchName = employeeName;
			
			actionHelper.invokeAction("employee.readAll", null, {"employeeName" : employeeName}, readEmpcallBack, true);
			return;
		}
		
		actionHelper.invokeAction("employee.readAll", null, null, readEmpcallBack, true);
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
		
		actionHelper.invokeAction("projectMember.readAll", null, {"projectId" : projectId}, initMemCallBack, true);
	};
	
	// Invoked on drag
	$scope.dragEmployees = function(event){
		
		if(!projectId || projectId == 0)
		{
			utils.alert("Please select a project");
			return;
		}
		
		empObj = $scope.empIdObjMap[event.target.id];
		
		$scope.selectedEmployee = empObj;
	
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
			
		switch(data.projectMemberRole)
		{
			case 'PROJECT_ADMIN':
				{
					$scope.admins.splice($scope.admins.indexOf(data), 1);
					break;
				}
			case 'PROJECT_MEMBER':
				{
					$scope.members.splice($scope.members.indexOf(data), 1);
					break;
				}
			case 'PROJECT_MANAGER':
				{
					$scope.manager = {};
				}
		}
		
		employeeIds.splice(employeeIds.indexOf(data.employeeId), 1);
		
		deleteProjectMembers(data.employeeId);
	};
	
	var callBack = function(readResponse, respConfig){
		
		if(readResponse.code != 0)
		{
			$scope.initProMem();
		}
	};
	
	// Common function get invoked for saving members
	saveProjectMembers = function(employeeId, projectMemberRole){
		
		model = {"employeeId": employeeId, "projectId" : projectId, "projectMemberRole" : projectMemberRole, 
				"photo" : $scope.selectedEmployee.photo, "name" : $scope.selectedEmployee.name};
		
		switch(model.projectMemberRole)
		{
			case 'PROJECT_ADMIN':
				{
					$scope.admins.push(model);
					initPhoto($scope.admins);
					break;
				}
			case 'PROJECT_MEMBER':
				{
					$scope.members.push(model);
					initPhoto($scope.members);
					break;
				}
			case 'PROJECT_MANAGER':
				{
					if($scope.manager.employeeId)
					{
						$scope.removeProjectMembers($scope.manager);
					}
					
					$scope.manager = model;
					initPhoto($scope.manager);
				}
		}
		
		employeeIds.push($scope.selectedEmployee.id);
		
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}
		
		actionHelper.invokeAction("projectMember.save", model, null, callBack, true);
		
	};
	
	// Common function get invoked for delete members
	deleteProjectMembers = function(employeeId){
		
		console.log(employeeId);
		actionHelper.invokeAction("projectMember.deleteByEmployeeId", null, {"employeeId" : employeeId}, callBack, true);
		
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		$scope.initProMem();
	   
	});

	
}]);