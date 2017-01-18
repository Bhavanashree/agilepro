$.application.controller('taskController', ["$scope", "crudController", "utils", "actionHelper",
                                            function($scope, crudController, utils, actionHelper) {
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
	 * Default story filter by story title.
	 */
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
	 * Filter story by owner.
	 */
	$scope.filterStoryByOwner = function(){
		
		var ownerObj = $scope.getSelectedOwner();
		
		for(index in $scope.storiesForTask)
		{
			var storyObj = $scope.storiesForTask[index];
			
			if(storyObj.employeeId == ownerObj.id)
			{
				storyObj.display = true;
			}else
			{
				storyObj.display = false;
			}
		}
	};
	
	/**
	 * Filter story by status.
	 */
	$scope.filterStoryByStoryStatus = function(){
		
		var storyStatus = $scope.getSelectedStoryStatus();
		
		for(index in $scope.storiesForTask)
		{
			var storyObj = $scope.storiesForTask[index];
			
			if(storyObj.status == storyStatus)
			{
				storyObj.display = true;
			}else
			{
				storyObj.display = false;
			}
		}
	};
	
	/**
	 * Initalize task
	 */
	$scope.initTask = function(){
		
		var projectId = $scope.getActiveProjectId();
		
		if(projectId == -1)
		{
			return;
		}
		
		$scope.idToTask = {};
		
		actionHelper.invokeAction("story.fetchBacklogsByProjectId", null, {"projectId" : projectId},
				function(readResponse, respConfig)
				{
					$scope.backlogs = readResponse.model;
					
					$scope.idToBacklog = {};
					for(index in $scope.backlogs)
					{
						var obj = $scope.backlogs[index];
						
						$scope.idToBacklog[obj.id] = obj;
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				}, {"hideInProgress" : true});
		
	};

	
	/**
	 * Fetch stories by sprint
	 */
	$scope.fetchStoriesBySprint = function(){
		
		actionHelper.invokeAction("story.readStoriesBySprint", null, {"sprintId" : $scope.getSelectedSprint().id},
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
						var storyObj = $scope.idToStory[storyId]; 
						
						storyObj.tasks = readResponse.model;
						storyObj.expanded = true;
						$scope.expandedStoryId = storyId;
						
						for(index in storyObj.tasks)
						{
							var taskObj = storyObj.tasks[index];
							
							$scope.idToTask[taskObj.id] = taskObj;
						}
						
						$scope.taskChanges = {};
						$scope.onTypeTargets = [];
						$scope.taskError = {"show" : false, "message" : ""};
						
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
						
						$scope.idToStory[storyId].tasks.push(model);
						
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
	 * On change of story status.
	 */
	$scope.onStoryStatusChange = function(storyId, status){
		
		actionHelper.invokeAction("story.updateStoryStatus", null, {"id" : storyId, "status" : status},
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						$scope.idToStory[storyId].status = status;
					}
					
					if(status == "COMPLETED")
					{
						var taskArr = $scope.idToStory[storyId].tasks;
						
						for(index in taskArr)
						{
							taskArr[index].status = status;
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
	 * On change of status drop down.
	 */
	$scope.onTaskStatusChange = function(taskId, status){
		
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
								$scope.idToStory[$scope.expandedStoryId].tasks.splice(indexToRemove, 1);
								
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
	$scope.updateTaskChanges = function(){
		
		if($scope.taskChanges && Object.keys($scope.taskChanges).length > 0)
		{
			var model = {"taskChanges" : $scope.taskChanges};
			
			actionHelper.invokeAction("task.updateTaskChanges", model, null, 
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
		
		$scope.fetchStoriesBySprint();
	});

	// Listener for broadcast
	$scope.$on("activeOwnerSelectionChanged", function(event, args) {
		
		$scope.filterStoryByOwner();
	});
	
	// Listener for broadcast
	$scope.$on("activeStoryStatusSelectionChanged", function(event, args) {
		
		$scope.filterStoryByStoryStatus();
	});
	
	// Modal open related methods
	
	/**
	 * Fetch the story info and open the modal.
	 */
	$scope.openStoryNoteModal = function(storyId){
		
		actionHelper.invokeAction("storyNote.readAllNoteByStoryId", null, {"storyId" : storyId}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$("#storyNoteModal").modal("show");
						$scope.storyNotes = readResponse.model;
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
					
				}, {"hideInProgress" : true});
	};
	
	/**
	 * Fetch task and open the modal.
	 */
	$scope.openTaskModal = function(taskId){
		
		actionHelper.invokeAction("task.read", null, {"id" : taskId}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$("#taskModelDialog").modal("show");
						$scope.taskModel = readResponse.model;
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
					
				}, {"hideInProgress" : true});
	};
	
	/**
	 * Update task changes.
	 */
	$scope.updateTask = function(){
		
		actionHelper.invokeAction("task.update", $scope.taskModel, null,
				function(updateResposne, respConfig)
				{
					if(updateResposne.code == 0)
					{
						$("#taskModelDialog").modal("hide");
					}
				
				}, {"hideInProgress" : true});
	};
	
	// Drag and Drop methods
	
	/**
	 * Drag backlogs
	 */
	$scope.dragBacklogs = function(event){
		
		$scope.draggingId = Number((event.target.id).split('_')[1]);
	};
	
	/**
	 * On drop of backlog.
	 */
	$scope.onDropOfBacklog = function(event){
		
		var sprintObj = $scope.getSelectedSprint();
		
		if($scope.draggingId && sprintObj)
		{
			$scope.updateStorySprint(sprintObj.id);
		}
	};
	
	/**
	 * Common method for updating the story sprint.
	 */
	$scope.updateStorySprint = function(sprintId){
		
		actionHelper.invokeAction("story.updateStorySprint", null, {"id" : $scope.draggingId, "sprintId" : sprintId},
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						$scope.idToBacklog[$scope.draggingId].sprintId = sprintId;
					}
					
				}, {"hideInProgress" : true});
	};
	
}]);