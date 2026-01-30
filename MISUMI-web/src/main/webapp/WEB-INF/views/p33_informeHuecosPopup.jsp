		<script src="./misumi/scripts/p33InformeHuecosPopup.js" type="text/javascript"></script>
		<!--  Contenido pagina -->
		
			<div id="p33_informeHuecos" style="display: none;">
					<div id="p33_avisoInformeHuecosIcono">
						<img src="./misumi/images/blanco.jpg" style="vertical-align: middle;" height="40px" width="1px"  />
						<img src="./misumi/images/informe.png" style="vertical-align: middle;" class="imagenTablonAnunciosLista"/>
					</div>
					
					
					<div id="p33_mensajesInformes">
						<div id="p33_cabeceraInformes"  >	
							<div class="p33_informesMensajeTitulo">	
								<p id="p33_parrafoCabeceraInformes"><spring:message code="p33_informeHuecosPopup.mensajeAvisos" /></p>
							</div>
						</div>
						<div id="p33_avisoInformeHuecos" style="display:none">
							<div class="p33_avisosMensaje" >	
								<div id="p33_avisosTexto">
									<p id="p33_avisosParrafo">- <span id='p33_avisosFecha'></span> <spring:message code="p33_informeHuecosPopup.mensajeRecordatorioInformeHuecos" /></p>
								</div>
								<div id="p33_avisosExcel">
									<a href="#" id="p33_mensajeInformeHuecosEnlace"><img src="./misumi/images/Excell.gif" class="p33_imagenExcel"></a>
								</div>
							</div>
						</div>
						<div id="p33_avisoInformes" style="display: none;">
							<div class="p33_avisosMensaje">	
							</div>
						</div>
						
						<div id="p35_cuerpoInformes" style="display:none">
							<div id="p35_informePescaMostrador"  >
								<div id="p35_informeTexto">
									<p id="p35_informeParrafo">- <span id='p35_informesPescaFecha'></span> <spring:message code="p35_popUpInformes.informePescaMostrador" /></p>
								</div>
								<div id="p35_informePdf">
									<a href="#" onclick="p36loadDatos('S');"><img src="./misumi/images/pdf.gif" class="p35_imagenPdf"></a>
								</div>
							</div>
						</div>
						
						
				 </div>	
				 
				 <div id="informePopup"></div>
				<div id="informeWindow"></div>
			</div>
