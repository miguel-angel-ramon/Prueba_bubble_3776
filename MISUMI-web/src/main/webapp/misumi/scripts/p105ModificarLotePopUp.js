/**
 * Variables globales
 */

var extensiones = ["jpg","jpeg","gif","png"];

//Listados
var listadoBorrado = new Array();
var listadoModificado = new Array();
var listadoNuevo = new Array();

//Guarda la fila actual que se esta tratando.
var filaActual = null;

var rellenarCampos = null;
var codigoArticuloLote = null;
var descripcionArticuloLote = null;
var fechaInicioLote = null;
var fechaFinLote = null;
var imagen1Lote = null;
var imagen2ContenidoLote = null;
var contenidoLote = null;
var flagEroskiLote = null;
var flagCapraboLote = null;
var ordenLote = null;

var sinModificacion = null;

var fechaFinAnteriorFechaIni = null;
var fechaIniPosteriorFechaFin = null;

//Variables de texto
var comboSi = null;
var comboNo = null;
var modificar = null;
var nuevo = null;
var extensionIncorrecta = null;


function CestasNavidadArticulo(idCestasNavidadArticulo,
		codArtLote, tituloArticuloLote, descrArticuloLote){

	this.idCestasNavidadArticulo = idCestasNavidadArticulo;
	this.codArtLote = codArtLote;
	this.tituloArticuloLote = tituloArticuloLote;
	this.descrArticuloLote = descrArticuloLote;

	this.prepareToJsonObject = function(){
		var jsonObject = null;
		jsonObject = {
				'idCestasNavidadArt': this.idCestasNavidadArticulo,
				'codArtLote': this.codArtLote,
				'tituloArticuloLote': this.tituloArticuloLote,
				'descrArticuloLote': this.descrArticuloLote
		};
		return jsonObject;
	};

};

function initializeScreenP105(){
	loadP105(locale);

	p105_inicializarPopUp();
	p105_inicializarFechas();
	p105_inicializarComboboxes();
	p105_inicializarInput();

	events_p105_rad_contenidoLote();
	events_p105_nuevoContenidoLote();
	events_p105_nuevoModificar();
	events_p105_imagen1();
	events_p105_imagen2();
}

function loadP105(locale){

	this.i18nJSON = './misumi/resources/p105ModificacionLotePopUp/p105ModificacionLotePopUp_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		comboSi = data.comboSi;
		comboNo = data.comboNo;

		btnModificar = data.btnModificar;
		btnNuevo = data.btnNuevo;

		extensionIncorrecta = data.extensionIncorrecta;

		rellenarCampos = data.rellenarCampos;
		codigoArticuloLote = data.codigoArticuloLote;
		descripcionArticuloLote = data.descripcionArticuloLote;
		fechaInicioLote = data.fechaInicioLote;
		fechaFinLote = data.fechaFinLote;
		imagen1Lote = data.imagen1Lote;
		imagen2ContenidoLote = data.imagen2ContenidoLote;
		contenidoLote = data.contenidoLote;
		flagEroskiLote = data.flagEroskiLote;
		flagCapraboLote = data.flagCapraboLote;
		ordenLote = data.ordenLote;

		sinModificacion = data.sinModificacion;
		fechaFinAnteriorFechaIni = data.fechaFinAnteriorFechaIni;
		fechaIniPosteriorFechaFin = data.fechaIniPosteriorFechaFin;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function p105_inicializarPopUp(){
	//Definión del popup que contendrá los formularios
	$("#p105_popup").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		title: "",
		resizable: false,
		dialogClass: "",
		open: function() {
			
		},
		close:function(){
			//Limpiamos el popup
			p105_limpiarPopUp();						
		},
		stack:false
	});
}

