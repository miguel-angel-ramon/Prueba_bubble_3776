<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda_p105_msgAccionSiNo.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="N"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p105_msgAccionSiNo_contenido">
				<div id="pda_p105_msgAccionSiNo_msg">
					<c:choose>
						<c:when test="${accionBulto == 'AbrirBulto'}">
							<span class="colorMsg"><spring:message code="pda_p105_msgAbrirBultoSiNo.msg" /></span>
						</c:when>
						<c:otherwise>
							<span class="colorMsg"><spring:message code="pda_p105_msgBorrarBultoSiNo.msg" /></span>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p105_msgAccionSiNo_button">
					<form id="pda_p105_msgAccionSiNo_form" action="${actionP105}" method="post">
						<input id="pda_p105_btn_accionSi" name="accion_si" class="buttonAccionSi" value="<spring:message code="pda_p105_msgAccionSiNo.buttonSi" />" type="submit">
						<input id="pda_p105_btn_accionNo" name="accion_no" class="buttonAccionNo" value="<spring:message code="pda_p105_msgAccionSiNo.buttonNo" />" type="submit">
						
						<input type="hidden" id="paginaActual" name="paginaActual" value="${paginaActual}">
						<input type="hidden" id="accion" name="accion" value="${accionBulto}">
						<input type="hidden" id="devolucion" name="devolucion" value="${devolucion}">
						<input type="hidden" id="bulto" name="bulto" value="${bulto}">
						<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">
						<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
					</form>
				</div>				
			</div>
		</div>