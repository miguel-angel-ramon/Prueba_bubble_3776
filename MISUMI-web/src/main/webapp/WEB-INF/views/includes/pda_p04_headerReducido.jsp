<%@ page trimDirectiveWhitespaces="true" %>
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
		
		<link href="./misumi/styles/pda_p04_headerReducido.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
		<c:if test="${not empty param.cssFile}">
			<link href="./misumi/styles/${param.cssFile}" media="all" type="text/css" rel="stylesheet" />
		</c:if>
		
		<script src='./misumi/scripts/pda_p01Header.js?version=${misumiVersion}' type='text/javascript'></script>
		<c:if test="${not empty param.jsFile}">
			<script src="./misumi/scripts/${param.jsFile}" type="text/javascript"></script>
		</c:if>
		
		<title>misumi</title>
		<link rel="shortcut icon" href="./misumi/images/misumi.ico" type="image/x-icon" />
	</head>

	<body>
<!-- La etiqueta <body>, se cierra en "pda_p02_footer.jsp" -->

		<div id="marco">  <!-- Se cierra en pda_p02_footer.jsp -->
		
			<!-- Cabecera de página -->
			<div id="cabecera"> 
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
				<div id="LogoCerrar">
					<c:choose>
						<c:when test="${param.flechaMenu eq 'S'}">
							<c:choose>
								<c:when test="${not empty param.actionRef && (param.actionRef eq 'pdaP105ListaBultosProv.do')}">
									<c:choose>
										<c:when test="${origenPantalla eq 'pdaP104'}">
											<a href="./pdaP104ListaProveedores.do?devolucion=${devolucionCabecera.devolucion}&tipoDevolucion=''"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${origenPantalla eq 'pdaP105'}">
													<a href="./pdaP105ListaBultosProv.do?devolucion=${devolucionCabecera.devolucion}&tipoDevolucion=''&selectProv=${selProv}"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
												</c:when>
												<c:otherwise>
													<a href="./pdaP60RealizarDevolucion.do?limpiarSesion=S"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
												</c:otherwise>
											</c:choose>
										</c:otherwise>									
									</c:choose>
								</c:when>
								<c:otherwise>
									<a href="./pdaP60RealizarDevolucion.do?limpiarSesion=S"><img src="./misumi/images/arrow_left_40.gif?version=${misumiVersion}" width="40" height="20" style="vertical-align: middle;" class="imagenInicioCabecera" title='<spring:message code="p01_header.welcome" />'/></a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<img src="./misumi/images/blanco20.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="20px" width="1px"  />
							<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" style="vertical-align: middle;" id="pda_p01_imagenCerrar" class="imagenCerrarCabecera" title='<spring:message code="pda_p01_header.cerrar" />'/>
						</c:otherwise>
					</c:choose>
				</div>
			</div> <!-- end div cabecera" -->

<!-- El cierre de las etiquetas body y html está en pda_p02_footer.jsp -->