function p105_inicializarFechas(){
	$.datepicker.setDefaults($.datepicker.regional["esMisumi"]);
	$("#p105_fld_fechaIni").datepicker({
		onSelect: function(dateText, inst) {
			var fecFin = $("#p105_fld_fechaFin").datepicker("getDate");
			var fecIni = $("#p105_fld_fechaIni").datepicker("getDate");
			if(fecFin != null && fecIni > fecFin){
				createAlert(fechaIniPosteriorFechaFin, "ERROR");
				var fechaIniAnterior = ($('#p105_fld_fechaIni').data("oldDate") != undefined && $('#p105_fld_fechaIni').data("oldDate") != null) ? $('#p105_fld_fechaIni').data("oldDate") : fecFin;
				$('#p105_fld_fechaIni').datepicker('setDate', fechaIniAnterior);
			}else{
				$('#p105_fld_fechaIni').data("oldDate",fecIni);
			}
		}
	});

	$("#p105_fld_fechaFin" ).datepicker({
		onSelect: function(dateText, inst) {
			var fecFin = $("#p105_fld_fechaFin").datepicker("getDate");
			var fecIni = $("#p105_fld_fechaIni").datepicker("getDate");
			if(fecIni != null && fecFin < fecIni){
				createAlert(fechaFinAnteriorFechaIni, "ERROR");				
				var fechaFinAnterior = ($('#p105_fld_fechaFin').data("oldDate") != undefined && $('#p105_fld_fechaFin').data("oldDate") != null) ? $('#p105_fld_fechaFin').data("oldDate") : fecIni;
				$('#p105_fld_fechaFin').datepicker('setDate', fechaFinAnterior)
			}else{
				$('#p105_fld_fechaFin').data("oldDate",fecFin);
			}
			
		}
	});
}

function p105_inicializarComboboxes(){
	$("#p105_fld_flgEroski").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null"  ) {
				//pedidoAdicional.excluir = ui.item.value;
			}

		} ,		       
		changed: function(event, ui) { 
			if (ui.item==null ||ui.item.value!="" || ui.item.value!="null"){
				//pedidoAdicional.excluir = ui.item.value;
			}	 
		}
	});
	$("#p105_fld_flgCaprabo").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null"  ) {
				//pedidoAdicional.excluir = ui.item.value;
			}

		} ,		       
		changed: function(event, ui) { 
			if (ui.item==null ||ui.item.value!="" || ui.item.value!="null"){
				//pedidoAdicional.excluir = ui.item.value;
			}	 
		}
	});
}

function p105_inicializarInput(){
	//Indicamos que el campo es numérico.
	$("#p105_fld_codArtLote").filter_input({regex:'[0-9]'});	
	$("#p105_fld_orden").filter_input({regex:'[0-9]'});	
}
function p105OpenPopUp(fila){
	if(fila != null){
		filaActual = fila;

		$.ajax({
			type : 'GET',			
			url : './parametrizacionCestas/popup/loadArticulosCesta.do?codArtLote='+ fila.codArtLote,
			success : function(data) {	
				//Si existen datos, rellenamos la parte del popup de articulos de BD.
				if(data != null){
					if(data.length > 0){
						//MISUMI-284: Si hay texto, seleccionar radio button de Texto
						$("#p105_radioImg").prop("checked", false);
						$("#p105_radioTxt").prop("checked", true);
						// Se invoca al metodo "change" para que se muestre el panel con los articulos
						$("#p105_radioTxt").change();

						for(var i = 0; i< data.length; i++){
							addContenidoLote(data[i]);
						}
					}
				}

				//Input en readonly
				$('#p105_fld_codArtLote').attr('readonly', true);

				$("#p105_fld_codArtLote").val(fila.codArtLote);
				$("#p105_fld_descrCodArtLote").val(fila.descrLoteMisumi);

				var fechaIniSplit = fila.fecIni.split("-");
				var anioIni = fechaIniSplit[2];
				var mesIni = fechaIniSplit[1] - 1;
				var diaIni = fechaIniSplit[0];

				var fechaIni = new Date(anioIni,mesIni,diaIni);
				$('#p105_fld_fechaIni').datepicker({ dateFormat: 'yy-mm-dd' });
				$('#p105_fld_fechaIni').datepicker('setDate', fechaIni);
				$('#p105_fld_fechaIni').data('oldDate', fechaIni);
				
				var fechaFinSplit = fila.fecFin.split("-");
				var anioFin = fechaFinSplit[2];
				var mesFin = fechaFinSplit[1] - 1;
				var diaFin = fechaFinSplit[0];

				var fechaFin = new Date(anioFin,mesFin,diaFin);
				$('#p105_fld_fechaFin').datepicker({ dateFormat: 'yy-mm-dd' });
				$('#p105_fld_fechaFin').datepicker('setDate', fechaFin);
				$('#p105_fld_fechaFin').data('oldDate', fechaFin); 
				
				//Metemos la imagen 1 como tooltip
				var imagen1 = $("#p105_imagen1" + fila.codArtLote + "-" + fila.codArtLoteCpb).prop("title");
				$("#p105_fld_img1").prop("title",imagen1);

				$("#p105_fld_img1").tooltip({
					position: {
						my: "left top",
						at: "right+5 bottom+5"
					}
				});

				//Metemos la imagen 2 como tooltip
				var imagen1 = $("#p105_imagen2" + fila.codArtLote + "-" + fila.codArtLoteCpb).prop("title");
				$("#p105_fld_img2").prop("title",imagen1);

				$("#p105_fld_img2").tooltip({
					position: {
						my: "left top",
						at: "right+5 bottom+5"
					}
				});
				//Indicamos la opción seleccionada y su texto. Es necesario txtEjercicio porque si no, muestra solo el año.
				if(fila.flgEroski == "S"){
					$("#p105_fld_flgEroski").combobox('autocomplete',comboSi);
				}else{
					$("#p105_fld_flgEroski").combobox('autocomplete',comboNo);
				}

				if(fila.flgCaprabo == "S"){
					$("#p105_fld_flgCaprabo").combobox('autocomplete',comboSi);
				}else{
					$("#p105_fld_flgCaprabo").combobox('autocomplete',comboNo);
				}

				//Texto botón
				$("#p105_btn_new").val(btnModificar);

				//Ponemos el valor al orden.
				$("#p105_fld_orden").val(fila.orden);										
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);				
			}			
		});		
	}else{
		
	}
	$("#p105_popup").dialog("open");
}

