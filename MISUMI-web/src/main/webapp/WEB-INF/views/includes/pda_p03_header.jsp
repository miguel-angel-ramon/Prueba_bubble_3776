
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! String lan; %>
<% lan = "es"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="<%=lan%>" xml:lang="<%=lan%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta http-equiv="Expires" content="Mon, 26 Jul 1997 05:00:00 GMT"/>
<meta http-equiv="Last-Modified" content="Sun, 25 Jul 2004 16:12:09 GMT"/>
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
<meta http-equiv="Pragma" content="nocache"/>
<meta name="author" content="Ibermatica" />
<meta name="copyright" content="Ibermatica 2013" />
<meta http-equiv="Content-Language" content="<%=lan%>" />
<meta name="title" content="Ibermatica" />
<meta name="generator" content="Ibermatica" />
<meta name="distribution" content="global" />
<meta name="revisit" content="1 day" />
<meta name="robots" content="all" />
<meta name="description" content="Home" />
<meta name="keywords" content="Home" />	

<link href="./misumi/styles/pda_p03_header.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
<c:if test="${not empty param.cssFile}">
<link href="./misumi/styles/${param.cssFile}" media="all" type="text/css" rel="stylesheet" />
</c:if>

<c:if test="${not empty param.otroCssFile}">
<link href="./misumi/styles/${param.otroCssFile}" media="all" type="text/css" rel="stylesheet" />
</c:if>

<script src='./misumi/scripts/pda_p01Header.js?version=${misumiVersion}' type='text/javascript'></script>
<c:if test="${not empty param.jsFile}">
<script src="./misumi/scripts/${param.jsFile}" type="text/javascript"></script>
</c:if>

<title>misumi</title>
<link rel="shortcut icon" href="./misumi/images/misumi.ico" type="image/x-icon" />
</head>

