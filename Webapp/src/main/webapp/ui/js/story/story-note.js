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
			
			$scope.activeNoteModel = {};
			$scope.versionTitle = "";
			$scope.activeVersionTitle = "";
			$scope.activeContent = "";
			$scope.versionTitlesSet = [];
			
			$scope.storyNotes = readResponse.model;
			
			if($scope.storyNotes.length > 0)
			{
				$scope.activeNoteModel = $scope.storyNotes[0];
				
				$scope.activeContent = $scope.activeNoteModel.content;
				$scope.activeVersionTitle = $scope.activeNoteModel.versionTitle;
				tinymce.activeEditor.setContent($scope.activeContent);
			}
			
			$scope.draftIsSelected = $scope.activeNoteModel.storyNoteStatus == "DRAFT" ? true : false;
			
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
	
	// Listener for saving new story note, save or published button in parent controller displayed at the bottom of the dailoge.
	$scope.$on("saveNewStoryNote", function(event, status) {
		
		$scope.checkVersionTitle(null);
		
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
		
		if(status == "PUBLISHED" && !$scope.draftIsSelected)
		{
			$scope.activeNoteModel = {};
		}
		
		$scope.activeNoteModel["content"] = $scope.editedContent;
		$scope.activeNoteModel["storyId"] = $scope.getStoryForUpdate().id;
		$scope.activeNoteModel["storyNoteStatus"] = status;
		$scope.activeNoteModel["versionTitle"] = $scope.versionTitle;
		$scope.activeNoteModel["draftIsSelected"] = $scope.draftIsSelected;
		
		var simpleDateFormatter = new simpleDateFormat('d/MM/yyyy');
		var date = new Date();
		$scope.activeNoteModel["updatedOn"]  = simpleDateFormatter.format(date);
		
		$scope.saveOrUpdate(status);
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
				if(status == "PUBLISHED")
				{
					if($scope.draftIsSelected)
					{
						$scope.storyNotes[0] = $scope.activeNoteModel;
						
						$scope.draftIsSelected = false;
					}else
					{
						$scope.storyNotes.push($scope.activeNoteModel);
						$scope.versionTitlesSet.push($scope.activeNoteModel.versionTitle);
					}
				}else 
				{
					
				}
				
				tinymce.activeEditor.setContent("");
				$scope.versionTitle = "";
				
			}else
			{
				utils.alert("Error in saving story notes");
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
	$scope.activeNote = function(storyNote){
		
		$scope.activeNoteModel = storyNote;
		
		$scope.draftIsSelected = $scope.activeNoteModel.storyNoteStatus == "DRAFT" ? true : false;
		
		tinymce.activeEditor.setContent(storyNote.content);
		$scope.versionTitle = storyNote.versionTitle;
		$scope.checkVersionTitle(null);
	};
	
	/**
	 * Gets invoked on type in input box for new version title.
	 */
	$scope.checkVersionTitle = function(event){
		
		// Set draftIsSelected as false as the new title for draft or published
		$scope.draftIsSelected = false;
		
		if($scope.versionTitle.length > 20)
		{
			$scope.errorStoryNote.error = true,
			$scope.errorStoryNote.message  = "Title length can be maximum 20";
			return;
		}
		
		if($scope.versionTitlesSet.indexOf($scope.versionTitle) != -1 && !$scope.draftIsSelected)
		{
			$scope.errorStoryNote.error = true,
			$scope.errorStoryNote.message  = "Please provide different version title, provided version title is already existing";
			return;
		}
			
		$scope.errorStoryNote.error = false;
		
	};
	
	$scope.clear = function(){
		
		tinymce.activeEditor.setContent("");
	};
	
	
}]);
