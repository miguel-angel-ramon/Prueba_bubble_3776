var referenciaRequired;
var unidadesRequired;
var unidadesRange;
var savedOK;

var flgBusquedaRealizada = false;
$(document).ready(function(){
	loadP32(locale);
	events_p32_btn_save();
	events_p32_btn_accept();
	events_p32_btn_informe();
	events_p32_fld_ref();
	initializeScreenP32PopupVentaAnticipada();

});
function loadReferenceDataP32(locale){
	if ($("#p32_fld_referencia").val()){
		var vVentaAnticipada=new VentaAnticipada($("#p32_fld_referencia").val());
		var objJson = $.toJSON(vVentaAnticipada.prepareToJsonObject());	
		$.ajax({
			type : 'POST',
			url : './ventaAnticipada/getVenta.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {	
				$("#p32_ventaAnticipada_bloqueGuardado").hide();
				if (null == data.descError){
					$("#p32_fld_denominacion").val(data.descArt);
					if (null != data.cantidad){
						$("#p32_fld_unidades").val(data.cantidad);
					} else {
						$("#p32_fld_unidades").val("");
					}
					$("#p32_fld_referenciaBuscada").val(data.codArt);
					$("#p32_fld_existe").val(data.existe);
					$("#p32_fld_flgEnvioAC").val(data.flgEnvioAC);
					$("#p32_fld_fechaGen").val(data.fechaGen);
					$("#p32_lbl_fechaGen").text(data.fechaGenFormated);
					$("#p32_AreaDatosVentaAnticipada").show();
					$("#p32_fld_unidades").select();
					$("#p32_fld_unidades").focus();

					//flag que indica si se ha buscado la referencia antes de hacer el guardado.
					//Así se evita que el usuario introduzca la referencia y sin pulsar aceptar 
					//o enter tras introducir la referencia, introduzca las unidades y pulse guardar.
					flgBusquedaRealizada= true;
				} else {
					$("#p32_AreaDatosVentaAnticipada").hide();
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						$("#p32_fld_referencia").focus();
						$("#p32_fld_referencia").select();
					});
					createAlert(data.descError, "ERROR");
					$("#p03_btn_aceptar").focus();
				}
			},
			error : function (xhr, status, error){
				$("#p32_ventaAnticipada_bloqueGuardado").hide();
				handleError(xhr, status, error, locale);
			}			
		});	
	} else {
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
			$("#p32_fld_referencia").focus();
			$("#p32_fld_referencia").select();
		});
		createAlert(referenciaRequired, "ERROR");
		$("#p03_btn_aceptar").focus();
	}
}

function saveReferenceDataP32(locale){

	if(flgBusquedaRealizada){
		if ($("#p32_fld_referencia").val() && $("#p32_fld_unidades").val() && checkValidUnidades()){
			var vVentaAnticipada=new VentaAnticipada($("#p32_fld_referencia").val());
			vVentaAnticipada.cantidad = $("#p32_fld_unidades").val();
			vVentaAnticipada.existe = $("#p32_fld_existe").val();
			vVentaAnticipada.flgEnvioAC = $("#p32_fld_flgEnvioAC").val();
			vVentaAnticipada.fechaGen = $("#p32_fld_fechaGen").val();
			var objJson = $.toJSON(vVentaAnticipada.prepareToJsonObject());	
			$.ajax({
				type : 'POST',
				url : './ventaAnticipada/saveVenta.do',
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				success : function(data) {	
					$("#p32_fld_referencia").val("");
					/*$("#p03_btn_aceptar").unbind("click");
			$("#p03_btn_aceptar").bind("click", function(e) {
				$("#p32_fld_referencia").focus();
				$("#p32_fld_referencia").select();
			 });
			createAlert(savedOK, "INFO");
			$("#p03_btn_aceptar").focus();*/
					$("#p32_ventaAnticipada_bloqueGuardado").show();
					$("#p32_fld_referencia").focus();

				},
				error : function (xhr, status, error){
					$("#p32_ventaAnticipada_bloqueGuardado").hide();
					handleError(xhr, status, error, locale);
				}			
			});
		} else {
			if ($("#p32_fld_referencia").val() && $("#p32_fld_unidades").val()){
				$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					$("#p32_fld_unidades").focus();
					$("#p32_fld_unidades").select();
				});
				createAlert(unidadesRange, "ERROR");
				$("#p03_btn_aceptar").focus();
			} else if ($("#p32_fld_referencia").val()){
				$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					$("#p32_fld_unidades").focus();
					$("#p32_fld_unidades").select();
				});
				createAlert(unidadesRequired, "ERROR");
				$("#p03_btn_aceptar").focus();
			} else {
				$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {
					$("#p32_fld_referencia").focus();
					$("#p32_fld_referencia").select();
				});
				createAlert(referenciaRequired, "ERROR");
				$("#p03_btn_aceptar").focus();
			}
		}
	}else{
		createAlert(busquedaReferenciaObligatoria,"ERROR");
	}
}




