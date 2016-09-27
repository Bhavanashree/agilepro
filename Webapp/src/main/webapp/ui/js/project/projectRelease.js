$.application.controller('projectReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.rlseIdObjMap = {};
	
	$scope.selectedRelease = "--Select Release--";
	
	$scope.fetchAllReleasePrjctStrs = function(){
		
		actionHelper.invokeAction("release.readAll", null, null, function(readResponse, respConfig){
			$scope.releases = readResponse.model;
			
			var index;
			
			for(index in $scope.releases)
			{
				$scope.rlseIdObjMap[$scope.releases[index].id] = $scope.releases[index];
			}
		})
		
		actionHelper.invokeAction("project.readAll", null, null, function(readResponse, respConfig){
			$scope.projectsForRelease = readResponse.model;
		});
	};
	
	$scope.onReleaseChange  = function(releaseId){
		
		console.log("releaseId = " + releaseId);
		
		$scope.selectedRelease = $scope.rlseIdObjMap[releaseId].name;
		
		$scope.releaseToDisplay = $scope.selectedRelease;
	};
	
}]);

