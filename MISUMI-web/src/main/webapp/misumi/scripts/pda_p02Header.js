window.onload = function(){
	
	var inputFechaDesde = document.getElementById('fechaDesde');

    if (inputFechaDesde) {
        inputFechaDesde.onclick = function() {
        	if (this.value && this.value.length >= 2) {
                highlightFirstTwoChars(this); 
            } 
        }
    }
    
    
	if (typeof(initializeScreen_p04) !== 'undefined') {
		//Inicializo la página p04
		initializeScreen_p04();
	}
	if (typeof(initializeScreen_p12) !== 'undefined') {
		//Inicializo la página p12
		initializeScreen_p12();
	}
	if (typeof(initializeScreen_p13) !== 'undefined') {
		//Inicializo la página p13
		initializeScreen_p13();
	}
	if (typeof(initializeScreen_p15) !== 'undefined') {
		//Inicializo la página p15
		initializeScreen_p15();
	}
	if(typeof(initializeScreen_p20) !== 'undefined'){
		//Inicializo la página p20
		initializeScreen_p20();
	}
	if (typeof(initializeScreen_p21) !== 'undefined') {
		//Inicializo la página p21
		initializeScreen_p21();
	}
	if (typeof(initializeScreen_p22) !== 'undefined') {
		//Inicializo la página p22
		initializeScreen_p22();
	}
	if (typeof(initializeScreen_p27) !== 'undefined') {
		//Inicializo la página p27
		initializeScreen_p27();
	}
	if (typeof(initializeScreen_p28) !== 'undefined') {
		//Inicializo la página p27
		initializeScreen_p28();
	}
	if (typeof(initializeScreen_p29) !== 'undefined') {
		//Inicializo la página p30
		initializeScreen_p29();
	}
	if (typeof(initializeScreen_p30) !== 'undefined') {
		//Inicializo la página p30
		initializeScreen_p30();
	}
	if (typeof(initializeScreen_p31) !== 'undefined') {
		//Inicializo la página p31
		initializeScreen_p31();
	}
	if (typeof(initializeScreen_p33) !== 'undefined') {
		//Inicializo la página p33
		initializeScreen_p33();
	}
	if (typeof(initializeScreen_p42) !== 'undefined') {
		//Inicializo la página p42
		initializeScreen_p42();
	}
	if (typeof(initializeScreen_p43) !== 'undefined') {
		//Inicializo la página p4
		initializeScreen_p43();
	}
	if (typeof(initializeScreen_p44) !== 'undefined') {
		//Inicializo la página p4
		initializeScreen_p44();
	}
	if (typeof(initializeScreen_p45) !== 'undefined') {
		//Inicializo la página p45
		initializeScreen_p45();
	}
	if (typeof(initializeScreen_p03InvLibShowMessage) !== 'undefined') {
		//Inicializo la página p03InvLibShowMessage
		initializeScreen_p03InvLibShowMessage();
	}
	if (typeof(initializeScreen_p47) !== 'undefined') {
		//Inicializo la página p45
		initializeScreen_p47();
	}
	if (typeof(initializeScreen_p51) !== 'undefined') {
		//Inicializo la página p51
		initializeScreen_p51();
	}
	/*if (typeof(initializeScreen_p62) !== 'undefined') {
		//Inicializo la página p62
		initializeScreen_p62();
	}*/
	if(typeof(initializeScreen_p61) !== 'undefined'){
		//Inicializo la página p61
		initializeScreen_p61();
	}
	if(typeof(initializeScreen_p64) !== 'undefined'){
		//Inicializo la página p65
		initializeScreen_p64();
	}
	if(typeof(initializeScreen_p65) !== 'undefined'){
		//Inicializo la página p65
		initializeScreen_p65();
	}
	if(typeof(initializeScreen_p67) !== 'undefined'){
		//Inicializo la página p67
		initializeScreen_p67();
	}
	if(typeof(initializeScreen_p69) !== 'undefined'){
		//Inicializo la página p69
		initializeScreen_p69();
	}
	if(typeof(initializeScreen_p70) !== 'undefined'){
		//Inicializo la página p70
		initializeScreen_p70();
	}
	if(typeof(initializeScreen_p71) !== 'undefined'){
		//Inicializo la página p71
		initializeScreen_p71();
	}
	if(typeof(initializeScreen_p73) !== 'undefined'){
		//Inicializo la página p71
		initializeScreen_p73();
	}
	if(typeof(initializeScreen_p80) !== 'undefined'){
		//Inicializo la página p80
		initializeScreen_p80();
	}
	if(typeof(initializeScreen_p91) !== 'undefined'){
		//Inicializo la página p91
		initializeScreen_p91();
	}
	if(typeof(initializeScreen_p92) !== 'undefined'){
		//Inicializo la página p92
		initializeScreen_p92();
	}
	if(typeof(initializeScreen_p93) !== 'undefined'){
		//Inicializo la página p93
		initializeScreen_p93();
	}
	if(typeof(initializeScreen_p94) !== 'undefined'){
		//Inicializo la página p94
		initializeScreen_p94();
	}
	if(typeof(initializeScreen_p95) !== 'undefined'){
		//Inicializo la página p95
		initializeScreen_p95();
	}
	if(typeof(initializeScreen_p98) !== 'undefined'){
		//Inicializo la página p98
		initializeScreen_p98();
	}	
	
	if(typeof(initializeScreen_p102) !== 'undefined'){
		//Inicializo la página p102
		initializeScreen_p102();
	}	
	
	if(typeof(initializeScreen_p103) !== 'undefined'){
		//Inicializo la página p103
		initializeScreen_p103();
	}	

	if(typeof(initializeScreen_p104) !== 'undefined'){
		//Inicializo la página p104. Lista de proveedores.
		initializeScreen_p104();
	}

	if(typeof(initializeScreen_p105) !== 'undefined'){
		//Inicializo la página p105. Lista de bultos por proveedor.
		initializeScreen_p105();
	}
	
	if(typeof(initializeScreen_p107) !== 'undefined'){
		//Inicializo la página p108. Lista de bultos por proveedor.
		initializeScreen_p107();
	}
	
	if(typeof(initializeScreen_p108) !== 'undefined'){
		//Inicializo la página p108. Lista de bultos por proveedor.
		initializeScreen_p108();
	}

	if(typeof(initializeScreen_p109) !== 'undefined'){
		//Inicializo la página p109. Lista de bultos por proveedor.
		initializeScreen_p109();
	}

	// Inicializo la página de error de Packing-List
	if(typeof(initializeScreen_p111) !== 'undefined'){
		//Inicializo la página p111
		initializeScreen_p111();
	}

	//Inicializo la cabecera
	if (typeof(initializeScreen_p42) !== 'undefined' || typeof(initializeScreen_p03InvLibShowMessage) !== 'undefined' ||
			typeof(initializeScreen_p21) !== 'undefined' || typeof(initializeScreen_p22) !== 'undefined' || typeof(initializeScreen_p27) !== 'undefined') {
		//Inicializo la cabecera de la página p42
		initializeHeaderP42();
	}else{
		if (typeof(initializeScreen_p60) !== 'undefined'){
			//Inicializo la cabecera de la página p60
			initializeHeaderP60();
		}else if (typeof(initializeScreen_p62) !== 'undefined'){
			//Inicializo la cabecera de la página p62
			initializeScreen_p62();
		}else if (typeof(initializeScreen_p63) !== 'undefined'){
			//Inicializo la cabecera de la página p63
			initializeScreen_p63();
		}else{
			initializeHeader();
		}
	}
	
}
function initializeHeader(){
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		infoRef.className = infoRef.className + " inputResaltado";
		infoRef.value = "";
		try {
			//Para evitar que de error cuando infoRef sea hidden se coloca el try / catch
			infoRef.focus();
		}
		catch(err) {
			//Error
		}
	}
	events_p01_referencia();
	events_p01_img_cerrar();
}

