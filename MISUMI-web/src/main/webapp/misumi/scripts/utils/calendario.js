//Variable para controlar el calendario que se está ultilizando. Así se puede controlar
//que a este calendario no se le añada la clase hayVentasUltimasOfertas capando el evento
//que llama al popup que desglosa las ofertas de calendario.
var calendarioP56 = "p56";

//Crear variable para guardar la lista de ofertas de la ayuda.
var listaVOfertaPaAyuda;

//Variables utilizadas en calendario con más de un mes
var listaDeListasDeMesesGlobal;
var listaMesesGlobal;
var indiceMes = 0;

//Guardar referencia del artículo
var refArticulo;

var sumVentaAnticipada;

//Asigna el evento a los objetos que en ese momento tienen la clase hayVentasUltimasOfertas asignada.
function recalcularEnlacesPopupUltimasOfertas(){
	$(".hayVentasUltimasOfertas").bind('click', function() {
		recalcularPopupUltimasOfertasCalendario(this);
	});
}

function recalcularPopupUltimasOfertasCalendario(diaCalendario){

	//Calcular el día de venta
	var pId = diaCalendario.id.replace("Mes", "MesFecha");
	var fechaVentaDDMMYYYY = $("#"+pId).val();

	var diaFechaTitulo = parseInt(fechaVentaDDMMYYYY.substring(0,2),10);
	var mesFechaTitulo = parseInt(fechaVentaDDMMYYYY.substring(2,4),10);
	var anyoFechaTitulo = parseInt(fechaVentaDDMMYYYY.substring(4),10);

	//Formateo de fecha
	var fechaTituloFormateada = $.datepicker.formatDate(formatoFechaTituloPopup, new Date(anyoFechaTitulo, mesFechaTitulo - 1, diaFechaTitulo));
	$( "#p17_popupVentas" ).dialog( "option", "title", ventasTituloPopup + " " + fechaTituloFormateada);

	resetResultadosPopupVentas();
	loadPopupVentasUltimasOfertas(fechaVentaDDMMYYYY);
}

//Mediante esta función se consigue el objeto de la lista de ventas en última oferta y se rellenan los elementos de la ayuda dinámicamente.
/*function load_AyudaData(comboValue, semanas, dias, lblCantidadMoneda,lblPeriodoOferta,idDia,idCantidad,idCantidadMesFecha,idTituloMes,idPagAnt,idPagSig,idTituloTabla){
	//Conseguir la posicion del elemento seleccionado de la lista.
	var posicionElementoLista = comboValue;

	//Conseguir el elemento seleccionado de la lista.
	var ventaUltimaOferta = listaVOfertaPaAyuda[posicionElementoLista];

	//Obtener los datos sin formato
	var precioSinFormato = ventaUltimaOferta.cPvp;

	//Dar formato a los datos, 1.56 -> 1,56 | 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16
	var precio = precioSinFormato.toString().replace(".",",");

	//18/08/2016 24/08/2016
	var desde = ventaUltimaOferta.fechaIniPeriodo;

	//Conseguir día, mes y año desde
	var diaDesde = parseInt(desde.substring(0,2),10);
	var mesDesde = parseInt(desde.substring(2,4),10);
	var anyoDesde = parseInt(desde.substring(4),10);

	//Dar formato 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16 a desde
	var fechaDesdeFormateada = $.datepicker.formatDate("D dd/mm/yy", new Date(anyoDesde, mesDesde - 1, diaDesde),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	var hasta = ventaUltimaOferta.fechaFinPeriodo;

	//Conseguir día, mes y año hasta
	var diaHasta = parseInt(hasta.substring(0,2),10);
	var mesHasta= parseInt(hasta.substring(2,4),10);
	var anyoHasta = parseInt(hasta.substring(4),10);

	//Dar formato 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16 a hasta
	var fechaHastaFormateada = $.datepicker.formatDate("D dd/mm/yy", new Date(anyoHasta, mesHasta - 1, diaHasta),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Unir las dos fechas
	var periodo = fechaDesdeFormateada +' - '+fechaHastaFormateada;

	//Asignar texto correspondiente.
	$(lblCantidadMoneda).text(precio);
	$(lblPeriodoOferta).text(periodo);

	dibujarCalendario(ventaUltimaOferta.listaDiaVentasUltimasOfertas,semanas,dias,idDia,idCantidad,idCantidadMesFecha,idTituloMes,idPagAnt,idPagSig,idTituloTabla);
}*/

