$.application.controller('taskController', ["$scope", "crudController", function($scope, crudController) {
	crudController.extend($scope, {
		"name": "Task",
		
		"nameColumn" : "title",
		
		"modelDailogId": "taskDialog",
		
		
		"saveAction": "task.save",
		"readAction": "task.read",
		"updateAction": "task.update",
		"deleteAction": "task.delete",
	});
	
	 $scope.handleKeyPress = function(e) {
		 console.log("handleKeyPress is invoked " + $scope.inlineTitle);
		 e = e || window.event;
		 var key = e.keyCode ? e.keyCode : e.which;
			  
		 //enter key   
			   if (key == 13) {   
			         $scope.saveTask();
			      }
	}
		
	 $scope.isDisplayElement = false;
		$scope.newModelMode = true;
		$scope.model = {};

		 var projectId;
		//set title to model
		$scope.saveTask= function(e){
			
			console.log("inlinetext:     " + $scope.inlineTitle );
			
			$scope.model = {"title" : $scope.inlineTitle.trim(),
					
							"projectId" : projectId	
							};
			
		    console.log("model is invoked===model====" + projectId); 

		    console.log("model is invoked===model====" + $scope.model); 
			$scope.initErrors("model", true);
			$scope.newModelMode= 'Save';
			$scope.saveChanges();
			
			document.getElementById('tasktextarea').value=null;
		 }


		  $scope.getActiveIndex = function(){
			  return $scope.selectedIndex;
		  };
		  
	
}]);