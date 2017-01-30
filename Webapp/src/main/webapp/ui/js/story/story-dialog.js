$.application.controller('storyDialogController', ["$scope", "actionHelper", "utils", "crudController", 
                                              function($scope, actionHelper, utils, crudController) {
	
	crudController.extend($scope, {
		"name": "Story",
		"modelName": "StoryModel",
		
		"nameColumn" : "title",
		
		"modelDailogId": "storyDialogId",
		
		"saveAction": "story.save",
		"readAction": "story.read",
		"updateAction": "story.update",
		"deleteAction": "story.delete",
		
		"onHide" : function(){
		
			//$scope.stopInterval();
		},
		
		"onDisplay" : function(model){
			
			$scope.storyForUpdate = model;
			
			$scope.$broadcast("fetchAllStoryNotes");
			
			
			//$scope.initTinyMce();
			
			$scope.message = "";
				
			// logic for adjust height as extension value can be added
			var modelFormElem = angular.element('#modelFormId'); 
			var panelBodyElem = angular.element('#panelBodyId');
			
			var conversationHeight = modelFormElem.height();

			panelBodyElem.css('height', conversationHeight + 'px');
			
			//$scope.getAllProjectMembers();
			
			$scope.selectedTitle = {};
			$scope.titles = [];
				
			//$scope.getAllTitle();
			
			//$scope.getAllAttachment();
		}
		
	});
	
	// Listener for broadcast
	$scope.$on("editStory", function(event, id) {
		
		$scope.selectedId = id;
		
		$scope.initModelDef();
		
		$scope.editEntry();
	});
	
}]);