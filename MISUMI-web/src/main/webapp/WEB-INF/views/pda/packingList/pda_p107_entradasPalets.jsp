<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String currentUrl = request.getRequestURL().toString(); %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda/packingList/pda_p107_entradasPalets.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda/packingList/pda_p107EntradasPalets.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP107EntradasPalets.do"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
<div id="contenidoPagina">
    <div id="pda_p107_entradasPalets_titulo">
        <label id="pda_p107_lbl_titulo"><spring:message code="pda_p107_entradasPalets.titulo" /></label>
    </div>
    <div id="pda_p107_entradasPalets_contenido">
        <form:form id="palet" method="post" action="pdaP108ConsultasMatricula.do" modelAttribute="palet">			
            <div id="pda_p107_entradasPalets_areaLinks">
            	<!-- Contenedor de paginación anclado a la parte inferior -->
				<!-- Detectar si el navegador es IE6 usando el User-Agent -->
				<c:set var="isIE6" value="false" />
				<c:if test="${fn:contains(header['User-Agent'], 'MSIE 6.0')}">
				    <c:set var="isIE6" value="true" />
				</c:if>
                <c:choose>
                    <c:when test="${existeEntradasPalets}">
	                    <input type="hidden" id="urlActual" name="urlPalet" value="<%= currentUrl %>" /> 
	                    <input type="hidden" id="soyPalet" name="soyPalet" value="true" />
	                    <input id="pda_p12_matricula" type="hidden" name="matricula" value="" />
	                    <input id="matricula" type="hidden"  name="matricula" value="" />                    
	                    <c:forEach items="${pagPalet.rows}" var="palet" varStatus="loop">
	                        <!-- Usamos el formulario exterior y modificamos el valor del input dinámicamente -->
	                        <div class="bloqueEntradaPalet" onclick="enviarClick('${palet.matricula}');">
	                            <div id="pda_p107_datosEntradaPalet">
	                                <div id="pda_p107_imagenBarcode">
	                                    <img src="./misumi/images/barcode.png?version=${misumiVersion}" height="25px" width="25px">
	                                </div>
	                                <div id="pda_p107_matriculaPalets">
	                                    <span>${palet.matricula}</span>
	                                </div>
	                                <div id="pda_p107_plataformaPalets">
	                                    <span>${palet.plataforma}</span>
	                                </div>
	                                <img id="pda_p107_imagenTick" src="./misumi/images/tick_82x82.png?version=${misumiVersion}" height="15px" width="15px">
	                                <div id="pda_p107_frecepcionPalets"style="${isIE6 ? 'left: 35px;' : ''}">
	                                    <span>${palet.frecepcionFormateada}&nbsp;&nbsp;${palet.hrecepcionFormateada}</span>
	                                </div>
	                            </div>
	                        </div>
	                    </c:forEach>
	                </c:when>
                    <c:otherwise>
                        <div id="pda_p107_noHayDevolucionesError" class="mens_error">
                            <spring:message code="pda_p107_noHayEntradasPalets.error"/>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div>

	<!-- Contenedor de paginación anclado a la parte inferior -->
<!-- Detectar si el navegador es IE6 usando el User-Agent -->
<c:set var="isIE6" value="false" />
<c:if test="${fn:contains(header['User-Agent'], 'MSIE 6.0')}">
    <c:set var="isIE6" value="true" />
</c:if>

	<!-- Sección de la botonera de paginación -->
	            <c:choose>
	                <c:when test="${pagPalet.total>0}">
	                    <div id="pda_p107_entradasPalets_areaFlechas"
	                    style="text-align: center; overflow: hidden; ${isIE6 ? 'position: absolute; bottom: 0; left: 0; right: 0;' : ''}">
	                        <div id="bloqueFlechas" style="display: inline-block; *display: inline; zoom: 1; vertical-align: middle; margin-left: 20px;">
	                            <div id="pagPrimera" class="paginacion">
	                                <c:choose>
	                                    <c:when test="${pagPalet.page eq '1'}">
	                                        <img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
	                                    </c:when>
	                                    <c:otherwise>
	                                        <a href="./pdaP107Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=firstPalet">
	                                            <img id="pda_p107_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
	                                        </a>
	                                    </c:otherwise>
	                                </c:choose>
	                            </div>
	                            <div id="pagAnt" class="paginacion">
	                                <c:choose>
	                                    <c:when test="${pagPalet.page eq '1'}">
	                                        <img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
	                                    </c:when>
	                                    <c:otherwise>
	                                        <a href="./pdaP107Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=prevPalet">
	                                            <img id="pda_p107_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
	                                        </a>
	                                    </c:otherwise>
	                                </c:choose>						
	                            </div>
	                            <div id="numeroPagina" class="paginacion">
	                                ${pagPalet.page}/${pagPalet.total}
	                            </div>
	                            <div id="pagSig" class="paginacion">
	                                <c:choose>
	                                    <c:when test="${pagPalet.page eq pagPalet.total}">
	                                        <img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
	                                    </c:when>
	                                    <c:otherwise>
	                                        <a href="./pdaP107Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=nextPalet">
	                                            <img id="pda_p107_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
	                                        </a>
	                                    </c:otherwise>
	                                </c:choose>						
	                            </div>
	                            <div id="pagUltima" class="paginacion">
	                                <c:choose>
	                                    <c:when test="${pagPalet.page eq pagPalet.total}">
	                                        <img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
	                                    </c:when>
	                                    <c:otherwise>
	                                        <a href="./pdaP107Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=lastPalet">
	                                            <img id="pda_p107_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
	                                        </a>
	                                    </c:otherwise>
	                                </c:choose>						
	                            </div>
	                        </div>
	                    </div>
	                </c:when>
	            </c:choose>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>