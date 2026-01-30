<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p100_montajeAdicionalMAC.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p100_montajeAdicionalMAC.js?version=${misumiVersion}" name="jsFile"></jsp:param> 
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP100MontajeAdicionalMAC.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p100_datosReferencia_bloqueCapacidad">
				<div id="pda_p100_datosReferencia_capacidadTitle_div">
					<label id="pda_p100_lbl_capacidad" class="etiquetaCampoNegrita"><spring:message code="pda_p100_montajeAdicionalMAC.capacidad" /></label>
					<input id="pda_p100_lbl_capacidadVal" name="capacidadMAC" class="input75" type="text" disabled="disabled" value="${capacidad}"/>
				</div>
			</div>
			<div id="pda_p100_datosReferencia_bloqueOferta">
				<div id="pda_p100_datosReferencia_ofertaTitle_div">
					<label id="pda_p100_lbl_oferta" class="etiquetaCampoNegrita"><spring:message code="pda_p100_montajeAdicionalMAC.oferta" /></label>
					<label id="pda_p100_lbl_ofertaVal" class="valorCampo" >${oferta}</label>	
				</div>
			</div>
			<div id="pda_p100_datosReferencia_bloqueEspacioProm">
				<div id="pda_p100_datosReferencia_espacioPromTitle_div">
					<label id="pda_p100_lbl_espacioProm" class="etiquetaCampoNegrita"><spring:message code="pda_p100_montajeAdicionalMAC.espacioProm" /></label>
					<label id="pda_p100_lbl_espacioPromVal" class="valorCampo" >${espacioProm}</label>	
				</div>
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>