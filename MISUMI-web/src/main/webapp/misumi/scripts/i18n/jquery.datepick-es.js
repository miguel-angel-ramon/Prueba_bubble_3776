/* http://keith-wood.name/datepick.html
   Spanish localisation for jQuery Datepicker.
   Traducido por Vester (xvester@gmail.com). */
(function($) {
	
	//Campos especiales para cálculo de días de servicio
	var cargadosDiasServicio = 'N';
	var calculadoMes = 'N';
	var fechaMax = $("#diasMaximos").val();
//	fechaInicioMax.setDate(fechaInicio.getDate() + servTemp.plazoMaxAdelantarAtrasar);
	 
	$.datepicker.regional['es'] = {
			monthNames: ['ENERO','FEBRERO','MARZO','ABRIL','MAYO','JUNIO',
			'JULIO','AGOSTO','SEPTIEMBRE','OCTUBRE','NOVIEMBRE','DICIEMBRE'],
			monthNamesShort: ['ENE','FEB','MAR','ABR','MAY','JUN',
			'JUL','AGO','SEP','OCT','NOV','DIC'],
			dayNames: ['DOMINGO','LUNES','MARTES','MIERCOLES','JUEVES','VIERNES','SABADO'],
			dayNamesShort: ['D','L','M','X','J','V','S'],
			dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
			dateFormat: 'dd/mm/yy', firstDay: 1,
			renderer: $.datepicker.defaultRenderer,
			prevText: '&#x3c;Ant', prevStatus: '',
			prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
			nextText: 'Sig&#x3e;', nextStatus: '',
			nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
			currentText: 'Hoy', currentStatus: '',
			todayText: 'Hoy', todayStatus: '',
			clearText: 'Limpiar', clearStatus: '',
			closeText: 'Cerrar', closeStatus: '',
			yearStatus: '', monthStatus: '',
			weekText: 'Sm', weekStatus: '',
			dayStatus: 'D, M d', defaultStatus: '',
			isRTL: false,
			changeMonth: true,
			changeYear: true,
			yearRange: "-0:+3"
		};
	
	$.datepicker.regional['esMisumi'] = {
			monthNames: ['ENERO','FEBRERO','MARZO','ABRIL','MAYO','JUNIO',
			'JULIO','AGOSTO','SEPTIEMBRE','OCTUBRE','NOVIEMBRE','DICIEMBRE'],
			monthNamesShort: ['ENE','FEB','MAR','ABR','MAY','JUN',
			'JUL','AGO','SEP','OCT','NOV','DIC'],
			dayNames: ['DOMINGO','LUNES','MARTES','MIERCOLES','JUEVES','VIERNES','SABADO'],
			dayNamesShort: ['D','L','M','X','J','V','S'],
			dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
			dateFormat: 'D dd-M yy', firstDay: 1,
			renderer: $.datepicker.defaultRenderer,
			prevText: '&#x3c;Ant', prevStatus: '',
			prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
			nextText: 'Sig&#x3e;', nextStatus: '',
			nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
			currentText: 'Hoy', currentStatus: '',
			todayText: 'Hoy', todayStatus: '',
			clearText: 'Limpiar', clearStatus: '',
			closeText: 'Cerrar', closeStatus: '',
			yearStatus: '', monthStatus: '',
			weekText: 'Sm', weekStatus: '',
			dayStatus: 'D, M d', defaultStatus: '',
			isRTL: false,
			changeMonth: true,
			changeYear: true,
			yearRange: "-0:+3"
		};
	
	$.datepicker.regional['esDiasServicio'] = {
			monthNames: ['ENE','FEB','MAR','ABR','MAY','JUN',
   			'JUL','AGO','SEP','OCT','NOV','DIC'],
			monthNamesShort: ['ENE','FEB','MAR','ABR','MAY','JUN',
			'JUL','AGO','SEP','OCT','NOV','DIC'],
			dayNames: ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado'],
			dayNamesShort: ['D','L','M','X','J','V','S'],
			dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
			dateFormat: 'dd/mm/yy', firstDay: 1,
			renderer: $.datepicker.defaultRenderer,
			prevText: '&#x3c;Ant', prevStatus: '',
			prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
			nextText: 'Sig&#x3e;', nextStatus: '',
			nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
			currentText: 'Hoy', currentStatus: '',
			todayText: 'Hoy', todayStatus: '',
			clearText: 'Limpiar', clearStatus: '',
			closeText: 'Cerrar', closeStatus: '',
			yearStatus: '', monthStatus: '',
			weekText: 'Sm', weekStatus: '',
			dayStatus: 'D, M d', defaultStatus: '',
			isRTL: false,
			beforeShow: cargarDias,
			beforeShowDay: tratarDiasEspeciales,
		    onChangeMonthYear: cambiarMes,
		    changeMonth: true,
		    changeYear: true,
//		    maxDate: '+45D',
//		    maxDate: fechaMax,
//		    maxDate: function(){
//				var date = new Date("2023-07-26");
//				var currentMonth = date.getMonth();
//				var currentDate = date.getDate();
//				var currentYear = date.getFullYear();
//			    $(this).datepicker( "option", "maxDate", new Date(currentYear, currentMonth, currentDate));
//			    $(this).datepicker("setDate","26/07/23");
//		    },
		    yearRange: "-0:+3"
		};
	
		function cargarDias(){
			var $calendario = $( this );
			$calendario.prop( "tratarDiasEspeciales", 'S');
//			$(this).datepicker("option", "maxDate", "+7D");
		}
		
		function cambiarMes( year, month, inst){
			var $calendario = $( this );
			$calendario.prop( "calculadoMes", 'N');
	    } 
		
		function tratarDiasEspeciales(date){
			var $calendario = $( this );
			
			if ($("#recargarParametrosCalendario").val() == 'S'){
				$("#recargarParametrosCalendario").val('N');
				$calendario.prop( "cargadosParametros", 'N');
				$calendario.prop( "tratarDiasEspeciales", 'S');
				$calendario.prop( "calculadoMes", 'N');
			}
			if ($calendario.prop( "cargadosParametros") != 'S'){
				$calendario.prop( "codCentro", $("#codCentroCalendario").val());
				if ($("#codArticuloCalendario").val()== "" || $("#codArticuloCalendario").val()==null || $("#codArticuloCalendario").val()=="null"){
					$calendario.prop( "codArticulo", null);					
				}else{
					$calendario.prop( "codArticulo", $("#codArticuloCalendario").val());	
				}
				if ($("#esFresco").val()== "" || $("#esFresco").val()==null || $("#esFresco").val()=="null"){
					$calendario.prop( "esFresco", null);					
				}else{
					$calendario.prop( "esFresco", $("#esFresco").val());	
				}
				if ($("#uuidCalendario").val()== "" || $("#uuidCalendario").val()==null || $("#uuidCalendario").val()=="null"){
					$calendario.prop( "uuid", null);					
				}else{
					$calendario.prop( "uuid", $("#uuidCalendario").val());	
				}

				if ($("#diasMaximos").val()=="" || $("#diasMaximos").val()==null || $("#diasMaximos").val()=="null"){
					$calendario.prop( "diasMaximos", null);					
				}else{
					$calendario.prop( "diasMaximos", $("#diasMaximos").val());	
				}

				$calendario.prop( "identificador", $("#identificadorCalendario").val());
				$calendario.prop( "identificadorSIA", $("#identificadorSIACalendario").val());
				$calendario.prop( "clasePedido", $("#clasePedidoCalendario").val());
				$calendario.prop( "cargadoDS", $("#cargadoDSCalendario").val());
				$calendario.prop( "cargadosParametros", 'S');
				$calendario.prop( "encargoClienteEspecial", $("#encargoClienteEspecialCalendario").val());
				$calendario.prop( "encargoClientePrimFechaEntrega", $("#encargoClientePrimFechaEntregaCalendario").val());
				$calendario.prop( "encargoClienteUnidadesPedir", $("#encargoClienteUnidadesPedirCalendario").val());
				$calendario.prop( "encargoClienteFechaVentaModificada", $("#encargoClienteFechaVentaModificadaCalendario").val());
			}
			if ($calendario.prop( "tratarDiasEspeciales") == 'S'){
				var fechasControlCompletas = new Array();
				var fechasControl = new Array();
				var tipoFechasControl = new Array();
				var fechaControlada = null;
				var tipoFechaControlada = null;
				var $calendario = $( this );
				if ($calendario.prop( "cargadoDS") != 'S'){
					$calendario.prop( "cargadoDS", "S");
					cargadosDiasServicio = 'S';
					cargarDiasServicio($calendario.prop("codCentro"), $calendario.prop("codArticulo"));
				}

				var dmy_full_current = "";
				var currentDate = $(this).datepicker( "getDate" );
				if (currentDate){
					var dmy_d_current = (currentDate.getDate()); 
					var dmy_m_current = (currentDate.getMonth() + 1);
					var dmy_y_current = (currentDate.getFullYear());
					dmy_d_current = ((dmy_d_current < 10)?"0".concat(dmy_d_current):dmy_d_current);
					dmy_m_current = ((dmy_m_current < 10)?"0".concat(dmy_m_current):dmy_m_current);
					dmy_full_current = "".concat(dmy_d_current,dmy_m_current,dmy_y_current);
				}

				var dmy_d = (date.getDate()); 
				var dmy_m = (date.getMonth() + 1);
				var dmy_y = (date.getFullYear());
				dmy_d = ((dmy_d < 10)?"0".concat(dmy_d):dmy_d);
				dmy_m = ((dmy_m < 10)?"0".concat(dmy_m):dmy_m);
				var dmy_full = "".concat(dmy_d,dmy_m,dmy_y);
				
				var minDate = $.datepicker._getMinMaxDate( $calendario.data('datepicker'), 'min' );
//				$(this).datepicker("setDate","26/07/23");
//				$calendario.data('datepicker')( "option", "maxDate", "+7D" );
//				$( "#datepicker" ).datepicker("setDate","26/07/23");
//				$( "#datepicker" ).datepicker("option", "maxDate", "+7D");
//				$(this).datepicker("option", "maxDate", "+7D");
				
				if ($calendario.prop( "calculadoMes") != 'S'){
					$calendario.prop( "calculadoMes", "S");
					if($calendario.prop("codArticulo")!=null){
						fechasControlCompletas = obtenerDiasServicio($calendario.prop("codCentro"), $calendario.prop("codArticulo"), $calendario.prop("identificador"),$calendario.prop("identificadorSIA"),$calendario.prop("uuid"), $calendario.prop("clasePedido"),$calendario.prop("esFresco"),$calendario.prop("diasMaximos"), dmy_full);
						$calendario.prop( "fechasControl", fechasControlCompletas);
					}
				}else{
					fechasControlCompletas = $calendario.prop( "fechasControl");
				}
				
				//Mirar si hay control de deshabilitados anteriores a una fecha. Esto es para evitar el mindate 
				//que modifica el valor del día mostrado.

				if(disableBeforeDay(date)){
					var controlDate = new Date();
					if($calendario.prop( "dayControl") != null && $calendario.prop( "dayControl")!='undefined'){
						controlDate = $calendario.prop( "dayControl");	
					}
					var dmy_d_control = (controlDate.getDate()); 
					var dmy_m_control = (controlDate.getMonth() + 1);
					var dmy_y_control = (controlDate.getFullYear());
					dmy_d_control = ((dmy_d_control < 10)?"0".concat(dmy_d_control):dmy_d_control);
					dmy_m_control = ((dmy_m_control < 10)?"0".concat(dmy_m_control):dmy_m_control);

					var ymd_full = "".concat(dmy_y,dmy_m,dmy_d);
					var ymd_control = "".concat(dmy_y_control,dmy_m_control,dmy_d_control);
					if (ymd_control > ymd_full){
						if (dmy_full_current == dmy_full){
							return [true,''];
						}else{
							return [false,''];
						}
					}
				}
				
				if (dmy_full_current!=dmy_full && (minDate == null || date >= minDate)){
					//Control de visualización de fechas
					if (fechasControlCompletas != null){
						fechasControl = fechasControlCompletas[0];
						tipoFechasControl = fechasControlCompletas[1];
						var posicionFechaControl = $.inArray(dmy_full, fechasControl);
						if (posicionFechaControl>=0){
							fechaControlada = fechasControl[posicionFechaControl];
							tipoFechaControlada = tipoFechasControl[posicionFechaControl];
						}else{
							tipoFechaControlada = '';
						}
						if (tipoFechaControlada =='F'){//Festivo
							return [false,'dia-festivo'];
						}else if (tipoFechaControlada =='S'){//Servicio
							return [true,'dia-servicio'];
						}else if (tipoFechaControlada =='P'){//Con pedido
							return [false,''];							
						}else if (tipoFechaControlada =='EE'){//Encargo y bloqueo de encargo
							return [false,'dia-bloqueo-e-des'];
						}else if (tipoFechaControlada =='MAEP'){//Montaje adicional y es alimentación y bloqueo de encargo y pilada
							return [false,'dia-bloqueo-ep-des'];
						}else if (tipoFechaControlada =='MFE'){//Montaje adicional y es frescos y bloqueo de encargos
							return [true,'dia-bloqueo-e'];							
						}else if (tipoFechaControlada =='MFEP'){//Montaje adicional y es frescos y bloqueo de encargo y pilada
							return [false,'dia-bloqueo-ep-des'];
						}else if ($calendario.prop("clasePedido") == "EC") {
							if ($calendario.prop("encargoClienteUnidadesPedir") == "") {
								return [false,''];//Para habilitar el calendario hay que meter una cantidad en unidades a pedir
							}
							var strFechaEntrega = $calendario.prop( "encargoClientePrimFechaEntrega");
							var fechaEntrega = new Date(); //Inicializamos la fecha de entraga a hoy
							if ($.isNumeric(strFechaEntrega)){
								fechaEntrega = new Date( Number($calendario.prop( "encargoClientePrimFechaEntrega")) ); //Si existe primeraFechaEntrega entonces actualizamos la fecha de entrega
							}
							var strFechaVentaModificada = $calendario.prop( "encargoClienteFechaVentaModificada");
							var fechaVentaModificada = null;
							if ($.isNumeric(strFechaVentaModificada)){
								fechaVentaModificada = new Date( Number($calendario.prop( "encargoClienteFechaVentaModificada")) );
							}else{
								if (strFechaVentaModificada!=null){
									fechaVentaModificada = devuelveDate(strFechaVentaModificada);
								}
							}
							
							var encargoClienteEspecial = $calendario.prop( "encargoClienteEspecial");
							if (fechaVentaModificada != null && fechaVentaModificada.valueOf()==date.valueOf()) {//Fecha de venta modificada
									return [true,'dia-ec-fecha-venta-modificada'];
							}else if (tipoFechaControlada =='ECFV'){//Es encargo cliente y es fecha de venta
								if (fechaEntrega>date) {//Fecha de venta anterior a primera fecha de entrega
									if (encargoClienteEspecial=='true'){
										return [false,''];//Es encargo cliente especial y fecha de venta anterior a primera fecha de entrega
									}
									else{
										return [true,'dia-ec-fecha-anterior'];//Es encargo cliente normal y fecha de venta anterior a primera fecha de entrega
									}
								}
								else{
									return [true,''];//Es encargo cliente y fecha de venta normal posterior a primera fecha de entrega
								}
							}else if (tipoFechaControlada =='ECFN'){//Es encargo cliente y no es fecha de venta
								return [false,''];
							}else {//Es encargo cliente y no es fecha de venta
								return [false,''];
							}
						}else{
							return [true,''];
						}
					}else{
						return [true,''];
					}
				}else{
					return [true,''];
				}
			}else{
				return [true,''];
			}
		}
		
		function cargarDiasServicio(centro, articulo){	
			var $calendario = $( this );
			var fechasControl = new Array();
			var diasServicio=new DiasServicio(centro, articulo, null, null, null, null);
			var objJson = $.toJSON(diasServicio.prepareDiasServicioToJsonObject());
			 $.ajax({
				type : 'POST',
				url : './calendario/cargarDiasServicio.do',
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				async: false,
				success : function(data) {	
					/*for (i = 0; i < data.length; i++){
						//alert(data[i]);
						fechasControl.push(data[i]);
					}
					$(this).datepicker('refresh');*/
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
		        }			
			});		
		}

		function obtenerDiasServicio(centro, articulo, identificador, identificadorSIA, uuid, clasePedido, esFresco, diasMaximos, date, uuid){
			var $calendario = $( this );
			var fechasControlCompletas = new Array();
			var fechasControl = new Array();
			var tipoFechasControl = new Array();
			var diasServicio=new DiasServicio(centro, articulo, null, null, null, date);
			diasServicio.uuid = uuid;
			if (clasePedido == 'E'){
				diasServicio.clasePedido = 1;
			}
			
			var objJson = $.toJSON(diasServicio.prepareDiasServicioToJsonObject());
			 $.ajax({
				type : 'POST',
				url : './calendario/obtenerDiasServicio.do',
				data : objJson,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				async: false,
				success : function(data) {	
					for (i = 0; i < data.length; i++){
						//Hay que controlar para la fecha si es festivo, tiene servicio, tiene pedido, tiene bloqueo de encargo o tiene bloqueo de pilada
						var fechaControlada = data[i].fechaPantalla;
						var tipoFechaControlada = '';
						

						if (data[i].dFestivo != null && data[i].dFestivo =='S'){
							tipoFechaControlada = 'F';
						}else if (clasePedido == 'EC'){
							if (data[i].dEncargoCliente != null && data[i].dEncargoCliente =='S'){
								tipoFechaControlada = 'ECFV';
							}
							else {
								tipoFechaControlada = 'ECFN';
							}
						}else if (data[i].dEncargo != null && data[i].dEncargo =='S' && clasePedido == 'E' && ((((identificador == null || identificador == '') && (data[i].identificador != null && data[i].identificador != '')) || (identificador != null && identificador != '' && identificador != data[i].identificador)) || (((identificadorSIA == null || identificadorSIA == '') && (data[i].identificadorSIA != null && data[i].identificadorSIA != '')) || (identificadorSIA != null && identificadorSIA != '' && identificadorSIA != data[i].identificadorSIA)))){
							tipoFechaControlada = 'P';
						}else if (data[i].dMontaje != null && data[i].dMontaje =='S' && clasePedido == 'M' && ((((identificador == null || identificador == '') && (data[i].identificador != null && data[i].identificador != '')) || (identificador != null && identificador != '' && identificador != data[i].identificador)) || (((identificadorSIA == null || identificadorSIA == '') && (data[i].identificadorSIA != null && data[i].identificadorSIA != '')) || (identificadorSIA != null && identificadorSIA != '' && identificadorSIA != data[i].identificadorSIA)))){	
							tipoFechaControlada = 'P';
						}else if (clasePedido == 'E' && (data[i].bEncargo =='S' || data[i].bEncargoPilada =='S')){
							tipoFechaControlada = 'EE';
						}else if (clasePedido == 'M' && esFresco != null && esFresco=='N' && data[i].bEncargoPilada =='S'){
							tipoFechaControlada = 'MAEP';
						}else if (clasePedido == 'M' && esFresco != null && esFresco=='S' && data[i].bEncargoPilada =='S'){
							tipoFechaControlada = 'MFEP';							
						}else if (clasePedido == 'M' && esFresco != null && esFresco=='S' && data[i].bEncargo =='S'){
							tipoFechaControlada = 'MFE';
						}else if (data[i].dServicio != null && data[i].dServicio =='S' || data[i].dServicio =='X'){
							tipoFechaControlada = 'S';
						}else if(clasePedido == 'E' && data[i].dServicio =='N'){
							tipoFechaControlada = 'P';
						}
						
						
						//alert('d_encargo:'+data[i].dEncargo+' dMontaje:'+data[i].dMontaje+' clasePedido:'+clasePedido+' identificador:'+identificador+' data[i].identificador:'+data[i].identificador+' dFestivo:'+data[i].dFestivo+' dServicio:'+data[i].dServicio+' tipoFechaControlada:'+tipoFechaControlada);
						fechasControl.push(fechaControlada);
						tipoFechasControl.push(tipoFechaControlada);
					}
					fechasControlCompletas.push(fechasControl);
					fechasControlCompletas.push(tipoFechasControl);

					$(this).datepicker('refresh');
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
		        }			
			});	
			 
			return fechasControlCompletas; 
		}
		
		function prepareToJsonCalObject(){
			var jsonObject = {}; 
			
			return jsonObject;
		}
		
		function formatDateToDmy(fecha){
			var dmy = "";
			var dmy_d = (fecha.getDate()); 
			var dmy_m = (fecha.getMonth() + 1);
			var dmy_y = (fecha.getFullYear());
			dmy_d = ((dmy_d < 10)?"0".concat(dmy_d):dmy_d);
			dmy_m = ((dmy_m < 10)?"0".concat(dmy_m):dmy_m);

			var dmy = "".concat(dmy_d,dmy_m,dmy_y);
			
			return dmy;
		}
		
		function disableBeforeDay(day){
			var $calendario = $( this );
			if($calendario.prop( "disableBeforeDay")=='S'){
				if ($calendario.prop("clasePedido") == "EC"){
					var strFechaVentaModificada = $calendario.prop( "encargoClienteFechaVentaModificada");
					var fechaVentaModificada = null;
					if ($.isNumeric(strFechaVentaModificada)){
						fechaVentaModificada = new Date( Number($calendario.prop( "encargoClienteFechaVentaModificada")) );
					}else{
						if (strFechaVentaModificada!=null){
							fechaVentaModificada = devuelveDate(strFechaVentaModificada);
						}
					}
					if (fechaVentaModificada != null && fechaVentaModificada.valueOf()==day.valueOf()) {//Fecha de venta modificada
						return false
					}
					else{
						return true;
					}
				}
				else{
					return true;
				}
			}
			else{
				return false;
			}
		}
})(jQuery);
