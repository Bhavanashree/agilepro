$.application.controller('customerInvoiceDetailscontroller',["$scope", "crudController", "actionHelper", "utils",
                   function($scope, crudController, actionHelper, utils) {
		crudController.extend($scope, {

			"name" : "Invoice",
			
			"nameColumn" : "customerInvoice",
			
			"modelDailogId" : "customerInvoiceDetailsDialog",

			"readAction" : "invoice.read",
			
			"deleteAction" : "invoice.delete",
	
		
		});
		
		$scope.steps = function(elements, step) {
			var arr = [];
			
			if(!elements || elements.length == 0)
			{
				return arr;
			}
			
			for(var i = 0; i < elements.length; i+=step)
			{
				arr.push(i);
			}
			
			return arr;
		};
		
		$scope.viewInvoice=function(e)
		{
			var readVarCallback = $.proxy(function(readResponse, respConfig) 
		   {
				  if(readResponse.model)
				  {
					   //this will set data on model, which in turn should update ui
					   $scope.helpInfo = readResponse.model;
					   
					   try
					   {
						   $scope.$digest();
					   }catch(ex)
					   {}
					   
						utils.openModal("invoiceDetailsDialog");
					   
				  }
			});
					
			actionHelper.invokeAction("invoice.read", null,  {
				"id": $scope.selectedId
			}, readVarCallback);
				
		};
	
}]);