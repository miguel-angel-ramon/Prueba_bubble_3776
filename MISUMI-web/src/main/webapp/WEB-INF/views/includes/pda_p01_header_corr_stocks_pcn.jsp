<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! String lan; %>
<% 		
		lan = "es";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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

<link href="./misumi/styles/misumi_pda.css" media="all" type="text/css" rel="stylesheet" />
<link href="./misumi/styles/simpleDropdown/style.css" media="all" type="text/css" rel="stylesheet" />
<!--[if lte IE 7]>
		<link href="./misumi/scripts/simpleDropdown/css/ie.css" media="all" type="text/css" rel="stylesheet" />
<![endif]-->
<script src="./misumi/scripts/jqueryCore/jquery-1.3.1.min.js"></script>
<script src="./misumi/scripts/pda_p01Header.js" type="text/javascript"></script>
<script src="./misumi/scripts/simpleDropdown/jquery.dropdownPlain.js" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jshashtable-2.1.js" type="text/javascript"></script>
<script src="./misumi/scripts/pda_p31CorrStockPCN.js" type="text/javascript"></script>

<title>misumi</title>
<link rel="shortcut icon" href="./misumi/images/misumi.ico" type="image/x-icon" />
</head>

<body>
<!-- Se cierra en pda_p02_footer.jsp -->

		<div id="marco">  <!-- Se cierra en pda_p02_footer.jsp -->

			<!-- Cabecera de página -->
			<div id="cabecera">
				<div id="pda_p01_datosCentroMenu">
					<div id="Logo">
						<img src="./misumi/images/blanco.jpg" style="vertical-align: middle;" height="20px" width="1px"  />
						<img src="./misumi/images/logo_misumi_pda.gif" style="vertical-align: middle;" class="imagenLogoCabecera"/>
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
					
					<div id="pda_p01_menu">
						<ul class="dropdown">
				        	<li><a href="#"><spring:message code="pda_p01_header.menuPrincipal" /></a>
				        		<ul class="sub_menu">
				        		 	 <li><a href="./pdaP21Sfm.do?menu=PDA_SFM"><c:if test="${user.centro.flgFacing == 'S'}"><spring:message code="pda_p01_header.menuPrincipalSfmFac" /></c:if><c:if test="${user.centro.flgFacing != 'S' && user.centro.flgCapacidad == 'S'}"><spring:message code="pda_p01_header.menuPrincipalSfmCap" /></c:if><c:if test="${user.centro.flgFacing != 'S' && user.centro.flgCapacidad != 'S'}"><spring:message code="pda_p01_header.menuPrincipalSfm" /></c:if></a></li>
				        			 <li><a href="./pdaP25VentaAnticipada.do?menu=PDA_VA"><spring:message code="pda_p01_header.menuPrincipalVanticipada" /></a></li>
				        		</ul>
				        	</li>
				        	<li><a href="#"><spring:message code="pda_p01_header.menuConsultas" /></a>
				        		<ul class="sub_menu">
				        			<li><a href="./pdaP12DatosReferencia.do?menu=PDA_DR"><spring:message code="pda_p01_header.menuConsultasRef" /></a></li>
				        			<li><a href="./pdaP32QueHacerRef.do?menu=PDA_QHR"><spring:message code="pda_p01_header.menuConsultasQueHacerRef" /></a></li>
				        		</ul>
				        	</li>
				        </ul>
					</div>
					<div id="LogoCerrar">
						<img src="./misumi/images/blanco.jpg" style="vertical-align: middle;" height="20px" width="1px"  />
						<img src="./misumi/images/dialog_cancel.gif" style="vertical-align: middle;" id="pda_p01_imagenCerrar" class="imagenCerrarCabecera" title='<spring:message code="pda_p01_header.cerrar" />'/>
					</div>
				</div>	
					<input id="pda_p01_txt_infoRef" type="hidden"/>
					<input id="pda_p01_txt_descRef" type="hidden"/>
				</div> <!-- end div cabecera" -->

<!-- El cierre de las etiquetas body y html está en pda_p02_footer.jsp -->
