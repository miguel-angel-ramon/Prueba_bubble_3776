<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p03_existeMapNumEti.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p03ExisteMapNumEti.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p03_existeMapNumEti_bloque1" class="mens_aviso">
				<spring:message code="pda_p03_existeMapNumEti.mensajeExistenEtiquetasPendientes"/>
			</div>
			<div id="pda_p03_filterButtons">
				<form method="post" id="pda03ExisteMapNumEti" action="" >
					<input type="button" id="pda_p03_btn_no" class="boton" value='<spring:message code="pda_p03_existeMapNumEti.no" />' title="<spring:message code="pda_p03_existeMapNumEti.no" />"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" id="pda_p03_btn_si" class="boton" value='<spring:message code="pda_p03_existeMapNumEti.si" />' title="<spring:message code="pda_p03_existeMapNumEti.si" />"/>
				</form>
				<input type="hidden" name="pda_p115_sufijoPrehueco" id="pda_p115_sufijoPrehueco" value="${sufijoPrehueco}"/>
			</div>	
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>