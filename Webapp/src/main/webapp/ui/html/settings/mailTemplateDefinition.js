$.application.controller('mailTemplteDefinitionController', ["$scope", "crudController","utils","modelDefService","actionHelper",
                          function($scope, crudController,utils, modelDefService, actionHelper) {
	crudController.extend($scope, {
		"name": "MailTemplateDefinition",
		
		"nameColumn" : "name",
		
		"modelDailogId": "mailTemplteDefinition",
		
		
		"saveAction": "mailTemplteDefinition.save",
		"readAction": "mailTemplteDefinition.read",
	});
	
	$scope.admin = {"name" : "Admin", "toAdmin" : false};
	$scope.projectManager = {"name" : "To Manager", "toProjectManager" : false};
	$scope.ccAdmins = {"name" : "Admin", "ccAdmin" : false};
	$scope.ccProjectManagers = {"name" : "ProjectManager", "ccProjectManager" : false};
	
	
	$scope.model={};	
	 $scope.init = function() {
		 console.log("check");
			tinymce.init({
			    "selector": '#messageId',
			    "plugins": "autolink link emoticons  textcolor mention",
			    "toolbar": "undo, redo | bold, italic, underline, strikethrough, subscript, superscript | forecolor backcolor emoticons | fontselect, fontsizeselect | bullist, numlist",
			    "menubar": false,
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
	
		$scope.setMailTemDefId =function(mailDefId)
		{
			$scope.selectedmailObj = $scope.mailDefIdObjMap[mailDefId];
		};
		
		$scope.listOfMailDefin = function()
		{
			actionHelper.invokeAction("mailTemplteDefinition.readAll", null, null,  readCallBack );		
		};
		
		 readCallBack =  function(read, response){
					
					$scope.mailDefinitionTemplates = read.model;
					
					$scope.mailDefIdObjMap = {};
					var index;
					var obj;
					
					for(index in $scope.mailDefinitionTemplates)
					{
						obj = $scope.mailDefinitionTemplates[index];
						
						$scope.mailDefIdObjMap[obj.id] = obj;
					}
					
					console.log($scope.mailDefinitionTemplates);

					console.log("$scope.mailDefi------" + read.model);
					try
					{
				    	$scope.$apply();
					}catch(ex)
					{}
					
			$scope.init();
		};
					//TODO: description and context attributes//json objects
						
		$scope.saveMailSubDesc =function()
		{
			console.log($scope.mailTempBody);
			var model = { "subject": $scope.mailTempSub, "body" : $scope.mailTempBody,"toAdmin": $scope.admin.toAdmin , "toProjectManager":$scope.projectManager.toProjectManager,
							"ccAdmin": $scope.ccAdmins.ccAdmin, "ccProjectManager": $scope.ccProjectManagers.ccProjectManager
							};
			
			actionHelper.invokeAction("mailTemplate.saveMailTemplate", model, null, function(read, Response){
			});
		};
				
		//TODO: save body 
		$scope.onType = function(e) {
		
			 $scope.message = $("#messageId").val();
			
		 };
		
		 //reset
		 $scope.resetTable = function()
		 {
			$scope.mailTempSub=null;
			$scope.mailTempBody=null;
			$scope.admin.toAdmin =null;
			$scope.ccAdmins.ccAdmin = null;
			$scope.ccProjectManagers.ccProjectManager = null;
			$scope.projectManager.toProjectManager = null;
		 };
}]);