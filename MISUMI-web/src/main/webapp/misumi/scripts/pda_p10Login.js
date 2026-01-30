window.onload = function(){
	events_p10_img_cerrar();
	events_p10_btn_cancelar();
}
function events_p10_img_cerrar(){
	var imagenCerrar =  document.getElementById('pda_p10_imagenCerrar');
	if (imagenCerrar != null && typeof(imagenCerrar) != 'undefined') {
		imagenCerrar.onclick = function () {
			Close_Window();
		}
	}
}
function events_p10_btn_cancelar(){ 
	var btnCancelar =  document.getElementById('pda_p10_btn_cancelar');
	if (btnCancelar != null && typeof(btnCancelar) != 'undefined') {
		btnCancelar.onclick = function () {
			resetValues();
		}
	}
}
function Close_Window() {
	self.close();
}
function resetValues(){
	var login_code =  document.getElementById('code');
	if (login_code != null && typeof(login_code) != 'undefined') {
		login_code.value = "";
	}
	var login_password =  document.getElementById('password');
	if (login_password != null && typeof(login_password) != 'undefined') {
		login_password.value = "";
	}
}
