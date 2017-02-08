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
			
			var indexInMultiple = $scope.multipleCheckedStoryIds.indexOf(childObj.id);
			
			if(childObj.check && indexInMultiple == -1)
			{
				$scope.multipleCheckedStoryIds.push(childObj.id);
			}else if(!childObj.check && indexInMultiple != -1)
			{
				$scope.multipleCheckedStoryIds.splice(indexInMultiple, 1);
			}
			
			if(childObj.childrens.length > 0)
			{
				$scope.checkBoxChildStories(childObj.childrens, checkValue);
			}
		}
		
	};
	
	/**
	 * Fetch stories by sprint
	 */
	$scope.fetchStoriesBySprint = function(){
		
		actionHelper.invokeAction("storyAndBug.readStoriesAndBugBySprint", null, {"sprintId" : $scope.getSelectedSprint().id},
			function(readResponse, respConfig)
			{
				$scope.storyModels = readResponse.model.storyModels;
				$scope.bugModels  = readResponse.model.bugModels;
				
				$scope.itemsFortask = $scope.storyModels.concat($scope.bugModels);
				
				$scope.idToStory = {};
				$scope.idToBug = {};
				
				if($scope.itemsFortask)
				{
					for(index in $scope.itemsFortask)
					{
						var obj = $scope.itemsFortask[index];
						obj.display = true;
						obj.childrens = [];
						
						if(obj.isBug)
						{
							$scope.idToBug[obj.id] = obj;
						}else
						{
							$scope.idToStory[obj.id] = obj;
						}
					}
				}
				
				
				try
				{
					$scope.$apply();
				}catch(ex)
				{}
				
			}, {"hideInProgress" : true});	
	};
	
	// Drag and Drop methods
	
	/**
	 * Drag backlogs
	 */
	$scope.dragBacklogs = function(event){
		
		//event.preventDefault();
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var arrElem = (event.target.id).split('_');
		
		$scope.draggingItemIsBug = (arrElem[1] == "true");
		$scope.draggingId = Number(arrElem[2]);

		$scope.childIdsFromBacklog = [];
		var backlogContainsChild = false;
		
		if($scope.draggingItemIsBug)
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
			
			var childrens = ($scope.getBacklogStory($scope.draggingId)).childrens;

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
		
		console.log("$scope.multipleCheckedStoryIds "  + $scope.multipleCheckedStoryIds);
		
		console.log("$scope.multipleCheckedBugIds " + $scope.multipleCheckedBugIds);
		
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
		
		
		$scope.multipleCheckedBugIds = [];
		$scope.multipleCheckedStoryIds = [];
	};
	
	/**
	 * On drop of backlog.
	 */
	$scope.onDropOfBacklog = function(event){
		
		event.preventDefault();
		
		if($scope.allowedFromBacklogToStory)
		{
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
		
		if((($scope.multipleCheckedBugIds.length > 0) || ($scope.multipleCheckedStoryIds.length > 0)  || ($scope.childIdsFromBacklog.length > 0)) && sprintObj) 
		{
			for(index in $scope.childIdsFromBacklog)
			{
				var childId = $scope.childIdsFromBacklog[index];
				
				if($scope.multipleCheckedStoryIds.indexOf(childId) == -1)
				{
					$scope.multipleCheckedStoryIds.push(childId);
				}
			}
			
			$scope.updateStorySprint(sprintObj.id, $scope.multipleCheckedBugIds, $scope.multipleCheckedStoryIds);
		}
		else if($scope.draggingId && sprintObj)
		{
			if($scope.draggingItemIsBug)
			{
				$scope.multipleCheckedBugIds = [$scope.draggingId];
			}else
			{
				$scope.multipleCheckedStoryIds = [$scope.draggingId];
			}
			
			$scope.updateStorySprint(sprintObj.id, $scope.multipleCheckedBugIds, $scope.multipleCheckedStoryIds);
		}
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
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}else
					{
						utils.alert("Error in dragging");
					}

				$scope.multipleBacklogIds = [];
				$scope.draggingId = null;
					
				}, {"hideInProgress" : true});
	};

	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTask();
	});
	
	// Listener for broadcast
	$scope.$on("activeSprintSelectionChanged", function(event, args) {
		
		$scope.fetchStoriesBySprint();
	});
	
}]);