//Mediante esta función se consigue el objeto de la lista de ventas en última oferta y se rellenan los elementos de la ayuda dinámicamente.
/*function load_AyudaData(comboValue, semanas, dias, tableCantidadMoneda,tableDiaUno,tableDiaDos,tableDiaTres,tableTotalVentas,idDia,idCantidad,idCantidadMesFecha,idTituloMes,idPagAnt,idPagSig,idTituloTabla){
	//Conseguir la posicion del elemento seleccionado de la lista.
	var posicionElementoLista = comboValue;

	//Conseguir el elemento seleccionado de la lista.
	var ventaUltimaOferta = listaVOfertaPaAyuda[posicionElementoLista];

	//Si los días existen
	if(ventaUltimaOferta.listaDiaVentasUltimasOfertas != null && ventaUltimaOferta.listaDiaVentasUltimasOfertas.length > 0){
		//Obtener los datos sin formato
		var precioSinFormato;
		if(ventaUltimaOferta.cPvp != null){		
			precioSinFormato = ventaUltimaOferta.cPvp;
		}else{
			precioSinFormato = "0.0";
		}

		var totalVentas;
		if(ventaUltimaOferta.totalVentas != null){
			totalVentas = ventaUltimaOferta.totalVentas;
		}else{
			totalVentas = "0";
		}
		var diaUno = "0";
		var diaDos = "0";
		var diaTres = "0";
		if(ventaUltimaOferta.listaDiaVentasUltimasOfertas.length == 1){
			diaUno = ventaUltimaOferta.listaDiaVentasUltimasOfertas[0].cVD;
		}else if(ventaUltimaOferta.listaDiaVentasUltimasOfertas.length == 2){
			diaUno = ventaUltimaOferta.listaDiaVentasUltimasOfertas[0].cVD;
			diaDos = ventaUltimaOferta.listaDiaVentasUltimasOfertas[1].cVD;
		}else if(ventaUltimaOferta.listaDiaVentasUltimasOfertas.length > 2){
			diaUno = ventaUltimaOferta.listaDiaVentasUltimasOfertas[0].cVD;
			diaDos = ventaUltimaOferta.listaDiaVentasUltimasOfertas[1].cVD;
			diaTres = ventaUltimaOferta.listaDiaVentasUltimasOfertas[2].cVD;
		}

		//Dar formato a los datos, 1.56 -> 1,56 | 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16
		var precio = precioSinFormato.toString().replace(".",",");

		//Asignar texto correspondiente.
		$(tableCantidadMoneda).text(precio);
		$(tableTotalVentas).text(totalVentas);
		$(tableDiaUno).text(diaUno);
		$(tableDiaDos).text(diaDos);
		$(tableDiaTres).text(diaTres);

		dibujarCalendario(ventaUltimaOferta.listaDiaVentasUltimasOfertas,semanas,dias,idDia,idCantidad,idCantidadMesFecha,idTituloMes,idPagAnt,idPagSig,idTituloTabla);
	}else{

		//Si la lista de días es vacía
		limpiarCalendario(semanas,dias,idDia,idCantidad,idCantidadMesFecha);

		//Asignar texto correspondiente a la tabla
		$(tableCantidadMoneda).text("0,0");
		$(tableTotalVentas).text("0");
		$(tableDiaUno).text("0");
		$(tableDiaDos).text("0");
		$(tableDiaTres).text("0");

		//Ocultar div con botones siguiente y previo y quitar texto
		$(idTituloMes).text("");
		$(idTituloTabla).hide();
	}
}*/

