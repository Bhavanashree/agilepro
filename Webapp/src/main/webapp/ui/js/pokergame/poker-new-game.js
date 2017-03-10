$.application.controller("pokerNewGameController", ["$scope", "actionHelper", "utils", 
                                             function($scope, actionHelper, utils){
	
	$scope.firstRequest = true;
	
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
	  * Select default backlogs item, first backlog item which has story points as null or the backlog item in zero index.
	  */
	 $scope.selectDefaultBacklogItem = function(backlogArr){

		$scope.selectedBacklogItem = null;
		var pokerGame = null;
		
		if($scope.getIsGameStarted())
		{
			pokerGame =  $scope.getPokerGame();
		}
			
		if(!backlogArr)
		{
			return;
		}
		
		for(var i = 0 ; i < backlogArr.length ; i++)
		{
			var obj = backlogArr[i];
			
			if(pokerGame)
			{
				if(pokerGame.activeItemIsBug && obj.isBug && obj.id == pokerGame.bugId)
				{
					obj.check = true;
					$scope.selectedBacklogItem = obj;
					break;
				}else if(!pokerGame.activeItemIsBug && !obj.isBug && obj.id == pokerGame.storyId)
				{
					obj.check = true;
					$scope.selectedBacklogItem = obj;
					break;
				}
			}
			else if(!obj.storyPoints)
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
		
		// fetch running notes for the selected backlog item.
		$scope.fetchRunningNotes();
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
		
		actionHelper.invokeAction("pokerGameUser.onChangeCard", null, 
				{"pokerGameId" :  $scope.getPokerGame().id, "pokerGameUserId" : $scope.getPokerGame().pokerGameUserModel.id, "cardValueDisplay" : value},
					function(saveResponse, respConfig)
					{
						if(saveResponse.code == 0)
						{
							$scope.getPokerGame().pokerGameUserModel.cardValue = value;
						}
						
					}, {"hideInProgress" : true});
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
					
					$scope.fetchPokerDatas();
					
					pokerGameUser.id = saveResposne.id;
					$scope.getPokerGame().pokerGameUserModel = pokerGameUser;
					
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
	
	// Listener for broadcast
	$scope.$on("selectDefaultBacklogItem", function(event, args) {

		$scope.pokerGameUsers = [];
		$scope.selectDefaultBacklogItem($scope.backlogs);
		$scope.fetchPokerDatas();
		
	});
	
	// Listener for emit
	$scope.$on("backlogsForPokerGame", function(event, args) {
		
		$scope.backlogs = args.backlogs;

		$scope.isGameStarted();
	});
	
	
	// Listener for emit
	$scope.$on("backlogIsSelectedForPokerGame", function(event, args) {
		
		$scope.selectClickedBacklogItem(args.selectedBacklogItem);
		
		$scope.updateNewSelectedItem();
		
		$scope.fetchRunningNotes();
	});
	
	/**
	 * Update on change of backlog item.
	 */
	$scope.updateNewSelectedItem = function(){
		
		actionHelper.invokeAction("pokerGame.onChangeBacklogItemPokerGame", null, 
				{"projectId" : $scope.getActiveProjectId(), "backlogId" : $scope.selectedBacklogItem.id, "selectedItemIsBug" : $scope.selectedBacklogItem.isBug},
				function(updateResponse, respConfig)
				{
					
			
				}, {"hideInProgress" : true});
	};
	
	// Running notes
	$scope.onTypeNotes = function(e) {
		
		e = e || window.event;
		var key = e.keyCode ? e.keyCode : e.which;
		
		if(key == 13)
		{
			if($scope.newRunningNote.trim().length == 0)
			{
				return;
			}
			
			$scope.saveNewRunningNote($scope.newRunningNote);
			$scope.newRunningNote = "";
		}
		
	};
	
	/**
	 * Save new running notes calls the controller and saves the new note.
	 */
	$scope.saveNewRunningNote = function(runningNote){
		
		var model = {"runningNote" : runningNote, "activeUserId" : $scope.activeUser.userId, "projectId" :  $scope.getActiveProjectId()};
		
		if($scope.selectedBacklogItem.isBug)
		{
			model.bugId = $scope.selectedBacklogItem.id;
		}else
		{
			model.storyId = $scope.selectedBacklogItem.id;
		}
		
		
		actionHelper.invokeAction("runningNotes.save", model, null,
				function(saveResponse, respConfig)
				{
					if(saveResponse.code == 0)
					{
						$scope.runningNotes.push({"runningNote" : runningNote});
						
						try
						{
							$scope.$apply();
						}catch(ex)
						{}
					}else
					{
						$scope.newRunningNote = runningNote;
					}
				}, {"hideInprogress" : true});
		
	};
	
	/**
	 * Fetchs the running notes calls the controller.
	 */
	$scope.fetchRunningNotes = function(){
		
		actionHelper.invokeAction("runningNotes.fetchRunningNotes", null, {"mappingId" : $scope.selectedBacklogItem.id, "isBug" : $scope.selectedBacklogItem.isBug},
				function(readResponse, respConfig){
				
				if(readResponse.code == 0)
				{
					$scope.runningNotes = readResponse.model;
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
				}
		}, {"hideInProgress" : true});
	};
	
	/**
	 * Filter note.
	 */
	$scope.filterNote = function(){
		
		var retFunc = function(item){
			
			if(!$scope.searchNote)
			{
				return true;
			}
			 
			var searchString = $scope.searchNote.toLowerCase();
			
			return item.runningNote.toLowerCase().includes(searchString);
		};
		
		if($scope.oldSearchNote == $scope.searchNote)
		{
			return retFunc;
		}
		
		$scope.oldSearchNote = $scope.searchNote;

		return retFunc;
	};
	
	/**
	 * Fetch poker datas.
	 */
	$scope.fetchPokerDatas = function(){
		
		if(false)
		{
			clearInterval($scope.intervalValue);
		}
		
		
		actionHelper.invokeAction("pokerGameUser.readPokerGameInterval", null, 
				{"pokerGameId" :  $scope.getPokerGame().id},
				function(readResponse, respConfig)
				{
					if(readResponse.code == 0)
					{
						$scope.pokerGameUsers = readResponse.model;
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
					
					for(var i = 0 ; i < $scope.pokerGameUsers.length ; i++)
					{
						$("#" + $scope.pokerGameUsers[i].id + "_selectedCard").flip({
							  trigger: 'manual'
						});
					}
							
				}, {"hideInProgress" : true});
		
		if($scope.firstRequest)
		{
			$scope.intervalValue = setInterval($scope.fetchPokerDatas, ($.appConfiguration.conversationRefreshInterval));
			
			$scope.firstRequest = false;
		}
		
	};
	
	/**
	 * Flip cards.
	 */
	$scope.flipCards = function(){
		
		for(var i = 0 ; i < $scope.pokerGameUsers.length ; i++)
		{
			var obj = $scope.pokerGameUsers[i];
			
			if(obj.cardValue)
			{
				$("#" + obj.id + "_selectedCard").flip(true);
			}
		}
		
		clearInterval($scope.intervalValue);
	};
	
}]);