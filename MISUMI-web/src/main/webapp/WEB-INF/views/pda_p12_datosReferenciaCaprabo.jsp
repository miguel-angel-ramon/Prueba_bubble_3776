<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p12_datosReferenciaCaprabo.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p12DatosReferenciaCaprabo.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP12DatosReferenciaCaprabo.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<c:choose>
				<c:when test="${empty (pdaDatosRef.codArt)}">
					<div id="pda_p12_caprabo_datosReferencia_bloque1">
						<label id="pda_p12_caprabo_caprabo_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_caprabo_datosReferencia.datosReferencia" /></label>
					</div>
				</c:when>
				<c:otherwise>
					<div id="pda_p12_caprabo_datosReferencia_bloque1">
						<label id="pda_p12_caprabo_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_caprabo_datosReferencia.datosReferencia" /></label>
					</div>
					
					<div id="pda_p12_caprabo_datosReferencia_bloqueReferencia">
						<input id="pda_p12_caprabo_fld_descripcionRef" name="descArtConCodigo" class="input225" type="text" disabled="disabled" value="${pdaDatosCab.descArtCabConCodigo}"/>
					</div>	
					
					<div id="pda_p12_caprabo_datosReferencia_bloque2">
						
	
		  			 <!-- <div id="pda_p12_caprabo_tengoMuchoPoco">
						<fieldset id=pda_p12_caprabo_tengoMuchoPocoFieldsetOk>
							<div id="pda_p12_caprabo_tengoMuchoPocoOkLabelDiv">
								<span id="pda_p12_caprabo_tengoMuchoPocoOkLabel"><spring:message code="pda_p12_caprabo_datosReferencia.stockDias" /></span>
							</div>
							<div id="pda_p12_tengoMuchoPocoOkStockDiv">
									<span id="pda_p12_caprabo_lbl_stockActualVal" class="pda_p12_caprabo_tengoMuchoPocoStockSinEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind> -> <spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
							</div>
						</fieldset>	
					 </div> -->
					 
					<div id="pda_p12_caprabo_referenciasCentroPedir"> 
					 <c:choose>
						<c:when test="${pdaDatosRef.pedir eq 'S'}">
						
							<c:choose>
								<c:when test="${not empty pdaDatosRef.tMisMcgCaprabo.fechaActivacion && pdaDatosRef.mostrarFechaGen}">
								
									<a href="./pdaP19fechaActivacionPopupCaprabo.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&fechaActivacion=${pdaDatosRef.strFechaGen}">
										<fieldset id="pda_p12_caprabo_pedidoAutomaticoFieldsetActivo">
											<p class="pda_p12_caprabo_parrafoPedidoAutomaticoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
										</fieldset>
									</a>
								</c:when>
								<c:otherwise>
									<fieldset id="pda_p12_caprabo_pedidoAutomaticoFieldsetActivo">
										<p class="pda_p12_caprabo_parrafoPedidoAutomaticoActivo"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
									</fieldset>
								</c:otherwise>
							</c:choose>
						
						</c:when>
						<c:when test="${pdaDatosRef.pedir eq 'N'}">
							
								
							<c:choose>
								<c:when test="${not empty pdaDatosRef.motivoCaprabo}">	
									<a href="./pdaP16MotivosTengoMPPopupCaprabo.do?codArt=${pdaDatosRef.codArt}">
										<fieldset id="pda_p12_caprabo_pedidoAutomaticoFieldsetNoActivo">
											<p class="pda_p12_caprabo_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
										</fieldset>
									</a>
								</c:when>
								<c:otherwise>
									<fieldset id="pda_p12_caprabo_pedidoAutomaticoFieldsetNoActivo">
								   		<p class="pda_p12_caprabo_parrafoPedidoAutomaticoNoActivo"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
									</fieldset>
								</c:otherwise>
							</c:choose>
							
							
						</c:when>
						<c:otherwise>
										
						</c:otherwise>
					</c:choose>
				</div>			
		
				<div id= "pda_p12_caprabo_FFPP_div">
			
					<c:choose>
						<c:when test="${pdaDatosRef.mostrarSustARef eq 'S'}">
							<div id ="pda_p12_caprabo_SustARef">
								<a href="./pdaP12FFPPActivoCaprabo.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.tMisMcgCaprabo.sustitutaDe}"><spring:message code="pda_p12_caprabo_datosReferencia.SustARef" /></a>
							</div>
						</c:when>
						<c:when test="${pdaDatosRef.mostrarSustPorRef eq 'S'}">
							<div id ="pda_p12_caprabo_SustPorRef">
								<a href="./pdaP12FFPPActivoCaprabo.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.tMisMcgCaprabo.sustituidaPor}"><spring:message code="pda_p12_caprabo_datosReferencia.SustPorRef" /></a>
							</div>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</div>
			</div>			
					
					<div id="pda_p12_caprabo_datosReferencia_bloque5">
						<fieldset id="pda_p12_caprabo_imagenComercial">
						   <c:choose>
							  <c:when test="${pdaDatosRef.flgColorImplantacion eq 'VERDE'}">
									<legend id="pda_p12_caprabo_imagenComercialLegendVerde"><span id="pda_p12_caprabo_imagenComercialSpanVerde"> <spring:message code="pda_p12_caprabo_datosReferencia.legendImagenComercial" /> </span> <span id="pda_p12_caprabo_SpanImplantacion"><a href="./pdaP17implantacionPopupCaprabo.do?codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.tMisMcgCaprabo.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}"><spring:message code="pda_p12_caprabo_datosReferencia.implantacion" /></a></span></legend>
							  </c:when>
							   <c:when test="${pdaDatosRef.flgColorImplantacion eq 'ROJO'}">
							   		<legend id="pda_p12_caprabo_imagenComercialLegendRojo"><span id="pda_p12_caprabo_imagenComercialSpanRojo"> <spring:message code="pda_p12_caprabo_datosReferencia.legendImagenComercial" /> </span> <span id="pda_p12_caprabo_SpanImplantacion"><a href="./pdaP17implantacionPopupCaprabo.do?codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.tMisMcgCaprabo.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}"><spring:message code="pda_p12_caprabo_datosReferencia.implantacion" /></a></span></legend>
							  </c:when>
							 
							  <c:otherwise>
							  		<legend id="pda_p12_caprabo_imagenComercialLegend"> <spring:message code="pda_p12_datosReferencia.legendImagenComercial" /> </legend>
							  </c:otherwise>
						   </c:choose>		
						 </fieldset>
					</div>
				</c:otherwise>
			</c:choose>						
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>