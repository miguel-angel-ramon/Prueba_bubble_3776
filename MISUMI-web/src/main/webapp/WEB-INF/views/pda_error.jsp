<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_error.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
				<div id="pda_p03_error_bloque1" class="mens_error">
					<spring:message code="error"/>
				</div>		
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>