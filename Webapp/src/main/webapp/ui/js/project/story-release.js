$.application.controller('storyReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {

	$scope.selectedReleasedProject = {};
	$scope.storiesForRelease = [];
	$scope.storiesReleased = [];
	
	$scope.storyFilter = function(){
		
		return function( item ) {
			
			if($scope.searchUnreleaseStory)
			{
				return item.name.toLowerCase().includes($scope.searchUnreleaseStory.toLowerCase());
			}
			
		    return true;
		  };
	};
	
	$scope.checkAllUnreleasedStory = function(){
		
		var index;
		var storyObj;
		
		for(index in $scope.storiesForRelease)
		{
			storyObj = $scope.storiesForRelease[index];
			
			if($scope.searchUnreleaseStory)
			{
				if(item.name.toLowerCase().includes($scope.searchUnreleaseStory.toLowerCase()))
				{
					storyObj.check = true;
					$scope.multipleUnreleasedSelectedStoryIds.push(storyObj.id);
				}
			}else
			{
				storyObj.check = true;
				$scope.multipleUnreleasedSelectedStoryIds.push(storyObj.id);
			}
		}
	};
	
	$scope.unCheckAllUnreleasedStory = function(){
		
		var index;
		for(index in $scope.storiesForRelease)
		{
			$scope.storiesForRelease[index].check = false;
		}
		
		$scope.multipleUnreleasedSelectedStoryIds = [];
	};
	
	/*
	 * Check box method to select multiple stories
	 */
	$scope.checkBoxStory = function(storyId){
		
		$scope.unreleasedStoryIdObjMap[storyId].check = !$scope.unreleasedStoryIdObjMap[storyId].check;
		
		
		if($scope.unreleasedStoryIdObjMap[storyId].check)
		{
			$scope.multipleUnreleasedSelectedStoryIds.push(storyId);
			console.log(storyId + " added to list");
		}
		else
		{
			$scope.multipleUnreleasedSelectedStoryIds.splice($scope.multipleUnreleasedSelectedStoryIds.indexOf(storyId), 1);
			console.log(storyId + " removed from list");
		}
		
	};
	
	readAllStoryAndStoryReleaseCallBack =  function(readResponse, respConfig){
		
		$scope.storiesReleased = readResponse.basicStoryInfos;
		$scope.storiesForRelease = readResponse.storiesForRelease;
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
		var index
		for(index in $scope.storiesReleased)
		{
			$scope.releasedStoryIdObjMap[$scope.storiesReleased[index].id] = $scope.storiesReleased[index];
		}
		
		var obj;
		var index
		for(index in $scope.storiesForRelease)
		{
			obj = $scope.storiesForRelease[index];
			
			obj.check = false;
			
			$scope.unreleasedStoryIdObjMap[obj.id] = obj;
		}
	};
	
	$scope.onReleaseProjectChange = function(projectId){
		
		if($scope.selectedReleasedProject.id == $scope.releasedPrjctIdObjMap[projectId].id)
		{
			return;
		}
		
		$scope.selectedReleasedProject = $scope.releasedPrjctIdObjMap[projectId];
		
		console.log($scope.selectedReleasedProject);
		
		$scope.releasedStoryIdObjMap = {};
		$scope.multipleReleasedSelectedStoryIds = [];
		
		$scope.unreleasedStoryIdObjMap = {};
		$scope.multipleUnreleasedSelectedStoryIds = [];
		
		$scope.activeReleaseId = $scope.getActiveReleaseId();
		
		actionHelper.invokeAction("storyRelease.readAllStoryReleaseByReleaseAndProject", null, 
				{"releaseId" : $scope.activeReleaseId, "projectId" : $scope.selectedReleasedProject.id}, readAllStoryAndStoryReleaseCallBack, {"hideInProgress" : true});
	};
	
	
	// Dragging methods
	$scope.dragStories = function(event){
	
		console.log("drag story is called");
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		if(!$scope.selectedReleasedProject)
		{
			utils.alert("Please select project");
			return;
		}
		
		if($scope.multipleUnreleasedSelectedStoryIds.length == 0)
		{
			$scope.selectedStoryId = event.target.id;
		}
		
		$scope.allowedInStoryRelease = true;
	};
	
	$scope.dropStories = function(event){

		event.preventDefault();
		
		if(!$scope.allowedInStoryRelease)
		{
			utils.alert("Drop in this area is not allowed");
			return;
		}
		$scope.allowedInStoryRelease = false;
		
		console.log("drop story" + $scope.selectedStoryId);
		var storyObj;
		var storyId;
		var index;
		
		if($scope.multipleUnreleasedSelectedStoryIds.length == 0)
		{
			storyObj = $scope.unreleasedStoryIdObjMap[$scope.selectedStoryId];
			$scope.storiesForRelease.splice($scope.storiesForRelease.indexOf(storyObj),1);
			
			storyObj.check = false;
			$scope.storiesReleased.push(storyObj);
			$scope.releasedStoryIdObjMap[storyObj.id] = storyObj;
			
			var model = {"releaseId" : $scope.selectedRelease.id, "storyId" : $scope.selectedStoryId};
			saveNewStoryRelease(model);
		}
		else
		{
			for(index in $scope.multipleUnreleasedSelectedStoryIds)
			{
				storyId = $scope.multipleUnreleasedSelectedStoryIds[index];
				
				storyObj = $scope.unreleasedStoryIdObjMap[storyId];
				$scope.storiesForRelease.splice($scope.storiesForRelease.indexOf(storyObj),1);
				
				storyObj.check = false;
				$scope.storiesReleased.push(storyObj);
				$scope.releasedStoryIdObjMap[storyObj.id] = storyObj;
			}
			
			var model = {"releaseId" : $scope.selectedRelease.id, "storyIds" : $scope.multipleUnreleasedSelectedStoryIds};
			saveNewStoryRelease(model);
			
			$scope.multipleUnreleasedSelectedStoryIds = [];
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};
		
	
	// save new story release
	saveNewStoryRelease = function(model){
		
		actionHelper.invokeAction("storyRelease.save", model, null, 
			function(readResponse, respConfig){
			
				if(readResponse.code != 0)
				{
					$scope.onReleaseChange($scope.selectedRelease.id);
				}
				
		}, {"hideInProgress" : true});
		
	};
	
	// Listener
	$scope.$on("activeReleaseSelectionChanged", function(event, args) {
		
		console.log("activeReleaseSelectionChanged story-release");
		
		// init stories as release is changed
		$scope.selectedReleasedProject = {};
		$scope.storiesForRelease = [];
		$scope.storiesReleased = [];
	});
	
	// listener for drop down
	$scope.$on("initProjectReleasedForStory", function(event, args) {
		
		console.log("listener for initProjectReleasedForStory = " + $scope.selectedReleasedProject.name);
		
		if($scope.projectReleased.length > 0)
		{
			if(!$scope.selectedReleasedProject.name)
			{
				console.log("if = " + $scope.selectedReleasedProject.name);
				$scope.onReleaseProjectChange($scope.projectReleased[0].id);
				//$scope.selectedReleasedProject = $scope.projectReleased[0];
			}
		}else
		{
			$scope.selectedReleasedProject = {};
			$scope.storiesForRelease = [];
			$scope.storiesReleased = [];
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
		}
		
	});
	
	// listener
	$scope.$on("initProjectReleasedStoryAfterDropBackProject", function(event, args) {
		
		if(projectId == $scope.selectedPrjctId)
		{
			return;
		}
		
		var projectId = $scope.selectedPrjctId;
		
		console.log("listener initProjectReleasedStoryAfterDropBackProject");

		console.log("project id fro storyrelease delete = " + $scope.selectedPrjctId);
		
		var projectIds;
		
		if($scope.multipleReleasedSelectedProjectsId.length == 0)
		{
			projectIds = [$scope.selectedPrjctId];
		}else
		{
			projectIds = $scope.multipleReleasedSelectedProjectsId;
			
			 $scope.multipleReleasedSelectedProjectsId = [];
		}
		
		var model = {"releaseId" : $scope.slectedReleaseId, "projectIds" : projectIds};
		
		actionHelper.invokeAction("storyRelease.deleteByProjectId", model, null, 
				function(deleteResponse, respConfig){
			
		}, {"hideInProgress" : true});

		
		if($scope.projectReleased.length == 0)
		{
			$scope.selectedReleasedProject = {};
			$scope.storiesForRelease = [];
			$scope.storiesReleased = [];
		}
		else if($scope.selectedPrjctId == $scope.selectedReleasedProject.id)
		{
			$scope.onReleaseProjectChange($scope.projectReleased[0].id);
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	});
	
	
	/*
	 * Drag stories back
	 * 
	 */
	$scope.dragBackStories = function(event){
		
		console.log("drag back story is called");
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		if(!$scope.selectedReleasedProject)
		{
			utils.alert("Please select project");
			return;
		}
		
		if($scope.multipleUnreleasedSelectedStoryIds.length == 0)
		{
			$scope.selectedStoryId = event.target.id;
		}
		
		$scope.allowedInStoryBack = true;
	};
	
	/*
	 * Drop back stories
	 */
	$scope.dropBackStories = function(event){
		
		event.preventDefault();
		console.log("drop back story" + $scope.selectedStoryId);
		
		if(!$scope.allowedInStoryBack)
		{
			utils.alert("Drop in this area is not allowed");
			return;
		}
		$scope.allowedInStoryBack = false;
		
		
		var storyObj;
		var storyId;
		var index;
		
		if($scope.multipleReleasedSelectedStoryIds.length == 0)
		{
			storyObj = $scope.releasedStoryIdObjMap[$scope.selectedStoryId];
			
			$scope.storiesReleased.splice($scope.storiesReleased.indexOf(storyObj), 1);
			
			storyObj.check = false;
			$scope.storiesForRelease.push(storyObj);
			$scope.unreleasedStoryIdObjMap[storyObj.id] = storyObj;
			
			var model = {"releaseId" : $scope.selectedRelease.id, "storyIds" : [$scope.selectedStoryId]};
			deleteStoryRelease(model);
		}
		else
		{
			for(index in $scope.multipleReleasedSelectedStoryIds)
			{
				storyId = $scope.multipleReleasedSelectedStoryIds[index];
				
				storyObj = $scope.releasedStoryIdObjMap[storyId];
				
				$scope.storiesReleased.splice($scope.storiesReleased.indexOf(storyObj),1);
				
				storyObj.check = false;
				$scope.storiesForRelease.push(storyObj);
				$scope.unreleasedStoryIdObjMap[storyObj.id] = storyObj;
			}
			
			var model = {"releaseId" : $scope.selectedRelease.id, "storyIds" : $scope.multipleReleasedSelectedStoryIds};
			deleteStoryRelease(model);
			
			$scope.multipleReleasedSelectedStoryIds = [];
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
	};
	
	
	deleteStoryRelease = function(model){
		
		actionHelper.invokeAction("storyRelease.deleteByStoryId", model, null, 
				function(deleteResponse, respConfig){
			
					if(deleteResponse.code != 0)
					{
						//$scope.onReleaseProjectChange($scope.selectedReleasedProject.id);
					}
					
				}, {"hideInProgress" : true});
		
	};
	
	
	$scope.storyReleasedFilter = function(){
		
		return function( item ) {
			
			if($scope.searchReleaseStory)
			{
				return item.title.toLowerCase().includes($scope.searchReleaseStory.toLowerCase());
			}
			
		    return true;
		  };
	};
	
	/*
	 * Check box method to select multiple released stories
	 */
	$scope.checkBoxReleasedStory = function(storyId){
		
		$scope.releasedStoryIdObjMap[storyId].check = !$scope.releasedStoryIdObjMap[storyId].check;
		
		
		if($scope.releasedStoryIdObjMap[storyId].check)
		{
			$scope.multipleReleasedSelectedStoryIds.push(storyId);
			console.log(storyId + " added to list");
		}
		else
		{
			$scope.multipleReleasedSelectedStoryIds.splice($scope.multipleReleasedSelectedStoryIds.indexOf(storyId), 1);
			console.log(storyId + " removed from list");
		}
		
	};
	
	$scope.checkAllReleasedStory = function(){
		
		var index;
		var storyObj;
		
		for(index in $scope.storiesReleased)
		{
			storyObj = $scope.storiesReleased[index];
			
			if($scope.searchReleaseStory)
			{
				if(item.name.toLowerCase().includes($scope.searchReleaseStory.toLowerCase()))
				{
					storyObj.check = true;
					$scope.multipleReleasedSelectedStoryIds.push(storyObj.id);
				}
			}else
			{
				storyObj.check = true;
				$scope.multipleReleasedSelectedStoryIds.push(storyObj.id);
			}
		}
	};
	
	$scope.unCheckAllReleasedStory = function(){
		
		var index;
		for(index in $scope.storiesReleased)
		{
			$scope.storiesReleased[index].check = false;
		}
		
		$scope.multipleReleasedSelectedStoryIds = [];
	};
	
}]);
