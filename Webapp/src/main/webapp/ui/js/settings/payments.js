$.application.controller('customerPaymentController', [ "$scope",
	"crudController", function($scope, crudController) {
		crudController.extend($scope, {

			"name" : "Payments",
			
			"nameColumn" : "customerName",
			
			"modelDailogId" : "customerPaymentDialog",

			"saveAction" : "payment.save",
			"readAction" : "payment.read",
			"updateAction" : "payment.update",
			"deleteAction" : "payment.delete",
	
		
			"greaterThanEquals" :{
				"validate" : function(model, validationDefValues, value) {
					var otherValue = model[validationDefValues.field];
					
					if(!value || !otherValue)
					{
						return true;
					}
					
					if(value < otherValue)
					{
						return false;
					}
					
					return true;
				}
			},
		
		        "minValue" :{
	                		"validate" : function(model, validationDefValues, value) {
			          	if(!value)
		 		{
					return true;
				}
				
				if(value < validationDefValues.value)
				{
					return false;
				}
				
				return true;
			}
		
		},
		"maxValue" :{
			"validate" : function(model, validationDefValues, value) {
				if(!value)
				{
					return true;
				}
				
				if(value > validationDefValues.value)
				{
					return false;
				}
				
				return true;
			}
		}
		
		
		
		});
}]);


