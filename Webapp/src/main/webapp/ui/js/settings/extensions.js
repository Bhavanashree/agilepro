$.application.controller('extensionsController', ["$scope", "crudController", function($scope, crudController) {

	crudController.extend($scope, {
		"name": "Extension-field",
		"modelName": "ExtensionFieldModel",
		
		"nameColumn" : "name",
		
		"modelDailogId": "extensionsModelDialog",
		
		"saveAction": "extensions.save",
		"updateAction": "extensions.update",
		
		"readOp" : function($scope, actionHelper, callback) {
			actionHelper.invokeAction("extensions.fetch.field", null, {
				"name": $scope.searchQuery["extensionName"],
				"id": $scope.selectedId
			}, callback);
		},
		
		"deleteOp" : function($scope, actionHelper, callback) {
			actionHelper.invokeAction("extensions.delete", null, {
				"name": $scope.searchQuery["extensionName"],
				"id": $scope.selectedId
			}, callback);
		},
		
		"validateOp": function(model, $scope) {
			var errors = [];
			
			if(model.type == 'LIST_OF_VALUES')
			{
				if(!model.lovOptions || model.lovOptions.length <= 0)
				{
					errors.push({"field": "lovOptions", "message": "For LIST_OF_VALUES type atleast one 'List of Values' is required"});
				}
			}
			else if(model.type == 'STRING' || model.type == 'MULTI_LINE_STRING')
			{
				if(!model.maxLength || model.maxLength <= 0)
				{
					errors.push({"field": "maxLength", "message": "For " + model.type + " type value should be non zero positive value."});
				}
			}
			
			return errors;
		}
	});
	
	$scope.addLovEntry= function(e){
		var model = $scope.model;
		
		if(!model.lovOptions)
		{
			model.lovOptions = [];
		}
		
		model.lovOptions[model.lovOptions.length] = {
			    label : "",
			    value  : "",
		};
		
		if($scope.errors && $scope.errors.model && $scope.errors.model.lovOptions)
		{
			delete $scope.errors.model.lovOptions;
		}
	};
	
	$scope.removeLovEntry=function(e)
	{ 
		$scope.model.lovOptions.splice(e, 1);
	};
}]);
