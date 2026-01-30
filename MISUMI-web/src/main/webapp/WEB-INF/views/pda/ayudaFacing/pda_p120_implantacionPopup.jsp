<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda/ayudaFacing/pda_p120_implantacionPopup.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP119AyudaFacing.do"></jsp:param>
    <jsp:param name="procede" value="${pdaDatosPopupImplantacion.procede}"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->
	<!--  Contenido página -->
	<div id="contenidoPaginaResaltado">
		<form:form method="post" action="pdaP119AyudaFacing.do" commandName="pdaDatosPopupImplantacion">
			<div id="pda_p120_AreaPopupImplantacion">
				<div id="pda_p120_botonCerrar">
				 	<c:choose>
				 		<c:when test = "${not empty pdaDatosPopupImplantacion.procede && pdaDatosPopupImplantacion.procede eq 'pdaP98CapturaRestos'}">
				 			<a class="barraTituloCerrar" href="./pdaP98CapturaRestos.do?codArt=${pdaDatosPopupImplantacion.codArt}">
								<img src="./misumi/images/dialog_cancel.gif?version=local" title="<spring:message code="pda_p17_implantacionPopup.cerrar" />" class="barraTituloIconoCerrar">
							</a>
				 		</c:when>
				 		<c:when test = "${not empty pdaDatosPopupImplantacion.procede && pdaDatosPopupImplantacion.procede eq 'pdaP99SacadaRestos'}">
				 			<a class="barraTituloCerrar" href="./pdaP99SacadaRestos.do?codArt=${pdaDatosPopupImplantacion.codArt}">
								<img src="./misumi/images/dialog_cancel.gif?version=local" title="<spring:message code="pda_p17_implantacionPopup.cerrar" />" class="barraTituloIconoCerrar">
							</a>
				 		</c:when>
				 		<c:otherwise>
							<a class="barraTituloCerrar" href="./pdaP119AyudaFacing.do?codArt=${pdaDatosPopupImplantacion.codArt}
								&descArt=${pdaDatosImc.descArtConCodigo}&impl=${pdaDatosPopupImplantacion.implantacion}
								&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}
								&tipoRef=${pdaDatosImc.tipoReferencia}
								&facAncho=${pdaDatosImc.facAncho}
								&facAlto=${pdaDatosImc.facAlto}
								&cap=${pdaDatosImc.capacidad}
								&fac=${pdaDatosImc.facing}
								&imc=${pdaDatosImc.imc}
								&mult=${pdaDatosImc.multiplicador}">
								<img src="./misumi/images/dialog_cancel.gif?version=local" title="<spring:message code="pda_p17_implantacionPopup.cerrar" />" class="barraTituloIconoCerrar">
							</a>
					 	</c:otherwise>
					</c:choose>	
				</div>
				<div id="pda_p120_AreaImplantacion">
					<c:choose>
						<c:when test="${pdaDatosPopupImplantacion.flgColorImplantacion eq 'VERDE'}">
							<span id="pda_p120_ImplantacionVerde"><spring:bind path="pdaDatosPopupImplantacion.implantacion">${status.value}</spring:bind></span>
						</c:when>
						<c:when test="${pdaDatosPopupImplantacion.flgColorImplantacion eq 'ROJO'}">
							<span id="pda_p120_ImplantacionRojo"><spring:bind path="pdaDatosPopupImplantacion.implantacion">${status.value}</spring:bind></span>
						</c:when>
						<c:otherwise>
							<span id="pda_p120_Implantacion"><spring:bind path="pdaDatosPopupImplantacion.implantacion">${status.value}</spring:bind></span>
						</c:otherwise>
					</c:choose>		
				</div>
			</div>			
		</form:form>
	</div>
	<input type="hidden" id="pda_p17_origenGISAE" name="pda_p17_origenGISAE" value="${origenGISAE}"/>
	<input type="hidden" id="pda_p17_codArt" name="pda_p17_codArt" value="${pdaDatosPopupImplantacion.codArt}"/>
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>