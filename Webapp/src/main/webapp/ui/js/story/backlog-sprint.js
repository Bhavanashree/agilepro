$.application.controller('backLogSprintController', ["$scope", "actionHelper", function($scope, actionHelper) {

	/**
	 * Initialize backlogs
	 */
	$scope.initBackLogs = function(){
		
		var projectId = $scope.getActiveProjectId();
		
		if(projectId == -1)
		{
			return;
		}
		
		actionHelper.invokeAction("story.fetchBacklogsByProjectId", null, {"projectId" : projectId}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$scope.backlogs = readResponse.model;
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
			
				}, {"hideInProgress" : true});
		
	};

	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initBackLogs();
	});

	
}]);
