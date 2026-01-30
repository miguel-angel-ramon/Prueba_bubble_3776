<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%! String lan; %>
<% 		
		lan = "es";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="<%=lan%>" xml:lang="<%=lan%>">
<head>
<!-- Enable IE8 Standards mode -->
<meta http-equiv="X-UA-Compatible" content="IE=8; IE=11" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta http-equiv="Expires" content="Mon, 26 Jul 1997 05:00:00 GMT"/>
<meta http-equiv="Last-Modified" content="Sun, 25 Jul 2004 16:12:09 GMT"/>
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
<meta http-equiv="Pragma" content="nocache"/>
<meta name="author" content="Ibermatica" />
<meta name="copyright" content="Ibermatica 2012" />
<meta http-equiv="Content-Language" content="<%=lan%>" />
<meta name="title" content="Ibermatica" />
<meta name="generator" content="Ibermatica" />
<meta name="distribution" content="global" />
<meta name="revisit" content="1 day" />
<meta name="robots" content="all" />
<meta name="description" content="Home" />
<meta name="keywords" content="Home" />	

<link href="./misumi/styles/superfish/superfish.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
<link href="./misumi/styles/breadcrumb/breadCrumb.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
<link href="./misumi/styles/jquery-ui-1.8.23.custom.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" /> 

<link href="./misumi/styles/jquery.jqplot.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
<link href="./misumi/styles/ui.jqgrid.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
<link href="./misumi/styles/jquery.ui.tooltip.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
<link href="./misumi/styles/misumi.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />

<script src="./misumi/scripts/jqueryCore/jquery-1.8.2.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery-ui-1.9.1.custom.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.json-2.3.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/superfish/hoverIntent.js?version=${misumiVersion}"></script> 
<script src="./misumi/scripts/superfish/superfish.js?version=${misumiVersion}"></script> 
<script src="./misumi/scripts/utils/p03_showMessage.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.combo.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p01Header.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/User.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Centro.js?version=${misumiVersion}" type="text/javascript"></script>
<!-- Plugin para formateo de números. Necesita internamente jshashtable.js -->
<script src="./misumi/scripts/jqueryCore/jshashtable-2.1.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.numberformatter-1.2.3.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/misumi.js?version=${misumiVersion}" type="text/javascript"></script>

<% if (lan.equals("es")){ %>
<script src="./misumi/scripts/i18n/grid.locale-es.js?version=${misumiVersion}" 	type="text/javascript"></script>
<script src="./misumi/scripts/i18n/jquery.numberFormatter-es.js?version=${misumiVersion}" 	type="text/javascript"></script>
<script src="./misumi/scripts/i18n/jquery.datepick-es.js?version=${misumiVersion}" 	type="text/javascript"></script>
<% } else { %>
<script src="./misumi/scripts/i18n/grid.locale-en.js?version=${misumiVersion}" 	type="text/javascript"></script>
<% } %>

<title>misumi

</title>
<link rel="shortcut icon" href="./misumi/images/misumi.ico?version=${misumiVersion}" type="image/x-icon" />
</head>

