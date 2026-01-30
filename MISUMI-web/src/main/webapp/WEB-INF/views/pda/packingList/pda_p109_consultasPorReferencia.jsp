<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/includes/pda_p02_header.jsp" >
    <jsp:param name="cssFile" value="pda/packingList/pda_p109_ConsutasPorReferencia.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda/packingList/pda_p109ConsutasPorReferencia.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP109ConsultasReferencia.do"></jsp:param>
</jsp:include>

</head>
<div id="contenidoPagina">


	<div id="pda_p108_consultaPorMatricula_titulo">
		<label id="pda_p108_lbl_titulo"><spring:message
				code="pda_p108_consultaPorReferencia.titulo" /> </label>
	</div>

<c:choose>
    <c:when test="${(existeEntradasPalets) and (not empty pagPalet.rows)}">
       <input id="pda_p108_articuloDescripcion" name="descArtConCodigo"
                       class="input225 linkInput" type="text"
                       value="${pagPalet.rows[0].articulo} - ${pagPalet.rows[0].descripcion}"
                       readonly =""
                       
                       
                       >




        <c:forEach items="${pagPalet.rows}" var="articulo">
            <div id="pda_p108_datosArticulo" class="registros">
            

				<div id="pda_p108_matriculaArticulo">
					<span><strong style="font-weight: bold;">Mat: </strong>${articulo.matricula}</span>
				</div>
				

                <div id="pda_p108_albaranFecha">
                    <span><strong style="font-weight: bold;">Alb: </strong> ${articulo.albaran}</span>
                    <span><strong>  </strong> ${articulo.formattedFechaAlbaran}</span>
                </div>

               

                <div id="pda_p108_bultosArticulo">
					<span><strong style="font-weight: bold;">Bul:</strong>${articulo.bultos}</span>&nbsp;
					<span><strong style="font-weight: bold;">Cant:</strong> ${articulo.cantidad}</span>&nbsp;
					<span>${articulo.tipoCantidad}</span>&nbsp;&nbsp;
					<span>${articulo.alta == true ? 'ALTA' : ''}</span>
				</div>
            </div>
        </c:forEach>
        
        
        
    </c:when>
    
    
    <c:when test="${empty pagPalet.rows}">
        <div style="color: red; font-weight: bold; text-align: center; padding: 20px;">
            <spring:message code="pda_p108_noHayMatricula.error" />
        </div>
    </c:when>
    
   
   
    
   
                       
</c:choose>




<div>


<c:set var="isIE6" value="false" />
<c:if test="${fn:contains(header['User-Agent'], 'MSIE 6.0')}">
    <c:set var="isIE6" value="true" />
</c:if>


<c:choose>
    <c:when test="${pagPalet.total > 0}">
        <div id="pda_p107_entradasPalets_areaFlechas" 
             style="text-align: center; overflow: hidden; ${isIE6 ? 'position: absolute; bottom: 0; left: 0; right: 0;' : ''}">

            <div id="bloqueFlechas" style="display: inline-block; *display: inline; zoom: 1; vertical-align: middle; margin-left: 20px;">
                

                <div id="pagPrimera" class="paginacion" style="float: left; margin-right: 5px;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq '1'}">
                            <img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Primera página deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP109Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=firstPalet">
                                <img id="pda_p107_lnk_first"
                                     src="./misumi/images/pager_first_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la primera página">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>


                <div id="pagAnt" class="paginacion" style="float: left; margin-right: 5px;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq '1'}">
                            <img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Página anterior deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP109Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=prevPalet">
                                <img id="pda_p107_lnk_prev"
                                     src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la página anterior">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>


                <div id="numeroPagina" class="paginacion" style="float: left; margin-right: 10px; margin-left: 10px; line-height: 16px; vertical-align: middle;">
                    ${pagPalet.page}/${pagPalet.total}
                </div>


                <div id="pagSig" class="paginacion" style="float: left; margin-right: 5px;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq pagPalet.total}">
                            <img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Página siguiente deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP109Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=nextPalet">
                                <img id="pda_p107_lnk_next"
                                     src="./misumi/images/pager_next_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la página siguiente">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>


                <div id="pagUltima" class="paginacion" style="float: left;">
                    <c:choose>
                        <c:when test="${pagPalet.page eq pagPalet.total}">
                            <img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}"
                                 class="botonPaginacionDeshabilitado" alt="Última página deshabilitada">
                        </c:when>
                        <c:otherwise>
                            <a href="./pdaP109Paginar.do?pgPalet=${pagPalet.page}&pgTotPalet=${pagPalet.total}&botPag=lastPalet">
                                <img id="pda_p107_lnk_last"
                                     src="./misumi/images/pager_last_24.gif?version=${misumiVersion}"
                                     class="botonPaginacion" alt="Ir a la última página">
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div style="clear: both;"></div>
            </div>
        </div>
    </c:when>
</c:choose>
</div>
<input id="pda_p12_codArt" type="hidden" value="${pagPalet.rows[0].articulo}" />
					<input id="pda_p12_tieneFoto" type="hidden" value="${pagPalet.rows[0].tieneFoto == true ? 'S' : 'N'}" />
					<input id="pda_p12_fechaDesde" type="hidden" value="${pdaDatosCab.getFechaDesde()}" />
					<input id="pda_p12_fechaHasta" type="hidden" value="${pdaDatosCab.getFechaHasta()}" />
		
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>
