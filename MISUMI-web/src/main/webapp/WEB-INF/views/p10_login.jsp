<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%! String lan; %>
<% 	
		lan = "es";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="<%=lan%>" xml:lang="<%=lan%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Expires" content="Mon, 26 Jul 1997 05:00:00 GMT"/>
<meta http-equiv="Last-Modified" content="Sun, 25 Jul 2004 16:12:09 GMT"/>
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
<meta http-equiv="Pragma" content="nocache"/>
<meta http-equiv="imagetoolbar" content="no" />
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

<link href="./misumi/styles/misumi.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />
<link href="./misumi/styles/jquery-ui-1.8.23.custom.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />

<script src="./misumi/scripts/jqueryCore/jquery-1.7.2.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery-ui-1.8.23.custom.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.json-2.3.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/misumi.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/p03_showMessage.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/login/login.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/User.js?version=${misumiVersion}" type="text/javascript"></script>


<title>misumi</title>
<link rel="shortcut icon" href="./misumi/images/misumi.ico?version=${misumiVersion}" type="image/x-icon" />
</head>

<body> 

	<div id="marcoRojo"> <!-- Se cierra en p02_footer.jsp -->
	<div id="marco">  <!-- Se cierra en p02_footer.jsp -->

		<!-- Cabecera de página -->
		<div id="cabecera">
			<div id="LogoLogin">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="70px" width="1px"  />
				<img src="./misumi/images/misumi.jpg?version=${misumiVersion}" style="vertical-align: middle;"  class="imagenLogoLogin"/>
				
				<!-- <span style="vertical-align: middle; font-size:180%; font-weight:bold;">misumi</span> -->
				
			</div>
			<div id="Logo2Login">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="70px" width="1px"  />
				<img src="./misumi/images/eroskigrupo.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogo2Login"/>
				<img src="./misumi/images/caprabo_login.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogo2Login"/>
			</div>
			
		</div>
  
		<!--  Contenido página -->
		<div id="contenidoPagina">
		<div id="globalErrors">
					        <spring:bind path="user">
								<div class="mens_error"><c:out value="${status.errorMessage}" /></div>
							</spring:bind>
		</div>
			 
		<div id="login">
			 	
					   
						<form:form method="post" action="login.do" commandName="user">
						<table >
					
								<tr>
									<td><spring:message code="login.code"/>:<div class="mens_error"><form:errors path="code" cssClass="mens_error" /></div></td>
								
									<td><form:input path="code" /></td>
								</tr>
								<tr>
									<td><spring:message code="login.password"/>:<div class="mens_error"><form:errors path="password"  cssClass="mens_error"/></div></td>
								
									<td><form:password path="password" /></td>
								</tr>																
								<tr>
								 
									<td id="p10_tdAceptar"><input type="submit" id="p10_btn_aceptar"  class="boton  botonHover" value='<spring:message code="login.acept" />' /></td>
										
									<td id="p10_tdCancelar"><input type="button" id="p10_btn_cancelar" name="p10_btn_cancelar" class="boton  botonHover" value='<spring:message code="login.cancel" />'/></td>
										 			 								
								</tr>
							</table>
						</form:form>	
				
				
			 		<!-- <tr>
			 			<td class="etiquetaCampo"><label id="p10_lbl_usuario">Usuario:</label></td>
			 			<td><input type=text id="p10_fld_usuario"></input></td>
			 		</tr>
			 		<tr>
			 			<td class="etiquetaCampo" ><label id="p10_lbl_clave">Clave:</label></td>
			 			<td><input type=password id="p10_fld_clave"></input></td>
			 		</tr> -->
			
			 	</div>
			 	<!-- <div id="loginButtons"> -->
	 			<!-- 	<input type="button" id="p10_btn_aceptar" class="boton  botonHover" value="Aceptar"></input> --> 
	 				<!-- <input type="button" id="p10_btn_cancelar" class="boton  botonHover" value="Cancelar"></input> -->		 			 
			 	<!-- </div> --> <!-- end div loginButtons -->
			 <!-- </div>-->
		</div>	<!-- end div contenidoPagina -->
	
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>