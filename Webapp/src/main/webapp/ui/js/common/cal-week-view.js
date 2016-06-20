$.application.controller('weekCalendarController', ["$scope", "clientContext", function($scope, clientContext){

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
	
}]);	