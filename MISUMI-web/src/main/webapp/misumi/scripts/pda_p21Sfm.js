function initializeScreen_p21(){
	fld_sfm = document.getElementById("pda_p21_fld_sfm");
	if (fld_sfm != null && typeof(fld_sfm) != 'undefined') {
		fld_sfm.onblur = function() {
			validacionSfm(this.id);
		}
		fld_sfm.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
	}
	fld_sfm_dias = document.getElementById("pda_p21_fld_sfm_dias");
	if (fld_sfm_dias != null && typeof(fld_sfm_dias) != 'undefined') {
		fld_sfm_dias.onblur = function() {
			validacionCoberturaSfm(this.id);
		}
		fld_sfm_dias.onclick = function(e) {
			e = e || window.event;
			this.select();
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
		}
	}
	events_21_volver();
	posInicialCursorP21();
}
function validacionSfm(id){
	var campoActual = document.getElementById(id);
	var campoActualPunto = document.getElementById(id).value.replace(',','.');
	var campoSfmTmp = "pda_p21_fld_sfm_tmp";
	var campoCoberturaSfm = "pda_p21_fld_sfm_dias";
	var campoCoberturaSfmTmp = "pda_p21_fld_sfm_dias_tmp";
	var campoVentaMediaActual = "pda_p21_fld_sfm_vm_sfm";
	var resultadoValidacion = 'S';
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0.000";
	}
	//Sólo si ha cambiado el valor del campo.
	if ((isNaN(parseFloat(campoActualPunto)) || campoActual.value.split(",").length > 2) &&
			(parseFloat(document.getElementById(campoSfmTmp).value) != parseFloat(campoActualPunto)))
	{
		//Hay error y no se calcula la cobertura
	}
	else
	{
		document.getElementById(id).value = parseFloat(campoActualPunto).toFixed(3);
		//Con este comentario y el del if actualiza SFM dias al clicar fuera del input
		//if ((document.getElementById(campoSfmTmp).value != null) && (document.getElementById(campoSfmTmp).value != campoActual.value))
		if ((document.getElementById(campoSfmTmp).value != null) && (document.getElementById(campoSfmTmp).value != campoActual))
		{
			//Con este comentario y el del if actualiza el SFM dias al clicar fuera del input
			//document.getElementById(campoSfmTmp).value = campoActual.value;
			document.getElementById(campoSfmTmp).value = campoActual;
			if (parseFloat(document.getElementById(campoVentaMediaActual).value) == 0)
			{
				document.getElementById(campoCoberturaSfm).value = document.getElementById(id).value;
				document.getElementById(campoCoberturaSfmTmp).value = document.getElementById(id).value;
			}
			else
			{
				document.getElementById(campoCoberturaSfm).value = (parseFloat(campoActualPunto)/parseFloat(document.getElementById(campoVentaMediaActual).value.replace(',','.'))).toFixed(3); 
				document.getElementById(campoCoberturaSfmTmp).value = (parseFloat(campoActualPunto)/parseFloat(document.getElementById(campoVentaMediaActual).value.replace(',','.'))).toFixed(3);
			}
		}
	}
	return resultadoValidacion;
}

function validacionCoberturaSfm(id){
	var campoActual = document.getElementById(id).value;
	var campoActualPunto = document.getElementById(id).value.replace(',','.');
	var error = 0;
	var campoSfm = "pda_p21_fld_sfm";
	var campoSfmTmp = "pda_p21_fld_sfm_tmp";
	var campoCoberturaSfmTmp = "pda_p21_fld_sfm_dias_tmp";
	var campoVentaMediaActual = "pda_p21_fld_sfm_vm_sfm";
	var resultadoValidacion = 'S';
	//Formateamos el valor introducido por el usuario
	if (campoActual == '')
	{
		campoActualPunto = "0.000";
	}
	if (isNaN(parseFloat(campoActualPunto)) || campoActual.split(",").length > 2)
	{
		//Hay error y no se calcula el sfm
	}
	else
	{
		document.getElementById(id).value = parseFloat(campoActualPunto).toFixed(3);
		if ((document.getElementById(campoCoberturaSfmTmp).value != null) && document.getElementById(campoCoberturaSfmTmp).value != campoActual)
		{
			document.getElementById(campoCoberturaSfmTmp).value = campoActual;
			if (parseFloat(document.getElementById(campoVentaMediaActual).value) == 0)
			{
				document.getElementById(campoSfm).value = document.getElementById(id).value;
				document.getElementById(campoSfmTmp).value = document.getElementById(id).value;
			}
			else
			{
				document.getElementById(campoSfm).value = (parseFloat(campoActualPunto)*parseFloat(document.getElementById(campoVentaMediaActual).value.replace(',','.'))).toFixed(3); 
				document.getElementById(campoSfmTmp).value = (parseFloat(campoActualPunto)*parseFloat(document.getElementById(campoVentaMediaActual).value.replace(',','.'))).toFixed(3);
			}
		}
	}
	return resultadoValidacion;
}

function events_21_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var origenConsulta = document.getElementById('pda_p21_fld_sfm_origen');
			if (origenConsulta.value == "1"){
				 document.forms[0].action = "pdaP21Sfm.do?actionPrev=yes";    
				 document.forms[0].submit();
			} else {
				window.location.href = "./pdawelcome.do";
			}
		};
	}
}

function posInicialCursorP21(){
	var bloqueSfm =  document.getElementById('pda_p21_sfm_bloque1');
	var bloqueGuardado =  document.getElementById('pda_p21_sfm_bloqueGuardado');
	idFoco = "pda_p01_txt_infoRef";
		if (bloqueSfm != null && typeof(bloqueSfm) != 'undefined') {
	    	idFoco = "pda_p21_fld_sfm";
	    	if (bloqueGuardado != null && typeof(bloqueGuardado) != 'undefined') {
	    		idFoco = "pda_p01_txt_infoRef";
		   	}
	   	}
	document.getElementById(idFoco).focus();
	document.getElementById(idFoco).select();
	
}