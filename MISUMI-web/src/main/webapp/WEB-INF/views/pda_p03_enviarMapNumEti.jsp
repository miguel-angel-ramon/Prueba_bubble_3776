<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p03_enviarMapNumEti.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<input type="hidden" name="esPrehueco" id="esPrehueco" value="${esPrehueco}"/>
			<div id="pda_p03_enviarMapNumEti_bloqueTitulo">
				<label id="pda_p03_enviarMapNumEti_lbl_title"><spring:message code="pda_p03_enviarMapNumEti.imprimiendo"/></label>
			</div>
			<div id="pda_p03_enviarMapNumEti_bloque1" class="mens_aviso">
				<c:choose>
					<c:when test="${esPrehueco eq 'SI'}">
						<div id="marcoBorrar" class="marco">
							<a class="linkImpr" href="./pdaP03ResetNumeroEtiqueta.do?paginaDeVuelta=pdaP115PrehuecosLineal&codArt=${codigoArticulo}&sufijoPrehueco=_PREHUECO">
								<spring:message code="pda_p03_enviarMapNumEti.mensajeBorrarReferenciasRecogidas"/>					
							</a>
						</div>
						<br>
						<br>
						<div id="marcoNoBorrar" class="marco">
							<a class="linkImpr" href="./pdaP115PrehuecosLineal.do?menu=PDA_DR&codArt=${codigoArticulo}">
								<spring:message code="pda_p03_enviarMapNumEti.mensajeContinuarSinBorrar"/>					
							</a>
						</div>
					</c:when>
					<c:otherwise>
						<div id="marcoBorrar" class="marco">
							<a class="linkImpr" href="./pdaP03ResetNumeroEtiqueta.do?paginaDeVuelta=pdaP12DatosReferencia&codArt=${codigoArticulo}">
								<spring:message code="pda_p03_enviarMapNumEti.mensajeBorrarReferenciasRecogidas"/>					
							</a>
						</div>
						<br>
						<br>
						<div id="marcoNoBorrar" class="marco">
							<a class="linkImpr" href="./pdaP12DatosReferencia.do?menu=PDA_DR&codArt=${codigoArticulo}">
								<spring:message code="pda_p03_enviarMapNumEti.mensajeContinuarSinBorrar"/>					
							</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>