$.application.controller('taskTestController', ["$scope", "crudController", "utils", "actionHelper",
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
	$scope.checkBoxBacklog = function(backlogId, isBug){
		
		var backlogObj = isBug ? $scope.idToBacklogBug[backlogId] : $scope.idToBacklogStory[backlogId];
		
		backlogObj.check = !backlogObj.check; 
		
		if(backlogObj.check)
		{
			if(backlogObj.isBug)
			{
				$scope.multipleCheckedBugIds.push(backlogObj.id);
			}else
			{
				$scope.multipleCheckedStoryIds.push(backlogObj.id);
			}
		}else
		{
			if(backlogObj.isBug)
			{
				var index = $scope.multipleCheckedBugIds.indexOf(backlogObj.id);
				
				$scope.multipleCheckedBugIds.splice(index, 1);
			}else
			{
				var index = $scope.multipleCheckedStoryIds.indexOf(backlogObj.id);
				
				$scope.multipleCheckedStoryIds.splice(index, 1);
			}
		}
		
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
			
			var indexInMultiple = $scope.multipleBacklogIds.indexOf(childObj.id);
			
			if(childObj.check && indexInMultiple == -1)
			{
				$scope.multipleBacklogIds.push(childObj.id);
			}else if(!childObj.check && indexInMultiple != -1)
			{
				$scope.multipleBacklogIds.splice(indexInMultiple, 1);
			}
			
			if(childObj.childrens.length > 0)
			{
				$scope.checkBoxChildStories(childObj.childrens, checkValue);
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
		
		$scope.idToStoryTask = {};
		$scope.idToBugTask = {};
		
		$scope.multipleCheckedStoryIds = [];
		$scope.multipleCheckedBugIds = [];
		
		$scope.itemsForTask = [];
		$scope.backlogs = [];
		
		actionHelper.invokeAction("story.fetchBacklogsForDragByProjectId", null, {"projectId" : projectId},
				function(readResponse, respConfig)
				{
					$scope.backlogStoryModels = readResponse.model.backlogStoryModels;
					$scope.backlogBugModels = readResponse.model.backlogBugModels;
					
					$scope.backlogs = $scope.backlogStoryModels.concat($scope.backlogBugModels);
					
					$scope.backlogs.sort(function(a, b){return a.priority-b.priority});
					
					$scope.idToBacklogStory = {};
					$scope.idToBacklogBug = {};
					
					for(index in $scope.backlogs)
					{
						var obj = $scope.backlogs[index];
						obj.childrens = [];
						
						if(obj.isBug)
						{
							$scope.idToBacklogBug[obj.id]  = obj;
						}else
						{
							$scope.idToBacklogStory[obj.id] = obj;
						}
					}
				
					// add childrens
					$scope.addChildrens($scope.backlogStoryModels, $scope.idToBacklogStory);
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				}, {"hideInProgress" : true});
	};

	/**
	 * Add childrens
	 */
	$scope.addChildrens = function(arrObjs, idToObjMap){
		
		for(index in arrObjs)
		 {
			 var obj =  arrObjs[index];
			 var parent = idToObjMap[obj.parentStoryId];
			 
			 if(parent)
			 {
				 parent.childrens.push(obj);
			 }
		 }
	};
	
	
	// Drag and Drop methods
	
	/**
	 * Drag backlogs
	 */
	$scope.dragBacklogs = function(event){
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var arrElem = (event.target.id).split('_');
		
		var draggingItemIsBug = arrElem[1];
		$scope.draggingId = Number(arrElem[2]);
		
		if(draggingItemIsBug)
		{
			if($scope.multipleCheckedBugIds.indexOf($scope.draggingId) == -1)
			{
				$scope.multipleCheckedBugIds.push($scope.draggingId);
			}
		}else
		{
			if($scope.multipleCheckedStoryIds.indexOf($scope.draggingId) == -1)
			{
				$scope.multipleCheckedStoryIds.push($scope.draggingId);
			}
			
			var childrens = $scope.idToBacklogStory[$scope.draggingId].childrens;
			$scope.childIdsFromBacklog = [];
			var backlogContainsChild = false;

			if(childrens.length > 0)
			{
				for(index in childrens)
				{
					var childObj = childrens[index];
					
					if(($scope.backlogs.indexOf(childObj) != -1))
					{
						backlogContainsChild = true;
						break;
					}
				}
				
				if(backlogContainsChild)
				{
					$scope.addChildIdsForDrag(childrens);
				}
			}
		}
		
		$('#dropStoryForTaskId').css("border", "3px solid #66c2ff");
		$('#dropStoryForTaskId').css('box-shadow', "5px 5px 5px #888888");
		
		$scope.allowedFromBacklogToStory = true;
		$scope.allowedFromStoryToBacklog = false;
	};
	
	
	/**
	 * Recursive method for adding the child stories.
	 */
	$scope.addChildIdsForDrag = function(childArr){
		
		for(index in childArr)
		{
			var childObj = childArr[index];
			
			if($scope.multipleCheckedStoryIds.indexOf(childObj.id) == -1)
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
	 * Gets invoked when mouse leaves the dragging item.
	 */
	$scope.mouseDroppedItem = function(event){
		
		$('#dropStoryForTaskId').css("border", "3px solid grey");
		$('#dropStoryForTaskId').css('box-shadow', "none");
		
		$('#dropStoryForBacklogId').css("border", "3px solid grey");
		$('#searchBacklogInputId').css("border-bottom", "3px solid grey");
		$('#dropStoryForBacklogId').css('box-shadow', "none");
		
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTask();
	});
	
}]);