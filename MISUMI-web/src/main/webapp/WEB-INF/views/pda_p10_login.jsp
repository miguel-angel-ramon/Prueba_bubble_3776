<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%! String lan; %>
<% lan = "es"; %>
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

<link href="./misumi/styles/pda_p10_login.css?version=${misumiVersion}" media="all" type="text/css" rel="stylesheet" />

<script src="./misumi/scripts/pda_p10Login.js?version=${misumiVersion}" type="text/javascript"></script>

<title>misumi</title>
<link rel="shortcut icon" href="./misumi/images/misumi.ico" type="image/x-icon" />
</head>

<body> 
	<div id="marco">  <!-- Se cierra en p02_footer.jsp -->

		<!-- Cabecera de página -->
		<div id="cabeceraLogin">
			<div id="LogoLogin">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="40px" width="1px"  />
				<img src="./misumi/images/logo_misumi_pda.gif?version=${misumiVersion}" style="vertical-align: middle;"  class="imagenLogoLogin"/>
			</div>
			<div id="Logo2Login">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="40px" width="1px"  />
				<img src="./misumi/images/logo_eroski_pda.gif?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogo2Login"/>
				<img src="./misumi/images/logo_caprabo_pda.gif?version=${misumiVersion}" style="vertical-align: middle;" class="imagenLogo2Login"/>
			</div>
			<div id="LogoCerrarLogin">
				<img src="./misumi/images/blanco.jpg?version=${misumiVersion}" style="vertical-align: middle;" height="15px" width="1px"  />
				<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" style="vertical-align: middle;" id="pda_p10_imagenCerrar" class="imagenCerrarCabecera" title='<spring:message code="pda_p01_header.cerrar" />'/>
			</div>
		</div>
		<!--  Contenido página -->
		<div id="contenidoPagina">
		<div id="globalErrors">
							<c:if test="${not empty welcomeGetError}">
								<div class="mens_error">${welcomeGetError}</div>
							</c:if>
					        <!--<spring:bind path="user">-->
								<div class="mens_error"><c:out value="${status.errorMessage}" /></div>
							<!--</spring:bind>-->
		</div>
		<div id="login">
						<form:form method="post" action="pdawelcome.do" commandName="user">
						<table >
								<tr>
									<td class="pda_p10_columnaIzda"><spring:message code="login.code"/>:<div class="mens_error"><form:errors path="code" cssClass="mens_error" /></div></td>
									<td class="pda_p10_columnaDcha"><form:input path="code" class="input85"/></td>
								</tr>
								<tr>
								 
									<td id="p10_tdAceptar" class="pda_p10_columnaIzda"><input type="submit" id="pda_p10_btn_aceptar"  class="boton  botonHover" value='<spring:message code="login.acept" />' /></td>
									<td id="p10_tdCancelar" class="pda_p10_columnaDcha"><input type="button" id="pda_p10_btn_cancelar" name="p10_btn_cancelar" class="boton  botonHover" value='<spring:message code="login.cancel" />'/></td>
								</tr>
							</table>
						</form:form>	
			 	</div>
		</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>