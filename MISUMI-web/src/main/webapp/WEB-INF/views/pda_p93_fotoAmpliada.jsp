<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p93_fotoAmpliada.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p93FotoAmpliada.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP93FotoAmpliada.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p93_fotoAmpliada_contenido">
				<div id="pda_p93_fotoAmpliada_foto">						
					<img id="pda_p93_img_foto" src="./pdaGetImageP93.do?codArtFoto=${codArtFoto}"/>	
				</div>	
				<form id="pda_p93_fotoAmpliada_form" action="${origen}" method="get">
					<input type="hidden" id="seccion" name="seccion" value="${seccion}">						
					<input type="hidden" id="pagina" name="pagina" value="${paginaActual}">	
					<input type="hidden" id="pgSubList" name="pgSubList" value="${pgSubList}">	
				</form>						
			</div>
		</div>