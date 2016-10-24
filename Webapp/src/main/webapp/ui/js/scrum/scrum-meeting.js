$.application.controller("scrumController", ["$scope", "crudController", "utils", "actionHelper",
                                     function($scope, crudController, utils, actionHelper) {
	
	$scope.meetingDate = null;
	
	
	 $scope.initScrum = function() {
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
	};
	
	
	$scope.onType = function($event){
		
		if(!$scope.meetingDate)
		{
			utils.alert("Please select a date");
		}
	};
	
}]);