<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p14_refCentroOkPopup.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pda_p14RefCentroPopUp.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="pdaP14refCentroOkPopup.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPaginaResaltado" onfocus="javascript:controlVentana(event);">
			<form:form method="post" action="pdaP14refCentroPopup.do" commandName="pdaDatosPopupReferencia">
				<div id="pda_p14_AreaPopupPedir" onfocus="javascript:controlVentana(event);">
					<div id="pda_p14_MotActivaDivTitle">
						<div id="pda_p14_div_TituloMotActiva" onfocus="javascript:controlVentana(event);">
							<span id="pda_p14_TituloMotActiva"></span>
						</div>	
						<div id="pda_p14_botonCerrar">
							<c:choose>
								<c:when test="${pdaDatosPopupReferencia.procede eq 'datosRef'}">
									<a class="barraTituloCerrar" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupReferencia.codArt}">
										<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
									</a>
								</c:when>
								<c:when test="${pdaDatosPopupReferencia.procede eq 'segPedidos'}">
									<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupReferencia.codArt}">
										<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
									</a>
								</c:when>
								<c:when test="${pdaDatosPopupReferencia.procede eq 'pdaP115PrehuecosLineal'}">
									<c:choose>
										<c:when test="${origen eq 'PH'}">
											<a class="barraTituloCerrar" href="./pdaP115PrehuecosLineal.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}">
												<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
											</a>
										</c:when>
										<c:otherwise>
											<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&origen=${origen}">
												<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
											</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<a class="barraTituloCerrar" href="./pdaP15MovStocks.do?codArt=${pdaDatosPopupReferencia.codArt}">
										<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
									</a>						
								</c:otherwise>
							</c:choose>
						</div>	
					</div>
	
					<form:hidden path="codArt" id="pda_p14_fld_codArt" />
					<form:hidden path="procede" id="pda_p14_fld_procede" />
					<c:choose>
						<c:when test="${pdaDatosPopupReferencia.tratamientoVegalsa}">
							<br>
							<br>
							<span class="pda_p14_Fecha"><spring:message code="pda_p14_refCentroPopup.mensajeFechaMMCActivoVegalsa"/>&nbsp;${pdaDatosPopupReferencia.fechaMmcStr}</span>
							<c:if test="${pdaDatosPopupReferencia.soloReparto eq 'S'}">
								<br>
								<br>
								<span class="pda_p14_Texto"><spring:message code="pda_p14_refCentroPopup.mensajeSoloRepartoActivoVegalsa"/></span>
							</c:if>
							<br>
							<br>
							<span class="pda_p14_Texto"><spring:message code="pda_p14_refCentroPopup.mensajeMapaVegalsa"/>&nbsp;${pdaDatosPopupReferencia.mapaReferencia}</span>
						</c:when>
					</c:choose>
				</div>
			</form:form>
		</div>
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>