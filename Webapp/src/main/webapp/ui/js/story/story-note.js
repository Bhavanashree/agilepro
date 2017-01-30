$.application.controller('storyNoteController', ["$scope", "crudController", "utils", "actionHelper",
       function($scope, crudController, utils, actionHelper) {
	
	
	// Listener for broadcast
	$scope.$on("fetchAllStoryNotes", function(event, args) {

		tinymce.init({
		    "selector": '#noteId',
		    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist",
		    "menubar": false,
		    "statusbar": false,
		    "mentions": {
		        
		    }
		});
		
		$scope.fetchNotes();
		$scope.errorStoryNote = {"error" : false, "message" : ""};
	});
	
	/**
	 * Fetch all the notes of the selected story.
	 */
	$scope.fetchNotes = function(){
		
		var selectedStory = $scope.getStoryForUpdate();
		
		actionHelper.invokeAction("storyNote.readAllNoteByStoryId", null, {"storyId" : selectedStory.id}, 
				function(readResponse, respConfig){

			$scope.versionTitlesSet = [];			
			tinymce.activeEditor.setContent("");
			$scope.versionTitle = "";

			$scope.storyNotes = readResponse.model;
			
			if($scope.storyNotes.length > 0)
			{
				$scope.activeNoteModel = $scope.storyNotes[0];
				
				tinymce.activeEditor.setContent($scope.activeNoteModel.content);
				$scope.versionTitle = $scope.activeNoteModel.versionTitle;
			}else
			{
				$scope.activeNoteModel = {};
			}
			
			
			$scope.draftIsSelected = $scope.activeNoteModel.storyNoteStatus == "DRAFT" ? true : false;
			
			if($scope.draftIsSelected)
			{
				$scope.draftIndex = 0;
			}
			
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
			for(index in $scope.storyNotes)
			{
				var noteObj = $scope.storyNotes[index];
				
				$scope.versionTitlesSet.push(noteObj.versionTitle);
			}
			
		}, {"hideInProgress" : true});
	};
	
	// Listener for saving new story note, save or published button in parent controller displayed 
	// at the bottom of the dailoge.
	$scope.$on("saveNewStoryNote", function(event, newStatus) {
		
		// error if title length is more than 20.
		if($scope.errorStoryNote.error)
		{
			return;
		}
		
		if($scope.versionTitle.length == 0)
		{
			utils.alert("Please provide version");
			return;
		}
		
		$scope.editedContent = tinymce.activeEditor.getContent();
		
		if($scope.editedContent.length == 0)
		{
			utils.alert("Please provide some note");
			return;
		}

		if(!$scope.draftIsSelected && $scope.versionTitlesSet.indexOf($scope.versionTitle) != -1)
		{
			$scope.errorStoryNote.error = true,
			$scope.errorStoryNote.message  = "Please provide different version title, provided version title is already existing";
			return;
		}
		
		if(!$scope.draftIsSelected)
		{
			$scope.activeNoteModel = {};
		}
		
		$scope.activeNoteModel["content"] = $scope.editedContent;
		$scope.activeNoteModel["storyId"] = $scope.getStoryForUpdate().id;
		$scope.activeNoteModel["storyNoteStatus"] = newStatus;
		$scope.activeNoteModel["versionTitle"] = $scope.versionTitle;
		
		var simpleDateFormatter = new simpleDateFormat('d/MM/yyyy');
		var date = new Date();
		$scope.activeNoteModel["updatedOn"]  = simpleDateFormatter.format(date);
		
		$scope.saveOrUpdate(newStatus);
	});
	
	/**
	 * Save or update.
	 */
	$scope.saveOrUpdate = function(status){
		
		actionHelper.invokeAction("storyNote.saveOrUpdate", $scope.activeNoteModel, null, 
				
		function(saveResponse, respConfig)
		{
			if(saveResponse.code == 0)
			{
				$scope.activeNoteModel.owner = $scope.activeUser.displayName;
				
				$scope.versionTitlesSet.push($scope.activeNoteModel.versionTitle);
				
				if($scope.storyNotes.length == 0)
				{
					$scope.storyNotes.push($scope.activeNoteModel);
				}
				else if($scope.draftIsSelected)
				{
					$scope.storyNotes[$scope.draftIndex] = $scope.activeNoteModel;
				}
				else
				{
					$scope.storyNotes.unshift($scope.activeNoteModel);
				}				
				
				tinymce.activeEditor.setContent("");
				$scope.versionTitle = "";
				
			}else
			{
				utils.alert(saveResponse.message);
			}
		
			try
			{
				$scope.$apply();
			}catch(ex)
			{}
			
		}, {"hideInProgress" : true});
	};
	

	/**
	 * Gets invoked when active note is selected.
	 */
	$scope.activeNote = function(storyNote, noteIndex){
		
		$scope.errorStoryNote = {"error" : false, "message" : ""};
		
		$scope.activeNoteModel = storyNote;
		
		tinymce.activeEditor.setContent(storyNote.content);
		$scope.versionTitle = storyNote.versionTitle;
		
		$scope.draftIsSelected = $scope.activeNoteModel.storyNoteStatus == "DRAFT" ? true : false;
		
		if($scope.draftIsSelected)
		{
			$scope.draftIndex = noteIndex;
		}
	};
	
	/**
	 * Gets invoked on type in input box for new version title.
	 */
	$scope.checkVersionTitleLength = function(event){
		
		$scope.draftIsSelected = false;
		
		if($scope.versionTitle.length > 20)
		{
			$scope.errorStoryNote.error = true,
			$scope.errorStoryNote.message  = "Title length can be maximum 20";
			return;
		}
		
		$scope.errorStoryNote.error = false;
		
	};
	
	
}]);
