$.application.controller('mailTemplateController', ["$scope", "crudController","utils","modelDefService","actionHelper",
                          function($scope, crudController,utils, modelDefService, actionHelper) {
	crudController.extend($scope, {
		"name": "MailTemplateDefinition",
		
		"nameColumn" : "name",
		
		"modelDailogId": "MailTemplateModel",
		
		
		"saveAction": "mailTemplate.save",
		"readAction": "mailTemplate.read",
	});
	
	$scope.templateModel={};
	
	$scope.model={};	
	 $scope.init = function() {
		 console.log("check");
			tinymce.init({
			    "selector": '#messageId',
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
	
		$scope.setMailTemplateConfigName =function(mailTemplateConfigName)
		{
			console.log(mailTemplateConfigName);
			$scope.mailTemplateConfigName = mailTemplateConfigName;
			console.log($scope.mailTemplateConfigName );
			
			actionHelper.invokeAction("mailTemplate.readByOwner", null, {"templateName" : $scope.mailTemplateConfigName}, 
					function(readResponse, respConfig)
					{
						$scope.templateModel = readResponse.model;
						 
						console.log(readResponse.model);
						console.log($scope.templateModel);
						
						try
						{
					    	$scope.$apply();
						}catch(ex)
						{}
						
					}, {"hideInProgress" : true}
			);
		};

		//TODO: description and context attributes//json objects
						
		$scope.saveMailSubDesc =function()
		{
			console.log($scope.mailTempBody);
			var model = {	"templateName" :  $scope.templateModel.templateName, 
							"subjectTemplate": $scope.templateModel.subjectTemplate, 
							"contentTemplate" :  $scope.templateModel.contentTemplate,
							"toListTemplate":$scope.templateModel.toListTemplate,
							"ccListTemplate":$scope.templateModel.ccListTemplate,
							"bccListTemplate":$scope.templateModel.bccListTemplate,
							"contentTemplate":$scope.templateModel.contentTemplate,
							"customization":null
			};
			console.log(model);
			
			actionHelper.invokeAction("mailTemplate.save", model, null, function(read, Response){
			});
		};
		 
		$scope.initTemplate =function(){
			$scope.selectedMailObjId = null;
			actionHelper.invokeAction("mailTemplateConfig.fetchNames", null, null, 
					function(basicReadListResponse, respConfig){
						
						$scope.mailTemplateConfigNames = basicReadListResponse.values; 
						$scope.selectedMailObjId = $scope.mailTemplateConfigNames;
						
					},{"hideInProgress" : true}
			);
			
		 };
}]);