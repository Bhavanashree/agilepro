$.application.controller('projectReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.rlseIdObjMap = {};
	
	$scope.prjctIdObjMap = {};
	
	$scope.multipleSelectedProjects = [];
	
	$scope.projectReleased = [];
	
	$scope.projectsForRelease = [];
	
	$scope.selectedRelease = "--Select Release--";
	
	$scope.fetchAllRelease = function(){
		
		actionHelper.invokeAction("release.readAll", null, null, function(readResponse, respConfig){
			
			$scope.releases = readResponse.model;
			
			var index;
			
			for(index in $scope.releases)
			{
				$scope.rlseIdObjMap[$scope.releases[index].id] = $scope.releases[index];
			}
		});
		
		/*actionHelper.invokeAction("project.readAll", null, null, function(readResponse, respConfig){
			
			$scope.projectsForRelease = readResponse.model;
			
			var index;
			
			for(index in $scope.projectsForRelease)
			{
				$scope.prjctIdObjMap[$scope.projectsForRelease[index].id] = $scope.projectsForRelease[index];
			}
			
		});*/
		
	};
	

	// On change
	$scope.onReleaseChange  = function(releaseId){
		
		console.log("releaseId = " + releaseId);
		
		$scope.selectedRelease = $scope.rlseIdObjMap[releaseId].name;
		
		$scope.slectedReleaseId = $scope.rlseIdObjMap[releaseId].id;
		
		$scope.projectReleaseToDisplay = "Project release with " + $scope.selectedRelease;
		
		actionHelper.invokeAction("projectRelease.readAll", null, {"releaseId" : $scope.slectedReleaseId},
									function(readResponse, respConfig){
									$scope.projectReleased = readResponse.model;
									}
								, true);
		
		actionHelper.invokeAction("project.readAll", null, null, function(readResponse, respConfig){
			
			var index;
			var tempProjectArr = readResponse.model;

			for(index in tempProjectArr)
			{
				if($scope.projectReleased.indexOf(tempProjectArr[index]) == -1)
				{
					$scope.projectsForRelease.push(tempProjectArr[index]);
					
					console.log("pushed to for release");
				}
			}
			
			//$scope.projectsForRelease = readResponse.model;
			
			for(index in $scope.projectsForRelease)
			{
				$scope.prjctIdObjMap[$scope.projectsForRelease[index].id] = $scope.projectsForRelease[index];
			}
			
		});
		
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

