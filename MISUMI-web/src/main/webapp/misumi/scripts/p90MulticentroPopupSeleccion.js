var referenciaRequired;
var unidadesRequired;
var unidadesRange;
var savedOK;
var optionActivaVacia = "";
var optionActivaTodas = "Todas";
//Lista de Centros
var listaCentros = new Array()

//Lista Temporal
var listaTmp = new Array();

//Lista final

var listaFinal= new Array();


var listaCentrosId = new Array();
var uis = new Array();
var cod_region;
var cod_zona;

$(document).ready(function(){

	initializeScreenP90PopupVentaAnticipada();
	p90_initializeCentroRegionZonaProvincia();
	p90_resetCentroRegionZonaProvincia();
	event_p90_centro();
	event_p90_region();
	event_p90_zona();
	event_p90_fecha();
	event_p90_btnLimpiar();
	event_p90_btnAceptar();
	load_p90_cmbRegion();

	$("#p90_multicentrosPopupSeleccion").keydown(function(e) {
		// ESCAPE key pressed
		if (e.keyCode == 27) {

			listaCentros= new Array();
			listaTmp = new Array();
			cleanTable();
			$('#p90_multicentrosPopupSeleccion').dialog('close');
		}
	});

});


function p90_initializeCentroRegionZonaProvincia(){
	//Inicializamos apartado 2) Centro o Región/Zona o Provincia
	p90_fld_centro_AutoComplete();
	//$("#p90_fld_centro").attr("disabled", "disabled");
	$("#p90_cmb_region").combobox(null);
	$("#p90_cmb_region").combobox("enable");
	$("#p90_cmb_zona").combobox(null);
	$("#p90_cmb_zona").combobox("disable");





}


function event_p90_btnAceptar(){

	var aux;
	var id ;
	var descr;


	$("#p90_btn_aceptar").on("click", function(e) {

		if (listaCentros.length != 0){

			for(i=0;i<listaCentros.length;i++){



				aux = listaCentros[i].split("-");
				id = aux[0];
				descr = aux[1]; 

				uis.push({'item':{

					'abbrev': parseInt(aux[0]),
					'id':  parseInt(aux[0]),
					'label': listaCentros[i]  ,
					'value': listaCentros[i] 

				}})

				aux=[];
			}



			$('#p90_multicentrosPopupSeleccion').dialog('close');
			if(listaCentros.length==1){
				setCentroUsuarioSession(uis[0],false);

			}else{
				setCentroUsuarioSession(uis,true);

			}
		}else{
			createAlert("No hay centro en la lista.","ERROR");
			$("#p90_fld_centro").focus();
		}

		//Resetear Arrays;
		listaFinal= new Array();

		for(var i = 0; i<listaCentros.length; i++){
			listaFinal.push(listaCentros[i]);
		}

		resetElementos();

	});
}

function event_p90_btnLimpiar(){

	$("#p90_btn_limpiar").on("click", function(e) {
		listaCentros= new Array();
		resetElementos();
	});
}

function resetElementos(){

	cleanTable();
	p90_resetCentroRegionZonaProvincia();
	resetListas();

}

function event_p90_centro(){

	$("#p90_fld_centro").on("keydown", function(e) {
		p90_fld_centro_AutoComplete();
		if(e.which == 13) {
			e.preventDefault();
			$("#p90_fld_centro").val('');
//			if($("#p90_fld_centro").val()!='')
//			{
//			var centro = new Array();
//			centro.push($("#p90_fld_centro").val());
//			//addCentros(centro);
//			loadTable();
//			}
		}
	});

	$("#p90_fld_centro").on("click", function(e) {
		$("#p90_fld_centro").val('');
		$("#p90_fld_centroId").val('');
	});



//	$("#p90_fld_region").on("focus", function(e) {
//	$("#p90_cmb_zona").val("");
//	$("#p90_cmb_region").val("");
//	});
}

function event_p90_region(){

	$("#p90_fld_region").on("focus", function(e) {
		$("#p90_fld_centro").val("");
	});
}


