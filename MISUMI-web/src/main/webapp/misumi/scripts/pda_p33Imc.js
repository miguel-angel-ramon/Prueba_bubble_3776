var ejecutarEventoBlur = true;
var tratamientoVegalsaElemento=null;
var facAltoOriginal;
var facAnchoOriginal;
var facOriginal;

function initializeScreen_p33(){
	
	/*
	 *MISUMI-395 
	 */
//	tratamientoVegalsaElemento=document.getElementById("pda_p33_fld_tratamientoVegalsa");
	
	if (document.getElementById("pda_p33_fld_tratamientoVegalsa") == null){
		tratamientoVegalsaElemento="false";
	} else {
		tratamientoVegalsaElemento=document.getElementById("pda_p33_fld_tratamientoVegalsa").value;
	}
	
	//Sirve para simular el IMC
	events_p33_simularImc();
	//Sirve para modificar el IMC
	events_p33_modificarImc();
	//Eventos de cálculo facing, ancho, alto, capacidad
	events_p33_calculate();
	// Guardar los datos de facing original al entrar en la pantalla
	save_initial_facing();
}

function save_initial_facing(){
	
	if (tratamientoVegalsaElemento=="false"){
		facAltoOriginal = document.getElementById("pda_p33_fld_facAlto").value;
		facAnchoOriginal = document.getElementById("pda_p33_fld_facAncho").value;
	}
	
	facOriginal = document.getElementById("pda_p33_fld_facing").value;
}

