$.application.controller('holidayController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Holiday",
		
		"nameColumn" : "name",
		
		"modelDailogId": "holidayDialog",
		
		"saveAction": "holiday.save",
		"readAction": "holiday.read",
		"updateAction": "holiday.update",
		"deleteAction": "holiday.delete",
	});
}]);