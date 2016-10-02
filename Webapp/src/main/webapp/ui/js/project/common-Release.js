$.application.controller('projectReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.projectFilter = function(){
		
		return function( item ) {
			
			if($scope.searchUnreleaseProject)
			{
				return item.name.toLowerCase().includes($scope.searchUnreleaseProject.toLowerCase());
			}
			
		    return true;
		  };
	};
	
	
	$scope.checkAllUnreleasedProject = function(){
		
		var index;
		var prjctObj;
		
					
			for(index in $scope.projectsForRelease)
			{
				prjctObj = $scope.projectsForRelease[index];
				
				if($scope.searchUnreleaseProject)
				{
					if(prjctObj.name.toLowerCase().includes($scope.searchUnreleaseProject.toLowerCase()))
					{
						prjctObj.check = true;
						$scope.multipleUnreleasedSelectedProjectsId.push(prjctObj.id);
					}
				}else
				{
					prjctObj.check = true;
					$scope.multipleUnreleasedSelectedProjectsId.push(prjctObj.id);
				}
			}
		
		/*for(index in $scope.projectsForRelease)
		{
			prjctObj = $scope.projectsForRelease[index];
			
			prjctObj.check = true;
			$scope.multipleUnreleasedSelectedProjectsId.push(prjctObj.id);
		}*/
	};
	
	$scope.unCheckAllUnreleasedProject = function(){
		
		var index;
		for(index in $scope.projectsForRelease)
		{
			$scope.projectsForRelease[index].check = false;
		}
		
		$scope.multipleUnreleasedSelectedProjectsId = [];
	};
	
	/*
	 * Check box method to select multiple projects
	 */
	$scope.checkBoxProject = function(projectId){
		
		$scope.unreleasedPrjctIdObjMap[projectId].check = !$scope.unreleasedPrjctIdObjMap[projectId].check;
		
		
		if($scope.unreleasedPrjctIdObjMap[projectId].check)
		{
			$scope.multipleUnreleasedSelectedProjectsId.push(projectId);
			console.log(projectId + " added to list");
		}
		else
		{
			$scope.multipleUnreleasedSelectedProjectsId.splice($scope.multipleUnreleasedSelectedProjectsId.indexOf(projectId), 1);
			console.log(projectId + " removed from list");
		}
		
	};
	
	readAllReleaseCallBack = function(readResponse, respConfig){
		
		$scope.rlseIdObjMap = {};
		
		$scope.releases = readResponse.model;
		
		var index;
		
		for(index in $scope.releases)
		{
			$scope.rlseIdObjMap[$scope.releases[index].id] = $scope.releases[index];
		}
		
		$scope.onReleaseChange($scope.releases[0].id);
	};
	
	// init method
	$scope.fetchAllRelease = function(){
		
		actionHelper.invokeAction("release.readAll", null, null, readAllReleaseCallBack, true);
		
	};
	

	readAlPrjctAndReleaseCallBack =  function(readResponse, respConfig){
		
		$scope.projectReleased = readResponse.basicProjectInfos;
		$scope.projectsForRelease = readResponse.projectForRelease;
		
		var obj;
		var index
		
		for(index in $scope.projectsForRelease)
		{
			obj = $scope.projectsForRelease[index];
			
			obj.check = false;
			
			$scope.unreleasedPrjctIdObjMap[obj.id] = obj;
		}
		
		console.log("broadcast release");
		$scope.$broadcast("activeReleaseSelectionChanged");
	};
	
	// On change
	$scope.onReleaseChange  = function(releaseId){
		
		$scope.unreleasedPrjctIdObjMap = {};
		
		$scope.multipleUnreleasedSelectedProjectsId = [];
		
		/////////////////////////////////////////
		
		console.log("releaseId = " + releaseId);
		
		$scope.selectedRelease = $scope.rlseIdObjMap[releaseId];
		
		console.log($scope.rlseIdObjMap[releaseId]);
		
		$scope.slectedReleaseId = $scope.rlseIdObjMap[releaseId].id;
		
		actionHelper.invokeAction("projectRelease.readAllProjectAndProjectRelease", null, 
				{"releaseId" : $scope.slectedReleaseId}, readAlPrjctAndReleaseCallBack, true);
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
		
		if($scope.multipleUnreleasedSelectedProjectsId.length == 0)
		{
			$scope.selectedPrjctId = event.target.id;
		}
	};
	
	
	$scope.dropProjects = function(event){
		
		console.log("project drop");
		
		event.preventDefault();
		
		if($scope.multipleUnreleasedSelectedProjectsId.length == 0)
		{
			var model = {"releaseId" : $scope.slectedReleaseId, "projectId" : $scope.selectedPrjctId};
			
			saveNewProjectRelease(model);
		}
		else
		{
			var model = {"releaseId" : $scope.slectedReleaseId, "projectIds" : $scope.multipleUnreleasedSelectedProjectsId};
			
			saveNewProjectRelease(model);
		}
		
	};
	
	// save new project release
	saveNewProjectRelease = function(model){
		
		actionHelper.invokeAction("projectRelease.save", model, null, function(readResponse, respConfig){
								if(readResponse.code == 0)
								{
									$scope.onReleaseChange($scope.selectedRelease.id);
								}
								
							}, true);
	};
	
	$scope.getActiveReleaseId = function(){
		
		return $scope.selectedRelease.id;
	};

	
}]);


