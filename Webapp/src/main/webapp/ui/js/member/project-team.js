$.application.controller('projectTeamController', 
						["$scope", "crudController", "actionHelper", "utils","modelDefService", "validator", 
                        function($scope, crudController, actionHelper, utils, modelDefService, validator){
	
	$scope.projectTeams = [];
	$scope.teamIdObjMap = {};	
	
	
	$scope.fetchAllProjectTeam = function(){
	
		actionHelper.invokeAction("projectTeam.readByProjectId", null, {"projectId" : $scope.getActiveProjectId()}, function(readResponse, respConfig){
			
			$scope.projectTeams = readResponse.model;
			$scope.selectedTeam = $scope.projectTeams[0]; 
			
			for(index in $scope.projectTeams)
			{
				$scope.teamIdObjMap[$scope.projectTeams[index].id] = $scope.projectTeams[index];
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
		}, true);
		
		
	};
	
	$scope.onTeamChange = function(teamId){
		
		$scope.selectedTeam = $scope.teamIdObjMap[teamId];
	};
	
	
	$scope.addNewTeam = function(){
		
		$scope.projectTeam = {};
		//$scope.initErrors("projectTeam", true);
		 
		console.log("add new team");
	};
	
	
	$scope.saveTeam = function($event){
		
		$scope.projectTeam.projectId = $scope.getActiveProjectId();
		
		$scope.projectTeams.push($scope.projectTeam);
		
		$scope.selectedTeam = $scope.projectTeams[0]; 
		
		actionHelper.invokeAction("projectTeam.save", $scope.projectTeam, null, function(readResponse, respConfig){
			
			if(readResponse.code != 0)
			{
				// fetch all
			}
			
		}, true);
	};
	
	$scope.deleteTeam = function(teamId){
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("projectTeam.delete", null, {"id" : $scope.teamIdObjMap[teamId].id}, deleteAttachmentCallBack);
			}
			
			try
			{
				this.$scope.$parent.$digest();
			}catch(ex)
			{}
			
		}, {"$scope": $scope});

		utils.confirm(["Are you sure you want to delete {} and there respective members ?", $scope.teamIdObjMap[teamId].name], deleteOp);

	};
	
	
}]);