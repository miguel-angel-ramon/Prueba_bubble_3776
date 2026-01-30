var pedidoAdicional = null;
var modificable = false;
var endDateGrtrStartDate = null;
var invalidCant1 = null;
var invalidCant2 = null;
var invalidCant3 = null;
var invalidCant4 = null;
var invalidCant5 = null;
var invalidCapMax = null;
var invalidImpMin = null;
var referenceNoMaintenance = null;
var p60TipoEncargo = null;
var implInicialError = null;
var implFinalError = null;
var literalAyuda1ConOferta = null;
var literalAyuda1SinOferta = null;
var p60MensajeFechaHasta = null;
var impFinalModif = false;
var cantidadMinima = 1;
var cantidadMaxima = 9999;
var p60ModificableIndiv = null;
var fechaSelBloqueosEncargosMontajes = null;
var fechaSelBloqueosEncargos = null;
var fechaSelBloqueosMontajes = null;

function initializeP60(){

	initializeScreenP60();
	load60Literales(locale);
	$("#p60_AreaModificacion").keydown(function(event) {
		if(event.which == 13) {
			//event.preventDefault();
	        var buttons = $( "#p60_AreaModificacion" ).dialog( "option", "buttons" );
	        var numBotones = buttons.length;
	      
	        if (numBotones == 2){
	        	//Botones de guardar y volver
	        	//Se fuerza a cambiar el foco a la cantidad2 y así provocar el evento change de la cantidad1
	        	if($("#p60_fld_cantidad2").is(':visible') && 'disabled' !=$("#p60_fld_cantidad2").attr('disabled')){
	        		$("#p60_fld_cantidad2").focus();
	        	}
	        	p60GuardarPedido();
	        }else{
	        	//Botón de volver
	        	p60ControlesCierrePopUp($( "#p60_AreaModificacion" ).dialog());
	        }
		}        
    });
	initializeP53();

}

//Si se pulsa intro, volvemos a lanzar el buscar.
/*$(".controlReturnP60").keydown(function(e) {
    if(e.which == 13) {
    	e.preventDefault();
    	p60GuardarPedido();
    }
});*/



function initializeScreenP60(){
	
	p60CrearCalendarios();
	
	$("#p60_fld_tipoPedidoAdicional").attr("disabled", "disabled");
	$("#p60_fld_referenciaVisualizada").attr("disabled", "disabled");
	$("#p60_fld_denominacionVisualizada").attr("disabled", "disabled");
	$("#p60_fld_aprov").attr("disabled", "disabled");
	$(".p60_bloque3AreaDatosAyuda").css("display", "none");
	
	$("#p60_cmb_excluir").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null"  ) {
        		pedidoAdicional.excluir = ui.item.value;
        	}
          
        } ,		       
        changed: function(event, ui) { 
			   if (ui.item==null ||ui.item.value!="" || ui.item.value!="null"){
				   pedidoAdicional.excluir = ui.item.value;
			   }	 
		   }
    });
	
	$("#p60_cmb_cajas").combobox({
        selected: function(event, ui) {
        	if ( ui.item.value!="" && ui.item.value!="null"  ) {
        		pedidoAdicional.cajas = ui.item.value;
        	}
          
        } ,		       
        changed: function(event, ui) { 
			   if (ui.item==null ||ui.item.value!="" || ui.item.value!="null"){
				   pedidoAdicional.cajas = ui.item.value;
			   }	 
		   }
    });
	$("#p60_cmb_tratamiento").combobox({
		selected: function(event, ui) {
			if ( ui.item.value!="" && ui.item.value!="null"  ) {
				pedidoAdicional.tratamiento = ui.item.value;
				if (pedidoAdicional.tratamiento=="A"){
					$("#p60_cmb_tratamiento").combobox('autocomplete',"Añadir");
					$("#p60_cmb_tratamiento").combobox('comboautocomplete',"Añadir         - Añade cantidad a transmisión");
				}else if(pedidoAdicional.tratamiento == "S"){
					$("#p60_cmb_tratamiento").combobox('autocomplete',"Sustituir");
					$("#p60_cmb_tratamiento").combobox('comboautocomplete',"Sustituir      - Machaca la cantidad transmitida por las del encargo");
				}else{
					$("#p60_cmb_tratamiento").combobox('autocomplete',"Fija/Mínima");
					$("#p60_cmb_tratamiento").combobox('comboautocomplete',"Fija/Mínima - Aplica la mayor cantidad entre lo transmitido y las unidades del encargo. Garantiza al menos la cantidad del encargo");
				}
				$("#p60_cmb_tratamiento").val(pedidoAdicional.tratamiento);
				
			}

		} ,		       
		changed: function(event, ui) { 
			if (ui.item!=null && ui.item.value!="" && ui.item.value!="null"){
				if (null != pedidoAdicional){
					pedidoAdicional.tratamiento = ui.item.value;
					$("#p60_cmb_tratamiento").combobox('autocomplete',p60_cmb_tratamiento.tratamiento);
				}	   
			}	 
		}
	});
	
	//Inicializamos el popup de modificación.
		$( "#p60_AreaModificacion" ).dialog({
	        autoOpen: false,
	        height: 600,
	        width: 980,
	        modal: true,
	        resizable: false,
	        buttons:[{
	            text: "Guardar",
	            click: function() {
	            		//p60ControlesCierrePopUp($(this));
	            		p60GuardarPedido();
	                   }
	        },{
	            text: "Volver",
	            click: function() {
	            	p60ControlesCierrePopUp($(this));
	                   }
	        }
	        ],
			open: function() {
				$(".p60_bloque3AreaDatosCampos").css("margin-top", "14px;");
				
				if($("#p60_fld_tipoPedidoAdicional").val()!=p60TipoEncargo){//Sólo se recarga la ayuda si no es encargo
					reloadAyuda1();	
				}
				$('.ui-dialog-titlebar-close').on('mousedown', function(){
					p60ControlesCierrePopUpAspa();
				});
			}
	    });
	
	$("#p60_cmb_excluir").combobox('autocomplete',"");
	$("#p60_cmb_excluir").combobox('comboautocomplete',null);
	
	$("#p60_cmb_cajas").combobox('autocomplete',"");
	$("#p60_cmb_cajas").combobox('comboautocomplete',null);
}

