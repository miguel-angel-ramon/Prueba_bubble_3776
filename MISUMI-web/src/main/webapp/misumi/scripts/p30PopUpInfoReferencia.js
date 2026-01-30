//Incio variables misumi-124
var esPlanogramaRef = 1;
var esSfmRef = 2;
var esFacCapRef = 3;
var esCapRef = 4;
var esFacNoAliRef  = 5;


var esCajaExpositoraRef = 1;
var esMadreRef = 2;
var esFFPPRef = 3;
//Fin variables misumi-124

var referenceLabel=null;
var grupajeCon=null;
var centralizadaCon=null;
var descentralizadaCon=null;
var othersCon=null;
var gridP30Motivos=null;
var gridP30MMC=null;
var p30capacity=null;
var p30facing=null;
var p30facingVegalsa=null;
var p30fondoVegalsa=null;
var grid30Pedido=null;
var p30StockMinimo=null;
var p30Multiplicador=null;
var p30Imc=null;
var codigoArticulo = null;

var mensajeMapaHoyN = null;
var mensajeMapaHoySPedirS = null;
var mensajeErrorWSStock = null;
var gridP30Motivos=null;
var gridP30MotivosMMC=null;

var mensajeImpSFM=null;
var mensajeCapSFM=null;
var mensajeFacing=null;
var mensajeFacingCapacidad = null;

//Mensaje tipo plano P petición MISUMI-37
var mensajeTipoPlanoP;
var mensajeTipoPlanoPCExp;

var flagP30 = null;

//Para la descripción.
var noTienePlanograma = null;
var p30UC;
var p30yCon;

var p30labelFilesetNoActivaVegalsa;
var p30mensajeFilesetNoActivaVegalsa;


$(document).ready(function(){

	initializeScreenP30PopupInfoRef();
	events_p30_btn_masInfoRef();
	events_p30_btn_incluirRefGama();
	events_p30_btn_deshacerIncluirRefGama();
	events_p30_btn_excluirRefGama();
	events_p30_btn_deshacerExcluirRefGama();
	events_p30_btn_masInfoPedidos();
	
	gridP30Motivos = new GridP30Motivos(locale);
	gridP30MMC	= new GridP30MotivosMMC(locale);
	grid30Pedido = new GridP30UPed(locale);
	$("#p30_divAccion").show();
});

function resetResultadosP30(){
	$("#p30_lbl_stockActualVal").removeClass("p30_stockActualMensajeErrorWS").addClass("valorCampo");
	$('#p30_popupInfoReferencia').animate({scrollTop: '0px'}, 0);
	$("#p30_lbl_pendienteRecibir1Val").text("");
	$("#p30_lbl_pendienteRecibir2Val").text("");
	$("#p30_AreaFotos").hide();
	$("#p30_divImportante").hide();
	$("#p30_respuestaWS").hide();
	$("#p30_lbl_mensajeIncluirExcluir").text("");	
}

function loadP30PopupMessages(locale, codArt){


	var jqxhr = $.getJSON('./misumi/resources/p30PopupInfoReferencia/p30PopupInfoReferencia_' + locale + '.json',
			function(data) {

	})
	.success(function(data) {
		grid30Pedido.colNames = data.ColNames;
		grid30Pedido.title = data.GridTitle;
		grid30Pedido.emptyRecords= data.emptyRecords;
		referenceLabel=data.referenceLabel;
		grupajeCon=data.grupajeCon;
		centralizadaCon=data.centralizadaCon;
		descentralizadaCon=data.descentralizadaCon;
		othersCon=data.othersCon;
		mensajeErrorWSStock=data.mensajeErrorWSStock;
		mensajeImpSFM=data.mensajeImpSFM;
		mensajeCapSFM=data.mensajeCapSFM;
		mensajeFacing=data.mensajeFacing;
		mensajeFacingCapacidad = data.mensajeFacingCapacidad;
		loadReferenceData(locale);
		gridP30Motivos.colNames = data.p30MotivosColNames;
		gridP30Motivos.emptyRecords= data.emptyRecords;
		gridP30MMC.colNames = data.p30MotivosColNames;
		gridP30MMC.emptyRecords= data.emptyRecords;	
		p30capacity=data.p30capacity;
		p30facing=data.p30facing;
		p30facingVegalsa=data.p30facingVegalsa;
		p30fondoVegalsa=data.p30fondoVegalsa;
		p30StockMinimo=data.p30StockMinimo;
		p30Multiplicador=data.p30Multiplicador;
		p30Imc=data.p30Imc;
		noTienePlanograma = data.noTienePlanograma;
		p30yCon = data.p30yCon;
		p30UC = data.p30UC;
		mensajeTipoPlanoP = data.mensajeTipoPlanoP;
		mensajeTipoPlanoPCExp = data.mensajeTipoPlanoPCExp;
		
		p30labelFilesetNoActivaVegalsa = data.p30labelFilesetNoActivaVegalsa;
		p30mensajeFilesetNoActivaVegalsa = data.p30mensajeFilesetNoActivaVegalsa;
		
		
		loadP30MotivosMock();
		loadP30MotivosMMCMock();
		load_gridP30UPedMock();
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});

}

function loadDataPopUpP30(locale, codArt){
	//Recogemos el codigo articulo solo para el pop up del Motivo del Bloqueo
	codigoArticulo = codArt;

	resetResultadosP30();
	loadP30PopupMessages(locale, codArt);
}

