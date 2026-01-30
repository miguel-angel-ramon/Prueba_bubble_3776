<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p91_listRepo.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p91ListRepo.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP91ListadoRepo.do" name="actionRef"></jsp:param>
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
					<div id="pda_p91_listRepo_titulo">
						<label id="pda_p91_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p91_listRepo.titulo" /></label>
					</div>
					<div id="pda_p91_listRepo_bloqueReferenciaColor">
						
						<c:choose>
							<c:when test="${reposicion.area eq 'TEXTIL'}">
								<div id="pda_p91_listRepo_bloqueReferencia">
									<input id="pda_p91_fld_descripcionRef" data-mp ="${reposicion.modeloProveedor}" name="" class="" type="text" value="${reposicion.modeloProveedor}-${reposicion.denominacion}" readonly/>
								</div>
							</c:when>
							<c:otherwise>	
								<div id="pda_p91_listRepo_bloqueReferencia">
									<input id="pda_p91_fld_descripcionRef" data-mp ="${reposicion.modeloProveedor}" name="" class="" type="text" value="${reposicion.reposicionLineas[0].codArticulo}-${reposicion.denominacion}" readonly/>
								</div>					
							</c:otherwise>
						</c:choose>
	
						<c:choose>
							<c:when test="${reposicion.area eq 'TEXTIL'}">
								<div id="pda_p91_listRepo_bloqueColor">
									<label id="pda_p91_fld_color" class=""><spring:message code="pda_p91_listRepo.lbl.color" /></label>
									<c:choose>
										<c:when test="${fn:length(reposicion.descrColor) > 20}">
											<a id="pda_p91_listRepo_bloqueColor_descrLink" class="pda_p91_fontSize" href ="#">${fn:substring(reposicion.descrColor,0,20)}</a>
											<label id="pda_p91_fld_queColor" style="display:none">${reposicion.descrColor}</label>				
										</c:when>
										<c:otherwise>
											<label id="pda_p91_fld_queColor" class="">${reposicion.descrColor}</label>								
										</c:otherwise>								
									</c:choose>
								</div>
							</c:when>
						</c:choose>
					</div>
					<div id="pda_p91_listRepo_bloqueError">													
						<label id="pda_p91_fld_errorColor" class="p91_colorError"></label>						
					</div>				
					<div id="pda_p91_listRepo_bloqueFotoStockRepo">
						<div id="pda_p91_listRepo_bloqueFoto">
							<c:choose>
								<c:when test="${reposicion.codArtFoto eq null}">
									<span class="pda_p91_helper"></span>
									<img id="" class="pda_p91_fotoPistola" src="./misumi/images/nofotoPda.gif">							
								</c:when>
								<c:otherwise>
									<span class="pda_p91_helper"></span>
									<img id="pda_p91_listRepo_foto" class="pda_p91_fotoPistola" data-codArtFoto="${reposicion.codArtFoto}" src="./pdaGetImageP91.do?codArticulo=${reposicion.codArtFoto}">							
								</c:otherwise>
							</c:choose>
						</div>
						<div id="pda_p91_listRepo_bloqueStockRepo">
							<div id="pda_p91_listRepo_bloqueStockRepo_titulos">
								<div class="pda_p91_listRepo_bloqueStockRepo_descr">&nbsp;</div>
								<div class="pda_p91_listRepo_bloqueStockRepo_inputStock pda_p91_listRepo_titleBold"><spring:message code="pda_p91_listRepo.lbl.stock" /></div>	
								<div class="pda_p91_listRepo_bloqueStockRepo_inputRepo pda_p91_listRepo_titleBold"><spring:message code="pda_p91_listRepo.lbl.repo" /></div>												
							</div>
							<div id="pda_p91_listRepo_bloqueStockRepo_filas">
								<c:forEach items="${pageSubListaReposicion.rows}" var="reposicionLinea">									
										<div class="pda_p91_listRepo_bloqueStockRepo_fila">									
											<c:choose>
												<c:when test="${reposicion.area eq 'TEXTIL'}">
													<div class="pda_p91_listRepo_bloqueStockRepo_descr">																				
														<a class="pda_p91_listRepo_bloqueStockRepo_descrLink pda_p91_fontSize" href ="#">${fn:substring(reposicionLinea.descrTalla,0,5)}</a>
														<label id="pda_p91_listRepo_bloqueStockRepo_descrLink${fn:substring(reposicionLinea.descrTalla,0,5)}" data-codArtTalla="${reposicionLinea.codArticulo}" style="display:none">${reposicionLinea.descrTalla}</label>																																	
													</div>
												</c:when>
												<c:otherwise>	
													<div class="pda_p91_listRepo_bloqueStockRepo_descr">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</div>					
												</c:otherwise>
											</c:choose>
											
											<div class="pda_p91_listRepo_bloqueStockRepo_inputStock">
												<c:choose>
													<c:when test="${reposicionLinea.flgPantCorrStock eq 'N'}">												
														<input data-codArt="${reposicionLinea.codArticulo}" class="pda_p91_listRepo_bloqueStockRepo_stock pda_p91_fontSize" type="number" step="0.01" value="${reposicionLinea.stock}">
														<input id="pda_p91_listRepo_bloqueStockRepo_stock${reposicionLinea.codArticulo}" type="hidden" value="${reposicionLinea.stock}">
													</c:when>
													<c:when test="${reposicionLinea.flgPantCorrStock eq 'S'}">
														<c:choose>
															<c:when test="${reposicionLinea.stock % 1 != 0.0}">		
																<c:choose>
																	<c:when test="${!empty (guardadoStockOk)}">
																		<a href="#" class="pda_p91_listRepo_bloqueStockRepo_stockLink pda_p91_fontSize pda_p91_enlaceOk" data-codArt="${reposicionLinea.codArticulo}">${fn:replace(reposicionLinea.stock,'.',',')}</a>
																	</c:when>
																	<c:otherwise>
																		<a href="#" class="pda_p91_listRepo_bloqueStockRepo_stockLink pda_p91_fontSize" data-codArt="${reposicionLinea.codArticulo}">${fn:replace(reposicionLinea.stock,'.',',')}</a>
																	</c:otherwise>
																</c:choose>																								
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${!empty (guardadoStockOk)}">
																		<fmt:formatNumber value="${reposicionLinea.stock}" minFractionDigits="0" maxFractionDigits="0" var="stock"/>
																		<a href="#" class="pda_p91_listRepo_bloqueStockRepo_stockLink pda_p91_fontSize pda_p91_enlaceOk" data-codArt="${reposicionLinea.codArticulo}">${stock}</a>
																	</c:when>
																	<c:otherwise>
																		<fmt:formatNumber value="${reposicionLinea.stock}" minFractionDigits="0" maxFractionDigits="0" var="stock"/>
																		<a href="#" class="pda_p91_listRepo_bloqueStockRepo_stockLink pda_p91_fontSize" data-codArt="${reposicionLinea.codArticulo}">${stock}</a>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>														
													</c:when>
													<c:otherwise>
														<label class="pda_p91_fontSize">${reposicionLinea.flgPantCorrStock}</label>
													</c:otherwise>
												</c:choose>
											</div>
											<div class="pda_p91_listRepo_bloqueStockRepo_inputRepo">
												<input data-codArt="${reposicionLinea.codArticulo}" class="pda_p91_listRepo_bloqueStockRepo_repo pda_p91_fontSize" type="number" step="0.01" value="${reposicionLinea.cantRepo}">
												<input id="pda_p91_listRepo_bloqueStockRepo_repo${reposicionLinea.codArticulo}" type="hidden" value="${reposicionLinea.cantRepo}">
											</div>	
										</div>	
								</c:forEach>
							</div>		
							<!-- Si no existe más de una paginación no mostramos la paginación. -->															
							<c:if test="${pageSubListaReposicion.total ne 1}">
								<div id="pda_p91_listRepo_bloqueStockRepo_paginacion">
									<div id="pda_p91_listRepo_bloqueStockRepo_pagAnt" class="pda_p91_listRepo_bloqueStockRepo_pag">
										<c:choose>
											<c:when test="${pageSubListaReposicion.page eq '1'}">
												<img src="./misumi/images/pager_prev_des_24.gif?version=" class="pda_p91_listRepo_bloqueStockRepo_flecha" class="botonPaginacionDeshabilitado">
											</c:when>
											<c:otherwise>
											 	<a href="./pdaP91ListadoRepo.do?pgSubList=${pageSubListaReposicion.page}&pgTotSubList=${pageSubListaReposicion.total}&botPag=prevPag"> 
													<img src="./misumi/images/pager_prev_24.gif?version=" class="pda_p91_listRepo_bloqueStockRepo_flecha">
												</a>
											</c:otherwise>
										</c:choose>							
									</div>
									<div id="pda_p91_listRepo_bloqueStockRepo_numeroPagina" class="pda_p91_listRepo_bloqueStockRepo_pag pda_p91_fontSize" data-pagina="${pageSubListaReposicion.page}">
										${pageSubListaReposicion.page}/${pageSubListaReposicion.total}
									</div>
									<div id="pda_p91_listRepo_bloqueStockRepo_pagSig" class="pda_p91_listRepo_bloqueStockRepo_pag">
										<c:choose>
											<c:when test="${pageSubListaReposicion.page eq pageSubListaReposicion.total}">
												<img src="./misumi/images/pager_next_des_24.gif?version=" class="pda_p91_listRepo_bloqueStockRepo_flecha" class="botonPaginacionDeshabilitado">
											</c:when>
											<c:otherwise>
												<a href="./pdaP91ListadoRepo.do?pgSubList=${pageSubListaReposicion.page}&pgTotSubList=${pageSubListaReposicion.total}&botPag=nextPag"> 
													<img src="./misumi/images/pager_next_24.gif?version=" class="pda_p91_listRepo_bloqueStockRepo_flecha">
											 	</a> 
											</c:otherwise>
										</c:choose>										
									</div>
								</div>
							</c:if>						
							<form id="pda_p91_listRepo_stock_form" action="pdaP91ListadoRepoStock.do" method="POST">
								<input type="hidden" id="pda_p91_listRepo_paginaStk" name="paginaStk" value="${reposicion.posicion}">	
								<input type="hidden" id="pda_p91_listRepo_codArtStk" name="codArtStk" value="">			
								<input type="hidden" id="pda_p91_listRepo_paginaTallaStk" name="paginaTallaStk" value="${pageSubListaReposicion.page}">			
							</form>
						</div>
					</div>
					
					<div id="pda_p91_listRepo_bloqueFlechasBoton">
						<div id="pda_p91_listRepo_bloqueFlechasBoton_paginacion">
								<div id="pda_p91_listRepo_bloqueFlechasBoton_pagPrimera" class="pda_p91_listRepo_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq 1}">
											<img src="./misumi/images/pager_first_des_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP91ListadoRepoPaginar.do">
												<img src="./misumi/images/pager_first_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pda_p91_listRepo_bloqueFlechasBoton_pagAnt" class="pda_p91_listRepo_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq 1}">
											<img src="./misumi/images/pager_prev_des_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP91ListadoRepoPaginar.do?pagina=${reposicion.posicion - 1}">
												<img src="./misumi/images/pager_prev_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pda_p91_listRepo_bloqueFlechasBoton_numeroPagina" class="pda_p91_listRepo_bloqueFlechasBoton_pag" data-pagina="${reposicion.posicion}">
									${reposicion.posicion}/${reposicion.paginasTotales}
								</div>
								<div id="pda_p91_listRepo_bloqueFlechasBoton_pagSig" class="pda_p91_listRepo_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq reposicion.paginasTotales}">											
											<img src="./misumi/images/pager_next_des_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP91ListadoRepoPaginar.do?pagina=${reposicion.posicion + 1}">
												<img src="./misumi/images/pager_next_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pda_p91_listRepo_bloqueFlechasBoton_pagUltima" class="pda_p91_listRepo_bloqueFlechasBoton_pag">
									<c:choose>
										<c:when test="${reposicion.posicion eq reposicion.paginasTotales}">
											<img src="./misumi/images/pager_last_des_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
										</c:when>
										<c:otherwise>
											<a href="pdaP91ListadoRepoPaginar.do?pagina=${reposicion.paginasTotales}">
												<img src="./misumi/images/pager_last_24.gif?version=" class="pda_p91_listRepo_bloqueFlechasBoton_flecha">
										 	</a>
										</c:otherwise>
									</c:choose>							
								</div>
						</div>
						<div id="pda_p91_listRepo_bloqueBoton">					
							<div id="pda_p91_listRepo_btn_eliminar" class="pda_p91_listRepo_bloqueBoton_btn">
								<img id="pda_p91_listRepo_bloqueBoton_borrar" src="./misumi/images/delete_48.gif?version=">
								<form id="pda_p91_listRepo_borrar_form" action="pdaP91ListadoRepoBorrar.do" method="POST">
									<input type="hidden" id="modeloProveedor" name="modeloProveedor" value="${reposicion.modeloProveedor}">				
									<input type="hidden" id="descrColor" name="descrColor" value="${reposicion.descrColor}">				
									<input type="hidden" id="codArt" name="codArt" value="${reposicion.codArtFoto}">
									<input type="hidden" id="pagina" name="pagina" value="${reposicion.posicion}">	
									<input type="hidden" id="paginasTotales" name="paginasTotales" value="${reposicion.paginasTotales}">
									<input type="hidden" id="pgSubList" name="pgSubList" value="${pageSubListaReposicion.page}">											
								</form>	
							</div>
							<div id="pda_p91_listRepo_btn_fin" class="pda_p91_listRepo_bloqueBoton_btn">
								<img id="pda_p91_listRepo_bloqueBoton_finalizar" src="./misumi/images/finalizar_45.gif?version=">
								
								<form id="pda_p91_listRepo_finalizar_form" action="pdaP91ListadoRepoFinalizar.do" method="POST">
									<!--  <input type="hidden" id="pagina" name="pagina" value="${reposicion.posicion}">	-->
									<input type="hidden" id="pgSubList" name="pgSubList" value="${pageSubListaReposicion.page}">							
								</form>
							</div>
						</div>
						
						
						
					</div>
				</c:if>			
				<c:if test="${empty reposicion}">
					<div id="pda_p91_errorListadoRepo_error">						
						<label id="pda_p91_errorListadoRepo_lbl_error">${msgError}</label>
					</div>
				</c:if>							
		</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>