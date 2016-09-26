$.application.controller('projectRealseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.fetchAllPrjctAndStrs = function(){
		
		/*actionhelper.invokeAction("project.readAll", null, null, function(readResponse, respConfig){
			$scope.projects = readResponse.model;
		});*/
	};
	
}]);

