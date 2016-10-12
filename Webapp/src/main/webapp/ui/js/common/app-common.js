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
    
    var projArr;
    
    var userSettingId;
    
    var version;
    
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
    		userSettingId = userSettingModel.id;
    		version = userSettingModel.version;
    		
    		$scope.selectedProject = $scope.idToProject["" + userSettingModel.value];
   			$scope.userDefaultProjectId = userSettingModel.value;
    		
    		// broad cast for (project member) , (sprint display stories according to project)
    		$scope.$broadcast("activeProjectSelectionChanged");
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
		
		if(!userSettingId)
    	{
    		saveUserSetting(presentProjectId);
    	}
		else
		{
        	editUserSetting(presentProjectId);
		}
    	
    	// Broad cast 
    	$scope.$broadcast("activeProjectSelectionChanged");
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
    	
    	version = readResponse.model.version;
    };
    
    // Call back function for every update user setting
    var editCallBack = function(saveResponse, respConfig){
    	
    	actionHelper.invokeAction("userSetting.read", null, {"userId" : $scope.activeUser.userId}, versionCallBack);
    };
    
    // Method for update setting
    editUserSetting = function(currentProjectId){
    	
    	var model = {"id" : userSettingId, "userId" : $scope.activeUser.userId, "key" : "activeProjectId", "value" : currentProjectId, "version" : version};
    	
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
    
}]);