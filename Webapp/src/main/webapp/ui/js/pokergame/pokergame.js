$.application.controller('pokerGameController', ["$scope", "crudController","utils","modelDefService","actionHelper", 
                                              function($scope, crudController,utils,modelDefService,actionHelper) {
	
	$scope.listOfSeriesArr =[{"id" : "1", "name" : "FIBONACCI SERIES" ,},{"id" : "2","name": "EVEN" },
	                         { "id" : "3","name": "SEQUENTIAL"}];

	var projectId;
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		//$scope.readListOfUnassignedStories();
		$scope.pokerRead();
	   
	});

	$scope.readListOfUnassignedStories = function(){
		
		projectId = $scope.getActiveProjectId();
		
		actionHelper.invokeAction("story.readByProjectIdAndStatus", null, {"projectId" : projectId}, 
			function(readResponse, respConfig){
			
				$scope.stories= readResponse.model;
				console.log($scope.stories);
				var index;				
				
				$scope.storyIdObj = {};
				
					for(index in $scope.stories)
					{
						if($scope.stories[index].status === "null")
						{
						console.log($scope.stories[index].status);
						var storyObj = $scope.stories[index];
						$scope.storyIdObj[storyObj.id] = storyObj;
						console.log(storyObj);
						}
					}			
				$scope.$apply();	
		//	console.log($scope.stories);
		},{"hideInProgress" : true});
	};
	
	$scope.selectedStory = function(id , name)
	{
		console.log(id);
		
		$scope.selectedStoryId = id;
		
		$scope.selectedStoryName = name;
	}

	$scope.onSelectOfSeries  = function(name){
		  
		$scope.selectedSeries = name;
		
		   if($scope.countno > 0 && $scope.selectedSeries)
			{	   
			   if(name == "FIBONACCI SERIES")
			   {
				  $scope.listOfBox = $scope.fibonacciSeries($scope.countno);	 
						
				}else if(name == "SEQUENTIAL")
				{
					$scope.listOfBox = $scope.sequentialNo($scope.countno);
				}else 
				{
					return;
				}
			}else
			{
				utils.alert("Please Select a count value for the series Selections");
			}
	};
	   
	$scope.fibonacciSeries = function(n){
	
		if (n==1) 
	    {
	       return [0, 1];
	    } 
	    else 
	    {
	    	 var s = $scope.fibonacciSeries(n-1);
	    	 s.push(s[s.length - 1] + s[s.length - 2]);
	    	 return s;
	    }
	};
	   
	$scope.sequentialNo = function(value){
		if(value > 0)
		 {
			 var seqArr =[];
			 
			for(var i = 0 ; i< value ; i++)
			{
				seqArr.push(i);
				 console.log(seqArr[i]);	
			}
			
			return seqArr;
		}else
		{
			utils.alert("Please Select a count");
		}
	 };
	
	$scope.listOfusers = function()
	{
		
		readProjectMembersByProjectId
	 	
		actionHelper.invokeAction("projectMember.readProjectMembersByProjectId", null, {"projectId" : projectId}, 
				function(readResponse, respConfig){
				
					$scope.stories= readResponse.model;
					var index;				
					
					$scope.storyIdObj = {};
					
					for(index in $scope.stories)
					{
						var storyObj = $scope.stories[index];
						$scope.storyIdObj[storyObj.id] = storyObj;
					}
										
					$scope.$apply();	
				console.log($scope.stories);
			},{"hideInProgress" : true});
		
	};
	
	$scope.savePokerGameAndUserId = function(){
		
		var model = {"members" : null , "pokerGame" :null };
		actionHelper.invokeAction("pokerGameUser.save" , model , null ,function(read , response){
			
		})
		
	};
	
	$scope.pokerRead = function(){
		console.log("displaygames");

		actionHelper.invokeAction("pokerGame.readAll", null,null,  function(read, resp){	
			$scope.pokerGame = read.model;

			if($scope.pokerGame === " null")
			{
				console.log("null game")
			}
			try
			{
				$scope.$apply();
			}catch(ex)
			{}	
		});
	}
	
}]);