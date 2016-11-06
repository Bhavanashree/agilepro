$.application.controller('holidayController', ["$scope", "crudController","utils","modelDefService","actionHelper", function($scope, crudController,utils,modelDefService,actionHelper) {
	crudController.extend($scope, {
		"name": "Holiday",
		"modelName": "HolidayModel",
		
		"nameColumn" : "date",
		
		"modelDailogId": "holidayDialog",
		
		"saveAction": "holiday.save",
		"readAction": "holiday.read",
		"updateAction": "holiday.update",
		"deleteAction": "holiday.delete",
	});
	
	$scope.holidays = {};
	$scope.testId ={};
	
	$scope.daysArray =[{"id" : "1", "name" : "SUNDAY" ,"check":false },{"id" : "2","name": "MONDAY", "check":false },{ "id" : "3","name": "TUESDAY", "check":false},{"id" : "4","name":"WEDNESDAY", "check":false},
	                   {"id" : "5","name": "THURSDAY", "check":false},{"id" : "6","name":"FRIDAY", "check":false},{"id" : "7","name": "SATURDAY", "check":false}];

	//save weekly holidays checkbox
	$scope.test = function(){
		
		$scope.value = [];
		
		var daysArrayLen = $scope.daysArray.length;
		
		for (i = 0; i < daysArrayLen; i++) 
		{
			console.log($scope.daysArray[i].check);
			
			if($scope.daysArray[i].check)
			{
				$scope.value.push($scope.daysArray[i].name);
				var test =$scope.value;
				$scope.selectedCheckValues =test;
				console.log($scope.selectedCheckValues);
			}
		}
		
		var model = {"days" : $scope.value};

		actionHelper.invokeAction("holiday.listOfDays", model, null, function(saveResponse, respConfig){
			$scope.daysAdded= saveResponse.model;
			
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}	
		
		})
};
	
	//read checkbox values
	$scope.fetchHolidaySetting = function(){
		console.log("test fetch");
		 $scope.displayHolidays();
	
		actionHelper.invokeAction("holiday.readListOfDays", null, null, 
			function(readResponse, respConfig){
			
				var daysModel = readResponse.model;
				
				if(daysModel)
				{
					var arrStr = daysModel.value.substring(1,daysModel.value.length-1);
					
					console.log(arrStr);
					
					var temp =  arrStr.split(",");
					
					for(index in temp)
					{
						temp[index] = temp[index].trim(); 
					}
					console.log(temp);
					
					for(var i=0; i<$scope.daysArray.length ; i++)
					{
						if(temp.indexOf($scope.daysArray[i].name) >= 0)
						{
							$scope.daysArray[i].check = true;
						}
					}
				}
		},{"hideInProgress" : true});
   };
	  
   //Save new Holidays for occasions
   	$scope.openHolidayModel = function(e)
	{
		console.log("openjholidaymodel");
		$scope.newModelMode = true;
		
		$scope.holidayModel = {};
		$scope.initErrors("holidayModel", true);
		
		utils.openModal("holidayDialog");
	
	};
   		
   	$scope.saveHoliday = function()
	{
		actionHelper.invokeAction("holiday.save", $scope.holidayModel, null, function(read, Response){
			 $('#holidayDialog').modal('hide');
			$scope.displayHolidays();
		 
		try
		{
    		$scope.$apply();
		}catch(ex)
		{}	
		});	
	};
  
	  	//read all added holidays   
	$scope.displayHolidays =function(){
		
		console.log("displayHolidays");
		
		actionHelper.invokeAction("holiday.readAll", null,null,  function(read, resp){	
			$scope.holidays = read.model;
		
			try
			{
	    		$scope.$apply();
			}catch(ex)
			{}	
		   });
	};
	
	var updateholidayCallback = function(readResponse, respConfig){
		console.log(readResponse);
	};
	
	$scope.editDays =function(obj){
		console.log(obj);
		
		$scope.initErrors("holidayModel", false);
		
		$scope.newModelMode = false;
		
		$scope.holidayModel = $.extend(true, {}, obj);
		
		utils.openModal('holidayDialog');

	};
	
	updateHolidayCallBack = function(readResponse, respConfig){
		 $('#holidayDialog').modal('hide');
		 $scope.displayHolidays();
	};
	
	$scope.updateHoliday = function(e){
		
		actionHelper.invokeAction("holiday.update", $scope.holidayModel , null,updateHolidayCallBack);
	};

	
	$scope.getSelectedHolidayId = function(holidayObj){
		$scope.selectedHolidayObj = holidayObj;
	};
	
	$scope.mainDelete =function(){
		
		if(!$scope.selectedHolidayObj)
		{
			$scope.holidayError  = {"error" : true, "message" : "Please select a holiday"};
			return;
		}else
		{
			$scope.holidayError = {};
		}
		
		$scope.commonDeleteHoliday();
	};
	
	$scope.deleteHoliday = function(){
		$scope.commonDeleteHoliday();
	};
	
	var deleteCallBack = function(readResponse, respConfig){
		 $scope.displayHolidays();

	};
	
	$scope.commonDeleteHoliday = function(){
		
		var deleteOp = $.proxy(function(confirmed) {
		
			if(!confirmed)
			{
				this.logger.trace("Delete operation is cancelled by user.");
				return;
			}
			else
			{
				//action helper
				actionHelper.invokeAction("holiday.delete", null, {"id" : $scope.selectedHolidayObj.id}, deleteCallBack, {"hideInProgress" : true});
				
			}
			
		}, {"$scope": $scope});
		
		utils.confirm(["Are you sure you want to delete Holiday with name '{}' ?", $scope.selectedHolidayObj.title], deleteOp);
	};
}]);