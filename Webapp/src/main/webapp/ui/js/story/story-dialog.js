$.application.controller('storyDialogController', ["$scope", "actionHelper", "utils", "crudController", "modelDefService", 
                                              function($scope, actionHelper, utils, crudController, modelDefService) {
	
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
	
	// Listener for broadcast
	$scope.$on("editStory", function(event, id) {
		
		$scope.selectedId = id;
		
		//$scope.initModelDef();
		$scope.editEntry();
	});
	
	/**
	 * Get selected story for update.
	 */
	$scope.getStoryForUpdate = function(){
		
		return $scope.storyForUpdate;
	};
	
	/**
	 * Gets invoked on click of update button on dailoge.
	 * 
	 * It uses action helper to call the controller for updating the story.
	 */
	$scope.updateBacklog = function(){
		
		actionHelper.invokeAction("story.update", $scope.storyForUpdate, null, 
				
				function(updateResponse, respConfig)
				{
					if(updateResponse.code == 0)
					{
						$('#storyDialogId').modal('hide');
						
						$scope.updateStoryChanges($scope.storyForUpdate);
						
						$scope.refreshPriority();
					}
			
				},{"hideInProgress" : true});
		
	};
	
	
	// Listener for broadcast
	$scope.$on("deleteStory", function(event, backlogId) {

		var backlogObj = $scope.getBacklog(backlogId);
		
		var deleteOp = $.proxy(function(confirmed) {
				
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("story.delete", null, {"id" : backlogId}, 
						function(deleteResponse, respConfig)
						{
							if(deleteResponse.code == 0)
							{
								$scope.removeBacklog(backlogId);
								
								$scope.refreshDependencyTree(null);
								
								$scope.refreshPriority();
								
							}
						}, {"hideInProgress" : true});
			}
			
		}, {"$scope": $scope, "backlogId": backlogId});
		
		utils.confirm([$scope.getAlertMessage(backlogId), backlogObj.title], deleteOp);
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