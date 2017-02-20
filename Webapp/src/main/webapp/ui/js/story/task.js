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
	 * Gets invoked by the angular js filter.
	 * 
	 * Default story filter by story title.
	 */
	$scope.storyBugFilter = function(){
		
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
		
		for(var i = 0 ; i < $scope.itemsFortask.length ; i++)
		{
			var storyObj = $scope.itemsFortask[i];
			
			if(ownerObj)
			{
				if((storyObj.ownerId == ownerObj.id) || (ownerObj.id == 0 && storyObj.ownerId))
				{
					storyObj.display = true;
				}else
				{
					storyObj.display = false;
					continue;
				}
			}
		
			if(storyStatusName)
			{
				if((storyStatusName == "All" || !storyStatusName)|| (storyStatusName == "Completed" && storyObj.status == "COMPLETED")|| (storyStatusName == "Not Completed" && storyObj.status != "COMPLETED"))
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
	 * Fetch stories by sprint
	 */
	$scope.fetchStoriesAndBugBySprint = function(){
		
		$scope.itemsFortask = [];
		$scope.idToStoryTask = {};
		$scope.idToBugTask = {};
		
		if(!$scope.getSelectedSprint())
		{
			return;
		}
		
		actionHelper.invokeAction("storyAndBug.readStoriesAndBugBySprint", null, {"sprintId" : $scope.getSelectedSprint().id},
			function(readResponse, respConfig)
			{
				$scope.storyModels = readResponse.model.storyModels;
				$scope.bugModels  = readResponse.model.bugModels;
				
				$scope.itemsFortask = $scope.storyModels.concat($scope.bugModels);
				$scope.itemsFortask.sort(function(a, b){return a.title.localeCompare(b.title)});
				
				var idToStory = {};
				var idToBug = {};
				var storyIdsInSprint = [];
				
				if($scope.itemsFortask)
				{
					for(var i = 0 ; i < $scope.itemsFortask.length ; i++)
					{
						var obj = $scope.itemsFortask[i];
						obj.display = true;
						obj.childrens = [];
						
						if(obj.isBug)
						{
							idToBug[obj.id] = obj;
						}else
						{
							idToStory[obj.id] = obj;
							storyIdsInSprint.push(obj.id);
						}
					}
				}
				
				$scope.addFetchedStoryItemsToParent(idToBug, idToStory, storyIdsInSprint);
				
				try
				{
					$scope.$apply();
				}catch(ex)
				{}
				
			}, {"hideInProgress" : true});	
	};
	
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
							$scope.message = "Currently there is no note for " + $scope.storyForUpdate.title;
						}else
						{
							$scope.message = "";
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
	$scope.openEditModal = function(id, clickedItemIsBug){
		
		if(clickedItemIsBug)
		{
			$scope.$broadcast("openEditBugDialog", id);
		}else
		{
			$scope.$broadcast("editStory", id);
		}
	};

	// TASK
	
	/**
	 * On click plus.
	 */
	$scope.onClickPlus = function(indexInTask){
		
		var obj = $scope.itemsFortask[indexInTask];

		if($scope.previousClickedIndex == null || $scope.previousClickedIndex == indexInTask)
		{
			obj.expanded = !obj.expanded;
		}
		else if($scope.previousClickedIndex != null && ($scope.previousClickedIndex != indexInTask))
		{
			var previousObj = $scope.itemsFortask[$scope.previousClickedIndex];
			
			previousObj.expanded = false;
			obj.expanded = true;
		}
		
		$scope.previousClickedIndex = indexInTask;
		
		if(obj.expanded)
		{
			$scope.expandedId = obj.id;
			
			if(obj.isBug)
			{
				$scope.expandedItemIsBug = true;
				
				$scope.fetchTasks("bugTask.readByBugId", {"bugId" : obj.id}, obj);
				
			}else
			{
				$scope.expandedItemIsBug = false;
				
				$scope.fetchTasks("storyTask.readByStoryId", {"storyId" : obj.id}, obj);
			}
		}
	};

	/**
	 * Common method to fetch the tasks.
	 */
	$scope.fetchTasks = function(actionType, searchQueryObj, obj){

		actionHelper.invokeAction(actionType, null, searchQueryObj, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						obj.tasks = readResponse.model;
						
						for(var i = 0 ; i < obj.tasks.length ; i++)
						{
							var taskObj = obj.tasks[i];
							
							if($scope.expandedItemIsBug)
							{
								$scope.idToBugTask[taskObj.id] = taskObj;
							}else
							{
								$scope.idToStoryTask[taskObj.id] = taskObj;
							}
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
			if($scope.expandedItemIsBug)
			{
				var model = {"title" : taskTitle, 
						 "estimateTime" : estimateTime, 
						 "bugId" : $scope.expandedId, 
						 "projectId" : $scope.getActiveProjectId(),
						 "status" : "NOT_STARTED",
						 "actualTimeTaken" : 0};
			
				$scope.saveNewTask(model, "bugTask.save");
			}else
			{
				var model = {"title" : taskTitle, 
						 "estimateTime" : estimateTime, 
						 "storyId" : $scope.expandedId, 
						 "projectId" : $scope.getActiveProjectId(),
						 "status" : "NOT_STARTED",
						 "actualTimeTaken" : 0};
			
				$scope.saveNewTask(model, "storyTask.save");
			}
		}
	};

	/**
	 * Common method to save new task.
	 */
	$scope.saveNewTask = function(model, actionType){
		
		actionHelper.invokeAction(actionType, model, null, 
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						model.id = saveResponse.id;
						
						if($scope.expandedItemIsBug)
						{
							var bug = $scope.getSprintBug($scope.expandedId);
							bug.tasks.push(model);
							$scope.idToBugTask[model.id] = model;
						}else
						{
							var story = $scope.getSprintStory($scope.expandedId);
							story.tasks.push(model);
							$scope.idToStoryTask[model.id] = model;
						}
												
						$("#newTaskTitle_" + $scope.expandedItemIsBug + "_" + $scope.expandedId).focus();
						$("#newTaskTitle_" + $scope.expandedItemIsBug + "_" + $scope.expandedId).val("");
						$("#estimateTime_" + $scope.expandedItemIsBug + "_" + $scope.expandedId).val("");

						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
		}, {"hideInProgress" : true});
		
	};
	
	
	/**
	 * Delete task.
	 */
	$scope.deleteTask = function(taskId, indexToRemove){
		
		var taskObj = $scope.expandedItemIsBug ? $scope.idToBugTask[taskId] : $scope.idToStoryTask[taskId];

		var actionType = $scope.expandedItemIsBug ? "bugTask.delete" : "storyTask.delete";
		
		var deleteOp = $.proxy(function(confirmed) {
				
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction(actionType, null, {"id" : taskId}, 
						function(deleteResponse, respConfig)
						{
							if(deleteResponse.code == 0)
							{
								if($scope.expandedItemIsBug)
								{
									$scope.getSprintBug($scope.expandedId).tasks.splice(indexToRemove, 1);
								}else
								{
									$scope.getSprintStory($scope.expandedId).tasks.splice(indexToRemove, 1);
								}
								
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
	 * Fetch task and open the modal.
	 */
	$scope.openTaskModal = function(taskId, indexInTaskArr){
		
		var actionType = $scope.expandedItemIsBug ? "bugTask.read" : "storyTask.read";
		
		$scope.indexInTaskArr = indexInTaskArr;
		
		actionHelper.invokeAction(actionType, null, {"id" : taskId}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						if($scope.expandedItemIsBug)
						{
							$("#bugTaskModelDialogId").modal("show");
							$scope.bugTaskModel = readResponse.model;
						}else
						{
							$("#storyTaskModelDialogId").modal("show");
							$scope.storyTaskModel = readResponse.model;
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
	 * Update task changes.
	 */
	$scope.updateTask = function(){
		
		var actionType = $scope.expandedItemIsBug ? "bugTask.update" : "storyTask.update";
		
		var model = $scope.expandedItemIsBug ? $scope.bugTaskModel : $scope.storyTaskModel;
		
		actionHelper.invokeAction(actionType, model, null,
				function(updateResposne, respConfig)
				{
					if(updateResposne.code == 0)
					{
						if($scope.expandedItemIsBug)
						{
							var bug = $scope.getSprintBug($scope.expandedId);
							
							if(bug)
							{
								bug.tasks[$scope.indexInTaskArr] = model;
							}
							
							$scope.idToBugTask[model.id] = model;
							$("#bugTaskModelDialogId").modal("hide");
						}else
						{
							var story = $scope.getSprintStory($scope.expandedId);
							
							if(story)
							{
								story.tasks[$scope.indexInTaskArr] = model;
							}
							
							$scope.idToStoryTask[model.id] = model;
							$("#storyTaskModelDialogId").modal("hide");
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
	 * On change status.
	 */
	$scope.onStatusChange = function(id, updateItemIsBug, status){
		
		console.log("$scope.onStatusChange");
		
		if(updateItemIsBug)
		{
			$scope.updateParentStatus("bug.updateBugStatus", {"id" : id, "status" : status}, updateItemIsBug);
		}else
		{
			$scope.updateParentStatus("story.updateStoryStatus", {"id" : id, "status" : status}, updateItemIsBug);
		}
	};
	
	/**
	 * Common method to update status.
	 */
	$scope.updateParentStatus = function(actionType, updateQueryObj, updateItemIsBug){

		actionHelper.invokeAction(actionType, null, updateQueryObj,
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						var obj = updateItemIsBug ? $scope.getSprintBug(updateQueryObj.id) : $scope.getSprintStory(updateQueryObj.id);

						obj.status = status;
						
						// if parent story or bug is completed then all the task related to story or bug should be completed. 
						if(status == "COMPLETED")
						{
							var taskArr = obj.tasks;
							for(var i = 0 ; i < taskArr.length ; i++)
							{
								taskArr[i].status = status;
							}
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
	 * On type of actual time.
	 */
	$scope.onTypeActualTime = function(event, taskId){
		
		console.log("on type");
		
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
	 * Update task.
	 */
	$scope.updateTaskChangesByInput = function(actionType){
		
		if($scope.taskChanges && Object.keys($scope.taskChanges).length > 0)
		{
			var model = {"taskChanges" : $scope.taskChanges};
			
			actionHelper.invokeAction(actionType, model, null, 
					function(updateResponse, respConfig)
					{
						for(key in $scope.taskChanges)
						{
							var obj = $scope.expandedItemIsBug ? $scope.idToBugTask[key] : $scope.idToStoryTask[key];
							
							obj.actualTimeTaken = Number(obj.actualTimeTaken) + Number($scope.taskChanges[key].actualTime);
						}
						
						for(var i = 0 ; i < $scope.onTypeTargets.length ; i++)
						{
							$scope.onTypeTargets[i].val("");
						}
						
						$scope.taskChanges = {};
						$scope.onTypeTargets = [];
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
						
					}, {"hideInProgress" : true});
		}
	};
	
	// DRAG AND DROP METHODS.
	
	/**
	 * Drag backlogs
	 */
	$scope.dragStoryBugFromSprint = function(event){
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var arrElem = (event.target.id).split('_');
		
		var draggingItemIsBug = (arrElem[1] == "true");
		var draggingId = Number(arrElem[2]);
		
		$scope.onDragOfItemFromSprintToBacklog(draggingItemIsBug, draggingId);
	};
	
	/**
	 * Recursive method for adding the child stories.
	 */
	$scope.addChildIdsForDrag = function(childArr){
		
		var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds();
		var storyIdsInBacklog = $scope.getStoryIdsInBacklog();
		
		for(var i = 0 ;i < childArr.length ; i++)
		{
			var childObj = childArr[i];
			
			if( (multipleCheckedStoryIds.indexOf(childObj.id) == -1) && (storyIdsInBacklog.indexOf(childObj.id) != -1) )
			{
				$scope.childIdsFromBacklog.push(childObj.id);
			}
			
			var childrens = $scope.idToBacklogStory[childObj.id].childrens;
			
			if(childrens.length > 0)
			{
				$scope.addChildIdsForDrag(childrens);
			}
		}
		
	};
	
	/**
	 * On drop of backlog.
	 */
	$scope.onDropOfBacklog = function(event){
		
		event.preventDefault();
	
		if($scope.getAllowedFromBacklogToStory())
		{
			$scope.childIdsFromBacklog = [];
			
			var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds();
			
			// check for childs in case of dragging single item
			if((multipleCheckedStoryIds.length == 1))
			{
				var parent = $scope.getBacklogStory(multipleCheckedStoryIds[0]);
				
				if(parent)
				{
					var childArr = parent.childrens;
					
					if(childArr.length > 0)
					{
						$scope.addChildIdsForDrag(childArr);
					}
				}
			}
			
			var proceed = true;
			
			if($scope.childIdsFromBacklog.length > 0)
			{
				proceed = false;
				
				var deleteOp = $.proxy(function(confirmed) {
					
					if(!confirmed)
					{
						return;
					}else
					{
						$scope.forSuccessDropOfBacklog();
					}
					
				}, {"$scope": $scope, "proceed": proceed});

				
				utils.confirm(["Are you sure you want drag all the childrens of - '{}'", $scope.idToBacklogStory[$scope.draggingId].title], deleteOp);
			}
			
			if(proceed)
			{
				$scope.forSuccessDropOfBacklog();
			}
			
		}
	};

	
	/**
	 * Gets invoked for success drop of backlog
	 */
	$scope.forSuccessDropOfBacklog = function(){
		
		var sprintObj = $scope.getSelectedSprint();
		
		var multipleCheckedBugIds = $scope.getMultipleCheckedBugIds();
		var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds();
		var draggingId = $scope.getDraggingId();
		var draggingItemIsBug = $scope.getDraggingItem();
		
		if((multipleCheckedBugIds.length > 0) || (multipleCheckedStoryIds.length > 0) && sprintObj) 
		{
			for(var i = 0 ; i <  $scope.childIdsFromBacklog.length ; i++)
			{
				var childId = $scope.childIdsFromBacklog[i];
				
				if(multipleCheckedStoryIds.indexOf(childId) == -1)
				{
					multipleCheckedStoryIds.push(childId);
				}
			}
			
			$scope.updateStorySprint(sprintObj.id, multipleCheckedBugIds, multipleCheckedStoryIds);
		}
		else if(draggingId && sprintObj)
		{
			if(draggingItemIsBug)
			{
				multipleCheckedBugIds = [draggingId];
			}else
			{
				multipleCheckedStoryIds = [draggingId];
			}
			
			$scope.updateStorySprint(sprintObj.id, multipleCheckedBugIds, multipleCheckedStoryIds);
		}
	};
	
	/**
	 * Add items for task.
	 */
	$scope.addToItemsFortask = function(multipleBugIds, multipleStoryIds, sprintId){
		
		var storyIdsInSprint = $scope.getStoryIdsInSprint();
		
		for(var i = 0 ;i < multipleBugIds.length ; i++)
		{
			var obj = $scope.getBacklogBug(multipleBugIds[i]);
			
			if(obj)
			{
				obj.display = true;
				obj.sprintId = sprintId;
				
				$scope.setSprintBug(obj.id, obj);
				
				$scope.itemsFortask.push(obj);
			}
		}
		
		for(var i = 0 ;i < multipleStoryIds.length ; i++)
		{
			var obj = $scope.getBacklogStory(multipleStoryIds[i]);
			
			if(obj)
			{
				obj.display = true;
				obj.sprintId = sprintId;
				
				$scope.setSprintStory(obj.id, obj);
				storyIdsInSprint.push(obj.id);
				
				$scope.itemsFortask.push(obj);
			}
		}
		
		$scope.itemsFortask.sort(function(a, b){return a.title.localeCompare(b.title)});
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};
	
	/**
	 * Common method for updating the sprint.
	 */
	$scope.updateStorySprint = function(sprintId, multipleBugIds, multipleStoryIds){
		
		actionHelper.invokeAction("storyAndBug.updateStorySprintBugSprint", 
					{"multipleBugIds" : multipleBugIds, "multipleStoryIds" : multipleStoryIds, "sprintId" : sprintId}, null,
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						$scope.addToItemsFortask(multipleBugIds, multipleStoryIds, sprintId);
						
						$scope.reArrangeTheBacklogItems(multipleBugIds, multipleStoryIds, sprintId);
						
					}else
					{
						utils.alert("Error in dragging");
					}

				}, {"hideInProgress" : true});
	};
	
	// Listener for broadcast
	$scope.$on("reArrangeSprintItems", function(event, args) {
		
		var draggingItemIsBug = args.draggingItemIsBug;
		var draggingId = args.draggingId;
		
		if(!draggingItemIsBug)
		{
			var storyIdsInSprint = $scope.getStoryIdsInSprint();
			
			storyIdsInSprint.splice(storyIdsInSprint.indexOf(draggingId), 1);
		}
		
		var indexForRemove = -1;
		
		for(var i = 0 ; i < $scope.itemsFortask.length ; i++)
		{
			var obj = $scope.itemsFortask[i];

			if(draggingItemIsBug && obj.id == draggingId && obj.isBug)
			{
				indexForRemove = i;
				break;
			}
			else if(!draggingItemIsBug && obj.id == draggingId && !obj.isBug)
			{
				indexForRemove = i;
				break;
			}
		}
		
		if(indexForRemove >= 0)
		{
			$scope.itemsFortask.splice(indexForRemove, 1);
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
	});
	
	// Listener for broadcast
	$scope.$on("activeSprintSelectionChanged", function(event, args) {
		
		$scope.fetchStoriesAndBugBySprint();
	});
	
	// Listener for broadcast
	$scope.$on("activeOwnerSelectionChanged", function(event, args) {
		
		$scope.commonFilterStory();
	});
	
	// Listener for broadcast
	$scope.$on("activeStoryStatusSelectionChanged", function(event, args) {
		
		$scope.commonFilterStory();
	});
	
	// Listener for broadcast
	$scope.$on("updateTaskChanges", function(event, args) {
		
		var actionType = $scope.expandedItemIsBug ? "bugTask.updateTaskChanges" : "storyTask.updateTaskChanges";
		
		$scope.updateTaskChangesByInput(actionType);
	});
	
}]);