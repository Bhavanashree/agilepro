$.application.controller('adminUserController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "AdminUser",
		
		"nameColumn" : "userName",
		
		"modelDailogId": "AdminUserModelDialog",
	
		"saveAction": "admin.save",
		"readAction": "admin.read",
		"updateAction": "admin.update",
		"deleteAction": "admin.delete",
	});
}]);