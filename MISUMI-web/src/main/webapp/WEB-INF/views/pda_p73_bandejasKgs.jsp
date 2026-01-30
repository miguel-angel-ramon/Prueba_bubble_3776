<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p73_bandejasKgs.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p73_bandejasKgs.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP73BandejasKgs.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<form:form id="p73_form" method="post" action="pdaP73BandejasKgs.do" commandName="pdaArticulo">
			<div id="pda_p73_bandejasKgs_titulo">
				<label id="pda_p73_bandejasKgs_lbl_titulo" class="etiquetaCampoNegrita"><spring:message code="pda_p73_bandejasKgs.titulo" /></label>
			</div>
			<div id="pda_p73_bandejasKgs_bloqueError">
				<p id="pda_p73_bandejasKgs_mensajeError" class="pda_p73_colorMsgRed">${pdaArticulo.descripcionError}</p>
				<p id="pda_p73_bandejasKgs_mensajeError2" style="display:none" class="pda_p73_colorMsgRed"><spring:message code="pda_p73_bandejasKgs.error" /></p>
			</div>

			<div class="pda_p73_bandejasKgs_bloqueRegistro">
				<div id="pda_p73_bandejasKgs_descripcionCab" class="pda_p73_bandejasKgs_descripcionCab">
					<label id="pda_p73_bandejasKgs_lbl_descripcion" class="etiquetaCampoNegrita"><spring:message code="pda_p73_bandejasKgs.descripcion" /></label>
				</div>
				<div id="pda_p73_bandejasKgs_bandejasCab" class="pda_p73_bandejasKgs_bandejasCab">
					<label id="pda_p73_bandejasKgs_lbl_bandejas" class="etiquetaCampoNegrita"><spring:message code="pda_p73_bandejasKgs.bandejas" /></label>
				</div>
				<div id="pda_p73_bandejasKgs_stockCab" class="pda_p73_bandejasKgs_stockCab">
					<label id="pda_p73_bandejasKgs_lbl_stock" class="etiquetaCampoNegrita"><spring:message code="pda_p73_bandejasKgs.kgs" /></label>
				</div>
			</div>
			<div class="pda_p73_bandejasKgs_bloqueRegistro">
				<div id="pda_p73_bandejasKgs_descripcion" class="pda_p73_bandejasKgs_descripcion">
					<input id="pda_p73_bandejasKgs_fld_descripcionCompleta" value="${descCodArt}" class="input90 inputSinBorde" type="text" disabled="disabled"/>
				</div>
				
				<div id="pda_p73_reg_bandejas" class="pda_p73_reg_bandejas">
					<c:choose>
						<c:when test="${'WN' != pdaArticulo.codigoError}">
							<form:input path="bandejas" id="pda_p73_bandejasKgs_fld_bandejas" class="input50" type="number" onkeypress="return isNumberKey(event)"/>
						</c:when>
						<c:otherwise>
							<input id="pda_p73_bandejasKgs_fld_bandejas" class="input50" type="text" value="${pdaArticulo.bandejas}" disabled="disabled"/>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p73_reg_kgs" class="pda_p73_reg_kgs">
					<c:choose>
						<c:when test="${'WN' != pdaArticulo.codigoError}">
							<form:input path="kgs" id="pda_p73_bandejasKgs_fld_kgs" class="input50" type="number"/>
						</c:when>
						<c:otherwise>
							<input id="pda_p73_bandejasKgs_fld_kgs" class="input50" type="text" value="${pdaArticulo.kgs}" disabled="disabled"/>
						</c:otherwise>
					</c:choose>
				</div>
						
				
			</div>
		
			<div id="pda_p73_bandejasKgs_filterButtons">
				<c:choose>
					<c:when test="${'WN' != pdaArticulo.codigoError}">
						<input type="button" id="pda_p73_bandejasKgs_btn_guardar" class="botonSubmitGrabar operacionDefecto" value=''/>
					</c:when>
				</c:choose>
			</div>
			<input type="hidden" id="pda_p73_bandejasKgs_fld_bandejas_tmp" name="pda_p73_bandejasKgs_fld_bandejas_tmp" value="${pdaArticulo.bandejas}">
			<input type="hidden" id="pda_p73_bandejasKgs_fld_kgs_tmp" name="pda_p73_bandejasKgs_fld_kgs_tmp" value="${pdaArticulo.kgs}">
			<input type="hidden" id="pda_p73_bandejasKgs_variosBultos" name="pda_p73_bandejasKgs_variosBultos" value="${pdaArticulo.variosBultos}">
			<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">				
			<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
		</form:form>
		<input type="hidden" id="pda_p73_cantMaximaLin"  value="${cantMaximaLin}">
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>