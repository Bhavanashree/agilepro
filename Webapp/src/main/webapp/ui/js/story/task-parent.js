$.application.controller('taskHeaderController', ["$scope", "utils", "actionHelper",
                                               function($scope, utils, actionHelper) {
	
	/**
	 * Initialize task header.
	 * 
	 * Fetch the sprints and owner.
	 */
	$scope.initTaskheader = function(){
		
		$scope.taskStatusNames = ["NOT_STARTED", "IN_PROGRESS", "COMPLETED"];
		$scope.taskStatusForFilter = ["All", "Completed", "Not Completed"];
		
		
		$scope.multipleCheckedBugIds = [];
		$scope.multipleCheckedStoryIds = [];
		
		var projectId = $scope.getActiveProjectId();
		
		if(projectId == -1)
		{
			return;
		}
		
		// Fetch sprints.
		actionHelper.invokeAction("sprint.sprintProjectId", null, {"projectId" : projectId}, 
				function(readResponse, respConfig)
				{
					$scope.sprints = readResponse.model;

					$scope.idToSprint = {};
					
					for(index in $scope.sprints)
					{
						var sprintObj = $scope.sprints[index];
						
						$scope.idToSprint[sprintObj.id] = sprintObj;
					}
					
					// set active sprint.
					if($scope.sprints)
					{
						$scope.onSprintChange($scope.sprints[0].id);
					}
			
				}, {"hideInProgress" : true});
			
		// Fetch employees
		actionHelper.invokeAction("employee.readAll", null, null, 
				function(readResponse, respConfig)
				{
					$scope.employees = readResponse.model;

					if($scope.employees)
					{
						var allObj = {"id" : 0, "name" : "All"};
						
						$scope.employees.splice(0, 0, allObj);
					}
					
					$scope.idToEmployee = {};
					
					for(index in $scope.employees)
					{
						var empObj = $scope.employees[index];
						
						$scope.idToEmployee[empObj.id] = empObj;
					}
			
				}, {"hideInProgress" : true});
	/*	
		try
		{
			$scope.$apply();
		}catch(ex)
		{}*/

	};
	
	/**
	 * Display task for ui.
	 */
	$scope.displayTask = function(status){
		
		if(status == "NOT_STARTED" || !status)
		{
			return "Not started";
		}
		
		if(status == "IN_PROGRESS")
		{
			return "In Progress";
		}
		
		if(status == "COMPLETED")
		{
			return "Completed";
		}
	};
	
	/**
	 * On change of sprint from drop down.
	 */
	$scope.onSprintChange = function(sprintId){
		
		$scope.selectedSprintObj = $scope.idToSprint[sprintId];
		
		$scope.$broadcast("activeSprintSelectionChanged");
	};
	
	/**
	 * On change of owner from drop down.
	 */
	$scope.onOwnerChange = function(employeeId){
		
		$scope.selectedOwner = $scope.idToEmployee[employeeId];
		
		$scope.$broadcast("activeOwnerSelectionChanged");
	};
	
	/**
	 * On change of status for filter. 
	 */
	$scope.onStatusChangeForFilter = function(name){
		
		$scope.activeStoryStatus = name; 
		
		$scope.$broadcast("activeStoryStatusSelectionChanged");
	};
	
	/**
	 * Get selected sprint.
	 */
	$scope.getSelectedSprint = function(){
		
		return $scope.selectedSprintObj;
	};
	
	/**
	 * Get selected owner.
	 */
	$scope.getSelectedOwner = function(){
		
		return $scope.selectedOwner;
	};
	
	/**
	 * Get selected story status.
	 */
	$scope.getSelectedStoryStatus = function(){
		
		return $scope.activeStoryStatus;
	};
	
	/**
	 * Get allowed from backlog to story.
	 */
	$scope.getAllowedFromBacklogToStory = function(){
		
		return $scope.allowedFromBacklogToStory;
	};

	/**
	 * Get allowed from story to backlog.
	 */
	$scope.getAllowedFromStoryToBacklog = function(){
		
		return $scope.allowedFromStoryToBacklog;
	};
	
	/**
	 * Get multiple checked bug ids.
	 */
	$scope.getMultipleCheckedBugIds = function(){
		
		return $scope.multipleCheckedBugIds;
	};
	
	/**
	 * Get multiple checked story ids.
	 */
	$scope.getMultipleCheckedStoryIds = function(){
		
		return $scope.multipleCheckedStoryIds;
	};
	
	/**
	 * Get dragging item.
	 */
	$scope.getDraggingItem = function(){
		
		return $scope.draggingItemIsBug;
	};
	
	/**
	 * Get dragging id.
	 */
	$scope.getDraggingId = function(){
		
		return $scope.draggingId;
	};
	
	/**
	 * Get backlog list ids.
	 */
	$scope.getBacklogListIds = function(){
	
		return $scope.backlogListIds;
	};
	
	/**
	 * Add id to map items.
	 */
	$scope.addFetchedItemsToParent = function(idToBacklogBug, idToBacklogStory, backlogListIds){
		
		console.log("aded to paremt");
		
		$scope.idToBacklogBug = idToBacklogBug;
		$scope.idToBacklogStory = idToBacklogStory;
		$scope.backlogListIds = backlogListIds;
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
	 * Set backlog bug.
	 */
	$scope.setBacklogBug = function(bugId, data){
		
		$scope.idToBacklogBug[bugId] = data;
	};
	
	/**
	 * Set backlog story.
	 */
	$scope.setBacklogStory = function(storyId, data){
		
		$scope.idToBacklogStory[storyId] = data;
	};
	
	/**
	 * Gets invoked from the child controller when the item is dragged from backlog to sprint.
	 */
	$scope.onDragOfItemFromBacklogToSprint = function(draggingItemIsBug, draggingId){
	
		$scope.allowedFromBacklogToStory = true;
		$scope.allowedFromStoryToBacklog = false;
		
		$scope.draggingItemIsBug = draggingItemIsBug;
		$scope.draggingId = draggingId;
		
		$('#dropStoryAndBugForTaskDivId').css("border", "3px solid #66c2ff");
		$('#dropStoryAndBugForTaskDivId').css('box-shadow', "5px 5px 5px #888888");
	};
	
	/**
	 * Gets invoked from the child controller when the item is dragged from sprint to backlog.
	 */
	$scope.onDragOfItemFromSprintToBacklog = function(draggingItemIsBug, draggingId){
	
		$scope.allowedFromStoryToBacklog = true;
		$scope.allowedFromBacklogToStory = false;
		
		$scope.draggingItemIsBug = draggingItemIsBug;
		$scope.draggingId = draggingId;
		
		$('#dropStoryForBacklogId').css("border", "3px solid #66c2ff");
		$('#searchBacklogInputId').css("border-bottom", "3px solid #66c2ff");
		$('#dropStoryForBacklogId').css('box-shadow', "5px 5px 5px #888888");
	};
	
	/**
	 * On mouse release item
	 */
	$scope.initOnMouseReleasedItem = function(){

		$('#dropStoryAndBugForTaskDivId').css("border", "3px solid grey");
		$('#dropStoryAndBugForTaskDivId').css('box-shadow', "none");
		
		$('#dropStoryForBacklogId').css("border", "3px solid grey");
		$('#searchBacklogInputId').css("border-bottom", "3px solid grey");
		$('#dropStoryForBacklogId').css('box-shadow', "none");
		
		$scope.multipleCheckedBugIds = [];
		$scope.multipleCheckedStoryIds = [];
		$scope.draggingId = null;
	};
	
	/**
	 * Rearrange the items.
	 */
	$scope.reArrangeTheItems = function(multipleBugIds, multipleStoryIds, sprintId){
		
		var args = {"multipleBugIds" : multipleBugIds, "multipleStoryIds" : multipleStoryIds, "sprintId" : sprintId};
		
		$scope.$broadcast("reArrangeTheBacklogItems", args);
	};
	
	
	// Listener for broadcast
	$scope.$on("activeProjectSelectionChanged", function(event, args) {
		
		$scope.initTaskheader();
	});
	
}]);