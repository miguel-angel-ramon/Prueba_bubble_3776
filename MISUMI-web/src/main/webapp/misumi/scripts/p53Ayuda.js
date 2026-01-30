function initializeP53(){

	loadP53(locale);
	initializeScreenP53();
}

function initializeScreenP53(){
	
	$("#p53_pestanas").tabs({ 
	    select:
	        function (event, ui) {
	    		var nombrePestanaActivada = ui.panel.id;
	    		var valorPestanaCargada = $("#" + nombrePestanaActivada + "Cargada").val();
	    		//Sólo se ejecuta la carga de datos si no se ha cargado antes
	    		if (valorPestanaCargada == null || !(valorPestanaCargada == "S")){
		    		if (nombrePestanaActivada == "p53_pestanaAyuda1"){
		    			//Carga de la pestaña de ayuda1
		    			reloadAyuda1();
		    		}else{
						//Carga de la pestaña de ayuda2
						reloadAyuda2();  
					}
	    		}
	    		
	    		//Se actualiza el control de carga de datos en pestaña
	    		$(nombrePestanaActivada + "Cargada").val("S");
	    		
	            return true;
	        }
	});
	
	initializeScreenP54()
}

function limpiarAyudaP53(){
	$("#p53_pestanaAyuda1Cargada").val("N");
	$("#p53_pestanaAyuda2Cargada").val("N");
	$("#p53_pestanas").tabs("destroy");
	$(".p60_bloque3AreaDatosAyuda").css("display", "none");
	$("#p53_fld_pestanaAyuda1").text("");		

	initializeScreenP53();
}

function loadP53(locale){
	
}