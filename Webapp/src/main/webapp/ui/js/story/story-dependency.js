$.application.controller('storyDependencyController', ["$scope", "actionHelper", "utils",
                                                       function($scope, actionHelper, utils) {
	
	$scope.dependencyTypes = ["STARTS_WITH", "ENDS_WITH"];
	
	/**
	 * Called on click of plus button
	 */
	$scope.onClickPlus = function(backlogId){
		
		$scope.selectedDependencyType = null;
		$scope.selectedBacklogFromDropDown = null;
		
		var selectedBacklogObj = $scope.getBacklog(backlogId);
		selectedBacklogObj.showDependency = true;
		
		if($scope.selectedBackLogId == backlogId)
		{
			var previousBacklogObj = $scope.getBacklog($scope.selectedBackLogId);
			previousBacklogObj.showDependency = !previousBacklogObj.showDependency;
		}
		else if($scope.selectedBackLogId && $scope.selectedBackLogId != backlogId)
		{
			var previousBacklogObj = $scope.getBacklog($scope.selectedBackLogId);
			previousBacklogObj.showDependency = false;
		}
		
		$scope.selectedBackLogId = backlogId;
	};
	
	/**
	 * Invoked for displaying the drop down.
	 */
	$scope.onClickDropDown = function(backlogId) {
		
		$scope.dropdown = [];
		
		var backlogArr = $scope.getBackLogs();
		
		for(index in backlogArr)
		{
			if(backlogArr[index].id == backlogId)
			{
				continue;
			}
			
			$scope.dropdown.push(backlogArr[index]);
		}
	};
	
	/**
	 * Initialize story dependencies.
	 */
	$scope.initStoryDependency = function(){
	
		console.log("initStoryDependency is called");
		
		$scope.storyDependencies = [];
		
		$scope.addIndent($scope.getBackLogs(), 0);
		
		/*for(index in $scope.storyDependencies)
		{
			var storyObj = $scope.storyDependencies[index];
			
			storyObj.show = false;
		}*/
	};

	
	$scope.addIndent = function(backlogArr, indentValue){
		
		for(index in backlogArr)
		{
			var backlogObj = backlogArr[index];
			
			backlogObj["indent"] = indentValue;
			backlogObj["showDependency"] = false;
			
			if(backlogObj.storyDependencyType)
			{
				backlogObj["isDependencyStory"] = true;
			}
			
			$scope.storyDependencies.push(backlogObj);
			
			if(backlogObj.dependencies)
			{
				$scope.addIndent(backlogObj.dependencies, indentValue + 1);
			}
		}
		
	};
	
	/**
	 * On change of pattern.
	 */
	$scope.onDependencyTypeChange = function(type){
		
		$scope.selectedDependencyType = type;
	};

	/**
	 * On selection of story from drop down.
	 */
	$scope.onStoryChange = function(storyObj){
	
		$scope.selectedBacklogFromDropDown = storyObj;
	};
	
	/**
	 * Add new dependency
	 */
	$scope.addDependency = function(){
		
		if(!$scope.selectedDependencyType || !$scope.selectedBacklogFromDropDown)
		{
			utils.alert("error");
		}
		
		var model = {"mainStoryId" : $scope.selectedBackLogId,
					"dependencyStoryId" : $scope.selectedBacklogFromDropDown.id, 
					"storyDependencyType" : $scope.selectedDependencyType};
		
		actionHelper.invokeAction("storyDependency.save", model, null, 
				function(saveResposne, respConfig)
				{
			
				
				},{"hideInProgress" : true});
		
	};
	
}]);