function events_p105_rad_contenidoLote(){
	$("input[name='p105_rad_contenidoLote']").change(function () {
		if ($("input[name='p105_rad_contenidoLote']:checked").val() == "1"){
			//Estructura comercial
			$("div#p105_bloqueImg2").attr("style", "display:block");
			$("div#p105_bloqueContenidoLote").attr("style", "display:none");
		} else { 
			//Referencias
			$("div#p105_bloqueImg2").attr("style", "display:none");
			$("div#p105_bloqueContenidoLote").attr("style", "display:inline");
		}   
	});
}

function events_p105_nuevoContenidoLote(){
	$("#p105_btn_mas").click(function () {
		addContenidoLote();
	});
}

function events_p105_imagen1(){
	$("#p105_fld_img1").change(function () {
		var img1 = $("#p105_fld_img1");
		var img = img1.val().split(".");

		var tipo = img[1];
		var path = img[0];

		if(extensiones.indexOf(tipo.toLowerCase()) < 0){
			img1.val("");
			createAlert(extensionIncorrecta,"ERROR");
		}else{
			var file = document.getElementById("p105_fld_img1").files[0];
			imagen1Base64 = null;
			if(file != null){
				imagen1Base64 = getBase64(file, "p105_fld_img1");
			}
		}
	});
}

function events_p105_imagen2(){
	$("#p105_fld_img2").change(function () {
		var img2 = $("#p105_fld_img2");
		var img = img2.val().split(".");

		var tipo = img[1];
		var path = img[0];

		if(extensiones.indexOf(tipo.toLowerCase()) < 0){
			img2.val("");
			createAlert(extensionIncorrecta,"ERROR");
		}else{
			var file = document.getElementById("p105_fld_img2").files[0];
			imagen2Base64 = null;
			if(file != null){
				imagen2Base64 = getBase64(file, "p105_fld_img2");
			}
		}
	});
}

function getBase64(file, id) {
	var reader = new FileReader();
	var result = reader.readAsDataURL(file);
	reader.onload = function(e) {
		var base64 = reader.result.split(",");
		var imgBase64 = base64[1];
		$("#"+ id).data("img64",imgBase64);
	};
}

