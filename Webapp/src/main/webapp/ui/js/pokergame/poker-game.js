$.application.controller('pokerGameController', ["$scope", "actionHelper", 
                                              function($scope, actionHelper) {
	
	/**
	 * Returns the page configuration whether drag and drop is supported or not.
	 */
	$scope.getPageConfiguraion = function(){
		
		var pageConfig = {"dragAndDropFunctionality" : false}

		return pageConfig;
	};
	
	/**
	 * List of game series for drop down.
	 */
	$scope.listOfGameSeries =["FIBONACCI", "SEQUENTIAL", "EVEN"];
	
	$scope.pokerGameError = {"error" : false, "message" : ""};
	
	var projectId;
	
	/**
	 * Initialize the poker game page.
	 */
	$scope.isGameStarted = function(){
		
		$scope.gameStarted = false;
		$scope.gameNotStarted = false;
		$scope.isUserScrumMaster = false;
		$scope.isUserNonScrumMaster = false;
		$scope.nonScrumMasterJoined = false;
		
		$scope.selectedSeries = "";
		$scope.noOfCards = "";
		
		if($scope.activeUser && ($scope.getActiveProjectId() != -1))
		{
			if($scope.activeUser.roles.indexOf("SCRUM_MASTER") >= 0)
			{
				$scope.isUserScrumMaster = true;
			}else
			{
				$scope.isUserNonScrumMaster = true;
			}
			
			
			actionHelper.invokeAction("pokerGame.isPokerGameStarted", null, 
					{"projectId" :  $scope.getActiveProjectId(), "userId" : $scope.activeUser.userId},
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
						
						$scope.$broadcast("selectDefaultBacklogItem");
						
						if($scope.gameStarted)
						{
							$scope.nonScrumMasterJoined = $scope.pokerGame.pokerGameUserModel ? true : false;
							
							if($scope.isUserScrumMaster || $scope.nonScrumMasterJoined)
							{
								$scope.$broadcast("displayPokerGame");
							}
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

		$scope.isGameStarted();
	});

	/**
	 * Add fetched backlog items.
	 */
	$scope.addFetchedBacklogItemsToParent = function(idToBacklogBug, idToBacklogStory, storyIdsInBacklog){
		
		$scope.idToBacklogBug = idToBacklogBug;
		$scope.idToBacklogStory = idToBacklogStory;
		$scope.storyIdsInBacklog = storyIdsInBacklog;
	};
	
	/**
	 * Get backlog bug.
	 */
	$scope.getBacklogBug = function(bugId){
		
		return $scope.idToBacklogBug[bugId];
	};
	
	/**
	 * Get backlog story.
	 */
	$scope.getBacklogStory = function(storyId){
		
		return $scope.idToBacklogStory[storyId];
	};
	
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
			$scope.pokerGameError.message = "Please provide number smaller than 20";
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
		
	 };
	 
	 /**
	  * Add the poker user after save.
	  */
	 $scope.addPokerUserAfterSave = function(){
		 
		$scope.nonScrumMasterJoined = true;
			
		$scope.$broadcast("displayPokerGame");
	 };
	 
	 /**
	  * Get the value of isUserNonScrumMaster.
	  */
	 $scope.getIsUserNonScrumMaster = function(){
		 
		 return $scope.isUserNonScrumMaster;
	 };
	
	 /**
	  * Get is game started.
	  */
	 $scope.getIsGameStarted = function(){
		 
		 return $scope.gameStarted;
	 };
	 
	 
	 $scope.getPokerGame = function(){
		 
		 return $scope.pokerGame;
	 };
	 
}]);