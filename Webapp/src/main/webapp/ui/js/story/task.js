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
					for(var i = 0 ; i < $scope.itemsFortask.length ; i++)
					{
						var obj = $scope.itemsFortask[i];
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

	// DRAG AND DROP METHODS.
	
	/**
	 * Drag backlogs
	 */
	$scope.dragStoryBugFromSprint = function(event){
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$scope.draggingId = Number((event.target.id).split('_')[1]);
		
		//$scope.onDragOfItemFromSprintToBacklog();
		
		/*
		if($scope.multipleBacklogIds.indexOf($scope.draggingId) == -1)
		{
			$scope.multipleBacklogIds.push($scope.draggingId);
		}
		
		$('#dropStoryForBacklogId').css("border", "3px solid #66c2ff");
		$('#searchBacklogInputId').css("border-bottom", "3px solid #66c2ff");
		$('#dropStoryForBacklogId').css('box-shadow', "5px 5px 5px #888888");
		
		$scope.allowedFromStoryToBacklog = true;
		$scope.allowedFromBacklogToStory = false;*/
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
		
		for(var i = 0 ;i < multipleBugIds.length ; i++)
		{
			var obj = $scope.getBacklogBug(multipleBugIds[i]);
			
			if(obj)
			{
				obj.sprintId = sprintId;
				$scope.itemsFortask.push(obj);
			}
		}
		
		for(var i = 0 ;i < multipleStoryIds.length ; i++)
		{
			var obj = $scope.getBacklogStory(multipleStoryIds[i]);
			
			if(obj)
			{
				obj.sprintId = sprintId;
				$scope.itemsFortask.push(obj);
			}
		}
		
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
						
						$scope.reArrangeTheItems(multipleBugIds, multipleStoryIds, sprintId);
						
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