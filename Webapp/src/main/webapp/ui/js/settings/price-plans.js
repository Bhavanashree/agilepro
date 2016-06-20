$.application.controller('pricePlansController', ["$scope", "crudController", "actionHelper", function($scope, crudController, actionHelper) {
	crudController.extend($scope, {
		"name": "Priceplan",
		"modelName": "PricePlanModel",
		
		"nameColumn" : "name",
		
		"modelDailogId": "pricePlansModelDialog",
		
		"readAction": "priceplan.read",
		"saveAction": "priceplan.save",
		"updateAction": "priceplan.update",
		"deleteAction": "priceplan.delete",
		
		"validateOp": function(model, $scope)
		{
			var errors = [];
			
			//if expressions are not present show error
			if(!model.expressions || model.expressions.length <= 0)
			{
				errors.push({"field": "expressions", "message":  "Atleast one expression is required"});
			}

			
			var pattern= new RegExp( /^\w+$/ );
			
           //validate expression names 
		    if(model.expressions)
		     {
			  var expressions = model.expressions;
			   for(var i = 0; i < expressions.length; i++)
			   {
				  if(!pattern.test(expressions[i].name))
				  {
					errors.push({"field": "expressions", "message":  "Expression name should be alphanumeric - '" + expressions[i].name + "'"});
				  }
			
			
			      if(!expressions[i].label || expressions[i].label.length <= 0)
			      {
				   errors.push({"field": "expressions", "message":  "Expression label is required"});
			      }
			
			   }

		     }
			
			
			//validate variable names
		    if(model.numericVariables)
		    {
			 var variables = model.numericVariables;
			  for(var i = 0; i < variables.length; i++)
			  {
				if(!pattern.test(variables[i].name))
				{
					errors.push({"field": "numericVariables", "message":  "Variable name should be alphanumeric - '" + variables[i].name + "'"});
				}
				
				if(!variables[i].label || variables[i].label.length <= 0)
				{
					errors.push({"field": "numericVariables", "message":  "Variable label is required"});
				}
				
			   }
			 }
		  
           return errors;
		  }	
	});
	
	
	/**
	 * Adds a new variable to price plan model
	 */
	$scope.addVar = function(e)
	{
	
		var model = $scope.model;
		
		if(!model.numericVariables)
		{
			model.numericVariables = [];
		}
		
		model.numericVariables[model.numericVariables.length] = {
			    name : "",
			    label  : "",
			    defaultValue       :""
			};
		
		if($scope.errors && $scope.errors.model && $scope.errors.model.numericVariables)
		{
			delete $scope.errors.model.numericVariables;
		}
	};
	
	$scope.addExp = function(e)
	{
		var model = $scope.model;
		
		if(!model.expressions)
		{
			model.expressions = [];
		}
		
		model.expressions[model.expressions.length] = {
			    name : "",
			    label  : "",
			    expression       :""
		};
		
		if($scope.errors && $scope.errors.model && $scope.errors.model.expressions)
		{
			delete $scope.errors.model.expressions;
		}
	};
	
	$scope.insertExp = function(index)
	{
		var model = $scope.model;
		
		var newExpr = {
		    name : "",
		    label  : "",
		    expression :""
		};
		
		model.expressions.splice(index, 0, newExpr);
	};

	$scope.delVar=function(e)
	{ 
		$scope.model.numericVariables.splice(e, 1);
	};
	
	$scope.delExp=function(e)
	{ 
		$scope.model.expressions.splice(e, 1);
	};

	
	
	//Runtime variable list with descriptions
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
		  }
	});
	
	actionHelper.invokeAction("priceplan.readRuntimeVariables", null, null, readVarCallback);
}]);	
