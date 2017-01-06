$.application.controller('storyPriorityController', ["$scope", "actionHelper", 
                                              function($scope, actionHelper) {
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
		
		console.log(index);
		
		var storyToBeMovedUp = $scope.sortedBacklogs[index];
		
		var storyToBeMovedDown = $scope.sortedBacklogs[index - 1];
		
		$scope.swapPriority(storyToBeMovedUp, storyToBeMovedDown);
	};
	
	/**
	 * Gets invoked for stepping one step down for a story.
	 */
	$scope.oneStepDown = function(id, priority, index){
		
		$scope.draggingId = id;
		$scope.updatePriority(priority + 1, index + 1);
	};
	
	
	/**
	 * Common method for swapping the priority.
	 * 
	 * Uses action helper to call the controller. 
	 */
	$scope.swapPriority = function(storyToBeMovedUp, storyToBeMovedDown){
		
		actionHelper.invokeAction("story.swapPriority", null, {"idToMoveUp" : storyToBeMovedUp.id, "idToMoveDown" : storyToBeMovedDown.id}, 
				function(r, s)
				{
			
				}, {"hideInProgress" : true});
		
		
		/*var tempPriority = storyToBeMovedUp.priority;
		
		storyToBeMovedUp.priority = storyToBeMovedDown.priority;
		storyToBeMovedDown.priority = tempPriority;
		
		$scope.sortAccordingToPriority();*/
		
	};
	
	
	// Dragging methods
	
	/**
	 * Gets invoked on dragging a backlog.
	 */
	$scope.dragBacklog = function(event){
	
		var dragId = (event.target.id).split('_')[1];
		
		console.log(dragId);
		
		if($scope.draggingId == Number(dragId))
		{
			return;
		}
		
		$scope.draggingId = Number(dragId);
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var backlogObj = $scope.getBacklog($scope.draggingId); 
		
		console.log("dragging title = " + backlogObj.title);
		
		$scope.childIds = [];
		
		if(backlogObj.childrens)
		{
			$scope.fetchTheChildIds(backlogObj.childrens);
		}
	};
	
	/**
	 * Fetch the child ids.
	 */
	$scope.fetchTheChildIds = function(childrens){
		
		for(childObj of childrens)
		{
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
		
		if(($scope.draggingId == $scope.areaId) || ($scope.childIds.indexOf($scope.areaId) != -1))
		{
			return;
		}
		
		$("#" + $scope.expandAreaId).height(40);
	};
	
	/**
	 * Gets invoked on drop area leave.
	 */
	$scope.onDropAreaLeave = function(event){
		
		event.preventDefault();
		
		$("#" + $scope.expandAreaId).height(15);
		
		console.log("onDropAreaLeave");
	};
	
	/**
	 * Gets invoked on drop of backlog.
	 */
	$scope.onDropBacklog = function(event){
		
		event.preventDefault();
		
		var droppingAreaId = Number((event.target.id).split('_')[1]);
		
		if(($scope.draggingId == droppingAreaId) || ($scope.childIds.indexOf($scope.areaId) != -1))
		{
			return;
		}
		
		var newPriority = $scope.getBacklog(droppingAreaId).priority;
		
		var indexFrom = $(event.target).attr("name");
		
		$("#" + $scope.expandAreaId).height(15);
		
		$scope.updatePriority(newPriority, indexFrom);
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
						for(var i = indexFrom; i < $scope.sortedBacklogs.length; i++)
						{
							var obj = $scope.sortedBacklogs[i];
							
							if(obj.id == $scope.draggingId)
							{
								obj.priority = newPriority; 
							}else
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

	
}]);
