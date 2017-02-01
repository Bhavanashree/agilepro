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
	
	
	 $scope.model = {};
	 $scope.saveSprint = function() {
		projectId = $scope.getActiveProjectId();
			
		$scope.model.projectId =  projectId;
			
		$scope.saveChanges();
		};
		
		
		$scope.$on("activeProjectSelectionChanged", function(event, args) {	
			console.log("Kanban Board: Project change event recieved");
			$scope.listOfSprint();
		});
		
}]);

