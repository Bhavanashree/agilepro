$.application.controller('pokerGameController', ["$scope", "actionHelper", 
                                              function($scope, actionHelper) {
	/**
	 * List of game series for drop down.
	 */
	$scope.listOfGameSeries =["FIBONACCI", "SEQUENTIAL", "EVEN"];

	$scope.gameStarted = false;
	$scope.gameNotStarted = false;
	$scope.isUserScrumMaster = false;
	$scope.isUserNonScrumMaster = false;
	$scope.nonScrumMasterJoined = false;
	
	$scope.pokerGameError = {"error" : false, "message" : ""};
	
	var projectId;
	
	/**
	 * Initialize the poker game page.
	 */
	$scope.initPokerGame = function(){
		
		$scope.gameStarted = false;
		$scope.gameNotStarted = false;
		$scope.isUserScrumMaster = false;
		$scope.isUserNonScrumMaster = false;
		$scope.nonScrumMasterJoined = false;
		
		if($scope.activeUser && ($scope.getActiveProjectId() != -1))
		{
			if($scope.activeUser.roles.indexOf("SCRUM_MASTER") >= 0)
			{
				$scope.isUserScrumMaster = true;
			}else
			{
				$scope.isUserNonScrumMaster = true;
			}
			
			
			actionHelper.invokeAction("pokerGame.isPokerGameStarted", null, {"projectId" :  $scope.getActiveProjectId()},
					function(readResponse, respConfig)
					{
						if(readResponse.model)
						{
							$scope.pokerGame = readResponse.model; 
							$scope.gameStarted = true;
						}else
						{
							$scope.gameNotStarted = true;	
						}
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					});
		}
	};
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		$scope.initPokerGame();
	});

	/**
	 * Gets invoked on selection of series.
	 */
	$scope.onGameSeriesChange  = function(name){
		  
		$scope.selectedSeries = name;
		
		if($scope.selectedSeries)
		{
			$scope.pokerGameError.error = false;
		}
	};
	   
	/**
	 * Gets invoked on click of start button.
	 */
	$scope.startTheGame = function(){
		
		if(!$scope.selectedSeries)
		{
			$scope.pokerGameError.error = true;
			$scope.pokerGameError.message = "Please select a series";
			return;
		}
		
		if(!$scope.noOfCards)
		{
			$scope.pokerGameError.error = true;
			$scope.pokerGameError.message = "Please provide value for number of cards";
			return;
		}else if($scope.noOfCards <= 0)
		{
			$scope.pokerGameError.error = true;
			$scope.pokerGameError.message = "Please provide number greater than zero";
			return;
		}else if($scope.noOfCards > 20)
		{
			$scope.pokerGameError.error = true;
			$scope.pokerGameError.message = "Please provide number smaller than thirty";
			return;
		}else
		{
			$scope.pokerGameError.error = false;
		}
		
		// broad cast start new poker game.
		$scope.$broadcast("startNewPokerGame");
	};
	
	 
	 /**
	  * Get the selected stories.
	  */
	 $scope.getSelectedSeries = function(){
		 
		if($scope.pokerGame)
		{
			return $scope.pokerGame.gameSeries;
		}
		 
		 return $scope.selectedSeries;
	 };
	 
	 /**
	  * Get the number of cards.
	  */
	 $scope.getNumberOfCards = function(){
		 
		 if($scope.pokerGame)
		 {
			return $scope.pokerGame.numberOfCards
		 }
		 
		 return $scope.noOfCards;
	 };
	 
	 /**
	  * Get the active poker game id.
	  */
	 $scope.getPokerGameId = function(){
		 
		 return $scope.pokerGame.id;
	 };
	 
	 /**
	  * Set the game started value for ui change.
	  */
	 $scope.setGameStarted = function(){
		
		 $scope.gameNotStarted = false;
		 $scope.gameStarted = true;
	 };
	 
	 /**
	  * Non scrum master user joins the game. 
	  */
	 $scope.nonScrumUserJoinsTheGame = function(){
		 
		// broad cast add add new poker game user.
		$scope.$broadcast("addNewPokerGameUser");
		
		
		 //$scope.nonScrumMasterJoined = true;
	 };
	 
}]);