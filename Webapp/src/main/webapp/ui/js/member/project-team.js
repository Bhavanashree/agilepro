$.application.controller('projectTeamController', 
						["$scope", "crudController", "actionHelper", "utils","modelDefService", "validator", 
                        function($scope, crudController, actionHelper, utils, modelDefService, validator){
	
	$scope.projectTeams = [];
		
	$scope.addNewTeam = function(){
		
		$scope.projectTeam = {};
		//$scope.initErrors("projectTeam", true);
		 
		console.log("add new team");
	};
	
	
	$scope.saveTeam = function($event){
		
		$scope.projectTeams.push($scope.projectTeam);
		
		$scope.selectedTeam = $scope.projectTeams[0]; 
		
		actionHelper.invokeAction("projectTeam.save", $scope.projectTeam, null, function(readResponse, respConfig){
			
			if(readResponse.code != 0)
			{
				$scope.selectedTeam = {};
				// fetch all
			}
			
		}, true);
	};
	
}]);