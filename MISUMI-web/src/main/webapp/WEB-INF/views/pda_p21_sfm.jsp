<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p21_sfm.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p21Sfm.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP21Sfm.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" action="pdaP21Sfm.do" commandName="pdaDatosSfmCap">
			<c:choose>
				<c:when test="${empty (pdaDatosSfmCap.codArt)}">
					<div id="pda_p21_sfm_titulo">
						<label id="pda_p21_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p21_sfm.tituloSFMCap" /></label>
					</div>
				</c:when>
				<c:otherwise>
					<div id="pda_p21_sfm_titulo">
						<label id="pda_p21_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p21_sfm.titulo" /></label>
					</div>
					<div id="pda_p21_sfm_bloqueMensajes">
						
						<c:choose>
						<c:when test="${pdaDatosSfmCap.icono eq '1'}">
							<div id="pda_p21_sfm_bloqueError">
								<p class="pda_p21_error">${pdaDatosSfmCap.errorIcono}</p>
							</div>
						</c:when>	
						<c:when test="${pdaDatosSfmCap.icono eq '3'}">
							<div id="pda_p21_sfm_bloqueGuardado">
								<p class="pda_p21_guardado"><spring:message code="pda_p21_sfm.messageGuardado" /></p>
							</div>		
						</c:when>
						</c:choose>	
					</div>
					<div id="pda_p21_sfm_bloque1">
						<div id="pda_p21_sfm_bloque1_campo">	
							<form:input path="descArtConCodigo" id="pda_p21_fld_descripcionRef" class="input225" disabled="true" type="text"/>
						</div>	
					</div>		
					<!--<c:choose>
						<c:when test="${empty (pdaDatosCab.origenConsulta)}">
							<div id="pda_p21_sfm_bloque2">
								<div id="pda_p21_sfm_bloque2_pagAnt">
									<c:choose>
										<c:when test="${pdaDatosSfmCap.posicion > 1}">
											<input type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
										</c:when>
										<c:otherwise>
											<input type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>
										</c:otherwise>
									</c:choose>
								</div>	
								<div id="pda_p21_sfm_bloque2_pagNum">
									<p class="pda_p21_numReferencias"><spring:message code="pda_p21_sfm.numReferencias" arguments="${pdaDatosSfmCap.posicion},${pdaDatosSfmCap.total}" /></p>
								</div>
								<div id="pda_p21_sfm_bloque2_pagSig">
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
					
					
					<div id="pda_p21_sfm_bloque4">
						<div id="pda_p21_sfm_LimSup">
							<label id="pda_p21_lbl_LimSup" class="etiquetaCampoVertical"><spring:message code="pda_p21_sfm.LimSup" /></label>
							<form:input path="lsf" id="pda_p21_fld_LimSup" class="input70" disabled="true" type="text"/>
						</div>
						<div id="pda_p21_sfm_Estado">&nbsp;
							<!-- <div id="pda_p21_sfm_EstadoLabel">
								<label id="pda_p21_lbl_Estado" class="etiquetaCampoVerticalNegrita"><spring:message code="pda_p21_sfm.Estado" /></label>
							</div>
							<div id="pda_p21_sfm_EstadoIcono">	
								<c:choose>
									<c:when test="${pdaDatosSfmCap.icono eq '1'}">
										<img src="./misumi/images/dialog-error-24.gif?version=${misumiVersion}" class="pda_p21_imagen_estado">
									</c:when>
									<c:when test="${pdaDatosSfmCap.icono eq '2'}">
										<img src="./misumi/images/modificado.gif?version=${misumiVersion}" class="pda_p21_imagen_estado">
									</c:when>
									<c:when test="${pdaDatosSfmCap.icono eq '3'}">
										<img src="./misumi/images/floppy.gif?version=${misumiVersion}" class="pda_p21_imagen_estado">
									</c:when>
								</c:choose>
							</div>	 -->
						</div>
						<div id="pda_p21_sfm_LimInf">
							<label id="pda_p21_lbl_LimInf" class="etiquetaCampoVertical"><spring:message code="pda_p21_sfm.LimInf" /></label>
							<form:input path="lmin" id="pda_p21_fld_LimInf" class="input70" disabled="true" type="text"/>
						</div>
					</div>
					<div id="pda_p21_sfm_bloque5">
						<div id="pda_p21_sfm">
							<label id="pda_p21_lbl_sfm" class="etiquetaCampoVertical"><spring:message code="pda_p21_sfm.sfm" /></label>
							<c:choose>
								<c:when test="${pdaDatosSfmCap.flgSfmFijo eq 'B'}">
									<form:input path="sfm" id="pda_p21_fld_sfm" class="input70" disabled="true" type="number" step="0.001"/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaDatosSfmCap.icono eq '1'}">
											<form:input path="sfm" id="pda_p21_fld_sfm" class="input70 inputResaltado" type="number" step="0.001"/>
										</c:when>
										<c:otherwise>
											<form:input path="sfm" id="pda_p21_fld_sfm" class="input70 inputEditable" type="number" step="0.001"/>
										</c:otherwise>
									</c:choose>	
								</c:otherwise>
							</c:choose>
						</div>
						<div id="pda_p21_sfm_dias">
							<label id="pda_p21_lbl_sfm_dias" class="etiquetaCampoVertical"><spring:message code="pda_p21_sfm.sfm_dias" /></label>
							<c:choose>
								<c:when test="${pdaDatosSfmCap.flgSfmFijo eq 'B'}">
									<form:input path="coberturaSfm" id="pda_p21_fld_sfm_dias" class="input70" disabled="true" type="number" step="0.001"/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaDatosSfmCap.icono eq '1'}">
											<form:input path="coberturaSfm" id="pda_p21_fld_sfm_dias" class="input70 inputResaltado" type="number" step="0.001"/>
										</c:when>
										<c:otherwise>
											<form:input path="coberturaSfm" id="pda_p21_fld_sfm_dias" class="input70 inputEditable" type="number" step="0.001"/>
										</c:otherwise>
									</c:choose>	
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div id="pda_p21_sfm_bloque6">
						<div id="pda_p21_sfm_vidaUtil">
							<label id="pda_p21_lbl_vidaUtil" class="etiquetaCampoVertical"><spring:message code="pda_p21_sfm.vidaUtil" /></label>
							<form:input path="vidaUtil" id="pda_p21_fld_vidaUtil" class="input65" disabled="true" type="text"/>
						</div>
						<div id="pda_p21_sfm_vm_sfm">
							<label id="pda_p21_lbl_sfm_vm_sfm" class="etiquetaCampoVertical"><spring:message code="pda_p21_sfm.vm_sfm" /></label>
							<form:input path="ventaMedia" id="pda_p21_fld_sfm_vm_sfm" class="input65" disabled="true" type="text"/>
						</div>
						<div id="pda_p21_sfm_ventaAnti">
							<label id="pda_p21_lbl_sfm_ventaAnti" class="etiquetaCampoVertical"><spring:message code="pda_p21_sfm.ventaAnti" /></label>
							<form:input path="ventaAnticipada" id="pda_p21_fld_sfm_ventaAnti" class="input65" disabled="true" type="text"/>
						</div>
					</div>
				
				<c:if test="${user.perfil != 3}">
					<div id="pda_p21_filterButtons">
							<input type="submit" id="pda_p21_btn_save"  class="botonSubmitGrabar" name="actionSave" value=""/>
					</div>	
				</c:if>	
				
							
				</c:otherwise>
			</c:choose>
			<input type="hidden" id="pda_p21_procede" name="procede" value="${procede}">
			<input type="hidden" name="posicion" value="${pdaDatosSfmCap.posicion}">
			<input type="hidden" name="referenciaActual" value="${pdaDatosSfmCap.codArt}">
			<input type="hidden" id="pda_p21_fld_sfm_origen" name="origenConsulta" value="${pdaDatosCab.origenConsulta}">
			<input type="hidden" name="origenGISAE" value="${pdaDatosCab.origenGISAE}">
			<input type="hidden" id="pda_p21_fld_sfm_tmp" name="pda_p21_fld_sfm_tmp" value="${pdaDatosSfmCap.sfm}">
			<input type="hidden" id="pda_p21_fld_sfm_dias_tmp" name="pda_p21_fld_sfm_dias_tmp" value="${pdaDatosSfmCap.coberturaSfm}">
			</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>