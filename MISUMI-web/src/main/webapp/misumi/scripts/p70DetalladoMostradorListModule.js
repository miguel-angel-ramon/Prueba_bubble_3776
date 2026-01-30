var codArticuloAnt;
var stockAnt;

var detalladoMostradorListModule = ( () => { 
	/**
	 * Naming convenctions 
	 * _privateVariable 
	 * _privateFunction 
	 * publicFunction
	 * $varName jQuery object
	 */
	'use strict';
	/*Elementos del DOM*/
		
	/**
	 * Area de grid
	 */
	var _areaResultados = {
		idHTML : "p70_AreaResultados",
		idJquery : "#p70_AreaResultados",
		show: () => { $(_areaResultados.idJquery).show()},
		hide: () => { $(_areaResultados.idJquery).hide()},
		init: () => {
			_areaResultados.show();
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
	
	var _newId;
	
	var _newIdent = {
		guardarIdentDestino: (ident) => {
			let newIdent = ident.substring(0, ident.indexOf("_"));
			
			return newIdent;
		}
	}
	
	/**
	 * Area de totales
	 */
	var areaCajasEuros = {
		idHTML : "p70_AreaCajasEuros",
		idJquery : "#p70_AreaCajasEuros",
		idJqueryIniCosto : "#p70_inputEurosIniciales",
		idJqueryFinCosto : "#p70_inputEurosFinales",
		idJqueryIniPvp : "#p70_inputEurosPVPIniciales",
		idJqueryFinPvp : "#p70_inputEurosPVPFinales",
		idJqueryIniCajas : "#p70_inputCajasIniciales",
		idJqueryFinCajas : "#p70_inputCajasFinales",
		idJqueryVentasDiaEspejo : "#p70_ventasEspejoValor",
		idJqueryFecEntrega : "#p70_fechaEntregaValor",
		idJqueryFecAviso : "#p70_fechasAviso",
		idJqueryFecPedido : "#p70_fechaPedidoValor",
		idJqueryFecDobleTrans: "#p70_fechasDobleTransmision",
		msEffect:500,
		show: () => { $(areaCajasEuros.idJquery).show()},
		hide: () => { $(areaCajasEuros.idJquery).hide()},
		getSumatorios: (rows) => {

			let sum = { totalInicialCosto: 0, totalFinalCosto: 0, totalInicialPvp: 0, totalFinalPvp: 0, 
						totalInicialCajas: 0, totalFinalCajas: 0, totalVentasDiaEspejo: 0 }
			
			rows.forEach((row) => {
				
				const valorInicialCosto = Math.round((row.previsionVenta  * row.precioCostoArticulo * row.unidadesCaja) * 100) / 100; 
				sum.totalInicialCosto  	= Math.round((sum.totalInicialCosto + valorInicialCosto) * 100 ) /100;
				
				const valorFinalCosto 	= Math.round((Number(row.propuestaPedido)  * row.precioCostoArticulo * row.unidadesCaja) * 100) / 100;
				sum.totalFinalCosto  	= Math.round((sum.totalFinalCosto + valorFinalCosto) * 100 ) /100;
				
				const valorInicialPvp 	= Math.round((row.previsionVenta  * row.pvp * row.unidadesCaja) * 100) / 100; 
				sum.totalInicialPvp  	= Math.round((sum.totalInicialPvp + valorInicialPvp) * 100 ) /100;
				
				const valorFinalPvp 	= Math.round((Number(row.propuestaPedido)  * row.pvp * row.unidadesCaja) * 100) / 100; 
				sum.totalFinalPvp  		= Math.round((sum.totalFinalPvp + valorFinalPvp) * 100 ) /100;
				
				sum.totalInicialCajas 	= Math.round((sum.totalInicialCajas + Number(row.previsionVenta)) * 10) /10;
				
				sum.totalFinalCajas		+= Number(row.propuestaPedido);
				
				sum.totalVentasDiaEspejo += row.totImporteVentasEspejo;
				
			});
			
			return sum;
		},
		setValue: ($element, value, decimals) => {
			
			const options = {useGrouping : true , maximumFractionDigits: decimals == null ? 0 : decimals},
			fnNumberPattern = new Intl.NumberFormat('es-ES', options);
			
			//Si los totales cambian, actualizamos valor
			if ($element.text()!== value){
				$element
				.fadeOut(areaCajasEuros.msEffect).fadeIn(areaCajasEuros.msEffect)
				.fadeOut(areaCajasEuros.msEffect).fadeIn(areaCajasEuros.msEffect)
				.text(fnNumberPattern.format(value));	
			}
		},
		/*
		 * Recalculo de totales que pueden cambiar tras la edicion de la propuesta pedido
		 */
		refresh: (rows) => {
			const sumatorios = areaCajasEuros.getSumatorios(rows);
			
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryFinCosto), sumatorios.totalFinalCosto);
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryFinPvp), sumatorios.totalFinalPvp);
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryFinCajas), sumatorios.totalFinalCajas);
		},
		reset: () =>{
			$(areaCajasEuros.idJqueryIniCosto).text('');
			$(areaCajasEuros.idJqueryFinCosto).text('');
			$(areaCajasEuros.idJqueryIniPvp).text('');
			$(areaCajasEuros.idJqueryFinPvp).text('');
			$(areaCajasEuros.idJqueryIniCajas).text('');
			$(areaCajasEuros.idJqueryFinCajas).text('');
			
			$(areaCajasEuros.idJqueryVentasDiaEspejo).text('');
			
			$(areaCajasEuros.idJqueryFecEntrega).text('');
			$(areaCajasEuros.idJqueryFecAviso).hide();
			$(areaCajasEuros.idJqueryFecPedido).text('');
			$(areaCajasEuros.idJqueryFecDobleTrans).hide();
		},
		init: (rows, userData) => {
			
			const sumatorios = areaCajasEuros.getSumatorios(rows);

			//Reseteo
			areaCajasEuros.reset();
			areaCajasEuros.show();
			
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryIniCosto), sumatorios.totalInicialCosto);
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryFinCosto), sumatorios.totalFinalCosto);
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryIniPvp), sumatorios.totalInicialPvp);
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryFinPvp), sumatorios.totalFinalPvp);
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryIniCajas), sumatorios.totalInicialCajas);
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryFinCajas), sumatorios.totalFinalCajas);
			
			areaCajasEuros.setValue($(areaCajasEuros.idJqueryVentasDiaEspejo), sumatorios.totalVentasDiaEspejo);
			
			$(areaCajasEuros.idJqueryFecEntrega).text(userData.fechaEntrega);
			//Mensaje fecha aviso
			if (userData.avisoVisperaFestivo == true){
				$(areaCajasEuros.idJqueryFecAviso).show();
			}
			$(areaCajasEuros.idJqueryFecPedido).text(userData.fechaPedido);
						
			//Mensaje doble transmision
			const currentDay = new Date(),
			   currentDayFmt = $.datepicker.formatDate('D dd-M', currentDay);
			
			if (currentDayFmt == userData.fechaPedido){
				$(areaCajasEuros.idJqueryFecDobleTrans).show();
			}			
			
			//console.log("Valores Totales Iniciales:" + JSON.stringify(sumatorios));
		}
	}
	
	/*Grid*/
	var _grid = {
		idHTML : "gridP70PedidoDatos",
		idJquery : "#gridP70PedidoDatos",
		idJqueryPager:"#pagerP70PedidoDatos",
		idHTMLPager:"pagerP70PedidoDatos",
		
		/**
		 * Flag, controlador y funciones que se desean ejecutar solo primera carga
		 * o solo en las sucesivas cargas de grid
		 */
		firstLoad: true,
		manageFirstAndNextLoads:() =>{
			//Configuracion de última seleccion. Se utiliza en la exportacion de datos para saber los que estan "seleccionados en el momento de la peticion"   
			$(_grid.idJquery)[0].p.lastSelected = _grid.lastSelected;
			
			if (_grid.firstLoad==false){
			  	_grid.afterFirstLoad != null ? _grid.afterFirstLoad() : false;
		  	}
		  	else{
		  		_grid.firstLoad=false;
		  		_grid.onFirstLoad != null ? _grid.onFirstLoad() : false;			
		  	}			
		},
		onFirstLoad: () => {

			const jqGridDataRows = $(_grid.idJquery).jqGrid('getGridParam', 'data'),
				userData 		 = $(_grid.idJquery).jqGrid('getGridParam', 'userData');
			
			//Añadir title al encabezado de columnas
			const lstColModel = $(_grid.idJquery).jqGrid("getGridParam", "colModel"),
				  colPrefix   = "p70_detallado.mostrador.grid.title.";

			lstColModel.forEach( colModel => $(_grid.idJquery + "_" +colModel.name).attr("title", i18nModule._(colPrefix + colModel.name )));
			
			//Si el grid tiene registros
			if (jqGridDataRows.length){
				//Mostrar AreaResultados y contadores
				areaCajasEuros.init(jqGridDataRows, userData);
				_areaResultados.init();
				//Mostrar BOOOMBA
				p70DetalladoMostradorMainModule.bomba.show();
				
				//Habilitar botones exportar y guardar
				p70DetalladoMostradorMainModule.exportarBTN.enable();
				p70DetalladoMostradorMainModule.guardarBTN.enable();
				
				//Aviso de registros con estado error
				const jqGridDataRowsErrorRow = jqGridDataRows.filter( rowData => rowData.estadoGrid < 0);
				if (jqGridDataRowsErrorRow.length > 0){
					createAlert(i18nModule._("p70_detallado.mostrador.save.witherrors").replace('{0}', jqGridDataRowsErrorRow.length), "ERROR");
				}

				
			} else {
				//Deshabilitar botones exportar y guardar
				p70DetalladoMostradorMainModule.exportarBTN.disable();
				p70DetalladoMostradorMainModule.guardarBTN.disable();
				createAlert(i18nModule._("emptyRecords"), "INFO");
			}			
		},
		afterFirstLoad: () => {
			
			if (null != _grid.lastSelected && _grid.lastSelected.length > 0){
				//Habilitar botones exportar
				p70DetalladoMostradorMainModule.exportarBTN.enable();
			} else {
				//Deshabilitar botones exportar (solo mostrar si se visualiza algo en el grid porque se exportan los registros filtrados)
				p70DetalladoMostradorMainModule.exportarBTN.disable();
			}
			
		},
		getDataRows: () => {
			return $(_grid.idJquery).jqGrid('getGridParam', 'data');
		},
		/**
		 * Flag para indicar que se ha modificado algun registro del grid.
		 * false= NO modificado // true = Modificado no guardado
		 */
		dataGridDirty : false, 
		/**
		 * Modifica el valor interno de la celda
		 * @param {*} jqGridCellId Id de la celda
		 */
		updateDataGridCellValue: (jqGridCellId) => {		
			
			//Datos locales del grid
			const jqGridDataRows = $(_grid.idJquery).jqGrid('getGridParam', 'data'),
				  reccount		 = $(_grid.idJquery).jqGrid('getGridParam', 'reccount'),
				  rowId			 = jqGridCellId.substring(0, jqGridCellId.indexOf("_")),
				  rowData 		 = $(_grid.idJquery).jqGrid('getRowData', rowId),
				  columName 	 = jqGridCellId.substring(jqGridCellId.indexOf("_") + 1),
				  //Indice de la fila dentro del array de datos
				  indexDataRow	 = jqGridDataRows.findIndex(obj => obj._id_ == rowId),
				  storedValue 	 = $(_grid.idJquery).jqGrid('getGridParam', 'data')[indexDataRow].propuestaPedido,
				  lastIdent		 = $(_grid.idJquery).jqGrid('getGridParam', 'data')[reccount-1].ident;
							
			//Si dejan el campo sin informar, se inserta un 0
			if (!$('#' + jqGridCellId).val()){
				$('#' + jqGridCellId).val(0);
			}
			
			//Valor actual del input
			const newCellValue = $('#' + jqGridCellId).val(),
			//Valor guardado en la celda en el data del grid
				  oldCellValue = $(_grid.idJquery).jqGrid('getGridParam', 'data')[indexDataRow].propuestaPedido;

			//Modificar el valor de la celda dentro del data del grid
			//Si encuentra el elemento dentro de la data y si no coinciden los valores
			if (indexDataRow >= 0 && (newCellValue != oldCellValue)) {

				//Deshabilitar botones exportar y guardar
				p70DetalladoMostradorMainModule.exportarBTN.disable();
				p70DetalladoMostradorMainModule.guardarBTN.disable();
				
				/*
				 * Funcion interna que realiza las operaciones necesarias cuando se modifica el valor de la celda
				 */
				var _updateOperations = () => {
					//console.log("Modificacion del valor de la fila con indice " + indexDataRow + " en el data");
					
					//Guardar valores de la fila
					$(_grid.idJquery).jqGrid('saveRow',rowId);
					
					//Poner la fila en modo edicion de nuevo
					$(_grid.idJquery).jqGrid('editRow', rowId, false);
					
					//Recalcular Totales
					areaCajasEuros.refresh(jqGridDataRows);
					
					//Cambiar icono de la celda estado grid
					$(_grid.idJquery).jqGrid('setCell', rowId, 'estadoIcon', "<img src='./misumi/images/modificado.png' title='" + i18nModule._("p70_detallado.mostrador.estado.modificado") + "'>");
					
					//Mostrar el icono de alerta en la bomba
					p70DetalladoMostradorMainModule.bomba.showAlert();
					
					//Marcar el grid como modificado
					_grid.dataGridDirty= true;
					
					//console.log("jqGridCellId:" + jqGridCellId);
					//console.log("rowId:" + rowId);
					//console.log("columName:" + columName);
					//console.log("newCellValue:" + newCellValue);
					//console.log("oldCellValue:" + oldCellValue);
					//console.log("valor almacenado: " +  $(_grid.idJquery).jqGrid('getGridParam', 'data')[indexDataRow].propuestaPedido);
					//console.log(jqGridDataRows);
					
					//Habilitar botones exportar y guardar
					p70DetalladoMostradorMainModule.exportarBTN.enable();
					p70DetalladoMostradorMainModule.guardarBTN.enable();
					
					if (lastIdent != _newId || indexDataRow != (reccount-1)){
						$("#" + $.jgrid.jqID(_newId) + "_propuestaPedido").focus();
					}else{
						$("#pagerP70PedidoDatos_center .ui-pg-input").focus();
					}

					// Copia de los datos del grid
					var gridDataBackup = $(_grid.idJquery).jqGrid('getGridParam', 'data');

					// Guardar los datos en sesion
					localStorage.setItem('backupGrid', JSON.stringify(gridDataBackup));

				}
					
				// MISUMI-421. Actualizar valores estado, grid y bomba, pero no llamar al redondeo si el valor informado es 0
				if (0 == newCellValue ){
					_updateOperations();
					
				} else {
					
					//Parametros
					const paramsRedondeo = 	{ 
					  codCentro 		: p70DetalladoMostradorMainModule.codCentro,
					  referencia 		: rowData.referencia,
					  estado			: rowData.estado,
					  unidadesCaja 		: rowData.unidadesCaja, 
					  pdteRecibirVenta 	: rowData.pdteRecibirVenta, 
					  propuestaPedido 	: newCellValue 
					};

					//console.table(paramsRedondeo);
					
					//Request ajax -> redondeo
					$.ajax({
						type : 'GET',
						url : './detalladoMostrador/redondeo.do',
						data : paramsRedondeo,
						contentType : "application/json; charset=utf-8",
						dataType : "json",
						cache : false,
						success : function(data) {	
							
							//Cambiar valores de celdas por los recibidos del redondeo
							$(_grid.idJquery).jqGrid('setCell', rowId, columName, data.propuestaPedido);
							$(_grid.idJquery).jqGrid('setCell', rowId, 'pdteRecibirVentaGrid', data.pdteRecibirVentaGrid=='' ? null : data.pdteRecibirVentaGrid);
							
							_updateOperations();

						},
						error : function (xhr, status, error){
							handleError(xhr, status, error, locale);
						}
					});
				}

			}

		},

		reset: () =>{ 
			//Reinicio el flag de primera carga
			_grid.firstLoad=true;
			
			//Seteo de datos de grid NO modificados
			_grid.dataGridDirty= false;
			//Reseteo del contenido de la ultima seleccion
			_grid.lastSelected=null;
			
			//Reseteo datos y elimino table
			$(_grid.idJquery).jqGrid("clearGridData");
			$(_grid.idJquery).jqGrid('GridDestroy');
			
			//Creo la tabla y el pager en el DOM
			$(_areaResultados.idJquery)
				.prepend("<table id='"+_grid.idHTML+"'></table>")
				.prepend("<div id='"+_grid.idHTMLPager+"'></div>");
		},
		/**
		 * Posiciona el foco entre los campos editables navegando con las teclas de arriba y abajo
		 * @param key tecla
		 * @param rowId Id de la celda
		 */
		navegationControl: (e) => {
			
			const key 			= e.which,
				  rowId 		= e.target.id;

			//Control teclas arriba y abajo
			if (key == 38 || key == 40 ){ 
				e.preventDefault();
				//obtener los input-text de la pagina del grid
				const $lstEditableCells = $(_grid.idJquery +' .editable');
				let currentIndex = $lstEditableCells.index($("#" + rowId)),
					$cellTarget   = null; 
				
				if (key == 38) {
					$cellTarget = $lstEditableCells[--currentIndex];
				}
				if (key == 40) {
					$cellTarget = $lstEditableCells[++currentIndex];
				}
				//foco en la celda
				if ($cellTarget){
					$cellTarget.select();
				}
			}
			
		},
		lastSelected: null,
		extendGridFuntionality:() => {
			/**
			 * Extiendo la funcionalidad de jqgrid porque con la version que trabajamos no
			 * tiene implementada la funcionalidad de obtener los elementos seleccionadados despues de filtrar
			 */
			var oldFrom = $.jgrid.from;
			
			$.jgrid.from = function (source, initalQuery) {
				var result = oldFrom.call(this, source, initalQuery),
			    old_select = result.select;
				result.select = function (f) {
					_grid.lastSelected = old_select.call(this, f);
					return _grid.lastSelected;
				};
				
				return result;
			};
		},
		/**
		 * Creacion del grid
		 */
		init: () => {

			//Ocultar areas de totales y grid
			areaCajasEuros.hide();
			_areaResultados.hide();
			
			//Reinicio grid
			_grid.reset();
						
			$(_grid.idJquery).jqGrid({
				url : './detalladoMostrador/loadGridDetalleMostrador.do',
				mtype: "GET",
				datatype: "json",
				postData: { 'codCentro': p70DetalladoMostradorMainModule.codCentro},
				contentType: 'application/json',
				ajaxGridOptions: { contentType: 'application/json; charset=utf-8', cache: false	},
				loadonce: true,
				jsonReader: { repeatitems: false, root:'rows', userdata: 'userData' },
				editurl: 'clientArray',
				colModel: [
				    { label: 'Id', name: 'ident', key:true, hidden: true },
				    { label: 'tieneVentas', name: 'tieneVentas', hidden: true},
				    { label: 'Sección', name: 'seccionGrid', hidden: true },
				    { label: 'subcategoria', name: 'subcategoria', hidden: true },
				    { label: 'denomSubcategoria', name: 'denomSubcategoria', hidden: true },
				    { label: 'CodNecesidad', name: 'codNecesidad', hidden: true },
				    { label: 'precioCostoArticulo', name: 'precioCostoArticulo', hidden: true },
				    { label: 'propuestaPedidoAnt', name: 'propuestaPedidoAnt', hidden: true },
				    { label: 'tiradoParasitos', name: 'tiradoParasitos', hidden: true },
				    { label: 'totImporteVentasEspejo', name: 'totImporteVentasEspejo', hidden: true},//para sumatorio para Ventas dia Espejo
				    { label: 'pdteRecibirVenta', name: 'pdteRecibirVenta', hidden: true},//para redondeo
				    { label: 'fechaSgteTransmision', name: 'fechaSgteTransmision', hidden: true},
				    { label: 'fechaVenta', name: 'fechaVenta', hidden: true},
				    { label: 'orden', name: 'orden', hidden: true},
				    { label: 'pvpTarifa', name: 'pvpTarifa', hidden: true},
				    { label: 'diasCubrePedido', name: 'diasCubrePedido', hidden: true},
				    { label: 'ventaEspejoTarifa', name: 'ventaEspejoTarifa', hidden: true },
				    { label: 'ventaEspejoOferta', name: 'ventaEspejoOferta', hidden: true },
				    { label: 'Referencia', name: 'referencia', width: 120 },
				    { label: 'Descripción', name: 'descripcion', width: 440,
				    	formatter: function (cellvalue, options, rowData) {		
				        	//MISUMI-406
							//Mostrar icono en descripcion informando de fecha de entrega o sig pedido diferentes a las de cabecera 
							const fechaSgteTransmision = $.datepicker.formatDate("D dd-M", new Date(rowData.fechaSgteTransmision)),
	            				  fechaVenta 	   	   = $.datepicker.formatDate("D dd-M", new Date(rowData.fechaVenta)),
								  fechaPedido  		   = $(_grid.idJquery).jqGrid('getGridParam', 'userData').fechaPedido,
								  fechaEntrega 		   = $(_grid.idJquery).jqGrid('getGridParam', 'userData').fechaEntrega,
								  diasCubrePedido	   = rowData.diasCubrePedido;
							
							let descripcion 		   = cellvalue,
								titleImgDescripcion    ='', 
								imgDescripcion 		   ='';  

							let tipoAprov = rowData.tipoAprov;
							
							//console.log(" fst:"+ fechaSgteTransmision + " fpedido:" +fechaPedido + " fventa:"+fechaVenta + " fEntrega:"+fechaEntrega);
							
							if (fechaVenta != fechaEntrega){
				            	titleImgDescripcion += ' ' + i18nModule._("p70_detallado.mostrador.descripcion.title.fecha.entrega") + fechaVenta;
				            	imgDescripcion = "<img src='./misumi/images/dialog-confirm-12.png' title='"+titleImgDescripcion+"'>";
				            }
				            if (fechaSgteTransmision != fechaPedido){
				            	
				            	titleImgDescripcion += ' '+ i18nModule._("p70_detallado.mostrador.descripcion.title.fecha.pedido") + fechaSgteTransmision;
				            	//MISUMI-439
				            	//Mostrar dias cubre pedido
				            	if (diasCubrePedido){
				            		titleImgDescripcion += i18nModule._("p70_detallado.mostrador.descripcion.title.fecha.pedido.cubre.dias").replace('{0}', diasCubrePedido);
				            	}
				            	
				            	imgDescripcion = "<img src='./misumi/images/dialog-confirm-12.png' title='"+titleImgDescripcion+"'>";
				            	
				            }
				            
				            if (tipoAprov!=null && tipoAprov=='D'){
				            	let titleTipoAprov = '' + i18nModule._("p70_detallado.mostrador.descripcion.title.referencia");
				            	imgDescripcion = imgDescripcion + "<img src='./misumi/images/red-circle.png' title='"+titleTipoAprov+"'>";
				            }
				        	
				        	return imgDescripcion + descripcion;
				        }
				    },
				    { label: 'tipoAprov', name: 'tipoAprov', hidden: true },
				    { label: 'U/C', name: 'unidadesCaja', formatter: "number" , formatoptions: { decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 1, defaultValue: '0'},align: 'center', width: 60, search:false},
				    { label: 'Stock', name: 'stock', align: 'center', width: 60, search:false , 
				    	formatter: function (cellvalue, options, rowData) {
				    		var isGreen = "";
				    		var stock;
				    		if(cellvalue==null){
				    			stock="";
				    		}else{
				    			stock=utils.formatNumber(cellvalue, 1, 1);
				    		}
				    		if(codArticuloAnt!=null && rowData.referencia==codArticuloAnt && stockAnt!=null && utils.formatNumber(cellvalue)!=utils.formatNumber(stockAnt)){
				    			isGreen = ';color: #32CD32;font-weight: bold;';
				    			codArticuloAnt=null;
				    			stockAnt=null;
				    		}
				    		return "<a href='#' id='p107_stock"+options.rowId+"' style='cursor:pointer"+isGreen+"' onclick='showPopupChangeStock("+rowData.referencia+","+cellvalue+")' title='"+stock+"'>"+stock+"</a>";
				    	}
				    },
				    { label: 'Pdte.', name: 'empujePdteRecibir', align: 'center', width: 75, search:false },
				    { label: 'Tirado', name: 'tirado', align: 'center', formatter: "number", formatoptions: { decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 1, defaultValue: '0', suffix: "%"}, 
				    	cellattr: function (rowId, val, rawObject, cm, rdata) {

				            let parasitos = rawObject.tiradoParasitos; 
				            
				            if (parasitos > 0 ){
				            	parasitos = parasitos.toString().replace('.',',');
				            	let titleTirado = i18nModule._("p70_detallado.mostrador.tirado.title.parasitos") + " " + parasitos + '%';
				            	
				            	return 'style="color:red" title ="'+ titleTirado + '"';
				            }
				            else return '';
				        },width: 90, search:false },
				    { label: 'Vtas. E', name: 'totalVentasEspejo', formatter: "number", formatoptions: { decimalPlaces: 1, defaultValue: '0' },
						    cellattr: function (rowId, val, rawObject, cm, rdata) {
						    	
					            let ventaEspejoTarifa = rawObject.ventaEspejoTarifa;
					            let ventaEspejoOferta = rawObject.ventaEspejoOferta;
					            let ventaEspejoOfertaMod = "";
					            
					            if (ventaEspejoOferta > 0){
					            	ventaEspejoOfertaMod = ventaEspejoOferta.toString().replace('.',',');
					            	let titleVentaEspejo = i18nModule._("p70_detallado.mostrador.ventasEspejo.title.oferta") + " " + ventaEspejoOfertaMod;
						            if (ventaEspejoOferta > ventaEspejoTarifa ){
						            	return 'style="color:red" title ="'+ titleVentaEspejo + '"';
						            }else{
						            	return 'title ="'+ titleVentaEspejo + '"';
						            }
					            }else{
					            	return '';
					            }
						    }, align: 'center', width: 110, search:false },
				    { label: 'Prev.', name: 'previsionVenta', formatter: "number", formatoptions: { decimalPlaces: 1, defaultValue: '0' }, align: 'center', width: 75, search:false },
					{ label: 'Cant.', name: 'propuestaPedido', width: 75,
						editable: true,
						edittype: "text",
						cellEdit: true,
						cellsubmit: 'clientArray',
						align: 'center',
						formatter: "number" , formatoptions: { decimalPlaces: 0, defaultValue: 0},
						search:false,
						editoptions: {
							"autocomplete": "off",
							style: "border: 1px solid #000000", 
							size: 2,
							maxlength: 3,
							//Cuando la celda es EDITABLE controlar el color del texto del input text
							dataInit:function(e){
				                const jqGridCellId = $(e).attr('id'),
				                		   rowId = jqGridCellId.substring(0, jqGridCellId.indexOf("_")),
				                	       estado= $(_grid.idJquery).jqGrid('getCell', rowId, 'estado');
				                
				                //Solo se pueden introducir numeros
				                $(e).filter_input({ regex: '[0-9]' });
				                
				                let color='';
				                
				                switch (estado) {
				                  case 'P':
				                    color='#000000'; //Pendiente= Negro
				                    break; 
				                  case 'R':
				                	  color='#008000'; //Revisada = verde #79FC4E
				                    break;
				                  default:
				                    color='';
				                }
								
								e.style.color = color;
								e.style.fontWeight  = "bold";
				            },
							dataEvents: [
								{//control para el keyup
									type: 'keyup',
									fn: function (e) {
										
										//al pulsar return
										if (e.which == 13) {
											_grid.updateDataGridCellValue(e.target.id);
										}
									}
								},// cierra el control del keyup
								{
									type: 'keydown',
									fn: _grid.navegationControl
								},
								{//control para el blur
									type: 'blur',
									fn: function (e) {
										_newId = _newIdent.guardarIdentDestino(e.target.id);										
										_grid.updateDataGridCellValue(e.target.id);
									}
								},
								{//control para el focus
									type: 'focus',
									fn: function (e) {
									}
								}
							]
						},
						//Cuando la celda es NO EDITABLE controlar color del texto
						cellattr: function (rowId, val, rawObject, cm, rdata) {
							
							const estado = rawObject.estado; 
				            let color='';
			                
			                switch (estado) {
			                  case 'B':
			                	  color='#FF4AFF'; //Bloqueda = fucsia
			                    break;
			                  case 'I':
			                	  color='#FF5353'; //Integrada= rojo 
			                    break;
			                  default:
			                    color='';
			                }
			                
			                return 'style="color:' + color + '"';
				        }
					},
				    { label: ' Ped.', name: 'pdteRecibirVentaGrid', align: 'left', width: 75, search:false },
				    { label: 'Of. A-B', name: 'ofertaAB', align: 'center', width: 210, search:false, 
				    	formatter: function (cellvalue, options, rowData) {		
				    		if (cellvalue){
				    			return "<a href='#' id='linkOfertaAB_"+rowData.referencia+"' onclick='javascript:p70DetalladoMostradorMainModule.popUpOfertas("+rowData.referencia+")' title='"+cellvalue+"'>"+cellvalue+"</a>";			    			
				    		}
				    		return '';
		 				} 
				    },
				    { label: 'Of. C-D', name: 'ofertaCD', align: 'center', width: 210, search:false , 
				    	formatter: function (cellvalue, options, rowData) {		
				    		if (cellvalue){
				    			return "<a href='#' id='linkOfertaCD_"+rowData.referencia+"' onclick='javascript:p70DetalladoMostradorMainModule.popUpOfertas("+rowData.referencia+")' title='"+cellvalue+"'>"+cellvalue+"</a>";			    			
				    		}
				    		return '';
		 				}
				    },
				    { label: 'PVP', name: 'pvp', formatter: "currency" , formatoptions: {	decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2, suffix:"", defaultValue: '0.00'},
				    	//Cuando PVP es diferente de PVP Tarifa mostrar en color rojo
						cellattr: function (rowId, val, rawObject, cm, rdata) {
							
				            const color = rawObject.pvpTarifa == rawObject.pvp ? '' : '#FF0000';
			                
			                return 'style="color:' + color + '"';
				        },
				    	align: 'center', width: 75, search:false
				    },
				    { label: 'Margen', name: 'margen', formatter: "currency" , formatoptions: {	decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2, suffix:"%", defaultValue: '0.00'}, align: 'center', width: 100, search:false},
				    { label: 'Gama', name: 'tipoGama', width: 110, search:false},
				    { label: 'estado', name: 'estado', hidden:true },
				    { label: 'descripcionError', name: 'descripcionError', hidden:true },
				    { label: 'estadoGrid', name: 'estadoGrid', hidden:true },
				    { label: 'Estado', name: 'estadoIcon',align: 'center' , width: 75, search:false,
				    	formatter: function (cellvalue, options, rowData) {		
				    		//Mostrar icono de estado
							if (rowData.estadoGrid == 3 /*Modificado*/){
				    			return 	"<img src='./misumi/images/floppy.png' title='Guardado'>";		    			
				    		} else if (rowData.estadoGrid < 0 /*error*/) {
				    			return "<img src='./misumi/images/dialog-error-24.png' title='"+rowData.descripcionError+"'>"
				    		}
				    		
				    		return undefined === cellvalue ? '' : cellvalue;
		 				} 
				    },
				],
				hidegrid: false, //false, para ocultar el boton que colapsa el grid.
				viewrecords: true,
				rowNum: 100,
				rowList: [5, 10, 20, 30, 50, 100],
				height: "auto",// por defecto 150px
				autowidth: true,
				altRows: true,
				sortable: false,//Con subgrid no se permite ordenacion
				ignoreCase: true,//La propiedad ignoreCase: true en el buscador no funciona
				pager: _grid.idJqueryPager,
				loadComplete: function (data) {
					
	  			  	//Funciones a ejecutar solo en la primera carga o solo en las posteriores
					_grid.manageFirstAndNextLoads();

					// Recuperar los datos para restaurar
					var datosGuardados = JSON.parse(localStorage.getItem('backupGrid') || "[]");
//					localStorage.removeItem('backupGrid');

					//Configuracion de última seleccion. Se utiliza en la exportacion de datos para saber los que estan "seleccionados en el momento de la peticion"   
//					this.p.lastSelected = _grid.lastSelected;
										
					const idRows = $(this).jqGrid('getDataIDs'), 
							l = idRows.length - 1;

					var valorStock, mostrarStock=0;
					
					//Poner en modo edicion las celdas de la fila que tengan estado P o R					
					for (let i = l; i >= 0; i--) {
						const rowId = idRows[i],
						estado 			 = $(_grid.idJquery).jqGrid('getCell', rowId, 'estado'),
						tieneVentas 	 = $(_grid.idJquery).jqGrid('getCell', rowId, 'tieneVentas');

						 //Ocultar simbolo '+' de columna desplegable si no tiene referencias de nivel inferior
						if (tieneVentas !='S'){ 
							$(_grid.idJquery+" #"+rowId+" td.sgcollapsed:first-child").unbind('click').html('');
						}

						//Si el estado es Pendiente o Revisada la columna debe de ser editable
						if (estado == 'P' || estado == 'R'){
							// Buscar la fila en datosGuardados
						    const filaGuardada = datosGuardados.find(f => f._id_ == rowId);

						    if (filaGuardada) {
						        // Asignar el valor guardado del campo "propuesta_pedido" a la celda del grid
						        $(_grid.idJquery).jqGrid('setCell', rowId, 'propuestaPedido', filaGuardada.propuestaPedido);
						    }

						    //Celda editable
							$(this).jqGrid('editRow', rowId, false);

						}

						// *********************************************************************************** 
						// Obtención del valor de Stock para saber si se debe mostrar o no la columna "Stock".
						// Dependerá de si el centro está parametrizado. (52_DETALLADO_MOSTRADOR_STOCK).
						// --------------------------------------------------------------------------------------
						// Aprovecho que la columna Stock sólo tiene valores cuando el centro está parametrizado.
						// El código se encuentra en "p70DetalladoMostradorController.java" que se desarrolló para
						// la MISUMI-526.
						// *********************************************************************************** 
						valorStock = $(_grid.idJquery).jqGrid('getCell', rowId, 'stock');
						const posInicial = valorStock.search(">")+1;
						valorStock = valorStock.substring(posInicial); // Me quedo con el resto de la cadena.
						const posFinal = valorStock.search("<");

						valorStock = valorStock.substring(0, posFinal); // Me quedo con el valor de la celda que se muestra en la columna "Stock".

						if (typeof(mostrarStock)!=='undefined' && mostrarStock== 0 && valorStock!==null && valorStock!==""){
							mostrarStock = 1;
						}

					}

					// Si el Centro NO está parametrizado, se ocultará la columna "Stock".
					if (mostrarStock==0){
						$(_grid.idJquery).jqGrid('hideCol',["stock"]);
					}

				},
				grouping: true,
				groupingView: {	
					groupField: ["seccionGrid"],
					groupColumnShow: [false],
					minusicon: "ui-icon-minus", // "ui-icon-circlesmall-minus"
					plusicon: "ui-icon-plus",  // "ui-icon-arrowstop-1-s"
					groupText: [
						"Subcategoría: <b>{0}</b>"
					],
					groupOrder: ["asc"],
					groupCollapse: false
				},
				subGrid: true,
				subGridRowExpanded: function (subgridDivId, rowId) {

					const subgridTableId = subgridDivId + "_t",
								 pagerId = "p_" + subgridTableId,
						   parentRowData = $(this).jqGrid("getLocalRow", rowId);
					
					$("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table><div id='" + pagerId + "'></div>");
					$("#" + subgridTableId).jqGrid({
						url : './detalladoMostrador/loadSubgridDetalleMostrador.do?ident='+parentRowData.ident,
						mtype: "GET",
						datatype: "json",
						contentType: 'application/json',
						ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
						loadonce: true,
						jsonReader: { repeatitems: false, root:'rows' },
						colModel: [
						    { label: 'Id', name: 'ident', hidden: true, key:true},
						    { label: 'ventaEspejoTarifa', name: 'ventaEspejoTarifa', hidden: true },
						    { label: 'ventaEspejoOferta', name: 'ventaEspejoOferta', hidden: true },
						    { label: 'Referencia', name: 'referencia', width: 52 },
						    { label: 'Descripción', name: 'descripcion', width: 199 },
						    { label: 'U/C', width: 27},
						    { label: 'Stock', name: 'stock', width: 27},
						    { label: 'Pdte.', width: 34.5},
						    { label: 'Tirado', name: 'tirado', align: 'center', formatter: "integer", formatoptions: { suffix: "%"},  width: 41, search:false },
						    { label: 'Vtas. E', name: 'totalVentasEspejo', formatter: "number", formatoptions: { decimalPlaces: 1, defaultValue: '0' },
						    		cellattr: function (rowId, val, rawObject, cm, rdata) {
							            let ventaEspejoTarifa = rawObject.ventaEspejoTarifa;
							            let ventaEspejoOferta = rawObject.ventaEspejoOferta;
							            let ventaEspejoOfertaMod = "";

							            if (ventaEspejoOferta > 0){
							            	ventaEspejoOfertaMod = ventaEspejoOferta.toString().replace('.',',');
							            	let titleVentaEspejo = i18nModule._("p70_detallado.mostrador.ventasEspejo.title.oferta") + " " + ventaEspejoOfertaMod;
								            if (ventaEspejoOferta > ventaEspejoTarifa ){
								            	return 'style="color:red" title ="'+ titleVentaEspejo + '"';
								            }else{
								            	return 'title ="'+ titleVentaEspejo + '"';
								            }
							            }else{
							            	return '';
							            }
								    }, align: 'center', width: 50
							},
//						    { label: 'Prev.', width: 34.5 },
						    { label: 'Prev.', name: 'previsionVenta', formatter: "number", formatoptions: { decimalPlaces: 1, defaultValue: '0' }, align: 'center', width: 34.5}, 
							{ label: 'Cant.', width: 34.5 },
						    { label: ' Ped.', width: 34.5 },
						    { label: 'Of. A-B', name: 'ofertaAB', align: 'center', width: 95 },
						    { label: 'Of. C-D', name: 'ofertaCD', align: 'center', width: 95 },
						    { label: 'PVP', name: 'pvp', formatter: "currency" , formatoptions: { decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2, suffix:"", defaultValue: '0.00'}, align: 'center', width: 34.5 },
						    { label: 'Margen', name: 'margen', formatter: "currency" , formatoptions: {	decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2, suffix:"%", defaultValue: '0.00'}, align: 'center', width: 45 },
						    { label: 'Gama', name: 'tipoGama', width: 50 },
						    { label: 'Estado', width: 35 },
						],
						rowNum: 10,
						idPrefix: "s_" + rowId + "_",
						// pager: "#" + pagerId,
						autowidth: true,
						shrinkToFit:false,
						height: "auto",
						gridview: true,
						autoencode: true,
						sortorder: "asc",
						rowattr: function (rd) {
			                return { "class": "ui-state-highlight" };
			            },
						sortable: true,
						loadComplete: function (){
							//Ocultar header de la tabla
							$(".ui-subgrid .ui-jqgrid-labels").hide();
							//ocultar scroll horizontal
							$(this).closest(".ui-jqgrid-bdiv").css({"overflow-x":"hidden"});
							
							var valorStock;

							// *********************************************************************************** 
							// Obtención del valor de Stock para saber si se debe mostrar o no la columna "Stock".
							// Dependerá de si el centro está parametrizado. (52_DETALLADO_MOSTRADOR_STOCK).
							// --------------------------------------------------------------------------------------
							// Aprovecho que la columna Stock sólo tiene valores cuando el centro está parametrizado.
							// El código se encuentra en "p70DetalladoMostradorController.java" que se desarrolló para
							// la MISUMI-526.
							// *********************************************************************************** 
							valorStock = $(_grid.idJquery).jqGrid('getCell', rowId, 'stock');
							const posInicial = valorStock.search(">")+1;
							valorStock = valorStock.substring(posInicial); // Me quedo con el resto de la cadena.
							const posFinal = valorStock.search("<");

							valorStock = valorStock.substring(0, posFinal); // Me quedo con el valor de la celda que se muestra en la columna "Stock".

							if (!(valorStock!==null && valorStock!=="")){
								$("#" + subgridTableId).jqGrid('hideCol',["stock"]);
							}
							
						}
					});
				},
				caption: "Detallado Mostrador"
			})
			.jqGrid('filterToolbar', {
					autosearch: true,
					stringResult: true,
					searchOnEnter: true,
					searchOperators: false,
					defaultSearch: "cn",
					ignoreCase: true
			})
			.jqGrid('navGrid',
				$(_grid.pager), { search: false, edit: false, add: false, del: false, view: false, refresh: false }, {}, {}, {},
				{ multipleSearch: true, multipleGroup: false, closeAfterSearch: true, closeOnEscape: true }
			);

			//Adaptar anchura del grid al contenido 
			$(_grid.idJquery).jqGrid('setGridWidth', $("#contenidoPagina").width(), true);
			
			//Incluir funcionalidad a la del grid por defecto
			_grid.extendGridFuntionality();

		}
	};

