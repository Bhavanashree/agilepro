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

	
	 var selectedStory =[];
	 
	 $scope.sprints = [];
	 
	 var selectedSprintObj;
	 
	 
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
				
			for(index in $scope.story)
			{
				$scope.story[index].dragValue = false;
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
			
		// editSprint 
		$scope.editSprint = function(obj){		
			obj  = JSON.parse($scope.selectedSprintObj);
			console.log(obj.id);
			$scope.selectedId = obj.id;
			$scope.editEntry(obj);
			$scope.refreshSearch();
		};
					
					
			//drag and drop	1
		$scope.onSelectStories = function(backlog) {
				
			console.log("id------- is =========" + backlog.id);
			
					
			backlog.dragValue = true;
				
			selectedStory.push(backlog);

		};
			
			
		var saveCallBack = function(r, s){
				
		};
			
		//dropping 2	
		$scope.allowDrop = function(event){
			console.log("area for drop");
			event.preventDefault();
		};
				
		//saving sprint with  stories
		$scope.onSprintDrop = function(event){
					
			event.preventDefault();
			
			for(index in selectedStory)
			{
				var model = {
						"id" : selectedStory[index].id,
						"title" : selectedStory[index].title,
						"version" : selectedStory[index].version,
						"description" : selectedStory[index].description,
						"estimate" : selectedStory[index].estimate,
						"parentStoryId" : selectedStory[index].parentStoryId, 
						"ownerId" : selectedStory[index].ownerId,
						"status" : "ASSIGNED",
						"priority" :selectedStory[index].priority,
						"sprint" : selectedSprintObj.id 
				};
				// action helper update for stories
				actionHelper.invokeAction("story.update", model, null, saveCallBack);
				
			}
			
				
			// loop for update in ui
			for(index in selectedStory)
			{
				$scope.sprints.push(selectedStory[index]);
			}
			
			selectedStory = [];
			console.log("onSprintDrop invoked" + $scope.sprints.length);
			$scope.$apply();
			console.log(" test1", model );
			
		};
			
			
		$scope.dragToSprint = function(event){
			//selectedSprintObj = JSON.parse($scope.selectedSprintObj); 
			console.log("dragsprint1");		
			console.log($scope.selectedSprintObj);
			event.originalEvent.dataTransfer.setData('text/plain', 'text');
				
		};
		
		
		var dispalySprintCallBack  = function(read, response)
		{
			$scope.displayStory = read.model;
			
			console.log(" displaysprintBack" ,  read.model);
			
			try
			{
				$scope.$apply()
			}catch(ex)
			{}
		};
		

		$scope.displaySprint =function(){
			
			selectedSprintObj = JSON.parse($scope.selectedSprintObj); 
			
			 console.log("list2sprints" ,  selectedSprintObj);
			 // invoke story 
			 actionHelper.invokeAction("story.readSprints",null,{"sprintId" : selectedSprintObj.id},dispalySprintCallBack);
					 
		};	
}]);		