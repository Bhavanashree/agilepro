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
			$scope.releasedPrjctIdObjMap[obj.id] = obj;
		}
		
		// init the drop down for story
		$scope.$broadcast("initProjectReleasedForStory");
		
	};
	
	// Listener for broadcast
	$scope.$on("activeReleaseSelectionChanged", function(event, args) {
		
		if($scope.slectedReleaseId == $scope.getActiveReleaseId())
		{
			return;
		}
		
		console.log("listener is invoked");
		
		$scope.unreleasedPrjctIdObjMap = {};
		$scope.multipleUnreleasedSelectedProjectsId = [];
		
		// drag back
		$scope.releasedPrjctIdObjMap = {};
		$scope.multipleReleasedSelectedProjectsId = [];
		
		
		$scope.slectedReleaseId = $scope.getActiveReleaseId();
		
		actionHelper.invokeAction("projectRelease.readAllProjectAndProjectReleaseByReleaseId", null, 
				{"releaseId" : $scope.slectedReleaseId}, readAlPrjctAndReleaseCallBack, {"hideInProgress" : true});
	});
	
	// Listener for broadcast
	$scope.$on("initProjectReleaseToNull", function(event, args) {
	
		$scope.projectReleased = null;
		$scope.projectsForRelease = null;
	});
	
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
	
	// for release
	$scope.dropProjects = function(event){
		event.preventDefault();
		
		console.log("project drop " + $scope.slectedReleaseId);
		var projectObj;
		var projectId;
		var index;
		
		if($scope.multipleUnreleasedSelectedProjectsId.length == 0)
		{
			projectObj = $scope.unreleasedPrjctIdObjMap[$scope.selectedPrjctId];
			
			$scope.projectsForRelease.splice($scope.projectsForRelease.indexOf(projectObj),1);
			
			$scope.projectReleased.push(projectObj);
			$scope.releasedPrjctIdObjMap[projectObj.id] = projectObj;
			
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
				
				$scope.releasedPrjctIdObjMap[projectObj.id] = projectObj;
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
		}else
		{
			$scope.$broadcast("initProjectReleasedForStory");
		}
	};
	
	// save new project release
	saveNewProjectRelease = function(model){
		
		actionHelper.invokeAction("projectRelease.save", model, null, saveProjectReleaseCallBack, {"hideInProgress" : true});
	};
	
	// Reverse Drag
	$scope.dragBackProjects = function(event){
		
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
			$scope.onReleaseChange($scope.selectedRelease.id);
		}else
		{
			$scope.$broadcast("initProjectReleasedStoryAfterDropBack");
		}
	};
	
	
	// for unrelease
	$scope.dropBackProjects = function(event){
		event.preventDefault();
		
		console.log("drop back project");
		var projectObj;
		var projectId;
		var index;
		
		var indexReleased;
		var indexToRemove;
		var releasedObj;
		
		if($scope.multipleReleasedSelectedProjectsId.length == 0)
		{
			projectObj = $scope.releasedPrjctIdObjMap[$scope.selectedPrjctId];
			
			//$scope.$broadcast("initProjectReleasedStoryAfterDropBack");
			$scope.projectReleased.splice($scope.projectReleased.indexOf(projectObj), 1);
			
			projectObj.check = false;
			$scope.projectsForRelease.push(projectObj);
			$scope.unreleasedPrjctIdObjMap[projectObj.id] = projectObj;
			
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