function loadReferenceData(locale){


	if ($("#p01_txt_infoRef").val() == ""){
		var vReferenciasCentro=new ReferenciasCentro(codigoArticulo, $("#centerId").val(), 'InforBasica');
	}else{
		var vReferenciasCentro=new ReferenciasCentro($("#p01_txt_infoRef").val(), $("#centerId").val(), 'InforBasica');
	}


	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosMaestrosFijo.do?origen=BUSCADOR',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if ( data.length == 0 || 
					data.diarioArt == null ||
					data.diarioArt.length ==0){
				//reloadDataP34($("#p01_txt_infoRef").val(), "informacionBasica","", false, false);
				createAlert(replaceSpecialCharacters(emptyRecordReference), "ERROR");
			}else{
				if (data.respuestaWSStock!=null){
					$("#p30_respuestaWS").show();
					$("#p30_respuestaWS").text(data.respuestaWSStock);
				}else{
					$("#p30_respuestaWS").hide();
				}
				
				$("#p30_fld_referenciaEroski").val(data.codArtEroski);
				$("#p30_popupInfoReferencia").dialog( "open" );
				$("#p30_fld_mapaHoy").val(data.surtidoTienda != null ? (data.surtidoTienda.mapaHoy != null ? data.surtidoTienda.mapaHoy : 'N'):'N');
				$("#p30_fld_refActiva").val(data.surtidoTienda != null ? (data.surtidoTienda.pedir != null ? data.surtidoTienda.pedir : 'N'):'N');
				p30ActivateFielsetPedidoAuto(data);

				if (esCentroCaprabo){
					$("#p30_lbl_referenciaVal").text(data.codArtCaprabo + " - " +data.diarioArt.descripArt);
				}else{
					$("#p30_lbl_referenciaVal").text(data.diarioArt.codArt + " - " +data.diarioArt.descripArt);
				}
				$("#p30_fld_grupo1").val(data.diarioArt.grupo1);

				//Pintamos la estructura comercial
				$("#p30_lbl_estructuraVal").text(data.diarioArt.grupo1 + "-" + data.diarioArt.grupo2 +"-" + data.diarioArt.grupo3 + "- " + data.descGrupo3);

				if (data.mapaReferencia != null){
					$("#p30_lbl_mapa").attr("style", "display:inline");
					$("#p30_lbl_mapaVal").attr("style", "display:inline");
					$("#p30_lbl_mapaVal").text(data.mapaReferencia);
				}else if (data.mapaReferencia == null){
					$("#p30_lbl_mapa").attr("style", "display:none");
					$("#p30_lbl_mapaVal").attr("style", "display:none");
					$("#p30_lbl_mapaVal").text(data.mapaReferencia);
				}
				
				//Pintamos la Solo Reparto
				if (null != data.surtidoTienda && null != data.surtidoTienda.soloReparto){
					$("#p30_lbl_soloReparto").attr("style", "margin-left: 150px; display:inline");
					$("#p30_lbl_soloRepartoVal").attr("style", "display:inline");
					$("#p30_lbl_soloRepartoVal").text(data.surtidoTienda.soloReparto);
				}else if (null != data.surtidoTienda && null == data.surtidoTienda.soloReparto){
					$("#p30_lbl_soloReparto").attr("style", "margin-left: 150px; display:none");
					$("#p30_lbl_soloRepartoVal").attr("style", "display:none");
					$("#p30_lbl_soloRepartoVal").text(data.surtidoTienda.soloReparto);
				}
				
				//Pintamos el tipo de referencia: C (COMPRA), V (VENTA), T (COMPRA/VENTA)
				if (data.diarioArt.tipoCompraVenta != null) {
					if(data.diarioArt.tipoCompraVenta == 'C') {
						$("#p30_lbl_tipoReferenciaVal").text('COMPRA');
					}
					if(data.diarioArt.tipoCompraVenta == 'V') {
						$("#p30_lbl_tipoReferenciaVal").text('VENTA');
					}
					if(data.diarioArt.tipoCompraVenta == 'T') {
						$("#p30_lbl_tipoReferenciaVal").text('COMPRA/VENTA');
					}

				}
				if(data.stockPlataforma != null){
					$("#p30_lbl_stockPlataformaVal").text($.formatNumber(data.stockPlataforma,{format:"0.00",locale:"es"}));
					$("#p30_stockPlataforma").show();
				}else{
					$("#p30_stockPlataforma").hide();
				}
				
				$("#p30_referenciasCentroDescripcionMensajeError").css("display", "none");
				
				getDescriptionInfoPopupNuevo(data);
				loadPedidoBasicInfo();
				loadp30StockWS();
				loadP30Foto(data.tieneFoto);
				loadp30PendientesRecibir(locale);
				loadP30PuedePedir();

				//if ($("#centerTipoNegocio").val() == "H" && $("#p30_fld_grupo1").val() > 2){
				if(data.accion!=null){
					$("#p30_lbl_accionVal").text(data.accion);

				}else{
					$("#p30_lbl_accionVal").text('SIN ACCIÓN');
				}
				$("#p30_divAccion").show();
				//} else {
				//$("#p30_divAccion").hide();
				//}


				//Oferta y precios

				if(data.ofertaPVP != null){


					if(data.ofertaPVP.tarifa > 0) {
						$("#p30_lbl_pvpVal").text(data.ofertaPVP.tarifaStr);
						$("#p30_divPvp").show();
					}

					// MISUMI-391 MISUMIS-JAVA VEGALSA-FAMILIA sacar las ofertas de oferta vigente					
					if(data.ofertaPVP.annoOferta != null && data.ofertaPVP.codOferta != null && 
							data.ofertaPVP.annoOferta > 0 && data.ofertaPVP.codOferta > 0) {
						$("#p30_lbl_ofertaVal").text(data.ofertaPVP.annoOferta + "-" + data.ofertaPVP.codOferta);
						$("#p30_divOferta").show();
					}else{
						$("#p30_divOferta").hide();
					}
					if(data.ofertaPVP.flgMostrarOfertaPC == "S"){
						$("#p30_lbl_pvpOfertaVal").text(data.ofertaPVP.pvpOferStr);
						$("#p30_divPvpOferta").show();
					}else{
						$("#p30_divPvpOferta").hide();
						
					}

				} else {
					$("#p30_divPvp").hide();
					$("#p30_divPvpOferta").hide();
					$("#p30_divOferta").hide();
				}
				
				//MISUMI-428
				if(data.mac != null && data.mac!="" && data.espacio!=null && data.espacio!=""){
					$("#p30_divMAC").show();
					$("#p30_divEspacio").show();
					$("#p30_lbl_montajeAdicionalCentroVal").text(data.mac);
					$("#p30_lbl_espacioVal").text(data.espacio);
				}else{
					$("#p30_divMAC").hide();
					$("#p30_divEspacio").hide();
				}
				if(data.cantidadMA != null){
					$("#p30_divMA").show();
					$("#p30_lbl_montajeAdicionalVal").text(data.cantidadMA + " Desde " + p30FormateoFechaEntrega(data.fechaInicioMA) + " al " + p30FormateoFechaEntrega(data.fechaFinMA));
				}else{
					$("#p30_divMA").hide();
				}

				if ((data.diarioArt.grupo1 == 3) && (data.temporada != null || data.modeloProveedor  != null  || data.talla  != null  || data.color  != null)) {
					//Si la referencia es de textil pero no lote, pintamos los datos especificos de textil	
					$("#p30_lbl_estructuraTexilVal").text(data.temporada + " " + data.diarioArt.grupo2 + data.diarioArt.grupo3 + data.diarioArt.grupo4 +data.diarioArt.grupo5 + " " + data.numOrden);
					$("#p30_lbl_modeloProveedorVal").text(data.modeloProveedor);
					$("#p30_lbl_tallaVal").text(data.talla);
					$("#p30_lbl_colorVal").text(data.color);

					$("#p30_DatosEspecificosTextil").show();

				} else  {
					$("#p30_DatosEspecificosTextil").hide();
				}

				if(data.surtidoTienda ==null || data.surtidoTienda.marcaMaestroCentro == null){
					$("#p30_btn_incluirRefGama").hide();
					$("#p30_btn_excluirRefGama").hide();
					$("#p30_btn_deshacerIncluirRefGama").hide();
					$("#p30_btn_deshacerExcluirRefGama").hide();
				}else{
					if(data.surtidoTienda.marcaMaestroCentro == 'S'){
						$("#p30_btn_incluirRefGama").hide();
						$("#p30_btn_deshacerIncluirRefGama").hide();
						$("#p30_btn_excluirRefGama").show();
						if(data.existeGama){
							$("#p30_btn_excluirRefGama").prop("disabled", true);
							$("#p30_btn_excluirRefGama").css('cursor', 'default');
							$("#p30_btn_deshacerExcluirRefGama").show();
						}else{
							$("#p30_btn_excluirRefGama").prop("disabled", false);
							$("#p30_btn_excluirRefGama").css('cursor', 'pointer' );
							$("#p30_btn_deshacerExcluirRefGama").hide();
						}
						
					}else if(data.surtidoTienda.marcaMaestroCentro == 'N'){
						$("#p30_btn_excluirRefGama").hide();
						$("#p30_btn_deshacerExcluirRefGama").hide();
						$("#p30_btn_incluirRefGama").show();
						if(data.existeGama){
							$("#p30_btn_incluirRefGama").prop("disabled", true);
							$("#p30_btn_incluirRefGama").css('cursor', 'default');
							$("#p30_btn_deshacerIncluirRefGama").show();
						}else{
							$("#p30_btn_incluirRefGama").prop("disabled", false);
							$("#p30_btn_incluirRefGama").css('cursor', 'pointer' );
							$("#p30_btn_deshacerIncluirRefGama").hide();
						}
					}else{
						$("#p30_btn_incluirRefGama").hide();
						$("#p30_btn_excluirRefGama").hide();
						$("#p30_btn_deshacerIncluirRefGama").hide();
						$("#p30_btn_deshacerExcluirRefGama").hide();
					}
				}

			}
			$("#infoRef").show();
		},
		error : function (xhr, status, error){
			$("#infoRef").show();
			handleError(xhr, status, error, locale);
		}			
	});		
}

function p30ActivateFielsetPedidoAuto(data){
	if (data.flgDepositoBrita != null && data.flgDepositoBrita == 'S'){
		p30MostrarMensajePedidoDepositoBrita();
	}else if (data.flgPorCatalogo != null && data.flgPorCatalogo == 'S'){
		p30MostrarMensajePedidoPorCatalogo();
	}else if (data.esTratamientoVegalsa){
		if (data.surtidoTienda != null && data.surtidoTienda.marcaMaestroCentro != null && data.surtidoTienda.marcaMaestroCentro == 'S'){
			p30MostrarMensajePedidoActivoVegalsa(data);
		} else {
			p30MostrarMensajePedidoNoActivoVegalsa(data);
		}
	}else{
		//Evaluaci�n del dato pedir para y mapaHoy para mostrar el mensaje de pedido autom�tico
		if (data.surtidoTienda != null && data.surtidoTienda.pedir != null && data.surtidoTienda.pedir == 'S'){
			if (data.surtidoTienda.mapaHoy != null){
				if (data.surtidoTienda.mapaHoy == 'N'){
					if (data.surtidoTienda.numeroPedidosOtroDia == 0){
						//No hay pedido ning�n d�a
						p30MostrarMensajePedidoNoActivo();
					}else{
						//Hoy no hay pedido pero si alg�n otro d�a
						p30MostrarMensajePedidoActivo();
					}
				}else{
					p30MostrarMensajePedidoActivo();
				}
			}else{
				p30MostrarMensajePedidoNoActivo();
			}
		}else{
			p30MostrarMensajePedidoNoActivo();
		}
	}
}
function getDescriptionInfoPopup(data){
	var descriptionTot = getReferenceReferenciaIP(data);

	if(data.surtidoTienda != null){
		descriptionTot = descriptionTot +  getUnitesCPIP(data) ;	
	}

	loadP30ParamStockFinalMinimo(descriptionTot,data);


}

function getDescriptionInfoPopupNuevo(data){
	var descriptionTot=getReferenceReferenciaIP(data);	

	if(data.surtidoTienda != null){
		descriptionTot = descriptionTot + getUnitesCPIP(data);
	}
	loadP30Descipcion(descriptionTot);
}

