$.application.controller('storyHierarchyController', ["$scope", "actionHelper", "utils", 
                                              function($scope, actionHelper, utils) {
	
	$scope.storyFilter = function(){
		
		return function(item){
			
			if($scope.searchStory)
			{
				if(item.title.toLowerCase().includes($scope.searchStory.toLowerCase()))
				{
					if(item.parentStoryId)
					{
						
					}
					
					return false;
				}else
				{
					return false;
				}
				
			}
			
			return true;
		};
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
			 if( $scope.newBacklogTitle.trim().length == 0 )
				{
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
			 if( childTitle.trim().length == 0 )
				{
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
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						$scope.newBacklogTitle = "";
						
						backlogModel["indent"] = indentValue;
						backlogModel["id"] = saveResponse.id
						
						if(backlogModel.parentStoryId)
						{
							$scope.initStory();
							
						}else
						{
							$scope.addBacklog(backlogModel);
						}
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
