$.application.controller('projectsController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Projects",
		
		"nameColumn" : "name",
		
		"modelDailogId": "projectsDialogId",
		
		
		"saveAction": "projects.save",
		"readAction": "projects.read",
		"updateAction": "projects.update",
		"deleteAction": "projects.delete",
	});
}]);
