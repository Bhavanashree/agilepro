$.application.controller('projectReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.notAllowedInReleased = false;
	$scope.notAllowedInDropBack = false;
	
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
	

	readAlPrjctAndReleaseCallBack =  function(readResponse, respConfig){
		
		$scope.projectReleased = readResponse.basicProjectInfos;
		$scope.projectsForRelease = readResponse.projectForRelease;
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
		var obj;
		var index
		var projectName;
		
		for(index in $scope.projectsForRelease)
		{
			obj = $scope.projectsForRelease[index];
			
			obj.check = false;
			
			$scope.unreleasedPrjctIdObjMap[obj.id] = obj;
		}
		
		for(index in $scope.projectReleased)
		{
			obj = $scope.projectReleased[index];
			$scope.releasedPrjctIdNameMap[obj.id] = obj.name;
		}
		
	};
	
	// Listener for broadcast
	$scope.$on("activeReleaseSelectionChanged", function(event, args) {
		
		console.log("listener is invoked");
		
		$scope.unreleasedPrjctIdObjMap = {};
		$scope.multipleUnreleasedSelectedProjectsId = [];
		
		// drag back
		$scope.releasedPrjctIdNameMap = {};
		$scope.multipleReleasedSelectedProjectsId = [];
		
		
		$scope.slectedReleaseId = $scope.getActiveReleaseId();
		
		actionHelper.invokeAction("projectRelease.readAllProjectAndProjectReleaseByReleaseId", null, 
				{"releaseId" : $scope.slectedReleaseId}, readAlPrjctAndReleaseCallBack, {"hideInProgress" : true});
	});
	
	
	// Dragging methods
	$scope.dragProjects = function(event){
	
		$scope.notAllowedInDropBack = true;
		
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
	
	// for release
	$scope.dropProjects = function(event){
		event.preventDefault();
		
		if($scope.notAllowedInReleased)
		{
			$scope.notAllowedInReleased = false;
			return;
		}
		
		// validation for not dropping in same area
		$scope.notAllowedInReleased = false;
		$scope.notAllowedInDropBack = false;
		
		console.log("project drop " + $scope.slectedReleaseId);
		var projectObj;
		var projectId;
		var index;
		
		if($scope.multipleUnreleasedSelectedProjectsId.length == 0)
		{
			projectObj = $scope.unreleasedPrjctIdObjMap[$scope.selectedPrjctId];
			
			$scope.projectsForRelease.splice($scope.projectsForRelease.indexOf(projectObj),1);
			
			$scope.projectReleased.push(projectObj);
			$scope.releasedPrjctIdNameMap[projectObj.id] = projectObj.name;
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			var model = {"releaseId" : $scope.slectedReleaseId, "projectId" : $scope.selectedPrjctId};
			
			saveNewProjectRelease(model);
		}
		else
		{
			for(index in $scope.multipleUnreleasedSelectedProjectsId)
			{
				projectId = $scope.multipleUnreleasedSelectedProjectsId[index];
				
				projectObj = $scope.unreleasedPrjctIdObjMap[projectId];
				
				$scope.projectsForRelease.splice($scope.projectsForRelease.indexOf(projectObj),1);
				
				$scope.projectReleased.push(projectObj);
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			var model = {"releaseId" : $scope.slectedReleaseId, "projectIds" : $scope.multipleUnreleasedSelectedProjectsId};
			
			saveNewProjectRelease(model);
		}
		
	};
	
	saveProjectReleaseCallBack = function(readResponse, respConfig){
		
		if(readResponse.code != 0)
		{
			$scope.onReleaseChange($scope.selectedRelease.id);
		}
	};
	
	// save new project release
	saveNewProjectRelease = function(model){
		
		actionHelper.invokeAction("projectRelease.save", model, null, saveProjectReleaseCallBack, {"hideInProgress" : true});
	};
	
	// Reverse Drag
	$scope.dragBackProjects = function(event){
		
		$scope.notAllowedInReleased = true;
		
		console.log("drag back project is called");
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		if(!$scope.slectedReleaseId)
		{
			utils.alert("Please select release");
			return;
		}
		
		if($scope.multipleReleasedSelectedProjectsId.length == 0)
		{
			$scope.selectedPrjctId = event.target.id;
		}
	};
	
	
	deleteCallBack = function(deleteResponse, respConfig){
		
		if(deleteResponse.code != 0)
		{
			//$scope.onReleaseChange($scope.selectedRelease.id);
		}
	};
	
	
	// for unrelease
	$scope.dropBackProjects = function(event){
		event.preventDefault();
		
		if($scope.notAllowedInDropBack)
		{
			$scope.notAllowedInDropBack = false;
			return;
		}
		
		// validation for not dropping in same area
		$scope.notAllowedInReleased = false;
		$scope.notAllowedInDropBack = false;
		
		console.log("drop back project");
		var projectObj;
		var projectId;
		var index;
		
		var projectName;
		var indexReleased;
		var indexToRemove;
		
		if($scope.multipleReleasedSelectedProjectsId.length == 0)
		{
			console.log($scope.releasedPrjctIdNameMap);
			
			projectName = $scope.releasedPrjctIdNameMap[$scope.selectedPrjctId];
			
			for(indexReleased in $scope.projectReleased)
			{
				if($scope.projectReleased[indexReleased].name === projectName)
				{
					indexToRemove = indexReleased;
					break;
				}
			}
			
			$scope.projectsForRelease.push($scope.projectReleased[indexToRemove]);
			
			$scope.projectReleased.splice(indexToRemove, 1);
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			var model = {"releaseId" : $scope.slectedReleaseId, "projectIds" : [$scope.selectedPrjctId]};
			
			deleteProjectRelease(model);
		}
		else
		{
			for(index in $scope.multipleUnreleasedSelectedProjectsId)
			{
				projectId = $scope.multipleUnreleasedSelectedProjectsId[index];
				
				projectObj = $scope.unreleasedPrjctIdObjMap[projectId];
				
				$scope.projectsForRelease.splice($scope.projectsForRelease.indexOf(projectObj),1);
				
				$scope.projectReleased.push(projectObj);
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			var model = {"releaseId" : $scope.slectedReleaseId, "projectIds" : $scope.multipleUnreleasedSelectedProjectsId};
			
			saveNewProjectRelease(model);
		}
		
	};
	
	deleteProjectRelease = function(model){
		
		actionHelper.invokeAction("projectRelease.deleteByProjectId", model, null, deleteCallBack, {"hideInProgress" : true});
	};
	
}]);


