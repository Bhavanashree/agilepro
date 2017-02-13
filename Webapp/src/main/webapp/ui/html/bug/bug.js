$.application.controller('bugController', ["$scope", "crudController","validator", "utils","modelDefService","actionHelper", 
                                           function($scope, crudController,validator,utils,modelDefService,actionHelper) {
	//dropdowns array
	$scope.commentStatus = [{"name" : "Comment"},{"name": "reopen"}, {"name": "closed"}, {"name":"deferred"}];
		
	//	$scope.selectedStatus={};
	$scope.selectedEmpIdToasign={};
	
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
	
	// Bug dialog
	
	/**
	 * Gets invoked on click of plus button
	 */
	$scope.openNewBugDialog = function(event){
	
		$scope.$broadcast("openNewBugDialog");
		
	}; 
	
	/**
	 * Gets invoked on click of edit button.
	 */
	$scope.openEditBugDialog = function(event, bugId){
		
		console.log(bugId);
		
		//$scope.$broadcast("openEditBugDialog", bugId);
	};
	
	var saveCommentCallBack =function(readResponse, respConfig){
		
		$scope.displayComments();
		
	}
	
	//save comments dropdown
	$scope.onCommentedBtn =function(){
		
		var content = tinymce.activeEditor.getContent();
		$scope.comment = content; 
		
		console.log("Got message as ", $scope.comment);
		
		 bugModel = {"content": $scope.comment.trim() , "bugId" :  $scope.bugId , "commentStatus" : $scope.selectedStatus.name};

		actionHelper.invokeAction("bugComment.save", bugModel, null, saveCommentCallBack);
	};
	
	//save owner dropdown
	$scope.onCommentedBtnOwner =function(){
		
		var content = tinymce.activeEditor.getContent();
		$scope.comment = content; 
		
		console.log("Got message as ", $scope.comment);
		
		 bugModel = {"content": $scope.comment.trim() , "bugId" :  $scope.bugId , "commentStatus" : "Reassigned"}; 
		actionHelper.invokeAction("bugComment.save", bugModel, null, saveCommentCallBack);
	};
	
	var readCmtscallBack = function(read, resp){	
		
		$scope.commentRead = read.model;
		
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}	
	}
	
	//read comments as per bugid
	$scope.displayComments =function(){
		
		console.log("displaycommens");
		actionHelper.invokeAction("bugComment.readByBugId", null,{"bugId" :  $scope.bugId}, readCmtscallBack);
	};
	
	//readAllemployees
	var readEmployeecallBack  = function(readResponse , respConfig)
	{
		$scope.employees = readResponse.model;
		
		$scope.employeeIdObjMap = {};
		
		for(index in $scope.employees)
		{
			$scope.employeeIdObjMap[$scope.employees[index].id] = $scope.employees[index];
		}
		console.log($scope.employees);
		
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}		
	
	}   
	//listOfemployess
	$scope.listOfEmployees = function(){
		
		actionHelper.invokeAction("employee.readAll", null, null, readEmployeecallBack);
	};
	
	$scope.setStatus = function(obj){
		$scope.selectedStatus=obj; 
		console.log($scope.selectedStatus.name);
		$scope.onCommentedBtn();
	};
	
	$scope.assignedBugOwner = function(emObj)
	{
		$scope.selectedEmpIdToasign.name = emObj;
		
		 $scope.onEditOfBug(emObj);
		
		$scope.onCommentedBtnOwner();
		
	};
	
	$scope.onEditOfBug = function(obj){
	
		obj = $scope.bugId;
		$scope.bugObj = obj;
		$scope.bugOwner.owner = obj;
		actionHelper.invokeAction("bug.update", $scope.bugOwner, null,updateBugId);
	};
	//part 3	
	var updateBugId = function(readResponse, respConfig)
	{
		$scope.bugOwner.version = readResponse.version;
	
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}		
	};
	
	// modelde
	$scope.initModelDef = function() {
		
		modelDefService.getModelDef("BugModel", $.proxy(function(modelDefResp){
			this.$scope.modelDef = modelDefResp.modelDef;
			
			console.log("Attachment model");
		}, {"$scope": $scope}));
		
		modelDefService.getModelDef("BugAttachmentModel", $.proxy(function(modelDefResp){
			this.$scope.attachmentModelDef = modelDefResp.modelDef;
			
			console.log("Attachment model");
		}, {"$scope": $scope}));
	};
	
	$scope.getModelDef = function(prefix) {
	
		if(prefix == 'bugAttachmentModel')
		{
			return $scope.attachmentModelDef;
		}
		
		return $scope.modelDef;
	};
	
	//add attachment
	
	$scope.addAttachment = function(){
		
		$scope.newModelMode = true;
		$scope.bugAttachmentModel = {};
		$scope.initErrors("bugAttachmentModel", true);
		 
		utils.openModal('bugAttachmentModelDialog');
	};
	
	
	saveAttachmentCallBack = function(readResponse, respConfig){
		 
		 $('#bugAttachmentModelDialog').modal('hide');
		 
		 getAllAttachment();
	 };
	 
	$scope.saveAttachment = function(e){
		
		$scope.initErrors("bugAttachmentModel", false);
		
		$scope.bugAttachmentModel.bugId =  $scope.bugId;
		
		if(!validator.validateModel($scope.bugAttachmentModel, $scope.attachmentModelDef, $scope.errors.bugAttachmentModel))
		{
			utils.alert("Please correct the errors and then try!", function(){
				$('body').addClass('modal-open');
			});
			
			return;
		}
		
		if(!$scope.bugAttachmentModel.file && !$scope.bugAttachmentModel.link)
		{
			utils.alert("Please provide at least one value for link or file or both");
			return;
		}
		
		actionHelper.invokeAction("bugAttachment.save", $scope.bugAttachmentModel, null, saveAttachmentCallBack, {"hideInProgress" : true});
	};
	
	readAttachmentCallBack = function(readResponse, respConfig){
		 
		$scope.attachments = readResponse.model;
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}	
	 };
	 
	getAllAttachment = function(){
		
		actionHelper.invokeAction("bugAttachment.readAll", null, {"bugId" : $scope.bugId}, readAttachmentCallBack, {"hideInProgress" : true});
	};
	
	$scope.editAttachment = function(obj){
		
		$scope.initErrors("bugAttachmentModel", false);
		
		$scope.newModelMode = false;
		
		$scope.bugAttachmentModel = $.extend(true, {}, obj);
		
		utils.openModal('bugAttachmentModelDialog');
	};
	
	updateAttachmentCallBack = function(readResponse, respConfig){
		 $('#bugAttachmentModelDialog').modal('hide');
		 
		getAllAttachment();
	};
	
	$scope.updateAttachment = function(e){
		
		if(!$scope.bugAttachmentModel.file && !$scope.bugAttachmentModel.link)
		{
			utils.alert("Please provide at least one value for link or file or both");
			return;
		}
		
		actionHelper.invokeAction("bugAttachment.update", $scope.bugAttachmentModel, null, updateAttachmentCallBack, {"hideInProgress" : true});
	};
	
	deleteAttachmentCallBack = function(readResponse, respConfig){
		 
		getAllAttachment();
	};
	
	$scope.removeAttachment = function(obj){
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("bugAttachment.delete", null, {"id" : obj.id}, deleteAttachmentCallBack, {"hideInProgress" : true});
			}
			
			try
			{
				this.$scope.$parent.$digest();
			}catch(ex)
			{}
			
		}, {"$scope": $scope});

		utils.confirm(["Are you sure you want to delete attachment with name '{}' ?", obj.title], deleteOp);
	};

}]);