function p60ControlesCierrePopUp(elem){
	  p60LimpiarCalendarios();
	  limpiarAyudaP53();
	  elem.dialog('close');
}

function p60ControlesCierrePopUpAspa(){
	p60LimpiarCalendarios();
	limpiarAyudaP53();
}

function p60LimpiarCalendarios(){
	$("#p60_fechaInicioDatePicker").datepicker("destroy");
	$("#p60_fechaFinDatePicker").datepicker("destroy");
}

function p60CrearCalendarios(){
	
	//Se obtiene el valor de recargarParametrosCalendario para replicarlo para todos los calendarios
	var recargarParametrosCalendario = $("#recargarParametrosCalendario").val();
	$.datepicker.setDefaults($.datepicker.regional['esDiasServicio']);
	$("#recargarParametrosCalendario").val(recargarParametrosCalendario);
	$("#p60_fechaInicioDatePicker").datepicker( "option", $.datepicker.regional[ 'esDiasServicio' ] );
	$("#p60_fechaInicioDatePicker").datepicker({
	    onSelect: function(dateText, inst) {
	    	pedidoAdicional.fechaIni = $("#p60_fechaInicioDatePicker").datepicker("getDate");
	    	if($("#p60_div_fechaFin").is(':visible')){
		    	if (pedidoAdicional.fechaFin.getTime() >= pedidoAdicional.fechaIni.getTime()){
			    	if (pedidoAdicional.tipoPedido == 1){
			    		fechaCantidad1();
			    	} else {
			    		//pedidoAdicional.calcularFechaIniFin = 'F';
			    		pedidoAdicional.calcularFechaIniFin = '';
			    		p60LoadDates();
			    	}
		    	}else{
		    		createAlert(endDateGrtrStartDate, "ERROR");
		    	}
	    	}else{
		    	if (pedidoAdicional.tipoPedido == 1){
		    		fechaCantidad1();
		    	} else {
		    		//pedidoAdicional.calcularFechaIniFin = 'F';
		    		pedidoAdicional.calcularFechaIniFin = '';
		    		p60LoadDates();
		    	}
	    	}
	    }
	});

	$("#recargarParametrosCalendario").val(recargarParametrosCalendario);
	$("#p60_fechaFinDatePicker").datepicker( "option", $.datepicker.regional[ 'esDiasServicio' ] );
	$("#p60_fechaFinDatePicker").datepicker({
	    onSelect: function(dateText, inst) {
	    	pedidoAdicional.fechaFin = $("#p60_fechaFinDatePicker").datepicker("getDate");
	    	if (modificable){
	    		if (pedidoAdicional.fechaFin.getTime() >= pedidoAdicional.fechaIni.getTime()){
	    			//pedidoAdicional.calcularFechaIniFin = 'I';
	    			pedidoAdicional.calcularFechaIniFin = '';
	    			p60LoadDates();
	    		} else {
	    			createAlert(endDateGrtrStartDate, "ERROR");
	    		}
	    	}
	     }
	    	
	});
	
}

function fechaCantidad1(){
	var fecha = $( "#p60_fechaInicioDatePicker" ).datepicker("getDate");
	var fechaformateada = $.datepicker.formatDate("D dd", new Date(fecha),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});
		
		$("#p60_lbl_cantidad1").text(fechaformateada);
}

function p60DefinePedidoAdicionalE(data){
	pedidoAdicional = new PedidoAdicionalCompleto(data.clasePedido, data.codCentro, data.codArticulo);
	pedidoAdicional.calcularFechaIniFin = '';
	pedidoAdicional.identificador = data.identificador;
	if (data.excluir){
		pedidoAdicional.excluir = "S";
	}else{
		pedidoAdicional.excluir = "N";
	}
	
	if (data.cajas){
		pedidoAdicional.cajas = "S";
	}else{
		pedidoAdicional.cajas = "N";
	}
	pedidoAdicional.descArt = data.descriptionArt;
	var fecIni = $.datepicker.parseDate( "ddmmyy", data.fecEntrega );
	pedidoAdicional.fechaIni = fecIni;
	pedidoAdicional.uniCajas = data.uniCajaServ;
	pedidoAdicional.tratamiento = data.tratamiento;
	pedidoAdicional.cantidad1 = data.unidadesPedidas;
	pedidoAdicional.tipoAprovisionamiento = data.tipoAprovisionamiento;
	pedidoAdicional.identificadorSIA = data.identificadorSIA;
}