<!--[if IE]> <body class="ie">  <![endif]-->
<!--[if !(IE)]><!--><body class="no_ie"><!--<![endif]-->
<!-- Se cierra en p02_footer.jsp -->

	<div id="marcoRojo"> <!-- Se cierra en p02_footer.jsp -->
	<div id="marco">  <!-- Se cierra en p02_footer.jsp -->

		<!-- Cabecera de página -->
		<div id="cabecera">
			<div id="Logo">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="60px" width="1px"  />
				<img src="./misumi/images/misumi.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogoCabecera"/>
								
			</div>
			<div id="DatosUsuario">
			  
				<label id="p01_lbl_userHeader" class="etiquetaCampo"> <spring:message code="p01_header.usuario" /></label>
				<label id="p01_lbl_userHeaderData" class="etiquetaCampo">${user.userNameCab} </label>
				<label id="p01_lbl_centerHeader" class="etiquetaCampo"><spring:message code="p01_header.centro" /></label>
				<c:if test="${(user.perfil == 2) || (user.perfil == 3 && user.selCentro == 0)}">	
					<label id="p01_lbl_centerHeaderData" class="etiquetaCampo" >${user.centro.codCentro} - ${user.centro.descripCentro}</label>
					<div id="p01_div_centerHeaderData" class="comboBoxCenterName" style="display:none;">
						  <input id="centerName" type="text" style="display:none;" /> 
					</div>
				</c:if>
				<c:if test="${(user.perfil == 1 || user.perfil == 4) || (user.perfil == 3 && user.selCentro == 1)}">	
					<div id="p01_div_centerHeaderData" class="comboBoxCenterName">
						  <input id="centerName" type="text"/> 
					</div>
				</c:if>
				
				<c:if test="${not empty user.centro}">							 
			   		<input id="centerId" type="hidden" value="${user.centro.codCentro}" /> 
			   		<input id="centerNegocio" type="hidden" value="${user.centro.codNegocio}"/>
			   		<input id="centerTipoNegocio" type="hidden" value="${user.centro.negocio}"/>
			   		<input id="centerRegion" type="hidden" value="${user.centro.codRegion}" />
			   		<input id="centerZona" type="hidden" value="${user.centro.codZona}" />
			   		<input id="centerSoc" type="hidden" value="${user.centro.codSoc}" />
			   		<input id="centerEnsena" type="hidden" value="${user.centro.codEnsena}"  />
			   		<input id="centerArea" type="hidden" value="${user.centro.codArea}" />
			   		<input id="centerMultiCentro" type="hidden" value=""/>
			   		<c:if test = "${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '30_GOOGLE_MAPS')}">
				   		<c:if test="${not empty user.centro.direccionCentro}">	
				   			<c:if test="${user.centro.direccionCentro.direccion != null || (user.centro.direccionCentro.latitud != null && user.centro.direccionCentro.longitud != null)}">
				   				<div id="p01_div_googleMaps"></div> 
				   			</c:if>
				   		</c:if>
			   		</c:if>
				</c:if>
				
				<c:if test="${empty user.centro}">	

			   			  <input id="centerId" type="hidden"/>
			   			  <input id="centerNegocio" type="hidden"/>
			   			  <input id="centerTipoNegocio" type="hidden"/>
			   			  <input id="centerRegion" type="hidden"/>
			   			  <input id="centerZona" type="hidden"/>
			   			  <input id="centerSoc" type="hidden"/>
			   			  <input id="centerEnsena" type="hidden"/>
			   			  <input id="centerArea" type="hidden"/>
			   			  <input id="centerMultiCentro" type="hidden"/>
			   			
				</c:if>  
				
		  		
		   	
				<input id="userId" type="text" value="${user.code}" style="display:none;" /> 
				<input id="userPermision" type="text" value="${user.centro.opcHabil}" style="display:none;" /> 
				<input id="userPerfil" type="text" value="${user.perfil}" style="display:none;" /> 
				<input id="misumiVersion" type="text" value="${misumiVersion}" style="display:none;" /> 
			</div>	
			<!-- 
			<div id="InformesListado" style="display: none;" >
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="40px" width="1px"  />
				<img id="p01_btn_informesListado" src="./misumi/images/modificado.png?version=${misumiVersion}" style="vertical-align: middle;" class="imagenInformesListado" title="<spring:message code="p01_header.informes" />"/>
			</div>
			 -->
			<div id="TablonAnuncios" style="display: none;">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="40px" width="1px"  />
				<img id="p01_btn_tablonAnuncios" src="./misumi/images/tablonanuncios.png?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios" title="<spring:message code="p01_header.tablonAnuncios" />"/>
			</div>
			<div id="InformeHuecos" style="display: none;" >
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="40px" width="1px"  />
				<img id="p01_btn_informeHuecos" src="./misumi/images/informe.png?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios" title="<spring:message code="p01_header.informes" />"/>
			</div>
			<div id="Manual">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="50px" width="1px"  />
				<img id="p01_btn_lote_navidad" src="./misumi/images/loteNavidad.png?version=${misumiVersion}" style="vertical-align: middle;display:none" class="imagenBotonCesta" title="<spring:message code="p01_header.cesta" />"/>
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="50px" width="1px"  />
				<img id="p01_btn_manual" src="./misumi/images/manual.png?version=${misumiVersion}" style="vertical-align: middle;" class="imagenManual" title="<spring:message code="p01_header.manual" />"/>
				
			</div>
			<div id="Logo2">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="50px" width="1px"  />
				<img src="./misumi/images/eroski.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogo2Cabecera"/>
				<img src="./misumi/images/caprabo.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogo2Cabecera"/>
			</div>
			
			<!--
				55929-P . 
				Los centros Caprabo no deben visualizar ni el buscador ni la botonera superior.
				Para evitar el efecto de carga y posterior ocultación, se valida el codigo a nivel de jsp 
				en vez de a nivel de JS 
				BICUGUAL 
			-->	
		
				<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '1_BUSCADOR'))}">
				<div id="infoRef">
					<label id="p01_lbl_infoRef" class="etiquetaCampo" ><spring:message code="p01_header.infoRef" /></label>
					<input id="p01_txt_infoRef" type="text" class="input110" tabindex="1" maxlength="50"/>
					<img id="p01_btn_infoRef" src="./misumi/images/boton_buscar.png?version=${misumiVersion}" class="botonBuscar" title="<spring:message code="p01_header.buscar" />"/> 
				</div>
				</c:if>
			
				<div id="accesoDirectoMenu">
				
					<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '2_SFM/CAP/FAC'))}">
						<div id="p01_btn_sfmCapacidad" class="accesoDirecto">
							<c:choose>
								<c:when test="${user.centro.descripBotonSfmCapacidad != null}">
									<c:out value="${user.centro.descripBotonSfmCapacidad}" />
								</c:when>
								<c:otherwise>
									<spring:message code="p01_header.sfm" />
								</c:otherwise>
							</c:choose>
						</div>
						<div id="p01_img_sfm" class="accesoDirectoImg"><img class="logoBotoneraCabecera" src="./misumi/images/botonera_sfm.png?version=${misumiVersion}"/></div>
					</c:if>
						
						<input id="p01_txt_flgCapacidad" type="hidden"/>				
						<input id="p01_txt_flgFacing" type="hidden"/>				
						<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '3_MIS_PEDIDOS'))}">		
						<div class="accesoDirecto" id="p01_btn_misPedidos"><spring:message code="p01_header.misPedidos" /></div>
						<div id="p01_img_misPedidos"  class="accesoDirectoImg"><img class="logoBotoneraCabecera" src="./misumi/images/botonera_mispedidos.png?version=${misumiVersion}"/></div>
						</c:if>
						<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '4_DETALLADO'))}">
						 <div class="accesoDirecto"  id="botonDetallePedido"  ><spring:message code="p01_header.detallado"  /></div>
						 <div id="p01_img_detallado" class="accesoDirectoImg"><img  class="logoBotoneraCabecera" src="./misumi/images/botonera_detallado.png?version=${misumiVersion}"/></div>
						 <c:choose>
							<c:when test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '52_DETALLADO_MOSTRADOR')}">
								<input id="isMostrador" type="text" value="true" style="display:none;" />
							</c:when>
							<c:otherwise>
								<input id="isMostrador" type="text" value="false" style="display:none;" />								
							</c:otherwise>
						 </c:choose>
						</c:if>
						<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && (misumi:contieneOpcion(user.centro.opcHabil, '5_PEDIDOS_ADICIONAL') || misumi:contieneOpcion(user.centro.opcHabil, '11_ENCARGOS_CLIENTE') || misumi:contieneOpcion(user.centro.opcHabil, '111_ENCARGOS' || misumi:contieneOpcion(user.centro.opcHabil, '112_MONTAJE_ADICIONAL'))))}">
						<div class="accesoDirecto" id="p01_btn_pedidoAdicional"><spring:message code="p01_header.pedidoAdicional" /></div>
						<div id="p01_img_pedidoAdicional" class="accesoDirectoImg"><img class="logoBotoneraCabecera" src="./misumi/images/botonera_pedidoadicional.png?version=${misumiVersion}"/></div>
						</c:if>
						
						<c:if test="${user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '')}">
							<c:if test="${misumi:contieneOpcion(user.centro.opcHabil, '112_MONTAJE_ADICIONAL')}">
								<input id="p01_txt_tipoPedidoAdicional_opc_montajeAdicional" type="hidden" value="SI"/>
							</c:if>
						  	<c:if test="${misumi:contieneOpcion(user.centro.opcHabil, '111_ENCARGOS')}">
								<input id="p01_txt_tipoPedidoAdicional_opc_encargos" type="hidden" value="SI"/>
							</c:if>
							<c:if test="${misumi:contieneOpcion(user.centro.opcHabil, '11_ENCARGOS_CLIENTE')}">
								<input id="p01_txt_tipoPedidoAdicional_opc_encargoCliente" type="hidden" value="SI"/>
							</c:if>
						</c:if>
						
						<input id="p01_flgFocoInfoRef" type="hidden" value="${flgFocoInfoRef}"/>
				</div>
			
			
			<!--
				55929-P . 
				Los centros Caprabo no deben visualizar ni el buscador ni la botonera superior.
				Para evitar el efecto de carga y posterior ocultación, se valida el codigo a nivel de jsp 
				en vez de a nivel de JS 
				BICUGUAL 
			-->
			
			<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && (misumi:contieneOpcion(user.centro.opcHabil, '6_LISTADO_GAMA') || misumi:contieneOpcion(user.centro.opcHabil, '7_CONSULTA_DATOS_REF') || misumi:contieneOpcion(user.centro.opcHabil, '8_INTERTIENDA')))}">
			<div id="menu2">
				<ul class="sf-menu">
					<li class="current">
						<a href="#a"><spring:message code="p01_header.consultas" /></a>
						<ul>
				
							<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '6_LISTADO_GAMA'))}">
								<li>
									<a href="./altasCatalogo.do" id="altasCatalogo"><spring:message code="p01_header.altasCatalogo" /></a>
								</li>
							</c:if>
							<c:if test="${(user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '110_FACING_VEGALSA'))}">
								<li>
									<a href="./facingVegalsa.do" id="enlaceFacingVegalsa"><spring:message code="p01_header.facing.vegalsa" /></a>
								</li>
							</c:if>
							<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '65_LISTADO_RAPID'))}">
								<li>
									<a href="./altasCatalogoRapid.do" id="altasCatalogoRapid"><spring:message code="p01_header.altasCatalogoRapid" /></a>
								</li>
							</c:if>
							<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '7_CONSULTA_DATOS_REF'))}">
							<li>
								<a href="./referenciasCentro.do" id="referenciasCentro"><spring:message code="p01_header.references" /></a>
								<!-- <a href="#"><spring:message code="p01_header.references" /></a> -->
							</li>
							</c:if>
							<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '8_INTERTIENDA'))}">	
								<li id="intertienda">
									<a href="#"><spring:message code="p01_header.intertienda" /></a>
								</li>								
							</c:if>
							<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '66_LISTADO_REFERENCIAS_SEGUNDA_REPOSICION'))}">	
								<li>
									<a href="./listadoRefSegundaReposicion.do" id="segundaReposicion"><spring:message code="p01_header.segundaReposicion" /></a>
								</li>								
							</c:if>
							
						</ul>
					</li>
					
				</ul>
			</div> <!-- end div menu" -->
			</c:if>
			
			<!--
				55929-P . 
				Los centros Caprabo no deben visualizar ni el buscador ni la botonera superior.
				Para evitar el efecto de carga y posterior ocultación, se valida el codigo a nivel de jsp 
				en vez de a nivel de JS (${!user.centro.esCentroCaprabo})
				BICUGUAL 
			-->
			
			<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && (misumi:contieneOpcion(user.centro.opcHabil, '9_EXCLUSION_VENTA') || misumi:contieneOpcion(user.centro.opcHabil, '10_VENTA_ANTICIPADA') || misumi:contieneOpcion(user.centro.opcHabil, '15_DEVOLUCIONES') || misumi:contieneOpcion(user.centro.opcHabil, '40_CALENDARIO') || misumi:contieneOpcion(user.centro.opcHabil, '120_CALENDARIO_VEGALSA')))}">
				<div id="menu">
					<ul class="sf-menu">
						<li class="current">
							<a href="#a"><spring:message code="p01_header.otros" /></a>
							<ul>
								<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '9_EXCLUSION_VENTA'))}">
								<li>
									<a href="#" id="enlaceExclusionVentas"><spring:message code="p01_header.exclusionVentas" /></a>
								</li>
								</c:if>
								<c:if test="${user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '10_VENTA_ANTICIPADA'))}">
								<li>
									<a href="#" id="enlaceVentaAnticipada"><spring:message code="p01_header.ventaAnticipada" /></a>
								</li>
								</c:if>
								<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '107_ALARMAS_PLU'))}">
								<li>
									<a href="#" id="enlaceAlarmasPLU"><spring:message code="p01_header.alarmasPLU" /></a>
								</li>
								</c:if>
								<c:if test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '15_DEVOLUCIONES')}">
									<li>
										<a href="#" id="enlaceDevoluciones"><spring:message code="p01_header.Devoluciones" /></a>
									</li>
								</c:if>
								<c:if test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '40_CALENDARIO')}">
									<li>
									
										<a href="#" id="enlaceCalendario"><spring:message code="p01_header.calendario" /></a>
									</li>
								</c:if>
								<c:if test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '120_CALENDARIO_VEGALSA')}">
									<li>
										<a href="#" id="enlaceCalendarioVegalsa"><spring:message code="p01_header.calendario.vegalsa" /></a>
									</li>
								</c:if>
								<c:if test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '70_DESCENTRALIZADO')}">
									<li>
										<a href="#" id="enlaceEntradas"><spring:message code="p01_header.entradas" /></a>
									</li>
								</c:if>
								
								<c:if test="${user.flgCestas == 'S'}">
									<li>
										<a href="#" id="enlaceParametrizacionCestas"><spring:message code="p01_header.parametrizacion.cestas" /></a>
									</li>
								</c:if>
								
								<c:if test="${user.flgAvisos == 'S'}">
									<li>
										<a href="#" id="enlaceAvisosSiec"><spring:message code="p01_header.avisosSiec" /></a>
									</li>
								</c:if>
							</ul>
						</li>
					</ul>
				</div> <!-- end div menu" -->
			</c:if>
			
			<div>
				<a href="./logout.do"  title='<spring:message code="p01_header.logout" />'>
				<img src="./misumi/images/logout.png?version=${misumiVersion}" class="botonLogout" title='<spring:message code="p01_header.logout" />'/>
				</a>
			</div>
		</div> <!-- end div cabecera" -->
		<div id="manualPopup"></div> 
		<div id="manualWindow"></div>	
<%@ include file="/WEB-INF/views/p30_popUpInfoReferencia.jsp" %>   
<%@ include file="/WEB-INF/views/p19_tablonAnunciosPopup.jsp" %>   
<%@ include file="/WEB-INF/views/p32_popUpVentaAnticipada.jsp" %>
<%@ include file="/WEB-INF/views/p33_informeHuecosPopup.jsp" %>
<%@ include file="/WEB-INF/views/p34_popUpSelReferencias.jsp" %> 
<%@ include file="/WEB-INF/views/p36_popUpInformesPesca.jsp" %>   
<%@ include file="/WEB-INF/views/p80_popUpLotesNavidad.jsp" %>      
<%@ include file="/WEB-INF/views/p90_multicentroPopupSeleccion.jsp" %>
<%@ include file="/WEB-INF/views/p91_popUpGoogleMaps.jsp" %>

<!-- El cierre de las etiquetas body y html está en p02_footer.jsp -->
