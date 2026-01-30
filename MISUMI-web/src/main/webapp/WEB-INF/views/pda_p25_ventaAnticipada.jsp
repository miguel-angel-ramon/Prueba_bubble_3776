<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p25_ventaAnticipada.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p25VentaAnticipada.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP25VentaAnticipada.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		
		<div id="contenidoPagina">
		
			<div id="pda_p25_ventaAnticipada_titulo">
				<label id="pda_p25_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p25_ventaAnticipada.titulo" /></label>
			</div>
			<c:choose>
				<c:when test="${not empty (pdaVentaAnt)}">
					<form:form method="post" action="pdaP25VentaAnticipada.do" commandName="pdaVentaAnt">
					<div id="pda_p25_ventaAnticipada_bloqueMensajes">
						<c:choose>
						<c:when test="${not empty (pdaVentaAnt.esError)}">
							<div id="pda_p25_ventaAnticipada_bloqueError">
								<p class="pda_p25_error">${pdaVentaAnt.esError}</p>
							</div>
						</c:when>	
						<c:when test="${not empty (pdaVentaAnt.guardadoCorrecto)}">
							<div id="pda_p25_ventaAnticipada_bloqueGuardado">
								<p class="pda_p25_guardado"><spring:message code="pda_p25_ventaAnticipada.messageGuardado" /></p>
							</div>		
						</c:when>
						</c:choose>	
					</div>
					<div id="pda_p25_ventaAnticipada_bloque1">
						<div id="pda_p21_sfm_bloque1_campo">	
							<form:input path="descArtConCodigo" id="pda_p21_fld_descripcionRef" class="input225" disabled="true" type="text"/>
						</div>	
					</div>	
					<div id="pda_p25_ventaAnticipada_bloque2">
						<div id="textBox">
						<label id="pda_p23_lbl_opc1"class="etiquetaEnlaceDeshabilitado" ><spring:message code="pda_p25_ventaAnticipada.mensaje" arguments="${pdaVentaAnt.fecha}" /></label>
						</div>
					</div>
					<div id="pda_p25_ventaAnticipada_bloque3">
						<div id="pda_p25_ventaAnticipada_label">
							<label id="pda_p25_lbl_unidades" class="etiquetaCampoNegrita"><spring:message code="pda_p25_ventaAnticipada.unidades" /></label>
						</div>
						<div id="pda_p25_ventaAnticipada_campo">	
							<form:input path="cantidad" id="pda_p25_ventaAnticipada_unidades" class="input50 controlReturn" type="number" step="0.001"/>
							<form:input path="codArt" type="hidden" />
							<form:input path="descArt" type="hidden" />
							<form:input path="existe" type="hidden" />
							<form:input path="flgEnvio" type="hidden" />
							<form:input path="fechaGen" type="hidden" />
							<form:input path="fecha" type="hidden" />
						</div>	
					</div>		
					<div id="pda_p25_filterButtons">
						<c:if test="${user.perfil != 3}">
							<input type="submit" id="pda_p25_btn_save"  class="botonSubmitGrabar" name="actionSave" value=''/>
						</c:if>
					</div>
					</form:form>
				</c:when>
			</c:choose>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>