function addContenidoLote(articulo){
	$("#p105_sinArticuloLote").hide();
	$("#p105_nuevosArticulosLote").show();
	$("#p105_nuevosArticulosLote").append($("#p105_estructuraNuevoArticuloLote").html());

	//Obtenemos el último id que se haya insertado y le sumamos 1.
	var idNuevoElemento = parseInt($('#p105_nuevosArticulosLote').attr("data-count")) + 1;
	$('#p105_nuevosArticulosLote').attr("data-count",idNuevoElemento);

	//Al último elemento añadido, le ponemos ID.
	$('#p105_nuevosArticulosLote .p105_nuevoArticuloLote:last').attr("id","p105_" + idNuevoElemento + "_articuloLote");

	//A la cruz del último elemento insertado le guardamos el número del ID del padre de la estructura.
	$("#p105_" + idNuevoElemento + "_articuloLote").attr("data-idestructura", idNuevoElemento);

	//Si se obtiene de BD
	if(articulo != null){
		//Guardamos el id de bd del artículo del lote
		$("#p105_" + idNuevoElemento + "_articuloLote").attr("data-idbd", articulo.idCestasNavidadArticulo);
		$("#p105_" + idNuevoElemento + "_articuloLote .p105_inputTitle").val(articulo.tituloArticuloLote);
		$("#p105_" + idNuevoElemento + "_articuloLote .p105_inputDescr").val(articulo.descrArticuloLote);

		//Guardamos los valores originales de titulo y descripcion
		$("#p105_" + idNuevoElemento + "_articuloLote .p105_inputTitle").attr("data-ori", articulo.tituloArticuloLote);
		$("#p105_" + idNuevoElemento + "_articuloLote .p105_inputDescr").attr("data-ori", articulo.descrArticuloLote);
	}else{
		//Guardamos un 0 como idBd para indicar que es nuevo.
		$("#p105_" + idNuevoElemento + "_articuloLote").attr("data-idbd", "0");
	}
}
function events_p105_nuevoModificar(){
	$("#p105_btn_new").click(function(){
		//Si la fila actual tiene valor, es modificación.
		if(filaActual != null){
			if(!camposVaciosModificar()){
				//Codigo articulo lote
				var codArtLote = $("#p105_fld_codArtLote").val();

				//Comparamos los valores actuales con los originales.
				var descrCodArtLoteActual = $("#p105_fld_descrCodArtLote").val();
				var descrCodArtLoteOri = filaActual.descrLoteMisumi;
				var descrCodArtModi = descrCodArtLoteActual != descrCodArtLoteOri ? descrCodArtLoteActual : null;

				var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaIni').datepicker('getDate'));
				var fechaIniOri = filaActual.fecIni;
				
				var fechaIniActualDatePartes = fechaIniActual.split("-");
				var fechaIniAnio = fechaIniActualDatePartes[2];
				var fechaIniMes = fechaIniActualDatePartes[1] - 1;
				var fechaIniDia = fechaIniActualDatePartes[0];
				var fechaIniDate = new Date(fechaIniAnio,fechaIniMes,fechaIniDia);
				
				var fechaIniModi = fechaIniActual != fechaIniOri ? fechaIniDate : null;

				var fechaFinActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaFin').datepicker('getDate'));
				var fechaFinOri = filaActual.fecFin;
				
				var fechaFinActualDatePartes = fechaFinActual.split("-");
				var fechaFinAnio = fechaFinActualDatePartes[2];
				var fechaFinMes = fechaFinActualDatePartes[1] - 1;
				var fechaFinDia = fechaFinActualDatePartes[0];
				var fechaFinDate = new Date(fechaFinAnio,fechaFinMes,fechaFinDia);
				
				var fechaFinModi = fechaFinActual != fechaFinOri ? fechaFinDate : null;

				var file = document.getElementById("p105_fld_img1").files[0];
				var img1Base64 = null;
				if(file != null){
					img1Base64 = $("#p105_fld_img1").data("img64");
				}

				var file2 = document.getElementById("p105_fld_img2").files[0];
				var img2Base64 = null;
				if(file2 != null){
					img2Base64 = $("#p105_fld_img2").data("img64");
				}

				//Obtenemos los valores nuevos. Los que tienen data-idbd = 0
				var nuevosElementos = $('#p105_nuevosArticulosLote .p105_nuevoArticuloLote[data-idbd="0"]');
				
				//Reseteamos el listado nuevo
				listadoNuevo = new Array();
				for(var i = 0; i< nuevosElementos.length; i++){
					var idElemento = nuevosElementos[i].id;
					var inputTitle = $("#" + idElemento + " .p105_inputTitle");
					var inputDescr = $("#" + idElemento + " .p105_inputDescr");


					var codArtLote = $("#p105_fld_codArtLote").val();
					var titleStr = inputTitle.val();
					var descrStr = inputDescr.val();

					if(titleStr != "" || descrStr != ""){
						var cestaNavidadArticuloNuevo = new CestasNavidadArticulo(null,codArtLote,titleStr,descrStr);
						listadoNuevo.push(cestaNavidadArticuloNuevo);
					}
				}

				//Obtenemos los valores que tienen idCestasNavidadArt
				var elementosModificados = $('#p105_nuevosArticulosLote .p105_nuevoArticuloLote[data-idbd!="0"]');
				
				//Reseteamos el listado modificado
				listadoModificado = new Array();
				for(var j = 0; j<elementosModificados.length; j++){
					var idElemento = elementosModificados[j].id;
					var inputTitle = $("#" + idElemento + " .p105_inputTitle");
					var inputDescr = $("#" + idElemento + " .p105_inputDescr");

					//Obtenemos el id del articulo a modificar
					var idCestasNavidadArt = $("#" + idElemento).attr("data-idbd");

					//Obtenemos los valores de los inputs actuales
					var codArtLote = $("#p105_fld_codArtLote").val();
					var titleStrActual = inputTitle.val();
					var descrStrActual = inputDescr.val();

					//Obtenemos los valores de los inputs originales
					var titleStrOri = inputTitle.attr("data-ori");
					var descrStrOri = inputDescr.attr("data-ori");

					//Si los valores originales y los actuales son distintos, lo metemos en la lista de modificados
					if(titleStrOri != titleStrActual || descrStrOri != descrStrActual){
						var cestaNavidadArticuloModificado = new CestasNavidadArticulo(idCestasNavidadArt,codArtLote,titleStrActual,descrStrActual);
						listadoModificado.push(cestaNavidadArticuloModificado);
					}
				}

				//Combos
				var flgEroskiActual = $("#p105_fld_flgEroski").val();
				var flgEroskiOri = filaActual.flgEroski;
				var flgEroskiModi = flgEroskiActual != flgEroskiOri ? flgEroskiActual : null;

				var flgCapraboActual = $("#p105_fld_flgCaprabo").val();
				var flgCapraboOri = filaActual.flgCaprabo;
				var flgCapraboModi = flgCapraboActual != flgCapraboOri ? flgCapraboActual : null;

				//Orden
				var ordenActual = $("#p105_fld_orden").val();
				var ordenOri = filaActual.orden;
				var ordenModi = ordenActual != ordenOri ? ordenActual : null;

				if(
						descrCodArtModi != null ||
						fechaIniModi != null ||
						fechaFinModi != null ||
						img1Base64 != null ||
						img2Base64 != null ||
						listadoNuevo.length != 0 ||
						listadoModificado.length != 0 ||
						listadoBorrado.length != 0 ||
						flgEroskiModi != null || 
						flgCapraboModi != null || 
						ordenModi != null 
				){
					var cestasNavidad = new CestasNavidad(
							codArtLote,descrCodArtModi, 
							img1Base64,img2Base64, null,
							null, null,
							fechaIniModi,fechaFinModi,
							flgEroskiModi,flgCapraboModi, ordenModi,
							8, null,
							listadoBorrado, listadoModificado, listadoNuevo
					);
					
					var url='./parametrizacionCestas/updateRow.do';
					var objJson = $.toJSON(cestasNavidad);	
					$.ajax({
						type : 'POST',
						url : url,
						data: objJson,
						contentType : "application/json; charset=utf-8",
						success : function(data) {	
							//Si hay error, repintamos el grid.
							var mensajePartes = data.split("-");
							var okKo = mensajePartes[0];
							var mensaje = mensajePartes[1];
							
							if(okKo == "OK"){	
								$("#p104_AreaResultados").hide();
								
								//Refrescamos el grid
								loadData_gridP104ParametrizacionCestas();
								
								//Mostramos alerta de fila insertada.
								createAlert(mensaje,"INFO");
								
								//Cerramos el popup
								$("#p105_popup").dialog("close");	
							}else{								
								createAlert(mensaje,"ERROR");
								
								//Cerramos el popup
								$("#p105_popup").dialog("close");	
							}			
						},
						error : function (xhr, status, error){
							handleError(xhr, status, error, locale);
						}
					});	
				}else{
					createAlert(sinModificacion,"ERROR");
				}
			}
		}else{
			if(!camposVaciosNuevo()){
				//Codigo articulo lote
				var codArtLoteActual = $("#p105_fld_codArtLote").val();

				//Descripcion
				var descrCodArtLoteActual = $("#p105_fld_descrCodArtLote").val();

				//Fechas
				var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaIni').datepicker('getDate'));
				var fechaIniActualDatePartes = fechaIniActual.split("-");
				var fechaIniAnio = fechaIniActualDatePartes[2];
				var fechaIniMes = fechaIniActualDatePartes[1] - 1;
				var fechaIniDia = fechaIniActualDatePartes[0];
				var fechaIniDateActual = new Date(fechaIniAnio,fechaIniMes,fechaIniDia);
				
				var fechaFinActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaFin').datepicker('getDate'));
				var fechaFinActualDatePartes = fechaFinActual.split("-");
				var fechaFinAnio = fechaFinActualDatePartes[2];
				var fechaFinMes = fechaFinActualDatePartes[1] -1 ;
				var fechaFinDia = fechaFinActualDatePartes[0];
				var fechaFinDateActual = new Date(fechaFinAnio,fechaFinMes,fechaFinDia);
				
				//Combos
				var flgEroskiActual = $("#p105_fld_flgEroski").val();
				var flgCapraboActual = $("#p105_fld_flgCaprabo").val();

				//Imagenes	
				var file = document.getElementById("p105_fld_img1").files[0];
				var img1Base64 = null;
				if(file != null){
					img1Base64 = $("#p105_fld_img1").data("img64");
				}

				var file2 = document.getElementById("p105_fld_img2").files[0];
				var img2Base64 = null;
				if(file2 != null){
					img2Base64 = $("#p105_fld_img2").data("img64");
				}

				//Obtenemos los valores nuevos. Los que tienen data-idbd = 0
				var nuevosElementos = $('#p105_nuevosArticulosLote .p105_nuevoArticuloLote[data-idbd="0"]');
				
				//Reseteamos el listado nuevo
				listadoNuevo = new Array();
				for(var i = 0; i< nuevosElementos.length; i++){
					var idElemento = nuevosElementos[i].id;
					var inputTitle = $("#" + idElemento + " .p105_inputTitle");
					var inputDescr = $("#" + idElemento + " .p105_inputDescr");


					var codArtLote = $("#p105_fld_codArtLote").val();
					var titleStr = inputTitle.val();
					var descrStr = inputDescr.val();

					if(titleStr != "" || descrStr != ""){
						var cestaNavidadArticuloNuevo = new CestasNavidadArticulo(null,codArtLote,titleStr,descrStr);
						listadoNuevo.push(cestaNavidadArticuloNuevo);
					}
				}

				var ordenActual = $("#p105_fld_orden").val();
				
				var cestasNavidad = new CestasNavidad(
						codArtLoteActual,descrCodArtLoteActual, 
						img1Base64,img2Base64, null,
						null, null,
						fechaIniDateActual,fechaFinDateActual,
						flgEroskiActual,flgCapraboActual, ordenActual,
						8, null,
						listadoBorrado, listadoModificado, listadoNuevo
				);
				
				var url='./parametrizacionCestas/insertRow.do';
				var objJson = $.toJSON(cestasNavidad);	
				$.ajax({
					type : 'POST',
					url : url,
					data: objJson,
					contentType : "application/json; charset=utf-8",
					success : function(data) {	
						//Si hay error, repintamos el grid.
						var mensajePartes = data.split("-");
						var okKo = mensajePartes[0];
						var mensaje = mensajePartes[1];
						
						if(okKo == "OK"){		
							$("#p104_AreaResultados").hide();
							
							//Refrescamos el grid
							loadData_gridP104ParametrizacionCestas();
							
							//Mostramos alerta de fila insertada.
							createAlert(mensaje,"INFO");
							
							//Cerramos el popup
							$("#p105_popup").dialog("close");	
						}else{
							createAlert(mensaje,"ERROR");
							
							//Cerramos el popup
							$("#p105_popup").dialog("close");	
						}						
					},
					error : function (xhr, status, error){
						handleError(xhr, status, error, locale);
					}
				});	
			}
		}
	});
}
function camposVaciosModificar(){
	var camposVacios = false;
	var msgError = "";

	//Descripcion
	var descrCodArtLoteActual = $("#p105_fld_descrCodArtLote").val();
	if(descrCodArtLoteActual == "" || descrCodArtLoteActual == undefined){
		msgError = msgError + ", "+ descripcionArticuloLote;
		camposVacios = true;
	}

	//Fechas
	var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaIni').datepicker('getDate'));
	var fechaFinActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaFin').datepicker('getDate'));
	if(fechaIniActual == ""){
		msgError = msgError + ", "+ fechaInicioLote;
		camposVacios = true;
	}

	if(fechaFinActual == ""){
		msgError = msgError + ", "+ fechaFinLote;
		camposVacios = true;
	}

	//Combos
	var flgEroskiActual = $("#p105_fld_flgEroski").val();
	if(flgEroskiActual == ""){
		msgError = msgError + ", "+ flagEroskiLote;
		camposVacios = true;
	}

	var flgCapraboActual = $("#p105_fld_flgCaprabo").val();
	if(flgCapraboActual == ""){
		msgError = msgError + ", "+ flagCapraboLote;
		camposVacios = true;
	}

	//Imagenes. Si no hay tooltip, error.
	var img1 = $("#p105_fld_img1").attr("title");
	if(img1 == ""){
		msgError = msgError + ", "+ imagen1Lote;
		camposVacios = true;
	}

	//Imagen 2 y contenido
	var img2Title = $("#p105_fld_img2").attr("title");
	var img2 = $("#p105_fld_img2").val();
	var contenidoLote = $('#p105_nuevosArticulosLote').html();
	if((img2 == "" && img2Title == "") && !$.trim(contenidoLote).length){
		msgError = msgError + ", "+ imagen2ContenidoLote;
		camposVacios = true;
	}

	//Si la imagen2 es vacia y hay elementos en el lote, miramos que no sean TODOS vacios
	if((img2 == "" && img2Title == "") && $.trim(contenidoLote).length){
		var listaLote = $('#p105_nuevosArticulosLote .p105_nuevoArticuloLote');
		var existeLote = false;
		for(var i = 0; i<listaLote.length; i++){
			var elemento = listaLote[i];
			var idElemento = "#" +elemento.id;
			
			var titulo = $(idElemento + " .p105_inputTitle").val();
			var descripcion = $(idElemento + " .p105_inputDescr").val();
			
			if(titulo != "" || descripcion != ""){
				existeLote = true;
				break;
			}
		}
		if(!existeLote){
			msgError = msgError + ", "+ imagen2ContenidoLote;
			camposVacios = true;
		}
	}
	
	//Orden
	var orden = $("#p105_fld_orden").val();
	if(orden == "" || orden == undefined){
		msgError = msgError + ", "+ ordenLote;
		camposVacios = true;
	}

	//Si hay campos vacíos mostramos error.
	if(camposVacios){
		//Quitamos la primera coma
		msgError = msgError.substring(1)
		createAlert(rellenarCampos + msgError,"ERROR");
	}

	return camposVacios
}

