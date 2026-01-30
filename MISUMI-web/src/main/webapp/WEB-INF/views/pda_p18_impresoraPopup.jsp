<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p18_impresoraPopup.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pda_p18ImpresoraPopUp.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="pdaP12DatosReferencia.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPaginaResaltado">
		<form:form method="post" action="pdaP18ImpresoraPopup.do" commandName="pdaDatosPopupImpresora">
			<div id="pda_p18_AreaPopupImpresora">
				
				<div id="pda_p18_ImpresoraDivTitle">	
					<div id="pda_p18_botonCerrar">
						<a class="barraTituloCerrar" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupImpresora.codArt}">
							<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p18_impresoraPopup.cerrar" />" class="barraTituloIconoCerrar">
						</a>
					</div>	
				</div>

				<div id="pda_p18_AreaMensajeImpresora">			
					<span id="pda_p18_MensajeImpresora"><spring:bind path="pdaDatosPopupImpresora.mensajeErrorWS">${status.value}</spring:bind></span>
				</div>

			</div>
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>