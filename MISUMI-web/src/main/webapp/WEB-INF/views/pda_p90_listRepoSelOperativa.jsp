<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p90_listRepoSelOperativa.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP90ListRepoSelOperativa.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p90_listRepoSelOperativa_titulo">
				<label id="pda_p90_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p90_listRepoSelOperativa.titulo" /></label>
			</div>
			<div id="pda_p90_listRepoSelOperativa_bloque1">
				<label id="pda_p90_lbl_opcion" class="etiquetaCampoNegrita" style="display:inline-block"><spring:message code="pda_p90_listRepoSelOperativa.opcion" /></label>
			</div>		
			<div id="pda_p90_listRepoSelOperativa_bloque2">
				<label id="pda_p90_lbl_opc1" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p90_listRepoSelOperativa.opc1" /></label>
				<a href="pdaP91ListadoRepoPaginar.do?operativaCero='S'&area=${reposicion.area}"><spring:message code="pda_p90_listRepoSelOperativa.empezarDesdeCero" /></a>			
			</div>
			<div id="pda_p90_listRepoSelOperativa_bloque3">
				<label id="pda_p90_lbl_opc2" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p90_listRepoSelOperativa.opc2" /></label>
				<a href="pdaP91ListadoRepoPaginar.do?area=${reposicion.area}"><spring:message code="pda_p90_listRepoSelOperativa.continuar" /></a>
			</div>
			<div id="pda_p90_filterButtons">
			<form:form method="post" action="pdaP90ListRepoSelOperativa.do" commandName="user">
					<input type="submit" id="pda_p90_btn_prev" class="boton" value='<spring:message code="pda_p90_listRepoSelOperativa.atras" />' title="<spring:message code="pda_p90_listRepoSelOperativa.atrasTitulo" />"/>
			</form:form>	
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>