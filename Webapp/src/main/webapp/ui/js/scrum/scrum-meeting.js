$.application.controller("scrumController", ["$scope", "crudController", "utils", "actionHelper",
                                     function($scope, crudController, utils, actionHelper) {
	
	$scope.meetingDate = null;
	
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
		
		var simpleDateFormatter = new simpleDateFormat('d/MM/yyyy');
		var date = new Date();
		
		var model = {"scrumMeetingId" : 1, "userId" : $scope.activeUser.userId,"date" : simpleDateFormatter.format(date), "message" : $scope.message};
		
		actionHelper.invokeAction("scrumMeetingConversation.save", model, null, 
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						$scope.fetchAllScrumMeetings();
					}
				}
		, {"hideInProgress" : true});
	};	
	
	$scope.fetchAllScrumMeetings = function(){
		
		actionHelper.invokeAction("scrumMeetingConversation.readAll", null, null, 
				function(readResponse, respConfig)
				{
					$scope.scrumMeetings = readResponse.model;
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
				}
		, {"hideInProgress" : true});
	};
	
	
}]);