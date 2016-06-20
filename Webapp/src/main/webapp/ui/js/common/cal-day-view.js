$.application.controller('calendarController', ["$scope", "clientContext", function($scope, clientContext){

	$scope.range=function(min,max)
	{
	   var input=[];
	    for(var i=min;i<max;i++)
		 {
		 input.push(i);
		 }
	    
	    console.log("Range: ");
	    console.log(input);
         return input;
	};
	
	
	$scope.date = moment().format("DD/MM/YYYY");
		
		
	$scope.prevDate=function(e)
		{
			$scope.date=moment($scope.date,"DD/MM/YYYY").subtract(1,'days').format("DD/MM/YYYY");
			
			console.log("date");
		};
		
		
	$scope.nextDate=function(e)
		{
			$scope.date =moment($scope.date,"DD/MM/YYYY").add(1, 'days').format("DD/MM/YYYY");
			console.log("date");
		};
		
		
	$scope.todayDate=function(e)
		{
			$scope.date = moment().format("DD/MM/YYYY");
			console.log("date");
			
		};
	
}]);	
	