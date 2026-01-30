<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p101_montajeAdicionalMA.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p101_montajeAdicionalMA.js?version=${misumiVersion}" name="jsFile"></jsp:param> 
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP101MontajeAdicionalMA.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p101_datosReferencia_bloqueCapacidad">
				<div id="pda_p101_datosReferencia_capacidadTitle_div">
					<label id="pda_p101_lbl_capacidad" class="etiquetaCampoNegrita"><spring:message code="pda_p101_montajeAdicionalMA.capacidad" /></label>
					<input id="pda_p101_lbl_capacidadVal" name="capacidadMA" class="input75" type="text" disabled="disabled" value="${capacidad}"/>
				</div>
			</div>
			<div id="pda_p101_datosReferencia_bloqueOferta">
				<div id="pda_p101_datosReferencia_ofertaTitle_div">
					<label id="pda_p101_lbl_oferta" class="etiquetaCampoNegrita"><spring:message code="pda_p101_montajeAdicionalMA.oferta" /></label>
					<label id="pda_p101_lbl_ofertaVal" class="valorCampo" >${oferta}</label>
				</div>	
			</div>
			<div id="pda_p101_datosReferencia_bloqueFechaInicio">
				<div id="pda_p101_datosReferencia_fechaInicioTitle_div">
					<label id="pda_p101_lbl_fechaInicio" class="etiquetaCampoNegrita"><spring:message code="pda_p101_montajeAdicionalMA.fechaInicio" /></label>
					<label id="pda_p101_lbl_fechaInicioVal" class="valorCampo" >${fechaInicio}</label>	
				</div>
			</div>
			<div id="pda_p101_datosReferencia_bloqueFechaFin">
				<div id="pda_p101_datosReferencia_fechaFinTitle_div">
					<label id="pda_p101_lbl_fechaFin" class="etiquetaCampoNegrita"><spring:message code="pda_p101_montajeAdicionalMA.fechaFin" /></label>
					<label id="pda_p101_lbl_fechaFinVal" class="valorCampo" >${fechaFin}</label>	
				</div>
			</div>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>