function camposVaciosNuevo(){
	var camposVacios = false;
	var msgError = "";

	//Codigo articulo lote
	var codArtLote = $("#p105_fld_codArtLote").val();
	if(codArtLote == "" || codArtLote == undefined){
		msgError = msgError + ", "+ codigoArticuloLote;
		camposVacios = true;
	}

	//Descripcion
	var descrCodArtLoteActual = $("#p105_fld_descrCodArtLote").val();
	if(descrCodArtLoteActual == "" || descrCodArtLoteActual == undefined){
		msgError = msgError + ", "+ descripcionArticuloLote;
		camposVacios = true;
	}

	//Fechas
	var fechaIniActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaIni').datepicker('getDate'));
	var fechaFinActual = $.datepicker.formatDate("dd-mm-yy", $('#p105_fld_fechaFin').datepicker('getDate'));
	if(fechaIniActual == ""){
		msgError = msgError + ", "+ fechaInicioLote;
		camposVacios = true;
	}

	if(fechaFinActual == ""){
		msgError = msgError + ", "+ fechaFinLote;
		camposVacios = true;
	}

	//Combos
	var flgEroskiActual = $("#p105_fld_flgEroski").val();
	if(flgEroskiActual == ""){
		msgError = msgError + ", "+ flagEroskiLote;
		camposVacios = true;
	}

	var flgCapraboActual = $("#p105_fld_flgCaprabo").val();
	if(flgCapraboActual == ""){
		msgError = msgError + ", "+ flagCapraboLote;
		camposVacios = true;
	}

	//Imagenes
	var img1 = $("#p105_fld_img1").val();
	if(img1 == ""){
		msgError = msgError + ", "+ imagen1Lote;
		camposVacios = true;
	}

	//Imagen 2 y contenido
	var img2 = $("#p105_fld_img2").val();
	var contenidoLote = $('#p105_nuevosArticulosLote').html();
	if(img2 == "" && !$.trim(contenidoLote).length){
		msgError = msgError + ", "+ imagen2ContenidoLote;
		camposVacios = true;
	}

	//Orden
	var orden = $("#p105_fld_orden").val();
	if(orden == "" || orden == undefined){
		msgError = msgError + ", "+ ordenLote;
		camposVacios = true;
	}

	//Si hay campos vacíos mostramos error.
	if(camposVacios){
		//Quitamos la primera coma
		msgError = msgError.substring(1)
		createAlert(rellenarCampos + msgError,"ERROR");
	}

	return camposVacios
}

