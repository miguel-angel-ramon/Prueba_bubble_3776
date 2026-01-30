<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p96_areas.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p96_areas_titulo">
				<label id="pda_p96_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p96_areas.titulo" /></label>
			</div>
			<div id="pda_p96_areas_bloque1">
				<a href="./pdaP90ListRepoSelOperativa.do?area=TEXTIL" class="pda_p96_menuLink">
					<div class="pda_p96_div_enlace">
						<span class="pda_p96_areasTitulo"><spring:message code="pda_p96_areas.textil" /></span>
					</div>	
				</a>
			</div>					
			<div id="pda_p96_areas_bloque2">
				<a href="./pdaP90ListRepoSelOperativa.do?area=NO_TEXTIL" class="pda_p96_menuLink">
					<div class="pda_p96_div_enlace">
						<span class="pda_p96_areasTitulo"><spring:message code="pda_p96_areas.noTextil" /></span>
					</div>	
				</a>
			</div>			
		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>