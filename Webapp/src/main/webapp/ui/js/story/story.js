$.application.controller('storyController', ["$scope", "crudController", "utils","modelDefService", 
                                             "validator","$state","actionHelper",
       function($scope, crudController, utils, modelDefService, validator, $state, actionHelper) {
	
	 crudController.extend($scope, {
		"name": "Story",
		"modelName": "StoryModel",
		
		"nameColumn" : "title",
		
		"modelDailogId": "storyDialogId",
		
		"saveAction": "story.save",
		"readAction": "story.read",
		"updateAction": "story.update",
		"deleteAction": "story.delete",
		
		"onHide" : function(){
		
			//$scope.stopInterval();
		},
		
		"onDisplay" : function(model){
			
			$scope.storyForUpdate = model;
			
			$scope.$broadcast("fetchAllStoryNotes");
			
			
			//$scope.initTinyMce();
			
			$scope.message = "";
				
			// logic for adjust height as extension value can be added
			var modelFormElem = angular.element('#modelFormId'); 
			var panelBodyElem = angular.element('#panelBodyId');
			
			var conversationHeight = modelFormElem.height();

			panelBodyElem.css('height', conversationHeight + 'px');
			
			//$scope.getAllProjectMembers();
			
			$scope.selectedTitle = {};
			$scope.titles = [];
				
			//$scope.getAllTitle();
			
			//$scope.getAllAttachment();
		}
		
	});
	
	 
	 $scope.storyViewTab = {active: true, color: "blueBackGround"};
	 $scope.dependencyViewTab = {active: false, color: "greyBackGround"};
	 $scope.priorityViewTab = {active: false, color: "greyBackGround"};
	 
	 
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
		  
		$scope.scrollForFirstTime = true; 
		$scope.dependencyTree = [];
		$scope.dependencyIds = [];
		$scope.deletedIds = [];
		
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
			 			
			 			$scope.$broadcast("sortBacklogsByPriority");
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
	 
	 /**
	  * Adding child and child dependencies
	  */
	 $scope.addChildAndDependencies = function(){
		 
		 //load id to story map
		 for(backlog of $scope.backLogs)
		 {
			 $scope.idToStory[backlog.id] = backlog;
			 $scope.dependencyTree.push({"dependencyStory": backlog, "expanded": false});
			 
			 backlog.childrens = [];
			 backlog.dependencyStories = [];
		 }
		 
		 //load children and dependencies
		 for(var backlog of $scope.backLogs)
		 {
			 //set the hierarchy
			 if(!backlog.parentStoryId)
			 {
				 backlog["indentHierarchy"] = 0;
				 $scope.epicStoryList.push(backlog);
			 }
			 else
			 {
				 var parent = $scope.idToStory[backlog.parentStoryId];
				 backlog["indentHierarchy"] = parent.indentHierarchy + 1;
				 parent.childrens.push(backlog);
			 }
			 
			 //set the type of story
			 $scope.setStoryType(backlog);
			 
			 //set the dependencies
			 if(backlog.dependencies)
			 {
				 for(var dependency of backlog.dependencies)
				 {
					 dependency.mainStory = $scope.idToStory[dependency.mainStoryId];
					 dependency.dependencyStory = $scope.idToStory[dependency.dependencyStoryId];
					 
					 $scope.dependencyIds.push(dependency.dependencyStoryId);
				 }
			 }
				 
		 }
	
		 // scroll down the scrollbar.
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
	 
	/**
	 * Get the symbol for display according to the type of a story.
	 */ 	
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
	
	/**
	 * Set the story type.
	 * 
	 * Parent - EPIC
	 * Immediate child - FEATURE
	 * Rest Story - STORY
	 */
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
	$scope.addSavedBacklog = function(backlogModel, storyIdPriority){
		
		backlogModel.childrens = [];
		
		$scope.backLogs.push(backlogModel);
		$scope.idToStory[backlogModel.id] = backlogModel;
		$scope.dependencyTree.push({"dependencyStory": backlogModel, "expanded": false});
		
		if(!backlogModel.parentStoryId)
		{
			$scope.epicStoryList.push(backlogModel);
			var storyHierarchyElem  = $("#storyHierarchyId");
			storyHierarchyElem.animate({ scrollTop: storyHierarchyElem[0].scrollHeight });
		}else
		{
			var parentStory = $scope.idToStory[backlogModel.parentStoryId]
			var childrens = parentStory.childrens;
			
			if(childrens)
			{
				childrens.push(backlogModel);
			}else
			{
				parentStory.childrens = [backlogModel];
			}
			
			parentStory.isManagementStory = true;
			
			if(storyIdPriority)
			{
				for(key in storyIdPriority)
				{
					$scope.idToStory[key].priority = storyIdPriority[key];
				}
			}
		}
	};
	
	/**
	 * Add dependency story after save.
	 */
	$scope.addDependencyStoryAfterSave = function(depdendencyObj){
		
		$scope.dependencyIds.push(depdendencyObj.id);
		
		var mainStory = $scope.idToStory[depdendencyObj.mainStoryId];
		
		depdendencyObj.mainStory = $scope.idToStory[depdendencyObj.mainStoryId];
		depdendencyObj.dependencyStory = $scope.idToStory[depdendencyObj.dependencyStoryId];
		
		var dpendencyArr = mainStory.dependencies;
		
		if(dpendencyArr)
		{
			dpendencyArr.push(depdendencyObj);
		}else
		{
			mainStory.dependencies = [depdendencyObj];
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
	};
	
		
	/**
	 * Displays bulk story dialog.
	 */
	$scope.openBulkStories = function() {
		
		utils.openModal("bulkStoryDialog", {});
	
		$("#bulkStoryText").val("");
		$scope.bulkStories = [];
		
		try
		{
			$scope.$digest();
		}catch(ex)
		{}
	};
	
	
	/**
	 * Get backlog.
	 */
	$scope.getBacklog = function(backlogId){
		
		return $scope.idToStory[backlogId];
	};
	
	/**
	 * Get selected story for update.
	 */
	$scope.getStoryForUpdate = function(){
		
		return $scope.storyForUpdate;
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
	
	
	// Listener for broadcast
	$scope.$on("editStory", function(event, id) {
		
		$scope.selectedId = id;
		
		$scope.initModelDef();
		
		$scope.editEntry();
	});
	

	/**
	 * Returns the alert message for deleting a story.
	 */
	$scope.getAlertMessage = function(backlogId){
		
		var backlogObj = $scope.idToStory[backlogId];
		var alertMessage = "Are you sure you want to delete story with title - '{}'?";

		if((backlogObj.childrens.length > 0) && ($scope.dependencyIds.indexOf(backlogObj.id) != -1))
		{
			alertMessage = "Are you sure you want to delete all the child and dependency stories of - '{}'?";
		}else if(backlogObj.childrens.length > 0)
		{
			alertMessage = "Are you sure you want to delete all the child stories of - '{}'?";
		}else if($scope.dependencyIds.indexOf(backlogObj.id) != -1)
		{
			alertMessage = "Are you sure you want to delete the dependency story - '{}'?";
		}
		
		return alertMessage;
	};
	
	/**
	 * Update backlogs after updating in database.
	 */
	$scope.updateStoryChanges = function(updatedBacklog){
		
		var oldBacklogObj = $scope.idToStory[updatedBacklog.id];
		
		oldBacklogObj.title = updatedBacklog.title;
		
		if(oldBacklogObj.parentStoryId != updatedBacklog.parentStoryId)
		{
			if(oldBacklogObj.parentStoryId)
			{
				var parent = $scope.idToStory[oldBacklogObj.parentStoryId];
				
				parent.childrens.splice(parent.childrens.indexOf(oldBacklogObj), 1);
			}
			
			if(updatedBacklog.parentStoryId)
			{
				oldBacklogObj.parentStoryId = updatedBacklog.parentStoryId;
				
				var parent = $scope.idToStory[oldBacklogObj.parentStoryId];
				
				if(parent.childrens)
				{
					parent.childrens.push(oldBacklogObj);
				}else
				{
					parent.childrens = [oldBacklogObj];
				}
				
			}else
			{
				oldBacklogObj.parentStoryId = null;
			}
		}
		
		try
		{
			$scope.$digest();
		}catch(ex)
		{}
	};
	
	/**
	 * Remove backlog and childs by recursion  after delete.
	 */
	$scope.removeBacklog = function(backlogId){
		
		$scope.deletedIds.push(backlogId);
		
		var objToBeRemoved = $scope.idToStory[backlogId];
		$scope.backLogs.splice($scope.backLogs.indexOf(objToBeRemoved), 1);
		
		if(!objToBeRemoved.parentStoryId)
		{
			$scope.epicStoryList.splice($scope.epicStoryList.indexOf(objToBeRemoved), 1);
		}else
		{
			var childrens = ($scope.idToStory[objToBeRemoved.parentStoryId]).childrens;
			
			childrens.splice(childrens.indexOf(objToBeRemoved), 1);
			
			if(childrens.length == 0)
			{
				$scope.idToStory[objToBeRemoved.parentStoryId].isManagementStory = false;
			}
		}
		
		if(objToBeRemoved.childrens)
		{
			for(var childObj of objToBeRemoved.childrens)
			{
				$scope.removeBacklog(childObj.id);
			}
		}
		
		try
		{
			$scope.$digest();
		}catch(ex)
		{}
		
	};
	
	/**
	 * Update the dependency tree list.
	 */
	$scope.refreshDependencyTree = function(dpendencyArr){
		
		if(!dpendencyArr)
		{
			dpendencyArr = $scope.dependencyTree;
		}
		
		for(index in dpendencyArr)
		{
			var obj = dpendencyArr[index].dependencyStory;
			
			if($scope.deletedIds.indexOf(obj.id) != -1)
			{
				dpendencyArr.splice(index, 1);
			}
			
			if(obj.dependencies)
			{
				$scope.refreshDependencyTree(obj.dependencies);
			}
		}
		
	};
	
	/**
	 * Update priority.
	 */
	$scope.refreshPriority = function(){
		
		$scope.$broadcast("sortBacklogsByPriority");
	};
	
	/**
	 * Remove Dependency story after delete. 
	 */
	$scope.removeDependencyAfterDelete = function(mainStoryObj, dependencyObj){
		
		mainStoryObj.dependencies.splice(mainStoryObj.dependencies.indexOf(dependencyObj), 1);
		
		try
		{
			$scope.$digest();
		}catch(ex)
		{}
	};
	
	
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


