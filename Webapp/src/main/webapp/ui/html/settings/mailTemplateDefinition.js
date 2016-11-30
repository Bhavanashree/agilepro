$.application.controller('mailTemplateController', ["$scope", "crudController","utils","modelDefService","actionHelper",
                          function($scope, crudController,utils, modelDefService, actionHelper) {
	crudController.extend($scope, {
		"name": "MailTemplateDefinition",
		
		"nameColumn" : "name",
		
		"modelDailogId": "MailTemplateModel",
		
		
		"saveAction": "mailTemplate.save",
		"readAction": "mailTemplate.read",
	});
	
	$scope.templateConfigNameAndDesc = false;
	
	$scope.templateModel = {};
	
	$scope.mailTempConfigObj = {};
	
	$scope.model={};

	$scope.init = function() {	 
		try
		{
			tinymce.remove();
		}catch(ex)
		{
			//ignoring this error, which can happen before tinymce is not initialize
			//on target id
		}
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
	/*
	 * Invoked after selecting template name
	 */
	$scope.readMailTemplateName =function(mailTemplateName){
		
		$scope.mailTemplateNameObj = mailTemplateName;
		console.log($scope.mailTemplateNameObj);
		
		actionHelper.invokeAction("mailTemplate.readByOwner", null, {"templateName" : $scope.mailTemplateNameObj}, 
				function(readResponse, respConfig)
				{			
					$scope.templateModel = readResponse.model;
					
					if(!$scope.templateModel.toRecipientInfo)
					{
						$scope.templateModel.toRecipientInfo = {};
					}
					
					if(!$scope.templateModel.ccRecipientInfo)
					{
						$scope.templateModel.ccRecipientInfo = {};
					}

					if(!$scope.templateModel.toRecipientInfo.teamIds)
					{
						$scope.templateModel.toRecipientInfo.teamIds = [];
					}else
					{
						for(index in $scope.teams)
						{
							if($scope.templateModel.toRecipientInfo.teamIds.indexOf($scope.teams[index].id) != -1)
							{
								$scope.teams[index].check = true;
							}
						}
					}
					
					if(!$scope.templateModel.ccRecipientInfo.teamIds)
					{
						$scope.templateModel.ccRecipientInfo.teamIds = [];
					}else
					{
						for(index in $scope.teamsCC)
						{
							if($scope.templateModel.ccRecipientInfo.teamIds.indexOf($scope.teamsCC[index].id) != -1)
							{
								$scope.teamsCC[index].check = true;
							}
						}
					}
					
					tinymce.get('messageId').setContent($scope.templateModel.contentTemplate);
					
					$scope.fetchMailTemplateConfigValues();

				}, {"hideInProgress" : true}
					
			);
	};

	// To fetch the Description and context attribute 
	$scope.fetchMailTemplateConfigValues = function(){
		
		$scope.templateConfigNameAndDesc =true;
		
		actionHelper.invokeAction("mailTemplateConfig.fetch", null, {"name" : $scope.templateModel.templateName}, 
				function(basicReadListResponse, respConfig){
					$scope.mailTempConfigObj = basicReadListResponse.model;
						try
						{
					    	$scope.$apply();
						}catch(ex)
						{}
						
					},{"hideInProgress" : true}
			);
	};
		
	//To save or update As per user requirement
	$scope.saveMailSubDesc =function()
	{ 
		for(index in $scope.teams)
		{
			var teamObj = $scope.teams[index];
			
			if(teamObj.check && $scope.templateModel.toRecipientInfo.teamIds.indexOf(teamObj.id) == -1)
			{
				$scope.templateModel.toRecipientInfo.teamIds.push(teamObj.id);
			}
		}
		
		console.log($scope.teamsCC);
		
		for(index in $scope.teamsCC)
		{
			var teamccObj = $scope.teamsCC[index];
			
			if(teamccObj.check && $scope.templateModel.ccRecipientInfo.teamIds.indexOf(teamObj.id) == -1)
			{
				console.log(teamccObj);
				$scope.templateModel.ccRecipientInfo.teamIds.push(teamccObj.id);
			}
		}
		
		
		$scope.templateModel.contentTemplate = tinymce.get('messageId').getContent();
		
		actionHelper.invokeAction("mailTemplate.save", $scope.templateModel, null, function(read, Response){
	
			$scope.readMailTemplateName($scope.templateModel.templateName);
			
		});		
	};
	
	//fetch the Templates 
	$scope.initTemplate =function(){
		
		$scope.selectedMailObjId = null;
		
		actionHelper.invokeAction("mailTemplateConfig.fetchNames", null, null, 
				function(basicReadListResponse, respConfig){
			
					$scope.mailTemplateConfigNames = basicReadListResponse.values; 
					try
					{
				    	$scope.$apply();
					}catch(ex)
					{}
					 $scope.FetchAllTeams();
				},{"hideInProgress" : true}
		);			
	 };
	 
	 //fetching all the teams.
	 $scope.FetchAllTeams = function(){
	 
		 actionHelper.invokeAction("projectTeam.readAll", null,null,  function(read, resp){	
			$scope.teams = read.model;
			
			
			$scope.teamsCC = angular.copy( $scope.teams);
			
			console.log($scope.teamsCC);
			
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}	
		   });
	 };
	 
	 //reset to default delete current template
	 $scope.resetToDefault = function(){
		 console.log( $scope.templateModel);
		 
		 actionHelper.invokeAction("mailTemplate.delete", null, {"id" : $scope.templateModel.id}, function(read, Response){
				
				$scope.readMailTemplateName($scope.templateModel.templateName);
		 });
	 };
}]);