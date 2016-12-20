$.application.controller('storyHierarchyController', ["$scope", "actionHelper", "utils", 
                                              function($scope, actionHelper, utils) {
	
	$scope.storyFilter = function(){
		
		var retFunc = function(item){
			
			if(!$scope.searchStory)
			{
				return true;
			}
			
			console.log(item.title + "" + item.filtered);
			return item.filtered;
		};
		
		if($scope.oldSearchStory == $scope.searchStory)
		{
			return retFunc;
		}
		
		$scope.oldSearchStory = $scope.searchStory;
		
		//Set the flag based on search string
		if($scope.backLogs.length > 0)
		{
			$scope.checkForFilter($scope.backLogs);
		}

		return retFunc;
	};
	
	$scope.checkForFilter = function(stories) {
		
		var result = false;
		
		if($scope.searchStory)
		{
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
				
				if($scope.checkForFilter($scope.getChildList(story.id)))
				{
					story.filtered = true;
					result = true;
				}
			}
		}
		
		return result;
	};
	
	
	/**
	 * Listener method for key press for new back log.
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
			
			var backlogModel = {"title" : $scope.newBacklogTitle.trim(),"projectId" : $scope.getActiveProjectId()};
			 
			$scope.saveBacklog(backlogModel, 0);
		 }
	};

	/**
	 * Listener method for key press for new child back log.
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
					"parentStoryId" : parentId
			};
			 
		 	$scope.saveBacklog(backlogChildModel, indentValue + 1);
		}
	};
			
	/**
	 * Save new backlog(parent story)
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
					}else
					{
						utils.alert("error in save");
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
	 * Get invoked on click of edit button.
	 */
	$scope.editBacklog = function(backlogId){
		
		$scope.$emit("editStory",backlogId); 
	};
	
}]);