function initializeScreenP32PopupVentaAnticipada(){
	$( "#p32_popupVentaAnticipada" ).dialog({
		autoOpen: false,
		height: 310,
		width: 550,
		modal: true,
		resizable: false,
		open: function() {
			$('#p32_popupVentaAnticipada .ui-dialog-titlebar-close').on('mousedown', function(){
				$('#p32_popupVentaAnticipada').dialog('close');
			});
		},
		close:function(){
			//Si el campo referencia está vacío pero la referencia buscada no lo esta, significa que
			//se ha hecho una búsqueda anterior, por lo que al introducir el primer dígito de la nueva
			//referencia, limpiamos todos los datos de la referencia anterior. Si la referencia anterior
			//está vacía, quiere decir que es la primera referencia que buscamos y por lo tanto no hay
			//que limpiar los estados anteriores.
			if($("#p32_fld_referenciaBuscada").val() != ""){
				//Si cerramos el dialogo, borramos todos los datos relacionados con búsquedas anteriores.
				limpiarInformacionReferencia();
			}
		}
	});

	$(window).bind('resize', function() {
		$("#p32_popupVentaAnticipada").dialog("option", "position", "center");
	});

	$(".controlReturnP32").on("keydown", function(e) {
		if(e.which == 13) {
			e.preventDefault();
			if($("#p32_fld_referencia").is(":focus")){
				loadReferenceDataP32(locale);
			} else if ($("#p32_fld_unidades").is(":focus")){
				saveReferenceDataP32(locale);

			} else {
				$("#p32_fld_referencia").select();
				$("#p32_fld_referencia").focus();
			}
		}else{
			if($("#p32_fld_referencia").is(":focus")){
				//Si el campo referencia está vacío pero la referencia buscada no lo esta, significa que
				//se ha hecho una búsqueda anterior, por lo que al introducir el primer dígito de la nueva
				//referencia, limpiamos todos los datos de la referencia anterior. Si la referencia anterior
				//está vacía, quiere decir que es la primera referencia que buscamos y por lo tanto no hay
				//que limpiar los estados anteriores.
				if($("#p32_fld_referencia").val()  == "" && $("#p32_fld_referenciaBuscada").val() != ""){
					limpiarInformacionReferencia();
				}
			}
		}
	});

	$("#p32_fld_referencia").filter_input({regex:'[0-9]'});
	$("#p32_fld_unidades").filter_input({regex:'[0-9]'});
	//$("#p32_fld_unidades").formatNumber({format:"0.000",locale:"es"});

}

function events_p32_btn_save(){
	$("#p32_btn_save").click(function () {
		saveReferenceDataP32(locale);
	});	
}

function events_p32_btn_accept(){
	$("#p32_btn_aceptar").click(function () {
		loadReferenceDataP32(locale);
	});	
}

function events_p32_btn_informe(){
	$("#p32_btn_informe").click(function () {
		$( "#p112_popupRefConsumoRapido" ).dialog("open");
	});	
}


function events_p32_fld_ref(){
	$("#p32_fld_referencia").click(function () {
		if($("#p32_fld_referencia").val()  != ""){
			limpiarInformacionReferencia();
		}
	});	
}

function cleanP32(){
	$("#p32_fld_referencia").val("");
	$("#p32_fld_unidades").val("");
	$("#p32_fld_denominacion").val("");
	$("#p32_lbl_fechaGen").text("");
	$("#p32_AreaDatosVentaAnticipada").hide();
	$("#p32_ventaAnticipada_bloqueGuardado").hide();
}

function loadP32(locale){
	this.i18nJSON = './misumi/resources/p32PopUpVentaAnticipada/p32PopUpVentaAnticipada_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		referenciaRequired = data.referenciaRequired;
		unidadesRequired = data.unidadesRequired;
		unidadesRange = data.unidadesRange;
		busquedaReferenciaObligatoria = data.busquedaReferenciaObligatoria;
		savedOK= data.savedOK;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function checkValidUnidades(){
	var unidades = parseInt($("#p32_fld_unidades").val(), 10);
	if (unidades > 99999){
		return false;
	} else {
		return true;
	}
}

//Función que limpia toda la información de una referencia buscada anteriormente.
function limpiarInformacionReferencia(){
	//Limpiamos los datos visibles de la referencia anterior.
	$("#p32_fld_referencia").val("");
	$("#p32_fld_denominacion").val("");
	$("#p32_fld_unidades").val("");

	//Limpiamos los hiddens de la referencia anterior
	$("#p32_fld_referenciaBuscada").val("");
	$("#p32_fld_existe").val("");
	$("#p32_fld_flgEnvioAC").val("");
	$("#p32_fld_fechaGen").val("");
	$("#p32_lbl_fechaGen").text("");

	//Escondemos el posible mensaje de guardado con exito.
	$("#p32_ventaAnticipada_bloqueGuardado").hide();

	//La ejecución de esta función implica que se está introduciendo una
	//referencia nueva en el buscador, por lo que pasamos el flgBusquedaRealizada a falso.
	flgBusquedaRealizada = false;
}
