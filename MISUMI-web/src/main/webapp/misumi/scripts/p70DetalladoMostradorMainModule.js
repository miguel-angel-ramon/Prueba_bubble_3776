var p70DetalladoMostradorMainModule = ( () => {
	
	/**
	 * Naming convenctions 
	 * _privateVariable 
	 * _privateFunction 
	 * publicFunction
	 * $varName jQuery object
	 */
	'use strict';
	
	/*Variables globales*/
	const codCentro = $("#centerId").val();
	let tableId = '';
	
	//---------- ELEMENTOS DEL DOM -----------------
	var _centro = {
		idHTML: "centerName",
		idJquery: "#centerName",
		getCodCentro: () => {
			return $("#centerId").val();
		},
		getCentro:() => {
			return $(_centro.idJquery).val();
		},
		/*
		 * Limpia el centro seleccionado por el usuario en session en el back
		 * y redirige a la pantalla de inicio.
		 */
		onFocus: () => {
			
			$.ajax({
				type : 'GET',
				url : './detalladoMostrador/clearSessionCenter.do',
				cache : false,
				dataType : "json",
				success : function(data) {				
					window.location='./welcome.do';
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
				}			
			});	
		},
		init: () => {
			//Observar el cambio de centro
			$(_centro.idJquery).off('focus');
			$(_centro.idJquery).on('focus',_centro.onFocus);
		}
	}
	
	
	//------- Selectores -------
	var _comboboxUtil = {
		//recibe callback success
		ajaxRequest : (successCallback) => {
			
			$.ajax({
				type : 'GET',
				url : './detalladoMostrador/loadEstructuraComercial.do',
				data : _comboboxUtil.getAllFilterParamsValues(),
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				success : function(data, textStatus, jqXHR) {
					successCallback(data, jqXHR);
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
				}		
			});
		},
		/* La opcion $(idCombo).combobox('getValue') no devuelve valor 
		 * cuando borran de forma manual el contenido del selector y seleccionan un item.
		 * Por eso se obtiene valor del combo mediante $(idCombo).val().
		 */
		getValue: idCombo => $(idCombo).val() == 'null' ? null : $(idCombo).val(),
		getText: idCombo => $(idCombo + " option:selected").text(),
		getAllFilterParamsValues: () => {
			
			const filterParams = { 
				codCentro		: _centro.getCodCentro(),
				codSeccion		: _comboboxUtil.getValue(_seccionCMB.idJquery),
				codCategoria	: _comboboxUtil.getValue(_categoriaCMB.idJquery),
				codSubcategoria	: _comboboxUtil.getValue(_subcategoriaCMB.idJquery),
				codSegmento		: _comboboxUtil.getValue(_segmentoCMB.idJquery),
				tipoAprov		: _comboboxUtil.getValue(_tipoAprovCMB.idJquery)
			}
			
			return filterParams;
		},
		populate: (idCombo, data) => {
			let options = "<option value='null'>&nbsp;</option>";
			
			if ( data ) {
				data.lstOpciones.forEach( item => options += "<option value='" + item.codigo + "'>" + item.descripcion + "</option>");
			}
						
			$("select" +  idCombo).html(options);
			
			//Si solo se recibe un item
			if (data && data.lstOpciones.length == 1){
				//seleccionar el elemento
				$(idCombo).combobox('comboautocomplete', data.lstOpciones[0].codigo);
				$(idCombo).combobox('autocomplete', data.lstOpciones[0].descripcion);
				
				//invocar al evento de seleccion
				$(idCombo).trigger("comboboxselected", { item: {value: data.lstOpciones[0].codigo } } );
			}else{
				$(idCombo).combobox('autocomplete',"");
				$(idCombo).combobox('comboautocomplete', null);
			}
			
			$(idCombo).combobox("enable");
		},
		reset: (idCombo) => {
			$(idCombo).combobox(null);
			$("select"+idCombo).html("<option value=null selected='selected'>" + ''+ "</option>");
			$(idCombo).combobox('autocomplete',"");
			$(idCombo).combobox('comboautocomplete', null);
			
			// El combo "Tipo Aprov", NO se desactiva en ninguna situación. SIEMPRE permanecerá HABILITADO.
			if (idCombo!=_tipoAprovCMB.idJquery){
				$(idCombo).combobox("disable");
			}
		}
	}
	
	var _seccionCMB = {
		idHTML: "p70_cmb_seccion",
		idJquery: "#p70_cmb_seccion",
		getItems: () => {
			_comboboxUtil.ajaxRequest(_seccionCMB.populate);
		},
		populate: (data) => {
			_comboboxUtil.populate(_seccionCMB.idJquery, data);
		},
		onChange: (event, ui) => {
			$(_seccionCMB.idJquery).trigger("comboboxselected", ui );
		},
		onSelect: (event, ui) => {
			
			_comboboxUtil.reset(_categoriaCMB.idJquery);
			_comboboxUtil.reset(_subcategoriaCMB.idJquery);
			_comboboxUtil.reset(_segmentoCMB.idJquery);
//			_comboboxUtil.reset(_tipoAprovCMB.idJquery);

			if ( null != ui.item && "" != ui.item.value && "null" != ui.item.value) {
				_categoriaCMB.getItems();
			}
				
		},
		init: () => {
			$(_seccionCMB.idJquery).combobox();

//			$(_seccionCMB.idJquery).combobox({ selected: _seccionCMB.onSelect });
//			$(_seccionCMB.idJquery).combobox({ changed: _seccionCMB.onChange });
			$(_seccionCMB.idJquery).on("comboboxselected", _seccionCMB.onSelect);
			$(_seccionCMB.idJquery).on("comboboxchanged", _seccionCMB.onChange);

		}	
	}
	
	var _categoriaCMB = {
		idHTML: "p70_cmb_categoria",
		idJquery: "#p70_cmb_categoria",
		populate: (data) => {			
			_comboboxUtil.populate(_categoriaCMB.idJquery, data);
			
			if (data && data.fechaEspejo){
				_diaEspejo.setDate(data.fechaEspejo);
			}
		},
		onChange: (event, ui) => {
			$(_categoriaCMB.idJquery).trigger("comboboxselected", ui );
		},
		onSelect: function (event, ui) {

			_comboboxUtil.reset(_subcategoriaCMB.idJquery)
			_comboboxUtil.reset(_segmentoCMB.idJquery)

			//Si el elemento seleccionado tiene valor cargar subcategorias
			if ( null != ui.item && "" != ui.item.value && "null" != ui.item.value) {	
				// Cargar subcategorias 
				_subcategoriaCMB.getItems();
			}
		},
		getItems: () => {
			_comboboxUtil.ajaxRequest(_categoriaCMB.populate);
		},
		init: function(){
			
			$(_categoriaCMB.idJquery).combobox(null);
			$(_categoriaCMB.idJquery).combobox("disable");
			
			$(_categoriaCMB.idJquery).on("comboboxselected", _categoriaCMB.onSelect);
			$(_categoriaCMB.idJquery).on("comboboxchanged", _categoriaCMB.onChange);
		}	
	}

	var _tipoAprovCMB ={
			idHTML: "p70_cmb_tipoAprov",
			idJquery: "#p70_cmb_tipoAprov",
			populate: (data) => {
				_comboboxUtil.populate(_tipoAprovCMB.idJquery, data);
			},
			onChange: (event, ui) => {
				$(_tipoAprovCMB.idJquery).trigger("comboboxselected", ui );
			},
			onSelect: function (event, ui) {
				_comboboxUtil.reset(_subcategoriaCMB.idJquery);
				_comboboxUtil.reset(_segmentoCMB.idJquery);
				_tipoAprovCMB.getItems();
			},
			getItems: () => {
				
				let codSeccion =_comboboxUtil.getValue(_seccionCMB.idJquery);
				let codCategoria =_comboboxUtil.getValue(_categoriaCMB.idJquery);
				let codSubcategoria	= _comboboxUtil.getValue(_subcategoriaCMB.idJquery);
				let codSegmento	= _comboboxUtil.getValue(_segmentoCMB.idJquery);
				
				let comboSelector;
				
				if ( null == codSeccion || "" == codSeccion || "null" == codSeccion) {
					_comboboxUtil.ajaxRequest(_seccionCMB.populate);
				}else if (null == codCategoria || "" == codCategoria || "null" == codCategoria) {
					_comboboxUtil.ajaxRequest(_categoriaCMB.populate);
				}else if (null == codSubcategoria || "" == codSubcategoria || "null" == codSubcategoria) {
					_comboboxUtil.ajaxRequest(_subcategoriaCMB.populate);
				}else {
					_comboboxUtil.ajaxRequest(_segmentoCMB.populate);
				}
				
			},
			init: function(){
				$(_tipoAprovCMB.idJquery).combobox(null);
//				$(_tipoAprovCMB.idJquery).combobox("disable");
				$(_tipoAprovCMB.idJquery).on("comboboxchanged", _tipoAprovCMB.onChange);
				$(_tipoAprovCMB.idJquery).on("comboboxselected", _tipoAprovCMB.onSelect);
			}	
	}
	
	var _subcategoriaCMB ={
		idHTML: "p70_cmb_subcategoria",
		idJquery: "#p70_cmb_subcategoria",
		populate: (data) => {
			_comboboxUtil.populate(_subcategoriaCMB.idJquery, data);
		},
		onChange: (event, ui) => {
			$(_subcategoriaCMB.idJquery).trigger("comboboxselected", ui );
		},
		onSelect: function (event, ui) {

			_comboboxUtil.reset(_segmentoCMB.idJquery)

			//Si el elemento seleccionado tiene valor
			if ( null != ui.item && "" != ui.item.value && "null" != ui.item.value) {
				// Cargar segmentos 
				_segmentoCMB.getItems();
			}
		},
		getItems: () => {
			_comboboxUtil.ajaxRequest(_subcategoriaCMB.populate);
		},
		init: function(){
			$(_subcategoriaCMB.idJquery).combobox(null);
			$(_subcategoriaCMB.idJquery).combobox("disable");
			
			$(_subcategoriaCMB.idJquery).on("comboboxchanged", _subcategoriaCMB.onChange);
			$(_subcategoriaCMB.idJquery).on("comboboxselected", _subcategoriaCMB.onSelect);
		}	
	}
	
	var _segmentoCMB ={
		idHTML: "p70_cmb_segmento",
		idJquery: "#p70_cmb_segmento",
		populate: (data) => {
			_comboboxUtil.populate(_segmentoCMB.idJquery, data);
		},
		getItems: () => {
			_comboboxUtil.ajaxRequest(_segmentoCMB.populate);
		},
		init: function(){
			$(_segmentoCMB.idJquery).combobox(null);
			$(_segmentoCMB.idJquery).combobox("disable");
		}	
	}
	
	var _diaEspejo = {
		idJquery: "#p70_fechaEspejo",
		getValue: () => $.datepicker.formatDate('dd/mm/yy', $(_diaEspejo.idJquery).datepicker('getDate')),
		isValidDate: () => _diaEspejo.getValue() ? true : false ,
		reset:	  () =>	$(_diaEspejo.idJquery).datepicker('setDate',null),
		diasFestivos: [],
		getDiasFestivos: () =>{
			
			var promise = $.ajax({
				type : 'GET',
				url : './detalladoMostrador/festivos.do',
				data : { 'codCentro': _centro.getCodCentro },
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false
			});
			
			return  promise;	
		},
		/*
		 * Le da fecha al calendario
		 * @date fecha en formato string
		 * ejemplo: '2023-03-09 00:00:00' 
		 */
		setDate:  (date) => {
			const parsedDate = new Date(Date.parse(date));
			$(_diaEspejo.idJquery).datepicker('setDate', parsedDate);
		},
		initDatepicker: () =>{
			
			$.datepicker.setDefaults($.datepicker.regional["esMisumi"]);
			$(_diaEspejo.idJquery).datepicker({
				changeMonth: false,
		        changeYear: false,
		        yearRange: "c-1:c+1",
				minDate: -395,  
				maxDate: -1,
				beforeShowDay: function(date){
					const diaFestivo   = [false,"dia-festivo"],
						  diaNoFestivo = [true,""],
						  dia = $.datepicker.formatDate('dd/mm/yy', date);
					
					return $.inArray(dia, _diaEspejo.diasFestivos) >=0 ? diaFestivo : diaNoFestivo;
				}
			
			});
		},
		init: () => {
			//Promesa para inicializar el calendario despues de obtener las fechas de dias festivos
			_diaEspejo.getDiasFestivos().done( (data) => {
				
				//Obtener dias festivos
				_diaEspejo.diasFestivos = data;
				//Settear calendario con festivos
				_diaEspejo.initDatepicker();
				
				//Una vez cargados los festivos, invocar la carga de las secciones
				_seccionCMB.getItems();
				
			});
			
		}
	}
	
	var bomba = {
		idJq: "#p70_textoYKingBomba",
		idJqTexto: "#p70_textoKingBomba",
		idJqImagen: "#p70_kingBomba",
		idJqCountDown: "#p70_cronoKingBomba",
		idJqAlerta:"#p70_alerta",
		idInterval: null,
		difHorMinSeg: null,
		//Cuando queden menos de 5 minutos, se muestra en rojo.
		minutoLimiteBomba: 5,
		setCountDown: (hora, minuto, segundo) => {
			// Insertamos la hora regresiva en el label de la bomba
			$(bomba.idJqCountDown ).text(hora + ":" + minuto + ":" + segundo);
		},
		setCountDownAlert: () => {
			//estilo hora en rojo
			$(bomba.idJqCountDown ).addClass("p70_explotaBomba").removeClass("p70_bombaNoExplota");
			//efecto blink en hora
			$(bomba.idJqCountDown ).show().fadeOut(900);
		},
		//A mostrar cuando se edita algun dato del grid
		showAlert: () =>{
			//div de la bomba
			$(bomba.idJq).show();
			//texto
			$(bomba.idJqTexto).show();
			//alerta
			$(bomba.idJqAlerta).show();
		},
		isAlertShowing: () =>{
			return $(bomba.idJqAlerta).is(':visible');
		},
		//Varible utilizada para comprobar si mostrar o no el div
		flgShow : false,
		//Mostrar desde el listModule cuando se muestren el area de totales y el grid
		show: () => {
			if (bomba.flgShow){
				//div de la bomba
				$(bomba.idJq).show();
				//texto
				$(bomba.idJqTexto).show();
				//imagen bomba
				$(bomba.idJqImagen).show();
			} 
		},
		resetAllDivs: () => {
			//div de la bomba.
			$(bomba.idJq).hide();
			//texto.
			$(bomba.idJqTexto).hide();
			//icono alerta
			$(bomba.idJqAlerta).hide();
			//Ocultar imagen bomba
			$(bomba.idJqImagen).hide();
			//Limpiar hora
			$(bomba.idJqCountDown ).text('');
			//Restaurar estilos hora
			$(bomba.idJqCountDown ).removeClass("p70_explotaBomba").addClass("p70_bombaNoExplota");			
		},
		hide: () =>{
			$(bomba.idJq).hide();
		},
		intervalo: () => {
			
			const hora 	= bomba.difHorMinSeg.getHours().toString().padStart(2,'0'),
			    minuto 	= bomba.difHorMinSeg.getMinutes().toString().padStart(2,'0'),
				segundo = bomba.difHorMinSeg.getSeconds().toString().padStart(2,'0');
			
			// Insertamos cuenta atras en el label de la bomba
			bomba.setCountDown(hora, minuto, segundo);

			// Cuando queden 5 minutos justos, si hay modificados (existe alerta moficados), mostramos popup aviso.
			if (hora == "00"
				&& parseInt(minuto) == bomba.minutoLimiteBomba
				&& parseInt(segundo) == 0
				&& bomba.isAlertShowing()) {
				
				createAlert(i18nModule._("aviso.bomba.guardar"), "INFO");
			}

			// Si entramos en los últimos 5 minutos parpadea en rojo
			if (hora == "00" && parseInt(minuto) < bomba.minutoLimiteBomba) {
				bomba.setCountDownAlert();
			}
			
			if (hora != "00" || minuto != "00" || segundo != "00") {
				//Recalculamos la hora
				bomba.difHorMinSeg = new Date().subTime(0, 0, 1, bomba.difHorMinSeg);
			}											

		},
		calculoCronometro: () => {
			
			bomba.resetAllDivs();
			//Paramos el trigger. Es necesario pararlo, porque si no
			//habría múltiples triggers encendidos y los segundos no 
			//bajarían de 1 en 1. Por cada trigger se restaría 1 y cada
			//buscar generaría un trigger haciendo que el segundero se
			//reduzca a una velocidad desproporcionada.
			clearInterval(bomba.idInterval);

			$.ajax({
				type : 'GET',
				url : './detalladoMostrador/calculoCronometro.do',
				data : { 'codCentro': _centro.getCodCentro },
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				success : function (data) {
					
					if (data && data.numHorasLimite <= 1) {
						
						//Obtenemos la hora y minuto exacto limite, si hora limite es null se establece a 00:00
						const horaMinutoLimite = data.horaLimite ? data.horaLimite.split(":") : ['00','00'],
							  //Si hora limite es
							  horaLimite 	   = horaMinutoLimite[0],
							  minutoLimite 	   = horaMinutoLimite[1],
							  fechaAhora 	   =  new Date();

						// Si la hora limite es mayor a la hora actual se calcula la diferencia horaria
						if(horaLimite > fechaAhora.getHours()){
							// Calculamos la diferencia de horas, minutos y segundos.
							bomba.difHorMinSeg = new Date().subTime(horaLimite, minutoLimite, 60, null);
						}
						// Si la hora límite es igual a la hora actual, nos fijamos en los minutos
						// Si los minutos limite son mayores a los de la hora
						// actual se calcula la diferencia de horas.
						//Si no se pone 00:00:00
						else if (horaLimite == fechaAhora.getHours() && minutoLimite > fechaAhora.getMinutes()){
							// Calculamos la diferencia de horas, minutos y segundos.
							bomba.difHorMinSeg = new Date().subTime(horaLimite,minutoLimite, 60, null);
						}
						// Si la hora limite es menor que la hora actual, mostramos 00:00:00
						else{
							bomba.difHorMinSeg = new Date(new Date().setHours(0,0,0));
						}
						
						//Flag mostra el div completo
						bomba.flgShow=true;
						
						//Lanzamos el trigger regresivo
						bomba.initInterval();
					}				
 
					else {
						bomba.hide();
					}
				}//success	
				,
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
		        }			
			});
		},
		initInterval: () => {
			bomba.idInterval = setInterval(bomba.intervalo, 1000);
		}
	}

		
	/**
	 * Funcionalidad que extiende la clase Date para realizar calculos
	 * adhoc necesarios en la bomba
	 */
	var extendsDateFunctions = () => { 
		Date.prototype.subTime= function(h,m,s,horaResta){
			if(horaResta != null){
				horaResta.setHours(horaResta.getHours() - h);
				horaResta.setMinutes(horaResta.getMinutes() - m);
				horaResta.setSeconds(horaResta.getSeconds() - s);		
				return horaResta;
			}else{
				this.setHours(h - this.getHours());
				this.setMinutes(m - this.getMinutes());
				if(this.getSeconds() < s){
					this.setMinutes(this.getMinutes() - 1);
				}
				this.setSeconds(s - this.getSeconds());	
				
				return this;
			}
		}
	}
	
	//------- BOTONES -------
	
	/*Botones*/
	var _buscarBTN = {
		idHTML: "p70_btn_buscar",
		idJquery: "#p70_btn_buscar",
		onClick: () => {
			
			//Validar dia espejo obligatorio
			if (!_diaEspejo.isValidDate()){
				createAlert(i18nModule._("diaEspejo.required"),"ERROR");
			} 
			// Confirmar perder cambios en grid penientes por guardar 
			else if (detalladoMostradorListModule.isDataGridDirty()){
				$( "#p70_buscarConfirm" ).dialog({
					 resizable: false,
				      height: "auto",
				      width: 450,
				      modal: true,
				      buttons: {
				        Aceptar: function() {
				        	$( this ).dialog( "close" );
				        	_buscarBTN.loadTemporalView();
				        },
				        Cancelar: function() {
				          $( this ).dialog( "close" );
				        }
				      }
				});
			} else {
				_buscarBTN.loadTemporalView();
			}
		},
		loadTemporalView: () => {
			
			const filtrosCombos = _comboboxUtil.getAllFilterParamsValues(),
				  filtros = {
					codCentro		: codCentro,
					codSeccion		: filtrosCombos.codSeccion,
					codCategoria	: filtrosCombos.codCategoria,
					codSubcategoria	: filtrosCombos.codSubcategoria,
					codSegmento		: filtrosCombos.codSegmento,
					tipoAprov		: filtrosCombos.tipoAprov, 
					soloVenta		: $('#p70_chk_soloVenta').prop('checked') ? 'S': 'N',
					gamaLocal		: $('#p70_chk_gamaLocal').prop('checked') ? 'S': 'N',
					diaEspejo		: _diaEspejo.getValue() ? _diaEspejo.getValue() : null 
				};
								
			//Carga de tabla temporal
			$.ajax({
				type : 'GET',
				url : './detalladoMostrador/loadDetalleMostrador.do',
				data : filtros,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				beforeSend: function (jqXHR, settings) {
					$(_buscarBTN.idJquery).prop('disabled',true);
					guardarBTN.disable();
					exportarBTN.disable();
				},
				complete: function (jqXHR,textStatus){
					$(_buscarBTN.idJquery).prop('disabled',false);
					//Los botones de guardar y exportar se habilitan al finalizar la carga del grid si tiene registros
					//$(guardarBTN.idJquery).prop('disabled',false);
					//exportarBTN.disable();
				},
				success : function(countInsertItems) {	

					if (countInsertItems){

						localStorage.removeItem('backupGrid');

						//Lanzar busqueda + bomba
						_buscarBTN.launchsearch();

					} else {
						createAlert(i18nModule._("emptyRecords"),"ERROR");
					}
					
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
		        }
			});
		},
		launchsearch(){
			//Iniciar grid muestra areas de totales y grid
			detalladoMostradorListModule.loadgrid();
			
			//Calculo bomba
			bomba.calculoCronometro();
		},
		init: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).on('click', _buscarBTN.onClick);
		}
	};
	
	var guardarBTN = {
		idHTML: "p70_btn_guardar",
		idJquery: "#p70_btn_guardar",
		onClick: function (){
			
			const jqGridDataRows = detalladoMostradorListModule.getDataRows();			
			
			if (jqGridDataRows) {
			
				let lstDetalleMostrador = jqGridDataRows
				  	.filter(row => (row.estado == 'P' || row.estado == 'R') )
					.map( row => {
						
						const detallado = { ident: row.ident, 
								 referencia: row.referencia, 
								 propuestaPedido: row.propuestaPedido, 
								 unidadesCaja: row.unidadesCaja, 
								 codNecesidad: row.codNecesidad, 
								 propuestaPedidoAnt: row.propuestaPedidoAnt, 
								 estado: row.estado,
								 tipoAprov: row.tipoAprov,
								 fechaVenta: row.fechaVenta
								 };
						
						return detallado;
	
					} );
	
				$.ajax({
						type : 'POST',
						url : './detalladoMostrador/saveGridDetalleMostrador.do?codCentro='+codCentro,
						data : JSON.stringify(lstDetalleMostrador),
						contentType : "application/json; charset=utf-8",
						dataType : "text",
						cache : false,
						beforeSend: function (jqXHR, settings) {
							$(_buscarBTN.idJquery).prop('disabled',true);
							guardarBTN.disable();
							exportarBTN.disable();
						},
						complete: function (jqXHR,textStatus){
							$(_buscarBTN.idJquery).prop('disabled',false);
							//El boton excel se habilita desde la funcion complete del grid si hay registros
							guardarBTN.enable();
						},
						success : function(data) {	
							//Lanzar busqueda + bomba
							_buscarBTN.launchsearch()
							
						},
						error : function (xhr, status, error){
							handleError(xhr, status, error, locale);
				        }			
					});
			}
		}, 
	    disable: function (){
	    	$(guardarBTN.idJquery).prop('disabled', true);
	    },
	    enable: function(){
	    	$(guardarBTN.idJquery).prop('disabled', false);
	    },
		init: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).on('click', guardarBTN.onClick);
		}
	}
	
	var _consultaBTN = {
			idJquery:"#p70_btn_consulta",
			onClick: () =>{
				$(_popUpReferenciasNoGama.idJquery).dialog('open');
			},
			init: function () {
				$(this.idJquery).prop('disabled',false);
				$(this.idJquery).off("click");
				$(this.idJquery).on('click', _consultaBTN.onClick);
			}, 
		    disable: function (){
		    	$(_consultaBTN.idJquery).prop('disabled', true);
		    },
		    enable: function(){
		    	$(_consultaBTN.idJquery).prop('disabled', false);
		    }
		}
	
	var exportarBTN = {
	    idHTML: "p70_btn_excel",
	    idJquery: "#p70_btn_excel",
	    onClick: function(){	
	    	let [day, month, year] = _diaEspejo.getValue().split('/');
	    	const fechaEspejoDateObj = new Date(+year, +month - 1, +day);
	    	
			//Filtros y sumatorio
	    	const datosCabecera = {
					centro		: _centro.getCentro(),
					seccion		: ' ' == _comboboxUtil.getText(_seccionCMB.idJquery) ? null : _comboboxUtil.getText(_seccionCMB.idJquery),
					categoria	: ' ' == _comboboxUtil.getText(_categoriaCMB.idJquery) ? null : _comboboxUtil.getText(_categoriaCMB.idJquery),
					subcategoria: ' ' == _comboboxUtil.getText(_subcategoriaCMB.idJquery) ? null : _comboboxUtil.getText(_subcategoriaCMB.idJquery),
					segmento	: ' ' == _comboboxUtil.getText(_segmentoCMB.idJquery) ? null : _comboboxUtil.getText(_segmentoCMB.idJquery),
					soloVenta	: $('#p70_chk_soloVenta').prop('checked') ? 'S': '',
					gamaLocal	: $('#p70_chk_gamaLocal').prop('checked') ? 'S': '',
					diaEspejo	: _diaEspejo.getValue() ? $.datepicker.formatDate("D dd-M", fechaEspejoDateObj ) : null,
					iniCosto	: $(detalladoMostradorListModule.areaCajasEuros.idJqueryIniCosto).text(),
					finCosto	: $(detalladoMostradorListModule.areaCajasEuros.idJqueryFinCosto).text(),
					iniPvp		: $(detalladoMostradorListModule.areaCajasEuros.idJqueryIniPvp).text(),
					finPvp		: $(detalladoMostradorListModule.areaCajasEuros.idJqueryFinPvp).text(),
					iniCajas	: $(detalladoMostradorListModule.areaCajasEuros.idJqueryIniCajas).text(),
					finCajas	: $(detalladoMostradorListModule.areaCajasEuros.idJqueryFinCajas).text(),
					ventasEspejo: $(detalladoMostradorListModule.areaCajasEuros.idJqueryVentasDiaEspejo).text(),
					fecEntrega	: $(detalladoMostradorListModule.areaCajasEuros.idJqueryFecEntrega).text(),
					fecPedido	: $(detalladoMostradorListModule.areaCajasEuros.idJqueryFecPedido).text(),
			};

	    	//Datos
	    	let filteredData = null, count, n; 
	    	
	    	//Si no se ha realizado ningun filtrado recojo todos los datos del grid
	    	if ($(tableId).jqGrid('getGridParam', 'lastSelected')){
	    		filteredData =$(tableId).jqGrid('getGridParam', 'lastSelected');
	    	}else{
	    		filteredData =$(tableId).jqGrid('getGridParam', 'data');
	    	}
	    	
	    	//Si existen datos en la consulta, realizo la exportacion
	    	if (filteredData.length) {
	    		
		    	let form = "<form name='csvexportform' action='detalladoMostrador/excel.do'  method='post' accept-charset='ISO-8859-1'>";

		    	//Columnas
		        const colNames = $(tableId).jqGrid("getGridParam", "colNames");
		        //Filas
		        let rows=[];
	    		
	    		for (count = 0, n = filteredData.length; count < n; count++) {
	    			let row = {};    			
	    			
	    				row.field1 = filteredData[count].seccionGrid;
	    				row.field2 = filteredData[count].referencia;
	    				row.field3 = filteredData[count].descripcion;
	    				row.field4 = filteredData[count].tipoAprov;
	    				row.field5 = utils.formatNumber(filteredData[count].unidadesCaja, 1, 1);
	    				row.field6 = utils.formatNumber(filteredData[count].stock, 1, 1);
	    				row.field7 = filteredData[count].empujePdteRecibir;
	    				row.field8 = utils.formatNumber(filteredData[count].tirado, 1 , 1);
	    				row.field9 = utils.formatNumber(filteredData[count].totalVentasEspejo, 1, 1);
	    				row.field10 = utils.formatNumber(filteredData[count].previsionVenta, 1, 1);
	    				row.field11 = filteredData[count].propuestaPedido;
	    				row.field12 = filteredData[count].pdteRecibirVentaGrid;
	    				row.field13 = filteredData[count].ofertaAB;
	    				row.field14 = filteredData[count].ofertaCD;
	    				row.field15 = utils.formatNumber(filteredData[count].pvp);
	    				row.field16 = utils.formatNumber(filteredData[count].margen);
	    				row.field17 = filteredData[count].tipoGama;
	    				row.field18 = filteredData[count].descripcionError;
	    				row.field19 = filteredData[count].tiradoParasitos;
	    				row.field20 = filteredData[count].estado;
	    				
	    			rows.push(row);
	    		}
	    		
	    		let formData={};
		    	formData.datos=rows;
		    	
		    	//Convierto en json el objeto para pasarlo en el input
		    	const formDataJson=JSON.stringify(formData).replace(/\'/g,'&#39;'),
		    		datosCabeceraJson = JSON.stringify(datosCabecera);
		    	
		    	form = form + "<input type='hidden' name='cabecera' value='"+datosCabeceraJson+"'>";
		    	form = form + "<input type='hidden' name='rows' value='"+formDataJson+"'>";

		    	form = form + "</form><script>document.csvexportform.submit();</script>";
		
			    documentPopupModule.showPopup(form);
	    	}
	    },
	    init: function(){
	        $(exportarBTN.idJquery).unbind('click');
			$(exportarBTN.idJquery).on('click', exportarBTN.onClick);
	    }, 
	    disable: function (){
	    	$(exportarBTN.idJquery).prop('disabled', true);
	    },
	    enable: function(){
	    	$(exportarBTN.idJquery).prop('disabled', false);
	    }
	}

	//------- VENTANAS MODALES -------
	
	/**
	 * Ventana modal de Ofertas
	 */
	var popUpOfertas = {
		_idJquery:"#popUpOfertasDetalladoMostrador",
		_referencia: null,
		_getOfertas: (codCentro, referencia) => {
			
			const request =  
				$.ajax({
					type : 'GET',
					url : './detalladoMostrador/ofertas.do',
					data : {codCentro: codCentro, referencia: referencia},
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					cache : false
				});	
			
			return request;
			
		},
		_createTable: (data) => {
			
			const thClasses	='class="ui-state-default ui-th-column ui-th-ltr"';
			let $divTable= $('<div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all"></div>'),
			 	$table 	 = $('<table style="border-collapse: collapse; width: 100%; table-layout:fixed;" class="ui-jqgrid-htable;"></table>'),
			 	$tHead 	 = $("<thead>"),
				$row 	 = $('<tr class="ui-jqgrid-labels"></tr>');
			
			$row.append('<th title="Nº Oferta" style="width:70px;" '+ thClasses +'>Nº Oferta</th>');
			$row.append('<th title="Nombre oferta" style="width:125px;" '+ thClasses +'>Nombre oferta</th>');
			$row.append('<th title="Fecha desde" style="width:70px;" '+ thClasses +'>Fec Desde</th>');
			$row.append('<th title="Fecha hasta" style="width:70px;" '+ thClasses +'>Fec hasta</th>');
			$row.append('<th title="Referencia" style="width:70px;" '+ thClasses +'>Referencia</th>');
			$row.append('<th title="Denominación" style="width:200px;" '+ thClasses +'>Denominación</th>');
			$row.append('<th title="Mecánica" style="width:65px;" '+ thClasses +'>Mecánica</th>');
			$row.append('<th title="PVP Tarifa" style="width:80px;" '+ thClasses +'>PVP Tarifa</th>');
			$row.append('<th title="PVP Oferta" style="width:80px;" '+ thClasses +'>PVP Oferta</th>');
			$row.append('<th title="Acciones" '+ thClasses +'>Acciones</th>');
			
			$tHead.append($row);
			$table.append($tHead);
			
			let $tBody = $("<tbody>");
			$.each(data, (index, record) => {
			  let classOdd = ''
			  if (index%2==1){
				  classOdd = 'ui-priority-secondary';
			  }
				$row = $('<tr class="ui-widget-content jqgrow ui-row-ltr ' + classOdd + '"></tr>');
			  
			  const tdStyleCenter ='style="text-align: center;"';
						  
			  $row.append($('<td '+tdStyleCenter+'></td>').text(record.anioOferta +"-"+record.numOferta));
			  $row.append($('<td '+tdStyleCenter+'></td>').text(record.denomOferta == null ? '-' : record.denomOferta));
			  $row.append($('<td '+tdStyleCenter+'></td>').text( $.datepicker.formatDate('dd/mm/yy', new Date(Date.parse(record.fechaInicio)))));
			  $row.append($('<td '+tdStyleCenter+'></td>').text( $.datepicker.formatDate('dd/mm/yy', new Date(Date.parse(record.fechaFin)))));
			  $row.append($('<td '+tdStyleCenter+'></td>').text(record.referencia));
			  $row.append($('<td '+tdStyleCenter+'></td>').text(record.denominacion));
			  $row.append($('<td '+tdStyleCenter+'></td>').text(record.mecanica));
			  $row.append($('<td '+tdStyleCenter+'></td>').text(utils.formatNumber(record.pvpTarifa)));
			  $row.append($('<td '+tdStyleCenter+'></td>').text(utils.formatNumber(record.pvpOferta)));
			  $row.append($('<td '+tdStyleCenter+'></td>').append(
					  $("<input class='btnHistorico' type='button' onclick='p70DetalladoMostradorMainModule.popUpAyuda("+record.referencia+","+record.anioOferta+","+record.numOferta+")' class='boton  botonHover' value='histórico'/>")
			  ));
			  
			  $tBody.append($row);
			  
			});
			
			$table.append($tBody);
			$divTable.append($table);
			
			$(popUpOfertas._idJquery).empty();
			$(popUpOfertas._idJquery).append($divTable);
		},
		_open: ()=> {
			//Obtener las ofertas
			popUpOfertas._getOfertas(_centro.getCodCentro(), popUpOfertas._referencia)
				.done( data => {
					popUpOfertas._createTable(data);
					
					$('.btnHistorico').first().focus();
				});
		},
		_close: () => {
			$(popUpOfertas._idJquery).empty();
		},
		_options:{
			title: "Oferta",
			height:'auto',
			width:'950px',
			stack: false,
			modal: true,
			resizable: false,
			open: () => popUpOfertas._open(),
			close: ()=> popUpOfertas._close()
		 },
		init: (referencia) => {
			//Asiganacion de valor a variable global del objeto
			popUpOfertas._referencia = referencia;
			
			//iniciar dialog
			$(popUpOfertas._idJquery).dialog(popUpOfertas._options);
			
			//Cerrar popUpAyuda por si esta abierto
			$(popUpAyuda._idJquery).css('display') == 'block' ? $(popUpAyuda._idJquery).dialog('close') : null;
			
		}
	}
	
	/***
	 * Ventana modal de ayuda
	 */
	var popUpAyuda = {
		_idJquery:"#p56_AreaAyuda",
		_options:{
			autoOpen: false,
			height: 'auto',
			width: 475,
			minHeight:0,
			modal: false,
			resizable: false,
			dialogClass: "p56_popupResaltado",
			position:[0,0],
			zIndex: 1000,
			stack: true
		 },
		 _extendsOptions:{
				"maximizable" : false,
				"collapsable" : false,
				"minimizable" : true,
				"minimizeLocation" : "left",		
				"minimize" : function(evt) {
					//Obtenemos el título del diálogo minimizado y le insertamos la clase p56_tituloAcortado que sirve para acortar el título
					//y dejarlo con puntos suspensivos.
					$(".p56_popupResaltado").parent().find("#dialog-extend-fixed-container").find(".ui-dialog-title").addClass("p56_tituloAcortado");
				}
		},
		loadI18n: () => {
			return $.getJSON('./misumi/resources/p56AyudaPopup/p56AyudaPopup_' + locale + '.json');
		},
		init: (referencia) => {
			
			//Inicializamos el popup de ayuda.
			$(popUpAyuda._idJquery).dialog(popUpAyuda._options).dialogExtend(popUpAyuda._extendsOptions);
			
			//carga de etiquetas
			popUpAyuda.loadI18n().done((data) =>{
				//variables definidas y utilizadas en p56yudaPopUp.js
				productoPrecio = data.prodPrecio;
				ofertasSimilares = data.ofertasSimilares;

				//Logica de initializeScreenP56();
				var vOferta = new VOferta(_centro.getCodCentro(), referencia);
				
				//Se inicializa el combobox.
				$("#p56_cmb_ventasEnUltimasOfertas").combobox(null);
				
				//Guarda el nombre del título del dialogo en una variable global para más tarde añadirle texto Prod.Precio
				// u ofertas similares
				titleDialog =  'Ayuda para introducir las cajas a pedir';//$("#p56_AreaAyuda").parent().children(':first-child').children('span:first-child').text();
				
				load_cmbVentasUltimasOfertasP56(vOferta, null);
				
			});
			
		}
	}
	

	/**
	 * Ventana modal de consulta de referencias No gmaa
	 */
	var _popUpReferenciasNoGama = {
		idJquery:"#popUpReferenciasNoGama",
		table: {
			idJquery:"#p70_gridRng",
			idJqueryPrevPage:"#p70_pagAnterior",	 //icono <<
			idJqueryNextPage:"#p70_pagSiguiente",	 //icono >>
			idJqueryCurrentPage: "#p70_currentPage", //hidden
			idJqueryTotalPage: "#p70_totalPage",	 //hidden
			idJqueryRecordsPage: "#p70_paginaActual",//registros mostrados
			idJqueryTotalRecords: "#p70_paginaTotal",//total de registros
			maxRows: 10,
			getCurrentPage: () => Number($(_popUpReferenciasNoGama.table.idJqueryRecordsPage).val()),
			setCurrentPage: (page) => $(_popUpReferenciasNoGama.table.idJqueryRecordsPage).val(page),
			getTotalPages: () => Number($(_popUpReferenciasNoGama.table.idJqueryTotalPage).val()),
			setTotalPage: (page) => $(_popUpReferenciasNoGama.table.idJqueryTotalPage).val(page),
			
			setRowsPage: (page) => $(_popUpReferenciasNoGama.table.idJqueryRecordsPage).text(page),
			setTotalRows: (totalRecords) => $(_popUpReferenciasNoGama.table.idJqueryTotalRecords).text("/" + totalRecords),
			nextPage: () => {

				const currentPage = _popUpReferenciasNoGama.table.getCurrentPage(),
						 nextPage = currentPage + 1, 
					   totalPages = _popUpReferenciasNoGama.table.getTotalPages(),
						  maxRows = _popUpReferenciasNoGama.table.maxRows;

				if (nextPage <= totalPages){
					_popUpReferenciasNoGama.getReferencias(nextPage, maxRows);	
				} 
				
			},
			prevPage: () =>{
				const currentPage = _popUpReferenciasNoGama.table.getCurrentPage(),
				 		prevPage  = currentPage - 1,
				 		 maxRows  = _popUpReferenciasNoGama.table.maxRows;
				
				if (prevPage >= 1){					
					_popUpReferenciasNoGama.getReferencias(prevPage, maxRows);	
				}
				
			},
			nextPageEnable:() => $(_popUpReferenciasNoGama.table.idJqueryNextPage).removeClass("p70_pagSig_Des").addClass("p70_pagSig"),
			nextPageDisable:() => $(_popUpReferenciasNoGama.table.idJqueryNextPage).removeClass("p70_pagSig").addClass("p70_pagSig_Des"),
			prevPageEnable:() => $(_popUpReferenciasNoGama.table.idJqueryPrevPage).removeClass("p70_pagAnt_Des").addClass("p70_pagAnt"),
			prevPageDisable:() => $(_popUpReferenciasNoGama.table.idJqueryPrevPage).removeClass("p70_pagAnt").addClass("p70_pagAnt_Des"),
			/**
			 * La paginacion de registros se realiza a traves de los valores de la pagina actual y las paginas totales.
			 * Cada vez que se carga/resetea una pagina, se invoca a esta funcion para settear:
			 * los valores de las paginas (2 hidden): 
			 * - pagina actual y paginas totales
			 * Y visualizacion en el pie de tabla de: 
			 * - registros mostrados / total de registros.
			 * El numero de registros mostrados se calcula sumandole a los registros ya mostrados el numero de registros de la pagina.
			 * 
			 * @page Pagina actual (hidden)
			 * @total Numero de paginas totales (hidden)
			 * @records Numero registros totales
			 * @nrows Numero de registros existentes en la pagina
			 *  
			 */
			managePagination: (page, total, records, nrows) => {
				//const rowsOfPage= (page*_popUpReferenciasNoGama.table.maxRows) - _popUpReferenciasNoGama.table.maxRows + nrows;
				const rowsOfPage= (page-1) * _popUpReferenciasNoGama.table.maxRows + nrows;
				
				//Cargar numeraciones de pagina
				_popUpReferenciasNoGama.table.setCurrentPage(page);
				_popUpReferenciasNoGama.table.setTotalPage(total);
				//Cargar numeraciones de registros para el pie de tabla
				_popUpReferenciasNoGama.table.setRowsPage(rowsOfPage);
				_popUpReferenciasNoGama.table.setTotalRows(records);

				
				//Control prevPage
				if(page <= 1){
					_popUpReferenciasNoGama.table.prevPageDisable();
				} else {
					_popUpReferenciasNoGama.table.prevPageEnable();
				}
				
				//Control nextPage
				if(total > page){
					_popUpReferenciasNoGama.table.nextPageEnable();
				} else {
					_popUpReferenciasNoGama.table.nextPageDisable();
				}

			},
			loadPage: (data) => {
				
				//limpar pagina
				_popUpReferenciasNoGama.table.reset();

				//Gestion de paginacion
				_popUpReferenciasNoGama.table.managePagination(data.page, data.total, data.records, data.rows.length);
				
				const $divpage = $(_popUpReferenciasNoGama.table.idJquery);

				$.each(data.rows, (index, row) => {
					let $divItem = $('<div id="referencia_'+index+'" class="fotoBUP70"/>'),
						$divBorder = $('<div class="fotoReferenciaP70"/>'),
						$divImage = $('<div class="formBloqueFotoProductoP70"/>'),
						$image = "S" == row.foto 
										  ? $('<img id="image_'+index+'" class="imagenProductoP70" src="./welcome/getImage.do?codArticulo='+row.codArtFormLog+'">') 
										  : $('<img id="image_'+index+'" class="imagenProductoP70" src="./misumi/images/nofotoRecortada.png">'),
						$divRefDesc = $('<div class="formBloqueReferenciaP70">'+
											'<label class="formFotoLabel">'+row.codArtFormLog+'-'+row.denomRef+'</label>' +
										'</div>'),
						$divOtherInfo = $('<div class="formBloqueInfoP70"/>'),
						$divUc = $('<div class="formLineInfoP70"> ' + 
								'<span class="formBloqueInfoNormal">' + i18nModule._("p70_popUpReferenciasNoGama.unidadesCaja") +'</span>' + 
								'<span class="formBloqueInfoBold">' + row.unidadesCaja + '</span>' +
							  '</div>'),
						$divPvp = $('<div class="formLineInfoP70"> ' + 
										'<span class="formBloqueInfoNormal">' + i18nModule._("p70_popUpReferenciasNoGama.pvp") +'</span>' + 
										'<span class="formBloqueInfoBold">' + utils.formatNumber(row.pvpTarifa) + '&euro;</span>' +
						  			'</div>'),
						$divMargen = $('<div class="formLineInfoP70"> ' + 
										'<span class="formBloqueInfoNormal">' + i18nModule._("p70_popUpReferenciasNoGama.margen") +'</span>' + 
										'<span class="formBloqueInfoBold">' + utils.formatNumber(row.margen) + '%</span>' +
						  			  '</div>'),
						$divCc = $('<div class="formLineInfoP70"> ' + 
											'<span class="formBloqueInfoBiger">' + i18nModule._("p70_popUpReferenciasNoGama.cc") +'</span>' + 
											'<span class="formBloqueInfoBiger">' + row.cc + '</span>' +
							  			  '</div>');
						
						$divImage.append($image);
						
						$divBorder.append($divImage)
								  .append($divRefDesc);
						
						$divOtherInfo.append(row.unidadesCaja ? $divUc : '')
									 .append($divPvp)
									 .append($divMargen)
									 .append(row.cc ? $divCc : '');
						
						$divItem.append($divBorder)
								.append($divOtherInfo);
						
						$divpage.append($divItem);
				});

			},
			reset: () => {
				$(_popUpReferenciasNoGama.table.idJquery).empty();
				_popUpReferenciasNoGama.table.managePagination(1,1,0,0);
			},
			init:()=>{
				$(_popUpReferenciasNoGama.table.idJqueryNextPage).off("click");
				$(_popUpReferenciasNoGama.table.idJqueryNextPage).on("click",_popUpReferenciasNoGama.table.nextPage );
				
				$(_popUpReferenciasNoGama.table.idJqueryPrevPage).off("click");
				$(_popUpReferenciasNoGama.table.idJqueryPrevPage).on("click",_popUpReferenciasNoGama.table.prevPage );
				
			}
		},
		referenciaTXT:{
			idJquery:"#p70_rng_txt_referencia",
			onKeydown: (e) => {
				if(e.which == 13) {
					e.preventDefault();
					_popUpReferenciasNoGama.buscarBTN.onClick();
				}
			},
			getValue: () => $(_popUpReferenciasNoGama.referenciaTXT.idJquery).val(),
			reset: () => $(_popUpReferenciasNoGama.referenciaTXT.idJquery).val(''),
			init:()=>{
				$(_popUpReferenciasNoGama.referenciaTXT.idJquery).off("keydown");
				$(_popUpReferenciasNoGama.referenciaTXT.idJquery).on("keydown", _popUpReferenciasNoGama.referenciaTXT.onKeydown);
			}
		},
		buscarBTN: {
			idJquery:"#p70_rng_btn_buscar",
			onClick: () =>{
				//carga de tabla temporal
				_popUpReferenciasNoGama.loadTemporalView();
			},
			init: () => {
				$(_popUpReferenciasNoGama.buscarBTN.idJquery).off("click");
				$(_popUpReferenciasNoGama.buscarBTN.idJquery).on('click', _popUpReferenciasNoGama.buscarBTN.onClick);
			}, 
		},
		/* Carga en la tabla temporal la lista de referencias 
		 * y obtiene los datos para cargar la primera pagina*/
		loadTemporalView: () => {
			const filtrosCombos = _comboboxUtil.getAllFilterParamsValues(),
			  filtros = {
				codCentro		: codCentro,
				codSeccion		: filtrosCombos.codSeccion,
				codCategoria	: filtrosCombos.codCategoria,
				codSubcategoria	: filtrosCombos.codSubcategoria,
				codSegmento		: filtrosCombos.codSegmento,
				gamaLocal		: $('#p70_chk_gamaLocal').prop('checked') ? 'S': 'N',
				descripcion 	: _popUpReferenciasNoGama.referenciaTXT.getValue() 
			};
							
			//Carga de tabla temporal
			$.ajax({
				type : 'GET',
				url : './detalladoMostrador/loadReferenciasNoGamaCentro.do',
				data : filtros,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				cache : false,
				success : function(countInsertItems) {	
					if (countInsertItems > 0){
						//Cargar pagina
						_popUpReferenciasNoGama.getReferencias(1, _popUpReferenciasNoGama.table.maxRows);
					} else{
						_popUpReferenciasNoGama.table.reset();
						createAlert(i18nModule._("emptyRecords"),"ERROR");
					}
				},
				error : function (xhr, status, error){
					handleError(xhr, status, error, locale);
		        }			
			});
		},
		getReferencias: (page, rows) => {
					
			const request =  
				$.ajax({
					type : 'GET',
					url : './detalladoMostrador/getReferenciasNoGamaCentro.do',
					data : { codCentro: _centro.getCodCentro(), page: page, rows: rows },
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					cache : false,
					success : function(data) {	
						//Cargar pagina
						_popUpReferenciasNoGama.table.loadPage(data);
					},
					error : function (xhr, status, error){
						handleError(xhr, status, error, locale);
			        }			
				});	
			
			return request;
					
		},
		open: ()=> {
		},
		close: () => {
			//limpar pagina
			_popUpReferenciasNoGama.table.reset();
			//limpiar busqueda
			_popUpReferenciasNoGama.referenciaTXT.reset();
		},
		options:{
			title: "Referencias Centro No Gama",
	        autoOpen: false,
			height: 'auto',
			width: 'auto',
			modal: true,
			resizable: false,
			open: () => _popUpReferenciasNoGama.open(),
			close: () => _popUpReferenciasNoGama.close()
		 },
		init: () => {
			
			//iniciar boton y txt de busqueda
			_popUpReferenciasNoGama.buscarBTN.init();
			_popUpReferenciasNoGama.referenciaTXT.init();

			//iniciar tabla
			_popUpReferenciasNoGama.table.init();
						
			//iniciar dialog
			$(_popUpReferenciasNoGama.idJquery).dialog(_popUpReferenciasNoGama.options);

			//Cerrar popUpAyuda por si esta abierto
			$(popUpAyuda._idJquery).css('display') == 'block' ? $(popUpAyuda._idJquery).dialog('close') : null;
			
		}
			
	}
	
	var utils  = {
		/**
		 * @Autor BICUGUAL
		 * Formatea el numero utilizando la funcion JS Intl.NumberFormat
		 * Si solo se pasa el @param number, asigna formato por defecto
		 * @param number numero a formatear
		 * @param maximumFractionDigits maximo de decimales
		 * @param minimumFractionDigits minimo de decimales
		 * @return 
		 */
		formatNumber : (number, maximumFractionDigits, minimumFractionDigits) => {
			const defaultMaximumFractionDigits = 2,
				  defaultMinimumFractionDigits = 2,
				  options = { useGrouping : true , 
							  maximumFractionDigits: maximumFractionDigits ? maximumFractionDigits : defaultMinimumFractionDigits,
							  minimumFractionDigits: minimumFractionDigits ? minimumFractionDigits : defaultMinimumFractionDigits
						  	},
					fnNumberPattern   = new Intl.NumberFormat('es-ES', options);

			return fnNumberPattern.format(number);
		}
	}
	
	var initModule = () => {

		$(document).ready(() => {
			//Extiende funcionalidad de Date para calculos con horas en la bomba
			extendsDateFunctions();
			
			_centro.init();
			tableId = detalladoMostradorListModule.tableId;
			
			//Inicializar carga de estructura
			_segmentoCMB.init()
			_subcategoriaCMB.init();
			
			// Carga de Tipo Aprov.
			_tipoAprovCMB.init();
			
			//Cargar cbo y dia espejo
			_categoriaCMB.init();
			_seccionCMB.init();
			
			//Despues de cargar los dias festivos del datepicker se cargan los combos
			_diaEspejo.init();	
			
			_buscarBTN.init();
			guardarBTN.init();
			exportarBTN.init();
			
			_consultaBTN.init();
			
			_popUpReferenciasNoGama.init();
		});
			
	}

	return {
		init: initModule,
		codCentro : _centro.getCodCentro,
		bomba: bomba,
		exportarBTN: exportarBTN,
		guardarBTN: guardarBTN,
		popUpOfertas : popUpOfertas.init,
		popUpAyuda: popUpAyuda.init
	
	}
})();

p70DetalladoMostradorMainModule.init();