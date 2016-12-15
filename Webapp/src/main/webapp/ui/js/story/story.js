$.application.controller('storyController', ["$scope", "crudController", "utils","modelDefService", 
                                             "validator","$state","actionHelper",
       function($scope, crudController, utils, modelDefService, validator, $state, actionHelper) {
	
	 crudController.extend($scope, {
		"name": "Story",
		"modelName": "StoryModel",
		
		"nameColumn" : "title",
		
		"modelDailogId": "storyDialog",
		
		"saveAction": "story.save",
		"readAction": "story.read",
		"updateAction": "story.update",
		"deleteAction": "story.delete",
		
		"onHide" : function(){
		
			$scope.stopInterval();
		},
		
		"onDisplay" : function(model){
			
			$scope.initTinyMce();
			
			if(!(model.id))
			{
				$scope.conversationTab = false;
			}
			else
			{
				$scope.message = "";
				$scope.conversationTab = true;
				$scope.attachmentTab = true;
				
				// logic for adjust height as extension value can be added
				var modelFormElem = angular.element('#modelFormId'); 
				
				var panelBodyElem = angular.element('#panelBodyId');
				
				var conversationHeight = modelFormElem.height();

				panelBodyElem.css('height', conversationHeight + 'px');
				
				$scope.storyId = model.id;

				$scope.getAllProjectMembers();
				
				$scope.selectedTitle = {};
				$scope.titles = [];
				
				$scope.getAllTitle();
				
				$scope.getAllAttachment();
			}
			
			// Broad cast 
	    	$scope.$broadcast("fetchAllStoryNotes");
		}
		
	});
	
	 
	 $scope.storyViewTab = {active: true, color: "blueBackGround"};
	 $scope.dependencyViewTab = {active: false, color: "greyBackGround"};
	 $scope.priorityViewTab = {active: false, color: "greyBackGround"};
	 $scope.finalResult = [];
	 $scope.scrollForFirstTime = true; 
	 
 	/**
	 * Set the active tab.
	 */
	$scope.setActiveTab = function(tabName){
		
		switch(tabName)
		{
			case "STORY_VIEW":
			{
				$scope.storyViewTab = {active: true, color: "blueBackGround"};
				$scope.dependencyViewTab = {active: false, color: "greyBackGround"};
				$scope.priorityViewTab = {active: false, color: "greyBackGround"};
				break;
			}
			case "DEPENDENCY_VIEW":
			{
				$scope.storyViewTab = {active: false, color: "greyBackGround"};
				$scope.dependencyViewTab = {active: true, color: "blueBackGround"};
				$scope.priorityViewTab = {active: false, color: "greyBackGround"};
				break;
			}
			case "PRIORITY_VIEW":
			{
				$scope.storyViewTab = {active: false, color: "greyBackGround"};
				$scope.dependencyViewTab = {active: false, color: "greyBackGround"};
				$scope.priorityViewTab = {active: true, color: "blueBackGround"};
			}
		}
		
	};
	
	
	 /**
	  * Fetch back logs according to the project id. 
	  */
	 $scope.initStory = function(){
		 
		console.log("init story is called");
		
		$scope.idToBacklogItem = {};
		$scope.hierarchyList = [];
		$scope.epicStoryList = [];
		$scope.childStories = [];
		$scope.parentIdChildListMap = {};
		$scope.indentPosition = 0;
		  
		// This array will be assigned to final result after recursion so that the scroll 
		// bar will be not affected for child save.  
		$scope.backlogsForRecursion = [];
		
		var activeProjectId = $scope.getActiveProjectId()
		 
		if(activeProjectId != -1)
		{
			 actionHelper.invokeAction("story.fetchBacklogsByProjectId", null, {"projectId" : activeProjectId},
				 function(readResponse, respConfig)
				 {
			 		$scope.backLogs = readResponse.model;
			 		console.log("working");
			 		
			 		if($scope.backLogs)
			 		{
			 			$scope.loadBacklogItems($scope.backLogs);
			 		}
			 			
			 		try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
					$scope.$broadcast("storiesPopulated");
			 
				 },{"hideInProgress" : true}
			 );
		}
	 };
	 
	 /**
	  * Sorting the list of epic stories and calling the recursion method.
	  */
	 $scope.loadBacklogItems = function(backlogItems) {
		  
		  var backlog;
		  for(var index = 0; index < backlogItems.length; index++)
			{
			  backlog = backlogItems[index];
			  
			  $scope.idToBacklogItem[backlog.id] = backlog;
			  
			  if(backlog.parentStoryId)
			  {
				  $scope.childStories.push(backlog);
			  }else
			  {
				  $scope.epicStoryList.push(backlog);
			  }
			}
		  
		  // sorting
		  if($scope.epicStoryList.length > 0)
			{
			  $scope.epicStoryList.sort(function(a, b){return a.id-b.id});
			}
		  
		  // get the map of child stories
		  var childBackLog;
		  for(var index = 0; index < $scope.childStories.length; index++)
			{
			  	childBackLog = $scope.childStories[index];
			  	
			  	var childArr = $scope.parentIdChildListMap[childBackLog.parentStoryId];
			  	
			  	if(childArr)
				  	{
			  			childArr.push(childBackLog);
			  		
				  	}else
				  	{
				  		$scope.parentIdChildListMap[childBackLog.parentStoryId] = [childBackLog];
				  	}
			}
		  
		  $scope.addBackLogsAccordingToChild($scope.epicStoryList, 0);
		  
		 if($scope.scrollForFirstTime)
		 {
			var storyHierarchyElem  = $("#storyHierarchyId");
			
			try
			{
				storyHierarchyElem.animate({ scrollTop: storyHierarchyElem[0].scrollHeight + 10000 });
			}catch(ex)
			{}
			
			
			$scope.scrollForFirstTime = false;
		 }
		
		$scope.finalResult = $scope.backlogsForRecursion;
	};
	
	$scope.getSymbolFor = function(backlogItem) {
		if(backlogItem.type == "EPIC")
		{
			return "E";
		}
		else if(backlogItem.type == "FEATURE")
		{
			return "F";
		}
		else
		{
			return "S";
		}
	};
	
	$scope.setStoryType = function(backLog) {
		if(!backLog.parentStoryId)
		{
			backLog.type = "EPIC";
		}
		else 
		{
			parent = $scope.idToBacklogItem[backLog.parentStoryId];
		  
			if(parent.type == 'EPIC')
			{
				backLog.type = "FEATURE";
			}
			else
			{
				backLog.type = "STORY";
			}
		}
	};
	 
	/**
	 * Recursion wise adding the stories.
	 */
	$scope.addBackLogsAccordingToChild = function(backLogArr, indentValue){
		
		 var backLog, parent;
		 
		 for(var i = 0; i < backLogArr.length; i++)
		  {
			  backLog = backLogArr[i];
			  backLog["indent"] = indentValue;
			  
			  $scope.setStoryType(backLog);
			  
			  $scope.backlogsForRecursion.push(backLog);
			  
			  if($scope.parentIdChildListMap[backLog.id])
			  {
				  console.log("recursion");
				  $scope.addBackLogsAccordingToChild($scope.parentIdChildListMap[backLog.id], indentValue + 1);
			  }
		  }
	};
	
	
	/**
	 * Added new backlog after save. 
	 */
	$scope.addSavedBacklog = function(backlogModel){
		
		$scope.finalResult.push(backlogModel);
		$scope.epicStoryList.push(backlogModel);
		$scope.backLogs.push(backlogModel);
		
		$scope.idToBacklogItem[backlogModel.id] = backlogModel;
		
		var storyHierarchyElem  = $("#storyHierarchyId");
		storyHierarchyElem.animate({ scrollTop: storyHierarchyElem[0].scrollHeight });
	};
	
	/**
	 * After child save.
	 */
	$scope.addSavedChildBacklog = function(backlogModel, storyIdPriority){
		
		$scope.gotParent = false;
		
		$scope.backLogs.push(backlogModel);
		$scope.childStories.push(backlogModel);
		$scope.idToBacklogItem[backlogModel.id] = backlogModel;
		
		var tempChildList = $scope.parentIdChildListMap[backlogModel.parentStoryId];
		
		if(tempChildList)
		{
			tempChildList.push(backlogModel);
			$scope.parentIdChildListMap[backlogModel.parentStoryId] = tempChildList; 
		}else
		{
			$scope.parentIdChildListMap[backlogModel.parentStoryId] = [backlogModel];
		}
		
		for(var index = 0; index < $scope.finalResult.length ; index++)
		{
			var backlogObj = $scope.finalResult[index];
			
			// adding updated priorty.
			if(storyIdPriority[backlogObj.id])
			{
				backlogObj.priority = storyIdPriority[backlogObj.id]; 
			}
			
			if(backlogObj.id == backlogModel.parentStoryId)
			{
				 $scope.gotParent = true; 
				 continue;
			}

			if($scope.gotParent)
			{
				if(backlogObj.parentStoryId == backlogModel.parentStoryId)
				{
					continue;
				}else
				{
					$scope.finalResult.splice(index, 0, backlogModel);
				}
			}
		}
		
		// if the parent is not having any child or if there are no more parent stories
		if($scope.finalResult.indexOf(backlogModel) == -1)
		{
			$scope.finalResult.push(backlogModel);
		}
		
	};
	
	
	/**
	 * Return child list.
	 */
	$scope.getChildList = function(parentId){
		
		return $scope.parentIdChildListMap[parentId];
	};
	
	
	/**
	 * Displays bulk story dialog.
	 */
	$scope.openBulkStories = function() {
		utils.openModal("bulkStoryDialog", {});
		
	};
	
	/**
	 * Get backlog.
	 */
	$scope.getBacklog = function(backlogId){
		
		return $scope.idToBacklogItem[backlogId];
	};
	
	/**
	 * Get the list of back log items.
	 */
	$scope.getBackLogs = function(){
		
		return $scope.backLogs;
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.scrollForFirstTime = true;
		$scope.initStory();
	});
	
	
	$scope.initModelDef = function() {
		modelDefService.getModelDef("StoryModel", $.proxy(function(modelDefResp){
			this.$scope.modelDef = modelDefResp.modelDef;
			
			console.log("Story Model");
		}, {"$scope": $scope}));
		
		modelDefService.getModelDef("ConversationTitleModel", $.proxy(function(modelDefResp){
			this.$scope.titleModelDef = modelDefResp.modelDef;
			
			console.log("Title model");
		}, {"$scope": $scope}));
		
		modelDefService.getModelDef("StoryAttachmentModel", $.proxy(function(modelDefResp){
			this.$scope.attachmentModelDef = modelDefResp.modelDef;
			
			console.log("Attachment model");
		}, {"$scope": $scope}));
	};
	
	$scope.getModelDef = function(prefix) {
		if(prefix == 'converTitleModel')
		{
			return $scope.titleModelDef;
		}
		else if(prefix == 'storyAttachmentModel')
		{
			return $scope.attachmentModelDef;
		}
		
		return $scope.modelDef;
	};
	
	
}]);


