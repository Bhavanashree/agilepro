$.application.controller('storyPriorityController', ["$scope", "actionHelper", 
                                              function($scope, actionHelper) {

	// Dragging methods
	$scope.dragBacklog = function(event){
	
		console.log("drag backlog is called");
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
	};



}]);
