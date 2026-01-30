<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p61_realizarDevolucionCabecera.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p61RealizarDevolucionCabecera.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP61RealizarDevolucionCabecera.do" name="actionRef"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p61_realizarDevolucionCabecera_titulo">
				<label id="pda_p61_realizarDevolucionCabecera_lbl_titulo">${devolucion.titulo1}</label>
			</div>
			<div id="pda_p61_realizarDevolucionCabecera_contenido">
				<div class="pda_p61_realizarDevolucionCabecera_bloque">
					<div class="bloqueInfo">
						<label class="pda_p61_realizarDevolucionCabecera_lbl_Info"><spring:message code="pda_p61_realizarDevolucionCabecera.localizador" /></label>
					</div>
					<div class="bloqueDato">
						<label>${devolucion.localizador}</label>
					</div>
				</div>
				<c:choose>
					<c:when test="${devolucion.motivo eq 'Creada por el centro'}">
						<div class="pda_p61_realizarDevolucionCabecera_bloque">
							<label class="pda_p61_realizarDevolucionCabecera_lbl_Info"><spring:message code="pda_p61_realizarDevolucionCabecera.creadaPorCentro" /></label>
						</div>
					</c:when>
					<c:otherwise>
						<div class="pda_p61_realizarDevolucionCabecera_bloque">
							<div class="bloqueInfo">
								<label class="pda_p61_realizarDevolucionCabecera_lbl_Info"><spring:message code="pda_p61_realizarDevolucionCabecera.fechaDesde" /></label>
							</div>
							<div class="bloqueDato">
								<label>${devolucion.fechaDesdeStr}</label>
							</div>
						</div>
						<div class="pda_p61_realizarDevolucionCabecera_bloque">
							<div class="bloqueInfo">
								<label class="pda_p61_realizarDevolucionCabecera_lbl_Info"><spring:message code="pda_p61_realizarDevolucionCabecera.fechaHasta" /></label>
							</div>
							<div class="bloqueDato">
								<label>${devolucion.fechaHastaStr}</label>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="pda_p61_realizarDevolucionCabecera_bloque">
					<div class="bloqueInfo">
						<label class="pda_p61_realizarDevolucionCabecera_lbl_Info"><spring:message code="pda_p61_realizarDevolucionCabecera.recogida" /></label>
					</div>
					<div class="bloqueDato">
						<label class="colorGray">${devolucion.recogida}</label>
					</div>
				</div>
				<div class="pda_p61_realizarDevolucionCabecera_bloque">
					<div class="bloqueInfo">
						<label class="pda_p61_realizarDevolucionCabecera_lbl_Info"><spring:message code="pda_p61_realizarDevolucionCabecera.abono" /></label>
					</div>
					<div class="bloqueDato">
						<label class="colorGray">${devolucion.abono}</label>
					</div>
				</div>
				<div class="pda_p61_realizarDevolucionCabecera_bloque">
					<div class="bloqueInfo">
						<label class="pda_p61_realizarDevolucionCabecera_lbl_Info"><spring:message code="pda_p61_realizarDevolucionCabecera.observaciones" /></label>
					</div>
					<div class="bloqueDato">
						<label>${devolucion.descripcion}</label>
					</div>
				</div>
				<form id="pda_p61_realizarDevolucionCabecera_form" action="${actionP61Volver}" method="post">
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
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>