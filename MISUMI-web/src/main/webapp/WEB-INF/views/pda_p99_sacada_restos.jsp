<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp">

	<jsp:param value="pda_p99_sacada_restos.css?version=${misumiVersion}"
		name="cssFile"></jsp:param>
	<jsp:param value="pda_p12_datosReferencia.css?version=${misumiVersion}"
		name="otroCssFile"></jsp:param>
	<jsp:param value="pda_p99SacadaRestos.js?version=${misumiVersion}"
		name="jsFile"></jsp:param>
	<jsp:param value="pdaP99SacadaRestos.do" name="actionRef"></jsp:param>
	<jsp:param value="S" name="flechaMenu"></jsp:param>
</jsp:include>

<%-- METODO --%>
<c:set var = "esPlanogramaRef" value = "1"/>
<c:set var = "esSfmRef" value = "2"/>
<c:set var = "esFacCapRef" value = "3"/>
<c:set var = "esCapRef" value = "4"/>
<c:set var = "esFacNoAliRef" value = "5"/>
					
<%-- TIPO REFERENCIA --%>
<c:set var = "esCajaExpositoraRef" value = "1"/>
<c:set var = "esMadreRef" value = "2"/>
<c:set var = "esFFPPRef" value = "3"/>
		
<c:set var = "imc" value = "${pageData.sacadaResto.imagenComercial}"/>
<c:set var = "data" value = "${pageData.sacadaResto}"/>

