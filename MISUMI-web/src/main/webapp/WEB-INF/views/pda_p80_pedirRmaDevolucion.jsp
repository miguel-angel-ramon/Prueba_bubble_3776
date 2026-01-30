<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p80_pedirRmaDevolucion.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p80PedirRmaDevolucion.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP80PedirRmaDevolucion.do" name="actionRef"></jsp:param>
</jsp:include>

<!-- Contenido de la JSP -->
<!--  Contenido página -->
<div id="contenidoPagina">
	<form id="pda_p80_pedirRmaDevolucion_form" action="${actionP80}" method="post">
		<div id="pda_p80_pedirRmaDevolucion_contenido">
			<c:if test="${not empty msgErrorKey}">
				<%-- Con error --%>
				<div id="pda_p80_pedirRmaDevolucion_err">
					<span class="mens_error"><spring:message code="${msgErrorKey}" /></span>
				</div>
			</c:if>
			<c:if test="${'pda_p80_pedirRmaDevolucion.msg' ne msgErrorKey}">
				<%-- Excluyo el caso en que el mensaje de error es el mismo que el normal para no repertirlo --%>
				<div id="pda_p80_pedirRmaDevolucion_msg">
					<span class="colorMsg"><spring:message code="pda_p80_pedirRmaDevolucion.msg" /></span>
				</div>
			</c:if>
			<div id="pda_p80_pedirRmaDevolucion_bloque_rma">
				<label for="pda_p80_codRma" id="pda_p80_codRmaLabel"><spring:message code="pda_p80_pedirRmaDevolucion.label.rma" /></label>
				<input id="pda_p80_codRma" name="codRMA" type="number" value="${devolucion.codRMA}" maxlength="15">
			</div>
			<div id="pda_p80_pedirRmaDevolucion_button">
				<input id="pda_p68_btn_finalizarSi" name="finalizar_si" class="buttonFinalizarSi" value="<spring:message code="pda_p80_pedirRmaDevolucion.buttonSi" />" type="submit">
				<input id="pda_p68_btn_finalizarNo" name="finalizar_no" class="buttonFinalizarNo" value="<spring:message code="pda_p80_pedirRmaDevolucion.buttonNo" />" type="submit">
				
				<input type="hidden" id="paginaActual" name="paginaActual" value="${paginaActual}">
				<input type="hidden" id="accion" name="accion" value="FinalizarRma">
				<input type="hidden" id="devolucion" name="devolucion" value="${devolucion.devolucion}">
				<input type="hidden" id="referenciaFiltro" name="referenciaFiltro" value="${referenciaFiltro}">
				<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
				
				<c:forEach items="${devolucion.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
					<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
					<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
				</c:forEach>
			</div>				
		</div>	
	</form>
</div>