<script src="./misumi/scripts/p35PopUpInformes.js?version=${misumiVersion}" type="text/javascript"></script>

<!--  Contenido página -->
<div id="p35_popupInformes" title="<spring:message code="p35_popUpInformes.tituloPopup"/>">

	<div id="p35_informesIcono">
		<img src="./misumi/images/blanco.jpg" style="vertical-align: middle;" width="1px" height="40px">
		<img src="./misumi/images/modificado.png" style="vertical-align: middle;" class="imagenInformesListado2">
	</div>
	
	<div id="p35_NoInformesDisponible"  style="display:none">
		<div id="p35_cabeceraNoInformesDisponible">	
			<div class="p35_informesTitulo">	
				<p id="p35_parrafoCabeceraNoInformesDisponible"><spring:message code="p35_popUpInformes.actualmenteNoDisponibles" /></p>
			</div>
		</div>
	</div>	
	
	<div id="p35_informes"  style="display:none">
		<div id="p35_cabeceraInformes">	
			<div class="p35_informesTitulo">	
				<p id="p35_parrafoCabeceraInformes"><spring:message code="p35_popUpInformes.actualmenteDisponibles" /></p>
			</div>
		</div>
		<div id="p35_cuerpoInformes">
			<div id="p35_informePescaMostrador"  style="display:none">
				<div id="p35_informeTexto">
					<p id="p35_informeParrafo"><spring:message code="p35_popUpInformes.informePescaMostrador" /></p>
				</div>
				<div id="p35_informePdf">
					<a href="#" onclick="p36loadInformesPesca();"><img src="./misumi/images/pdf.gif" class="p35_imagenPdf"></a>
				</div>
			</div>
		</div>
	</div>	
	
</div>