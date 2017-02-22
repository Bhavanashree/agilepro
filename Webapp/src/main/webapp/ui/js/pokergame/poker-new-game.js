$.application.controller("pokerNewGameController", ["$scope", "actionHelper", 
                                             function($scope, actionHelper){
	
	// Listener for broadcast
	$scope.$on("startNewPokerGame", function(event, args) {
		
		// start new game
		$scope.startNewGame(); 
	});
	
	
	// Listener for broadcast
	$scope.$on("addNewPokerGameUser", function(event, args) {
		
		var pokerGameUser =  {"projectId" : $scope.getActiveProjectId(),
							  "pokerGameId" : $scope.getPokerGameId(),
							  "userId" : $scope.activeUser.userId};
		
		actionHelper.invokeAction("pokerGameUser.save", pokerGameUser, null, 
				function(saveResposne, respConfig)
				{
					$scope.addPokerUserAfterSave();
					
				}, {"hideInProgress" : true});
	});
	
	// Listener for broadcast
	$scope.$on("fetchPokerGameStatus", function(event, args) {
		
		$scope.fetchPokerGameStatus(); 
	});
	
	/**
	 * Initialize new poker game.
	 */
	$scope.initPokerNewGame = function(){
		
		$scope.fetchStories();
		
	};
	
	/**
	 * Fetch stories.
	 */
	$scope.fetchStories = function(){
		
		projectId = $scope.getActiveProjectId();
		
		if(projectId != -1)
		{
			actionHelper.invokeAction("story.readByProjectId", null, {"projectId" : projectId}, 
					function(readResponse, respConfig){
					
						$scope.stories = readResponse.model;
						
						var index, storyObj;
						
						if($scope.stories.length > 0)
						{
							for(index in $scope.stories)
							{
								storyObj = $scope.stories[index];
								
								if(!storyObj.storyPoints)
								{
									$scope.selectedStoryId = storyObj.id;
									break;
								}
							}
							
							if(!$scope.selectedStoryId)
							{
								$scope.selectedStoryId = $scope.stories[0].id;
							}
						}
						
						$scope.displayPokerGame();
						
						try
				 		{
				 			$scope.$apply();
				 		}catch(ex)
				 		{}
						
				},{"hideInProgress" : true});
		}
	};
	
	
	/**
	  * Start a new poker game.
	  */
	 $scope.startNewGame = function(){
		 
		var model = {"gameSeries" : $scope.getSelectedSeries(),
					 "projectId" : $scope.getActiveProjectId(), "userId" : $scope.activeUser.userId, 
					 "numberOfCards" : $scope.getNumberOfCards()};
		
		if($scope.selectedStoryId)
		{
			model["storyId"] = $scope.selectedStoryId; 
			model["pokerGameStoryStatus"] = "STORY_SELECTED";
		}else
		{
			model["pokerGameStoryStatus"] = "STORY_NOT_SEELCTED";
		}
		 
		 actionHelper.invokeAction("pokerGame.save", model, null,
				function(saveResponse, respConfig)
				{
			 		if(saveResponse.code == 0)
			 		{
			 			$scope.setGameStarted();
			 			
			 			$scope.displayPokerGame();
			 		}
			 		
			 		try
			 		{
			 			$scope.$apply();
			 		}catch(ex)
			 		{}
		 		}
		 , {"hideInProgress" : true});
		 
	 };
	
	 /**
	  * Fetch poker game status.
	  */
	 $scope.fetchPokerGameStatus = function(){
		 
		 console.log("fetchPokerGameStatus");
		 
		 actionHelper.invokeAction("pokerGameUser.readPokerGameStatus", null, {"pokerGameId" : $scope.getPokerGameId()},
					function(saveResponse, respConfig)
					{
				 		
			 		}
			 , {"hideInProgress" : true});
		 
	 }; 
	 
	 $scope.displayPokerGame = function(){
		
		 $scope.selectedSeries = $scope.getSelectedSeries();
		 
		 $scope.numberOfCards = $scope.getNumberOfCards();
		 
		 switch($scope.selectedSeries)
			{
				case "FIBONACCI":
					{
						$scope.cardValues = $scope.fibonacciSeries();
				  		break;
					}
					
				case "EVEN":
					{
						$scope.cardValues = $scope.evenSeries();
						break;
					}
				case "SEQUENTIAL":
					{
						$scope.cardValues = $scope.sequentialSeries();
						break;
					}
			}
		 
		$scope.cardValues.splice(0, 0, '?', "0");
		$scope.cardValues.push("\u221E");
	 };
	 
 	/**
	 * Fibbonacci series.
	 */
	$scope.fibonacciSeries = function(){
	
		var fibboArr = [0, 1];
		var a = 0;
		var b = 1;
		
		if($scope.numberOfCards == 1)
		{
			return [1];
		}else if($scope.numberOfCards == 2)
		{
			return [1, 2];
		}
		
		for(var i=2; i<$scope.numberOfCards ; i++)
		{
			fibboArr[i] = fibboArr[i-2] + fibboArr[i-1];
		}
		
		return fibboArr;
	};
	
	/**
	 * Sequential series.
	 */
	$scope.sequentialSeries = function(){
		
		var sequentialArr = [];
		
		for(var i=0; i<$scope.numberOfCards ; i++)
		{
			sequentialArr[i] =  i + 1;
		}
		
		return sequentialArr;
	 };
	
	 /**
	  * Even series.
	  */
	 $scope.evenSeries = function(){
		 
		 var evenSeries = [];
		 var evenNumber = 0;
		 
		 for(var i=0; i<$scope.numberOfCards ; i++)
		 {
			evenSeries[i] = evenNumber;
				
			evenNumber += 2; 
	 	 }
		 
		 return evenSeries;
	 };
		 
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		$scope.initPokerNewGame();
	});
	
	 		
}]);