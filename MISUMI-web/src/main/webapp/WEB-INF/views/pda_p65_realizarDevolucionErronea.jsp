<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p65_realizarDevolucionErronea.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p65RealizarDevolucionErronea.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP65RealizarDevolucionErronea.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p65_devolucionErronea_titulo">
				<div id="pda_p65_titulo" href="#">${devolucion.titulo1}</div>
			</div>
			<div id="pda_p65_devolucionErronea_contenido">
				<div id="pda_p65_devolucionErronea_msg">
					<div id="pda_p65_devolucionLineaErronea_header" class="colorMsg">${msgError}</div>
				</div>
				<form id="pda_p65_devolucionErronea_form" action="${actionP65Volver}" method="post">
					<input type="hidden" id="devolucion" name="devolucion" value="${devolucion.devolucion}">
					<input type="hidden" id="referenciaFiltro" name="referenciaFiltro" value="${referenciaFiltro}">
					<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
					<input type="hidden" id="paginaActual" name="paginaActual" value="${paginaActual}">
					<input type="hidden" id="accion" name="accion" value="">
					
					<c:forEach items="${devolucion.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
							<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
							<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
					</c:forEach>
				</form>
			</div>
		</div>