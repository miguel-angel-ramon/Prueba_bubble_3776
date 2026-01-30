function initializeScreen_p118() {
    events_p118_foto();
    initIconClicks();
    initStock();
}


function events_p118_foto(){
    var foto = document.getElementById('pda_p118_foto');
    if (foto) {
        foto.onclick = function () {
            var codArtFoto = foto.getAttribute("data-codArtFoto");
            window.location.href = "./pdaP20FotoAmpliadaDatosReferencia.do?codArticulo="+codArtFoto+"&tieneFoto=S&pestanaDatosRef=pdaP118PrehuecosAlmacen";
        };
    }
}


function addEventCompat(element, event, handler) {
    if (element.addEventListener) {
        element.addEventListener(event, handler, false);
    } else if (element.attachEvent) {
        element.attachEvent('on' + event, handler);
    } else {
        element['on' + event] = handler;
    }
}

function getElementsByClassNameCompat(className) {
    var results = [];
    var elements = document.getElementsByTagName('*');
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].className && elements[i].className.indexOf(className) !== -1) {
            results.push(elements[i]);
        }
    }
    return results;
}

function crearXHR() {
    if (window.XMLHttpRequest) {
      return new XMLHttpRequest();
    }
    try {
      return new ActiveXObject("Msxml2.XMLHTTP");
    } catch(e1) {
      try {
        return new ActiveXObject("Microsoft.XMLHTTP");
      } catch(e2) {
        return null;
      }
    }
  }


  function getFirstByClass(cls) {
    var all = document.getElementsByTagName('*'),
        re  = new RegExp('(^|\\s)'+cls+'(\\s|$)'),
        i, el;
    for (i = 0; i < all.length; i++) {
      el = all[i];
      if (re.test(el.className)) return el;
    }
    return null;
  }

  function enviarStock(input) {
    
    var xhr = crearXHR();
    if (!xhr) {
      alert("Tu navegador no soporta AJAX");
      return;
    }

   
    var codArtEl = getFirstByClass('codArtInput');
    if (!codArtEl) {
      alert("No he encontrado el campo codArt");
      return;
    }

 
    var ctx = window.location.pathname.split('/')[1];
    var url = '/' + ctx + '/pdaP118ActualizarStock.do';
   
    url += '?_=' + (new Date()).getTime();

   
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.setRequestHeader('If-Modified-Since', 'Sat, 1 Jan 2000 00:00:00 GMT');

  
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4) {

        var status = (xhr.status === 0 ? 200 : xhr.status);
        if (status === 200) {
          window.location.reload();
        } else {
          alert("Error al actualizar el stock (status=" + status + ")");
        }
      }
    };

    var data =
      'codArt=' + encodeURIComponent(codArtEl.value) +
      '&stock='  + encodeURIComponent(input.value);

    xhr.send(data);
  }

  function initStock() {
    var stocks = document.getElementsByTagName('input'),
        i, inp;
    for (i = 0; i < stocks.length; i++) {
      inp = stocks[i];
      if (inp.className && inp.className.indexOf('pda_p91_listRepo_bloqueStockRepo_stock') !== -1) {
    
        inp.onblur = function(){ enviarStock(this); };
    
        inp.onkeydown = function(e){
          e = e || window.event;
          if ((e.keyCode||e.which) === 13) {
            if (e.preventDefault) e.preventDefault();
            else e.returnValue = false;
            enviarStock(this);
          }
        };
      }
    }
  }




function initIconClicks() {
//    var imgs = document.getElementsByTagName("img");
    var divs = document.getElementsByTagName("div");
    var form = document.getElementById("formPrehuecos");

    for (var i = 0; i < divs.length; i++) {
        var div = divs[i];
        var className = div.className;

        if (className.indexOf("icono-click") !== -1) {
        
            var clickHandler = (function (element) {
                return function () {
//                    var estado = element.getAttribute("data-valor");
//                    var codArt = element.getAttribute("data-codart");
                	
                    // Compatible con IE6: buscar la primera imagen dentro del div clicado
                	var imgs = element.getElementsByTagName("img");
                    var img = imgs[0];

                    var estado = img.getAttribute("data-valor");
                    var codArt = img.getAttribute("data-codart");
                    
                    var campos = form.getElementsByTagName("input");
                    for (var j = campos.length - 1; j >= 0; j--) {
                        if (campos[j].name === "estadoSeleccionado" || campos[j].name === "codArtSeleccionado") {
                            form.removeChild(campos[j]);
                        }
                    }

                    var inputEstado = document.createElement("input");
                    inputEstado.type = "hidden";
                    inputEstado.name = "estadoSeleccionado";
                    inputEstado.value = estado;
                    form.appendChild(inputEstado);

                    var inputCodArt = document.createElement("input");
                    inputCodArt.type = "hidden";
                    inputCodArt.name = "codArtSeleccionado";
                    inputCodArt.value = codArt;
                    form.appendChild(inputCodArt);

                    form.submit();
                };
            })(div);

       
            if (div.addEventListener) {
            	div.addEventListener("click", clickHandler, false);
            } else if (div.attachEvent) {
            	div.attachEvent("onclick", clickHandler);
            }
        }
    }
}
