<c:when test="${imagenComercial.tratamientoVegalsa eq true}">

<jsp:include page="/WEB-INF/views/includes/pda_p01_header_modifPlanos.jsp" >
    <jsp:param value="pda_p33_imcVegalsa.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p33Imc.js?version=${misumiVersion}" name="jsFileComun"></jsp:param>
    <jsp:param value="pda_p33ImcVegalsa.js?version=${misumiVersion}" name="jsFilePropio"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="2" name="tipoReferencia"></jsp:param>
    <jsp:param value="pdaP33Imc.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" action="pdaP33Imc.do" commandName="pdaDatosImc">
			
				<div id="pda_p33_imc_error" style="visibility:hidden">
					<label id="pda_p33_fld_error"></label>
				</div>
			
				<div id="pda_p33_imc_titulo">
					<label id="pda_p33_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p33_modifPlanos.titulo" /></label>
				</div>
							
				<div id="pda_p33_imc_bloque_1">
					<div id="pda_p33_imc_bloque1_campo">	
						<form:input path="descArtConCodigo" id="pda_p33_fld_descripcionRef" class="input225" disabled="true" type="text"/>
					</div>	
				</div>		
				<div id="pda_p33_imc_bloque_2">
				</div>
				<div id="pda_p33_imc_bloque_3">
					<div id="pda_p33_imc_bloque_3_1" class="pda_p33_imc_bloque_3_1">
						<div id="pda_p33__bloque_3_1_1">
							<div class="pda_p33_lbl_80">
								<label id="pda_p33_lbl_facing" class="etiquetaCampo"><spring:message code="pda_p33_modifPlanos.facing" /></label>
							</div>
							<div class="pda_p33_input_30">
								<form:input path="facing" id="pda_p33_fld_facing" class="input30 inputEditable" maxlength="2" type="number"/>
							</div>
						</div>
						<div id="pda_p33_imc_bloque_3_1_2">
							<div class="pda_p33_lbl_80">
								<label id="pda_p33_lbl_multiplicador" class="etiquetaCampo"><spring:message code="pda_p33_modifPlanos.fondo" /></label>
							</div>
							<div class="pda_p33_input_30">
								<form:input path="multiplicador" id="pda_p33_fld_multiplicador" disabled="true" class="input30" type="text"/>
							</div>
						</div>
					</div>
					<div id="pda_p33_imc_bloque_3_2">
						<img src="./misumi/images/llave-50-gris.gif?version=${misumiVersion}" class="pda_p33_imagen_llave">
					</div>
					<div id="pda_p33__bloque_3_3">
						<div class="pda_p33_lbl_capacidad">
							<label id="pda_p33_lbl_capacidad" class="etiquetaCampo"><spring:message code="pda_p33_modifPlanos.capacidad" /></label>
						</div>
						<div class="pda_p33_input_capacidad">
							<form:input path="capacidad" id="pda_p33_fld_capacidad" class="input70" type="text" disabled="true"/>
						</div>
					</div>
				</div>
					
				 <c:if test="${user.perfil != 3}">
					<div id="pda_p33_loading">
						<img id="capaLoading"  style="visibility:hidden" src="./misumi/images/loading.gif?version=${misumiVersion}"/>
					</div>
					<div id="pda_p33_Buttons">
						<input type="button" id="pda_p33_btn_save" class="botonSubmitGrabar"/>
					</div>
				</c:if>
				<input type="hidden" id="pda_p33_procede" name="procede" value="${procede}">
				<input type="hidden" id="pda_p33_refActual" name="referenciaActual" value="${imagenComercial.codArt}">
				<input type="hidden" id="pda_p33_fld_facingOrig" name="referenciaActual" value="${imagenComercial.facing}">
				<input type="hidden" id="pda_p33_fld_capacidadOrig" value="${imagenComercial.capacidad}">
				<input type="hidden" id="pda_p33_fld_tratamientoVegalsa" value="${imagenComercial.tratamientoVegalsa}">
			</form:form>
		</div>		
</c:when>