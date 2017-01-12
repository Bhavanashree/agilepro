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
	
	$scope.taskStatusNames = ["NOT_STARTED", "IN_PROGRESS", "COMPLETED"];
	
	/**
	 * Display task for ui.
	 */
	$scope.displayTask = function(status){
		
		if(status == "NOT_STARTED")
		{
			return "Not started";
		}
		
		if(status == "IN_PROGRESS")
		{
			return "In Progress";
		}
		
		if(status == "COMPLETED")
		{
			return "Completed";
		}
	};
	
	
	
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
						
						$scope.idToTask = {};
						
						for(index in $scope.tasks)
						{
							var obj = $scope.tasks[index];
							
							$scope.idToTask[obj.id] = obj;
						}
						
						$scope.taskChanges = {};
						
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
		}/*else (estimateTime <= 0)
		{
			console.log(estimateTime);
			
			$scope.taskError.show = true;
			$scope.taskError.message = "Please provide estimated time greater than 0";
			return;
		}*/
		
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
						$scope.tasks.push(model);
						
						$scope.taskTitle = "";
						$scope.estimateTime = "";
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
				}, {"hideInProgress" : true});
	};
	
	/**
	 * On change of status drop down.
	 */
	$scope.onStatusChange = function(taskId, status){
		
		if(!$scope.taskChanges[taskId])
		{
			$scope.taskChanges[taskId] = {"taskStatus" : status};
		}else
		{
			$scope.taskChanges[taskId].taskStatus = status;
		}
	};
	
	/**
	 * On type of actual time.
	 */
	$scope.onBlurActualTime = function(htmlElem){
		
		/*var actualTime = $(htmlElem).val();
		
		if(!$scope.taskChanges[taskId])
		{
			$scope.taskChanges[taskId] = {"actualTime" : actualTime};
		}else
		{
			$scope.taskChanges[taskId].actualTime = actualTime;
		}*/
		
		console.log("onBlurActualTime");
	};
	
	/**
	 * Delete task.
	 */
	$scope.deleteTask = function(taskId, indexToRemove){
		
		var taskObj = $scope.idToTask[taskId];
		
		var deleteOp = $.proxy(function(confirmed) {
				
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("task.delete", null, {"id" : taskId}, 
						function(deleteResponse, respConfig)
						{
							if(deleteResponse.code == 0)
							{
								$scope.tasks.splice(indexToRemove, 1);
								
								try
								{
									$scope.$apply();
								}catch(ex)
								{}
							}
						}, {"hideInProgress" : true});
			}
			
		}, {"$scope": $scope, "taskId": taskId});

		
		utils.confirm(["Are you sure you want to delete task - '{}'", taskObj.title], deleteOp);
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTask();
	});

}]);