//Mediante esta función se consigue el objeto de la lista de ventas en última oferta y se rellenan los elementos de la ayuda dinámicamente.
function load_AyudaData(comboValue, semanas, dias, tableCantidadMoneda,tableTotalVentas,idDia,idCantidad,idCantidadMesFecha,idTituloMes,idPagAnt,idPagSig,idTituloTabla){
	//Conseguir la posicion del elemento seleccionado de la lista.
	var posicionElementoLista = comboValue;

	//Conseguir el elemento seleccionado de la lista.
	var ventaUltimaOferta = listaVOfertaPaAyuda[posicionElementoLista];

	//Si los días existen
	if(ventaUltimaOferta.listaDiaVentasUltimasOfertas != null && ventaUltimaOferta.listaDiaVentasUltimasOfertas.length > 0){
		//Obtener los datos sin formato
		var precioSinFormato;
		if(ventaUltimaOferta.cPvp != null){		
			precioSinFormato = ventaUltimaOferta.cPvp;
		}else{
			precioSinFormato = "0.0";
		}

		var totalVentasSinFormato;
		if(ventaUltimaOferta.totalVentas != null){
			totalVentasSinFormato = ventaUltimaOferta.totalVentas;
		}else{
			totalVentasSinFormato = "0.0";
		}

		//Dar formato a los datos, 1.56 -> 1,56 | 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16
		var precio = precioSinFormato.toString().replace(".",",");
		var totalVentas = totalVentasSinFormato.toString().replace(".",",");

		//Asignar texto correspondiente.
		$(tableCantidadMoneda).text(precio);
		$(tableTotalVentas).text(totalVentas);

		dibujarCalendario(ventaUltimaOferta.listaDiaVentasUltimasOfertas,semanas,dias,idDia,idCantidad,idCantidadMesFecha,idTituloMes,idPagAnt,idPagSig,idTituloTabla);
	}else{

		//Si la lista de días es vacía
		limpiarCalendario(semanas,dias,idDia,idCantidad,idCantidadMesFecha);

		//Asignar texto correspondiente a la tabla
		$(tableCantidadMoneda).text("0,0");
		$(tableTotalVentas).text("0");

		//Ocultar div con botones siguiente y previo y quitar texto
		//$(idTituloMes).text("");
		$(idTituloTabla).hide();
		$(idTituloTabla+"2").hide();
	}
}

