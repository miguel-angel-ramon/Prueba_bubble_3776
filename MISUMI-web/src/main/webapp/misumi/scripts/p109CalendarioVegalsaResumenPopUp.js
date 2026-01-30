var grid= null;

/*Clase de constantes para el GRID*/
var GridP109 = function(locale){
	// Atributos
	this.name = "gridP109"; 
	this.nameJQuery = "#gridP109"; 
	this.i18nJSON = './misumi/resources/p109CalendarioVegalsaPopUp/p109CalendarioVegalsaPopUp_' + locale + '.json';
	this.colNames = null;
	this.cm = [
		{
			"name" : "codMapa",
			"index":"COD_MAPA",
			"width" : 20,
			"align":"center"
		}
		,{
			"name" : "fechaPedido",
			"index":"FECHA_PEDIDO", 
			"width" : 25,
			"align":"center"
		}
		,{
			"name" : "diaSemPedido",
			"index": "DIA_SEM_PED", 
			"width" : 25,
			"align":"center"
		}
		,{
			"name" : "horaPedido",
			"index":"HORA_PED", 
			"width" : 25,
			"align":"center"
		}
		,{
			"name" : "fechaReposicion",
			"index":"FECHA_REPO", 
			"width" : 25,
			"align":"center"
		}
		,{
			"name" : "diaSemReposicion",
			"index": "DIA_SEM_REPO", 
			"width" : 30,
			"align":"center"   
		}
		,{
			"name" : "turnoReposicion",
			"index":"TURNO_REPO", 
			"width" : 20,
			"align":"center"		
		}
		,{
			"name" : "marcador",
			"index":"MARCADOR", 
			"hidden" : true
		}
	];
		
	this.sortIndex = null;
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.pagerName = "pagerP109"; 
	this.pagerNameJQuery = "#pagerP109";
	this.title = null;
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null;
	this.myColumnStateName = this.name+'.colState';
    this.myColumnsState = null;
    this.isColState = null;
    this.firstLoad = true;
    this.modificado =false;
	
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
	};
	
	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	};
	
	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	}; 
	
	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}
	};
	
	this.getSortOrder = function getSortOrder () {
		
		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}
	};
    
    this.saveColumnState = function (perm) {
         var colModel =jQuery(grid.nameJQuery).jqGrid('getGridParam', 'colModel'); 
         var i;
         var l = colModel.length;
         var colItem; 
         var cmName;
         var postData = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'postData');
         var columnsState = {
                 search: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'search'),
                 page: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'page'),
                 sortname: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'sortname'),
                 sortorder: jQuery(grid.nameJQuery).jqGrid('getGridParam', 'sortorder'),
                 permutation: perm,
                 colStates: {}
             };
          var colStates = columnsState.colStates;

         if ((typeof(postData) !== 'undefined') && (typeof (postData.filters) !== 'undefined')) {
             columnsState.filters = postData.filters;
         }

         for (i = 0; i < l; i++) {
             colItem = colModel[i];
             cmName = colItem.name;
             if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                 colStates[cmName] = {
                     width: colItem.width,
                     hidden: colItem.hidden
                 };
             }
         }
         
         if (typeof window.localStorage !== 'undefined') {
             window.localStorage.setItem(grid.myColumnStateName, JSON.stringify(columnsState));
         }
     };
     this.getFilters = function (){
    	 var postData = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'postData');
    	 return postData;
     }
     
     this.getSearch= function (){
    	 var postData = jQuery(grid.nameJQuery).jqGrid('getGridParam', 'postData');
    	 if (typeof(postData) !== 'undefined')
    		 return postData._seach;
    	 return false;
     }
}


