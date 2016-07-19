$.application.controller('sprintController', ["$scope", "crudController", "utils","modelDefService","actionHelper",
                                               function($scope, crudController,utils, modelDefService, actionHelper) {
	
	 crudController.extend($scope, {
		"name": "Sprint",
		"modelName": "SprintModel",
		
		"nameColumn" : "name",
		
		"modelDailogId": "sprintDialog",
		
		"saveAction": "sprint.save",
		"readAction": "sprint.read",
		"updateAction": "sprint.update",
		"deleteAction": "sprint.delete",
		
		});

		$scope.newModelMode = true;
		$scope.model =[];
		
		 $scope.saveSprint = function(e) {
			 console.log("model is invoked " + $scope.sprintName);	
			 $scope.addEntry();
			 console.log("model is invoked 1111" + $scope.sprintName);
		
		};
	
		
		var readSprintCallBack = function(read, response){
			$scope.sprint =read.model;
			console.log(read.model);
			
			console.log($scope.sprint);
			
			var index;
			
			var sprintId;
			
			for(index in $scope.sprint)
				{
				sprintId = $scope.sprint[index].name;
			console.log(" sprint id---  >", sprintId);
			console.log(" sprint id---  >", $scope.sprint[index].id);

				}
			
			$scope.$apply();
			
		};
		
		 $scope.listOfSprint= function(sprintName){			 
			 console.log("listofsprint");
			 actionHelper.invokeAction("sprint.readAll", null, null, readSprintCallBack);
			 };
		
	
			 
			 var readStoryCallBack = function(read, response){
					$scope.story =read.model;
					console.log(read.model);
					
					console.log($scope.story);
					
					var index;
					
					var storyId;
					
					for(index in $scope.story)
						{
						storyId = $scope.story[index].name;
						}
					
					$scope.$apply();
					
				};
				

				 $scope.listOfStories =function(storyTitle){
					 console.log("liststories");
					 actionHelper.invokeAction("story.readAll",null,null,readStoryCallBack);
					 
				 };
}]);		