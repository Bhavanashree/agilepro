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
		
			$scope.stopInterval();
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
			
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}	
		}
		
	});
	 
	 $scope.getAllProjectMembers = function(){
		 actionHelper.invokeAction("projectMember.readProjectMembersByProjectId", null, 
				 {"projectId" : $scope.getActiveProjectId()}, 
				 function(readResponse, respConfig)
				 {
					 if(readResponse.model.length > 0)
					 {
						 $scope.projectMembers = readResponse.model;
					 }else
					 {
						 $scope.projectMembers = [];
					 }
				 }
		, {"hideInProgress" : true});
	 };
	 
	 
	 $scope.dlgModeField = "newStoryMode";

	 $scope.init = function() {
		 console.log("init is invoked");
		 
		 var mceContext = {
		    "selector": '#messageId',
		    "plugins": "autolink link emoticons  textcolor mention",
		    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist",
		    "menubar": false,
		    "content_css" : "/ui/css/conversations.css",
		    "setup" : function(ed) {
		    	ed.on('keyup', function (e) {  
		    		$scope.onType(e);  
		        });
		     },
		 };
		 
		 mceContext.mentions = {
	    	delimiter: ['@'],
	        
	    	"insert": $.proxy(function(item) {
	            return '&nbsp;<span class="userMention" id="' + item.id + '">' + this.currentDelimiter + item.name + '</span>&nbsp;';
	        }, mceContext),
	        
	        source: $.proxy(function (query, process, delimiter) {
	        	
		        this.currentDelimiter = delimiter;
		        return $scope.projectMembers;
			}, mceContext)
	    };
 	
		 
		tinymce.init(mceContext);
	};
	
	
	$scope.submitContent = function() {

		// if user clicks in send button without selecting a title
		$scope.onType(null);

		var content = tinymce.activeEditor.getContent();
		$scope.message = content; 
		
		console.log("Got message as ", $scope.message);
		console.log("Action users are: ", $scope.extractActionUsers($scope.message))
		
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

	// attribute for getting conversations after time interval
	$scope.isFirstRequest = true;
	
	var projectId;
		
	//set title to model
	$scope.saveBacklog= function(e){
			
		console.log("inlinetext:     " + $scope.inlineTitle );
		
		projectId = $scope.getActiveProjectId();
		
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
		
		projectId = $scope.getActiveProjectId();
		
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

		console.log("on type is invoked");
		
		if(!$scope.selectedTitle.id)
		{
			utils.alert("Please select a title");
			return;
		}
		
		$scope.message = tinymce.activeEditor.getContent();
	
		if(e)
		{
			e = e || window.event;
			var key = e.keyCode ? e.keyCode : e.which;
			
			console.log(e.ctrlKey);
			/*// if enter key is pressed
			if((key == 13) && $scope.message.length > 0)
			{
				$scope.saveConversationMessage();
			}
			else
			{
				return;
			}*/
		}
		
	 };
	 
	 $scope.readCallBack = function(readResponse, respConfig){
		 
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
		$scope.getAllTitle();
	 };
	 
	 $scope.getAllConversation = function(){
		 
		 actionHelper.invokeAction("conversationMessage.readAll", null, {"conversationTitleId" : $scope.selectedTitle.id}, $scope.readCallBack, {"hideInProgress" : true});
		 
		 if($scope.isFirstRequest)
		 {
			console.log("interval is set");
			$scope.intervalValue = setInterval($scope.getAllConversation, ($.appConfiguration.conversationRefreshInterval));
			 
			 $scope.isFirstRequest = false;
		 }
		 
	 };
	 
	 $scope.stopInterval = function(){
		 clearInterval($scope.intervalValue); 
	 };
	 
	 $scope.saveConverCallBack = function(readResponse, respConfig){
		 
		 if(respConfig.success)
		 {
			 $scope.message = "";
			 tinymce.activeEditor.setContent("");
			 $("#messageId").focus();
			 
			 $scope.saveSuccess = true;
			 
			 // after save user can see the message in conversation box
			 actionHelper.invokeAction("conversationMessage.readAll", null, {"conversationTitleId" : $scope.selectedTitle.id}, $scope.readCallBack, {"hideInProgress" : true});
		 }
	 };
	 
	 $scope.saveConversationMessage = function(){
		 
		if($scope.message)
		{
			if($scope.message.length > 0)
			{
				var model = {"message" : $scope.message.trim(), "conversationTitleId" : $scope.selectedTitle.id,
						"userId" : $scope.activeUser.userId,"projectMemberIds" : $scope.extractActionUsers($scope.message)};
				 
				actionHelper.invokeAction("conversationMessage.save", model, null, $scope.saveConverCallBack, {"hideInProgress" : true});
			}
		}
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
	
	$scope.addTitle = function(){
		 
		 $scope.converTitleModel = {};
		 $scope.initErrors("converTitleModel", true);
		 
		 console.log("add the title");
	 };
	 
	 
	 $scope.getAllTitle = function(){
		 
		 console.log("line number 391 = " +  $scope.storyId);
		 
		 actionHelper.invokeAction("conversationTitle.readAll", null, {"storyId" : $scope.storyId}, 
			function(readResponse, respConfig){
			 
			 if(readResponse.model.length > 0)
			{
				 $scope.titles =  readResponse.model;
			}else
			{
				$scope.titles = [];
			}
			 
			try
			{
				 $scope.$apply();
			}catch(ex)
			{}	
			
			// getAllConversation();
		 }, {"hideInProgress" : true});
	 };
	 
	 $scope.saveTitleCallBack = function(readResponse, respConfig){
		 
		 $('#conversationTitleModal').modal('hide');
		 
		 $scope.getAllTitle();
	 };
	 
	$scope.saveTitle = function(e){
		
		console.log($scope.model);
		
		$scope.initErrors("converTitleModel", false);

		$scope.converTitleModel.storyId = $scope.storyId;
		
		$scope.converTitleModel.ownerId = $scope.activeUser.userId;
		
		if(!validator.validateModel($scope.converTitleModel, $scope.titleModelDef, $scope.errors.converTitleModel))
		{
			utils.alert("Please correct the errors and then try!", function(){
				$('body').addClass('modal-open');
			});
			
			return;
		}
		
		actionHelper.invokeAction("conversationTitle.save", $scope.converTitleModel, null, $scope.saveTitleCallBack, {"hideInProgress" : true});
	};
	
	
	$scope.titleSelectionChanged = function(selectedTitle){
	
		$scope.selectedTitle = selectedTitle;
		
		// variable used for scroll down
		$scope.onTitleSelectionChange = true;
		
		if(!$scope.selectedTitle)
		{
			$scope.conversations = {};
			$scope.stopInterval();
			return;
		}
		
		$scope.getAllConversation();
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
		 
		 $scope.getAllAttachment();
	 };
	 
	$scope.saveAttachment = function(e){
		
		$scope.initErrors("storyAttachmentModel", false);
		
		$scope.storyAttachmentModel.storyId = $scope.storyId;
		
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
		
		actionHelper.invokeAction("storyAttachment.save", $scope.storyAttachmentModel, null, saveAttachmentCallBack, {"hideInProgress" : true});
	};
	
	readAttachmentCallBack = function(readResponse, respConfig){
		 
		$scope.attachments = readResponse.model;
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}	
	 };
	 
	$scope.getAllAttachment = function(){
		
		actionHelper.invokeAction("storyAttachment.readAll", null, {"storyId" : $scope.storyId}, readAttachmentCallBack, {"hideInProgress" : true});
	};
	
	$scope.editAttachment = function(obj){
		
		$scope.initErrors("storyAttachmentModel", false);
		
		$scope.newModelMode = false;
		
		$scope.storyAttachmentModel = $.extend(true, {}, obj);
		
		utils.openModal('storyAttachmentModelDialog');
	};
	
	updateAttachmentCallBack = function(readResponse, respConfig){
		 $('#storyAttachmentModelDialog').modal('hide');
		 
		$scope.getAllAttachment();
	};
	
	$scope.updateAttachment = function(e){
		
		if(!$scope.storyAttachmentModel.file && !$scope.storyAttachmentModel.link)
		{
			utils.alert("Please provide at least one value for link or file or both");
			return;
		}
		
		actionHelper.invokeAction("storyAttachment.update", $scope.storyAttachmentModel, null, updateAttachmentCallBack, {"hideInProgress" : true});
	};
	
	deleteAttachmentCallBack = function(readResponse, respConfig){
		 
		$scope.getAllAttachment();
	};
	
	$scope.removeAttachment = function(obj){
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("storyAttachment.delete", null, {"id" : obj.id}, deleteAttachmentCallBack, {"hideInProgress" : true});
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
		searchQuery.projectId = $scope.getActiveProjectId();
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
	
	$scope.saveNote = function(published){
		
		$scope.$broadcast("saveNewStoryNote", published);
		
	};
	
}]);


