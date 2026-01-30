<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p72_msgFinalizarBultoStockDevueltoVacios.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP72MsgFinalizarBultoStockDevueltoVacios.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p72_msgFinalizarBultoStockDevueltoVacios_contenido">
				<div id="pda_p72_msgFinalizarBultoStockDevueltoVacios_msg">
					<span class="colorMsg"><spring:message code="pda_p72_msgFinalizarBultoStockDevueltoVacios.msg" /></span>
				</div>
				<div id="pda_p72_msgFinalizarBultoStockDevueltoVacios_button">
					<form id="pda_p72_msgFinalizarBultoStockDevueltoVacios_form" action="${actionP72}" method="post">
						<input id="pda_p72_msgFinalizarBultoStockDevueltoVacios_finalizarSi" name="finalizar_si" class="buttonFinalizarSi" value="<spring:message code="pda_p72_msgFinalizarBultoStockDevueltoVacios.buttonSi" />" type="submit">
						<input id="pda_p72_msgFinalizarBultoStockDevueltoVacios_finalizarNo" name="finalizar_no" class="buttonFinalizarNo" value="<spring:message code="pda_p72_msgFinalizarBultoStockDevueltoVacios.buttonNo" />" type="submit">
						
						<input type="hidden" id="paginaActual" name="paginaActual" value="${paginaActual}">
						<input type="hidden" id="accion" name="accion" value="FinalizarPopup">
						<input type="hidden" id="devolucion" name="devolucion" value="${devolucion.devolucion}">
						<input type="hidden" id="referenciaFiltro" name="referenciaFiltro" value="${referenciaFiltro}">
						<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
						<input type="hidden" id="flgRellenarHuecos" name ="flgRellenarHuecos" value="S">
						
						<c:forEach items="${devolucion.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
							<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
							<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
						</c:forEach>	
					</form>
				</div>				
			</div>
		</div>