function p60DefinePedidoAdicionalM(data){

	// MISUMI-352
	obtenerSiEsCentroReferenciaVegalsa(data.codArticulo);
	
	pedidoAdicional = new PedidoAdicionalCompleto(data.clasePedido, data.codCentro, data.codArticulo);
	pedidoAdicional.calcularFechaIniFin = '';
	pedidoAdicional.identificador = data.identificador;
	pedidoAdicional.referenciaNueva = data.referenciaNueva;
	pedidoAdicional.bloqueoPilada = data.bloqueoPilada;
	pedidoAdicional.noGestionaPbl = data.noGestionaPbl;
	pedidoAdicional.identificadorSIA = data.identificadorSIA;
	pedidoAdicional.identificadorVegalsa = data.identificadorVegalsa;
	
	//Inicio MISUMI-409
	pedidoAdicional.diasMaximos = data.diasMaximos;
	pedidoAdicional.cantidadMaxima = data.cantidadMaxima;
	//FIN MISUMI-409

	//Si es un pedido no gestionado por PBL excluir va fijo a S
	if (pedidoAdicional.noGestionaPbl != null && "S"==pedidoAdicional.noGestionaPbl && (pedidoAdicional.identificadorSIA == null || pedidoAdicional.identificadorSIA == '')){
		pedidoAdicional.excluir = "S";
		if (data.cajas)
		{
			pedidoAdicional.cajas = "S";
		}
		else
		{
			pedidoAdicional.cajas = "N";
		}
		pedidoAdicional.descArt = data.descriptionArt;
		var fecIni = $.datepicker.parseDate( "ddmmyy", data.fechaInicio );
		pedidoAdicional.fechaIni = fecIni;
		var fecFin = $.datepicker.parseDate( "ddmmyy", data.fechaFin );
		pedidoAdicional.fechaFin = fecFin;
		pedidoAdicional.uniCajas = data.uniCajaServ;
		pedidoAdicional.cantidad1 = data.cantidad1;
		if (null != data.fecha2){
			var fec2 = $.datepicker.parseDate( "ddmmyy", data.fecha2 );
			pedidoAdicional.fecha2 = fec2
			pedidoAdicional.cantidad2 = data.cantidad2;
		}
		if (null != data.fecha3){
			var fec3 = $.datepicker.parseDate( "ddmmyy", data.fecha3 );
			pedidoAdicional.fecha3 = fec3;
			pedidoAdicional.cantidad3 = data.cantidad3;
		}
		if (null != data.fecha4){
			var fec4 = $.datepicker.parseDate( "ddmmyy", data.fecha4 );
			pedidoAdicional.fecha4 = fec4;
			pedidoAdicional.cantidad4 = data.cantidad4;
		}
		if (null != data.fecha5){
			var fec5 = $.datepicker.parseDate( "ddmmyy", data.fecha5 );
			pedidoAdicional.fecha5 = fec5;
			pedidoAdicional.cantidad5 = data.cantidad5;
		}
		if (null != data.fechaPilada){
			var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.fechaPilada );
			pedidoAdicional.fechaPilada = fechaPilada;
		}
		
		pedidoAdicional.capacidadMaxima = data.capMax;
		pedidoAdicional.implantacionMinima = data.capMin;
		$("#p60_fld_cantidad1_ant").val(data.capMin); // MISUMI-409.
		
		pedidoAdicional.identificador = data.identificador;
		pedidoAdicional.tipoAprovisionamiento = data.tipoAprovisionamiento;
		if (null != data.cantMax && data.cantMax != ""){
			cantidadMaxima = data.cantMax;
		}
		if (null != data.cantMin && data.cantMin != ""){
			cantidadMinima = data.cantMin;
		}
	}else{
		if (data.excluir)
		{
			pedidoAdicional.excluir = "S";
		}
		else
		{
			pedidoAdicional.excluir = "N";
		}
		if (data.cajas)
		{
			pedidoAdicional.cajas = "S";
		}
		else
		{
			pedidoAdicional.cajas = "N";
		}
		pedidoAdicional.descArt = data.descriptionArt;
		var fecIni = $.datepicker.parseDate( "ddmmyy", data.fechaInicio );
		pedidoAdicional.fechaIni = fecIni;
		var fecFin = $.datepicker.parseDate( "ddmmyy", data.fechaFin );
		pedidoAdicional.fechaFin = fecFin;
		pedidoAdicional.uniCajas = data.uniCajaServ;
		pedidoAdicional.cantidad1 = data.cantidad1;
		if (null != data.fecha2){
			var fec2 = $.datepicker.parseDate( "ddmmyy", data.fecha2 );
			pedidoAdicional.fecha2 = fec2
			pedidoAdicional.cantidad2 = data.cantidad2;
		}
		if (null != data.fecha3){
			var fec3 = $.datepicker.parseDate( "ddmmyy", data.fecha3 );
			pedidoAdicional.fecha3 = fec3;
			pedidoAdicional.cantidad3 = data.cantidad3;
		}
		if (null != data.fechaPilada){
			var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.fechaPilada );
			pedidoAdicional.fechaPilada = fechaPilada;
		}
		
		pedidoAdicional.capacidadMaxima = data.capMax;
		pedidoAdicional.implantacionMinima = data.capMin;
		$("#p60_fld_cantidad1_ant").val(data.capMin); // MISUMI-409.

		if (data.tipoPedido == 'E'){
			pedidoAdicional.frescoPuro = true;
			if (data.modificableIndiv != null && (data.modificableIndiv.indexOf('S') != -1 ||
					data.modificableIndiv.indexOf('P') != -1)){
			
			
				modificable = true;
			}
			if(pedidoAdicional.referenciaNueva){
				$("#p60_fechaFinDatePicker").datepicker("disable");
				$("#p60_excluir").hide();
				$("#p60_leyendaReferenciaNueva").show();
				if (pedidoAdicional.bloqueoPilada){
					$("#p60_ReferenciaNuevaBloqueo").show();
	
				} else {
					$("#p60_ReferenciaNueva").show();
				}
				$("#p60_rellenoExcluir").show();
			}else{
				$("#p60_fechaFinDatePicker").datepicker("enable");
				if(data.showExcluirAndCajas){
					$("#p60_excluir").show();
				}else{
					$("#p60_excluir").hide();
					$("#p60_bloque3AreaDatosCampos1_1").hide();
				}
				$("#p60_leyendaReferenciaNueva").hide();
				$("#p60_rellenoExcluir").hide();
				$("#p60_ReferenciaNueva").hide();
				$("#p60_ReferenciaNuevaBloqueo").hide();
			}
		} else {
			
			$("#p60_fld_cantidad1").change(function(event) {
				p60ImplantacionInicial();
			});
			$("#p60_fld_cantidad2").change(function(event) {
				pedidoAdicional.implantacionMinima = $(this).val();
			});
			pedidoAdicional.frescoPuro = false;
			modificable = true;
	
				if(pedidoAdicional.referenciaNueva){
					$("#p60_fechaFinDatePicker").datepicker("disable");
					$("#p60_leyendaReferenciaNueva").show();
					if (pedidoAdicional.bloqueoPilada){
						$("#p60_ReferenciaNuevaBloqueo").show();
	
					} else {
						$("#p60_ReferenciaNueva").show();
					}
					$("#p60_rellenoExcluir").show();
				}else{
					$("#p60_fechaFinDatePicker").datepicker("enable");
					$("#p60_leyendaReferenciaNueva").hide();
					$("#p60_rellenoExcluir").hide();
					$("#p60_ReferenciaNueva").hide();
					$("#p60_ReferenciaNuevaBloqueo").hide();
				}
	
			if ((pedidoAdicional.fechaIni != null)&&(pedidoAdicional.fechaFin != null)&&(daysDiff() > 7) && !esCentroReferenciaVegalsa){
				$("#p60_fld_cantidad2").removeAttr("disabled");
			} else {
				$("#p60_fld_cantidad2").attr("disabled", "disabled");
			}
		}
		
		pedidoAdicional.identificador = data.identificador;
		pedidoAdicional.tipoAprovisionamiento = data.tipoAprovisionamiento;
		if (data.clasePedido == "7"){
			if (null != data.fecha4){
				var fec4 = $.datepicker.parseDate( "ddmmyy", data.fecha4 );
				pedidoAdicional.fecha4 = fec4;
				pedidoAdicional.cantidad4 = data.cantidad4;
			}
			
			if (null != data.fecha5){
				var fec5 = $.datepicker.parseDate( "ddmmyy", data.fecha5 );
				pedidoAdicional.fecha5 = fec5;
				pedidoAdicional.cantidad5 = data.cantidad5;
			}
			
			if (null != data.cantMax && data.cantMax != ""){
				cantidadMaxima = data.cantMax;
			}
			if (null != data.cantMin && data.cantMin != ""){
				cantidadMinima = data.cantMin;
			}
		}
	}
	
	// Inicio MISUMI-409
	if (pedidoAdicional.diasMaximos!=null && pedidoAdicional.diasMaximos>=0){
		$("#p60_fechaInicioDatePicker").datepicker("option", "maxDate", pedidoAdicional.diasMaximos);
		$("#p60_fechaFinDatePicker").datepicker("option", "maxDate", pedidoAdicional.diasMaximos);
	}
	// FIN MISUMI-409

}

