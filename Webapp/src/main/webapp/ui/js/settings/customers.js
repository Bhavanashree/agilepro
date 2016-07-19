$.application.controller('customerController', ["$scope", "crudController", "utils", 
                                                "validator", "modelDefService", "logger",
                                                "actionHelper",
		function($scope, crudController, utils, validator, modelDefService, logger, actionHelper) {
	
	crudController.extend($scope, {
		"name": "Customer",
		
		"nameColumn" : "name",
		
		"modelDailogId": "customerDialog",
		
		"readAction": "customer.read",
		"saveAction": "customer.save",
		"updateAction": "customer.update",
		"deleteAction": "customer.delete",
		
		"validateOp": function(model, $scope) {
			var errors = [];
			
			//if price plan has variables
			if($scope.planVariables && $scope.planVariables.length > 0)
			{
				var name = null;
				
				//ensure values are provided for 
				for(var i = 0; i < $scope.planVariables.length; i++)
				{
					name = $scope.planVariables[i].name;
					
					if(!$scope.model.variableMap[name])
					{
						errors.push({"field": "pricePlanVarialbes", "message": "Values are mandatory for all price plan variables"});
						break;
					}
				}
			}
			
			return errors;
		},
		
		"onBeforeShow": function(forAdd, $scope) {
			$('#cusomerDlgBasicTab_tab').tab('show');
		}
	});
	
	
	var count = 0;
	
	$scope.dlgModeField = "newCustomerMode";
	$scope.planVariables = [];
	
	$scope.$watch(function(){
		if(!$scope.model)
		{
			return null;
		}
		
		return $scope.model.customerPricePlanId;
	}, function(newPricePlanId, oldValue){
		
		if(!$scope.model)
		{
			return;
		}
		
		if(!newPricePlanId || newPricePlanId == "")
		{
			$scope.planVariables = [];
			$scope.model.variableMap = {};
			
			try
			{
				$scope.$digest();
			}catch(ex)
			{}
			
			return;
		}
		
		var readVarCallback = $.proxy(function(readResponse, respConfig) {
			
			if(!readResponse || readResponse.code != 0 || !readResponse.model)
			{
				this.logger.error("Failed to read variable details for price plan with id - {}", this.$scope.model.customerPricePlanId);
				this.utils.alert(["Failed to read variable details for price plan with id - {}", this.$scope.model.customerPricePlanId]);
				return;
			}
			
			var planVarLst = readResponse.model;
			var existingValues = this.$scope.model.variableMap;
			
			this.$scope.model.variableMap = {};
			this.$scope.planVariables = [];
			
			if(planVarLst && planVarLst.length > 0)
			{
				for(var i = 0; i < planVarLst.length; i++)
				{
					this.$scope.model.variableMap[planVarLst[i].name] = null;
					
					//if existing value map contains value with same var name, use that value
					if(existingValues && existingValues[planVarLst[i].name])
					{
						this.$scope.model.variableMap[planVarLst[i].name] = existingValues[planVarLst[i].name];
					}
					//else if default value is present use default value
					else if(planVarLst[i].defaultValue)
					{
						this.$scope.model.variableMap[planVarLst[i].name] = planVarLst[i].defaultValue;
					}
				}
				
				this.$scope.planVariables = planVarLst;
			}
			
			this.$scope.$digest();
		}, {"$scope": $scope, "logger": logger, "utils": utils});
		
		try
		{
			actionHelper.invokeAction("priceplan.readVariables", null, {
				"id": newPricePlanId
			}, readVarCallback);
		}catch(ex)
		{
			console.error("An error occurred while reading price plan variables.");
			console.error(ex);
		}
	});

	$scope.initModelDef = function() {
		modelDefService.getModelDef("Customer", $.proxy(function(modelDefResp){
			this.$scope.modelDef = modelDefResp.modelDef;
		}, {"$scope": $scope}));
		
		modelDefService.getModelDef("CustomerPocModel", $.proxy(function(modelDefResp){
			this.$scope.pocModelDef = modelDefResp.modelDef;
		}, {"$scope": $scope}));
	};
	
	$scope.getModelDef = function(prefix) {
		if(prefix == 'pocModel')
		{
			return $scope.pocModelDef;
		}
		
		return $scope.modelDef;
	};
	
	$scope.addPoc = function(e){
		console.log("Add poc is invoked ");

		$scope.newModelMode = true;
		$scope.pocModel = {};
		
		if(count < 15)
			{
				utils.openModal('customerPocModelDialog');
				count = count + 1;
			}
		else
			{
				utils.alert("You have exceeded your limit of adding POC please delete any of them to add more");
			}
		
	};
		
	$scope.editPoc = function(index){
		console.log("editPoc is invoked");
		
		$scope.initErrors("pocModel", false);
		console.log("Pocmodel is invoked", + $scope.pocModel);
		
		$scope.newModelMode = false;
		$scope.pocModel = $scope.model.customerPocModelList[index];
		console.log("customerPocModelList is invoked", + $scope.model.customerPocModelList[index]);
		console.log("Pocmodel is invoked", + $scope.pocModel);
		utils.openModal('customerPocModelDialog');		
	};
		
	$scope.savePoc = function(e){
		console.log("Save poc is invoked..");
		
		$scope.initErrors("pocModel", false);

		var model = $scope.model;
		
		if(!validator.validateModel($scope.pocModel, $scope.pocModelDef, $scope.errors.pocModel))
		{
			utils.alert("Please correct the errors and then try!", function(){
				$('body').addClass('modal-open');
			});
			
			return;
		}
		
		$('#customerPocModelDialog').modal('hide');
		
		if($scope.newModelMode == false)
		{
			return;
		}
		
		if(!model.customerPocModelList)
		{
			model.customerPocModelList = [];
		}
		
		model.customerPocModelList.push({
			name : $scope.pocModel.name,
		    email  : $scope.pocModel.email,
		    phoneNumber :$scope.pocModel.phoneNumber,
		    address :$scope.pocModel.address
		});
		
	};
	
	$scope.removePoc = function(index){
		console.log("removePoc is invoked");

		var pocModel = $scope.model.customerPocModelList[index]; 
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				this.logger.trace("Delete operation is cancelled by user.");
				return;
			}
			else
			{
				console.log("count = " + count);
				count = count - 1;
			}

			this.$scope.model.customerPocModelList.splice(this.index, 1);
			
			try
			{
				this.$scope.$parent.$digest();
			}catch(ex)
			{}
			
		}, {"$scope": $scope, "index": index});
		
		utils.confirm(["Are you sure you want to delete POC with name '{}' ?", pocModel.name], deleteOp);
		
		
	};
		
}]);