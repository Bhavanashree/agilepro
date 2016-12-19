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
		
		$scope.idToStory = {};
		$scope.epicStoryList = [];
		$scope.parentIdChildListMap = {};
		$scope.storyIdDependencyListMap = {};
		  
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
			 		
			 		if($scope.backLogs)
			 		{
			 			$scope.addChildAndDependencies();
			 		}
			 			
			 		try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				 },{"hideInProgress" : true}
			 );
		}
	 };
	 
	 
	 $scope.addChildAndDependencies = function(){
		 
		 for(index in $scope.backLogs)
		 {
			var backlogObj  = $scope.backLogs[index];
			 
			$scope.idToStory[backlogObj.id] = backlogObj;
			
			if(!backlogObj.parentStoryId)
			{
				$scope.setStoryType(backlogObj);
				backlogObj["indentHierarchy"] = 0;
				
				$scope.epicStoryList.push(backlogObj);
			}else
			{
				backlogObj["indentHierarchy"] = $scope.idToStory[backlogObj.parentStoryId].indent + 1;
				
				var childArr = $scope.parentIdChildListMap[backlogObj.parentStoryId];
			  	
			  	if((childArr) && (childArr.length > 0))
			  	{
		  			childArr.push(backlogObj);
		  		
			  	}else
			  	{
			  		$scope.parentIdChildListMap[backlogObj.parentStoryId] = [backlogObj];
			  	}

			}
			
			
			if(backlogObj.dependencies)
			{
				for(i in backlogObj.dependencies)
				{
					var dependencyObj = backlogObj.dependencies[i];
					
					var mainStory = $scope.getBacklog(dependencyObj.mainStoryId);
					
					dependencyObj.parentStoryId = mainStory.parentStoryId;
					dependencyObj.title = mainStory.title;
					
					$scope.setStoryType(dependencyObj);
					
					var dependencyArr = $scope.storyIdDependencyListMap[backlogObj.parentStoryId];
				  	
				  	if((dependencyArr) && (dependencyArr.length > 0))
				  	{
				  		dependencyArr.push(dependencyObj);
			  		
				  	}else
				  	{
				  		$scope.storyIdDependencyListMap[backlogObj.id] = [dependencyObj];
				  	}
				}
			}
			
			
		}
		 
		 for(key in $scope.parentIdChildListMap)
		 {
			 ($scope.idToStory[key])["childrens"] = $scope.parentIdChildListMap[key];
		 }
		 
		 for(key in $scope.storyIdDependencyListMap)
		 {
			 ($scope.idToStory[key])["dependencyArr"] = $scope.storyIdDependencyListMap[key];
		 }	
		 
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
			parent = $scope.idToStory[backLog.parentStoryId];
		  
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
	 * Added new backlog after save. 
	 */
	$scope.addSavedBacklog = function(backlogModel){
		
		$scope.epicStoryList.push(backlogModel);
		$scope.backLogs.push(backlogModel);
		
		$scope.idToStory[backlogModel.id] = backlogModel;
		
		var storyHierarchyElem  = $("#storyHierarchyId");
		storyHierarchyElem.animate({ scrollTop: storyHierarchyElem[0].scrollHeight });
	};
	
	/**
	 * After child save.
	 */
	$scope.addSavedChildBacklog = function(backlogModel, storyIdPriority){
		
		$scope.gotParent = false;
		
		$scope.backLogs.push(backlogModel);
		$scope.idToStory[backlogModel.id] = backlogModel;
		
		var tempChildList = $scope.parentIdChildListMap[backlogModel.parentStoryId];
		
		if(tempChildList)
		{
			tempChildList.push(backlogModel);
			
			$scope.parentIdChildListMap[backlogModel.parentStoryId] = tempChildList; 
		}else
		{
			$scope.parentIdChildListMap[backlogModel.parentStoryId] = [backlogModel];
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


