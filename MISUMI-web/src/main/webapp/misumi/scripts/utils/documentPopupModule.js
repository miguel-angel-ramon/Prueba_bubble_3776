var documentPopupModule= (function (){
	/*PRE: En el HTML/JSP se deben añadir los div con id  documentPopup en la pagina donde se realiza la exportacion*/
	'use strict';
	var idHTML = "documentPopup";
	var idJquery = "#documentPopup";
	
	var showPopup= function (form){
		$(idJquery).html(form);
	};
	
	var closePopup = function (){
		$(idJquery).fadeOut('fast');
	};
	
	return {
		showPopup : showPopup
	};	
})();