$.application.controller('holidayController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Holiday",
		"modelName": "HolidayModel",
		
		"nameColumn" : "name",
		
		"modelDailogId": "holidayDialog",
		
		"saveAction": "holiday.save",
		"readAction": "holiday.read",
		"updateAction": "holiday.update",
		"deleteAction": "holiday.delete",
	});
}]);