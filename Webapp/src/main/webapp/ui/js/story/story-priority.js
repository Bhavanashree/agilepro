$.application.controller('storyPriorityController', ["$scope", "actionHelper", 
                                              function($scope, actionHelper) {

	// Dragging methods
	$scope.dragBacklog = function(event){
	
		$scope.draggingId = event.target.id;
		
		console.log("drag backlog is called");
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
	};

	
	$scope.onDropAreaEnter = function(event){
		
		console.log("drop");
		
		event.preventDefault();
		
		var dropId = event.target.id;
		
		$("#" + dropId).height(30);
		
		console.log($("#" + dropId).height());
	};
	
	$scope.onDropAreaLeave = function(event){
		
		console.log("testMouse()");
		
		var dropId = event.target.id;
		
		$("#" + dropId).height(20);
		
	};
	
	
	$scope.onDropBacklog = function(event){
		
		var droppingAreaId = event.target.id;
		
		
		actionHelper.invokeAction("story.updatePriority", null, 
				{"id" : $scope.draggingId, 
				 "newPriority" : $scope.getBacklog(droppingAreaId).priority,
				 "projectId" : $scope.getActiveProjectId()}, 
				function(updateResposne, respConfig)
				{
					
				}, {"hideInProgress" : true});
	};

}]);
