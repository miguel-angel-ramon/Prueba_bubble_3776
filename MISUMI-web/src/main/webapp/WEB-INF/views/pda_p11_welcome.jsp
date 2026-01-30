<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p11_welcome.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaWelcome.do" name="actionRef"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<c:set var="user" value='<%=request.getSession().getAttribute("user")%>'/>

			<!-- ----- Pet. 55590 Gestion de menú dependiendo del cod_zona del centro ----- -->
			<c:choose>
				<c:when test="${user.centro.esCentroCaprabo}"> 
					<c:set var="escaprabo" value='true'/>
				</c:when>
				<c:otherwise>
					<c:set var="escaprabo" value='false'/>
				</c:otherwise>
			</c:choose>
			
			<div id="pda_p11_welcome_titulo">
				<label id="pda_p11_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p11_welcome.titulo" /></label>
			</div>
			<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '17_PDA_SFM'))}">
				<div id="pda_p11_welcome_bloque1">
				<a href="./pdaP21Sfm.do?menu=PDA_SFM" class="pda_p11_menuLink1">
					<div class="pda_p11_div_enlace">
						<span class="pda_p11_welcomeTitulo">
							<c:choose>
								<c:when test="${user.centro.descripBotonSfmCapacidad != null}">
									<c:out value="${user.centro.descripBotonSfmCapacidad}" />
								</c:when>
								<c:otherwise>
									<spring:message code="pda_p11_welcome.menuSfm" />
								</c:otherwise>
							</c:choose>
						</span>
					</div>
				</a>
			</div>
			</c:if>
			
			<div id="pda_p11_welcome_bloque2">
				<a href="./pdaP12DatosReferencia.do?menu=PDA_DR&cleanAll=I" class="pda_p11_menuLink1">
					<div class="pda_p11_div_enlace2">
						<span class="pda_p11_welcomeTitulo"><spring:message code="pda_p11_welcome.menuDatosRef" /></span>
					</div>
				</a>
			</div>
			<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '18_PDA_VENTA_ANTICIPADA'))}">
				<div id="pda_p11_welcome_bloque3">
					<a href="./pdaP25VentaAnticipada.do?menu=PDA_VA" class="pda_p11_menuLink1">
						<div class="pda_p11_div_enlace2">
							<span class="pda_p11_welcomeTitulo"><spring:message code="pda_p11_welcome.menuVentaAnt" /></span>
						</div>
					</a>
				</div>
			</c:if>
			<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '19_PDA_QUE_HACER'))}">
				<div id="pda_p11_welcome_bloque4">
					<a href="./pdaP32QueHacerRef.do?menu=PDA_QHR" class="pda_p11_menuLink1">
						<div class="pda_p11_div_enlace2">
							<span class="pda_p11_welcomeTitulo"><spring:message code="pda_p11_welcome.menuQueHacerRef" /></span>
						</div>
					</a>
				</div>
			</c:if>

			<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && (misumi:contieneOpcion(user.centro.opcHabil, '20_PDA_REVISION_HUECOS') || misumi:contieneOpcion(user.centro.opcHabil, '21_PDA_INVENTARIO_LIBRE') || misumi:contieneOpcion(user.centro.opcHabil, '26_PDA_DEVOLUCIONES')))}">	
				<div id="pda_p11_welcome_bloque5">
					<a href="./pdaP40SelFiabilidad.do?menu=PDA_SF" class="pda_p11_menuLink1">
						<div class="pda_p11_div_enlace2">
							<span class="pda_p11_welcomeTitulo"><spring:message code="pda_p11_welcome.menuFiabilidadStock" /></span>
						</div>
					</a>
				</div>
			</c:if>
			
			<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '125_PDA_PACKING_LIST'))}">
				<div id="pda_p11_welcome_bloque6">
					<a href="./pdaP106PackingList.do" class="pda_p11_menuLink1">
						<div class="pda_p11_div_enlace2">
							<span class="pda_p11_welcomeTitulo"><spring:message code="pda_p11_welcome.menuPackingList" /></span>
						</div>
					</a>
				</div>
			</c:if>

		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>