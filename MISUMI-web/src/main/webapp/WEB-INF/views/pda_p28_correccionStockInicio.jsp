<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda_p28_correccionStockInicio.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda_p28correccionStockInicio.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP28CorreccionStockInicio.do"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p28_correccionStockInicio_titulo">
				<label id="pda_p28_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p28_correccionStockInicio.titulo" /></label>
			</div>
			<div id="pda_p28_correccionStockInicio_bloque1">
				<p class="etiquetaCampoVerticalNegrita"><spring:message code="pda_p28_correccionStockInicio.mensaje" /></p>
			</div>		
			<div id="pda_p28_filterButtons">
				<form:form method="post" action="pdaP28CorreccionStockInicio.do" commandName="user">
					<input type="submit" id="pda_p28_btn_si" name="actionYes" class="botonAceptar" value='' title="<spring:message code="pda_p28_correccionStockInicio.siTitle" />"/>
					<input type="submit" id="pda_p28_btn_no" name="actionNo" class="botonCancelar" value='' title="<spring:message code="pda_p28_correccionStockInicio.noTitle" />"/>
					<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">
					<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
					<input type="hidden" id="procede" name="procede" value="${procede}">
				</form:form>
			</div>
		</div>
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>