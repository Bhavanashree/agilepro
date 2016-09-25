$.application.controller('mailSettingsController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "NotificationMailDetails",
		
		"nameColumn" : "name",
		
		"modelDailogId": "mailDialogId",
		
		
		"saveAction": "mailDetails.save",
		"readAction": "mailDetails.read",
		"updateAction": "mailDetails.update",
		"deleteAction": "mailDetails.delete",
	});
}]);
