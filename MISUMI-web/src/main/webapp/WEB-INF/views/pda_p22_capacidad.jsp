<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p22_capacidad.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p22Cap.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP21Sfm.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" action="pdaP21Sfm.do" commandName="pdaDatosSfmCap">
			<div id="pda_p22_capacidad_titulo">
				<c:choose>
					<c:when test="${pdaDatosSfmCap.flgFacing eq 'S' && pdaDatosSfmCap.flgFacingCapacidad eq 'S'}">
						<label id="pda_p22_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p22_capacidad.tituloFacingCapacidad" /></label>
					</c:when>
					<c:otherwise>
						<label id="pda_p22_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p22_capacidad.titulo" /></label>
					</c:otherwise>
				</c:choose>
			</div>
			<div id="pda_p22_capacidad_bloqueMensajes">
						
					<c:choose>
						<c:when test="${pdaDatosSfmCap.icono eq '1'}">
							<div id="pda_p22_capacidad_bloqueError">
								<p class="pda_p22_error">${pdaDatosSfmCap.errorIcono}</p>
							</div>
						</c:when>	
						<c:when test="${pdaDatosSfmCap.icono eq '3'}">
							<div id="pda_p22_capacidad_bloqueGuardado">
								<p class="pda_p22_guardado"><spring:message code="pda_p21_sfm.messageGuardado" /></p>
							</div>		
						</c:when>
					</c:choose>	
			</div>
			<div id="pda_p22_capacidad_bloque1">
				<div id="pda_p22_capacidad_bloque1_campo">	
					<form:input path="descArtConCodigo" id="pda_p22_fld_descripcionRef" class="input225" disabled="true" type="text"/>
				</div>	
			</div>		
			<!-- <c:choose>
				<c:when test="${empty (pdaDatosCab.origenConsulta)}">
					<div id="pda_p22_capacidad_bloque2">
						<div id="pda_p22_capacidad_bloque2_pagAnt">
							<c:choose>
								<c:when test="${pdaDatosSfmCap.posicion > 1}">
									<input type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
								</c:when>
								<c:otherwise>
									<input type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>
								</c:otherwise>
							</c:choose>
						</div>	
						<div id="pda_p22_capacidad_bloque2_pagNum">
							<p><spring:message code="pda_p22_capacidad.numReferencias" arguments="${pdaDatosSfmCap.posicion},${pdaDatosSfmCap.total}" /></p>
						</div>	
						<div id="pda_p22_capacidad_bloque2_pagSig">
							<c:choose>
								<c:when test="${pdaDatosSfmCap.total > pdaDatosSfmCap.posicion}">
									<input type="submit" name="actionSig" value='' class='botonSubmitSig'/>
								</c:when>
								<c:otherwise>
									<input type="submit" name="actionSig" value='' class='botonSubmitSigDes' disabled="disabled"/>
								</c:otherwise>
							</c:choose>
						</div>					
					</div>
				</c:when>
			</c:choose> -->
			
			<div id="pda_p22_capacidad_bloque3">
				<div id="pda_p22_capacidad_stock">
					<label id="pda_p22_lbl_stock" class="etiquetaCampoVertical"><spring:message code="pda_p22_capacidad.stock" /></label>
					<form:input path="stock" id="pda_p22_fld_stock" class="input70" disabled="true" type="text"/>
				</div>
				<div id="pda_p22_capacidad_Estado">&nbsp;
					<!--<div id="pda_p22_capacidad_EstadoLabel">
						<label id="pda_p22_lbl_Estado" class="etiquetaCampoVerticalNegrita"><spring:message code="pda_p22_capacidad.Estado" /></label>
					</div>	
					<div id="pda_p22_capacidad_EstadoIcono">&nbsp;
						<c:choose>
							<c:when test="${pdaDatosSfmCap.icono eq '1'}">
								<img src="./misumi/images/dialog-error-24.gif?version=${misumiVersion}" class="pda_p22_imagen_estado">
							</c:when>
							<c:when test="${pdaDatosSfmCap.icono eq '2'}">
								<img src="./misumi/images/modificado.gif?version=${misumiVersion}" class="pda_p22_imagen_estado">
							</c:when>
							<c:when test="${pdaDatosSfmCap.icono eq '3'}">
								<img src="./misumi/images/floppy.gif?version=${misumiVersion}" class="pda_p22_imagen_estado">
							</c:when>
						</c:choose> 
					</div>	-->
				</div>
				<div id="pda_p22_capacidad_diasStock">
					<label id="pda_p22_lbl_diasStock" class="etiquetaCampoVertical"><spring:message code="pda_p22_capacidad.diasStock" /></label>
					<form:input path="diasStock" id="pda_p22_fld_diasStock" class="input70" disabled="true" type="text"/>
				</div>
			</div>
			

			<c:choose>
				<c:when test="${pdaDatosSfmCap.flgFacing eq 'S' && pdaDatosSfmCap.flgFacingCapacidad eq 'S' && pdaDatosSfmCap.flgFacingCapacidad ne '0'}">
					<c:choose>
						<c:when test="${pdaDatosSfmCap.esFFPP}">
							<div id="pda_p22_capacidad_bloque_ffpp">
								
								<div id="pda_p22_capacidad_ffpp">
									<label id="pda_p22_lbl_capacidad" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.capacidad" /></label>
									<form:input path="capacidad" id="pda_p22_fld_capacidad_con_multiplicador"  disabled="true"  type="text"/>
								</div>
							
								<div id="pda_p22_facing_ffpp">
									<label id="pda_p22_lbl_facing" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.facing" /></label>
									<form:input path="facing" id="pda_p22_fld_facing_con_multiplicador" class="inputEditable"  type="number"/>
								</div>
							</div>
							<div id="pda_p22_capacidad_bloque_Imagen_ffpp">
								<div id="pda_p22_imagenMinimaComercial_ffpp">
									<label id="pda_p22_lbl_imagenMinimaComercial" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.imagenMinimaComercial" /></label>
									<form:input path="imagenComercialMin" id="pda_p22_fld_imagenMinimaComercial_con_multiplicador"  disabled="true"  type="text"/>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div id="pda_p22_capacidad_bloque_con_multiplicador">
								
								<div id="pda_p22_capacidad_con_multiplicador">
									<label id="pda_p22_lbl_capacidad" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.capacidad" /></label>
									<form:input path="capacidad" id="pda_p22_fld_capacidad_con_multiplicador"  disabled="true"  type="text"/>
								</div>
							
								<div id="pda_p22_facing_con_multiplicador">
									<label id="pda_p22_lbl_facing" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.facing" /></label>
									<form:input path="facing" id="pda_p22_fld_facing_con_multiplicador" class="inputEditable"  type="number"/>
								</div>
		
								<div id="pda_p22_multiplicadorFacing">
									<label id="pda_p22_lbl_multiplicadorFacing" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.multiplicadorFacing" /></label>
									<form:input path="multiplicadorFacing" id="pda_p22_fld_multiplicador"  disabled="true" type="text"/>
								</div>
								
							</div>
							<div id="pda_p22_capacidad_bloque_con_multiplicador_llave">
								
									<img src="./misumi/images/llave-50-gris.gif?version=${misumiVersion}" class="pda_p22_imagen_llave">
							
							</div>
							<div id="pda_p22_capacidad_bloque_con_multiplicador_Imagen">
								<div id="pda_p22_imagenMinimaComercial_con_multiplicador">
									<label id="pda_p22_lbl_imagenMinimaComercial" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.imagenMinimaComercial" /></label>
									<form:input path="imagenComercialMin" id="pda_p22_fld_imagenMinimaComercial_con_multiplicador"  disabled="true"  type="text"/>
								</div>
							</div>
						</c:otherwise>
					</c:choose>		
				</c:when>
				<c:otherwise>
				
					<div id="pda_p22_capacidad_bloque_sin_multiplicador">
						<div id="pda_p22_capacidad_bloque4">
							<div id="pda_p22_capacidad">
								<label id="pda_p22_lbl_capacidad" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.capacidad" /></label>
								
								<c:choose>
					        		<c:when test="${pdaDatosSfmCap.flgSfmFijo eq 'B'}">
										<form:input path="capacidad" id="pda_p22_fld_capacidad" class="input70" disabled="true"  type="text"/>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${pdaDatosSfmCap.flgFacing eq 'S' && pdaDatosSfmCap.flgFacingCapacidad eq 'S'}">
												<form:input path="capacidad" id="pda_p22_fld_capacidad" class="input70" disabled="true"  type="text"/>
											</c:when>
											<c:when test="${pdaDatosSfmCap.icono eq '1'}">
												<form:input path="capacidad" id="pda_p22_fld_capacidad" class="input70 inputResaltado"  type="number"/>
											</c:when>
											<c:otherwise>
												<form:input path="capacidad" id="pda_p22_fld_capacidad" class="input70 inputEditable"  type="number"/>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
											
							</div>
						</div>
						<div id="pda_p22_capacidad_bloque5">
							<div id="pda_p22_facing">
								<label id="pda_p22_lbl_facing" class="etiquetaCampo"><spring:message code="pda_p22_capacidad.facing" /></label>
								<c:choose>
									<c:when test="${pdaDatosSfmCap.flgFacing eq 'S' && pdaDatosSfmCap.flgFacingCapacidad eq 'S'}">
										<form:input path="facing" id="pda_p22_fld_facing" class="input70 inputEditable"  type="number"/>
									</c:when>
									<c:otherwise>
										<form:input path="facing" id="pda_p22_fld_facing" class="input70" disabled="true" type="text"/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						
					</div>
					
					
					
				</c:otherwise>
		    </c:choose>
			
			
			
						
			
		 <c:if test="${user.perfil != 3}">
			<div id="pda_p22_filterButtons">
					<input type="submit" id="pda_p22_btn_save"  class="botonSubmitGrabar" name="actionSave" value=""/>
			</div>
		</c:if>
			<input type="hidden" name="posicion" value="${pdaDatosSfmCap.posicion}">
			<input type="hidden" name="referenciaActual" value="${pdaDatosSfmCap.codArt}">
			<input type="hidden" id="pda_p22_fld_cap_origen" name="origenConsulta" value="${pdaDatosCab.origenConsulta}">
			<input type="hidden" name="origenGISAE" value="${pdaDatosCab.origenGISAE}">
			
			<input type="hidden" name="procede" value="${procede}">
			</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>