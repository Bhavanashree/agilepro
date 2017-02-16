$.application.controller('storyPriorityController', ["$scope", "actionHelper", "utils", 
                                              function($scope, actionHelper, utils) {
	
	$scope.filteredItems = [];
	
	/**
	 * Gets invoked on type of search title in the search input box.
	 * 
	 * Only backlogs are filtered.
	 */
	$scope.priorityStoryFilter = function(){
		
		var retFunc = function(item){
				
			if(!$scope.prioritySearchStory)
			{
				$scope.filteredItems = [];
				return true;
			}
			
			var searchString = $scope.prioritySearchStory.toLowerCase();

			var filtered = item.title.toLowerCase().includes(searchString);
			var index = $scope.filteredItems.indexOf(item);
			
			if(filtered && (index == -1))
			{
				$scope.filteredItems.push(item);
			}else if(!filtered && index != -1)
			{
				$scope.filteredItems.splice(index, 1);
			}
			
			return filtered;
		};
		
		if($scope.oldSearchPriorityStory == $scope.prioritySearchStory)
		{
			return retFunc;
		}
				
		$scope.oldSearchPriorityStory = $scope.prioritySearchStory;

		return retFunc;
	};

	/**
	 * Load and sort by priority.
	 */
	$scope.loadStoriesByPriority = function() {
		
		$scope.sortedBacklogs = [];
		
    	var backLogArr = $scope.getBackLogs();
    	
    	if(!backLogArr)
    	{
    		return;
    	}
    	
    	for(var index in backLogArr)
    	{
    		$scope.sortedBacklogs[index] = backLogArr[index];
    	}
    	
    	$scope.sortAccordingToPriority();
	};
	
	// listener
	$scope.$on("sortBacklogsByPriority", function(event, args) {
		$scope.loadStoriesByPriority();
	});

	/**
	 * Common method for sorting
	 */
	$scope.sortAccordingToPriority = function(){
		
		$scope.sortedBacklogs.sort(function(a, b){return a.priority-b.priority});
		
		try
		{
			$scope.$digest();
		}catch(ex)
		{}
	};
	
	/**
	 * Gets invoked for stepping one step up for a story.
	 */
	$scope.oneStepUp = function(index){
		
		var storyToBeMovedUp = $scope.sortedBacklogs[index];
		
		var storyToBeMovedDown = $scope.sortedBacklogs[index - 1];
		
		$scope.swapPriority(storyToBeMovedUp, storyToBeMovedDown);
	};
	
	/**
	 * Gets invoked for stepping one step down for a story.
	 */
	$scope.oneStepDown = function(index){
		
		var storyToBeMovedUp = $scope.sortedBacklogs[index + 1];
		
		var storyToBeMovedDown = $scope.sortedBacklogs[index];
		
		$scope.swapPriority(storyToBeMovedUp, storyToBeMovedDown);
	};
	
	/**
	 * Move to the top.
	 */
	$scope.moveToTop = function(id, index){
		
		$scope.draggingId = id;
		
		$scope.draggingIndex = index;
		
		$scope.updatePriority($scope.sortedBacklogs[0].priority, 0);
	};
	
	/**
	 * Move to the bottom.
	 */
	$scope.moveToBottom = function(id, index){
		
		$scope.draggingId = id;
		
		$scope.updateToLeastPriority(index);
	};
	
	/**
	 * Gets invoked on type of new priority.
	 */
	$scope.onTypeNewPriority = function(event, index, backlogId){
		
		event = event || window.event;
		var key = event.keyCode ? event.keyCode : event.which;

		$scope.inputPriorityElm = $(event.target);
		
		var newPriority = $(event.target).val();

		// if input is empty
		if(newPriority.trim().length == 0)
		{
			$scope.newPriorityHasError = false;
		}
		
		newPriority = Number(newPriority);
		
		//enter key   
		if (key == 13) 
		{
			if(isNaN(newPriority))
			{
				utils.alert("Please provide a number");
				$scope.newPriorityHasError = true;
				return;
			}
			
			if(newPriority <= 0)
			{
				utils.alert("Please provide the priority value greater than 0");
				$scope.newPriorityHasError = true;
				return;
			}else
			{
				var backlogObj = $scope.getBacklog(backlogId);
				
				var childrens = backlogObj.childrens;
				if(childrens.length > 0)
				{
					var maxPriorityChildObj = $scope.getMaxPriorityChildObj(childrens);
					
					if(newPriority < maxPriorityChildObj.priority)
					{
						utils.alert("Provided priority value cannot be less than " + maxPriorityChildObj.title + " priority");
						$scope.newPriorityHasError = true;
						return;
					}
				}
				
				var parentStoryId = backlogObj.parentStoryId;
				if(parentStoryId)
				{
					var parent = $scope.getBacklog(parentStoryId);
					
					if(newPriority > parent.priority )
					{
						utils.alert("Provided priority value cannot be greater than " + parent.title + " priority");
						$scope.newPriorityHasError = true;
						return;
					}
				}
				
				$scope.updateNewInputPriority(newPriority, backlogId);
			}
			
			$scope.newPriorityHasError = false;
		}
		
	};
	
	/**
	 * Get minimum priority child object.
	 */
	$scope.getMaxPriorityChildObj = function(childArr){
		
		var maxPriorityChildObj = childArr[0];
		
		for(var index = 1; index < childArr.length ; index++)
		{
			var childObj = childArr[index];
			
			if(maxPriorityChildObj.priority < childObj.priority)
			{
				maxPriorityChildObj = childObj;
			}
		}
		
		return maxPriorityChildObj;
	};
	
	
	/**
	 * Update new input priority.
	 */
	$scope.updateNewInputPriority = function(newInputPriority, backlogId){
		
		actionHelper.invokeAction("story.updateInputPriority", null,{"id" : backlogId, "newInputPriority" : newInputPriority, "projectId" : $scope.getActiveProjectId()},
			 function(updateResponse, respConfig)
			 {
				if(updateResponse.code == 0)
				{
					var minPriority = $scope.sortedBacklogs[0].priority; 
					var maxPriority = $scope.sortedBacklogs[$scope.sortedBacklogs.length - 1].priority;
					
					var backlogObj = $scope.getBacklog(backlogId);
					
					if(newInputPriority > maxPriority)
					{
						backlogObj.priority = maxPriority + 1;
					}
					else if(newInputPriority < minPriority)
					{
						$scope.increasePriorityFrom(0);
						
						backlogObj.priority = minPriority;
					}
					else
					{
						for(index in $scope.sortedBacklogs)
						{
							var obj = $scope.sortedBacklogs[index];
							
							if(obj.priority == newInputPriority)
							{
								$scope.increasePriorityFrom(index);
								break;
							}
						}
						
						backlogObj.priority = newInputPriority;
					}
		
					
					backlogObj.displayInputPriority = false;
					$scope.inputPriorityElm.val("");
					$scope.sortAccordingToPriority();
				}
							
			 }, {"hideInProgress" : true});
		
	};
	
	/**
	 * Increase priority from.
	 */
	$scope.increasePriorityFrom = function(indexFrom){
		
		for(var i = indexFrom ; i < $scope.sortedBacklogs.length ; i++)
		{
			var obj = $scope.sortedBacklogs[i];
			
			obj.priority = obj.priority + 1; 
		}
	};
	
	/**
	 * Common method for swapping the priority.
	 * 
	 * Uses action helper to call the controller. 
	 */
	$scope.swapPriority = function(storyToBeMovedUp, storyToBeMovedDown){
		
		actionHelper.invokeAction("story.swapPriority", null, {"idToMoveUp" : storyToBeMovedUp.id, "idToMoveDown" : storyToBeMovedDown.id}, 
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						var tempPriority = storyToBeMovedUp.priority;
						
						storyToBeMovedUp.priority = storyToBeMovedDown.priority;
						storyToBeMovedDown.priority = tempPriority;
						
						$scope.sortAccordingToPriority();
					}
					
				}, {"hideInProgress" : true});
		
	};
	
	
	// Dragging methods
	
	/**
	 * Gets invoked on dragging a backlog.
	 */
	$scope.dragBacklog = function(event){
	
		var dragId = (event.target.id).split('_')[1];
		
		$scope.draggingIndex = Number($(event.target).attr("name"));
		
		$scope.showOnDrag = true;
		
		if($scope.draggingId == Number(dragId))
		{
			return;
		}
		
		$scope.draggingId = Number(dragId);
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var arrayItems = $scope.filteredItems.length > 0 ? $scope.filteredItems : $scope.sortedBacklogs; 
		
		var arraylength = arrayItems.length;
		
		for(var i = 0 ; i < arraylength - 1 ; i++)
		{
			// text should not be displayed for the dragging item area.
			if($scope.draggingIndex == i || $scope.draggingIndex == i + 1)
			{
				continue;
			}
			
			var upperBacklog = arrayItems[i];
			var lowerBacklog = arrayItems[i + 1];
			
			if(lowerBacklog.priority - upperBacklog.priority == 1 || $scope.filteredItems.length == 0)
			{
				var message = upperBacklog.title  + " and " + lowerBacklog.title;
				upperBacklog.message = message;
				
				$("#dropAreaBetween_" + upperBacklog.id).height(25);
				$("#dropAreaBetween_" + upperBacklog.id).css("visibility", "visible");
				$("#dropAreaBetween_" + upperBacklog.id).css("background-color", "#383838");
				
			}else
			{
				$("#dropAreaBelow_" + upperBacklog.id).height(25);
				$("#dropAreaBelow_" + upperBacklog.id).css("visibility", "visible");
				$("#dropAreaBelow_" + upperBacklog.id).css("background-color", "#383838");
			
				$("#dropAreaAbove_" + lowerBacklog.id).height(25);
				$("#dropAreaAbove_" + lowerBacklog.id).css("visibility", "visible");
				$("#dropAreaAbove_" + lowerBacklog.id).css("background-color", "#383838");
			}
		}

		if($scope.draggingIndex != 0)
		{
			$("#dropForMaxPriority").height(25);
			$("#dropForMaxPriority").css("visibility", "visible");
		}
		
		if($scope.draggingIndex != arraylength - 1)
		{
			$("#dropForLeastPriority").height(25);
			$("#dropForLeastPriority").css("visibility", "visible");
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};
	
	/**
	 * Fetch the child ids.
	 */
	$scope.fetchTheChildIds = function(childrens){
		
		for(var i = 0 ; i < childrens.length ; i++)
		{
			var childObj = childrens[i]; 
			$scope.childIds.push(childObj.id);
			
			if(childObj.childrens)
			{
				$scope.fetchTheChildIds(childObj.childrens);
			}
		}
		
	};

	/**
	 * Drop for max priority.
	 */
	$scope.onDropForMaxPriority = function(event){
		
		event.preventDefault();
		
		console.log("onDropForMaxPriority");
		
		var arrayItems = $scope.filteredItems.length > 0 ? $scope.filteredItems : $scope.sortedBacklogs; 
		
		var droppingAreaId = arrayItems[0].id;

		$scope.onDropBacklog(droppingAreaId, 0);
	};
	
	$scope.onDropAboveBacklog = function(event){
		
		event.preventDefault();
		console.log("onDropAboveBacklog");
		
		var droppedAreaIndex = Number($(event.target).attr("name"));
		
		var droppedAreaBelowObj = $scope.filteredItems[droppedAreaIndex];
		
		$scope.updateNewInputPriority(droppedAreaBelowObj.priority - 1, $scope.draggingId);
	};
	
	$scope.onDropBelowBacklog = function(event){
		
		event.preventDefault();
		console.log("onDropBelowBacklog");
		
		var droppedAreaIndex = Number($(event.target).attr("name"));
		
		var droppedAreaBelowObj = $scope.filteredItems[droppedAreaIndex];
		
		$scope.updateNewInputPriority(droppedAreaBelowObj.priority + 1, $scope.draggingId);
	};
	
	$scope.onDropBetweenBacklog = function(event){
		
		event.preventDefault();
		console.log("onDropBetweenBacklog");

		// In case of drop in between index should be consider for the below item.
		var indexFrom = Number($(event.target).attr("name")) + 1;
		
		var arrayItems = $scope.filteredItems.length > 0 ? $scope.filteredItems : $scope.sortedBacklogs; 
		
		var droppingAreaId = arrayItems[indexFrom].id;
		
		$scope.onDropBacklog(droppingAreaId, indexFrom);
	};
	
	$scope.onDropForLeastPriority = function(event){
		
		event.preventDefault();
		console.log("onDropForLeastPriority");
		
		/*if((!$scope.draggingIndex) || ($scope.sortedBacklogs.length == 1))
		{
			return;
		}
		
		var backlogObj = $scope.getBacklog($scope.draggingId);
		var parentStoryId = backlogObj.parentStoryId;
		var maxPriorityObj = $scope.sortedBacklogs[$scope.sortedBacklogs.length - 1];
		
		if(parentStoryId)
		{
			var parent = $scope.getBacklog(parentStoryId);
			
			if(maxPriorityObj.priority > parent.priority)
			{
				utils.info("You are not allowed to drop below " + parent.title, 5);
				$("#" + $scope.expandAreaId).height(15);
				return;
			}
		}
		
		$scope.updateToMaxPriority($scope.draggingIndex);
		
		$scope.draggingIndex = null;
		$('#dropForMaxPriority').css("background-color", "white");*/
		
		$scope.updateToLeastPriority($scope.draggingIndex);
	};

	/**
	 * Gets invoked on drop of backlog.
	 */
	$scope.onDropBacklog = function(droppingAreaId, indexFrom){
		
		var droppingAreaObj = $scope.getBacklog(droppingAreaId);
		
		if($scope.draggingId == droppingAreaId)
		{
			return;
		}
		
		var backlogObj = $scope.getBacklog($scope.draggingId); 
		var childrens = backlogObj.childrens;
		
		// validate priority
		if(childrens.length > 0)
		{
			var maxPriorityChildObj = $scope.getMaxPriorityChildObj(childrens);
			
			if(droppingAreaObj.priority <= maxPriorityChildObj.priority)
			{
				utils.info("You are not allowed to drop above "+ maxPriorityChildObj.title, 5);
				$("#" + $scope.expandAreaId).height(15);
				return;
			}
		}
		
		var parentStoryId = backlogObj.parentStoryId;
		
		if(parentStoryId)
		{
			var parent = $scope.getBacklog(parentStoryId);
			
			if(droppingAreaObj.priority >= parent.priority )
			{
				utils.info("You are not allowed to drop below " + parent.title, 5);
				$("#" + $scope.expandAreaId).height(15);
				return;
			}
		}
		
		var newPriority = $scope.getBacklog(droppingAreaId).priority;
		
		$scope.updatePriority(newPriority, indexFrom);
	};
	
	/**
	 * Gets invoked when mouse release the dragging item.
	 */
	$scope.mouseDroppedItem = function(event){
		
		$('#dropForLeastPriority').css("visibility", "hidden");
		$('#dropForMaxPriority').css("visibility", "hidden");
		$('#dropForMaxPriority').css("height", "5");
		
		for(var i = 0 ; i < $scope.sortedBacklogs.length ; i++)
		{
			var backlog = $scope.sortedBacklogs[i];
			
			$("#dropAreaAbove_" + backlog.id).height(1);
			$("#dropAreaAbove_" + backlog.id).css("visibility", "hidden");
			
			$("#dropAreaBelow_" + backlog.id).height(1);
			$("#dropAreaBelow_" + backlog.id).css("visibility", "hidden");
			
			$("#dropAreaBetween_" + backlog.id).height(1);
			$("#dropAreaBetween_" + backlog.id).css("visibility", "hidden");
		}
	};
	
	
	/**
	 * Common method for updating the priority and calling the controller with the help of action helper.
	 */
	$scope.updatePriority = function(newPriority, indexFrom){
		
		actionHelper.invokeAction("story.updatePriority", null, 
				{"id" : $scope.draggingId, 
				 "newPriority" : newPriority,
				 "projectId" : $scope.getActiveProjectId()},
				 
				function(updateResposne, respConfig)
				{
					if(updateResposne.code == 0)
					{
						$scope.sortedBacklogs[$scope.draggingIndex].priority = newPriority;;
						
						for(var i = indexFrom; i < $scope.sortedBacklogs.length; i++)
						{
							var obj = $scope.sortedBacklogs[i];
							
							if(obj.id != $scope.draggingId)
							{
								obj.priority = obj.priority + 1;
							}
						}
							
						$scope.sortAccordingToPriority();
						
					}
					
				}, {"hideInProgress" : true});
		
	};

	/**
	 * Update the provided story to max priority.
	 */
	$scope.updateToLeastPriority = function(index){
		
		actionHelper.invokeAction("story.updateToLeastPriority", null, {"id" : $scope.draggingId, "projectId" : $scope.getActiveProjectId()},
			function(updateResponse, respConfig)
			{
				if(updateResponse.code == 0)
				{
					$scope.sortedBacklogs[index].priority = $scope.sortedBacklogs[$scope.sortedBacklogs.length - 1].priority + 1;
					
					$scope.sortAccordingToPriority();
				}
				
			}, {"hideInProgress" : true});
	};

	/**
	 * Display input box for the priority.
	 */
	$scope.showInputPriority = function(backlogId){
		
		var backlogObj = $scope.getBacklog(backlogId);
		
		backlogObj.displayInputPriority = true;
		
		setTimeout(function(){
			$("#" + backlogId + "_priorityInputBoxId").focus();
        }, 15);
	};
	
	
	/**
	 * On blur of input box.
	 */
	$scope.onBlurInput = function(backlogId){
		
		// if new priority has error in input then do not hide the input box
		if(!$scope.newPriorityHasError)
		{
			$("#" + backlogId + "_priorityInputBoxId").val("");
			
			var backlogObj = $scope.getBacklog(backlogId);
			backlogObj.displayInputPriority = false;

		}
	};
	
}]);
