<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p30_correccionStockPCS.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p30CorrStockPCS.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP30CorrStockPCS.do" name="actionRef"></jsp:param>
</jsp:include>
<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<form:form method="post" id="p30_form" action="pdaP30CorrStockPCS.do" commandName="pdaArticulo">
			<div id="pda_p30_correccionStock_pcs_titulo">
				<label id="pda_p30_lbl_titulo" class="etiquetaCampoNegrita"><spring:message code="pda_p30_correccionStockPCS.titulo" /></label>
			</div>
			<div id="pda_p30_bloqueError">
				<label id="pda_p30_mensajeError" class="pda_p30_error">${pdaArticulo.descripcionError}</label>
				<label id="pda_p30_mensajeAviso" class="pda_p30_aviso" style="display:none"><spring:message code="pda_p30_correccionStockPCS.excedido.limite" /></label>
			</div>	
			<fmt:parseNumber var="uni" type="number" value="${pdaArticulo.unidades}"/>
			<fmt:parseNumber var="stk" type="number" value="${pdaArticulo.stock}"/>
			<c:choose>
				<c:when test="${'S' == pdaArticulo.pesoVariable}">
					<div id="pda_p30_bloqueMensaje">
						<label id="pda_p30_lbl_mensaje" class="etiquetaCampoNegrita">
							<spring:message code="pda_p30_correccionStockPCS.mensaje" />
						</label>
					</div>
				</c:when>
			</c:choose>
			<div class="pda_p30_bloqueRegistro">
				<div id="pda_p30_reg_descripcionCab" class="pda_p30_reg_descripcionCab">
					<label id="pda_p30_lbl_descripcion" class="etiquetaCampoNegrita"><spring:message code="pda_p30_correccionStockPCS.descripcion" /></label>
				</div>
				<div id="pda_p30_reg_bandejasCab" class="pda_p30_reg_bandejasCab">
					<label id="pda_p30_lbl_bandejas" class="etiquetaCampoNegrita"><spring:message code="pda_p30_correccionStockPCS.bandejas" /></label>
				</div>
				<div id="pda_p30_reg_stockCab" class="pda_p30_reg_stockCab">
					<label id="pda_p30_lbl_stock" class="etiquetaCampoNegrita"><spring:message code="pda_p30_correccionStockPCS.stock" /></label>
				</div>
			</div>
			<div class="pda_p30_bloqueRegistro">
				<div id="pda_p30_reg_descripcion" class="pda_p30_reg_descripcion">
					<form:input path="descripcionCompleta" id="pda_p30_fld_descripcionCompleta" class="input90 inputSinBorde" type="text"/>
				</div>
				<div id="pda_p30_reg_bandejas" class="pda_p30_reg_bandejas">
					<c:choose>
						<c:when test="${'WN' != pdaArticulo.codigoError}">
							<form:input path="unidades" id="pda_p30_fld_bandejas" class="input50" type="number" onkeypress="return isNumberKey(event)"/>
						</c:when>
						<c:otherwise>
							<input id="pda_p30_fld_bandejas" class="input50" type="text" value="${pdaArticulo.unidades}" disabled="disabled"/>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p30_reg_stock" class="pda_p30_reg_stock">
					<c:choose>
						<c:when test="${'WN' != pdaArticulo.codigoError}">
							<form:input path="stock" id="pda_p30_fld_stock" class="input50" type="number" step="0.01"/>
						</c:when>
						<c:otherwise>
							<input id="pda_p30_fld_stock" class="input50" type="number" step="0.01" value="${pdaArticulo.stock}" disabled="disabled"/>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		
			<div id="pda_p30_filterButtons">
				<c:choose>
					<c:when test="${'WN' != pdaArticulo.codigoError}">
						<input type="" id="pda_p30_btn_guardar" name="actionSave" class="botonSubmitGrabar operacionDefecto" value=''/>
					</c:when>
				</c:choose>
			</div>
			<input type="hidden" id="pda_p30_fld_bandejas_tmp" name="pda_p30_fld_bandejas_tmp" value="${pdaArticulo.unidades}">
			<input type="hidden" id="pda_p30_fld_stock_tmp" name="pda_p30_fld_stock_tmp" value="${pdaArticulo.stock}">
			<input type="hidden" id="pda_p30_fld_precio" name="pda_p30_fld_precio" value="${pdaArticulo.precio}">
			<input type="hidden" id="pda_p30_fld_precio_kg" name="pda_p30_fld_precio_kg" value="${pdaArticulo.precioKg}">
			<input type="hidden" id="pda_p30_fld_kgs" name="pda_p30_fld_kgs" value="${pdaArticulo.kgs}">
			<input type="hidden" id="pda_p30_fld_peso_variable" name="pda_p30_fld_peso_variable" value="${pdaArticulo.pesoVariable}">
			<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">
			<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
			<input type="hidden" id="procede" name="procede" value="${procede}">

		</form:form>
		
		<input type="hidden" id="pda_p30_fld_bandejas_tmpOri"value="${pdaArticulo.unidades}">
		<input type="hidden" id="pda_p30_esCaprabo" value = "${user.centro.esCentroCaprabo || user.centro.esCentroCapraboEspecial}">
		<input type="hidden" id="pda_p30_msgExcedido" value="<spring:message code="pda_p30_msg.excedido"/>">
			
		<input type="hidden" id="pda_p30_origen" value="${pdaArticulo.origen}">
	</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>