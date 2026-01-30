<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p63_realizarDevolucionOrdenDeRetirada.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p63RealizarDevolucionOrdenDeRetirada.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP63RealizarDevolucionOrdenDeRetirada.do" name="actionRef"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" id="pdaP63RealizarDevolucionOrdenDeRetirada" action="pdaP63RealizarDevolucionOrdenDeRetirada.do" commandName="devolucionLinea">
				<c:set var="conTextil" value="${devolucionLinea.area eq 3}" />
								 
				<div id="pda_p63_tipoDeDevolucion_tituloComun">
					<div id="pda_p63_tipoDevolucion_tituloLink">							
						<a href="#" id="pda_p63_titulo">${devolucionCabecera.titulo1}</a>								
					</div>
					<div id="pda_p63_tipoDevolucion_numeroReferencias">
						<div id="numeroReferenciasInfo">
							<label><spring:message code="pda_p63_tipoDeDevolucion.referenciasCompletadas" /></label>
						</div>
						<div id="numeroReferenciasDatos">
							<div id="numeroReferenciaDatosCompletadas">
								<a href="#" id="pda_p63_lineasCompletadas">${completados} / </a>
							</div>
							<div id="numeroReferenciaDatosTotal">
								<label>${pagDevolucionLineas.total}</label>
							</div>
						</div>
					</div>
				</div>
				 <div class="pda_p63_tipoDeDevolucion_contenidoDistinto_bloque ${conTextil ? 'pda_p63_con_textil': ''}">
					<div class="ordenDeRetiradaDatos">
						<input id="pda_p63_fld_descripcionRef" name="descArtConCodigo" class="input225" type="text" disabled="disabled" value="${devolucionLinea.codArticulo} - ${devolucionLinea.denominacion}"/>
					</div>
					<c:if test="${conTextil}">
						<%-- Datos de textil --%>
						<div class="ordenDeRetiradaDatos_compacto">
							<div class="bloqueSesenta3 pda_p63_limitado modeloP63">
								<label class="valorCampo" ><c:out value="${devolucionLinea.modelo}" /></label>
							</div>
							<div class="bloqueCuarenta tallaYColorP63">
								<div class="bloqueMitad pda_p63_limitado">
									<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.textil.talla"/></label>
									<c:choose>
										<c:when test="${linkTextil.talla}">
											<a id="pda_p63_talla" class="valorCampo" href="#">
												<c:out value="${devolucionLinea.descrTalla}" />
											</a>
										</c:when>
										<c:otherwise>
											<label id="pda_p62_talla" class="valorCampo"><c:out value="${devolucionLinea.descrTalla}" /></label>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="bloqueMitad pda_p63_limitado">
									<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.textil.color"/></label>
									<c:choose>
										<c:when test="${linkTextil.color}">
											<a id="pda_p63_color" class="valorCampo" href="#">
												<c:out value="${devolucionLinea.descrColor}" />
											</a>
										</c:when>
										<c:otherwise>
											<label id="pda_p63_color" class="valorCampo"><c:out value="${devolucionLinea.descrColor}" /></label>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="ordenDeRetiradaDatos_compacto">
							<div class="bloqueSesenta3">
							</div>
							<div class="bloqueCuarenta modeloProveedorP63 pda_p63_limitado">
								<label class="pda_p63_lbl_Info" ><spring:message code="pda_p63_ordenDeRetirada.textil.modeloProveedor"/></label>
								<c:choose>
									<c:when test="${linkTextil.mp}">
										<a id="pda_p63_modelo_proveedor" class="valorCampo" href="#">
											<c:out value="${devolucionLinea.modeloProveedor}" />
										</a>
									</c:when>
									<c:otherwise>
										<label id="pda_p63_modelo_proveedor" class="valorCampo" >
											<c:out value="${devolucionLinea.modeloProveedor}" />
										</label>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:if>
					<div class="ordenDeRetiradaDatos">						
						<div class="bloqueMitad">
							<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.marca"/></label>
							<label class="valorCampo" >${devolucionLinea.marca}</label>
						</div>
						<div class="bloqueMitad">
							<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.formato"/></label>
							<label id="pda_p63_valorCampo_formato" class="valorCampo" >${devolucionLinea.formato}&nbsp;${devolucionLinea.tipoFormato}&nbsp;${devolucionLinea.tipoReferencia}</label>
						</div>
					</div>
					<div class="ordenDeRetiradaDatos">						
						<div class="bloqueEntero">
							<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.lote"/></label>
							<c:choose>
								<c:when test="${linkLote eq true && linkCaducidad eq true}">
									<a id="pdaP63CaducidadLink" href ="#"><spring:message code="pda_p63_ordenDeRetirada.linkLoteCaducidad"/></a>
								</c:when>
								<c:when test="${linkLote eq true}">
									<a id="pdaP63CaducidadLink" href ="#"><spring:message code="pda_p63_ordenDeRetirada.linkLoteCaducidad"/></a>
								</c:when>
								<c:otherwise>										
									<label class="valorCampo" >${devolucionLinea.nLote}</label>
								</c:otherwise>
							</c:choose>									
						</div>							
					</div>
					<div class="ordenDeRetiradaDatos">						
						<div class="bloqueEntero">
							<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.caducidad"/></label>
							<c:choose>
								<c:when test="${linkLote eq true && linkCaducidad eq true}">
									<a id="pdaP63LoteLink" href ="#"><spring:message code="pda_p63_ordenDeRetirada.linkLoteCaducidad"/></a>
								</c:when>
								<c:when test="${linkCaducidad eq true}">
									<a id="pdaP63LoteLink" href ="#"><spring:message code="pda_p63_ordenDeRetirada.linkLoteCaducidad"/></a>
								</c:when>
								<c:otherwise>
									<label class="valorCampo" >${devolucionLinea.nCaducidad}</label>										
								</c:otherwise>
							</c:choose>	
						</div>	
					</div>
					<div class="ordenDeRetiradaDatos">						
						<div class="bloqueStock">
							<div class="bloqueCuarentaStock">	
								<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.stk"/></label>
							</div>
							<div class="bloqueSesentaStock">
								<a id="pda_p63_valorCampo_stock" class="valorCampoStk" href="#">${devolucionLinea.stockActual}</a>
							</div>
						</div>
						<div class="bloqueStockDevuelto">	
							<div class="bloqueCuarentaStockDevuelto">
								<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.cant"/></label>
							</div>

							<c:choose>
								<c:when test="${(devolucionLinea.flgBandejas eq 'S') && (devolucionLinea.flgPesoVariable eq 'S')}">
									<div class="bloqueSesentaLabel">
										<a id="pda_p63_fld_stockDevuelto" class="valorCampo" href="#">
											${devolucionLinea.stockDevuelto != null  ? devolucionLinea.stockDevuelto:0}
										</a>
									</div>	
								</c:when>
								<c:otherwise>

									<c:choose>
										<c:when test="${devolucionCabecera.fechaDeDevolucionPasada eq true}">
											<div class="bloqueSesentaLabel">
												<label id="pda_p63_fld_stockDevuelto_label" class="valorCampo"></label>
												<input id="pda_p63_fld_stockDevuelto" name="pda_p63_fld_stockDevuelto" class="inputFinCampania" type="hidden" value=""/>
											</div>	
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${devolucionLinea.variosBultos eq true}">
													<div class="bloqueCincuentaStockDevuelto">
														<input id="pda_p63_fld_stockDevuelto" name="pda_p63_fld_stockDevuelto" class="inputFinCampaniaReadOnly" value="" readonly="readonly"/>
													</div>
												</c:when>
												<c:otherwise>
													<div class="bloqueCincuentaStockDevuelto">
														<input id="pda_p63_fld_stockDevuelto" name="pda_p63_fld_stockDevuelto" class="inputFinCampania" type="number" step="0.001" value=""/>
													</div>
												</c:otherwise>
											</c:choose>
												
										</c:otherwise>
									</c:choose>									

								</c:otherwise>
							</c:choose>																				

 							<input type="hidden" id="flgBandejas" value="${devolucionLinea.flgBandejas}"/>
