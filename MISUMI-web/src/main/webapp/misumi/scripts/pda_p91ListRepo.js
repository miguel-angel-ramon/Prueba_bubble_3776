var ejecutarEventoBlur = true;

function initializeScreen_p91(){
	events_p91_guardarRepo();
	events_p91_foto();
	events_p91_borrar();
	events_p91_finalizar();
	events_p91_stockLink();
	events_p91_colorLink();
	events_p91_descrLink();
	events_p91_guardarStockInput();
}


function events_p91_guardarRepo(){	
	//Conseguir todas los input de repo
	var inputRepo =  getElementsByClassName(document.body,'pda_p91_listRepo_bloqueStockRepo_repo');

	//Mirar que los elementos no sean vacíos
	if (inputRepo != null && typeof(inputRepo) != 'undefined' && inputRepo != null && typeof(inputRepo) != 'undefined') {
		for (var i = 0; i < inputRepo.length; i++) {
			inputRepo[i].onkeydown = function(e) {
				e = e || window.event;
				var key = e.which || e.keyCode; //para soporte de todos los navegadores								
				if (key == 13){		
					if(e.preventDefault) {
						e.preventDefault();
					} else {
						e.returnValue = false;
					}
					
					//Evitamos que ejecute más eventos.					
					guardarRepo(this);	
					
					//Evitamos que se ejecute el blur
					ejecutarEventoBlur = false;
					document.getElementById("pda_p01_txt_infoRef").focus();										
					
				}
			}
			inputRepo[i].onblur = function(e) {
				if(ejecutarEventoBlur){
					guardarRepo(this);	
				}else{
					ejecutarEventoBlur = true;
				}
			}
			inputRepo[i].onclick = function(e) {
				this.select();		
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputRepo = null;
}

function guardarRepo(elemento){
	var codArt = elemento.getAttribute("data-codart");
	var elementoOrigRepo = document.getElementById('pda_p91_listRepo_bloqueStockRepo_repo'+codArt);
	
	if(validacionRepoStock(elemento,elementoOrigRepo)){						
		var cantRepo = elemento.value.replace(',','.');
		var modeloProveedor =  document.getElementById('pda_p91_fld_descripcionRef').getAttribute("data-mp");
		var color =  document.getElementById('pda_p91_fld_queColor') != null ? document.getElementById('pda_p91_fld_queColor').innerHTML : null; 
		var reposicionGuardar = new ReposicionGuardar(null,null,null,modeloProveedor,codArt,color,cantRepo);

		var objJson = JSON.stringify(reposicionGuardar);
		var url = './pdaP91ListadoRepoGuardar.do';
		var method = 'POST';
		postData(url, method, objJson, true, "application/json; charset=utf-8", colorearRepo, hayError);
	}		
}
function events_p91_guardarStockInput(){
	//Conseguir todas los input de stock
	var inputStock =  getElementsByClassName(document.body,'pda_p91_listRepo_bloqueStockRepo_stock');

	//Mirar que los elementos no sean vacíos
	if (inputStock != null && typeof(inputStock) != 'undefined' && inputStock != null && typeof(inputStock) != 'undefined') {
		for (var i = 0; i < inputStock.length; i++) {
			inputStock[i].onkeydown = function(e) {
				e = e || window.event;
				var key = e.which || e.keyCode; //para soporte de todos los navegadores
				if (key == 13){		
					if(e.preventDefault) {
						e.preventDefault();
					} else {
						e.returnValue = false;
					}
					
					guardarStock(this);
				
					//Evitamos que se ejecute el blur
					ejecutarEventoBlur = false;
					document.getElementById("pda_p01_txt_infoRef").focus();		
				}
			}
			inputStock[i].onblur = function(e) {
				if(ejecutarEventoBlur){
					guardarStock(this);	
				}else{
					ejecutarEventoBlur = true;
				}		
			}
			inputStock[i].onclick = function(e) {
				this.select();		
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputStock = null;
}

function guardarStock(elemento){
	var codArt = elemento.getAttribute("data-codart");
	var elementoOrigStock = document.getElementById('pda_p91_listRepo_bloqueStockRepo_stock'+codArt);
	if(validacionRepoStock(elemento,elementoOrigStock)){
		var cantStock = elemento.value.replace(',','.');
		var stockCodart = 'stock='+cantStock+'&codArt='+codArt;

		var objJson = JSON.stringify(stockCodart);
		var url = './pdaP91ListadoRepoGuardarStock.do';
		var method = 'POST';
		postData(url, method, stockCodart, true, "application/x-www-form-urlencoded", colorearStock, hayError);
	}
}

function events_p91_foto(){
	var foto =  document.getElementById('pda_p91_listRepo_foto');

	if (foto != null && typeof(foto) != 'undefined') {
		foto.onclick = function () {
			var codArtFoto = this.getAttribute("data-codArtFoto");
			var paginaActual =  document.getElementById('pda_p91_listRepo_bloqueFlechasBoton_numeroPagina').getAttribute("data-pagina");		
			var paginaTalla =  document.getElementById('pda_p91_listRepo_bloqueStockRepo_numeroPagina') != null ? document.getElementById('pda_p91_listRepo_bloqueStockRepo_numeroPagina').getAttribute("data-pagina") : 1;
			
			window.location.href = "./pdaP93FotoAmpliada.do?codArtFoto=" + codArtFoto + "&paginaActual="+paginaActual+"&origen=pdaP91ListadoRepo&pgSubList="+paginaTalla;
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	foto = null;
}

function events_p91_borrar(){
	var borrar =  document.getElementById('pda_p91_listRepo_bloqueBoton_borrar');

	if (borrar != null && typeof(borrar) != 'undefined') {
		borrar.onclick = function () {
			document.getElementById("pda_p91_listRepo_borrar_form").submit();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	borrar = null;
}

function events_p91_finalizar(){
	var finalizar =  document.getElementById('pda_p91_listRepo_btn_fin');

	if (finalizar != null && typeof(finalizar) != 'undefined') {
		finalizar.onclick = function () {
			document.getElementById("pda_p91_listRepo_finalizar_form").submit();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	finalizar = null;
}

function events_p91_stockLink(){
	//Conseguir todos los links de stock
	var stockLink =  getElementsByClassName(document.body,'pda_p91_listRepo_bloqueStockRepo_stockLink');

	//Mirar que los elementos no sean vacíos
	if (stockLink != null && typeof(stockLink) != 'undefined' && stockLink != null && typeof(stockLink) != 'undefined') {
		for (var i = 0; i < stockLink.length; i++) {
			stockLink[i].onclick = function(e) {
				document.getElementById('pda_p91_listRepo_codArtStk').value = this.getAttribute("data-codart");
				document.getElementById("pda_p91_listRepo_stock_form").submit();				
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	stockLink = null;
}

function events_p91_descrLink(){
	//Conseguir todos los links de descripcion
	var descrLink =  getElementsByClassName(document.body,'pda_p91_listRepo_bloqueStockRepo_descrLink');

	//Mirar que los elementos no sean vacíos
	if (descrLink != null && typeof(descrLink) != 'undefined' && descrLink != null && typeof(descrLink) != 'undefined') {
		for (var i = 0; i < descrLink.length; i++) {
			descrLink[i].onclick = function(e) {
				var paginaActual =  document.getElementById('pda_p91_listRepo_bloqueFlechasBoton_numeroPagina').getAttribute("data-pagina");
				var paginaTalla =  document.getElementById('pda_p91_listRepo_bloqueStockRepo_numeroPagina') != null ? document.getElementById('pda_p91_listRepo_bloqueStockRepo_numeroPagina').getAttribute("data-pagina") : 1;
				var textoLink =   document.getElementById('pda_p91_listRepo_bloqueStockRepo_descrLink'+this.innerText);
				var codArt =  document.getElementById('pda_p91_listRepo_bloqueStockRepo_descrLink'+this.innerText).getAttribute("data-codArtTalla");
				window.location.href = "./pdaP95DescripcionDetalle.do?descripcion="+textoLink.innerText+"&paginaActual="+paginaActual+"&origen=pdaP91ListadoRepo.do&pgSubList="+paginaTalla+"&codArt="+codArt;		
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	descrLink = null;
}

function events_p91_colorLink(){
	//Conseguir todos los links de descripcion
	var colorLink =  document.getElementById('pda_p91_listRepo_bloqueColor_descrLink');

	//Mirar que los elementos no sean vacíos
	if (colorLink != null && typeof(colorLink) != 'undefined' && colorLink != null && typeof(colorLink) != 'undefined') {
		colorLink.onclick = function(e) {
			var paginaActual =  document.getElementById('pda_p91_listRepo_bloqueFlechasBoton_numeroPagina').getAttribute("data-pagina");
			var paginaTalla =  document.getElementById('pda_p91_listRepo_bloqueStockRepo_numeroPagina') != null ? document.getElementById('pda_p91_listRepo_bloqueStockRepo_numeroPagina').getAttribute("data-pagina") : 1;
			var textoLink = document.getElementById('pda_p91_fld_queColor');
			window.location.href = "./pdaP95DescripcionDetalle.do?descripcion="+textoLink.innerText+"&paginaActual="+paginaActual+"&origen=pdaP91ListadoRepo.do&pgSubList="+paginaTalla;		
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	colorLink = null;
}

function colorearRepo(data){
	//Obtenemos el input
	var inputsCodArt = getElementsByAttribute('data-codart',data.codArt);
	var inputAColorear = inputsCodArt.length > 1 ? inputsCodArt[1] : inputsCodArt[0];
	if(data.codError == 0 || data.codError == null){		
		inputAColorear.className = inputAColorear.className.replace('pda_p91_redInput','');
		inputAColorear.className = inputAColorear.className + " pda_p91_blueInput";
		tabularAVacio('pda_p91_listRepo_bloqueStockRepo_repo');

		var error =  document.getElementById('pda_p91_fld_errorColor');
		error.style.display = "none";
		
		//Actualizamos el hidden con el nuevo valor cuando se guarda.
		document.getElementById("pda_p91_listRepo_bloqueStockRepo_repo"+data.codArt).value = inputAColorear.value;
	}else{		
		inputAColorear.className = inputAColorear.className.replace('pda_p91_blueInput','');
		inputAColorear.className = inputAColorear.className + " pda_p91_redInput";
		inputAColorear.value = "";

		var error =  document.getElementById('pda_p91_fld_errorColor');
		error.innerHTML = data.descError;
		error.style.display = "block";			 	
	}
}

function colorearStock(data){
	//Obtenemos el input
	var inputsCodArt = getElementsByAttribute('data-codart',data.codigoReferenciaModificada);

	//Puede haber input de stock y de repo.
	var inputAColorear = inputsCodArt[0];
	if(data.codigoRespuesta == "OK" || data.codigoRespuesta == "WN"){		
		inputAColorear.className = inputAColorear.className.replace('pda_p91_redInput','');
		inputAColorear.className = inputAColorear.className + " pda_p91_blueInput";
		tabularAVacio('pda_p91_listRepo_bloqueStockRepo_stock');

		var error =  document.getElementById('pda_p91_fld_errorColor');
		error.style.display = "none";
		
		//Actualizamos el hidden con el nuevo valor cuando se guarda.
		document.getElementById("pda_p91_listRepo_bloqueStockRepo_stock"+data.codigoReferenciaModificada).value = inputAColorear.value;
	}else{		
		inputAColorear.className = inputAColorear.className.replace('pda_p91_blueInput','');
		inputAColorear.className = inputAColorear.className + " pda_p91_redInput";
		inputAColorear.value = "";

		var error =  document.getElementById('pda_p91_fld_errorColor');
		error.innerHTML = data.descripcionRespuesta;
		error.style.display = "block";			 	
	}
}

function hayError(){
	alert("Ha ocurrido un error");
}

function tabularAVacio(id){
	var inputRepo =  getElementsByClassName(document.body,id);
	for(var i=0;i<inputRepo.length;i++){
		if(inputRepo[i].value == ""){
			inputRepo[i].focus();
			break;
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	inputRepo = null;
}


//Valida el campo repo y sotck.
function validacionRepoStock(elemento,elementoOrig){
	//Validamos el formato del valor
	var numeroDouble=elemento.value.replace('.',',');
	var numeroDoubleIsOk = /^(-?\d+((\,)\d{0,3}){0,1})?$/.test(numeroDouble);
	var resultadoValidacionFmt;

	//Pintamos de rojo si está mal
	if (!numeroDoubleIsOk || elemento.value == ""){
		resultadoValidacionFmt = false;
	}else{
		resultadoValidacionFmt = true;
	}
	
	//Validamos que cambia el valor
	var valorActual = elemento.value;
	var valorOrig = elementoOrig.value;
	var resultadoValidacionValor;
	
	if(valorActual != valorOrig){
		resultadoValidacionValor = true;
	}else{
		resultadoValidacionValor =  false;
	}
	
	//Si la validación de formato no pasa se colorea de rojo.
	if(!resultadoValidacionFmt){
		//Quitamos el azul de posibles guardados anteriores
		elemento.className = elemento.className.replace('pda_p91_blueInput','');

		//Quitamos el rojo de posibles guardados fallidos anteriores.
		elemento.className = elemento.className.replace('pda_p91_redInput','');
		
		//Pintamos de rojo si está mal
		elemento.className = elemento.className + " pda_p91_redInput";
	}else{
		//Quitamos el azul de posibles guardados anteriores.
		elemento.className = elemento.className.replace('pda_p91_blueInput','');
		
		//Quitamos el rojo de posibles guardadosfallidos anteriores.
		elemento.className = elemento.className.replace('pda_p91_redInput','');
		
		//Quitamos el rojo del campo.
		elemento.className = elemento.className.replace(/pda_p91_redInput/g,'');
	}
	
	return resultadoValidacionFmt && resultadoValidacionValor;
}