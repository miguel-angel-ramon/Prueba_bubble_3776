/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/****************************************************************** CONSTANTES ***************************************************************/
var popUpTitleP100;

var cerrado = 'C';
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************************** DOCUMENTO LISTO **************************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
$(document).ready(function(){
	//Inicializa el popup que contiene el calendario anual
	initializeScreenP100CalendarioAnual();
});

/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
/************************************************ INICIALIZACIÓN DE ELEMENTOS E IDIOMA *******************************************************/
/*********************************************************************************************************************************************/
/*********************************************************************************************************************************************/
function initializeScreenP100CalendarioAnual(){
	//Inicializamos el idioma.
	loadP100(locale);

	//Inicializamos el popup
	load_dialog_P100();	
}

//Función que carga las variables de lenguaje json.
function loadP100(locale){
	this.i18nJSON = './misumi/resources/p100PopUpCalendarioAnual/p100PopUpCalendarioAnual_' + locale + '.json';

	var jqxhr = $.getJSON(this.i18nJSON,
			function(data) {
		popUpTitleP100 = data.popUpTitle;
	})
	.success(function(data) {

	})
	.error(function (xhr, status, error){
		handleError(xhr, status, error, locale);
	});
}

//Carga el diálogo de los servicios estacionales.
function load_dialog_P100(){
	$("#p100_popUpCalendarioAnual").dialog({
		autoOpen: false,
		height:'650',
		width:'750',
		modal: true,
		resizable: false
	}).prev(".ui-dialog-titlebar").css({"background":"#359AE8","text-align":"center"}).find(".ui-dialog-title").css({"text-align":"center","float":"none","color":"white","font-weight":"bold","font-size":"15px"});
}

function load_calendarioAnualP100(){
	//Borramos los elementos html de días de calendario que pueda haber en el calendario.
	//Limpiamos el calendario.
	limpiarCalendarioAnualP100();


	var anioEjercicio = $("#anioEjercicio"+comboValueAnioP96).val();
	var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();

	$.ajax({
		type : 'POST',
		url : "./calendario/popup/calendarioAnual/loadCalendarioAnual.do?codigoEjercicio="+anioEjercicio +'&tipoEjercicio='+tipoEjercicio,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
			if(data != null){
				if(data.lstMesAnual != null){
					var tituloPopUp = $("[id='p96_cmb_anio'] option:selected").text();
					$('#p100_popUpCalendarioAnual').dialog('option', 'title', tituloPopUp);
					dibujarMesCalendarioP100(data.lstMesAnual);
					$("#p100_popUpCalendarioAnual").dialog("open");
				}
			}
		},
		error : function (xhr, status, error){
			handleError(xhr, status, error, locale);
		}	
	});	
}

function dibujarMesCalendarioP100(listaMesAnual){

	//Variables que guardan el nombre del mes
	var partesMes;
	var mesActual

	//Crear variable que guardará la estructura del mes del calendario
	var estructuraFormMesCalendario;

	for(var idxMes = 0;idxMes<mesAnioLst.length;idxMes++){
		//Obtenemos el nombre del mes.
		var partesMes = mesAnioLst[idxMes].split("-");
		var anioActual = partesMes[0];
		var mesActual = partesMes[1];

		var nombreMes = $.datepicker.formatDate("MM", new Date(anioActual, mesActual - 1, '01'),{
			monthNames: $.datepicker.regional[ "es" ].monthNames
		});

		//Se obtiene la esctructura del mes del calendario y se cambian los ids adecuados al nuevo formulario.
		estructuraFormMesCalendario = getEstructuraMesCalendarioP100(idxMes);

		//Se inserta el formulario en su sección correspondiente del popup
		$("#p100_calendario").append(estructuraFormMesCalendario);

		//Añadimos el nombre al mes.
		$("#formMesEstructuraNombre"+idxMes).text(nombreMes);

		//Obtenemos los días del mes que estamos pintando
		var listaDiasTemporal = listaMesAnual[idxMes];
		dibujarDiasCalendarioP100(listaDiasTemporal.listadoFechaTemporal,idxMes);

		$("#formMesEstructura"+idxMes).hover(function(){
			$(this).css("background-color", "#d9d2d3");
		}, function(){
			$(this).css("background-color", "white");
		});
	}
	events_p100_mostrarMes();
}

//Obtiene la estructura de mes del calendario, que contiene una sección para meter los días
//del mes y otra para meter el nombre del mes.
function getEstructuraMesCalendarioP100(idxMes){
	var estructuraMesCalendario;
	estructuraMesCalendario = $("#formMesEstructura").prop('outerHTML');

	estructuraMesCalendario = estructuraMesCalendario.replace(new RegExp("\\bformMesEstructura\\b"),"formMesEstructura"+idxMes);
	estructuraMesCalendario = estructuraMesCalendario.replace(new RegExp("\\bformMesEstructuraNombre\\b"),"formMesEstructuraNombre"+idxMes);
	estructuraMesCalendario = estructuraMesCalendario.replace(new RegExp("\\bformMesEstructuraDiasDelMes\\b"),"formMesEstructuraDiasDelMes"+idxMes);

	return estructuraMesCalendario;
}


