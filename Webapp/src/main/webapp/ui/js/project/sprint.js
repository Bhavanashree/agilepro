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

	 
		$scope.newSprintMode = true;		
		 $scope.saveSprint = function(e) {
			 console.log("model is invoked " + $scope.sprintName);	
			 $scope.addEntry();
		
		};
	
		
		var readSprintCallBack = function(read, response){
			$scope.sprint = read.model;
			console.log(read.model);
			
			console.log($scope.sprint);
			
			var index;
			var sprintId;
			
			for(index in $scope.sprint)
				{
				sprintName = $scope.sprint[index].name;
				sprintId = $scope.sprint[index].id;
	
			console.log(" sprint id2---  >", $scope.sprint[index].id);

				}
			$scope.$apply();
			
		};
		
		
			$scope.listOfSprint= function(sprintName){			 
			 console.log("listofsprint");
			 actionHelper.invokeAction("sprint.readAll", null, null, readSprintCallBack);
			 };
			 	
			 //List of stories
			 
			 var readStoryCallBack = function(read, response){
				$scope.story = read.model;
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
			
					//autorefresh
				$scope.refreshSearch = function(){
					$scope.$broadcast("invokeSearch", {});
				};
			
				$scope.editSprint = function(obj){		
						obj  = JSON.parse($scope.selectedSprintObj);
						console.log(obj.id);
						$scope.selectedId = obj.id;
						$scope.editEntry(obj);
						$scope.refreshSearch();
					};
					
				$scope.notAssignedStories = function(e){
				var stories = $scope.story;
				var index;
				
				for(index in $scope.story)
				{
				storyId = $scope.story[index].name;

				console.log("3", $scope.story[index].title);

				console.log(" status check 1 :" , $scope.story[index].status);
							
				if($scope.story[index].status =='ASSIGNED')
						{
							var test = $scope.story[index];
							console.log(" status check test :" ,test);

							//console.log(" status check indexstatus scope :" , $scope.story[1]);
							
						}
				}
				};
}]);		