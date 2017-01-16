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
	
	
	$scope.storyFilter = function(){
		
		var retFunc = function(item){
				
			if(!$scope.storySearch)
			{
				return true;
			}
			
			var searchString = $scope.storySearch.toLowerCase();

			return item.title.toLowerCase().includes(searchString);
		};
		
		if($scope.oldSearchStory == $scope.storySearch)
		{
			return retFunc;
		}
				
		$scope.oldSearchStory = $scope.storySearch;

		return retFunc;
	};

	
	/**
	 * Filter story by sprint.
	 */
	$scope.filterStoryBySprint = function(){
		
		var sprintObj = $scope.getSelectedSprint();
		
		for(index in $scope.storiesForTask)
		{
			var storyObj = $scope.storiesForTask[index];
			
			if(storyObj.sprintId == sprintObj.id)
			{
				storyObj.display = true;
			}else
			{
				storyObj.display = false;
			}
		}
	};
	
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
							
							obj.display = true;
							
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
	 * On click plus.
	 */
	$scope.onClickPlus = function(storyId){
		
		if(($scope.previousStoryId) && ($scope.previousStoryId != storyId))
		{
			if(Object.keys($scope.taskChanges).length > 0)
			{
				utils.alert("Please update");
				return;
			}
			
			$scope.idToStory[$scope.previousStoryId].expanded = false;
		}
		
		if(!$scope.idToStory[storyId].expanded)
		{
			$scope.fetchTaskByStory(storyId);
		}else
		{
			if(Object.keys($scope.taskChanges).length > 0)
			{
				utils.alert("Please update");
				return;
			}
			
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
						
						$scope.onTypeTargets = [];
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
	$scope.addNewTask = function(taskTitle, estimateTime, storyId){

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
			$scope.saveNewTask(taskTitle, estimateTime, storyId);
		}
	};
	
	/**
	 * Save new task uses action helper to call the controller.
	 */
	$scope.saveNewTask = function(taskTitle, estimateTime, storyId){
		
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
						model.id = saveResponse.id;
						
						$scope.tasks.push(model);
						
						$scope.idToTask[model.id] = model;
						
						$("#newTaskTitle_" + storyId).focus();
						$("#newTaskTitle_" + storyId).val("");
						$("#estimateTime_" + storyId).val("");

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
		
		$scope.idToTask[taskId].status = status;
	};
	
	/**
	 * On type of actual time.
	 */
	$scope.onTypeActualTime = function(event, taskId){
		
		var targetType = $(event.target);
		
		$scope.onTypeTargets.push(targetType);
		
		var actualTime = targetType.val();
		
		if(!$scope.taskChanges[taskId])
		{
			$scope.taskChanges[taskId] = {"actualTime" : actualTime};
		}else
		{
			$scope.taskChanges[taskId].actualTime = actualTime;
		}
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
	
	/**
	 * Update task.
	 */
	$scope.updateTask = function(){
		
		if($scope.taskChanges && Object.keys($scope.taskChanges).length > 0)
		{
			var model = {"taskChanges" : $scope.taskChanges};
			
			actionHelper.invokeAction("task.update", model, null, 
					function(updateResponse, respConfig)
					{
						for(key in $scope.taskChanges)
						{
							var obj = $scope.idToTask[key];
							obj.actualTimeTaken = Number(obj.actualTimeTaken) + Number($scope.taskChanges[key].actualTime);
						}
						
						for(index in $scope.onTypeTargets)
						{
							$scope.onTypeTargets[index].val("");
						}
						
						$scope.taskChanges = {};
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
						
					}, {"hideInProgress" : true});
		}
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTask();
	});
	
	// Listener for broadcast
	$scope.$on("activeSprintSelectionChanged", function(event, args) {
		
		$scope.filterStoryBySprint();
	});

}]);