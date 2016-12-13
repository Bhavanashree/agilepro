$.application.controller('storyHierarchyController', ["$scope", "actionHelper", "utils", 
                                              function($scope, actionHelper, utils) {
	
	$scope.storyFilter = function(){
		
		/*if($scope.oldSearchStory == $scope.searchStory)
		{
			return;
		}
		
		$scope.oldSearchStory = $scope.searchStory;*/
		
		//Set the flag based on search string
		if($scope.finalResult.length > 0)
		{
			$scope.checkForFilter($scope.finalResult);
		}
		
		
		return function(item){
			
			if(!$scope.searchStory)
			{
				return true;
			}
			
			console.log(item.title + "" + item.filtered);
			return item.filtered;
		};
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
		
		 var childTitle = $(e.target).val();
		 
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
			 
			 $scope.saveBacklog(backlogChildModel, indentValue);
		 }
	};
			
	/**
	 * Save new backlog(parent story)
	 */
	$scope.saveBacklog = function(backlogModel, indentValue){
			
		actionHelper.invokeAction("story.save", backlogModel, null, 
				function(storyResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						$scope.newBacklogTitle = "";
						
						backlogModel["indent"] = indentValue;
						backlogModel["id"] = storyResponse.newStoryId;
						
						if(backlogModel.parentStoryId)
						{
							backlogModel["priority"] = storyResponse.storyIdPriority[backlogModel.id].priority;
							
							$scope.addChildBacklog(backlogModel, storyResponse.storyIdPriority);
							
						}else
						{
							$scope.addBacklog(backlogModel);
						}
					}else
					{
						// response error
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				},{"hideInProgress" : true}
		);
	};

	
	$scope.editBacklog = function(backlogId){
		
		$('#storyDialogId').modal('show');
	};
	
}]);
