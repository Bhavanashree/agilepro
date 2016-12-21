$.application.controller('storyDependencyController', ["$scope", "actionHelper", "utils",
                                                       function($scope, actionHelper, utils) {
	
	$scope.dependencyTypes = ["STARTS_WITH", "ENDS_WITH"];
	$scope.previousIndex = -1;
	
	
	/**
	 * Called on click of plus button
	 */
	$scope.onClickPlus = function(dependencyTreeObj, backlogId){
		
		$scope.selectedDependencyType = null;
		$scope.selectedBacklogFromDropDown = null;
		
		dependencyTreeObj.expanded = !dependencyTreeObj.expanded; 
		
		/*$scope.indexDisplayObj[index].showDependency = !$scope.indexDisplayObj[index].showDependency;
		
		if(($scope.previousIndex != -1) && ($scope.previousIndex != index))
		{	
			$scope.indexDisplayObj[$scope.previousIndex].showDependency = false;
		}*/
		
		// logic for dependency stories.
		/*if($scope.indexDisplayObj[index].showDependency)
		{
			$scope.displayAllDependencies(backlogId);
		}else
		{
			console.log("hideAllDependencies else");
			$scope.hideAllDependencies(backlogId);
		}
		
		if(($scope.previousIndex != -1) &&  ($scope.previousIndex != index) && ($scope.selectedBackLogId) && (!$scope.indexDisplayObj[$scope.previousIndex].showDependency))
		{
			console.log("hideAllDependencies if");
			$scope.hideAllDependencies($scope.selectedBackLogId);
		}*/
		
		
		//$scope.previousIndex = index;
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
		
	};

	
	/**
	 * Display all dependencies.
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
	
	$scope.displayForDependencyOnly = function(storyDependencyType){
		
		if(storyDependencyType)
		{
			return true;
		}
		
		return false;
	};
	
	$scope.displayStoryDependencyType = function(storyDependencyType){
		
		if(storyDependencyType == "STARTS_WITH")
		{
			return "Starts with";
		}else
		{
			return "Ends with";
		}
	};
	
	
	/**
	 * Add new dependency
	 */
	$scope.addDependency = function(){
		
		if(!$scope.selectedDependencyType || !$scope.selectedBacklogFromDropDown)
		{
			utils.alert("error");
		}
		
		console.log($scope.selectedBackLogId);
		
		var model = {"mainStoryId" : $scope.selectedBackLogId,
					"dependencyStoryId" : $scope.selectedBacklogFromDropDown.id, 
					"storyDependencyType" : $scope.selectedDependencyType};
		
		actionHelper.invokeAction("storyDependency.save", model, null, 
				function(saveResposne, respConfig)
				{
			
				
				},{"hideInProgress" : true});
		
	};
	
}]);
