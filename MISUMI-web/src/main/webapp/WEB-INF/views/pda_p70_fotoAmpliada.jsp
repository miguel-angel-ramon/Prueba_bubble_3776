<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p70_FotoAmpliada.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p70FotoAmpliada.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP70FotoAmpliada.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p70_fotoAmpliada_contenido">
				<div id="pda_p70_fotoAmpliada_foto">						
					<img id="pda_p70_img_referencia" src="./pdaGetImageP70.do?codArticulo=${codArticuloFoto}"/>	
				</div>					
				<form id="pda_p70_fotoAmpliada_form" action="${actionP70}" method="post">
						<input type="hidden" id="devolucion" name="devolucion" value="${devolucion.devolucion}">
						<input type="hidden" id="referenciaFiltro" name="referenciaFiltro" value="${referenciaFiltro}">
						<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
						<input type="hidden" id="paginaActual" name="paginaActual" value="${paginaActual}">	
						<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">
						<input type="hidden" id="selProv" name="selProv" value="${selectProv}">

						<c:forEach items="${devolucion.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
							<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
							<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
						</c:forEach>
				</form>					
			</div>
		</div>
		