//Función que crea las marcas html de los días del calendario, inserta la información
//de los días del calendario en su lugar correspondiente.
function dibujarDiasCalendarioP100(listaDiasTemporal,idxMes){
	//Crear variable que guardará el día del calendario
	var diaCalendario;

	//Crear variable que guardará la estructura de la devolución
	var estructuraFormDiaAnualCalendario;

	//Conseguimos el día de la semana que cae el primer día del calendario.
	var primerDiaCalendario = listaDiasTemporal[0];

	//Conseguimos qué día de la semana cae ese día 1 para pintar los días de la semana anteriores de blanco.
	var primerDiaMes = primerDiaCalendario.fechaCalendario;

	//Conseguir día de la semana primer día
	var anyo = parseInt(primerDiaMes.substring(0,4),10);
	var mes = parseInt(primerDiaMes.substring(5,7),10);
	var dia = parseInt(primerDiaMes.substring(8),10);

	// L-> 1 , M->2, X->3, J->4, V->5, S->6, D->7
	var fechaDesdeFormateada = $.datepicker.formatDate("D", new Date(anyo, mes - 1, dia),{
		dayNamesShort: [7,1,2,3,4,5,6]
	});

	//Días anteriores no rellenables.
	var diasPreNoRellenablesP100 = fechaDesdeFormateada - 1;

	var diasParaNoMostrarP100 = listaDiasPreviosP100(diasPreNoRellenablesP100);
	listaDiasTemporal = diasParaNoMostrarP100.concat(listaDiasTemporal);


	//Crear un bucle que dibuje las devoluciones en el popup según su estado
	for(var i=0;i<listaDiasTemporal.length;i++){

		//Obtener el día del calendario a dibujar
		diaCalendario = listaDiasTemporal[i];

		//Se obtiene la esctructura 1 y se cambian los ids adecuados al nuevo formulario
		estructuraFormDiaAnualCalendario = getEstructuraDiaCalendarioP100(diaCalendario,idxMes,i);

		//Se inserta el formulario en su sección correspondiente del popup
		$("#formMesEstructuraDiasDelMes"+idxMes).append(estructuraFormDiaAnualCalendario);

		//Se llama a la función que pinta la información del día.
		rellenarInformacionDiariaP100(i,idxMes,diaCalendario);				
	}
}

//Devuelve una lista con los días anteriores especificados a no enseñar.
function listaDiasPreviosP100(days){
	var lista = [];
	for(var i = days; i>=1;i--){
		lista.push(diasPreviosP100());
	}
	return lista;
}

//Crea un objeto que indicará que no se tiene que mostrar el día.
function diasPreviosP100() {
	var diaCalendario = {mostrarDia:"N"}
	return diaCalendario;
}

//Función que sirve para limpiar el calendario.
function limpiarCalendarioP100(){
	$("#p100_calendario").empty();
}

//Se obtiene la esctructura del dia del calendario y se cambian los ids adecuados al nuevo dia.Si es un día vacío, se devuelve la estructura vacía.
function getEstructuraDiaCalendarioP100(diaCalendario, indiceMes, indiceDia){
	//Es necesario si el indice es de un solo número. Se entiende mejor con este ejemplo.El mes de marzo corresponde al indice 1 y el de enero al indice 11.
	//El día 11 de marzo, se crearía un id formDiaAnualEstructura1(1->correspondiente al indice del mes)11(11->correspondiente al indice del día) -> formDiaAnualEstructura111. El 1 de enero se crearía un id
	//exactamente igual; formDiaAnualEstructura1(11->correspondiente al indice del mes)1(1->correspondiente al indice del día) -> formDiaAnualEstructura111. Al ser iguales, hay que diferenciarlos, y para eso,
	//si el índice es de un sólo número se le mete un 0 delante y ya está. formDiaAnualEstructura0111 != formDiaAnualEstructura111.
	if(indiceMes < 10){
		indiceMes = '0' + indiceMes;
	}

	var estructuraDiaAnualCalendario;
	if(diaCalendario.mostrarDia == null){
		estructuraDiaAnualCalendario = $("#formDiaAnualEstructura").prop('outerHTML');

		//Para la estructura visible
		estructuraDiaAnualCalendario = estructuraDiaAnualCalendario.replace(new RegExp("\\bformDiaAnualEstructura\\b"),"formDiaAnualEstructura"+ indiceMes + indiceDia);
	}else{
		estructuraDiaAnualCalendario = $("#formDiaAnualEstructuraHidden").prop('outerHTML');
	}

	return estructuraDiaAnualCalendario;
}

