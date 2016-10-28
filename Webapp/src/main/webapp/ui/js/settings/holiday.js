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
	
	$scope.daysArray =[{"id" : "1", "name" : "SUNDAY" ,"check":false },{"id" : "2","name": "MONDAY", "check":false },{ "id" : "3","name": "TUESDAY", "check":false},{"id" : "4","name":"WEDNESDAY", "check":false},
	                   {"id" : "5","name": "THURSDAY", "check":false},{"id" : "6","name":"FRIDAY", "check":false},{"id" : "7","name": "SATURDAY", "check":false}];
	
	$scope.test = function(){
		var daysArrayLen = $scope.daysArray.length;
		
		$scope.value = [];
		
		for (i = 0; i < daysArrayLen; i++) 
		{
			if($scope.daysArray[i].check)
			{
				$scope.value.push($scope.daysArray[i].name);
				console.log($scope.value);
			}
		}
		
		var model = {"days" : $scope.value};

		actionHelper.invokeAction("holiday.listOfDays", model, null, saveCommentCallBack);
	}
	
	  var saveCommentCallBack = function(saveResponse, respConfig){
			
	   };
	
	   $scope.fetchHolidaySetting = function(){
			  console.log("test fetch");
			  actionHelper.invokeAction("holiday.readListOfDays", null, null, 
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
	   };
	
	   $scope.displayHolidays =function(){
		console.log("displayHolidays");
		actionHelper.invokeAction("holiday.readAll", null,null, readCmtscallBack);
	   };
}]);