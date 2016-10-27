$.application.controller("scrumController", ["$scope", "crudController", "utils", "actionHelper",
                                     function($scope, crudController, utils, actionHelper) {
	
	$scope.meetingDate = null;
	
	$scope.isFirstRequest = true;
	
	$scope.storyIdObjMap = {};
	
	$scope.selectedStory = {};
	
	$scope.projectMemberIds = [];
	
	$scope.initScrum = function() {
		
		setTimeout($scope.initScrum1, 3000);
	};
	
	 $scope.initScrum1 = function() {
		 console.log("init scrum is invoked");
		 
		 var mceContext = {
		    "selector": '#scrumMessageId',
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
		 
		 //tinymce.EditorManager.execCommand('mceRemoveControl',true, mceContext.id);
		
		 //tinymce.EditorManager.execCommand('mceAddControl',true, mceContext.id);
		 
		$scope.simpleDateFormatter = new simpleDateFormat('d/MM/yyyy');
		
		$scope.meetingDate = $scope.simpleDateFormatter.format(new Date());
		
		$scope.fetchAllScrumMeetings();
	};
	
	
	$scope.onType = function($event){
		
		
	};
	
	
	$scope.submitScrumContent = function(){
		
		$scope.message = tinymce.activeEditor.getContent();
		
		if($scope.message.length == 0)
		{
			utils.alert("Please provide some message");
			return;
		}
		
		$scope.projectMemberIds  = $scope.extractActionUsers($scope.message);
		
		var date = new Date();
		
		var model = {"scrumMeetingId" : 1, "userId" : $scope.activeUser.userId,"date" : $scope.simpleDateFormatter.format(date), "message" : $scope.message,
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
						tinymce.activeEditor.setContent("");
						
						$scope.fetchAllScrumMeetings();
					}
				}
		, {"hideInProgress" : true});
	};	
	
	$scope.fetchAllScrumMeetings = function(){
		
		// fetch srum metting
		actionHelper.invokeAction("scrumMeeting.readAll", null, {"date" : $scope.meetingDate}, 
				function(readResponse, respConfig)
				{
					$scope.scrumMeetings = readResponse.model;
					
					$scope.fetchAllScrumMeetingConversation();
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
		
		// fetch project members
		actionHelper.invokeAction("projectMember.readProjectMembersByProjectId", null, {"projectId" : $scope.getActiveProjectId()}, 
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
	
	
	$scope.fetchAllScrumMeetingConversation = function(){
		
		actionHelper.invokeAction("scrumMeetingConversation.readAll", null, {"scrumMeetingId" : 1}, 
				function(readResponse, respConfig)
				{
					$scope.scrumConversations = readResponse.model;
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
				}
		, {"hideInProgress" : true});
		
		 if($scope.isFirstRequest)
		 {
			console.log("interval is set");
			$scope.intervalValue = setInterval($scope.fetchAllScrumMeetingConversation, ($.appConfiguration.conversationRefreshInterval));
				 
			$scope.isFirstRequest = false;
		 }
	};
	
	$scope.onStoryChange = function(storyId){
		
		$scope.selectedStory = $scope.storyIdObjMap[storyId]; 
	};
	
	
}]);