$.application.controller('backlogController', ["$scope", "utils", "actionHelper",
       function($scope, utils, actionHelper) {
	
	/**
	 * Check box multiple backlogs.
	 */
	$scope.checkBoxBacklog = function(backlogId, isBug){
		
		var backlogObj = isBug ? $scope.idToBacklogBug[backlogId] : $scope.idToBacklogStory[backlogId];
		
		backlogObj.check = !backlogObj.check; 
		
		if(backlogObj.check)
		{
			if(backlogObj.isBug)
			{
				$scope.multipleCheckedBugIds.push(backlogObj.id);
			}else
			{
				$scope.multipleCheckedStoryIds.push(backlogObj.id);
			}
		}else
		{
			if(backlogObj.isBug)
			{
				var index = $scope.multipleCheckedBugIds.indexOf(backlogObj.id);
				
				$scope.multipleCheckedBugIds.splice(index, 1);
			}else
			{
				var index = $scope.multipleCheckedStoryIds.indexOf(backlogObj.id);
				
				$scope.multipleCheckedStoryIds.splice(index, 1);
			}
		}
		
		if(backlogObj.childrens.length > 0)
		{
			$scope.checkBoxChildStories(backlogObj.childrens, backlogObj.check);
		}
	};
	
	/**
	 * Check box child stories as per the parent.
	 */
	$scope.checkBoxChildStories = function(childArr, checkValue){
		
		for(index in childArr)
		{
			var childObj = childArr[index];
			
			childObj.check = checkValue;
			
			var indexInMultiple = $scope.multipleCheckedStoryIds.indexOf(childObj.id);
			
			if(childObj.check && indexInMultiple == -1)
			{
				$scope.multipleCheckedStoryIds.push(childObj.id);
			}else if(!childObj.check && indexInMultiple != -1)
			{
				$scope.multipleCheckedStoryIds.splice(indexInMultiple, 1);
			}
			
			if(childObj.childrens.length > 0)
			{
				$scope.checkBoxChildStories(childObj.childrens, checkValue);
			}
		}
		
	};

	
	/**
	 * Initalize backlog
	 */
	$scope.initBacklog = function(){
		
		var projectId = $scope.getActiveProjectId();
		
		if(projectId == -1)
		{
			return;
		}
		
		$scope.multipleCheckedStoryIds = [];
		$scope.multipleCheckedBugIds = [];
		
		$scope.backlogs = [];
		
		$scope.idToBacklogStory = {};
		$scope.idToBacklogBug = {};
		
		actionHelper.invokeAction("storyAndBug.fetchBacklogsByProjectId", null, {"projectId" : projectId},
				function(readResponse, respConfig)
				{
					$scope.backlogStoryModels = readResponse.model.backlogStoryModels;
					$scope.backlogBugModels = readResponse.model.backlogBugModels;
					
					$scope.backlogs = $scope.backlogStoryModels.concat($scope.backlogBugModels);
					
					$scope.backlogs.sort(function(a, b){return a.priority-b.priority});
					
					for(index in $scope.backlogs)
					{
						var obj = $scope.backlogs[index];
						obj.childrens = [];
						
						if(obj.isBug)
						{
							$scope.idToBacklogBug[obj.id]  = obj;
						}else
						{
							$scope.idToBacklogStory[obj.id] = obj;
						}
					}
				
					// add childrens
					$scope.addChildrens($scope.backlogStoryModels, $scope.idToBacklogStory);
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				}, {"hideInProgress" : true});
	};

	/**
	 * Add childrens
	 */
	$scope.addChildrens = function(arrObjs, idToObjMap){
		
		for(index in arrObjs)
		 {
			 var obj =  arrObjs[index];
			 var parent = idToObjMap[obj.parentStoryId];
			 
			 if(parent)
			 {
				 parent.childrens.push(obj);
			 }
		 }
	};
	
	/**
	 * Get backlog story obj.
	 */
	$scope.getBacklogStory = function(backlogStoryId){
		
		return $scope.idToBacklogStory[backlogStoryId];
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initBacklog();
	});
	
}]);