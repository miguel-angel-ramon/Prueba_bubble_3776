<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p13ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/HistoricoVentaUltimoMes.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Motivo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/MotivoTengoMuchoPoco.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PlanogramaVigente.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.jqplot.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/excanvas.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jqplot.meterGaugeRenderer.min.js?version=${misumiVersion}"></script>




<!--[if IE]> <input id="p13_esExplorer" type="hidden" value="ie"/><![endif]-->
<!--[if !(IE)]><!--><input id="p13_esExplorer" type="hidden" value="no_ie"/><!--<![endif]-->
	
		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p13_referenciasCentro.welcome" /></a></li>
						<li><spring:message code="p13_referenciasCentro.referenciasCentro" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="p13_AreaFiltro">
				<div id="p13_filtroReferencia">
					<div class="textBox">
						<label id="p13_lbl_referencia" class="etiquetaCampo"><spring:message code="p13_referenciasCentro.reference" /></label>
						<input type=text id="p13_fld_referencia" class="input100 controlReturn" value="${reference}"></input>
						<input type=hidden id="p13_fld_referenciaEroski" value="${referenceEroski}"></input>
						<input type=hidden id="p13_fld_refPagina" value="${pagConsulta}"></input>	
					</div>
					<div id="p13_descripcionRef" class="textBox" style=" display: none;">
							<label id="p13_lbl_descripcionRef" class="etiquetaCampo"><spring:message code="p13_referenciasCentro.descripcionRef" /></label>
							<input type=text id="p13_fld_descripcionRef" class="input360"></input>
						</div>
				</div>			
				<div id="p13_filterButtons">
					
					<input type="button" id="p13_btn_buscar" class="boton  botonHover" value='<spring:message code="p13_referenciasCentro.find" />'></input>
					<input type="button" id="p13_btn_reset" class="boton  botonHover" value='<spring:message code="p13_referenciasCentro.reset" />'></input>
					
				</div>
			</div>
			
			<div id="AreaMaestrosFijo" style="display:none;">
													 								
				<div id="p13_maestroFijoEstructura">

					<div class="p13_maestroFijoEstructuraField" >

						<div id="p13_referenciasCentroPedir">
							<div id="p13_referenciasCentroStock">
									<div id="p13_referenciasCentroStockMensajeError" style="display: none;">
										<span id="p13_referenciasCentroStockMensajeErrorTexto"><spring:message code="p13_referenciasCentro.errorWSVentasTienda" /></span>
									</div>
									<fieldset id="p13_stockActualFieldsetNoDefinido">
										<legend id="p13_stockActualLegendNoDefinido"><spring:message code="p13_referenciasCentro.stockNoDefinido" /></legend>
											<div id="p13_divStockActualNoDefinidoCorreccion" class="p13_divCorreccionStockActualNoDefinido" style="display: none;"></div>
											<div class="p13_divStockActualNoDefinido">
												<p class="p13_parrafoStockActualNoDefinido"></p><br>
												<p class="p13_parrafoStockActualNoDefinidoDias"></p>
											</div>
									</fieldset>	
									<fieldset id="p13_stockActualFieldsetOk">
										<legend id="p13_stockActualLegendOk"><spring:message code="p13_referenciasCentro.stockOK" /></legend>
											<div id="p13_divStockActualOkCorreccion" class="p13_divCorreccionStockActualOk" style="display: none;"></div>
											<div class="p13_divStockActualOk">
												<p class="p13_parrafoStockActualOk"></p><br>
												<p class="p13_parrafoStockActualOkDias"></p>
											</div>
									</fieldset>	
									<fieldset id="p13_stockActualFieldsetErroneo">
										<legend id="p13_stockActualLegendErroneo"></legend>
										<div id="p13_divStockActualErroneoCorreccion" class="p13_divCorreccionStockActualErroneo" style="display: none;"></div>
										<div class="p13_divStockActualErroneo">
											<div id="p13_stockUp" class="p13_imagenStock"><img src='./misumi/images/up.png?version=${misumiVersion}'/></div>
											<div id="p13_stockDown" class="p13_imagenStock"><img src='./misumi/images/down.png?version=${misumiVersion}'/></div>
											<div id="p13_cantidadStockErroneo"><span class="p13_parrafoStockActualErroneo"></span><span class="p13_parrafoStockActualErroneoDias"></span></div>
										</div>
										<div id="p13_divStockActualErroneoInfo" style="display: none;">
											<p class="p13_stockActualErroneoInfo"><spring:message code="p13_referenciasCentro.mensajeInfoStockActualErroneo" /></p>
										</div>
									</fieldset>	
									<div id="p13_stockChart" class="plot"></div>
							  </div>
							 <div id="p13_ChartDivider" class="p13_divider"></div> 
							<div id="p13_referenciasCentroPedir1">
								<fieldset id="p13_pedidoAutomaticoFieldsetDepositoBrita">
								<legend id="p13_pedidoAutomaticoLegendDepositoBrita"> <spring:message code="p13_referenciasCentro.legendAprovisionamiento" /> </legend>
									<div id="p13_divDepositoBritaCorreccion" class="p13_divCorreccionPedidoDepositoBrita" style="display: none;"></div>
									<div class="p13_divPedidoAutomaticoDepositoBrita">
										<p class="p13_parrafoPedidoAutomaticoDepositoBrita"><spring:message code="p13_referenciasCentro.mensajeAprovisionamientoReferenciaDepositoBrita" /></p>
									</div>
								</fieldset>	
								<fieldset id="p13_pedidoAutomaticoFieldsetPorCatalogo">
								<legend id="p13_pedidoAutomaticoLegendPorCatalogo"> <spring:message code="p13_referenciasCentro.legendAprovisionamiento" /> </legend>
									<div id="p13_divPorCatalogoCorreccion" class="p13_divCorreccionPedidoPorCatalogo" style="display: none;"></div>
									<div class="p13_divPedidoAutomaticoPorCatalogo">
										<p class="p13_parrafoPedidoAutomaticoPorCatalogo"><spring:message code="p13_referenciasCentro.mensajeAprovisionamientoReferenciaPorCatalogo" /></p>
									</div>
								</fieldset>	
								<fieldset id="p13_pedidoAutomaticoFieldsetActivo">
								<legend id="p13_pedidoAutomaticoLegendActivo"> <spring:message code="p13_referenciasCentro.legendPedidoAutomatico" /> </legend>
									<div id="p13_divActivoCorreccion" class="p13_divCorreccionPedidoActivo" style="display: none;"></div>
									<div class="p13_divPedidoAutomaticoActivo">
										<p class="p13_parrafoPedidoAutomaticoActivo"><spring:message code="p13_referenciasCentro.mensajePedidoAutomaticoActivo" /></p>
									</div>
								</fieldset>	
								<fieldset id="p13_pedidoAutomaticoFieldsetActivoVegalsa">
								<legend id="p13_pedidoAutomaticoLegendActivoVegalsa"> <spring:message code="p13_referenciasCentro.legendPedidoAutomaticoActivoVegalsa" /> </legend>
									<div id="p13_divActivoCorreccionVegalsa" class="p13_divCorreccionPedidoActivo" style="display: none;"></div>
									<div class="p13_divPedidoAutomaticoActivo">
										<input type=hidden id="p13_fld_TextoFechaBasico" value="<spring:message code='p13_referenciasCentro.mensajePedidoAutomaticoActivoVegalsa'/>"></input>
										<p id="p13_TextoFecha" class="p13_parrafoPedidoAutomaticoActivo"></p>
									</div>
								</fieldset>	
								<fieldset id="p13_pedidoAutomaticoFieldsetNoActivo">
								<legend id="p13_pedidoAutomaticoLegendNoActivo"> <spring:message code="p13_referenciasCentro.legendPedidoAutomatico" /> </legend>
									<div id="p13_divNoActivoCorreccion" class="p13_divCorreccion" style="display: none;"></div>
									<div class="p13_divPedidoAutomaticoNoActivo">
										<c:choose>
											<c:when test="${user.centro.esCentroCaprabo}"> 
												<p id="p13_parrafoPedidoAutomaticoNoActivo" class="p13_parrafoPedidoAutomaticoNoActivo" style="display: none;"><spring:message code="p13_caprabo_referenciasCentro.mensajePedidoAutomaticoNoActivo1" /></p>
												<p id="p13_parrafoPedidoAutomaticoNoActivoSinEnlace" class="p13_parrafoPedidoAutomaticoNoActivoSinEnlace" style="display: none;"><spring:message code="p13_caprabo_referenciasCentro.mensajePedidoAutomaticoNoActivo2" /></p>
											</c:when>
											<c:otherwise>							    	
												<p id="p13_parrafoPedidoAutomaticoNoActivo" class="p13_parrafoPedidoAutomaticoNoActivo"><spring:message code="p13_referenciasCentro.mensajePedidoAutomaticoNoActivo1" /></p>											
											</c:otherwise>
										</c:choose>									
									</div>
								</fieldset>
								<fieldset id="p13_pedidoAutomaticoFieldsetNoActivoVegalsa">
								<legend id="p13_pedidoAutomaticoLegendNoActivoVegalsa"> <spring:message code="p13_referenciasCentro.legendPedidoAutomaticoNoActivoVegalsa" /> </legend>
									<div id="p13_divNoActivoCorreccionVegalsa" class="p13_divCorreccion" style="display: none;"></div>
									<div class="p13_divPedidoAutomaticoNoActivo">							    	
										<p id="p13_parrafoPedidoAutomaticoNoActivoVegalsa" class="p13_parrafoPedidoAutomaticoNoActivo">
											<spring:message code="p13_referenciasCentro.mensajePedidoAutomaticoNoActivoVegalsa" />&nbsp;<span id="p13_TextoFechaNo" style="display: inline; color: #D8000C !important; font-size: inherit;"><spring:message code="p13_referenciasCentro.mensajePedidoAutomaticoNoActivoVegalsaFecha"/></span>
										</p>																				
									</div>
								</fieldset>		
							</div>
							 <div class="p13_divider"></div> 
							<div id="p13_referenciasCentroPedir2">	
								<fieldset id="p13_pedidosAdicionalesFieldset" style="display:none">
								<legend id="p13_pedidosAdicionalesLegend"> <spring:message code="p13_referenciasCentro.legendPedidosAdicionales" /> </legend>
									<div id="p13_divPedidoAdicionalCorreccion" class="p13_divCorreccion" style="display: none;"></div>
									<div class="p13_divPedidosAdicionales">
										<div id="p13_pedidosAdicionalesFieldsetEnlace" class="p13_pedidosAdicionales">
											<p id="p13_pedidosAdicionalesEncargos" class="p13_parrafoPedidosAdicionales"><spring:message code="p13_referenciasCentro.mensajePedidosAdicionales1" /><span id="p13_pedidosAdicionalesEncargosCont" class="p13_parrafoPedidosAdicionales" style="display:inline"></span></p>
											<p id="p13_pedidosAdicionalesMontajesAdicionales" class="p13_parrafoPedidosAdicionales"><spring:message code="p13_referenciasCentro.mensajePedidosAdicionales2" /><span id="p13_pedidosAdicionalesMontajesAdicionalesCont" class="p13_parrafoPedidosAdicionales" style="display:inline"></span></p>
											<p id="p13_pedidosAdicionalesMac" class="p13_parrafoPedidosAdicionales"><spring:message code="p13_referenciasCentro.mensajePedidosAdicionales3" /><span id="p13_pedidosAdicionalesMacCont" class="p13_parrafoPedidosAdicionales" style="display:inline"></span></p>
											<p id="p13_pedidosAdicionalesError" class="p13_parrafoPedidosAdicionalesError"><spring:message code="p13_referenciasCentro.errorPedidoAdicionales" /></p>
										</div>
									</div>
								</fieldset>	
							</div>
							<div class="p13_divider"></div> 
							<div id="p13_referenciasCentroPedir3">	
								<div id="p13_divFfppActivaUnitaria">
								<fieldset id="p13_ffppActivaFieldset" style="display:none">
								<legend id="p13_ffppActivaLegend"> <spring:message code="p13_referenciasCentro.legendFfppActiva" /> </legend>
									<div id="p13_divFfppActivaCorreccion" class="p13_divCorreccionFfppUnitaria" style="display: none;"></div>
									<div class="p13_divFfppActiva">
										<p class="p13_parrafoFfppActiva"><spring:message code="p13_referenciasCentro.mensajeFfppActiva1" /></p>
									</div>
								</fieldset>	
								<fieldset id="p13_unitariaFieldset" style="display:none">
								<legend id="p13_unitariaLegend"> <spring:message code="p13_referenciasCentro.legendUnitaria" /> </legend>
									<div id="p13_divUnitariaCorreccion" class="p13_divCorreccionFfppUnitaria" style="display: none;"></div>
									<div class="p13_divUnitaria">
										<p class="p13_parrafoUnitaria"><spring:message code="p13_referenciasCentro.mensajeUnitaria1" /></p>
									</div>
								</fieldset>	
								</div>
								<div id="p13_SustituyeARef">
									<fieldset id="p13_SustituyeARefFieldset" style="display:none">
										<legend id="p13_SustituyeARefLegend"> <spring:message code="p13_referenciasCentro.legendSustituyeARef" /> </legend>	
										<div id="p13_divSustituyeARefCorreccion" class="p13_divSustituyeSustitutaCorreccion" style="display: none;"></div>
										<div class="p13_divSustituyeARef">
											<p class="p13_parrafoSustituyeARef"><spring:message code="p13_referenciasCentro.mensajeSustituyeARef" /></p>
										</div>
									</fieldset>	
								</div>
								<div id="p13_SustituidaPorRef">
									<fieldset id="p13_SustituidaPorRefFieldset" style="display:none">
										<legend id="p13_SustituidaPorRefLegend"> <spring:message code="p13_referenciasCentro.legendSustituidaPorRef" /> </legend>	
										<div id="p13_divSustituidaPorRefCorreccion" class="p13_divSustituyeSustitutaCorreccion" style="display: none;"></div>
										<div class="p13_divSustituidaPorRef">
											<p class="p13_parrafoSustituidaPorRef"><spring:message code="p13_referenciasCentro.mensajeSustituidaPorRef" /></p>
										</div>
									</fieldset>	
								</div>
					
								
							</div>
							
							<input type="hidden" id="p13_fld_refActiva" value=""></input>
							<input type="hidden" id="p13_fld_mapaHoy" value=""></input>
							<input type="hidden" id="p13_numeroPedidosOtroDia" value=""></input>
							<input type="hidden" id="p13_fld_tieneFoto" value="N"></input>

							<input type=hidden id="p16_fld_stockInicial" value=""></input>
							<input type=hidden id="p16_fld_entradas" value=""></input>
							<input type=hidden id="p16_fld_ventaTarifa" value=""></input>
							<input type=hidden id="p16_fld_ventaPromocional" value=""></input>
							<input type=hidden id="p16_fld_ventaForzada" value=""></input>
							<input type=hidden id="p16_fld_modifAjuste" value=""></input>
							<input type=hidden id="p16_fld_modifRegul" value=""></input>
							<input type=hidden id="p16_fld_stockFinal" value=""></input>
							<input type=hidden id="p16_fld_centroParametrizado" value=""></input>

						</div>
					</div>
				</div>	
			</div>
			<div id=p13_AreaPestanas style="display:none;">
				<div id="p13_pestanas">
				    <ul>
						<li><a href="#p13_pestanaMovimientos"><span id="p13_StylePestanaMovimientosSpan"><spring:message code="p13_referenciasCentro.movimientos" /></span></a><input type=hidden id="p13_pestanaMovimientosCargada"></input></li>
				        <li><a href="#p13_pestanaImagenComercial"><span id="p13_StylePestanaImagenComercialSpan" class="p13_StylePestanaImagenComercialSpan"><spring:message code="p13_referenciasCentro.imagenComercial" /></span></a><input type=hidden id="p13_pestanaImagenComercialCargada"></input></li>
				        <li><a href="#p13_pestanaDatosMaestro"><span id="p13_StylePestanaDatosMaestroSpan" class="p13_StylePestanaDatosMaestroSpan"><spring:message code="p13_referenciasCentro.datosMaestros" /></a></span><input type=hidden id="p13_pestanaDatosMaestroCargada"></input></li>
				    </ul>
				    <div id="p13_pestanaMovimientos">
				        <%@ include file="/WEB-INF/views/p16_referenciasCentroMovimientos.jsp" %>
				    </div>
				    <div id="p13_pestanaImagenComercial">
				        <%@ include file="/WEB-INF/views/p15_referenciasCentroImagenComercial.jsp" %>
				    </div>
				    <div id="p13_pestanaDatosMaestro">
				        <%@ include file="/WEB-INF/views/p14_referenciasCentroDatosMaestro.jsp" %>
				    </div>
				</div>
				<%@ include file="/WEB-INF/views/p18_referenciasCentroPopupPedir.jsp" %>
				<%@ include file="/WEB-INF/views/p31_popupMotivosTengoMuchoPoco.jsp" %>
			</div>
			<!-- No borrar este comentario. Es el area para las acciones de página  
			<div id=AreaAccionesPagina>
					<input type="button" id="pXX_btn_accion1" class="boton  botonHover" value="Acción 1"></input>
					<input type="button" id="pXX_btn_accion2" class="boton  botonHover" value="Acción 2"></input>
					<input type="button" id="pXX_btn_accion3" class="boton  botonHover" value="Acción 3"></input>
			</div> 
			-->
		</div>	
										

<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>