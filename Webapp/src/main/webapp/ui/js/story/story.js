$.application.controller('storyController', ["$scope", "crudController", "utils","modelDefService", "validator","$state","actionHelper",
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
			
			getAllConversation();
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
			
	 // method for saving conversation 	
	 $scope.onType = function(e) {
		 
		console.log("onType invoked");
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
			$scope.saveConversation();
		}
		else
		{
			return;
		}
	 };
	 
	 
	 $scope.addTitle = function(){
		 
		 console.log("add the title");
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
		 
		 actionHelper.invokeAction("conversation.readAll", null, {"storyId" : storyId}, readCallBack);
	 };
	 
	 saveCallBack = function(readResponse, respConfig){
		 
		 if(respConfig.success)
		 {
			 $scope.message = "";
			 $("#messageId").focus();
			 
			 getAllConversation();
		 }
	 };
	 
	 $scope.saveConversation = function(){
		
		if($scope.message)
		{
			if($scope.message.length > 0)
			{
				var model = {"message" : $scope.message.trim(), "storyId" : storyId, 
							"userId" : $scope.activeUser.userId};
				 
				actionHelper.invokeAction("conversation.save", model, null, saveCallBack);
			}
			else
			{
				return;
			}
		}
	 };
	 
	// Reduce height of input field conversation
	reduceHeight = function(){
		
		$("#messageId").css('height', 2.5 + 'em');
		$("#sendButtonId").css('height', 2.5 + 'em');
		
		console.log("height is reduced");
	};
	
}]);