//Renderiza el grid
function createGridP109(grid) {

	$(grid.nameJQuery).jqGrid({
		datatype: "local",
		colNames:grid.colNames,
	    colModel:grid.cm,
		headertitles: true ,
		colNames : grid.colNames,
		colModel : grid.cm,
		gridview: true,
		height : "auto",
		autowidth : true,
		width : "auto",
		rownumbers : true,
		rownumWidth:40,
		autoencode:true,//codifica html
		pager : grid.pagerNameJQuery,
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		viewrecords : true,
		caption : grid.title,
		altclass: "ui-priority-secondary",
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		index: grid.sortIndex,
		sortname: grid.sortIndex,
		sortorder: grid.sortOrder,
		emptyrecords : grid.emptyRecords,
		rowattr: function (rd) {
			let clase = "";

			if (rd.marcador=='E'){
				clase='p109_marcador_row_red';
			}
			else if(rd.marcador=='X'){
				clase='p109_marcador_row_green'
			}
            return { "class": clase};
        },
		loadComplete : function(data) {
			grid.actualPage = data.page;
			grid.localData = data;
			grid.sortIndex = null;
			grid.sortOrder = null;
			$("#AreaResultados .loading").css("display", "none");	
			if (grid.firstLoad){
				$(grid.nameJQuery).jqGrid('setGridWidth', modalCalendarioVegalsa.getWidthMax(), true);
			}
		},
		onPaging : function(postdata) {
			grid.sortIndex = null;
			grid.sortOrder = null;
			reloadData(grid, 'S', 'paging');
			return 'stop';
		},
		loadError : function (xhr, status, error){
			handleError(xhr, status, error, locale);
        },

		resizeStop: function () {
			grid.modificado = true;
			jQuery(grid.nameJQuery).jqGrid('setGridWidth', modalCalendarioVegalsa.getWidthMax(), false);
        },
		onSortCol : function (index, columnIndex, sortOrder){
			grid.sortIndex = index;
			grid.sortOrder = sortOrder;
			reloadData(grid, 'S',"sort");
			return 'stop';
		},
		beforeRequest : function() {
			reloadData(grid,'S', 'main');
		}
		
	 });
	
	$(grid.nameJQuery).jqGrid('filterToolbar',{ stringResult: true }); 
	$(grid.nameJQuery).jqGrid('setGridWidth', modalCalendarioVegalsa.getWidthMax(), true);

}


var reloadData = function (grid, recarga, origen) {

	//Capturo los datos de busqueda introducidos y los convierto a JSON para pasarselos al controller
	var objJson = getFiltrosJson(grid);

	let codMapa = mapasVegalsaCmb.getValue();
	let url = './calendarioVegalsa/loadDetalleCalendario.do';
	if (codMapa.length>0 && codMapa != "0" && codMapa != "null"){
		url += '?codMapa='+codMapa;
	}
	
    //Llamo al servidor para saber cuantos registros me devuelve la consulta
    $.ajax({
    	type : 'POST',
		url : url,
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
    	success: function(data) {
    			//Si la busqueda no devuelve registros, muestro mensaje.
    			$(grid.nameJQuery)[0].addJSONData(data);
    			
	      		jQuery(grid.nameJQuery).jqGrid('setGridWidth', modalCalendarioVegalsa.getWidthMax(), true);
    	},
    	error : function (xhr, status, error){
				handleError(xhr, status, error, locale);				
	    }			
    });
		
}


/*
 * Obtiene todos los filtros en json para ser pasados como parametro GridFilters
 * @param grid
 * @returns filtros en json para obtener los registros deseados
 */ 
var getFiltrosJson = function (grid){
	
	var gridFilter= new GridFilters();
	var filtros=null;
	var rules=new Array();
	var filtroPostData=grid.getFilters().filters;
	
	if (typeof filtroPostData != 'undefined'){
		var objRules=JSON.parse(filtroPostData).rules;
		
		//Si se han introducido parametros (en los campos de busqueda de la tabla) los cargo
		if (objRules.length>0){
		
			for(var i=0;i<objRules.length; i++){
				var objRules=JSON.parse(filtroPostData).rules;
				rules.push(new Rules(objRules[i].field,objRules[i].op,objRules[i].data));
			}
			
			filtros= new Filters("AND",rules);
		}
	
	}
	
	gridFilter=new GridFilters(grid.getActualPage(), grid.getRowNumPerPage(), grid.getSortIndex(),grid.getSortOrder(),grid.getFilters()._search,filtros);
	
	var objJson = $.toJSON(gridFilter);
    
	return objJson;
}



//Carga de i18n para el grid
var loadI18nP109 = function (locale){
	grid = new GridP109(locale);
	var jqxhr = $.getJSON(grid.i18nJSON,
			function(data) {
											
			})
			.success(function(data) {
				grid.colNames = data.p109ColNames;
				grid.index = '';
				grid.sortOrder = 'asc';
				grid.title = data.p109GridTitle;
				grid.emptyRecords= data.emptyRecords;
				
				//Despues de cargar el i18n, creamos el grid
				createGridP109(grid);
			})
			.error(function (xhr, status, error){
				handleError(xhr, status, error, locale);
            });
}

