$.application.controller('bugDialogController', ["$scope", "crudController","validator", "utils","modelDefService","actionHelper", 
                         function($scope, crudController,validator,utils,modelDefService,actionHelper) {
	
	crudController.extend($scope, {
		"name": "Bug",
		
		"nameColumn" : "name",
		
		"modelDailogId": "bugDialogId",
		"saveAction": "bug.save",
		"readAction": "bug.read",
		"updateAction": "bug.update",
		"deleteAction": "bug.delete",
	
		"onDisplay" : function(model){
			
			//$scope.init();
			
			if(!(model.id))
			{
				$scope.commentTab = false;	
			}
			else
			{
				$scope.bugId = model.id;
				$scope.bugOwner = model;
				$scope.commentTab = true;	
				$scope.bugAttachmentTab = true;
				$scope.listOfEmployees();
				$scope.displayComments();
			}
		}
	});
	
	// listener
	$scope.$on("openNewBugDialog", function(event, args) {

		$("#bugDialogId").show();
		
		$scope.addEntry(event);
	});

	// listener
	$scope.$on("openEditBugDialog", function(event, id) {

		$("#bugDialogId").show();
		$scope.selectedId = id;
		$scope.editEntry(event);
	});

	
}]);
