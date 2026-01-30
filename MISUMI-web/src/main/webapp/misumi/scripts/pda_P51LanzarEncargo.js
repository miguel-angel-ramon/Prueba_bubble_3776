function initializeScreen_p51(){
	//Evento para pintar caja S N
	events_p51_cambiarColorCaja();

	//Escribe la cantidad 
	events_p51_escribirCantidadUnidDouble();
}

//Este evento se encarga de colorear la caja de S N de la pantalla
//Además se encarga de poner S o N según la caja seleccionada en
//Un campo hidden. Hay que tener en cuenta que solo coloreará la
//caja cuando sea de id cajaCajas1/2 y no cuando sea de tipo 
//cajaCajas1/2Block. En el último caso no se podrá cambiar de N a S
//y viceversa.
function events_p51_cambiarColorCaja(){
	//Conseguir todos los elementos de las cajas
	var cajaCajas1 =  document.getElementById('cajaCajas1');
	var cajaCajas2 =  document.getElementById('cajaCajas2');
	var cajaCajasHidden =  document.getElementById('cajas');
	var cajaExcluir1 =  document.getElementById('cajaExcluir1');
	var cajaExcluir2 =  document.getElementById('cajaExcluir2');
	var cajaExcluirHidden=  document.getElementById('excluir');

	//Mirar que los elementos no sean vacíos
	if (cajaCajas1 != null && typeof(cajaCajas1) != 'undefined' && cajaCajas2 != null && typeof(cajaCajas2) != 'undefined') {
		//Ejentos para cambiar el color de las cajas a verde
		cajaCajas1.onclick = function () {
			coloreaCaja(cajaCajas1,cajaCajasHidden,cajaCajas2);
		}
		cajaCajas2.onclick = function () {
			coloreaCaja(cajaCajas2,cajaCajasHidden,cajaCajas1);
		}
	}
	if(cajaExcluir1 != null && typeof(cajaExcluir1) != 'undefined' && cajaExcluir2 != null && typeof(cajaExcluir2) != 'undefined'){
		cajaExcluir1.onclick = function () {
			coloreaCaja(cajaExcluir1,cajaExcluirHidden,cajaExcluir2);
		}
		cajaExcluir2.onclick = function () {
			coloreaCaja(cajaExcluir2,cajaExcluirHidden,cajaExcluir1);
		}		
	}
}

//Cambiar color de blanco a verde en la caja pinchada
function coloreaCaja(cajaAColorear,hidden,cajaAQuitarColor){
	//Si no existe la clase cajaVerde se añade al elemento
	if(cajaAColorear.className.indexOf("cajaVerde") == -1){
		cajaAColorear.className += " cajaVerde";
		//Si es caja si poner S, si no poner N
		if(cajaAColorear.className.indexOf("cajaSi") != -1){
			hidden.value ='S'; 
		}else{
			hidden.value ='N';
		}
	}
	//Si existe la clase cajaVerde se elimina al elemento
	if(cajaAQuitarColor.className.indexOf("cajaVerde") != -1){
		var cajaClass=cajaAQuitarColor.className.replace(" cajaVerde",'');
		cajaAQuitarColor.className = cajaClass;
	}
}

//Función que gestiona el input de cantidad
function events_p51_escribirCantidadUnidDouble(){
	var unidadesPedidas =  document.getElementById('unidadesPedidasShow');
	var unidadesPedidasHidden =  document.getElementById('unidadesPedidas');
	
	var lanzarEncargoBtn = document.getElementById("lanzarEncargoBtn");
	if (unidadesPedidas != null && typeof(unidadesPedidas)){
		unidadesPedidas.onkeyup = function(){
			validar(unidadesPedidas,unidadesPedidasHidden,lanzarEncargoBtn);
		}
	}
	document.getElementById('unidadesPedidasShow').select();
}

function validar(unidadesPedidas,unidadesPedidasHidden,lanzarEncargoBtn){
	//Conseguir unidades pedidas
	var numeroUnidadesPedidas = unidadesPedidas.value;
	//var expresionRegular = new RegExp('^(0\,[0-9]|[0-9][0-9]{0,2}(\,[0-9]{1,2})?)$');
	//var expresionRegular = new RegExp('^([1-9][0-9]{0,2}(\,[0-9]{1,2})?)$');
	//var expresionRegular = new RegExp('^((0\,[5-9][0-9]?)|([1-9][0-9]{0,2}(\,[0-9]{1,2})?))$');
	var expresionRegular = new RegExp('^([1-9][0-9]{0,2})$');
	
	var resultado = expresionRegular.test(numeroUnidadesPedidas);
	if(resultado){
		unidadesPedidasHidden.value = numeroUnidadesPedidas.replace(',','.');
		lanzarEncargoBtn.disabled = false;
		if(unidadesPedidas.className.indexOf("error") != -1){
			var errorClass=unidadesPedidas.className.replace(" error",'');
			unidadesPedidas.className = errorClass;
		}
	}else{
		lanzarEncargoBtn.disabled = true;
		if(unidadesPedidas.className.indexOf("error") == -1){
			unidadesPedidas.className += " error";
		}
	}
}
