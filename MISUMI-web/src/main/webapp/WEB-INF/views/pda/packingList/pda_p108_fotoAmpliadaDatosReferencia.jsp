<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p20_FotoAmpliadaDatosReferencia.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p20_FotoAmpliadaDatosReferencia.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP20FotoAmpliadaDatosReferencia.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido p�gina -->
		<div id="contenidoPagina">
			<div id="pda_p20_estructura_contenido">
				<div>
					<label id="pda_p20_fotoAmpliada_estructura" class="etiquetaCampoNegrita"><spring:message code="pda_p20_fotoAmpliada.estructura" /></label>
					<label id="pda_p20_fld_fotoAmpliada_estructura" class="valorCampo" >${estructura}</label>	
				</div>	
				<div>
					<label id="pda_p20_fotoAmpliada_tipoReferencia" class="etiquetaCampoNegrita"><spring:message code="pda_p20_fotoAmpliada.tipoReferencia" /></label>
					<label id="pda_p20_fld_fotoAmpliada_tipoReferencia" class="valorCampo" >${tipoReferencia}</label>	
				</div>				
			</div>						
			<div id="pda_p20_fotoAmpliada_contenido">
				<c:if test="${tieneFoto == 'S'}">
					<div id="pda_p20_fotoAmpliada_foto">						
						<img id="pda_p20_img_referencia" src="./pdaGetImageP20DatosReferencia.do?codArticulo=${codArticuloFoto}"/>
					</div>
				</c:if>	
				
				
				<form id="pda_p20_fotoAmpliadaDatosReferencia_form" action="javascript:history.back()" method="post">
						<input type="hidden" id="matricula" name="matricula" value="${matricula}">
						<input type="hidden" id="pgPalet" name="pgPalet" value="${pgPalet}">
						<input type="hidden" id="pgTotPalet" name="pgTotPalet" value="${pgTotPalet}">	
						
								
				</form>	
				
				
				
			
			</div>
			
		</div>