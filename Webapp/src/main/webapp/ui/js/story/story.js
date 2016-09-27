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
		
		"onHide" : function(){
		
			stopInterval();
		},
		
		"onDisplay" : function(model){
			$scope.init();
			
			if(!(model.id))
			{
				$scope.conversationTab = false;
			}
			else
			{
				$scope.message = "";
				$scope.conversationTab = true;
				$scope.attachmentTab = true;
				
				var modelFormElem = angular.element('#modelFormId'); 
				
				var panelBodyElem = angular.element('#panelBodyId');
				
				var conversationHeight = modelFormElem.height() - 100;

				panelBodyElem.css('height', conversationHeight + 'px');
				
				$scope.storyId = model.id;
				
				getAllTitle();
				
				getAllAttachment();
			}
			
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}	
		},
		
		"deleteOp" : function(confirmed)
		{
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				$scope.deleteEntry(object);
			}
		}
		
	});
	 
	 
	 $scope.dlgModeField = "newStoryMode";

	 $scope.init = function() {
		tinymce.init({
		    "selector": '#messageId',
		    "plugins": "autolink link emoticons  textcolor",
		    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist",
		    "menubar": false
		});
	};
	
	$scope.submitContent = function() {
		var content = tinymce.activeEditor.getContent();
		$scope.message = content; 
		
		console.log("Got message as ", $scope.message);
		
		$scope.saveConversationMessage();
	};

	 
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
	$scope.storyModel = {};
	$scope.model = {};
	
	$scope.isFirstRequest = true;
	
	var storyId;
	var projectId;
		
	//set title to model
	$scope.saveBacklog= function(e){
			
		console.log("inlinetext:     " + $scope.inlineTitle );
		
		projectId = $scope.getActiveProject();
		
		if(!projectId)
		{
			utils.alert("Please Select Project");
			return;
		}
		
		$scope.model = {"title" : $scope.inlineTitle.trim(),"projectId" : projectId};
		
		$scope.initErrors("model", true);
			
		$scope.newStoryMode= 'true';
			
		$scope.saveChanges();
		$("#parentStorytextarea").val(null);
		$scope.refreshSearch();
	};

	//autorefresh
	$scope.refreshSearch = function(){
		$scope.$broadcast("invokeSearch", {});
	};
		
		
	$scope.saveSubstory= function(title){
		
		projectId = $scope.getActiveProject();
		
		$scope.model = {"title" : title, "parentStoryId": $scope.selectedId, "projectId" : projectId};

		$scope.initErrors("model", true);
		$scope.newStoryMode= 'true';
		
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
			
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		$scope.$broadcast("rowsModified");
	});
			
	 $scope.onType = function(e) {

		if(!$scope.selectedTitle)
		{
			utils.alert("Please select a title");
			//$("#messageId").val($("#messageId").val("").trim());
			return;
		}
		
		$scope.message = $("#messageId").val();
		
		e = e || window.event;
		var key = e.keyCode ? e.keyCode : e.which;
		
		/*
		if($scope.message)
		{
			if($scope.message.length > 50)
			{
				console.log("$scope.message.length = " + $scope.message.length); 
				console.log("$(#messageId).width() = " + $("#messageId").width());
				
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
		*/
		
		// if enter key is pressed
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
		 
		 var panelBodyElem = $("#panelBodyId");
		 
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}	
		
		if($scope.saveSuccess || $scope.onTitleSelectionChange || ($scope.prvsScrlHeight <=  panelBodyElem.scrollTop()))
		{
			$scope.saveSuccess = false;
			$scope.onTitleSelectionChange = false;
			$scope.prvsScrlHeight =  panelBodyElem.scrollTop();
			
			panelBodyElem.animate({ scrollTop: panelBodyElem[0].scrollHeight });
			
			$scope.prvsScrlHeight =  panelBodyElem.scrollTop();
			
			console.log("$scope.prvsScrlHeight =" + $scope.prvsScrlHeight);
			console.log("panelBodyElem.scrollTop() = " + panelBodyElem.scrollTop());
		}
		
		// for getting new saved title
		getAllTitle();
	 };
	 
	 getAllConversation = function(){
		 
		 actionHelper.invokeAction("conversationMessage.readAll", null, {"conversationTitleId" : $scope.selectedTitle.id}, readCallBack, true);
		 
		 if($scope.isFirstRequest)
		 {
			 $scope.intervalValue = setInterval(getAllConversation, (5 * 1000));
			 
			 $scope.isFirstRequest = false;
		 }
		 
	 };
	 
	 stopInterval = function(){
		 clearInterval($scope.intervalValue); 
	 };
	 
	 saveConverCallBack = function(readResponse, respConfig){
		 
		 if(respConfig.success)
		 {
			 $scope.message = "";
			 $("#messageId").focus();
			 
			 $scope.saveSuccess = true;
			 
			 getAllConversation();
		 }
	 };
	 
	 $scope.saveConversationMessage = function(){
		
		$scope.message = $("#messageId").val();
		$("#messageId").val(""); 
		
		if($scope.message)
		{
			if($scope.message.length > 0)
			{
				var model = {"message" : $scope.message.trim(), "conversationTitleId" : $scope.selectedTitle.id,
						"userId" : $scope.activeUser.userId};
				 
				actionHelper.invokeAction("conversationMessage.save", model, null, saveConverCallBack, true);
			}
		}
	 };
	 
	 /*
	// Reduce height of input field conversation
	reduceHeight = function(){
		
		$("#messageId").css('height', 2.5 + 'em');
		$("#sendButtonId").css('height', 2.5 + 'em');
	};
	*/
	
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
	
	$scope.addTitle = function(){
		 
		 $scope.converTitleModel = {};
		 $scope.initErrors("converTitleModel", true);
		 
		 console.log("add the title");
	 };
	
	 readTitleCallBack = function(readResponse, respConfig){

		 $scope.titles = readResponse.model;
		 
		// $scope.selectedTitle = ;
		 
		 console.log($scope.titles);
		 
		 try
		{
			 $scope.$apply();
		}catch(ex)
		{}	
		
		// getAllConversation();
	 };
	 
	 getAllTitle = function(){
		 
		 console.log("line number 391 = " +  $scope.storyId);
		 
		 actionHelper.invokeAction("conversationTitle.readAll", null, {"storyId" : storyId}, readTitleCallBack, true);
	 };
	 
	 saveTitleCallBack = function(readResponse, respConfig){
		 
		 $('#conversationTitleModal').modal('hide');
		 
		 getAllTitle();
	 };
	 
	$scope.saveTitle = function(e){
		
		console.log($scope.model);
		
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
		
		// variable used for scroll down
		$scope.onTitleSelectionChange = true;
		
		if(!$scope.selectedTitle)
		{
			$scope.conversations = {};
			stopInterval();
			return;
		}
		
		getAllConversation();
	};
	
	
	// Attachment
	$scope.addAttachment = function(){
		
		$scope.newModelMode = true;
		$scope.storyAttachmentModel = {};
		$scope.initErrors("storyAttachmentModel", true);
		 
		utils.openModal('storyAttachmentModelDialog');
	};
	
	saveAttachmentCallBack = function(readResponse, respConfig){
		 
		 $('#storyAttachmentModelDialog').modal('hide');
		 
		 getAllAttachment();
	 };
	 
	$scope.saveAttachment = function(e){
		
		$scope.initErrors("storyAttachmentModel", false);
		
		$scope.storyAttachmentModel.storyId = storyId;
		
		if(!validator.validateModel($scope.storyAttachmentModel, $scope.attachmentModelDef, $scope.errors.storyAttachmentModel))
		{
			utils.alert("Please correct the errors and then try!", function(){
				$('body').addClass('modal-open');
			});
			
			return;
		}
		
		if(!$scope.storyAttachmentModel.file && !$scope.storyAttachmentModel.link)
		{
			utils.alert("Please provide at least one value for link or file or both");
			return;
		}
		
		actionHelper.invokeAction("storyAttachment.save", $scope.storyAttachmentModel, null, saveAttachmentCallBack);
	};
	
	readAttachmentCallBack = function(readResponse, respConfig){
		 
		$scope.attachments = readResponse.model;
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}	
	 };
	 
	getAllAttachment = function(){
		
		actionHelper.invokeAction("storyAttachment.readAll", null, {"storyId" : storyId}, readAttachmentCallBack);
	};
	
	$scope.editAttachment = function(obj){
		
		$scope.initErrors("storyAttachmentModel", false);
		
		$scope.newModelMode = false;
		
		$scope.storyAttachmentModel = $.extend(true, {}, obj);
		
		utils.openModal('storyAttachmentModelDialog');
	};
	
	updateAttachmentCallBack = function(readResponse, respConfig){
		 $('#storyAttachmentModelDialog').modal('hide');
		 
		getAllAttachment();
	};
	
	$scope.updateAttachment = function(e){
		
		if(!$scope.storyAttachmentModel.file && !$scope.storyAttachmentModel.link)
		{
			utils.alert("Please provide at least one value for link or file or both");
			return;
		}
		
		actionHelper.invokeAction("storyAttachment.update", $scope.storyAttachmentModel, null, updateAttachmentCallBack);
	};
	
	deleteAttachmentCallBack = function(readResponse, respConfig){
		 
		getAllAttachment();
	};
	
	$scope.removeAttachment = function(obj){
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("storyAttachment.delete", null, {"id" : obj.id}, deleteAttachmentCallBack);
			}
			
			try
			{
				this.$scope.$parent.$digest();
			}catch(ex)
			{}
			
		}, {"$scope": $scope});

		utils.confirm(["Are you sure you want to delete attachment with name '{}' ?", obj.title], deleteOp);
	};
	
	$scope.customizeSearchQuery = function(searchQuery) {
		searchQuery.projectId = $scope.getActiveProject();
	};
	
	
	$scope.deleteTask = function(object){
		
		$scope.selectedId = object.id;
		console.log("$scope.selectedId  -----",$scope.selectedId );
		
		$scope.deleteEntry(object);
		
	};
	
	/**
	 * Displays bulk story dialog.
	 */
	$scope.openBulkStories = function() {
		utils.openModal("bulkStoryDialog", {
		});
		
	};
	
}]);


