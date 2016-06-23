$.application.controller('tagsController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Tags",
		
		"nameColumn" : "name",
		
		"modelDailogId": "tagsDialogId",
		
		
		"saveAction": "tags.save",
		"readAction": "tags.read",
		"updateAction": "tags.update",
		"deleteAction": "tags.delete",
	});
}]);
