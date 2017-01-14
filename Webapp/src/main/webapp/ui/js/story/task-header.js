$.application.controller('taskHeaderController', ["$scope", "utils", "actionHelper",
                                               function($scope, utils, actionHelper) {
	/**
	 * Initialize task header.
	 * 
	 * Fetch the sprints and owner.
	 */
	$scope.initTaskheader = function(){
		
		var projectId = $scope.getActiveProjectId();
		
		if(projectId == -1)
		{
			return;
		}
		
		actionHelper.invokeAction("sprint.sprintProjectId", null, {"projectId" : projectId}, 
				function(readResponse, respConfig)
				{
					$scope.sprints = readResponse.model;
					
					$scope.idToSprint = {};
					
					for(index in $scope.sprints)
					{
						var sprintObj = $scope.sprints[index];
						
						$scope.idToSprint[sprintObj.id] = sprintObj;
					}
			
				}, {"hideInProgress" : true});
		
	};
	
	/**
	 * On change of sprint from drop down.
	 */
	$scope.onSprintChange = function(sprintId){
		
		$scope.selectedSprintObj = $scope.idToSprint[sprintId];
		
		$scope.$broadcast("activeSprintSelectionChanged");
	};
	
	/**
	 * Get selected sprint.
	 */
	$scope.getSelectedSprint = function(){
		
		return $scope.selectedSprintObj;
	};
	
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTaskheader();
	});
	
}]);