<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p03_headerReducido.jsp" >
    <jsp:param value="pda_p31_correccionStockPCN.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p31CorrStockPCN.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
	<jsp:param value="pdaP31CorrStockPCN.do" name="actionRef"></jsp:param>
</jsp:include>
<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" id="p31_form" action="pdaP31CorrStockPCN.do" commandName="pdaStock">	
				<c:choose>
					<c:when test="${not empty pdaStock.listaArticulosPagina}">
						<c:choose>
							 <c:when test="${pdaStock.tipoMensaje eq 'CB'}">  <!--  Cuando las referencias son de textil -->
							
									<div id="pda_p31_bloqueDatos">
										<div id="pda_p31_correccionStock_pcn_titulo">
											<label id="pda_p31_lbl_titulo" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.titulo" /></label>
										</div>
										<div id="pda_p31_descripcionRefTextil">
											<label id="pda_p31_lbl_descripcionRefTextil" class="etiquetaCampoNegrita">${pdaStock.listaArticulosPagina[0].descArt}</label>
										</div>
										<div id="pda_p31_infoRefTextil">
											<label id="pda_p31_lbl_descripcionRefTextil" class="etiquetaCampoNegrita">${pdaStock.listaArticulosPagina[0].temporada}</label>
											<label id="pda_p31_lbl_descripcionRefTextil" class="etiquetaCampoNegrita">${pdaStock.listaArticulosPagina[0].estructura}</label>
											<label id="pda_p31_lbl_descripcionRefTextil" class="etiquetaCampoNegrita">${pdaStock.listaArticulosPagina[0].numOrden}</label>
											<label id="pda_p31_lbl_descripcionRefTextil" class="etiquetaCampoNegrita">${pdaStock.listaArticulosPagina[0].modeloProveedor}</label>
										</div>
										<c:choose>
											<c:when test="${pdaStock.totalArticulos > 3}">
												<div id="pda_p31_bloque1">
													<div id="pda_p31_bloque1_pagAnt">
														<c:choose>
															<c:when test="${pdaStock.posicionGrupoArticulos > 1}">
																<input id="pda_p31_btn_pagAnt" type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
															</c:when>
															<c:otherwise>
																<input id="pda_p31_btn_pagAnt" type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>
															</c:otherwise>
														</c:choose>
													</div>	
													<div id="pda_p31_bloque1_pagNum">
														<p class="pda_p31_numPaginas"><spring:message code="pda_p31_correccionStockPCN.paginacion" arguments="${pdaStock.posicionGrupoArticulos},${pdaStock.totalPaginas}" /></p>
													</div>
													<div id="pda_p31_bloque1_pagSig">
														<c:choose>
															<c:when test="${pdaStock.totalPaginas > pdaStock.posicionGrupoArticulos}">
																<input id="pda_p31_btn_pagSig" type="submit" name="actionSig" value='' class='botonSubmitSig'/>
															</c:when>
															<c:otherwise>
																<input id="pda_p31_btn_pagSig" type="submit" name="actionSig" value='' class='botonSubmitSigDes' disabled="disabled"/>
															</c:otherwise>
														</c:choose>
													</div>					
												</div>
											</c:when>
										</c:choose>
										<div id="pda_p31_bloqueError">
											<label id="pda_p31_mensajeError" class="pda_p31_error">${pdaStock.descripcionError}</label>
											<label id="pda_p31_mensajeAviso" class="pda_p31_aviso" style="display:none"><spring:message code="pda_p31_correccionStockPCN.excedido.limite" /></label>
										</div>
										<div class="pda_p31_bloqueRegistro">
											<div id="pda_p31_reg_referCab" class="pda_p31_reg_referCab">
												<label id="pda_p31_lbl_referCab" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.refer" /></label>
											</div>
											<div id="pda_p31_reg_tallaColorCab" class="pda_p31_reg_tallaColorCab">
												<label id="pda_p31_lbl_tallaCab" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.talla" /></label>
											<!--</div>
											<div id="pda_p31_reg_colorCab" class="pda_p31_reg_colorCab"> -->
												/<label id="pda_p31_lbl_colorCab" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.color" /></label>
											</div>
											<div id="pda_p31_reg_stockCab" class="pda_p31_reg_stockCab">
												<label id="pda_p31_lbl_stockCab" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.stockTextil" /></label>
											</div>
										</div>	
										<c:forEach var="articulo" items="${pdaStock.listaArticulosPagina}" varStatus="loopStatus">
											<div class="pda_p31_bloqueRegistroTextil">
												<div class="pda_p31_reg_refer">
