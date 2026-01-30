<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p12_datosReferencia.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p12DatosReferencia.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP12DatosReferencia.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<c:choose>
				<c:when test="${empty (pdaDatosRef.codArt)}">
					<div id="pda_p12_datosReferencia_bloque1">
						<div id="pda_p12_datosReferencia_bloque1_subBloque1">
							<label id="pda_p12_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.datosReferencia" /></label>
							<label id="pda_p12_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.separadorEnlaces" /></label>
							<label id="pda_p12_lbl_segPedidos" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.segPedidos" /></label>
							<label id="pda_p12_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.separadorEnlaces" /></label>
							<label id="pda_p12_lbl_movStocks" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.movStocks" /></label>
						</div> 
						<div id="pda_p12_datosReferencia_bloque1_subBloque2">
							<%--<c:choose>
								<c:when test="${pdaDatosRef.mostrarLanzarEncargo eq 'S'}">
									<span>LANZAR ENC.</span>
									<!-- <a href="./pdaP15MovStocks.do?mostrarFFPP=N&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}"><spring:message code="pda_p12_datosReferencia.movStocksEnlace" /></a>-->
								</c:when>
							</c:choose>--%>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="pda_p12_origenGISAE" id="pda_p12_origenGISAE" value="${origenGISAE}"/>
					<div id="pda_p12_datosReferencia_bloque1">
						<div id="pda_p12_datosReferencia_bloque1_subBloque1">
							<label id="pda_p12_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.datosReferencia" /></label>
							<label id="pda_p12_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.separadorEnlaces" /></label>
							<c:choose>
								<c:when test="${pdaDatosRef.mostrarFFPP eq 'S'}">
									<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=N&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p12_datosReferencia.segPedidosEnlace" /></a>
								</c:when>
								<c:when test="${pdaDatosRef.mostrarFFPP eq 'N'}">
									<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?mostrarFFPP=S&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p12_datosReferencia.segPedidosEnlace" /></a>
								</c:when>	
								<c:otherwise>
									<a onclick="javascript:bloquearEnlaces()" href="./pdaP13SegPedidos.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p12_datosReferencia.segPedidosEnlace" /></a>
								</c:otherwise>
							</c:choose>	
							<label id="pda_p12_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p12_datosReferencia.separadorEnlaces" /></label>
							<c:choose>
								<c:when test="${pdaDatosRef.mostrarFFPP eq 'S'}">
									<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=N&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p12_datosReferencia.movStocksEnlace" /></a>
								</c:when>
								<c:when test="${pdaDatosRef.mostrarFFPP eq 'N'}">
									<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=S&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p12_datosReferencia.movStocksEnlace" /></a>
								</c:when>	
								<c:otherwise>
									<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p12_datosReferencia.movStocksEnlace" /></a>
								</c:otherwise>
							</c:choose>	
						</div>						
							<c:choose>								
								<c:when test="${pedidoPda.mostrasLinkLanzarEncargo eq true and status.value ne 'Descentralizada'}">
									<div id="pda_p12_datosReferencia_bloque1_subBloque2" class="bordeLanzarEncargo">
										<c:choose>
											<c:when test="${greenLink eq true}">
												<a class="greenLink" onclick="javascript:bloquearEnlaces()" href="./pdaP51LanzarEncargos.do?origenGISAE=${origenGISAE}"><spring:message code="pda_p15_movStocks.lanzarEnc" /></a>
											</c:when>
											<c:otherwise>
												<a onclick="javascript:bloquearEnlaces()" href="./pdaP51LanzarEncargos.do?origenGISAE=${origenGISAE}"><spring:message code="pda_p15_movStocks.lanzarEnc" /></a>
											</c:otherwise>
										</c:choose>																				
									</div>
								</c:when>								
							</c:choose>						
					</div>
					
					<div id="pda_p12_datosReferencia_bloqueReferencia">
					
						<c:choose>
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '25_PDA_ROTACION')) && !empty (pdaDatosRef.tipoRotacion)}">
								<div id="pda_p12_bloque2_descConTipoRot">
								
									<input id="pda_p12_fld_descripcionRef" name="descArtConCodigo" class="input190 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
									<!-- Ahora siempre va a tener link. Si no tiene foto, se abrira la ventana cob la estructura y el tipo de referencia
									<c:choose>
										<c:when test="${pdaDatosRef.tieneFoto == 'S'}">
											<input id="pda_p12_fld_descripcionRef" name="descArtConCodigo" class="input190 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>									
										</c:when>
										<c:otherwise>
											<input id="pda_p12_fld_descripcionReff" name="descArtConCodigo" class="input190 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
										</c:otherwise>
									</c:choose>	
									-->
								</div>
								<div id="pda_p12_bloque2_tipoRot">
									<c:choose> 
										<c:when test="${pdaDatosRef.tipoRotacion=='AR'}">
											<input id="pda_p12_fld_tipoRotacion" class="pda_p12_tipoRotacionAR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
										</c:when>
										<c:when test="${pdaDatosRef.tipoRotacion=='MR'}">
											<input id="pda_p12_fld_tipoRotacion" class="pda_p12_tipoRotacionMR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
										</c:when>	
										<c:when test="${pdaDatosRef.tipoRotacion=='BR'}">
											<input id="pda_p12_fld_tipoRotacion" class="pda_p12_tipoRotacionBR" disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
										</c:when>	
										<c:otherwise>
											<input id="pda_p12_fld_tipoRotacion" class="pda_p12_tipoRotacionUndef"disabled="disabled" type="text" value="${pdaDatosRef.tipoRotacion}"/>
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && !empty (pdaDatosRef.esUSS) && pdaDatosRef.esUSS eq 'S')}">
								<div id="pda_p12_bloque2_descConUSS">
									
									<input id="pda_p12_fld_descripcionRef" name="descArtConCodigo" class="input190 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>		
									<!-- Ahora siempre va a tener link. Si no tiene foto, se abrira la ventana cob la estructura y el tipo de referencia
									<c:choose>
										<c:when test="${pdaDatosRef.tieneFoto == 'S'}">
											<input id="pda_p12_fld_descripcionRef" name="descArtConCodigo" class="input190 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>									
										</c:when>
										<c:otherwise>
											<input id="pda_p12_fld_descripcionReff" name="descArtConCodigo" class="input190 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
										</c:otherwise>
									</c:choose>	
									-->
									
								</div>
								<div id="pda_p12_bloque2_USS">									
									<input id="pda_p12_fld_USS" class="pda_p12_tipoRotacionUSS" disabled="disabled" type="text" value="<spring:message code="pda_p12_datosReferencia.uss"/>"/>										
								</div>
							</c:when>
							<c:otherwise> <!--  solo descripcion de la referencia, sin rotación. Lo que habia. -->
								
								<input id="pda_p12_fld_descripcionRef" name="descArtConCodigo" class="input225 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
								<!-- Ahora siempre va a tener link. Si no tiene foto, se abrira la ventana cob la estructura y el tipo de referencia
								<c:choose>
									<c:when test="${pdaDatosRef.tieneFoto == 'S'}">
										<input id="pda_p12_fld_descripcionRef" name="descArtConCodigo" class="input225 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>									
									</c:when>
									<c:otherwise>
										<input id="pda_p12_fld_descripcionReff" name="descArtConCodigo" class="input225 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>
									</c:otherwise>
								</c:choose>	
								-->
							</c:otherwise>
						</c:choose>			
					</div>	
					
					<div id="pda_p12_datosReferencia_bloque2">
					  <c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '24_PDA_CONTROL_STOCK'))}">
						
						<div id="pda_p12_tengoMuchoPoco">
							<c:choose>																																													
								<c:when test="${pdaDatosRef.stockActual eq 'Error'}">
									<fieldset id=pda_p12_tengoMuchoPocoFieldsetErrorStock>
										<div id="pda_p12_tengoMuchoPocoErrorStockLabelDiv"><span id="pda_p12_tengoMuchoPocoErrorStockLabel"><spring:message code="pda_p12_datosReferencia.errorStockActual" /></span></div>
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
												<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&origen=DR&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
											</c:if>
											<fieldset id=pda_p12_tengoMuchoPocoFieldsetError>
												<div id="pda_p12_tengoMuchoPocoErrorLabelDiv"><span id="pda_p12_tengoMuchoPocoErrorLabel"><spring:message code="pda_p12_datosReferencia.stockDias" /></span></div>
												<div id="pda_p12_tengoMuchoPocoErrorStockDiv">

														<c:choose>
															<c:when test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
																<c:choose>
																	<c:when test="${!empty (guardadoStockOk)}">
																		<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoStockConEnlaceOk"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																	</c:when>
																	<c:otherwise>
																		<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoStockConEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoErrorStockSinEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind></span>
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
														<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&origen=DR&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
													</c:if>
														<fieldset id=pda_p12_tengoMuchoPocoFieldsetOk>
															<div id="pda_p12_tengoMuchoPocoOkLabelDiv"><span id="pda_p12_tengoMuchoPocoOkLabel"><spring:message code="pda_p12_datosReferencia.stockOk" /></span>
															</div>
															<div id="pda_p12_tengoMuchoPocoOkStockDiv">
																<c:choose>
																	<c:when test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
																		<c:choose>
																			<c:when test="${!empty (guardadoStockOk)}">																	
																				<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoStockConEnlaceOk"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind> <spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																			</c:when>
																			<c:otherwise>
																				<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoStockConEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind> <spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoOkStockSinEnlace"><spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind></span>
																	</c:otherwise>
																</c:choose>
															</div>
														</fieldset>
													<c:if test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
														</a>
													</c:if>
												</c:when>
												<c:otherwise>
													<fieldset id=pda_p12_tengoMuchoPocoFieldsetAltoBajo>
														<c:choose>
															<c:when test="${pdaDatosRef.valoresStock.stock > pdaDatosRef.valoresStock.sobreStockInferior}">
																<c:choose>
																	<c:when test="${pdaDatosRef.valoresStock.mostrarMotivosStock != null &&  pdaDatosRef.valoresStock.mostrarMotivosStock == 'S'}">
																		<div id="pda_p12_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p12_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p12_datosReferencia.stock" /></span>
																			<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaDatosRef.codArt}&procede=capturaRestos&muchoPoco=M&stockAlto=${pdaDatosRef.valoresStock.sobreStockInferior}&stockBajo=${pdaDatosRef.valoresStock.stockBajo}&stock=${pdaDatosRef.valoresStock.stock}">
																				<span class="pda_p12_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p12_datosReferencia.stockAlto"/></span>
																			</a>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div id="pda_p12_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p12_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p12_datosReferencia.stock" />&nbsp;<spring:message code="pda_p12_datosReferencia.stockAlto" /></span></div>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${pdaDatosRef.valoresStock.mostrarMotivosStock != null &&  pdaDatosRef.valoresStock.mostrarMotivosStock == 'S'}">
																		<div id="pda_p12_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p12_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p12_datosReferencia.stock" /></span>
																			<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaDatosRef.codArt}&procede=capturaRestos&muchoPoco=P&stockAlto=${pdaDatosRef.valoresStock.sobreStockInferior}&stockBajo=${pdaDatosRef.valoresStock.stockBajo}&stock=${pdaDatosRef.valoresStock.stock}">
																				<span class="pda_p12_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p12_datosReferencia.stockBajo"/></span>
																			</a>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div id="pda_p12_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p12_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p12_datosReferencia.stock" />&nbsp;<spring:message code="pda_p12_datosReferencia.stockBajo" /></span></div>
																	</c:otherwise>
																</c:choose>																		
															</c:otherwise>
														</c:choose>
														<div id="pda_p12_tengoMuchoPocoAltoBajoStockDiv">
															<label id="pda_p12_lbl_stockActual">													
																	<c:choose>
																		<c:when test="${pdaDatosRef.stockActivo eq 'S' && origenGISAE ne 'SI'}">
																			<c:choose>
																				<c:when test="${!empty (guardadoStockOk)}">
																					<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&origen=DR&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
																						<c:choose>
																							<c:when test="${pdaDatosRef.valoresStock.stock > pdaDatosRef.valoresStock.sobreStockInferior}">
																								<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoAltoBajoConEnlaceOkUp">
																							</c:when>
																							<c:otherwise>
																								<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoAltoBajoConEnlaceOkDown">
																							</c:otherwise>
																						</c:choose>	
																						<spring:bind path="pdaDatosRef.stockActual">${status.value}</spring:bind><spring:bind path="pdaDatosRef.diasStock">${status.value}</spring:bind></span>
																					</a>
																				</c:when>
																				<c:otherwise>
																					<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaDatosRef.codArt}&origen=DR&mmc=${pdaDatosRef.MMC}&calculoCC=${pdaDatosRef.calculoCC}">
																						<c:choose>
																							<c:when test="${pdaDatosRef.valoresStock.stock > pdaDatosRef.valoresStock.sobreStockInferior}">
																								<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoAltoBajoStockConEnlaceUp">
																							</c:when>
																							<c:otherwise>
																								<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoAltoBajoStockConEnlaceDown">
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
																					<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoAltoBajoStockSinEnlaceUp">
																				</c:when>
																				<c:otherwise>
																					<span id="pda_p12_lbl_stockActualVal" class="pda_p12_tengoMuchoPocoAltoBajoStockSinEnlaceDown">
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
						
						<div id="pda_p12_referenciasCentroPedir">
							<c:choose>
								<c:when test="${pdaDatosRef.flgDepositoBrita eq 'S'}">
									<fieldset id="pda_p12_pedidoAutomaticoFieldsetDepositoBrita">
										<p class="pda_p12_parrafoPedidoAutomaticoDepositoBrita"><spring:message code="pda_p12_datosReferencia.mensajeEnDepositoBrita" /></p>
									</fieldset>
								</c:when>
								<c:when test="${pdaDatosRef.flgPorCatalogo eq 'S'}">
									<fieldset id="pda_p12_pedidoAutomaticoFieldsetPorCatalogo">
										<p class="pda_p12_parrafoPedidoAutomaticoPorCatalogo"><spring:message code="pda_p12_datosReferencia.mensajePorCatalogo" /></p>
									</fieldset>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaDatosRef.pedidoActivo eq 'S'}">
											<!--<c:choose>
												<c:when test="${user.centro.esCentroCaprabo || user.centro.esCentroCapraboEspecial}">
													<c:choose>
														<c:when test="${not empty pdaDatosRef.tMisMcgCaprabo.fechaActivacion && pdaDatosRef.mostrarFechaGen}">								
															<a href="./pdaP19fechaActivacionPopup.do?origen=DR&mostrarFFPP=${pdaDatosRef.mostrarFFPP}&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&fechaActivacion=${pdaDatosRef.strFechaGen}">
																<fieldset id="pda_p12_pedidoAutomaticoFieldsetActivo">
																	<p class="pda_p12_parrafoPedidoAutomaticoActivoLink"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
																</fieldset>
															</a>
														</c:when>
														<c:otherwise>
															<fieldset id="pda_p12_pedidoAutomaticoFieldsetActivo">
																<p class="pda_p12_parrafoPedidoAutomaticoActivo"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
															</fieldset>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${not empty pdaDatosRef.strFechaGen && pdaDatosRef.mostrarFechaGen}">
															<a href="./pdaP19fechaActivacionPopup.do?origen=DR&mostrarFFPP=${pdaDatosRef.mostrarFFPP}&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&fechaActivacion=${pdaDatosRef.strFechaGen}">
																<fieldset id="pda_p12_pedidoAutomaticoFieldsetActivo">
																	<p class="pda_p12_parrafoPedidoAutomaticoActivoLink"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
																</fieldset>
															</a>
														</c:when>
														<c:otherwise>
															<fieldset id="pda_p12_pedidoAutomaticoFieldsetActivo">
																<p class="pda_p12_parrafoPedidoAutomaticoActivo"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
															</fieldset>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>	-->		
											<c:choose>
												<c:when test="${not empty pdaDatosRef.strFechaGen && pdaDatosRef.mostrarFechaGen}">
													<a href="./pdaP19fechaActivacionPopup.do?origen=DR&mostrarFFPP=${pdaDatosRef.mostrarFFPP}&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}&origenGISAE=${origenGISAE}&fechaActivacion=${pdaDatosRef.strFechaGen}">
														<fieldset id="pda_p12_pedidoAutomaticoFieldsetActivo">
															<p class="pda_p12_parrafoPedidoAutomaticoActivoLink"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
														</fieldset>
													</a>
												</c:when>
												<c:otherwise>
													<fieldset id="pda_p12_pedidoAutomaticoFieldsetActivo">
														<p class="pda_p12_parrafoPedidoAutomaticoActivo"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivo" /></p>
													</fieldset>
												</c:otherwise>
											</c:choose>									
										</c:when>
										
										
										
									<c:otherwise>	
																			
											<!--<c:choose>
												<c:when test="${(user.centro.esCentroCaprabo || user.centro.esCentroCapraboEspecial)}">			
																				
													<c:choose>
													
														<c:when test="${(user.centro.esCentroCapraboNuevo)}">
														
														<!--  
															<c:choose>
																<c:when test="${pdaDatosRef.pedir eq 'N'}">
																	<c:choose>
																		<c:when test="${not empty pdaDatosRef.motivoCaprabo}">	
																			<a href="./pdaP14refCentroPopup.do?procede=capturaRestos&codArt=${pdaDatosCab.codArtCab}">
																				<fieldset id="pda_p12_pedidoAutomaticoFieldsetNoActivo">
																					<p class="pda_p12_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																				</fieldset>
																			</a>
																		</c:when>
																		<c:otherwise>
																			<fieldset id="pda_p12_pedidoAutomaticoFieldsetNoActivo">
										   										<p class="pda_p12_parrafoPedidoAutomaticoNoActivo"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																			</fieldset>
																		</c:otherwise>
																	</c:choose>
																</c:when>														
															</c:choose>		
															
														--><!-- >
															<a href="./pdaP14refCentroPopup.do?procede=capturaRestos&codArt=${pdaDatosCab.codArtCab}">
																<fieldset id="pda_p12_pedidoAutomaticoFieldsetNoActivo">
																	<p class="pda_p12_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																</fieldset>
															</a>
															
															
														</c:when>
														
															
														
														<c:otherwise>
													
															<c:choose>
																<c:when test="${pdaDatosRef.pedir eq 'N'}">
																	<c:choose>
																		<c:when test="${not empty pdaDatosRef.motivoCaprabo}">	
																			<a href="./pdaP16MotivosTengoMPPopupCaprabo.do?codArt=${pdaDatosCab.codArtCab}&procede=capturaRestos&codArtRel=${pdaDatosRef.tMisMcgCaprabo.sustituidaPor}">
																				<fieldset id="pda_p12_pedidoAutomaticoFieldsetNoActivo">
																					<p class="pda_p12_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																				</fieldset>
																			</a>
																		</c:when>
																		<c:otherwise>
																			<fieldset id="pda_p12_pedidoAutomaticoFieldsetNoActivo">
										   										<p class="pda_p12_parrafoPedidoAutomaticoNoActivo"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																			</fieldset>
																		</c:otherwise>
																	</c:choose>
																</c:when>														
															</c:choose>		
														</c:otherwise>
														
														
													</c:choose>
												  </c:when>	
												<c:otherwise>
										
													<a href="./pdaP14refCentroPopup.do?procede=capturaRestos&codArt=${pdaDatosCab.codArtCab}">
														<fieldset id="pda_p12_pedidoAutomaticoFieldsetNoActivo">
															<p class="pda_p12_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
														</fieldset>
													</a>									
												</c:otherwise>																																												
											</c:choose>-->												
											
											<a href="./pdaP14refCentroPopup.do?procede=capturaRestos&codArt=${pdaDatosCab.codArtCab}">
												<fieldset id="pda_p12_pedidoAutomaticoFieldsetNoActivo">
													<p class="pda_p12_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
												</fieldset>
											</a>											
										</c:otherwise>
								
								
								
									</c:choose>
								</c:otherwise>
							</c:choose>	
						</div>
						
						
						
						
						
						
						
						<div id= "pda_p12_FFPP_div">
							<c:choose>
								<c:when test="${pdaDatosRef.mostrarFFPP eq 'S'}">
									<div id ="pda_p12_FFPP">
										<a href="./pdaP12FFPPActivo.do?mostrarFFPP=S&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}"><spring:message code="pda_p12_datosReferencia.FFPP" /></a>
									</div>
								</c:when>
								<c:when test="${pdaDatosRef.mostrarFFPP eq 'N'}">
									<div id ="pda_p12_FFPP">
										<a href="./pdaP12FFPPActivo.do?mostrarFFPP=N&codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.codArtRel}"><spring:message code="pda_p12_datosReferencia.retornoFFPP" /></a>
									</div>
								</c:when>
								<c:otherwise>
									<div id ="pda_p12_FFPP_vacio">
									</div>
								</c:otherwise>
							</c:choose>

								
							<c:choose>
								<c:when test="${pdaDatosRef.mostrarSustARef eq 'S'}">
									<div id ="pda_p12_SustARef">
										<a href="./pdaP12FFPPActivo.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.sustitutaDe}"><spring:message code="pda_p12_datosReferencia.SustARef" /></a>
									</div>
								</c:when>
								<c:when test="${pdaDatosRef.mostrarSustPorRef eq 'S'}">
									<div id ="pda_p12_SustPorRef">
										<a href="./pdaP12FFPPActivo.do?codArt=${pdaDatosRef.codArt}&codArtRel=${pdaDatosRef.sustituidaPor}"><spring:message code="pda_p12_datosReferencia.SustPorRef" /></a>
									</div>
								</c:when>
							</c:choose>

							
						</div>
					</div>
					
					<c:if test="${ofertaPVP != null}">
						<div id="pda_p12_datosReferencia_bloquePvp">
							<c:if test="${ofertaPVP.tarifa gt 0}">
								<div id="pda_p12_datosReferencia_bloquePvp_v1">
									<label id="pda_p12_lbl_pvp" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.pvp" /></label>
									<label id="pda_p12_lbl_pvpVal" class="valorCampo" >${ofertaPVP.tarifaStr}</label>								
								</div>	
							</c:if>
							<c:if test="${ofertaPVP.flgMostrarOfertaPistola == 'S'}">			
								<div id="pda_p12_datosReferencia_bloquePvp_v2">
									<label id="pda_p12_lbl_pvpOferta" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.pvpOferta" /></label>
									<label id="pda_p12_lbl_pvpOfertaVal" class="valorCampo" >${ofertaPVP.pvpOferStr}</label>									
								</div>	
							</c:if>			
						</div>
					</c:if>
					
					<div id="pda_p12_datosReferencia_bloque3">
						<div id="pda_p12_datosReferencia_bloque3_v1">
							<div id="pda_p12_datosReferencia_bloque3_v1_parr1">
								<label id="pda_p12_lbl_uc" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.uc" /></label>
								<label id="pda_p12_lbl_ucVal" class="valorCampo" ><spring:bind path="pdaDatosRef.uniCajaServ">${status.value}</spring:bind></label>
								<label id="pda_p12_lbl_ufp" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.ufp" /></label>
								<label id="pda_p12_lbl_ufpVal" class="valorCampo" ><spring:bind path="pdaDatosRef.ufp">${status.value}</spring:bind></label>								
							</div>	
						</div>				
						<div id="pda_p12_datosReferencia_bloque3_v2">
							<c:choose>
								<c:when test="${user.centro.esCentroCapraboEspecial}">
									<c:choose>
										<c:when test="${plataformaAprovisionamientoMercancia != null && plataformaAprovisionamientoMercancia.codLocOri != null}">
											<label id="pda_p12_lbl_plataformaOrionVal" class="etiquetaCampoNegrita" >${plataformaAprovisionamientoMercancia.plataformaCapraboOrion}</label>
										</c:when>
										<c:otherwise>
											<div id="pda_p12_datosReferencia_bloque3_v2_parr1">
												<label id="pda_p12_lbl_centralizada" class="etiquetaCampoNegrita" ><spring:bind path="pdaDatosRef.tipoAprov">${status.value}</spring:bind></label>
											</div>				
											<c:choose>
												<c:when test="${pdaDatosRef.flgGamaDiscont eq 'S'}">
													<div id="pda_p12_datosReferencia_bloque3_v2_parr2">
														<label id="pda_p12_lbl_discontinua" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.discontinua" /></label>
													</div>
												</c:when>
											</c:choose>	
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>								
									<div id="pda_p12_datosReferencia_bloque3_v2_parr1">
										<label id="pda_p12_lbl_centralizada" class="etiquetaCampoNegrita" ><spring:bind path="pdaDatosRef.tipoAprov">${status.value}</spring:bind></label>
									</div>				
									<c:choose>
										<c:when test="${pdaDatosRef.flgGamaDiscont eq 'S'}">
									<div id="pda_p12_datosReferencia_bloque3_v2_parr2">
										<label id="pda_p12_lbl_discontinua" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.discontinua" /></label>
									</div>
										</c:when>
									</c:choose>	
								</c:otherwise>
							</c:choose>		
						</div>				
					</div>
					
					
					
					<!-- recibir , oferta, discon. -->
						<div id="pda_p12_datosReferencia_bloque4">
							<label id="pda_p12_lbl_pdteRecibir" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.pdteRecibir" /></label>
							<label id="pda_p12_lbl_pdteRecibirVal" class="valorCampo">
							${pdaDatosRef.cantHoy}/${pdaDatosRef.cantFutura}</label>
							
							<c:if test="${ofertaPVP != null}">
								<c:if test="${ofertaPVP.flgMostrarOfertaPistola == 'S'}">	
									<label id="pda_p12_lbl_oferta" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.oferta" /></label>
									<label id="pda_p12_lbl_ofertaVal" class="valorCampo" >${ofertaPVP.annoOferta}-${ofertaPVP.codOferta} </label>
								</c:if>	
							</c:if>					
						</div>
					
					
					<!-- 48332
					<div id="pda_p12_datosReferencia_bloque4">
						<fieldset id="pda_p12_ventasMedias">
							<legend id="pda_p12_ventasMediasLegend"> <spring:message code="pda_p12_datosReferencia.legendVentasMedias" /> </legend>
							<table id="pda_p12_ventasMediasTablaEstructura" border="1">
								<thead class="tablaCabecera">
								    <tr>
								        <th class="pda_p12_ventasMediasTablaTh"><spring:message code="pda_p12_datosReferencia.tarifa" /></th>
								        <th class="pda_p12_ventasMediasTablaTh"><spring:message code="pda_p12_datosReferencia.competencia" /></th>
								        <th class="pda_p12_ventasMediasTablaTh"><spring:message code="pda_p12_datosReferencia.ofertaTabla" /></th>
								        <th class="pda_p12_ventasMediasTablaTh"><spring:message code="pda_p12_datosReferencia.anticipada" /></th>
								    </tr>
								</thead>
								<tbody class="tablaContent">
								    <tr> 							
								    	<td id="pda_p12_ventasMediasTdTarifa" class="pda_p12_ventasMediasTablaTd"><spring:bind path="pdaDatosRef.tarifa">${status.value}</spring:bind></td>
								        <td id="pda_p12_ventasMediasTdCompetencia" class="pda_p12_ventasMediasTablaTd"><spring:bind path="pdaDatosRef.competencia">${status.value}</spring:bind></td>
								        <td id="pda_p12_ventasMediasTdOferta" class="pda_p12_ventasMediasTablaTd"><spring:bind path="pdaDatosRef.oferta">${status.value}</spring:bind></td>
								        <td id="pda_p12_ventasMediasTdAnticipada" class="pda_p12_ventasMediasTablaTd"><spring:bind path="pdaDatosRef.anticipada">${status.value}</spring:bind></td>
								    </tr>
								</tbody>
							</table>
						</fieldset>	
					</div>
					-->
					
					<div id="pda_p12_datosReferencia_bloqueVentasMedias">
							<label id="pda_p12_ventasMediasLegend" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.legendVentasMediasTarifa" /></label>
							<label id="pda_p12_ventasMediasTdTarifa" class="valorCampo" ><spring:bind path="pdaDatosRef.tarifa">${status.value}</spring:bind></label>
							
					</div>
					
					
					<div id="pda_p12_datosReferencia_bloque5">
						<fieldset id="pda_p12_imagenComercial">
							
						   			<c:choose>						   
						   	  			<c:when test="${pdaDatosRef.flgFacingX ne 'S' || pdaDatosRef.mostrarFFPP eq 'N' || (pdaDatosRef.vPlanogramaTipoP != null && pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S')}"> 
							  	 			<c:choose>
							  					<c:when test="${pdaDatosRef.flgColorImplantacion eq 'VERDE'}">
													<legend id="pda_p12_imagenComercialLegendVerde">
														<span>
															<a id="pda_p12_imagenComercialSpanVerde" href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
																<c:choose>
																	<c:when test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
																	</c:when>
																	<c:when test="${pdaDatosRef.mostrarFFPP == null || pdaDatosRef.mostrarFFPP eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
																	</c:when>
																	<c:otherwise>
																		<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
																	</c:otherwise>
																</c:choose>
															</a>
														</span>
													</legend>
							  	 				</c:when>
							  	 				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'ROJO'}">
							   						<legend id="pda_p12_imagenComercialLegendRojo">
							   							 <span>
							   							 	<a id="pda_p12_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
							   							 		<c:choose>
							   							 			<c:when test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
																	</c:when>
																	<c:when test="${pdaDatosRef.mostrarFFPP == null || pdaDatosRef.mostrarFFPP eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
																	</c:when>
																	<c:otherwise>
																		<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
																	</c:otherwise>
																</c:choose>
							   							 	</a>
							   							 </span>
							   						</legend>
							  	 				</c:when>
							  	 				<c:otherwise>
							   						<legend id="pda_p12_imagenComercialLegend"> 
							   							<c:choose>
							   								<c:when test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S'}">
																<spring:message code="pda_p12_datosReferencia.legendImagenComercialCajaExp" />
															</c:when>
															<c:when test="${pdaDatosRef.mostrarFFPP == null || pdaDatosRef.mostrarFFPP eq 'S'}">
																<spring:message code="pda_p12_datosReferencia.legendImagenComercialCajas" />
															</c:when>
															<c:otherwise>
																<spring:message code="pda_p12_datosReferencia.legendImagenComercialFFPP" />
															</c:otherwise>
														</c:choose>
							   						</legend>
							  	 				</c:otherwise>
							  	 			</c:choose>
							 			</c:when>	  								 
										 <c:when test="${pdaDatosRef.flgFacingX eq 'S' && (pdaDatosRef.mostrarFFPP ne 'N') && (pdaDatosRef.vPlanogramaTipoP.esCajaExp ne 'S')}"> 
										     <c:choose>
										  		 <c:when test="${pdaDatosRef.flgColorImplantacion eq 'VERDE'}">
													<legend id="pda_p12_imagenComercialLegendVerde">
														 <span>
														 	<a id="pda_p12_imagenComercialSpanVerde" href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
														 		<c:choose>
														 			<c:when test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
																	</c:when>
																	<c:when test="${pdaDatosRef.mostrarFFPP == null || pdaDatosRef.mostrarFFPP eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
																	</c:when>
																	<c:otherwise>
																		<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
																	</c:otherwise>
																</c:choose>
														 	</a>
														 </span>
													</legend>
										  	 	 </c:when>
										  	 	 <c:when test="${pdaDatosRef.flgColorImplantacion eq 'ROJO'}">
										   			<legend id="pda_p12_imagenComercialLegendRojo">							
										   				<span>
										   					<a id="pda_p12_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										   						<c:choose>
										   							<c:when test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
																	</c:when>
																	<c:when test="${pdaDatosRef.mostrarFFPP == null || pdaDatosRef.mostrarFFPP eq 'S'}">
																		<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
																	</c:when>
																	<c:otherwise>
																		<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
																	</c:otherwise>
																</c:choose>
										   					</a>
										   				</span>
										   			</legend>
										  	 	 </c:when>
										  	 	 <c:otherwise>
										   			<legend id="pda_p12_imagenComercialLegend"> 
										   				<c:choose>
										   					<c:when test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S'}">
																<spring:message code="pda_p12_datosReferencia.legendImagenComercialCajaExp" />
															</c:when>
															<c:when test="${pdaDatosRef.mostrarFFPP == null || pdaDatosRef.mostrarFFPP eq 'S'}">
																<spring:message code="pda_p12_datosReferencia.legendImagenComercialCajas" />
															</c:when>
															<c:otherwise>
																<spring:message code="pda_p12_datosReferencia.legendImagenComercialFFPP" />
															</c:otherwise>
														</c:choose>
										   			 </legend>
										  	 	 </c:otherwise>
										  	 </c:choose> 
										 </c:when>	   
						   			</c:choose>	
						  
						   	${data.vPlanogramaTipoP.facingAncho}
						   	${data.vPlanogramaTipoP.facingAlto}
							<c:if test="${pdaDatosRef.vPlanogramaTipoP != null && pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S'}">
								<div>
									<label class="etiquetaCampoNegrita"> <spring:message code="pda_p12_datosReferencia.implCajas.ancho" /> </label>${pdaDatosRef.vPlanogramaTipoP.facingAncho}
									<label class="etiquetaCampoNegrita"> <spring:message code="pda_p12_datosReferencia.implCajas.alto" /> </label>${pdaDatosRef.vPlanogramaTipoP.facingAlto}
								</div>					    	 	
							</c:if>
								<c:choose>		

								<c:when test="${pdaDatosRef.flgFacing eq 'S' && pdaDatosRef.flgFacingCapacidad ne 'S'}"> <!-- Facing puro -->
									<div id="pda_p12_datosReferencia_bloque5_parr1">
										<c:choose>
											<c:when test="${pdaDatosRef.parametrizadoFacing eq 'S'}"> 
												<c:if test="${empty guardadoSfm }">	
													<label id="pda_p12_lbl_facingEnlace" class="etiquetaCampoNegrita"><a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message code="pda_p12_datosReferencia.facing" /></a></label>
												</c:if>	
												<c:if test="${not empty guardadoSfm }">	
													<label id="pda_p12_lbl_facingEnlace" class="etiquetaCampoNegrita"><a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><span class="pda_p12_enlaceSfmOk"><spring:message code="pda_p12_datosReferencia.facing" /></span></a></label>
												</c:if>	
											</c:when>
											<c:otherwise>
												<label id="pda_p12_lbl_facing" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing" /></label>
											</c:otherwise>
										</c:choose>	
										<label id="pda_p12_lbl_facingVal" class="valorCampo">
											<spring:bind path="pdaDatosRef.facingCentro">${status.value}</spring:bind>
											<c:if test="${not empty pdaDatosRef.facingCentroSIA}">
												(${pdaDatosRef.facingCentroSIA})
											</c:if>
											<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
												<div id="pda_p12_datosReferencia_bloque5_parr2_0_1">
													<label class="etiquetaCampoNegrita">
														<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />																     																	     																     																     	
													</label>
													<label class="valorCampo">															     															     																	     	
														${pdaDatosRef.multiplicadorFac}															     																     	
													</label>
												</div>											
												<c:if test="${pdaDatosRef.flgFacingX eq 'S' && (pdaDatosRef.mostrarFFPP ne 'N')}">
													<div id="pda_p12_datosReferencia_bloque5_parr2_0_2">
														<label class="etiquetaCampoNegrita">
															<spring:message code="pda_p12_datosReferencia.legendIMCImplantacion1" />																     																	     																     																     	
														</label>
														<label class="valorCampo">															     															     																	     	
															${pdaDatosRef.imc}															     																     	
														</label>
													</div>															     	
												</c:if>
											</c:if>
										</label>
									</div>
								</c:when>
								<c:otherwise>
									<c:choose>
									<c:when test="${pdaDatosRef.flgSfmCapacidad eq 'S'}">  
										<div id="pda_p12_datosReferencia_bloque5_parr1">
											<c:choose>
												<c:when test="${pdaDatosRef.parametrizadoSfmCap eq 'S' && pdaDatosRef.pedir eq 'S'}">
													<c:if test="${empty guardadoSfm}">
														<label id="pda_p12_lbl_sfm" class="etiquetaCampoNegrita"><a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message code="pda_p12_datosReferencia.sfm" /></a></label>
													</c:if>
													<c:if test="${not empty guardadoSfm}">
														<label id="pda_p12_lbl_sfm" class="etiquetaCampoNegrita"><a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><span class="pda_p12_enlaceSfmOk"><spring:message code="pda_p12_datosReferencia.sfm" /></span></a></label>
													</c:if>
												</c:when>
												<c:otherwise>
													<label id="pda_p12_lbl_sfm" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.sfm" /></label>
												</c:otherwise>
											</c:choose>	
											<label id="pda_p12_lbl_sfmVal" class="valorCampo"><spring:bind path="pdaDatosRef.sfm">${status.value}</spring:bind><c:if test="${pdaDatosRef.mostrarCantidadSfmSIA eq 'SI'}">(<spring:bind path="pdaDatosRef.cantidaSfmSIA">${status.value}</spring:bind>)</c:if></label>
										</div>
									</c:when>
									<c:otherwise>  
									  <c:choose>
  										<c:when test="${pdaDatosRef.flgFacingX eq 'S'}"> <!-- Pet. 53005 facing_x -->
 											<div id="pda_p12_datosReferencia_bloque5_parr1X">
  										</c:when>
  										<c:otherwise>
											<div id="pda_p12_datosReferencia_bloque5_parr1">		     		
  										</c:otherwise>
										</c:choose>
										
										
											<div id="pda_p12_datosReferencia_bloque5_parr1_1"> 
												<c:choose>
													<c:when test="${pdaDatosRef.esAutoServicio eq 'S' && pdaDatosRef.parametrizadoSfmCap eq 'S' && pdaDatosRef.pedir eq 'S'}">
														<c:if test="${empty guardadoSfm}">
															<label id="pda_p12_lbl_capacidadEnlace" class="etiquetaCampoNegrita">
																<a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message code="pda_p12_datosReferencia.capacidadLineal" /></a>
															</label>
														</c:if>
														<c:if test="${not empty guardadoSfm}">
															<label id="pda_p12_lbl_capacidadEnlace" class="etiquetaCampoNegrita">
																<a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}">
																	<span class="pda_p12_enlaceSfmOk"><spring:message code="pda_p12_datosReferencia.capacidadLineal" /></span>
																</a>
															</label>
														</c:if>
													</c:when>
													<c:when test="${pdaDatosRef.flgCapacidad eq 'S'}"> <!-- Pet. 53005 Capacidad -> Facing X -->
														<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita">
															<a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}">
																<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
																</span>
															</a>
														</label>
													</c:when>
													<c:otherwise>
														<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidadLineal" /></label>
													</c:otherwise>
												</c:choose>
												<label id="pda_p12_lbl_capacidadVal" class="valorCampo">
													<spring:bind path="pdaDatosRef.capacidadLineal">${status.value}</spring:bind>
													<c:if test="${pdaDatosRef.mostrarCantidadCapSIA eq 'SI' && pdaDatosRef.capacidadLineal ne pdaDatosRef.cantidadCapLinealSIA}">
														(<spring:bind path="pdaDatosRef.cantidadCapLinealSIA">${status.value}</spring:bind>)
													</c:if>
												</label>
											</div>
											
										
											<c:choose>
												<c:when test="${pdaDatosRef.pedidoActivo eq 'S'}">
					                                <c:if test="${(pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
														<c:choose>
															<c:when test="${pdaDatosRef.mostrarFFPP ne 'N' && pdaDatosRef.flgFacingX eq 'S' && pdaDatosRef.vPlanogramaTipoP != null}">
																<!-- Se pone la clase pda_p12_cap1CuandoExisteMultConVPlanoPporque en el caso de existir multiplicador y VPlanogramaP
																en facingX, el multiplicador aparece debajo de Fac.Lin(Al*An) y Cap1 y Fac1 no aparecen alineados. -->
																<div id="pda_p12_datosReferencia_bloque5_parr1_2" class="pda_p12_cap1CuandoExisteMultConVPlanoP">
																	<label id="pda_p12_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
																	<label id="pda_p12_lbl_capacidad1Val" class="valorCampo"><spring:bind path="pdaDatosRef.capacidad1">${status.value}</spring:bind></label>
																</div>	
															</c:when>
															<c:otherwise>
																<div id="pda_p12_datosReferencia_bloque5_parr1_2" class="pda_p12_cap1CuandoExisteMultConVPlanoP">
																	<label id="pda_p12_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
																	<label id="pda_p12_lbl_capacidad1Val" class="valorCampo"><spring:bind path="pdaDatosRef.capacidad1">${status.value}</spring:bind></label>
																</div>	
															</c:otherwise>
														</c:choose>														
													</c:if>
													<!-- 
					                                <c:if test="${(pdaDatosRef.capacidad2 ne null && pdaDatosRef.capacidad2 ne 0 && pdaDatosRef.capacidad2 ne '') || (pdaDatosRef.facing2 ne null && pdaDatosRef.facing2 ne 0 && pdaDatosRef.facing2 ne '')}">
														<div id="pda_p12_datosReferencia_bloque5_parr1_3">
															<label id="pda_p12_lbl_capacidad2" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad2" /></label>
															<label id="pda_p12_lbl_capacidad2Val" class="valorCampo"><spring:bind path="pdaDatosRef.capacidad2">${status.value}</spring:bind></label>
														</div>	
													</c:if>
													--> 
												</c:when>
											</c:choose>			
																			
										</div>
										<c:choose>
  											<c:when test="${pdaDatosRef.flgFacingX eq 'S'}"> <!-- Pet. 53005 facing_x -->
 												<div id="pda_p12_datosReferencia_bloque5_parr2X">
  											</c:when>
  											<c:otherwise>
												<div id="pda_p12_datosReferencia_bloque5_parr2">		     		
  											</c:otherwise>
										</c:choose>										
											<c:choose>
												<c:when test="${pdaDatosRef.flgFacing eq 'S' && pdaDatosRef.flgFacingCapacidad eq 'S'}"> <!-- Facing-Capacidad -> Pet. 53005 Facing X-->
													<div id="pda_p12_datosReferencia_bloque5_parr2_0">
													   <!--   <label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita"><a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message code="pda_p12_datosReferencia.facingLineal" /></a></label> -->
													     <div id="pda_p12_datosReferencia_bloque5_parr2_0_0">
														     <label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita">
														     	<a href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}">
															     	<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
															     		<spring:message code="pda_p12_datosReferencia.facingLineal" />
															     	</c:if>
															     	<c:if test="${pdaDatosRef.mostrarFFPP eq 'N' ||  (pdaDatosRef.vPlanogramaTipoP != null && pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S')}">
															     		<spring:message code="pda_p12_datosReferencia.facingLineal" />
															     	</c:if>
														     	</a>
														     </label>
													     	<!--  <label id="pda_p12_lbl_linealFacingVal" class="valorCampo"><spring:bind path="pdaDatosRef.facingCentro">${status.value}</spring:bind></label> -->
													     	<label id="pda_p12_lbl_linealFacingVal" class="valorCampo">
													     		<spring:bind path="pdaDatosRef.facingCentro">${status.value}</spring:bind>
													     		<c:if test="${not empty pdaDatosRef.facingCentroSIA}">
													     			(${pdaDatosRef.facingCentroSIA})
													     		</c:if>													     		
													     	</label>
												     	</div>
												     	<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
															<div id="pda_p12_datosReferencia_bloque5_parr2_0_1">
																<label class="etiquetaCampoNegrita">
																    <spring:message code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />																     																	     																     																     	
																</label>
																<label class="valorCampo">															     															     																	     	
																    ${pdaDatosRef.multiplicadorFac}															     																     	
																</label>
															 </div>															     	
														</c:if>
														<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
													     	<!-- /X${pdaDatosRef.multiplicadorFac}-->
													     	<c:if test="${pdaDatosRef.flgFacingX eq 'S' && (pdaDatosRef.mostrarFFPP ne 'N')}">
																<div id="pda_p12_datosReferencia_bloque5_parr2_0_2">
																	<label class="etiquetaCampoNegrita">
																		<spring:message code="pda_p12_datosReferencia.legendIMCImplantacion1" />																     																	     																     																     	
																	</label>
																	<label class="valorCampo">															     															     																	     	
																	    ${pdaDatosRef.imc}															     																     	
																	</label>
																</div>															     	
															</c:if>
													    </c:if>
													</div>
												</c:when>
												<c:when test="${pdaDatosRef.flgFacingX eq 'S'}"> <!-- Pet. 53005 Facing X -->
													<div id="pda_p12_datosReferencia_bloque5_parr2_0">
													    <c:choose>
													        <c:when test="${pdaDatosRef.vPlanogramaTipoP != null}">
															    <div id="pda_p12_datosReferencia_bloque5_parr2_0_0">
															    	 <c:choose>
																    	 <c:when test="${pdaDatosRef.mostrarFFPP ne 'N'}">
																    	 	<c:choose>
																	    	 	<c:when test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp ne 'S'}">
																				    <c:choose>
																				    	<c:when test="${pdaDatosRef.vPlanogramaTipoP.facingAlto eq 0 || pdaDatosRef.vPlanogramaTipoP.facingAlto == null || pdaDatosRef.vPlanogramaTipoP.facingAncho eq 0 || pdaDatosRef.vPlanogramaTipoP.facingAncho == null}">
																					    	<label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita">														    		
																					    		<spring:message code="pda_p12_datosReferencia.facingLineal"/>															    		
																					    	</label>
																				    	</c:when>
																				    	<c:otherwise>
																				    		<label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita">														    		
																				    			<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoP"/>															    		
																				    		</label>
																				    	</c:otherwise>
																				    </c:choose>																			   
																			    </c:when>
																			    <c:otherwise>
																			    	<label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita">														    		
																			    		<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoPFFPP"/>															    		
																			   		 </label>
																			    </c:otherwise>
																		    </c:choose>																		    
																	    </c:when>
																	    <c:when test="${pdaDatosRef.mostrarFFPP eq 'N'}">
																	     	<label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita">														    		
																		    	<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoPFFPP" />															    		
																		    </label>
																	    </c:when>
																    </c:choose>
															     	<label id="pda_p12_lbl_linealFacingVal" class="valorCampo">
															     		<spring:bind path="pdaDatosRef.facingLineal">${status.value}</spring:bind>
															     		<c:if test="${pdaDatosRef.mostrarCantidadCapSIA eq 'SI'}">
															     			(<spring:bind path="pdaDatosRef.cantidadFacLinealSIA">${status.value}</spring:bind>)
															     		</c:if>
															     		<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">															     																			    	 
																	    	 <c:if test="${pdaDatosRef.vPlanogramaTipoP.esCajaExp ne 'S' && (pdaDatosRef.vPlanogramaTipoP.facingAlto ne 0 && pdaDatosRef.vPlanogramaTipoP.facingAlto != null && pdaDatosRef.vPlanogramaTipoP.facingAncho ne 0 && pdaDatosRef.vPlanogramaTipoP.facingAncho != null)}">																				   
															     				(<spring:bind path="pdaDatosRef.vPlanogramaTipoP.facingAncho">${status.value}</spring:bind>*<spring:bind path="pdaDatosRef.vPlanogramaTipoP.facingAlto">${status.value}</spring:bind>)
																			 </c:if>																			    
															     		</c:if>
															     	</label>
														     	</div>
														     	<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
															     	<div id="pda_p12_datosReferencia_bloque5_parr2_0_1">
																     	<label class="etiquetaCampoNegrita">
																     		<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />																     																	     																     																     	
																     	</label>
																     	<label class="valorCampo">															     															     																	     	
																     		${pdaDatosRef.multiplicadorFac}															     																     	
																     	</label>
															     	</div>															     	
														     	</c:if>
														     	<c:if test="${pdaDatosRef.flgFacingX eq 'S' && (pdaDatosRef.mostrarFFPP ne 'N')}">
															     	<div id="pda_p12_datosReferencia_bloque5_parr2_0_2">
																     	<label class="etiquetaCampoNegrita">
																     		<spring:message code="pda_p12_datosReferencia.legendIMCImplantacion1" />																     																	     																     																     	
																     	</label>
																     	<label class="valorCampo">															     															     																	     	
																     		${pdaDatosRef.imc}															     																     	
																     	</label>
															     	</div>															     	
														     	</c:if>
													     	</c:when>
													     	<c:otherwise>
													     		<div id="pda_p12_datosReferencia_bloque5_parr2_0_0">
														     		<label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita">
																    	<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
																		    <spring:message code="pda_p12_datosReferencia.facingLineal" />											    																	
																    	</c:if>
																    	<c:if test="${pdaDatosRef.mostrarFFPP eq 'N' || (pdaDatosRef.vPlanogramaTipoP != null && pdaDatosRef.vPlanogramaTipoP.esCajaExp eq 'S')}">															    		    		
																    		<spring:message code="pda_p12_datosReferencia.facingLineal" />
																    	</c:if>
																    </label>
															     	<label id="pda_p12_lbl_linealFacingVal" class="valorCampo">
															     		<spring:bind path="pdaDatosRef.facingLineal">${status.value}</spring:bind>
															     		<c:if test="${pdaDatosRef.mostrarCantidadCapSIA eq 'SI'}">
															     			(<spring:bind path="pdaDatosRef.cantidadFacLinealSIA">${status.value}</spring:bind>)
															     		</c:if>	
															     	</label>	
														     	</div>	
														     	<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
																	<div id="pda_p12_datosReferencia_bloque5_parr2_0_1">
																		<label class="etiquetaCampoNegrita">
																   		 	<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />																     																	     																     																     	
																		</label>
																		<label class="valorCampo">															     															     																	     	
																    		${pdaDatosRef.multiplicadorFac}															     																     	
																		</label>
															 		</div>															     	
																</c:if>											     	
														     	<c:if test="${pdaDatosRef.mostrarFFPP ne 'N'}">
														     		<!-- /X${pdaDatosRef.multiplicadorFac}-->
														     		<c:if test="${pdaDatosRef.flgFacingX eq 'S' && (pdaDatosRef.mostrarFFPP ne 'N')}">
																	   	<div id="pda_p12_datosReferencia_bloque5_parr2_0_2">
																	     	<label class="etiquetaCampoNegrita">
																	     		<spring:message code="pda_p12_datosReferencia.legendIMCImplantacion1" />																     																	     																     																     	
																	     	</label>
																	     	<label class="valorCampo">															     															     																	     	
																	     		${pdaDatosRef.imc}															     																     	
																	     	</label>
																     	</div>															     	
													     			</c:if>
													     		</c:if>														     
													     	</c:otherwise>
												     	</c:choose>
													</div>
												</c:when>
												<c:otherwise>
													<div id="pda_p12_datosReferencia_bloque5_parr2_1">	
														<label id="pda_p12_lbl_linealFacing" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facingLineal" /></label>
														<label id="pda_p12_lbl_linealFacingVal" class="valorCampo"><spring:bind path="pdaDatosRef.facingLineal">${status.value}</spring:bind><c:if test="${pdaDatosRef.mostrarCantidadCapSIA eq 'SI'}">(<spring:bind path="pdaDatosRef.cantidadFacLinealSIA">${status.value}</spring:bind>)</c:if></label>
														
													</div>
												</c:otherwise>	
											</c:choose>		
									
											<c:choose>
												<c:when test="${pdaDatosRef.pedidoActivo eq 'S'}">
					                                <c:if test="${(pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
														<div id="pda_p12_datosReferencia_bloque5_parr2_2">
															<label id="pda_p12_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
															<label id="pda_p12_lbl_facing1Val" class="valorCampo"><spring:bind path="pdaDatosRef.facing1">${status.value}</spring:bind></label>
														</div>	
													</c:if>
													<!--
					                                <c:if test="${(pdaDatosRef.capacidad2 ne null && pdaDatosRef.capacidad2 ne 0 && pdaDatosRef.capacidad2 ne '') || (pdaDatosRef.facing2 ne null && pdaDatosRef.facing2 ne 0 && pdaDatosRef.facing2 ne '')}">
														<div id="pda_p12_datosReferencia_bloque5_parr2_3">
															<label id="pda_p12_lbl_facing2" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing2" /></label>
															<label id="pda_p12_lbl_facing2Val" class="valorCampo"><spring:bind path="pdaDatosRef.facing2">${status.value}</spring:bind></label>
														</div>	
													</c:if>
													 -->
												</c:when>
											</c:choose>												
										</div>										
									</c:otherwise>
								</c:choose>
							</c:otherwise>
							</c:choose>
						</fieldset>	
					</div>
				</c:otherwise>
			</c:choose>						
		</div>	
		<input id="pda_p12_codArt" type="hidden" value="${pdaDatosRef.codArt}"/>	
		<input id="pda_p12_tieneFoto" type="hidden" value="${pdaDatosRef.tieneFoto}"/>
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>