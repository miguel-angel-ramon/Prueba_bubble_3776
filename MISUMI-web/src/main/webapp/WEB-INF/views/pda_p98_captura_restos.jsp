<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp">

	<jsp:param value="pda_p98_captura_restos.css?version=${misumiVersion}"
		name="cssFile"></jsp:param>

	<jsp:param value="pda_p98CapturaRestos.js?version=${misumiVersion}"
		name="jsFile"></jsp:param>
	<jsp:param value="pdaP98CapturaRestos.do" name="actionRef"></jsp:param>
	<jsp:param value="S" name="flechaMenu"></jsp:param>
</jsp:include>

<%-- METODO --%>
<c:set var="esPlanogramaRef" value="1" />
<c:set var="esSfmRef" value="2" />
<c:set var="esFacCapRef" value="3" />
<c:set var="esCapRef" value="4" />
<c:set var="esFacNoAliRef" value="5" />

<%-- TIPO REFERENCIA --%>
<c:set var="esCajaExpositoraRef" value="1" />
<c:set var="esMadreRef" value="2" />
<c:set var="esFFPPRef" value="3" />

<c:set var="imc" value="${pageData.capturaResto.imagenComercial}" />
<c:set var="data" value="${pageData.capturaResto}" />


<!--  Contenido página -->
<div id="contenidoPagina">
	<div id="contenidoCentral">
		<div id="pda_p98_capturaRestos_titulo">
			<label class="etiquetaEnlaceDeshabilitado"> <spring:message
					code="pda_p98_capturaRestos.titulo" />
			</label>
		</div>
		<c:if test="${pageData.totalPages > 0 }">

			<div id="pda_p98_datosReferencia_bloqueReferencia">

				<!-- Si se ha de mostrar rotacion -->
				<c:set var="mostrarRotacion" value="false" />
				<!-- Clase a usar en el input descArtConCodigo en caso de que se muestre o no rotacion -->
				<c:set var="descripRefClass" value="input225" />
				<c:if
					test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '25_PDA_ROTACION')) && !empty data.rotacion}">
					<c:set var="mostrarRotacion" value="true" />
					<c:set var="descripRefClass" value="input180" />
				</c:if>

				<div id="pda_p98_bloque2_descReferencia">
					<input id="pda_p98_fld_descripcionRef" name="descArtConCodigo"
						class="${mostrarRotacion ? 'input180' : 'input225'} linkInput"
						type="text" value="${data.codArt}-${data.descripArt}" readonly>

					<input id="pda_p98_codArt" type="hidden" value="${data.codArt}" />
					<input id="pda_p98_tieneFoto" type="hidden"
						value="${pageData.tieneFoto}" />

				</div>
				<c:if test="${mostrarRotacion}">
					<!-- Si se ha de mostrar rotacion-->
					<div id="pda_p98_bloque2_tipoRot">
						<c:choose>
							<c:when
								test="${data.rotacion=='AR' || data.rotacion=='MR' || data.rotacion=='BR' }">
								<input id="pda_p98_fld_tipoRotacion"
									class="pda_p98_tipoRotacion${data.rotacion}"
									disabled="disabled" type="text" value="${data.rotacion}" />
							</c:when>
							<c:otherwise>
								<input id="pda_p98_fld_tipoRotacion"
									class="pda_p98_tipoRotacionUndef" disabled="disabled"
									type="text" value="${data.rotacion}" />
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>
			</div>

			<div id="pda_p98_datosStock_UC">
				<div id="pda_p98_datosStock">
					<fmt:formatNumber value="${pageData.stockWs.stock}"
						minFractionDigits="0" maxFractionDigits="0" var="stockActual" />
					<label id="pda_p98_lbl_stock" class="etiquetaCampoNegrita"><spring:message
							code="pda_p98_capturaRestos.stock" />:</label>
					<c:choose>
						<c:when test="${null != pageData.stockWs && null != stockActual}">
							<a
								href="./pdaP28CorreccionStockInicio.do?codArt=${data.codArt}&origen=CR&calculoCC=${pageData.stockWs.calculoCC}&mmc=${pageData.mmc}"
								class="etiquetaCampoNegrita"> <span
								class="${guardadoStockOk =='S' ? 'pda_p98_enlaceGuardadoOK' : 'pda_p98_enlace'}">${stockActual}</span>
							</a>
						</c:when>
						<c:otherwise>
							<label class="valorCampo">0</label>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p98_datosUC">
					<fmt:formatNumber value="${data.unidadesCaja}"
						minFractionDigits="2" maxFractionDigits="2" var="unidadesCaja" />
					<label id="pda_p98_lbl_uc" class="etiquetaCampoNegrita">U/C:</label>
					<label id="pda_p98_lbl_uc_valor" class="valorCampo"><c:out
							value="${unidadesCaja}"></c:out></label>
				</div>
			</div>

			<c:if test="${null != pageData.oferta}">
				<div id="pda_p98_datosOferta_cabecera">
					<div id="pda_p98_datosOferta">
						<label id="pda_p98_lbl_oferta" class="etiquetaCampoNegrita">Of:</label>
						<label id="pda_p98_lbl_oferta_valor" class="valorCampo">${pageData.oferta.anoOferta}-${pageData.oferta.codOferta}</label>
					</div>
					<c:if test="${pageData.oferta.mostrarCabeceraX == true}">
						<div id="pda_p98_datosCabecera">
							<label id="pda_p98_lbl_cabecera" class="etiquetaCampoNegrita"><spring:message
									code="pda_p98_capturaRestos.cabecera" /></label>
						</div>
					</c:if>
				</div>
			</c:if>

			<c:if test="${null != pageData.stockWs.msgError}">
				<div id="pda_p98_datosAlbaran">
					<label class="etiquetaCampoNegrita"><span style='color:#FF0000;font-weight:bold;'>${pageData.stockWs.msgError}</span></label>
				</div>
			</c:if>

			<c:if test="${imc.metodo eq 1 && imc.facing gt 0}">
				<div id="pda_p98_capacidad_facing">
					<c:set var="capFacingText" value="unid.fondoXfila" />
					<c:choose>
						<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
							<c:set var="capFacingText" value="unid.fondoXfila" />
						</c:when>
						<c:when test="${imc.tipoReferencia eq esMadreRef}">
							<c:set var="capFacingText" value="unid.fondoXfila" />
						</c:when>
						<c:when test="${imc.tipoReferencia eq esFFPPRef}">
							<c:set var="capFacingText" value="unid.tot FFPP" />
						</c:when>
					</c:choose>

					<div class="pda_p98_capacidad_facing_left">
						<form name="flgCapacidadIncorrecta">
							<label class="etiquetaCampoNegrita">
								<c:out value="${capFacingText}"/>:
							</label>
							<label class="valorCampoResaltado">
								<c:out value="${data.capacidadFacing}"></c:out>&nbsp;
							</label>
							<label class="etiquetaCampoNegrita">
								Cap.Errónea
							</label>
							<input id="pda_p98_checkbox_no_ok" type="checkbox"
								${data.flgCapacidadIncorreta == 'S' ? 'checked' : ''}
								onclick="javascript:form_submit();"/>
						</form>
					</div>
				</div>
			</c:if>

			<c:if test="${imc.metodo eq 1}">
				<div id="pda_p98_imc_mensaje_restos">
					<c:set var="capital" value="${imc.capacidad + data.capacidad1}"/>
					<c:set var="capital2" value="${imc.capacidad + (data.unidadesCaja/2) + data.capacidad1}"/>
					<c:choose>
						<c:when test="${(pageData.stockWs.stock - capital)/data.unidadesCaja gt 0}">
							<label id="pda_p98_lbl_mensaje_restos_ok"><spring:message code="pda_p98_imc.mensaje.restos.ok"/></label>
						</c:when>
						<c:when test="${(pageData.stockWs.stock - capital2)/data.unidadesCaja gt 0}">
							<label id="pda_p98_lbl_mensaje_restos_ok"><spring:message code="pda_p98_imc.mensaje.restos.ok"/></label>
						</c:when>
						<c:otherwise>
							<label id="pda_p98_lbl_mensaje_restos_ko"><spring:message code="pda_p98_imc.mensaje.restos.ko"/></label>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
			
			<!-- BLOQUE DE IMPLANTACION EN UNIDADES -->
			<%@ include file="/WEB-INF/views/pda_p98_captura_restos_imc.jsp"%>

		</div>
	<!-- Contenido central -->

		<div id="pda_p98_bloqueFlechas">
			<div id="pda_p98_pager">
				<div id="pda_p98_pager_pagPrimera" class="pda_p98_pager_arrow">
					<c:choose>
						<c:when test="${pageData.currentPage eq 1}">
							<img src="./misumi/images/pager_first_des_24.gif?version="
								class="pda_p98_pager_arrow_img">
						</c:when>
						<c:otherwise>
							<a href="pdaP98CapturaRestos.do?pagina=1"> <img
								src="./misumi/images/pager_first_24.gif?version="
								class="pda_p98_pager_arrow_img">
							</a>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p98_pager_pagAnt" class="pda_p98_pager_arrow">
					<c:choose>
						<c:when test="${pageData.currentPage eq 1}">
							<img src="./misumi/images/pager_prev_des_24.gif?version="
								class="pda_p98_pager_arrow_img">
						</c:when>
						<c:otherwise>
							<a
								href="pdaP98CapturaRestos.do?pagina=${pageData.currentPage - 1}">
								<img src="./misumi/images/pager_prev_24.gif?version="
								class="pda_p98_pager_arrow_img">
							</a>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p98_pager_numbers" class=""
					data-pagina="${pageData.currentPage}">
					${pageData.currentPage}/${pageData.totalPages}
				</div>
				<div id="pda_p98_pager_pagSig" class="pda_p98_pager_arrow">
					<c:choose>
						<c:when test="${pageData.currentPage eq pageData.totalPages}">
							<img src="./misumi/images/pager_next_des_24.gif?version="
								class="pda_p98_pager_arrow_img">
						</c:when>
						<c:otherwise>
							<a
								href="pdaP98CapturaRestos.do?pagina=${pageData.currentPage + 1}">
								<img src="./misumi/images/pager_next_24.gif?version="
								class="pda_p98_pager_arrow_img">
							</a>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p98_pager_pagUltima" class="pda_p98_pager_arrow">
					<c:choose>
						<c:when test="${pageData.currentPage eq pageData.totalPages}">
							<img src="./misumi/images/pager_last_des_24.gif?version="
								class="pda_p98_pager_arrow_img">
						</c:when>
						<c:otherwise>
							<a href="pdaP98CapturaRestos.do?pagina=${pageData.totalPages}">
								<img src="./misumi/images/pager_last_24.gif?version="
								class="pda_p98_pager_arrow_img">
							</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	<!-- pda_p98_bloqueFlechas -->
	</c:if>

	<c:if test="${null != pdaError}">
		<div id="pda_p98_error_bloque" class="mens_error">
			<spring:bind path="pdaError.descError">${status.value}</spring:bind>
		</div>
	</c:if>

</div>
<!-- Contenido pagina -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp"%>