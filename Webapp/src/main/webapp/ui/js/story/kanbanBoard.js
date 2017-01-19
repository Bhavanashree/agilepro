$.application.controller('kanbanController', ["$scope", "crudController", "utils","modelDefService","actionHelper",
                                               function($scope, crudController,utils, modelDefService, actionHelper) {

	 $scope.childStrsToUpdtInDb = [];
	
	 $scope.noSprintIsSelected = true;
	 
	 $scope.isSprintIsSelected = false;
	 
	 $scope.sprintVerId = {};
	 $scope.sprints={};
	 
	 /* attribute for flip */
	 $scope.isBacklogActive = true;
	 
	 $scope.flipStatus = function(){
		 
		 $scope.isBacklogActive = !$scope.isBacklogActive;
		 
		if(!$scope.isBacklogActive && $scope.selectedTeam)
		{
			$scope.clonedSelectedTeam = $scope.selectedTeam;
			$scope.selectedTeam = null; 
		}else
		{
			$scope.selectedTeam = $scope.clonedSelectedTeam;
		}
	 };
	 
	 /**
	  * On sprint change drop down.
	  */
	 $scope.onSprintChange = function(sprintId){
		 
		 $scope.selectedSprintObj = $scope.sprintIdObjMap[sprintId];
		 $scope.listOfStories();
		 
		 $scope.$broadcast("onSelectedSprintChange");
	 };
	 
	 /**
	  * On team change drop down.
	  */
	 $scope.onTeamChange = function(teamId){
		 
		 $scope.selectedTeam = $scope.teamIdObMap[teamId];
		 
		 console.log("called " + teamId);
		 
		 var newStories = [];
		 
		 var index;
		 for(index in $scope.clonedStories)
		 {
			var storyObj = $scope.clonedStories[index];
			if((storyObj.teamId == teamId) || (!storyObj.sprintId))
			{
				newStories.push(storyObj);
			}
		 }
		 
		 $scope.arrangeStories(newStories);
	 };
	 
	 /**
	  * On user change drop down.
	  */
	 $scope.onUserChange = function(userId){
		 
		 $scope.selectedUser = $scope.employeeIdObjMap[userId];
		 
	 };
	 
	 
	 /**
	  * Empty all the array for new records to be fetched. 
	  */
	 $scope.clean = function() {
		 $scope.story = [];
		 $scope.inProgress = [];
		 $scope.notStarted = [];
		 $scope.completed = [];
		 $scope.idToStory = {};
	 };
	 
	// after sprint save get it by project id and display in dropdown
	$scope.initKanbanBoard = function(){
		
		console.log("initKanbanBoard");
		
		$('#toggleButtonId').bootstrapToggle();
		
		projectId = $scope.getActiveProjectId();
		$scope.isSprintIsSelected = false;
		$scope.selectedSprintObj = null;
		
		actionHelper.invokeAction("kanbanBoard.readSprintTeamUserByProjectId", null, {"projectId" : projectId}, function(read, response){
			$scope.sprints = read.sprints;
			$scope.teams = read.teams;
			$scope.employees = read.employees;
			
			$scope.clean();
			
			
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}
			
			console.log("After apply...");
			
			if($scope.sprints && $scope.sprints.length > 0)
			{
				$scope.selectedSprintObj = $scope.sprints[0];
				
				// get list of stories by projectId because stories are not yet assigned
				$scope.listOfStories(); 
			}
			
			// map for getting the values easily from ids
			$scope.sprintIdObjMap = {};
			for(index in $scope.sprints)
			{
				var sprintObj = $scope.sprints[index];
				
				$scope.sprintIdObjMap[sprintObj.id] = sprintObj;
			}
			
			// map for getting the values easily from ids
			$scope.teamIdObMap = {};
			for(index in $scope.teams)
			{
				var teamObj = $scope.teams[index];
				
				$scope.teamIdObMap[teamObj.value] = teamObj;
			}
			
			// map for getting the values easily from ids
			$scope.employeeIdObjMap = {};
			for(index in $scope.employees)
			{
				var empObj = $scope.employees[index];
				
				$scope.employeeIdObjMap[empObj.value] = empObj; 
			}
			
			
		}, {"hideInProgress" : true});
	 };	 
	 
	// Get invoked after broad cast on  selection projectId from the drop down. 
	$scope.listOfStories = function(){
		
		projectId = $scope.getActiveProjectId();
		 
		if($scope.selectedSprintObj)
		{
			actionHelper.invokeAction("story.fetchBacklogBysprintAndProjectId", null, 
					 {"projectId" : projectId ,"sprint" : $scope.selectedSprintObj.id}, 
			
				 function(read, response){
					
					$scope.clonedStories = read.model; 
					
					$scope.arrangeStories($scope.clonedStories);
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}	
				}
			, {"hideInProgress" : true});
		}
		 
	}; 

	
	$scope.arrangeStories = function(storyArrForIterat){
		
		$scope.clean();
		
		var index;

		for(index in storyArrForIterat)
		{
			if(storyArrForIterat[index].photo)
			{
				fileId =  storyArrForIterat[index].photo.fileId;	
				storyArrForIterat[index]["photoUrl"] = actionHelper.actionUrl("files.fetch", {"id": fileId});
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
		
	};
	
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {	
		console.log("Kanban Board: Project change event recieved");
		$scope.initKanbanBoard();
	});
	
	
	// DRAG AND DROP METHODS
	
	// on drag method for stories
	$scope.onDragStories = function(event){
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$scope.selectedStoryForDragId = event.target.id;
	};
	
	$scope.onDropBacklog = function(event){
		
		event.preventDefault();

		$scope.handleOnDropEvent(null);
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

		 try
		 {
			 $scope.$apply();
		 }catch(ex)
		 {}
		 
		 
		 if(skipServer)
		 {
			 return;
		 }
		
		actionHelper.invokeAction("story.updateStoryStatus", null, {"id" : dropedStory.id, "status" : dropedStory.status}, $.proxy(function(updateResponse, respConfig) {
			//if update failed
			if(updateResponse.code != 0)
			{
				this.$scope.handleOnDropEvent(this.oldStatus, true);
				return;
			}
			
			this.story.version = updateResponse.version;
			
		}, {"$scope": $scope, "story": dropedStory, "oldStatus" : oldStatus}), {"hideInProgress" : true});
	}
	
	// broad cast method for drag drop of bugs
	$scope.onDropBugBack = function(event){
		event.preventDefault();
		$scope.$broadcast("onDropBugBack");
	};
	
	$scope.onBugDropForOpen = function(event){
		event.preventDefault();
		$scope.$broadcast("onBugDropForOpen");
	};
	
	$scope.onBugDropForSubmit = function(event){
		event.preventDefault();
		$scope.$broadcast("onBugDropForSubmit");
	};
	
	$scope.onBugDropForReported = function(event){
		event.preventDefault();
		$scope.$broadcast("onBugDropForReported");
	};
	
	$scope.onBugDropForClose = function(event){
		event.preventDefault();
		$scope.$broadcast("onBugDropForClose");
	};
	
}]);		