<body>
<!-- Se cierra en pda_p02_footer.jsp -->
		<div id="marco">  <!-- Se cierra en pda_p02_footer.jsp -->
			<!-- Cabecera de p�gina -->
			<c:choose>
				<c:when test="${not empty param.actionRef && ((param.actionRef eq 'pdaP20FotoAmpliadaDatosReferencia.do') || (param.actionRef eq 'pdaP51LanzarEncargos.do') || (param.actionRef eq 'pdaP61RealizarDevolucionCabecera.do') || (param.actionRef eq 'pdaP64RealizarDevolucionSatisfactoria.do') || (param.actionRef eq 'pdaP65RealizarDevolucionErronea.do') || (param.actionRef eq 'pdaP67ErrorDevolucionLinea.do') || (param.actionRef eq 'pdaP69OrdenDeRetiradaDetalle.do') || (param.actionRef eq 'pdaP70FotoAmpliada.do') || (param.actionRef eq 'pdaP71NoExiteRefDevLin.do') || (param.actionRef eq 'pdaP04ListaImpresoras.do') || (param.actionRef eq 'pdaP93FotoAmpliada.do') || (param.actionRef eq 'pdaP94ErrorListadoRepo.do') || (param.actionRef eq 'pdaP95DescripcionDetalle.do'))}">
					<div id="cabeceraSinLinea">
				</c:when>	
				<c:otherwise>
					<div id="cabecera">
				</c:otherwise>		
			</c:choose>			
			<div id="Logo">
				<img src="./misumi/images/blanco20.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="20px" width="1px"  />
				<img src="./misumi/images/logo_misumi_pda_15.gif?version=${misumiVersion}" width="15" height="15" style="vertical-align: middle;" class="imagenLogoCabecera" title='<spring:message code="pda_p01_header.salir" />'/>
			</div>
			<div id="pda_p01_datosCentro">
				<label id="pda_p01_lbl_centerHeader" class="etiquetaCampoNegrita"><spring:message code="pda_p01_header.centro" /> </label>
				<c:set var="user" value='<%=request.getSession().getAttribute("user")%>'/>
				<label id="pda_p01_lbl_centerHeaderData" class="etiquetaCampo"><c:out value="${user.centro.codCentro}"/></label>
				<div id="pda_p01_div_centerHeaderData" style="display:none;">
					<input id="centerName" type="text" style="display:none;" /> 
					<input id="centerId" type="text" style="display:none;" /> 
				</div>
			</div>

				<c:choose>
					<c:when test="${ ((param.actionRef eq 'pdaP12DatosReferencia.do') || (param.actionRef eq 'pdaP12DatosReferenciaCaprabo.do'))  && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '23_PDA_IMPRESORA')) && not empty (pdaDatosCab.descArtCab)}">				
						<div id="pda_p01_impresora"> 
							<a id="linkEtiqueta" href="./${param.actionRef}?codArt=${pdaDatosCab.codArtCab}&impresoraWS=NO&origenGISAE=${origenGISAE}&etiqueta=true">
								<img src="./misumi/images/Tag_icon9.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
							</a>
							<div id="divNumeroEtiqueta" class="numeroEtiquetaClass">
								<input type="text" id="inputNumeroEtiqueta" value="${pdaDatosCab.numEti}" disabled>
								<input id="existeMapNumeroEtiqueta" type="hidden" value="${pdaDatosCab.existeMapNumEti}" />
							</div>
							<c:choose>
								<c:when test="${ (pdaDatosCab.impresoraActiva eq 'SI') }">
									<a id="pda_p01_impresoraActivaSi" class="divImpresora" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=SI&origenGISAE=${origenGISAE}">
										<img src="./misumi/images/impresora-verde2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">						  
									</a>
								</c:when>
								<c:when test="${(greenImpr eq 'SI')}">
									<div id="pda_p01_impresoraActivaNo" class="divImpresora">
										<img src="./misumi/images/impresora-verde2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</div>
								</c:when>
								<c:when test="${not pdaDatosCab.existeMapNumEti}">
									<div id="pda_p01_impresoraActivaNo" class="divImpresora">
										<img src="./misumi/images/impresora2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</div>
								</c:when>
								<c:otherwise>
									<a id="pda_p01_impresoraActivaNo" class="divImpresora" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=SI&origenGISAE=${origenGISAE}">
										<img src="./misumi/images/impresora2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</a>
								</c:otherwise>	
							</c:choose>
						</div>
					</c:when>
					<c:when test="${ (param.actionRef eq 'pdaP13SegPedidos.do')  &&  (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '23_PDA_IMPRESORA'))}">				
						<div id="pda_p01_impresora"> 
							<a id="linkEtiqueta" href="./pdaP13SegPedidos.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=NO&origenGISAE=${origenGISAE}&etiqueta=true">
								<img src="./misumi/images/Tag_icon9.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
							</a>
							<div id="divNumeroEtiqueta" class="numeroEtiquetaClass">
								<input id="inputNumeroEtiqueta" type="text" value="${pdaDatosCab.numEti}" disabled>
								<input id="existeMapNumeroEtiqueta" type="hidden" value="${pdaDatosCab.existeMapNumEti}" />
							</div>
							<c:choose>
								<c:when test="${ (pdaDatosCab.impresoraActiva eq 'SI') }">
									<a id="pda_p01_impresoraActivaSi" class="divImpresora" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=SI&origenGISAE=${origenGISAE}">
										<img src="./misumi/images/impresora-verde2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">						  
									</a>
								</c:when>
								<c:when test="${(greenImpr eq 'SI')}">
									<div id="pda_p01_impresoraActivaNo" class="divImpresora">
										<img src="./misumi/images/impresora-verde2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</div>
								</c:when>
								<c:when test="${not pdaDatosCab.existeMapNumEti}">
									<div id="pda_p01_impresoraActivaNo" class="divImpresora">
										<img src="./misumi/images/impresora2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</div>
								</c:when>
								<c:otherwise>
									<a id="pda_p01_impresoraActivaNo" class="divImpresora" href="./pdaP13SegPedidos.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=SI&origenGISAE=${origenGISAE}">
										<img src="./misumi/images/impresora2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</a>
								</c:otherwise>	
							</c:choose>
						</div>
					</c:when>
					<c:when test="${ (param.actionRef eq 'pdaP15MovStocks.do')   && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '23_PDA_IMPRESORA'))}">										
						<div id="pda_p01_impresora"> 
							<a id="linkEtiqueta" href="./pdaP15MovStocks.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=NO&origenGISAE=${origenGISAE}&etiqueta=true">
								<img src="./misumi/images/Tag_icon9.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
							</a>
							<div id="divNumeroEtiqueta" class="numeroEtiquetaClass">
								<input type="text" id="inputNumeroEtiqueta" value="${pdaDatosCab.numEti}" disabled>
								<input id="existeMapNumeroEtiqueta" type="hidden" value="${pdaDatosCab.existeMapNumEti}" />
							</div>
							<c:choose>
								<c:when test="${ (pdaDatosCab.impresoraActiva eq 'SI') }">
									<a id="pda_p01_impresoraActivaSi" class="divImpresora" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=SI&origenGISAE=${origenGISAE}">
										<img src="./misumi/images/impresora-verde2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">						  
									</a>
								</c:when>
								<c:when test="${(greenImpr eq 'SI')}">
									<div id="pda_p01_impresoraActivaNo" class="divImpresora">
										<img src="./misumi/images/impresora-verde2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</div>
								</c:when>
								<c:when test="${not pdaDatosCab.existeMapNumEti}">
									<div id="pda_p01_impresoraActivaNo" class="divImpresora">
										<img src="./misumi/images/impresora2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</div>
								</c:when>
								<c:otherwise>
									<a id="pda_p01_impresoraActivaNo" class="divImpresora" href="./pdaP15MovStocks.do?codArt=${pdaDatosCab.codArtCab}&impresoraWS=SI&origenGISAE=${origenGISAE}">
										<img src="./misumi/images/impresora2.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
									</a>
								</c:otherwise>	
							</c:choose>
						</div>
					</c:when>
				</c:choose>				

				<div id="LogoCerrar">
	
					<c:choose>
						<c:when test="${ (param.actionRef eq 'pdaP42InventarioLibre.do') && (not empty pdaDatosInvLib.origenGISAE) && (not empty pdaDatosInvLib.codArticulo)}">
						  <div style="float: left;">	
							<a class="barraTituloCerrar" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosInvLib.codArticulo}&origenGISAE=SI">
								<img src="./misumi/images/navegacion.gif?version=${misumiVersion}" class="barraTituloIconoCerrar">
							</a>
						  </div>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${param.flechaMenu eq 'S'}">
							<c:choose>
								<c:when test="${not empty param.actionRef && ((param.actionRef eq 'pdaP42InventarioLibre.do') || (param.actionRef eq 'pdaP47RevisionHuecos.do')
												||(param.actionRef eq 'pdaP03InvLibShowMessage')||param.actionRef eq 'pdaP12DatosReferencia.do'||param.actionRef eq 'pdaP12DatosReferenciaCaprabo.do'
												||param.actionRef eq 'pdaP13SegPedidos.do'||param.actionRef eq 'pdaP15MovStocks.do'
												||param.actionRef eq 'pdaP21Sfm.do'||param.actionRef eq 'pdaP28CorreccionStockInicio.do'||param.actionRef eq 'pdaP30CorrStockPCS.do'
												||param.actionRef eq 'pdaP31CorrStockPCN.do'||param.actionRef eq 'pdaP43invLibCorreccionStockSeleccion.do'
												||param.actionRef eq 'pdaP45InvLibCorreccionStockPCN.do'
												||param.actionRef eq 'pdaP73BandejasKgs.do')}">
									<a href="#" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionRef && ((param.actionRef eq 'pdaP16MotivosTengoMPPopup.do') || (param.actionRef eq 'pdaP16MotivosTengoMPPopupCaprabo.do'))}">
									<c:choose>
										<c:when test="${pdaDatosPopupMotivosTengoMP.procede eq 'datosRef'}">
											<a href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>											
										</c:when>
										<c:when test="${pdaDatosPopupMotivosTengoMP.procede eq 'segPedidos'}">
											<a href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>											
										</c:when>
										<c:otherwise>
											<a href="./pdaP15MovStocks.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>										
										</c:otherwise>
									</c:choose>									
								</c:when>

								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP107EntradasPalets.do')}">									
									<a href="./pdaP106PackingList.do" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP108ConsultasMatricula.do')}">									
									<c:choose>
								        <c:when test="${paraPalet}">
								             <a href="${urlInputPalet}" id="pda_p10_volver">
								                <img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" 
								                     width="40" height="20" style="vertical-align: middle;" 
								                     class="imagenInicioCabecera" 
								                     title='<spring:message code="p01_header.welcome" />'/>
								            </a>
								        </c:when>
								        <c:otherwise>
								            <a href="./pdaP106PackingList.do" id="pda_p10_volver">
								                <img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" 
								                     width="40" height="20" style="vertical-align: middle;" 
								                     class="imagenInicioCabecera" 
								                     title='<spring:message code="p01_header.welcome" />'/>
								            </a>
								        </c:otherwise>
								    </c:choose>
									
								</c:when>
								
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP102VariosBultosOrdenRetirada.do')}">									
									<a href="./pdaP63RealizarDevolucionOrdenDeRetirada.do?devolucion=${devolucion.devolucion}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP103VariosBultosFinCampana.do')}">									
									<a href="./pdaP62RealizarDevolucionFinCampania.do?devolucion=${devolucion.devolucion}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionRef && ((param.actionRef eq 'pdaP03enviarMapNumEti.do') || (param.actionRef eq 'pdaP04ListaImpresoras.do'))}">									
									<a href="./${pdaDatosRefOrigenImpresora}?codArt=${codigoArticulo}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>											
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP100MontajeAdicionalMAC.do')}">									
									<a href="./pdaP12DatosReferencia.do?codArt=${codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>											
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP101MontajeAdicionalMA.do')}">									
									<a href="./pdaP12DatosReferencia.do?codArt=${codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>											
								</c:when>
								<c:when test="${not empty param.actionRef && ((param.actionRef eq 'pdaP62RealizarDevolucionFinCampania.do') || (param.actionRef eq 'pdaP63RealizarDevolucionOrdenDeRetirada.do') || 
												(param.actionRef eq 'pdaP64RealizarDevolucionSatisfactoria.do'))}">
									<c:choose>									
										<c:when test="${origenPantalla eq 'pdaP104'}">
											<a href="./pdaP104ListaProveedores.do?devolucion=${devolucionCabecera.devolucion}&tipoDevolucion=''"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${origenPantalla eq 'pdaP105'}">
													<a href="./pdaP105ListaBultosProv.do?devolucion=${devolucionCabecera.devolucion}&tipoDevolucion=''&selectProv=${selectProv}"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
												</c:when>
												<c:otherwise>
													<a href="./pdaP60RealizarDevolucion.do?limpiarSesion=S"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP60RealizarDevolucion.do')}">
									<a href="./pdaP40SelFiabilidad.do?menu=PDA_SF"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP66NoHayDevolucionesError.do')}">
									<c:choose>
										<c:when test="${origenErrorDevolucion eq 'pda_p60_realizarDevolucion'}">
											<c:choose>
												<c:when test = "${codError eq '0'}">
													<a href="./pdawelcome.do"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
												</c:when>
												<c:otherwise>
													<a href="./pdaP40SelFiabilidad.do?menu=PDA_SF"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
												</c:otherwise>
											</c:choose>																					
										</c:when>
										<c:when test="${origenErrorDevolucion eq 'pda_p62_realizarDevolucionFinDeCampania'}">										
											<a href="./pdaP60RealizarDevolucion.do?limpiarSesion=S"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:when test="${origenErrorDevolucion eq 'pda_p63_realizarDevolucionOrdenDeRetirada'}">										
											<a href="./pdaP60RealizarDevolucion.do?limpiarSesion=S"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
									</c:choose>
								</c:when>
								<%-- <c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP65RealizarDevolucionErronea.do')}">
									<a href="javascript: document.getElementById('pda_p65_devolucionErronea_form').submit();"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP61RealizarDevolucionCabecera.do')}">
									<c:choose>
										<c:when test="${origenPantalla eq 'DEV_OK'}">										
											<a href="./pdaP64RealizarDevolucionSatisfactoria.do?devolucion=${devolucion.devolucion}&titulo=${devolucion.titulo1}&imprimir=N"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:when test="${origenPantalla eq 'DEV_KO'}">										
											<a href="./pdaP65RealizarDevolucionErronea.do?devolucion=${devolucion.devolucion}&origenPantalla=${origenPantalla}&titulo=${devolucion.titulo1}&msgError=${msgError}&referenciaFiltro=${referenciaFiltro}&proveedorFiltro=${proveedorFiltro}"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:when test="${origenPantalla eq 'ORDEN_RET'}">										
											<a href="javascript: document.getElementById('pda_p61_realizarDevolucionCabecera_form').submit();"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:otherwise>
											<a href="javascript: document.getElementById('pda_p61_realizarDevolucionCabecera_form').submit();"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:otherwise>
									</c:choose>
								</c:when> --%>

								<c:when test="${not empty param.actionRef && ((param.actionRef eq 'pdaP51LanzarEncargos.do') || (param.actionRef eq 'pdaP51ShowError.do'))}">
									<a href="pdaP51ReturnToP12.do?origenGISAE=${origenGISAE}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP17ImplantacionPopup.do')}">
									<c:choose>
										<c:when test="${not empty pdaDatosPopupImplantacion.procede && pdaDatosPopupImplantacion.procede eq 'pdaP98CapturaRestos'}">
											<a href="./pdaP98CapturaRestos.do?codArt=${pdaDatosPopupImplantacion.codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:when test="${not empty pdaDatosPopupImplantacion.procede && pdaDatosPopupImplantacion.procede eq 'pdaP99SacadaRestos'}">
											<a href="./pdaP99SacadaRestos.do?codArt=${pdaDatosPopupImplantacion.codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:otherwise>
											<a href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupImplantacion.codArt}" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:otherwise>
									</c:choose>

								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP17ImplantacionPopup.do')}">
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP98CapturaRestos.do')}">
									<a href="./pdaP97SelReposicion.do" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP99SacadaRestos.do')}">
									<a href="./pdaP97SelReposicion.do" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>
								<c:when test="${not empty param.actionVolver}">
									<a href="./pdawelcome.do" id="pda_p10_volver"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:when>			
								<c:otherwise>
									<a href="./pdawelcome.do"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${not empty param.actionRef && ((param.actionRef eq 'pdaP20FotoAmpliadaDatosReferencia.do') || (param.actionRef eq 'pdaP61RealizarDevolucionCabecera.do') || (param.actionRef eq 'pdaP68MsgFinalizarSiNo.do') || (param.actionRef eq 'pdaP80PedirRmaDevolucion.do') || (param.actionRef eq 'pdaP64RealizarDevolucionSatisfactoria.do') || (param.actionRef eq 'pdaP65RealizarDevolucionErronea.do') || (param.actionRef eq 'pdaP67ErrorDevolucionLinea.do') || (param.actionRef eq 'pdaP69OrdenDeRetiradaDetalle.do') || (param.actionRef eq 'pdaP70FotoAmpliada.do') || (param.actionRef eq 'pdaP71NoExiteRefDevLin.do') || (param.actionRef eq 'pdaP72MsgFinalizarBultoStockDevueltoVacios.do') || (param.actionRef eq 'pdaP93FotoAmpliada.do') || (param.actionRef eq 'pdaP94ErrorListadoRepo.do') || (param.actionRef eq 'pdaP95DescripcionDetalle.do'))}">
								
								</c:when>
								<c:otherwise>
									<img src="./misumi/images/blanco20.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="20px" width="1px"  />
									<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" style="vertical-align: middle;" id="pda_p01_imagenCerrar" class="imagenCerrarCabecera" title='<spring:message code="pda_p01_header.cerrar" />'/>
								</c:otherwise>
							</c:choose>							
						</c:otherwise>
					</c:choose>
				</div>

				<c:if test="${not empty param.actionRef}">
					<c:choose>
						<c:when test="${ ( (param.actionRef eq 'pdaP21Sfm.do') && (not empty pdaDatosCab.origenConsulta) ) || (param.actionRef eq 'pdaP31') || (param.actionRef eq 'pdaP41InvLibSelOperativa.do') || (param.actionRef eq 'pdaP45InvLibCorreccionStockPCN.do') || (param.actionRef eq 'pdaP43invLibCorreccionStockSeleccion.do') || (param.actionRef eq 'pdaP51LanzarEncargos.do') || (param.actionRef eq 'pdaP51ShowError.do') || (param.actionRef eq 'pdaP90ListRepoSelOperativa.do') }">
							<input id="pda_p01_txt_infoRef" type="hidden"/>
							<input id="pda_p01_txt_descRef" type="hidden"/>
						</c:when>
						<c:when test="${(param.actionRef eq 'pdaP20FotoAmpliadaDatosReferencia.do') || (param.actionRef eq 'pdaP61RealizarDevolucionCabecera.do') || (param.actionRef eq 'pdaP64RealizarDevolucionSatisfactoria.do') || (param.actionRef eq 'pdaP65RealizarDevolucionErronea.do') ||  (param.actionRef eq 'pdaP66NoHayDevolucionesError.do') || (param.actionRef eq 'pdaP67ErrorDevolucionLinea.do') || (param.actionRef eq 'pdaP68MsgFinalizarSiNo.do') || (param.actionRef eq 'pdaP80PedirRmaDevolucion.do') || (param.actionRef eq 'pdaP69OrdenDeRetiradaDetalle.do') || (param.actionRef eq 'pdaP70FotoAmpliada.do') || (param.actionRef eq 'pdaP71NoExiteRefDevLin.do') || (param.actionRef eq 'pdaP72MsgFinalizarBultoStockDevueltoVacios.do')|| (param.actionRef eq 'pdaP93FotoAmpliada.do') || (param.actionRef eq 'pdaP94ErrorListadoRepo.do') || (param.actionRef eq 'pdaP95DescripcionDetalle.do')}">
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${  ((param.actionRef eq 'pdaP13SegPedidos.do') || (param.actionRef eq 'pdaP15MovStocks.do') || (param.actionRef eq 'pdaP16MotivosTengoMPPopup.do') || (param.actionRef eq 'pdaP16MotivosTengoMPPopupCaprabo.do') || (param.actionRef eq 'pdaP17ImplantacionPopup.do'))}">
									<c:set var="varActionRef" value="pdaP12DatosReferencia.do" scope="request" />	
								</c:when>
								<c:otherwise>
									<c:set var="varActionRef" value="${param.actionRef}" scope="request" />
								</c:otherwise>
							</c:choose>	
							
							<form:form id="p12_form" method="post" action="${varActionRef}" commandName="pdaDatosCab">			
							<c:choose>
								<c:when test="${(param.actionRef eq 'pdaP21Sfm.do') || (param.actionRef eq 'pdaP12DatosReferencia.do') || (param.actionRef eq 'pdaP12DatosReferenciaCaprabo.do')
													|| (param.actionRef eq 'pdaP13SegPedidos.do') || (param.actionRef eq 'pdaP15MovStocks.do')
													|| (param.actionRef eq 'pdaP25VentaAnticipada.do') || (param.actionRef eq 'pdaP32QueHacerRef.do') || (param.actionRef eq 'pdaP60RealizarDevolucion.do') || (param.actionRef eq 'pdaP16MotivosTengoMPPopupCaprabo.do') || (param.actionRef eq 'pdaP16MotivosTengoMPPopup.do') || (param.actionRef eq 'pdaP17ImplantacionPopup.do') || (param.actionRef eq 'pdaP91ListadoRepo.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<input id="pda_p01_txt_infoRef" name="codArtCab" class="input100" value="" type="text"/>
											<input id="pda_p01_txt_descRef" type="hidden"/>
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" onclick="javascript:limpiarInput()"  class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP30CorrStockPCS.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input100" type="text"/>
											<input id="pda_p01_txt_descRef" type="hidden"/>
											<input id="pda_p30_fld_bandejas_pantalla" name="pda_p30_fld_bandejas_pantalla" type="hidden" value="${pdaArticulo.unidades}"/>
											<input id="pda_p30_fld_stock_pantalla" name="pda_p30_fld_stock_pantalla" type="hidden" value="${pdaArticulo.stock}"/>
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>	
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP73BandejasKgs.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input100" type="text"/>
											<input id="pda_p01_txt_descRef" type="hidden"/>
											<input id="pda_p73_fld_bandejas_pantalla" name="pda_p73_fld_bandejas_pantalla" type="hidden" value="${pdaArticulo.unidades}"/>
											<input id="pda_p73_fld_stock_pantalla" name="pda_p73_fld_stock_pantalla" type="hidden" value="${pdaArticulo.stock}"/>
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>	
								</c:when>
								<c:when test="${((param.actionRef eq 'pdaP42InventarioLibre.do') || (param.actionRef eq 'pdaP03InvLibShowMessage'))}">
									<div id="pda_p01_datosArticulo">
										<c:choose>
											<c:when test="${empty (pdaDatosInvLib.origenGISAE)}">		
												<div id="pda_p01_referenciaConSeccion">
													<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
													<input path="codArtCab" id="pda_p01_txt_infoRef" class="input40" type="text"/>
													<input id="pda_p01_txt_descRef" type="hidden"/>
												</div>
											</c:when>
										</c:choose>
										<div id="pda_p01_seccion">
											<label id="pda_p01_lbl_infoSeccion" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.seccion" /></label>
											<c:choose>
												<c:when test="${empty (pdaDatosInvLib.origenGISAE)}">
													<form:select path="seccion" id="pda_p01_txt_infoSeccion" class="input80">
														<form:options items="${secciones}" />
													</form:select>
												</c:when>
												<c:otherwise>
													<form:select path="seccion" id="pda_p01_txt_infoSeccion" class="input160">
														<form:options items="${secciones}" />
													</form:select>
												</c:otherwise>
											</c:choose>
												    
											<input id="pda_p01_txt_descRef" type="hidden"/>
										</div>
										<c:choose>
											<c:when test="${empty (pdaDatosInvLib.origenGISAE)}">		
												<div id="pda_p01_referencia_botonera">
													<input type="button" id="pda_p01_btn_aceptar"  class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
												</div>
											</c:when>
										</c:choose>
									</div>
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP44InvLibCorreccionStockPCS.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input100" type="text"/>
											<input id="pda_p01_txt_descRef" type="hidden"/>
											<input id="pda_p44_fld_bandejas_pantalla" name="pda_p44_fld_bandejas_pantalla" type="hidden" value="${pdaArticulo.unidades}"/>
											<input id="pda_p44_fld_stock_pantalla" name="pda_p44_fld_stock_pantalla" type="hidden" value="${pdaArticulo.stock}"/>
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="button" id="pda_p01_btn_aceptar" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>	
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaWelcome.do')}">
									<div id="pda_p01_datosArticulo">
										<c:choose>
											<c:when test="${avisoValidarCantidadesExtra eq true && avisoDevolucionesUrgente eq true}">
												<div id="mensErrorpda01" class="mens_error" >
													<div id="validarCantidadesExtraMini"><spring:message code="pda_p01_header.msgValidarCantidadesExtra" /></div>
													<div id="realizarDevolucionesMini"><spring:message code="pda_p01_header.msgRealizarDevolucionesUrgente" /></div>													
												</div>
											</c:when>
											<c:when test="${avisoValidarCantidadesExtra eq true && avisoDevoluciones eq true}">
												<div id="mensErrorpda01" class="mens_error" >
													<div id="validarCantidadesExtraMini"><spring:message code="pda_p01_header.msgValidarCantidadesExtra" /></div>
													<div id="realizarDevolucionesMini"><spring:message code="pda_p01_header.msgRealizarDevoluciones" /></div>													
												</div>
											</c:when>
											<c:when test="${avisoDevolucionesUrgente eq true}">
												<div id="mensErrorpda02" class="mens_error" >
													<div id="realizarDevolucionesUrgente"><spring:message code="pda_p01_header.msgRealizarDevolucionesUrgente" /></div>												
												</div>
											</c:when>
											<c:when test="${avisoValidarCantidadesExtra eq true}">
												<div id="mensErrorpda01" class="mens_error" >
													<div id="validarCantidadesExtra"><spring:message code="pda_p01_header.msgValidarCantidadesExtra" /></div>																										
												</div>
											</c:when>
											<c:when test="${avisoDevoluciones eq true}">
												<div id="mensErrorpda02" class="mens_error" >
													<div id="realizarDevoluciones"><spring:message code="pda_p01_header.msgRealizarDevoluciones" /></div>												
												</div>
											</c:when>
										</c:choose>
										<c:if test="${avisoCentro eq true}">
											<div id="aviso">
												<a href="./pdaP50Avisos.do"  id="avisosLink" title='<spring:message code="pda_p01_header.importante" />'>
        											<spring:message code="pda_p01_header.importante" />
        										</a>
											</div>
										</c:if>	
									</div>	
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP28CorreccionStockInicio.do') || (param.actionRef eq 'pdaP100MontajeAdicionalMAC.do') || (param.actionRef eq 'pdaP101MontajeAdicionalMA.do') || (param.actionRef eq 'pdaP03enviarMapNumEti.do') || (param.actionRef eq 'pdaP04ListaImpresoras.do')}">
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP62RealizarDevolucionFinCampania.do') || (param.actionRef eq 'pdaP63RealizarDevolucionOrdenDeRetirada.do')}">
									<div id="pda_p01_referencia">
										<c:choose>
											<c:when test="${(origenPantalla ne 'pdaP104') && (origenPantalla ne 'pdaP105')}">
												<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
												<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input40" type="text"/>
												<form:select path="proveedor" id="pda_p01_txt_proveedor" class="input150">
													<form:options items="${proveedores}" />
												</form:select>											
											</c:when>
											<c:otherwise>
												<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
												<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input40" type="text"/>
												<input type="text" id="selectProv" name="selectProv" value="${selectProv}" class="input125" disabled="disabled"></input>
											</c:otherwise>
										</c:choose>
									</div>
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP92ListadoRepoAnt.do')}">
									<div id="pda_p01_referencia">
										<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
										<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input40" type="text"/>
										<form:select path="seccion" id="pda_p01_txt_seccion" class="input150">
											<form:options items="${secciones}" />
										</form:select>											
									</div>
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP73BandejasKgs.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input100" type="text"/>																								
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>		
								</c:when>
									
								<c:when test="${(param.actionRef eq 'pdaP98CapturaRestos.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input100" type="text"/>																								
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>		
								</c:when>
									
								<c:when test="${(param.actionRef eq 'pdaP99SacadaRestos.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoRef" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input100" type="text"/>																								
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar" />' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>		
								</c:when>
								<c:when test="${(param.actionRef eq 'pdaP107EntradasPalets.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoMat" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.matricula" /></label>
											<form:input path="matricula" id="pda_p01_txt_infoRef" class="input100" type="text"/>																								
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" onclick="javascript:limpiarInput()" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar"/>' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>		
								</c:when>

								<c:when test="${(param.actionRef eq 'pdaP109ConsultasReferencia.do')}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoMat" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.referencia" /></label>
											<form:input path="codArtCab" id="pda_p01_txt_infoRef" class="input100" type="text"/>																								
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" onclick="javascript:limpiarInput()" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar"/>' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>		
								</c:when>

								<c:when test="${param.actionRef eq 'pdaP108ConsultasMatricula.do' and paraPalet != true}">
									<div id="pda_p01_datosArticulo">
										<div id="pda_p01_referencia">
											<label id="pda_p01_lbl_infoMat" class="etiquetaCampoNegrita" ><spring:message code="pda_p01_header.matricula" /></label>
											<form:input path="matricula" id="pda_p01_txt_infoRef" class="input100" type="text"/>																								
										</div>
										<div id="pda_p01_referencia_botonera">
											<input type="submit" id="pda_p01_btn_aceptar" onclick="javascript:limpiarInput()" name="actionIntroRef" class="botonFormulario" value='<spring:message code="pda_p01_header.aceptar"/>' title='<spring:message code="pda_p01_header.aceptarTitulo" />'/>
										</div>
									</div>	

								</c:when>
								
							</c:choose>
						</form:form>	
					</c:otherwise>
				</c:choose>
			</c:if>
		</div> <!-- end div cabecera" -->

<!-- El cierre de las etiquetas body y html est� en pda_p02_footer.jsp -->
