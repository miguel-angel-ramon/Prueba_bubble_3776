<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p23_limpiarSfmCapacidad.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP21Sfm.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p23_limpiarSfmCapacidad_bloque1">
				<label id="pda_p23_lbl_opcion" class="etiquetaCampoNegrita" style="display:inline-block"><spring:message code="pda_p23_limpiarSfmCapacidad.opcion" /></label>
			</div>		
			<div id="pda_p23_limpiarSfmCapacidad_bloque2">
				<label id="pda_p23_lbl_opc1" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p23_limpiarSfmCapacidad.opc1" /></label>
				<a href="./pdaP23Limpiar.do?cleanAll=N&posicion=${pdaDatosSfmCap.posicion}"><spring:message code="pda_p23_limpiarSfmCapacidad.limpRef" /></a>
			</div>
			<div id="pda_p23_limpiarSfmCapacidad_bloque3">
				<label id="pda_p23_lbl_opc2" class="etiquetaCampo" style="display:inline-block"><spring:message code="pda_p23_limpiarSfmCapacidad.opc2" /></label>
				<a href="./pdaP23Limpiar.do?cleanAll=Y&posicion=${pdaDatosSfmCap.posicion}"><spring:message code="pda_p23_limpiarSfmCapacidad.limpTodasRef" /></a>
				
			</div>
			<div id="pda_p23_filterButtons">
			<form:form method="post" action="pdaP23Limpiar.do" commandName="user">
					<input type="submit" id="pda_p23_btn_prev" class="boton" value='<spring:message code="pda_p23_limpiarSfmCapacidad.atras" />' title="<spring:message code="pda_p23_limpiarSfmCapacidad.atrasTitulo" />"/>
					<input type="hidden" name="posicion" value="${pdaDatosSfmCap.posicion}">
			</form:form>	
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>