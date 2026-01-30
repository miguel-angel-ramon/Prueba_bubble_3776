function initializeScreen_p102(){
	var flgPesoVariableIni = document.getElementById("flgPesoVariable").value;
	if(flgPesoVariableIni!='S'){
		document.getElementById("pda_p102_lbl_avisoPesoVariable").style.display = "none";
		formatNoPesoVariable();
	}else{
		document.getElementById("pda_p102_lbl_avisoPesoVariable").style.display = "block";
	}
	
	events_102_volver();
}

function formatNoPesoVariable(){
	for(var i = 0; i<5 ; i++){
		var campoCantidad="pda_p102_cantidad"+i;
		
		document.getElementById(campoCantidad).value=(document.getElementById(campoCantidad).value!="")?parseInt(document.getElementById(campoCantidad).value):"";
	}
}

function events_102_calcularBulto(indice){
	var campoCantidad="pda_p102_cantidad"+indice;
	var valorCantidad = document.getElementById(campoCantidad).value;
	var campoBultoCaja="pda_p102_btn_cajaBulto"+indice;
	var bultoCerrado=document.getElementById(campoCantidad).className.indexOf('pda_p102_readonly');
	if(bultoCerrado == -1){
		if(validacionCantidadP102(indice)=='S'){
			document.getElementById("pda_p102_lbl_Error").style.display = "none";
			var campoBulto="pda_p102_bulto"+indice;
			var valorBulto=document.getElementById(campoBulto).value;
			if(valorBulto=="" && valorCantidad!=""){
				var listaBultos=document.getElementById('listaBultos').value;
				var aux = listaBultos.split(","); 
				var valorEstadoCerrado="pda_p102_estadoCerrado"+indice;
				document.getElementById(campoBultoCaja).style.display = "block";
				document.getElementById(valorEstadoCerrado).value='N';
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
				for(var i = 0; i<aux.length && document.getElementById(campoBulto).value=="" ; i++){
					var existebulto=false;
					for(var j = 0; j<5 ; j++){
						var campoBultoComp="pda_p102_bulto"+j;
						var valorBultoComp=document.getElementById(campoBultoComp).value;
						if(valorBultoComp==aux[i]){
							existebulto=true;
						}
					}
					if(!existebulto){
						document.getElementById(campoBulto).value=aux[i];
					}
				}
			}else if( valorCantidad==""){
				document.getElementById(campoBultoCaja).style.display = "none";
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
			}else if(valorCantidad!=""){
				var valorEstadoCerrado="pda_p102_estadoCerrado"+indice;
				document.getElementById(campoBultoCaja).style.display = "block";
				document.getElementById(valorEstadoCerrado).value='N';
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
			}
		}else{
			if(valorCantidad!=''){
				document.getElementById(campoCantidad).focus();
				document.getElementById(campoCantidad).select();
				//ERRORES EN LA VALIDACIÓN DE LA CANTIDAD POR LO QUE PONEMOS EL FOCO EN LA CANTIDAD Y MOSTRAMOS EL ERROR
				document.getElementById("pda_p102_lbl_Error").style.display = "block";
				document.getElementById("pda_p102_lbl_Error2").style.display = "none";
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
			}else{
				document.getElementById("pda_p102_lbl_Error").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
			}
		}
	}
}

