$.application.controller('notificationSettingsController', ["$scope", "crudController","utils","modelDefService","actionHelper",
                          function($scope, crudController,utils, modelDefService, actionHelper) {
	crudController.extend($scope, {
		"name": "NotificationSettings",
		
		"nameColumn" : "name",
		
		"modelDailogId": "NotificationModel",
		
		
		"saveAction": "notificationSettings.save",
		"readAction": "notificationSettings.read",
	});
	
	
	var searchName; 
	
	$scope.notifiObjs = {};
	

	//search fliter
	$scope.notifiFilter = function(){
		
		return function( item ) {
			
			if($scope.notificationMode)
			{
				console.log($scope.notificationMode);
				return item.notificationType.toLowerCase().includes($scope.notificationMode.toLowerCase());
			}
			
		    return true;
		  };
	};
	
	
	$scope.fetchNotifications = function(){
		
		actionHelper.invokeAction("notificationSettings.read", null, null, 
				function(basicReadListResponse, respConfig){
			
					$scope.notifiObjs = basicReadListResponse.model;
					
					$scope.notificationSettings = [];
					
					try
						{
					    	$scope.$apply();
						}catch(ex)
						{}
						
					},{"hideInProgress" : true}
			);
	};
	
	
	$scope.saveNotificationSettings = function()
	{
		for(index in $scope.notifiObjs)
		{
			var notificationsObj = $scope.notifiObjs[index];
			console.log(notificationsObj);
			//if(notificationsObj.enabled.indexOf(notificationsObj) == -1)	
				
			if(notificationsObj.enabled == true)
			{
				var notificationModel = {"notificationType" : notificationsObj.notificationType,"enabled" : notificationsObj.enabled};
				
				$scope.notificationSettings.push(notificationModel);

				console.log("$scope.notificationSettings" , $scope.notificationSettings);
			}
			
		}
			
		var model = {"notificationSettings" : $scope.notificationSettings}
		
		actionHelper.invokeAction("notificationSettings.save", model, null, function(read, Response){
			
			$scope.notificationSettings = [];
			$scope.fetchNotifications();
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}	
		});
	};
	
	
	$scope.searchNotification = function(notificationName){

		$scope.notifiObjMap = {};
		if(notificationName)
		{
		searchName = notificationName;
		console.log("$scope.notifiSearch");
		actionHelper.invokeAction("notificationSettings.read", null, {"notificationType" : notificationName}, readNotificallBack, {"hideInProgress" : true});
		return;
	}
	
		actionHelper.invokeAction("notificationSettings.read", null, null, readNotificallBack, {"hideInProgress" : true});
	};

	var readNotificallBack = function(readResponse, respConfig){

		$scope.notifiSearch = readResponse.model;

		console.log($scope.notifiSearch);
		
		if($scope.notifiSearch.length == 0)
		{
			if(searchName)
			{
				utils.alert("The type provided doesnt match");
			}
			else
			{
				utils.alert(" there are no notifications");
			}
			
			return;
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
		
		var index;
		var obj;
		for(index in $scope.notifiSearch)
		{
			obj = $scope.notifiSearch[index];
			
			$scope.notifiObjMap[obj.id] = obj ;
		}
	}
	
	$scope.onTypeOfSearch = function(event)
	{
		var key = event.keyCode ? event.keyCode : event.which;
		
		if(key == 13)
		{

			console.log("$scope.notifiSearch");
			$scope.searchNotification($scope.notificationMode);
		}
	}
	
}]);