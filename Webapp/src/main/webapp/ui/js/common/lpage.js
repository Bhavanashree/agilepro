$.application.controller('lpageHelper', ["$scope", "clientContext", "utils", "actionHelper", function($scope, clientContext, utils, actionHelper) {
	var lpageConfig = null;
	
	lpageConfig = clientContext.invokeGetApi($.appConfiguration.lpageApiUrl, {"loginUrl": window.location.href}, $.proxy(function(lpageConfig, resConfig){
		if(!resConfig.success)
		{
			console.error("An error occurred while fetching landing page customization.");
			console.error(lpageConfig);
			
			if(lpageConfig && lpageConfig.message)
			{
				alert("An error occurred while contacting server. Error - " + lpageConfig.message);
			}
			
			return;
		}

		clientContext.addAuthAttribute("customerId", lpageConfig.customerId);
	    this.$scope.customerName = lpageConfig.customerName;
//	    this.$scope.slideImages = lpageConfig.slideImages;
//	    this.$scope.contentLinks = lpageConfig.contentLinks;
//	    this.$scope.newsLst = lpageConfig.news;
	    this.$scope.customerId = lpageConfig.customerId;
	   
//	    
//	    for(var i = 0; i < this.$scope.slideImages.length; i++)
//	    {
//	    	//if image path is not found and file is present.. set dynamic path
//	    	if(!this.$scope.slideImages[i].imagePath && this.$scope.slideImages[i].imageFile)
//	    	{
//	    		this.$scope.slideImages[i].imagePath = actionHelper.actionUrl("files.fetch.insecure", {"id": this.$scope.slideImages[i].imageFile.id});
//	    	}
//	    }
	    
	    try
	    {
	    	this.$scope.$digest();
	    }catch(ex)
	    {}
	    
	    $(".preProcessHidden").removeClass("preProcessHidden");
	}, {"$scope": $scope}));
    
    $scope.email = "";
    $scope.password = "";
    
    $scope.authenticate = function(e){
    	try
    	{
    		clientContext.authenticate($scope.email, $scope.password);
    		$scope.password = "";
    		
    		window.location.href = $.appConfiguration.indexPageUrl;
    		e.preventDefault();
    		return false;
    	}catch(ex)
    	{
    		alert(ex);
    		e.preventDefault();
    		return false;
    	}
    };
    $scope.showPopup = function(e){
    	console.log("Show popul method...");
    	utils.openModal("resetPasswordDlg", {
			context: {"$scope": $scope},
			
			"onShow": function(){
				$("#resetPasswordDlg input").first().focus();
			}
		});
    	
		e.preventDefault();
		return false;
	};
    
    /**
     * Used to reset password of the current user
     */
	$scope.resetPassword = function(e) {
		var mailPattern = /^[\w\-\.]+\@[\w\-\.]+\.[A-Za-z0-9]+$/;
		
		//validate input mail id
        if(!mailPattern.test($scope.mailId)) 
        {
            alert('Invalid mail id specified. Please provide valid mail id and retry!');
            return;
        }
        
        //define reset call back method
		var resetCallback = function(response, resConfig) {
			//on failure, display error
			if(!resConfig.success)
			{
				alert("Specified email id does not exists. Please verify your mail id and retry!");
				return;
			}

			//on success hide the reset modal
			$('#resetPasswordDlg').modal('hide');
			alert("Password is reset successfully and sent to your mail id!");
		};	
		
		//invoke server action method
		actionHelper.invokeAction("lpage.passwordReset", null, {
			"mailId" : $scope.mailId,
			"customerId": $scope.customerId
		}, resetCallback);		
	};
    
}]);