function events_102_validarBulto(indice){
	var campoBulto="pda_p102_bulto"+indice;
	var valorBulto = document.getElementById(campoBulto).value;
	var campoBultoCaja="pda_p102_btn_cajaBulto"+indice;
	var bultoCerrado=document.getElementById(campoBulto).className.indexOf('pda_p102_readonly');
	if(bultoCerrado == -1){
		if(validacionBultoP102(indice)=='S' && valorBulto!=''){
			document.getElementById("pda_p102_lbl_Error").style.display = "none";
			var listaBultos=document.getElementById('listaBultos').value;
			var aux = listaBultos.split(",");
			var bultoValido=false;
			for(var i = 0; i<aux.length && !bultoValido; i++){
				if(valorBulto==aux[i]){
					bultoValido=true;
				}
			}
			var campoCantidad="pda_p102_cantidad"+indice;
			var valorCampoCantidad = document.getElementById(campoCantidad).value;
			if(!bultoValido){
				document.getElementById(campoBulto).value="";
				document.getElementById("pda_p102_lbl_Error3").style.display = "block";
				document.getElementById("pda_p102_lbl_Error").style.display = "none";
				document.getElementById("pda_p102_lbl_Error2").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
				var listaBultos=document.getElementById('listaBultos').value;
				var aux = listaBultos.split(","); 
				var valorEstadoCerrado="pda_p102_estadoCerrado"+indice;
				document.getElementById(valorEstadoCerrado).value='N';
				for(var i = 0; i<aux.length && document.getElementById(campoBulto).value=="" ; i++){
					var existebulto=false;
					for(var j = 0; j<5 ; j++){
						var campoBultoComp="pda_p102_bulto"+j;
						var valorBultoComp=document.getElementById(campoBultoComp).value;
						if(valorBultoComp==aux[i]){
							existebulto=true;
						}
					}
					if(!existebulto){
						document.getElementById(campoBulto).value=aux[i];
					}
				}
				if(valorCampoCantidad!=''){
					document.getElementById(campoBultoCaja).style.display = "block";
				}
			}else{
				
				if(valorCampoCantidad!=''){
					document.getElementById("pda_p102_lbl_Error").style.display = "none";
					document.getElementById("pda_p102_lbl_Error2").style.display = "none";
					document.getElementById("pda_p102_lbl_Error3").style.display = "none";
					var valorEstadoCerrado="pda_p102_estadoCerrado"+indice;
					document.getElementById(valorEstadoCerrado).value='N';
					document.getElementById(campoBultoCaja).style.display = "block";
				}else{
					document.getElementById("pda_p102_lbl_Error").style.display = "none";
					document.getElementById("pda_p102_lbl_Error2").style.display = "none";
					document.getElementById("pda_p102_lbl_Error3").style.display = "none";
					var valorEstadoCerrado="pda_p102_estadoCerrado"+indice;
					document.getElementById(valorEstadoCerrado).value='N';
					document.getElementById(campoBultoCaja).style.display = "none";
				}
			}
		}else{
			if(valorBulto!=''){
				document.getElementById(campoBulto).focus();
				document.getElementById(campoBulto).select();
				//ERRORES EN LA VALIDACIÓN DE LA CANTIDAD POR LO QUE PONEMOS EL FOCO EN LA CANTIDAD Y MOSTRAMOS EL ERROR
				document.getElementById("pda_p102_lbl_Error").style.display = "block";
				document.getElementById("pda_p102_lbl_Error2").style.display = "none";
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
			}else{
				document.getElementById("pda_p102_lbl_Error").style.display = "none";
				document.getElementById("pda_p102_lbl_Error2").style.display = "none";
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
			}
		}
	}
}


function validarCantidadesyBultos(){
	var camposValidos=true;
	var cantidad0 = document.getElementById("pda_p102_cantidad0").value;
	var bulto0 = document.getElementById("pda_p102_bulto0").value;
	var cantidad1 = document.getElementById("pda_p102_cantidad1").value;
	var bulto1 = document.getElementById("pda_p102_bulto1").value;
	var cantidad2 = document.getElementById("pda_p102_cantidad2").value;
	var bulto2 = document.getElementById("pda_p102_bulto2").value;
	var cantidad3 = document.getElementById("pda_p102_cantidad3").value;
	var bulto3 = document.getElementById("pda_p102_bulto3").value;
	var cantidad4 = document.getElementById("pda_p102_cantidad4").value;
	var bulto4 = document.getElementById("pda_p102_bulto4").value;

	//Validacion si todos los campos están vacios
	if((cantidad0=="" && bulto0=="") && (cantidad1=="" && bulto1=="") && (cantidad2=="" && bulto2=="") && (cantidad3=="" && bulto3=="") && (cantidad4=="" && bulto4=="")){
		camposValidos=true;
	}else{
		for(var i = 0; i<5 && camposValidos; i++){
			var campoCantidad="pda_p102_cantidad"+i;
			var campoBulto="pda_p102_bulto"+i;
			
			var cantidadPantalla = document.getElementById(campoCantidad).value;
			var bultoPantalla = document.getElementById(campoBulto).value;
			var flgPesoVariablePantalla = document.getElementById("flgPesoVariable").value;
			camposValidos=validarCamposPantalla(cantidadPantalla,bultoPantalla,flgPesoVariablePantalla);
		}
	}
	return camposValidos;
}

function validarCamposPantalla(cantidad,bulto,flgPesoVariable){
	var camposValidosValidacion=true;
	//Validación para ver si los 2 registros están rellenos de una misma fila
	if((cantidad=="" && bulto!="") || (cantidad!="" && bulto=="")){
		camposValidosValidacion=false;
	//Validación para ver si los 2 registros están vacios
	}else if(cantidad=="" && bulto==""){
		camposValidosValidacion=true;
	}else{
		//Los registros de cantidad y bulto están rellenos y se valida que si es peso variable 
		var numeroCantidadIsOk;
		if(flgPesoVariable=='S'){
			//Validación número decimal
			numeroCantidadIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(cantidad);
		}else{
			//Validación número entero
			numeroCantidadIsOk = /^\d{0,12}$/.test(cantidad);
		}
		if (!numeroCantidadIsOk){
			camposValidosValidacion=false;
		}
		var numeroBulto0IsOk = /^\d{0,2}$/.test(bulto);
		if (!numeroBulto0IsOk){
			camposValidosValidacion=false;
		}
	}
	return camposValidosValidacion;
}

