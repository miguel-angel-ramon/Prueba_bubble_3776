/**
 * MISUMI-301 JS Calendario Vegalsa
 * 
 * @author BICUGUAL
 */

/*
 * Combo de mapas de Vegalsa
 */
var mapasVegalsaCmb = {
	idHTML : "p108_cmb_mapa",
	idJquery : "#p108_cmb_mapa",
	populate : function() {
		$.ajax({
			type : 'GET',
			url : './calendarioVegalsa/loadMapasCombo.do',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(data) {
				if (data != null) {
					options = "<option value='0'>SIN SELECCIÓN</option>";

					data.forEach(function(item, indice, array) {
						options = options + "<option value='" + item.codigo
								+ "'>" + item.descripcion + "</option>";
					});

					// Ponemos la opción TODOS por defecto.
					$(mapasVegalsaCmb.idJquery).combobox('autocomplete',
							"SIN SELECCIÓN");
					$(mapasVegalsaCmb.idJquery).combobox('comboautocomplete',
							"null");
					$("select" + mapasVegalsaCmb.idJquery).html(options);
				}
			},
			error : function(xhr, status, error) {
				handleError(xhr, status, error, locale);
			}
		});

	},
	getValue: function(){
		return $(mapasVegalsaCmb.idJquery).val();
	},
	getText: function(){
		return $(mapasVegalsaCmb.idJquery + ' option:selected').text();
	},
	onSelect: function(){
		// Evento de seleccion del combo
		$(this.idJquery).combobox({
			selected : function(event, ui) {
				if (ui.item.value != "0" && ui.item.value != "null") {

					isCenterSelected.validate() 
						? calendarioVegalsa.load(calendarioVegalsa.getCurrentDate(), ui.item.value) 
						: isCenterSelected.alert();
					
				} else {
					// Ocultar calendario
					calendarioVegalsa.hide();
				}
			}
		});
	},
	init : function() {
		this.populate();
		this.onSelect();
	}
}

/*
 *Componente de calendario 
 */
