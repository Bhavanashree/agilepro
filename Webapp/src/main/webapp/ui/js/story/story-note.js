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
			
			$scope.activeNoteModel = {};
			
			$scope.versionTitle = "";
			$scope.publishedNotes = readResponse.publishedNotes;
			$scope.draftNote = readResponse.draftNote;
			$scope.activeContent = "";
			
			if($scope.draftNote)
			{
				$scope.activeNoteModel = $scope.draftNote;
				
			}else if($scope.publishedNotes.length > 0)
			{
				$scope.activeNoteModel = $scope.publishedNotes[0];
			}
			
			console.log($scope.activeNoteModel);
			
			if($scope.activeNoteModel.id)
			{
				$scope.activeContent = $scope.activeNoteModel.content;
				
				$scope.versionTitle = $scope.activeNoteModel.versionTitle;
				
				console.log("value for tny = " + $scope.activeContent);
				
				tinymce.activeEditor.setContent($scope.activeContent);
			}
			
			console.log("tiny is assinged");
			
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
		
		$scope.activeNoteModel["content"] = $scope.editedContent;
		$scope.activeNoteModel["storyId"] = $scope.storyId;
		$scope.activeNoteModel["published"] = published;
		$scope.activeNoteModel["versionTitle"] = $scope.versionTitle;
		
		
		//var model = {"content" : $scope.editedContent, "storyId" : $scope.storyId, "published" : published, "versionTitle" : $scope.versionTitle};
		
		if(published)
		{
			$scope.publishedNotes.push($scope.activeNoteModel);
		}
		
		actionHelper.invokeAction("storyNote.saveOrUpdate", $scope.activeNoteModel, null, function(saveResponse, respConfig){
			
			if(saveResponse.code == 0)
			{
				tinymce.activeEditor.setContent("");
				
				$scope.versionTitle = "";
			}else
			{
				$scope.fetchNotes();
			}

			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
		}, {"hideInProgress" : true});
		
		
		
	});
	
	
	$scope.activeNote = function(content){
		
		tinymce.activeEditor.setContent(content);
		
		//$scope.versionTitl = 
	};
	
	
	$scope.clear = function(){
		
		tinymce.activeEditor.setContent("");
	};
	
	
}]);