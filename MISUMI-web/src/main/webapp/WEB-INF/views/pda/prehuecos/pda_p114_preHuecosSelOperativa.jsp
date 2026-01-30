<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda/prehuecos/pda_p114_prehuecosSelOperativa.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP114PreHuecosSelOperativa.do"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p114_prehuecosSelOperativa_titulo">
				<label id="pda_p114_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p114_prehuecosSelOperativa.titulo" /></label>
			</div>
			<div id="pda_p114_prehuecosSelOperativa_bloque1">
				<label id="pda_p114_lbl_opcion" class="etiquetaCampoNegrita" style="display:inline-block"><spring:message code="pda_p41_invLibSelOperativa.opcion" /></label>
			</div>		
			<div id="pda_p114_prehuecosSelOperativa_bloque2">
				<label id="pda_p114_lbl_opc1" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p41_invLibSelOperativa.opc1" /></label>
				<a href="./pdaP114PrehuecosSelOperativa.do?operativa=E"><spring:message code="pda_p114_prehuecosSelOperativa.empezarDesdeCero" /></a>
			</div>
			<div id="pda_p114_prehuecosSelOperativa_bloque3">
				<label id="pda_p114_lbl_opc2" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p41_invLibSelOperativa.opc2" /></label>
				<a href="./pdaP114PrehuecosSelOperativa.do?operativa=C"><spring:message code="pda_p114_prehuecosSelOperativa.continuar" /></a>
			</div>
			<div id="pda_p114_filterButtons">
				<form:form method="post" action="pdaP41InvLibSelOperativa.do" commandName="user">
					<input type="submit" id="pda_p41_btn_prev" class="boton" value='<spring:message code="pda_p41_invLibSelOperativa.atras" />' title="<spring:message code="pda_p41_invLibSelOperativa.atrasTitulo" />"/>
				</form:form>	
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>