function p60DefinePedidoAdicionalMO(data){
	
	// MISUMI-352
	obtenerSiEsCentroReferenciaVegalsa(data.codArticulo);
	
	pedidoAdicional = new PedidoAdicionalCompleto(data.clasePedido, data.codCentro, data.codArticulo);
	pedidoAdicional.calcularFechaIniFin = '';
	pedidoAdicional.identificador = data.identificador;
	pedidoAdicional.bloqueoPilada = data.bloqueoPilada;
	pedidoAdicional.noGestionaPbl = data.noGestionaPbl;
	pedidoAdicional.identificadorSIA = data.identificadorSIA;
	pedidoAdicional.identificadorVegalsa = data.identificadorVegalsa;
	
	pedidoAdicional.diasMaximos = data.diasMaximos;
	pedidoAdicional.cantidadMaxima = data.cantidadMaxima;

	//Si es un pedido no gestionado por PBL excluir va fijo a S
	if (pedidoAdicional.noGestionaPbl != null && "S"==pedidoAdicional.noGestionaPbl && (pedidoAdicional.identificadorSIA == null || pedidoAdicional.identificadorSIA == '')){
		pedidoAdicional.excluir = "S";
		if (data.cajas)
		{
			pedidoAdicional.cajas = "S";
		}
		else
		{
			pedidoAdicional.cajas = "N";
		}
		pedidoAdicional.descArt = data.descriptionArt;
		var fecIni = $.datepicker.parseDate( "ddmmyy", data.fechaInicio );
		pedidoAdicional.fechaIni = fecIni;
		var fecFin = $.datepicker.parseDate( "ddmmyy", data.fechaFin );
		pedidoAdicional.fechaFin = fecFin;
		pedidoAdicional.uniCajas = data.uniCajaServ;
		pedidoAdicional.cantidad1 = data.cantidad1;
		if (null != data.fecha2){
			var fec2 = $.datepicker.parseDate( "ddmmyy", data.fecha2 );
			pedidoAdicional.fecha2 = fec2
			pedidoAdicional.cantidad2 = data.cantidad2;
		}
		if (null != data.fecha3){
			var fec3 = $.datepicker.parseDate( "ddmmyy", data.fecha3 );
			pedidoAdicional.fecha3 = fec3;
			pedidoAdicional.cantidad3 = data.cantidad3;
		}
		if (null != data.fecha4){
			var fec4 = $.datepicker.parseDate( "ddmmyy", data.fecha4 );
			pedidoAdicional.fecha4 = fec4;
			pedidoAdicional.cantidad4 = data.cantidad4;
		}
		if (null != data.fecha5){
			var fec5 = $.datepicker.parseDate( "ddmmyy", data.fecha5 );
			pedidoAdicional.fecha5 = fec5;
			pedidoAdicional.cantidad5 = data.cantidad5;
		}
		if (null != data.fechaPilada){
			var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.fechaPilada );
			pedidoAdicional.fechaPilada = fechaPilada;
		}
		pedidoAdicional.capacidadMaxima = data.capMax;
		pedidoAdicional.implantacionMinima = data.capMin;
		pedidoAdicional.identificador = data.identificador;
		pedidoAdicional.tipoAprovisionamiento = data.tipoAprovisionamiento;
		if (null != data.cantMax && data.cantMax != ""){
			cantidadMaxima = data.cantMax;
		}
		if (null != data.cantMin && data.cantMin != ""){
			cantidadMinima = data.cantMin;
		}
	}else{
		if (data.excluir)
		{
			pedidoAdicional.excluir = "S";
		}
		else
		{
			pedidoAdicional.excluir = "N";
		}
		if (data.cajas)
		{
			pedidoAdicional.cajas = "S";
		}
		else
		{
			pedidoAdicional.cajas = "N";
		}
		pedidoAdicional.descArt = data.descriptionArt;
		var fecIni = $.datepicker.parseDate( "ddmmyy", data.fechaInicio );
		pedidoAdicional.oferta = data.oferta;
		pedidoAdicional.fechaIni = fecIni;
		var fecFin = $.datepicker.parseDate( "ddmmyy", data.fechaFin );
		pedidoAdicional.fechaFin = fecFin;
		pedidoAdicional.uniCajas = data.uniCajaServ;
		pedidoAdicional.cantidad1 = data.cantidad1;
		if (null != data.fecha2){
			var fec2 = $.datepicker.parseDate( "ddmmyy", data.fecha2 );
			pedidoAdicional.fecha2 = fec2
			pedidoAdicional.cantidad2 = data.cantidad2;
		}
		if (null != data.fecha3){
			var fec3 = $.datepicker.parseDate( "ddmmyy", data.fecha3 );
			pedidoAdicional.fecha3 = fec3;
			pedidoAdicional.cantidad3 = data.cantidad3;
		}
		if (null != data.fechaPilada){
			var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.fechaPilada );
			pedidoAdicional.fechaPilada = fechaPilada;
		}
		
		
		pedidoAdicional.capacidadMaxima = data.capMax;
		pedidoAdicional.implantacionMinima = data.capMin;
		if (data.tipoPedido == 'E'){
			pedidoAdicional.frescoPuro = true;
			if (data.modificableIndiv != null && (data.modificableIndiv.indexOf('S') != -1 ||
					data.modificableIndiv.indexOf('P') != -1)){
				modificable = true;
			}
		} else {
			$("#p60_fld_cantidad1").change(function(event) {
				p60ImplantacionInicial();
			});
			$("#p60_fld_cantidad2").change(function(event) {
				pedidoAdicional.implantacionMinima = $(this).val();
			});
			pedidoAdicional.frescoPuro = false;
			modificable = true;
			if ((pedidoAdicional.fechaIni != null)&&(pedidoAdicional.fechaFin != null)&&(daysDiff() > 7) && !esCentroReferenciaVegalsa){
				$("#p60_fld_cantidad2").removeAttr("disabled");
			} else {
				$("#p60_fld_cantidad2").attr("disabled", "disabled");
			}
		}
		pedidoAdicional.identificador = data.identificador;
		pedidoAdicional.tipoAprovisionamiento = data.tipoAprovisionamiento;
		if (data.clasePedido == "8"){
			if (null != data.fecha4){
				var fec4 = $.datepicker.parseDate( "ddmmyy", data.fecha4 );
				pedidoAdicional.fecha4 = fec4;
				pedidoAdicional.cantidad4 = data.cantidad4;
			}
			
			if (null != data.fecha5){
				var fec5 = $.datepicker.parseDate( "ddmmyy", data.fecha5 );
				pedidoAdicional.fecha5 = fec5;
				pedidoAdicional.cantidad5 = data.cantidad5;
			}
			
			if (null != data.cantMax && data.cantMax != ""){
				cantidadMaxima = data.cantMax;
			}
			if (null != data.cantMin && data.cantMin != ""){
				cantidadMinima = data.cantMin;
			}
		}
	}	
	if (pedidoAdicional.diasMaximos!=null && pedidoAdicional.diasMaximos>=0){
		$("#p60_fechaInicioDatePicker").datepicker("option", "maxDate", pedidoAdicional.diasMaximos);
		$("#p60_fechaFinDatePicker").datepicker("option", "maxDate", pedidoAdicional.diasMaximos);
	}
}

