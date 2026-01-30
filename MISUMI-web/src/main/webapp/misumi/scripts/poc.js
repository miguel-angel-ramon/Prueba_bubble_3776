var pocModule = (function () {


	var saludo = () => {
		console.log("Hola Mundo desde el modulo");
	}


	var createPoCJqGrid = (grid) => {

		console.log(grid);

		function imageFormatEst(cellValue, opts, rowObject) {

			// console.log(rowObject);
			// console.log(opts);

			return "<img src='./misumi/images/modificado.png' class='p107_imgCenter' title='MODIFICADO'/>";

		}


		$(grid.id).jqGrid({
			url: './misumi/resources/mockPoC.json',
			editurl: 'clientArray',
			mtype: "GET",
			datatype: "local",
			contentType: 'application/json',
			ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
			jsonReader: { repeatitems: false },
			colModel: [
				{ label: 'OrderID', name: 'OrderID', key: true, width: 155 },
				{ label: 'Customer ID', name: 'CustomerID', width: 70 },
				{ label: 'Order Date', name: 'OrderDate', width: 150 },
				{
					label: 'Freight',
					name: 'Freight',
					width: 150,
					formatter: 'number',
					editable: true
					// summaryTpl : "<b>{0}</b>",
					// summaryType: "sum"
				},
				{ label: 'Employee ID', name: 'EmployeeID', width: 150 },
				{
					"name": "Estado",
					"width": 80,
					"formatter": imageFormatEst,
					"fixed": true,
					"hidden": false,
					"sortable": false,
					"search": false

				}
			],
			loadonce: true,
			gridview: true,
			viewrecords: true,
			rowList: [20, 30, 50],
			// height: 250,
			height: "auto",// por defecto 150px
			// width: auto,
			autowidth: true,
			rowNum: 20,
			altRows: true,
			sortname: 'OrderDate',
			pager: grid.pager,
			onSelectRow: editRow, // the javascript function to call on row click.
			// will ues to to put the row in edit mode
			grouping: true,
			groupingView: {
				groupField: ["CustomerID", "EmployeeID"],
				groupColumnShow: [true, true],
				groupCollapse: false,
				minusicon: "ui-icon-minus", // "ui-icon-circlesmall-minus"
				plusicon: "ui-icon-plus",  // "ui-icon-arrowstop-1-s"
				groupText: [
					"CustomerID: <b>{0}</b>",
					"EmployeeID: <b>{0}</b>"
				],
				groupOrder: ["asc", "asc"],
				// groupSummary: [true, false],
				// groupSummaryPos: ['header', 'header'],
				groupCollapse: true
			},
			loadComplete: function (data) {
				// console.log(data);
				$(".minuevarowClass", "#jqGridPoC").css({ display: "none" });

			},
			rowattr: function (rd) {

				if (rd.CustomerID == 'ANTON' && rd.OrderID == 10573) {
					console.log(rd);
					return { "class": "minuevarowClass" };
				}
				else return '';
			}
		})
			.jqGrid('filterToolbar', {
				autosearch: true,
				stringResult: true,
				searchOnEnter: false,
				searchOperators: false,
				autosearchDelay: 200,
				defaultSearch: "cn"
			})
			.jqGrid('navGrid',
				$(grid.pager), { search: false, edit: false, add: false, del: false, view: false, refresh: false }, {}, {}, {},
				{ multipleSearch: true, multipleGroup: false, closeAfterSearch: true, closeOnEscape: true }
			);



		var lastSelection;

		// Editar
		function editRow(id) {
			if (id && id !== lastSelection) {
				var grid = $(grid.id);
				grid.jqGrid('restoreRow', lastSelection);
				grid.jqGrid('editRow', id, {
					keys: true,
					onEnter: function (rowid, options, event) {
						if (confirm("Save the row with ID: " + rowid) === true) {
							$(this).jqGrid("saveRow", rowid, options);
						}
					}
				});
				lastSelection = id;
			}
		}
	}


	var createPoCSubGrid = (grid) => {

		// console.log(grid);

		$(grid.id).jqGrid({
			url: './misumi/resources/mockPoC.json',
			mtype: "GET",
			datatype: "local",
			contentType: 'application/json',
			ajaxGridOptions: { contentType: 'application/json; charset=utf-8', cache: false },
			loadonce: true,
			jsonReader: { repeatitems: false },
			editurl: 'clientArray',
			colModel: [
				{ label: 'OrderID', name: 'OrderID', key: true, width: 155 },
				{ label: 'Customer ID', name: 'CustomerID', width: 70 },
				{ label: 'Order Date', name: 'OrderDate', width: 150 },
				{
					label: 'Freight',
					name: 'Freight',
					width: 150,
					//				   formatter: 'number',
					// "sortable" : false,
					editable: true,
					edittype: "text",
					cellEdit: true,
					cellsubmit: 'clientArray',
					editoptions: {
						size: 10,
						maxlength: 3,
						dataEvents: [

							{//control para el keyup
								type: 'keyup',
								fn: function (e) {

									let key = e.which; //para soporte de todos los navegadores
									if (key == 13) {
										updateDataGridCellValue(e.target.id);
									}
								}
							}// cierra el control del keyup
							,
							{//control para el blur
								type: 'blur',
								fn: function (e) {

									//idInterno de la celda en formato idrow_columname
									let jqGridRowId = e.target.id,
										//Datos locales del grid
										jqGridDataRows = $("#jqGridPoC").jqGrid('getGridParam', 'data'),
										rowId = jqGridRowId.substring(0, jqGridRowId.indexOf("_")),
										columName = jqGridRowId.substring(jqGridRowId.indexOf("_") + 1),
										//Indice de la fila dentro del array de datos
										index = jqGridDataRows.findIndex(obj => obj._id_ == rowId),
										//Valor del input
										newCellValue = $('#' + jqGridRowId).val();

									//Valor inicial
									oldCellValue = $("#jqGridPoC").jqGrid('getGridParam', 'data')[index].Freight;

									console.log("newCellValue:" + newCellValue + "- oldCellValue:" + oldCellValue);

									if (newCellValue !== oldCellValue) {
										console.log("guardar");
									}


								}
							}// cierra el control del blur
						]
					},

				},
				{ label: 'Employee ID', name: 'EmployeeID', width: 150 },
				{
					"name": "Estado",
					"width": 80,
					// "formatter" : imageFormatEst,
					"fixed": true,
					"hidden": false,
					"sortable": false,
					"search": false

				}
			],
			//			cellEdit : true,
			//			cellsubmit : "clientArray",
			// gridview: true,
			hidegrid: false, //false, para ocultar el boton que colapsa el grid.
			viewrecords: true,
			rowList: [20, 30, 50],
			height: "auto",// por defecto 150px
			autowidth: true,
			altRows: true,
			sortname: 'OrderDate',
			pager: grid.pager,
			sortname: 'id',
			viewrecords: true,
			sortorder: "desc",
			multiselect: false,
			afterSaveCell: function (rowid, cellname, value, iRow, iCol) {
				console.log("afterSaveCell");
			},
			beforeSaveCell: function (rowid, cellname, value, iRow, iCol) {
				console.log("beforeSaveCell");
			},
			//			onSelectRow: editRow,
			onSelectCell: function (rowid, celname, value, iRow, iCol) {
				console.log("onSelectCell");
			},
			gridComplete: function () {
				console.log("gridComplete");
			},
			loadComplete: function (data) {
				console.log("loadComplete");

				//Poner en modo edicion todas las celdas editables del grid
				var $this = $(this), ids = $this.jqGrid('getDataIDs'), i, l = ids.length;
				for (i = 0; i < l; i++) {
					$this.jqGrid('editRow', ids[i], false);


					//Solo se pueden introducir numeros
					$("#" + ids[i] + "_Freight").filter_input({ regex: '[0-9]' });

					//Aplicar formateo
					//$("#"+ids[i]+"_Freight").formatNumber({format:"0",locale:"es", round:false});

					//Añadir observable evento salir del inputtext
					//        	        $("#"+ids[i]+"_Freight").focusout(function() {
					//				    	
					//        	        	console.log("Modificacion del elemento: "+ this.id +" "+this.value);
					//				    });

					//        	        $("#"+ids[i]+"_Freight").on('blur', function() {
					//				    	
					//        	        	console.log("Modificacion del elemento: "+ this.id +" "+this.value);
					//				    });


					//Situamos el foco fuera de los input text, en el buscar
					$("#p70_btn_buscar").focus();

					/* Para cambiar el valor y estilos de una celda
					 *
					rowid,colname, data,class,properties,forceup
					This method can change the content of particular cell and can set class or style properties. Where:
						rowid the id of the row,
						colname the name of the column (this parameter can be a number (the index of the column) beginning from 0
						data the content that can be put into the cell. If empty string the content will not be changed
						class if class is string then we add a class to the cell using addClass; if class is an array we set the new css properties via css
						properties sets the attribute properies of the cell,
						forceup If the parameter is set to true we perform update of the cell instead that the value is empty
					*/
					//$this.jqGrid('setCell',ids[i],'Freight',17,{'font-weight':'bold','color':'#ff4aff','text-align':'center'},{ title: 'Perro'});
				}

			},
			// Incluir clases en las filas
			//	         rowattr: function (rd) {                    

			//	        	 console.log(rd);
			//	        	 $(grid.id).editRow(rd)

			// if (rd.CustomerID == 'ANTON' && rd.OrderID == 10573){
			// console.log(rd);
			// return { "class": "minuevarowClass" };
			// }
			// else return '';

			//			},
			grouping: true,
			groupingView: {
				// groupField: ["CustomerID", "EmployeeID"],	
				groupField: ["CustomerID"],
				// groupColumnShow: [true, true],
				groupColumnShow: [true],
				minusicon: "ui-icon-minus", // "ui-icon-circlesmall-minus"
				plusicon: "ui-icon-plus",  // "ui-icon-arrowstop-1-s"
				groupText: [
					"CustomerID: <b>{0}</b>"
					// ,"EmployeeID: <b>{0}</b>"
				],
				//groupOrder: ["asc", "asc"],
				groupOrder: ["asc"],
				// groupSummary: [true, false],
				// groupSummaryPos: ['header', 'header'],
				groupCollapse: false
			},
			subGrid: true,
			subGridOptions: {
				plusicon: "ui-icon-triangle-1-e",
				minusicon: "ui-icon-triangle-1-s",
				openicon: "ui-icon-arrowreturn-1-e"
			},
			subGridRowExpanded: function (subgrid_id, row_id) {

				var subgrid_table_id = subgrid_id + "_t",
					pager_id = "p_" + subgrid_table_id;
				// var localRowData = $(this).jqGrid("getLocalRow", row_id);
				console.log(subgrid_id);
				$("#" + subgrid_id).html("<table id='" + subgrid_table_id + "'></table><div id='" + pager_id + "'></div>");
				$("#" + subgrid_table_id).jqGrid({
					url: './misumi/resources/mockPoCSubGrid.json',
					mtype: "GET",
					datatype: "json",
					contentType: 'application/json',
					ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
					loadonce: true,
					jsonReader: { repeatitems: false },
					colNames: ['OrderID(s)', 'CustomerID(s)', 'EmployeeID(s)', 'ShipName(s)'],
					colModel: [
						{ name: 'OrderID', width: 80, key: true },
						{ name: 'CustomerID', width: 130 },
						{ name: 'EmployeeID', width: 70, align: "right" },
						{ name: 'ShipName' }
					],
					rowNum: 20,
					idPrefix: "s_" + row_id + "_",
					// pager: "#" + pager_id,
					autowidth: true,
					height: "auto",
					gridview: true,
					autoencode: true,
					sortorder: "asc",
					altclass: "ui-priority-secondary",
					altRows: false,
					sortable: true
				});
			},

			caption: "Subgrid Example"
		})
			.jqGrid('filterToolbar', {
				autosearch: true,
				stringResult: true,
				searchOnEnter: false,
				searchOperators: false,
				autosearchDelay: 200,
				defaultSearch: "cn"
			})
			.jqGrid('navGrid',
				$(grid.pager), { search: false, edit: false, add: false, del: false, view: false, refresh: false }, {}, {}, {},
				{ multipleSearch: true, multipleGroup: false, closeAfterSearch: true, closeOnEscape: true }
			);

		var lastSelection;

		// Editar
		function editRow(id) {

			if (id && id !== lastSelection) {
				$(this).jqGrid('restoreRow', lastSelection);
				$(this).jqGrid('editRow', id, {
					keys: true,
					onEnter: function (rowid, options, event) {
						console.log("Edicion");
						if (confirm("Save the row with ID: " + rowid) === true) {
							$(this).jqGrid("saveRow", rowid, options);
						}
					}
				});

				//               $(this).jqGrid('editRow',id, true);
				lastSelection = id;
			}
		}


		/**
		 * Modifica el valor interno de la celda
		 * @param {*} jqGridCellId Id de la celda
		 */
		var updateDataGridCellValue = (jqGridCellId) => {

			//idInterno de la celda en formato idrow_columname
			// let jqGridCellId = e.target.id,
			
			//Datos locales del grid
			let	jqGridDataRows = $("#jqGridPoC").jqGrid('getGridParam', 'data'),
				rowId = jqGridCellId.substring(0, jqGridCellId.indexOf("_")),
				columName = jqGridCellId.substring(jqGridCellId.indexOf("_") + 1),
				//Indice de la fila dentro del array de datos
				indexDataRow = jqGridDataRows.findIndex(obj => obj._id_ == rowId),
				//Valor actual del input
				newCellValue = $('#' + jqGridCellId).val();
				//Valor actual de la celda en el grid
				oldCellValue = $("#jqGridPoC").jqGrid('getGridParam', 'data')[indexDataRow].Freight;


			//Modificar el valor de la celda dentro del data del grid
			// Si encuentra el elemento en 
			if (indexDataRow >= 0 && (newCellValue !== oldCellValue)) {
				console.log("guardar");
			
				console.log("Modificacion del valor de la fila con indice " + index + " en el data");
				// $("#jqGridPoC").jqGrid('getGridParam', 'data')[index].Freight = newCellValue;
				$("#jqGridPoC").jqGrid("saveRow", rowId);
				$("#jqGridPoC").jqGrid('editRow', rowId, false);
				
			} else {
				//Restaurar el valor anterior
				$('#' + jqGridCellId).val(oldCellValue);
				$("#jqGridPoC").jqGrid("saveRow", rowId);
				$("#jqGridPoC").jqGrid('editRow', rowId, false);											
			}
			
			console.log("jqGridCellId:" + jqGridCellId);
			console.log("rowId:" + rowId);
			console.log("columName:" + columName);
			console.log("newCellValue:" + newCellValue);
			console.log("oldCellValue:" + oldCellValue);
			console.log("valor almacenado: " + $("#jqGridPoC").jqGrid('getGridParam', 'data')[indexDataRow].Freight);
			console.log(jqGridDataRows);
		}

	}


	var initModule = () => {

		let grid = {
			id: '#jqGridPoC',
			pager: '#jqGridPoCPager'
		}


		$(document).ready(function () {

			$("#p70_AreaResultados").css("display", "block");

			$("#p70_btn_buscar").on("click", function () {
				console.log("Buscar");
				$("#jqGridPoC").jqGrid('setGridParam', { datatype: 'json' }).trigger('reloadGrid');
			});

			$("#p70_btn_guardar").on("click", function () {
				console.log("guardar");
				let jqGridDataRows = $("#jqGridPoC").jqGrid('getGridParam', 'data');
				console.log(jqGridDataRows);
			});

			// createPoCJqGrid(grid);

			createPoCSubGrid(grid);


		});

	}

	return {
		init: initModule,
		saludar: saludo
	}

})();

pocModule.saludar();
pocModule.init();

