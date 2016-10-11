$.application.controller('releaseController', ["$scope", "crudController", "utils", "modelDefService", "validator", 
                                               "actionHelper",
                        function($scope, crudController, utils, modelDefService, validator, actionHelper) {
	
	crudController.extend($scope, {
		"name": "Release",
		
		"nameColumn" : "name",
		
		"modelDailogId": "releaseDialogId",
		
		"saveAction": "release.save",
		"readAction": "release.read",
		"updateAction": "release.update",
		"deleteAction": "release.delete",
	});
	
	
	$scope.releases = [];
	$scope.rlseIdObjMap = {};
	
	$scope.initModelDef = function() {
		modelDefService.getModelDef("Release", $.proxy(function(modelDefResp){
			this.$scope.modelDef = modelDefResp.modelDef;
			
			console.log("Release Model");
		}, {"$scope": $scope}));
	};
	
	$scope.getModelDef = function(prefix) {
		
		return $scope.modelDef;
	};
	
	/*
	 * Add new Release
	 */
	$scope.addNewRelease = function(){
		
		$scope.newModelMode = true;
		
		$scope.releaseModel = {};
		$scope.initErrors("releaseModel", true);
		
		utils.openModal("releaseDialogId");
	};
	
		
	$scope.saveRelease = function(){
		
		$scope.initErrors("releaseModel", false);
		
		if(!validator.validateModel($scope.releaseModel, $scope.modelDef, $scope.errors.releaseModel))
		{
			utils.alert("Please correct the errors and then try!", function(){
				$('body').addClass('modal-open');
			});
			
			return;
		}
		
		actionHelper.invokeAction("release.save", $scope.releaseModel, null, 
				function(saveResponse, respConfig){
			
					if(saveResponse.code == 0)
					{
						$scope.releaseModel.id = saveResponse.id;
						$scope.releases.push($scope.releaseModel);
						$scope.rlseIdObjMap[$scope.releaseModel.id] = $scope.releaseModel;
						
						if(!$scope.selectedRelease)
						{
							$scope.onReleaseChange($scope.releases[0].id);
						}
						
						$('#releaseDialogId').modal('hide');
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
						
					}
					
				}, {"hideInProgress" : true});
		
	};
	
	/*
	 * Read release
	 */
	$scope.readRelease = function(){
		
		actionHelper.invokeAction("release.read", null, {"id" : $scope.selectedRelease.id}, function(readResponse, respConfig){
			
			if(readResponse.code == 0)
			{
				$scope.newModelMode = false;
				
				$scope.initErrors("releaseModel", false);
				
				$scope.releaseModel = readResponse.model;
				
				utils.openModal("releaseDialogId");
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
		}, {"hideInProgress" : true});

		
	};
	
	
	$scope.updateRelease = function(){
		
		actionHelper.invokeAction("release.update", $scope.releaseModel, null, function(readResponse, respConfig){
			
			if(readResponse.code == 0)
			{
				$scope.releases[$scope.releases.indexOf($scope.selectedRelease)] = $scope.releaseModel;
				
				$scope.rlseIdObjMap[$scope.releaseModel.id] = $scope.releaseModel;
				
				$scope.selectedRelease = $scope.releaseModel;
				
				$('#releaseDialogId').modal('hide');
				
				try
				{
					$scope.$apply();
				}catch(ex)
				{}
			}
			
		}, {"hideInProgress" : true});

	};
	
	/*
	 * Delete release
	 */
	$scope.deleteRelease = function(){
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("release.delete", null, {"id" : $scope.selectedRelease.id}, function(deleteResponse, respConfig){
				
					if(deleteResponse.code == 0)
					{
						$scope.releases.splice($scope.releases.indexOf($scope.selectedRelease), 1);
						
						// how to remove from map
						
						if($scope.releases.length > 0)
						{
							$scope.onReleaseChange($scope.releases[0].id);
							
						}else
						{
							$scope.selectedRelease = null;
							
							$scope.$broadcast("initProjectReleaseToNull");
						}
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}

				}, {"hideInProgress" : true});

			}
		
			try
			{
				this.$scope.$parent.$digest();
			}catch(ex)
			{}
			
		}, {"$scope": $scope});
		
		utils.confirm(["Are you sure you want to delete {} along with there respective projects ?", $scope.selectedRelease.name], deleteOp);
	};
	
	readAllReleaseCallBack = function(readResponse, respConfig){
		
		$scope.releases = readResponse.model;
		
		if($scope.releases.length > 0)
		{
			$scope.selectedRelease = $scope.releases[0];
			
			console.log("Release is selected");
			
			$scope.$broadcast("activeReleaseSelectionChanged");
			
			console.log("broadcast");
		}
		
		var index;
		
		for(index in $scope.releases)
		{
			$scope.rlseIdObjMap[$scope.releases[index].id] = $scope.releases[index];
		}
		
	};
	
	// init method
	$scope.fetchAllRelease = function(){
		
		actionHelper.invokeAction("release.readAll", null, null, readAllReleaseCallBack, {"hideInProgress" : true});
		
	};
	
	
	$scope.onReleaseChange  = function(releaseId){
		
		$scope.selectedRelease = $scope.rlseIdObjMap[releaseId];
		
		$scope.$broadcast("activeReleaseSelectionChanged");
	};
	
	$scope.getActiveReleaseId = function(){
		
		return $scope.selectedRelease.id;
	};
	
}]);

