$.application.controller('storyNoteController', ["$scope", "crudController", "utils", "actionHelper",
       function($scope, crudController, utils, actionHelper) {
	
	// Listener for broadcast
	$scope.$on("fetchAllStoryNotes", function(event, args) {

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
		        
		    }
		});
		
		$scope.fetchNotes();
	});
	
	$scope.fetchNotes = function(){
		
		actionHelper.invokeAction("storyNote.readAllNoteByStoryId", null, {"storyId" : $scope.storyId}, 
				function(readResponse, respConfig){
			
			$scope.versionTitle = "";
			$scope.publishedNotes = readResponse.model;
			$scope.activeContent = "";
			
			if($scope.publishedNotes.length > 0)
			{
				$scope.activeContent  = $scope.publishedNotes[0].content;
				
				tinymce.activeEditor.setContent($scope.activeContent);
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
		}, {"hideInProgress" : true});
	};
	
	//$scope.saveNote = function(published){
	$scope.$on("saveNewStoryNote", function(event, published) {
		
		if($scope.versionTitle.length == 0)
		{
			utils.alert("Please provide version");
			return;
		}
		
		$scope.editedContent = tinymce.activeEditor.getContent();
		
		console.log($scope.editedContent);
		
		if($scope.editedContent.length == 0)
		{
			utils.alert("Please provide some note");
			return;
		}
		
		var model = {"content" : $scope.editedContent, "storyId" : $scope.storyId, "published" : published};
		
		if(published)
		{
			$scope.publishedNotes.push(model);
		}
		
		if($scope.activeContent.length > 0)
		{
			
		}
		
		actionHelper.invokeAction("storyNote.save", model, null, function(saveResponse, respConfig){
			
			if(saveResponse.code == 0)
			{
				tinymce.activeEditor.setContent("");
			}else
			{
				$scope.fetchNotes();
			}
			
		}, {"hideInProgress" : true});
		
	});
	
	
	$scope.activeNote = function(content){
		
		tinymce.activeEditor.setContent(content);
	};
	
	
	$scope.clear = function(){
		
		tinymce.activeEditor.setContent("");
	};
	
	
}]);