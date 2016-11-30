$.application.controller('tagController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Tag",
		
		"nameColumn" : "name",
		
		"modelDailogId": "tagsDialogId",
		
		
		"saveAction": "tag.save",
		"readAction": "tag.read",
		"updateAction": "tag.update",
		"deleteAction": "tag.delete",
	});
	
}]);

