<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p17_implantacionPopupCaprabo.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP12DatosReferenciaCaprabo.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPaginaResaltado">
		<form:form method="post" action="pdaP17implantacionPopupCaprabo.do" commandName="pdaDatosPopupImplantacion">
			<div id="pda_p17_caprabo_AreaPopupImplantacion">
				
				<div id="pda_p17_caprabo_ImplantacionDivTitle">	
					<div id="pda_p17_caprabo_botonCerrar">
						<a class="barraTituloCerrar" href="./pdaP12DatosReferenciaCaprabo.do?codArt=${pdaDatosPopupImplantacion.codArt}">
							<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p17_caprabo_implantacionPopup.cerrar" />" class="barraTituloIconoCerrar">
						</a>
					</div>	
				</div>

				<div id="pda_p17_caprabo_AreaImplantacion">			
					<c:choose>
						<c:when test="${pdaDatosPopupImplantacion.flgColorImplantacion eq 'VERDE'}">
							<span id="pda_p17_caprabo_ImplantacionVerde"><spring:bind path="pdaDatosPopupImplantacion.implantacion">${status.value}</spring:bind></span>
						</c:when>
						<c:when test="${pdaDatosPopupImplantacion.flgColorImplantacion eq 'ROJO'}">
							<span id="pda_p17_caprabo_ImplantacionRojo"><spring:bind path="pdaDatosPopupImplantacion.implantacion">${status.value}</spring:bind></span>
						</c:when>
						<c:otherwise>
							<span id="pda_p17_caprabo_Implantacion"><spring:bind path="pdaDatosPopupImplantacion.implantacion">${status.value}</spring:bind></span>
						</c:otherwise>
					</c:choose>		
				</div>

			</div>
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>