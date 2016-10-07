$.application.controller('storyNoteController', ["$scope", "crudController", "utils", "actionHelper",
       function($scope, crudController, utils, actionHelper) {
	
	/* crudController.extend($scope, {
		
		 "onDisplay" : function(model){
			 
			 $scope.initStoryNote();
		 }
	 });*/
	
	$scope.initStoryNote = function() {
		
		console.log("$scope.initStoryNote is invoked");
		
		tinymce.init({
		    "selector": '#noteId',
		    "plugins": ["autolink link emoticons  textcolor mention",
		                "advlist autolink lists link image charmap print preview anchor",
		                "searchreplace visualblocks code fullscreen",
		                "insertdatetime media table contextmenu paste imagetools"],
		    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | " +
		    			"forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist" +
		    			"insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
		    "menubar": true,
		    "mentions": {
		        "source": [
		            { name: 'Tyra Porcelli' }, 
		            { name: 'Brigid Reddish' },
		            { name: 'Ashely Buckler' },
		            { name: 'Teddy Whelan' }
		        ]
		    }
		});
	};
	
}]);