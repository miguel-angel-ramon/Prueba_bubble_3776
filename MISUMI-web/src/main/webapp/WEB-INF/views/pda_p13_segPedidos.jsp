<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="actionRefValue" value="pdaP13SegPedidos.do"/>
<c:if test="${procede eq 'pdaP115PrehuecosLineal'}">
    <c:set var="origenValue" value="PHPed"/>
    <c:set var="procede" value="pdaP115PrehuecosLineal"/>
</c:if>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda_p13_segPedidos.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda_p13SegPedidos.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="${actionRefValue}"></jsp:param>
    <jsp:param name="origen" value="${origenValue}"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<c:choose>
				<c:when test="${empty (pdaSegPedidos.codArt)}">
					<div id="pda_p13_segPedidos_bloque1">
						<div id="pda_p13_segPedidos_bloque1_subBloque1">
							<c:choose>
								<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
									<label id="pda_p13_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p115_prehuecos.prehueco"/></label>
								</c:when>
								<c:otherwise>
									<label id="pda_p13_lbl_datosReferencia" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p13_segPedidos.datosReferencia"/></label>
								</c:otherwise>
							</c:choose>
							<label id="pda_p13_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p13_segPedidos.separadorEnlaces" /></label>
							<label id="pda_p13_lbl_segPedidos" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p13_segPedidos.segPedidos" /></label>
							<label id="pda_p13_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p13_segPedidos.separadorEnlaces" /></label>
						</div>
						<div id="pda_p13_segPedidos_bloque1_subBloque2">							
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="pda_p13_origenGISAE" id="pda_p13_origenGISAE" value="${origenGISAE}"/>
					<div id="pda_p13_segPedidos_bloque1">
						<div id="pda_p13_segPedidos_bloque1_subBloque1">
							<c:choose>
								<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
									<c:choose>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP115PrehuecosLineal.do?mostrarFFPP=N&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&procede=pdaP115PrehuecosLineal&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&cleanAll=I"><spring:message code="pda_p13_segPedidos.prehuecoEnlace"/></a>
										</c:when>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP115PrehuecosLineal.do?mostrarFFPP=S&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&procede=pdaP115PrehuecosLineal&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&cleanAll=I"><spring:message code="pda_p13_segPedidos.prehuecoEnlace"/></a>
										</c:when>
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP115PrehuecosLineal.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&procede=pdaP115PrehuecosLineal&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&cleanAll=I"><spring:message code="pda_p13_segPedidos.prehuecoEnlace"/></a>
										</c:otherwise>	
									</c:choose>		
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP12DatosReferencia.do?mostrarFFPP=N&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p13_segPedidos.datosReferenciaEnlace"/></a>
										</c:when>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP12DatosReferencia.do?mostrarFFPP=S&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p13_segPedidos.datosReferenciaEnlace"/></a>
										</c:when>
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP12DatosReferencia.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p13_segPedidos.datosReferenciaEnlace" /></a>
										</c:otherwise>	
									</c:choose>		
								</c:otherwise>
							</c:choose>
							<label id="pda_p13_lbl_separadorEnlaces1" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p13_segPedidos.separadorEnlaces" /></label>
							<label id="pda_p13_lbl_segPedidos" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p13_segPedidos.segPedidos" /></label>
							<label id="pda_p13_lbl_separadorEnlaces2" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p13_segPedidos.separadorEnlaces" /></label>
							<c:choose>
								<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
									<c:choose>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=N&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p13_segPedidos.movStocksEnlace" /></a>
										</c:when>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=S&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p13_segPedidos.movStocksEnlace" /></a>
										</c:when>	
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}&procede=pdaP115PrehuecosLineal&sufijoPrehueco=_PREHUECO"><spring:message code="pda_p13_segPedidos.movStocksEnlace" /></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'S'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=N&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p13_segPedidos.movStocksEnlace" /></a>
										</c:when>
										<c:when test="${pdaSegPedidos.mostrarFFPP eq 'N'}">
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?mostrarFFPP=S&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p13_segPedidos.movStocksEnlace" /></a>
										</c:when>	
										<c:otherwise>
											<a onclick="javascript:bloquearEnlaces()" href="./pdaP15MovStocks.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&greenImpr=${greenImpr}"><spring:message code="pda_p13_segPedidos.movStocksEnlace" /></a>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>	
						</div>
					</div>
					<div id="pda_p13_segPedidos_bloqueReferencia">
						<c:choose>
							
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '25_PDA_ROTACION')) && !empty (pdaSegPedidos.tipoRotacion)}">
								<div id="pda_p13_bloque2_descConTipoRot">									
									<input id="pda_p13_fld_descripcionRef" name="descArtConCodigo" class="input180 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>										
								</div>
								<div id="pda_p13_bloque2_tipoRot">
									<c:choose> 
										<c:when test="${pdaSegPedidos.tipoRotacion=='AR'}">
											<input id="pda_p13_fld_tipoRotacion" class="pda_p13_tipoRotacionAR" disabled="disabled" type="text" value="${pdaSegPedidos.tipoRotacion}"/>
										</c:when>
										<c:when test="${pdaSegPedidos.tipoRotacion=='MR'}">
											<input id="pda_p13_fld_tipoRotacion" class="pda_p13_tipoRotacionMR" disabled="disabled" type="text" value="${pdaSegPedidos.tipoRotacion}"/>
										</c:when>	
										<c:when test="${pdaSegPedidos.tipoRotacion=='BR'}">
											<input id="pda_p13_fld_tipoRotacion" class="pda_p13_tipoRotacionBR" disabled="disabled" type="text" value="${pdaSegPedidos.tipoRotacion}"/>
										</c:when>	
										<c:otherwise>
											<input id="pda_p13_fld_tipoRotacion" class="pda_p13_tipoRotacionUndef"disabled="disabled" type="text" value="${pdaSegPedidos.tipoRotacion}"/>
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && !empty (pdaSegPedidos.esUSS) && pdaSegPedidos.esUSS eq 'S')}">
								<div id="pda_p13_bloque2_descConUSS">									
									<input id="pda_p13_fld_descripcionRef" name="descArtConCodigo" class="input180 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>										
								</div>
								<div id="pda_p13_bloque2_USS">									
									<input id="pda_p13_fld_USS" class="pda_p13_tipoRotacionUSS" disabled="disabled" type="text" value="<spring:message code="pda_p13_datosReferencia.uss"/>"/>										
								</div>
							</c:when>
							<c:otherwise> <!--  solo descripcion de la referencia, sin rotación. Lo que habia. -->							
								<input id="pda_p13_fld_descripcionRef" name="descArtConCodigo" class="input225 linkInput" type="text" value="${pdaDatosCab.descArtCabConCodigoGenerico}" readonly/>										
							</c:otherwise>
						</c:choose>			
								
					</div>
					<div id="pda_p13_segPedidos_bloque2">
					<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '24_PDA_CONTROL_STOCK'))}">
						<div id="pda_p13_tengoMuchoPoco">
							<c:choose>																																													
								<c:when test="${pdaSegPedidos.stockActual eq 'Error'}">
									<fieldset id=pda_p13_tengoMuchoPocoFieldsetErrorStock>
										<div id="pda_p13_tengoMuchoPocoErrorStockLabelDiv"><span id="pda_p13_tengoMuchoPocoErrorStockLabel"><spring:message code="pda_p13_segPedidos.errorStockActual" /></span></div>
									</fieldset>	
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaSegPedidos.valoresStock == null || (pdaSegPedidos.valoresStock.flgErrorWSVentasTienda!=null && pdaSegPedidos.valoresStock.flgErrorWSVentasTienda!=0)
													||!(pdaSegPedidos.valoresStock.stockBajo <= pdaSegPedidos.valoresStock.sobreStockInferior &&
																	pdaSegPedidos.valoresStock.sobreStockInferior <= pdaSegPedidos.valoresStock.sobreStockSuperior &&
																	pdaSegPedidos.valoresStock.stockBajo >= 0 &&
																	(pdaSegPedidos.valoresStock.stock > 0
													|| pdaSegPedidos.valoresStock.stockBajo > 0 || pdaSegPedidos.valoresStock.sobreStockInferior > 0 || pdaSegPedidos.valoresStock.sobreStockSuperior > 0))}">
											<c:if test="${pdaSegPedidos.stockActivo eq 'S' && origenGISAE ne 'SI'}">
												<c:choose>
													<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
														<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=PHStock&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
													</c:when>
													<c:otherwise>
														<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=SP&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
													</c:otherwise>
												</c:choose>
											</c:if>
											<fieldset id=pda_p13_tengoMuchoPocoFieldsetError>
												<div id="pda_p13_tengoMuchoPocoErrorLabelDiv"><span id="pda_p13_tengoMuchoPocoErrorLabel"><spring:message code="pda_p13_segPedidos.stockDias" /></span></div>
												<div id="pda_p13_tengoMuchoPocoErrorStockDiv">
													<c:choose>
														<c:when test="${pdaSegPedidos.stockActivo eq 'S'  && origenGISAE ne 'SI'}">
															<c:choose>
																<c:when test="${!empty (guardadoStockOk)}">
																	<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoStockConEnlaceOk"><spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind><spring:bind path="pdaSegPedidos.diasStock">${status.value}</spring:bind></span>																
																</c:when>
																<c:otherwise>
																	<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoStockConEnlace"><spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind><spring:bind path="pdaSegPedidos.diasStock">${status.value}</spring:bind></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoErrorStockSinEnlace"><spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind></span>
														</c:otherwise>
													</c:choose>
												</div>
											</fieldset>	
											<c:if test="${pdaSegPedidos.stockActivo eq 'S' && origenGISAE ne 'SI'}">
												</a>
											</c:if>																		
										</c:when>
										<c:otherwise>
											<c:choose>	
												<c:when test="${pdaSegPedidos.valoresStock.stock >= pdaSegPedidos.valoresStock.stockBajo && pdaSegPedidos.valoresStock.stock <= pdaSegPedidos.valoresStock.sobreStockInferior}">
												<c:if test="${pdaSegPedidos.stockActivo eq 'S' && origenGISAE ne 'SI'}">
												<c:choose>
													<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
														<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=PHStock&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
													</c:when>
													<c:otherwise>
														<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=SP&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
													</c:otherwise>
												</c:choose>
												</c:if>
													<fieldset id=pda_p13_tengoMuchoPocoFieldsetOk>
														<div id="pda_p13_tengoMuchoPocoOkLabelDiv"><span id="pda_p13_tengoMuchoPocoOkLabel"><spring:message code="pda_p13_segPedidos.stockOk" /></span></div>
														<div id="pda_p13_tengoMuchoPocoOkStockDiv">
															<c:choose>
																<c:when test="${pdaSegPedidos.stockActivo eq 'S' && origenGISAE ne 'SI'}">
																	<c:choose>
																		<c:when test="${!empty (guardadoStockOk)}">																	
																			<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoStockConEnlaceOk"><spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind><spring:bind path="pdaSegPedidos.diasStock">${status.value}</spring:bind></span>
																		</c:when>
																		<c:otherwise>
																			<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoStockConEnlace"><spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind><spring:bind path="pdaSegPedidos.diasStock">${status.value}</spring:bind></span>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoErrorStockSinEnlace"><spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind></span>
																</c:otherwise>
															</c:choose>
														</div>
													</fieldset>
													<c:if test="${pdaSegPedidos.stockActivo eq 'S' && origenGISAE ne 'SI'}">
														</a>
													</c:if>
												</c:when>
												<c:otherwise>
													<fieldset id=pda_p13_tengoMuchoPocoFieldsetAltoBajo>
														<c:choose>
															<c:when test="${pdaSegPedidos.valoresStock.stock > pdaSegPedidos.valoresStock.sobreStockInferior}">
																<c:choose>
																	<c:when test="${pdaSegPedidos.valoresStock.mostrarMotivosStock != null &&  pdaSegPedidos.valoresStock.mostrarMotivosStock == 'S'}">
																		<div id="pda_p13_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p13_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p13_segPedidos.stock" /></span>
																			<c:choose>
																				<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
																					<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaSegPedidos.codArt}&procede=${procede}&muchoPoco=M&stockAlto=${pdaSegPedidos.valoresStock.sobreStockInferior}&stockBajo=${pdaSegPedidos.valoresStock.stockBajo}&stock=${pdaSegPedidos.valoresStock.stock}">
																						<span class="pda_p13_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p13_segPedidos.stockAlto"/></span>
																					</a>
																				</c:when>
																				<c:otherwise>
																					<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaSegPedidos.codArt}&procede=segPedidos&muchoPoco=M&stockAlto=${pdaSegPedidos.valoresStock.sobreStockInferior}&stockBajo=${pdaSegPedidos.valoresStock.stockBajo}&stock=${pdaSegPedidos.valoresStock.stock}">
																						<span class="pda_p13_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p13_segPedidos.stockAlto"/></span>
																					</a>
																				</c:otherwise>
																			</c:choose>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div id="pda_p13_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p13_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p13_segPedidos.stock" />&nbsp;<spring:message code="pda_p13_segPedidos.stockAlto" /></span></div>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${pdaSegPedidos.valoresStock.mostrarMotivosStock != null &&  pdaSegPedidos.valoresStock.mostrarMotivosStock == 'S'}">
																		<div id="pda_p13_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p13_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p13_segPedidos.stock" /></span>
																			<c:choose>
																				<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
																					<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaSegPedidos.codArt}&procede=${procede}&muchoPoco=P&stockAlto=${pdaSegPedidos.valoresStock.sobreStockInferior}&stockBajo=${pdaSegPedidos.valoresStock.stockBajo}&stock=${pdaSegPedidos.valoresStock.stock}">
																						<span class="pda_p13_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p13_segPedidos.stockBajo"/></span>
																					</a>
																				</c:when>
																				<c:otherwise>
																					<a href="./pdaP16MotivosTengoMPPopup.do?codArt=${pdaSegPedidos.codArt}&procede=segPedidos&muchoPoco=P&stockAlto=${pdaSegPedidos.valoresStock.sobreStockInferior}&stockBajo=${pdaSegPedidos.valoresStock.stockBajo}&stock=${pdaSegPedidos.valoresStock.stock}">
																						<span class="pda_p13_tengoMuchoPocoAltoBajoConEnlace"><spring:message code="pda_p13_segPedidos.stockBajo"/></span>
																					</a>
																				</c:otherwise>
																			</c:choose>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div id="pda_p13_tengoMuchoPocoAltoBajoLabelDiv"><span id="pda_p13_tengoMuchoPocoAltoBajoLabel"><spring:message code="pda_p13_segPedidos.stock" />&nbsp;<spring:message code="pda_p13_segPedidos.stockBajo" /></span></div>
																	</c:otherwise>
																</c:choose>																		
															</c:otherwise>
														</c:choose>
														<div id="pda_p13_tengoMuchoPocoAltoBajoStockDiv">
															<label id="pda_p13_lbl_stockActual">
																<c:choose>
																	<c:when test="${pdaSegPedidos.stockActivo eq 'S'  && origenGISAE ne 'SI'}">
																		<c:choose>
																			<c:when test="${!empty (guardadoStockOk)}">
																				<c:choose>
																					<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
																						<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=PHStock&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
																					</c:when>
																					<c:otherwise>
																						<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=SP&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
																					</c:otherwise>
																				</c:choose>
																					<c:choose>
																						<c:when test="${pdaSegPedidos.valoresStock.stock > pdaSegPedidos.valoresStock.sobreStockInferior}">
																							<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoAltoBajoConEnlaceOkUp">
																						</c:when>
																						<c:otherwise>
																							<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoAltoBajoConEnlaceOkDown">
																						</c:otherwise>
																					</c:choose>	
																					<spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind><spring:bind path="pdaSegPedidos.diasStock">${status.value}</spring:bind></span>
																				</a>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
																						<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=PHStock&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
																					</c:when>
																					<c:otherwise>
																						<a href="./pdaP28CorreccionStockInicio.do?codArt=${pdaSegPedidos.codArt}&origen=SP&mmc=${pdaSegPedidos.MMC}&calculoCC=${pdaSegPedidos.calculoCC}">
																					</c:otherwise>
																				</c:choose>																					
																				<c:choose>
																					<c:when test="${pdaSegPedidos.valoresStock.stock > pdaSegPedidos.valoresStock.sobreStockInferior}">
																						<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoAltoBajoStockConEnlaceUp">
																					</c:when>
																					<c:otherwise>
																						<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoAltoBajoStockConEnlaceDown">
																					</c:otherwise>
																				</c:choose>
																				<spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind><spring:bind path="pdaSegPedidos.diasStock">${status.value}</spring:bind></span>
																				</a>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${pdaSegPedidos.valoresStock.stock > pdaSegPedidos.valoresStock.sobreStockInferior}">
																				<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoAltoBajoStockSinEnlaceUp">
																			</c:when>
																			<c:otherwise>
																				<span id="pda_p13_lbl_stockActualVal" class="pda_p13_tengoMuchoPocoAltoBajoStockSinEnlaceDown">
																			</c:otherwise>
																		</c:choose>	
																		<spring:bind path="pdaSegPedidos.stockActual">${status.value}</spring:bind><spring:bind path="pdaSegPedidos.diasStock">${status.value}</spring:bind></span>
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
						<div id="pda_p13_referenciasCentroPedir">
							<c:choose>
								<c:when test="${pdaSegPedidos.flgDepositoBrita eq 'S'}">
									<fieldset id="pda_p13_pedidoAutomaticoFieldsetDepositoBrita">
										<p class="pda_p13_parrafoPedidoAutomaticoDepositoBrita"><spring:message code="pda_p13_segPedidos.mensajeEnDepositoBrita" /></p>
									</fieldset>
								</c:when>
								<c:when test="${pdaSegPedidos.flgPorCatalogo eq 'S'}">
									<fieldset id="pda_p13_pedidoAutomaticoFieldsetPorCatalogo">
										<p class="pda_p13_parrafoPedidoAutomaticoPorCatalogo"><spring:message code="pda_p13_segPedidos.mensajePorCatalogo" /></p>
									</fieldset>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaSegPedidos.pedidoActivo eq 'S'}">
											<c:choose>
												<c:when test="${user.centro.esCentroCaprabo || user.centro.esCentroCapraboEspecial}">
													<c:choose>
														<c:when test="${not empty pdaSegPedidos.tMisMcgCaprabo.fechaActivacion && pdaSegPedidos.mostrarFechaGen}">
															<a href="./pdaP19fechaActivacionPopup.do?origen=SP&mostrarFFPP=${pdaSegPedidos.mostrarFFPP}&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&fechaActivacion=${pdaSegPedidos.strFechaGen}">
																<fieldset id="pda_p13_pedidoAutomaticoFieldsetActivo">
																	<p class="pda_p13_parrafoPedidoAutomaticoActivoLink"><spring:message code="pda_p13_segPedidos.mensajePedidoAutomaticoActivo" /></p>
																</fieldset>
															</a>
														</c:when>
														<c:otherwise>
															<fieldset id="pda_p13_pedidoAutomaticoFieldsetActivo">
																<p class="pda_p13_parrafoPedidoAutomaticoActivo"><spring:message code="pda_p13_segPedidos.mensajePedidoAutomaticoActivo" /></p>
															</fieldset>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${not empty pdaSegPedidos.strFechaGen && pdaSegPedidos.mostrarFechaGen}">
															<a href="./pdaP19fechaActivacionPopup.do?origen=SP&mostrarFFPP=${pdaSegPedidos.mostrarFFPP}&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&origenGISAE=${origenGISAE}&fechaActivacion=${pdaSegPedidos.strFechaGen}">
																<fieldset id="pda_p13_pedidoAutomaticoFieldsetActivo">
																	<p class="pda_p13_parrafoPedidoAutomaticoActivoLink"><spring:message code="pda_p13_segPedidos.mensajePedidoAutomaticoActivo" /></p>
																</fieldset>
															</a>
														</c:when>
														<c:otherwise>
															<fieldset id="pda_p13_pedidoAutomaticoFieldsetActivo">
																<p class="pda_p13_parrafoPedidoAutomaticoActivo"><spring:message code="pda_p13_segPedidos.mensajePedidoAutomaticoActivo" /></p>
															</fieldset>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:when>
										
										
										<c:otherwise>
											<c:choose>
												<c:when test="${user.centro.esCentroCaprabo || user.centro.esCentroCapraboEspecial}">													
													<c:choose>
														<c:when test="${(user.centro.esCentroCapraboNuevo)}">
															
															
															<!--  
															<c:choose>
																<c:when test="${pdaSegPedidos.pedir eq 'N'}">
																	<c:choose>
																		<c:when test="${not empty pdaSegPedidos.motivoCaprabo}">	
																			<a href="./pdaP14refCentroPopup.do?procede=segPedidos&codArt=${pdaDatosCab.codArtCab}">
																				<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
																					<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																				</fieldset>
																			</a>
																		</c:when>
																		<c:otherwise>
																			<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
										   										<p class="pda_p13_parrafoPedidoAutomaticoNoActivo"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																			</fieldset>
																		</c:otherwise>
																	</c:choose>
																</c:when>
															</c:choose>	
															-->	
															<c:choose>
																<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
																	<a href="./pdaP14refCentroPopup.do?procede=${procede}&codArt=${pdaDatosCab.codArtCab}">
																		<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
																			<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																		</fieldset>
																	</a>
																</c:when>
																<c:otherwise>
																	<a href="./pdaP14refCentroPopup.do?procede=segPedidos&codArt=${pdaDatosCab.codArtCab}">
																		<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
																			<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																		</fieldset>
																	</a>
																</c:otherwise>
															</c:choose>
														</c:when>	
														<c:otherwise>
															<c:choose>
																<c:when test="${pdaSegPedidos.pedir eq 'N'}">
																	<c:choose>
																		<c:when test="${not empty pdaSegPedidos.motivoCaprabo}">	
																			<a href="./pdaP16MotivosTengoMPPopupCaprabo.do?codArt=${pdaDatosCab.codArtCab}&procede=segPedidos&codArtRel=${pdaSegPedidos.tMisMcgCaprabo.sustituidaPor}">
																				<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
																					<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																				</fieldset>
																			</a>
																		</c:when>
																		<c:otherwise>
																			<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
										   										<p class="pda_p13_parrafoPedidoAutomaticoNoActivo"><spring:message code="pda_p12_caprabo_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																			</fieldset>
																		</c:otherwise>
																	</c:choose>
																</c:when>
															</c:choose>	
														</c:otherwise>
													</c:choose>
												</c:when>	
												<c:otherwise>
													<c:choose>
														<c:when test="${pdaSegPedidos.tratamientoVegalsa}">
															<c:choose>
																<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
																	<c:choose>
																		<c:when test="${pdaSegPedidos.MMC eq 'S'}">
																			<a href="./pdaP14refCentroOkPopup.do?procede=${procede}&origen=${origen}&codArt=${pdaDatosCab.codArtCab}">
																				<fieldset id="pda_p13_pedidoAutomaticoFieldsetActivoMMC">
																					<p class="pda_p13_parrafoPedidoAutomaticoActivoLinkMMC"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivoVegalsa" /></p>
																				</fieldset>
																			</a>
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14refCentroPopup.do?procede=${procede}&origen=${origen}&codArt=${pdaDatosCab.codArtCab}">
																				<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivoMMC">
																					<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLinkMMC"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoNoActivoVegalsa" /></p>
																				</fieldset>
																			</a>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${pdaSegPedidos.MMC eq 'S'}">
																			<a href="./pdaP14refCentroOkPopup.do?procede=segPedidos&codArt=${pdaDatosCab.codArtCab}">
																				<fieldset id="pda_p13_pedidoAutomaticoFieldsetActivoMMC">
																					<p class="pda_p13_parrafoPedidoAutomaticoActivoLinkMMC"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoActivoVegalsa" /></p>
																				</fieldset>
																			</a>
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14refCentroPopup.do?procede=segPedidos&codArt=${pdaDatosCab.codArtCab}">
																				<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivoMMC">
																					<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLinkMMC"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoNoActivoVegalsa" /></p>
																				</fieldset>
																			</a>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
																	<a href="./pdaP14refCentroPopup.do?procede=${procede}&codArt=${pdaDatosCab.codArtCab}">
																		<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
																			<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																		</fieldset>
																	</a>
																</c:when>
																<c:otherwise>
																	<a href="./pdaP14refCentroPopup.do?procede=segPedidos&codArt=${pdaDatosCab.codArtCab}">
																		<fieldset id="pda_p13_pedidoAutomaticoFieldsetNoActivo">
																			<p class="pda_p13_parrafoPedidoAutomaticoNoActivoLink"><spring:message code="pda_p12_datosReferencia.mensajePedidoAutomaticoNoActivo" /></p>
																		</fieldset>
																	</a>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>		
												</c:otherwise>																																												
											</c:choose>				
										</c:otherwise>
									
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
						<div id= "pda_p13_FFPP_div">
							<c:choose>
								<c:when test="${pdaSegPedidos.mostrarFFPP eq 'S'}">
									<div id ="pda_p13_FFPP">
										<c:choose>
											<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
												<a href="./pdaP13FFPPActivo.do?mostrarFFPP=S&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&procede=pdaP115PrehuecosLineal"><spring:message code="pda_p13_segPedidos.FFPP" /></a>
											</c:when>
											<c:otherwise>
												<a href="./pdaP13FFPPActivo.do?mostrarFFPP=S&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}"><spring:message code="pda_p13_segPedidos.FFPP" /></a>
											</c:otherwise>
										</c:choose>
									</div>
								</c:when>
								<c:when test="${pdaSegPedidos.mostrarFFPP eq 'N'}">
									<div id ="pda_p13_FFPP">
										<c:choose>
											<c:when test="${procede eq 'pdaP115PrehuecosLineal'}">
												<a href="./pdaP13FFPPActivo.do?mostrarFFPP=N&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}&procede=pdaP115PrehuecosLineal"><spring:message code="pda_p13_segPedidos.retornoFFPP" /></a>
											</c:when>
											<c:otherwise>
												<a href="./pdaP13FFPPActivo.do?mostrarFFPP=N&codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.codArtRel}"><spring:message code="pda_p13_segPedidos.retornoFFPP" /></a>
											</c:otherwise>
										</c:choose>
									</div>
								</c:when>
								<c:otherwise>
									<div id ="pda_p13_FFPP_vacio">
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${user.centro.esCentroCaprabo || user.centro.esCentroCapraboEspecial}">
									<c:choose>
										<c:when test="${pdaSegPedidos.mostrarSustARef eq 'S'}">
											<div id ="pda_p13_SustARef">
												<a href="./pdaP13FFPPActivo.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.tMisMcgCaprabo.sustitutaDe}"><spring:message code="pda_p12_caprabo_datosReferencia.SustARef" /></a>
											</div>
										</c:when>
										<c:when test="${pdaSegPedidos.mostrarSustPorRef eq 'S'}">
											<div id ="pda_p13_SustPorRef">
												<a href="./pdaP13FFPPActivo.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.tMisMcgCaprabo.sustituidaPor}"><spring:message code="pda_p12_caprabo_datosReferencia.SustPorRef" /></a>
											</div>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${pdaSegPedidos.mostrarSustARef eq 'S'}">
											<div id ="pda_p13_SustARef">
												<a href="./pdaP13FFPPActivo.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.sustitutaDe}"><spring:message code="pda_p12_datosReferencia.SustARef" /></a>
											</div>
										</c:when>
										<c:when test="${pdaSegPedidos.mostrarSustPorRef eq 'S'}">
											<div id ="pda_p13_SustPorRef">
												<a href="./pdaP13FFPPActivo.do?codArt=${pdaSegPedidos.codArt}&codArtRel=${pdaSegPedidos.sustituidaPor}"><spring:message code="pda_p12_datosReferencia.SustPorRef" /></a>
											</div>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<!-- <div id="pda_p13_segPedidos_bloque3">
						<label id="pda_p13_lbl_pdteRecibir" class="etiquetaCampoNegrita"><spring:message code="pda_p13_segPedidos.pdteRecibir" /></label>
						<label id="pda_p13_lbl_pdteRecibirVal" class="valorCampo">${pdaSegPedidos.cantHoy}/${pdaSegPedidos.cantFutura}</label>
					</div>  -->
					<div id="pda_p13_segPedidos_bloque4">
						<div id="pda_p13_ultEnviosTabla" class="tablaJqgrid">
							<div id="pda_p13_ultEnviosTit" class="tablaTitulo">
								<span class="pda_p13_ultEnvios_Title"><spring:message code="pda_p13_segPedidos.ultEnvios" /></span>
							</div>
							<table id="pda_p13_ultEnviosTablaEstructura" border="1">
								<thead class="tablaCabecera">
								    <tr>
								        <th class="pda_p13_ultEnviosTablaTh"><spring:message code="pda_p13_segPedidos.fecPrevisEnt" /></th>
								        <th class="pda_p13_ultEnviosTablaTh"><spring:message code="pda_p13_segPedidos.envio" /></th>
								        <th class="pda_p13_ultEnviosTablaTh"><spring:message code="pda_p13_segPedidos.nsr" /></th>
								    </tr>
								</thead>
								<tbody class="tablaContent">
								    <c:forEach var="pedido" items="${pdaSegPedidos.listaEnvios}" varStatus="loopStatus">
										<tr class="${loopStatus.index % 2 == 0 ? 'tablaTr_impar' : 'tablaTr_par'}">							
									    	<td id="pda_p13_ultEnviosTdFecPrevisEnt" class="pda_p13_ultEnviosTablaTd">
									    		<c:out value="${pedido.fechaEnvio}"/>
									    	</td>
									       	<td id="pda_p13_ultEnviosTdEnvio" class="pda_p13_ultEnviosTablaTd">
									        	<c:choose>
									        		<c:when test="${pedido.artPed == 1}">
									        			<img src="./misumi/images/dialog-accept-24.gif?version=${misumiVersion}" class="imagenTd">
									        		</c:when>
									        		<c:when test="${pedido.artPed == 2}">
									        			<img src="./misumi/images/dialog-help-24_2.gif?version=${misumiVersion}" class="imagenTd">
									        		</c:when>
									        	</c:choose>
									        	<c:out value="${pedido.tipoPedido}"/>
									        </td>
									        <td id="pda_p13_ultEnviosTdNSR" class="pda_p13_ultEnviosTablaTd">
									        	<c:choose>
									        		<c:when test="${pedido.artNsr gt 0}">
									        			<img src="./misumi/images/dialog-cancel-24.gif?version=${misumiVersion}" class="imagenTd">
									        		</c:when>
									        	</c:choose>
									        </td>
									    </tr>
								
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:otherwise>
			</c:choose>			
		</div>		
		<input id="pda_p13_tieneFoto" type="hidden" value="${pdaSegPedidos.tieneFoto}"/>
		<input id="pda_p13_codArt" type="hidden" value="${pdaSegPedidos.codArt}"/>
		<input id="pda_p13_procede" type="hidden" value="${procede}"/>
		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>