$.application.controller('projectReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.rlseIdObjMap = {};
	
	$scope.prjctIdObjMap = {};
	
	$scope.multipleSelectedProjects = [];
	
	$scope.projectReleased = [];
	
	$scope.projectsForRelease = [];
	
	// init method
	$scope.fetchAllRelease = function(){
		
		actionHelper.invokeAction("release.readAll", null, null, function(readResponse, respConfig){
			
			$scope.releases = readResponse.model;
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			var index;
			
			for(index in $scope.releases)
			{
				$scope.rlseIdObjMap[$scope.releases[index].id] = $scope.releases[index];
			}
			
		});
		
	};
	

	readAlPrjctAndReleaseCallBack =  function(readResponse, respConfig){
		
		$scope.projectReleased = readResponse.basicProjectInfos;
		$scope.projectsForRelease = readResponse.projectForRelease;
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	}
	
	// On change
	$scope.onReleaseChange  = function(releaseId){
		
		console.log("releaseId = " + releaseId);
		
		$scope.selectedRelease = $scope.rlseIdObjMap[releaseId];
		
		console.log($scope.rlseIdObjMap[releaseId]);
		
		$scope.slectedReleaseId = $scope.rlseIdObjMap[releaseId].id;
		
		$scope.projectReleaseToDisplay = "Project release with " + $scope.selectedRelease.name;
		
		actionHelper.invokeAction("projectRelease.readAllProjectAndProjectRelease", null, {"releaseId" : $scope.slectedReleaseId}, 
				readAlPrjctAndReleaseCallBack, true);
	};
	
	
	// Dragging methods
	$scope.dragProjects = function(event){
	
		console.log("drag project is called");
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		if(!$scope.slectedReleaseId)
		{
			utils.alert("Please select release");
			return;
		}
		
		if($scope.multipleSelectedProjects.length == 0)
		{
			$scope.selectedPrjctId = event.target.id;
		}
	};
	
	
	$scope.dropReleases = function(event){
		
		event.preventDefault();
		
		if($scope.multipleSelectedProjects.length == 0)
		{
			var model = {"releaseId" : $scope.slectedReleaseId, "projectId" : $scope.selectedPrjctId};
			
			saveNewProjectRelease(model);
		}
	};
	
	// call back method after save new already releasde
	saveAlreadyReleasecallBack = function(readResponse, respConfig){
		
		if(readResponse.code == 0)
		{
			var index = $scope.projectsForRelease.indexOf($scope.prjctIdObjMap[$scope.selectedPrjctId]);
			
			$scope.projectReleased.push($scope.prjctIdObjMap[$scope.selectedPrjctId]);
			
			$scope.projectsForRelease.splice(index, 1);
		}
	};
	
	// save new release
	saveNewProjectRelease = function(model){
		
		actionHelper.invokeAction("projectRelease.save", model, null, saveAlreadyReleasecallBack, true);
	};
	
}]);

