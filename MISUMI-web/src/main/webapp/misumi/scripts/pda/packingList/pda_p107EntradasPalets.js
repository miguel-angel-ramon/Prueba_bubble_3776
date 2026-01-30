function initializeScreen_p107() {
}

function enviarClick(matricula) {
    var form = document.getElementById('palet');
    if (form) {
        var matriculaInput = document.getElementById('pda_p12_matricula');
        var soyPaletInput = document.getElementById('soyPalet');
        var urlPalet = window.location.href;
        var urlInput = document.getElementById('urlActual');

        if (matriculaInput && soyPaletInput && urlPalet) {
            matriculaInput.value = matricula;

            urlInput.value = urlPalet;
            soyPaletInput.value = true;

            form.submit();
            return true;
        } else {
            console.error('Campos de matrícula o soyPalet no encontrados');
        }
    } else {
        console.error('Formulario principal no encontrado');
    }
}
