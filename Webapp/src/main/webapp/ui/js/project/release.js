$.application.controller('releaseController', ["$scope", "crudController", function($scope, crudController) {
	
	crudController.extend($scope, {
		"name": "Release",
		
		"nameColumn" : "name",
		
		"modelDailogId": "releaseDialogId",
		
		"saveAction": "release.save",
		"readAction": "release.read",
		"updateAction": "release.update",
		"deleteAction": "release.delete",
	});
	
}]);

