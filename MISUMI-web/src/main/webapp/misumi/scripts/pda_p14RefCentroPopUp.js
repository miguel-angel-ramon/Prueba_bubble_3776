window.onload = function(){
	createEllipsis();
}

function createEllipsis(){
  var divsMotivos = document.getElementsByClassName("pda_p14_MotivoTextosStyle");
  var divHeight=0;
  var divWidth=0;
  var elems; 
  
  var i=0; 
  for(; i< divsMotivos.length; i++){
	    divHeight = divsMotivos[i].offsetHeight;
		divWidth = divsMotivos[i].offsetWidth;
		elems = divsMotivos[i].querySelectorAll("span");
		
		var i2;
	    
	    var widthMotivos = 0; 
	    
	    for (i2 = 0; i2 < elems.length; i2++) {
	    	widthMotivos+=elems[i2].offsetWidth; 
	    }
	    
		if(widthMotivos >= divWidth ){
			divsMotivos[i].style.height = '10px';
			divsMotivos[i].style.overflow='hidden';
		}
  }
}

function controlVentana(event){
   var divPadre = document.getElementById("contenidoPaginaResaltado");
   var containerClassName = null;
   if(event.target && event.target.className){
	   containerClassName = event.target.className;
   }
   
   var id = null;
   if(event.target && event.target.id){
	   id = event.target.id;
   }

   if (((null == id || divPadre.id == id)  ||  divPadre.contains(event.target)) 
       && (null == containerClassName || containerClassName.indexOf("botonPaginacion") == -1)) // ... nor a descendant of the container
   {

   	var origen = document.getElementById("pda_p14_fld_procede").value;
       var codArt = document.getElementById("pda_p14_fld_codArt").value;
       if(origen == 'datosRef'){
           window.location.href = "./pdaP12DatosReferencia.do?codArt="+codArt;
       } else if (origen == 'segPedidos'){
            window.location.href = "./pdaP13SegPedidos.do?codArt="+codArt;
       } else {//movStocks
            window.location.href = "./pdaP15MovStocks.do?codArt="+codArt;
       }
   } else if (containerClassName.indexOf("botonPaginacion") != -1) {
	   document.getElementById(event.target.id).click();
   }
}


