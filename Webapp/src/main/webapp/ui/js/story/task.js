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
		
		$scope.taskError = {"show" : false, "message" : ""};
		
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
		
		if(($scope.previousStoryId) && ($scope.previousStoryId != storyId))
		{
			$scope.idToStory[$scope.previousStoryId].expanded = false;
		}
		
		if(!$scope.idToStory[storyId].expanded)
		{
			$scope.fetchTaskByStory(storyId);
		}else
		{
			$scope.idToStory[storyId].expanded = false;
		}
		
		$scope.previousStoryId = storyId;
		
	};
	
	/**
	 * Fetch task by stories.
	 */
	$scope.fetchTaskByStory = function(storyId){
		
		actionHelper.invokeAction("task.readByStoryId", null, {"storyId" : storyId}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$scope.tasks = readResponse.model;
						
						$scope.idToStory[storyId].expanded = true;
						
						$scope.expandedStoryId = storyId;
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
					
				}, {"hideInProgress" : true});
	};
	
	/**
	 * Gets invoked on click of add button. 
	 */
	$scope.addNewTask = function(taskTitle, estimateTime){

		console.log(estimateTime);
		
		if(!taskTitle)
		{
			$scope.taskError.show = true;
			$scope.taskError.message = "Please provide a title";
			return;
		}

		if(!estimateTime)
		{
			$scope.taskError.show = true;
			$scope.taskError.message = "Please provide the estimated time";
			return;
		}

		$scope.taskError.show = false;
		
		if(taskTitle && estimateTime)
		{
			$scope.saveNewTask(taskTitle, estimateTime);
		}
	};
	
	/**
	 * Save new task uses action helper to call the controller.
	 */
	$scope.saveNewTask = function(taskTitle, estimateTime){
		
		var model = {"title" : taskTitle, 
					 "estimateTime" : estimateTime, 
					 "storyId" : $scope.expandedStoryId, 
					 "projectId" : $scope.getActiveProjectId(),
					 "status" : "NOT_STARTED",
					 "actualTimeTaken" : 0};
		
		actionHelper.invokeAction("task.save", model, null, 
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						
					}
				}, {"hideInProgress" : true});
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTask();
	});

}]);