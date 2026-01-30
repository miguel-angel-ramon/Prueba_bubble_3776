$(document).ready(function(){
	//S�lo para la Maquetaci�n
	initializeScreenP58();
});

function initializeScreenP58(){
}

function reloadAyudaPopup2() {

	$("#p56_pestanaAyuda2Cargada").val("S");
	loadP58Ayuda2(locale);
}

function loadP58Ayuda2(locale){
	//var vReferenciasCentro=new ReferenciasCentro('1222', '679');
	//var vReferenciasCentro=new ReferenciasCentro('34', '679');
	var objJson = $.toJSON(vReferenciasCentro.prepareToJsonObject());	
	 $.ajax({
		type : 'POST',
		url : './nuevoPedidoAdicional/loadAyuda2.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (validateReloadingAy2(data)){
				//ActualizaciÃ³n de los datos de "Ventas media"
				p58updateResultadosAy2VentasMedia(data);
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		

}

function validateReloadingAy2(data){

	if (data.length == 0){

		return false;
	}else{
		return true;
	}
}

function p58updateResultadosAy2VentasMedia (data){
	if (data.historicoVentaMedia != null ){
		
		if (data.historicoVentaMedia.tarifa != null ){
			$("#p58_ventasMediaTdTarifa").text(data.historicoVentaMedia.tarifa).formatNumber();
		}else{
			$("#p58_ventasMediaTdTarifa").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.competencia != null ){
			$("#p58_ventasMediaTdCompetencia").text(data.historicoVentaMedia.competencia).formatNumber();
		}else{
			$("#p58_ventasMediaTdCompetencia").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.media != null ){
			$("#p58_ventasMediaTdMedia").text(data.historicoVentaMedia.media).formatNumber();
		}else{
			$("#p58_ventasMediaTdMedia").text("0").formatNumber();
		}
	}else{
		$("#p58_ventasMediaTdTarifa").text("0").formatNumber();
		$("#p58_ventasMediaTdCompetencia").text("0").formatNumber();
		$("#p58_ventasMediaTdMedia").text("0").formatNumber();
	}
}