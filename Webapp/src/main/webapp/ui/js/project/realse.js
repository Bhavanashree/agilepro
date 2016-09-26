$.application.controller('realseController', ["$scope", "crudController", function($scope, crudController) {
	
	crudController.extend($scope, {
		"name": "Realse",
		
		"nameColumn" : "name",
		
		"modelDailogId": "realseDialogId",
		
		"saveAction": "realse.save",
		"readAction": "realse.read",
		"updateAction": "realse.update",
		"deleteAction": "realse.delete",
	});
	
}]);

