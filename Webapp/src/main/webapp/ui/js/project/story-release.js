$.application.controller('storyReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {

	$scope.selectedReleasedProject = {};
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
	 * Check box method to select multiple projects
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
	
	readAllStoryReleaseCallBack =  function(readResponse, respConfig){
		
		$scope.storiesReleased = readResponse.model;
		$scope.storiesForRelease = [];
		
		var obj;
		var index
		
		for(index in $scope.storiesForRelease)
		{
			obj = $scope.storiesForRelease[index];
			
			obj.check = false;
			
			$scope.unreleasedStoryIdObjMap[obj.id] = obj;
		}
		
		$scope.selectedReleasedProject = $scope.projectReleased.length > 0 ? $scope.projectReleased[0] : {};
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};

	// Listener for broadcast
	$scope.$on("fetchAllStoryRelease", function(event, args) {
		
		if($scope.activeReleaseId == $scope.getActiveReleaseId())
		{
			return;
		}
		
		console.log("listener is invoked");
		
		$scope.activeReleaseId = $scope.getActiveReleaseId();
		
		$scope.unreleasedStoryIdObjMap = {};
		
		$scope.multipleUnreleasedSelectedStoryIds = [];
		
		actionHelper.invokeAction("storyRelease.readAllStoryRelease", null, 
				{"releaseId" : $scope.activeReleaseId}, readAllStoryReleaseCallBack, {"hideInProgress" : true});
		
	});
	
	
	$scope.onReleaseProjectChange = function(projectId){
		
		/*if($scope.selectedReleasedProject == $scope.releasedPrjctIdObjMap[projectId])
		{
			return;
		}*/
		
		$scope.selectedReleasedProject = $scope.releasedPrjctIdObjMap[projectId];
		
		console.log($scope.selectedReleasedProject);
		
		
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
			$scope.slectedStoryId = event.target.id;
		}
	};
	
	$scope.dropStories = function(event){
		
		console.log("drop story" + $scope.slectedStoryId);
		var storyObj;
		var storyId;
		var index;
		
		event.preventDefault();
		
		if($scope.multipleUnreleasedSelectedStoryIds.length == 0)
		{
			storyObj = $scope.unreleasedStoryIdObjMap[$scope.slectedStoryId];
			
			$scope.storiesForRelease.splice($scope.storiesForRelease.indexOf(storyObj),1);
			
			$scope.storiesReleased.push(storyObj);
			
			var model = {"projectId" : $scope.selectedReleasedProject.id, "storyId" : $scope.slectedStoryId};
			saveNewStoryRelease(model);
		}
		else
		{
			for(index in $scope.multipleUnreleasedSelectedStoryIds)
			{
				storyId = $scope.multipleUnreleasedSelectedStoryIds[index];
				
				storyObj = $scope.unreleasedStoryIdObjMap[storyId];
				
				$scope.storiesForRelease.splice($scope.storiesForRelease.indexOf(storyObj),1);
				
				$scope.storiesReleased.push(storyObj);
			}
			
			var model = {"projectId" : $scope.selectedReleasedProject.id, "storyIds" : $scope.multipleUnreleasedSelectedStoryIds};
			saveNewStoryRelease(model);
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};
	
	
	saveStoryReleaseCallBack = function(readResponse, respConfig){
		
		if(readResponse.code != 0)
		{
			$scope.onReleaseChange($scope.selectedRelease.id);
		}
	};
	
	
	// save new story release
	saveNewStoryRelease = function(model){
		
		actionHelper.invokeAction("storyRelease.save", model, null, saveStoryReleaseCallBack, {"hideInProgress" : true});
	};
	
	// listener
	$scope.$on("initProjectReleasedForStory", function(event, args) {
		
		if($scope.projectReleased.length > 0)
		{
			if(!$scope.selectedReleasedProject.name)
			{
				$scope.onReleaseProjectChange($scope.projectReleased[0].id);
				//$scope.selectedReleasedProject = $scope.projectReleased[0];
			}
		}else
		{
			$scope.selectedReleasedProject = {};
			
			$scope.storiesReleased = [];
			$scope.storiesForRelease = [];
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	});
	
	// listener
	$scope.$on("initProjectReleasedStoryAfterDropBack", function(event, args) {
		
		if($scope.storiesReleased.length > 0)
		{
			actionHelper.invokeAction("storyRelease.deleteByProjectId", null, {"projectId" : $scope.selectedPrjctId}, 
					
					function(deleteResponse, respConfig){
			
						if(deleteResponse.code == 0)
						{
							
						}
				}, {"hideInProgress" : true});
		}
		
		if($scope.projectReleased.length == 0)
		{
			$scope.selectedReleasedProject = {};
			
			$scope.storiesReleased = [];
			$scope.storiesForRelease = [];
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
	
}]);
