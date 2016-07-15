$.application.controller('sprintController', ["$scope", "crudController", "utils","modelDefService",
                                               function($scope, crudController,utils, modelDefService) {
	
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

		$scope.newModelMode = true;
		$scope.model =[];
		 $scope.saveSprint = function(e) {
				$scope.addEntry();
				$scope.initErrors("model", true);
		};
}]);		