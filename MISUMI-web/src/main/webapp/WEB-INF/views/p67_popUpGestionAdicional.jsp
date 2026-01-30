	<script src="./misumi/scripts/p67PopUpGestionAdicional.js?version=${misumiVersion}" type="text/javascript"></script>
	<!--  Contenido página -->
	
		<div id="p67_popupGestionAdicional" class="controlReturnP67" title="<spring:message code="p67_popUpGestionAdicional.tituloPopup"/>" style="display:none">
			<div id="p67AreaErrores" style="display: none;">
				<span id="p67_erroresTexto"><spring:message code="p67_popUpGestionAdicional.errorCargaDatos" /></span>
			</div>
			<div id="p67_AreaBloquePreguntas">
				<div id="p67_AreaBloquePreguntasLabel">
					<label id="p67_lbl_Preguntas" class="etiquetaCampoNegrita"><spring:message code="p67_popUpGestionAdicional.preguntasDelCentro" /></label>
				</div>
				<div id="p67_AreaBloquePreguntasImg">
					<img src="./misumi/images/question-24.png?version=${misumiVersion}" class="p67_ImgLabel" title="<spring:message code="p67_popUpGestionAdicional.preguntasDelCentro" />"/>
				</div>								
				<div id="p67_AreaBloquePreguntasDatos">
					<textarea id="p67_fld_Preguntas" rows="3" cols="100"></textarea>
				</div>
			</div>
			<div id="p67_AreaBloqueRespuestas">
				<div id="p67_AreaBloqueRespuestasLabel">
					<label id="p67_lbl_Respuestas" class="etiquetaCampoNegrita"><spring:message code="p67_popUpGestionAdicional.respuestas" /></label>
				</div>
				<div id="p67_AreaBloqueRespuestasImg">
					<img src="./misumi/images/dialog-accept-24.png?version=${misumiVersion}" class="p67_ImgLabel" title="<spring:message code="p67_popUpGestionAdicional.respuestas" />"/>
				</div>								
				<div id="p67_AreaBloqueRespuestasDatos">
					<textarea id="p67_fld_Respuestas" rows="3" cols="100"></textarea>
				</div>
			</div>
		</div>