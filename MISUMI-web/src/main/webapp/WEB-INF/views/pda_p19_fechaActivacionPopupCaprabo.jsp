<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p19_fechaActivacionPopupCaprabo.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP12DatosReferenciaCaprabo.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPaginaResaltado">
		<form:form method="post" action="pdaP19FechaActivacionPopupCaprabo.do" commandName="pdaDatosPopupFechaActivacion">
			<div id="pda_p19_caprabo_AreaPopupFechaActivacion">
				
				<div id="pda_p19_caprabo_FechaActivacionDivTitle">	
					<div id="pda_p19_caprabo_botonCerrar">
								
						<a class="barraTituloCerrar" href="./pdaP12DatosReferenciaCaprabo.do?codArt=${pdaDatosPopupFechaActivacion.codArt}">
								<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p19_caprabo_fechaActivacionPopup.cerrar" />" class="barraTituloIconoCerrar">
						</a>
						
					</div>	
				</div>

				<div id="pda_p19_caprabo_AreaFechaActivacion">
					<span id="pda_p19_caprabo_FechaActivacion"><spring:message code="pda_p19_caprabo_fechaActivacionPopup.textFechaActivacion" arguments="${pdaDatosPopupFechaActivacion.fechaActivacion}" /></span>
				</div>

			</div>
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>