function hayBultosIguales(){
	var bultosIguales=false;
	for(var i = 0; i<5 && !bultosIguales; i++){
		var idCampoBulto="pda_p102_bulto"+i;
		var campoBulto = document.getElementById(idCampoBulto).value;
		if(campoBulto!=""){
			for(var j = i+1; j<5; j++){
				var idCampoBultoComparar="pda_p102_bulto"+j;
				var campoBultoComparar = document.getElementById(idCampoBultoComparar).value;
				if( campoBultoComparar!="" && campoBulto==campoBultoComparar){	
					bultosIguales=true;
					break;
				}
			}
		}
	}
	return bultosIguales;
}

function events_102_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.forms[0].action = "pdaP102VariosBultosOrdenRetirada.do?action=return";    
			document.forms[0].submit();
		};
	}
}

function events_p102_guardar(){
	var guardar =  document.getElementById('pda_p102_btn_save');
	if (guardar != null && typeof(guardar) != 'undefined') {
		if(validarCantidadesyBultos()){
			if(hayBultosIguales()){
				//ERRORES EN LA VALIDACIÓN: HAY BULTOS INTRODUCIDOS IGUALES POR LO QUE VOLVEMOS A LA PANTALLA Y MOSTRAMOS EL DIV DE ERROR EN LA PARTE INFERIOR
				document.getElementById("pda_p102_lbl_Error2").style.display = "block";
				document.getElementById("pda_p102_lbl_Error").style.display = "none";
				document.getElementById("pda_p102_lbl_Error3").style.display = "none";
			}else{
				document.forms[0].action = "pdaP102VariosBultosOrdenRetirada.do?action=save";    
				document.forms[0].submit();
			}
		}else{
			//ERRORES EN LA VALIDACIÓN POR LO QUE VOLVEMOS A LA PANTALLA Y MOSTRAMOS EL DIV DE ERROR EN LA PARTE INFERIOR
			document.getElementById("pda_p102_lbl_Error").style.display = "block";
			document.getElementById("pda_p102_lbl_Error2").style.display = "none";
			document.getElementById("pda_p102_lbl_Error3").style.display = "none";
		}
		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	guardar = null;
}

function events_p102_eliminar(indice){
	var cajaBultoPantalla='pda_p102_btn_cajaBulto'+indice;
	document.getElementById(cajaBultoPantalla).style.display = "none";
	var campoCantidadEliminar="pda_p102_cantidad"+indice;
	var campoBultoEliminar="pda_p102_bulto"+indice;
	var campoEstadoEliminar='pda_p102_estadoCerrado'+indice;
	document.getElementById(campoCantidadEliminar).value="";
	document.getElementById(campoBultoEliminar).value="";
	document.getElementById(campoEstadoEliminar).value='N';
	document.getElementById(campoCantidadEliminar).className = document.getElementById(campoCantidadEliminar).className.replaceAll(' pda_p102_readonly','');
	document.getElementById(campoBultoEliminar).className = document.getElementById(campoBultoEliminar).className.replaceAll(' pda_p102_readonly','');
}

function events_p102_cajaBulto(indice){
	var campoEstado='pda_p102_estadoCerrado'+indice;
	document.getElementById(campoEstado).value='S';
	var cajaBultoPantalla='pda_p102_btn_cajaBulto'+indice;
	document.getElementById(cajaBultoPantalla).style.display = "none";
	var campoCantidad="pda_p102_cantidad"+indice;
	var campoBulto="pda_p102_bulto"+indice;
	document.getElementById(campoCantidad).readOnly = true;
	document.getElementById(campoBulto).readOnly = true;
	document.getElementById(campoCantidad).className+= ' pda_p102_readonly';
	document.getElementById(campoBulto).className+= ' pda_p102_readonly';
	var botonEliminarPantalla="pda_p102_btn_eliminar"+indice
	document.getElementById(botonEliminarPantalla).style.display = "none";
}

function validacionCantidadP102(indice){
	var resultadoValidacion = 'S';
	var id = 'pda_p102_cantidad'+indice;
	var campoActual = document.getElementById(id);
	if (campoActual != null && typeof(campoActual) != 'undefined') {
		var flgPesoVaribalePantalla=document.getElementById("flgPesoVariable").value;
		var numeroCantidadIsOk;
		if(flgPesoVaribalePantalla=='S'){
			//Validación número decimal
			numeroCantidadIsOk = /^(-?\d+((\,|\.)\d{0,3}){0,1})?$/.test(campoActual.value);
		}else{
			//Validación número entero
			numeroCantidadIsOk = /^\d*$/.test(campoActual.value);
		}
		if (!numeroCantidadIsOk){
			resultadoValidacion = 'N';
		}else{
			resultadoValidacion = 'S';
		}
	}
	
	return resultadoValidacion;
}

function validacionBultoP102(indice){
	var resultadoValidacion = 'S';
	var idBulto = 'pda_p102_bulto'+indice;
	var campoActualBulto = document.getElementById(idBulto);
	if (campoActualBulto != null && typeof(campoActualBulto) != 'undefined') {
		var numeroBultoIsOk = /^\d{0,2}$/.test(campoActualBulto.value);
		if (!numeroBultoIsOk){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}


