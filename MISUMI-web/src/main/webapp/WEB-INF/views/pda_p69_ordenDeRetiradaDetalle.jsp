<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p69_ordenDeRetiradaDetalle.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p69OrdenDeRetiradaDetalle.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP69OrdenDeRetiradaDetalle.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p69_ordenDeRetiradaDetalle_contenido">
				<div id="pda_p69_ordenDeRetiradaDetalle_datos">
					<c:if test="${conTextil}">
						<div class="ordenDeRetiradaDetalle conTextil">
							<div class="ordenDeRetiradaDetalleInfo">
								<label class="valorCampo" >${devolucionLinea.modelo}</label>
							</div>
							<div class="ordenDeRetiradaDetalleTitleInfo">												
								<label class="pda_p69_lbl_Info"><spring:message code="pda_p69_ordenDeRetiradaDetalle.talla"/></label>
								<label class="valorCampo" >${devolucionLinea.descrTalla}</label>
							</div>
							<div class="ordenDeRetiradaDetalleTitleInfo">												
								<label class="pda_p69_lbl_Info"><spring:message code="pda_p69_ordenDeRetiradaDetalle.color"/></label>
								<label class="valorCampo" >${devolucionLinea.descrColor}</label>
							</div>
							<div class="ordenDeRetiradaDetalleTitleInfo">												
								<label class="pda_p69_lbl_Info"><spring:message code="pda_p69_ordenDeRetiradaDetalle.modeloProveedor"/></label>
								<label class="valorCampo" >${devolucionLinea.modeloProveedor}</label>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty devolucionLinea.nLote && devolucionLinea.nLote != null}">
						<div class="ordenDeRetiradaDetalle">	
							<div class="ordenDeRetiradaDetalleTitle">
								<label class="pda_p69_lbl_Info"><spring:message code="pda_p69_ordenDeRetiradaDetalle.lote"/></label>														
							</div>
							<div class="ordenDeRetiradaDetalleInfo">					
								<c:forEach items="${loteConcatLst}" var="lote">	
									<label class="valorCampo" >${lote}</label>
									</br>
								</c:forEach>
							</div>																						
						</div>
					</c:if>
					<c:if test="${not empty devolucionLinea.nCaducidad && devolucionLinea.nCaducidad != null}">
						<div class="ordenDeRetiradaDetalle">
							<div class="ordenDeRetiradaDetalleTitle">												
								<label class="pda_p69_lbl_Info"><spring:message code="pda_p69_ordenDeRetiradaDetalle.caducidad"/></label>
							</div>
							<div class="ordenDeRetiradaDetalleInfo">
								<c:forEach items="${caducidadConcatLst}" var="caducidad">	
									<label class="valorCampo" >${caducidad}</label>
									</br>
								</c:forEach>
							</div>																	
						</div>
					</c:if>
				</div>					
				<form id="pda_p69_ordenDeRetiradaDetalle_form" action="${actionP69}" method="post">
						<input type="hidden" id="devolucion" name="devolucion" value="${devolucionLinea.devolucion}">
						<input type="hidden" id="referenciaFiltro" name="referenciaFiltro" value="${referenciaFiltro}">
						<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
						<input type="hidden" id="paginaActual" name="paginaActual" value="${paginaActual}">	
						
						<c:forEach items="${devolucionLinea.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
							<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
							<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
						</c:forEach>	
				</form>					
			</div>
		</div>
		