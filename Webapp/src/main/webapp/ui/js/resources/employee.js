$.application.controller('employeeController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Employee",
		
		"nameColumn" : "name",
		
		"modelDailogId": "employeeDialog",
		
		
		"saveAction": "employee.save",
		"readAction": "employee.read",
		"updateAction": "employee.update",
		"deleteAction": "employee.delete",
	});
}]);
