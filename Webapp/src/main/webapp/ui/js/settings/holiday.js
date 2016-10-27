$.application.controller('holidayController', ["$scope", "crudController","utils","modelDefService","actionHelper", function($scope, crudController,utils,modelDefService,actionHelper) {
	crudController.extend($scope, {
		"name": "Holiday",
		"modelName": "HolidayModel",
		
		"nameColumn" : "name",
		
		"modelDailogId": "holidayDialog",
		
		"saveAction": "holiday.save",
		"readAction": "holiday.read",
		"updateAction": "holiday.update",
		"deleteAction": "holiday.delete",
	});
	
	$scope.daysArray =[{"id" : "1", "name" : "Monday" ,"check":false },{"id" : "2","name": "Tuesday", "check":false },{ "id" : "3","name": "Wednesday", "check":false},{"id" : "4","name":"Thursday", "check":false},
	                   {"id" : "5","name": "Friday", "check":false},{"id" : "6","name":"Saturday", "check":false},{"id" : "7","name": "Sunday", "check":false}];
	
	$scope.test = function()
	{
		console.log($scope.daysArray);
	
		var fLen = $scope.daysArray.length;
		
		$scope.value = [];
		
		for (i = 0; i < fLen; i++) 
		{
			if($scope.daysArray[i].check)
			{
				$scope.value.push($scope.daysArray[i].name);
				console.log($scope.value);
			}
		}
		
		var model = {"listOfValues" : $scope.value};

		actionHelper.invokeAction("customerSetting.save", model, null, saveCommentCallBack);
	}
	
	  var saveCommentCallBack = function(saveResponse, respConfig){
			
	    };
	
	
	  $scope.fetchHolidaySetting = function()
	  {
		  console.log("test fetch");
		  actionHelper.invokeAction("customerSetting.read", null, null, 
			function(readResponse, respConfig)
			{
			  var daysModel = readResponse.model;
			  console.log(daysModel.value);
			  
			 var array = JSON.parse("[" + daysModel.value + "]");
			  
			  if(readResponse.model.length > 0)
				{
				  //  check box
				  
				  
				}
			}
		 ,{"hideInProgress" : true});
	  };
	  
	$scope.holidays ={};
	var readCmtscallBack = function(read, resp){	
		$scope.holidays = read.model;
		console.log(read.model);
		console.log($scope.holidays);
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}	
	}
	$scope.displayHolidays =function(){
		console.log("displayHolidays");
		actionHelper.invokeAction("holiday.readAll", null,null, readCmtscallBack);
	};
	
}]);