$.application.controller('backlogListController', ["$scope", "utils", "actionHelper",
       function($scope, utils, actionHelper) {
	
	/**
	 * Gets invoked by the angular js filter
	 * 
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
	 * Check box multiple backlogs.
	 */
	$scope.checkBoxBacklog = function(backlogId, isBug){
		
		var backlogObj = isBug ? $scope.getBacklogBug(backlogId) : $scope.getBacklogStory(backlogId);
		
		backlogObj.check = !backlogObj.check; 
		
		// executed for the page where drag and drop functionality is avoided.
		if(!$scope.pageConfig.dragAndDropFunctionality)
		{
			// if checked item selected by default not by click.
			if(!$scope.previuosCheckedObj)
			{
				$scope.previuosCheckedObj = $scope.getSelectedBacklogItem();
			}
			
			if($scope.previuosCheckedObj && ($scope.previuosCheckedObj != backlogObj))
			{
				$scope.previuosCheckedObj.check = false;
			}
			
			$scope.previuosCheckedObj = backlogObj; 
			
			if(backlogObj.check)
			{
				$scope.$emit("backlogIsSelectedForPokerGame", {"selectedBacklogItem" : backlogObj});
			}
			
			return;
		}
		
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
		
		$scope.pageConfig = $scope.getPageConfiguraion();
		
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
					
					var storyIdsInBacklog = [];

					for(var i = 0 ;i < $scope.backlogStoryModels.length ; i++)
					{
						var obj = $scope.backlogStoryModels[i];
						storyIdsInBacklog.push(obj.id);
					}
					
					for(var i = 0 ;i < $scope.backlogs.length ; i++)
					{
						var obj = $scope.backlogs[i];
						obj.childrens = [];
						
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
					
					$scope.addFetchedBacklogItemsToParent(idToBacklogBug, idToBacklogStory, storyIdsInBacklog);
					
					// emit for poker game. 
					$scope.$emit("backlogsForPokerGame", {"backlogs" : $scope.backlogs});
					
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

	/**
	 * Add items to backlog.
	 */
	$scope.addToItemsInBacklogs = function(storySprintUpdateModel){
		
		var storyIdsInBacklog = $scope.getStoryIdsInSprint();
		var multipleStoryIds = storySprintUpdateModel.multipleStoryIds;
		
		var draggingId = -1; 
		
		if(multipleStoryIds && multipleStoryIds.length == 1)
		{
			var storyId = multipleStoryIds[0];
			storyIdsInBacklog.push(storyId);
			
			var story = $scope.getSprintStory(storyId);
			story.check = false;
			
			$scope.backlogs.push(story);
			draggingId = storyId;
			$scope.setBacklogStory(storyId, story);
			$scope.setSprintStory(storyId, null);
		}
		
		var multipleBugIds = storySprintUpdateModel.multipleBugIds;
		
		if(multipleBugIds && multipleBugIds.length == 1)
		{
			var bugId = multipleBugIds[0];
			
			var bug = $scope.getSprintBug(bugId);
			bug.check= false;
			
			$scope.backlogs.push(bug);
			draggingId = bugId;
			$scope.setBacklogBug(bugId, bug);
			$scope.setSprintBug(bugId, null);
		}
		
		$scope.backlogs.sort(function(a, b){return a.priority-b.priority});
		
		$scope.reArrangeSprintItems(draggingId);
	};
	
	/**
	 * Calls the controller method for updating the bug or story
	 */
	$scope.updateStoryAndBugToBacklog = function(draggingId){
		
		var draggingItemIsBug = $scope.getDraggingItem();
		
		var storySprintUpdateModel = {"sprintId" : null};
		
		if(draggingItemIsBug)
		{
			storySprintUpdateModel.multipleStoryIds = [];
			storySprintUpdateModel.multipleBugIds = [draggingId];
		}else
		{
			storySprintUpdateModel.multipleStoryIds = [draggingId];
			storySprintUpdateModel.multipleBugIds = [];
		}
		
		
		actionHelper.invokeAction("storyAndBug.updateStorySprintBugSprint", storySprintUpdateModel, null, 
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						$scope.addToItemsInBacklogs(storySprintUpdateModel);
					}
					
					
				}, {"hideInProgress" : true});
		
		
	};
	
	// Drag and Drop methods
	
	/**
	 * Drag backlogs
	 */
	$scope.dragBacklogs = function(event){
		
		if(!$scope.pageConfig.dragAndDropFunctionality)
		{
			return;
		}
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var arrElem = (event.target.id).split('_');
		
		var draggingItemIsBug = (arrElem[1] == "true");
		var draggingId = Number(arrElem[2]);

		var multipleCheckedBugIds = $scope.getMultipleCheckedBugIds();
		var multipleCheckedStoryIds = $scope.getMultipleCheckedStoryIds();
		
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
		}
		
		$scope.onDragOfItemFromBacklogToSprint(draggingItemIsBug, draggingId);
	};
	
	
	/**
	 * Drag back the story
	 */
	$scope.onDropOfBackStory = function(event){
		
		event.preventDefault();

		if($scope.getAllowedFromStoryToBacklog())
		{
			var draggingItemIsBug = $scope.getDraggingItem();
			var draggingId  = $scope.getDraggingId();
			
			if(!draggingItemIsBug)
			{
				var parentStoryId = $scope.getSprintStory(draggingId).parentStoryId;
				
				if(parentStoryId)
				{
					var parentObj = $scope.getSprintStory(parentStoryId);
					var storyIdsInSprint = $scope.getStoryIdsInSprint();
					
					if(parentObj && (storyIdsInSprint.indexOf(parentObj.id) != -1))
					{
						utils.alert("Please drag the " + parentObj.title + " first");
						return;
					}
				}
			}
			
			$scope.updateStoryAndBugToBacklog(draggingId);
		}
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
		
		var multipleBugIds = args.multipleBugIds;
		var multipleStoryIds = args.multipleStoryIds;
		var sprintId = args.sprintId;
		
		var storyIdsInBacklog = $scope.getStoryIdsInBacklog();
		
		if(sprintId)
		{
			for(var i = 0 ; i < multipleBugIds.length ; i++)
			{
				var bugId = multipleBugIds[i];
				
				$scope.setBacklogBug(bugId, null);
				
				$scope.removeFromBacklogList(bugId, true);
			}
			
			for(var i = 0 ; i < multipleStoryIds.length ; i++)
			{
				var storyId = multipleStoryIds[i];
				
				if(storyIdsInBacklog.indexOf(bugId) != -1)
				{
					storyIdsInBacklog.splice(storyIdsInBacklog.indexOf(bugId), 1);
				}
				
				$scope.setBacklogStory(storyId, null);
				
				$scope.removeFromBacklogList(storyId, false);
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
	$scope.removeFromBacklogList = function(id, removeBug){
		
		var indexForRemove = -1;
		
		for(var i = 0 ; i < $scope.backlogs.length ; i++)
		{
			var obj = $scope.backlogs[i];
			
			if(removeBug)
			{
				if(obj.id == id && obj.isBug)
				{
					indexForRemove = i;
					break;
				}
			}else
			{
				if(obj.id == id && !obj.isBug)
				{
					indexForRemove = i;
					break;
				}
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