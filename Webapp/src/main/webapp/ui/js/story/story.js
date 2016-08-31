$.application.controller('storyController', ["$scope", "crudController", "utils","modelDefService", 
                                             "validator","$state","actionHelper",
       function($scope, crudController,utils, modelDefService, validator,$state,actionHelper) {
	
	 crudController.extend($scope, {
		"name": "Story",
		"modelName": "StoryModel",
		
		"nameColumn" : "title",
		
		"modelDailogId": "storyDialog",
		
		"saveAction": "story.save",
		"readAction": "story.read",
		"updateAction": "story.update",
		"deleteAction": "story.delete",
		
		"onDisplay" : function(model, containerHeight){
			
			$scope.message = "";
			
			var modelFormElem = angular.element('#modelFormId'); 
			
			var panelBodyElem = angular.element('#panelBodyId');
			
			var conversationHeight = modelFormElem.height() - 100;

			panelBodyElem.css('height', conversationHeight + 'px');
			
			storyId = model.id;
			
			getAllTitle();
		}

	});
	 
	 
	 $scope.dlgModeField = "newStoryMode";
	 
	 $scope.handleKeyPress = function(e) {
		 console.log("handleKeyPress is invoked " + $scope.inlineTitle);
		 e = e || window.event;
		 var key = e.keyCode ? e.keyCode : e.which;
			  
		 //enter key   
		 if (key == 13) 
		 {   
		   $scope.saveBacklog();
		 }
	};

	$scope.handleKeyForSubtitle = function(e) {
			 
		var element = $(e.target);
		console.log("handlekey press 2:   from save substories->  " +  element.val());
			 
		e = e || window.event;
		var key = e.keyCode ? e.keyCode : e.which;
			   
		//enter key   
		if (key == 13) 
		{  
			$scope.saveSubstory(element.val());
		} 
	};
			
		 
	$scope.isDisplayElement = false;
	$scope.newModelMode = true;
	$scope.storyModel = {};
	$scope.model = {};
	
	var storyId;
	var projectId;
		
	//set title to model
	$scope.saveBacklog= function(e){
			
		console.log("inlinetext:     " + $scope.inlineTitle );
		
		projectId = $scope.getActiveProject();
		
		$scope.model = {"title" : $scope.inlineTitle.trim(),"projectId" : projectId};
			
		console.log("model is invoked===model====" + projectId); 

		console.log("model is invoked===model====" + $scope.model); 
			
		$scope.initErrors("model", true);
			
		$scope.newModelMode= 'Save';
			
		$scope.saveChanges();
			
		document.getElementById('parentStorytextarea').value=null;
		$scope.refreshSearch();
	};

	//autorefresh
	$scope.refreshSearch = function(){
		$scope.$broadcast("invokeSearch", {});
	};
		
		
	$scope.saveSubstory= function(title){
		
		$scope.model = {"title" : title, "parentStoryId": $scope.selectedId, "projectId" : projectId};

		$scope.newModelMode= 'Save';
		$scope.saveChanges();
		$scope.refreshSearch();
	};
	
		    	
	  $scope.getActiveIndex = function(){
		  return $scope.selectedIndex;
	  };
		  
	//
	var updatefetchStoriesByProjectId = function(re, res){
		console.log("updateStoryByProjectId");
		var model = re.model;
		console.log("model1--", re.model);
		
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}				
	};
			
	//
	$scope.fetchStoriesByProjectId = function(){
		
		projectId = $scope.getActiveProject();

		console.log("PROJECT ID = " + projectId);
		if(!projectId) return;
		
		console.log("storyById");

		//actionHelper.invokeAction("story.readAll", null, null, updatefetchStoriesByProjectId);
		actionHelper.invokeAction("story.storyProjectId", null, {"projectId" : projectId}, updatefetchStoriesByProjectId);				
	};
			
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		$scope.fetchStoriesByProjectId();
	   
	});
			
	 $scope.onType = function(e) {

		$scope.message = $("#messageId").val();
		
		e = e || window.event;
		var key = e.keyCode ? e.keyCode : e.which;
		
		
		if($scope.message)
		{
			if($scope.message.length > 50)
			{
				$("#messageId").css('height', 4 + 'em');
				$("#sendButtonId").css('height', 4 + 'em');
			}
			else if($scope.message.length < 50)
			{
				reduceHeight();
			}
		}
		else
		{
			reduceHeight();
		}
		
		// if enter
		if((key == 13) && $scope.message)
		{
			$scope.saveConversationMessage();
		}
		else
		{
			return;
		}
	 };
	 
	 readCallBack = function(readResponse, respConfig){
		 
		 $scope.conversations = readResponse.model;
		 reduceHeight();
		 
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}	
	 };
	 
	 getAllConversation = function(){
		 
		 actionHelper.invokeAction("conversationMessage.readAll", null, {"conversationTitleId" : $scope.selectedTitle.id}, readCallBack);
	 };
	 
	 saveConverCallBack = function(readResponse, respConfig){
		 
		 if(respConfig.success)
		 {
			 $("#messageId").val("");
			 $scope.message = "";
			 $("#messageId").focus();
			 
			 getAllConversation();
		 }
	 };
	 
	 $scope.saveConversationMessage = function(){
		
		$scope.message = $("#messageId").val();
		 
		if($scope.message)
		{
			if($scope.message.length > 0 && $scope.selectedTitle)
			{
				var model = {"message" : $scope.message.trim(), "conversationTitleId" : $scope.selectedTitle.id,
						"userId" : $scope.activeUser.userId};
				 
				actionHelper.invokeAction("conversationMessage.save", model, null, saveConverCallBack);
			}
			else
			{
				utils.alert("Please select a title");
				return;
			}
		}
	 };
	 
	// Reduce height of input field conversation
	reduceHeight = function(){
		
		$("#messageId").css('height', 2.5 + 'em');
		$("#sendButtonId").css('height', 2.5 + 'em');
	};
	
	$scope.initModelDef = function() {
		modelDefService.getModelDef("StoryModel", $.proxy(function(modelDefResp){
			this.$scope.modelDef = modelDefResp.modelDef;
		}, {"$scope": $scope}));
		
		modelDefService.getModelDef("ConversationTitleModel", $.proxy(function(modelDefResp){
			this.$scope.titleModelDef = modelDefResp.modelDef;
		}, {"$scope": $scope}));
	};
	
	$scope.addTitle = function(){
		 
		 $scope.converTitleModel = {};
		 
		 utils.openModal('conversationTitleModelDialog');
		 
		 console.log("add the title");
	 };
	
	 readTitleCallBack = function(readResponse, respConfig){

		 $scope.titles = readResponse.model;
		 
		 try
		{
			 $scope.$apply();
		}catch(ex)
		{}	
		
		// getAllConversation();
	 };
	 
	 getAllTitle = function(){
		 
		 actionHelper.invokeAction("conversationTitle.readAll", null, {"storyId" : storyId}, readTitleCallBack);
	 };
	 
	 saveTitleCallBack = function(readResponse, respConfig){
		 
		 $('#conversationTitleModelDialog').modal('hide');
		 
		 getAllTitle();
	 };
	 
	$scope.saveTitle = function(e){
		
		$scope.initErrors("converTitleModel", false);

		$scope.converTitleModel.storyId = storyId;
		
		$scope.converTitleModel.ownerId = $scope.activeUser.userId;
		
		if(!validator.validateModel($scope.converTitleModel, $scope.titleModelDef, $scope.errors.converTitleModel))
		{
			utils.alert("Please correct the errors and then try!", function(){
				$('body').addClass('modal-open');
			});
			
			return;
		}
		
		actionHelper.invokeAction("conversationTitle.save", $scope.converTitleModel, null, saveTitleCallBack);
	};
	
	$scope.titleSelectionChanged = function(selectedTitle){
	
		$scope.selectedTitle = selectedTitle;
		
		if(!$scope.selectedTitle)
		{
			$scope.conversations = {};
			return;
		}
		
		getAllConversation();
	};
	
}]);


