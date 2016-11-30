$.application.controller('projectReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {
	
	$scope.allowedInProjectRelease = false;
	$scope.allowedInProjectBack = false;
	
	$scope.allowedInStoryRelease = false;
	$scope.allowedInStoryBack = false;
	
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
		
		if($scope.projectReleased.length > 0)
		{
			$scope.$broadcast("initProjectReleasedForStory");
		}
		
	};
	
	// Listener for broadcast
	$scope.$on("activeReleaseSelectionChanged", function(event, args) {
		
		console.log("listener activeReleaseSelectionChanged is invoked");
		
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
		
		$scope.allowedInProjectRelease = true;
	};
	
	// for release
	$scope.dropProjects = function(event){
		event.preventDefault();
		
		
		if(!$scope.allowedInProjectRelease)
		{
			utils.alert("Drop in this area is not allowed");
			return;
		}
		$scope.allowedInProjectRelease = false;
		
		console.log("project drop " + $scope.slectedReleaseId);
		var projectObj;
		var projectId;
		var index;
		
		if($scope.multipleUnreleasedSelectedProjectsId.length == 0)
		{
			projectObj = $scope.unreleasedPrjctIdObjMap[$scope.selectedPrjctId];
			
			$scope.projectsForRelease.splice($scope.projectsForRelease.indexOf(projectObj),1);
			
			projectObj.check = false;
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
				
				projectObj.check = false;
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
			
			$scope.multipleUnreleasedSelectedProjectsId = [];
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
		
		$scope.allowedInProjectBack = true;
	};
	
	
	deleteCallBack = function(deleteResponse, respConfig){
		
		if(deleteResponse.code != 0)
		{
			$scope.onReleaseChange($scope.selectedRelease.id);
		}else
		{
			$scope.$broadcast("initProjectReleasedStoryAfterDropBackProject");
		}
	};
	
	
	// for unrelease
	$scope.dropBackProjects = function(event){
		event.preventDefault();
		
		if(!$scope.allowedInProjectBack)
		{
			utils.alert("Drop in this area is not allowed");
			return;
		}
		$scope.allowedInProjectBack = false;
		
		
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
			for(index in $scope.multipleReleasedSelectedProjectsId)
			{
				projectId = $scope.multipleReleasedSelectedProjectsId[index];
				
				projectObj = $scope.releasedPrjctIdObjMap[projectId];
				
				$scope.projectReleased.splice($scope.projectReleased.indexOf(projectObj),1);
				
				projectObj.check = false;
				$scope.projectsForRelease.push(projectObj);
				$scope.unreleasedPrjctIdObjMap[projectObj.id] = projectObj;
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			var model = {"releaseId" : $scope.slectedReleaseId, "projectIds" : $scope.multipleReleasedSelectedProjectsId};
			
			deleteProjectRelease(model);
		}
		
	};
	
	deleteProjectRelease = function(model){
		
		actionHelper.invokeAction("projectRelease.deleteByProjectId", model, null, deleteCallBack, {"hideInProgress" : true});
	};
	
	/*
	 * Selected Project Filter 
	 */
	$scope.projectReleaseFilter = function(){
		
		return function( item ) {
			
			if($scope.searchReleaseProject)
			{
				return item.name.toLowerCase().includes($scope.searchReleaseProject.toLowerCase());
			}
			
		    return true;
		  };
	};
	
	/*
	 * Check box method to select multiple released projects
	 */
	$scope.checkBoxReleasedProject = function(projectId){
		
		$scope.releasedPrjctIdObjMap[projectId].check = !$scope.releasedPrjctIdObjMap[projectId].check;
		
		if($scope.releasedPrjctIdObjMap[projectId].check)
		{
			$scope.multipleReleasedSelectedProjectsId.push(projectId);
			console.log(projectId + " added to list");
		}
		else
		{
			$scope.multipleReleasedSelectedProjectsId.splice($scope.multipleReleasedSelectedProjectsId.indexOf(projectId), 1);
			console.log(projectId + " removed from list");
		}
		
	};	
	
	$scope.checkAllReleasedProject = function(){
		
		var index;
		var prjctObj;
		
		for(index in $scope.projectReleased)
		{
			prjctObj = $scope.projectReleased[index];
			
			if($scope.searchReleaseProject)
			{
				if(prjctObj.name.toLowerCase().includes($scope.searchReleaseProject.toLowerCase()))
				{
					prjctObj.check = true;
					$scope.multipleReleasedSelectedProjectsId.push(prjctObj.id);
				}
			}else
			{
				prjctObj.check = true;
				$scope.multipleReleasedSelectedProjectsId.push(prjctObj.id);
			}
		}
		
	};
	
	
	$scope.unCheckAllReleasedProject = function(){
		
		var index;
		for(index in $scope.projectReleased)
		{
			$scope.projectReleased[index].check = false;
		}
		
		$scope.multipleReleasedSelectedProjectsId = [];
	};
	
}]);