//Función que sirve para dibujar el calendario
function dibujarCalendario(listaDias, cantidadSemanas,cantidadDiasSemana, idDia, idCantidad,idCantidadMesFecha,idTituloMes,idPagAnt,idPagSig,idTituloTabla){

	//Conseguir numero de días a mostrar
	var cantidadDias = listaDias.length;

	//Conseguir objeto del primer día y fecha
	var objPrimerDia = listaDias[0];
	var fechaPrimerDia = objPrimerDia.d;

	//Conseguir objeto del ultimo día y fecha
	var objUltimoDia = listaDias[listaDias.length - 1];
	var fechaUltimoDia = objUltimoDia.d;

	//Conseguir día de la semana primer día
	var dia = parseInt(fechaPrimerDia.substring(0,2),10);
	var mes = parseInt(fechaPrimerDia.substring(2,4),10);
	var anyo = parseInt(fechaPrimerDia.substring(4),10)

	// L-> 1 , M->2, X->3, J->4, V->5, S->6, D->7
	var fechaDesdeFormateada = $.datepicker.formatDate("D", new Date(anyo, mes - 1, dia),{
		dayNamesShort: [7,1,2,3,4,5,6]
	});

	/*Puede ocurrir que un periodo sea menor a 5 semanas (35 dias) y que el primer día del periodo
	no sea lunes. En el caso de ser un periodo de 35 dias exactos y caer martes la fecha de inicio,
	la primera posicion del calendario queda vacía y ya no entra el último día del periodo en el 
	calendario. Lo mismo ocurriría si son 34 días de periodo (33,32,etc.) pero cae un jueves(miercoles,
	jueves,etc) la fecha de inicio. Las posiciones de los tres (cuatro,cinco,etc) primeros días no se 
	pueden usar. Por lo que hay que tener en cuenta qué día cae el primer día del periodo y sumarlo a la
	cantidad de días.*/
	var diasPreNoRellenables = fechaDesdeFormateada -1;

	//Conseguir la cantidad de calendarios a mostrar. Ejemplo 66 días y el calendario tendrá 8 semanas con 7 días a la semana 66/56 = 1,17...redondeando 2
	var cantidadDiasTotal = cantidadDias+diasPreNoRellenables;
	var cantidadCalendarios = Math.ceil((cantidadDiasTotal) / (cantidadSemanas * cantidadDiasSemana));

	//Limpiar listas globales de meses y días al cargar nuevo calendario. Indicar que se cargará el 1 mes.
	listaDeListasDeMesesGlobal = [];
	listaMesesGlobal = [];
	indiceMes = 0;


	//Si solo hay un calendario
	if(cantidadCalendarios == 1){
		//Ocultar div con botones siguiente y previo y quitar texto
		//$(idTituloMes).text("");
		$(idTituloTabla).hide();
		$(idTituloTabla+"2").hide();

		//Guardar la lista en una lista total, luego se irá actualizando con los días no rellenables.
		var listaTotalDias = listaDias;

		/*Conseguir la lista de días previos. Si por ejemplo es 12/01/2017 (12012017), cae jueves, serían
		3 dias correspondientes a lunes (09012017), martes(10012017) y miércoles(11012017). El if siguiente
		controla que el primer día del periodo no sea Lunes para calcular los rellenables.*/
		if(diasPreNoRellenables > 0){
			var diasPrevios = listaDiasPrevios(fechaPrimerDia,diasPreNoRellenables);
			listaTotalDias = diasPrevios.concat(listaTotalDias);
		}

		/*Calcular días de después del periodo que no serán rellenables. Calcula que el último dia del
		 * periodo no sea el 35 del calendario*/
		var diasPostNoRellenables = (cantidadSemanas*cantidadDiasSemana)-cantidadDiasTotal;
		if(diasPostNoRellenables > 0){
			var diasPosteriores = listaDiasSiguientes(fechaUltimoDia,((cantidadSemanas*cantidadDiasSemana)-cantidadDiasTotal));
			listaTotalDias = listaTotalDias.concat(diasPosteriores);
		}

		rellenaMatrizCalendario(cantidadSemanas,cantidadDiasSemana,listaTotalDias,idDia,idCantidad,idCantidadMesFecha);
	}else{
		//Enseñar div con botones siguiente y previo
		$(idTituloTabla).show();
		$(idTituloTabla+"2").show();

		//Calcular cantidad dias no válidos del ultimo mes
		var ultimoDia = parseInt(fechaUltimoDia.substring(0,2),10);
		var ultimoMes = parseInt(fechaUltimoDia.substring(2,4),10);
		var ultimoAnyo = parseInt(fechaUltimoDia.substring(4),10);

		//Días del primer mes no validos
		var diasPreviosNoValidos = dia -1;
		//Dias del ultimo mes no validos
		var diasPosterioresNoValidos = diasEnMes(ultimoMes,ultimoAnyo)-ultimoDia;


		//Guardar la lista en una lista total, luego se irá actualizando con los días no rellenables.
		var listaTotalDiasDosCalendarios = listaDias;

		/*Conseguir la lista de días previos. Si empieza el día 15, conseguir el día 14,13,12...*/
		if(diasPreviosNoValidos > 0){
			var diasPrevios = listaDiasPrevios(fechaPrimerDia,diasPreviosNoValidos);
			listaTotalDiasDosCalendarios = diasPrevios.concat(listaTotalDiasDosCalendarios);
		}

		/*Calcular días posteriores*/
		if(diasPosterioresNoValidos > 0){
			var diasPosteriores = listaDiasSiguientes(fechaUltimoDia,diasPosterioresNoValidos);
			listaTotalDiasDosCalendarios = listaTotalDiasDosCalendarios.concat(diasPosteriores);
		}

		//Inicializar lista de títulos de meses y lista de días por mes.
		var listaMeses = [];
		var listaDeListasDeMeses = [];

		//Indice que sirve para calcular de qué posición a qué posición buscar los días de un mes.
		var count = 0;

		//Calcular los nombre de los meses que están dentro del periodo
		for(var i = 0;i<cantidadDias;i++){
			var diaNombreMes = parseInt((listaDias[i].d).substring(0,2),10);
			var mesNombreMes = parseInt((listaDias[i].d).substring(2,4),10);
			var anyoNombreMes = parseInt((listaDias[i].d).substring(4),10);

			// ENERO FEBRERO MARZO ABRIL MAYO JUNIO JULIO AGOSTO SEPTIEMBRE OCTUBRE NOVIEMBRE DICIEMBRE
			var nombreMes = $.datepicker.formatDate("MM", new Date(anyoNombreMes, mesNombreMes - 1, diaNombreMes),{
				monthNames: $.datepicker.regional[ "es" ].monthNames
			});

			//Pasar de ENERO a Enero, FEBRERO Febrero
			nombreMes = nombreMes.replace(/\w\S*/g, nombreMes.charAt(0).toUpperCase() + nombreMes.substr(1).toLowerCase());

			//Insertar el nombre del mes y un número correspondiente
			if(!containsMonth(nombreMes,listaMeses)){
				//Insertar el nombre del mes en una lista
				var mesObj = nombreMes +" - "+anyoNombreMes;
				listaMeses.push(mesObj);

				//Cantidad de dias que tiene el mes
				var cantidadDiasMes = diasEnMes(mesNombreMes,anyoNombreMes);

				var listaDiasDelMes;
				//var countInterior = 0;
				var diaDeSemana;
				for(var i = count; i< cantidadDiasMes+count;i++){
					var dia = parseInt((listaTotalDiasDosCalendarios[i].d).substring(0,2));
					//Si es día 1 calcular su posición en el calendario y sus días anteriores
					if(dia == "01"){
						var mes = parseInt((listaTotalDiasDosCalendarios[i].d).substring(2,4),10);
						var anyo = parseInt((listaTotalDiasDosCalendarios[i].d).substring(4),10);

						// L-> 1 , M->2, X->3, J->4, V->5, S->6, D->7
						diaDeSemana = $.datepicker.formatDate("D", new Date(anyo, mes - 1, dia),{
							dayNamesShort: [7,1,2,3,4,5,6]
						});
						//Conseguir días anterioresno válidos insertar en lista
						listaDiasDelMes = listaDiasPrevios(listaTotalDiasDosCalendarios[i].d,diaDeSemana-1);
						//Insertar primer día del mes
						listaDiasDelMes.push(listaTotalDiasDosCalendarios[i]);
					}
					else if(dia == cantidadDiasMes){
						var diasSiguientesVacios = (cantidadSemanas*cantidadDiasSemana) - (cantidadDiasMes + (diaDeSemana - 1));

						//Insertar último día del mes 
						listaDiasDelMes.push(listaTotalDiasDosCalendarios[i]);


						var diasSiguientes = listaDiasSiguientes(listaTotalDiasDosCalendarios[i].d,diasSiguientesVacios);

						//Insertar días no válidos para rellenar el mes
						listaDiasDelMes = listaDiasDelMes.concat(diasSiguientes);												
					}else{
						listaDiasDelMes.push(listaTotalDiasDosCalendarios[i]);
					}
					//countInterior ++;
				}
				//count = count + countInterior;
				count = count + cantidadDiasMes;

				//Insertar lista en lista de meses
				listaDeListasDeMeses.push(listaDiasDelMes);
			}
		}
		//Pasar la lista de meses y sus días a variables globales
		listaDeListasDeMesesGlobal = listaDeListasDeMeses;
		listaMesesGlobal = listaMeses;

		//Indicar que la flecha de anterior estará desabilitada y la de siguiente no, al estar cargado el 1 mes. 
		$(idPagAnt).removeClass("mes_Ant").addClass("mes_Ant_des");
		$(idPagSig).removeClass("mes_Sig_des").addClass("mes_Sig");

		//Rellenar el calendario y el mes.
		rellenaCalendario(0,cantidadSemanas,cantidadDiasSemana,idDia,idCantidad,idCantidadMesFecha,idTituloMes);
	}
}




