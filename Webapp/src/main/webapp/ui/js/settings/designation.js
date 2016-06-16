$.application.controller('designationController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Designation",
		
		"nameColumn" : "name",
		
		"modelDailogId": "designationDialog",
		
		"saveAction": "designation.save",
		"readAction": "designation.read",
		"updateAction": "designation.update",
		"deleteAction": "designation.delete",
	});
}]);
