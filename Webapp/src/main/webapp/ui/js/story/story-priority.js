$.application.controller('storyPriorityController', ["$scope", "actionHelper", 
                                              function($scope, actionHelper) {

	// listener
	$scope.$on("sortBacklogsByPriority", function(event, args) {

		$scope.sortedBacklogs = [];
		
    	var backLogArr = $scope.getBackLogs();
    	
    	for(var index in backLogArr)
    	{
    		$scope.sortedBacklogs[index] = backLogArr[index];
    	}
    	
    	$scope.sortAccordingToPriority();
    	
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
	
	
	// Dragging methods
	$scope.dragBacklog = function(event){
	
		if($scope.draggingId == event.target.id)
		{
			return;
		}
		
		$scope.draggingId = event.target.id;
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		var backlogObj = $scope.getBacklog($scope.draggingId); 
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
	
	
	$scope.onDropAreaEnter = function(event){
		
		event.preventDefault();
		
		$scope.areaId = event.target.id;
		
		console.log($scope.childIds[0]);
		console.log($scope.areaId);
		console.log($scope.childIds.indexOf($scope.areaId));
		
		if(($scope.draggingId == $scope.areaId) || ($scope.childIds.indexOf($scope.areaId) != -1))
		{
			return;
		}
		
		$("#" + $scope.areaId).height(40);
	};
	
	$scope.onDropAreaLeave = function(event){
		
		event.preventDefault();
		
		$("#" + $scope.areaId).height(15);
		
		console.log("onDropAreaLeave");
	};
	
	
	$scope.onDropBacklog = function(event){
		
		event.preventDefault();
		
		var droppingAreaId = event.target.id;
		
		if(($scope.draggingId == droppingAreaId) || ($scope.childIds.indexOf($scope.areaId) != -1))
		{
			return;
		}
		
		var newPriority = $scope.getBacklog(droppingAreaId).priority;
		
		var indexFrom = $(event.target).attr("name");
		
		$("#" + $scope.areaId).height(15);
		
		actionHelper.invokeAction("story.updatePriority", null, 
				{"id" : $scope.draggingId, 
				 "newPriority" : newPriority,
				 "projectId" : $scope.getActiveProjectId()},
				 
				function(updateResposne, respConfig)
				{
					if(updateResposne.code == 0)
					{
						console.log("success update");
						
						for(var i = indexFrom; i<$scope.sortedBacklogs.length; i++)
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
