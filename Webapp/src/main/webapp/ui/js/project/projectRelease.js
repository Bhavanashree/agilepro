$.application.controller('projectReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.rlseIdObjMap = {};
	
	$scope.prjctIdObjMap = {};
	
	$scope.multipleSelectedProjects = [];
	
	
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
			
			var index;
			
			for(index in $scope.projectsForRelease)
			{
				$scope.prjctIdObjMap[$scope.projectsForRelease[index].id] = $scope.projectsForRelease[index];
			}
			
		});
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};
	
	$scope.onReleaseChange  = function(releaseId){
		
		console.log("releaseId = " + releaseId);
		
		$scope.selectedRelease = $scope.rlseIdObjMap[releaseId].name;
		
		$scope.releaseToDisplay = $scope.selectedRelease;
	};
	
	
	// Dragging methods
	$scope.dragProjects = function(event){
	
		if($scope.multipleSelectedProjects.length == 0)
			{
				$scope.selectedPrjctId = event.target.id;
			}
		
	};
	
	
	$scope.dropReleases = function(event){
		
		if($scope.multipleSelectedProjects.length == 0)
		{
			$scope.selectedPrjctId = event.target.id;
		}
	};
	
	// save new release
	saveNewRelease = function(obj){
		
		// actionHelper
	};
	
	
}]);