function event_p90_zona(){

	$("#p90_fld_zona").on("focus", function(e) {
		$("#p90_fld_centro").val("");
	});
}


function event_p90_fecha(){

	var id;
	var descripcion;

	$("#p90_flecha").on("click", function(e) {

		//si es centro
		if($("#p90_fld_centro").val()!='')
		{
			listaTmp=new Array();
			listaTmp.push($("#p90_fld_centro").val());
			//addCentros($("#p90_fld_centro").val());
			addCentros(listaTmp);
			loadTable();


		}else{

			//si es Region o Zona 
			if(cod_region/*$("#p90_cmb_region").find('option:selected').text()*/!=''){

				if(cod_zona/*$("#p90_cmb_zona").find('option:selected').text()*/!=''){

					if(cod_zona!='Todas'){

						addCentros(listaTmp);
					}else{
						createAlert("No hay Centros para la Region Seleccionada","ERROR");
						$("#p90_fld_centro").focus();
					}

//					id = $("#p90_cmb_zona").find('option:selected').val();
//					descripcion = $("#p90_cmb_zona").find('option:selected').text();

//					if(descripcion!='Todas'){
//					listCentrosAñadir.push(id+"-"+descripcion);
//					}else{

//					var length = $('#p90_cmb_zona > option').length;

//					if(length>1)
//					{
//					//añadimos todas las opciones a la lista
//					for(var i=1; i < length;i++){
//					id = document.getElementById("p90_cmb_zona").options[i].text;
//					descripcion = document.getElementById("p90_cmb_zona").options[i].text;

//					if(descripcion!='Todas'){
//					listCentrosAñadir.push(id+"-"+descripcion);
//					}else{
//					createAlert("No hay zonas en para esta Region","ERROR");
//					}

//					}
//					}else{
//					createAlert("No hay zonas en para esta Region","ERROR");
//					}
//					}

				}else{

					addCentros(listaTmp);
					//obtenemos el tamaño del select
					//var length = $('#p90_cmb_zona > option').length;

					//añadimos todas las opciones a la lista
//					for(var i=0; i < length;i++){
//					id = document.getElementById("p90_cmb_zona").options[i].text;
//					descripcion = document.getElementById("p90_cmb_zona").options[i].text;

//					if(descripcion!='Todas'){
//					listCentrosAñadir.push(id+"-"+descripcion);
//					}else{
//					createAlert("No hay zonas en para esta Region","ERROR");
//					}

//					}
				}

				cod_region= null;
				cod_zona= null;

				cleanTable();
				loadTable();

			}else{
				createAlert("Selecciona un CENTRO, una REGION o una ZONA.");
			}

		}
		//listaTmp = new Array();
		p90_resetCentroRegionZonaProvincia();

	});
}

function event_p90_deleteCentro(id){
	$("#p90_idCentros_"+id).on("click", function(e) {

		var idCentroDelete = $(this).attr("data-centroId_"+id);//document.querySelectorAll('img[data-centroId_'+id+']');

		//eliminamos datos del array de centros
		listaCentros.splice(idCentroDelete, 1);
		uis.splice(idCentroDelete, 1);
		cleanTable();
		loadTable();

	});
}


function p90_fld_centro_AutoComplete(){
	var cache = {};
	$( "#p90_fld_centro" ).autocomplete({
		minLength: 1,
		mustMatch:true,
		source: function( request, response ) {
			var term = request.term;

			$.ajax({
				url: "./welcome/loadCentrosPattern.do?term="+ request.term,
				dataType: "json",
				cache : false,

				success: function(data) {
					cache[ term ] = data;
					response($.map(data, function(item) {
						return {
							label: item.codCentro  +"-" +item.descripCentro  ,
							id: item.codCentro ,
							abbrev: item.codCentro
						};
					}));
				}
			});
		},

		select: function(event, ui) {
			//uis.push(ui);
			$("#p90_fld_centroId").val(ui.item.id);
			this.close;
			borrarSeleccionAnterior( $(this).attr("id"));
			var centro = new Array();
			centro.push(ui.item.label);
			addCentros(centro); 
			loadTable();

		},
		change: function (event, ui) {

			//uis.push(ui);

			if (!ui.item) {
				$("#p90_fld_centroId").val('');
				this.value = '';
			}else{
				$("#p90_fld_centroId").val(ui.item.id);
			}
		}
	});
	$('#p90_fld_centro').css('width', '408px');

}