function loadP30Descipcion(descriptionTot){	
	var vReferenciasCentro;
	if ($("#p01_txt_infoRef").val() == ""){
		vReferenciasCentro=new ReferenciasCentro(codigoArticulo, $("#centerId").val());
	}else{
		vReferenciasCentro=new ReferenciasCentro($("#p01_txt_infoRef").val(), $("#centerId").val());
	}
	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadDatosImagenComercialNuevo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data != null){				
				/* Obtenemos el método de la referencia.
				Puede ser:
			 		o   1-PLANOGRAMA
					o	2-SFM
					o	3-FAC-CAP
					o	4-CAP
					o	5-FAC NO ALI */

				var metodo = data.metodo;

				var sfm = data.sfm;
				var facing = data.facing;
				var imc = 	data.imc;
				var capacidad = data.capacidad;
				var facingAlto = data.facingAlto;
				var facingAncho = data.facingAncho;
				var tipoReferencia = data.tipoReferencia;
				var tratamientoVegalsa = data.tratamientoVegalsa;
				var flgErrorWSFacingVegalsa = data.flgErrorWSFacingVegalsa;

				var descripcion;

				//Hay veces que sobra el "y con" de texto.
				var descriptionTotReducida = descriptionTot.replace(p30yCon,'');

				
				if (tratamientoVegalsa == 1){
					
					if(capacidad != null && facing != null && data.multiplicador != null){
						descripcion = descriptionTot + capacidad + p30capacity + facing + p30facingVegalsa + data.multiplicador + p30fondoVegalsa;
					}else{
						descripcion = descriptionTotReducida;
					}

					//Se añade la descripción
					$("#p30_lbl_descripcionVal").text(descripcion);
					
					// Si se ha producido un error al atacar al WS se mostará un error.
					if (flgErrorWSFacingVegalsa == 1){
						$("#p30_referenciasCentroDescripcionMensajeError").css("display", "inline-block");
					}else{
						$("#p30_referenciasCentroDescripcionMensajeError").css("display", "none");
					}

				}else{
				
				//Referencia de SFM
				if (metodo == esSfmRef){ 
					//Se crea el texto de la descripcion 
					descripcion = descriptionTot + sfm + p30StockMinimo;
	
					//Se añade la descripción.
					$("#p30_lbl_descripcionVal").text(descripcion);	
				}		
				//Referencia facing.
				else if(metodo == esFacNoAliRef){
					//Si no es ffpp se muestra multiplicador.
					if(tipoReferencia != esFFPPRef){
						if(facing != null && data.multiplicador != null && imc != null){
							descripcion = descriptionTot +  facing + p30facing + p30Multiplicador + data.multiplicador +  p30Imc + imc;
						}else{
							descripcion = descriptionTotReducida + noTienePlanograma;
						}
					}else{
						if(facing != null && imc != null){
							//Si es ffpp no se muestra el multiplicador
							descripcion = descriptionTot + facing + p30facing +  p30Imc + imc;							
						}else{
							descripcion = descriptionTotReducida + noTienePlanograma;
						}
					}	
	
					//Se añade la descripción
					$("#p30_lbl_descripcionVal").text(descripcion);	
				}
				//Referencia capfac o capfac
				else if(metodo == esFacCapRef || metodo == esCapRef){
					//Si no es ffpp se muestra multiplicador.
					if(tipoReferencia != esFFPPRef){
						if(capacidad != null && facing != null && data.multiplicador != null && imc != null){
							descripcion = descriptionTot +  capacidad + p30capacity + facing + p30facing + p30Multiplicador + data.multiplicador +  p30Imc + imc;
						}else{
							descripcion = descriptionTotReducida + noTienePlanograma;
						}
					}else{
						if(capacidad != null && facing != null && imc != null){
							//Si es ffpp no se muestra el multiplicador
							descripcion = descriptionTot + capacidad + p30capacity + facing + p30facing +  p30Imc + imc;
						}else{
							descripcion = descriptionTotReducida + noTienePlanograma;
						}
					}	
					//Se añade la descripción
					$("#p30_lbl_descripcionVal").text(descripcion);	
				}
				//Referencia planograma
				else if(metodo == esPlanogramaRef){
					var esCajaExpositoraRef = 1;
					var esMadreRef = 2;
					var esFFPPRef = 3;
	
					//Si es caja expositora
					if(tipoReferencia == esCajaExpositoraRef){
						//El alto y ancho tienen que ser mayores que 1 para mostrarse
						if(facingAlto > 0 && facingAncho > 0){
							if(capacidad != null && facing != null && data.multiplicador != null && imc != null && facingAlto != null && facingAncho != null){
								var anchoAltoAyuda = mensajeTipoPlanoPCExp.replace('{0}',facingAlto);
	
								var anchoAltoAyuda2 = anchoAltoAyuda.replace('{1}','X');
								var anchoAlto = anchoAltoAyuda2.replace('{2}',facingAncho);
	
	
								descripcion = descriptionTot +  capacidad + p30capacity + facing + p30facing + anchoAlto + p30Multiplicador + data.multiplicador +  p30Imc + imc;
							}else{
								descripcion = descriptionTotReducida + noTienePlanograma;
							}
						}else{
							if(capacidad != null && facing != null && data.multiplicador != null && imc != null){
								descripcion = descriptionTot +  capacidad + p30capacity + facing + p30facing + p30Multiplicador + data.multiplicador +  p30Imc + imc;
							}else{
								descripcion = descriptionTotReducida + noTienePlanograma;
							}
						}
					}
					//Si es referencia madre
					else if(tipoReferencia == esMadreRef){
						if(facingAncho > 0){
							if(capacidad != null && facing != null && data.multiplicador != null && imc != null && facingAlto != null && facingAncho != null){
								var anchoAltoAyuda = mensajeTipoPlanoP.replace('{0}',facingAlto);
	
								var anchoAltoAyuda2	 = anchoAltoAyuda.replace('{1}','X');
								var anchoAlto = anchoAltoAyuda2.replace('{2}',facingAncho);
	
								descripcion = descriptionTot +  capacidad + p30capacity + facing + p30facing + anchoAlto +  p30Multiplicador + data.multiplicador +  p30Imc + imc;
							}else{
								descripcion = descriptionTotReducida + noTienePlanograma;
							}
						}else{
							if(capacidad != null && facing != null && data.multiplicador != null && imc != null){
								descripcion = descriptionTot +  capacidad + p30capacity + facing + p30facing + p30Multiplicador + data.multiplicador +  p30Imc + imc;
							}else{
								descripcion = descriptionTotReducida + noTienePlanograma;
							}
						}						
					}
					//Si es FFPP
					else if(tipoReferencia == esFFPPRef){
						if(capacidad != null && facing != null && imc != null){
							descripcion = descriptionTot +  capacidad + p30capacity + facing + p30facing +  p30Imc + imc;
						}else{
							descripcion = descriptionTotReducida + noTienePlanograma;
						}
					}
	
					//Se añade la descripción
					$("#p30_lbl_descripcionVal").text(descripcion);	
				} else {
					//Se añade la descripción
					$("#p30_lbl_descripcionVal").text(descriptionTotReducida);
				}
				}
					
				//Si hay aviso en IMPORTANTE se muestra.
				var avisoCambio = data.avisoCambio
				if (avisoCambio!=null && tratamientoVegalsa == 0){
					$("#p30_lbl_importanteVal").text(avisoCambio);
					$("#p30_divImportante").show();
				}else{
					$("#p30_divImportante").hide();
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}



function getReferenceReferenciaIP(data){
	var referenciaComplete = referenceLabel;
	var tipoAprov =  data.surtidoTienda != null ? (data.surtidoTienda.tipoAprov != null ? data.surtidoTienda.tipoAprov : "C") : "C"
		if (tipoAprov=="G"){
			referenciaComplete =referenciaComplete + " " + grupajeCon;
		}else if(tipoAprov=="C"){
			referenciaComplete =referenciaComplete + " " + centralizadaCon;
		}else if(tipoAprov=="D"){
			referenciaComplete =referenciaComplete + " " + descentralizadaCon;
		}else {
			referenciaComplete =referenciaComplete + " " + othersCon;
		}
	return referenciaComplete;
}
function getUnitesCPIP(data){
	var referenciaComplete = " con";

	referenciaComplete =referenciaComplete + " " + $.formatNumber(data.surtidoTienda.uniCajaServ,{format:"0.##",locale:"es"});
	referenciaComplete=referenciaComplete + p30UC + p30yCon;

	return referenciaComplete;


}

function p30MostrarMensajePedidoDepositoBrita(){
	//Mostrar el mensaje de pedido deposito brita
	$("#p30_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:inline");
	$("#p30_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
}

function p30MostrarMensajePedidoPorCatalogo(){
	//Mostrar el mensaje de pedido por catálogo
	$("#p30_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:inline");
	$("#p30_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
}

function p30MostrarMensajePedidoActivo(){
	//Mostrar el mensaje de pedido autom�tico activo
	$("#p30_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivo").attr("style", "display:inline");
	$("#p30_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
}

function p30MostrarMensajePedidoActivoVegalsa(data){
	//Mostrar el mensaje de pedido automatico activo
	$("#p30_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:inline");
	$("#p30_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:none");
	
	//$("#p30_TextoFecha").text($("#p30_TextoFecha").text().replace("#VALOR#", data.surtidoTienda.fechaMmcStr));
	
	$("#p30_TextoFecha").html(reemplazarFecha($("#p30_fld_TextoFechaBasico").val(), data.surtidoTienda.fechaMmcStr, data.bloqueo));
}

function reemplazarFecha(texto, fecha, bloqueo) {
	if (fecha != null && fecha != "" && typeof(fecha) != 'undefined') {
		if (bloqueo != null && bloqueo != "" && typeof(bloqueo) != 'undefined') {
			return texto + " con fecha " + fecha + ' <span style="color:red;font:bold 12px Verdana, Arial, Tahoma, Helvetica, sans-serif;">con estado ' + bloqueo + '</span>';
		} else {
			return texto + " con fecha " + fecha;
		}
	} else {
		if (bloqueo != null && bloqueo != "" && typeof(bloqueo) != 'undefined') {
			return texto + ' <span style="color:red;font:bold 12px Verdana, Arial, Tahoma, Helvetica, sans-serif;">con estado ' + bloqueo + '</span>';
		} else {
			return texto;
		}
	}
}

function p30MostrarMensajePedidoNoActivo(){
	//Mostrar el mensaje de pedido automatico no activo
	$("#p30_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:inline");
	loadP30GridsFromWebservices();
}

function p30MostrarMensajePedidoNoActivoVegalsa(data){
	//Mostrar el mensaje de pedido automatico no activo
	$("#p30_pedidoAutomaticoFieldsetDepositoBrita").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetPorCatalogo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivo").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetActivoVegalsa").attr("style", "display:none");
	$("#p30_pedidoAutomaticoFieldsetNoActivo").attr("style", "display:inline");
	
	if (null != data.surtidoTienda){
		// En este caso cambiamos el texto por javascript porque esa capa tiene mucha logica y no merece repetirla
		$("#p30_pedidoAutomaticoLegendNoActivo").text(p30labelFilesetNoActivaVegalsa);
		$("#p30_parrafoPedidoAutomaticoNoActivo").text(p30mensajeFilesetNoActivaVegalsa.replace("#VALOR#", data.surtidoTienda.fechaMmcStr));
	
		loadP30GridsFromWebservices();
	}
}

function initializeScreenP30PopupInfoRef(){

	$( "#p30_popupInfoReferencia" ).dialog({
		autoOpen: false,
		height: 645,
		width: 860,
		modal: true,
		resizable: false,
		buttons:[{
			text: "Aceptar",
			click: function() {
				$(this).dialog('close');
			}
		}],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$('#p30_popupInfoReferencia').dialog('close');
			});
		},
		beforeClose: function() {
			$('#p01_txt_infoRef').focus();
			$('#p01_txt_infoRef').select();
		}
	});

	$(window).bind('resize', function() {
		$("#p30_popupInfoReferencia").dialog("option", "position", "center");
	});

	$(".controlReturnP30").on("keydown", function(e) {
		if (e.which == 13) {
			$('#p30_popupInfoReferencia').dialog('close');

		}
	});

}

