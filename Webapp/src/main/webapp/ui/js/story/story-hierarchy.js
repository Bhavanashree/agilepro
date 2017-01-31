$.application.controller('storyHierarchyController', ["$scope", "actionHelper", "utils", 
                                              function($scope, actionHelper, utils) {
	/**
	 * Story Filter gets invoked on type in search box.
	 */
	$scope.storyFilter = function(){
		
		var retFunc = function(item){
			
			if(!$scope.searchStory)
			{
				return true;
			}
			
			return item.filtered;
		};
		
		if($scope.oldSearchStory == $scope.searchStory)
		{
			return retFunc;
		}
		
		//Set the flag based on search string
		if($scope.backLogs.length > 0)
		{
			$scope.checkForFilter($scope.backLogs);
		}
		
		$scope.oldSearchStory = $scope.searchStory;

		return retFunc;
	};
	
	/**
	 * Assign filter value for filter parent and respective child stories.
	 */
	$scope.checkForFilter = function(stories) {
		
		if(!stories)
		{
			return;
		}
		
		var result = false;
		var searchString = $scope.searchStory.toLowerCase();
		
		for(index in stories)
		{
			var story = stories[index];
			
			if(story.title.toLowerCase().includes(searchString))
			{
				story.filtered = true;
				result = true;
			}else
			{
				story.filtered = false;
			}
			
			if($scope.checkForFilter(story.childrens))
			{
				story.filtered = true;
				result = true;
			}
		}
		
		return result;
	};
	
	
	/**
	 * Listener method for key press for adding new back log.
	 */
	$scope.onTypeNewBacklog = function(e) {
		 
		 e = e || window.event;
		 var key = e.keyCode ? e.keyCode : e.which;
		 
			  
		 //enter key   
		 if (key == 13) 
		 {
			 if( $scope.newBacklogTitle.trim().length <= 3 )
				{
				 	utils.alert("Title must be at least 4 characters");
					return;
				}
			
			var backlogModel = {"title" : $scope.newBacklogTitle.trim(), "projectId" : $scope.getActiveProjectId(),
								"isManagementStory" : false};
			 
			$scope.saveBacklog(backlogModel, 0);
		 }
	};

	/**
	 * Listener method for key press for adding new child back log.
	 */
	$scope.onTypeNewBacklogChild = function(e, parentId, indentValue){
		
		$scope.targetType = $(e.target);
		 
		var childTitle = $scope.targetType.val();
		 
		e = e || window.event;
		var key = e.keyCode ? e.keyCode : e.which;
			  
		//enter key   
		if (key == 13) 
		{
			if( childTitle.trim().length <= 3 )
			{
				utils.alert("Title must be at least 4 characters");	
				return;
			}
			 
			var backlogChildModel = {"title" : childTitle.trim(),
					"projectId" : $scope.getActiveProjectId(),
					"parentStoryId" : parentId,
					"isManagementStory" : false
			};
			 
		 	$scope.saveBacklog(backlogChildModel, indentValue + 1);
		}
	};
			
	/**
	 * Common method for saving new backlog.
	 */
	$scope.saveBacklog = function(backlogModel, indentValue){
			
		actionHelper.invokeAction("story.save", backlogModel, null, 
				function(storyResponse, respConfig)
				{
					if(storyResponse.code == 0)
					{
						$scope.newBacklogTitle = "";
						
						backlogModel["indentHierarchy"] = indentValue;
						backlogModel["id"] = storyResponse.newStoryId;
						backlogModel["priority"] = storyResponse.storyIdPriority[backlogModel.id];
						
						$scope.setStoryType(backlogModel);
						
						if(backlogModel.parentStoryId)
						{
							$scope.targetType.val("");
						}
						
						$scope.addSavedBacklog(backlogModel, storyResponse.storyIdPriority);
						
						$scope.refreshPriority();
					}else
					{
						utils.alert(storyResponse.message);
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				},{"hideInProgress" : true}
		);
	};

	/**
	 * Gets invoked on click of edit button on search result.
	 */
	$scope.editBacklog = function(backlogId){
		
		$scope.$broadcast("editStory", backlogId);
	};
	
	/**
	 * Gets invoked on click of delete button.
	 */
	$scope.deleteBacklog = function(backlogId){
		
		$scope.initDeletedIds();
		
		$scope.$broadcast("deleteStory", backlogId);
	}
	
	/**
	 * Update management story.
	 */
	$scope.updateManagementStory = function(backlogId, isManagementStory){
		
		actionHelper.invokeAction("story.updateStoryManagement", null, {"id" : backlogId, "isManagmentStory" : !isManagementStory},
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						$scope.idToStory[backlogId].isManagementStory = !isManagementStory;
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				}, {"hideInProgress" : true});
	};
	
	/**
	 * save new note
	 */
	$scope.saveStoryNote  = function(storyNoteStatus){
		
		$scope.$broadcast("saveNewStoryNote", storyNoteStatus);
	};	
	
}]);
