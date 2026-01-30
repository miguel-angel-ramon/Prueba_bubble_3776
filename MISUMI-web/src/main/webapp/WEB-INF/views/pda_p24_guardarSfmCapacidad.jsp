<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p24_guardarSfmCapacidad.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP21Sfm.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
				<c:choose>
					<c:when test="${pdaDatosSave.totalRegistros eq '0'}">
						<div id="pda_p24_guardarSfmCapacidad_bloque1" class="mens_error">
							<spring:message code="pda_p24_guardarSfmCapacidad.messageNoRegistrosGuardado" />
						</div>		
					</c:when>
					<c:otherwise>
						<div id="pda_p24_guardarSfmCapacidad_bloque1">
							<p><spring:message code="pda_p24_guardarSfmCapacidad.messageGuardado" arguments="${pdaDatosSave.totalGuardados},${pdaDatosSave.totalRegistros}" /></p>
						</div>		
					</c:otherwise>
				</c:choose>	
			<div id="pda_p24_filterButtons">
				<form:form method="post" action="pdaP24Guardar.do" commandName="user">
					<input type="submit" id="pda_p24_btn_aceptar"  class="botonAceptar" value=''/>
					<input type="hidden" name="origenConsulta" value="${pdaDatosSave.origenPantalla}">
					<c:choose>
					<c:when test="${pdaDatosSave.totalRegistros eq '0'}">
						<input type="hidden" name="errorGuardado" value="S">	
					</c:when>
					<c:when test="${pdaDatosSave.totalGuardados eq '0'}">
						<input type="hidden" name="errorGuardado" value="S">	
					</c:when>
					<c:otherwise>
						<input type="hidden" name="errorGuardado" value="N">		
					</c:otherwise>
				</c:choose>	
					
				</form:form>
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>