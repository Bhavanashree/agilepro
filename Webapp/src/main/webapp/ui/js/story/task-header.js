$.application.controller('taskHeaderController', ["$scope", "utils", "actionHelper",
                                               function($scope, utils, actionHelper) {
	
	/**
	 * Initialize task header.
	 * 
	 * Fetch the sprints and owner.
	 */
	$scope.initTaskheader = function(){
		
		$scope.taskStatusNames = ["NOT_STARTED", "IN_PROGRESS", "COMPLETED"];
		$scope.taskStatusForFilter = ["All", "Completed", "Not Completed"];
		
		var projectId = $scope.getActiveProjectId();
		
		if(projectId == -1)
		{
			return;
		}
		
		// Fetch sprints.
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
					
					// set active sprint.
					if($scope.sprints)
					{
						$scope.onSprintChange($scope.sprints[0].id);
					}
			
				}, {"hideInProgress" : true});
			
		// Fetch employees
		actionHelper.invokeAction("employee.readAll", null, null, 
				function(readResponse, respConfig)
				{
					$scope.employees = readResponse.model;

					if($scope.employees)
					{
						var allObj = {"id" : 0, "name" : "All"};
						
						$scope.employees.splice(0, 0, allObj);
					}
					
					$scope.idToEmployee = {};
					
					for(index in $scope.employees)
					{
						var empObj = $scope.employees[index];
						
						$scope.idToEmployee[empObj.id] = empObj;
					}
			
				}, {"hideInProgress" : true});
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}

	};
	
	/**
	 * Display task for ui.
	 */
	$scope.displayTask = function(status){
		
		if(status == "NOT_STARTED" || !status)
		{
			return "Not started";
		}
		
		if(status == "IN_PROGRESS")
		{
			return "In Progress";
		}
		
		if(status == "COMPLETED")
		{
			return "Completed";
		}
	};
	
	/**
	 * On change of sprint from drop down.
	 */
	$scope.onSprintChange = function(sprintId){
		
		$scope.selectedSprintObj = $scope.idToSprint[sprintId];
		
		$scope.$broadcast("activeSprintSelectionChanged");
	};
	
	/**
	 * On change of owner from drop down.
	 */
	$scope.onOwnerChange = function(employeeId){
		
		$scope.selectedOwner = $scope.idToEmployee[employeeId];
		
		$scope.$broadcast("activeOwnerSelectionChanged");
	};
	
	/**
	 * On change of status for filter. 
	 */
	$scope.onStatusChangeForFilter = function(name){
		
		$scope.activeStoryStatus = name; 
		
		$scope.$broadcast("activeStoryStatusSelectionChanged");
	};
	
	/**
	 * Get selected sprint.
	 */
	$scope.getSelectedSprint = function(){
		
		return $scope.selectedSprintObj;
	};
	
	/**
	 * Get selected owner.
	 */
	$scope.getSelectedOwner = function(){
		
		return $scope.selectedOwner;
	};
	
	/**
	 * Get selected story status.
	 */
	$scope.getSelectedStoryStatus = function(){
		
		return $scope.activeStoryStatus;
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTaskheader();
	});
	
}]);