$(document).ready(function(){
	
	$("#hide").click(function(){
		
		console.log("called");
		
		$("#leftMenu").fadeOut("slow");
		
		$("#hide").fadeOut("slow");
		
		$("#hide").addClass("triangle-right");
	});
	
});