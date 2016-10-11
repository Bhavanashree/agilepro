$.application.controller('storyReleaseController', ["$scope", "crudController", "utils", "actionHelper", 
                         function($scope, crudController, utils, actionHelper) {

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
	
	readAlStoryAndReleaseCallBack =  function(readResponse, respConfig){
		
		$scope.storiesReleased = readResponse.basicStoryInfos;
		$scope.storiesForRelease = readResponse.storiesForRelease;
		
		var obj;
		var index
		
		for(index in $scope.storiesForRelease)
		{
			obj = $scope.storiesForRelease[index];
			
			obj.check = false;
			
			$scope.unreleasedStoryIdObjMap[obj.id] = obj;
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};
	
	// Listener for broadcast
	$scope.$on("activeReleaseSelectionChangedTest", function(event, args) {

		console.log("listenr story");
		
		var releaseId = $scope.getActiveReleaseId();
		
		$scope.unreleasedStoryIdObjMap = {};
		
		$scope.multipleUnreleasedSelectedStoryIds = [];
		
		console.log("releaseId = " + releaseId);
		
		$scope.selectedRelease = $scope.rlseIdObjMap[releaseId];
		
		console.log($scope.rlseIdObjMap[releaseId]);
		
		$scope.slectedReleaseId = $scope.rlseIdObjMap[releaseId].id;
		
		actionHelper.invokeAction("storyRelease.readAllStoryAndStoryRelease", null, 
				{"releaseId" : $scope.slectedReleaseId}, readAlStoryAndReleaseCallBack, {"hideInProgress" : true});
	   
	});

	
	// Dragging methods
	$scope.dragStories = function(event){
	
		console.log("drag story is called");
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		if(!$scope.slectedReleaseId)
		{
			utils.alert("Please select release");
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
			
			var model = {"releaseId" : $scope.slectedReleaseId, "storyId" : $scope.slectedStoryId};
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
			
			var model = {"releaseId" : $scope.slectedReleaseId, "storyIds" : $scope.multipleUnreleasedSelectedStoryIds};
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
	
}]);