function events_p30_btn_masInfoRef(){
	$("#p30_btn_masInfoRef").click(function () {
		$( "#p30_popupInfoReferencia" ).dialog('close');

		if($("#p01_txt_infoRef").val()== ""){
			window.location='./referenciasCentro.do?reference='+codigoArticulo;
		}else{
			var pagConsulta="p30_btn_masInfoRef";
			window.location='./referenciasCentro.do?reference='+$("#p01_txt_infoRef").val()+'&pagConsulta='+pagConsulta;
		}

	});
}

function events_p30_btn_incluirRefGama(){
	$("#p30_btn_incluirRefGama").click(function () {
		incluirExcluirRefGama(1);
	});
}

function events_p30_btn_deshacerIncluirRefGama(){
	$("#p30_btn_deshacerIncluirRefGama").click(function () {
		incluirExcluirRefGama(3);
	});
}

function events_p30_btn_excluirRefGama(){
	$("#p30_btn_excluirRefGama").click(function () {
		incluirExcluirRefGama(2);
	});
}

function events_p30_btn_deshacerExcluirRefGama(){
	$("#p30_btn_deshacerExcluirRefGama").click(function () {
		incluirExcluirRefGama(4);
	});
}

function incluirExcluirRefGama(incluirExcluir){
	if ($("#p01_txt_infoRef").val() == ""){
		var vReferenciasCentro=new ReferenciasCentro(codigoArticulo, $("#centerId").val(), 'InforBasica');
	}else{
		var vReferenciasCentro=new ReferenciasCentro($("#p01_txt_infoRef").val(), $("#centerId").val(), 'InforBasica');
	}


	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/incluirExluirRefGama.do?incluirExluir='+incluirExcluir,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data != null){
				var desc= data.descError;
				$("#p30_lbl_mensajeIncluirExcluir").text(desc);	
				// Si codError es 0 entonces letras en verde; si codError es diferente de 0 entonces letras en rojo
				if(data.codError != 0){
					$("#p30_lbl_mensajeIncluirExcluir").attr("style", "color:red");  
				}else{
					$("#p30_lbl_mensajeIncluirExcluir").attr("style", "color:green"); 
					if(data.accion == "1"){
						$("#p30_btn_incluirRefGama").prop("disabled", true);
						$("#p30_btn_incluirRefGama").css('cursor', 'default');
						$("#p30_btn_deshacerIncluirRefGama").show();
						$("#p30_btn_excluirRefGama").hide();
						$("#p30_btn_deshacerExcluirRefGama").hide();
					}else if(data.accion == "2"){
						$("#p30_btn_excluirRefGama").prop("disabled", true);
						$("#p30_btn_excluirRefGama").css('cursor', 'default');
						$("#p30_btn_deshacerExcluirRefGama").show();
						$("#p30_btn_incluirRefGama").hide();
						$("#p30_btn_deshacerIncluirRefGama").hide();
					}else if(data.accion == "3"){
						$("#p30_btn_incluirRefGama").prop("disabled", false);
						$("#p30_btn_incluirRefGama").css('cursor', 'pointer');
						$("#p30_btn_deshacerIncluirRefGama").hide();
						$("#p30_btn_excluirRefGama").hide();
						$("#p30_btn_deshacerExcluirRefGama").hide();
					}else if(data.accion == "4"){
						$("#p30_btn_excluirRefGama").prop("disabled", false);
						$("#p30_btn_excluirRefGama").css('cursor', 'pointer');
						$("#p30_btn_deshacerIncluirRefGama").hide();
						$("#p30_btn_incluirRefGama").hide();
						$("#p30_btn_deshacerExcluirRefGama").hide();
					}
					
				}
			}else{
				$("#p30_lbl_mensajeIncluirExcluir").text("");	
			}
			
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
	
}

function events_p30_btn_masInfoPedidos(){
	$("#p30_btn_masInfoPedidos").click(function () {
		$( "#p30_popupInfoReferencia" ).dialog('close');
		if($("#p30_fld_referenciaEroski").val() == ""){
			window.location='./misPedidos.do?reference='+codigoArticulo+"&flgMasInfPedidos=S";
		}else{
			window.location='./misPedidos.do?reference='+$("#p01_txt_infoRef").val()+"&flgMasInfPedidos=S";
		}
	});
}


function load_gridP30UPedMock() {

	$(grid30Pedido.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : grid30Pedido.colNames,
		colModel :[
		           {
		        	   "name"  : "fechaPed",
		        	   "index" : "fechaPed",
		        	   "width" : 160,
		        	   "formatter":"date",
		        	   "formatoptions":{
		        		   "srcformat": "Y-m-d",
		        		   "newformat": "d/m/Y"
		        	   },
		        	   "align" : "center",
		        	   "sortable" : false
		           },{
		        	   "name"  : "confirmadas",
		        	   "index" : "confirmadas", 
		        	   "width" : 125,
		        	   "formatter": imageFormatNormal,	
		        	   "align" : "center",
		        	   "sortable" : false					
		           },{
		        	   "name"  : "nsr",
		        	   "index" : "nsr", 
		        	   "width" : 125,
		        	   "formatter": imageFormatNSR,
		        	   "align" : "center",
		        	   "sortable" : false
		           },{
		        	   "name"  : "empuje",
		        	   "index" : "empuje",
		        	   "width" : 125,
		        	   "formatter": imageFormatEmpuje,
		        	   "align" : "center",
		        	   "sortable" : false
		           },{
		        	   "name"  : "impCab",
		        	   "index" : "impCab",
		        	   "width" : 125,
		        	   "formatter": imageFormatImpl,
		        	   "align" : "center",
		        	   "sortable" : false						
		           },{
		        	   "name"  : "intertienda",
		        	   "index" : "intertienda",
		        	   "width" : 125,
		        	   "formatter": imageFormatIntertienda,
		        	   "align" : "center",
		        	   "sortable" : false
		           }
		           ], 
		           rowNum : 5,
		           rowList : [],
		           height : "100%",
		           autowidth : false,
		           pginput: false,
		           width : "100%",
		           rownumbers : grid30Pedido.rownumbers,
		           pager : grid30Pedido.pagerNameJQuery,
		           viewrecords : false,
		           caption : grid30Pedido.title,
		           altclass: "ui-priority-secondary",
		           altRows: true, //false, para que el grid no muestre cebrado
		           hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		           sortable : false,
		           index: grid30Pedido.sortIndex,
		           emptyrecords : grid30Pedido.emptyRecords,
		           gridComplete : function() {
		        	   grid30Pedido.headerHeight("p30_gridUltPedidosHeader");	
		           },
		           loadComplete : function(data) {
		           },
		           jsonReader : {
		        	   root : "rows",
		        	   page : "page",
		        	   total : "total",
		        	   records : "records",
		        	   repeatitems : false
		           },
		           loadError : function (xhr, status, error){
		        	   handleError(xhr, status, error, locale);
		           }
	});

}