<!-- Inicio MISUMI-195 -->
							<input type="hidden" id="flgPesoVariable" value="${devolucionLinea.flgPesoVariable}"/>
<!-- FIN MISUMI-195 -->
						</div>
						<div class="bloqueBulto">
							 <div class="bloqueTreintaCinco">
								<label class="pda_p63_lbl_Info"><spring:message code="pda_p63_ordenDeRetirada.bulto"/></label>
							</div>
							<c:choose>
								<c:when test="${devolucionCabecera.fechaDeDevolucionPasada eq true}">
									<div class="bloqueSesentaLabel">
										<label id="bulto_label" class="valorCampo">${devolucionLinea.bulto}</label>
										<form:input path="bulto"  id="pda_p63_fld_bulto" type="hidden"/>
									</div>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${devolucionLinea.variosBultos eq true}">
											<div class="bloqueSesentaBulto">
												<input id="pda_p63_fld_bulto" name="pda_p63_fld_bulto" class="inputFinCampaniaReadOnly" value="${devolucionLinea.bultoStr}" readonly="readonly" onclick="javascript:events_p63_variosBultos_bulto();"/>
											</div>	
										</c:when>
										<c:otherwise>
											<div class="bloqueSesentaBulto">
												<form:input path="bulto" id="pda_p63_fld_bulto" class="inputFinCampania" type="number"/>
											</div>			
											
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '27_PDA_DEVOLUCIONES_BULTOS'))}">
											<c:choose>
												<c:when test="${!((devolucionLinea.flgBandejas eq 'S') && (devolucionLinea.flgPesoVariable eq 'S'))}">
													<div class="bloqueDiez">
														<label id="pda_p63_varios_bultos_label" class="variosBultos">+</label>
													</div>	
												</c:when>
											</c:choose>
										</c:when>
									</c:choose>
								</c:otherwise>
							</c:choose>	
						</div>
					</div>
				</div>
				
				<input type="hidden" id="stockDevuelto_orig" name="stockDevuelto_orig" value="${devolucionLinea.stockDevueltoOrig}">
				<input type="hidden" id="stockDevuelto" name="stockDevuelto" value="${devolucionLinea.stockDevuelto}">
					
				<input type="hidden" id="stockDevueltoBandejas" name="stockDevueltoBandejas" value="${devolucionLinea.stockDevueltoBandejas}">
				<input type="hidden" id="stockDevueltoBandejas_orig"  name="stockDevueltoBandejas_orig" value="${devolucionLinea.stockDevueltoBandejasOrig}">
				<input type="hidden" id="pda_p63_variosBultos" name="variosBultos" value="${devolucionLinea.variosBultos}">
				<input type="hidden" id="pda_p63_estadoCerrado" name="estadoCerrado" value="${devolucionLinea.estadoCerrado}">