//	function _guardarIdentDestino(ident){
//		_newIdent = ident.substring(0, ident.indexOf("_"));
//	}
	
	return {
		tableId : _grid.idJquery,
		loadgrid : _grid.init,
		getDataRows : _grid.getDataRows, 
		isDataGridDirty: ()=>_grid.dataGridDirty,
		areaCajasEuros: areaCajasEuros
	}
	
})();

function afterIframe(data){

	document.getElementById('iframePopupChangeStock').contentWindow.document.getElementById("LogoCerrar").style.display = "none"; 

	const action = document.getElementById('iframePopupChangeStock').contentWindow.document.getElementsByTagName('form')[0].action;
	if (action.indexOf("P12")!=-1 && dialogPopupChangeStock!=null){
		dialogPopupChangeStock.dialog('close');
		detalladoMostradorListModule.loadgrid();
	}
}

function showPopupChangeStock(codArticulo,stock){
	codArticuloAnt=codArticulo;
	stockAnt=stock;
	var url = "./pdaP28CorreccionStockInicio.do?codArt="+codArticulo+"&origen=DR&calculoCC=S";
	
    var iframe = $('<iframe id="iframePopupChangeStock" onload="afterIframe(this);" frameborder="0" marginwidth="0" marginheight="0" allowfullscreen></iframe>');
    
    iframe.attr({
        width: 240,
        height: 320,
        src: url
    });

	dialogPopupChangeStock = $("<div id='popupChangeStock'></div>").append(iframe).appendTo("body").dialog({
        autoOpen: false,
        modal: true,
        resizable: false,
        width: "auto",
        height: "auto",
        close: function () {
            iframe.remove();
        }
    });
	
	dialogPopupChangeStock.dialog("option", "title", "Cambiar Stock").dialog("open");
}