function imageFormatNormal(cellValue, opts, rowObject) {

	var imagen = "";
	var textoCajas = "";

	if (rowObject.formato == "FFPP") {
		textoCajas = "FFPP";
	} else {
		if (rowObject.cajaNormal == 1) {
			textoCajas = "caja";
		}else{
			textoCajas = "cajas";
		}
	}

	if (cellValue == "S"){
		imagen = "<div class='p30_celda'><div class='p30_celdaImagen'><img src='./misumi/images/dialog-accept-24.png'/></div><div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaNormal,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>";
	}else if (cellValue == "X"){
		imagen = "<div class='p30_celda'><div class='p30_celdaImagen'><img src='./misumi/images/dialog-help-24_2.png'/></div><div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaNormal,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>";
	}else{
		imagen = "";
	} 

	return imagen;
}

function imageFormatEmpuje(cellValue, opts, rowObject) {

	var imagen = "";
	var textoCajas = "";

	if (rowObject.formato == "FFPP") {
		textoCajas = "FFPP";
	} else {
		if (rowObject.cajaEmpuje == 1) {
			textoCajas = "caja";
		}else{
			textoCajas = "cajas";
		}
	}

	if (cellValue == "S"){
		imagen = "<div class='p30_celda'><div class='p30_celdaImagen'><img src='./misumi/images/dialog-accept-24.png'/></div><div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaEmpuje,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>";
	}else if (cellValue == "X"){
		imagen = "<div class='p30_celda'><div class='p30_celdaImagen'><img src='./misumi/images/dialog-help-24_2.png'/></div><div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaEmpuje,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>";
	}else{
		imagen = "";
	} 

	return imagen;
}

function imageFormatImpl(cellValue, opts, rowObject) {

	var imagen = "";
	var textoCajas = "";

	if (rowObject.formato == "FFPP") {
		textoCajas = "FFPP";
	} else {
		if (rowObject.cajaImpl == 1) {
			textoCajas = "caja";
		}else{
			textoCajas = "cajas";
		}
	}

	if (cellValue == "S"){
		imagen = "<div class='p30_celda'><div class='p30_celdaImagen'><img src='./misumi/images/dialog-accept-24.png'/></div><div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaImpl,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>";
	}else if (cellValue == "X"){
		imagen = "<div class='p30_celda'><div class='p30_celdaImagen'><img src='./misumi/images/dialog-help-24_2.png'/></div><div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaImpl,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>";
	}else{
		imagen = "";
	} 

	return imagen;
}

function imageFormatIntertienda(cellValue, opts, rowObject) {

	var imagen = "";
	var textoCajas = "";

	if (rowObject.formato == "FFPP") {
		textoCajas = "FFPP";
	} else {
		if (rowObject.cajaIntertienda == 1) {
			textoCajas = "caja";
		}else{
			textoCajas = "cajas";
		}
	}

	if (cellValue == "S"){
		imagen = "<div class='p30_celda'> <div class='p30_celdaImagen'> <img src='./misumi/images/dialog-accept-24.png'/></div>  <div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaIntertienda,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>" ;
	}else if (cellValue == "X"){
		imagen = "<div class='p30_celda'> <div class='p30_celdaImagen'> <img src='./misumi/images/dialog-help-24_2.png'/></div>  <div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaIntertienda,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>" ;		
	}else{
		imagen = "";
	} 

	return imagen;
}

function imageFormatNSR(cellValue, opts, rowObject) {
	var imagen = "";
	var textoCajas = "";

	if (rowObject.formato == "FFPP") {
		textoCajas = "FFPP";
	} else {
		if (rowObject.cajaNsr == 1) {
			textoCajas = "caja";
		}else{
			textoCajas = "cajas";
		}
	}

	if (cellValue == "S"){
		imagen = "<div class='p30_celda'>  <div class='p30_celdaImagen'> <img src='./misumi/images/dialog-cancel-24.png'/></div>  <div class='p30_infNumeroCajas'><div class='p30_infNumeroCajasAux'><div class='p30_numeroCajas'>" + $.formatNumber(rowObject.cajaNsr,{format:"0.##",locale:"es"}) + "</div><div class='p30_textoCajas'>" + textoCajas + "</div></div></div></div>" ;
	}else{
		imagen = "";
	} 

	return imagen;
}



//**************
/*Clase de constantes para el GRID de ultimos pedidos*/
function GridP30UPed (locale){
	// Atributos
	this.name = "gridP30UPed"; 
	this.nameJQuery = "#gridP30UPed"; 

	this.colNames = null;

	this.locale = locale;
	this.sortIndex = "FECHAGEN";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP30UPed"; 
	this.pagerNameJQuery ="#pagerP30UPed";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.rownumbers = false;

	//Metodos		
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');

		return this.actualPage;
	};

	this.getSelectedRow = function getSelectedRow(){ 
		return $(this.nameJQuery).getGridParam('selrow'); 
	};

	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	};

	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	} ;

	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}else{
			return null;
		}

	} 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	

	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	}

	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		for (i = 0; i < this.colNames.length; i++){
			if (this.rownumbers)
				$(this.nameJQuery).setLabel(colModel[i + 1].name, this.colNames[i], cssClass);
			else
				$(this.nameJQuery).setLabel(colModel[i].name, this.colNames[i], cssClass);
		}
	}

}

