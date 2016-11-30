$.application.controller('pokerGameController', ["$scope", "crudController","utils","modelDefService","actionHelper", 
                                              function($scope, crudController,utils,modelDefService,actionHelper) {

	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		$scope.readListOfUnassignedStories();
	   
	});
	
	
	
$scope.readListOfUnassignedStories = function(){
		
		projectId = $scope.getActiveProjectId();
		
		actionHelper.invokeAction("story.readByProjectIdAndStatus", null, {"projectId" : projectId}, 
			function(readResponse, respConfig){
			
				$scope.stories= readResponse.model;
				console.log($scope.stories);
				var index;				
				
				$scope.storyIdObj = {};
				
					for(index in $scope.stories)
					{
						if($scope.stories[index].status === "NOT_STARTED")
						{
						console.log($scope.stories[index].status);
						var storyObj = $scope.stories[index];
						$scope.storyIdObj[storyObj.id] = storyObj;
						console.log(storyObj);
						}
					}			
				$scope.$apply();	
			console.log($scope.stories);
		},{"hideInProgress" : true});
	};














}]);