/*
 * Objeto ventana modal
 */
var modalCalendarioVegalsa = {
	idHTML:"p109_calendario_vegalsa_resumen_popup",
	idJquery:"#p109_calendario_vegalsa_resumen_popup",
	getTitle: function(){
		$(modalCalendarioVegalsa.idJquery).dialog( "option", "title" );
	},
	setTitle: function(title) {
		$(modalCalendarioVegalsa.idJquery).dialog( "option", "title", title );
	},
	getWidthMax:function(){
		return $(modalCalendarioVegalsa.idJquery).width() - 20;
	},
	open: function(){
		//Carga i18n y despues se crea el grid 
		loadI18nP109(locale);

		//Setteo de title
		let codMapa = mapasVegalsaCmb.getValue();		
		let title = "Mapas consultados en el detalle de pedido: ";
		
		if (codMapa.length>0 && codMapa != "0" && codMapa != "null"){
			title += mapasVegalsaCmb.getText();
		}
		else{
			title += 'TODOS';
		}
		
		modalCalendarioVegalsa.setTitle(title);

		//apertura de modal
		$(modalCalendarioVegalsa.idJquery).dialog('open');
	},
	close: function(){
		$(modalCalendarioVegalsa.idJquery).dialog('close');
		// limpio los datos del grid
		$(grid.nameJQuery).jqGrid('clearGridData');
		// Reseteo el grid para que se construya de nuevo al entrar
		$(grid.nameJQuery).jqGrid('GridUnload');
		
	},
	init : function (){
		// Abrir el pop-up de datos pedido
		$(this.idJquery).dialog({
			autoOpen: false,
	        height: 450,
	        width: 900,
	        modal: true,
	        resizable: false,
			open: function() {
//				console.log("evento de apertura de modal");
			},
			close: function(){
//				console.log("evento de cierre de modal");
			}
	    });			
		
		// Cuando se clique sobre la X cerrar ventana
		$( '.ui-dialog-titlebar-close' ).on('mousedown', function(){
			modalCalendarioVegalsa.close();
		});
	
//		console.log("modal inicializada");
	}
}

//Exportacion a Excel
var exportarBtnp109 = {
	idHTML : "p109_bexdata",
	idJquery : "#p109_bexdata",
	onClick : function(){
		//Comento la venta de confirmacion para que se exporte directamente
		//		$(modalConfirmarExportacion.idJquery).dialog( "open" );
		modalConfirmarExportacion.aceptar();
		
	},
	init : function() {
		$(this.idJquery).unbind('click');
		$(this.idJquery).on('click', this.onClick);
	}
}

var modalConfirmarExportacion = {
	idHTML:"p109_dialog-confirm",
	idJquery:"#p109_dialog-confirm",
	//Funcion que envia la peticion para obtener el excel.
	aceptar: function (){
		
		let form = "<form name='csvexportform' action='calendarioVegalsa/exportData.do'  method='get' accept-charset='ISO-8859-1'>";
		form = form + "<input type='hidden' name='centro' value='"+$("#centerName").val()+"'>";
		
		if (mapasVegalsaCmb.getValue()>0){
			form = form + "<input type='hidden' name='codMapa' value='"+mapasVegalsaCmb.getValue()+"'>";
		}
		form = form + "<input type='hidden' name='filtrosJson' value='"+getFiltrosJson(grid)+"'>";
		
		//Truco para que i8 convierta a utf8 los datos enviados en el post
		form = form + "<input name='iehack' type='hidden' value='&#9760;' />";
		form = form + "</form><script>document.csvexportform.submit();</script>";
		
		Show_Popup(form);

	},
	init : function (){

		$(this.idJquery).dialog({
			autoOpen: false,
	        modal: true,
	        position: "center",
			resizable:false,
			buttons: {
			    "Aceptar": {
			    	click: function() {
			    		modalConfirmarExportacion.aceptar();
			    		$( this ).dialog( "close" );
			    	},
			    	text: "Aceptar"
		        },
		        "Cancelar": {
	                click: function () {
	                    $(this).dialog("close");
	                },
	                text: "Cancelar"
	            }
			},
	    });				
	}
}

var initializeScreenp109 = function () {
//	console.log("inicializar popUp109");
	modalCalendarioVegalsa.init();
	exportarBtnp109.init();
	modalConfirmarExportacion.init();

}

$(document).ready(function() {
	initializeScreenp109();
});