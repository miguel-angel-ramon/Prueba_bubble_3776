<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda/packingList/pda_p106_packingList.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p106_packingList_titulo">
				<label id="pda_p106_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p106_PackingList.titulo"/></label>
			</div>
			
			<div id="pda_p106_packingList_bloque1">
				<a href="./pdaP107EntradasPalets.do" class="pda_p106_menuLink">
					<div class="pda_p106_div_enlace1">
						<span class="pda_p106_packingListTitulo"><spring:message code="pda_p106_PackingList.entradasPalets"/></span>
					</div>
				</a>
			</div>

			<!-- Comprobar si el centro está parametrizado -->
			<c:if test = "${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '125_PDA_CONSULTA_PACKING_LIST')}">
				<div id="pda_p106_packingList_bloque2">
					<a href="./pdaP109ConsultasReferencia.do?origenGISAE=SI" class="pda_p106_menuLink">
						<div class="pda_p106_div_enlace2">
							<span class="pda_p106_packingListTitulo"><spring:message code="pda_p106_PackingList.consultaPorReferencia"/></span>
						</div>
					</a>
				</div>

				<div id="pda_p106_packingList_bloque3">
					<a href="./pdaP108ConsultasMatricula.do" class="pda_p106_menuLink">
						<div class="pda_p106_div_enlace3">
							<span class="pda_p106_packingListTitulo"><spring:message code="pda_p106_PackingList.consultaPorMatricula" /></span>
						</div>
					</a>
				</div>
			</c:if>

		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>