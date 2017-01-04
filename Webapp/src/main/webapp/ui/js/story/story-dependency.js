$.application.controller('storyDependencyController', ["$scope", "actionHelper", "utils",
                                                       function($scope, actionHelper, utils) {
	/**
	 * Dependency type.
	 */
	$scope.dependencyTypes = ["STARTS_WITH", "ENDS_WITH"];
	
	/**
	 * Gets invoked on type of search title in the search input box.
	 * 
	 * Only backlogs are filtered.
	 */
	$scope.dependencyStoryFilter = function(){
		
		var retFunc = function(item){
				
			if(!$scope.dependencySearchStory)
			{
				return true;
			}
			
			var searchString = $scope.dependencySearchStory.toLowerCase();

			return item.dependencyStory.title.toLowerCase().includes(searchString);
		};
		
		if($scope.oldSearchDependencyStory == $scope.dependencySearchStory)
		{
			return retFunc;
		}
				
		$scope.oldSearchDependencyStory = $scope.dependencySearchStory;

		return retFunc;
	};
	
	/**
	 * Called on click of plus button.
	 */
	$scope.onClickPlus = function(dependencyTreeObj, backlogId){
		
		console.log(dependencyTreeObj);
		
		if(!$scope.previousObj)
		{
			$scope.previousObj = dependencyTreeObj;
		}
		
		$scope.selectedDependencyType = null;
		$scope.selectedBacklogFromDropDown = null;
		
		dependencyTreeObj.expanded = !dependencyTreeObj.expanded; 
		
		dependencyTreeObj.mainStoryId = backlogId;
		
		
		if(!dependencyTreeObj.expanded && dependencyTreeObj.dependencyStory.dependencies)
		{
			$scope.minimizeDependencies(dependencyTreeObj.dependencyStory.dependencies);
		}
		
		if((!dependencyTreeObj.storyDependencyType) && ($scope.previousObj != dependencyTreeObj) && (dependencyTreeObj.expanded))
		{
			$scope.previousObj.expanded = false;
			
			$scope.minimizeDependencies($scope.previousObj.dependencyStory.dependencies);
		}
		
		if(!dependencyTreeObj.storyDependencyType)
		{
			$scope.previousObj = dependencyTreeObj;
		}
		
	};
	
	/**
	 * Minimize Dependency and there dependencies.
	 */
	$scope.minimizeDependencies = function(dependencyArr){
		
		for(obj of dependencyArr)
		{
			obj.expanded = false;
			
			if(obj.dependencyStory.dependencies)
			{
				$scope.minimizeDependencies(obj.dependencyStory.dependencies);
			}
		}
		
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
	 * On change of pattern from drop down.
	 */
	$scope.onDependencyTypeChange = function(dependencyObj, type){
		
		dependencyObj.selectedDependencyType = type; 
	};

	/**
	 * On selection of story from drop down.
	 */
	$scope.onStoryChange = function(dependencyObj, storyObj){
	
		dependencyObj.selectedDependencyStoryId = storyObj.id;
	};

	/**
	 * Used for displaying only for dependency story.
	 */
	$scope.displayForDependencyOnly = function(storyDependencyType){
		
		if(storyDependencyType)
		{
			return true;
		}
		
		return false;
	};
	
	/**
	 * Display the type of dependency.
	 */
	$scope.displayStoryDependencyType = function(storyDependencyType){
		
		if(storyDependencyType == "STARTS_WITH")
		{
			return "Starts with";
		}
		else if(storyDependencyType == "ENDS_WITH")
		{
			return "Ends with";
		}
	};
	
	
	/**
	 * Add new dependency
	 */
	$scope.addDependency = function(dependencyObj){
		
		if(!dependencyObj.selectedDependencyType || !dependencyObj.selectedDependencyStoryId)
		{
			utils.alert("error");
			return;
		}
		
		var model = {"mainStoryId" : dependencyObj.mainStoryId,
					"dependencyStoryId" : dependencyObj.selectedDependencyStoryId, 
					"storyDependencyType" : dependencyObj.selectedDependencyType};
		
		actionHelper.invokeAction("storyDependency.save", model, null, 
				function(saveResposne, respConfig)
				{
					if(saveResposne.code == 0)
					{
						model.id = saveResposne.id; 
						
						dependencyObj.selectedDependencyType = null;
						dependencyObj.selectedDependencyStoryId = null;
						
						$scope.addDependencyStoryAfterSave(model);
					}
				
				},{"hideInProgress" : true});
	};
	
	/**
	 * Updates the dependency type on selection of type from already added dependency drop down.
	 */
	$scope.updateDependencyObj = function(dependencyObj, type){
		
		if(dependencyObj.storyDependencyType != type)
		{
			actionHelper.invokeAction("storyDependency.updateDependencyType", null, {"id" : dependencyObj.id, "storyDependencyType" : type}, 
					function(deleteResponse, respConfig)
					{
						if(deleteResponse.code == 0)
						{
							dependencyObj.storyDependencyType = type;
							
							try
							{
								$scope.$digest();
							}catch(ex)
							{}
						}
					}, {"hideInProgress" : true});
		}
	};
	
	/**
	 * Gets invoked on click of delete button for removing a dependency from main story.
	 */
	$scope.removeDependencyObj = function(dependencyArrObj){
		
		var dependencyObj = dependencyArrObj.dependencyStory;
		var mainStoryObj = dependencyArrObj.mainStory;
		var dependencyId = dependencyArrObj.id;
		
		if(dependencyObj.dependencies.length > 0)
		{
			utils.alert("This story has dependencies");
			return;
		}
		
		var deleteOp = $.proxy(function(confirmed) {
			
			if(!confirmed)
			{
				return;
			}
			else
			{
				actionHelper.invokeAction("storyDependency.delete", null, {"id" : dependencyId}, 
						function(deleteResponse, respConfig)
						{
							if(deleteResponse.code == 0)
							{
								$scope.removeDependencyAfterDelete(mainStoryObj, dependencyObj);
							}
						}, {"hideInProgress" : true});
			}
			
		}, {"$scope": $scope, "dependencyId": dependencyId});
		
		
		utils.confirm(["Are you sure you want to remove '{}' dependency from '{}'?", dependencyObj.title, mainStoryObj.title], deleteOp);
		
	};
	
}]);
