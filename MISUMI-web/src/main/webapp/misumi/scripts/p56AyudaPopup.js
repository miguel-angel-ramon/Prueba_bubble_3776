var campo = null;

//Variables json para rellenar titulo calendario
var productoPrecio;
var ofertasSimilares; 
//Titulo inicial dialogo
var titleDialog;

function initializeP56(){
	loadP56(locale);
	initializeScreenP56();
}
function loadP56(locale){
	this.i18nJSON = './misumi/resources/p56AyudaPopup/p56AyudaPopup_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		productoPrecio= data.prodPrecio;
		ofertasSimilares = data.ofertasSimilares;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function defineCampoFoco(target){
	campo = target;
}

//Oculta la ayuda.
function limpiarAyudaP56(){

	//Es necesario destruir el combo para que al pulsar enter no deje el componente 
	//desplegado
	$("#p56_cmb_ventasEnUltimasOfertas").combobox('destroy');

	/*Se inicializa el combobox. Se pone aquí y no en load_cmb_ventasUltimasOfertas()
	 *para que al entrar en encargos no de error al cerrar el popup y destruir el combo
	 *que no se ha inicializado.*/
	$("#p56_cmb_ventasEnUltimasOfertas").combobox(null);

}
//Función creada para cerrar el dialogo. Se ha creado la función porque
//el dialog acumula eventos mouseDowns que ejecutan su contenido una y otra vez
//y se quiere hacer un unbind mousedown sólo de esta función y no de todos
//los mousedown del componente.
function closeDialog(){
	e.preventDefault();
	$("#p56_AreaAyuda").dialog('close');
}

function initializeScreenP56(){
	//Se inicializa el combobox.
	$("#p56_cmb_ventasEnUltimasOfertas").combobox(null);

	//Se inicializan los eventos
	events_p56_btn_pagAnt();
	events_p56_btn_pagSig();
	//$("#p56_AreaPestanas").show();

	//Inicializamos el popup de ayuda.
	$( "#p56_AreaAyuda" ).dialog({
		autoOpen: false,
		height: 'auto',
		width: 475,
		minHeight:0,
		modal: false,
		resizable: false,
		dialogClass: "p56_popupResaltado",
		//Nos posicionamos en la mitad de la pantalla, le sumamos la mitad del marco para situarnos
		//al final del marco y le restamos el tama�o del popup.
		//position:[($(document).width()/2)+490-650,0],
		//Cambio para que aparezca pegado a la izda de la pantalla
		position:[0,0],
		open: function(e) {
			//load_cmbVentasUltimasOfertasP56(v_Oferta);
			/*$(this).parent().children(':first-child').children('.ui-dialog-titlebar-close').on('mousedown', function(e){
				e.preventDefault();
				$("#p56_AreaAyuda").dialog('close');
				$("#p56_AreaAyuda").parent().children(':first-child').children('.ui-dialog-titlebar-close').unbind('mousedown');
			});*/
			//Si el campo que abre el popup es input hacemos focus. Si es link (<a href="">) el select() da error, por
			//eso hay que hacer este control.
			if(campo.nodeName == "INPUT"){
				campo.focus();
				campo.select();
			}
			//reload();
		},
		//Fijar el z-index del popup p56 para que las opciones del combobox no se queden detrás del popup 
		//al autoincrementarse el z-index.
		zIndex: 1000,
		stack: false
	})
	.dialogExtend({
		"maximizable" : false,
		"collapsable" : false,
		"minimizable" : true,
		"minimizeLocation" : "left",		
		"minimize" : function(evt) {
			//Obtenemos el título del diálogo minimizado y le insertamos la clase p56_tituloAcortado que sirve para acortar el título
			//y dejarlo con puntos suspensivos.
			$(".p56_popupResaltado").parent().find("#dialog-extend-fixed-container").find(".ui-dialog-title").addClass("p56_tituloAcortado");
		}
	});	

	//Guarda el nombre del título del dialogo en una variable global para más tarde añadirle texto Prod.Precio
	// u ofertas similares
	titleDialog =  $("#p56_AreaAyuda").parent().children(':first-child').children('span:first-child').text();
	//$("#p56_AreaAyuda").parent().children(':first-child').children('span:first-child').after('<span id="spanNuevo" style="background-color:white"></span>');
}
//Función que sirve para obtener la lista de ventas de última oferta y así rellenar el combobox de selección.
function load_cmbVentasUltimasOfertasP56(v_Oferta,oferta,tipoPedido) {
	//Se crean las variables apra guardar los elementos html del combobox.
	var options = "";

	//Se transforma la oferta a tipo json
	var objJson = $.toJSON(v_Oferta.prepareToJsonObject());

	//Si no es suma anticipada
	sumVentaAnticipada=0;

	//if (tipoPedido != "1") { //Si es ENCARGO no aparece el calendario por lo que no tenemos que calcular nada de esto
			//Se realiza una llamada ajax para obtener una lista de ventas de las últimas ofertas.
			$.ajax({
				type : 'POST',			
				url : './nuevoPedidoAdicional/loadAyuda1.do?sumVentaAnticipada='+sumVentaAnticipada,
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				async:false,
				success : function(data) {	
					//Ocultar divs.
					$("#p56_infoCalendario").hide();
					$("#p56_sinVentasOfertas").hide();
		
					//Si ha habido ventas en últimas ofertas enseñarlas
					if(data.length > 0){
						//Se rellena el combobox con la lista de ventas de ofertas con html, al cambiar el elemento del combo se actualizarán los demás elementos de la ayuda.
						//Por cada oferta una línea html, como se van a guardar la lista de objetos de oferta en una lista javascript el valor de cada objeto será su posición en la lista.
						var periodo;
						for (i = 0; i < data.length; i++){	   
							periodo = fechaInicioFechaFinCorta(data[i].fechaIniPeriodo,data[i].fechaFinPeriodo);
							options = options + "<option value='"+i+"'>"+data[i].cOferta+" ("+periodo+") "+data[i].tipoOfertaString+"</option>"; 
						}
						//Se inseta el código html en su correspondiente componente jquery.
						$("select#p56_cmb_ventasEnUltimasOfertas").html(options);
		
						//Se guarda la lista de objetos de ofertas en la variable global, de esta forma al seleccionar el combo tenemos el objeto.
						listaVOfertaPaAyuda = data;
		
						//Elige el primer elemento del combobox y lo selecciona.
						var primerElemento = 0;
						periodo = fechaInicioFechaFinCorta(data[primerElemento].fechaIniPeriodo,data[primerElemento].fechaFinPeriodo);
						var primerElementoCombo = data[primerElemento].cOferta +" ("+periodo+") "+data[primerElemento].tipoOfertaString;
						$("#p56_cmb_ventasEnUltimasOfertas").combobox('autocomplete',primerElementoCombo);
		
						//Guardar referencia de articulo para poder utilizarlo para sacar el popup p17. Se utiliza en p17.js
						refArticulo = v_Oferta.codArt;
		
						//Elimina los eventos de otros calendarios
						eliminarEventosP56();
		
						load_AyudaData(primerElemento,6,7,"#p56_lbl_cantidadMoneda","#p56_lbl_cantidadTotalVentas","#p56_ventasUltimasOfertasDiaTd","#p56_ventasUltimasOfertasCantidadMesTd","#p56_ventasUltimasOfertasCantidadMesFechaTd","#p56_mes","#p56_pagAnt","#p56_pagSig","#p56_showTitle");	
		
						//Enseñar div de calendario. Se pone después del dibujado para que no se vean los datos viejos.
						$("#p56_infoCalendario").show();
		
		
					}else{
						//Enseñar div de no hay últimas ofertas
						$("#p56_sinVentasOfertas").show();
		
					}	//Conseguir el texto del dialog.
					if(oferta == 0 || oferta == 4){
						//$("#spanNuevo").text(productoPrecio); 
						$("#p56_AreaAyuda").parent().children(':first-child').children('span:first-child').text(titleDialog +" "+productoPrecio);
						//$("#p56_AreaAyuda").parent().children(':first-child').children('span:first-child').after('<span>'+"Jaime "+'</span>');
					}else{
						//$("#spanNuevo").text(ofertasSimilares);  
						//$("#p56_AreaAyuda").parent().children(':first-child').children('span:first-child').after('<span>'+"Eva"+'</span>');
						$("#p56_AreaAyuda").parent().children(':first-child').children('span:first-child').text(titleDialog +" "+ofertasSimilares);
					}
					//Abrir el dialogo una vez esten cargados los datos
					$( "#p56_AreaAyuda" ).dialog( "open" );
				},
				error : function (xhr, status, error){
					/*	$("#p54_AreaAyudaDatos .loading").css("display", "none");*/		
					handleError(xhr, status, error, locale);			
				}			
			});	
			
	//} else {
		//Ocultar divs.
		//$("#p56_infoCalendario").hide();
		//$("#p56_sinVentasOfertas").hide();
	//}

	//Abrir el dialogo una vez esten cargados los datos
	//$( "#p56_AreaAyuda" ).dialog( "open" );

	//pedido hoy y mañana

	if(v_Oferta.codArt){
		var pendientesRecibir = new PendientesRecibir ( 
				$("#centerId").val() , 
				v_Oferta.codArt ,
				null ,
				null);

		var objJson = $.toJSON(pendientesRecibir.prepareToJsonObject_2());

		$.ajax({
			type : 'POST',			
			url : './referenciasCentro/pendientesRecibir.do',
			data : objJson,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {	
				$("#p57_cantidades_hoy").text(data.cantHoy);
				$("#p57_cantidades_mannana").text(data.cantFutura);
			},
			error : function (xhr, status, error){
				handleError(xhr, status, error, locale);				
			}			
		});			
	}

	//Ver que elemento del combobox se ha seleccionado y rellenar "tabla", "precio" y "oferta del"
	$("#p56_cmb_ventasEnUltimasOfertas").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null") {
				//Conseguir el emento del combo seleccionado.
				var comboValue = $("#p56_cmb_ventasEnUltimasOfertas").val();
				if (comboValue !=null){
					//Elimina los eventos de otros calendarios
					eliminarEventosP56();

					//Si no es suma anticipada
					sumVentaAnticipada=0;

					//Cargar los datos de la tabla, precio y oferta del según la oferta seleccionada. Funciones en utils/calendario.js
					load_AyudaData(comboValue,6,7,"#p56_lbl_cantidadMoneda","#p56_lbl_cantidadTotalVentas","#p56_ventasUltimasOfertasDiaTd","#p56_ventasUltimasOfertasCantidadMesTd","#p56_ventasUltimasOfertasCantidadMesFechaTd","#p56_mes","#p56_pagAnt","#p56_pagSig","#p56_showTitle");	
				}
			}  
		}  
	});
}

