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
	
		$scope.draggingId = event.target.id;
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
	};

	
	$scope.onDropAreaEnter = function(event){
		
		event.preventDefault();
		
		$scope.areaId = event.target.id;
		
		$("#" + $scope.areaId).height(40);
		
		console.log("onDropAreaEnter");
	};
	
	$scope.onDropAreaLeave = function(event){
		
		event.preventDefault();
		
		$("#" + $scope.areaId).height(15);
		
		console.log("onDropAreaLeave");
	};
	
	
	$scope.onDropBacklog = function(event){
		
		event.preventDefault();
		
		var droppingAreaId = event.target.id;
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
