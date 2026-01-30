<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param name="cssFile" value="pda_p62_realizarDevolucionFinDeCampania.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda_p62RealizarDevolucionFinDeCampania.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP62RealizarDevolucionFinCampania.do"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
<fmt:setLocale value="es_ES"/>
<c:set var="conCosteMaximo" value="${0 lt devolucionCabecera.costeMaximo}" />

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" id="pdaP62RealizarDevolucionFinCampania" action="pdaP62RealizarDevolucionFinCampania.do" modelAttribute="devolucionConLineas">
				<div id="pda_p62_tipoDeDevolucion_tituloComun" class="${conCosteMaximo ? 'pda_p62_con_euros' : ''} ${esTextil ? 'pda_p62_con_textil' : ''}">
					<div id="pda_p62_tipoDevolucion_tituloLink">
						<a href="#" id="pda_p62_titulo">${devolucionCabecera.titulo1}</a>
					</div>
					<div id="pda_p62_tipoDevolucion_numeroReferencias">
						<div id="numeroReferenciasInfo">
							<label><spring:message code="pda_p62_tipoDeDevolucion.referenciasCompletadas" /></label>
						</div>
						<div id="numeroReferenciasDatos">
							<div id="numeroReferenciaDatosCompletadas">
								<a href="#" id ="pda_p62_lineasCompletadas">${completados} / </a>
							</div>
							<div id="numeroReferenciaDatosTotal">
								<label>${pagDevolucionLineas.records}</label>
							</div>
						</div>
					</div>
					<c:if test="${conCosteMaximo}">
						<div id="pda_p62_tipoDevolucion_euros_contenedor" class="${sumaEuros gt devolucionCabecera.costeMaximo ? 'pda_p62_red' : ''}">
							<div id="pda_p62_eurosInfo">
								<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_tipoDeDevolucion.euros" /></label>
							</div>
							<div id="pda_p62_eurosDatos">
								<span id="pda_p62_euros_val" class="valorCampo">
									<fmt:formatNumber maxFractionDigits="2" value="${sumaEuros}" />
								</span>
							</div>
							<div id="pda_p62_eurosMaxInfo">
								<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_tipoDeDevolucion.eurosMax" /></label>
							</div>
							<div id="pda_p62_eurosMaxDatos">
								<label id="pda_p62_euros_max_val" class="valorCampo">
									<fmt:formatNumber maxFractionDigits="2" value="${devolucionCabecera.costeMaximo}" />
								</label>
							</div>
						</div>
					</c:if>
				</div>
				<div id="pda_p62_tipoDeDevolucion_contenidoDistinto">
					<c:forEach items="${devolucionConLineas.tDevLineasLst}" var="devolucionLinea" varStatus="status">	
						<c:set var="conTextil" value="${devolucionLinea.area eq 3}" />
						
						<div class="pda_p62_tipoDeDevolucion_contenidoDistinto_bloque ${conTextil ? 'pda_p62_con_textil': ''}">
							<div class="finDeCampaniaDatos">
								<input id="pda_p62_fld_descripcionRef" name="descArtConCodigo" class="input225" type="text" disabled="disabled" value="${devolucionLinea.codArticulo} - ${devolucionLinea.denominacion}"/>
							</div>
							<c:choose>
								<%-- Datos de textil --%>
								<c:when test="${conTextil}">
									<div class="finDeCampaniaDatos">
										<div class="bloqueSesenta3 pda_p62_limitado modeloP62">
											<label class="valorCampo" ><c:out value="${devolucionLinea.modelo}" /></label>
										</div>
										<div class="bloqueCuarenta tallaYColorP62">
											<div class="bloqueMitad pda_p62_limitado">
												<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.textil.talla"/></label>
												<c:choose>
													<c:when test="${linkTextil.talla}">
														<a id="pda_p62_talla" class="valorCampo" href="#">
															<c:out value="${devolucionLinea.descrTalla}" />
														</a>
													</c:when>
													<c:otherwise>
														<label id="pda_p62_talla" class="valorCampo"><c:out value="${devolucionLinea.descrTalla}" /></label>
													</c:otherwise>
												</c:choose>
											</div>
											<div class="bloqueMitad pda_p62_limitado">
												<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.textil.color"/></label>
												<c:choose>
													<c:when test="${linkTextil.color}">
														<a id="pda_p62_color" class="valorCampo" href="#">
															<c:out value="${devolucionLinea.descrColor}" />
														</a>
													</c:when>
													<c:otherwise>
														<label id="pda_p62_color" class="valorCampo"><c:out value="${devolucionLinea.descrColor}" /></label>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
									<div class="finDeCampaniaDatos">
										<div class="bloqueSesenta3 proveedorP62 pda_p62_limitado">
											<div class="bloqueDiez">								
												<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.p"/></label>
											</div>
											<div class="bloqueNoventa pda_p62_limitado">
												<label class="valorCampo" >${devolucionLinea.denomProveedor}</label>								
											</div>
										</div>
										<div class="bloqueCuarenta modeloProveedorP62 pda_p62_limitado">
											<label class="pda_p62_lbl_Info" ><spring:message code="pda_p62_finDeCampania.textil.modeloProveedor"/></label>
											<c:choose>
												<c:when test="${linkTextil.mp}">
													<a id="pda_p62_modelo_proveedor" class="valorCampo" href="#">
														<c:out value="${devolucionLinea.modeloProveedor}" />
													</a>
												</c:when>
												<c:otherwise>
													<label id="pda_p62_modelo_proveedor" class="valorCampo" >
														<c:out value="${devolucionLinea.modeloProveedor}" />
													</label>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="finDeCampaniaDatos">
										<div class="bloqueEntero">
											<div class="bloqueDiez">								
												<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.p"/></label>
											</div>
											<div class="bloqueNoventa">
												<label class="valorCampo" >${devolucionLinea.denomProveedor}</label>								
											</div>
										</div>										
									</div>
								</c:otherwise>
							</c:choose>
							<div class="finDeCampaniaDatos">
								 
								<div class="bloqueTercio2">
									 <div class="bloqueTreinta">
										<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.stk"/></label>
									</div>
									<div class="bloqueSesenta2">
										<a id="pda_p62_valorCampo_stock${status.index}" class="valorCampo" href="#">${devolucionLinea.stockActual}</a>
									</div>
								</div>
								<div class="bloqueTercio2">
									 <div class="bloqueTreinta">
										<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.ftoDev"/></label>
									</div>
									<div class="bloqueSesenta2">
										<label class="valorCampo" >${devolucionLinea.formatoDevuelto}</label>
									</div>
								</div>
								
								<div class="bloqueTercio3">
									 <div class="bloqueTreintaFormato">
										<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.formato"/></label>
									</div>
									<div class="bloqueSesentaFormato">
										<label id="pda_p62_valorCampo_formato${status.index}" class="valorCampo" >${devolucionLinea.formato}&nbsp;${devolucionLinea.tipoFormato}&nbsp;${devolucionLinea.tipoReferencia}</label> 
									</div>
								</div>
							</div>
							<div class="finDeCampaniaDatos">
								<div class="bloqueStock">
									<div class="bloqueLabelStock">	
										<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.dev"/></label>
									</div>
									<div class="bloqueDatoStock">
										<label id="pda_p62_valorCampo_dej${status.index}" class="valorCampo" >${devolucionLinea.stockTienda}</label>
									</div>
								</div>
								<div class="bloqueStockDevuelto">	
									<div class="bloqueLabelStockDevuelto">
										<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.cant"/></label>
									</div>
									<c:choose>
										<c:when test="${(devolucionLinea.flgBandejas eq 'S') && (devolucionLinea.flgPesoVariable eq 'S')}">
											<div class="bloqueStockDevueltoLabel">
												<a id="pda_p62_fld_stockDevuelto${status.index}" class="valorCampo" href="#">
													${devolucionLinea.stockDevuelto != null ? devolucionLinea.stockDevuelto:0}
												</a>
											</div>	
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${devolucionCabecera.fechaDeDevolucionPasada eq true}">
													<div class="bloqueStockDevueltoLabel">
														<label id="pda_p62_fld_stockDevuelto${status.index}_label" class="valorCampo"></label>
														<input id="pda_p62_fld_stockDevuelto${status.index}" name="pda_p62_fld_stockDevuelto${status.index}" class="inputFinCampania" type="hidden" value="${devolucionLinea.stockDevuelto}"/>
													</div>	
												</c:when>
												<c:otherwise>
														<c:choose>
															<c:when test="${devolucionLinea.cantidadMaximaLin eq 0}">
																<div class="bloqueStockDevueltoLabel">
																	<label id="pda_p62_fld_stockDevuelto${status.index}_label" class="valorCampo"></label>
																	<input id="pda_p62_fld_stockDevuelto${status.index}" name="pda_p62_fld_stockDevuelto${status.index}" class="inputFinCampania" type="hidden" value="${devolucionLinea.stockDevuelto}"/>
																</div>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${devolucionLinea.variosBultos eq true}">
																		<div class="bloqueDatoStockDevuelto">
																			<input id="pda_p62_fld_stockDevuelto${status.index}" name="pda_p62_fld_stockDevuelto${status.index}" class="inputFinCampaniaReadOnly" value="" readonly="readonly"/>
																		</div>
																	</c:when>
																	<c:otherwise>
																		<div class="bloqueDatoStockDevuelto">
																			<input id="pda_p62_fld_stockDevuelto${status.index}" name="pda_p62_fld_stockDevuelto${status.index}" class="inputFinCampania" type="number" step="0.001" value=""/>
																		</div>
																	</c:otherwise>
																</c:choose>
																	
															</c:otherwise>	
														</c:choose>													
												</c:otherwise>
											</c:choose>										
										</c:otherwise>
									</c:choose>																				
									<input type="hidden" id="flgBandejas" value="${devolucionLinea.flgBandejas}"/>
									<input type="hidden" id="flgPesoVariable" value="${devolucionLinea.flgPesoVariable}"/>
								</div>
								<div class="bloqueBulto">
									<div class="bloqueLabelBulto">
										<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.bulto"/></label>
									</div>
									<c:choose>
										<c:when test="${devolucionCabecera.fechaDeDevolucionPasada eq true}">
											<div class="bloqueBultoLabel">												
												<label id="tDevLineasLst[${status.index}].bulto_label" class="valorCampo">${devolucionLinea.bulto}</label>
												<form:input path="tDevLineasLst[${status.index}].bulto" class="inputFinCampania inputSinBorde" type="hidden"/>
											</div>	
										</c:when>
										<c:otherwise>
												<c:choose>
													<c:when test="${devolucionLinea.cantidadMaximaLin eq 0}">
														<div class="bloqueBultoLabel">	
															<label id="tDevLineasLst[${status.index}].bulto_label" class="valorCampo">${devolucionLinea.bulto}</label>
															<form:input path="tDevLineasLst[${status.index}].bulto" class="inputFinCampania inputSinBorde" type="hidden"/>
														</div>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${devolucionLinea.variosBultos eq true}">
																<div class="bloqueDatoBulto">
																	<input id="pda_p62_fld_stockDevuelto_bultoStr" name="tDevLineasLst[${status.index}].bultoStr" class="inputFinCampaniaReadOnly" value="${devolucionLinea.bultoStr}" onclick="javascript:navegarVariosBultos()" readonly="readonly"/>
																</div>	
															</c:when>
															<c:otherwise>
																<div class="bloqueDatoBulto">
																	<form:input path="tDevLineasLst[${status.index}].bulto"  class="inputFinCampania" type="number" step="0.001" value=""/>
																</div>
																
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '27_PDA_DEVOLUCIONES_BULTOS'))}">
																<c:choose>
																	<c:when test="${!((devolucionLinea.flgBandejas eq 'S') && (devolucionLinea.flgPesoVariable eq 'S'))}">
																		<div class="bloqueDiez">
																			<label id="pda_p62_varios_bultos_label" class="variosBultos">+</label>
																		</div>	
																	</c:when>
																</c:choose>
															</c:when>
														</c:choose>
													</c:otherwise>
												</c:choose>
										</c:otherwise>
									</c:choose>	
								</div>
							</div>
					</div>
					<form:input type="hidden" path="tDevLineasLst[${status.index}].stockDevuelto"/>
					<form:input type="hidden" path="tDevLineasLst[${status.index}].stockDevueltoBandejas"/>
					<input type="hidden" id="stockDevuelto_orig${status.index}" value="${devolucionLinea.stockDevuelto}">
					<input type="hidden" id="stockDevuelto${status.index}" name="stockDevuelto${status.index}" value="${devolucionLinea.stockDevuelto}">
					<!-- Para saber el proveedor de la linea de devolucion -->
					<input type="hidden" id="proveedorDevolucionLinea${status.index}" name="proveedorDevolucionLinea${status.index}" value="${devolucionLinea.provrGen}">					
					<input type="hidden" id="codArticulo${status.index}" name="codArticulo${status.index}" value="${devolucionLinea.codArticulo}">	
					<input type="hidden" id="codArticulo" name="codArticulo" value="${devolucionLinea.codArticulo}">	
					<input type="hidden" id="estructuraComercial${status.index}" name="estructuraComercial${status.index}" value="${devolucionLinea.estructuraComercial}">	

					<!-- Para saber si el bulto se ha cambiado al pulsar enter y en ese caso guardar el bulto por proveedor -->
					<input type="hidden" id="bulto_orig${status.index}" value="${devolucionLinea.bulto}">
					
					<input type="hidden" id="stockDevueltoBandejas${status.index}" name="stockDevueltoBandejas${status.index}" value="${devolucionLinea.stockDevueltoBandejas}">
					<input type="hidden" id="stockDevueltoBandejas_orig${status.index}"  name="stockDevueltoBandejas_orig${status.index}" value="${devolucionLinea.stockDevueltoBandejasOrig}">
					<input type="hidden" id="pda_p62_variosBultos" name="tDevLineasLst[${status.index}].variosBultos" value="${devolucionLinea.variosBultos}">
					<input type="hidden" id="pda_p62_estadoCerrado" name="tDevLineasLst[${status.index}].estadoCerrado" value="${devolucionLinea.estadoCerrado}">
				</c:forEach>
				<div id="pda_p62_areaInferior" >
					<div id="pda_p62_areaDatosExtra2">
						<label id="pda_p62_lbl_error" class="pda_p62_error" style="display:none;"><spring:message code="pda_p62_finDeCampania.error"/></label>					
						<label id="pda_p62_lbl_error2" class="pda_p62_error" style="display:none;"><spring:message code="pda_p62_pesoVariable.error"/></label>
						<label id="pda_p62_lbl_error3" class="pda_p62_error" style="display:none;"><spring:message code="pda_p62_variosBultos.bultoErroneo"/></label>
					</div>
					<div id="pda_p62_areaDatosExtra">
						<c:if test="${conCosteMaximo and pagDevolucionLineas.total>0}">
							<label class="pda_p62_lbl_Info" ><spring:message code="pda_p62_finDeCampania.costo"/></label>
							<label id="pda_p62_coste_unitario${status.index}" class="valorCampo" >
								<fmt:formatNumber maxFractionDigits="2" value="${devolucionConLineas.tDevLineasLst[0].costeUnitario}" />
							</label>
						</c:if>
						<c:if test="${devolucionConLineas.tDevLineasLst[0].cantidadMaximaLin != null}">
							<label class="pda_p62_lbl_Info"><spring:message code="pda_p62_finDeCampania.cantmax"/></label>
							<label id="pda_p62_valorCampo_cantMax" class="valorCampo" >${fn:replace(devolucionConLineas.tDevLineasLst[0].cantidadMaximaLin,'.',',')}</label>																
						</c:if>
					</div>
					<c:if test="${tieneFoto eq true}">
						<c:choose>
							<c:when test="${(conCosteMaximo and pagDevolucionLineas.total>0) || (devolucionConLineas.tDevLineasLst[0].cantidadMaximaLin != null)}">
								<div id="pda_p62_areaImagenMitad">
									<img id="pda_p62_img_referencia" src="./pdaGetImageP62.do?codArticulo=${devolucionConLineas.tDevLineasLst[0].codArticulo}"/>
								</div>
							</c:when>
							<c:otherwise>
								<div id="pda_p62_areaImagen">
									<img id="pda_p62_img_referencia" src="./pdaGetImageP62.do?codArticulo=${devolucionConLineas.tDevLineasLst[0].codArticulo}"/>
								</div>
							</c:otherwise>
						</c:choose>						
					</c:if>
				</div>
			</div>
			<c:choose>
				<c:when test="${pagDevolucionLineas.total>0}">
					<div id="pda_p62_tipoDeDevolucion_pieComun">
						<div id="bloqueFlechas">
							<div id="pagPrimera" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucionLineas.page eq '1'}">
										<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<img id="pda_p62_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
									</c:otherwise>
								</c:choose>
							</div>
							<div id="pagAnt" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucionLineas.page eq '1'}">
										<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<img id="pda_p62_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="numeroPagina" class="paginacion">
								${pagDevolucionLineas.page}/${pagDevolucionLineas.total}
							</div>
							<div id="pagSig" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucionLineas.page eq pagDevolucionLineas.total}">
										<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<img id="pda_p62_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="pagUltima" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucionLineas.page eq pagDevolucionLineas.total}">
										<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<img id="pda_p62_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
									</c:otherwise>
								</c:choose>						
							</div>
						</div>
						<div id="bloqueBotonCerrarBulto">
							<div id="pda_p62_btn_cerrarBulto" onclick="javascript:events_p62_cerrarBulto();">
								<div class="botonCerrarBulto"></div>
							</div>					
						</div>
						<c:choose>
							<c:when test="${devolucionCabecera.fechaDeDevolucionPasada eq false}">
								<div id="bloqueBoton">
									<div id="pda_p62_btn_fin">
										<div class="botonSubmitGrabar"></div>
									</div>					
								</div>
							</c:when>
						</c:choose>	
					</div>
				</c:when>
			</c:choose>
			<input type="hidden" id="referenciaFiltro" name="referenciaFiltro" value="${referenciaFiltro}">
			<input type="hidden" id="proveedorFiltro" name="proveedorFiltro" value="${proveedorFiltro}">
			<input type="hidden" id="accion" name="accion" value="">
			<input type="hidden" id="paginaActual" name="paginaActual" value="${pagDevolucionLineas.page}">
			<input type="hidden" id="devolucion" name="devolucion" value="${devolucionCabecera.devolucion}">
			<input type="hidden" id="primerElementoBultoFila" name="primerElementoBultoFila" value="${primerElementoBultoFila}">
			<input type="hidden" id="referenciaFiltroRellenaYCantidadEnter" name="referenciaFiltroRellenaYCantidadEnter" value="${referenciaFiltroRellenaYCantidadEnter}">
			<input type="hidden" id="proveedorAnterior" name="proveedorAnterior" value="${proveedorAnterior}">				
			<input type="hidden" id="codArticuloFoto" name="codArticuloFoto" value="${devolucionConLineas.tDevLineasLst[0].codArticulo}">				
			<input type="hidden" id="fila" name="fila" value="${fila}">				
			<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">
			<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
			<input type="hidden" id="listaBultos" name="listaBultos" value="${listaBultos}">
			<input type="hidden" id="centroParametrizado" name="centroParametrizado" value="${centroParametrizado}">
			<input type="hidden" id="pda_p62_stockModificado" name="listaBultos" value="${stockModificado}">
			<input type="hidden" id="pda_p62_devolucionFinalizada" name="devolucionFinalizada" value="${devolucionFinalizada}">
			<c:forEach items="${devolucionConLineas.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
				<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
				<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
			</c:forEach>							
		</form:form>
		
		<!-- Si se guarda correctamente el stock tendrá un flag en S  -->
		<input type="hidden" id="flgBienGuardado" name="flgBienGuardado" value='${flgBienGuardado}'>
		
		<!-- Hidden que sirve para situar el cursor en el campo cantidad si se ha pulsado anteriormente la accion siguientePagina, anteriorPagina, primeraPagina, ultimaPagina -->
		<input type="hidden" id="accionAnterior" value="${accionAnterior}">
		
		<!-- Guarda el valor del MMC del link del stock -->
		<input type="hidden" id="mmc" value="${MMC}">
		
	</div>

		<script>
			document.addEventListener("DOMContentLoaded", function() {
      			let enlaces = document.querySelectorAll(".paginacion");

//console.log('LLEGO 0');
      			
      			enlaces.forEach(function(enlace) {
	          		enlace.addEventListener("click", function() {
//console.log('LLEGO 1');
	  					document.getElementById("bloqueFlechas").style.pointerEvents = "none";
//console.log('Desactivando botones de paginación');
	          		});
	      		});

      			document.addEventListener('keydown', function(event) {
      			    if (event.key === 'Enter') {
      			        event.preventDefault();
      			        enlaces.forEach(enlace => {
      			            if (document.activeElement === enlace) {
//console.log('Enter presionado en un botón de paginación');
      		  					document.getElementById("bloqueFlechas").style.pointerEvents = "none";
//console.log('Desactivando botones de paginación');
      			            }
      			        });
      			    }
      			});
      			
    		});
		</script>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>