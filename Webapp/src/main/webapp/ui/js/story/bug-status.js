$.application.controller('bugStatusController', ["$scope", "utils", "actionHelper", function($scope, utils, actionHelper){
	
	/**
	 * Clean all the records with empty array for new search results.
	 */
	 $scope.cleanBugs = function() {
		 $scope.bugs = [];
		 $scope.openedBug = [];
		 $scope.submitedBug = [];
		 $scope.reportedBug = [];
		 $scope.closedBug = [];
		 $scope.idToBug = {};
	 };
	 
	 /**
	  * Get list according to the status.
	  */
	 $scope.getListWithStatus = function(status){
			switch(status)
			{
				case 'OPEN':
					return $scope.openedBug;
				case 'SUBMIT':
					return $scope.submitedBug;
				case 'REPORTED':
					return $scope.reportedBug;
				case 'CLOSED':
					return $scope.closedBug;
				default:
					return $scope.bugs;
			}
	}
	 
	/**
	 * Fetch bugs by active project.
	 */
	$scope.fetchBugs = function(){
		
		actionHelper.invokeAction("bug.fetchBugBySprintId", null, {"sprintId" : $scope.selectedSprintObj.id, "projectId" : $scope.getActiveProjectId()}, 
		
				function(readResponse, respConfig){
					
					var bugArrForIterat = readResponse.model;
					
					$scope.cleanBugs();
					
					var index;
		
					for(index in bugArrForIterat)
					{
						if(bugArrForIterat[index].photo)
						{
							fileId =  bugArrForIterat[index].photo.fileId;	
							bugArrForIterat[index]["photoUrl"] = actionHelper.actionUrl("files.fetch", {"id": fileId});
						}
						
						$scope.idToBug["" + bugArrForIterat[index].id] = bugArrForIterat[index];
						
						switch(bugArrForIterat[index].status)
						{
							case 'OPEN':
								$scope.openedBug.push(bugArrForIterat[index]);
								break;
							case 'SUBMIT':
								 $scope.submitedBug.push(bugArrForIterat[index]);
								break;
							case 'REPORTED':
								$scope.reportedBug.push(bugArrForIterat[index]);
								break;
							case 'CLOSED':
								$scope.closedBug.push(bugArrForIterat[index]);
								break;
							default:
								$scope.bugs.push( bugArrForIterat[index] );
						}
					}
					
					try
					{
						$scope.$apply();
					}catch(ex)
					{}
							
						
		},{"hideInProgress" : true});
	};
	
	
	// DRAG AND DROP METHODS
	
	// on drag method for stories
	$scope.onDragBugs = function(event){
		event.originalEvent.dataTransfer.setData('text/plain', 'text');
		
		$scope.selectedBugIdForDrag = event.target.id;
	};
	
	// listener events for the broad cast
	$scope.$on("onBugDropForOpen", function(event, args) {	
		$scope.handleBugOnDropEvent("OPEN");
	});
	
	$scope.$on("onBugDropForSubmit", function(event, args) {	
		$scope.handleBugOnDropEvent("SUBMIT");
	});
	
	$scope.$on("onBugDropForReported", function(event, args) {	
		$scope.handleBugOnDropEvent("REPORTED");
	});
	
	$scope.$on("onBugDropForClose", function(event, args) {	
		$scope.handleBugOnDropEvent("CLOSED");
	});
	
	$scope.$on("onDropBugBack", function(event, args) {	
		$scope.handleBugOnDropEvent(null);
	});
	
	$scope.$on("onSelectedSprintChange", function(event, args) {	
		$scope.fetchBugs();
	});
	
	
	//common method for update status and sprintId to stories
	$scope.handleBugOnDropEvent = function(status, skipServer)
	{	
		 var dropedBug = $scope.idToBug[$scope.selectedBugIdForDrag];
		 var oldStatus = dropedBug.status;
		 
		 var srcList = $scope.getListWithStatus(dropedBug.status);
		 var targetList = $scope.getListWithStatus(status);
		 
		 var currentIndex = srcList.indexOf(dropedBug);
		 srcList.splice(currentIndex, 1);
		 
		 targetList.push(dropedBug);
		 
		 dropedBug.sprint = $scope.selectedSprintObj.id;
		 dropedBug.status = status;

		 try
		 {
			 $scope.$apply();
		 }catch(ex)
		 {}
		 
		 
		 if(skipServer)
		 {
			 return;
		 }
		
		actionHelper.invokeAction("bug.update", dropedBug, null, $.proxy(function(updateResponse, respConfig) {
			//if update failed
			if(updateResponse.code != 0)
			{
				utils.info(["Failed to move bug '{}' to status '{}'. Please try again!", bug.title, bug.status]);
				this.$scope.handleBugOnDropEvent(this.oldStatus, true);
				return;
			}
			
			this.bug.version = updateResponse.version;
			
		}, {"$scope": $scope, "bug": dropedBug, "oldStatus" : oldStatus}), {"hideInProgress" : true});
	}
	
}]);