<!--  Contenido página -->
<div id="contenidoPagina">

	<div id="pda_p99_sacadaRestos_titulo">
		<label class="etiquetaEnlaceDeshabilitado">
			<spring:message code="pda_p99_sacadaRestos.titulo" />
		</label>
	</div>
	<c:if test="${pageData.totalPages > 0 }">
		
		<div id="pda_p99_datosReferencia_bloqueReferencia">
		
			<!-- Si se ha de mostrar rotacion -->
			<c:set var="mostrarRotacion" value="false"/>
			<!-- Clase a usar en el input descArtConCodigo en caso de que se muestre o no rotacion -->
			<c:set var="descripRefClass" value="input225"/>
			<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '25_PDA_ROTACION')) && !empty data.rotacion}">
				<c:set var="mostrarRotacion" value="true"/>
				<c:set var="descripRefClass" value="input180"/>
			</c:if>
			
			<div id="pda_p99_bloque2_descReferencia" onclick="javascript:events_p99_foto();">
				<input id="pda_p99_fld_descripcionRef" name="descArtConCodigo" class="${mostrarRotacion ? 'input180' : 'input225'} linkInput" type="text" value="${data.codArt}-${data.descripArt}" readonly>
			
				<input id="pda_p99_codArt" type="hidden" value="${data.codArt}"/>
				<input id="pda_p99_tieneFoto" type="hidden" value="${pageData.tieneFoto}"/>
				
			</div>
			<c:if test="${mostrarRotacion}">
				<!-- Si se ha de mostrar rotacion-->
				<div id="pda_p99_bloque2_tipoRot">
					<c:choose>
						<c:when test="${data.rotacion=='AR' || data.rotacion=='MR' || data.rotacion=='BR' }">
							<input id="pda_p99_fld_tipoRotacion" class="pda_p99_tipoRotacion${data.rotacion}" disabled="disabled" type="text" value="${data.rotacion}"/>
						</c:when>
						<c:otherwise>
							<input id="pda_p99_fld_tipoRotacion" class="pda_p99_tipoRotacionUndef" disabled="disabled" type="text" value="${data.rotacion}"/>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
			
		</div>
		
		<div id="pda_p99_datosStock_UC">
			<div id="pda_p99_datosStock">
				<fmt:formatNumber value="${pageData.stockWs.stock}" minFractionDigits="0" maxFractionDigits="0" var="stockActual" />
				<label id="pda_p99_lbl_stock" class="etiquetaCampoNegrita"><spring:message code="pda_p99_sacadaRestos.stock" />:</label>
				<c:choose>
					<c:when test="${null != pageData.stockWs && null != stockActual}">
						<a href="./pdaP28CorreccionStockInicio.do?codArt=${data.codArt}&origen=SR&calculoCC=${pageData.stockWs.calculoCC}&mmc=${pageData.mmc}" class="etiquetaCampoNegrita">
							<span class="${guardadoStockOk =='S' ? 'pda_p99_enlaceGuardadoOK' : 'pda_p99_enlace'}">${stockActual}</span>
						</a>				
					</c:when>
					<c:otherwise>
						<label class="valorCampo">0</label>
					</c:otherwise>
				</c:choose> 
			</div>
			<div id="pda_p99_datosUC">
				<label id="pda_p99_lbl_uc" class="etiquetaCampoNegrita">U/C:</label>
				<label id="pda_p99_lbl_uc_valor" class="valorCampo">${data.unidadesCaja}</label>
			</div>
			<div id="pda_p12_FFPP_div">
				<c:choose>
					<c:when test="${data.mostrarFFPP eq 'S'}">
						<div id ="pda_p12_FFPP">
							<a href="./pdaP99FFPPActivo.do?&codArt=${data.codArtRel}"><spring:message code="pda_p12_datosReferencia.FFPP" /></a>
						</div>
					</c:when>
					<c:when test="${data.mostrarFFPP eq 'N'}">
						<div id ="pda_p12_FFPP">
							<a href="./pdaP99FFPPActivo.do?codArt=${data.codArtRel}"><spring:message code="pda_p12_datosReferencia.retornoFFPP" /></a>
						</div>
					</c:when>
					<c:otherwise>
						<div id ="pda_p12_FFPP_vacio">
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:if test="${pageData.oferta != null && pageData.oferta.anoOferta != '' && pageData.oferta.codOferta != ''}">
			<div id="pda_p99_datosOferta_cabecera">
				<div id="pda_p99_datosOferta">
					<label id="pda_p99_lbl_oferta" class="etiquetaCampoNegrita">Of:</label>
					<label id="pda_p99_lbl_oferta_valor" class="valorCampo">${pageData.oferta.anoOferta}-${pageData.oferta.codOferta}</label>
				</div>
				<c:if test="${pageData.oferta.mostrarCabeceraX == true}">
					<div id="pda_p99_datosCabecera">
						<label id="pda_p99_lbl_cabecera" class="etiquetaCampoNegrita"><spring:message code="pda_p99_sacadaRestos.cabecera"/></label>
					</div>
				</c:if>
			</div>
		</c:if>
		
		<c:if test="${null != pageData.stockWs.msgError}">
			<div id="pda_p99_datosAlbaran">
				<label id="pda_p99_lbl_error_oferta">${pageData.stockWs.msgError}</label>
			</div>
		</c:if>

		<div id="pda_p99_dejarCajas_div">
			<c:if test="${data.flgDejarCajasSeptima}">
				<label id="pda_p99_fld_dejarCajasSeptima">${data.dejarCajasSeptima}</label>
			</c:if>
		</div>

		<div id="pda_p99_dejarCajas_div">
			<c:if test="${data.flgDejarCajas}">
				<label id="pda_p99_fld_dejarCajas">${data.dejarCajas}</label>
			</c:if>
		</div>
		
		<div id="pda_p99_espacio_div"></div>
		
		<!-- BLOQUE DE IMPLANTACION EN UNIDADES -->				
		<%@ include file="/WEB-INF/views/pda_p99_sacada_restos_imc.jsp" %>
		
		
	</c:if>
	
	<c:if test="${null != pdaError}">
		<div id="pda_p99_error_bloque" class="mens_error">
			<spring:bind path="pdaError.descError">${status.value}</spring:bind>
		</div>
	</c:if>

</div><!-- Contenido pagina -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp"%>