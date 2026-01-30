<%@ page trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda/prehuecos/pda_p115_prehuecos.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda/prehuecos/pda_p115Prehuecos.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP115PrehuecosLineal.do"></jsp:param>
    <jsp:param name="procede" value="${procede}"></jsp:param>
</jsp:include>
<script src="./misumi/scripts/utils/json2.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
		<c:choose>
			<c:when test="${empty (pdaDatosRef.codArt)}">
				<div id="pda_p115_prehuecos_bloque1">
					<div id="pda_p115_prehuecos_bloque1_subBloque1">
						<label id="pda_p115_lbl_prehuecos" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.prehueco" /></label>
						<label id="pda_p115_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.separadorEnlaces" /></label>
						<label id="pda_p115_lbl_segPedidos" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.segPedidos" /></label>
						<label id="pda_p115_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.separadorEnlaces" /></label>
						<label id="pda_p115_lbl_movStocks" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.movStocks" /></label>
						<label id="pda_p115_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.separadorEnlaces" /></label>
						<label id="pda_p115_lbl_almacen" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.almacen" /></label>
					</div> 
					<div id="pda_p115_datosReferencia_bloque1_subBloque2">
						<%--<c:choose>
							<c:when test="${pdaDatosRef.mostrarLanzarEncargo eq 'S'}">
								<span>LANZAR ENC.</span>
								<!-- <a href="./pdaP15MovStocks.do?mostrarFFPP=N&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}"><spring:message code="pda_p115_prehuecos.movStocksEnlace" /></a>-->
							</c:when>
						</c:choose>--%>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="pda_p115_origenGISAE" id="pda_p115_origenGISAE" value="${origenGISAE}"/>
				<div id="pda_p115_prehuecos_bloque1">
					<div id="pda_p115_prehuecos_bloque1_subBloque1">
						<label id="pda_p115_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.prehueco" /></label>
						<label id="pda_p115_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.separadorEnlaces" /></label>
						<c:choose>
							<c:when test="${pdaDatosRef.mostrarFFPP eq 'S'}">
								<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=N&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origen=PHPed&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p115_prehuecos.segPedidosEnlace"/></a>
							</c:when>
							<c:when test="${pdaDatosRef.mostrarFFPP eq 'N'}">
								<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=S&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origen=PHPed&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p115_prehuecos.segPedidosEnlace"/></a>
							</c:when>
							<c:otherwise>
								<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origen=PHPed&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p115_prehuecos.segPedidosEnlace"/></a>
							</c:otherwise>
						</c:choose>
						<label id="pda_p115_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.separadorEnlaces" /></label>
						<c:choose>
							<c:when test="${pdaDatosRef.mostrarFFPP eq 'S'}">
								<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=N&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p115_prehuecos.movStocksEnlace"/></a>
							</c:when>
							<c:when test="${pdaDatosRef.mostrarFFPP eq 'N'}">
								<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=S&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p115_prehuecos.movStocksEnlace"/></a>
							</c:when>	
							<c:otherwise>
								<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p115_prehuecos.movStocksEnlace"/></a>
							</c:otherwise>
						</c:choose>
						<label id="pda_p115_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado">
							<spring:message code="pda_p115_prehuecos.separadorEnlaces"/>
						</label>
						<label id="pda_p115_lbl_almacen" class="etiquetaEnlaceDeshabilitado">
							<a onclick="javascript:bloquearEnlaces()" href="./pdaP118PrehuecosAlmacen.do?codArt=${pdaDatosRef.codArt}&tipoListado=1&soyPaletInput=true&actionRef=pdaP115PrehuecosLineal.do">
								<spring:message code="pda_p115_prehuecos.almacenEnlace"/>
							</a>
						</label>
					</div>						
				</div>
					
				<div id="pda_p115_prehuecos_bloqueReferencia">
					
					<c:choose>
						<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '25_PDA_ROTACION')) && !empty (pdaDatosRef.tipoRotacion)}">
							<div id="pda_p115_bloque2_descConTipoRot">
								<input id="pda_p115_fld_descripcionRef" name="descArtConCodigo" class="input180 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
							</div>
							<div id="pda_p115_bloque2_tipoRot">
								<c:choose> 
									<c:when test="${pdaDatosRef.tipoRotacion=='AR'}">
										<input id="pda_p115_fld_tipoRotacion" class="pda_p115_tipoRotacionAR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
									</c:when>
									<c:when test="${pdaDatosRef.tipoRotacion=='MR'}">
										<input id="pda_p115_fld_tipoRotacion" class="pda_p115_tipoRotacionMR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
									</c:when>
									<c:when test="${pdaDatosRef.tipoRotacion=='BR'}">
										<input id="pda_p115_fld_tipoRotacion" class="pda_p115_tipoRotacionBR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
									</c:when>	
									<c:otherwise>
										<input id="pda_p115_fld_tipoRotacion" class="pda_p115_tipoRotacionUndef"disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:otherwise> <!--  solo descripcion de la referencia, sin rotación. Lo que habia. -->
							<input id="pda_p115_fld_descripcionRef" name="descArtConCodigo" class="input225 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
						</c:otherwise>
					</c:choose>			

				</div>	
					
				<div id="pda_p115_prehuecos_bloque2">
					<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '24_PDA_CONTROL_STOCK'))}">
						
						<div id="pda_p115_tengoMuchoPoco">
							<c:choose>																																													
								<c:when test="${pdaDatosRef.stockActual eq 'Error'}">
									<fieldset id=pda_p115_tengoMuchoPocoFieldsetErrorStock>
										<div id="pda_p115_tengoMuchoPocoErrorStockLabelDiv"><span id="pda_p115_tengoMuchoPocoErrorStockLabel"><spring:message code="pda_p115_prehuecos.errorStockActual" /></span></div>
									</fieldset>	
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaDatosRef.valoresStock == null || (pdaDatosRef.valoresStock.flgErrorWSVentasTienda!=null && pdaDatosRef.valoresStock.flgErrorWSVentasTienda!=0) ||
										!(pdaDatosRef.valoresStock.stockBajo <= pdaDatosRef.valoresStock.sobreStockInferior &&
																	pdaDatosRef.valoresStock.sobreStockInferior <= pdaDatosRef.valoresStock.sobreStockSuperior &&
																	pdaDatosRef.valoresStock.stockBajo >= 0 &&
																	(pdaDatosRef.valoresStock.stock > 0 || pdaDatosRef.valoresStock.stockBajo > 0 || pdaDatosRef.valoresStock.sobreStockInferior > 0 || pdaDatosRef.valoresStock.sobreStockSuperior > 0))}">
											<c:if test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
												<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&procede=pdaP115PrehuecosLineal&origen=PH&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
											</c:if>
											<fieldset id=pda_p115_tengoMuchoPocoFieldsetError>
												<div id="pda_p115_tengoMuchoPocoErrorLabelDiv">
													<span id="pda_p115_tengoMuchoPocoErrorLabel"><spring:message code="pda_p115_prehuecos.stockDias" /></span>
												</div>
												<div id="pda_p115_tengoMuchoPocoErrorStockDiv">
													<c:choose>
														<c:when test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
															<c:choose>
																<c:when test="${!empty (guardadoStockOk)}">
																	<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoStockConEnlaceOk"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																</c:when>
																<c:otherwise>
																	<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoStockConEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoErrorStockSinEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind></span>
														</c:otherwise>
													</c:choose>
												</div>
											</fieldset>		
											<c:if test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
												</a>
											</c:if>															
										</c:when>
			
										<c:otherwise>
											<c:choose>	
												<c:when test="${pdaDatosRef.valoresStock.stock >= pdaDatosRef.valoresStock.stockBajo && pdaDatosRef.valoresStock.stock <= pdaDatosRef.valoresStock.sobreStockInferior}">
													<c:if test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
														<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&procede=pdaP115PrehuecosLineal&origen=PH&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
													</c:if>
													<fieldset id=pda_p115_tengoMuchoPocoFieldsetOk>
														<div id="pda_p115_tengoMuchoPocoOkLabelDiv">
															<span id="pda_p115_tengoMuchoPocoOkLabel"><spring:message code="pda_p115_prehuecos.stockOk" /></span>
														</div>
														<div id="pda_p115_tengoMuchoPocoOkStockDiv">
															<c:choose>
																<c:when test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
																	<c:choose>
																		<c:when test="${!empty (guardadoStockOk)}">
																			<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoStockConEnlaceOk"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind> <spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																		</c:when>
																		<c:otherwise>
																			<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoStockConEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind> <spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoOkStockSinEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind></span>
																</c:otherwise>
															</c:choose>
														</div>
													</fieldset>
													<c:if test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
														</a>
													</c:if>
												</c:when>
												<c:otherwise>
													<fieldset id=pda_p115_tengoMuchoPocoFieldsetAltoBajo>
														<c:choose>
															<c:when test="${pdaDatosRef.valoresStock.stock > pdaDatosRef.valoresStock.sobreStockInferior}">
																<c:choose>
																	<c:when test="${pdaDatosRef.valoresStock.mostrarMotivosStock != null &&  pdaDatosRef.valoresStock.mostrarMotivosStock == 'S'}">
																		<div id="pda_p115_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p115_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p115_prehuecos.stock" /></span>
																			<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaDatosRef.codArt}&procede=capturaRestos&muchoPoco=M&stockAlto=${pdaDatosRef.valoresStock.sobreStockInferior}&stockBajo=${pdaDatosRef.valoresStock.stockBajo}&stock=${pdaDatosRef.valoresStock.stock}">
																				<span class="pda_p115_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p115_prehuecos.stockAlto"/></span>
																			</a>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div id="pda_p115_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p115_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p115_prehuecos.stock" />&nbsp;<spring:message code="pda_p115_prehuecos.stockAlto" /></span></div>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${pdaDatosRef.valoresStock.mostrarMotivosStock != null &&  pdaDatosRef.valoresStock.mostrarMotivosStock == 'S'}">
																		<div id="pda_p115_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p115_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p115_prehuecos.stock" /></span>
																			<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaDatosRef.codArt}&procede=capturaRestos&muchoPoco=P&stockAlto=${pdaDatosRef.valoresStock.sobreStockInferior}&stockBajo=${pdaDatosRef.valoresStock.stockBajo}&stock=${pdaDatosRef.valoresStock.stock}">
																				<span class="pda_p115_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p115_prehuecos.stockBajo"/></span>
																			</a>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div id="pda_p115_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p115_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p115_prehuecos.stock" />&nbsp;<spring:message code="pda_p115_prehuecos.stockBajo" /></span></div>
																	</c:otherwise>
																</c:choose>																		
															</c:otherwise>
														</c:choose>
														<div id="pda_p115_tengoMuchoPocoAltoBajoStockDiv">
															<label id="pda_p115_lbl_stockActual">														
																<c:choose>
																	<c:when test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
																		<c:choose>
																			<c:when test="${!empty (guardadoStockOk)}">
																				<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&procede=pdaP115PrehuecosLineal&origen=PH&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
																					<c:choose>
																						<c:when test="${pdaDatosRef.valoresStock.stock > pdaDatosRef.valoresStock.sobreStockInferior}">
																							<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoAltoBajoConEnlaceOkUp">
																						</c:when>
																						<c:otherwise>
																							<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoAltoBajoConEnlaceOkDown">
																						</c:otherwise>
																					</c:choose>	
																					<spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																				</a>
																			</c:when>
																			<c:otherwise>
																				<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&procede=pdaP115PrehuecosLineal&origen=PH&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
																					<c:choose>
																						<c:when test="${pdaDatosRef.valoresStock.stock > pdaDatosRef.valoresStock.sobreStockInferior}">
																							<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoAltoBajoStockConEnlaceUp">
																						</c:when>
																						<c:otherwise>
																							<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoAltoBajoStockConEnlaceDown">
																						</c:otherwise>
																					</c:choose>
																					<spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																				</a>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${pdaDatosRef.valoresStock.stock > pdaDatosRef.valoresStock.sobreStockInferior}">
																				<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoAltoBajoStockSinEnlaceUp">
																			</c:when>
																			<c:otherwise>
																				<span id="pda_p115_lbl_stockActualVal" class="pda_p115_tengoMuchoPocoAltoBajoStockSinEnlaceDown">
																			</c:otherwise>
																		</c:choose>	
																		<spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																	</c:otherwise>
																</c:choose>
															</label>	
														</div>
													</fieldset>
												</c:otherwise>
											</c:choose>	
										</c:otherwise>																														
									</c:choose>	
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>

					<div id="pda_p115_referenciasCentroPedir">
						<c:choose>
							<c:when test="${pdaDatosRef.flgDepositoBrita eq 'S'}">
								<fieldset id="pda_p115_pedidoAutomaticoFieldsetDepositoBrita">
									<p class="pda_p115_parrafoPedidoAutomaticoDepositoBrita"><spring:message code="pda_p115_prehuecos.mensajeEnDepositoBrita" /></p>
								</fieldset>
							</c:when>
							<c:when test="${pdaDatosRef.flgPorCatalogo eq 'S'}">
								<fieldset id="pda_p115_pedidoAutomaticoFieldsetPorCatalogo">
									<p class="pda_p115_parrafoPedidoAutomaticoPorCatalogo"><spring:message code="pda_p115_prehuecos.mensajePorCatalogo" /></p>
								</fieldset>
							</c:when>
							<c:when test="${pdaDatosRef.tratamientoVegalsa}">
								<c:choose>
									<c:when test="${pdaDatosRef.MMC eq 'S'}">
										<c:choose>
											<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
												<a href="./pdaP14refCentroOkPopup.do?procede=pdaP115PrehuecosLineal&origen=PH&codArt=${pdaDatosCab.codArtCab}">
													<fieldset id="pda_p115_pedidoAutomaticoFieldsetActivoMMC">
														<p class="pda_p115_parrafoPedidoAutomaticoActivoLinkMMC"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoActivoVegalsa" /></p>
													</fieldset>
												</a>
											</c:when>
											<c:otherwise>
												<a href="./pdaP14refCentroOkPopup.do?procede=capturaRestos&codArt=${pdaDatosCab.codArtCab}">
													<fieldset id="pda_p115_pedidoAutomaticoFieldsetActivoMMC">
														<p class="pda_p115_parrafoPedidoAutomaticoActivoLinkMMC"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoActivoVegalsa" /></p>
													</fieldset>
												</a>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
												<a href="./pdaP14refCentroPopup.do?procede=pdaP115PrehuecosLineal&origen=PH&codArt=${pdaDatosCab.codArtCab}">
													<fieldset id="pda_p115_pedidoAutomaticoFieldsetNoActivoMMC">
														<p class="pda_p115_parrafoPedidoAutomaticoNoActivoLinkMMC"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoNoActivoVegalsa" /></p>
													</fieldset>
												</a>
											</c:when>
											<c:otherwise>
												<a href="./pdaP14refCentroPopup.do?procede=datosRef&codArt=${pdaDatosCab.codArtCab}">
													<fieldset id="pda_p115_pedidoAutomaticoFieldsetNoActivoMMC">
														<p class="pda_p115_parrafoPedidoAutomaticoNoActivoLinkMMC"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoNoActivoVegalsa" /></p>
													</fieldset>
												</a>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${pdaDatosRef.pedidoActivo eq 'S'}">
										<c:choose>
											<c:when test="${not empty pdaDatosRef.strFechaGen && pdaDatosRef.mostrarFechaGen}">
												<a href="./pdaP19fechaActivacionPopup.do?origen=DR&mostrarFFPP=${pdaDatosRef.mostrarFFPP}&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&fechaActivacion=${pdaDatosRef.strFechaGen}">
													<fieldset id="pda_p115_pedidoAutomaticoFieldsetActivo">
														<p class="pda_p115_parrafoPedidoAutomaticoActivoLink"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoActivo" /></p>
													</fieldset>
												</a>
											</c:when>
											<c:otherwise>
												<fieldset id="pda_p115_pedidoAutomaticoFieldsetActivo">
													<p class="pda_p115_parrafoPedidoAutomaticoActivo"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoActivo" /></p>
												</fieldset>
											</c:otherwise>
										</c:choose>									
									</c:when>
									<c:otherwise>	
										<c:choose>
											<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
												<a href="./pdaP14refCentroPopup.do?procede=pdaP115PrehuecosLineal&origen=PH&codArt=${pdaDatosCab.codArtCab}">
													<fieldset id="pda_p115_pedidoAutomaticoFieldsetNoActivo">
														<p class="pda_p115_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoNoActivo" /></p>
													</fieldset>
												</a>
											</c:when>
											<c:otherwise>
												<a href="./pdaP14refCentroPopup.do?procede=datosRef&codArt=${pdaDatosCab.codArtCab}">
													<fieldset id="pda_p115_pedidoAutomaticoFieldsetNoActivo">
														<p class="pda_p115_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p115_prehuecos.mensajePedidoAutomaticoNoActivo" /></p>
													</fieldset>
												</a>											
											</c:otherwise>
										</c:choose>											
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>	
					</div>

					<div id="pda_p115_datosReferencia_bloqueStockLineal">
						<div id="pda_p115_stockLineal_parrafo">
							<p>
								<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita">
									<spring:message	code="pda_p115_prehuecos.stockLineal"/>
								</label>
							</p>
							<input id="pda_p115_fld_stockLineal" name="pda_p115_fld_stockLineal" class="inputFinCampania" type="text" value="${stockLineal}"/>
							<div id="pda_p115_prehuecoLineal_estado">
								<c:if test="${estadoRef gt -1}">
									<c:choose>
					  					<c:when test="${estadoRef eq 0}">
											<img src="./misumi/images/icono_aspa.png?version=${misumiVersion}" style="width:15px;height:15px;"> 
										</c:when>
										<c:when test="${estadoRef eq 1}">
											<img src="./misumi/images/icono_pendiente.png?version=${misumiVersion}" style="width:15px;height:15px;"> 
										</c:when>
										<c:otherwise>
											<img src="./misumi/images/icono_tick.png?version=${misumiVersion}" style="width:15px;height:15px;">
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</div>
						
						<div id="pda_p115_portapapeles">
							<a id="linkPortapeles" href="./pdaP116PrehuecosStockAlmacen.do"><img src="./misumi/images/informe.png?version=${misumiVersion}"></a><input type="text" id="inputNumeroPrehuecos" value="${numPrehuecosSinValidar}" disabled/>
						</div>
					</div>

					<!-- BLOQUE DE IMPLANTACION EN UNIDADES -->				
					<%request.setAttribute("procede", "pdaP115PrehuecosLineal");%>
 					<%@ include file="/WEB-INF/views/pda_p12_datosReferencia_imc.jsp" %>

					<div id="pda_p115_prehuecos_bloqueMontajesAdicionales">
						<c:if test="${pdaDatosRef.cantidadMac != null && pdaDatosRef.cantidadMac != ''}">
							<div id="pda_p115_prehuecos_montajesAdicionalesMAC">
								<a href="./pdaP100MontajeAdicionalMAC.do?codArt=${pdaDatosRef.codArt}">
									<span id="pda_p115_montajesAdicionalesMAC" class="pda_p115_montajeAdicionalConEnlace"><spring:message code="pda_p115_prehuecos.macLegend" /><spring:bind path="pdaDatosRef.cantidadMac">${status.value}</spring:bind></span>
								</a>
							</div>
						</c:if>
						<c:if test="${pdaDatosRef.cantidadMa != null && pdaDatosRef.cantidadMa != ''}">
							<div id="pda_p115_prehuecos_montajesAdicionalesMA">
								<a href="./pdaP101MontajeAdicionalMA.do?codArt=${pdaDatosRef.codArt}">
									<span id="pda_p115_montajesAdicionalesMA" class="pda_p115_montajeAdicionalConEnlace"><spring:message code="pda_p115_prehuecos.maLegend" /><spring:bind path="pdaDatosRef.cantidadMa">${status.value}</spring:bind></span>
								</a>
							</div>
						</c:if>	
					</div>
			</c:otherwise>
		</c:choose>
		</div>	
		<div id="contenidoPaginaPromo">
			<label id="pda_p115_msg_promo">
				<spring:message code="pda_p115_prehuecos.msgPromo"/>
			</label>
		</div>
		
		<input type="hidden" id="pda_p115_codArt" value="${pdaDatosRef.codArt}"/>
		<input type="hidden" id="pda_p115_tieneFoto" value="${pdaDatosRef.tieneFoto}"/>
		<input type="hidden" id="pda_p115_codArtPromo" value="${pdaDatosRef.codArtPromo}"/>
		<input type="hidden" id="pda_p115_fld_stockLineal_ori" value="${stockLineal}"/>
		<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">
		<input type="hidden" id="pda_p115_procede" name="pda_p115_procede" value="${procede}">
		<input type="hidden" id="prehuecoSeleccionado" name="prehuecoSeleccionado" value="${prehuecoSeleccionado}">
		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>