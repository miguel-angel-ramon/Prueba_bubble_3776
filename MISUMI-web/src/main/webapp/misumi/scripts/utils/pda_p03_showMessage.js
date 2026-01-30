/******* Inicio bloque: Para manejar los POPUP y las ventanas de mensajes *****/
function createAlert(messages, type){
	
	$("#popupShowMessage" ).dialog({
			autoOpen: false,
			modal: true,
			dialogClass: "noTitleStuff" ,
			position: "center",
			resizable:false
	});
	$(window).bind('resize', function() {
	    $("#popupShowMessage").dialog("option", "position", "center");
	});
	
	var length = $("div[id^='popupMessages']").length;
	for(var i=0; i<length; i++)
		$("#popupMessages" + i).remove();
	    $("div#popupMessages" + i).remove();
    
	var id = $("div[id^='popupMessages']").length + 1;
	var divMsg = "popupMessages" + "1";
	$('#popupShowMessage').prepend("<div id='"+divMsg+"'></div>");	
	$("#" + divMsg).append("<table id='table_msg' style='border-collapse: separate; margin: 2px 2px 2px 2px;'>");

	if(type == "INFO"){
		$("#table_msg").append("<tr><td width='15%' style='text-align: center'><img src='./misumi/images/dialog-accept-24.png' /></td>" +
								   "<td width='85%' style='text-align: left'>"+messages+"</td></tr>");
	}
	if(type == "ERROR"){
		$("#table_msg").append("<tr><td width='15%' style='text-align: center'><img src='./misumi/images/dialog-error-24.png' /></td>" +
								   "<td width='85%' style='text-align: left'>"+messages+"</td></tr>");
	}
	if(type == "HELP"){
		$("#table_msg").append("<tr><td width='15%' style='text-align: center'><img src='./misumi/images/dialog-help-24.png' /></td>" +
								   "<td width='85%' style='text-align: left'>"+messages+"</td></tr>");
	}
	
	$("#" + divMsg).append("</table>");
	
	var w=$("#popupShowMessage").width()+10;
//	
	$("#p03_btn_aceptar").click(function(){
		closePopupMessages();
	});
	
	$( "#popupShowMessage" ).dialog( "open" );
}
function closePopupMessages(){	
	closeModal();
	closePopup("popupShowMessage");
	
}

function closePopup(name){
	var dialogNam ="#"+ name;
	$( dialogNam ).dialog( "close" );
	//$( dialogNam ).dialog( "destroy" );
} 

function closeModal(){
    // removemos divs creados
	var length = $("div[id^='popupMessages']").length;

	for(var i=0; i<length; i++){
		 $("#popupMessages" + i).hide();
	    $("#popupMessages" + i).remove();
	}    
   $('#bgtransparent').remove();
}
/******* FIN bloque: Para manejar los POPUP y las ventanas de mensajes *****/
