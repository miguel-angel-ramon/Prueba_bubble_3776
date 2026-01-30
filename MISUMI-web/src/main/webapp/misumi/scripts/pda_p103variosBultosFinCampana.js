var sumatorioCantidad=0;
function initializeScreen_p103(){
	events_103_volver();
	var flgPesoVariableIni = document.getElementById("flgPesoVariable").value;
	if(flgPesoVariableIni!='S'){
		document.getElementById("pda_p103_lbl_avisoPesoVariable").style.display = "none";
		formatNoPesoVariable();
	}else{
		document.getElementById("pda_p103_lbl_avisoPesoVariable").style.display = "block";
	}
}

function formatNoPesoVariable(){
	for(var i = 0; i<5 ; i++){
		var campoCantidad="pda_p103_cantidad"+i;
		
		document.getElementById(campoCantidad).value=(document.getElementById(campoCantidad).value!="")?parseInt(document.getElementById(campoCantidad).value):"";
	}
}

function events_103_calcularBulto(indice){
	var campoCantidad="pda_p103_cantidad"+indice;
	var valorCantidad = document.getElementById(campoCantidad).value;
	var campoBultoCaja="pda_p103_btn_cajaBulto"+indice;
	var bultoCerrado=document.getElementById(campoCantidad).className.indexOf('pda_p103_readonly');
	if(bultoCerrado == -1){
		if(validacionCantidadP103(indice)=='S'){
			document.getElementById("pda_p103_lbl_Error").style.display = "none";
			var campoBulto="pda_p103_bulto"+indice;
			var valorBulto=document.getElementById(campoBulto).value;
			if(valorBulto=="" && valorCantidad!=""){
				var listaBultos=document.getElementById('listaBultos').value;
				var aux = listaBultos.split(","); 
				var valorEstadoCerrado="pda_p103_estadoCerrado"+indice;
				document.getElementById(campoBultoCaja).style.display = "block";
				document.getElementById(valorEstadoCerrado).value='N';
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
				for(var i = 0; i<aux.length && document.getElementById(campoBulto).value=="" ; i++){
					var existebulto=false;
					for(var j = 0; j<5 ; j++){
						var campoBultoComp="pda_p103_bulto"+j;
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
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
			}else if(valorCantidad!=""){
				var valorEstadoCerrado="pda_p103_estadoCerrado"+indice;
				document.getElementById(campoBultoCaja).style.display = "block";
				document.getElementById(valorEstadoCerrado).value='N';
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
			}
		}else{
			if(valorCantidad!=''){
				document.getElementById(campoCantidad).focus();
				document.getElementById(campoCantidad).select();
				//ERRORES EN LA VALIDACIÓN DE LA CANTIDAD POR LO QUE PONEMOS EL FOCO EN LA CANTIDAD Y MOSTRAMOS EL ERROR
				document.getElementById("pda_p103_lbl_Error").style.display = "block";
				document.getElementById("pda_p103_lbl_Error2").style.display = "none";
				document.getElementById("pda_p103_lbl_Error3").style.display = "none";
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
			}else{
				document.getElementById("pda_p103_lbl_Error").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
			}
		}
	}
}

function events_103_validarBulto(indice){
	var campoBulto="pda_p103_bulto"+indice;
	var valorBulto = document.getElementById(campoBulto).value;
	var campoBultoCaja="pda_p103_btn_cajaBulto"+indice;
	var bultoCerrado=document.getElementById(campoBulto).className.indexOf('pda_p103_readonly');
	if(bultoCerrado == -1){
		if(validacionBultoP103(indice)=='S' && valorBulto!=''){
			document.getElementById("pda_p103_lbl_Error").style.display = "none";
			var listaBultos=document.getElementById('listaBultos').value;
			var aux = listaBultos.split(",");
			var bultoValido=false;
			for(var i = 0; i<aux.length && !bultoValido; i++){
				if(valorBulto==aux[i]){
					bultoValido=true;
				}
			}
			var campoCantidad="pda_p103_cantidad"+indice;
			var valorCampoCantidad = document.getElementById(campoCantidad).value;
			if(!bultoValido){
				document.getElementById(campoBulto).value="";
				document.getElementById("pda_p103_lbl_Error4").style.display = "block";
				document.getElementById("pda_p103_lbl_Error").style.display = "none";
				document.getElementById("pda_p103_lbl_Error2").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
				var listaBultos=document.getElementById('listaBultos').value;
				var aux = listaBultos.split(","); 
				for(var i = 0; i<aux.length && document.getElementById(campoBulto).value=="" ; i++){
					var existebulto=false;
					for(var j = 0; j<5 ; j++){
						var campoBultoComp="pda_p103_bulto"+j;
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
					document.getElementById("pda_p103_lbl_Error").style.display = "none";
					document.getElementById("pda_p103_lbl_Error2").style.display = "none";
					document.getElementById("pda_p103_lbl_Error4").style.display = "none";
					var valorEstadoCerrado="pda_p103_estadoCerrado"+indice;
					document.getElementById(valorEstadoCerrado).value='N';
					document.getElementById(campoBultoCaja).style.display = "block";
				}else{
					document.getElementById("pda_p103_lbl_Error").style.display = "none";
					document.getElementById("pda_p103_lbl_Error2").style.display = "none";
					document.getElementById("pda_p103_lbl_Error4").style.display = "none";
					var valorEstadoCerrado="pda_p103_estadoCerrado"+indice;
					document.getElementById(valorEstadoCerrado).value='N';
					document.getElementById(campoBultoCaja).style.display = "none";
				}
			}
		}else{
			if(valorBulto!=''){
				document.getElementById(campoBulto).focus();
				document.getElementById(campoBulto).select();
				//ERRORES EN LA VALIDACIÓN DE LA CANTIDAD POR LO QUE PONEMOS EL FOCO EN LA CANTIDAD Y MOSTRAMOS EL ERROR
				document.getElementById("pda_p103_lbl_Error").style.display = "block";
				document.getElementById("pda_p103_lbl_Error2").style.display = "none";
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
			}else{
				document.getElementById("pda_p103_lbl_Error").style.display = "none";
				document.getElementById("pda_p103_lbl_Error2").style.display = "none";
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
				document.getElementById(campoBultoCaja).style.display = "none";
			}
		}
	}
}

function validarCantidadesyBultos(){
	var camposValidos=true;
	var cantidad0 = document.getElementById("pda_p103_cantidad0").value;
	var bulto0 = document.getElementById("pda_p103_bulto0").value;
	var cantidad1 = document.getElementById("pda_p103_cantidad1").value;
	var bulto1 = document.getElementById("pda_p103_bulto1").value;
	var cantidad2 = document.getElementById("pda_p103_cantidad2").value;
	var bulto2 = document.getElementById("pda_p103_bulto2").value;
	var cantidad3 = document.getElementById("pda_p103_cantidad3").value;
	var bulto3 = document.getElementById("pda_p103_bulto3").value;
	var cantidad4 = document.getElementById("pda_p103_cantidad4").value;
	var bulto4 = document.getElementById("pda_p103_bulto4").value;
	//Validacion si todos los campos están vacios
	if((cantidad0=="" && bulto0=="") && (cantidad1=="" && bulto1=="") && (cantidad2=="" && bulto2=="") && (cantidad3=="" && bulto3=="") && (cantidad4=="" && bulto4=="")){
		camposValidos=true;
	}else{
		sumatorioCantidad=0;
		for(var i = 0; i<5 && camposValidos; i++){
			var campoCantidad="pda_p103_cantidad"+i;
			var campoBulto="pda_p103_bulto"+i;
			
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
		//Los registros de cantidad y bulto están rellenos y se valida que sea un número entero
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
		}else{
			sumatorioCantidad=sumatorioCantidad+parseFloat(cantidad);
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
		var idCampoBulto="pda_p103_bulto"+i;
		var campoBulto = document.getElementById(idCampoBulto).value;
		if(campoBulto!=""){
			for(var j = i+1; j<5; j++){
				var idCampoBultoComparar="pda_p103_bulto"+j;
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

function events_103_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			document.forms[0].action = "pdaP103VariosBultosFinCampana.do?action=return"; 
			document.forms[0].submit();
		};
	}
}

function events_p103_guardar(){
	var guardar =  document.getElementById('pda_p103_btn_save');
	if (guardar != null && typeof(guardar) != 'undefined') {
		if(validarCantidadesyBultos()){
			if(hayBultosIguales()){
				//ERRORES EN LA VALIDACIÓN: HAY BULTOS INTRODUCIDOS IGUALES POR LO QUE VOLVEMOS A LA PANTALLA Y MOSTRAMOS EL DIV DE ERROR EN LA PARTE INFERIOR
				document.getElementById("pda_p103_lbl_Error2").style.display = "block";
				document.getElementById("pda_p103_lbl_Error").style.display = "none";
				document.getElementById("pda_p103_lbl_Error3").style.display = "none";
				document.getElementById("pda_p103_lbl_Error4").style.display = "none";
			}else{
				//AQUI HAY Q VALIDAR CUANDO EL VALOR DE CANTIDAD MAX ESTE RELLENO,EL SUMATORIO DE LAS CANTIDADES NO SEA SUPERIOR
				var cantidadMax = document.getElementById("cantidadMax").value;
				if(cantidadMax!= 'undefined' && cantidadMax!=null && parseFloat(sumatorioCantidad) > parseFloat(cantidadMax)){
					document.getElementById("pda_p103_lbl_Error3").style.display = "block";
					document.getElementById("pda_p103_lbl_Error").style.display = "none";
					document.getElementById("pda_p103_lbl_Error2").style.display = "none";
					document.getElementById("pda_p103_lbl_Error4").style.display = "none";
				}else{
					document.forms[0].action = "pdaP103VariosBultosFinCampana.do?action=save";    
					document.forms[0].submit();
				}
			}
		}else{
			//ERRORES EN LA VALIDACIÓN POR LO QUE VOLVEMOS A LA PANTALLA Y MOSTRAMOS EL DIV DE ERROR EN LA PARTE INFERIOR
			document.getElementById("pda_p103_lbl_Error").style.display = "block";
			document.getElementById("pda_p103_lbl_Error2").style.display = "none";
			document.getElementById("pda_p103_lbl_Error3").style.display = "none";
			document.getElementById("pda_p103_lbl_Error4").style.display = "none";
		}
		
	}
	//Limpiamos las variables DOM que no se utilizan más para evitar memory leak
	guardar = null;
}

function events_p103_eliminar(indice){
	var campoCantidadEliminar='pda_p103_cantidad'+indice;
	var campoBultoEliminar='pda_p103_bulto'+indice;
	var campoEstadoEliminar='pda_p103_estadoCerrado'+indice;
	var campoBultoCajaEliminar='pda_p103_btn_cajaBulto'+indice;
	document.getElementById(campoBultoCajaEliminar).style.display = 'none';
	document.getElementById(campoCantidadEliminar).value='';
	document.getElementById(campoBultoEliminar).value='';
	document.getElementById(campoEstadoEliminar).value='N';
	document.getElementById(campoCantidadEliminar).className = document.getElementById(campoCantidadEliminar).className.replaceAll(' pda_p103_readonly','');
	document.getElementById(campoBultoEliminar).className = document.getElementById(campoBultoEliminar).className.replaceAll(' pda_p103_readonly','');
	
}

function events_p103_cajaBulto(indice){
	var campoEstado='pda_p103_estadoCerrado'+indice;
	document.getElementById(campoEstado).value='S';
	var cajaBultoPantalla='pda_p103_btn_cajaBulto'+indice;
	document.getElementById(cajaBultoPantalla).style.display = 'none';
	var campoCantidad='pda_p103_cantidad'+indice;
	var campoBulto='pda_p103_bulto'+indice;
	document.getElementById(campoCantidad).readOnly = true;
	document.getElementById(campoBulto).readOnly = true;
	document.getElementById(campoCantidad).className+= ' pda_p103_readonly';
	document.getElementById(campoBulto).className+= ' pda_p103_readonly';
	var botonEliminarId='pda_p103_btn_eliminar'+indice
	document.getElementById(botonEliminarId).style.display = 'none';
}

function validacionCantidadP103(indice){
	var resultadoValidacion = 'S';
	var id = 'pda_p103_cantidad'+indice;
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

function validacionBultoP103(indice){
	var resultadoValidacion = 'S';
	var idBulto = 'pda_p103_bulto'+indice;
	var campoActualBulto = document.getElementById(idBulto);
	if (campoActualBulto != null && typeof(campoActualBulto) != 'undefined') {
		var numeroBultoIsOk = /^\d{0,2}$/.test(campoActualBulto.value);
		if (!numeroBultoIsOk){
			resultadoValidacion = 'N';
		}
	}
	return resultadoValidacion;
}

