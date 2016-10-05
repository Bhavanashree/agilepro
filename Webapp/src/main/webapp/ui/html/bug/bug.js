$.application.controller('bugController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Bug",
		
		"nameColumn" : "name",
		
		"modelDailogId": "bugDialog",
		"saveAction": "bug.save",
		"readAction": "bug.read",
		"updateAction": "bug.update",
		"deleteAction": "bug.delete",
	});
	
	
}]);