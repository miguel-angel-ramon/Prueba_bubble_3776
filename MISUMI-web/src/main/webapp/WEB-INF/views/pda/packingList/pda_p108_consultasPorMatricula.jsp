<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/pda_p03_header.jsp" >
    <jsp:param name="cssFile" value="pda/packingList/pda_p108_ConsutasPorMatricula.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda/packingList/pda_p108ConsultasPorMatricula.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP108ConsultasMatricula.do"></jsp:param>
</jsp:include>

</head>
<div id="contenidoPagina">
	

	<div id="pda_p108_consultaPorMatricula_contenido">
		<form:form method="post" action="${param.actionRef}"
			modelAttribute="pdaDatosCab">
			<div id="pda_p108_consultaPorMatricula_areaLinks">
			
			<div id="pda_p108_consultaPorMatricula_titulo">
		<label id="pda_p108_lbl_titulo"><spring:message
				code="pda_p108_consultaPorMatricula.titulo" /> </label>
	</div>


				<c:choose>
					<c:when test="${existeEntradasPalets}">
						<!-- Mostrar Matrícula fuera del borde y en negrita -->
						<div id="pda_p108_matriculaArticulo">
							<strong style="font-weight: bold;">Matricula:</strong> ${pagPalet.rows[0].matricula}
						</div>

						<c:forEach items="${pagPalet.rows}" var="articulo">
							<div id="pda_p108_datosArticulo" style="border-bottom: 1px solid black; max-width: 210px;">


								<div id="pda_p108_albaranFecha">
									<span><strong style="font-weight: bold;">Alb:</strong> ${articulo.albaran} &nbsp;&nbsp; ${articulo.formattedFechaAlbaran}</span> 
								</div>

								<input id="pda_p108_articuloDescripcion" name="descArtConCodigo"
									class="input225 linkInput pda_p108_articuloDescripcion" type="text"
									value="${articulo.articulo} - ${articulo.descripcion}"
									readonly=""
									<%-- data-tieneFoto="${articulo.tieneFoto == true ? 'S' : 'N'}"
									data-matricula="${pagPalet.rows[0].matricula}" --%>
									
									
									>


								<input id="pda_p12_pgTotal" type="hidden" value="${pagPalet.total}" />
							<%-- 	<input id="pda_p12_botPag" type="hidden" value="${pagPalet.botPag}" /> --%>
								<input id="pda_p12_pgPalet" type="hidden" value="${pagPalet.page}" />
								 <input id="pda_p12_tieneFoto" type="hidden" value="${articulo.tieneFoto == true ? 'S' : 'N'}" />
									<input id="pda_p12_matricula" type="hidden" value="${pagPalet.rows[0].matricula}" />

								<div id="pda_p108_bultosArticulo">
									<span><strong style="font-weight: bold;">Bul:</strong>${articulo.bultos}</span>&nbsp;
										<span><strong style="font-weight: bold;">Cant:</strong> ${articulo.cantidad}</span>&nbsp;
									<span>${articulo.tipoCantidad}</span>&nbsp;&nbsp;
									<span>${articulo.alta == true ? 'ALTA' : ''}</span>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
					<div style="color: red; font-weight: bold; text-align: center; padding: 20px;">
                            <spring:message code="pda_p108_noHayMatricula.error" />
                        </div>
					</c:otherwise>
				</c:choose>



			</div>
		</form:form>
	</div>
</div>

<div>
	<!-- Contenedor de paginación anclado a la parte inferior -->
<!-- Detectar si el navegador es IE6 usando el User-Agent -->
<c:set var="isIE6" value="false" />
<c:if test="${fn:contains(header['User-Agent'], 'MSIE 6.0')}">
    <c:set var="isIE6" value="true" />
</c:if>

<!-- Contenedor de paginación -->
<c:choose>
    <c:when test="${pagPalet.total > 0}">
        <div id="pda_p107_entradasPalets_areaFlechas" 
             style="text-align: center; overflow: hidden; ${isIE6 ? 'position: absolute; bottom: 0; left: 0; right: 0;' : ''}">
            <!-- Contenedor para las flechas -->
            <div id="bloqueFlechas" style="display: inline-block; *display: inline; zoom: 1; vertical-align: middle; margin-right: 24px;">
                
                <!-- Paginación Primera -->
                <div id="pagPrimera" class="paginacion" style="float: left; margin-right: 5px;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq '1'}">
                            <img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Primera página deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP108Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=firstPalet&paraPalet=${paraPalet}">
                                <img id="pda_p107_lnk_first"
                                     src="./misumi/images/pager_first_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la primera página">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Paginación Anterior -->
                <div id="pagAnt" class="paginacion" style="float: left; margin-right: 5px;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq '1'}">
                            <img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Página anterior deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP108Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=prevPalet&paraPalet=${paraPalet}">
                                <img id="pda_p107_lnk_prev"
                                     src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la página anterior">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Número de Página -->
                <div id="numeroPagina" class="paginacion" style="float: left; margin-right: 10px; line-height: 24px; vertical-align: middle; margin-left: 10px; font-weight: bold;">
                    ${pagPalet.page}/${pagPalet.total}
                </div>

                <!-- Paginación Siguiente -->
                <div id="pagSig" class="paginacion" style="float: left; margin-right: 5px;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq pagPalet.total}">
                            <img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Página siguiente deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP108Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=nextPalet&paraPalet=${paraPalet}">
                                <img id="pda_p107_lnk_next"
                                     src="./misumi/images/pager_next_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la página siguiente">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Paginación Última -->
                <div id="pagUltima" class="paginacion" style="float: left;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq pagPalet.total}">
                            <img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Última página deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP108Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=lastPalet&paraPalet=${paraPalet}">
                                <img id="pda_p107_lnk_last"
                                     src="./misumi/images/pager_last_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la última página">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Limpiar flotados para evitar que el contenedor se desajuste -->
                <div style="clear: both;"></div>
            </div>
        </div>
    </c:when>
</c:choose>
</div>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>
