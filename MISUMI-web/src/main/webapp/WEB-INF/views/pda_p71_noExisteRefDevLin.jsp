<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p71_NoExisteRefDevLin.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p71NoExisteRefDevLin.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP71NoExiteRefDevLin.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p71_noExisteRefDevLin_contenido">
				<div id="pda_p71_noExisteRefDevLin_mensaje">	
					<c:choose>
						<c:when test="${codErrP71 eq '1'}">
							<label class="etiquetaCampo pda_p71_colorLetra"><spring:message code="pda_p71_noExisteRefDevLin.msgRefPerm" /></label>				
						</c:when>
						<c:when test="${codErrP71 eq '2'}">
							<label class="etiquetaCampo pda_p71_colorLetra"><spring:message code="pda_p71_noExisteRefDevLin.msgNoRefPro" /></label>				
						</c:when>
						<c:otherwise>
							<label class="etiquetaCampo pda_p71_colorLetra"><spring:message code="pda_p71_noExisteRefDevLin.msg" /></label>						
						</c:otherwise>
					</c:choose>					
				</div>					
				<form id="pda_p71_noExisteRefDevLin_form" action="${actionP71}" method="post">
						<input type="hidden" id="devolucion" name="devolucion" value="${devolucionLinea.devolucion}">
						<input type="hidden" id="paginaActual" name="paginaActual" value="${paginaActual}">
						<input type="hidden" id="referenciaFiltroRellenaYCantidadEnter" name="referenciaFiltroRellenaYCantidadEnter" value="S">	
						<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
						<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">				
						<input type="hidden" id="selProv" name="selProv" value="${selectProv}">

						<c:forEach items="${devolucionLinea.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
							<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
							<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
						</c:forEach>
				</form>					
			</div>
		</div>