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
	 * Check box multiple backlogs.
	 */
	$scope.checkBoxBacklog = function(backlogId, indexInBacklogs){
		
		var backlogObj = $scope.idToBacklog[backlogId];
		
		backlogObj.check = !backlogObj.check; 
		
		var indexInMultiple = $scope.multipleBacklogIds.indexOf(backlogId);
		
		if(indexInMultiple == -1)
		{
			$scope.multipleBacklogIds.push(backlogId);
		}else
		{
			$scope.multipleBacklogIds.splice(indexInMultiple, 1);
		}
		
		console.log(backlogObj.childrens);
		
		if(backlogObj.childrens.length > 0)
		{
			$scope.checkBoxChildStories(backlogObj.childrens, backlogObj.check);
		}
		
	};
	
	/**
	 * Check box child stories as per the parent.
	 */
	$scope.checkBoxChildStories = function(childArr, checkValue){
		
		for(index in childArr)
		{
			var childObj = childArr[index];
			
			childObj.check = checkValue;
			
			if(childObj.childrens.length > 0)
			{
				$scope.checkBoxChildStories(childObj.childrens, checkValue);
			}
		}
		
	};
	
	/**
	 * Default backlog filter by backlog title.
	 */
	$scope.backlogFilter = function(searchString){
		
		var retFunc = function(item){
				
			if(!searchString)
			{
				return true;
			}
			
			return item.title.toLowerCase().includes(searchString.toLowerCase());
		};
		
		if($scope.oldSearchBacklog == searchString)
		{
			return retFunc;
		}
				
		$scope.oldSearchBacklog = searchString;

		return retFunc;
	};
	
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
	 * Filter story by status.
	 */
	$scope.commonFilterStory = function(){
		
		var storyStatusName = $scope.getSelectedStoryStatus();
		var ownerObj = $scope.getSelectedOwner();
		
		for(index in $scope.storiesForTask)
		{
			var storyObj = $scope.storiesForTask[index];
			
			if(ownerObj && !storyStatusName)
			{
				if((storyObj.employeeId == ownerObj.id) || (ownerObj.id == 0 && storyObj.employeeId))
				{
					storyObj.display = true;
				}else
				{
					storyObj.display = false;
					continue;
				}
			}
		
			if(!ownerObj && storyStatusName)
			{
				if(storyStatusName == "All" || !storyStatusName)
				{
					storyObj.display = true;
				}else if(storyStatusName == "Completed" && storyObj.status == "COMPLETED")
				{
					storyObj.display = true;
				}else if(storyStatusName == "Not Completed" && storyObj.status != "COMPLETED")
				{
					storyObj.display = true;
				}else
				{
					storyObj.display = false;
				}
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
		$scope.multipleBacklogIds = [];
		$scope.storiesForTask = [];
		$scope.backlogs = [];
		
		actionHelper.invokeAction("story.fetchBacklogsForDragByProjectId", null, {"projectId" : projectId},
				function(readResponse, respConfig)
				{
					$scope.backlogs = readResponse.model;
					
					$scope.idToBacklog = {};
					for(index in $scope.backlogs)
					{
						var obj = $scope.backlogs[index];
						obj.childrens = [];
						
						$scope.idToBacklog[obj.id] = obj;
					}
					
					// add childrens
					for(index in $scope.backlogs)
					 {
						 var backlog =  $scope.backlogs[index];
						
						 var parent = $scope.idToBacklog[backlog.parentStoryId];
						 
						 if(parent)
						 {
							 parent.childrens.push(backlog);
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

		if(!estimateTime || (estimateTime.trim()).length == 0)
		{
			$scope.taskError.show = true;
			$scope.taskError.message = "Please provide the estimated time";
			return;
		}
		
		//estimateTime = toInt(estimateTime);
		
		if (estimateTime <= 0)
		{
			console.log(estimateTime);
			
			$scope.taskError.show = true;
			$scope.taskError.message = "Please provide estimated time greater than 0";
			return;
		}
		
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
		
		$scope.commonFilterStory();
	});
	
	// Listener for broadcast
	$scope.$on("activeStoryStatusSelectionChanged", function(event, args) {
		
		$scope.commonFilterStory();
	});
	
	// Modal open related methods
	
	/**
	 * Fetch the story info and open the modal.
	 */
	$scope.openStoryNoteModal = function(storyId){
		
		$scope.storyForUpdate = $scope.idToStory[storyId];
		
		actionHelper.invokeAction("storyNote.readLatestStoryNoteByStoryId", null, {"storyId" : storyId}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$("#storyNoteModal").modal("show");
						$scope.storyNote = readResponse.model;
						
						if(!$scope.storyNote)
						{
							$scope.storyNote = {"content" : "Currently there is no note for " + $scope.storyForUpdate.title};
						}
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
					
				}, {"hideInProgress" : true});
	};
	
	/**
	 * Open story edit modal.
	 */
	$scope.openStoryEditModal = function(storyId){
		
		$scope.$broadcast("editStory",storyId);
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
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$scope.draggingId = Number((event.target.id).split('_')[1]);
		$scope.multipleBacklogIds.push($scope.draggingId);
		
		$('#dropStoryForTaskId').css("border", "3px solid #66c2ff");
		$('#dropStoryForTaskId').css('box-shadow', "5px 5px 5px #888888");
		
		$scope.allowedFromBacklogToStory = true;
		$scope.allowedFromStoryToBacklog = false;
	};

	/**
	 * Drag backlogs
	 */
	$scope.dragStory = function(event){
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$scope.draggingId = Number((event.target.id).split('_')[1]);
		$scope.multipleBacklogIds.push($scope.draggingId);
		
		$('#dropStoryForBacklogId').css("border", "3px solid #66c2ff");
		$('#searchBacklogInputId').css("border-bottom", "3px solid #66c2ff");
		$('#dropStoryForBacklogId').css('box-shadow', "5px 5px 5px #888888");
		
		$scope.allowedFromStoryToBacklog = true;
		$scope.allowedFromBacklogToStory = false;
	};
	
	/**
	 * On drop of backlog.
	 */
	$scope.onDropOfBacklog = function(event){
		
		event.preventDefault();
		
		if($scope.allowedFromBacklogToStory)
		{
			var sprintObj = $scope.getSelectedSprint();
			
			if($scope.multipleBacklogIds.length > 0 && sprintObj)
			{
				$scope.updateStorySprint(sprintObj.id, $scope.multipleBacklogIds);
			}
			else if($scope.draggingId && sprintObj)
			{
				$scope.updateStorySprint(sprintObj.id, [$scope.draggingId]);
			}
		}
	};
	
	/**
	 * Drag back the story
	 */
	$scope.onDropOfBackStory = function(event){
		
		event.preventDefault();

		if($scope.draggingId && $scope.allowedFromStoryToBacklog)
		{
			$scope.updateStorySprint(null, [$scope.draggingId]);
		}
	};
	
	/**
	 * Gets invoked when mouse leaves the dragging item.
	 */
	$scope.mouseDroppedItem = function(event){
		
		$('#dropStoryForTaskId').css("border", "3px solid grey");
		$('#dropStoryForTaskId').css('box-shadow', "none");
		
		$('#dropStoryForBacklogId').css("border", "3px solid grey");
		$('#searchBacklogInputId').css("border-bottom", "3px solid grey");
		$('#dropStoryForBacklogId').css('box-shadow', "none");
		
		$scope.multipleBacklogIds = [];
	};
	
	
	/**
	 * Common method for updating the story sprint.
	 */
	$scope.updateStorySprint = function(sprintId, ids){
		
		actionHelper.invokeAction("story.updateStorySprint", {"ids" : ids, "sprintId" : sprintId}, null,
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						for(index in ids)
						{
							if(sprintId)
							{
								$scope.reArrangeTheItems(ids[index], sprintId, $scope.backlogs, $scope.idToBacklog, $scope.storiesForTask, $scope.idToStory);
							}else
							{
								$scope.reArrangeTheItems(ids[index], sprintId, $scope.storiesForTask, $scope.idToStory, $scope.backlogs, $scope.idToBacklog);
								
								$scope.backlogs.sort(function(a, b){return a.priority-b.priority});
							}
						}
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}else
					{
						utils.alert("Error in dragging");
					}

				 $scope.draggingId = null;
					
				}, {"hideInProgress" : true});
	};

	/**
	 * Common method for removing and adding the dragged objects.
	 */
	$scope.reArrangeTheItems = function(id, sprintId, sourceArr, sourceMap, destinationArr, destinationMap){
		
		var obj = sourceMap[id];
		obj.sprintId = sprintId;
		obj.display = true;
		
		destinationArr.push(obj);
		
		destinationMap[obj.id] = obj;
		
		for(var index=0; index<sourceArr.length ; index++)
		{
			var idToBeRemove = sourceArr[index].id;
			
			if(idToBeRemove == id)
			{
				sourceArr.splice(index, 1);
				break;
			}
		}
		
	};
	
}]);