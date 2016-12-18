$.application.controller('storyDependencyController', ["$scope", "actionHelper", "utils",
                                                       function($scope, actionHelper, utils) {
	
	$scope.dependencyTypes = ["STARTS_WITH", "ENDS_WITH"];
	$scope.previousIndex = -1;
	
	/**
	 * Called on click of plus button
	 */
	$scope.onClickPlus = function(index, backlogId){
		
		$scope.selectedDependencyType = null;
		$scope.selectedBacklogFromDropDown = null;
		
		$scope.indexDisplayObj[index].showDependency = !$scope.indexDisplayObj[index].showDependency;
		
		if(($scope.previousIndex != -1) && ($scope.previousIndex != index))
		{	
			$scope.indexDisplayObj[$scope.previousIndex].showDependency = false;
		}
		
		// logic for dependency stories.
		if($scope.indexDisplayObj[index].showDependency)
		{
			$scope.displayAllDependencies(backlogId);
		}else
		{
			$scope.hideAllDependencies(backlogId);
		}
		
		if(($scope.previousIndex != -1) &&  ($scope.selectedBackLogId) && (!$scope.indexDisplayObj[$scope.previousIndex].showDependency))
		{
			$scope.hideAllDependencies($scope.selectedBackLogId);
		}
		
		
		$scope.previousIndex = index;
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
		
		$scope.indexDisplayObj = {};
		$scope.backlogIdDependencyObj = {};
		
		for(index in $scope.storyDependencies)
		{
			var backlogObj = $scope.storyDependencies[index];
			
			$scope.indexDisplayObj[index] = {"showDependency" : false};
			
			$scope.backlogIdDependencyObj[backlogObj.id] = backlogObj;
		}
	};

	/**
	 * Recursion wise adding the dependencies.
	 */
	$scope.addIndent = function(backlogArr, indentValue){
		
		for(index in backlogArr)
		{
			var backlogObj = backlogArr[index];
			
			backlogObj["indent"] = indentValue;
			backlogObj["showDependency"] = false;
			
			if(backlogObj.storyDependencyType)
			{
				backlogObj["showBackLog"] = false;
			}else
			{
				backlogObj["showBackLog"] = true;
			}
			
			$scope.storyDependencies.push(backlogObj);
			
			if(backlogObj.dependencies)
			{
				$scope.addIndent(backlogObj.dependencies, indentValue + 1);
			}
		}
		
	};
	
	/**
	 * Display all depenencies.
	 */
	$scope.displayAllDependencies = function(backLogId){
		
		console.log("displayAllDependencies");
		
		var backLogObj = $scope.backlogIdDependencyObj[backLogId];
		
		if(backLogObj.dependencies)
		{
			for(index in backLogObj.dependencies)
			{
				backLogObj.dependencies[index].showBackLog = true;
			}
		}
	};
	
	/**
	 * Hide all dependencies.
	 */
	$scope.hideAllDependencies = function(backLogId){
		
		console.log("hideAllDependencies");
		
		var backLogObj = $scope.backlogIdDependencyObj[backLogId];
		
		if(backLogObj.dependencies)
		{
			for(index in backLogObj.dependencies)
			{
				backLogObj.dependencies[index].showBackLog = false;
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