function initializeHeaderP42(){
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		infoRef.className = infoRef.className + " inputResaltado";
		infoRef.value = "";
	}	
	events_p01_referencia();
	events_p01_img_cerrar();
}

function initializeHeaderP60(){
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		try {
			//Para evitar que de error cuando infoRef sea hidden se coloca el try / catch
			infoRef.focus();
		}
		catch(err) {
			//Error
		}
	}
	events_p01_referencia();
	events_p01_img_cerrar();
}

function events_p01_referencia(){
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		infoRef.onclick = function () {
			limpiarCabecera();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	infoRef = null;
}
function events_p01_img_cerrar(){
	var imagenCerrar =  document.getElementById('pda_p01_imagenCerrar');
	if (imagenCerrar != null && typeof(imagenCerrar) != 'undefined') {
		imagenCerrar.onclick = function () {
			Close_Window();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	imagenCerrar = null;
}

function limpiarCabecera() {
	var infoRef =  document.getElementById('pda_p01_txt_infoRef');
	if (infoRef != null && typeof(infoRef) != 'undefined') {
		infoRef.value = "";
		infoRef.className = infoRef.className + " inputResaltado";
	}
	var descRef =  document.getElementById('pda_p01_txt_descRef');
	if (descRef != null && typeof(descRef) != 'undefined') {
		descRef.value = "";
	}
}
function Close_Window() {
	self.close();
}

function limpiarInput() {
	document.getElementById('pda_p01_btn_aceptar').disabled=true;
	document.getElementById("p12_form").submit();
}

function bloquearEnlaces() {
	document.getElementById('contenidoPagina').style.display = "none";
}


function highlightFirstTwoChars(input) {

    var isIE6 = /MSIE (5\.5|5\.0|6\.0|7\.0)/.test(navigator.userAgent);
    if (input && input.value) {

        var value = input.value;
        if (value.length >= 2) {
            var range;
            if (isIE6) {

                var range = input.createTextRange();
                range.collapse(true);
                range.moveStart('character', 0);
                range.moveEnd('character', 2);  
                range.select();
            } else {

                input.setSelectionRange(0, 2);
            }
        }
    }
}
