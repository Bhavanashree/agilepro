$.application.controller("scrumActionConversationController", ["$scope", "crudController", "utils", "actionHelper",
                                     function($scope, crudController, utils, actionHelper) {
	
	// Listener for broadcast
	$scope.$on("displayActionItemsConversation", function(event, args) {

		$('#actionConversationModelId').modal('show');
		console.log("listner");
		
		 var mceContext = {
				    "selector": '#actionMessageId',
				    "plugins": "autolink link emoticons  textcolor",
				    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist",
				    "menubar": false,
				    "statusbar": false,
				    "content_css" : "/ui/css/conversations.css",
				    "setup" : function(ed) {
				    	ed.on('keyup', function (e) {  
				    		$scope.onActionConversationType(e);  
				        });
				     },
				 };
				 
		tinymce.init(mceContext);
		tinymce.get('actionMessageId').setContent("");
		
		$scope.isActionFirstRequest = true;
		$scope.saveActionConversation = true;
		$scope.fetchActionConversation();
	   
	});
	
	$scope.onActionConversationType = function(e){
		
		var key = e.keyCode ? e.keyCode : e.which;
	};
	
	/**
	 * Gets invoked on click of send button.
	 */
	$scope.submitActionContent = function(statusType){
		
		$scope.activeActionType = statusType;
		$scope.actionMessage = tinymce.get('actionMessageId').getContent();
		
		if($scope.actionMessage.length == 0)
		{
			utils.alert("Please provide some message");
			return;
		}
		
		$scope.projectMemberIds  = $scope.extractActionUsers($scope.actionMessage);
		
		var date = new Date();
		
		var model = {"scrumActionItemId" : $scope.getSelectedActionItemId(), "userId" : $scope.activeUser.userId, "message" : $scope.actionMessage,
				"projectMemberIds" : $scope.projectMemberIds};
		
		// scrumActionStatus for passing the status from model to action item entity.
		model.scrumActionStatus = $scope.activeActionType;
		
		// status to be displayed in the message
		if($scope.activeActionType == "CLOSE")
		{
			model.actionConversationStatus = "CLOSED";
		}
		else if($scope.activeActionType == "RE_OPEN")
		{
			model.actionConversationStatus = "RE_OPEN"
		}
		
		actionHelper.invokeAction("scrumMeetingConversation.saveActionConversation", model, null, 
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						tinymce.get('actionMessageId').setContent("");
						$scope.saveActionConversation = true;
						
						// fetch new conversation afetr save
						$scope.fetchActionConversation();
						
						// set for ui change in color
						($scope.getSelectedActionObj()).scrumActionStatus = $scope.activeActionType;
						
						if($scope.activeActionType == "CLOSE")
						{
							$scope.hideAction();
						}
					}
				}
		, {"hideInProgress" : true});
	};	

	/**
	 * Gets invoked on click of edit button, Method to get the action conversation.
	 */
	$scope.fetchActionConversation = function(){
		
		$scope.selectedActionItemId = $scope.getSelectedActionItemId();
		
		var status = ($scope.getSelectedActionObj()).scrumActionStatus;
		
		if(status == "OPEN" || status == "RE_OPEN")
		{
			$scope.scrumActionStatus = { "status1" : { "type": "Send", "value" : "OPEN"}, "status2" : {"type" : "Close", "value" : "CLOSE"}};
		}else
		{
			$scope.scrumActionStatus = { "status1" : { "type": "Send", "value" : "OPEN"}, "status2" : {"type" : "Re Open", "value" : "RE_OPEN"}};
		}
		
		console.log($scope.scrumActionStatus);
		
		actionHelper.invokeAction("scrumMeetingConversation.readActionConversationByScrumActionId", null, {"scrumActionItemId" : $scope.selectedActionItemId},
				function(readResponse, respCOnfig){
				
					$scope.actionConversations = readResponse.model;
					
					// logic for recursion.
					if($scope.isActionFirstRequest)
					 {
						$scope.actionIntervalValue = setInterval($scope.fetchActionConversation, ($.appConfiguration.conversationRefreshInterval));
						$scope.isActionFirstRequest = false;
					 }
					
					// logic for the scroll bar postion.
					var actionConversationId = $("#actionConversationId");
					
					if($scope.saveActionConversation || ($scope.prvsActionScrlHeight <=  actionConversationId.scrollTop()))
					{
						$scope.saveActionConversation = false;
						$scope.prvsActionScrlHeight =  actionConversationId.scrollTop();
						
						actionConversationId.animate({ scrollTop: actionConversationId.scrollHeight });
						
						$scope.prvsActionScrlHeight =  actionConversationId.scrollTop();
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
				}
		, {"hideInprogress" : true});
	};
	
	$scope.hideAction = function(){
		
		clearInterval($scope.actionIntervalValue);
		
		$('#actionConversationModelId').modal('hide');
		
		console.log("hide action");
	};
	
}]);