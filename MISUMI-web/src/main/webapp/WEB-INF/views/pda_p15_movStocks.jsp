<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="actionRefValue" value="pdaP15MovStocks.do" />
<c:if test="${procede eq 'pdaP115PrehuecosLineal'}">
    <c:set var="origenValue" value="PHStock"/>
    <c:set var="procede" value="pdaP115PrehuecosLineal"/>
</c:if>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda_p15_movStocks.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda_p15MovStocks.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="${actionRefValue}"></jsp:param>
    <jsp:param name="origen" value="${origenValue}"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<c:choose>
				<c:when test="${empty (pdaMovStocks.codArt)}">
					<div id="pda_p15_movStocks_bloque1">
						<div id="pda_p15_movStocks_bloque1_subBloque1">
							<c:choose>
								<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
									<label id="pda_p15_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.prehueco" /></label>
								</c:when>
								<c:otherwise>
									<label id="pda_p15_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.datosReferencia" /></label>
								</c:otherwise>
							</c:choose>

							<label id="pda_p15_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.separadorEnlaces" /></label>
							<label id="pda_p15_lbl_segPedidos" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.segPedidos" /></label>
							<label id="pda_p15_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.separadorEnlaces" /></label>
							<label id="pda_p15_lbl_movStocks" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.movStocks" /></label>
						</div>
						<div id="pda_p15_movStocks_bloque1_subBloque2">						
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="pda_p15_origenGISAE" id="pda_p15_origenGISAE" value="${origenGISAE}"/>
					<div id="pda_p15_movStocks_bloque1">
						<div id="pda_p15_movStocks_bloque1_subBloque1">
							<c:choose>
								<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
									<c:choose>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP115PrehuecosLineal.do?mostrarFFPP=N&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&cleanAll=I"><spring:message code="pda_p15_movStocks.prehuecoEnlace"/></a>
										</c:when>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP115PrehuecosLineal.do?mostrarFFPP=S&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&cleanAll=I"><spring:message code="pda_p15_movStocks.prehuecoEnlace"/></a>
										</c:when>
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP115PrehuecosLineal.do?codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&cleanAll=I"><spring:message code="pda_p15_movStocks.prehuecoEnlace"/></a>
										</c:otherwise>	
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP12DatosReferencia.do?mostrarFFPP=N&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p15_movStocks.datosReferenciaEnlace" /></a>
										</c:when>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP12DatosReferencia.do?mostrarFFPP=S&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p15_movStocks.datosReferenciaEnlace" /></a>
										</c:when>
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP12DatosReferencia.do?codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p15_movStocks.datosReferenciaEnlace" /></a>
										</c:otherwise>	
									</c:choose>		
								</c:otherwise>
							</c:choose>
							<label id="pda_p15_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.separadorEnlaces" /></label>
							<c:choose>
								<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
									<c:choose>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=N&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p15_movStocks.segPedidosEnlace" /></a>
										</c:when>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=S&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p15_movStocks.segPedidosEnlace" /></a>
										</c:when>	
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p15_movStocks.segPedidosEnlace" /></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=N&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p15_movStocks.segPedidosEnlace" /></a>
										</c:when>
										<c:when test="${pdaMovStocks.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=S&codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p15_movStocks.segPedidosEnlace" /></a>
										</c:when>	
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?codArt=${pdaMovStocks.codArt}&codArtRel=${pdaMovStocks.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p15_movStocks.segPedidosEnlace" /></a>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>	
							<label id="pda_p15_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.separadorEnlaces" /></label>
							<label id="pda_p15_lbl_movStocks" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p15_movStocks.movStocks" /></label>
					    </div>
						<c:if test="${procede == null || procede ne 'pdaP115PrehuecosLineal'}">
						    <c:choose>
						   		<c:when test="${not empty pedidoPda.msgGamaLibre}">
									<!--  MISUMI-278: En lugar de bloquear el link de lanzar encargo, mostrar un mensaje indicando que para lanzar el encargo tiene que incluirlo en gama libre --> 
									<a onclick="alert('<c:out value='${pedidoPda.msgGamaLibre}'/>' + '&euro;')"><spring:message code="pda_p15_movStocks.lanzarEnc" /></a>
								</c:when>
								<c:when test="${pedidoPda.mostrasLinkLanzarEncargo eq true}">
									<div id="pda_p15_movStocks_bloque1_subBloque2" class="bordeLanzarEncargo">
										<a onclick="javascript:bloquearEnlaces()" href="./pdaP51LanzarEncargos.do?origenGISAE=${origenGISAE}"><spring:message code="pda_p15_movStocks.lanzarEnc" /></a>
									 </div>
								</c:when>
							</c:choose>					  
						</c:if>
					</div>
					<div id="pda_p15_movStocks_bloqueReferencia">
						<c:choose>
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '25_PDA_ROTACION')) && !empty (pdaMovStocks.tipoRotacion)}">
								<div id="pda_p15_bloque2_descConTipoRot">	
									<input id="pda_p15_fld_descripcionRef" name="descArtConCodigo" class="input180 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>																	
								</div>
								<div id="pda_p15_bloque2_tipoRot">
									<c:choose> 
										<c:when test="${pdaMovStocks.tipoRotacion=='AR'}">
											<input id="pda_p15_fld_tipoRotacion" class="pda_p15_tipoRotacionAR" disabled="disabled" type="text" value="${pdaMovStocks.tipoRotacion}"/>
										</c:when>
										<c:when test="${pdaMovStocks.tipoRotacion=='MR'}">
											<input id="pda_p15_fld_tipoRotacion" class="pda_p15_tipoRotacionMR" disabled="disabled" type="text" value="${pdaMovStocks.tipoRotacion}"/>
										</c:when>	
										<c:when test="${pdaMovStocks.tipoRotacion=='BR'}">
											<input id="pda_p15_fld_tipoRotacion" class="pda_p15_tipoRotacionBR" disabled="disabled" type="text" value="${pdaMovStocks.tipoRotacion}"/>
										</c:when>	
										<c:otherwise>
											<input id="pda_p15_fld_tipoRotacion" class="pda_p15_tipoRotacionUndef"disabled="disabled" type="text" value="${pdaMovStocks.tipoRotacion}"/>
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && !empty (pdaMovStocks.esUSS) && pdaMovStocks.esUSS eq 'S')}">
								<div id="pda_p15_bloque2_descConUSS">									
									<input id="pda_p15_fld_descripcionRef" name="descArtConCodigo" class="input180 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>			
								</div>
								<div id="pda_p15_bloque2_USS">									
									<input id="pda_p15_fld_USS" class="pda_p15_tipoRotacionUSS" disabled="disabled" type="text" value="<spring:message code="pda_p15_datosReferencia.uss"/>"/>										
								</div>
							</c:when>
							<c:otherwise> <!--  solo descripcion de la referencia, sin rotación. Lo que habia. -->									
								<input id="pda_p15_fld_descripcionRef" name="descArtConCodigo" class="input225 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
							</c:otherwise>
						</c:choose>			
					</div>
					<div id="pda_p15_movStocks_bloque4">
						<div id="pda_p15_movStocksTabla" class="tablaJqgrid">
							<div id="pda_p15_movStocksTit" class="tablaTitulo">
								<span class="pda_p15_movStocks_Title"><spring:message code="pda_p15_movStocks.movimientosStock" /></span>
							</div>
							<table id="pda_p15_movStocksTablaEstructura" border="1">
								<thead class="tablaCabecera">
								    <tr>
								        <th class="pda_p15_movStocksTablaThFecha"><spring:message code="pda_p15_movStocks.fecha" /></th>
								        <th class="pda_p15_movStocksTablaThVentas"><spring:message code="pda_p15_movStocks.ventas" /></th>
								        <th class="pda_p15_movStocksTablaThCorr1"><spring:message code="pda_p15_movStocks.correccion1" /></th>
								        <th class="pda_p15_movStocksTablaThCorr2"><spring:message code="pda_p15_movStocks.correccion2" /></th>
								    </tr>
								</thead>
								<tbody class="tablaContent">
								    <c:forEach var="movimiento" items="${pdaMovStocks.listaMovStocks}" varStatus="loopStatus">
								    <c:choose>
										<c:when test="${movimiento.fecha eq 'HOY'}">
											<tr class="${loopStatus.index % 2 == 0 ? 'tablaTr_impar' : 'tablaTr_par'}">							
										    	<td id="pda_p15_movStocksTdFecha" class="pda_p15_movStocksTablaTdFechaHoy">
										    		<span class="pda_p15_movStocksTablaMovimientosHoy"><c:out value="${movimiento.fecha}"/></span>
										    	</td>
										        <td id="pda_p15_movStocksTdVentas" class="pda_p15_movStocksTablaTdVentasHoy">
										        	<span class="pda_p15_movStocksTablaMovimientosHoy"><c:out value="${movimiento.ventasConFormato}"/></span>
										        </td>
										        <td id="pda_p15_movStocksTdCorr1" class="pda_p15_movStocksTablaTdCorr1Hoy">
										        	<span class="pda_p15_movStocksTablaMovimientosHoy"><c:out value="${movimiento.corr1ConFormato}"/></span>
										        </td>
										        <td id="pda_p15_movStocksTdCorr2" class="pda_p15_movStocksTablaTdCorr2Hoy ">
									        		<span class="pda_p15_movStocksTablaMovimientosHoy"><c:out value="${movimiento.corr2ConFormato}"/></span>
									        	</td>
										</c:when>
										<c:otherwise>
											<tr class="${loopStatus.index % 2 == 0 ? 'tablaTr_impar' : 'tablaTr_par'}">							
									    	<td id="pda_p15_movStocksTdFecha" class="pda_p15_movStocksTablaTdFecha">
									    	<c:out value="${movimiento.fecha}"/></td>
									        <td id="pda_p15_movStocksTdVentas" class="pda_p15_movStocksTablaTdVentas">
									        <c:out value="${movimiento.ventasConFormato}"/></td>
									        <td id="pda_p15_movStocksTdCorr1" class="pda_p15_movStocksTablaTdCorr1">
									        <c:out value="${movimiento.corr1ConFormato}"/></td>
									        <td id="pda_p15_movStocksTdCorr2" class="pda_p15_movStocksTablaTdCorr2">
									        <c:out value="${movimiento.corr2ConFormato}"/></td>
								    </tr>
										</c:otherwise>
									</c:choose>
									
								</c:forEach>
								</tbody>
							</table>
							
							<table class="tablaPaginacion" cellspacing="0" cellpadding="0" border="0">
								<tbody>
									<tr>
										<td id="pagerP15Motivos_left" align="left">
											<table class="paginacion" cellspacing="0" cellpadding="0" border="0" >
												<tbody>
													<tr>
														<td id="first_pagerp15Motivos" align="right">
															<c:choose>
																<c:when test="${pdaMovStocks.page eq '1' or empty pdaMovStocks.page}">
																	<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP15Paginar.do?codArt=${pdaMovStocks.codArt}&page=${pdaMovStocks.page}&pgTot=${pdaMovStocks.total}&botPag=first&procede=${procede}">
																		<img src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
																	</a>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
											   </tbody>
											</table>
										</td>
										<td id="pagerp15Motivos_center" align="center" class="paginacionCentro">
											<table class="paginacion" cellspacing="0" cellpadding="0" border="0">
												<tbody>
													<tr>
														<td id="prev_pagerp15Motivos" align="left">
															<c:choose>
																<c:when test="${pdaMovStocks.page eq '1' or empty pdaMovStocks.page}">
																	<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP15Paginar.do?codArt=${pdaMovStocks.codArt}&page=${pdaMovStocks.page}&pgTot=${pdaMovStocks.total}&botPag=prev&procede=${procede}">
																		<img src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
																	</a>
																</c:otherwise>
															</c:choose>
														</td>
														<td id="next_pagerp15Motivos" align="right">
															<c:choose>
																<c:when test="${pdaMovStocks.page eq pdaMovStocks.total}">
																	<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP15Paginar.do?codArt=${pdaMovStocks.codArt}&page=${pdaMovStocks.page}&pgTot=${pdaMovStocks.total}&botPag=next&procede=${procede}">
																		<img src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
																	</a>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td id="pagerp15Motivos_right" align="right">
											<table class="paginacion" cellspacing="0" cellpadding="0" border="0" >
												<tbody>
													<tr>
														<td id="last_pagerp15Motivos" align="left">
															<c:choose>
																<c:when test="${pdaMovStocks.page eq pdaMovStocks.total}">
																	<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP15Paginar.do?codArt=${pdaMovStocks.codArt}&page=${pdaMovStocks.page}&pgTot=${pdaMovStocks.total}&botPag=last&procede=${procede}">
																		<img src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
																	</a>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
				
						</div>
					</div>
				</c:otherwise>
			</c:choose>			
		</div>	
		<input id="pda_p15_tieneFoto" type="hidden" value="${pdaMovStocks.tieneFoto}"/>
		<input id="pda_p15_codArt" type="hidden" value="${pdaMovStocks.codArt}"/>
		<input id="pda_p15_procede" type="hidden" value="${procede}"/>

<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>