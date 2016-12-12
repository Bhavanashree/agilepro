$.application.controller('storyHierarchyController', ["$scope", "actionHelper", 
                                              function($scope, actionHelper) {
	/**
	 * Listener method for key press for new back log.
	 */
	$scope.onTypeNewBacklog = function(e) {
		 
		/*var storyHierarchyId  = $("storyHierarchyId");
		console.log(storyHierarchyId);*/
		
		 e = e || window.event;
		 var key = e.keyCode ? e.keyCode : e.which;
			  
		 //enter key   
		 if (key == 13) 
		 {
			 if( $scope.newBacklogTitle.trim().length == 0 )
				{
					return;
				}
			 
		   $scope.saveBacklog();
		 }
	};

	/**
	 * Save new backlog(parent story)
	 */
	$scope.saveBacklog = function(e){
			
		var backlogModel = {"title" : $scope.newBacklogTitle.trim(),"projectId" : $scope.getActiveProjectId()};
		
		actionHelper.invokeAction("story.save", backlogModel, null, 
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						$scope.newBacklogTitle = "";
						
						backlogModel["indent"] = 0;
						backlogModel["id"] = saveResponse.id
						
						$scope.addBacklog(backlogModel);
						
						var storyHierarchyId  = $("storyHierarchyId");
						storyHierarchyId.animate({ scrollTop: storyHierarchyId[0].scrollHeight });
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				},{"hideInProgress" : true}
		);
	};

}]);