function GridP30Motivos (locale){
	// Atributos
	this.name = "gridP30Motivos"; 
	this.nameJQuery = "#gridP30Motivos"; 
	this.i18nJSON = './misumi/resources/p18ReferenciasCentroPopupPedir/p18referenciasCentroPopupPedir_' + locale + '.json';
	this.colNames = null;

	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP30Motivos"; 
	this.pagerNameJQuery = "#pagerP30Motivos";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;

	//Metodos
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');

		return this.actualPage;
	}

	this.getSelectedRow = function getSelectedRow(){ 
		return $(this.nameJQuery).getGridParam('selrow'); 
	}

	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	}

	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	} 

	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}else{
			return "grupo1";
		}
	} 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	
}
function GridP30MotivosMMC (locale){
	// Atributos
	this.name = "gridP30MMC"; 
	this.nameJQuery = "#gridP30MMC"; 
	this.colNames = null;

	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP30MotivosMMC"; 
	this.pagerNameJQuery = "#pagerP30MotivosMMC";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;

	//Metodos
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');

		return this.actualPage;
	}

	this.getSelectedRow = function getSelectedRow(){ 
		return $(this.nameJQuery).getGridParam('selrow'); 
	}

	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	}

	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	} 

	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}else{
			return "grupo1";
		}
	} 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	
}
function loadP30MotivosMock() {
	//para hacer cabeceras
	$(gridP30Motivos.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP30Motivos.colNames,
		colModel :[
		           {
		        	   "name"  : "textoMotivo",
		        	   "index" : "textoMotivo", 
		        	   "width" : 450,
		        	   "formatter": MotivoFormatterP30,
		        	   "sortable" : false
		           }
		           ],  
		           rowNum : 5,
		           height : "100%",
		           autowidth : true,
		           width : "auto",
		           pginput: false,
		           rownumbers : true,
		           pager : gridP30Motivos.pagerNameJQuery,
		           viewrecords : false,
		           //caption : gridP18.title,
		           altclass: "ui-priority-secondary",
		           altRows: true, //false, para que el grid no muestre cebrado
		           hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		           sortable : false,
		           index: gridP30Motivos.sortIndex,
		           sortname: gridP30Motivos.sortIndex,
		           sortorder: gridP30Motivos.sortOrder,
		           emptyrecords : gridP30Motivos.emptyRecords,
		           gridComplete : function() {
		        	   $("#p30_AreaPopupPedirGeneral .loading").css("display", "none");

		           },
		           loadComplete : function(data) {
		        	   gridP30Motivos.actualPage = data.page;
		        	   gridP30Motivos.localData = data;
		        	   gridP30Motivos.sortIndex = null;
		        	   gridP30Motivos.sortOrder = null;
		        	   $("#p30_AreaPopupPedirGeneral .loading").css("display", "none");	


		           },
		           onPaging : function(postdata) {			
		        	   alreadySorted = false;
		        	   gridP30Motivos.sortIndex = null;
		        	   gridP30Motivos.sortOrder = null;
		        	   reloadDataP30Motivos();
		        	   return 'stop';
		           },
		           onSelectRow: function(id){

		           },
		           onSortCol : function (index, columnIndex, sortOrder){
		        	   gridP30Motivos.sortIndex = index;
		        	   gridP30Motivos.sortOrder = sortOrder;
		        	   reloadDataP30Motivos();
		        	   return 'stop';
		           },
		           jsonReader : {
		        	   root : "rows",
		        	   page : "page",
		        	   total : "total",
		        	   records : "records",
		        	   repeatitems : false
		           },
		           loadError : function (xhr, status, error){
		        	   handleError(xhr, status, error, locale);
		           }
	});
}
function loadP30MotivosMMCMock() {
	$(gridP30MMC.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		datatype : 'local',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : gridP30MMC.colNames,
		colModel :[
		           {
		        	   "name"  : "textoMotivo",
		        	   "index" : "textoMotivo", 
		        	   "width" : 450,
		        	   "formatter": MotivoFormatterP30,
		        	   "sortable" : false
		           }
		           ],  
		           rowNum : 5,
		           height : "100%",
		           autowidth : true,
		           width : "auto",
		           pginput: false,
		           rownumbers : true,
		           pager : gridP30MMC.pagerNameJQuery,
		           viewrecords : false,
		           altclass: "ui-priority-secondary",
		           altRows: true, //false, para que el grid no muestre cebrado
		           hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		           sortable : false,
		           index: gridP30MMC.sortIndex,
		           sortname: gridP30MMC.sortIndex,
		           sortorder: gridP30MMC.sortOrder,
		           emptyrecords : gridP30Motivos.emptyRecords,
		           gridComplete : function() {


		           },
		           loadComplete : function(data) {
		        	   gridP30MMC.actualPage = data.page;
		        	   gridP30MMC.localData = data;
		        	   gridP30MMC.sortIndex = null;
		        	   gridP30MMC.sortOrder = null;
		        	   //reloadDataP30MotivosMMC();
		           },
		           onPaging : function(postdata) {			
		        	   alreadySorted = false;
		        	   gridP30MMC.sortIndex = null;
		        	   gridP30MMC.sortOrder = null;
		        	   reloadDataP30MotivosMMC();
		        	   $("#p30_AreaPopupPedirMMC .loading").css("display", "none");	
		        	   return 'stop';
		           },
		           onSelectRow: function(id){

		           },
		           onSortCol : function (index, columnIndex, sortOrder){
		        	   gridP30MMC.sortIndex = index;
		        	   gridP30MMC.sortOrder = sortOrder;
		        	   reloadDataP30MotivosMMC
		        	   return 'stop';
		           },
		           jsonReader : {
		        	   root : "rows",
		        	   page : "page",
		        	   total : "total",
		        	   records : "records",
		        	   repeatitems : false
		           },
		           loadError : function (xhr, status, error){
		        	   handleError(xhr, status, error, locale);
		           }
	});
}
function MotivoFormatterP30(cellvalue, options, rowObject) {
	var cellData = rowObject.textoMotivo; 
	var cellDataWebservice = rowObject.motivoWebservice;
	var cellDataPedir = rowObject.pedir;
	var cellDataMapaHoy = rowObject.mapaHoy;
	var textoFormateado = "";
	var formato=rowObject.tipoMensaje;
	if (formato!=null && formato!=""){
		if (formato!="SF"){
			textoFormateado= "<span class=\"p18MotivoMapaHoySPedirS\">" + cellData.texto1 + "</span>";
		}else{
			textoFormateado = cellData.texto1 + " " + cellData.texto2;
		}
	}else{
		textoFormateado = "<span class=\"p18MotivoTexto1\">" + cellData.texto1 + "</span>";
		textoFormateado += " <span class=\"p18MotivoTexto2\">" + cellData.texto2 + "</span>";
	}

	return textoFormateado;
}
function loadP30ParamStockFinalMinimo(descriptionTot,dateDiario){
	var  auxiliarLine =null;
	var paramStockFinalMin=new ParamStockFinalMin($("#centerId").val(),
			dateDiario.diarioArt.grupo1,
			dateDiario.diarioArt.grupo2,
			dateDiario.diarioArt.grupo3,
			dateDiario.diarioArt.grupo4,
			dateDiario.diarioArt.grupo5);
	var objJson = $.toJSON(paramStockFinalMin.prepareToJsonObjectArticulo());	
	$.ajax({
		type : 'POST',
		url : './welcome/loadStockMinParam.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (data!="1"){
				loadP30PlanogramaVigente(descriptionTot);
			}else{
				//data
				loadP30StockFinalMinimo(descriptionTot);
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);

		}			
	});	
	return auxiliarLine;
}
function loadP30StockFinalMinimo(descriptionTot){
	var  auxiliarLine =null;
	if($("#p30_fld_referenciaEroski").val() == ""){
		var vArticulo = new Articulo(new Centro($("#centerId").val(),null,null), codigoArticulo);
	}else{
		var vArticulo = new Articulo(new Centro($("#centerId").val(),null,null), $("#p30_fld_referenciaEroski").val());
	}

	var objJson = $.toJSON(vArticulo.prepareToJsonObjectArticulo());
	$.ajax({
		type : 'POST',
		url : './welcome/getStockFinalMinimo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (data.stockFinMinS != null)
			{
				$("#p30_lbl_descripcionVal").text(descriptionTot + " " +data.stockFinMinS + p30StockMinimo);	
				if (data.cantidadManualSIA!=null && data.cantidadManualSIA!=data.stockFinMinS){
					var mensajeImpFormateado = mensajeImpSFM.replace("#stockFinalMinimo#", $.formatNumber(data.cantidadManualSIA,{format:"0.000",locale:"es"}));
					$("#p30_lbl_importanteVal").text(mensajeImpFormateado);
					$("#p30_divImportante").show();
				}else{
					$("#p30_divImportante").hide();
				}
			}
			else
			{
				$("#p30_lbl_descripcionVal").text(descriptionTot + " 0 " + p30StockMinimo);
			}	
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);

		}			
	});	
	return auxiliarLine;
}
function loadP30PlanogramaVigente(descriptionTot){
	var  auxiliarLine =null;

	if($("#p30_fld_referenciaEroski").val() == "") {
		var vReferenciasCentro=new ReferenciasCentro(codigoArticulo, $("#centerId").val());
	}else{
		var vReferenciasCentro=new ReferenciasCentro($("#p30_fld_referenciaEroski").val(), $("#centerId").val());
	}

	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './welcome/loadDatosPlanogramaVigente.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if (data.planogramaVigente!=null){
				var capacidad = 0;
				var stockMin = 0;
				if (data.planogramaVigente.capacidadMaxLineal != null)
				{
					capacidad = data.planogramaVigente.capacidadMaxLineal;
				}
				if (data.planogramaVigente.stockMinComerLineal != null)
				{
					stockMin = data.planogramaVigente.stockMinComerLineal;
				}
				if (data.flgFacing=='S'){//Parametrizada para Facing no muestra Capacidad
					auxiliarLine = descriptionTot + " " + stockMin + p30facing;
				} else {
					auxiliarLine = descriptionTot + " " +capacidad +p30capacity +  stockMin + p30facing;
				}

				if (data.planogramaVigente.recalculado != null && data.planogramaVigente.recalculado == 'SI')
				{
					//En este caso se debe mostrar el mensaje de que se ha recalculado la capacidad y el facing.
					var mensajeCapFormateado = mensajeCapSFM.replace("#capacidadMaxLineal#", data.planogramaVigente.capacidadMaxLinealRecal);
					mensajeCapFormateado = mensajeCapFormateado.replace("#stockMinComerLineal#", data.planogramaVigente.stockMinComerLinealRecal);
					$("#p30_lbl_importanteVal").text(mensajeCapFormateado);
					$("#p30_divImportante").show();
				} else if (data.flgFacing == 'S' && data.flgFacingCapacidad != 'S' && data.stockFinalMinimo.facingCentroSIA!=null && data.stockFinalMinimo.facingCentroSIA!=data.stockFinalMinimo.facingCentro){
					var mensajeFacingFormateado = mensajeFacing.replace("#facing#", $.formatNumber(data.stockFinalMinimo.facingCentroSIA,{format:"0.###",locale:"es"}));
					$("#p30_lbl_importanteVal").text(mensajeFacingFormateado);
					$("#p30_divImportante").show();
				} else if (data.flgFacingCapacidad == 'S' && data.stockFinalMinimo.facingCentroSIA!=null && data.stockFinalMinimo.facingCentroSIA!=data.stockFinalMinimo.facingCentro){
					var mensajeFacingCapacidadFormateado = mensajeFacingCapacidad.replace("#facing#", $.formatNumber(data.stockFinalMinimo.facingCentroSIA,{format:"0.###",locale:"es"}));
					mensajeFacingCapacidadFormateado = mensajeFacingCapacidadFormateado.replace("#capacidadLineal#", $.formatNumber(data.stockFinalMinimo.capacidadSIA,{format:"0.###",locale:"es"}));
					$("#p30_lbl_importanteVal").text(mensajeFacingCapacidadFormateado);
					$("#p30_divImportante").show();
				} else {
					$("#p30_divImportante").hide();
				}

			}else{
				//Se obtiene el ancho y alto.
				var anchoAlto = "";
				if(data.vPlanogramaTipoP != null && !data.esFFPP){

					if(data.vPlanogramaTipoP.esCajaExp == "S"){ //Es Caja Expositora
						var anchoAltoAyuda = mensajeTipoPlanoPCExp.replace('{0}',data.vPlanogramaTipoP.facingAlto);
					} else {
						var anchoAltoAyuda = mensajeTipoPlanoP.replace('{0}',data.vPlanogramaTipoP.facingAlto);
					}


					//Si no es FFPP, se inserta una X.
					var anchoAltoAyuda2;
					/*if(data.esFFPP){
						anchoAltoAyuda2 = anchoAltoAyuda.replace('{1}','');
					}else{*/
					anchoAltoAyuda2 = anchoAltoAyuda.replace('{1}','X');
					//}	
					anchoAlto = anchoAltoAyuda2.replace('{2}',data.vPlanogramaTipoP.facingAncho);
				}

				if (data.flgFacing=='S'){//Parametrizada para Facing no muestra Capacidad
					auxiliarLine = descriptionTot + " 0 " +  p30facing;
					//auxiliarLine = descriptionTot + data.stockFinalMinimo.facingCentroSIA +  p30facing;
				} else {    
					auxiliarLine = descriptionTot + " 0 " + p30capacity + " 0 " +  p30facing;
					//auxiliarLine = descriptionTot + data.stockFinalMinimo.capacidad + p30capacity + data.stockFinalMinimo.facingCentroSIA +  p30facing;
				}
				auxiliarLine = auxiliarLine + anchoAlto;
				//auxiliarLine = descriptionTot + " 0 " + p30capacity + " 0 " +  p30facing;
			}

