$.application.controller('mailDetailsController', ["$scope", "crudController","modelDefService","actionHelper", 
                                                   			function($scope, crudController, modelDefService, actionHelper) {
	crudController.extend($scope, {
		"name": "EmailServerSettings",
		
		"nameColumn" : "name",
		
		"modelDailogId": "emailServerSettingsDialogId",
		
		"saveAction": "mailDetails.save",
		"readAction": "mailDetails.read",
		"updateAction": "mailDetails.update",
		"deleteAction": "mailDetails.delete",
	});
	 var model={};
	 
	$scope.saveMailServer = function()
	{
		
		actionHelper.invokeAction("mailDetails.save",  $scope.mailServerSetting , null, function(read, Response){
		});		
	};
	
	$scope.readMailServer = function(){
		
		actionHelper.invokeAction("mailDetails.fetch", null, null, 
			function(read, Response){
			
				$scope.mailServerSetting = read.model;
				try
				{
			    	$scope.$apply();
				}catch(ex)
				{}
				
			
		}
		,{"hideInProgress" : true});		
	};
}]);
