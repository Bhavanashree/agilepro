$.application.controller('backlogListController', ["$scope", "utils", "actionHelper",
       function($scope, utils, actionHelper) {
	
	/**
	 * Check box multiple backlogs.
	 */
	$scope.checkBoxBacklog = function(backlogId, isBug){
		
		var backlogObj = isBug ? $scope.idToBacklogBug[backlogId] : $scope.idToBacklogStory[backlogId];
		
		backlogObj.check = !backlogObj.check; 
		
		var multipleCheckedBugIds = $scope.getMultipleCheckedBugIds();
		var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds();
		
		if(backlogObj.check)
		{
			if(backlogObj.isBug)
			{
				multipleCheckedBugIds.push(backlogObj.id);
			}else
			{
				multipleCheckedStoryIds.push(backlogObj.id);
			}
		}else
		{
			if(backlogObj.isBug)
			{
				var index = multipleCheckedBugIds.indexOf(backlogObj.id);
				
				multipleCheckedBugIds.splice(index, 1);
			}else
			{
				var index = multipleCheckedStoryIds.indexOf(backlogObj.id);
				
				multipleCheckedStoryIds.splice(index, 1);
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
		
		var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds(); 
		
		for(var i = 0 ; i < childArr.length ; i++)
		{
			var childObj = childArr[i];
			
			childObj.check = checkValue;
			
			var indexInMultiple = multipleCheckedStoryIds.indexOf(childObj.id);
			
			if(childObj.check && indexInMultiple == -1)
			{
				multipleCheckedStoryIds.push(childObj.id);
			}else if(!childObj.check && indexInMultiple != -1)
			{
				multipleCheckedStoryIds.splice(indexInMultiple, 1);
			}
			
			if(childObj.childrens.length > 0)
			{
				$scope.checkBoxChildStories(childObj.childrens, checkValue);
			}
		}
		
	};

	
	/**
	 * Initalize backlog
	 */
	$scope.initBacklog = function(){
		
		var projectId = $scope.getActiveProjectId();
		
		if(projectId == -1)
		{
			return;
		}
		
		$scope.multipleCheckedStoryIds = [];
		$scope.multipleCheckedBugIds = [];
		
		$scope.backlogs = [];
		
		var idToBacklogStory = {};
		var idToBacklogBug = {};
		
		actionHelper.invokeAction("storyAndBug.fetchBacklogsByProjectId", null, {"projectId" : projectId},
				function(readResponse, respConfig)
				{
					$scope.backlogStoryModels = readResponse.model.backlogStoryModels;
					$scope.backlogBugModels = readResponse.model.backlogBugModels;
					
					$scope.backlogs = $scope.backlogStoryModels.concat($scope.backlogBugModels);
					
					$scope.backlogs.sort(function(a, b){return a.priority-b.priority});
					
					var backlogListIds = [];
					
					for(var i = 0 ;i < $scope.backlogs.length ; i++)
					{
						var obj = $scope.backlogs[i];
						obj.childrens = [];
						
						backlogListIds.push(obj.id);
						
						if(obj.isBug)
						{
							idToBacklogBug[obj.id]  = obj;
						}else
						{
							idToBacklogStory[obj.id] = obj;
						}
					}
				
					// add childrens
					$scope.addChildrens($scope.backlogStoryModels, idToBacklogStory);
					
					$scope.addFetchedItemsToParent(idToBacklogBug, idToBacklogStory, backlogListIds);
					
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
		
		for(var i = 0 ; i < arrObjs.length ; i++)
		 {
			 var obj =  arrObjs[i];
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
		
		//event.preventDefault();
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var arrElem = (event.target.id).split('_');
		
		var draggingItemIsBug = (arrElem[1] == "true");
		var draggingId = Number(arrElem[2]);

		var multipleCheckedBugIds = $scope.getMultipleCheckedBugIds();
		var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds();
		
	/*	$scope.childIdsFromBacklog = [];
		var backlogContainsChild = false;*/
		
		if(draggingItemIsBug)
		{
			if(multipleCheckedBugIds.indexOf(draggingId) == -1)
			{
				multipleCheckedBugIds.push(draggingId);
			}
		}else
		{
			if(multipleCheckedStoryIds.indexOf(draggingId) == -1)
			{
				multipleCheckedStoryIds.push(draggingId);
			}
			
			/*var childrens = ($scope.idToBacklogStory[$scope.draggingId]).childrens;

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
*/		}
		
		$scope.onDragOfItemFromBacklogToSprint(draggingItemIsBug, draggingId);
	};
	
	
	/**
	 * Drag back the story
	 */
	$scope.onDropOfBackStory = function(event){
		
		event.preventDefault();

		/*var parentStoryId = $scope.idToStory[$scope.draggingId].parentStoryId;
		
		var parentObj = $scope.idToStory[parentStoryId];
		
		if(parentObj && ($scope.storiesForTask.indexOf(parentObj) != -1))
		{
			utils.alert("Please drag the " + parentObj.title + " first");
			return;
		}
		
		if($scope.draggingId && $scope.allowedFromStoryToBacklog)
		{
			$scope.updateStorySprint(null, [$scope.draggingId]);
		}*/
	};
	

	/**
	 * Gets invoked when mouse leaves the dragging item.
	 */
	$scope.mouseReleasedItem = function(event){
		
		$scope.initOnMouseReleasedItem();
		
	};
	
	// Listener for broadcast
	$scope.$on("reArrangeTheBacklogItems", function(event, args) {
		
		console.log("Listener reArrangeTheBacklogItems");
		
		//debugger;
		
		var multipleBugIds = args.multipleBugIds;
		var multipleStoryIds = args.multipleStoryIds;
		var sprintId = args.sprintId;
		
		var backlogListIds = $scope.getBacklogListIds();
		
		if(sprintId)
		{
			for(var i = 0 ; i < multipleBugIds.length ; i++)
			{
				var bugId = multipleBugIds[i];
				
				if(backlogListIds.indexOf(bugId) != -1)
				{
					backlogListIds.splice(backlogListIds.indexOf(bugId), 1);
				}
				
				$scope.setBacklogBug(bugId, null);
				
				$scope.removeFromBacklogList(bugId);
			}
			
			for(var i = 0 ; i < multipleStoryIds.length ; i++)
			{
				var storyId = multipleStoryIds[i];
				
				if(backlogListIds.indexOf(storyId) != -1)
				{
					backlogListIds.splice(backlogListIds.indexOf(storyId), 1);
				}
				
				$scope.setBacklogStory(storyId, null);
				
				$scope.removeFromBacklogList(storyId);
			}
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
	});

	/**
	 * Remove from backlog list.
	 */
	$scope.removeFromBacklogList = function(id){
		
		var indexForRemove = -1;
		
		for(var i = 0 ; i < $scope.backlogs.length ; i++)
		{
			var obj = $scope.backlogs[i];
			
			if(obj.id == id)
			{
				indexForRemove = i;
				break;
			}
		}
		
		if(indexForRemove >= 0)
		{
			$scope.backlogs.splice(indexForRemove, 1);
		}
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initBacklog();
	});
	
}]);