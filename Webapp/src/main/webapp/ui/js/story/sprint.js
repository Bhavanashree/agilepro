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
	 
	 var projectId;
	 
	 var fileId;
	 
	 var dropedStoryId;
	 
	 $scope.sprintVerId = {};
	 $scope.noSprintIsSelected = true;
	 
	 $scope.isSprintIsSelected = false;

	 $scope.newSprintMode = true;	
	 
		$scope.saveSprint = function() {
			projectId = $scope.getActiveProject();
			
			$scope.model.projectId =  projectId;
			
			$scope.saveChanges();
			$scope.listOfSprint();
		};
		
		//for getting active sprint
		 var selectedSprintObj =  function(){
			 $scope.getActiveSprint($scope.sprint);
		 };
		
		var readSprintCallBack = function(read, response){
			$scope.sprint = read.model;
			
			var index;
			
			var sprintId;
			
				for(index in $scope.sprint)
				{
					sprintId = $scope.sprint[index].id;
				}
				try
				{
		    		$scope.$apply();
				}catch(ex)
				{}	
				
			$scope.listOfStories(); 
		};

		$scope.listOfSprint= function(){	
			console.log("listofsprint");
			 projectId = $scope.getActiveProject();
			actionHelper.invokeAction("sprint.sprintProjectId", null, {"projectId" : projectId}, readSprintCallBack);
		 };
			 	
		 //List of stories
		var readStoryCallBack = function(read, response){
			
			$scope.story = read.model;
			
			var index;
			
			
			for(index in $scope.story)
			{
				if($scope.story[index].photo)
				{
					fileId =  $scope.story[index].photo.fileId;	
					$scope.story[index]["$photoUrl"] = actionHelper.actionUrl("files.fetch", {"id": fileId});
				}
			}
			
			$scope.$apply();					
		};
			
		
		$scope.listOfStories =function(){
			
			 projectId = $scope.getActiveProject();
			 actionHelper.invokeAction("story.readAll",null,{"projectId" : projectId},readStoryCallBack);
		};
		
		// editSprint 
		$scope.editSprint = function(obj){		
			//obj  = JSON.parse($scope.selectedSprintObj);
			obj = $scope.selectedSprintObj;
			console.log(obj.id);
			$scope.selectedId = obj.id;
			$scope.editEntry(obj);
		};
			
		var displaySprintCallBack  = function(read, response)
		{
			var backLogModels  = read.model;
			var backLogModel;
			var index;
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
			
			 $scope.noSprintIsSelected = false;
			 
			 $scope.isSprintIsSelected = true;
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
		};
		

		$scope.displayStoriesBySprint = function(selectedSprintObj){
			
			if(!selectedSprintObj)
			{
				return;
			}
			
			$scope.selectedSprintObj = selectedSprintObj;
			
			$scope.inProgress = [];
			 
			$scope.notStarted = [];
			 
			$scope.completed = [];
				
			 // invoke backlog 
			actionHelper.invokeAction("story.readStoriesBySprint",null,{"sprintId" : selectedSprintObj.id},displaySprintCallBack);
					 
		};
		
		
		var updateEditCallBack = function(updateResponse, respConfig){
			console.log(updateResponse);
			
		//	console.log(updateResponse.model);
			
			$scope.sprintVerId[dropedStoryId] = updateResponse.version;
			
			if(updateResponse.code != 0)
			{
				$scope.displaySprint($scope.selectedSprintObj);
			}
			
		};
		
		// on drag method for stories
		$scope.onDragStories = function(event){
			
			event.originalEvent.dataTransfer.setData('text/plain', 'text');
			
			if(!$scope.selectedSprintObj)
			{
				utils.alert("Please select sprint");
				return;
			}
			$scope.selectedStoryForDrag = JSON.parse(event.target.id);
		};
		
		
		//common method for save status and sprintId to stories
		$scope.sprintUpdateAfterDrop = function(status)
		{	
			 dropedStoryId = $scope.selectedStoryForDrag.id;
			
			// logic to remove object from  array
			if($scope.selectedStoryForDrag.status)
			{
				switch($scope.selectedStoryForDrag.status)
				{
					case 'IN_PROGRESS':
						for(i = 0 ; i < $scope.inProgress.length ; i++)
						{
							if($scope.inProgress[i].id == dropedStoryId)
							{
								$scope.inProgress.splice(i, 1);
							}
						}
						break;
					case 'NOT_STARTED':
						for(i = 0 ; i < $scope.notStarted.length ; i++)
						{
							if($scope.notStarted[i].id == dropedStoryId)
							{
								$scope.notStarted.splice(i, 1);
							}
						}
						break;
					case 'COMPLETED':
						for(i = 0 ; i < $scope.completed.length ; i++)
						{
							if($scope.completed[i].id == dropedStoryId)
							{
								$scope.completed.splice(i, 1);
							}
						}
				}
			}
			
			if( $scope.sprintVerId[dropedStoryId])
			{
				$scope.selectedStoryForDrag.version = $scope.sprintVerId[dropedStoryId];
			}
			
			$scope.selectedStoryForDrag.status = status;
			$scope.selectedStoryForDrag.sprint = $scope.selectedSprintObj.id;
			$scope.selectedStoryForDrag.photo = fileId;
			// logic to push in array
			switch($scope.selectedStoryForDrag.status)
			{
				case 'IN_PROGRESS':
					{
						$scope.inProgress.push($scope.selectedStoryForDrag);
					}
					break;
				case 'NOT_STARTED':
					{
						$scope.notStarted.push($scope.selectedStoryForDrag);
					}
					break;
				case 'COMPLETED':
					{
						$scope.completed.push($scope.selectedStoryForDrag);
					}
			}
			
			for(i = 0 ; i < $scope.story.length ; i++)
			{
				if($scope.story[i].id == dropedStoryId)
				{
					$scope.story.splice(i, 1);
				}
			}
			
			$scope.$apply();
			
			actionHelper.invokeAction("story.update", $scope.selectedStoryForDrag, null, updateEditCallBack);
		}
		
		//functions of Drag to Not started
		$scope.allowDropOnNotStarted = function(event){
			console.log("area for allowDropOnNotStarted");
			event.preventDefault();
		};
		
		$scope.onDropofNotStarted = function(event){
			
			console.log("invoked onDropofNotStarted ", $scope.selectedSprintObj);
			event.preventDefault();
			
			$scope.sprintUpdateAfterDrop("NOT_STARTED");	
			
			$scope.$apply();
		};	

	
		//dropping 2 on progress
		$scope.allowDropOnProgress = function(event){
			console.log("area for allowDropOnProgress");
			event.preventDefault();
		};
		
		$scope.onDropofInProgress = function(event){
			event.preventDefault();
			
			$scope.sprintUpdateAfterDrop("IN_PROGRESS");	
				
			$scope.$apply();
			
		};
	
		//dropping 2	
		$scope.allowDropOnCompleted = function(event){
			console.log("area for allowDropOndragToCompleted");
			 event.preventDefault();
		};
		
		$scope.onDropofCompleted = function(event){
			console.log("invoked onDropofCompleted ");
			event.preventDefault();
			$scope.sprintUpdateAfterDrop("COMPLETED");	
	
			$scope.$apply();		
		};	
		
		// Listener for broadcast
		$scope.$on("activeProjectSelectionChanged", function(event, args) {	
			
			console.log("braod cast is working");
			$scope.listOfSprint();
		});
		
		// sprint active  wrong
		$scope.getActiveSprint = function(sprintId){
			console.log("acctive sprt",$scope.sprint); 
			return $scope.selectedSprintObj;
		 };	
}]);		