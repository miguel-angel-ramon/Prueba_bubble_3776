<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p19_fechaActivacionPopup.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP12DatosReferencia.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPaginaResaltado">
		<form:form method="post" action="pdap19fechaActivacionPopup.do" commandName="pdaDatosPopupFechaActivacion">
			<div id="pda_p19_AreaPopupFechaActivacion">
				
				<div id="pda_p19_FechaActivacionDivTitle">	
					<div id="pda_p19_botonCerrar">
						<c:choose>
							<c:when test="${pdaDatosPopupFechaActivacion.origen eq 'DR'}">
								<a class="barraTituloCerrar" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupFechaActivacion.codArt}">
									<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p19_fechaActivacionPopup.cerrar" />" class="barraTituloIconoCerrar">
								</a>
							</c:when>
							<c:when test="${pdaDatosPopupFechaActivacion.origen eq 'SP'}">
								<c:choose>
									<c:when test="${pdaDatosPopupFechaActivacion.mostrarFFPP eq 'S'}">
										<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?mostrarFFPP=N&codArt=${pdaDatosPopupFechaActivacion.codArt}&codArtRel=${pdaDatosPopupFechaActivacion.codArtRel}&origenGISAE=${origenGISAE}">
											<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p19_fechaActivacionPopup.cerrar" />" class="barraTituloIconoCerrar">
										</a>
									</c:when>
									<c:when test="${pdaDatosPopupFechaActivacion.mostrarFFPP eq 'N'}">
										<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?mostrarFFPP=S&codArt=${pdaDatosPopupFechaActivacion.codArt}&codArtRel=${pdaDatosPopupFechaActivacion.codArtRel}&origenGISAE=${origenGISAE}">
											<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p19_fechaActivacionPopup.cerrar" />" class="barraTituloIconoCerrar">
										</a>
									</c:when>	
									<c:otherwise>
										<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupFechaActivacion.codArt}&codArtRel=${pdaDatosPopupFechaActivacion.codArtRel}&origenGISAE=${origenGISAE}">
											<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p19_fechaActivacionPopup.cerrar" />" class="barraTituloIconoCerrar">
										</a>
									</c:otherwise>
								</c:choose>	
							</c:when>	
							<c:otherwise>
								<a class="barraTituloCerrar" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupFechaActivacion.codArt}">
									<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p19_fechaActivacionPopup.cerrar" />" class="barraTituloIconoCerrar">
								</a>
							</c:otherwise>
						</c:choose>	
					</div>	
				</div>

				<div id="pda_p19_AreaFechaActivacion">
					<span id="pda_p19_FechaActivacion"><spring:message code="pda_p19_fechaActivacionPopup.textFechaActivacion" arguments="${pdaDatosPopupFechaActivacion.fechaActivacion}" /></span>
				</div>

			</div>
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>