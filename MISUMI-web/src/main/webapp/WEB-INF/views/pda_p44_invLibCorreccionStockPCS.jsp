<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p44_invLibCorreccionStockPCS.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p44InvLibCorrStockPCS.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP44InvLibCorreccionStockPCS.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<form:form method="post" id="pdaP44InvLibCorreccionStockPCS" action="pdaP44InvLibCorreccionStockPCS.do" commandName="pdaArticulo">
			<div id="pda_p44_correccionStock_pcs_titulo">
			<c:choose>
				<c:when test="${empty (pdaArticulo.origenGISAE)}">
					
						<label id="pda_p44_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p44_correccionStockPCS.titulo" /></label>
					
				</c:when>
				<c:otherwise>
					<label id="pda_p44_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p42_inventario.titulo" /></label>
				</c:otherwise>
				</c:choose>
			</div>
			<c:choose>
				<c:when test="${pdaArticulo.origenInventario == 'CA'}">
				<label id="pda_p44_lbl_titulo2" class="etiquetaCampoNegrita"><spring:message code="pda_p44_correccionStockPCS.camaraAlmacen" /></label>
				</c:when>
				<c:otherwise>
				<label id="pda_p44_lbl_titulo2" class="etiquetaCampoNegrita"><spring:message code="pda_p44_correccionStockPCS.salaVenta" /></label>
				</c:otherwise>
			</c:choose>	
			</div>
			<div id="pda_p44_bloqueError">
				<p id="pda_p44_mensajeError" class="pda_p44_error">${pdaArticulo.descripcionError}</p>
			</div>

			<div class="pda_p44_bloqueRegistro">
				<div id="pda_p44_reg_descripcionCab" class="pda_p44_reg_descripcionCab">
					<label id="pda_p44_lbl_descripcion" class="etiquetaCampoNegrita"><spring:message code="pda_p44_correccionStockPCS.descripcion" /></label>
				</div>
				<div id="pda_p44_reg_bandejasCab" class="pda_p44_reg_bandejasCab">
					<label id="pda_p44_lbl_bandejas" class="etiquetaCampoNegrita"><spring:message code="pda_p44_correccionStockPCS.bandejas" /></label>
				</div>
				<div id="pda_p44_reg_stockCab" class="pda_p44_reg_stockCab">
					<label id="pda_p44_lbl_stock" class="etiquetaCampoNegrita"><spring:message code="pda_p44_correccionStockPCS.stock" /></label>
				</div>
			</div>
			<div class="pda_p44_bloqueRegistro">
				<div id="pda_p44_reg_descripcion" class="pda_p44_reg_descripcion">
					<form:input path="descripcionCompleta" id="pda_p44_fld_descripcionCompleta" class="input90 inputSinBorde" type="number"/>
				</div>
				<div id="pda_p44_reg_bandejas" class="pda_p44_reg_bandejas">
					<c:choose>
						<c:when test="${'WN' != pdaArticulo.codigoError}">
							<form:input path="unidades" id="pda_p44_fld_bandejas" class="input50" type="number" onkeypress="return isNumberKey(event)"/>
						</c:when>
						<c:otherwise>
							<input id="pda_p44_fld_bandejas" class="input50" type="text" value="${pdaArticulo.unidades}" disabled="disabled"/>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p44_reg_stock" class="pda_p44_reg_stock">
					<c:choose>
						<c:when test="${'WN' != pdaArticulo.codigoError}">
							<form:input path="stock" id="pda_p44_fld_stock" class="input50" type="number"/>
						</c:when>
						<c:otherwise>
							<input id="pda_p44_fld_stock" class="input50" type="text" value="${pdaArticulo.stock}" disabled="disabled"/>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		
			<div id="pda_p44_filterButtons">
				<c:choose>
					<c:when test="${'WN' != pdaArticulo.codigoError}">
						<input type="submit" id="pda_p44_btn_guardar" name="actionSave" class="botonSubmitGrabar operacionDefecto" value=''/>
					</c:when>
				</c:choose>
			</div>
			<input type="hidden" id="pda_p44_fld_bandejas_tmp" name="pda_p44_fld_bandejas_tmp" value="${pdaArticulo.unidades}">
			<input type="hidden" id="pda_p44_fld_stock_tmp" name="pda_p44_fld_stock_tmp" value="${pdaArticulo.stock}">
			<input type="hidden" id="pda_p44_fld_precio" name="pda_p44_fld_precio" value="${pdaArticulo.precio}">
			<input type="hidden" id="pda_p44_fld_precio_kg" name="pda_p44_fld_precio_kg" value="${pdaArticulo.precioKg}">
			<input type="hidden" id="pda_p44_fld_kgs" name="pda_p44_fld_kgs" value="${pdaArticulo.kgs}">
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>