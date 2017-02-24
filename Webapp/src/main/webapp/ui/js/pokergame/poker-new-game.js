$.application.controller("pokerNewGameController", ["$scope", "actionHelper", "utils", 
                                             function($scope, actionHelper, utils){
	
	/**
	 * Initialize new poker game.
	 */
	$scope.initPokerNewGame = function(){
		
		
	};
	
	/**
	  * Start a new poker game.
	  */
	 $scope.startNewGame = function(){
		 
		var model = {"gameSeries" : $scope.getSelectedSeries(),
					 "projectId" : $scope.getActiveProjectId(), "userId" : $scope.activeUser.userId, 
					 "numberOfCards" : $scope.getNumberOfCards()};
		
		if($scope.selectedBacklogItem)
		{
			if($scope.selectedBacklogItem.isBug)
			{
				model["bugId"] = $scope.selectedBacklogItem.id; 
			}else
			{
				model["storyId"] = $scope.selectedBacklogItem.id;
			}
			
			model["pokerGameStatus"] = "CARDS_NOT_FLIPPED";
		}else
		{
			utils.alert("Please create story or bug");
			return;
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
		 		});
		 
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
		 
		$scope.cardValues.splice(0, 0, '?', "\u221E", "0");
		
		// set the width dynamically of game box for non scrum users
		
		var  isUserNonScrumMaster = $scope.getIsUserNonScrumMaster();
		
		if(isUserNonScrumMaster)
		{
			$("#" + "pokerGameId").css("width", "80%");
			$("#" + "pokerGameSelectedCardBoxId").css("height", "69%");
		}
		
		try
		{
			$scope.$apply();
		}catch(ex)
		{}
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
		 
	 
	 /**
	  * Select default backlogs item, first backlog item which has null story points or the backlog item in zero index.
	  */
	 $scope.selectDefaultBacklogItem = function(backlogArr){
		 
		for(var i = 0 ; i < backlogArr.length ; i++)
		{
			var obj = backlogArr[i];
			
			$scope.selectedBacklogItem = null;
			
			if(!obj.storyPoints)
			{
				obj.check = true;
				$scope.selectedBacklogItem = obj;
				break;
			}
		}
		
		if(backlogArr.length > 0 &&  !$scope.selectedBacklogItem)
		{
			$scope.selectedBacklogItem = backlogArr[0]; 
		}	
	 };
	 
	/**
	 * Get selected backlog item.
	 */
	$scope.getSelectedBacklogItem = function(){
		
		return $scope.selectedBacklogItem;
	};
	 
	/**
	 * Set the clicked backlog item.
	 */
	$scope.selectClickedBacklogItem = function(selectedBacklogItem){
		
		$scope.selectedBacklogItem = selectedBacklogItem;
	};
	
	/**
	 * Fetch the story info and open the modal.
	 */
	$scope.openNoteModal = function(){
		
		actionHelper.invokeAction("storyNote.readLatestStoryNoteByStoryId", null, {"storyId" : $scope.selectedBacklogItem.id}, 
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$("#storyNoteModal").modal("show");
						$scope.storyNote = readResponse.model;
						
						if(!$scope.storyNote)
						{
							$scope.message = "Currently there is no note for " + $scope.selectedBacklogItem.title;
						}else
						{
							$scope.message = "";
						}
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}
					
				}, {"hideInProgress" : true});
	};
	
	/**
	 * On change of card.
	 */
	$scope.onChangeCard = function(value){
		
	};
	
	
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
	$scope.$on("activeProjectSelectionChanged", function(event, args) {

		$scope.initPokerNewGame();
	});
	
	// Listener for broadcast
	$scope.$on("displayPokerGame", function(event, args) {

		$scope.displayPokerGame();
	});
	
	// Listener for emit
	$scope.$on("backlogsForPokerGame", function(event, args) {
		
		$scope.selectDefaultBacklogItem(args.backlogs);
	});
	
	// Listener for emit
	$scope.$on("backlogIsSelectedForPokerGame", function(event, args) {
		
		$scope.selectClickedBacklogItem(args.selectedBacklogItem);
		
	});
	
	
}]);