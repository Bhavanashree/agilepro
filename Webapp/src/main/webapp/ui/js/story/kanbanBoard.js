$.application.controller('kanbanController', ["$scope", "crudController", "utils","modelDefService","actionHelper",
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
		

		"postSaveOp": function(model, $scope) {
			$scope.listOfSprint();
		}

		});
	 $scope.childStrsToUpdtInDb = [];
	
	 $scope.noSprintIsSelected = true;
	 
	 $scope.isSprintIsSelected = false;
	 
	 $scope.sprintVerId = {};
	 $scope.sprints={};
	 
	 $scope.clean = function() {
		 $scope.story = [];
		 $scope.inProgress = [];
		 $scope.notStarted = [];
		 $scope.completed = [];
		 $scope.idToStory = {};
	 };
	 
	// after sprint save get it by project id and display in dropdown
	$scope.listOfSprint = function(){	
		console.log("listofsprint");
		
		projectId = $scope.getActiveProjectId();
		$scope.isSprintIsSelected = false;
		$scope.selectedSprintObj = null;
		
		actionHelper.invokeAction("sprint.sprintProjectId", null, {"projectId" : projectId}, function(read, response){
			$scope.sprints = read.model;
			$scope.clean();
			
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}	
			
			if($scope.sprints && $scope.sprints.length > 0)
			{
				$scope.selectedSprintObj = $scope.sprints[0];
				
				// get list of stories by projectId because stories are not yet assigned
				$scope.listOfStories(); 
			}
		});
	 };	 
	 
	// after broad cast from projectId selection 
	$scope.listOfStories = function(){
		
		projectId = $scope.getActiveProjectId();
		 
		 //List of stories
		var readStoryCallBack = function(read, response){
			
			var storyArrForIterat = read.model;
			
			$scope.clean();
			
			var index;

			for(index in storyArrForIterat)
			{
				if(storyArrForIterat[index].photo)
				{
					fileId =  storyArrForIterat[index].photo.fileId;	
					storyArrForIterat[index]["$photoUrl"] = actionHelper.actionUrl("files.fetch", {"id": fileId});
				}
				
				$scope.idToStory["" + storyArrForIterat[index].id] = storyArrForIterat[index];
				
				switch(storyArrForIterat[index].status)
				{
					case 'IN_PROGRESS':
						$scope.inProgress.push(storyArrForIterat[index]);
						break;
					case 'NOT_STARTED':
						 $scope.notStarted.push(storyArrForIterat[index]);
						break;
					case 'COMPLETED':
						$scope.completed.push(storyArrForIterat[index]);
						break;
					default:
						$scope.story.push( storyArrForIterat[index] );
				}
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}				
		};
			
		 
		 actionHelper.invokeAction("story.fetchStoryBysprintAndProjectId", null, 
				 {"projectId" : projectId ,"sprint" : $scope.selectedSprintObj.id}, readStoryCallBack);
	}; 
	 
	 // save new sprint
	 $scope.saveSprint = function() {
		projectId = $scope.getActiveProject();
		
		$scope.model.projectId =  projectId;
		
		$scope.saveChanges();
	};
	 
	// editSprint 
	$scope.editSprint = function(obj){	
		
		obj = $scope.selectedSprintObj;
		
		$scope.selectedId = obj.id;
		$scope.editEntry(obj);
	};
	
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {	
		console.log("Kanban Board: Project change event recieved");
		$scope.listOfSprint();
	});
	
	// DRAG AND DROP METHODS
	
	// on drag method for stories
	$scope.onDragStories = function(event){
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$scope.selectedStoryForDragId = event.target.id;
	};
	
	
	$scope.onDropofNotStarted = function(event){
		event.preventDefault();
		$scope.handleOnDropEvent("NOT_STARTED");	
	};	
	
	$scope.onDropofInProgress = function(event){
		event.preventDefault();
		$scope.handleOnDropEvent("IN_PROGRESS");	
	};
	
	$scope.onDropofCompleted = function(event){
		event.preventDefault();
		$scope.handleOnDropEvent("COMPLETED");	
	};
	
	$scope.getListWithStatus = function(status)
	{
		switch(status)
		{
			case 'IN_PROGRESS':
				return $scope.inProgress;
			case 'NOT_STARTED':
				return $scope.notStarted;
			case 'COMPLETED':
				return $scope.completed;
			default:
				return $scope.story;
		}
	}
	
	//common method for update status and sprintId to stories
	$scope.handleOnDropEvent = function(status, skipServer)
	{	
		 var dropedStory = $scope.idToStory[$scope.selectedStoryForDragId];
		 var oldStatus = dropedStory.status;
		 
		 var srcList = $scope.getListWithStatus(dropedStory.status);
		 var targetList = $scope.getListWithStatus(status);
		 
		 var currentIndex = srcList.indexOf(dropedStory);
		 srcList.splice(currentIndex, 1);
		 
		 targetList.push(dropedStory);
		 
		 dropedStory.sprint = $scope.selectedSprintObj.id;
		 dropedStory.status = status;

		 $scope.$apply();
		 
		 if(skipServer)
		 {
			 return;
		 }
		
		actionHelper.invokeAction("story.update", dropedStory, null, $.proxy(function(updateResponse, respConfig) {
			//if update failed
			if(updateResponse.code != 0)
			{
				utils.info(["Failed to move story '{}' to status '{}'. Please try again!", story.title, story.status]);
				this.$scope.handleOnDropEvent(this.oldStatus, true);
				return;
			}
			
			this.story.version = updateResponse.version;
			
		}, {"$scope": $scope, "story": dropedStory, "oldStatus" : oldStatus}));
	}
	
}]);		