$.application.controller('bugController', ["$scope", "crudController", "utils","modelDefService","actionHelper", 
                                           function($scope, crudController,utils,modelDefService,actionHelper) {
	crudController.extend($scope, {
		"name": "Bug",
		
		"nameColumn" : "name",
		
		"modelDailogId": "bugDialog",
		"saveAction": "bug.save",
		"readAction": "bug.read",
		"updateAction": "bug.update",
		"deleteAction": "bug.delete",
	
		"onDisplay" : function(model){
			$scope.init();
			
			if(model.id)
			{
				$scope.bugId = model.id;
				$scope.displayComments();
				$scope.listOfEmployees();
			}
		}
	});
	//dropdowns array
	$scope.commentStatus = [{"name" : "Comment"},{"name": "reopen"}, {"name": "closed"}, {"name":"deferred"}];
	
	$scope.selectedStatus={};
	$scope.init = function() {
		 console.log("check");
			tinymce.init({
			    "selector": '#commentId',
			    "plugins": "autolink link emoticons  textcolor mention",
			    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist",
			    "menubar": true,
			    "mentions": {
			        "source": [
			            { name: 'Tyra Porcelli' }, 
			            { name: 'Brigid Reddish' },
			            { name: 'Ashely Buckler' },
			            { name: 'Teddy Whelan' }
			        ],
			        "render": function(item) {
			            return '<li>' +
			                       '<a href="javascript:;"><span>' + item.name + '</span></a>' +
			                   '</li>';
			        }
			    }
			});
		};
	
		

	$scope.bugModel = {};
	
	var saveCommentCallBack =function(readResponse, respConfig){
		
	}
	//save comments
	$scope.onCommentedBtn =function(){
		
		var content = tinymce.activeEditor.getContent();
		$scope.comment = content; 
		
		console.log("Got message as ", $scope.comment);
		
		
		 bugModel = {"content": $scope.comment.trim() , "bugId" :  $scope.bugId };
		 
		 console.log(bugModel);
		 
		actionHelper.invokeAction("bugComment.save", bugModel, null, saveCommentCallBack);
	};
	
	
	var readCmtscallBack = function(read, resp){
		console.log("ddddddd");
		
		$scope.commentRead = read.model;
	}
	//read comments as per bugid
	$scope.displayComments =function(){
		console.log("displaycommens");
		actionHelper.invokeAction("bugComment.readByBugId", null,{"bugId" :  $scope.bugId}, readCmtscallBack);
	};
	
	
	var readEmployeecallBack  = function(readResponse , respConfig)
	{
		console.log(readResponse);

		$scope.employees = readResponse.model;
		console.log($scope.employees);
	
	}
	//listOfemployess
	$scope.listOfEmployees = function(){
		
		actionHelper.invokeAction("employee.readAll", null, null, readEmployeecallBack);
	};
	
	$scope.setStatus = function(obj){
		$scope.selectedStatus=obj; 
		console.log($scope.selectedStatus 	);
		
	
	};
	
	
}]);