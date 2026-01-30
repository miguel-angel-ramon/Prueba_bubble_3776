<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp">
    <jsp:param name="cssFile" value="pda/prehuecos/pda_p118_PrehuecosAlmacen.css?version=${misumiVersion}" />
    <jsp:param name="jsFile" value="pda/prehuecos/pda_p118PrehuecoAlmacen.js?version=${misumiVersion}" />
    <jsp:param name="flechaMenu" value="S" />
    <jsp:param name="actionRef" value="pdaP118PrehuecosAlmacen.do" />
</jsp:include>
<script src="./misumi/scripts/model/ReposicionGuardar.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<!-- Detectar IE6 -->
<c:set var="isIE6" value="false" />
<c:if test="${fn:contains(header['User-Agent'], 'MSIE 6.0')}">
    <c:set var="isIE6" value="true" />
</c:if>


<c:choose>
  <c:when test="${guardadoOK}">
    <c:set var="linkClass" value="link-verde"/>
  </c:when>
  <c:otherwise>
    <c:set var="linkClass" value="link-azul"/>
  </c:otherwise>
</c:choose>
<c:if test="${not empty listaAlmacen}">
  <c:set var="firstCodArt" value="${listaAlmacen[0].codArt}" />
</c:if>
<div id="contenidoPagina">
    <c:choose>
        <c:when test="${not empty listaAlmacen}">
            <div id="pda_p117_stockAlmacen_contenido">
                <%-- <form:form method="post" action="pdaP118PrehuecosAlmacen.do" modelAttribute="pdaDatosCab"> --%>
				<form:form id="formPrehuecos" method="post" action="${pageContext.request.contextPath}/pdaP118PrehuecosAlmacen.do" modelAttribute="pdaDatosCab">

                    <!-- Título -->
                    <div id="pda_p118_stockAlmacen_tituloComun">
                        <label class="etiquetaCampoNegrita">
                            <spring:message code="pda_p118_PrehuecosAlmacen.Almacen" />
                        </label>
                        <span style="margin: 0 10px;">|</span>
            			<a href="${pageContext.request.contextPath}/pdaP115PrehuecosLineal.do?codArt=${firstCodArt}"
              				class="etiquetaCampoNegrita"
              				style="color: blue; text-decoration: none; text-decoration: underline;">
              				<spring:message code="pda_p118_PrehuecosAlmacen.Prehuecos"/>
            			</a>
					</div>

                    <c:forEach items="${listaAlmacen}" var="referencia">

                        <!-- Código de artículo y descripción en input readonly -->
						<div id="pda_p118_bloqueReferencia">
							<input type="text" readonly
								value="${referencia.codArt} - ${referencia.descArt}"
								style="width: 90%; text-align: center; background-color: transparent;" />
						</div>
						<div id="pda_p118_bloqueFotoStock">
							<div id="pda_p118_bloqueFoto">
								<!-- Imagen -->
								<c:choose>
									<c:when test="${not referencia.tieneFoto}">
										<span class="pda_p118_helper"></span>
											<img class="pda_p118_fotoPistola" src="./misumi/images/nofotoPda.gif" />
									</c:when>
									<c:otherwise>
										<span class="pda_p118_helper"></span>
											<img id="pda_p118_foto" class="pda_p118_fotoPistola" data-codArtFoto="${referencia.codArt}" 
											src="./pdaGetImageP91.do?codArticulo=${referencia.codArt}">
									</c:otherwise>
								</c:choose>
							</div>
							<div id="pda_p118_bloqueStock">
	
								<!-- LINEAL y STOCK -->
								<div id="pda_p118_bloqueStock_titulos">
									<span class="etiquetaCampoNegrita"><spring:message code="pda_p118_PrehuecosAlmacen.lineal" /></span>
									<span class="etiquetaCampoNegrita" style="margin-left: 25px;"><spring:message code="pda_p118_PrehuecosAlmacen.Stock" /></span>
									<span class="etiquetaCampoNegrita" style="margin-left: 70px;"><spring:message code="pda_p118_PrehuecosAlmacen.Total" /></span>
								</div>
		
								<!-- Valores -->
								<div id="pda_p118_bloqueStock_filas">
									
									<div id="pda_p118_bloqueStock_valorLineal">
										<label class="etiquetaStockLineal">${referencia.stockLineal}</label>
									</div>
									<%-- <input type="text" name="stock" class="etiquetaCampoNegrita" value="${referencia.stock}" style="width: 63px; text-align: center; margin-left: 20px;" /> --%>
									<div id="pda_p118_bloqueStock_valorStock">
									<c:choose>
										<c:when test="${referencia.flgPantCorrStock == 'N'}">
										        <input type="text"
											            maxlength="6"
											            class="stock-field"
											            value="${referencia.stock}"
											            onfocus="this.select();"
											            onblur="enviarStock(this);"
											            onkeydown="var e=event||window.event;
											                       if((e.keyCode||e.which)==13){
											                           if(e.preventDefault) e.preventDefault();
											                           else e.returnValue=false;
											                           enviarStock(this);
											                       }"
											            data-codArt="${referencia.codArt}"
											            id="stockInput_${referencia.codArt}"
												/>
												<input type="hidden" class="codArtInput" value="${referencia.codArt}"/>
										</c:when>
																				
										<c:when test="${referencia.flgPantCorrStock == 'S'}">
											<a href="./pdaP28CorreccionStockInicio.do?codArt=${fn:escapeXml(referencia.codArt)}
													&origen=DR&mmc=${fn:escapeXml(referencia.MMC)}
													&calculoCC=${fn:escapeXml(referencia.calculoCC)}
													&origenPantalla=${fn:escapeXml(origenPantalla)}"
													class="etiquetaCampoNegrita ${linkClass}${isIE6 ? ' ie6' : ''}">
													<c:choose>
														<c:when test="${empty referencia.stock}">0</c:when>
														<c:otherwise>${referencia.stock}</c:otherwise>
													</c:choose>
											</a>
											<input type="hidden" id="stock_${referencia.codArt}" value="${referencia.stock}"/>
										</c:when>
		
										<c:otherwise>
											<label class="etiquetaCampoNegrita" style="margin-left: 20px;${isIE6 ? ' font-size: medium;margin-left: 20px; color: blue; text-decoration: none' : ''}">
												${referencia.flgPantCorrStock} </label>
										</c:otherwise>
									</c:choose>
									</div>
								</div>
							</div>
		
		                        <!-- Iconos de estado -->
								<div id="estadoIconos">
								    <div class="iconoEstadoItem">
										<div class="iconoClickWrapper icono-click">
									        <img src="./misumi/images/icono_tick.png?version=${misumiVersion}"
											     class="iconoEstado "
											     data-valor="2"
											     data-codart="${referencia.codArt}" />
											<span class="textoEstado ${referencia.estado == 2 ? 'subrayado' : ''}">
												<spring:message code="pda_p118_PrehuecosAlmacen.Imagen.tick" />
											</span>
										</div>
									</div>
									<div class="iconoEstadoItem">
										<div class="iconoClickWrapper icono-click">
											<img src="./misumi/images/icono_pendiente.png?version=${misumiVersion}"
												class="iconoEstado"
												data-valor="1"
												data-codart="${referencia.codArt}" />
											<span class="textoEstado ${referencia.estado == 1 ? 'subrayado' : ''}">
												<spring:message code="pda_p118_PrehuecosAlmacen.Imagen.pendiente" />
											</span>
										</div>
									</div>
									<div class="iconoEstadoItem ">
										<div class="iconoClickWrapper icono-click">
											<img src="./misumi/images/icono_aspa.png?version=${misumiVersion}"
											     class="iconoEstado"
											     data-valor="0"
											     data-codart="${referencia.codArt}" />
											<span class="textoEstado ${referencia.estado == 0 ? 'subrayado' : ''}">
												<spring:message code="pda_p118_PrehuecosAlmacen.Imagen.aspa" />
											</span>
										</div>
									</div>
								</div>
							</div>
                        <!-- Ocultos -->
						<input type="hidden" class="codArtHidden" value="${referencia.codArt}" />
                    </c:forEach>
                </form:form>
            </div>
        </c:when>
        <c:otherwise>
            <div id="pda_p117_noHayReferenciasError" class="mens_error">
                <spring:message code="pda_p117_stockAlmacen.errorNoReferencias" />
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>