//Función que pinta los datos en los días. Si es festivo, si está cerrado, etc.
function rellenarInformacionDiariaP100(indiceDia,indiceMes,diaCalendario){
	//Es necesario si el indice es de un solo número. Se entiende mejor con este ejemplo.El mes de marzo corresponde al indice 1 y el de enero al indice 11.
	//El día 11 de marzo, se crearía un id formDiaAnualEstructura1(1->correspondiente al indice del mes)11(11->correspondiente al indice del día) -> formDiaAnualEstructura111. El 1 de enero se crearía un id
	//exactamente igual; formDiaAnualEstructura1(11->correspondiente al indice del mes)1(1->correspondiente al indice del día) -> formDiaAnualEstructura111. Al ser iguales, hay que diferenciarlos, y para eso,
	//si el índice es de un sólo número se le mete un 0 delante y ya está. formDiaAnualEstructura0111 != formDiaAnualEstructura111.
	if(indiceMes < 10){
		indiceMes = '0' + indiceMes;
	}

	if(diaCalendario.mostrarDia == "N"){
		$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("visibility","hidden");
	}else{
		//Insertamos el día en su label
		var dia = parseInt(diaCalendario.fechaCalendario.substring(8),10);
		$("#formDiaAnualEstructura"+ indiceMes + indiceDia).text(dia);

		//Ponemos la clase correspondiente a cada festivo. 
		if(diaCalendario.festivo == "N"){
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("p100_festivoNacional")

			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoLocal");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoProvincial");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoAutonomico");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_sinFestivo");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoPlataforma");
		}else if(diaCalendario.festivo == "L" || diaCalendario.festivo == "Y"){
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("p100_festivoLocal");

			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoNacional");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoProvincial");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoAutonomico");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_sinFestivo");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoPlataforma");
		}else if(diaCalendario.festivo == "P"){
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("p100_festivoProvincial");

			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoLocal");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoNacional");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoAutonomico");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_sinFestivo");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoPlataforma");
		}else if(diaCalendario.festivo == "A"){
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("p100_festivoAutonomico");

			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoLocal");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoProvincial");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoNacional");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_sinFestivo");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoPlataforma");
		}else if(diaCalendario.festivo == "D"){
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("p100_festivoDomingo");

			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoLocal");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoNacional");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoAutonomico");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_sinFestivo");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoPlataforma");
		}else if(diaCalendario.festivo == "X"){
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("p100_festivoPlataforma");

			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoLocal");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoNacional");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoAutonomico");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_sinFestivo");
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_festivoDomingo");
		}

		if((diaCalendario.cerrado != "N") && (diaCalendario.cerrado != "M")){ //Los dias medio abiertos los interpretara como abiertos
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).text(cerrado);
		}
		
		//Ponemos los días verdes y los tooltip de los camiones.
		if((diaCalendario.ponerDiaVerde == "S" || diaCalendario.verdePlataforma == "S") && diaCalendario.festivo != "D"){
			//Para saber si poner el día verde, además, se tiene en cuenta también estos campos.
			var flgModificarServicioCentro = $("#flgModificarServicioCentro"+comboValueAnioP96).val();
			var flgModificarServicioTecnico = $("#flgModificarServicioTecnico"+comboValueAnioP96).val();
			
			//En el caso de que no cumpla la condición, es necesario decir que dia verde es N. Esto sucede, porque puede
			//que un centro sea solo de consulta y tenga el dia verde en S. Entonces aparecería la opción de modificar los
			//servicios. Como no queremos que se puedan modificar, ponemos el flag en N y así aparece el día en blanco y no
			//modificable con el botón guardar quitado además.
			var tipoEjercicio = $("#tipoEjercicio"+comboValueAnioP96).val();
			if((flgModificarServicioCentro == "S" && rol == "2")||(flgModificarServicioTecnico == "S" && (rol == "1" || rol == "4"))){
				if ((tipoEjercicio == "E" && diaCalendario.esePuedeModificarServicio == "S") || (tipoEjercicio == "P") || (rol == "4")) {
					$("#formDiaAnualEstructura"+ indiceMes + indiceDia).addClass("p100_diaVerde");
				}
			}else{
				$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_diaVerde");
			}
		}else{
			$("#formDiaAnualEstructura"+ indiceMes + indiceDia).removeClass("p100_diaVerde");			
		}	
	}
}

//Función que sirve para limpiar el calendario anual.
function limpiarCalendarioAnualP100(){
	$("#p100_calendario").empty();
}

function events_p100_mostrarMes(){
	$(".p100_mes").click(function(){
		//Obtenemos el mes que estamos clicando.
		var idMes = this.id.replace("formMesEstructura","");
		
		//Actualizamos el idxActual con el número del mes.
		idxActual = parseInt(idMes);
		
		//Al clicar el mes, es una recarga de la temporal.
		load_diasCalendarioP96(comboValueAnioP96,comboValueServicioP96,null,"S",mesAnioLst[idMes]);	
		
		$("#p100_popUpCalendarioAnual").dialog("close");
	});
}