$.application.controller('taskController', ["$scope", "crudController","utils","modelDefService", "validator","$state","actionHelper",
                                            function($scope, crudController,utils, modelDefService, validator,$state,actionHelper) {
	crudController.extend($scope, {
		"name": "Task",
		"modelName": "TaskModel",
		
		"nameColumn" : "title",
		
		"modelDailogId": "taskDialog",
		
		"saveAction": "task.save",
		"readAction": "task.read",
		"updateAction": "task.update",
		"deleteAction": "task.delete",
	});
	
	
	/**
	 * Initalize task
	 */
	$scope.initTask = function(){
		
		if($scope.getActiveProjectId() == -1)
		{
			return;
		}
		
		actionHelper.invokeAction("story.readByProjectId", null, {"projectId" : $scope.getActiveProjectId()},
				function(readResponse, respConfig)
				{
					$scope.storiesForTask = readResponse.model;
					
					$scope.idToStory = {};
					
					if($scope.storiesForTask)
					{
						for(index in $scope.storiesForTask)
						{
							var obj = $scope.storiesForTask[index];
							
							$scope.idToStory[obj.id] = obj;
						}
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				}, {"hideInProgress" : true});
	};

	/**
	 * On click plus
	 */
	$scope.onClickPlus = function(storyId){
		
		
		actionHelper.invokeAction("task.readByStoryId", null, {"storyId" : storyId}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$scope.tasks = readResponse.model;
						
						$scope.idToStory[storyId].expanded = true;
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
					
				}, {"hideInProgress" : true});
		
	};
	
	
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTask();
	});

}]);