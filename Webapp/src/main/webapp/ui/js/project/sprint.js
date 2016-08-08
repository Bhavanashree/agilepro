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
	 
	 var draggingBacklogObj;
	 
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
			 actionHelper.invokeAction("backlog.readAll",null,null,readStoryCallBack);
					 
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
		
		var saveCallBack = function(read, response){
			console.log("savecallback");
		 };
		
		 //saving sprint with  stories
		$scope.onSprintDrop = function(event){
					
			event.preventDefault();
			console.log("invoked onDropBacklog " , model);
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
						"status" : "IN_PROGRESS",
						"priority" :selectedStory[index].priority,
						"sprint" : selectedSprintObj.id 
				};
				// action helper update for stories
				actionHelper.invokeAction("backlog.update", model, null, saveCallBack);
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
			
				
		var displaySprintCallBack  = function(read, response)
		{
			console.log(" displaysprintBack" ,  read.model);
			
			var backLogModels  = read.model;
			
			var backLogModel;
			
			var i = -1;
			
			for(index in backLogModels)
			{
				backLogModel = backLogModels[index];
				
				switch(backLogModel.status)
				{
					case 'IN_PROGRESS':
						$scope.inProgress.push(backLogModel);
						break;
					case 'NOT_STARTED':
						 $scope.notStarted.push(backLogModel);
						break;
					case 'COMPLETED':
						$scope.completed.push(backLogModel);
						
				}
			
				for(i = 0 ; i < $scope.story.length ; i++)
				{
					if($scope.story[i].id == backLogModel.id)
					{
						$scope.story.splice(i, 1);
					}
				}
			}	
			
			try
			{
				$scope.$apply()
			}catch(ex)
			{}
		};
		

				$scope.displaySprint = function(){
				
				$scope.inProgress = [];
				 
				$scope.notStarted = [];
				 
				$scope.completed = [];
			
			selectedSprintObj = JSON.parse($scope.selectedSprintObj); 
			
			 console.log("list2sprints" ,  selectedSprintObj);
			 // invoke backlog 
			 actionHelper.invokeAction("backlog.readSprints",null,{"sprintId" : selectedSprintObj.id},displaySprintCallBack);
					 
		};
		
		$scope.hideLeftMenu = function(){
			
			$scope.common_hideLeftMenu();
		};
		
		var updateEditCallBack = function(read, response){
			
			$scope.displaySprint();
		};	
		
		//drop to in progress
		$scope.dragToProgress = function(event){
	
			console.log("drag to progress");
			event.originalEvent.dataTransfer.setData('text/plain', 'text');
			
			console.log( "id", event.target.id);
			draggingBacklogObj = JSON.parse(event.target.id);
		};
	
		//dropping 2	
		$scope.allowDropOnProgress = function(event){
			console.log("area for allowDropOnProgress");
			 event.preventDefault();
		};
		
		$scope.onDropofInProgress = function(event){
	
			event.preventDefault();
			
				var model = {
						
						"id" : draggingBacklogObj.id,
						"title" : draggingBacklogObj.title,
						"version" : draggingBacklogObj.version,
						"description" : draggingBacklogObj.description,
						"estimate" : draggingBacklogObj.estimate,
						"parentStoryId" :  draggingBacklogObj.parentStoryId, 
						"ownerId" :  draggingBacklogObj.ownerId,	
						"status" :  "IN_PROGRESS", 
						"sprint": selectedSprintObj.id 
				};
				
				// action helper update for stories
				actionHelper.invokeAction("backlog.update", model, null, updateEditCallBack);
				
			$scope.$apply();
			console.log(" test1", model );
			
		};
		
		//drop to in NotStarted
		$scope.dragToNotStarted= function(event){
	
			console.log("drag to NotStarted");
			event.originalEvent.dataTransfer.setData('text/plain', 'text');
			console.log(" id", event.target.id);
			draggingBacklogObj = JSON.parse(event.target.id);
		};
		
		$scope.allowDropOnNotStarted = function(event){
			console.log("area for allowDropOnNotStarted");
			event.preventDefault();
		};
		
		$scope.onDropofNotStarted = function(event){
			console.log("invoked onDropofNotStarted ");
			event.preventDefault();
			
				var model = {
						
						"id" : draggingBacklogObj.id,
						"title" : draggingBacklogObj.title,
						"version" : draggingBacklogObj.version,
						"description" : draggingBacklogObj.description,
						"estimate" : draggingBacklogObj.estimate,
						"parentStoryId" :  draggingBacklogObj.parentStoryId, 
						"ownerId" :  draggingBacklogObj.ownerId,	
						"status" :  "NOT_STARTED", 
						"sprint": selectedSprintObj.id 
				};
			
				// action helper update for stories
				actionHelper.invokeAction("backlog.update", model, null, updateEditCallBack);	
			
				$scope.$apply();
				console.log(" test1", model );
			
		};
	
		// drop  to completed
		$scope.dragToCompleted= function(event){
	
			console.log("drag to dragToCompleted");
			event.originalEvent.dataTransfer.setData('text/plain', 'text');
			
			console.log(" id", event.target.id);
			draggingBacklogObj = JSON.parse(event.target.id);
		};
	
		//dropping 2	
		$scope.allowDropOnCompleted = function(event){
			console.log("area for allowDropOndragToCompleted");
			 event.preventDefault();
		};
		
		$scope.onDropofCompleted = function(event){
			console.log("invoked onDropofCompleted ");
			event.preventDefault();
			
				var model = {
						
						"id" : draggingBacklogObj.id,
						"title" : draggingBacklogObj.title,
						"version" : draggingBacklogObj.version,
						"description" : draggingBacklogObj.description,
						"estimate" : draggingBacklogObj.estimate,
						"parentStoryId" :  draggingBacklogObj.parentStoryId, 
						"ownerId" :  draggingBacklogObj.ownerId,	
						"status" :  "COMPLETED", 
						"sprint": selectedSprintObj.id 
				};
				console.log("invoked onDropofCompleted ", draggingBacklogObj);
				// action helper update for stories
				actionHelper.invokeAction("backlog.update", model, null, updateEditCallBack);
			
			$scope.$apply();		
		};	
		
		
}]);		