//			auxiliarLine = descriptionTot + " " +data.planogramaVigente.capacidadMaxLineal +p30capacity + data.planogramaVigente.stockMinComerLineal + p30facing;

			//Control de mostrado del multiplicador e Imc
			if (data.esFacingX || (data.flgFacing=='S' && stockMin >0)){
				//if (data.esFacingX || (data.flgFacing=='S' && (stockMin >0 || data.stockFinalMinimo.facingCentroSIA > 0))){
				//Si es FFPP no muestra el multiplicador
				var anchoAlto = "";
				if(data.vPlanogramaTipoP != null && !data.esFFPP){

					if(data.vPlanogramaTipoP.esCajaExp == "S"){ //Es Caja Expositora
						var anchoAltoAyuda = mensajeTipoPlanoPCExp.replace('{0}',data.vPlanogramaTipoP.facingAlto);
					} else {
						var anchoAltoAyuda = mensajeTipoPlanoP.replace('{0}',data.vPlanogramaTipoP.facingAlto);
					}

					//Si no es FFPP, se inserta una X.
					var anchoAltoAyuda2;
					/*if(data.esFFPP){
						anchoAltoAyuda2 = anchoAltoAyuda.replace('{1}','');
					}else{*/
					//}					
					anchoAltoAyuda2 = anchoAltoAyuda.replace('{1}','X');
					anchoAlto = anchoAltoAyuda2.replace('{2}',data.vPlanogramaTipoP.facingAncho);
				}
				if (data.esFFPP){
					auxiliarLine = auxiliarLine + anchoAlto + p30Imc + data.imc;
				}else{
					auxiliarLine = auxiliarLine + anchoAlto + p30Multiplicador + data.multiplicador +  p30Imc + data.imc;
				}
			}

			$("#p30_lbl_descripcionVal").text(auxiliarLine);



		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);

		}			
	});	
	return auxiliarLine;
}
function loadP30GridsFromWebservices(){
	if($("#p30_fld_referenciaEroski").val() == ""){
		var vReferenciasCentro=new ReferenciasCentro(codigoArticulo, $("#centerId").val(), 'InforBasica');
	}else{
		var vReferenciasCentro=new ReferenciasCentro($("#p30_fld_referenciaEroski").val(), $("#centerId").val(), 'InforBasica');
	}

	if ($("#p30_fld_refActiva").val()!="S" && (!esCentroCaprabo || (esCentroCaprabo && esCentroCapraboNuevo))){
		var objJson = $.toJSON(vReferenciasCentro);	
		$.ajax({
			type : 'POST',
			url : './referenciasCentro/loadMotivosFromWS.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			cahe:false,
			success : function(data){

				reloadDataP30Motivos();
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);
			}			
		});		
	}else{
		reloadDataP30Motivos();
	}
}	

function loadp30PendientesRecibir(locale){

	if(	$("#p30_fld_referenciaEroski").val() == ""){
		var pendientesRecibir = new PendientesRecibir ( 
				$("#centerId").val() , 
				codigoArticulo ,
				null ,
				null);
	}else{
		var pendientesRecibir = new PendientesRecibir ( 
				$("#centerId").val() , 
				$("#p30_fld_referenciaEroski").val() ,
				null ,
				null);
	}


	var objJson = $.toJSON(pendientesRecibir.prepareToJsonObject_2());

	$.ajax({
		type : 'POST',			
		url : './referenciasCentro/pendientesRecibir.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			var tipoAprov = data.tipoAprov;
			if (tipoAprov != null && tipoAprov=='D'){
				$("#p30_divPendienteRecibir1").css("display", "none");
				$("#p30_divPendienteRecibir2").css("display", "none");
			}else{
				$("#p30_divPendienteRecibir1").css("display", "block");
				$("#p30_divPendienteRecibir2").css("display", "block");
			}
			$("#p30_lbl_pendienteRecibir1Val").text(data.cantHoy);
			$("#p30_lbl_pendienteRecibir2Val").text(data.cantFutura);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});		
}

function loadP30PuedePedir(){
	var vReferenciasCentro=new ReferenciasCentro(codigoArticulo, $("#centerId").val());

	var objJson = $.toJSON(vReferenciasCentro);	
	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadFechaProximasEntregas.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			if(data.listaFechaProximasEntregas != null && data.listaFechaProximasEntregas.length > 0){
				//Enseñamos la tabla
				$("#p30_tabla").show();

				//Se borran los posibles datos de búsquedas anteriores.
				$("#p30_puedePedirTablaFilas").children().not("#p30_puedePedirRefData").remove();
				
				//Guarda la fechaProximaEntrega
				var fechaProximaEntrega;
				for(var i=0; i<data.listaFechaProximasEntregas.length; i++){
					//Obtener fechas
					fechaProximaEntrega = data.listaFechaProximasEntregas[i];

					//Se obtiene la esctructura de la fila y se cambian los ids adecuados.
					estructuraFechas = getEstructuraFechas(i);

					//Se inserta la fila
					$("#p30_puedePedirTablaFilas").append(estructuraFechas);

					//Se llama a la función que pinta la información de la fila
					rellenarInformacionFila(i,fechaProximaEntrega);
				}
			}else{
				//Ocultamos la tabla
				$("#p30_tabla").hide();
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

//Se obtiene la esctructura de la fila de fechas
function getEstructuraFechas(indice){
	var estructuraFechas = $("#p30_puedePedirRefData").prop('outerHTML');

	//Para la estructura visible
	estructuraFechas = estructuraFechas.replace(new RegExp("\\bp30_puedePedirRefData\\b"),"p30_puedePedirRefData"+indice);
	estructuraFechas = estructuraFechas.replace(new RegExp("\\bp30_puedePedirRefDiaTransmision\\b"),"p30_puedePedirRefDiaTransmision"+indice);
	estructuraFechas = estructuraFechas.replace(new RegExp("\\bp30_puedePedirRefFechaTransmision\\b"),"p30_puedePedirRefFechaTransmision"+indice);
	estructuraFechas = estructuraFechas.replace(new RegExp("\\bp30_puedePedirRefDiaTransporte\\b"),"p30_puedePedirRefDiaTransporte"+indice);	
	estructuraFechas = estructuraFechas.replace(new RegExp("\\bp30_puedePedirRefFechaTransporte\\b"),"p30_puedePedirRefFechaTransporte"+indice);
	estructuraFechas = estructuraFechas.replace(new RegExp("\\bp30_puedePedirRefDiaVenta\\b"),"p30_puedePedirRefDiaVenta"+indice);
	estructuraFechas = estructuraFechas.replace(new RegExp("\\bp30_puedePedirRefFechaVenta\\b"),"p30_puedePedirRefFechaVenta"+indice);

	return estructuraFechas;
}

function rellenarInformacionFila(indice,fechaProximaEntrega){
	//Mostramos la estructura
	$("#p30_puedePedirRefData"+indice).show();

	var fechaTransmision = "";
	var diaTransmision = "";
	if(fechaProximaEntrega.fechaTransmision != null){
		fechaTransmision = $.datepicker.formatDate("dd/mm/yy",new Date(fechaProximaEntrega.fechaTransmision));
		diaTransmision = $.datepicker.formatDate("D", new Date(fechaProximaEntrega.fechaTransmision),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		}).toUpperCase();
	}

	var fechaTransporte = "";
	var diaTransporte = "";
	if(fechaProximaEntrega.fechaTransporte != null){
		fechaTransporte = $.datepicker.formatDate("dd/mm/yy",new Date(fechaProximaEntrega.fechaTransporte));
		diaTransporte = $.datepicker.formatDate("D", new Date(fechaProximaEntrega.fechaTransporte),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		}).toUpperCase();
	}

	var fechaVenta = "";
	var diaVenta = "";
	if(fechaProximaEntrega.fechaVenta != null){
		var fechaVenta =  $.datepicker.formatDate("dd/mm/yy",new Date(fechaProximaEntrega.fechaVenta));
		var diaVenta = $.datepicker.formatDate("D", new Date(fechaProximaEntrega.fechaVenta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		}).toUpperCase();
	}

//	//Ponemos los valores de fechas con dd/mm/yyyy
//	$("#p30_puedePedirRefFechaTransmision"+indice).text(fechaTransmision);
//	$("#p30_puedePedirRefFechaTransporte"+indice).text(fechaTransporte);
//	$("#p30_puedePedirRefFechaVenta"+indice).text(fechaVenta);
//
//	//Ponemos los valores del dia
//	$("#p30_puedePedirRefDiaTransmision"+indice).text(diaTransmision);
//	$("#p30_puedePedirRefDiaTransporte"+indice).text(diaTransporte);
//	$("#p30_puedePedirRefDiaVenta"+indice).text(diaVenta);
	
	//Añadimos color a la fila.
	if(indice % 2 == 1){
		$("#p30_puedePedirRefData"+ indice).addClass("p30_puedePedirFila");
	}
	//Ponemos los valores del dia
	$("#p30_puedePedirRefFechaTransmision"+indice).text(diaTransmision + " " + fechaTransmision);
	$("#p30_puedePedirRefFechaTransporte"+indice).text(diaTransporte + " " + fechaTransporte);
	$("#p30_puedePedirRefFechaVenta"+indice).text(diaVenta + " " + fechaVenta);
}

