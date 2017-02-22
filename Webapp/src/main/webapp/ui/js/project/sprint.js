$.application.controller('sprintController', ["$scope", "crudController","utils","modelDefService","actionHelper",
                                              function($scope, crudController,utils,modelDefService,actionHelper) {
	crudController.extend($scope, {
		"name": "Sprint",
		"modelName": "SprintModel",
		
		"nameColumn" : "name",
		
		"modelDailogId": "sprintDialog",
		
		
		"saveAction": "sprint.save",
		"readAction": "sprint.read",
		"updateAction": "sprint.update",
		"deleteAction": "sprint.delete",
	});
	
	/**
	 * Add active project id to the new sprint and save.
	 */
	 $scope.saveSprint = function() {
		 
		$scope.model.projectId =  $scope.getActiveProjectId();
			
		$scope.saveChanges();
	};
	
	/**
	 * Customize search query.
	 */
	$scope.customizeSearchQuery = function(searchQuery){
		
		searchQuery.projectId = $scope.getActiveProjectId();
	};
	
	// listener	activeProjectSelectionChanged
	$scope.$on("activeProjectSelectionChanged", function(event, args) {	
	
		$scope.$emit('invokeSearch');
	});
		
}]);