var calendarioVegalsa = {
	idHTML : "p108_calendario",
	idJquery : "#p108_calendario",
	monthTextIdJquery : "#p108_calendario_mes",
	daysIdJquery: "#p108_calendario_dias",
	maxMonth:3,
	minMonth:1,
	defaultDatePattern : 'yy-mm',
	selectedDate : new Date(),
	show : function() {
		$(calendarioVegalsa.idJquery).show();
	},
	hide : function() {
		$(calendarioVegalsa.idJquery).hide();
	},
	empty : function() {
		$(calendarioVegalsa.daysIdJquery).empty();
	},
	load : function(date, codMapa) {
		
//		console.log("cargan la fecha: " + date + " para el mapa: "+codMapa);
		
		$.ajax({
			type : 'GET',
			url : './calendarioVegalsa/loadMesCalendario.do?mesAnio='+date+'&codMapa='+codMapa,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			cache : false,
			success : function(lstDays) {
				if (lstDays != null) {
					// cargar de calendario
					calendarioVegalsa.renderizeCalendarPage(date, lstDays);
				}
			},
			error : function(xhr, status, error) {
				handleError(xhr, status, error, locale);
			}
		});

	},
	//Muestra la pagina del calendario
	renderizeCalendarPage: function (date, lstDays) {
		// ocultar calendario
		calendarioVegalsa.hide();
		// limpiar calendario
		calendarioVegalsa.empty();

		//Saltar dias de la semana para empezar a renderizar el dia del mes a partir del primer dia de la semana del mes 
		let numberOfDayInTheWeek=new Date(date).getDay();
//		console.log("La pagina del calendario:"+date +". Empezamos a pintar en el dia de la semana numero: "+ numberOfDayInTheWeek);

		//lunes = 1, martes = 2, miercoles = 3, jueves = 4, viernes = 5, sabado = 6, domingo = 0. 
		if (numberOfDayInTheWeek==0){
			numberOfDayInTheWeek = 7;
		}
		
		for (let hiddenDay = numberOfDayInTheWeek; hiddenDay > 1; hiddenDay--) {
			$(calendarioVegalsa.daysIdJquery).append(createVegalsaDayHtml.getHiddenDay());
		}

		//Renderizamos los dias del calendario que obtenemos con la consulta en el load
		for (let day = 0; day < lstDays.length; day++) {
//			console.log(lstDays[day].fechaCalendario);
			$(calendarioVegalsa.daysIdJquery).append(createVegalsaDayHtml.getDay(lstDays[day]));
		}
		
		// setear la fecha interna del calendario
		calendarioVegalsa.setSelectedDate(date);
		
		//mostrar texto del mes en el calendario
		$(calendarioVegalsa.monthTextIdJquery).html(calendarioVegalsa.formatDate(date, "MM y"));
		
		// mostrar calendario
		calendarioVegalsa.show();

	},
	loadPrevMonth : function() {
		//Obtener fecha
		let date = calendarioVegalsa.getPrevSelectedDate();
		
		//Al paginar hacia atras, activamos la arrow de nextMonth
		calendarioVegalsa.nextMonthArrow.enabled();
		
		//Si se ha llegado a fechaMin desactivamos arrow de prevMonth
		if (calendarioVegalsa.isMonthMin(date) ){
			calendarioVegalsa.prevMonthArrow.disabled();
		} else{
			calendarioVegalsa.prevMonthArrow.enabled();
		}
				
		// cargar calendario
		calendarioVegalsa.load(date, mapasVegalsaCmb.getValue());
	},
	loadNextMonth : function() {
		//Obtener la fecha
		let date = calendarioVegalsa.getNextSelectedDate();
		
		//Al paginar hacia adelante, activamos la arrow de prevMonth
		calendarioVegalsa.prevMonthArrow.enabled();
		
		//Si se ha llegado a fechaMin desactivamos arrow de nextMonth
		if (calendarioVegalsa.isMonthMax(date) ){
			calendarioVegalsa.nextMonthArrow.disabled();
		} else{
			calendarioVegalsa.nextMonthArrow.enabled();
		}

		// cargar calendario
		calendarioVegalsa.load(date, mapasVegalsaCmb.getValue());
		
	},
	getCurrentDate : function(pattern) {
		return calendarioVegalsa.formatDate(new Date(), pattern);
	},
	getPrevSelectedDate : function(pattern) {

		const date = new Date(calendarioVegalsa.selectedDate);
		const month = date.getMonth();
		date.setMonth(date.getMonth() - 1);
		while (date.getMonth() === month) {
			date.setDate(date.getDate() - 1);
		}

		return calendarioVegalsa.formatDate(date, pattern);
	},
	getNextSelectedDate : function(pattern) {
		let	date = new Date(calendarioVegalsa.selectedDate);

		return calendarioVegalsa.formatDate(new Date(date.setMonth(date
				.getMonth() + 1)), pattern);
	},
	getSelectedDate : function(pattern) {
		return calendarioVegalsa.formatDate(calendarioVegalsa.selectedDate,
				pattern);
	},
	setSelectedDate : function(date) {
		calendarioVegalsa.selectedDate = new Date(date);
	},
	isMonthMin : function(date){
		let currentDate = new Date(calendarioVegalsa.getCurrentDate());
		let minDate = new Date(currentDate.setMonth(currentDate.getMonth() - calendarioVegalsa.minMonth));
		let selectedDate = new Date(date);
		
//		console.log("MinDate: "+calendarioVegalsa.formatDate(minDate) + "\nFecha seleccionada: "+ calendarioVegalsa.formatDate(selectedDate));
		
		if (selectedDate.setHours(0,0,0,0) <= minDate.setHours(0,0,0,0)){
			return true
		}
		
		return false;
	},
	isMonthMax : function(date){
		let currentDate = new Date(calendarioVegalsa.getCurrentDate());
		let maxDate = new Date(currentDate.setMonth(currentDate.getMonth() + calendarioVegalsa.maxMonth));
		let selectedDate = new Date(date);
		
//		console.log("MaxDate: "+calendarioVegalsa.formatDate(maxDate) + "\nFecha seleccionada: "+ calendarioVegalsa.formatDate(selectedDate));
		
		if (selectedDate.setHours(0,0,0,0) >= maxDate.setHours(0,0,0,0)){
			return true
		}
		
		return false;
	},
	formatDate : function(date, pattern) {
		let	datepickerEs = $.datepicker;
		// Cambio la i18 del datepicker a ES
		datepickerEs.setDefaults($.extend($.datepicker.regional['es']));

		if (typeof pattern === 'undefined') {
			pattern = calendarioVegalsa.defaultDatePattern;
		}

		return datepickerEs.formatDate(pattern, new Date(date));
	},
	prevMonthArrow : {
		idJquery: "#p108_pagAnterior",
		enabled: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).on('click', this.onClick);
			$(this.idJquery).removeClass("p108_pagAnt_Des").addClass("p108_pagAnt");
		},
		disabled: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).removeClass("p108_pagAnt").addClass("p108_pagAnt_Des");
		},
		onClick: function(){
			isCenterSelected.validate() ? calendarioVegalsa.loadPrevMonth() : isCenterSelected.alert();
		},
		init: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).on('click', this.onClick);
		}
	},
	nextMonthArrow : {
		idJquery: "#p108_pagSiguiente",
		enabled: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).on('click', this.onClick);
			$(this.idJquery).removeClass("p108_pagSig_Des").addClass("p108_pagSig");
		},
		disabled: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).removeClass("p108_pagSig").addClass("p108_pagSig_Des");
		},
		onClick: function(){
			isCenterSelected.validate() ? calendarioVegalsa.loadNextMonth() : isCenterSelected.alert();
		},
		init: function(){
			$(this.idJquery).unbind('click');
			$(this.idJquery).on('click', this.onClick);
		}
	},
	init : function() {
		this.prevMonthArrow.init();
		this.nextMonthArrow.init();
	}
}


