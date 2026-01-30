<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%! String lan; %>
<% 		
		lan = "es";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="<%=lan%>">
<head>
<!-- Enable IE8 Standards mode -->
<meta http-equiv="X-UA-Compatible" content="IE=8" />
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
<link href="./misumi/styles/misumi.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />

<script src="./misumi/scripts/jqueryCore/jquery-1.7.2.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery-ui-1.8.23.custom.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.json-2.3.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/superfish/hoverIntent.js?version=${misumiVersion}"></script> 
<script src="./misumi/scripts/superfish/superfish.js?version=${misumiVersion}"></script> 
<script src="./misumi/scripts/utils/p03_showMessage.js?version=${misumiVersion}"></script>


<script src="./misumi/scripts/misumi.js?version=${misumiVersion}" type="text/javascript"></script>
<title>misumi

</title>
<link rel="shortcut icon" href="./misumi/images/misumi.ico?version=${misumiVersion}" type="image/x-icon" />
</head>

<body> <!-- Se cierra en p02_footer.jsp -->

	<div id="marcoRojo"> <!-- Se cierra en p02_footer.jsp -->
	<div id="marco">  <!-- Se cierra en p02_footer.jsp -->

		<!-- Cabecera de página -->
		<div id="cabecera">
			<div id="Logo">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="60px" width="1px"  />
				<img src="./misumi/images/misumi.jpg?version=${misumiVersion}" style="vertical-align: middle;"  class="imagenLogoCabecera"/>
				
				<!-- <span style="vertical-align: middle; font-size:180%; font-weight:bold;"><spring:message code="p01_header.nombreApp" /></span> -->
				
			</div>	

			<div id="Logo2">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="60px" width="1px"  />
				<img src="./misumi/images/eroskigrupo.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogo2Cabecera"/>
			</div>
			
			<div>
				<a href="./logout.do"  title='<spring:message code="p01_header.logout" />'>
				<img src="./misumi/images/logout.png?version=${misumiVersion}" class="botonLogout"/>
				</a>
			</div>
			
		</div> <!-- end div cabecera" -->


<!-- El cierre de las etiquetas body y html está en p02_footer.jsp -->

		

<div id="contenidoPagina">
			<div id="welcome">
				<br/><br/><br/><br/>
					<div class="mens_aviso"><spring:message code="error" /></div>
				<br/><br/><br/><br/>
			</div>
		</div>		
<jsp:directive.include file="/WEB-INF/views/includes/p02_footer.jsp"/>