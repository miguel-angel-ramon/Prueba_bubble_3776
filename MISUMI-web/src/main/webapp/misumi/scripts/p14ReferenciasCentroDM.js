var tipoAprovCentralizado = null;
var tipoAprovDescentralizado = null;
var tipoAprovGrupaje = null;

$(document).ready(function(){

	initializeScreenDM()
	loadP14(locale);
	
});

function initializeScreenDM(){
	
	$("#p14_fld_tipoAprovisionamiento").attr("disabled", "disabled");
	$("#p14_fld_UFP").attr("disabled", "disabled");
	$("#p14_fld_unidadesCaja").attr("disabled", "disabled");
	$("#p14_fld_cc").attr("disabled", "disabled");
	$("#p14_fld_tipoRef").attr("disabled", "disabled");
	
	$("#p14_fld_oferta").attr("disabled", "disabled");
	$("#p14_fld_tipo").attr("disabled", "disabled");
	//$("#p14_accion").hide();
	$("#p14_accion").show();
	$("#p14_DatosEspecificosTextil").hide();


}

function updateResultadosDM(data){
	
	updateResultadosDMDatosMaestro(data);
	//Los datos de ffpp pasan a mensaje en la cabecera
	//updateResultadosDMRefRelacionadas(data);
	updateResultadosDMOferta(data);
	updateResultadosDMGamaFinSemana(data);
	$("#p13_pestanaDatosMaestrosCargada").val("S");
}

function updateResultadosDMDatosMaestro(data){
	var valorTipoAprov = data.surtidoTienda != null ? (data.surtidoTienda.tipoAprov != null ? data.surtidoTienda.tipoAprov :"C") : "C";
	var valorTipoAprovConversion = "";
	if (valorTipoAprov != null){
		switch(valorTipoAprov)
		{
		case 'C':
			valorTipoAprovConversion = tipoAprovCentralizado;
		    break;
		case 'D':
			valorTipoAprovConversion = tipoAprovDescentralizado;
			break;
		case 'G':
			valorTipoAprovConversion = tipoAprovGrupaje;
		    break;
		default:
		}
	}
	$("#p14_fld_tipoAprovisionamiento").val(valorTipoAprovConversion);
	
	if (data.surtidoTienda != null){
		if (data.esTratamientoVegalsa){
			$("#p14_fld_UFP").val(data.surtidoTienda.ufp);
		}else{
			$("#p14_fld_UFP").val(data.surtidoTienda.ufp).formatNumber({format:"0.000"});
		}
		$("#p14_fld_unidadesCaja").val(data.surtidoTienda.uniCajaServ).formatNumber({format:"0.##"});
	}
	if (data.cc != null){
		$("#p14_fld_cc").val(data.cc).formatNumber({format:"0.##"});
	}
	
	//Pintamos el tipo de referencia: C (COMPRA), V (VENTA), T (COMPRA/VENTA), 
	if (data.diarioArt.tipoCompraVenta != null) {
		if(data.diarioArt.tipoCompraVenta == 'C') {
			$("#p14_fld_tipoRef").val('COMPRA');
		}
		if(data.diarioArt.tipoCompraVenta == 'V') {
			$("#p14_fld_tipoRef").val('VENTA');
		}
		if(data.diarioArt.tipoCompraVenta == 'T') {
			$("#p14_fld_tipoRef").val('COMPRA/VENTA');
		}
		
	}
	
	//Pintamos la estructura comercial
	$("#p14_lbl_estructuraVal").text(data.diarioArt.grupo1 + "-" + data.diarioArt.grupo2 +"-" + data.diarioArt.grupo3 + "- " + data.descGrupo3);

	//Pintar el MAPA
	if (data.mapaReferencia != null){
		$("#mapaReferenciaShow").show();
		$("#p14_lbl_mapaReferenciaVal").text(data.mapaReferencia);
	} else {
		$("#mapaReferenciaShow").hide();
		$("#p14_lbl_mapaReferenciaVal").text("");
	}
	
	//Pintamos la Solo Reparto
	if (null!=data.surtidoTienda.soloReparto){
		$("#soloRepartoShow").show();
		$("#p14_lbl_soloRepartoVal").text(data.surtidoTienda.soloReparto);
	} else {
		$("#soloRepartoShow").hide();
		$("#p14_lbl_soloRepartoVal").text("");
	}
	
	$("#p14_fld_grupo1").val(data.diarioArt.grupo1)
	//if ($("#centerTipoNegocio").val() == "H" && $("#p14_fld_grupo1").val() > 2){
		if(data.accion!=null) {
			$("#p14_lbl_accionVal").text(data.accion);
		}else{
			$("#p14_lbl_accionVal").text('SIN ACCIÓN');
		}
	
		$("#p14_accion").show();
	//} else {
		//$("#p14_accion").hide();
	//}
		
	
	if ((data.diarioArt.grupo1 == 3) && (data.temporada != null || data.modeloProveedor  != null  || data.talla  != null  || data.color  != null)) {
		//Si la referencia es de textil pero no lote, pintamos los datos especificos de textil	
		$("#p14_lbl_estructuraTexilVal").text(data.temporada + " " + data.diarioArt.grupo2 + data.diarioArt.grupo3 + data.diarioArt.grupo4 +data.diarioArt.grupo5 + " " + data.numOrden);
		$("#p14_lbl_modeloProveedorVal").text(data.modeloProveedor);
		$("#p14_lbl_tallaVal").text(data.talla);
		$("#p14_lbl_colorVal").text(data.color);
		
		$("#p14_DatosEspecificosTextil").show();
		
	} else  {
		$("#p14_DatosEspecificosTextil").hide();
	}
		
}

