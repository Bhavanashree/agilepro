$.application.controller('storyController', ["$scope", "crudController", "utils","modelDefService", "validator","$state","$timeout",
                                               function($scope, crudController,utils, modelDefService, validator,$state,$timeout) {
	
	 crudController.extend($scope, {
		"name": "Story",
		"modelName": "StoryModel",
		
		"nameColumn" : "name",
		
		"modelDailogId": "storyDialog",
		
		"saveAction": "story.save",
		"readAction": "story.read",
		"updateAction": "story.update",
		"deleteAction": "story.delete",

		});

		 $scope.handleKeyPress = function(e) {
			 console.log("handleKeyPress is invoked " + $scope.inlineTitle);
			 e = e || window.event;
			 var key = e.keyCode ? e.keyCode : e.which;
				  
			 //enter key   
				   if (key == 13) {   
				         $scope.saveBacklog();
				      }
		}

		 $scope.handleKeyForSubtitle = function(e) {
			 
			 var element = $(e.target);
			 console.log("handlekey press 2:   from save substories->  " +  element.val());
			 
			 e = e || window.event;
			 var key = e.keyCode ? e.keyCode : e.which;
			   //enter key   
			   if (key == 13) {  
				   $scope.saveSubstory(element.val());
			      } 
		}
			
		 
		$scope.isDisplayElement = false;
		$scope.newModelMode = true;
		$scope.storyModel = {};
		$scope.model = {};
		//set title to model
		$scope.saveBacklog= function(e){
			
			console.log("inlinetext:     " + $scope.inlineTitle );
			
			$scope.model = {"title" : $scope.inlineTitle.trim()};
			
		    console.log("model is invoked===model====" + $scope.model); 
			$scope.initErrors("model", true);
		
			$scope.saveChanges();
			
			document.getElementById('parentStorytextarea').value=null;
			$scope.refreshSearch();
		 }

		//autorefresh
		$scope.refreshSearch = function(){
			$scope.$broadcast("invokeSearch", {});
		};
		
		
		$scope.saveSubstory= function(title){
			$scope.model = {
				"title" : title, 
				"parentStoryId": $scope.selectedId
			};

			$scope.newModelMode= 'Save';
			$scope.saveChanges();
			$scope.refreshSearch();
		};
	
		    	
		  $scope.getActiveIndex = function(){
			  return $scope.selectedIndex;
		  };
}]);


