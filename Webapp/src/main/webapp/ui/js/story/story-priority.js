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
		
		$scope.updateToMaxPriority(index);
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
		
		console.log("$scope.draggingIndex = " + $scope.draggingIndex);
		
		$scope.showOnDrag = true;
		
		if($scope.draggingId == Number(dragId) || ($scope.filteredItems.length == 1))
		{
			return;
		}
		
		$scope.draggingId = Number(dragId);
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$('#dropForLeastPriority').css("background-color", "lightblue");
		
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
	 * Gets invoked on drop area enter.
	 */
	$scope.onDropAreaEnter = function(event){
		
		event.preventDefault();
		
		$scope.expandAreaId = event.target.id;
		$scope.areaId = Number((event.target.id).split('_')[1]);
		var dropAreaIndex = Number($(event.target).attr("name"));
		
		// when item is filtered then no need to check with immediate item
		if(($scope.draggingId == $scope.areaId) || (($scope.draggingIndex == dropAreaIndex + 1) && ($scope.filteredItems.length == 0)))
		{
			return;
		}
		
		if($scope.expandAreaId)
		{
			debugger;
			
			if($scope.filteredItems.length == 0)
			{
				$scope.upperObj = $scope.sortedBacklogs[dropAreaIndex];
				$scope.lowerObj = $scope.sortedBacklogs[dropAreaIndex + 1];
			}
			else if($scope.filteredItems.length > 1)
			{
				$scope.upperObj = $scope.filteredItems[dropAreaIndex];
				$scope.lowerObj = $scope.filteredItems[dropAreaIndex + 1];
			}
			
			if(($scope.lowerObj.priority - $scope.upperObj.priority) == 1)
			{
				$scope.message = $scope.upperObj.title + $scope.lowerObj.title;  
				$("#displayOnPriorityHasNoGap_" + $scope.upperObj.id).css("display", "inherit ");
			}else
			{
				$("#displayBelowOnPriorityHasGap_" + $scope.upperObj.id).css("display", "inherit");
				$("#displayAboveOnPriorityHasGap_" + $scope.upperObj.id).css("display", "inherit");
			}
			
			$("#" + $scope.expandAreaId).height(25);
		}
	};
	
	/**
	 * Gets invoked on drop area leave.
	 */
	$scope.onDropAreaLeave = function(event){
		
		event.preventDefault();
		
		if($scope.expandAreaId == "dropForMaxPriority")
		{
			$("#" + $scope.expandAreaId).height(30);
		}else if($scope.expandAreaId)
		{
			if($scope.lowerObj && $scope.upperObj && ($scope.lowerObj.priority - $scope.upperObj.priority) == 1)
			{
				$("#displayOnPriorityHasNoGap_" + $scope.upperObj.id).css("display", "none");
			}
			
			$("#" + $scope.expandAreaId).height(10);
		}
	};
	
	/**
	 * Gets invoked on drop of backlog.
	 */
	$scope.onDropBacklog = function(event){
		
		event.preventDefault();
		
		var droppingAreaId = Number((event.target.id).split('_')[1]);
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
		
		var indexFrom = $(event.target).attr("name");
		
		$("#" + $scope.expandAreaId).height(15);
		
		$scope.updatePriority(newPriority, indexFrom);
	};
	

	/**
	 * Drop for max priority.
	 */
	$scope.onDropForMaxPriority = function(event){
		
		event.preventDefault();
		
		if((!$scope.draggingIndex) || ($scope.sortedBacklogs.length == 1))
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
		$('#dropForMaxPriority').css("background-color", "white");
	};
	
	
	$scope.mouseDroppedItem = function(event){
		
		$('#dropForLeastPriority').css("background-color", "white");
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
						console.log($scope.draggingIndex);
						
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
						
						for(var index in $scope.sortedBacklogs)
						{
							var o = $scope.sortedBacklogs[index];
							console.log(o.title + "--" + o.priority);
						}
					}
					
				}, {"hideInProgress" : true});
		
	};

	/**
	 * Update the provided story to max priority.
	 */
	$scope.updateToMaxPriority = function(index){
		
		actionHelper.invokeAction("story.updateToMaxPriority", null, {"id" : $scope.draggingId, "projectId" : $scope.getActiveProjectId()},
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