var createVegalsaDayHtml = {
	hiddenClassHtml : "p108_diaHidden",
	getHiddenDay: function (){
		let div ="<div class="+ this.hiddenClassHtml + "></div>"; 
		return div;
	},
	getDay: function(day){
		
//		console.log(day);
		
		let nDay = calendarioVegalsa.formatDate(day.fechaCalendario, "dd");
		let fecha = day.fechaPedido != null ? "P. " + day.fechaPedido : '';
		let hora = day.horaPedido != null ? day.horaPedido +' h.'  : ''; 
		let turno = day.turnoReposicion != null ? day.turnoReposicion :'';
		let turnoLabel = "";
		
		if (turno =="M")
			turnoLabel = "Mañana";
		else if (turno =="T")
			turnoLabel = "Tarde";
		
		let title = day.fechaPedido != null ? "Pedido del dia "+day.fechaPedido+"\nReposición en el turno de "+turnoLabel+"\nHora de reposición, las "+hora : "";
		
		let today = day.diaActual != null ? "today" : ''; //today es la clase a añadir para incluir el estilo
		let marcador = day.marcador != null ? "marcador" : '';//marcador es la clase a añadir para incluir el estilo
		let estado  = day.estado == 'CERRADO' ? 'cerrado': ''//cerrado es la clase a añador para incluir el estilo
		

		let $divDiaEstructura = $("<div id='diaEstructura"+nDay+"' class='p108_dia " + today + "' title='Pedido del dia "+fecha+"\nReposición en el turno de "+turnoLabel+"\nHora de reposición, las "+hora+"'>");

		let $divDiaEstructuraDiaMes = $("<div id='diaEstructuraDiaMes"+nDay+"' class='p108_numeroDia "+estado+" "+marcador+"'></div>");
		let $divDiaEstructuraDiaMesLbl = $("<label id='diaEstructuraDiaMesLbl"+nDay+"' class='p108_letraDia'>"+nDay+"</label>");

		let $divDiaEstructuraInfo = $("<div id='diaEstructuraInfo"+nDay+"' class='p108_infoDia'></div>");

		let $divDiaEstructuraInfoReposicion = $("<div id='diaEstructuraInfoReposicion"+nDay+"' class='p108_reposicionDia'></div>");
		let $divDiaEstructuraInfoReposicionTop = $("<div id='diaEstructuraInfoReposicionTop"+nDay+"' class='p108_reposicion_top " + today + "'>"+fecha+"</div>");
		let $divDiaEstructuraInfoReposicionBottom = $("<div id='diaEstructuraInfoReposicionBottom"+nDay+"' class='p108_reposicion_bottom " + today + "' >"+hora+"</div>");
			
		let $divDiaEstructuraInfoTurno = $("<div id='diaEstructuraTurno'"+nDay+"' class='p108_turnoDia "+ estado +"'>");
		let $divDiaEstructuraInfoTurnoTop =  $("<div id='diaEstructuraTurnoTop'"+nDay+"' class='p108_turno_top " + today + "' >"+ (turno.length >0 ? "Turno" :"") +"</div>");
		let $divDiaEstructuraInfoTurnoBottom =  $("<div id='diaEstructuraTurnoBottom'"+nDay+"' class='p108_turno_bottom " + today + "' >"+turno +"</div>");
		
		//Incluir top y Bottom en estructura reposicion info
		$divDiaEstructuraInfoReposicion.append($divDiaEstructuraInfoReposicionTop);
		$divDiaEstructuraInfoReposicion.append($divDiaEstructuraInfoReposicionBottom);
		
		//Incluir top y Bottom en estructura reposicion info
		 if (estado!="cerrado"){
			 $divDiaEstructuraInfoTurno.append($divDiaEstructuraInfoTurnoTop);
			 $divDiaEstructuraInfoTurno.append($divDiaEstructuraInfoTurnoBottom);
		 }
		
		//Incluir info repo y turno en estructura Ingo
		$divDiaEstructuraInfo.append($divDiaEstructuraInfoReposicion);
		$divDiaEstructuraInfo.append($divDiaEstructuraInfoTurno);
		
		//Incluir dia del mes
		$divDiaEstructuraDiaMes.append($divDiaEstructuraDiaMesLbl);

		//Incluir el dia completo en la estructura
		$divDiaEstructura.append($divDiaEstructuraDiaMes);
		$divDiaEstructura.append($divDiaEstructuraInfo);
		
		return $divDiaEstructura;
	}
}


var resumenVegalsaBtn = {
	idHTML : "p108_resumen_btn",
	idJquery : "#p108_resumen_btn",
	onClick : function(){
//		console.log('click en resumen');
		//Ejecucion de apertura de modal con validacion de centro previa
		isCenterSelected.validate() ? modalCalendarioVegalsa.open() : isCenterSelected.alert();	
	},
	init : function() {
		$(this.idJquery).unbind('click');
		$(this.idJquery).on('click', this.onClick);
	}
}



var isCenterSelected = {
	idJquery: "#centerId",
	validate: function (){
		if ($("#centerId").val()==null || $("#centerId").val()==""){
			return false;
		}
		return true;
	},
	alert: function (){
		createAlert(replaceSpecialCharacters(centerRequired), "ERROR");
	}
}

var initializeScreenp108 = function() {

	mapasVegalsaCmb.init();
	calendarioVegalsa.init();
	resumenVegalsaBtn.init();

}

$(document).ready(function() {
	initializeScreenp108();
});