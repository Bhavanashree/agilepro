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
		
		for(index in childArr)
		{
			var childObj = childArr[index];
			
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
					
					for(index in $scope.backlogs)
					{
						var obj = $scope.backlogs[index];
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
	 * Gets invoked when mouse leaves the dragging item.
	 */
	$scope.mouseReleasedItem = function(event){
		
		$scope.initOnMouseReleasedItem();
		
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initBacklog();
	});
	
}]);