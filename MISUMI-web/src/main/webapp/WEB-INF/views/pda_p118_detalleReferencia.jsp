<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp">
	<jsp:param name="cssFile" value="pda_p118_detalleReferencia.css?version=${misumiVersion}"></jsp:param>
	<jsp:param name="jsFile" value="pda_p118DetalleReferencia.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP118DatosRefDetalleReferencia.do"></jsp:param>
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

<c:set var="imc" value="${pdaDatosRef.imagenComercial}" />
<c:set var="data" value="${pdaDatosRef}" />

<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p118_detalleReferencia_titulo">
				<label id="pda_p118_lbl_titulo" class="etiquetaEnlaceDeshabilitado">${pdaDatosRef.descSubcategoria}</label>
			</div>
			<div id="pda_p118_detalleReferencia_bloqueReferenciaColor">
				<div id="pda_p118_detalleReferencia_bloqueReferencia">
					<input id="pda_p118_fld_descripcionRef" type="text" value="${pdaDatosRef.codArt}-${pdaDatosRef.descArt}" readonly/>
				</div>	
			</div>

			<div id="pda_p118_detalleReferencia_bloqueFotoDetRef">
				<div id="pda_p118_detalleReferencia_bloqueFoto">
					<c:choose>
						<c:when test="${pdaDatosRef.tieneFoto eq 'N'}">
					        <div id="" class="foto-contenedor" style="background-image:url('./misumi/images/nofotoPda.gif');"></div>
						</c:when>
						<c:otherwise>
			        		<div id="pda_p118_detalleReferencia_foto" class="foto-contenedor" data-codArtFoto="${pdaDatosRef.codArt}" style="background-image:url('./pdaGetImageP91.do?codArticulo=${pdaDatosRef.codArt}');"></div>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="pda_p118_detalleReferencia_bloquePvp">
					<div id="pda_p118_detalleReferencia_pvp">
						<div id="pda_p118_detalleReferencia_pvplabel">
							<label id="pda_p118_lbl_pvp" class="etiquetaCampoNegrita"><spring:message code="pda_p118_detalleReferencia.pvp" /></label>
						</div>
						<div id="pda_p118_detalleReferencia_pvpval">
							<label id="pda_p118_lbl_pvpval" class="etiquetaCampoNegrita">${ofertaPVP.tarifaStr}</label>
						</div>
					</div>
					<c:choose>
						<c:when test="${ofertaPVP.pvpOferStr eq null }">
						</c:when>
						<c:otherwise>
							<div id="pda_p118_detalleReferencia_pvpOferta">
								<div id="pda_p118_detalleReferencia_pvpOfertalabel">
									<label id="pda_p118_lbl_pvpOferta" class="etiquetaCampoNegrita"><spring:message code="pda_p118_detalleReferencia.pvpOferta" /></label>
								</div>
								<div id="pda_p118_detalleReferencia_pvpOfertaval">
									<label id="pda_p118_lbl_pvpOfertaval" class="etiquetaCampoNegrita">${ofertaPVP.pvpOferStr}</label>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${ofertaPVP.annoOferta eq null && ofertaPVP.codOferta eq null}">
						</c:when>
						<c:otherwise>
							<div id="pda_p118_detalleReferencia_oferta">
								<div id="pda_p118_detalleReferencia_ofertalabel">
									<label id="pda_p118_lbl_oferta" class="etiquetaCampoNegrita"><spring:message code="pda_p118_detalleReferencia.oferta" />${ofertaPVP.annoOferta}-${ofertaPVP.codOferta}</label>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div id="pda_p118_detalleReferencia_rotacion">
						<div id="pda_p118_detalleReferencia_rotacionlabel">
							<label id="pda_p118_lbl_pvp" class="etiquetaCampoNegrita"><spring:message code="pda_p118_detalleReferencia.rotacion" /></label>
						</div>
						<div id="pda_p118_detalleReferencia_rotacionval">
							<c:choose> 
								<c:when test="${pdaDatosRef.tipoRotacion=='AR'}">
									<input id="pda_p118_fld_tipoRotacion" class="pda_p118_tipoRotacionAR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
								</c:when>
								<c:when test="${pdaDatosRef.tipoRotacion=='MR'}">
									<input id="pda_p118_fld_tipoRotacion" class="pda_p118_tipoRotacionMR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
								</c:when>	
								<c:when test="${pdaDatosRef.tipoRotacion=='BR'}">
									<input id="pda_p118_fld_tipoRotacion" class="pda_p118_tipoRotacionBR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
								</c:when>	
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
			<!-- BLOQUE DE IMPLANTACION EN UNIDADES -->				
			<%@ include file="/WEB-INF/views/pda_p118_detalleReferencia_imc.jsp" %>
		</div>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>