var ejecutarEventoBlur = true;

function initializeScreen_p92(){
	events_p92_foto();
	events_p92_guardarRepo();
	events_p92_RevSustRepo();
	events_p92_guardarStockInput();
	events_p92_descrLink();
	events_p92_colorLink();
	events_p92_stockLink();
	events_p92_seccion();
}

function events_p92_guardarRepo(){
	//Conseguir todas los input de repo
	var inputRepo =  getElementsByClassName(document.body,'pda_p92_listRepoAnt_bloqueStockRepo_repo');

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
	var elementoOrigRepo = document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_repo'+codArt);

	if(validacionRepoStock(elemento,elementoOrigRepo)){
		var cantRepo = elemento.value.replace(',','.');
		var modeloProveedor =  document.getElementById('pda_p92_fld_descripcionRef').getAttribute("data-mp");
		var color =  document.getElementById('pda_p92_fld_queColor') != null ? document.getElementById('pda_p92_fld_queColor').innerHTML : null; 
		var reposicionGuardar = new ReposicionGuardar(null,null,null,modeloProveedor,codArt,color,cantRepo);

		var objJson = JSON.stringify(reposicionGuardar);
		var url = './pdaP92ListadoRepoAntGuardar.do';
		var method = 'POST';
		postData(url, method, objJson, true, "application/json; charset=utf-8" , colorearRepo, hayError);
	}
}
function events_p92_guardarStockInput(){
	//Conseguir todas los input de stock
	var inputStock =  getElementsByClassName(document.body,'pda_p92_listRepoAnt_bloqueStockRepo_stock');

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
	var elementoOrigStock = document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_stock'+codArt);
	if(validacionRepoStock(elemento,elementoOrigStock)){
		var cantStock = elemento.value.replace(',','.');
		var stockCodart = 'stock='+cantStock+'&codArt='+codArt;

		var objJson = JSON.stringify(stockCodart);
		var url = './pdaP92ListadoRepoAntGuardarStock.do';
		var method = 'POST';
		postData(url, method, stockCodart, true, "application/x-www-form-urlencoded", colorearStock, hayError);
	}
}

