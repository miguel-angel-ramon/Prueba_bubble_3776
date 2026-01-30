<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p46_guardarInvLib.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p46_guardarInvLib_titulo">
				<label id="pda_p46_lbl_titulo" class="etiquetaCampoNegritaReducido"><spring:message code="pda_p46_guardarInvLib.titulo" /></label>
			</div>
			<c:choose>
				<c:when test="${pdaDatosSave.totalRegistros eq '0'}">
					<div id="pda_p46_guardarInvLib_bloque1" class="mens_error">
						<spring:message code="pda_p46_guardarInvLib.messageNoRegistrosGuardado" />
					</div>		
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${pdaDatosSave.totalRegistros eq '1'}">
							<div id="pda_p46_guardarInvLib_bloque1">
								<p><spring:message code="pda_p46_guardarInvLib.messageReferenciaUnica"/></p>
							</div>
						</c:when>
						<c:otherwise>
							<div id="pda_p46_guardarInvLib_bloque1">
								<p><spring:message code="pda_p46_guardarInvLib.messageReferenciasVarias" arguments="${pdaDatosSave.totalRegistros}" /></p>
							</div>
						</c:otherwise>	
					</c:choose>
					
					<c:choose>
						<c:when test="${pdaDatosSave.totalGuardadosOk ne '0'}">
							<div class="mens_sangria">
							<c:choose>
								<c:when test="${pdaDatosSave.totalGuardadosOk eq '1'}">
									<div id="pda_p46_guardarInvLib_bloqueGuardadoOk" class="mens_guardadoOk">
										<spring:message code="pda_p46_guardarInvLib.messageGuardadoOkUnica"/>
									</div>
								</c:when>
								<c:otherwise>
									<div id="pda_p46_guardarInvLib_bloqueGuardadoOk" class="mens_guardadoOk">
										<spring:message code="pda_p46_guardarInvLib.messageGuardadoOkVarias" arguments="${pdaDatosSave.totalGuardadosOk}" />
									</div>
								</c:otherwise>
							</c:choose>	
						</c:when>
					</c:choose>	

					<c:choose>
						<c:when test="${pdaDatosSave.totalGuardadosError ne '0'}">
							<div class="mens_sangria">
							<c:choose>
								<c:when test="${pdaDatosSave.totalGuardadosError eq '1'}">
									<div id="pda_p46_guardarInvLib_bloqueGuardadoError" class="mens_guardadoError">
										<spring:message code="pda_p46_guardarInvLib.messageGuardadoErrorUnica"/>
									</div>
								</c:when>
								<c:otherwise>
									<div id="pda_p46_guardarInvLib_bloqueGuardadoError" class="mens_guardadoError">
										<spring:message code="pda_p46_guardarInvLib.messageGuardadoErrorVarias" arguments="${pdaDatosSave.totalGuardadosError}" />
									</div>
								</c:otherwise>
							</c:choose>	
						</c:when>
					</c:choose>	

					<c:choose>
						<c:when test="${pdaDatosSave.totalGuardadosAviso ne '0'}">
							<div class="mens_sangria">
							<c:choose>
								<c:when test="${pdaDatosSave.totalGuardadosAviso eq '1'}">
									<div id="pda_p46_guardarInvLib_bloqueGuardadoAviso" class="mens_guardadoAviso">
										<spring:message code="pda_p46_guardarInvLib.messageGuardadoAvisoUnica"/>
									</div>
								</c:when>
								<c:otherwise>
									<div id="pda_p46_guardarInvLib_bloqueGuardadoAviso" class="mens_guardadoAviso">
										<spring:message code="pda_p46_guardarInvLib.messageGuardadoAvisoVarias" arguments="${pdaDatosSave.totalGuardadosAviso}" />
									</div>
								</c:otherwise>
							</c:choose>	
						</c:when>
					</c:choose>	
					
					<c:choose>
						<c:when test="${pdaDatosSave.totalGuardadosAviso ne '0'}">
							</div>
						</c:when>
					</c:choose>	
					
					<c:choose>
						<c:when test="${pdaDatosSave.totalGuardadosError ne '0'}">
							</div>
						</c:when>
					</c:choose>	
					
					<c:choose>
						<c:when test="${pdaDatosSave.totalGuardadosOk ne '0'}">
							</div>
						</c:when>
					</c:choose>	
				</c:otherwise>
			</c:choose>	
			<div id="pda_p46_filterButtons">
				<form:form method="post" action="pdaP46GuardarInvLib.do" commandName="user">
					<c:choose>
						<c:when test="${pdaDatosSave.totalRegistros eq '1' && pdaDatosSave.totalGuardadosOk eq '0'}">
							<input type="hidden" id="pda_p46_volverReferenciaActual" name="pda_p46_volverReferenciaActual" value="S"/>
						</c:when>
						<c:otherwise>
						<input type="hidden" id="pda_p46_volverReferenciaActual" name="pda_p46_volverReferenciaActual" value="N"/>
						</c:otherwise>
					</c:choose>	
					<input type="hidden" name="origenGISAE" value="${pdaDatosCab.origenGISAE}">
					<input type="hidden" name="seccion" value="${pdaDatosCab.seccion}">
					<input type="submit" id="pda_p46_btn_aceptar"  class="botonAceptar" value=''/>
				</form:form>
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>