function reloadDataP30Motivos() {

	var recordMotivo = new Motivo ($("#p30_fld_mapaHoy").val(), $("#p30_fld_refActiva").val(), $("#p30_fld_referenciaEroski").val(), $("#centerId").val(), 'InforBasica');


	var objJson = $.toJSON(recordMotivo.prepareToJsonObject());

	$("#p30_AreaPopupPedirGeneral .loading").css("display", "block");
	if ( $("#p30_fld_refActiva").val()!=true){
		//si 
		$.ajax({
			type : 'POST',
			url : './referenciasCentro/loadMotivosNoActiva.do?page='+gridP30Motivos.getActualPage()+'&max='+gridP30Motivos.getRowNumPerPage()+'&index='+gridP30Motivos.getSortIndex()+'&sortorder='+gridP30Motivos.getSortOrder(),
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {		
				$(gridP30Motivos.nameJQuery)[0].addJSONData(data);
				gridP30Motivos.actualPage = data.page;
				gridP30Motivos.localData = data;
				$("#p30_AreaPopupPedirGeneral .loading").css("display", "none");	
				if (data.records>0){
					$("#p30_MotNoActivaDivTitle").attr("style", "display:block");
				}else{
					$("#p30_MotNoActivaDivTitle").attr("style", "display:none");
				}
				if (data.records == 0){
					createAlert(replaceSpecialCharacters(gridP30Motivos.emptyRecords), "ERROR");
				} 
				if (!esCentroCaprabo || (esCentroCaprabo && esCentroCapraboNuevo)){

					reloadDataP30MotivosMMC();
				}
			},
			error : function (xhr, status, error){
				$("#p30_AreaPopupPedirGeneral .loading").css("display", "none");	
				handleError(xhr, status, error, locale);				
			}			
		});		
	}

}
function reloadDataP30MotivosMMC() {
	var recordMotivo = new Motivo ($("#p30_fld_mapaHoy").val(), $("#p30_fld_refActiva").val(), $("#p30_fld_referenciaEroski").val(), $("#centerId").val(), 'InforBasica');


	var objJson = $.toJSON(recordMotivo.prepareToJsonObject());

	$("#p30_AreaPopupPedirMMC .loading").css("display", "block");

	$.ajax({
		type : 'POST',
		url : './referenciasCentro/loadMotivosNoActivaMMC.do?page='+gridP30MMC.getActualPage()+'&max='+gridP30MMC.getRowNumPerPage()+'&index='+gridP30MMC.getSortIndex()+'&sortorder='+gridP30MMC.getSortOrder(),
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			if (data.records>0){
				$("#p30_mmcDivTitle").attr("style", "display:block");
				$("#p30_AreaPopupPedirMMC").attr("style", "display:inline-block");
			}else{
				$("#p30_mmcDivTitle").attr("style", "display:none");
				$("#p30_AreaPopupPedirMMC").attr("style", "display:none");
			}
			$(gridP30MMC.nameJQuery)[0].addJSONData(data);
			gridP30MMC.actualPage = data.page;
			gridP30MMC.localData = data;
			$("#p30_AreaPopupPedirMMC .loading").css("display", "none");	

		},
		error : function (xhr, status, error){
			$("#p30_AreaPopupPedirMMC .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});	

}
function loadPedidoBasicInfo(){

	if($("#p30_fld_referenciaEroski").val() == ""){
		var vArticulo = new Articulo(new Centro($("#centerId").val(),null,null), codigoArticulo);
	}else{
		var vArticulo = new Articulo(new Centro($("#centerId").val(),null,null), $("#p30_fld_referenciaEroski").val());
	}

	var objJson = $.toJSON(vArticulo.prepareToJsonObjectArticulo());	
	$.ajax({
		type : 'POST',
		url : './welcome/loadPedidoInfoBasic.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		cache:false,
		success : function(data){
			if (data.ultimosPedidos.records == null || data.ultimosPedidos.records == 0){
				$("#p30_FSET_ultimosPedidosNoExistenParrafo").text(data.mensajeSinPedidos);
				$("#p30_ultimosPedidosNoExisten").attr("style", "display:inline-block");
				$("#p30_ultimosPedidos").attr("style", "display:none");
				$("#p30_AreaUltimosPedidosBotonera").attr("style", "display:none");
			} else{
				$("#p30_ultimosPedidos").attr("style", "display:inline-block");
				$("#p30_ultimosPedidosNoExisten").attr("style", "display:none");
				$("#p30_AreaUltimosPedidosBotonera").attr("style", "display:inline-block");
				$(grid30Pedido.nameJQuery)[0].addJSONData(data.ultimosPedidos);
				$(grid30Pedido.nameJQuery).jqGrid('setGridWidth', $("#p30_AreaUltimosPedidos").width(), true);
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}	

function loadp30StockWS() {

	if($("#p30_fld_referenciaEroski").val() == ""){
		var vArticulo = new Articulo(new Centro($("#centerId").val(),null,null), codigoArticulo);
	}else{
		var vArticulo = new Articulo(new Centro($("#centerId").val(),null,null), $("#p30_fld_referenciaEroski").val());
	}

	var objJson = $.toJSON(vArticulo.prepareToJsonObjectArticulo());	
	$.ajax({
		type : 'POST',
		url : './welcome/loadStockCentroArticulo.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		cache:false,
		success : function(data){
			if (data!=null && data!="" && data!="Error"){
				$("#p30_lbl_stockActualVal").text($.formatNumber(data,{format:"0.00",locale:"es"}));	
				$("#p30_lbl_stockActualVal").removeClass("p30_stockActualMensajeErrorWS").addClass("valorCampo");
			}else{
				//$("#p30_lbl_stockActualVal").text(mensajeErrorWSStock);
				$("#p30_lbl_stockActualVal").text("0");
				//$("#p30_lbl_stockActualVal").removeClass("valorCampo").addClass("p30_stockActualMensajeErrorWS");
			}

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
}

function redirigirLogin() {
	window.location='./login.do';
}

function loadP30Foto(tieneFoto){
	if (tieneFoto == "S"){
		var url = "./welcome/getImage.do?codArticulo="+$("#p30_fld_referenciaEroski").val();
		$('#p30_img_referencia').attr('src',url);
		$('#p30_img_referencia').attr("onerror", "redirigirLogin()");
		$("#p30_AreaFotos").show();
	} else {
		$("#p30_AreaFotos").hide();
	}

}

function p30FormateoFechaEntrega(fecha) {

	var fechaFormateada = '';
	if (fecha != null)
	{
		var anyoFecha = parseInt(fecha.substring(6),10);
		var mesFecha = parseInt(fecha.substring(3,5),10);
		var diaFecha = parseInt(fecha.substring(0,2),10);



		fechaFormateada = $.datepicker.formatDate("D dd-M", new Date(anyoFecha, mesFecha - 1, diaFecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});
	}

	return fechaFormateada;

}