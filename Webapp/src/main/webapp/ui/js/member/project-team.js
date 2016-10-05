$.application.controller('projectTeamController', 
						["$scope", "crudController", "actionHelper", "utils","modelDefService", "validator", 
                        function($scope, crudController, actionHelper, utils, modelDefService, validator){
	
			crudController.extend($scope, {
				"name": "ProjectTeam",
				
				"nameColumn" : "name",
				
				"modelDailogId": "projectTeamModal",
				
				
				"saveAction": "projectTeam.save",
				"readAction": "projectTeam.read",
				"updateAction": "projectTeam.update",
				"deleteAction": "projectTeam.delete",
			});
		
			
	readAllProjectTeamCallBack = function(readResponse, respConfig){

		$scope.teamIdObjMap = {};
		
		$scope.projectTeams = readResponse.model;
		$scope.selectedTeam = $scope.projectTeams[0]; 
		
		for(index in $scope.projectTeams)
		{
			$scope.teamIdObjMap[$scope.projectTeams[index].id] = $scope.projectTeams[index];
		}
		
		
		console.log("team is inserted " + $scope.selectedTeam.name);
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
	};
			
	$scope.fetchAllProjectTeam = function(){
	
		actionHelper.invokeAction("projectTeam.readByProjectId", null, {"projectId" : $scope.getActiveProjectId()}, 
				readAllProjectTeamCallBack, true);
		
	};
	
	$scope.onTeamChange = function(teamId){
		
		$scope.selectedTeam = $scope.teamIdObjMap[teamId];
	};
	
	
	$scope.addNewTeam = function(){
		
		$scope.projectTeam = {};
		$scope.initErrors("projectTeam", true);
		
		console.log("add new team");
	};
	
	
	$scope.saveTeam = function($event){
		
		$scope.projectTeam.projectId = $scope.getActiveProjectId();
		
		actionHelper.invokeAction("projectTeam.save", $scope.projectTeam, null, function(readResponse, respConfig){
			
			if(readResponse.code == 0)
			{
				$scope.projectTeam.id = readResponse.id; 
				
				$scope.projectTeams.push($scope.projectTeam);
				
				$scope.teamIdObjMap[$scope.projectTeam.id] = $scope.projectTeam;
				
				$scope.selectedTeam = $scope.projectTeams[0];
				
				$('#projectTeamModal').modal('hide');
			}else
			{
				// fetch all
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
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