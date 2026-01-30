<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p03_sfm_showMessage.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p03showMessage.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP21Sfm.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
				<div id="pda_p03_error_bloque1" class="mens_error">
					<spring:bind path="pdaError.descError">${status.value}</spring:bind>
				</div>	
				<div id="pda_p03_filterButtons">
					<form method="get" action="pdaP21Sfm.do" id="p03_form_sfm" >
						<input id="codArtAnterior" name="codArtAnterior" value="anterior" type="hidden"/>
						<input id="codArt" name="codArt" value='<spring:bind path="pdaDatosSfmCap.codArt">${status.value}</spring:bind>' type="hidden"/>
						<input id="origen" name="origen" value='<spring:bind path="pdaDatosCab.origenConsulta">${status.value}</spring:bind>' type="hidden"/>
					</form>
				</div>	
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>