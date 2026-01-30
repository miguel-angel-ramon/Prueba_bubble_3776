jQuery(document).ready(function() {
	load_p10_btn_aceptar();
	load_p10_btn_cancelar();
});

function resetValues(){
	$("input#code").val("");
	$("input#password").val("");
}

function load_p10_btn_aceptar(){ 
	$("#p10_btn_aceptar").click(function() {
		validateLogin();
	});
}

function load_p10_btn_cancelar(){ 
	$("#p10_btn_cancelar").click(function() {
		resetValues();
	});
}

function validateLogin() {
	
//	var user = new User($("#p10_fld_usuario").val(),
//						$("#p10_fld_clave").val(),
//						null,
//						null);
//	var objJson = $.toJSON(user.prepareToJsonObject());
//	
//	$.ajax({
//		type : 'POST',
//		url : './login/validateUser.do',
//		data : objJson,
//		contentType : "application/json; charset=utf-8",
//		dataType : "json",
//		success : function(data) {				
//			if (data.messageType == data.error) {
//				createAlert(replaceSpecialCharacters(data.message), "ERROR");
//				resetValues();				
//			} else {
//				window.location = data.redirect;
//			}			
//		},
//		error : function (xhr, status, error){
//			handleError(xhr, status, error, locale);				
//       }			
//	});		
}


