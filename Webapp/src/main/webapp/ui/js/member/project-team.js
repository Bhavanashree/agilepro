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
		
		
	$scope.projectTeams = [];
	$scope.teamIdObjMap = {};
	
	readAllProjectTeamCallBack = function(readResponse, respConfig){

		if(readResponse.model.length != 0)
		{
			$scope.projectTeams = readResponse.model;
			$scope.selectedTeam = $scope.projectTeams[0];
			
			// set in parent 
			$scope.setActiveTeamId($scope.selectedTeam.id); 
			
			
			$scope.fetchMembers($scope.selectedTeam.id);
			
			for(index in $scope.projectTeams)
			{
				$scope.teamIdObjMap[$scope.projectTeams[index].id] = $scope.projectTeams[index];
			}
		}else
		{
			$scope.projectTeams = [];
			$scope.selectedTeam = {};
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
	};
	
	// Listener for broadcast
	$scope.$on("adminAndMembersAreFetched", function(event, args) {

		console.log("listener");
		
		actionHelper.invokeAction("projectTeam.readByProjectId", null, {"projectId" : $scope.getActiveProjectId()}, 
				readAllProjectTeamCallBack, {"hideInProgress" : true});
	   
	});

	$scope.onTeamChange = function(teamId){
		
		$scope.selectedTeam = $scope.teamIdObjMap[teamId];
		
		// set in parent 
		$scope.setActiveTeamId(teamId); 
		
		$scope.fetchMembers(teamId);
	};
	
	
	$scope.addNewTeam = function(){
		
		$scope.newModelMode = true;
		
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
				
				if(!$scope.selectedTeam.name)
				{
					$scope.onTeamChange($scope.projectTeams[0].id);
				}
				
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
			
		}, {"hideInProgress" : true});
	};
	
	
	
	$scope.readTeam = function(){
		
		actionHelper.invokeAction("projectTeam.read", null, {"id" : $scope.selectedTeam.id}, function(readResponse, respConfig){
			
			if(readResponse.code == 0)
			{
				$scope.newModelMode = false;
				
				$scope.initErrors("projectTeam", false);
				
				$scope.projectTeam = readResponse.model;
				
				utils.openModal("projectTeamModal");
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
		}, {"hideInProgress" : true});
	};
	
	$scope.updateTeam = function(){
		
		actionHelper.invokeAction("projectTeam.update", $scope.projectTeam, null, function(readResponse, respConfig){
			
			if(readResponse.code == 0)
			{
				$('#projectTeamModal').modal('hide');
			}
			
		}, {"hideInProgress" : true});
		
	};
	
	$scope.deleteTeam = function(teamId){
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				console.log($scope.selectedTeam.id + " for delete");
				
				actionHelper.invokeAction("projectTeam.delete", null, {"id" : $scope.selectedTeam.id}, function(readResponse, respConfig){
					
					if(readResponse.code == 0)
					{
						$scope.projectTeams.splice($scope.projectTeams.indexOf($scope.selectedTeam), 1);
						
						console.log($scope.projectTeams);
						
						if($scope.projectTeams.length > 0)
						{
							$scope.onTeamChange($scope.projectTeams[0].id)
						}else
						{
							$scope.fetchMembers($scope.selectedTeam.id)
							
							$scope.selectedTeam = {};
							
							// set in parent 
							$scope.setActiveTeamId(null);
						}
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}

					}
				}, {"hideInProgress" : true});
			}
			
			try
			{
				this.$scope.$parent.$digest();
			}catch(ex)
			{}
			
		}, {"$scope": $scope});

		utils.confirm(["Are you sure you want to delete {} along with there respective members ?", $scope.selectedTeam.name], deleteOp);

	};
	
}]);