function p90_resetCentroRegionZonaProvincia(){

	$("#p90_fld_centro").val("");
	$("#p90_fld_centroId").val("");
	$("select#p72_cmb_region").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p90_cmb_region").combobox('autocomplete','');
	$("#p90_cmb_region").combobox('comboautocomplete',null);
	$("select#p90_cmb_zona").html("<option value=null selected='selected'>"+''+"</option>");
	$("#p90_cmb_zona").combobox('autocomplete','');
	$("#p90_cmb_zona").combobox('comboautocomplete',null);
}

//function events_p90_fld_centro(){
//$( "#p90_fld_centro" ).bind('click', function() {
//$("#p90_fld_centro").val('');
//$("#p90_fld_centroId").val('');
//});
//}

function reset_p90_CentroUsuarioSession(centroNuevo){

	$.ajax({
		type : 'POST',
		url : './welcome/resetUserCentro.do',
		contentType : "application/json; charset=utf-8",
		dataType : "json",

		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function set_p90_CentroUsuarioSession(centroNuevo){

	var centro = new Centro();
	centro.codCentro = centroNuevo.item.id; //codDesCentro(centroNuevo)[0];
	centro.descripCentro =$.trim( codDesCentro( centroNuevo.item.value)[1]);

	var objJson = $.toJSON(centro.prepareToJsonObject());

	$.ajax({
		type : 'POST',
		url : './welcome/setUserCentro.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
			if (data.centro!=null){
				$("#p90_fld_centro").val(data.centro.codCentro);
				$("#centerName").val(concatCodDesCentro(data.centro.codCentro, data.centro.descripCentro));
				$("#centerNegocio").val(data.centro.codNegocio);
				$("#centerRegion").val(data.centro.codRegion);
				$("#centerZona").val(data.centro.codZona);
				$("#centerEnsena").val(data.centro.codEnsena);
				$("#centerArea").val(data.centro.codArea);
				if (data.centro.flgCapacidad != null){
					//Control de autoservicio para sfm y capacidad
					$("#p01_txt_flgCapacidad").val(data.centro.flgCapacidad);
				}else{
					$("#p01_txt_flgCapacidad").val("N");
				}	
				if (data.centro.flgFacing != null){
					//Control de facing
					$("#p01_txt_flgFacing").val(data.centro.flgFacing);
				}else{
					$("#p01_txt_flgFacing").val("N");
				}				 
			}else{
				$("#p01_txt_flgCapacidad").val("N");
				$("#p01_txt_flgFacing").val("N");
			}


			//location.reload();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
		}			
	});	

}

function load_p90_cmbRegion(){	

	var options = "";
	var region=new Region(null, null, null, null, null);
	var objJson = $.toJSON(region);	
	$.ajax({
		type : 'POST',
		url : './intertienda/loadListaRegion.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {				
			for (i = 0; i < data.length; i++){
				options = options + "<option value='" + data[i].codRegion + "'>" + data[i].descripcion + "</option>"; 
			}
			$("select#p90_cmb_region").html(options);
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
	$("#p90_cmb_region").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null" ) {
				if ($("#p90_cmb_region").val()!=null){
					$("#p90_cmb_region").combobox('autocomplete',ui.item.value);
					$("#p90_cmb_region").combobox('comboautocomplete',ui.item.value);
					$("#p90_cmb_zona").combobox("enable");
					cod_region=$("#p90_cmb_region").attr("value");
					load_p90_cmbZona();

					obtenerCentrosRegion();


					//cuando seleccione en zona reseteo centro y provincia
					borrarSeleccionAnterior( $(this).attr("id"));
				}
			}else{
				$("#p90_cmb_region").combobox(null);
				$("#p90_cmb_region").combobox("disable");
			}

		}
	}); 

	//Después de cargar el combo lo inicializamos a vacio 
	$("#p90_cmb_region").combobox('autocomplete',optionActivaVacia);
	$("#p90_cmb_region").combobox('comboautocomplete',null);

}

