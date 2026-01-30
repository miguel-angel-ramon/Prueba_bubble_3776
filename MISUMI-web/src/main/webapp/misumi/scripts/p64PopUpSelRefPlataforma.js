/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Constante que indica el número de elementos máximo a mostrar por paginación. Cambiar si se quiere mostrar menos formularios
var maxElementosPopupP64 = 20;

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
var gridP64=null;
var p64Generico = false;
var p64Seccion = null;
var p64Categoria = null;
var p64ExisteVitrina = false;
var p64Title = null;
var p64TitleGenerico = null;

var vitrinaAlerta = null;
var especialAlerta = null;

var enCliPlatP64 = null

//Variable que guarda el número de página que nos encontramos en el popup.
var numeroDePaginaPopupP64 = 1;
var idxInicioListaP64 = null;
var idxFinListaP64 = null;
var largoListaP64 = null;

var encargoCliente = null;

var emptyRecordSelectionP64 = null;

$(document).ready(function(){

	loadP64(locale);

});

function initializeScreenP64PopupSelRefPlataforma(){
	$( "#p64_popupSelRefPlataforma").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		// buttons:[],
		open: function() {
			$('.ui-dialog-titlebar-close').on('mousedown', function(){
				$("#p64_popupSelRefPlataforma").dialog('close');

				//comboPopup = "Foto";
				numeroDePaginaPopupP64 = 1;
				idxInicioListaP64 = null;
				idxFinListaP64 = null;
				largoListaP64 = null;
			});
		}
	});

	$(window).bind('resize', function() {
		$("#p64_popupSelRefPlataforma").dialog("option", "position", "center");
	});

	$("#p64_cmb_seccion").combobox(null);
	$("#p64_cmb_categoria").combobox(null);
	$("#p64_cmb_categoria").combobox("disable");

	//Carga de datos de combos para maqueta
	//optionsSeccion = "<option value='1'>1-FRUTA Y HORTALIZA</option><option value='2'>2-CARNICERIA</option><option value='4'>4-CHARCUTERIA</option>"; 
	//optionsCategoria = "<option value='1'>1-FRUTA</option><option value='2'>2-HORTALIZA</option>";

	//$("select#p64_cmb_seccion").html(optionsSeccion);
	//$("select#p64_cmb_categoria").html(optionsCategoria);
	events_p64_flecha_sig();
	events_p64_flecha_ant();
}

function reset_p64(){
	$(gridP64.nameJQuery).jqGrid('clearGridData');
	$(gridP64.nameJQuery).showCol("codReferencia");
	$(gridP64.nameJQuery).showCol("unidadesCaja");
	$(gridP64.nameJQuery).showCol("vitrina");
	p64Seccion = null;
	p64Categoria = null;
	$("#p64_cmb_seccion").combobox(null);
	$("#p64_cmb_seccion").combobox('autocomplete',"");
	$("#p64_cmb_seccion").val(null);
	$("#p64_cmb_categoria").combobox(null);
	$("#p64_cmb_categoria").combobox('autocomplete',"");
	$("#p64_cmb_categoria").val(null);
	$("#p64_cmb_categoria").combobox("disable");
	$("#p64_popupSelRefPlataforma .ui-pg-selbox").val(10);
}

