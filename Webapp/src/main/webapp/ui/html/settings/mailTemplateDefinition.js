$.application.controller('mailTemplteDefinitionController', ["$scope", "crudController","utils","modelDefService","actionHelper",
                          function($scope, crudController,utils, modelDefService, actionHelper) {
	crudController.extend($scope, {
		"name": "MailTemplateDefinition",
		
		"nameColumn" : "name",
		
		"modelDailogId": "mailTemplteDefinition",
		
		
		"saveAction": "mailTemplteDefinition.save",
		"readAction": "mailTemplteDefinition.read",
	});
	
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
				};
				//TODO: description and cotext attributes//json objects
					
	$scope.saveMailSubDesc =function()
	{
		console.log("sssssss");
		
		console.log($scope.mailTempSub);
		
		vmodel = { "subject": $scope.mailTempSub, "body" : $scope.mailTempBody};
		
		actionHelper.invokeAction("mailTemplate.save", model, null, function(read, Response){
			
			console.log(read.model);
			
		});
		
	};
				
				
				
				
	
}]);