function validacionCamposModificar(){
	return true;
}

//Borra contenido del lote.
function borrarContenidoLote(elementoCruz){
	var elementoEstructura = $(elementoCruz).closest(".p105_nuevoArticuloLote");
	var idBd = elementoEstructura.attr("data-idbd");
	var idEstructura = elementoEstructura.attr("data-idestructura");

	//Si idBd es 0, significa que hemos pulsado nuevo y que el dato no viene de BD, por lo que borramos del html.
	if(idBd == "0"){
		$("#p105_" + idEstructura + "_articuloLote").remove();
	}else{
		//Si idBd tiene dato, el dato viene de BD, por lo que insertamos el elemento en una lista para borrarlo cuando se pulse modificar
		var cestasNavidadArticulo = new CestasNavidadArticulo(idBd);
		listadoBorrado.push(cestasNavidadArticulo);

		//Eliminamos el artículo de la lista.
		$("#p105_" + idEstructura + "_articuloLote").remove();
	}

	//Si el bloque de articulos de lote está vacío, se muestra el mensaje de que no hay artículos.
	if (!$.trim($('#p105_nuevosArticulosLote').html()).length){
		$("#p105_sinArticuloLote").show();
	}
}

function p105_limpiarPopUp(){
	//Reseteamos el popup

	//Ponemos por defecto el radio de imagen
	$("#p105_radioImg").click();

	//Mostramos el mensaje de no hay datos.
	$("#p105_sinArticuloLote").show();

	//Borramos los nuevos campos del lote que se hayan podido crear y ocultamos.
	$( "#p105_nuevosArticulosLote").empty();
	$( "#p105_nuevosArticulosLote").hide();

	//No deja modificar codArt
	$('#p105_fld_codArtLote').attr('readonly', false);

	//Ponemos el input de cod art modificable.
	$('#p105_fld_codArtLote').attr('readonly', false);
	
	//Vaciamos todos los campos
	$("#p105_fld_codArtLote").val("");
	$("#p105_fld_descrCodArtLote").val("");
	$('#p105_fld_fechaIni').datepicker('setDate',null);
	$('#p105_fld_fechaIni').data("oldDate",null);
	$('#p105_fld_fechaFin').datepicker('setDate', null);
	$('#p105_fld_fechaFin').data("oldDate",null);
	$("#p105_fld_img1").val("");
	$("#p105_fld_img2").val("");
	$("#p105_fld_img1").prop("title","");
	$("#p105_fld_img2").prop("title","");
	$("#p105_fld_flgEroski").combobox('autocomplete',comboSi);
	$("#p105_fld_flgCaprabo").combobox('autocomplete',comboSi);
	$("#p105_fld_orden").val("");

	//Texto botón
	$("#p105_btn_new").val(btnNuevo);

	//Ponemos en 0 la cantidad de elementos de lote.
	$('#p105_nuevosArticulosLote').attr("data-count",0);

	//Guarda la fila actual que se esta tratando.
	filaActual = null;

	//Reseteamos los listados
	listadoBorrado = new Array();
	listadoModificado = new Array();
	listadoNuevo = new Array();
}