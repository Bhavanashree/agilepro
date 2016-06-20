$(document).ready(function(){
    $("#project").click(function(){
        $("#pritam").animate({left: '250px'});
		
		sleep(1000);
		$("#pritam").addClass('show');
		
		
		
		
		
		console.log("called");
		
		
		
    });
	
	function sleep(milliseconds) {
		var start = new Date().getTime();
		for (var i = 0; i < 1e7; i++) {
			if ((new Date().getTime() - start) > milliseconds){
			break;
			
			}
		}
	}
});