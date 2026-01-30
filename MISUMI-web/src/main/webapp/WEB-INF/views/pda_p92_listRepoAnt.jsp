<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p92_listRepoAnt.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p92ListRepoAnt.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP92ListadoRepoAnt.do" name="actionRef"></jsp:param>
</jsp:include>
<script src="./misumi/scripts/model/ReposicionGuardar.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<c:if test="${user.mac != null && user.perfil != 3 && (user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '62_PDA_LISTADO_REPO'))}">
	<script src="./misumi/scripts/utils/json2.js?version=${misumiVersion}" type="text/javascript"></script>
</c:if>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
				<c:if test="${not empty reposicion}">
					<div id="pda_p92_listRepoAnt_titulo">
						<c:choose>
							<c:when test="${reposicion.tipoListado eq 1}">
								<label id="pda_p92_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p92_listRepoAnt.titulo1" /></label>
							</c:when>
							<c:otherwise>
								<label id="pda_p92_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p92_listRepoAnt.titulo2" /></label>					
							</c:otherwise>
						</c:choose>	
					</div>
					<div id="pda_p92_listRepoAnt_bloqueReferenciaColor">
						<c:choose>
							<c:when test="${reposicion.area eq 'TEXTIL'}">
								<div id="pda_p92_listRepoAnt_bloqueReferencia">
									<input id="pda_p92_fld_descripcionRef" name="" class="" type="text" data-mp ="${reposicion.modeloProveedor}" value="${reposicion.modeloProveedor}-${reposicion.denominacion}" readonly/>
								</div>
								<div id="pda_p92_listRepoAnt_bloqueColor">
									<label id="pda_p92_fld_color" class=""><spring:message code="pda_p92_listRepoAnt.lbl.color" /></label>
									<c:choose>
										<c:when test="${fn:length(reposicion.descrColor) > 20}">
											<a id="pda_p92_listRepoAnt_bloqueColor_descrLink" class="pda_p92_fontSize" href ="#">${fn:substring(reposicion.descrColor,0,20)}</a>
											<label id="pda_p92_fld_queColor" style="display:none">${reposicion.descrColor}</label>				
										</c:when>
										<c:otherwise>
											<label id="pda_p92_fld_queColor" class="">${reposicion.descrColor}</label>							
										</c:otherwise>								
									</c:choose>
									
								</div>
							</c:when>
							<c:otherwise>	
								<div id="pda_p91_listRepo_bloqueReferencia">
									<input id="pda_p92_fld_descripcionRef" data-mp ="${reposicion.modeloProveedor}" name="" class="" type="text" value="${reposicion.reposicionLineas[0].codArticulo}-${reposicion.denominacion}" readonly/>
								</div>					
							</c:otherwise>
						</c:choose>
					</div>	
					<div id="pda_p92_listRepoAnt_bloqueError">													
						<label id="pda_p92_fld_errorColor" class="p92_colorError"></label>						
					</div>				
					<div id="pda_p92_listRepoAnt_bloqueFotoStockRepo">
						<div id="pda_p92_listRepoAnt_bloqueFotoRevSust">
							<div id="pda_p92_listRepoAnt_bloqueFoto">
								<c:choose>
									<c:when test="${reposicion.codArtFoto eq null}">	
										<span class="pda_p92_helper"></span>								
										<img id="" class="pda_p92_fotoPistola" src="./misumi/images/nofotoPda.gif">							
									</c:when>
									<c:otherwise>
										<span class="pda_p92_helper"></span>
										<img id="pda_p92_listRepoAnt_foto" class="pda_p92_fotoPistola" data-codArtFoto="${reposicion.codArtFoto}" src="./pdaGetImageP92.do?codArticulo=${reposicion.codArtFoto}">							
									</c:otherwise>
								</c:choose>		
							</div>	
							<div id="pda_p92_listRepoAnt_bloqueBotonRevisadaSustituida">
								<c:choose>
									<c:when test="${reposicion.flgRevisada eq 'S'}">
										<div id="pda_p92_listRepoAnt_bloqueBotonRevisada" class="pda_p92_greenRevSust p92_btn" data-codArtRevSust="R" data-codArt="${reposicion.reposicionLineas[0].codArticulo}">
											<label id="pda_p92_btn_revisada" class=""><spring:message code="pda_p92_listRepoAnt.btn.revisada" /></label>
										</div>
										<div id="pda_p92_listRepoAnt_bloqueBotonSustituida" data-revSust="S" data-codArtRevSust="${reposicion.reposicionLineas[0].codArticulo}" class="p92_btn">					
											<label id="pda_p92_btn_sustituida" class=""><spring:message code="pda_p92_listRepoAnt.btn.sustituida" /></label>
										</div>
									</c:when>
									<c:when test="${reposicion.flgSustituida eq 'S'}">
										<div id="pda_p92_listRepoAnt_bloqueBotonRevisada" data-revSust="R" data-codArtRevSust="${reposicion.reposicionLineas[0].codArticulo}" class="p92_btn">
											<label id="pda_p92_btn_revisada" class=""><spring:message code="pda_p92_listRepoAnt.btn.revisada" /></label>
										</div>
										<div id="pda_p92_listRepoAnt_bloqueBotonSustituida" class="pda_p92_greenRevSust p92_btn" data-codArtRevSust="S" data-codArt="${reposicion.reposicionLineas[0].codArticulo}">					
											<label id="pda_p92_btn_sustituida" class=""><spring:message code="pda_p92_listRepoAnt.btn.sustituida" /></label>
										</div>
									</c:when>
									<c:otherwise>
										<div id="pda_p92_listRepoAnt_bloqueBotonRevisada" data-revSust="R" data-codArtRevSust="${reposicion.reposicionLineas[0].codArticulo}" class="p92_btn">
											<label id="pda_p92_btn_revisada" class=""><spring:message code="pda_p92_listRepoAnt.btn.revisada" /></label>
										</div>
										<div id="pda_p92_listRepoAnt_bloqueBotonSustituida" data-revSust="S" data-codArtRevSust="${reposicion.reposicionLineas[0].codArticulo}" class="p92_btn">					
											<label id="pda_p92_btn_sustituida" class=""><spring:message code="pda_p92_listRepoAnt.btn.sustituida" /></label>
										</div>
									</c:otherwise>
								</c:choose>						
							</div>			
						</div>
					
						<div id="pda_p92_listRepoAnt_bloqueStockRepo">
							<div id="pda_p92_listRepoAnt_bloqueStockRepo_titulos">
								<div class="pda_p92_listRepoAnt_bloqueStockRepo_descr">&nbsp;</div>
								<div class="pda_p92_listRepoAnt_bloqueStockRepo_inputStock pda_p92_listRepoAnt_titleBold"><spring:message code="pda_p92_listRepoAnt.lbl.stock" /></div>	
								<div class="pda_p92_listRepoAnt_bloqueStockRepo_inputRepo pda_p92_listRepoAnt_titleBold"><spring:message code="pda_p92_listRepoAnt.lbl.repo" /></div>												
							</div>
							<div id="pda_p92_listRepoAnt_bloqueStockRepo_filas">
								<c:forEach items="${pageSubListaReposicion.rows}" var="reposicionLinea">	
										<div class="pda_p92_listRepoAnt_bloqueStockRepo_fila">
											<c:choose>
												<c:when test="${reposicion.area eq 'TEXTIL'}">
													<div class="pda_p92_listRepoAnt_bloqueStockRepo_descr">												
														<a class="pda_p92_listRepoAnt_bloqueStockRepo_descrLink pda_p92_fontSize" href ="#">${fn:substring(reposicionLinea.descrTalla,0,5)}</a>
														<label id="pda_p92_listRepoAnt_bloqueStockRepo_descrLink${fn:substring(reposicionLinea.descrTalla,0,5)}" data-codArtTalla="${reposicionLinea.codArticulo}" style="display:none">${reposicionLinea.descrTalla}</label>																	
													</div>
												</c:when>
												<c:otherwise>	
													<div class="pda_p92_listRepoAnt_bloqueStockRepo_descr">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</div>					
												</c:otherwise>
											</c:choose>
											<div class="pda_p92_listRepoAnt_bloqueStockRepo_inputStock">
												<c:choose>
													<c:when test="${reposicionLinea.flgPantCorrStock eq 'N'}">												
														<input data-codArt="${reposicionLinea.codArticulo}" class="pda_p92_listRepoAnt_bloqueStockRepo_stock pda_p92_fontSize" type="number" step="0.01" value="${reposicionLinea.stock}">
														<input id="pda_p92_listRepoAnt_bloqueStockRepo_stock${reposicionLinea.codArticulo}" type="hidden" value="${reposicionLinea.stock}">
													</c:when>
													<c:when test="${reposicionLinea.flgPantCorrStock eq 'S'}">
														<c:choose>
															<c:when test="${reposicionLinea.stock % 1 != 0.0}">	
																<c:choose>
																	<c:when test="${!empty (guardadoStockOk)}">
																		<a href="#" class="pda_p92_listRepoAnt_bloqueStockRepo_stockLink pda_p92_fontSize pda_p92_enlaceOk" data-codArt="${reposicionLinea.codArticulo}">${fn:replace(reposicionLinea.stock,'.',',')}</a>																								
																	</c:when>
																	<c:otherwise>																
																		<a href="#" class="pda_p92_listRepoAnt_bloqueStockRepo_stockLink pda_p92_fontSize" data-codArt="${reposicionLinea.codArticulo}">${fn:replace(reposicionLinea.stock,'.',',')}</a>																								
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${!empty (guardadoStockOk)}">
																		<fmt:formatNumber value="${reposicionLinea.stock}" minFractionDigits="0" maxFractionDigits="0" var="stock"/>
																		<a href="#" class="pda_p92_listRepoAnt_bloqueStockRepo_stockLink pda_p92_fontSize pda_p92_enlaceOk" data-codArt="${reposicionLinea.codArticulo}">${stock}</a>
																	</c:when>
																	<c:otherwise>
																		<fmt:formatNumber value="${reposicionLinea.stock}" minFractionDigits="0" maxFractionDigits="0" var="stock"/>
																		<a href="#" class="pda_p92_listRepoAnt_bloqueStockRepo_stockLink pda_p92_fontSize" data-codArt="${reposicionLinea.codArticulo}">${stock}</a>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>														
													</c:when>
													<c:otherwise>
														<label class="pda_p92_fontSize">${reposicionLinea.flgPantCorrStock}</label>
													</c:otherwise>
												</c:choose>
											</div>	
											<div class="pda_p92_listRepoAnt_bloqueStockRepo_inputRepo">
												<input data-codArt="${reposicionLinea.codArticulo}" class="pda_p92_listRepoAnt_bloqueStockRepo_repo pda_p92_fontSize" type="number" step="0.01" value="${reposicionLinea.cantRepo}">
												<input id="pda_p92_listRepoAnt_bloqueStockRepo_repo${reposicionLinea.codArticulo}" type="hidden" value="${reposicionLinea.cantRepo}">
											</div>	
										</div>
								</c:forEach>
							</div>	
							<c:if test="${pageSubListaReposicion.total ne 1}">									
								<div id="pda_p92_listRepoAnt_bloqueStockRepo_paginacion">
									<div id="pda_p92_listRepoAnt_bloqueStockRepo_pagAnt" class="pda_p92_listRepoAnt_bloqueStockRepo_pag">
										<c:choose>
											<c:when test="${pageSubListaReposicion.page eq '1'}">
												<img src="./misumi/images/pager_prev_des_24.gif?version=" class="pda_p92_listRepoAnt_bloqueStockRepo_flecha" class="botonPaginacionDeshabilitado">
											</c:when>
											<c:otherwise>
											 	<a href="./pdaP92ListadoRepoAnt.do?pagina=${reposicion.posicion}&pgSubList=${pageSubListaReposicion.page}&pgTotSubList=${pageSubListaReposicion.total}&botPag=prevPag&seccion=${reposicion.seccion}"> 
													<img src="./misumi/images/pager_prev_24.gif?version=" class="pda_p92_listRepoAnt_bloqueStockRepo_flecha">
												</a>
											</c:otherwise>
										</c:choose>							
									</div>
									<div id="pda_p92_listRepoAnt_bloqueStockRepo_numeroPagina" class="pda_p92_listRepoAnt_bloqueStockRepo_pag" data-pagina="${pageSubListaReposicion.page}">
										${pageSubListaReposicion.page}/${pageSubListaReposicion.total}
									</div>
									<div id="pda_p92_listRepoAnt_bloqueStockRepo_pagSig" class="pda_p92_listRepoAnt_bloqueStockRepo_pag">
										<c:choose>
											<c:when test="${pageSubListaReposicion.page eq pageSubListaReposicion.total}">
												<img src="./misumi/images/pager_next_des_24.gif?version=" class="pda_p92_listRepoAnt_bloqueStockRepo_flecha" class="botonPaginacionDeshabilitado">
											</c:when>
											<c:otherwise>
												<a href="./pdaP92ListadoRepoAnt.do?pagina=${reposicion.posicion}&pgSubList=${pageSubListaReposicion.page}&pgTotSubList=${pageSubListaReposicion.total}&botPag=nextPag&seccion=${reposicion.seccion}"> 
													<img src="./misumi/images/pager_next_24.gif?version=" class="pda_p92_listRepoAnt_bloqueStockRepo_flecha">
											 	</a> 
											</c:otherwise>
										</c:choose>
										
									</div>
								</div>
							</c:if>	
							<form id="pda_p92_listRepoAnt_stock_form" action="pdaP92ListadoRepoAntStock.do" method="POST">
								<input type="hidden" id="pda_p92_listRepoAnt_seccionStk" name="seccionStk" value="${reposicion.seccion}">
								<input type="hidden" id="pda_p92_listRepoAnt_paginaStk" name="paginaStk" value="${reposicion.posicion}">	
								<input type="hidden" id="pda_p92_listRepoAnt_codArtStk" name="codArtStk" value="">	
								<input type="hidden" id="pda_p92_listRepoAnt_paginaTallaStk" name="paginaTallaStk" value="${pageSubListaReposicion.page}">					
							</form>
						</div>
					</div>
					<div id="pda_p92_listRepoAnt_bloqueFlechasBoton">
						<div id="pda_p92_listRepoAnt_bloqueFlechasBoton_paginacion">
								<div id="pda_p92_listRepoAnt_bloqueFlechasBoton_pagPrimera" class="pda_p92_listRepoAnt_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq 1}">
											<img src="./misumi/images/pager_first_des_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP92ListadoRepoAnt.do?seccion=${reposicion.seccion}">
												<img src="./misumi/images/pager_first_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pda_p92_listRepoAnt_bloqueFlechasBoton_pagAnt" class="pda_p92_listRepoAnt_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq 1}">
											<img src="./misumi/images/pager_prev_des_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP92ListadoRepoAnt.do?pagina=${reposicion.posicion - 1}&seccion=${reposicion.seccion}">
												<img src="./misumi/images/pager_prev_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pda_p92_listRepoAnt_bloqueFlechasBoton_numeroPagina" class="pda_p92_listRepoAnt_bloqueFlechasBoton_pag" data-pagina="${reposicion.posicion}">	
									<c:choose>
										<c:when test="${reposicion.paginasTotales eq 1}">
												${reposicion.posicion}/${reposicion.paginasTotales}
										</c:when>
										<c:otherwise>
											<a id="pda_p92_sigVacio" class="pda_p92_fontSize" href="./pdaP92ListadoRepoAnt.do?pagina=${reposicion.posicion}&sigPosVacia=S&seccion=${reposicion.seccion}">
												${reposicion.posicion}/${reposicion.paginasTotales}
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pda_p92_listRepoAnt_bloqueFlechasBoton_pagSig" class="pda_p92_listRepoAnt_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq reposicion.paginasTotales}">											
											<img src="./misumi/images/pager_next_des_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP92ListadoRepoAnt.do?pagina=${reposicion.posicion + 1}&seccion=${reposicion.seccion}">
												<img src="./misumi/images/pager_next_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pda_p92_listRepoAnt_bloqueFlechasBoton_pagUltima" class="pda_p92_listRepoAnt_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq reposicion.paginasTotales}">
											<img src="./misumi/images/pager_last_des_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP92ListadoRepoAnt.do?pagina=${reposicion.paginasTotales}&seccion=${reposicion.seccion}">
												<img src="./misumi/images/pager_last_24.gif?version=" class="pda_p92_listRepoAnt_bloqueFlechasBoton_flecha">
										 	</a>
										</c:otherwise>
									</c:choose>							
								</div>
						</div>
					</div>
				</c:if>
				<c:if test="${empty reposicion}">
					<div id="pda_p92_errorListadoRepoAnt_error">						
						<label id="pda_p92_errorListadoRepoAnt_lbl_error">${msgError}</label>
					</div>
				</c:if>
		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>