function loadP64(locale){
	gridP64 = new GridP64SelRefPlataforma(locale);
	//$(gridP64.nameJQuery).jqGrid('setGridWidth', $("#p64_AreaFiltro").width(), true);
	var jqxhr = $.getJSON(gridP64.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		gridP64.colNames = data.ColNames;
		gridP64.title = data.GridTitle;
		p64Title = data.GridTitle;
		p64TitleGenerico = data.GridTitleGenerico;
		gridP64.emptyRecords= data.emptyRecords;

		encargoCliente = data.encargoCliente;
		emptyRecordSelectionP64 = data.emptyRecordSelection;
		
		vitrinaAlerta = data.vitrinaAlerta;
		especialAlerta = data.especialAlerta;

		load_gridP64SelRefPlataformaMock(gridP64);
		initializeScreenP64PopupSelRefPlataforma();
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

function loadP64_cmbSeccion(){
	var options = "";
	$("#p64_cmb_seccion").combobox(null);

	$.ajax({
		type : 'POST',
		url : './seleccionReferenciaPlataforma/loadSeccion.do',
		//data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {		

			options = "<option value='' selected>&nbsp;</option>";
			for (i = 0; i < data.length; i++){



				options = options + "<option value='" + data[i] + "'>" + data[i]+ "</option>";

			}
			$("select#p64_cmb_seccion").html(options);

			// $("#p64_cmb_seccion").combobox('autocomplete',descNuevo);
			// $("#p64_cmb_seccion").val(claveNuevo);

			//{
			//	loadP64_cmbCategory();
			//}
			//else
			//{
			//controlBotonCancelarNuevo();
			//	}


		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});

	$("#p64_cmb_seccion").combobox({
		selected: function(event, ui) {

			if ( ui.item.value!="" && ui.item.value!="null") {
				if ($("#p64_cmb_seccion").val()!=null){
					//var result=cleanFilterSelection(SECTION_CONST);
					p64Categoria = null;
					//$(gridP64.nameJQuery).jqGrid('clearGridData');
					loadP64_cmbCategory();
					numeroDePaginaPopupP64 = 1;
					idxInicioListaP64 = null;
					idxFinListaP64 = null;
					largoListaP64 = null;
					reloadData_FotoP64SelRefPlataforma();

				}
			}  else {
				p64Categoria = null;
				$(gridP64.nameJQuery).jqGrid('clearGridData');
				numeroDePaginaPopupP64 = 1;
				idxInicioListaP64 = null;
				idxFinListaP64 = null;
				largoListaP64 = null;
				reloadData_FotoP64SelRefPlataforma();
				$("#p64_cmb_categoria").combobox(null);
				$("#p64_cmb_categoria").combobox('autocomplete',"");
				$("#p64_cmb_categoria").combobox("disable");
			}
		}  
	,	
	changed: function(event, ui) { 
		if (ui.item==null || ui.item.value!="" || ui.item.value!="null"){
			//return result=cleanFilterSelection(RESET_CONST);

		}	 
	}
	});

	//if ($("#p64_flagCancelarNuevo").val() != 'S')
	//{
	//	$("#p64_cmb_seccion").combobox('autocomplete',optionNull);
	//	$("#p64_cmb_seccion").combobox('comboautocomplete',null);
	//}

}

function loadP64_cmbCategory(){
	var options = "";
	var optionNull = "";
	var claveCatNuevo = "";
	var descCatNuevo = "";
	$("#p64_cmb_categoria").combobox(null);
	p64Seccion = $("#p64_cmb_seccion").val();

	//var encargoClientePlataforma =new EncargoClientePlataforma($("#centerId").val(),codArea,codSeccion);
	//var objJson = $.toJSON(encargoClientePlataforma);	
	$.ajax({
		type : 'POST',
		url : './seleccionReferenciaPlataforma/loadCategoria.do?seccion='+p64Seccion,
		//data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {		
			options = "<option value=''>&nbsp;</option>";
			for (i = 0; i < data.length; i++){
				options = options + "<option value='" + data[i] + "'>" + data[i]+ "</option>";

			}
			$("select#p64_cmb_categoria").html(options);


			$("#p64_cmb_categoria").combobox("enable");
			$("#p64_cmb_categoria").combobox('autocomplete',descCatNuevo);

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		
	$("#p64_cmb_categoria").combobox({
		selected: function(event, ui) {
			//$(gridP64.nameJQuery).jqGrid('clearGridData');
			numeroDePaginaPopupP64 = 1;
			idxInicioListaP64 = null;
			idxFinListaP64 = null;
			largoListaP64 = null;
			reloadData_FotoP64SelRefPlataforma();

		}  
	,	
	changed: function(event, ui) { 
		//$(gridP64.nameJQuery).jqGrid('clearGridData');
		numeroDePaginaPopupP64 = 1;
		idxInicioListaP64 = null;
		idxFinListaP64 = null;
		largoListaP64 = null;
		reloadData_FotoP64SelRefPlataforma(); 
	}
	});


	$("#p64_cmb_categoria").combobox('autocomplete',optionNull);
	$("#p64_cmb_categoria").combobox('comboautocomplete',null);


}

function P64_CargaReferencias(){
	var descripcion = $("#p52_fld_referencia").val();
	$.ajax({
		type : 'POST',
		url : './seleccionReferenciaPlataforma/loadReferencias.do?descripcion='+descripcion,
		//data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {	
			p64Generico = data.generico;
			loadP64_cmbSeccion();
			if (p64Generico){
				$("#p64_lbl_notaPie").html(data.notas);
			} else {
				$("#p64_lbl_notaPie").html("");
			}

			//Insertar texto e el titulo
			var textoTituloPopup = encargoCliente;
			$("#p64_tituloEstructura").text(textoTituloPopup);
			reloadData_FotoP64SelRefPlataforma();
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});		


}


function load_gridP64SelRefPlataformaMock(grid) {

	$(grid.nameJQuery).jqGrid({
		ajaxGridOptions : {
			contentType : 'application/json; charset=utf-8',
			cache : false
		},
		url : './misumi/resources/mock.json',
		datatype : 'json',
		contentType : 'application/json',
		mtype : 'POST',
		colNames : grid.colNames,
		colModel : grid.cm,
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		height : "100%",
		autowidth : true,
		rownumbers : grid.rownumbers,
		shrinkToFit:true,
		viewrecords : true,
		caption : grid.title,
		altclass: "ui-priority-secondary",
		pager : grid.pagerNameJQuery,
		altRows: true, //false, para que el grid no muestre cebrado
		hidegrid : false, //false, para ocultar el boton que colapsa el grid.
		sortable : true,
		index: grid.sortIndex,
		sortname: grid.sortIndex,
		sortorder: grid.sortOrder,
		emptyrecords : grid.emptyRecords,
		gridComplete : function() {
			gridP64.headerHeight("p64_gridHeader");	
		},
		loadComplete : function(data) {
			gridP64.actualPage = data.page;
			gridP64.localData = data;
			gridP64.sortIndex = null;
			gridP64.sortOrder = null;
			$(gridP64.nameJQuery).jqGrid('setGridWidth', $("#p64_AreaFiltro").width(), true);
		},
		resizeStop: function () {
			gridP64.saveColumnState.call($(this),gridP64.myColumnsState);

		},
		onPaging : function(postdata) {			
			gridP64.sortIndex = null;
			gridP64.sortOrder = null;
			gridP64.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP64SelRefPlataforma();
			return 'stop';
		},
		onSelectRow: function(id){

			p52Generico = p64Generico;
			p64CodReferencia =$(gridP64.nameJQuery).getCell(id, "codReferencia");
			p64DescReferencia =$(gridP64.nameJQuery).getCell(id, "descripcionArt");
			$("#p64_popupSelRefPlataforma").dialog('close');
			getReferencia();

		},
		onSortCol : function (index, columnIndex, sortOrder){
			gridP64.sortIndex = index;
			gridP64.sortOrder = sortOrder;
			gridP64.saveColumnState.call($(this), this.p.remapColumns);
			reloadData_gridP64SelRefPlataforma();
			return 'stop';
		},
		jsonReader : {
			root : "rows",
			page : "page",
			total : "total",
			records : "records",
			repeatitems : false
		},
		loadError : function (xhr, status, error){
			handleError(xhr, status, error);
		}
	});

}

function reloadDataP64(){
	reset_p64();
	P64_CargaReferencias();
}

/*function reloadData_gridP64SelRefPlataforma() {

	$(gridP64.nameJQuery).jqGrid('setGridWidth', $("#p64_AreaFiltro").width(), true);
	var encargoClientePlataforma = new EncargoClientePlataforma($("#centerId").val());
	p64Seccion = $("#p64_cmb_seccion").val();
	if (p64Seccion && p64Seccion != "null"){
	encargoClientePlataforma.seccion = p64Seccion;
	}
	p64Categoria = $("#p64_cmb_categoria").val();
	if (p64Categoria && p64Categoria != "null"){
		encargoClientePlataforma.categoria = p64Categoria;
	}

	var objJson = $.toJSON(encargoClientePlataforma.prepareToJsonObject());
	$("#p64_AreaReferencias .loading").css("display", "block");
	p64ExisteVitrina = false;
	$.ajax({
		type : 'POST',
		url : './seleccionReferenciaPlataforma/loadDataGrid.do?page='+gridP64.getActualPage()+'&max='+gridP64.getRowNumPerPage()+'&index='+gridP64.getSortIndex()+'&sortorder='+gridP64.getSortOrder(),

		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			$(gridP64.nameJQuery)[0].addJSONData(data);
			gridP64.actualPage = data.page;
			gridP64.localData = data;
			//Control generico o no generico



			if (p64Generico){


				$("#p64_AreaReferencias .ui-jqgrid-title").html(p64TitleGenerico);
				$(gridP64.nameJQuery).hideCol("codReferencia");
				$(gridP64.nameJQuery).hideCol("unidadesCaja");
				$(gridP64.nameJQuery).hideCol("vitrina");

			} else {

				$("#p64_AreaReferencias .ui-jqgrid-title").html(p64Title);
				$(gridP64.nameJQuery).showCol("codReferencia");
				$(gridP64.nameJQuery).showCol("unidadesCaja");

				$(gridP64.nameJQuery).showCol("vitrina");
				if(!p64ExisteVitrina){
					$(gridP64.nameJQuery).hideCol("vitrina");
				}
			}
			$(gridP64.nameJQuery).jqGrid('setGridWidth', $("#p64_AreaFiltro").width(), true);
			$("#p64_AreaReferencias .loading").css("display", "none");
			loadReferenciaActiva = false;
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
        }			
	});		
}*/

function reloadData_FotoP64SelRefPlataforma() {

	var encargoClientePlataforma = new EncargoClientePlataforma($("#centerId").val());
	p64Seccion = $("#p64_cmb_seccion").val();
	if (p64Seccion && p64Seccion != "null"){
		encargoClientePlataforma.seccion = p64Seccion;
	}
	p64Categoria = $("#p64_cmb_categoria").val();
	if (p64Categoria && p64Categoria != "null"){
		encargoClientePlataforma.categoria = p64Categoria;
	}
	var objJson = $.toJSON(encargoClientePlataforma.prepareToJsonObject());
	$("#p64_AreaReferencias .loading").css("display", "block");
	p64ExisteVitrina = false;
	$.ajax({
		type : 'POST',
		url : './seleccionReferenciaPlataforma/loadDataGrid.do?page='+numeroDePaginaPopupP64+'&max='+maxElementosPopupP64+'&index=referencia&sortorder=asc',
		data : objJson,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {		
			/*$(gridP64.nameJQuery)[0].addJSONData(data);
			gridP64.actualPage = data.page;
			gridP64.localData = data;
			//Control generico o no generico



			if (p64Generico){


				$("#p64_AreaReferencias .ui-jqgrid-title").html(p64TitleGenerico);
				$(gridP64.nameJQuery).hideCol("codReferencia");
				$(gridP64.nameJQuery).hideCol("unidadesCaja");
				$(gridP64.nameJQuery).hideCol("vitrina");

			} else {

				$("#p64_AreaReferencias .ui-jqgrid-title").html(p64Title);
				$(gridP64.nameJQuery).showCol("codReferencia");
				$(gridP64.nameJQuery).showCol("unidadesCaja");

				$(gridP64.nameJQuery).showCol("vitrina");
				if(!p64ExisteVitrina){
					$(gridP64.nameJQuery).hideCol("vitrina");
				}
			}
			$(gridP64.nameJQuery).jqGrid('setGridWidth', $("#p64_AreaFiltro").width(), true);
			$("#p64_AreaReferencias .loading").css("display", "none");*/
			if(data != null){
				if(data.rows.length > 0){
					loadReferenciaActiva = false;

					limpiarFotosAnterioresP64();
					dibujarFotosBuscadorUniversalP64(data);

					//Calculamos el inicio de la lista y el final
					idxInicioListaP64 = (numeroDePaginaPopupP64-1)*maxElementosPopupP64;
					idxFinListaP64 = (numeroDePaginaPopupP64)*maxElementosPopupP64;
					largoListaP64 = data.records;

					if(idxFinListaP64 > largoListaP64){
						idxFinListaP64 = largoListaP64;
					}

					actualizarNumeroDePaginaP64(idxInicioListaP64,idxFinListaP64,largoListaP64);
					$( "#p64_popupSelRefPlataforma" ).dialog( "open" );
				}else{
					//Si no existen muestro mensaje de error			
					loadReferenciaActiva = false;
					createAlert(replaceSpecialCharacters(emptyRecordSelectionP64), "ERROR");
				}
			}else{
				//Si no existen muestro mensaje de error
				loadReferenciaActiva = false;
				createAlert(replaceSpecialCharacters(emptyRecordSelectionP64), "ERROR");
			}
		},
		error : function (xhr, status, error){
			$("#AreaResultados .loading").css("display", "none");	
			handleError(xhr, status, error, locale);				
		}			
	});		
}

//Función que sirve para limpiar del popup las fotos anteriores y para ocultar la paginación. 
function limpiarFotosAnterioresP64(){
	$("#p64_listaFotos").empty();
	$("#p64_AreaReferencias").hide();
}

function loadP64Foto(enCliPlatP64,pos){
	if (enCliPlatP64.tieneFoto == "S"){
		var url = "./welcome/getImage.do?codArticulo="+enCliPlatP64.codReferencia;
		$('#formP64FotoBUEstructura1FotoProductoImg'+pos).attr('src',url);
	} else {
		$('#formP64FotoBUEstructura1FotoProductoImg'+pos).attr('src','./misumi/images/nofotoRecortada.png?version{misumiVersion}');
	}			
}

function dibujarFotosBuscadorUniversalP64(data){
	var estructuraFotoBuscadorUniversal = null;

	for(var i = 0; i<data.rows.length;i++){
		enCliPlatP64 = data.rows[i];

		//Se obtiene la esctructura y se cambian los ids adecuados al nuevo formulario
		estructuraFotoBuscadorUniversal = $("#formP64FotoBUEstructura1").prop('outerHTML');
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1","formP64FotoBUEstructura1"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1FotoProducto","formP64FotoBUEstructura1FotoProducto"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1FotoProductoImg","formP64FotoBUEstructura1FotoProductoImg"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1FotoAlerta1","formP64FotoBUEstructura1FotoAlerta1"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1FotoAlerta2","formP64FotoBUEstructura1FotoAlerta2"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1InfoAlerta1","formP64FotoBUEstructura1InfoAlerta1"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1InfoProducto","formP64FotoBUEstructura1InfoProducto"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1InfoAlerta1UC","formP64FotoBUEstructura1InfoAlerta1UC"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1CodArt","formP64FotoBUEstructura1CodArt"+i);
		estructuraFotoBuscadorUniversal = estructuraFotoBuscadorUniversal.replace("formP64FotoBUEstructura1DescArt","formP64FotoBUEstructura1DescArt"+i);

		//Se inserta el formulario en su sección correspondiente del popup
		$("#p64_listaFotos").append(estructuraFotoBuscadorUniversal);

		loadP64Foto(enCliPlatP64,i);

		//Activamos las alertas de vitrina y especial
		if (enCliPlatP64.flgEspec == null || enCliPlatP64.flgEspec == "" || enCliPlatP64.flgEspec == "S"){
			$("#formP64FotoBUEstructura1FotoAlerta1"+i).addClass("esferaVerde");
			$("#formP64FotoBUEstructura1FotoAlerta1"+i).append("<div class='p64_textoCentradoHV p64_colorTexto'>"+especialAlerta+"</div>");
		}if(enCliPlatP64.vitrina == "S"){
			$("#formP64FotoBUEstructura1FotoAlerta2"+i).addClass("esferaVerde");
			$("#formP64FotoBUEstructura1FotoAlerta2"+i).append("<div class='p64_textoCentradoHV p64_colorTexto'>"+vitrinaAlerta+"</div>");
		}

		$("#formP64FotoBUEstructura1InfoAlerta1UC"+i).text(enCliPlatP64.unidadesCaja);	

		if (p64Generico){
			$("#p64_AreaReferencias .ui-jqgrid-title").html(p64TitleGenerico);
			$("#formP64FotoBUEstructura1InfoProducto"+i).text(enCliPlatP64.descripcionArt);

			//Eliminamos unidades cajas
			$("#formP64FotoBUEstructura1InfoAlerta1UC"+i).hide();

			//Escondemos la capa de vitrina
			$("#formP64FotoBUEstructura1FotoAlerta1"+i).removeClass("esferaVerde");
			$("#formP64FotoBUEstructura1FotoAlerta1"+i).empty();

		} else {
			$("#p64_AreaReferencias .ui-jqgrid-title").html(p64Title);
			$("#formP64FotoBUEstructura1InfoProducto"+i).text(enCliPlatP64.codReferencia + "-" + enCliPlatP64.descripcionArt);

			//Mostramos unidades cajas
			$("#formP64FotoBUEstructura1InfoAlerta1UC"+i).show();
		}

		//Datos hiddens
		$("#formP64FotoBUEstructura1CodArt"+i).text(enCliPlatP64.codReferencia);
		$("#formP64FotoBUEstructura1DescArt"+i).text(enCliPlatP64.descripcionArt);

		$("#formP64FotoBUEstructura1InfoProducto"+i).text(enCliPlatP64.codReferencia + "-" + enCliPlatP64.descripcionArt);
		$("#formP64FotoBUEstructura1InfoAlerta1UC"+i).text(enCliPlatP64.unidadesCaja);		
	}

	//Añadimos evento
	event_loadSelectionP64();
	event_iluminarP64();
	event_noIluminarP64();	
}

//Función que actualiza las imágenes de flechas del popup. Y el número que aparece 
//debajo de las fotos 6/11, 5/5, 12/24, etc.
function actualizarNumeroDePaginaP64(idxInicioListaP64,idxFinListaP64,largoListaP64){
	//Eliminar la imagen de la flecha actual
	$("#p64_pagAnterior").removeClass("p64_pagAnt");
	$("#p64_pagAnterior").removeClass("p64_pagAnt_Des");

	$("#p64_pagSiguiente").removeClass("p64_pagSig");
	$("#p64_pagSiguiente").removeClass("p64_pagSig_Des");

	//Añadir la flecha correspondiente
	if(idxInicioListaP64 == 0){
		$("#p64_pagAnterior").addClass("p64_pagAnt_Des");
	}else{
		$("#p64_pagAnterior").addClass("p64_pagAnt");
	}

	//Se hace +1 para saber el número de elemento que estamos obteniendo. Por ejemplo si
	//el último índice de los objetos a mostrar de la lista es el 5 equivaldría al elemento 6.
	//En el caso de que el último indice sea el 5 y la lista tenga 6 elementos, quiere decir que
	//la flecha tiene que estar invalidada, pero 5<6!!! Por eso se hace +1, para que el indice
	//equivalga al número de objeto
	if(idxFinListaP64 < largoListaP64){
		$("#p64_pagSiguiente").addClass("p64_pagSig");
	}else{
		$("#p64_pagSiguiente").addClass("p64_pagSig_Des");
	}

	//Actualiza el número de debajo de los formularios. 
	//Indica cuántos formularios se han mostrado y el total de 
	//formularios a mostrar.
	$("#p64_paginaActual").text(idxFinListaP64+"/");
	$("#p64_paginaTotal").text(largoListaP64);

	//Enseñamos el número de página
	$("#p64_AreaReferencias").show();
}

//Función que sirve para paginar entre los formularios
function paginacionFormulariosP64(flechaSig){
	//Si se ha presionado la flecha siguiente
	if(flechaSig == 'S'){
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxFinListaP64 < largoListaP64) && $("#p64_pagSiguiente").hasClass("p64_pagSig")){		
			//Limpiar las devoluciones anteriores
			limpiarFotosAnterioresP64();

			//Actualizamos el número de página del popup
			numeroDePaginaPopupP64 ++;

			//Dibujar los formularios
			reloadData_FotoP64SelRefPlataforma();	
		}
		//Si se ha presionado la flecha anterior
	}else{
		//Si hay más formularios de los que se ven en la página, paginar
		if((idxInicioListaP64 > 0) && $("#p64_pagAnterior").hasClass("p64_pagAnt")){		
			//Limpiar las devoluciones anteriores
			limpiarFotosAnterioresP64();

			//Actualizamos el número de página del popup
			numeroDePaginaPopupP64 --;

			//Dibujar los formularios
			reloadData_FotoP64SelRefPlataforma();
		}
	}
}

/************************ EVENTOS ************************/
function event_iluminarP64(){
	//Iluminar la foto
	$(".fotoBUP64").mouseover(function () {		
		$(this).addClass("iluminar");
	});	
}

function event_noIluminarP64(){
	//Desiluminar foto
	$(".fotoBUP64").mouseout(function () {		
		$(this).removeClass("iluminar");
	});	
}

//Evento para flechas 
function events_p64_flecha_ant(){
	$("#p64_pagAnterior").click(function () {		
		paginacionFormulariosP64('N');
	});	
}

//Evento para flechas 
function events_p64_flecha_sig(){
	$("#p64_pagSiguiente").click(function () {		
		paginacionFormulariosP64('S');
	})
}

//Evento cuando se clica la imagen. Así se abre la información de esa referencia.
function event_loadSelectionP64(){
	$(".fotoBUP64").click(function () {	
		p52Generico = p64Generico;

		var idFicha = $(this).attr('id');
		var numIdFicha = idFicha.replace("formP64FotoBUEstructura1",'');

		var codReferencia = $("#formP64FotoBUEstructura1CodArt"+numIdFicha).text();
		var descripcionArt = $("#formP64FotoBUEstructura1DescArt"+numIdFicha).text();

		p64CodReferencia = codReferencia;
		p64DescReferencia = descripcionArt;

		$("#p64_popupSelRefPlataforma").dialog('close');
		getReferencia();

		/*if (p64Generico){


			$("#p64_AreaReferencias .ui-jqgrid-title").html(p64TitleGenerico);
			$(gridP64.nameJQuery).hideCol("codReferencia");
			$(gridP64.nameJQuery).hideCol("unidadesCaja");
			$(gridP64.nameJQuery).hideCol("vitrina");

		} else {

			$("#p64_AreaReferencias .ui-jqgrid-title").html(p64Title);
			$(gridP64.nameJQuery).showCol("codReferencia");
			$(gridP64.nameJQuery).showCol("unidadesCaja");

			$(gridP64.nameJQuery).showCol("vitrina");
			if(!p64ExisteVitrina){
				$(gridP64.nameJQuery).hideCol("vitrina");
			}
		}*/

	});	
}

function p64esCazaPesca(){
	var seccion = $("#p64_cmb_seccion").val();
	if(seccion && seccion != "null"){
		seccion = seccion.substring(0,4);
		if (seccion == "0002" || seccion == "0007"){
			return true;
		} else {
			return false;
		}

	} else {
		return false;
	}
}

function p64formatterVitrina(cellValue, opts, rData) {

	var imagen = "";
	if (cellValue && cellValue == "S"){
		p64ExisteVitrina = true;
		imagen = "<div align='center' ><img src='./misumi/images/dialog-accept-24.gif' /></div>"; //Modificado
		$(gridP64.nameJQuery).showCol("vitrina");
		$(gridP64.nameJQuery).jqGrid('setGridWidth', $("#p64_AreaFiltro").width(), true);
	}
	return imagen;
}

function p64formatterEspec(cellValue, opts, rData) {
	var imagen = "";
	if (cellValue == null || cellValue == "" || cellValue == "S"){
		imagen = "<div align='center' ><img src='./misumi/images/dialog-accept-24.gif' /></div>"; //Modificado
	}
	return imagen;
}

/*Clase de constantes para el GRID de referencias de alta de plataforma*/
function GridP64SelRefPlataforma (locale){

	// Atributos
	this.name = "gridP64SelRefPlataforma"; 
	this.nameJQuery = "#gridP64SelRefPlataforma"; 
	this.i18nJSON = './misumi/resources/p64PopUpSelRefPlataforma/p64PopUpSelRefPlataforma_' + locale + '.json';
	this.colNames = null; //Está en el json
	this.cm = [
	           {
	        	   "name"  : "codReferencia",
	        	   "index" : "codReferencia",
	        	   "width" : 20,	
	        	   "sortable" : true,
	        	   "align" : "left"
	           },{
	        	   "name"  : "descripcionArt",
	        	   "index" : "descripcionArt", 
	        	   "width" : 80,	
	        	   "sortable" : true,
	        	   "align" : "left"						
	           },{
	        	   "name"  : "unidadesCaja",
	        	   "index" : "unidadesCaja", 
	        	   "width" : 10,
	        	   "sortable" : true,
	        	   "align" : "left"
	           },{
	        	   "name"  : "vitrina",
	        	   "index" : "vitrina", 
	        	   "formatter": p64formatterVitrina,
	        	   "width" : 10,
	        	   "sortable" : true,
	        	   "align" : "left"
	           }
	           ,{
	        	   "name"  : "flgEspec",
	        	   "index" : "flgEspec", 
	        	   "width" : 40,
	        	   "formatter": p64formatterEspec,
	        	   "sortable" : true,
	        	   "align" : "left"
	           }
	           ];
	this.locale = locale;
	this.sortIndex = "referencia";
	this.sortOrder = "asc"; // Valores posibles "asc" o "desc"
	this.title = null; //Está en el json
	this.actualPage = null;
	this.localdata = null;
	this.emptyRecords = null; //Está en el json
	this.pagerName = "pagerP64"; 
	this.pagerNameJQuery = "#pagerP64";
	this.rownumbers = true;
	this.myColumnStateName = 'gridP64SelRefPlataforma.colState';
	this.myColumnsState = null;
	this.isColState = null;
	this.firstLoad = true;

	//Metodos		
	this.getActualPage = function getActualPage(){ 
		if (this.actualPage == null)
			this.actualPage = 1;
		else
			this.actualPage = $(this.nameJQuery).getGridParam('page');

		return this.actualPage;
	};

	this.getSelectedRow = function getSelectedRow(){ 
		return $(this.nameJQuery).getGridParam('selrow'); 
	};

	this.getCellValue = function getCellValue(rowId, colName){ 
		return $(this.nameJQuery).getRowData(rowId)[colName]; 
	};

	this.getRowNumPerPage = function getRowNumPerPage () {
		return $(this.nameJQuery).getGridParam('rowNum');
	} ;

	this.getSortIndex = function getSortIndex () {
		if ($(this.nameJQuery).getGridParam('sortname')!=null){
			return $(this.nameJQuery).getGridParam('sortname');
		}else{
			return null;
		}

	} 

	this.getSortOrder = function getSortOrder () {

		if ($(this.nameJQuery).getGridParam('sortorder')!=null){
			return $(this.nameJQuery).getGridParam('sortorder');
		}else{
			return "asc";
		}
	} 	

	this.clearGrid = function clearGrid() {
		$(this.nameJQuery).jqGrid('GridUnload');
	}

	this.headerHeight = function headerHeight(cssClass) {
		var colModel = $(this.nameJQuery).jqGrid('getGridParam','colModel');
		for (i = 0; i < colModel.length; i++){
			$(this.nameJQuery).setLabel(colModel[i].name, '', cssClass);
		}
	}

	this.saveObjectInLocalStorage = function (storageItemName, object) {
		if (typeof window.localStorage !== 'undefined') {
			window.localStorage.setItem(storageItemName, JSON.stringify(object));
		}
	}

	this.removeObjectFromLocalStorage = function (storageItemName) {
		if (typeof window.localStorage !== 'undefined') {
			window.localStorage.removeItem(storageItemName);
		}
	}

	this.getObjectFromLocalStorage = function (storageItemName) {
		if (typeof window.localStorage !== 'undefined') {
			return JSON.parse(window.localStorage.getItem(storageItemName));
		}
	}

	this.saveColumnState = function (perm) {
		var colModel = jQuery(gridP64.nameJQuery).jqGrid('getGridParam', 'colModel'); 
		var i;
		var l = colModel.length;
		var colItem; 
		var cmName;
		var postData = jQuery(gridP64.nameJQuery).jqGrid('getGridParam', 'postData');
		var columnsState = {
				search: jQuery(gridP64.nameJQuery).jqGrid('getGridParam', 'search'),
				page: jQuery(gridP64.nameJQuery).jqGrid('getGridParam', 'page'),
				sortname: jQuery(gridP64.nameJQuery).jqGrid('getGridParam', 'sortname'),
				sortorder: jQuery(gridP64.nameJQuery).jqGrid('getGridParam', 'sortorder'),
				permutation: perm,
				colStates: {}
		};
		var colStates = columnsState.colStates;

		if (typeof (postData.filters) !== 'undefined') {
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
		//this.saveObjectInLocalStorage(this.myColumnStateName, columnsState);
		if (typeof window.localStorage !== 'undefined') {
			window.localStorage.setItem(this.myColumnStateName, JSON.stringify(columnsState));
		}
	}

	this.restoreColumnState = function (colModel) {
		var colItem, i, l = colModel.length, colStates, cmName,
		columnsState = this.getObjectFromLocalStorage(this.myColumnStateName);

		if (columnsState) {
			colStates = columnsState.colStates;
			for (i = 0; i < l; i++) {
				colItem = colModel[i];
				cmName = colItem.name;
				if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
					colModel[i] = $.extend(true, {}, colModel[i], colStates[cmName]);
				}
			}
		}
		return columnsState;
	}
}