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
			
			$scope.storyNotes = readResponse.model;
			$scope.activeContent = "";
			$scope.versionTitlesSet = []; 
			
			$scope.alreadyPresent
			
			var obj;
			
			if($scope.storyNotes.length > 0)
			{
				$scope.activeNoteModel = $scope.storyNotes[0];
				
				$scope.activeContent = $scope.activeNoteModel.content;
				$scope.versionTitle = $scope.activeNoteModel.versionTitle;
				tinymce.activeEditor.setContent($scope.activeContent);
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			for(index in $scope.storyNotes)
			{
				obj = $scope.storyNotes[index];
				
				$scope.versionTitlesSet.push(obj.versionTitle);
			}
			
		}, {"hideInProgress" : true});
	};
	
	//$scope.saveNote = function(published){
	$scope.$on("saveNewStoryNote", function(event, published) {
		
		for(index in $scope.versionTitlesSet)
		{
			if($scope.versionTitle == $scope.versionTitlesSet[index])
			{
				$scope.titleAlreadyPresent = true;
				break;
			}else
			{
				$scope.titleAlreadyPresent = false;
			}
			
		}
		
		if($scope.titleAlreadyPresent)
		{
			return;
		}
		
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
		$scope.activeNoteModel["owner"] = $scope.activeUser.displayName;
		
		$scope.storyNotes.push($scope.activeNoteModel);
		$scope.versionTitlesSet.push($scope.activeNoteModel.versionTitle);
		tinymce.activeEditor.setContent("");
		$scope.versionTitle = "";
		
		actionHelper.invokeAction("storyNote.saveOrUpdate", $scope.activeNoteModel, null, function(saveResponse, respConfig){
			
			if(saveResponse.code == 0)
			{
				utils.info(["Successfully saved {} "], $scope.versionTitle);
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
	
	
	$scope.activeNote = function(storyNote){
		
		tinymce.activeEditor.setContent(storyNote.content);
		
		$scope.versionTitle = storyNote.versionTitle;
		
		$scope.checkVersionTitle(null);
	};
	
	
	$scope.checkVersionTitle = function(event){
		
		console.log($scope.versionTitlesSet);
		
		for(index in $scope.versionTitlesSet)
		{
			if($scope.versionTitle == $scope.versionTitlesSet[index])
			{
				$scope.titleAlreadyPresent = true;
				break;
			}else
			{
				$scope.titleAlreadyPresent = false;
			}
			
		}
		
	};
	
	$scope.clear = function(){
		
		tinymce.activeEditor.setContent("");
	};
	
	
}]);