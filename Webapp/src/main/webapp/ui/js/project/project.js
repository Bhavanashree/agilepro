$.application.controller('projectController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Project",
		
		"nameColumn" : "name",
		
		"modelDailogId": "projectDialogId",
		
		
		"saveAction": "project.save",
		"readAction": "project.read",
		"updateAction": "project.update",
		"deleteAction": "project.delete",
	});
}]);
