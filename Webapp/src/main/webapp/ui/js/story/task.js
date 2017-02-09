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
	 * Fetch stories by sprint
	 */
	$scope.fetchStoriesAndBugBySprint = function(){
		
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

	// DROP METHODS.
	
	/**
	 * Recursive method for adding the child stories.
	 */
	$scope.addChildIdsForDrag = function(childArr){
		
		var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds();
		var backlogListIds = $scope.getBacklogListIds();
		
		for(index in childArr)
		{
			var childObj = childArr[index];
			
			if( (multipleCheckedStoryIds.indexOf(childObj.id) == -1) && (backlogListIds.indexOf(childObj.id) != -1) )
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
			for(index in $scope.childIdsFromBacklog)
			{
				var childId = $scope.childIdsFromBacklog[index];
				
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
	$scope.$on("activeSprintSelectionChanged", function(event, args) {
		
		$scope.fetchStoriesAndBugBySprint();
	});
	
}]);