function load_p90_cmbZona(){
	var options = "";
	$("#p90_cmb_zona").combobox(null);
	var codRegion = $("#p90_cmb_region").combobox('getValue');
	var centro=new Centro(null, null, null, null, null, null, codRegion, null, null, null);
	var objJson = $.toJSON(centro);	
	$.ajax({
		type : 'POST',
		url : './intertienda/loadListaZona.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {			
			options = options + "<option value='" + optionActivaTodas + "'>" + optionActivaTodas + "</option>"; 
			for (i = 0; i < data.length; i++){
				options = options + "<option value='" + data[i].codZona + "'>" + data[i].descripZona + "</option>"; 
			}
			$("select#p90_cmb_zona").html(options);

			$('select option:first-child');
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
	$("#p90_cmb_zona").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null") {
				if ($("#p90_cmb_zona").val()!=null){
					$("#p90_cmb_zona").combobox('autocomplete',ui.item.value);
					$("#p90_cmb_zona").combobox('comboautocomplete',ui.item.value);
					cod_zona=$("#p90_cmb_zona").attr("value");
					obtenerCentrosZona();
					//cuando seleccione en zona reseteo centro y provincia
					borrarSeleccionAnterior( $(this).attr("id"));

				} 
			} 
		}
	});

	//Después de cargar el combo lo inicializamos a vacio 
	$("#p90_cmb_zona").combobox('autocomplete',optionActivaVacia);
	$("#p90_cmb_zona").combobox('comboautocomplete',null);
}


function initializeScreenP90PopupVentaAnticipada(){
	$( "#p90_multicentrosPopupSeleccion" ).dialog({
		autoOpen: false,
		height: 300,
		width: 1000,
		modal: true,
		resizable: false,
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				resetElementos();
				$('#p90_multicentrosPopupSeleccion').dialog('close');

			});
		}
	});

	$(window).bind('resize', function() {
		$("#p90_multicentrosPopupSeleccion").dialog("option", "position", "center");
	});

	$(".controlReturnP90").on("keydown", function(e) {
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
		}
	});

	$("#p32_fld_referencia").filter_input({regex:'[0-9]'});
	$("#p32_fld_unidades").filter_input({regex:'[0-9]'});
	//$("#p32_fld_unidades").formatNumber({format:"0.000",locale:"es"});

}

function borrarSeleccionAnterior(idSeleccion){

	if (idSeleccion=="p90_fld_centro"){
		//Resetea centro, region y zona 
		$("#p90_cmb_region").combobox("enable");
		$("#p90_cmb_region").combobox('comboautocomplete',null);

		$("#p90_cmb_region").combobox('autocomplete',optionActivaVacia);
		$("#p90_cmb_region").combobox('comboautocomplete',null);
		$("#p90_cmb_zona").combobox('autocomplete',optionActivaVacia);
		$("#p90_cmb_zona").combobox('comboautocomplete',null);
		$("#p90_cmb_zona").combobox("disable");

	}

	else if (idSeleccion=="p90_cmb_region" || idSeleccion=="p90_cmb_zona"){

		$("#p90_fld_centro").val("");
		$("#p90_fld_centroId").val("");

	}

	else {
		//Resetea centro y provincia
		$("#p90_fld_centro").val("");
		$("#p90_fld_centroId").val("");

		$("#p90_cmb_region").combobox('autocomplete',optionActivaVacia);
		$("#p90_cmb_region").combobox('comboautocomplete',null);
		$("#p90_cmb_zona").combobox('autocomplete',optionActivaVacia);
		$("#p90_cmb_zona").combobox('comboautocomplete',null);
		$("#p90_cmb_zona").combobox("disable");

	}

}

