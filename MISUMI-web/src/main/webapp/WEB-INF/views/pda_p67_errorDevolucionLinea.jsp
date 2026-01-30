<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p67_ErrorDevolucionLinea.css?version=${misumiVersion}" name="cssFile"></jsp:param>
     <jsp:param value="pda_p67ErrorDevolucionLinea.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP67ErrorDevolucionLinea.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p67_devolucionLinea_contenido">
				<c:set var = "contieneErr" value = "${codErrorActualizar}"/>
				<c:choose>
					<c:when test = "${fn:contains(contieneErr, 'ERR')}">
						<div id="pda_p67_errorDevolucionLinea_msg">
							<div id="pda_p67_errorDevolucionLinea_header" class="colorMsgRed">
								<c:choose>
									<c:when test="${codErrorActualizar eq 'ERR_ACT'}">
										<spring:message code="pda_p67_errorDevolucionLinea.error.errActualizacionLineas" />
									</c:when>
									<c:when test="${codErrorActualizar eq 'ERR_NO_MOD'}">
										<spring:message code="pda_p67_errorDevolucionLinea.error.noModificados" />								
									</c:when>
									<c:when test="${codErrorActualizar eq 'ERR_COSTE'}">
										<spring:message code="pda_p67_errorDevolucionLinea.error.costeExcedido" />								
									</c:when>
								</c:choose>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div id="pda_p67_devolucionLinea_msg">
							<div id="pda_p67_devolucionLinea_header" class="colorMsgGreen">
								<spring:message code="pda_p67_devolucionLinea.msgOk" />
							</div>
							<div id="pda_p67_devolucionLinea_actualizado">
								<div>
									<c:choose>
										<c:when test="${countGuardadosLinea eq '1'}">
											${countGuardadosLinea} &nbsp;<spring:message code="pda_p67_lineaActualizada" />
										</c:when>
										<c:when test="${countGuardadosLinea eq '0'}">
										</c:when>
										<c:otherwise>
											${countGuardadosLinea} &nbsp;<spring:message code="pda_p67_lineasActualizadas" />
										</c:otherwise>
									</c:choose>
								</div>
								<div>
									<c:choose>
										<c:when test="${countErrorLinea eq '1'}">
											${countErrorLinea} &nbsp;<spring:message code="pda_p67_lineaErronea" />
										</c:when>
										<c:when test="${countErrorLinea eq '0'}">
										</c:when>
										<c:otherwise>
											${countErrorLinea} &nbsp;<spring:message code="pda_p67_lineasErroneas" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div id="pda_p67_devolucionLinea_icon">
							<a href="javascript: document.getElementById('pda_p67_errorDevolucionLinea_form').submit();">
								<img src="./misumi/images/dialog-accept-24.gif?version=">
							</a>
						</div>
					</c:otherwise>
				</c:choose>
				<form id="pda_p67_errorDevolucionLinea_form" action="${actionP67Volver}" method="post">
					<input type="hidden" id="devolucion" name="devolucion" value="${devolucionLinea.devolucion}">
					<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
					<input type="hidden" id="referenciaFiltro" name="referenciaFiltro" value="${referenciaFiltro}">
					<input type="hidden" id="paginaActual" name="paginaActual" value="${pagDevolucionLineas.page}">
					
					<c:forEach items="${devolucionLinea.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
							<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
							<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
					</c:forEach>
				</form>
			</div>
		</div>