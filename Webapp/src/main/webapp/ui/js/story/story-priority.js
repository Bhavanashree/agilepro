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
	 * Validate when item is moved down.
	 */
	$scope.validateWhenItemIsMovedDown = function(itemId, newPriority, validateFor){
		
		var backlogObj = $scope.getBacklog(itemId);
		
		var parentStoryId = backlogObj.parentStoryId;
		
		if(parentStoryId)
		{
			var parent = $scope.getBacklog(parentStoryId);
			
			if(newPriority >= parent.priority)
			{
				switch(validateFor)
				{
					case "Drop":
					{
						utils.info("You are not allowed to drop below " + parent.title, 5);
						break;
					}
					case "Input":
					{
						utils.info("Provided priority value cannot be greater than " + parent.title + " priority");
						break;
					}
					case "Button":
					{
						utils.info("You are not allowed to move below " + parent.title);
					}
				}
				
				return false;;
			}
		}
		
		return true;
	};
	
	/**
	 * Validate when item is moved up.
	 */
	$scope.validateWhenItemIsMovedUp = function(itemId, newPriority, validateFor){
		
		var backlogObj = $scope.getBacklog(itemId);
		var childrens = backlogObj.childrens;
		
		if(childrens.length > 0)
		{
			var maxPriorityChildObj = $scope.getMaxPriorityChildObj(childrens);
			
			if(newPriority <= maxPriorityChildObj.priority)
			{
				switch(validateFor)
				{
					case "Drop":
					{
						utils.info("You are not allowed to drop above "+ maxPriorityChildObj.title, 5);
						break;
					}
					case "Input":
					{
						utils.alert("Provided priority value cannot be less than " + maxPriorityChildObj.title + " priority");
						break;
					}
					case "Button":
					{
						utils.info("You are not allowed to move above "+ maxPriorityChildObj.title);
					}
				}
				
				return false;
			}
		}
		
		return true;
	};
	
	
	/**
	 * Gets invoked for stepping one step up for a story.
	 */
	$scope.oneStepUp = function(index){
		
		var storyToBeMovedUp = $scope.sortedBacklogs[index];
		
		var storyToBeMovedDown = $scope.sortedBacklogs[index - 1];
		
		if($scope.validateWhenItemIsMovedUp(storyToBeMovedUp.id, storyToBeMovedDown.priority, "Button"))
		{
			$scope.swapPriority(storyToBeMovedUp, storyToBeMovedDown);
		}
	};
	
	/**
	 * Gets invoked for stepping one step down for a story.
	 */
	$scope.oneStepDown = function(index){
		
		var storyToBeMovedUp = $scope.sortedBacklogs[index + 1];
		
		var storyToBeMovedDown = $scope.sortedBacklogs[index];
		
		if($scope.validateWhenItemIsMovedDown(storyToBeMovedDown.id, storyToBeMovedUp.priority, "Button"))
		{
			$scope.swapPriority(storyToBeMovedUp, storyToBeMovedDown);
		}
	};
	
	/**
	 * Move to the top.
	 */
	$scope.moveToTop = function(id, index){
		
		$scope.draggingId = id;
		
		$scope.draggingIndex = index;
		
		var newPriority = $scope.sortedBacklogs[0].priority;
		
		if($scope.validateWhenItemIsMovedUp($scope.draggingId, newPriority, "Button"))
		{
			$scope.updatePriority(newPriority, 0);
		}
	};
	
	/**
	 * Move to the bottom.
	 */
	$scope.moveToBottom = function(id, index){
		
		$scope.draggingId = id;
		
		$scope.updateToLeastPriority(index, "Button");
	};
	
	/**
	 * Gets invoked on type of new priority.
	 */
	$scope.onTypeNewPriority = function(event, index, backlogId){
		
		event = event || window.event;
		var key = event.keyCode ? event.keyCode : event.which;

		$scope.inputPriorityElm = $(event.target);
		
		var newPriority = $(event.target).val();
		var inputLength = newPriority.trim().length;
		
		// if input is empty
		if(inputLength == 0)
		{
			$scope.newPriorityHasError = false;
		}
		
		newPriority = Number(newPriority);
		
		//enter key   
		if (key == 13) 
		{
			// if input is empty
			if(inputLength == 0)
			{
				utils.info("Please provide the priority value");
				return;
			}
			
			// if input value is not a number
			if(isNaN(newPriority))
			{
				utils.info("Please provide a number");
				$scope.newPriorityHasError = true;
				return;
			}
			
			if(newPriority <= 0)
			{
				utils.info("Please provide the priority value greater than 0");
				$scope.newPriorityHasError = true;
				return;
			}else
			{
				
				if(!$scope.validateWhenItemIsMovedDown(backlogId, newPriority, "Input") || !$scope.validateWhenItemIsMovedUp(backlogId, newPriority, "Input"))
				{
					$scope.newPriorityHasError = true;
					return;
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
	$scope.increasePriorityFrom = function(indexFrom, skipId){
		
		for(var i = indexFrom ; i < $scope.sortedBacklogs.length ; i++)
		{
			var obj = $scope.sortedBacklogs[i];
			
			if(skipId && skipId == obj.id)
			{
				continue;
			}
			
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
		
		if($scope.draggingId == Number(dragId))
		{
			return;
		}
		
		$scope.draggingId = Number(dragId);
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
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
	 * Gets invoked when dragged item mouse enters the drop area above.
	 */
	$scope.onDropAreaAboveEnter = function(event){

		event.preventDefault();

		var htmlElem = $("#" + event.target.id);
		htmlElem.css("height", 30);
		htmlElem.children().css("visibility", "visible");
	};
	
	/**
	 * Gets invoked when dragged item mouse leaves the drop area below.
	 */
	$scope.onDropAreaAboveLeave = function(event){

		event.preventDefault();

		var htmlElem = $("#" + event.target.id);
		htmlElem.css("height", 10);
		htmlElem.children().css("visibility", "hidden");
	};
	
	/**
	 * Gets invoked when dragged item mouse enters the drop area below.
	 */
	$scope.onDropAreaBelowEnter = function(event){
		
		event.preventDefault();

		var htmlElem = $("#" + event.target.id);
		htmlElem.css("height", 30);
		htmlElem.children().css("visibility", "visible");
	};
	
	/**
	 * Gets invoked when dragged item mouse leaves the drop area below.
	 */
	$scope.onDropAreaBelowLeave = function(event){

		event.preventDefault();

		var htmlElem = $("#" + event.target.id);
		htmlElem.css("height", 10);
		htmlElem.children().css("visibility", "hidden");
	};
	
	/**
	 * Gets invoked when item is dropped in drop area above.
	 */
	$scope.onDropAreaAbove = function(event){
		
		event.preventDefault();

		var droppedAreaIndex = Number($(event.target).attr("name"));
		
		var droppedAreaBelowObj = $scope.filteredItems[droppedAreaIndex];
		
		var newPriority = droppedAreaBelowObj.priority - 1;
		
		if(!$scope.validateWhenItemIsMovedDown($scope.draggingId, newPriority, "Drop") || !$scope.validateWhenItemIsMovedUp($scope.draggingId, newPriority, "Drop"))
		{
			return;
		}
		
		$scope.updateNewInputPriority(newPriority, $scope.draggingId);
	};

	/**
	 * Gets invoked when item is dropped in drop area below.
	 */
	$scope.onDropAreaBelow = function(event){
		
		event.preventDefault();
		
		var droppedAreaIndex = Number($(event.target).attr("name"));
		
		var droppedAreaBelowObj = $scope.filteredItems[droppedAreaIndex];
		
		var newPriority = droppedAreaBelowObj.priority + 1;
		
		if(!$scope.validateWhenItemIsMovedDown($scope.draggingId, newPriority, "Drop") || !$scope.validateWhenItemIsMovedUp($scope.draggingId, newPriority, "Drop"))
		{
			return;
		}
		
		$scope.updateNewInputPriority(newPriority, $scope.draggingId);
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

		var newPriority = $scope.getBacklog(droppingAreaId).priority;
		
		if(!$scope.validateWhenItemIsMovedDown($scope.draggingId, newPriority, "Drop") || !$scope.validateWhenItemIsMovedUp($scope.draggingId, newPriority, "Drop"))
		{
			return;
		}
		
		$scope.updatePriority(newPriority, indexFrom);
	};
	
	/**
	 * Gets invoked when mouse release the dragging item.
	 */
	$scope.mouseDroppedItem = function(event){
		
		
	
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
						
						$scope.increasePriorityFrom(indexFrom, $scope.draggingId);
							
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
		
		console.log("blur for " + $scope.newPriorityHasError);
		
		// if new priority has error in input then do not hide the input box
		if(!$scope.newPriorityHasError)
		{
			$("#" + backlogId + "_priorityInputBoxId").val("");
			
			var backlogObj = $scope.getBacklog(backlogId);
			backlogObj.displayInputPriority = false;

		}
	};
	
}]);