function p60LoadDates(){
	var objJson = $.toJSON(pedidoAdicional.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './modificarPedidoAdicional/loadDates.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {	
			pedidoAdicional.fechaBloqueoEncargo = data.fechaBloqueoEncargo;
			pedidoAdicional.fechaBloqueoEncargoPilada = data.fechaBloqueoEncargoPilada;
			if(data.tipoPedido != 1 && ('S' == data.fechaBloqueoEncargo || 'S' == data.fechaBloqueoEncargoPilada)){
        		$("#p03_btn_aceptar").unbind("click");
				$("#p03_btn_aceptar").bind("click", function(e) {

					if (null != data.strFechaIni){
						var fecIni = $.datepicker.parseDate( "ddmmyy", data.strFechaIni );
						pedidoAdicional.fechaIni = fecIni;
					}else{
						pedidoAdicional.fechaIni = null;
					}
					
					if (null != data.strFechaFin){
						var fecFin = $.datepicker.parseDate( "ddmmyy", data.strFechaFin );
						pedidoAdicional.fechaFin = fecFin;
					}else{
						pedidoAdicional.fechaFin = null;
					}
					
					if(pedidoAdicional.frescoPuro){

						if (null != data.strFecha2){
							var fecha2 = $.datepicker.parseDate( "ddmmyy", data.strFecha2 );	
							pedidoAdicional.fecha2 = fecha2;
						}else{
							pedidoAdicional.fecha2 = null;
						}
						
						if (null != data.strFecha3){
							var fecha3 = $.datepicker.parseDate( "ddmmyy", data.strFecha3 );
							pedidoAdicional.fecha3 = fecha3;
						}else{
							pedidoAdicional.fecha3 = null;
						}
						
						if (null != data.strFechaPilada){
							var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.strFechaPilada );
							pedidoAdicional.fechaPilada = fechaPilada;
						}else{
							pedidoAdicional.fechaPilada = null;
						}
						//Establecer fecha mínima para la fecha fin
						//if (p60ModificableIndiv != null && (p60ModificableIndiv == 'S' || p60ModificableIndiv == 'SS' || p60ModificableIndiv == 'SSS')){
							//$( "#p60_fechaFinDatePicker" ).datepicker( "option", "minDate", new Date(data.strFechaIni.substring(4), data.strFechaIni.substring(2,4) - 1, data.strFechaIni.substring(0,2)) );
						//}
					} else {
						if($("#p60_fld_cantidad2").attr("disabled") == undefined){
							impFinalModif = true;
						} 
						p60ImplantacionInicial();
					}
					p60PopulateCantidades();
					
					//Restaura el valor del recálculo de fecha
					pedidoAdicional.calcularFechaIniFin = '';
					
					// Inicio MISUMI-409.
					pedidoAdicional.diasMaximos = data.diasMaximos;
					pedidoAdicional.cantidadMaxima = data.cantidadMaxima;
					// FIN MISUMI-409.

				});
				
				if('S' == data.fechaBloqueoEncargo && 'S' == data.fechaBloqueoEncargoPilada){
					createAlert(fechaSelBloqueosEncargosMontajes, "ERROR");
				}else if('S' == data.fechaBloqueoEncargo ){
					createAlert(fechaSelBloqueosEncargos, "ERROR");	
				}else{//'S' == data.fechaBloqueoEncargoPilada
					createAlert(fechaSelBloqueosMontajes, "ERROR");	
				}
        		
        		$("#p03_btn_aceptar").focus();

			}else{
				if (null != data.strFechaIni){
					var fecIni = $.datepicker.parseDate( "ddmmyy", data.strFechaIni );
					pedidoAdicional.fechaIni = fecIni;
				}else{
					pedidoAdicional.fechaIni = null;
				}
				
				if (null != data.strFechaFin){
					var fecFin = $.datepicker.parseDate( "ddmmyy", data.strFechaFin );
					pedidoAdicional.fechaFin = fecFin;
				}else{
					pedidoAdicional.fechaFin = null;
				}
				
				if(pedidoAdicional.frescoPuro){
				
					if (null != data.strFecha2){
						var fecha2 = $.datepicker.parseDate( "ddmmyy", data.strFecha2 );	
						pedidoAdicional.fecha2 = fecha2;
					}else{
						pedidoAdicional.fecha2 = null;
					}
					
					if (null != data.strFecha3){
						var fecha3 = $.datepicker.parseDate( "ddmmyy", data.strFecha3 );
						pedidoAdicional.fecha3 = fecha3;
					}else{
						pedidoAdicional.fecha3 = null;
					}
					
					if (null != data.strFechaPilada){
						var fechaPilada = $.datepicker.parseDate( "ddmmyy", data.strFechaPilada );
						pedidoAdicional.fechaPilada = fechaPilada;
					}else{
						pedidoAdicional.fechaPilada = null;
					}
					//Establecer fecha mínima para la fecha fin
					//if (p60ModificableIndiv != null && (p60ModificableIndiv == 'S' || p60ModificableIndiv == 'SS' || p60ModificableIndiv == 'SSS')){
						//$( "#p60_fechaFinDatePicker" ).datepicker( "option", "minDate", new Date(data.strFechaIni.substring(4), data.strFechaIni.substring(2,4) - 1, data.strFechaIni.substring(0,2)) );
					//}
				} else {
					if($("#p60_fld_cantidad2").attr("disabled") == undefined){
						impFinalModif = true;
					} 
					p60ImplantacionInicial();
				}
				p60PopulateCantidades();
				
				//Restaura el valor del recálculo de fecha
				pedidoAdicional.calcularFechaIniFin = '';
				
				// Inicio MISUMI-409.
				pedidoAdicional.diasMaximos = data.diasMaximos;
				pedidoAdicional.cantidadMaxima = data.cantidadMaxima;
				// FIN MISUMI-409.

			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});	
}

