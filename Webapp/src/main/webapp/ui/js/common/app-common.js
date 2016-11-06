$.application.controller('commonController', ["$scope", "clientContext", "utils", "actionHelper", function($scope, clientContext, utils, actionHelper) {
	$scope.name = "Common Controller";

	$scope.data = [];
	
	/**
	 * Project mapping from id to project object.
	 */
    $scope.idToProject = {};
    
    /**
     * Current active project (selected by user on top)
     */
    $scope.selectedProject = null;
    
    /**
     * default project id for current user from settings.
     */
    $scope.userDefaultProjectId = -1;
    
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
    
	$scope.fetchProjectMembers = function(){
		
		console.log($scope.getActiveProjectId());
		
		// fetch project members
		actionHelper.invokeAction("projectMember.readProjectMembersByProjectId", null, {"projectId" : $scope.getActiveProjectId()}, 
				function(readResponse, respConfig)
				{
					if(readResponse.model.length > 0)
					{
						$scope.projectMembers = readResponse.model;
					}else
					{
						$scope.projectMembers = [];
					}
				}
		, {"hideInProgress" : true});
		
	};
	
    // Read all the projects call back
    var readProCallBack = function(readResponse, respConfig){
    	
    	$scope.projects = readResponse.model;
    	
    	$scope.showProjects = $scope.projects.length > 0 ? true : false;

    	if(!$scope.showProjects)
    	{
    		$scope.selectedProject = null;
    	}
    	else
    	{
    		$scope.idToProject = {};
    		
    		for(var i = 0; i < $scope.projects.length; i++)
    		{
    			$scope.idToProject["" + $scope.projects[i].id] = $scope.projects[i];
    		}
    		
    		if($scope.userDefaultProjectId > 0)
    		{
    			var project = $scope.idToProject["" + $scope.userDefaultProjectId];
    			
    			$scope.selectedProject = project;
    			
    			// broad cast for (project member) , (sprint display stories according to project)
        		$scope.$broadcast("activeProjectSelectionChanged");
        		
        		$scope.fetchProjectMembers();
    		}
    	}
    	
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
    		$scope.userSettingId = userSettingModel.id;
    		$scope.userSettingVersion = userSettingModel.version;
    		
    		$scope.selectedProject = $scope.idToProject["" + userSettingModel.value];
   			$scope.userDefaultProjectId = userSettingModel.value;
    	}
    	
    	actionHelper.invokeAction("project.readAll", null, null, readProCallBack);
    };
    
    // Invoked on page load for reading user setting and all projects
    $scope.fetchProjects = function(){
    	
    	actionHelper.invokeAction("userSetting.read", null, {"userId" : $scope.activeUser.userId}, readUserCallBack);
    	
    	// moved to readUserCallBack for synch calls
    };
    
    $scope.$on("activeUserIsReady", function(event, args) {

    	$scope.fetchProjects();
	});
    
    /**
     * Event method called when project selection is changed in ui.
     */
    $scope.setActiveProject = function(projectId) {
    	var project = $scope.idToProject["" + projectId];
    	$scope.projectSelectionChanged(project);
    };
    
    // Get invoked while selecting a project
    $scope.projectSelectionChanged = function(proObj){
    	$scope.selectedProject = proObj;
    	
    	if(!proObj)
    	{
    		return;
    	}
    	
		var presentProjectId = proObj.id;
		
		if(!$scope.userSettingId)
    	{
    		saveUserSetting(presentProjectId);
    	}
		else
		{
        	editUserSetting(presentProjectId);
		}
    	
    	// Broad cast 
    	$scope.$broadcast("activeProjectSelectionChanged");
    	console.log("Broad cast in app common");
    };
    
    // Save user call back
    var saveCallBack = function(saveResponse, respConfig){
        
		actionHelper.invokeAction("userSetting.read", null, {"userId" : $scope.activeUser.userId}, readUserCallBack);
    };
    
    // Get invoked only once when a user setting is saved
    saveUserSetting  = function(currentProjectId){
    	
    	var model = {"userId" : $scope.activeUser.userId, "key" : "activeProjectId", "value" : currentProjectId};
		
		actionHelper.invokeAction("userSetting.save", model, null, saveCallBack);
    };
    
    
    // Call back function for every read user setting
    var versionCallBack  = function(readResponse, respConfig){
    	
    	$scope.userSettingVersion = readResponse.model.version;
    };
    
    // Call back function for every update user setting
    var editCallBack = function(saveResponse, respConfig){
    	
    	actionHelper.invokeAction("userSetting.read", null, {"userId" : $scope.activeUser.userId}, versionCallBack);
    };
    
    // Method for update setting
    editUserSetting = function(currentProjectId){
    	
    	var model = {"id" : $scope.userSettingId, "userId" : $scope.activeUser.userId, "key" : "activeProjectId", "value" : currentProjectId, "version" : $scope.userSettingVersion};
    	
    	actionHelper.invokeAction("userSetting.update", model, null, editCallBack);
    };
    
    // To get the current active project
    $scope.getActiveProjectId = function() {
    	if(!$scope.selectedProject)
    	{
    		return -1;
    	}
    	
    	return $scope.selectedProject.id;
    };
    
    $scope.getProjectMembers = function() {
    	
    	return $scope.projectMembers;
    };
    
    /**
     * Extraction actions from conversation message.
     */
    $scope.extractActionUsers = function(mssg) {
    	var htmlElem = $(mssg);
    	var spans = htmlElem.find("span[class='userMention']");
    	
    	if(!spans || spans.length <= 0)
    	{
    		return null;
    	}
    	
    	var actionUsers = [];
    	
    	for(var i = 0; i < spans.length; i++)
    	{
    		actionUsers.push($(spans[i]).attr('id'));
    	}
    	
    	return actionUsers;
    };
    
}]);