function addCentros(descripcion){


	if(descripcion.length!=0){
		for(var i = 0; i < descripcion.length; i++){
			//Recogemos el centro a añadir.
			var resultado = jQuery.inArray(descripcion[i], listaCentros);
			//Comprobamos que el centro no este ya añadido.
			if(resultado==-1){
				//añadimos centro
				listaCentros.push(descripcion[i]);
				listaCentrosId.push($("#p90_fld_centroId").val());
			}else{
				createAlert("El centro "+descripcion[i]+" ya esta en la lista","ERROR");
				$("#p90_fld_centro").focus();
			}
		}
	}else{
		createAlert("No hay Centros para añadir.","ERROR");
		$("#p90_fld_centro").focus();
	}


//	$("#listaCentros tbody").append(
//	"<tr><td>"+descripcion+"</td><td><img src='./misumi/images/delete.gif?version=${misumiVersion}' class='btnDelete'/></td>"+ "</tr>"); 		

}

function deleteCentros(id){

	listaCentros.remove(id); 		
	loadTable();
}

function loadTable(){

	cleanTable();
	for (var i=0; i<listaCentros.length; i++) {
		$("#listaCentros tbody").append(
				"<tr><td style='width:93%'>"+listaCentros[i]+"</td><td><img id='p90_idCentros_"+i+"' data-centroId_"+i+"='"+i+"'src='./misumi/images/dialog-cancel-16.gif?version=${misumiVersion}' class='btnDelete'/></td>"+ "</tr>");
		event_p90_deleteCentro(i);

	}
	$("#p90_fld_centro").val('');
	$("#p90_fld_centro").focus();

}

function cleanTable(){
	$("#listaCentros tbody").html("");
}

function obtenerCentrosRegion(){

	//reseteamos array Temporal
	listaTmp = new Array();
	var objJson = $.toJSON(cod_region);

	$.ajax({
		type : 'POST',
		url : './multiCentro/getCentrosRegion.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {				
			if (data!=null){

				for(var i=0; i< data.length; i++){
					listaTmp.push(data[i].codCentro+"-"+data[i].descripCentro);
				}
			}

//			cod_zona="";
//			cod_region=""
//			addCentros(listCentrosAñadir);
//			cleanTable();
//			loadTable();
		},
		error : function (xhr, status, error){
			createAlert("ERROR Obtencion de Centros por region","ERROR");
			handleError(xhr, status, error, locale);
			$("#p90_fld_centro").focus();
		}			
	});

	p90_resetCentroRegionZonaProvincia();

}

function obtenerCentrosZona(){

	//reseteamos array Temporal
	listaTmp = new Array();

	if(cod_zona!='Todas'){


		var objJson = $.toJSON(cod_zona);

		$.ajax({
			type : 'POST',
			url : './multiCentro/getCentrosZona.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {				
				if (data!=null){
					listaTmp= new Array();
					for(var i=0; i< data.length; i++){
						listaTmp.push(data[i].codCentro+"-"+data[i].descripCentro);
					}
				}
			},
			error : function (xhr, status, error){
				createAlert("ERROR Obtencion de Centros por Zona","ERROR");
				handleError(xhr, status, error, locale);				
				$("#p90_fld_centro").focus();
			}			
		});

	}
}

function resetListas(){
	listaCentros = new Array();
	listaTmp = new Array();
	uis = new Array();
}

function p90_getCentros(){
	if($("#centerId").attr("value")!=''){
		if(listaFinal.length!=0){
			for(var i = 0 ; i < listaFinal.length; i++){
				listaCentros.push(listaFinal[i]);
			}

			//listaFinal = new Array();
			cleanTable();
			loadTable();
		}else{
			listaFinal.push($("#centerName").val());
			for(var i = 0 ;  i <  listaFinal.length; i++){
				listaCentros.push(listaFinal[i]);
			}
			//listaCentros = listaFinal;
			//listaFinal = new Array();
			cleanTable();
			loadTable();
		}
	}else{
		for(var i = 0; i<listaFinal.length; i++){
			listaCentros.push(listaFinal[i]);
		}

		//listaFinal = new Array();
	}
}

function clearListaFinal(){
	listaFinal = new Array();
}

function set_listaFinal(centro){

	listaFinal.push(centro);

}

