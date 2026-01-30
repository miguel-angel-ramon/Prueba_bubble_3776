<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p97_selReposicion.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pdaP40SelFiabilidad.do" name="actionVolver"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p97_selReposicion_titulo">
				<label id="pda_p97_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p97_selReposicion.titulo" /></label>
			</div>
			<!-- De momento dejamos las opciones sin mostrar hasta que se vayan abordando las distintas opciones -->
			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '64_PDA_RESTOS_ALMACEN'))}">
				<div id="pda_p97_selReposicion_bloque1">
					<a href="./pdaP99SacadaRestos.do" class="pda_p97_menuLink">
						<div class="pda_p97_div_enlace">
							<span class="pda_p97_selReposicionTitulo"><spring:message code="pda_p97_selReposicion.sacada.restos.almacen" /></span>
						</div>
					</a>
				</div>
			</c:if>
			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '63_PDA_CAPTURA_RESTOS'))}">
				<div id="pda_p97_selReposicion_bloque2">
					<a href="./pdaP98CapturaRestos.do" class="pda_p97_menuLink">
						<div class="pda_p97_div_enlace">
							<span class="pda_p97_selReposicionTitulo"><spring:message code="pda_p97_selReposicion.sacada.pedido.nuevo" /></span>
						</div>
					</a>
				</div>
			</c:if>


 
	  		<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '60_PDA_LISTADO_REPO'))}">
				<div id="pda_p97_selReposicion_bloque3">
					<a href="./pdaP96AreaSel.do?tipoListado=1" class="pda_p97_menuLink">
						<div class="pda_p40_div_enlace2">
							<span class="pda_p97_selReposicionTitulo"><spring:message code="pda_p97_selReposicion.listadoRepo" /></span>
						</div>
					</a>
				</div>
			</c:if>	
			
			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '61_PDA_LISTADO_REPO_VENTA'))}">
				<div id="pda_p97_selReposicion_bloque4">
					<a href="./pdaP92ListadoRepoAnt.do?tipoListado=2" class="pda_p97_menuLink">
						<div class="pda_p40_div_enlace2">
							<span class="pda_p97_selReposicionTitulo"><spring:message code="pda_p97_selReposicion.listadoVentaRepo" /></span>
						</div>
					</a>
				</div>
			</c:if>
			
			
		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>