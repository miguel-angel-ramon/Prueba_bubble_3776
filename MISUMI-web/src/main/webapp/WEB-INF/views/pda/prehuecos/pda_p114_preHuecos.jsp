<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda/prehuecos/pda_p114_prehuecos.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP114PrehuecosLineal.do"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p114_prehuecos_titulo">
				<label id="pda_p114_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p114_preHuecos.titulo"/></label>
			</div>
			
			<!-- Prehuecos Lineal -->
			<div id="pda_p114_prehuecos_bloque1">
				<a href="./pdaP114PrehuecosSelOperativa.do?operativa=S" class="pda_p114_menuLink">
					<div class="pda_p114_div_enlace1">
						<span class="pda_p114_prehuecosTitulo"><spring:message code="pda_p114_preHuecos.lineal"/></span>
					</div>
				</a>
			</div>

			<!-- Stock Almacen -->
			<div id="pda_p114_prehuecos_bloque2">
				<a href="./pdaP116PrehuecosStockAlmacen.do" class="pda_p114_menuLink">
					<div class="pda_p114_div_enlace2">
						<span class="pda_p114_prehuecosTitulo"><spring:message code="pda_p114_preHuecos.stockAlmacen"/></span>
					</div>
				</a>
			</div>

		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>