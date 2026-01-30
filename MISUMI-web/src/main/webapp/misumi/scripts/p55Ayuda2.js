

function reloadAyuda2() {

	$("#p53_pestanaAyuda2Cargada").val("S");
	loadAyuda2VentasMedias();
}

function loadAyuda2VentasMedias(){	
	var vReferenciasCentro=new ReferenciasCentro($("#p60_fld_referencia").val(), $("#centerId").val());
	//var vReferenciasCentro=new ReferenciasCentro('1222', '679');
	//var vReferenciasCentro=new ReferenciasCentro('34', '679');
	var objJson = $.toJSON(vReferenciasCentro);	
	 $.ajax({
		type : 'POST',
		url : './pedidoAdicional/loadAyuda2.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (validateReloadingAy2(data)){
				//ActualizaciÃ³n de los datos de "Ventas media"
				p55updateResultadosAy2VentasMedia(data);
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
function p55updateResultadosAy2VentasMedia (data){
	if (data.historicoVentaMedia != null ){
		
		if (data.historicoVentaMedia.tarifa != null ){
			$("#p55_ventasMediaTdTarifa").text(data.historicoVentaMedia.tarifa).formatNumber();
		}else{
			$("#p55_ventasMediaTdTarifa").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.competencia != null ){
			$("#p55_ventasMediaTdCompetencia").text(data.historicoVentaMedia.competencia).formatNumber();
		}else{
			$("#p55_ventasMediaTdCompetencia").text("0").formatNumber();
		}
		if (data.historicoVentaMedia.media != null ){
			$("#p55_ventasMediaTdMedia").text(data.historicoVentaMedia.media).formatNumber();
		}else{
			$("#p55_ventasMediaTdMedia").text("0").formatNumber();
		}
	}else{
		$("#p55_ventasMediaTdTarifa").text("0").formatNumber();
		$("#p55_ventasMediaTdCompetencia").text("0").formatNumber();
		$("#p55_ventasMediaTdMedia").text("0").formatNumber();
	}
}