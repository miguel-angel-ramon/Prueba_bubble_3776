<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p66_noHayDevolucionesError.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP66NoHayDevolucionesError.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
				<div id="pda_p66_error" class="mens_error">
					<c:choose>
						<c:when test="${origenErrorDevolucion eq 'pda_p60_realizarDevolucion'}">
							<c:choose>
								<c:when test ="${codError eq '0'}">
									<spring:message code="pda_p66_noHayDevoluciones.error"/>
								</c:when>
								<c:otherwise>
									<spring:message code="pda_p66_noHayDevoluciones.error2"/>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${origenErrorDevolucion eq 'pda_p62_realizarDevolucionFinDeCampania'}">
							<spring:message code="pda_p62_errorAlObtenerLineasDevolucionFinCampana.error"/>
						</c:when>
						<c:when test="${origenErrorDevolucion eq 'pda_p63_realizarDevolucionOrdenDeRetirada'}">
							<c:choose>
								<c:when test="${tipoErrorDevolucion eq 'pda_p63_obtenerLineasDevEstadosOrdenRetirada'}">
									<spring:message code="pda_p63_errorAlObtenerLineasDevEstadosOrdenRetirada.error"/>
								</c:when>
								<c:when test="${tipoErrorDevolucion eq 'pda_p63_obtenerListaDevOrdenRetirada'}">
									<spring:message code="pda_p63_errorAlObtenerListaDevOrdenRetirada.error"/>
								</c:when>
								<c:otherwise>
									<spring:message code="pda_p63_errorAlObtenerLineasDevolucionOrdenRetirada.error"/>
								</c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>
				</div>		
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>