<%-- 													<input name="listaArticulosPagina[${loopStatus.index}].refer" id="pda_p31_lbl_refer${loopStatus.index}" class="input55 inputSinBorde" value="${articulo.codArt}" readonly/> --%>
														<label>${articulo.codArt}</label>
												</div>
												<div class="pda_p31_reg_tallaColor">
<%-- 													<input name="listaArticulosPagina[${loopStatus.index}].talla" id="pda_p31_lbl_talla${loopStatus.index}" class="input120 inputSinBorde" value="${articulo.descrTalla}" readonly/> --%>
														<label>${articulo.descrTalla} /</label>
											<!-- 	</div>
												<div class="pda_p31_reg_color">  -->
<%-- 													<input name="listaArticulosPagina[${loopStatus.index}].color" id="pda_p31_lbl_color${loopStatus.index}" class="input120 inputSinBorde" value="${articulo.descrColor}" readonly/> --%>
														<label>${articulo.descrColor}</label>
												</div>
												<div class="pda_p31_reg_stockTextil">
													<c:choose>
														<c:when test="${'WN' != pdaStock.codigoError}">
															<input type="number" step="0.001" name="listaArticulosPagina[${loopStatus.index}].stock" id="pda_p31_fld_stock_${loopStatus.index}" class="input35 pda_p31_fld_stock" size ="3" value="${articulo.stock}"/>
														</c:when>
														<c:otherwise>
															<input name="listaArticulosPagina[${loopStatus.index}].stock" id="pda_p31_fld_stock_${loopStatus.index}" class="input35 pda_p31_fld_stock" size ="3" value="${articulo.stock}" disabled="disabled"/>
														</c:otherwise>
													</c:choose>
													<input id="pda_p31_fld_stock_${loopStatus.index}_tmp" type="hidden" value="${articulo.stock}"/>
												</div>
											</div>
										</c:forEach>
									</div>
							</c:when>	
							<c:otherwise> <!--  Cuando las referencias NO son de textil -->
						
									<div id="pda_p31_bloqueDatos">
										<div id="pda_p31_correccionStock_pcn_titulo">
											<label id="pda_p31_lbl_titulo" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.titulo" /></label>
										</div>
										<c:choose>
											<c:when test="${pdaStock.totalArticulos > 4}">
												<div id="pda_p31_bloque1">
													<div id="pda_p31_bloque1_pagAnt">
														<c:choose>
															<c:when test="${pdaStock.posicionGrupoArticulos > 1}">
																<input id="pda_p31_btn_pagAnt" type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
															</c:when>
															<c:otherwise>
																<input id="pda_p31_btn_pagAnt" type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>
															</c:otherwise>
														</c:choose>
													</div>	
													<div id="pda_p31_bloque1_pagNum">
														<p class="pda_p31_numPaginas"><spring:message code="pda_p31_correccionStockPCN.paginacion" arguments="${pdaStock.posicionGrupoArticulos},${pdaStock.totalPaginas}" /></p>
													</div>
													<div id="pda_p31_bloque1_pagSig">
														<c:choose>
															<c:when test="${pdaStock.totalPaginas > pdaStock.posicionGrupoArticulos}">
																<input id="pda_p31_btn_pagSig" type="submit" name="actionSig" value='' class='botonSubmitSig'/>
															</c:when>
															<c:otherwise>
																<input id="pda_p31_btn_pagSig" type="submit" name="actionSig" value='' class='botonSubmitSigDes' disabled="disabled"/>
															</c:otherwise>
														</c:choose>
													</div>					
												</div>
											</c:when>
										</c:choose>
										<div id="pda_p31_bloqueError">
											<label id="pda_p31_mensajeError" class="pda_p31_error">${pdaStock.descripcionError}</label>
											<label id="pda_p31_mensajeAviso" class="pda_p31_aviso" style="display:none"><spring:message code="pda_p31_correccionStockPCN.excedido.limite" /></label>
										</div>
										<div class="pda_p31_bloqueRegistro">
											<div id="pda_p31_reg_tipoCab" class="pda_p31_reg_tipoCab">
												<label id="pda_p31_lbl_tipoCab" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.tipo" /></label>
											</div>
											<div id="pda_p31_reg_descripcionCab" class="pda_p31_reg_descripcionCab">
												<label id="pda_p31_lbl_descripcionCab" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.descripcion" /></label>
											</div>
											<div id="pda_p31_reg_stockCab" class="pda_p31_reg_stockCab">
												<label id="pda_p31_lbl_stockCab" class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.stock" /></label>
											</div>
										</div>	
										<c:forEach var="articulo" items="${pdaStock.listaArticulosPagina}" varStatus="loopStatus">
											<div class="pda_p31_bloqueRegistro">
												<div class="pda_p31_reg_tipo">
													<label id="pda_p31_lbl_tipoVal${loopStatus.index}" class="valorCampo" >${articulo.tipo}</label>
												</div>
												<div class="pda_p31_reg_descripcion">
													<input name="listaArticulosPagina[${loopStatus.index}].descArt" id="pda_p31_lbl_descripcion${loopStatus.index}" class="input135 inputSinBorde" value="${articulo.descripcionCompleta}" readonly/>
												</div>
												<div class="pda_p31_reg_stock">
													<c:choose>
														<c:when test="${'WN' != pdaStock.codigoError}">
															<input type="number" step="0.001" name="listaArticulosPagina[${loopStatus.index}].stock" id="pda_p31_fld_stock_${loopStatus.index}" class="input50 pda_p31_fld_stock" value="${articulo.stock}"/>
														</c:when>
														<c:otherwise>
															<input name="listaArticulosPagina[${loopStatus.index}].stock" id="pda_p31_fld_stock_${loopStatus.index}" class="input50 pda_p31_fld_stock" value="${articulo.stock}" disabled="disabled"/>
														</c:otherwise>
													</c:choose>
													<input id="pda_p31_fld_stock_${loopStatus.index}_tmp" type="hidden" value="${articulo.stock}"/>
												</div>
											</div>
										</c:forEach>
									</div>
									<c:if test="${pdaStock.listaArticulosPagina[0].stockPlataforma != null}">
										<div id="pda_31_bloque_stockPlataforma">
											<label class="etiquetaCampoNegrita"><spring:message code="pda_p31_correccionStockPCN.stockPlataforma" /></label>
											<label class="valorCampo">${fn:replace(pdaStock.listaArticulosPagina[0].stockPlataforma,'.',',')}</label>
										</div>
									</c:if>									
							</c:otherwise>
			
						</c:choose>
					
						<div id="pda_p31_filterButtons">
							<c:choose>
								<c:when test="${'WN' != pdaStock.codigoError}">
									<input type="" id="pda_p31_btn_save"  class="botonSubmitGrabar operacionDefecto" value=''/>
								</c:when>
							</c:choose>
			
						</div>	
					</c:when>
					<c:otherwise>
						<div id="pda_p31_error_bloque1" class="mens_error"><spring:message code="p31_motivosTengoMuchoPoco.noData"/></div>
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="posicion" value="${pdaStock.posicionGrupoArticulos}">
				<input type="hidden" name="tipoMensaje" id="pda_p31_fld_tipoMensaje" value="${pdaStock.tipoMensaje}">
				<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">				
				<input type="hidden" id="selectProv" name="selectProv" value="${selectProv}">
				<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
			</form:form>
			<input type="hidden" id="pda_p31_esCaprabo" value = "${user.centro.esCentroCaprabo || user.centro.esCentroCapraboEspecial}">
			<input type="hidden" id="pda_p31_msgExcedido" value="<spring:message code="pda_p31_msg.excedido"/>">
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>