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
			if(!(Array.isArray(objArr)))
			{
				fileId = objArr.photo.fileId;
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
				
				switch(model[index].userRole)
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
		
		projectId = $scope.getActiveProject();
		
		if(projectId == -1 || !projectId)
		{
			return;
		}
		
		actionHelper.invokeAction("projectMember.readAll", null, {"projectId" : projectId}, initMemCallBack);
	};
	
	// Invoked on drag
	$scope.dragEmployees = function(event){
		
		if(projectId == -1 || !projectId)
		{
			utils.alert("Please select a project");
			return;
		}
		
		empObj = JSON.parse(event.target.id);
	
		//event.dataTransfer.setData("text", event.target.id);
		//event.dataTransfer.setDragImage(img, 10, 10);
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
				employeeIds.push(empObj.id);
				$scope.members.push(empObj);
				
				saveProjectMembers(empObj.id, "PROJECT_MEMBER");
			}
			else if(index >= 0)
			{
				utils.alert("He or she is already a member of the team please remove it from one of the group");
				return;
			}
			
		    /*var data = event.dataTransfer.getData("text");
		    event.target.appendChild(document.getElementById(data));*/
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
		}
	 
	 // Invoked when element is dropped in admin 
	 $scope.dropAdmins = function(event){
		 
		    event.preventDefault();
		    
		    var index = employeeIds.indexOf(empObj.id);
			
			if(index == -1)
			{
				employeeIds.push(empObj.id);
				$scope.admins.push(empObj);
				
				saveProjectMembers(empObj.id, "PROJECT_ADMIN");
			}
			else if(index >= 0)
			{
				utils.alert("He or she is already a member of the team please remove it from one of the group");
				return;
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
	 };
	
	 // Invoked when element is dropped in manager
	 $scope.dropManager = function(event){
		 	
		    event.preventDefault();
		    
		    var index = employeeIds.indexOf(empObj.id);
			
			if(index == -1)
			{
				if($scope.manager.name)
				{
					index = employeeIds.indexOf($scope.manager.employeeId);
					
					employeeIds.splice(index, 1);
					
					if($scope.manager.employeeId)
					{
						deleteProjectMembers($scope.manager.employeeId);
					}
					else
					{
						deleteProjectMembers($scope.manager.id);
					}
					
				}
				
				employeeIds.push(empObj.id);
				$scope.manager = empObj;
				
				saveProjectMembers(empObj.id, "PROJECT_MANAGER");
			}
			else if(index >= 0)
			{
				utils.alert("He or she is already a member of the team please remove it from one of the group");
				return;
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
	 };
	 
	 // Invoked on remove member
	$scope.removeMembers = function(data){
		
		var index;
		
		if(data.userRole)
		{
			index = employeeIds.indexOf(data.employeeId);
			
			deleteProjectMembers(data.employeeId);
		}
		else
		{
			index = employeeIds.indexOf(data.id);
			
			deleteProjectMembers(data.id);
		}
		
		if(index >= 0)
		{
			employeeIds.splice(index, 1);
			
			index = $scope.members.indexOf(data);
			
			$scope.members.splice(index, 1);
		}
	};
	
	// Invoked when admin is removed
	$scope.removeAdmins = function(data){
		
		var index;
		
		if(data.userRole)
		{
			index = employeeIds.indexOf(data.employeeId);
			
			deleteProjectMembers(data.employeeId);
		}
		else
		{
			index = employeeIds.indexOf(data.id);
			
			deleteProjectMembers(data.id);
		}
		
		if(index >= 0)
		{
			employeeIds.splice(index, 1);
			
			index = $scope.admins.indexOf(data);
			
			$scope.admins.splice(index, 1);
		}
	};
	
	// Invoked when manager is removed
	$scope.removeManager = function(data){
		
		var index;
		
		if(data.userRole)
		{
			index = employeeIds.indexOf(data.employeeId);
			
			deleteProjectMembers(data.employeeId);
		}
		else
		{
			index = employeeIds.indexOf(data.id);
			
			deleteProjectMembers(data.id);
		}
		
		if(index >= 0)
		{
			employeeIds.splice(index, 1);
			
			$scope.manager = {};
		}
	};
	
	var callBack = function(readResponse, respConfig){
		
	};
	
	// Common function get invoked for saving members
	saveProjectMembers = function(employeeId, userRole){
		
		model = {"employeeId": employeeId, "projectId" : projectId, "userRole" : userRole};
		
		actionHelper.invokeAction("projectMember.save", model, null, callBack);
		
	};
	
	// Common function get invoked for delete members
	deleteProjectMembers = function(employeeId){
		
		actionHelper.invokeAction("projectMember.delete", null, {"id" : employeeId}, callBack);
		
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelected", function(event, args) {

		$scope.initProMem();
	   
	});
	
}]);

