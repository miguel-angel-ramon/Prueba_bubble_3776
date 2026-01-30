/**
 * @author BICUGUAL
 */
var tituloVentanaListado = null;
var productoAgotado=null;
var stockPlataforma=null;
var closedialog;

$(document).ready(function(){
	initializeScreenP80PopupLotesNavidad();
});

/**
 * Inicializa  las propiedades de los popUp
 */
function initializeScreenP80PopupLotesNavidad(){

	loadMessages(locale);

	$( "#p80_popupLotesNavidad" ).dialog({
		dialogClass: 'noTitleStuff',
		autoOpen: false,
		// height: 587,
		// width: 606,
		height: 587,
		width: 800,
		modal: true,
		resizable: false
	});

	$( "#p81_popupCestaNavidad" ).dialog({
		dialogClass: 'noTitleStuff',
		autoOpen: false,
		height: 'auto',
		width: 990,
		height: 400,
		modal: true,
		resizable: false,
	});
}

/**
 * Carga de los mensajes internacionalizados
 * @param locale
 */
function loadMessages(locale){

	var jqxhr = $.getJSON('./misumi/resources/p80CestasNavidadPopup/p80CestasNavidadPopUp_' + locale + '.json',
			function(data) {

	})
	.success(function(data) {
		tituloVentanaListado = data.tituloVentanaListadoLbl; 
		productoAgotado=data.productoAgotadoLbl;
		stockPlataforma=data.stockPlataformaLbl;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}
/**
 * A�ade el html a rederizar en el popUp
 */
function loadP80Popup(){
	$( "#p80_popupLotesNavidad" ).empty();
	var $popup=$("#p80_popupLotesNavidad");
	var $divTitulo = $("<div>"+tituloVentanaListado+"</div>").addClass("loteNavidadTitle");
	$("<img src='./misumi/images/dialog_cancel_15.gif' onclick='events_p80_btn_cerrar()' id='p80_close' style='float:right;  cursor: pointer; cursor: hand;'/>").appendTo($divTitulo);
	$divTitulo.appendTo($popup);
	var $divListado=$("<div/>").addClass("loteNavidadListado");
	$divListado.appendTo($popup);

	$.ajax({
		type : 'GET',
		url : './cestasNavidad/getLotesNavidad.do',
		success : function(data) {	
			//Creo dinamicamente el contenido a renderizar del popUp
			$.each(data, function(key, val){


				var $div=$("<div/>").addClass("loteNavidad");
				$("<img onclick='events_p80_btn_detalle("+val.codArtLote+")'/>").attr("src", 'data:image/png;base64,' + val.imagen1)
				.attr("onerror", "cargaFotoError(this)")
				.attr("id",	"id_" + val.codArtLote)
				.addClass("imagenLoteNavidad")
				.appendTo($div);

				var $divText=$("<div/>").addClass("loteNavidadText");

				$("<p/>")
				.text(val.codArtLotePantalla + " " +val.descripcionLotePantalla)
				.appendTo($divText);

				var $pStock=$("<p/>")
				var stockage= val.stock;

				if (stockage>0){
					$divText.attr("style", "border: 0px solid #9cbd40");
					$pStock.attr("style", "color:#9cbd40");
					$pStock.append(stockPlataforma);
					$("<img src='./misumi/images/arrow right_green.png?version="+$("#misumiVersion").val()+"' style='vertical-align: middle; padding:0px 3px 3px 3px' height='16px' width='16px' />").appendTo($pStock);
					$pStock.append(stockage);
				}
				else{
					$divText.attr("style", "border: 0px solid #b94b58");
					$pStock.attr("style", "color:#b94b58");
					$("<img src='./misumi/images/across_delete.png?version="+$("#misumiVersion").val()+"' style='vertical-align: middle; padding:0px 3px 3px 3px' height='16px' width='16px'/>").appendTo($pStock);
					$pStock.append(productoAgotado);
				}

				$pStock.appendTo($divText);

				$divText.appendTo($div);
				$div.appendTo($divListado);
			});
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	
}

/**
 * Carga los datos a mostrar en el popUp de detalle y lo abre
 * @param id Codigo del articulo
 */
function events_p80_btn_detalle(id){
	load81popup(id);
	$( "#p81_popupCestaNavidad" ).dialog("open");
}

/**
 * Cierra el dialog p80
 */
function events_p80_btn_cerrar(){
	$( "#p80_popupLotesNavidad" ).dialog("close");
}

/**
 * Cierra el dialog p81
 */
function events_p81_btn_cerrar(){
	$( "#p81_popupCestaNavidad" ).dialog("close");
}

/**
 * Llamada al controlador para traerme los datos de la cesta
 * @param id Codigo del articulo
 */
function load81popup(id){
	$("#p81_popupCestaNavidad").empty();
	var $popup=$("#p81_popupCestaNavidad");
	var $divTitulo = $("<div></div>").addClass("loteNavidadTitle");
	$("<img src='./misumi/images/dialog_cancel_15.gif' onclick='events_p81_btn_cerrar()' id='p81_close' style='float:right;  cursor: pointer; cursor: hand;'/>").appendTo($divTitulo);
	$divTitulo.appendTo($popup);
	$.ajax({
		type : 'GET',
		async: false,
		url : './cestasNavidad/getCestaNavidad.do?codArtLote='+id,
		success : function(data) {	
			//Creo dinamicamente el contenido a renderizar del popUp

			var $div=$("<div/>").addClass("cestaNavidad");

			var $imagenCestaDiv=$("<div/>").addClass("parteImagenCestaNavidad");

			$("<img/>")	.attr("src", 'data:image/png;base64,' + data.imagen1)
			.attr("onerror", "cargaFotoError(this)")
			.addClass("imagenCestaNavidad")
			.appendTo($imagenCestaDiv);

			var $detalleTextDiv=$("<div/>").addClass("detalleCestaNavidad");
			var $detalleTextDivTitulo=$("<div/>").addClass("detalleCestaNavidadTitulo");
			var $detalleTextDivContenido=$("<div/>").addClass("detalleCestaNavidadContenido");
			
			var $detalleTextDivStock=$("<div/>").addClass("detalleCestaNavidadStock");

			$detalleTextDivTitulo.appendTo($detalleTextDiv);
			$detalleTextDivContenido.appendTo($detalleTextDiv);
			$detalleTextDivStock.appendTo($detalleTextDiv);

			$("<p/>")
			.text(data.codArtLotePantalla + " " +data.descripcionLotePantalla)
			.addClass("cestaNavidadTitle")
			.appendTo($detalleTextDivTitulo);

			if((data.lstArticulos != null && data.lstArticulos.length > 0)){
				var ul = $("<ul class='p81_cestaLista'>");
				for(var i = 0; i<data.lstArticulos.length ; i++){
					var articulo = data.lstArticulos[i];
					var title = articulo.tituloArticuloLote;
					var descripcion = articulo.descrArticuloLote;

					var b = "<strong>" + title + ": </strong>";
					var li = $("<li class='p81_cestaListaElemento'>").html("<label><label class='p81_elementoTitulo'>" + title + ": </label><label>" + descripcion + "</label></label>").appendTo(ul);
					
					li.appendTo(ul);
				}
				ul.appendTo($detalleTextDivContenido);
			}else{				
				$("<img/>")	.attr("src", 'data:image/png;base64,' + data.imagen2)
				.addClass("img2Cesta")
				.attr("onerror", "cargaFotoError(this)")
				.addClass("")
				.appendTo($detalleTextDivContenido);
			}

			$imagenCestaDiv.appendTo($div);

			var $pStock=$("<p/>").addClass("cestaNavidadStock");
			var stockage= data.stock;

			if (stockage>0){
				$pStock.attr("style", "color:#9cbd40");
				$pStock.append(stockPlataforma);
				$("<img src='./misumi/images/arrow right_green.png?version=${misumiVersion}' style='vertical-align: middle; padding:0px 3px 3px 3px' />").appendTo($pStock);
				$pStock.append("<label style='font-size:20px;color:#9cbd40;vertical-align: sub;'>"+stockage+"</label>");
			}
			else{
				$pStock.attr("style", "color:#b94b58");
				$("<img src='./misumi/images/across_delete.png?version=${misumiVersion}' style='vertical-align: middle; padding:0px 3px 3px 3px' />").appendTo($pStock);
				$pStock.append(productoAgotado);
			}

			$pStock.appendTo($detalleTextDivStock);


			$detalleTextDiv.appendTo($div);
			$div.appendTo($popup);

		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}			
	});	

}
