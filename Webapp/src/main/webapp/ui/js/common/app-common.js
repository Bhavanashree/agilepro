$.application.controller('commonController', ["$scope", "clientContext", "utils", "actionHelper", function($scope, clientContext, utils, actionHelper) {
	$scope.name = "Common Controller";
	
    $scope.mails = [
		{
			"id" : 1,
			"subject" : "Subject line 1",
			"from": "From1"
		},
		{
			"id" : 2,
			"subject" : "Subject line 2",
			"from": "From2"
		}
	];
    
    $scope.notifications = [
		{
			"id": 1,
			"title" : "New project created"
		},
		{
			"id": 2,
			"title" : "Pending Due Amoount - 3000$"
		},
		{
			"id": 3,
			"title" : "Low usage by Customer"
		}
	];
    
    $scope.alerts = [
		{
			"id": 1,
			"title": "Scrum meeting @ 10",
			"time": "10:30 AM"
		},
		{
			"id": 2,
			"title": "Retrospection",
			"time": "10:45 AM"
		}
	];
    
    
    $scope.toggleLeftMenu = function() {
    	var leftMenu = $("#appLeftMenu");
    	leftMenu.toggleClass('visibleElem');
    };
    
    $scope.$on('$viewContentLoading', function(event) {
    	utils.displayInProgress();
    });

    $scope.$on('$viewContentLoaded', function(event) {
    	utils.hideInProgress();
    	
    	var leftMenu = $("#appLeftMenu");
    	leftMenu.removeClass('visibleElem');
    });
    
    
    $scope.showConversation = function(){
    	
    	utils.openModal("conversationDialog");
    }
   
    $scope.send = function(){
    	
    	var messageFromUser = document.getElementById("message").value;
    	
    	var messageShowObj  =  document.getElementById("data1");
    	
    	messageShowObj.innerHTML = messageFromUser;
    	
    	messageShowObj.className = "mystyle";
    	
    	document.getElementById("message").value = " ";
    }
    
    $scope.data = [];
    
    var presentProjectId;
    
    var projArr;
    
    var userSettingId;
    
    var version;
    
    // Read all the projects call back
    var readProCallBack = function(readResponse, respConfig){
    	
    	var i;
    	var temp;
    	
    	projArr = readResponse.model;
    	
    	if(presentProjectId)
    	{
    		for(i=0; i< projArr.length ; i++)
        	{
        		if(projArr[i].id == presentProjectId)
        		{
        			temp = projArr[0];
        			projArr[0] = projArr[i];
        			projArr[i] = temp;
        		}
        	}
    		
    		$scope.show = true;
    	}
    	else if(projArr.length > 0)
    	{
    		projArr.splice(0, 0, {"name" : "Select Project", "id" : -1})
    		$scope.show = true;
    	}	
    	
    	if(projArr.length == 0)
    	{
    		$scope.show = false;
    	}
    	
    	$scope.projects = projArr;

    	try
		{
    		$scope.$apply();
		}catch(ex)
		{}
    };
    
    // Read user setting call back
    var readUserCallBack = function(readResponse, respConfig){
    	
    	 var userSettingModel = readResponse.model;
    	
    	if(userSettingModel)
    	{
    		userSettingId = userSettingModel.id;
    		presentProjectId = Number(userSettingModel.value); // convert string to number
    		version = userSettingModel.version;
    		
    		// broad cast for project member 
    		$scope.$broadcast("activeProjectSelected");
    	}
    };
    
    // Invoked on page load for reading user setting and all projects
    $scope.fetchProjects = function(){
    	
    	actionHelper.invokeAction("userSetting.read", null, {"userId" : $scope.activeUser.userId}, readUserCallBack);
    	
    	actionHelper.invokeAction("project.readAll", null, null, readProCallBack);
    };
    
    $scope.$on("activeUserIsReady", function(event, args) {

    	$scope.fetchProjects();
	});
    
    // Get invoked while selecting a project
    $scope.selectedProject = function(proObj){
    	
    	if(proObj.id != -1)
    	{
    		if(!userSettingId)
        	{
    			presentProjectId = proObj.id;
        		saveUserSetting(presentProjectId);
        	}
    		else
    		{
    			presentProjectId = proObj.id;
            	editUserSetting(presentProjectId);
    		}
    	}
    	else
    	{
    		presentProjectId = -1;
    	}
    	
    	// Broad cast 
    	$scope.$broadcast("activeProjectSelected");
    };
    
    // Save user call back
    var saveCallBack = function(saveResponse, respConfig){
        
		userSettingId = saveResponse.id;
    };
    
    // Get invoked only once when a user setting is saved
    saveUserSetting  = function(currentProjectId){
    	
    	var model = {"userId" : $scope.activeUser.userId, "key" : "activeProjectId", "value" : currentProjectId};
		
		actionHelper.invokeAction("userSetting.save", model, null, saveCallBack);
    };
    
    
    // Call back function for every read user setting
    var versionCallBack  = function(readResponse, respConfig){
    	
    	version = readResponse.model.version;
    };
    
    // Call back function for every update user setting
    var editCallBack = function(saveResponse, respConfig){
    	
    	actionHelper.invokeAction("userSetting.read", null, {"userId" : $scope.activeUser.userId}, versionCallBack);
    };
    
    // Method for update
    editUserSetting = function(currentProjectId){
    	
    	var model = {"id" : userSettingId, "userId" : $scope.activeUser.userId, "key" : "activeProjectId", "value" : currentProjectId, "version" : version};
    	
    	actionHelper.invokeAction("userSetting.update", model, null, editCallBack);
    };
    
    // To get the current active project
    $scope.getActiveProject = function(){
    	return presentProjectId;
    };
    
}]);