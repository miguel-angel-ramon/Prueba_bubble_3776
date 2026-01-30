/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** VARIABLES ******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Variables json.
var errorValidacion;
var warningValidacion;

//Mira si los campos del popup son válidos.
var bandejaValid;
var kgsValid;

//Variable global que guarda la tabla en uso.
var tablaEnUso;

//Para saber en que listado de modificados guardar los datos.
var nombrePopUpLista;

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
$(document).ready(function(){
	initializeScreenP88PopupCantidadDevueltaCentroKgs();
	regexForInput();
	event_btn_guardadoCantidadDevueltaCentroKgs();
	events_p88_keyUp_cantidadDevueltaCentro();
	events_p88_keyUp_Kgs();
	events_p88_keyDown_cantidadDevueltaCentro();
	events_p88_keyDown_Kgs();
	loadP88(locale);

	//Nos posicionamos en el campo al que ha navegado el usuario y que hemos almacenado en el hidden.
	//De esta forma al clicar aceptar en la alerta, vuelve al último campo de edición del grid.
	$("#p03_btn_aceptar").bind("click", function(e) {
		if(!bandejaValid){
			$("#p88_fld_stockDevuelto").focus();
			$("#p88_fld_stockDevuelto").select();
		}else if(!kgsValid){
			$("#p88_fld_kgs").focus();
			$("#p88_fld_kgs").select();
		}
	});
});

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** INICIALIZACIÓN  IDIOMA ***********************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function loadP88(locale){
	this.i18nJSON = './misumi/resources/p88PopUpCantidadDevueltaCentroKgs/p88PopUpCantidadDevueltaCentroKgs_' + locale + '.json';
	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {

	})
	.success(function(data) {
		errorValidacion = data.errorValidacion;
		warningValidacion = data.warningValidacion;
	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/********************************************************** POPUP Y FUNCIONES  ***************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
//Inicializa el diálogo.
function initializeScreenP88PopupCantidadDevueltaCentroKgs(){
	$( "#p88_popUpCantidadDevueltaCentroKgs").dialog({
		autoOpen: false,
		height: 'auto',
		width: 'auto',
		modal: true,
		resizable: false,
		open: function() {
		},
		close: function( event, ui ) {	
			//Si cerramos el popup de bandejas y kilos, situamos el cursor en el input de stockDevuelto, para que el
			//usuario siga moviendose por los diferentes inputs.
			$("#"+$("#p88_fld_StockDevuelto_Selecc").val()).focus();

			//Limpiamos el popup p88
			limpiarPopUpP88();
		},
		//Fijar el z-index del popup p56 para que las opciones del combobox no se queden detrás del popup 
		//al autoincrementarse el z-index.
		stack: false
	});
}

//Función que limpia el popup.
function limpiarPopUpP88(){	
	$("#p88_fld_stockDevuelto").val("");	
	$("#p88_fld_kgs").val("");

	$("#p88_fld_stockDevuelto").removeClass("p88_inputError");
	$("#p88_fld_kgs").removeClass("p88_inputError");
}

//Abrimos el popup p88 e introducimos los datos en los inputs de bandejas y kilos. Además guardamos en un hidden 
//el valor del input stockdevuelto para tras abrir el popup situar ahí el foco y que el usuario pueda seguir navegando
//con las flechas entre los distintos stock devueltos.
function openPopUpCantidadDevueltaCentroYKgs(fila,tabla,nombrePopUp){			
	//Guardamos en una variable oculta, el campo en el que estamos para poder posicionarnos en el posteriormente al guardar.
	$("#p88_fld_StockDevuelto_Selecc").val(fila + "_stockDevuelto");

	$("#p88_popUpCantidadDevueltaCentroKgs").dialog("open");			

	//Insertamos los datos de KGS y cantidad devuelta centro en el popup.
	$("#p88_fld_stockDevuelto").val($("#"+fila+"_stockDevueltoBandejas_tmp").val());
	$("#p88_fld_kgs").val($("#"+fila+"_stockDevuelto").val());

	//Obtenemos la referencia de la fila
	var ref = $("#"+fila).find("td[aria-describedby='"+tabla+"_codArticulo']").text();

	//Obtenemos la descripcion de la fila
	var descripcion = $("#"+fila).find("td[aria-describedby='"+tabla+"_denominacion']").text();

	//Juntar la referencia y la descripcion.
	var refDescripcion = ref + "-" + descripcion;

	//Insertamos la descripción de la fila.
	$("#p88_lbl_refDescripcion").text(refDescripcion);	

	//Guardar en una variable global la tabla
	tablaEnUso = tabla;

	//Para saber al modificar en que lista de modificados guardar.
	nombrePopUpLista = nombrePopUp;

	//Ponemos el foco en el input de stockdevuelto para que el usuario pueda seguir navegando. -- PUEDE QUE
	//$("#"+$("#p88_fld_StockDevuelto_Selecc").val()).focus();

	//Ponemos el cursor en bandejas.
	$("#p88_fld_stockDevuelto").focus();
}
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/******************************************************** EXPRESIONES REGULARES  *************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Inicializa las expresiones regulares de los imput del popup.
function regexForInput(){
	$("#p88_fld_stockDevuelto").filter_input({regex:'[0-9,]'});	
	$("#p88_fld_kgs").filter_input({regex:'[0-9,]'});	
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/**************************************************************** EVENTOS  *******************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Cuando pulsamos guardar, se validan los campos de bandejas y kgs. Si todo es correcto, actualiza los temporales de los datos de kgs y
//bandejas y vuelve al campo cantidad devuelta centro. Si no, devuelve error.
function event_btn_guardadoCantidadDevueltaCentroKgs(){
	$("#p88_btn_guardado").click(function(e){
		
		if (($("#p88_fld_stockDevuelto").val()!= 0 && $("#p88_fld_kgs").val() == 0)
				|| ($("#p88_fld_stockDevuelto").val()== 0 && $("#p88_fld_kgs").val() != 0)){
			//Mostramos info si alguno de los campo es 0
			createAlert(warningValidacion,"ERROR");
		} else {
			//Obtenemos la fila para mirar los kgs originales
			var fila = $("#p88_fld_StockDevuelto_Selecc").val().split("_")[0];
			var nuevosKgs = $("#p88_fld_kgs").val();
			
			//MISUMI-269
			var valorCantidadMaximaLin = jQuery(gridP83.nameJQuery).jqGrid('getCell',fila,'cantidadMaximaLin');
			//Si la cantidad maxima tiene valor
			if(valorCantidadMaximaLin != ""){
				//Si los kilos son mayor a la cantidad maxima lin 
				if(parseFloat(nuevosKgs.replace(",",".")) > parseFloat(valorCantidadMaximaLin.replace(",","."))){
					//Mostramos alerta
					createAlert(cantidadMaximaLinSuperadaP83, "ERROR");
					return;
				}
			}
			
			//Miramos que los campos nuevos de bandejas y kilos sean correctos
			bandejaValid = validarCantidadDevueltaCentroP88() == "S";
			kgsValid = validarKgsP88() == "S";
	
			//Si son correctos
			if(bandejaValid && kgsValid){
					//Evitas la ejecución de otros posibles eventos.
					e.stopPropagation();
	
					//Guardamos las nuevas bandejas y kgs antes de cerrar el popup para no perder los datos porque en el close del popup, se limpian los datos.
					var nuevasBandejas = $("#p88_fld_stockDevuelto").val();
					
	
					//Cerramos el dialogo.
					$("#p88_popUpCantidadDevueltaCentroKgs").dialog("close");
	
					//Si tenemos guardado el id del input de cantidad devolver centro del que venimos cuando hemos abierto el popup.
					if ($("#p88_fld_StockDevuelto_Selecc").val() != '')
					{
						//Escribimos en el input el dato del kg.
						$("#"+$("#p88_fld_StockDevuelto_Selecc").val()).val(nuevosKgs);
	
						//Lanzamos el evento change del input stockdevuelto. Para que si stockdevuelto es 0, copie un 0 en el bulto.
						$("#"+$("#p88_fld_StockDevuelto_Selecc").val()).trigger("change");
	
						//Realizamos la validación de los KGs y de stock devuelto
						validacionKgs(nuevosKgs,fila);
						validacionStockDevueltoPopUpP88(nuevasBandejas,fila);
	
						//Seleccionamos el bulto de esa fila.
						$("#"+fila+"_bulto").focus();
						$("#"+fila+"_bulto").select();
					}
				}else{
					//Mostramos error si no hay campo válido.
					createAlert(errorValidacion,"ERROR");						
			}
		}
	});
}

//Eventos al escribir en campos cantidad y validación.
function events_p88_keyUp_cantidadDevueltaCentro(){
	$("#p88_fld_stockDevuelto").keyup(function(event) {
		validarCantidadDevueltaCentroP88();
	});
}

//Eventos al escribir en caja kgs y validación.
function events_p88_keyUp_Kgs(){
	$("#p88_fld_kgs").keyup(function(event) {
		validarKgsP88();
	});
} 

//Función que valida las bandejas y pasa el cursor a los kgs si es correcto al presionar enter. Si no pinta error.
function events_p88_keyDown_cantidadDevueltaCentro(){
	$("#p88_fld_stockDevuelto").on("keydown",function(e) {
		if(e.which == 13) {
			if(validarCantidadDevueltaCentroP88() == "S"){
				$("#p88_fld_kgs").focus();
				$("#p88_fld_kgs").select();
			}else{
				$("#p88_fld_stockDevuelto").focus();
				$("#p88_fld_stockDevuelto").select();
			}
		}
	});
}
//Función que valida los kgs y ejecuta el guardado si es correcto al presionar enter. Si no pinta error.
function events_p88_keyDown_Kgs(){
	$("#p88_fld_kgs").on("keydown",function(e) {
		if(e.which == 13) {
			if(validarKgsP88() == "S"){
				$("#p88_btn_guardado").trigger("click");
			}else{
				$("#p88_fld_kgs").focus();
				$("#p88_fld_kgs").select();
			}
		}
	});
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** VALIDACIONES CAMPOS EDITABLES DEL POPUP **************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que valida los campos de bandejas.Tiene que cumplir con que máximo tenga 2 decimales.
function validarCantidadDevueltaCentroP88(){
	var bandejas = $("#p88_fld_stockDevuelto").val();

	var expresionRegular = new RegExp('^([0-9]+(,[0-9]{1,2})?)?$');

	var resultadoBandejas = expresionRegular.test(bandejas);

	if(!resultadoBandejas){
		$("#p88_fld_stockDevuelto").addClass("p88_inputError");
	}else{
		$("#p88_fld_stockDevuelto").removeClass("p88_inputError");
	}

	return resultadoBandejas == true ? "S":"N";
}


//Función que valida los campos de kgs. Tiene que cumplir con que máximo tenga 2 decimales.
function validarKgsP88(){
	var kgs = $("#p88_fld_kgs").val();

	var expresionRegular = new RegExp('^([0-9]+(,[0-9]{1,2})?)?$');

	var resultadoKgs = expresionRegular.test(kgs);

	if(!resultadoKgs){
		$("#p88_fld_kgs").addClass("p88_inputError");
	}else{
		$("#p88_fld_kgs").removeClass("p88_inputError");
	}

	return  resultadoKgs == true ? "S":"N";
}

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************** VALIDACIONES CAMPOS EDITABLES DEL GRID COMUNES ***************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/

//Función que valida los KGS. En este caso, la validación es cencilla, porque el número de KGS introducidos siempre va a ser correcto pues
//ya se trata en el popup al darle a guardar que su formato esté bien. Por eso mismo solo hay que mirar que haya habido algún cambio respecto
//a los kgs originales, y si es así se indica que la fila ha sido modificada.

//Cuando no existía el grid de KGS y bandejas, si el usuario introducía erroneamente un dato y ese dato ya había sido modificado, mostraba error,
//ponía el dato original y si se cambiaba de campo, quitaba el error, mostrando que el dato no se había editado. Como ahora para introducir las bandejas
//y los kgs, no deja guardar los nuevos datos a no ser que se pongan números correctos, nos evitamos que puedan salir errores por introducir los datos mal.

//La validación se realiza al guardar el popup, y si se han modificado los KGS.
function validacionKgs(campoKgsActual,fila){
	//Div que equivale a la columna Estado de la fila seleccionada.
	//Estos div contienen las imágenes del disquete, ficha y de aviso 
	var campoDivModificado = fila + "_stockDevueltoBulto_divModificado";
	var campoDivGuardado = fila + "_stockDevueltoBulto_divGuardado";
	var campoDivError = fila + "_stockDevueltoBulto_divError";

	var campoErrorOrig = fila + "_stockDevueltoBulto_codError_orig";

	//Hiddens de kgs
	var campoKgsTmp = fila + "_stockDevuelto_tmp";

	//Valores originales y temporales del dato
	var campoKgsTemporales = $("#"+fila+"_stockDevuelto_tmp").val();

	//Si no coinciden los kgs temporales con los insertados, se guarda en la variable temporal el valor nuevo.
	if(campoKgsTemporales != campoKgsActual){
		var campoActualFormatter = $.formatNumber(campoKgsActual.replace(',','.'),{format:"0.###",locale:"es"});
		if (($("#"+campoKgsTmp).val() != null) && $("#"+campoKgsTmp).val() != campoActualFormatter){
			//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
			$("#"+campoDivError).hide();
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).show();

			//Es necesario que el campo editable coja formato decimal, si no se hace este paso,
			//al clicar guardar el número del campo editable pasa sin decimales (en el caso de haberlo
			//introducido sin decimales), y se guarda en el hidden temporal sin decimales, al editarlo
			//por el mismo campo pero con decimales, detecta que son dos números distintos y vuelve a entrar
			//sin necesidad (aunque la ejecución salga bien). Además al clicar guardar, queda mejor si el 
			//número coge formato decimal automáticamente pues es el formato que va a devolver después del guardado.

			//Si no hicieramos esto, por ejemplo el usuario introduciría un 3, el campo temporal cogería el valor 3.
			//Más tarde el usuario introduciría el campo 3,000 y detectaría que es distinto, ejecutando de nuevo el código
			//sin ser necesario. Además hasta recibir la respuesta del guardado, el campo no cogería formato decimal 
			//y queda extaño viendo que en las tabulaciones o al presionar enter si lo coge.
			$("#"+campoKgsTmp).val(campoActualFormatter);

			//El código de error de la fila será 9 que equivale a modificado
			$("#"+campoErrorOrig).val("9");

			//Insertamos el elemento de los array de modificados. Este array se usa cuando se actualiza. Se mira si hay algún registro modificado de todo el grid
			//y si es así se guarda. En este caso, vamos a dejar el valor fila_stockDevuelto como se hace en P84 y p83, porque ya funciona correctamente y al fin
			//y al cabo la funcionalidad sigue siendo la misma.
			var valor = $("#"+tablaEnUso+" #"+fila+" [aria-describedby='"+tablaEnUso+"_rn']").text();
			if(nombrePopUpLista == "P83"){
				if(modificados.indexOf(valor+"_stockDevuelto") === -1){ 	
					//Añadimos la fila al array.
					addSeleccionados(fila);
					modificados.push(valor+"_stockDevuelto");
				}
			}else if(nombrePopUpLista == "P84"){
				if(modificadosP84.indexOf(valor+"_stockDevuelto") === -1){ 	
					//Añadimos la fila al array.
					addSeleccionadosP84(fila);
					modificadosP84.push(valor+"_stockDevuelto");
				}
			}else if(nombrePopUpLista == "P87"){
				if(modificadosP87.indexOf(valor+"_stockDevuelto") === -1){ 	
					//Añadimos la fila al array.
					addSeleccionadosP87(fila);
					modificadosP87.push(valor+"_stockDevuelto");
				}
			}
		}	
	}
}

//Función que valida las bandejas. En este caso, la validación es cencilla, porque el número de bandejas introducidas siempre va a ser correcta pues
//ya se trata en el popup al darle a guardar que su formato esté bien. Por eso mismo solo hay que mirar que haya habido algún cambio respecto
//a los kgs originales, y si es así se indica que la fila ha sido modificada.

//Cuando no existía el grid de KGS y bandejas, si el usuario introducía erroneamente un dato y ese dato ya había sido modificado, mostraba error,
//ponía el dato original y si se cambiaba de campo, quitaba el error, mostrando que el dato no se había editado. Como ahora para introducir las bandejas
//y los kgs, no deja guardar los nuevos datos a no ser que se pongan números correctos, nos evitamos que puedan salir errores por introducir los datos mal.

//La validación se realiza al guardar el popup, y si se han modificado stockdevuelto.
function validacionStockDevueltoPopUpP88(campoStockDevueltoBandejasActual,fila){
	//Div que equivale a la columna Estado de la fila seleccionada.
	//Estos div contienen las imágenes del disquete, ficha y de aviso 
	var campoDivModificado = fila + "_stockDevueltoBulto_divModificado";
	var campoDivGuardado = fila + "_stockDevueltoBulto_divGuardado";
	var campoDivError = fila + "_stockDevueltoBulto_divError";

	var campoErrorOrig = fila + "_stockDevueltoBulto_codError_orig";

	//Hiddens de kgs
	var campoStockDevueltoBandejasTmp = fila + "_stockDevueltoBandejas_tmp";

	//Valores originales y temporales del dato
	var campoStockDevueltoBandejasTemporales = $("#"+fila+"_stockDevueltoBandejas_tmp").val();

	//Si no coinciden los kgs temporales con los insertados, se guarda en la variable temporal el valor nuevo.
	if(campoStockDevueltoBandejasTemporales != campoStockDevueltoBandejasActual){
		var campoActualFormatter = $.formatNumber(campoStockDevueltoBandejasActual.replace(',','.'),{format:"0.###",locale:"es"});
		if (($("#"+campoStockDevueltoBandejasTmp).val() != null) && $("#"+campoStockDevueltoBandejasTmp).val() != campoActualFormatter){
			//En este caso se ha modificado el campo y hay que establecer el icono de modificaci?n.
			$("#"+campoDivError).hide();
			$("#"+campoDivGuardado).hide();
			$("#"+campoDivModificado).show();

			//Es necesario que el campo editable coja formato decimal, si no se hace este paso,
			//al clicar guardar el número del campo editable pasa sin decimales (en el caso de haberlo
			//introducido sin decimales), y se guarda en el hidden temporal sin decimales, al editarlo
			//por el mismo campo pero con decimales, detecta que son dos números distintos y vuelve a entrar
			//sin necesidad (aunque la ejecución salga bien). Además al clicar guardar, queda mejor si el 
			//número coge formato decimal automáticamente pues es el formato que va a devolver después del guardado.

			//Si no hicieramos esto, por ejemplo el usuario introduciría un 3, el campo temporal cogería el valor 3.
			//Más tarde el usuario introduciría el campo 3,000 y detectaría que es distinto, ejecutando de nuevo el código
			//sin ser necesario. Además hasta recibir la respuesta del guardado, el campo no cogería formato decimal 
			//y queda extaño viendo que en las tabulaciones o al presionar enter si lo coge.
			$("#"+campoStockDevueltoBandejasTmp).val(campoActualFormatter);

			//El código de error de la fila será 9 que equivale a modificado
			$("#"+campoErrorOrig).val("9");			

			//Insertamos el elemento de los array de modificados. Este array se usa cuando se actualiza. Se mira si hay algún registro modificado de todo el grid
			//y si es así se guarda. En este caso, vamos a dejar el valor fila_stockDevuelto como se hace en P84 y p83, porque ya funciona correctamente y al fin
			//y al cabo la funcionalidad sigue siendo la misma.
			var valor = $("#"+tablaEnUso+" #"+fila+" [aria-describedby='"+tablaEnUso+"_rn']").text();
			if(nombrePopUpLista == "P83"){
				if(modificados.indexOf(valor+"_stockDevuelto") === -1){ 	
					//Añadimos la fila al array.
					addSeleccionados(fila);
					modificados.push(valor+"_stockDevuelto");
				}
			}else if(nombrePopUpLista == "P84"){
				if(modificadosP84.indexOf(valor+"_stockDevuelto") === -1){ 	
					//Añadimos la fila al array.
					addSeleccionadosP84(fila);
					modificadosP84.push(valor+"_stockDevuelto");
				}
			}else if(nombrePopUpLista == "P87"){
				if(modificadosP87.indexOf(valor+"_stockDevuelto") === -1){ 	
					//Añadimos la fila al array.
					addSeleccionadosP87(fila);
					modificadosP87.push(valor+"_stockDevuelto");
				}
			}
		}	
	}
}