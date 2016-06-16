$.application.controller('newsController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "News",
		
		"nameColumn" : "title",
		
		"modelDailogId": "newsDialog",
		
		
		"saveAction": "news.save",
		"readAction": "news.read",
		"updateAction": "news.update",
		"deleteAction": "news.delete",
	});
}]);
