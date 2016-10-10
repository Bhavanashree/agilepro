$.application.controller('bulkStoryController', ["$scope", "crudController", "utils","modelDefService", 
                                             "validator", "$state", "actionHelper",
   function($scope, crudController, utils, modelDefService, validator, $state, actionHelper) {
		
		/**
		 * Specifies the indentation used by last story. Which in turn is used for new lines.
		 */
		$scope.lastIndentation = "";
		
		/**
		 * Maintains last text with which stories were created. Which
		 * in turn is used to track change in text.
		 */
		$scope.prevText = "";

		/**
		 * Event call back called when text bulk story field is modified.
		 */
		$scope.bulkStoryTextModified = function(e) {
			e = e || window.event;
			var key = e.keyCode ? e.keyCode : e.which;
			
			// if enter key is pressed
			if(key == 13)
			{
				var textArea = $("#bulkStoryText"); 
				textArea.val(textArea.val() + $scope.lastIndentation);
				//textArea.setCursorPosition(textArea.val().length);
				return;
			}

			$scope.createBulkStoryTree();
		};
		
		/**
		 * Takes the latest text and updates the bulk story tree
		 */
		$scope.createBulkStoryTree = function() {
			
			var text = $("#bulkStoryText").val();
			
			if(text.trim() == $scope.prevText)
			{
				return;
			}
			
			$scope.prevText = text.trim();
			
			var bulkStoryList = [];
			
			var lines = text.split("\n");
			var linePattern = /(\s*)(.+)/;
			var match = null, indent = null, title = null;
			
			var prevStory = null, newStory = null, parentStory = null;
			
			for(var line of lines)
			{
				match = linePattern.exec(line);
				
				if(!match)
				{
					continue;
				}
				
				//extract the amount of indentation and title from the current line
				indent = match[1];
				title = match[2];
				
				if(title.length <= 0)
				{
					return;
				}
				
				//if this is first story
				if(prevStory == null)
				{
					newStory = {"title": title, "$indent": indent.length, "$root": true, "$indentString": indent};
					bulkStoryList.push(newStory);
				}
				else
				{
					parentStory = prevStory;
					
					while(parentStory != null)
					{
						//if current line indent is greater than parent story indentation
						if(indent.length > parentStory["$indent"])
						{
							break;
						}
						
						//if lesser indent parent is not found, go to next level in hierarchy
						parentStory = parentStory["$parent"];
					}
					
					//if parent story is found
					if(parentStory)
					{
						if(!parentStory.substories)
						{
							parentStory.substories = [];
						}
						
						newStory = {"title": title, "$indent": indent.length, "$parent": parentStory, "$indentString": indent};
						parentStory.substories.push(newStory)
					}
					//if no parent story is found
					else
					{
						newStory = {"title": title, "$indent": indent.length, "$root": true, "$indentString": indent};
						bulkStoryList.push(newStory);
					}
				}
				
				prevStory = newStory;
			}
			
			//if atleast one story is found set last indentation to that story
			if(prevStory)
			{
				$scope.lastIndentation = prevStory.$indentString;
			}
			
			$scope.bulkStories = bulkStoryList;
			
			try
			{
				this.$scope.$digest();
			}catch(ex)
			{}
		};
		
		$scope.saveStoryTitle = function(e){
			$scope.newStoryMode= 'true';
			
			var model = {"stories" : $scope.bulkStories, "projectId" :  $scope.getActiveProjectId()};
		
			actionHelper.invokeAction("story.storiesInbulk", model, null, function(read, Response){
				
			});
			
		};
		
}]);