/*Función que rellena calendario y título del mes*/
function rellenaCalendario(idx,semanas,dias,idDia,idCantidad,idCantidadMesFecha,idTituloMes){
	//Obtener mes y fijarlo.
	var mes = listaMesesGlobal[idx];
	$(idTituloMes).text(mes);

	//Conseguir la lista de días del mes seleccionado
	var lista = listaDeListasDeMesesGlobal[idx];

	//Indice que sirve para seleccionar los días del mes
	var count = 0;

	rellenaMatrizCalendario(semanas,dias,lista,idDia,idCantidad,idCantidadMesFecha);
}

//Formato 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16
function fechaInicioFechaFin(desde,hasta){
	//Conseguir día, mes y año desde
	var diaDesde = parseInt(desde.substring(0,2),10);
	var mesDesde = parseInt(desde.substring(2,4),10);
	var anyoDesde = parseInt(desde.substring(4),10);

	//Dar formato 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16 a desde
	var fechaDesdeFormateada = $.datepicker.formatDate("D dd/mm/yy", new Date(anyoDesde, mesDesde - 1, diaDesde),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Conseguir día, mes y año hasta
	var diaHasta = parseInt(hasta.substring(0,2),10);
	var mesHasta= parseInt(hasta.substring(2,4),10);
	var anyoHasta = parseInt(hasta.substring(4),10);

	//Dar formato 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16 a hasta
	var fechaHastaFormateada = $.datepicker.formatDate("D dd/mm/yy", new Date(anyoHasta, mesHasta - 1, diaHasta),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Unir las dos fechas
	var periodo = fechaDesdeFormateada +' - '+fechaHastaFormateada;
	return periodo;
}

function fechaInicioFechaFinCorta(desde,hasta){
	//Conseguir día, mes y año desde
	var diaDesde = parseInt(desde.substring(0,2),10);
	var mesDesde = parseInt(desde.substring(2,4),10);
	var anyoDesde = parseInt(desde.substring(4),10);

	//Dar formato 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16 a desde
	var fechaDesdeFormateada = $.datepicker.formatDate("D dd M", new Date(anyoDesde, mesDesde - 1, diaDesde),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Conseguir día, mes y año hasta
	var diaHasta = parseInt(hasta.substring(0,2),10);
	var mesHasta= parseInt(hasta.substring(2,4),10);
	var anyoHasta = parseInt(hasta.substring(4),10);

	//Dar formato 18/08/2016 24/08/2016 -> J 18/08/2016 - X 24/08/16 a hasta
	var fechaHastaFormateada = $.datepicker.formatDate("D dd M", new Date(anyoHasta, mesHasta - 1, diaHasta),{
		dayNamesShort: $.datepicker.regional[ "es" ].dayNamesShort,
		dayNames: $.datepicker.regional[ "es" ].dayNames,
		monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort,
		monthNames: $.datepicker.regional[ "es" ].monthNames
	});

	//Unir las dos fechas
	var periodo = fechaDesdeFormateada +' - '+fechaHastaFormateada;
	return periodo;
}


function rellenaMatrizCalendario(cantidadSemanas,cantidadDiasSemana,lista,idDia,idCantidad,idCantidadMesFecha){
	var count = 0;
	for(var i = 1;i<=cantidadSemanas;i++){
		//Poner que por defecto NO hay que mostrar la fila
		var mostrarFila = 0;
		for(var j = 1;j<=cantidadDiasSemana;j++){
			//Conseguir dia
			var diaFormat = (lista[count].d).substring(0,2);
			var dia = parseInt((lista[count].d).substring(0,2));
			//Si es el primer dia del mes poner nombre de mes.
			if(dia == 1){
				//Conseguir mes y año
				var mes = parseInt((lista[count].d).substring(2,4),10);
				var anyo = parseInt((lista[count].d).substring(4),10);

				// ENE FEB MAR ABR MAY JUN JUL AGO SEP OCT NOV DIC
				var mesFormato = $.datepicker.formatDate("M", new Date(anyo, mes - 1, dia),{
					monthNamesShort: $.datepicker.regional[ "es" ].monthNamesShort
				});

				// MES DIA
				diaFormat = diaFormat + " " + mesFormato;
			}
			//Poner el dia o mes y dia según caso
			$(idDia+i+j).text(diaFormat);

			//Vacia el campo hidden del valor de la fecha del día
			$(idCantidadMesFecha+i+j).val("");

			//Si el laboral poner oferta y estilos, además desabilitar otros posibles estilos
			if(lista[count].laboral == "LABORAL" && lista[count].oferta == "SI"){
				$(idDia+i+j).addClass("ofertaDay").removeClass("notOferDay").removeClass("partyDay");

				$(idCantidad+i+j).removeClass("notOferDay").removeClass("hayVentasUltimasOfertas").removeClass("ventaAnticipadaDay");
				$(idCantidad+i+j).text($.formatNumber(lista[count].cVD,{format:"0.#",locale:"es"}));

				if(lista[count].cVD >0){
					//Capamos la clase que ejecuta el evento que abre el popup que desglosa las ofertas.
					if(!(idDia.indexOf(calendarioP56) != -1)){
						//Da estilo de link a la cantidad del dia del calendario.
						$(idCantidad+i+j).addClass("hayVentasUltimasOfertas");
					}

					//Llena el campo hidden del valor de la fecha del día
					$(idCantidadMesFecha+i+j).val(lista[count].d);
				}
				if(lista[count].anticipada == 'SI'){
					//Si es venta anticipada
					$(idCantidad+i+j).addClass("ventaAnticipadaDay");
				}
				//Si entra en laboral mostrar fila
				mostrarFila=1;
			}else if(lista[count].laboral == "LABORAL" && lista[count].oferta == "NO"){
				$(idDia+i+j).removeClass("notOferDay").removeClass("partyDay").removeClass("ofertaDay");

				$(idCantidad+i+j).removeClass("notOferDay").removeClass("hayVentasUltimasOfertas").removeClass("ventaAnticipadaDay");
				$(idCantidad+i+j).text($.formatNumber(lista[count].cVD,{format:"0.#",locale:"es"}));

				if(lista[count].cVD >0){
					//Capamos la clase que ejecuta el evento que abre el popup que desglosa las ofertas.
					if(!(idDia.indexOf(calendarioP56) != -1)){
						//Da estilo de link a la cantidad del dia del calendario.
						$(idCantidad+i+j).addClass("hayVentasUltimasOfertas");
					}

					//Llena el campo hidden del valor de la fecha del día
					$(idCantidadMesFecha+i+j).val(lista[count].d);
				}
				if(lista[count].anticipada == 'SI'){
					//Si es venta anticipada
					$(idCantidad+i+j).addClass("ventaAnticipadaDay");
				}
				//Si entra en laboral mostrar fila
				mostrarFila=1;
			}
			//Si el festivo poner oferta y estilos, además desabilitar otros posibles estilos
			else if(lista[count].laboral == "FESTIVO"){
				$(idDia+i+j).addClass("partyDay").removeClass("notOferDay").removeClass("ofertaDay");

				$(idCantidad+i+j).removeClass("notOferDay").removeClass("hayVentasUltimasOfertas").removeClass("ventaAnticipadaDay");
				$(idCantidad+i+j).text($.formatNumber(lista[count].cVD,{format:"0.#",locale:"es"}));

				//Si entra en Festivo mostrar fila
				mostrarFila=1;
			}
			//Si está desabilitado poner estilos, además desabilitar otros posibles estilos
			else if(lista[count].laboral == "DESABILITADO"){
				$(idDia+i+j).addClass("notOferDay").removeClass("ofertaDay").removeClass("partyDay");

				$(idCantidad+i+j).addClass("notOferDay").removeClass("hayVentasUltimasOfertas").removeClass("ventaAnticipadaDay");
				$(idCantidad+i+j).text("");
			}
			count++;
		}
		mostrarFilaDeCalendario(mostrarFila,idDia,idCantidad,i,cantidadDiasSemana);
	}
	//Se pone aquí porque si se pone al cargar la pantalla, el onclick solo afecta al DOM que estaba
	//al cargar la pantalla y no tiene en cuenta los cambios.
	recalcularEnlacesPopupUltimasOfertas();
}

//Función que limpia el calendario si no encuentra datos
function limpiarCalendario(cantidadSemanas,cantidadDiasSemana,idDia,idCantidad,idCantidadMesFecha){
	for(var i = 1;i<=cantidadSemanas;i++){
		for(var j = 1;j<=cantidadDiasSemana;j++){
			//Quitar estilos
			$(idDia+i+j).removeClass("notOferDay").removeClass("ofertaDay").removeClass("partyDay");
			$(idCantidad+i+j).removeClass("notOferDay").removeClass("hayVentasUltimasOfertas").removeClass("ventaAnticipadaDay");

			//Poner nbsp en los elementos
			$(idDia+i+j).html('&nbsp;');
			$(idCantidad+i+j).html('&nbsp;');
			$(idCantidadMesFecha+i+j).val("");
		}
		$(idDia+i+cantidadDiasSemana).parent().removeClass("filaNoVisible");
		$(idCantidad+i+cantidadDiasSemana).parent().removeClass("filaNoVisible");
	}
}

//i equivale a la fila a eliminar, mostrarFila a si hay que mostrarla o no, idCantidad e idDia a los elementos de esa fila.
function mostrarFilaDeCalendario(mostrarFila,idDia,idCantidad,i,cantidadDiasSemana){
	//Conseguir uno de los elementos de la fila a eliminar #idDiaNumeroFila7
	var idDiaTr = idDia+i+cantidadDiasSemana;
	//Conseguir uno de los elementos de la fila a eliminar #idDiaNumeroFila7
	var idCantidadTr = idCantidad+i+cantidadDiasSemana;

	if(mostrarFila == 1){
		$(idDiaTr).parent().removeClass("filaNoVisible");
		$(idCantidadTr).parent().removeClass("filaNoVisible");
	}else{
		$(idDiaTr).parent().addClass("filaNoVisible");
		$(idCantidadTr).parent().addClass("filaNoVisible");
	}
}
/*FUNCIONES DE AYUDA PARA DIBUJAR EL CALENDARIO*/

//Pasas como parámetro un día con formato mmddyyyy y un número X y devuelve el X día siguiente. Ej (01012017,5)-> 06012017
function diasSiguientes(date, days) {
	//date es de tipo ddmmyyyy y para hacer new Date(mm/dd/yyyy) necesitas tipo mm/dd/yyyy
	var mmddyyyy = parseToDate(date);
	var result = new Date(mmddyyyy);
	result.setDate(result.getDate() + days);
	var DiaVentasUltimasOfertas = {d:formatDate(result), dFormat:"",cVD:0,laboral:"DESABILITADO",anticipada:'NO',oferta:'NO'};
	return DiaVentasUltimasOfertas;
}

//Pasas como parámetro un día con formato mmddyyyy y un número X y devuelve el X día anterior. Ej (24012017,5)-> 19012017
function diasPrevios(date, days) {
	//date es de tipo ddmmyyyy y para hacer new Date(mm/dd/yyyy) necesitas tipo mm/dd/yyyy
	var mmddyyyy = parseToDate(date);
	var result = new Date(mmddyyyy);
	result.setDate(result.getDate() - days);
	var DiaVentasUltimasOfertas = {d:formatDate(result), dFormat:"",cVD:0,laboral:"DESABILITADO",anticipada:'NO',oferta:'NO'};
	return DiaVentasUltimasOfertas;
}

//Devuelve un objeto Date() en ddmmyyyy
function formatDate(date) {
	return (("0"+date.getDate()).slice(-2) + '' + ("0" + (date.getMonth() + 1)).slice(-2)+ '' + date.getFullYear());
}

//Cambia ddmmyyyy a mm/dd/yyyy. Para crear un Date() se necesita este formato.
function parseToDate(ddmmyyyy){
	var dia = parseInt(ddmmyyyy.substring(0,2),10);
	var mes = parseInt(ddmmyyyy.substring(2,4),10);
	var anyo = parseInt(ddmmyyyy.substring(4),10);

	var mmddyyyy = mes + '/' + '/' + dia + '/' + anyo;
	return mmddyyyy;
}

//Devuelve una lista con los días siguientes especificados
function listaDiasSiguientes(date,days){
	var lista = [];
	for(var i = 1; i<=days;i++){
		lista.push(diasSiguientes(date, i));
	}
	return lista;
}

//Devuelve una lista con los días anteriores especificados
function listaDiasPrevios(date,days){
	var lista = [];
	for(var i = days; i>=1;i--){
		lista.push(diasPrevios(date, i));
	}
	return lista;
}

//Dias en mes
function diasEnMes(mes,anyo) {
	return new Date(anyo, mes, 0).getDate();
}

//Mira si contiene un mes
function containsMonth(month, list) {
	var i;
	for (i = 0; i < list.length; i++) {
		if (list[i] == month) {
			return true;
		}
	}
	return false;
}