function updateResultadosDMRefRelacionadas(data){
	if (data.articuloRelacionado != null){
		$("#p14_referenciasRelacionadasTablaRef").text(data.articuloRelacionado.codArtRela);
		$("#p14_referenciasRelacionadasTablaDesc").text(data.articuloRelacionado.descripArt);
		$("#p14_referenciasRelacionadasTablaUC").text(data.articuloRelacionado.uniCajaServ).formatNumber({format:"0.000"});
	}
}

function updateResultadosDMOferta(data){
	if (data.oferta != null && data.oferta.anoOferta != null && data.oferta.numOferta != null){
		$("#p14_fld_oferta").val(data.oferta.anoOferta + "-" + data.oferta.numOferta);
	}else{
		$("#p14_fld_oferta").val("");
	}
	if (data.oferta != null && data.oferta.dTipoOferta != null){
		$("#p14_fld_tipo").val(data.oferta.dTipoOferta);
	}else{
		$("#p14_fld_tipo").val("");
	}				
}

function updateResultadosDMGamaFinSemana(data){
	if (data.variablesPedido != null){
		$("#p14_gamaFinSemanaTablaTdL").text(data.variablesPedido.gamaDiscLunes);
		$("#p14_gamaFinSemanaTablaTdM").text(data.variablesPedido.gamaDiscMartes);
		$("#p14_gamaFinSemanaTablaTdX").text(data.variablesPedido.gamaDiscMiercoles);
		$("#p14_gamaFinSemanaTablaTdJ").text(data.variablesPedido.gamaDiscJueves);
		$("#p14_gamaFinSemanaTablaTdV").text(data.variablesPedido.gamaDiscViernes);
		$("#p14_gamaFinSemanaTablaTdS").text(data.variablesPedido.gamaDiscSabado);
		$("#p14_gamaFinSemanaTablaTdD").text(data.variablesPedido.gamaDiscDomingo);
	}
}

function resetResultadosDM (){
	$("#p14_fld_tipoAprovisionamiento").val("");
	$("#p14_fld_UFP").val("");
	$("#p14_fld_unidadesCaja").val("");
	$("#p14_fld_cc").val("");
	$("#p14_fld_grupo1").val("");
	$("#p14_fld_tipoRef").val("");
	
	$("#p14_referenciasRelacionadasTablaRef").text("");
	$("#p14_referenciasRelacionadasTablaDesc").text("");
	$("#p14_referenciasRelacionadasTablaUC").text("");

	$("#p14_fld_oferta").val("");
	$("#p14_fld_tipo").val("");

	$("#p14_gamaFinSemanaTablaTdL").text("");
	$("#p14_gamaFinSemanaTablaTdM").text("");
	$("#p14_gamaFinSemanaTablaTdX").text("");
	$("#p14_gamaFinSemanaTablaTdJ").text("");
	$("#p14_gamaFinSemanaTablaTdV").text("");
	$("#p14_gamaFinSemanaTablaTdS").text("");
	$("#p14_gamaFinSemanaTablaTdD").text("");
}

function reloadDatosMaestro() {
	$("#p13_pestanaDatosMaestroCargada").val("S");
	//$("#p14_accion").hide();
	var vReferenciasCentro=new ReferenciasCentro($("#p13_fld_referenciaEroski").val(), $("#centerId").val());
	var objJson = $.toJSON(vReferenciasCentro);	
	 $.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosMaestros.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
	
			//Actualizaci�n de los datos de "Datos maestro"
			updateResultadosDM(data);
				
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        }			
	});		
}

function loadP14(locale){
	
	this.i18nJSON = './misumi/resources/p14ReferenciasCentroDM/p14referenciasCentroDM_' + locale + '.json';
	
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				tipoAprovCentralizado = data.tipoAprovCentralizado;
				tipoAprovDescentralizado = data.tipoAprovDescentralizado;
				tipoAprovGrupaje = data.tipoAprovGrupaje;
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
           });
}

