<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p40_selFiabilidad.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p40_selFiabilidad_titulo">
				<label id="pda_p40_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p40_selFiabilidad.titulo" /></label>
			</div>
			<!-- De momento dejamos las opciones sin mostrar hasta que se vayan abordando las distintas opciones -->
			<c:if test="${user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '20_PDA_REVISION_HUECOS'))}">
			<div id="pda_p40_selFiabilidad_bloque1">
				<a href="./pdaP47RevisionHuecos.do" class="pda_p40_menuLink">
					<div class="pda_p40_div_enlace">
						<span class="pda_p40_selFiabilidadTitulo"><spring:message code="pda_p40_selFiabilidad.revisionHuecos" /></span>
					</div>
				</a>
			</div>
			</c:if>
			<c:if test="${user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '20_PDA_REVISION_HUECOS'))}">
			<div id="pda_p40_selFiabilidad_bloque2">
				<a href="./pdaP42InventarioLibre.do?origenGISAE=SI" class="pda_p40_menuLink">
					<div class="pda_p40_div_enlace3">
						<span class="pda_p40_selFiabilidadTitulo"><spring:message code="pda_p40_selFiabilidad.menuInventarioPropuestoSistema" /></span>
					</div>
				</a>
			</div>
			</c:if>
			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '21_PDA_INVENTARIO_LIBRE'))}">
				<c:choose>
					<c:when test="${user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '20_PDA_REVISION_HUECOS'))}">
						<div id="pda_p40_selFiabilidad_bloque3">
					</c:when>
					<c:otherwise>
						<div id="pda_p40_selFiabilidad_bloque1">
					</c:otherwise>
				</c:choose>
				<a href="./pdaP41InvLibSelOperativa.do?menu=PDA_ILS" class="pda_p40_menuLink">
					<div class="pda_p40_div_enlace2">
						<span class="pda_p40_selFiabilidadTitulo"><spring:message code="pda_p40_selFiabilidad.menuInventarioLibre" /></span>
					</div>
				</a>
				</div>
			<!--  
			<div style="width:200px;height:20px;clear:left;position:absolute;top:160px;left:20px;border:1px solid #123456;">
			
					<label id="pda_p01_lbl_centerHeaderData" class="etiquetaCampo">mac:<c:out value="${user.mac}"/></label>
			</div>	
			-->
			</c:if>

			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '26_PDA_DEVOLUCIONES'))}">
				<div id="pda_p40_selFiabilidad_bloque4">
					<a href="./pdaP60RealizarDevolucion.do" class="pda_p40_menuLink">
						<div class="pda_p40_div_enlace2">
							<span class="pda_p40_selFiabilidadTitulo"><spring:message code="pda_p40_selFiabilidad.realizarDevolucion" /></span>
						</div>
					</a>
				</div>
			</c:if>		
			
			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && 
			(misumi:contieneOpcion(user.centro.opcHabil, '60_PDA_LISTADO_REPO') || misumi:contieneOpcion(user.centro.opcHabil, '61_PDA_LISTADO_REPO_VENTA') || misumi:contieneOpcion(user.centro.opcHabil, '63_PDA_CAPTURA_RESTOS') || misumi:contieneOpcion(user.centro.opcHabil, '64_PDA_RESTOS_ALMACEN') )
			)}">
				<div id="pda_p40_selFiabilidad_bloque5">
					<a href="./pdaP97SelReposicion.do" class="pda_p40_menuLink">
						<div class="pda_p40_div_enlace">
							<span class="pda_p40_selFiabilidadTitulo"><spring:message code="pda_p40_selFiabilidad.reposicion" /></span>
						</div>
					</a>
				</div>
			</c:if>
			
			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '127_PDA_PREHUECOS'))}">
				<div id="pda_p40_selFiabilidad_bloque6">
					<a href="./pdaP114PrehuecosLineal.do" class="pda_p40_menuLink">
						<div class="pda_p40_div_enlace">
							<span class="pda_p40_selFiabilidadTitulo"><spring:message code="pda_p40_selFiabilidad.menuPreHuecosLineal" /></span>
						</div>
					</a>
				</div>
			</c:if>		

		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>