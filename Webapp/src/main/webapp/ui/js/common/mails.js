$.application.controller('emailController', ["$scope", "crudController", "utils", 
                                                "validator", "modelDefService", "logger",
                                                "actionHelper",
		function($scope, crudController, utils, validator, modelDefService, logger, actionHelper) {
	
	
	$scope.sendMail = function(e){
		
		console.log("sendMail is called");
		
		//actionHelper.invokeAction("email.send",$scope.model);
	}
	
}]);