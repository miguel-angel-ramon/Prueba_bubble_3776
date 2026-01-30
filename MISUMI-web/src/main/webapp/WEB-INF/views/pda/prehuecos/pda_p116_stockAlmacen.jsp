<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp">
	<jsp:param name="cssFile" value="pda/prehuecos/pda_p116_stockAlmacen.css?version=${misumiVersion}"></jsp:param>
	<jsp:param name="jsFile" value="pda/prehuecos/pda_p116stockAlmacen.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP116StockAlmacen.do"></jsp:param>
</jsp:include>
<script type="text/javascript">
var pagActual = '${pagActual}';
var pagTotal = '${pagTotal}';	
</script>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<c:choose>
			  	<c:when test="${pagTotal > 0}">
			  		<div id="pda_p116_stockAlmacen_contenido">
						<form:form id="pdaP116StockAlmacen" method="post" action="pdaP116StockAlmacen.do" modelAttribute="pdaStockAlmacen">
							<div id="pda_p116_stockAlmacen_tituloComun">
								<div id="pda_p116_stockAlmacen_fecha">
									<div id="fechaInfo">
										<label id="pda_p116_lbl_fecha" class="etiquetaCampoNegrita"><spring:message code="pda_p116_stockAlmacen.fechaPrehueco" /></label>
									</div>
									<div id="fechaDatos">
										<div id="fechaValue">
											<label id="pda_p116_val_fecha" class="etiquetaCampoNegrita">${fechaStockAlmacen}</label>
										</div>
									</div>
								</div>
							</div>
							<div id="pda_p116_stockAlmacen_tituloLista">
								<div id="pda_p116_stockAlmacen_descripcionTabla">
									<label id="pda_p116_lbl_descripcionTabla" class="etiquetaCampoNegrita"><spring:message code="pda_p116_stockAlmacen.descripcionTabla" /></label>
								</div>
								<div id="pda_p116_stockAlmacen_stockTabla">
									<label id="pda_p116_lbl_stockTabla" class="etiquetaCampoNegrita"><spring:message code="pda_p116_stockAlmacen.stockTabla" /></label>
								</div>
							</div>
							
							<c:forEach items="${listaRef}" var="referencia">
							
								<div id="pda_p116_stockAlmacen_resultado">
									<div id="pda_p116_stockAlmacen_codigoArticulo">
										<a href="./pdaP118PrehuecosAlmacen.do?codArt=${referencia.codArt}&tipoListado=1&actionRef=pdaP116Paginar.do&pagActual=${pagActual}&pagTotal=${pagTotal}">${referencia.codArt}</a>
									</div>
									<div id="pda_p116_stockAlmacen_stock">
										<label id="pda_p116_lbl_stockLineal" class="etiquetaStockLineal">${referencia.stockLineal}</label><label id="pda_p116_lbl_stock" class="etiquetaCampoNegrita">/${referencia.stock}</label>
									</div>
									<div id="pda_p116_stockAlmacen_estado">
										<c:choose>
						  					<c:when test="${referencia.estado == 0}">
												<img src="./misumi/images/icono_aspa.png?version=${misumiVersion}" style="width:12px;height:12px;"> 
											</c:when>
											<c:otherwise>
												<img src="./misumi/images/icono_pendiente.png?version=${misumiVersion}" style="width:12px;height:12px;">
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div id="pda_p116_stockAlmacen_resultadoDescripcion">
									<a href="./pdaP118PrehuecosAlmacen.do?codArt=${referencia.codArt}&tipoListado=1&actionRef=pdaP116Paginar.do&pagActual=${pagActual}&pagTotal=${pagTotal}">${referencia.descArt}</a>
								</div>
							</c:forEach>
						</form:form>
					</div>
					<div id="pda_p116_stockAlmacen_areaFlechas">
						<div id="bloqueFlechas">
							<div id="pagPrimera" class="paginacion">
								<c:choose>
									<c:when test="${pagActual eq '1'}">
										<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP116Paginar.do?pagActual=1&pagTotal=${pagTotal}">
											<img id="pda_p116_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>
							</div>
							<div id="pagAnt" class="paginacion">
								<c:choose>
									<c:when test="${pagActual eq '1'}">
										<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP116Paginar.do?pagActual=${pagActual-1}&pagTotal=${pagTotal}">
											<img id="pda_p116_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="numeroPagina" class="paginacion">
								${pagActual}/${pagTotal}
							</div>
							<div id="pagSig" class="paginacion">
								<c:choose>
									<c:when test="${pagActual eq pagTotal}">
										<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP116Paginar.do?pagActual=${pagActual+1}&pagTotal=${pagTotal}">
											<img id="pda_p116_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="pagUltima" class="paginacion">
								<c:choose>
									<c:when test="${pagActual eq pagTotal}">
										<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP116Paginar.do?pagActual=${pagTotal}&pagTotal=${pagTotal}">
											<img id="pda_p116_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="pda_p116_noHayReferenciasError" class="mens_error">
						<spring:message code="pda_p116_stockAlmacen.errorNoReferencias"/>
					</div>
				</c:otherwise>
			</c:choose>
		</div>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>