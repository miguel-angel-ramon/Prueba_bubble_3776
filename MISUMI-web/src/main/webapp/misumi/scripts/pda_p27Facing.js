function initializeScreen_p27(){
	facing = document.getElementById("pda_p27_facing");
	if (facing != null && typeof(facing) != 'undefined') {
		fld_facing = document.getElementById("pda_p27_fld_facing");
		if (fld_facing != null && typeof(fld_facing) != 'undefined') {
			fld_facing.focus();
			if (fld_facing.value != null && fld_facing.value != ''){
				fld_facing.select();
			}
		}
	}
	//Begin: ref. textil lote
	var contador = 0;
	var objeto = document.getElementById("pda_p27_fld_facing_" + contador);
	if (objeto != null && typeof(objeto) != 'undefined'){
		while (objeto != null && typeof(objeto) != 'undefined') {
			objeto.onclick = function(e) {
				e = e || window.event;
				this.select();
				if(e.stopPropagation) {
				    e.stopPropagation();
				} else {
					e.cancelBubble = true;
				}
			}
			objeto.onkeydown = function(e) {
				controlNavegacion(e)
			}
			objeto.onblur = function(e) {
				e = e || window.event;
				var idActual = (e.target || e.srcElement).id;
		   		validacionFacing(idActual);
			}
			objeto.onkeyup = function(e) {
				e = e || window.event;
				var idActual = (e.target || e.srcElement).id;
		   		validacionNavegacion = validacionFacing(idActual);
		   		if (validacionNavegacion){
					if (document.getElementById("pda_p27_textil_lote_inactivar")){
						if (parseInt(this.value,10) != 0){
							document.getElementById("pda_p27_textil_lote_inactivar").checked = false;
						}
					}
		   		}
			}
		    contador++;
			objeto = document.getElementById("pda_p27_fld_facing_" + contador);
		}
		document.getElementById("pda_p27_fld_facing_0").focus();
		document.getElementById("pda_p27_fld_facing_0").select();
	}
	
	//Si se checkea inactivar entonces pongo a 0 todos los facing visibles
	var enlace = document.getElementById("pda_p27_textil_lote_inactivar_enlace");
	var hidden = document.getElementById("pda_p27_textil_lote_inactivar");
	if (enlace){
		enlace.onclick = function(e) {
			var contador = 0;
			var objeto = document.getElementById("pda_p27_fld_facing_" + contador);
			var objetoTemp = document.getElementById("pda_p27_fld_facing_" + contador + "_tmp");
			if (hidden){
				hidden.value=true;
			}
			if (objeto != null && typeof(objeto) != 'undefined'){
				while (objeto != null && typeof(objeto) != 'undefined') {

						objeto.value = "0";
					
				    contador++;
					objeto = document.getElementById("pda_p27_fld_facing_" + contador);
					objetoTemp = document.getElementById("pda_p27_fld_facing_" + contador + "_tmp");
				}
				document.getElementById("pda_p27_fld_facing_0").focus();
				document.getElementById("pda_p27_fld_facing_0").select();
			}
			document.getElementById("pda_p27_btn_save").click();
		}
	}
	//End: ref. textil lote

	events_27_volver();
}

//Begin: ref. textil lote
function validacionFacing(id){
	
	var idActual = id;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(idActual.lastIndexOf("_")+1);
	var campoActual = document.getElementById(id).value;
	var campoStockTmp = "pda_p27_fld_facing_" + fila + "_tmp";
	var resultadoValidacion = 'S';
	
	//Sólo si ha cambiado el valor del campo.
	var reg = new RegExp('^[0-9]+$');
	if (!reg.test(campoActual))
	{
		//Pintamos de rojo el campo.
		document.getElementById(id).className = document.getElementById(id).className + " inputFacingResaltado";
	}
	else
	{
		document.getElementById(id).className = document.getElementById(id).className.replace(/inputFacingResaltado/g,'');
		document.getElementById(id).parentNode.className = document.getElementById(id).parentNode.className.replace(/pda_p27_textil_lote_facing_error_td/g,'');
	}
	
	return resultadoValidacion;
}

function controlNavegacion(e) {
	e = e || window.event;
	var idActual = (e.target || e.srcElement).id;
	var idFoco;
	//Obtención de fila y columna actuales
	var fila = idActual.substring(idActual.lastIndexOf("_")+1);
	var validacionNavegacion = 'S';
	
	var key = e.which || e.keyCode; //para soporte de todos los navegadores
    //El Intro y las Flechas de cursores para navegación
 	if (key == 13){//Tecla enter, guardado
 		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }
 		var bnt_save = document.getElementById("pda_p27_btn_save");
		if (bnt_save != null && typeof(bnt_save) != 'undefined') {
			bnt_save.click();
		}
    }
    if (key == 38){//Tecla arriba
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	idFoco = "pda_p27_fld_facing_" + (parseInt(fila,10)-1);
    	objFoco = document.getElementById(idFoco);
		if (objFoco != null && typeof(objFoco) != 'undefined') {
	   		validacionNavegacion = validacionFacing("pda_p27_fld_facing_"+fila);
	    	if (validacionNavegacion=='S'){
	    		objFoco.focus();
	    		objFoco.select();
	    	}
		}
    }
    if (key == 40){//Tecla abajo
		if(e.preventDefault) {
	        e.preventDefault();
	    } else {
	        e.returnValue = false;
	    }  
    	idFoco = "pda_p27_fld_facing_" + (parseInt(fila,10)+1);
    	objFoco = document.getElementById(idFoco);
		if (objFoco != null && typeof(objFoco) != 'undefined') {
	    	validacionNavegacion = validacionFacing("pda_p27_fld_facing_"+fila);
	    	if (validacionNavegacion=='S'){
	    		objFoco.focus();
	    		objFoco.select();
	    	}
		}
    }
}
//End: ref. textil lote

function events_27_volver(){
	var imagenVolver =  document.getElementById('pda_p10_volver');
	if (imagenVolver != null && typeof(imagenVolver) != 'undefined') {
		imagenVolver.onclick = function () {
			var origenConsulta = document.getElementById('pda_p27_fld_fac_origen');
			if (origenConsulta.value == "1"){
				 document.forms[0].action = "pdaP21Sfm.do?actionPrev=yes";    
				 document.forms[0].submit();
			} else {
				window.location.href = "./pdawelcome.do";
			}
		};
	}
}

