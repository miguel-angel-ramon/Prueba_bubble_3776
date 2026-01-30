function initializeScreen_p108() {
	
	
	events_p108_foto();


}


function events_p108_foto() {
    var inputs = document.getElementsByTagName('input');
    
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].className && inputs[i].className.indexOf('pda_p108_articuloDescripcion') !== -1) {
            inputs[i].onclick = function() {
                var articuloDesc = this.value;
                var codArtFromDesc = articuloDesc.split(" - ")[0];
              //  var tieneFoto = document.getElementById("pda_p12_tieneFoto").value;
                var divParent = this;
                
                while (divParent && divParent.tagName !== 'DIV') {
                    divParent = divParent.parentNode;
                }
                
                var tieneFoto = null;
                if (divParent) {
                    var inputsDiv = divParent.getElementsByTagName('input');
                    for (var j = 0; j < inputsDiv.length; j++) {
                        if (inputsDiv[j].id === 'pda_p12_tieneFoto') {
                            tieneFoto = inputsDiv[j].value;
                            break;
                        }
                    }
                }

                if (!tieneFoto) {
                    tieneFoto = 'N';  
                }
                var matricula = document.getElementById('pda_p12_matricula').value;
                var pgPalet = document.getElementById('pda_p12_pgPalet').value;
                var pgTotPalet = document.getElementById('pda_p12_pgTotal').value;
                    window.location.href = "./pdaP108FotoAmpliadaDatosReferencia.do?codArticulo="
                        + codArtFromDesc
                        + "&tieneFoto="
                        + tieneFoto
                        + "&pgPalet="
                        + pgPalet
                        + "&pgTotPalet="
                        + pgTotPalet
                        + "&matricula="
                        + matricula
                        + "&pestanaDatosRef=pdaP108ConsultasMatricula";
            };
        }
    }
    linkFotos = null;
}