function p60PopulateCantidades(){
	if (pedidoAdicional.frescoPuro){
		fechaCantidad1();
		if (pedidoAdicional.fecha2 != null){
		 	var fecha2 = $.datepicker.formatDate("D dd", new Date(pedidoAdicional.fecha2),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
			
			$("#p60_lbl_cantidad2").text(fecha2);
			
			$("#p60_bloque3AreaDatosCampos2_2").show();  //$("#p60_div_cantidad2").show();
			$("#p60_fld_cantidad2").attr("style", "display:inherit");
			if (p60ModificableIndiv == null || p60ModificableIndiv.length < 2 || p60ModificableIndiv.charAt(1) == "S" && !esCentroReferenciaVegalsa){
				$("#p60_fld_cantidad2").removeAttr("disabled");
			}
		}else{
			$("#p60_fld_cantidad2").attr("style", "display:none");
			$("#p60_bloque3AreaDatosCampos2_2").hide();  //$("#p60_div_cantidad2").hide();
		}
		if (pedidoAdicional.fecha3 != null){
			var fecha3 = $.datepicker.formatDate("D dd", new Date(pedidoAdicional.fecha3),{
				dayNamesShort: $.datepicker.regional[ "es" ].dayNamesMin,
				dayNames: $.datepicker.regional[ "es" ].dayNames,
				monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
				monthNames: $.datepicker.regional[ "es" ].monthNames
				});
			
			$("#p60_lbl_cantidad3").text(fecha3);
			
			$("#p60_bloque3AreaDatosCampos2_3").show();  //$("#p60_div_cantidad3").show();
			$("#p60_fld_cantidad3").attr("style", "display:inherit");
			if (p60ModificableIndiv == null || p60ModificableIndiv.length < 3 || p60ModificableIndiv.charAt(2) == "S"){
				$("#p60_fld_cantidad3").removeAttr("disabled");
			}
			$("#p60_div_cantidad3").show();
		} else {
			$("#p60_div_cantidad3").hide();
			$("#p60_fld_cantidad3").attr("style", "display:none");
			$("#p60_bloque3AreaDatosCampos2_3").hide();  //$("#p60_div_cantidad3").hide();
		}
		if (pedidoAdicional.bloqueoPilada && (pedidoAdicional.tipoPedido == "2" || pedidoAdicional.tipoPedido == "3") && null != pedidoAdicional.fechaPilada){
			$("#p03_btn_aceptar").unbind("click");
			$("#p03_btn_aceptar").bind("click", function(e) {
				if (!$("#p60_div_cantidad3").is(':hidden')){
					$("#p60_fld_cantidad3").focus();
					$("#p60_fld_cantidad3").select();
				} 
			});
			createAlert(referenceNoMaintenance,"ERROR");
			$("#p03_btn_aceptar").focus();
			return false;
		}
	} else {
		$("#p60_fld_cantidad1").val(pedidoAdicional.capacidadMaxima);
		if ((pedidoAdicional.fechaIni != null)&&(pedidoAdicional.fechaFin != null)&&(daysDiff() > 7) && !esCentroReferenciaVegalsa){

			$("#p60_fld_cantidad2").removeAttr("disabled");
			$("#p60_fld_cantidad2").val(pedidoAdicional.implantacionMinima);
		} else {
			$("#p60_fld_cantidad2").attr("disabled", "disabled");
		}
	}
}


function p60ImplantacionInicial(){

	console.log("p60_fld_cantidad1: "+$("#p60_fld_cantidad1").val());
	if ($("#p60_fld_cantidad1").val() == ""){
		pedidoAdicional.capacidadMaxima = null;
	} else {
		pedidoAdicional.capacidadMaxima = $("#p60_fld_cantidad1").val();
	}
	
	// Inicio MISUMI-409
	if (pedidoAdicional.capacidadMaxima != null && pedidoAdicional.cantidadMaxima != null && pedidoAdicional.cantidadMaxima > 0
		&& pedidoAdicional.capacidadMaxima > pedidoAdicional.cantidadMaxima){
		let cantidadMaximaVegalsaNuevo = cantidadMaximaVegalsa+pedidoAdicional.cantidadMaxima;
		createAlert(cantidadMaximaVegalsaNuevo, "ERROR");
		$("#p60_fld_cantidad1").val($("#p60_fld_cantidad1_ant").val());
	}else{
	// FIN MISUMI-409
		if (null != pedidoAdicional.capacidadMaxima){
			if (daysDiff() > 7 && !esCentroReferenciaVegalsa){
				if (!impFinalModif){
					pedidoAdicional.implantacionMinima = Math.ceil(pedidoAdicional.capacidadMaxima * 0.2);
				}

			} else {
				pedidoAdicional.implantacionMinima = pedidoAdicional.capacidadMaxima;
			}
		} else {
			pedidoAdicional.implantacionMinima = pedidoAdicional.capacidadMaxima;
		}
		$("#p60_fld_cantidad2").val(pedidoAdicional.implantacionMinima);
		impFinalModif = false;
		
		$("#p60_fld_cantidad1_ant").val($("#p60_fld_cantidad1").val()); // MISUMI-409.
	}
	
}

function p60GuardarPedido(){
	if (pedidoAdicional.noGestionaPbl != null && "S"==pedidoAdicional.noGestionaPbl){
		pedidoAdicional.cantidad1 = $("#p60_fld_cantidad1").val().replace(',','.');
		pedidoAdicional.cantidad2 = $("#p60_fld_cantidad2").val().replace(',','.');
		pedidoAdicional.cantidad3 = $("#p60_fld_cantidad3").val().replace(',','.');
		pedidoAdicional.cantidad4 = $("#p60_fld_cantidad4").val().replace(',','.');
		pedidoAdicional.cantidad5 = $("#p60_fld_cantidad5").val().replace(',','.');
		p60ModifyPedido();
	}else{
		if (p60ValidarPedido()){
			if (pedidoAdicional.frescoPuro || pedidoAdicional.tipoPedido == 1){
				pedidoAdicional.cantidad1 = $("#p60_fld_cantidad1").val().replace(',','.');
				if (pedidoAdicional.fecha2 != null){
					pedidoAdicional.cantidad2 = $("#p60_fld_cantidad2").val().replace(',','.');
				}
				if (pedidoAdicional.fecha3 != null){
					pedidoAdicional.cantidad3 = $("#p60_fld_cantidad3").val().replace(',','.');
				}
				if (pedidoAdicional.fecha4 != null){
					pedidoAdicional.cantidad4 = $("#p60_fld_cantidad4").val().replace(',','.');
				}
				if (pedidoAdicional.fecha5 != null){
					pedidoAdicional.cantidad5 = $("#p60_fld_cantidad5").val().replace(',','.');
				}
			} else {
				pedidoAdicional.capacidadMaxima =  $("#p60_fld_cantidad1").val().replace(',','.');
				if ((pedidoAdicional.fechaIni != null)&&(pedidoAdicional.fechaFin != null)&&(daysDiff() > 7)){
					pedidoAdicional.implantacionMinima =  $("#p60_fld_cantidad2").val().replace(',','.');
				} 
			}
			p60ModifyPedido();
		}
	}
}

function p60ValidarPedido(){
	if (modificable){
		if (pedidoAdicional.fechaFin < pedidoAdicional.fechaIni){
    		createAlert(endDateGrtrStartDate, "ERROR");
    		return false;
    	}
	}

	if (pedidoAdicional.frescoPuro || pedidoAdicional.tipoPedido == 1){
		if(validarCantidad($("#p60_fld_cantidad1").val()) == false){
			alertCantidades(invalidCant1, $("#p60_fld_cantidad1"));
			return false;
		} else if (null != pedidoAdicional.fecha2 && validarCantidad($("#p60_fld_cantidad2").val()) == false){
			alertCantidades(invalidCant2, $("#p60_fld_cantidad2"));
			return false;
		} else if (null != pedidoAdicional.fecha3 && validarCantidad($("#p60_fld_cantidad3").val()) == false){
			alertCantidades(invalidCant3, $("#p60_fld_cantidad3"));
			return false;
		} else if (null != pedidoAdicional.fecha4 && validarCantidad($("#p60_fld_cantidad4").val()) == false){
			alertCantidades(invalidCant4, $("#p60_fld_cantidad4"));
			return false;
		} else if (null != pedidoAdicional.fecha5 && validarCantidad($("#p60_fld_cantidad5").val()) == false){
			alertCantidades(invalidCant5, $("#p60_fld_cantidad5"));
			return false;
		}
	} else {
		if(validarCantidadAlim($("#p60_fld_cantidad1").val()) == false || $("#p60_fld_cantidad1").val() == 0 ){
			$("#p03_btn_aceptar").unbind("click");
			$("#p03_btn_aceptar").bind("click", function(e) {
				$("#p60_fld_cantidad1").focus();
				$("#p60_fld_cantidad1").select();
				 });
			createAlert(invalidCapMax, "ERROR");
			$("#p03_btn_aceptar").focus();
			return false;
		}  else if ((pedidoAdicional.fechaIni != null) && (pedidoAdicional.fechaFin != null) && (daysDiff() > 7) && 
				(validarCantidadAlim($("#p60_fld_cantidad2").val()) == false || $("#p60_fld_cantidad2").val() == 0)){
			$("#p03_btn_aceptar").unbind("click");
			$("#p03_btn_aceptar").bind("click", function(e) {
				$("#p60_fld_cantidad2").focus();
				$("#p60_fld_cantidad2").select();
				 });
			createAlert(invalidImpMin, "ERROR");
			$("#p03_btn_aceptar").focus();
			return false;
		} 
		
	}
	if (pedidoAdicional.bloqueoPilada && (pedidoAdicional.tipoPedido == "2" || pedidoAdicional.tipoPedido == "3") && pedidoAdicional.frescoPuro && null != pedidoAdicional.fechaPilada){
		$("#p03_btn_aceptar").unbind("click");
		$("#p03_btn_aceptar").bind("click", function(e) {
			if (!$("#p60_fld_cantidad1").is(':hidden')){
				$("#p60_fld_cantidad1").focus();
				$("#p60_fld_cantidad1").select();
			}
		});
		createAlert(referenceNoMaintenance,"ERROR");
		$("#p03_btn_aceptar").focus();
		return false;
	}
	return true;
}

function daysDiff(){
	var a = pedidoAdicional.fechaFin.getTime(),
      b = pedidoAdicional.fechaIni.getTime(),
       c = 24*60*60*1000,
       diffDays = Math.round(Math.abs((a - b)/(c)));
 	return diffDays;
}

function p60ModifyPedido(){
	var objJson = $.toJSON(pedidoAdicional.preparePedidoAdicionalToJsonObject());
	$.ajax({
		type : 'POST',
		url : './modificarPedidoAdicional/modifyPedido.do',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		//dataType : "json",
		success : function(data) {	
			if (null != data){
				var valorError = data.split('**');
				var codError = valorError[0];
				var descError = valorError[1];
				if (codError == "1")
				{
					createAlert(replaceSpecialCharacters(implInicialError), "ERROR");

				}
				else if (codError == "2")
				{
					createAlert(replaceSpecialCharacters(implFinalError), "ERROR");
				}
				else if (codError == "L2001") //Aviso de piladas
				{
					$("#p03_btn_aceptar").unbind("click");
					$("#p03_btn_aceptar").bind("click", function(e) {
						if (pedidoAdicional.tipoPedido == "1"){
							reloadDataP41Encargos('S','','');
						} else if (pedidoAdicional.tipoPedido == "2" || pedidoAdicional.tipoPedido == "7"){
							reloadDataP42PedidoAdicionalM('S','','');
						} else {
							reloadDataP43PedidoAdicionalMO('S','','');
						}
						  p60LimpiarCalendarios();
						  limpiarAyudaP53();
						 $("#p60_AreaModificacion").dialog( "close" );
					});
					createAlert(replaceSpecialCharacters(descError), "ERROR");
					$("#p03_btn_aceptar").focus();
				}				
				else
				{
					createAlert(replaceSpecialCharacters(descError), "ERROR");
				}
			} else{
				if (pedidoAdicional.tipoPedido == "1"){
					reloadDataP41Encargos('S','','',null,'');
				} else if (pedidoAdicional.tipoPedido == "2" || pedidoAdicional.tipoPedido == "7"){
					reloadDataP42PedidoAdicionalM('S','','',null,'');
				} else {
					reloadDataP43PedidoAdicionalMO('S','','',null,'');
				}
				  p60LimpiarCalendarios();
				  limpiarAyudaP53();
				 $("#p60_AreaModificacion").dialog( "close" );
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);				
        }			
	});	
}

function validarCantidad(cantidad){
	var cantidadPunto = cantidad.replace(',','.');

	if (isNaN(parseFloat(cantidadPunto)) || cantidad.split(",").length > 2){
		return false;
	} else {
		if (cantidadPunto >= parseFloat(cantidadMinima) && cantidadPunto <= parseFloat(cantidadMaxima)){
			return true;
		} else {
			return false;
		}
	}
}

function validarCantidadAlim(cantidad){

	var cantidadPunto = cantidad.replace(',','.');

	if (isNaN(parseFloat(cantidadPunto)) || cantidad.split(",").length > 2){
		return false;
	} else {
		return true;
	}
}

function load60Literales(locale){
	
	var jqxhr = $.getJSON('./misumi/resources/p60ModificarPedidoUnico/p60modificarPedidoUnico_' + locale + '.json',
			function(data) {
											
			})
			.success(function(data) {
				endDateGrtrStartDate = data.endDateGrtrStartDate;
				invalidCant1 = data.invalidCant1;
				invalidCant2 = data.invalidCant2;
				invalidCant3 = data.invalidCant3;
				invalidCant4 = data.invalidCant4;
				invalidCant5 = data.invalidCant5;
				invalidCapMax = data.invalidCapMax;
				invalidImpMin = data.invalidImpMin;
				referenceNoMaintenance = data.referenceNoMaintenance;
				p60TipoEncargo = data.p60TipoEncargo;
				implInicialError = data.implInicialError;
				implFinalError = data.implFinalError;
				literalAyuda1ConOferta = data.literalAyuda1ConOferta;
				literalAyuda1SinOferta = data.literalAyuda1SinOferta;
				p60MensajeFechaHasta = data.p60MensajeFechaHasta;
				fechaSelBloqueosEncargosMontajes = data.fechaSelBloqueosEncargosMontajes;
				fechaSelBloqueosEncargos = data.fechaSelBloqueosEncargos;
				fechaSelBloqueosMontajes = data.fechaSelBloqueosMontajes;
				cantidadMaximaVegalsa = data.cantidadMaximaVegalsa;

			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
         });
}

function alertCantidades(literal, campo){
	literal = literal.replace('{0}',cantidadMinima).replace('{1}',cantidadMaxima);
	$("#p03_btn_aceptar").unbind("click");
	$("#p03_btn_aceptar").bind("click", function(e) {
		campo.focus();
		campo.select();
		 });
	createAlert(literal, "ERROR");
	$("#p03_btn_aceptar").focus();
}

function p60DibujarFechaHasta(fechaHastaDDMMYYYY){
	var fechaFormateada = "";
	if (null != fechaHastaDDMMYYYY && "" != fechaHastaDDMMYYYY){
		var diaFecha = parseInt(fechaHastaDDMMYYYY.substring(0,2),10);
		var mesFecha = parseInt(fechaHastaDDMMYYYY.substring(2,4),10);
		var anyoFecha = parseInt(fechaHastaDDMMYYYY.substring(4),10);
		var fechaCompleta = anyoFecha + "-" + mesFecha + "-" + diaFecha;
		
		fechaFormateada = $.datepicker.formatDate("dd-M-yy", devuelveDate(fechaCompleta),{
			dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
			dayNames: $.datepicker.regional[ "es" ].dayNames,
			monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
			monthNames: $.datepicker.regional[ "es" ].monthNames
			});

		var message = p60MensajeFechaHasta.replace('{0}',fechaFormateada);
		$("#p60_lbl_fechaHasta").html(message);
		$("#p60_fld_fechaHasta").val(fechaHastaDDMMYYYY);
	}else{
		$("#p60_lbl_fechaHasta").html("");
		$("#p60_fld_fechaHasta").val("");
	}
}
