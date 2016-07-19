$.application.controller('commonController', ["$scope", "clientContext", "utils", "actionHelper", function($scope, clientContext, utils, actionHelper) {
	$scope.name = "Common Controller";
	
    $scope.mails = [
		{
			"id" : 1,
			"subject" : "Subject line 1",
			"from": "From1"
		},
		{
			"id" : 2,
			"subject" : "Subject line 2",
			"from": "From2"
		}
	];
    
    $scope.notifications = [
		{
			"id": 1,
			"title" : "New project created"
		},
		{
			"id": 2,
			"title" : "Pending Due Amoount - 3000$"
		},
		{
			"id": 3,
			"title" : "Low usage by Customer"
		}
	];
    
    $scope.alerts = [
		{
			"id": 1,
			"title": "Scrum meeting @ 10",
			"time": "10:30 AM"
		},
		{
			"id": 2,
			"title": "Retrospection",
			"time": "10:45 AM"
		}
	];
    
    
    $scope.toggleLeftMenu = function() {
    	var leftMenu = $("#appLeftMenu");
    	leftMenu.toggleClass('visibleElem');
    };
    
    $scope.$on('$viewContentLoading', function(event) {
    	utils.displayInProgress();
    });

    $scope.$on('$viewContentLoaded', function(event) {
    	utils.hideInProgress();
    	
    	var leftMenu = $("#appLeftMenu");
    	leftMenu.removeClass('visibleElem');
    });
    
    
    $scope.showConversation = function(){
    	
    	utils.openModal("conversationDialog");
    }
   
    $scope.send = function(){
    	
    	console.log("send is invoked");
    	
    		
    	var messageFromUser = document.getElementById("message").value;
    	
    	var messageShowObj  =  document.getElementById("data1");
    	
    	messageShowObj.innerHTML = messageFromUser;
    	
    	messageShowObj.className = "mystyle";
    	
    	document.getElementById("message").value = " ";
    	
    	
    	console.log(message);
    	
    }
    $scope.data = [];
    
    
    
    $scope.projectName = "Select Projects";
    
    var projectId;
    
    var readProCallBack = function(readResponse, respConfig){
    	
    	$scope.projects = readResponse.model;
    	
    	console.log($scope.projects);
    	
    	$scope.$apply();
    };
    
    
    $scope.fetchProjects = function(){
    	
    	actionHelper.invokeAction("project.readAll", null, null, readProCallBack);
    };
    
    $scope.selectProject = function(project){
    	
    	$scope.projectName = project.name;
    	projectId = project.id;
    };
    
    $scope.getActiveProject = function(){
    	
    	return projectId;
    };
    
}]);