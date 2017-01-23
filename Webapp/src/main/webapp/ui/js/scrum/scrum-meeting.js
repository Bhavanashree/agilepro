$.application.controller("scrumController", ["$scope", "crudController", "utils", "actionHelper",
                                     function($scope, crudController, utils, actionHelper) {
	
	$scope.simpleDateFormatter = new simpleDateFormat('dd/MM/yyyy');
	$scope.meetingDate = $scope.simpleDateFormatter.format(new Date());
	$scope.isFirstRequest = true;
	$scope.storyIdObjMap = {};
	$scope.selectedStory = {};
	$scope.projectMemberIds = [];
	
	$scope.onDateChange = function(){
		
		$scope.onDateChangeValue = true;
		$scope.fetchAllScrumMeetings();
	};
	
	
	/**
	 * Init method to load the tinymce and fetch all the conversations 
	 */
	 $scope.initScrum = function() {
		 console.log("init scrum is invoked");
		 
		 var mceContext = {
		    "selector": '#scrumMessageId',
		    "plugins": "autolink link emoticons  textcolor mention",
		    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist",
		    "menubar": false,
		    "statusbar": false,
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
		        
		        return $scope.getProjectMembers();
			}, mceContext)
	    };

		try
		{
			tinymce.remove();
		}catch(ex)
		{
			//ignoring this error, which can happen before tinymce is not initialize
			//on target id
		}
		
		tinymce.init(mceContext);
		
		$scope.onDateChangeValue = true;
		$scope.fetchAllScrumMeetings();
	};
	
	/**
	 * This method gets invoked while typing in the text area. 
	 */
	$scope.onType = function($event){
		
		
	};
	
	/**
	 * Gets invoked on click of send button.
	 */
	$scope.submitScrumContent = function(){
		
		$scope.message = tinymce.get('scrumMessageId').getContent();
		
		var strMessage = $($scope.message).text();
		
		var employees = $($scope.message).find(".userMention");
		
		for(var i=0;i<employees.length;i++)
		{
			strMessage = strMessage.replace($(employees[i]).text(), "");
			console.log(strMessage.replace($(employees[i]).text(), ""));
		}
		
		
		if((strMessage.trim()).length == 0)
		{
			utils.alert("Please provide some message");
			return;
		}
		
		$scope.projectMemberIds  = $scope.extractActionUsers($scope.message);
		
		var date = new Date();
		
		var model = {"scrumMeetingId" : $scope.scrumMeeting.id, "userId" : $scope.activeUser.userId, "message" : $scope.message,
				"projectMemberIds" : $scope.projectMemberIds};
		
		if($scope.selectedStory.id)
		{
			model["storyId"] = $scope.selectedStory.id;
		}
		
		actionHelper.invokeAction("scrumMeetingConversation.save", model, null, 
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						tinymce.get('scrumMessageId').setContent("");
						$scope.saveSuccess = true;
						
						$scope.fetchAllScrumMeetingConversation();
						$scope.fetchScrumAction();
					}
				}
		, {"hideInProgress" : true});
	};	
	
	/**
	 * This method reads meeting as per the date.
	 */
	$scope.fetchAllScrumMeetings = function(){
		
		$scope.scrumMeeting = {};
		$scope.stories = {};
		$scope.scrumConversations = {};
		$scope.scrumActionItems = {};
		$scope.scrumActionItemIdObjMap = {};
		
		console.log($scope.meetingDate);
		
		if($scope.getActiveProjectId() != -1)
		{
			// fetch scrum meeting
			actionHelper.invokeAction("scrumMeeting.readByDateAndProjectId", null, {"date" : $scope.meetingDate, "projectId" : $scope.getActiveProjectId()}, 
					function(readResponse, respConfig)
					{
						//$scope.scrumMeeting = readResponse.model;
						
						// for test to get the values latter
						$scope.scrumMeeting = {"id" : 1};
						
						$scope.fetchAllScrumMeetingConversation();
						$scope.fetchScrumAction();
					}
			, {"hideInProgress" : true});
			
			// fetch stories
			actionHelper.invokeAction("story.readByProjectId", null, {"projectId" : $scope.getActiveProjectId()}, 
					function(readResponse, respConfig)
					{
						if(readResponse.model.length > 0)
						{
							$scope.stories = readResponse.model;
							
							for(index in $scope.stories)
							{
								$scope.storyIdObjMap[$scope.stories[index].id] = $scope.stories[index]; 
							}
						}else
						{
							$scope.stories = [];
						}
						
					}
			, {"hideInProgress" : true});
		}
		
	};
	
	
	/**
	 * Recursion method for fetching the conversation.
	 */
	$scope.fetchAllScrumMeetingConversation = function(){
		
		if($scope.getCurrentState().tab != "scrumTab")
		{
			clearInterval($scope.intervalValue);	
		}
		
		actionHelper.invokeAction("scrumMeetingConversation.readAll", null, {"scrumMeetingId" : $scope.scrumMeeting.id}, 
				function(readResponse, respConfig)
				{
					$scope.scrumConversations = readResponse.model;
					
					var scrumConversationId = $("#scrumConversationId");
					
					if($scope.saveSuccess || $scope.onDateChangeValue || ($scope.prvsScrlHeight <=  scrumConversationId.scrollTop()))
					{
						$scope.saveSuccess = false;
						$scope.onDateChangeValue = false;
						$scope.prvsScrlHeight =  scrumConversationId.scrollTop();
						
						scrumConversationId.animate({ scrollTop: scrumConversationId[0].scrollHeight });
						
						$scope.prvsScrlHeight =  scrumConversationId.scrollTop();
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
				}
		, {"hideInProgress" : true});
		
		 if($scope.isFirstRequest)
		 {
			$scope.intervalValue = setInterval($scope.fetchAllScrumMeetingConversation, ($.appConfiguration.conversationRefreshInterval));
				 
			$scope.isFirstRequest = false;
		 }
	};
	
	$scope.fetchScrumAction  = function(){
		
		actionHelper.invokeAction("scrumMeetingConversation.readActionByScrumId", null, {"scrumMeetingId" : $scope.scrumMeeting.id}, 
				function(readResponse, respConfig)
				{
					$scope.scrumActionItems = readResponse.model;
					
					$scope.scrumActionItemIdObjMap = {};
					
					if($scope.scrumActionItems)
					{
						for(index in $scope.scrumActionItems)
						{
							var actionItemObj = $scope.scrumActionItems[index];
							
							$scope.scrumActionItemIdObjMap[actionItemObj.id] = actionItemObj; 
						}
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
				}
		, {"hideInProgress" : true});
	};
	
	/**
	 * Method to get the active selected story.
	 */
	$scope.onStoryChange = function(storyId){
		
		$scope.selectedStory = $scope.storyIdObjMap[storyId]; 
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		$scope.fetchAllScrumMeetings();
	   
	});
	
	/**
	 * GetText removes user mention class from message returns only the text. 
	 */
	$scope.getText = function(data){
		
		return $(data).text();
	};
	
	$scope.showAction = function(actionItemId){
		
		$scope.selectedActionItemId = actionItemId
		$scope.$broadcast("displayActionItemsConversation");
	};
	
	
	$scope.getSelectedActionItemId = function(){
		
		if(!$scope.selectedActionItemId)
		{
			return -1;
		}
		
		return $scope.selectedActionItemId;
	};

	$scope.getSelectedActionObj = function(){
		
		return $scope.scrumActionItemIdObjMap[$scope.selectedActionItemId];
	};
	
}]);