<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p45_invLibCorreccionStockPCN.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p45InvLibCorrStockPCN.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
	<jsp:param value="pdaP45InvLibCorreccionStockPCN.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" action="pdaP45InvLibCorreccionStockPCN.do" commandName="pdaStock">
			<div class="pda_p45_bloqueDatos">
				<div id="pda_p45_correccionStock_pcn_titulo">
				<c:choose>
				<c:when test="${empty (pdaStock.origenGISAE)}">
					
						<label id="pda_p45_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p45_correccionStockPCN.titulo" /></label>
					
				</c:when>
				<c:otherwise>
					<label id="pda_p45_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p42_inventario.titulo" /></label>
				</c:otherwise>
				</c:choose>
				</div>
				<div id="pda_p45_correccionStock_pcn_titulo2">
					<c:choose>
						<c:when test="${pdaStock.origenInventario == 'CA'}">
						<label id="pda_p45_lbl_titulo2" class="etiquetaCampoNegrita"><spring:message code="pda_p45_correccionStockPCN.camaraAlmacen" /></label>
						</c:when>
						<c:otherwise>
						<label id="pda_p45_lbl_titulo2" class="etiquetaCampoNegrita"><spring:message code="pda_p45_correccionStockPCN.salaVenta" /></label>
						</c:otherwise>
					</c:choose>	
				</div>
				<c:choose>
					<c:when test="${pdaStock.totalArticulos > 4}">
						<div id="pda_p45_bloque1">
							<div id="pda_p45_bloque1_pagAnt">
								<c:choose>
									<c:when test="${pdaStock.posicionGrupoArticulos > 1}">
										<input type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
									</c:when>
									<c:otherwise>
										<!-- <input type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>  -->
									</c:otherwise>
								</c:choose>
							</div>	
							<div id="pda_p45_bloque1_pagNum">
								<p class="pda_p45_numPaginas"><spring:message code="pda_p45_correccionStockPCN.paginacion" arguments="${pdaStock.posicionGrupoArticulos},${pdaStock.totalPaginas}" /></p>
							</div>
							<div id="pda_p45_bloque1_pagSig">
								<c:choose>
									<c:when test="${pdaStock.totalPaginas > pdaStock.posicionGrupoArticulos}">
										<input type="submit" name="actionSig" value='' class='botonSubmitSig'/>
									</c:when>
									<c:otherwise>
										<!-- <input type="submit" name="actionSig" value='' class='botonSubmitSigDes' disabled="disabled"/>  -->
									</c:otherwise>
								</c:choose>
							</div>					
						</div>
					</c:when>
				</c:choose>
				<div id="pda_p45_bloqueError">
					<p id="pda_p45_mensajeError" class="pda_p45_error">${pdaStock.descripcionError}</p>
				</div>
				<div class="pda_p45_bloqueRegistro">
					<div id="pda_p45_reg_tipoCab" class="pda_p45_reg_tipoCab">
						<label id="pda_p45_lbl_tipoCab" class="etiquetaCampoNegrita"><spring:message code="pda_p45_correccionStockPCN.tipo" /></label>
					</div>
					<div id="pda_p45_reg_descripcionCab" class="pda_p45_reg_descripcionCab">
						<label id="pda_p45_lbl_descripcionCab" class="etiquetaCampoNegrita"><spring:message code="pda_p45_correccionStockPCN.descripcion" /></label>
					</div>
					<div id="pda_p45_reg_stockCab" class="pda_p45_reg_stockCab">
						<label id="pda_p45_lbl_stockCab" class="etiquetaCampoNegrita"><spring:message code="pda_p45_correccionStockPCN.stock" /></label>
					</div>
				</div>	
				<c:forEach var="articulo" items="${pdaStock.listaArticulosPagina}" varStatus="loopStatus">
					<div class="pda_p45_bloqueRegistro">
						<div class="pda_p45_reg_tipo">
							<label id="pda_p45_lbl_tipoVal${loopStatus.index}" class="valorCampo" >${articulo.tipo}</label>
						</div>
						<div class="pda_p45_reg_descripcion">
							<input name="listaArticulosPagina[${loopStatus.index}].descArt" id="pda_p45_lbl_descripcion${loopStatus.index}" class="input135 inputSinBorde" value="${articulo.descripcionCompleta}" readonly/>
						</div>
						<div class="pda_p45_reg_stock">
							<c:choose>
								<c:when test="${'WN' != pdaStock.codigoError}">
									<input type="number" step="0.001" name="listaArticulosPagina[${loopStatus.index}].stock" id="pda_p45_fld_stock_${loopStatus.index}" class="input50 pda_p45_fld_stock" value="${articulo.stock}"/>
								</c:when>
								<c:otherwise>
									<input name="listaArticulosPagina[${loopStatus.index}].stock" id="pda_p45_fld_stock_${loopStatus.index}" class="input50 pda_p45_fld_stock" value="${articulo.stock}" disabled="disabled"/>
								</c:otherwise>
							</c:choose>
							<input id="pda_p45_fld_stock_${loopStatus.index}_tmp" type="hidden" value="${articulo.stock}"/>
						</div>
					</div>
				</c:forEach>
			</div>
			<div id="pda_p45_filterButtons">
				<c:choose>
					<c:when test="${'WN' != pdaStock.codigoError}">
						<input type="submit" id="pda_p45_btn_save"  class="botonSubmitGrabar" name="actionSave" value=''/>
					</c:when>
				</c:choose>
			</div>		
			<input type="hidden" name="posicion" value="${pdaStock.posicionGrupoArticulos}">
			<input type="hidden" name="origenGISAE" value="${pdaStock.origenGISAE}">
			<input type="hidden" name="noGuardar" value="${pdaStock.noGuardar}">
			<input type="hidden" name="seccion" value="${pdaStock.seccion}">
			<input type="hidden" id="pda_p45_fld_tipoMensaje" name="tipoMensaje" value="${pdaStock.tipoMensaje}">
			<input type="hidden" id="origenInventario" name="origenInventario"value="${pdaStock.origenInventario}">
			<input type="hidden" id="pda_p45_introSubmit" name="pda_p45_introSubmit" value="">
			</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>