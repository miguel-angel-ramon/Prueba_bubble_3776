<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p26_guardarVentaAnticipada.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p25VentaAnticipada.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP25VentaAnticipada.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">

			<div id="pda_p26_guardarVentaAnticipada_bloque1">
				<p><spring:message code="pda_p26_guardarVentaAnticipada.messageGuardado" /></p>
			</div>		
			<div id="pda_p26_filterButtons">
				<form:form method="post" action="pdaP25VentaAnticipada.do" commandName="user">
					<input type="submit" id="pda_p26_btn_aceptar" name="actionReset" class="botonAceptar" value=''/>
				</form:form>
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>