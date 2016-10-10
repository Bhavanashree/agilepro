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
						$scope.selectedRelease = $scope.releases[0];
						
						$('#releaseDialogId').modal('hide');
					}
					
				}, {"hideInProgress" : true});
	};
	
	readAllReleaseCallBack = function(readResponse, respConfig){
		
		$scope.rlseIdObjMap = {};
		
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

