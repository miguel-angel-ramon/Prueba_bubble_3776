<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda_p98_datosReferenciaSelOperativa.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda_p98_datosReferenciaSelOperativa.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP41InvLibSelOperativa.do"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p98_DatosReferenciaSelOperativa_titulo">
				<label id="pda_p98_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p98_DatosReferenciaSelOperativa.titulo" /></label>
			</div>
			<div id="pda_p98_DatosReferenciaSelOperativa_bloque1">
				<label id="pda_p98_lbl_opcion" class="etiquetaCampoNegrita" style="display:inline-block"><spring:message code="pda_p98_DatosReferenciaSelOperativa.opcion" /></label>
			</div>		
			<div id="pda_p98_DatosReferenciaSelOperativa_bloque2">
				<label id="pda_p98_lbl_opc1" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p98_DatosReferenciaSelOperativa.opc1" /></label>
				<a href="./pdaP115PrehuecosSelOperativa.do?operativa=E"><spring:message code="pda_p98_DatosReferenciaSelOperativa.empezarDesdeCero" /></a>
			</div>
			<div id="pda_p98_DatosReferenciaSelOperativa_bloque3">
				<label id="pda_p98_lbl_opc2" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p98_DatosReferenciaSelOperativa.opc2" /></label>
				<a href="./pdaP115PrehuecosSelOperativa.do?operativa=C"><spring:message code="pda_p98_DatosReferenciaSelOperativa.continuar" /></a>
			</div>
			<div id="pda_p98_filterButtons">
			<form method="post" action="" id="pda98datosReferenciaSelOperativa">
					<input type="button" id="pda_p98_btn_prev" class="boton" value='<spring:message code="pda_p98_DatosReferenciaSelOperativa.atras" />' title="<spring:message code="pda_p98_DatosReferenciaSelOperativa.atrasTitulo" />"/>
			</form>	
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>