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
		

		"postSaveOp": function(model, $scope) {
			$scope.listOfSprint();
		}

		});
	 $scope.childStrsToUpdtInDb = [];
	
	 $scope.noSprintIsSelected = true;
	 
	 $scope.isSprintIsSelected = false;
	 
	 $scope.sprintVerId = {};
	 
	 // save new sprint
	 $scope.saveSprint = function() {
		projectId = $scope.getActiveProject();
		
		$scope.model.projectId =  projectId;
		
		$scope.saveChanges();
		
	};
	
	var readSprintByProjectCallBack = function(read, response){
		
		$scope.sprints = read.model;
		
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}	
			
		// get list of stories by projectId because stories are not yet assinged
		$scope.listOfStories(); 
	};
	
	// after sprint save get it by project id and display in dropdown
	$scope.listOfSprint = function(){	
		
		console.log("listofsprint");
		projectId = $scope.getActiveProject();
		
		actionHelper.invokeAction("sprint.sprintProjectId", null, {"projectId" : projectId}, readSprintByProjectCallBack);
	 };
		
	 
	// editSprint 
	$scope.editSprint = function(obj){	
		
		obj = $scope.selectedSprintObj;
		
		$scope.selectedId = obj.id;
		$scope.editEntry(obj);
	};
	
	// call back method for display stories
	var displayStoriesCallBack  = function(read, response)
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
	
	// on change dropdown
	$scope.displayStoriesBySprint = function(selectedSprintObj){
		
		if(!selectedSprintObj)
		{
			 $scope.noSprintIsSelected = true;
			 $scope.isSprintIsSelected = false;
			 $scope.selectedSprintObj = {};
			 return;
		}
		
		$scope.selectedSprintObj = selectedSprintObj;
		
		$scope.inProgress = [];
		 
		$scope.notStarted = [];
		 
		$scope.completed = [];
			
		
		 // invoke backlog 
		actionHelper.invokeAction("story.readStoriesBySprint",null,{"sprintId" : selectedSprintObj.id},displayStoriesCallBack);
				 
	};
	
	 //List of stories
	var readStoryCallBack = function(read, response){
		
		var storyArrForIterat = read.model;
		
		$scope.story = [];
		var tempIndex = 0;
		
		var index;

		for(index in storyArrForIterat)
		{
			if(!storyArrForIterat[index].status)
			{
				if(storyArrForIterat[index].photo)
				{
					fileId =  storyArrForIterat[index].photo.fileId;	
					storyArrForIterat[index]["$photoUrl"] = actionHelper.actionUrl("files.fetch", {"id": fileId});
				}
				
				$scope.story[tempIndex++] = storyArrForIterat[index];
			}	
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}				
	};
	
	// after broad cast from projectId selection 
	$scope.listOfStories =function(){
		
		 projectId = $scope.getActiveProject();
		 
		 actionHelper.invokeAction("story.readAll",null,{"projectId" : projectId},readStoryCallBack);
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {	
		
		console.log("braod cast is working");
		
		// init the drop down so no sprint obj is selected
		 $scope.noSprintIsSelected = true;
		 $scope.isSprintIsSelected = false;
		 $scope.selectedSprintObj = {};
		
		$scope.listOfSprint();
	});
	
	// DRAG AND DROP METHODS
	
	// on drag method for stories
	$scope.onDragStories = function(event){
		
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$scope.selectedStoryForDrag = JSON.parse(event.target.id);
		
		var selectedId = $scope.selectedStoryForDrag.id;
		
		$scope.tempChldstrsDragging = [];
		
		if($scope.selectedStoryForDrag.status)
		{
			switch($scope.selectedStoryForDrag.status)
			{
				case 'IN_PROGRESS':
					for(i = 0 ; i < $scope.inProgress.length ; i++)
					{
						if($scope.inProgress[i].parentStoryId == selectedId)
						{
							$scope.tempChldstrsDragging.push($scope.inProgress[i]);
						}
					}
					break;
				case 'NOT_STARTED':
					for(i = 0 ; i < $scope.notStarted.length ; i++)
					{
						if($scope.notStarted[i].parentStoryId == selectedId)
						{
							$scope.tempChldstrsDragging.push($scope.notStarted[i]);
						}
					}
					break;
				case 'COMPLETED':
					for(i = 0 ; i < $scope.completed.length ; i++)
					{
						if($scope.completed[i].parentStoryId == selectedId)
						{
							$scope.tempChldstrsDragging.push($scope.completed[i]);
						}
					}
			}
		}else
		{
			for(i = 0 ; i < $scope.story.length ; i++)
			{
				if($scope.story[i].parentStoryId == selectedId)
				{
					$scope.tempChldstrsDragging.push($scope.story[i]);
				}
			}
		}
				
	};
	
	
	$scope.allowDropOnNotStarted = function(event){
		console.log("area for allowDropOnNotStarted");
		event.preventDefault();
	};
	
	$scope.onDropofNotStarted = function(event){
		
		console.log("invoked onDropofNotStarted ", $scope.selectedSprintObj);
		event.preventDefault();
		
		$scope.sprintUpdateAfterDrop("NOT_STARTED");	
		
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
	
	// call back forr stry updATE
	var updateEditStoryCallBack = function(updateResponse, respConfig){
		
		$scope.sprintVerId[dropedStoryId] = updateResponse.version;
		
		// if update failed
		if(updateResponse.code != 0)
		{
			$scope.displayStoriesBySprint($scope.selectedSprintObj);
		}
		
	};
	
	//common method for update status and sprintId to stories
	$scope.sprintUpdateAfterDrop = function(status)
	{	
		 dropedStoryId = $scope.selectedStoryForDrag.id;
		 
		 // logic for parent and sub drag
		 
		// logic to remove object from  array
		if($scope.selectedStoryForDrag.status)
		{
			switch($scope.selectedStoryForDrag.status)
			{
				case 'IN_PROGRESS':
					for(i = 0 ; i < $scope.inProgress.length ; i++)
					{
						if($scope.inProgress[i].id == dropedStoryId || $scope.inProgress[i].parentStoryId == dropedStoryId)
						{
							$scope.inProgress.splice(i, 1);
							i = i - 1;
						}
					}
					break;
				case 'NOT_STARTED':
					for(i = 0 ; i < $scope.notStarted.length ; i++)
					{
						if($scope.notStarted[i].id == dropedStoryId || $scope.notStarted[i].parentStoryId == dropedStoryId)
						{
							$scope.notStarted.splice(i, 1);
							i = i - 1;
						}
					}
					break;
				case 'COMPLETED':
					for(i = 0 ; i < $scope.completed.length ; i++)
					{
						if($scope.completed[i].id == dropedStoryId || $scope.completed[i].parentStoryId == dropedStoryId)
						{
							$scope.completed.splice(i, 1);
							i = i - 1;
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
		
		// logic to push update object in array
		switch($scope.selectedStoryForDrag.status)
		{
			case 'NOT_STARTED':
				{
					$scope.notStarted.push($scope.selectedStoryForDrag);
					
					if($scope.tempChldstrsDragging.length > 0)
					{
						pushChildStories($scope.tempChldstrsDragging, status, $scope.selectedSprintObj.id, $scope.notStarted);
					}
				}
			break;
			case 'IN_PROGRESS':
				{
					$scope.inProgress.push($scope.selectedStoryForDrag);
					
					if($scope.tempChldstrsDragging.length > 0)
					{
						pushChildStories($scope.tempChldstrsDragging, status, $scope.selectedSprintObj.id, $scope.inProgress);
					}
				}				
				break;
			case 'COMPLETED':
				{
					$scope.completed.push($scope.selectedStoryForDrag);
					
					if($scope.tempChldstrsDragging.length > 0)
					{
						pushChildStories($scope.tempChldstrsDragging, status, $scope.selectedSprintObj.id, $scope.completed);
					}
				}
		}
		
		
		// logic to remove object from list Of stroies
		for(i = 0 ; i < $scope.story.length ; i++)
		{
			console.log("i = " + i);
			
			if($scope.story[i].id == dropedStoryId || $scope.story[i].parentStoryId == dropedStoryId)
			{
				$scope.story.splice(i, 1);
				i = i - 1;
			}
		}
		
		$scope.$apply();

		console.log("actionhelper ++ " ,  $scope.childStrsToUpdtInDb[i] );
		
		actionHelper.invokeAction("story.update", $scope.selectedStoryForDrag, null, updateEditStoryCallBack);
		
		if($scope.childStrsToUpdtInDb.length > 0)
		{
			for(i = 0; i < $scope.childStrsToUpdtInDb.length ; i++)
			{
				actionHelper.invokeAction("story.update", $scope.childStrsToUpdtInDb[i], null, updateEditStoryCallBack);
			}
			
		}
		// update all the child stoires
		 $scope.childStrsToUpdtInDb = [];
	}
	
	// method to push all the child stories
	pushChildStories = function(arrayFrom, status, sprintId, toArray){
	
		for(i = 0 ; i < arrayFrom.length ; i++)
		{
			arrayFrom[i].status = status;
			arrayFrom[i].sprint = sprintId;

			toArray.push(arrayFrom[i]);
			$scope.childStrsToUpdtInDb.push(arrayFrom[i]);
		}
	};
	
}]);		