function events_p33_simularImc(){	
	//Conseguir todas los input que se necesiten para simular el imc
	var inputSimular =  getElementsByClassName(document.body,'inputEditable');

	//Mirar que los elementos no sean vacíos y les añadimos el evento
	if (inputSimular != null && typeof(inputSimular) != 'undefined' && inputSimular != null && typeof(inputSimular) != 'undefined') {
		for (var i = 0; i < inputSimular.length; i++) {
			/*inputSimular[i].onkeydown = function(e) {
				e = e || window.event;
				var key = e.which || e.keyCode; //para soporte de todos los navegadores								
				if (key == 13){		
					if(e.preventDefault) {
						e.preventDefault();
					} else {
						e.returnValue = false;
					}

					//Evitamos que ejecute más eventos.					
					simularImc(this);	

					//Evitamos que se ejecute el blur
					//ejecutarEventoBlur = false;
					//document.getElementById("pda_p01_txt_infoRef").focus();															
				}
			}
			inputSimular[i].onchange = function(e) {
				simularImc(this);	
			}*/
			//Si clicamos el campo, lo selecciona todo.
			inputSimular[i].onclick = function(e) {
				this.select();		
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputSimular = null;
}

function simularImc(elemento){
	var codArt = document.getElementById("pda_p33_refActual").value;
	var facing = document.getElementById("pda_p33_fld_facing").value != "" ? document.getElementById("pda_p33_fld_facing").value : 1;
	var multiplicador = document.getElementById("pda_p33_fld_multiplicador").value;
	var tipoReferencia = document.getElementById("pda_p33_tipoReferencia").value;
	var pdaDatosImc =  
	{
			"codArt": codArt,
			"facing": facing,
			"multiplicador": multiplicador,
			"tipoReferencia": tipoReferencia
	}; 

	var objJson = JSON.stringify(pdaDatosImc);
	var url = './pdaP33SimularImc.do';
	var method = 'POST';
	ocultarBotonGuardar();
	mostrarCapaLoading();
	postData(url, method, objJson, true, "application/json; charset=utf-8", cambiarImc, hayErrorSimular);
}

function cambiarImc(data){
	//Miramos si existe algún campo editable vacio o incorrecto.
	//Si existe, coloreamos el input de rojo.
	var inputEditable =  getElementsByClassName(document.body,'inputEditable');
	var mostrarGuardar = true;
	for(var i = 0; i< inputEditable.length; i++){
		var valorInput = inputEditable[i].value;
		var numeroIsOk = /^\b(?![0]\b)\d{1,4}\b$/.test(valorInput);
		if(numeroIsOk){
			//Quitamos el rojo del campo.
			inputEditable[i].className = inputEditable[i].className.replace(/pda_p33_redInput/g,'');
		}else{
			//Quitamos el rojo del campo.
			inputEditable[i].className = inputEditable[i].className.replace(/pda_p33_redInput/g,'');

			//Pintamos de rojo si está mal
			inputEditable[i].className = inputEditable[i].className + " pda_p33_redInput";
			
			//No mostramos guardar
			mostrarGuardar = false;
		}
	}
	
	//Si no hay errores mostramos el guardar tras simular.
	if(mostrarGuardar){
		mostrarBotonGuardar();		
	}

	//Ocultamos la capa del loading
	ocultarCapaLoading();
	
	//Si imc es diferente que 0, significa que no ha habido error en la simulación y actualizamos la imagen comercial.
	//Si no, ha habido error.
	if(data.codError != 0){	
		//Mostramos bloque de error.
		mostrarError("ERROR EN RECÁLCULO IMC");
	}else{	
		//Escondemos bloque de error.
		ocultarError();

		//Actualizamos el imc
		document.getElementById("pda_p33_fld_imagenMinimaComercial").value = data.imc;
		
		//Miramos si el imc es mayor que capacidad. En ese caso, se actualiza la capacidad.
		var capacidadInput = document.getElementById("pda_p33_fld_capacidad");
		var capacidad = capacidadInput.value != "" ? parseInt(capacidadInput.value) : 0;
		if(data.imc > capacidad){
			capacidadInput.value = data.imc;
		}
	}
}

//Valida el campo 
/*function validacionInput(elemento){
	//Validamos el formato del valor 
	var numeroIsOk = /^\b(?![0]\b)\d{1,4}\b$/.test(elemento.value);
	var resultadoValidacion;

	//Pintamos de rojo si está mal
	if (!numeroIsOk){
		resultadoValidacion = false;
	}else{
		resultadoValidacion = true;
	}

	//Si la validación de formato no pasa se colorea de rojo.
	if(!resultadoValidacion){
		//Quitamos el rojo del campo.
		elemento.className = elemento.className.replace(/pda_p33_redInput/g,'');

		//Pintamos de rojo si está mal
		elemento.className = elemento.className + " pda_p33_redInput";
	}else{	
		//Quitamos el rojo del campo.
		elemento.className = elemento.className.replace(/pda_p33_redInput/g,'');
	}

	//Miramos si existe algún campo rojo. Si existe, no se simula y no se muestra el guardar.
	var inputError =  getElementsByClassName(document.body,'pda_p33_redInput');
	var existeInputError;

	if(inputError.length > 0){
		existeInputError = false;
		ocultarBotonGuardar();
	}else{
		existeInputError = true;		
		mostrarBotonGuardar();
	}

	return existeInputError;
}*/

function hayErrorSimular(){
	ocultarCapaLoading();

	//Mostramos bloque de error.
	mostrarError("ERROR EN RECÁLCULO IMC");

	ocultarBotonGuardar();
}

function hayErrorGuardar(){
	ocultarCapaLoading();
	//Mostramos bloque de error.
	mostrarError("ERROR EN GUARDADO");

	mostrarBotonGuardar();
}

function events_p33_modificarImc(){	
	//Conseguir todas los input que se necesiten para modificar el imc
	var botonModificar = document.getElementById('pda_p33_btn_save');
	
	botonModificar.onclick = function(e) {

		var facing = document.getElementById("pda_p33_fld_facing").value;

		if (tratamientoVegalsaElemento=="false"){
			var facAlto = document.getElementById("pda_p33_fld_facAlto").value;
			var facAncho = document.getElementById("pda_p33_fld_facAncho").value;

			// Si hay diferencia entre el valor original y el actual.
			if (facAlto!=facAltoOriginal || facAncho!=facAnchoOriginal){

				// Si la diferencia es superior a 2 puntos.
				if (Math.abs(facAlto - facAltoOriginal) >= 2 || Math.abs(facAncho - facAnchoOriginal) >= 2 ){
					var msg = "Aviso: Va a pasar de un facing de "+facOriginal+" a uno de "+facing+". ¿Esta seguro?";
					if (confirm(msg)){
						modificarImc();
					}else{
						document.getElementById("pda_p33_fld_facAlto").value = facAltoOriginal;
						document.getElementById("pda_p33_fld_facAncho").value = facAnchoOriginal;
						document.getElementById("pda_p33_fld_facing").value = facOriginal;
					}
				}else{
					modificarImc();
				}
			}
					
		// Si es VEGALSA.
		}else{
			modificarImc();
		}

	}
}

function modificarImc(){
	//En este momento todos los campos serán válidos porque si no lo son
	//no se mostraría el botón de modificación
	var codArt = document.getElementById("pda_p33_refActual").value;
	var capacidad = document.getElementById("pda_p33_fld_capacidad").value;
	var facing = document.getElementById("pda_p33_fld_facing").value;
	var multiplicador = document.getElementById("pda_p33_fld_multiplicador").value;
	var procede = document.getElementById("pda_p33_procede").value;


	var tipoReferencia="";
	var facAlto="";
	var facAncho="";
	var imc="";
	
	if (tratamientoVegalsaElemento == "false"){
		tipoReferencia = document.getElementById("pda_p33_tipoReferencia").value;
		facAlto = document.getElementById("pda_p33_fld_facAlto").value;
		facAncho = document.getElementById("pda_p33_fld_facAncho").value;
		imc = document.getElementById("pda_p33_fld_imagenMinimaComercial").value;
	}

	var pdaDatosImc =  
	{
			"codArt": codArt,
			"capacidad" : capacidad,
			"facing": facing,
			"multiplicador": multiplicador,
			"tipoReferencia": tipoReferencia,
			"facAlto" : facAlto,
			"facAncho" : facAncho,
			"imc" : imc,
			"procede" : procede
	}; 

	var objJson = JSON.stringify(pdaDatosImc);
	var url = './pdaP33ModificarImc.do';
	var method = 'POST';
	mostrarCapaLoading();
	postData(url, method, objJson, true, "application/json; charset=utf-8", volver, hayErrorGuardar);
}

function volver(data){
	
	//Si codError es diferente que 0, significa que no ha habido error en la modificación.
	//Si no, ha habido error.
	if (data.procede != "" && data.procede == "pdaP98CapturaRestos"){
		returnURL = "./pdaP98CapturaRestos.do?codArt="+data.referencia+"&guardadoImc=S";
	}else if(data.procede != "" && data.procede == "pdaP99SacadaRestos"){
		returnURL = "./pdaP99SacadaRestos.do?codArt="+data.referencia+"&guardadoImc=S";
	}else if(data.procede != "" && data.procede == "pdaP115PrehuecosLineal"){
		returnURL = "./pdaP115PrehuecosLineal.do?codArt="+data.referencia+"&guardadoImc=S";
	}else if(data.procede != "" && data.procede == "pdaP118DatosRefDetalleReferencia"){
		returnURL = "./pdaP118DatosRefDetalleReferencia.do?codArt="+data.referencia+"&guardadoImc=S";
	}else{
		returnURL = "./pdaP12DatosReferencia.do?codArt="+data.referencia+"&guardadoImc=S";
	}

	if (data.tratamientoVegalsa == 1){

		if (data.codError != 0){	

			var mensajeError = "ERROR AL GUARDAR.";
			
			if (data.tratamientoVegalsa == 1){
				mensajeError = data.descripcionError;
			}
			
			//Mostramos bloque de error.
			mostrarError(mensajeError);
			
			ocultarCapaLoading();
			
		// Si NO hay ERROR.
		}else{
			//Aquí no ocultamos la capa porque redireccionamos a pdaP12
			//Escondemos bloque de error.
			ocultarError();

			//Volvemos a la pantalla de datos de referencia indicando el guardado de IMC
			window.location.href = returnURL;
		}
	}else{
		// Si NO es un centro VEGALSA y está PARAMETRIZADO.
		if (data.centroParametrizado == 1){
			//Aquí no ocultamos la capa porque redireccionamos a pdaP12
			//Escondemos bloque de error.
			ocultarError();

			//Volvemos a la pantalla de datos de referencia indicando el guardado de IMC
			window.location.href = returnURL+"&centroParametrizado=SI";
			
		}else{
			if(data.codError != 0){	

				var mensajeError = "ERROR AL GUARDAR.";
				
				//Mostramos bloque de error.
				mostrarError(mensajeError);
				
				ocultarCapaLoading();
				
			// Si NO hay ERROR.
			}else{
				//Aquí no ocultamos la capa porque redireccionamos a pdaP12
				//Escondemos bloque de error.
				ocultarError();

				//Volvemos a la pantalla de datos de referencia indicando el guardado de IMC
				window.location.href = returnURL;
			}
		}
	}
}

function mostrarError(descError){
	var capaError = document.getElementById('pda_p33_imc_error');
	capaError.style.visibility = "visible";
	var error = document.getElementById('pda_p33_fld_error');
	error.innerHTML = descError;
	error.style.visibility = "visible";
}
function ocultarError(){
	var error =  document.getElementById('pda_p33_fld_error');
	error.innerHTML = "";
	error.style.visibility = "hidden";			
}

function mostrarCapaLoading(){	
	var loading =  document.getElementById('capaLoading');
	loading.style.visibility = "visible";
}
function ocultarCapaLoading(){
	var loading =  document.getElementById('capaLoading');
	loading.style.visibility = "hidden";
}

function mostrarBotonGuardar(){
	document.getElementById("pda_p33_btn_save").style.display = "block";
}
function ocultarBotonGuardar(){
	document.getElementById("pda_p33_btn_save").style.display = "none";
}