function events_p92_foto(){
	var foto =  document.getElementById('pda_p92_listRepoAnt_foto');

	if (foto != null && typeof(foto) != 'undefined') {
		foto.onclick = function () {
			var codArtFoto = this.getAttribute("data-codArtFoto");
			var paginaActual =  document.getElementById('pda_p92_listRepoAnt_bloqueFlechasBoton_numeroPagina').getAttribute("data-pagina");
			var paginaTalla =  document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_numeroPagina') != null ? document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_numeroPagina').getAttribute("data-pagina") : 1;
			var seccion = document.getElementById("pda_p01_txt_seccion").value;
			
			window.location.href = "./pdaP93FotoAmpliada.do?codArtFoto=" + codArtFoto + "&paginaActual="+paginaActual+"&origen=pdaP92ListadoRepoAnt&pgSubList="+paginaTalla+"&seccion="+seccion;
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	foto = null;
}

function events_p92_stockLink(){
	//Conseguir todos los links de stock
	var stockLink =  getElementsByClassName(document.body,'pda_p92_listRepoAnt_bloqueStockRepo_stockLink');

	//Mirar que los elementos no sean vacíos
	if (stockLink != null && typeof(stockLink) != 'undefined' && stockLink != null && typeof(stockLink) != 'undefined') {
		for (var i = 0; i < stockLink.length; i++) {
			stockLink[i].onclick = function(e) {
				document.getElementById('pda_p92_listRepoAnt_codArtStk').value = this.getAttribute("data-codart");
				document.getElementById("pda_p92_listRepoAnt_stock_form").submit();				
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	stockLink = null;
}

function events_p92_descrLink(){
	//Conseguir todos los links de descripcion
	var descrLink =  getElementsByClassName(document.body,'pda_p92_listRepoAnt_bloqueStockRepo_descrLink');

	//Mirar que los elementos no sean vacíos
	if (descrLink != null && typeof(descrLink) != 'undefined' && descrLink != null && typeof(descrLink) != 'undefined') {
		for (var i = 0; i < descrLink.length; i++) {
			descrLink[i].onclick = function(e) {
				var paginaActual =  document.getElementById('pda_p92_listRepoAnt_bloqueFlechasBoton_numeroPagina').getAttribute("data-pagina");	
				var paginaTalla =  document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_numeroPagina') != null ? document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_numeroPagina').getAttribute("data-pagina") : 1;
				var textoLink =   document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_descrLink'+this.innerText);
				var codArt =  document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_descrLink'+this.innerText).getAttribute("data-codArtTalla");
				var seccion = document.getElementById("pda_p01_txt_seccion").value;
				window.location.href = "./pdaP95DescripcionDetalle.do?descripcion="+textoLink.innerText+"&paginaActual="+paginaActual+"&origen=pdaP92ListadoRepoAnt.do&pgSubList="+paginaTalla+"&codArt="+codArt+"&seccion="+seccion;		
			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	descrLink = null;
}

function events_p92_colorLink(){
	//Conseguir todos los links de descripcion
	var colorLink =  document.getElementById('pda_p92_listRepoAnt_bloqueColor_descrLink');

	//Mirar que los elementos no sean vacíos
	if (colorLink != null && typeof(colorLink) != 'undefined' && colorLink != null && typeof(colorLink) != 'undefined') {
		colorLink.onclick = function(e) {
			var paginaActual =  document.getElementById('pda_p92_listRepoAnt_bloqueFlechasBoton_numeroPagina').getAttribute("data-pagina");
			var paginaTalla =  document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_numeroPagina') != null ? document.getElementById('pda_p92_listRepoAnt_bloqueStockRepo_numeroPagina').getAttribute("data-pagina") : 1;
			var textoLink = document.getElementById('pda_p92_fld_queColor');
			var seccion = document.getElementById("pda_p01_txt_seccion").value;
			window.location.href = "./pdaP95DescripcionDetalle.do?descripcion="+textoLink.innerText+"&paginaActual="+paginaActual+"&origen=pdaP92ListadoRepoAnt.do&pgSubList="+paginaTalla+"&seccion="+seccion;		
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	colorLink = null;
}

function events_p92_RevSustRepo(){
	//Conseguir los botones rev y sust
	var revSust =  getElementsByClassName(document.body,'p92_btn');

	//Mirar que los elementos no sean vacíos
	if (revSust != null && typeof(revSust) != 'undefined' && revSust != null && typeof(revSust) != 'undefined') {
		for (var i = 0; i < revSust.length; i++) {
			revSust[i].onclick = function(e) {

				var revSust = this.getAttribute("data-revSust");
				var modeloProveedor =  document.getElementById('pda_p92_fld_descripcionRef').getAttribute("data-mp");
				var color =  document.getElementById('pda_p92_fld_queColor') != null ? document.getElementById('pda_p92_fld_queColor').innerHTML : null; 
				var flgRevisada = null;
				var flgSustituida = null;

				var codArt = this.getAttribute("data-codArtRevSust");
				if(revSust == "R"){
					var revisada = document.getElementById("pda_p92_listRepoAnt_bloqueBotonRevisada");
					if(hasClass(revisada, 'pda_p92_greenRevSust')){
						flgRevisada = "N";						
					}else{
						flgRevisada = "S";	
					}
				}else{
					var sustituida = document.getElementById("pda_p92_listRepoAnt_bloqueBotonSustituida");
					if(hasClass(sustituida, 'pda_p92_greenRevSust')){
						flgSustituida = "N";						
					}else{
						flgSustituida = "S";	
					}
				}

				var reposicionRevSust = new ReposicionGuardar(null,null,null,modeloProveedor,codArt,color,null,flgRevisada,flgSustituida);

				var objJson = JSON.stringify(reposicionRevSust);
				var url = './pdaP92ListadoRepoAntRevSust.do';
				var method = 'POST';
				postData(url, method, objJson, true,  "application/json; charset=utf-8", colorearRevSust, hayError);

			}
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	revSust = null;
}

function events_p92_seccion(){
	var selectSeccion =  document.getElementById('pda_p01_txt_seccion');
	if (selectSeccion != null && typeof(selectSeccion) != 'undefined') {
		selectSeccion.onchange = function () {
			document.getElementById("p12_form").submit();
		}
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	selectSeccion = null;
}

function colorearRevSust(data){
	if(data.codError == 0){
		var sustituida =  document.getElementById('pda_p92_listRepoAnt_bloqueBotonSustituida');
		var revisada =  document.getElementById('pda_p92_listRepoAnt_bloqueBotonRevisada');
		if(data.flgRevisada == "S"){
			sustituida.className = sustituida.className.replace('pda_p92_greenRevSust','');
			revisada.className = revisada.className + " pda_p92_greenRevSust";
		}else if(data.flgRevisada == "N"){
			revisada.className = revisada.className.replace('pda_p92_greenRevSust','');
		}else if(data.flgSustituida == "N"){
			sustituida.className = revisada.className.replace('pda_p92_greenRevSust','');
		}else{			
			revisada.className = revisada.className.replace('pda_p92_greenRevSust','');
			sustituida.className = sustituida.className + " pda_p92_greenRevSust";
		}
		var error =  document.getElementById('pda_p92_fld_errorColor');
		error.style.display = "none";
		
		//Activamos el href del siguiente vacío
		var href = document.getElementById("pda_p92_sigVacio").href;
	    window.location.href = href;
	}else{
		var error =  document.getElementById('pda_p92_fld_errorColor');
		error.innerHTML = data.descError;
		error.style.display = "block";	
	}
}

function colorearRepo(data){
	//Obtenemos el input
	var inputsCodArt = getElementsByAttribute('data-codart',data.codArt);
	var inputAColorear = inputsCodArt.length > 1 ? inputsCodArt[1] : inputsCodArt[0];
	if(data.codError == 0 || data.codError == null){		
		inputAColorear.className = inputAColorear.className.replace('pda_p92_redInput','');
		inputAColorear.className = inputAColorear.className + " pda_p92_blueInput";
		tabularAVacio('pda_p92_listRepoAnt_bloqueStockRepo_repo');

		var error =  document.getElementById('pda_p92_fld_errorColor');
		error.style.display = "none";

		//Actualizamos el hidden con el nuevo valor cuando se guarda.
		document.getElementById("pda_p92_listRepoAnt_bloqueStockRepo_repo"+data.codArt).value = inputAColorear.value;
	}else{		
		inputAColorear.className = inputAColorear.className.replace('pda_p92_blueInput','');
		inputAColorear.className = inputAColorear.className + " pda_p92_redInput";
		inputAColorear.value = "";

		var error =  document.getElementById('pda_p92_fld_errorColor');
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
		inputAColorear.className = inputAColorear.className.replace('pda_p92_redInput','');
		inputAColorear.className = inputAColorear.className + " pda_p92_blueInput";
		tabularAVacio('pda_p92_listRepoAnt_bloqueStockRepo_stock');

		var error =  document.getElementById('pda_p92_fld_errorColor');
		error.style.display = "none";

		//Actualizamos el hidden con el nuevo valor cuando se guarda.
		document.getElementById("pda_p92_listRepoAnt_bloqueStockRepo_stock"+data.codigoReferenciaModificada).value = inputAColorear.value;
	}else{		
		inputAColorear.className = inputAColorear.className.replace('pda_p92_blueInput','');
		inputAColorear.className = inputAColorear.className + " pda_p92_redInput";
		inputAColorear.value = "";

		var error =  document.getElementById('pda_p92_fld_errorColor');
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
function hayError(){
	alert("Ha ocurrido un error");
}

//Valida el campo repo y sotck.
function validacionRepoStock(elemento,elementoOrig){
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
		elemento.className = elemento.className.replace('pda_p92_blueInput','');

		//Quitamos el rojo de posibles guardados fallidos anteriores.
		elemento.className = elemento.className.replace('pda_p92_redInput','');

		//Pintamos de rojo si está mal
		elemento.className = elemento.className + " pda_p92_redInput";
	}else{
		//Quitamos el azul de posibles guardados anteriores.
		elemento.className = elemento.className.replace('pda_p92_blueInput','');

		//Quitamos el rojo de posibles guardadosfallidos anteriores.
		elemento.className = elemento.className.replace('pda_p92_redInput','');

		//Quitamos el rojo del campo.
		elemento.className = elemento.className.replace(/pda_p92_redInput/g,'');
	}
	return resultadoValidacionFmt && resultadoValidacionValor;
}