function events_p56_btn_pagSig(){
	$( "#p56_pagSig" ).on('click',function() {
		if(indiceMes < listaMesesGlobal.length-1){ 	
			indiceMes ++;

			//Elimina los eventos de otros calendarios
			eliminarEventosP56();

			//Si no es suma anticipada
			sumVentaAnticipada=0;

			//Funciones en calendario.js. Rellena el calendario con el mes situado en la posición indiceMes
			rellenaCalendario(indiceMes,6,7,"#p56_ventasUltimasOfertasDiaTd","#p56_ventasUltimasOfertasCantidadMesTd","#p56_ventasUltimasOfertasCantidadMesFechaTd","#p56_mes");

			//SI el mes es el último, poner flecha siguiente invalidado y flecha anterior validada.
			if(indiceMes == listaMesesGlobal.length-1){
				$("#p56_pagSig").removeClass("mes_Sig").addClass("mes_Sig_des");
				$("#p56_pagAnt").removeClass("mes_Ant_des").addClass("mes_Ant");
			}else{
				$("#p56_pagAnt").removeClass("mes_Ant_des").addClass("mes_Ant");
			}
		}
	});
}

function events_p56_btn_pagAnt(){
	$( "#p56_pagAnt" ).on('click',function() {
		if(indiceMes > 0){ 	
			indiceMes --;

			//Elimina los eventos de otros calendarios
			eliminarEventosP56();

			//Si no es suma anticipada
			sumVentaAnticipada=0;

			//Funciones en calendario.js. Rellena el calendario con el mes situado en la posición indiceMes
			rellenaCalendario(indiceMes,6,7,"#p56_ventasUltimasOfertasDiaTd","#p56_ventasUltimasOfertasCantidadMesTd","#p56_ventasUltimasOfertasCantidadMesFechaTd","#p56_mes");

			//Si el mes es el primero, poner flecha anterior invalidada y validar flecha siguiente.
			if(indiceMes == 0){
				$("#p56_pagAnt").removeClass("mes_Ant").addClass("mes_Ant_des");
				$("#p56_pagSig").removeClass("mes_Sig_des").addClass("mes_Sig");
			}else{
				$("#p56_pagSig").removeClass("mes_Sig_des").addClass("mes_Sig");
			}
		}
	});
}

function eliminarEventosP56(){
	//Desasigno los eventos que se habían pusto en los objetos
	$(".hayVentasUltimasOfertas").unbind('click');
}