<!-- Inicio MISUMI-295 -->
				<div id="pda_p63_areaDatosExtra">
					<label id="pda_p63_lbl_error" class="pda_p63_error" style="display:none;"><spring:message code="pda_p63_pesoVariable.error"/></label>					
				</div>
<!-- FIN MISUMI-295 -->
				<div id="pda_p63_areaDatosExtra2">
					<label id="pda_p63_lbl_error2" class="pda_p63_error" style="display:none;"><spring:message code="pda_p63_variosBultos.bultoErroneo"/></label>
				</div>
				<c:choose>
					<c:when test="${pagDevolucionLineas.total>0}">
						<div id="pda_p63_tipoDeDevolucion_pieComun">
							<div id="bloqueFlechas">
								<div id="pagPrimera" class="paginacion">
									<c:choose>
										<c:when test="${pagDevolucionLineas.page eq '1'}">
											<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
										</c:when>
										<c:otherwise>
											<img id="pda_p63_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</c:otherwise>
									</c:choose>
								</div>
								<div id="pagAnt" class="paginacion">
									<c:choose>
										<c:when test="${pagDevolucionLineas.page eq '1'}">
											<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
										</c:when>
										<c:otherwise>
											<img id="pda_p63_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
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
											<img id="pda_p63_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</c:otherwise>
									</c:choose>						
								</div>
								<div id="pagUltima" class="paginacion">
									<c:choose>
										<c:when test="${pagDevolucionLineas.page eq pagDevolucionLineas.total}">
											<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
										</c:when>
										<c:otherwise>
											<img id="pda_p63_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</c:otherwise>
									</c:choose>						
								</div>
							</div>
							<div id="bloqueBotonCerrarBulto">
								<div id="pda_p63_btn_cerrarBulto" onclick="javascript:events_p63_cerrarBulto();">
									<div class="botonCerrarBulto"></div>
								</div>					
							</div>
							<c:choose>
								<c:when test="${devolucionCabecera.fechaDeDevolucionPasada eq false}">
									<div id="bloqueBoton">
										<div id="pda_p63_btn_fin">
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
			<input type="hidden" id="devolucion" name="devolucion" value="${devolucionLinea.devolucion}">
			<input type="hidden" id="referenciaFiltroRellenaYCantidadEnter" name="referenciaFiltroRellenaYCantidadEnter" value="${referenciaFiltroRellenaYCantidadEnter}">
			<input type="hidden" id="proveedorAnterior" name="proveedorAnterior" value="${proveedorAnterior}">				
			<input type="hidden" id="codArticulo" name="codArticulo" value="${devolucionLinea.codArticulo}">
			<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">				
			<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
			<input type="hidden" id="listaBultos" name="listaBultos" value="${listaBultos}">
			<input type="hidden" id="centroParametrizado" name="centroParametrizado" value="${centroParametrizado}">
			<input type="hidden" id="pda_p63_stockModificado" name="listaBultos" value="${stockModificado}">
			<input type="hidden" id="pda_p63_devolucionFinalizada" name="devolucionFinalizada" value="${devolucionFinalizada}">
			<c:if test="${linkCaducidad eq true}">
				<input type="hidden" id="nCaducidad" name="nCaducidad" value="${devolucionLinea.nCaducidad}">
			</c:if>
			<c:if test="${linkLote eq true}">
				<input type="hidden" id="nLote" name="nLote" value="${devolucionLinea.nLote}">
			</c:if>
			
			<c:forEach items="${devolucionLinea.bultoPorProveedorLst}" var="bultoPorProveedor" varStatus="status">	
				<c:set var="dateParts" value="${fn:split(bultoPorProveedor,'*')}" />
				<input id="${dateParts[0]}" value="${bultoPorProveedor}" name="bultoPorProveedorLst[${status.index}]" type="hidden"/>			
			</c:forEach>
			
			<input type="hidden" id="estructuraComercial" name="estructuraComercial" value="${devolucionLinea.estructuraComercial}">		
		</form:form>
		
		<!-- Si se guarda correctamente el stock tendrá un flag en S  -->
		<input type="hidden" id="flgBienGuardado" name="flgBienGuardado" value='${flgBienGuardado}'>
		
		<!-- Para saber el proveedor de la linea de devolucion -->
		<input type="hidden" id="proveedorDevolucionLinea" name="proveedorDevolucionLinea" value="${devolucionLinea.provrGen}">	
		
		<!-- Para saber si el bulto se ha cambiado al pulsar enter y en ese caso guardar el bulto por proveedor -->
		<input type="hidden" id="bulto_orig" value="${devolucionLinea.bulto}">
		
		<!-- Hidden que sirve para situar el cursor en el campo cantidad si se ha pulsado anteriormente la accion siguientePagina, anteriorPagina, primeraPagina, ultimaPagina -->
		<input type="hidden" id="accionAnterior" value="${accionAnterior}">
		
		<!-- Guarda el valor del MMC del link del stock -->
		<input type="hidden" id="mmc" value="${MMC}">
	</div>
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>