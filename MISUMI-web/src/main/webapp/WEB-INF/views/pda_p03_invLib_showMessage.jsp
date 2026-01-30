<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p03_invLib_showMessage.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p03InvLibShowMessage.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP03InvLibShowMessage" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
				<div id="pda_p03_error_bloque1" class="mens_error">
					<spring:bind path="pdaError.descError">${status.value}</spring:bind>
				</div>	
				<div id="pda_p03_filterButtons">
					<form method="post" id="pdaP42InventarioLibre" action="pdaP42InventarioLibre.do" >
						<input type="submit" id="pda_p03_btn_aceptar"  class="botonAceptar" value=''/>
						<input type="hidden" id="pda_p42_fld_codArtCab" name="pda_p42_fld_codArtCab" value="">
						<input type="hidden" id="pda_p42_fld_seccion" name="pda_p42_fld_seccion" value="">
						<input type="hidden" id="actionLogin" name="actionLogin" value="">
						<input type="hidden" id="actionNuevo" name="actionNuevo" value="">
						<input type="hidden" id="actionSeccion" name="actionSeccion" value="">
						<input type="hidden" id="actionVolver" name="actionVolver" value="">
					</form>
				</div>	
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>