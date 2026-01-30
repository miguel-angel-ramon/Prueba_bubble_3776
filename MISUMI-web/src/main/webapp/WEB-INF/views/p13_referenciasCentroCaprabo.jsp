<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p13ReferenciasCentroCaprabo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/HistoricoVentaUltimoMes.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Motivo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/MotivoTengoMuchoPoco.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.jqplot.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/excanvas.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jqplot.meterGaugeRenderer.min.js?version=${misumiVersion}"></script>




<!--[if IE]> <input id="p13_caprabo_esExplorer" type="hidden" value="ie"/><![endif]-->
<!--[if !(IE)]><!--><input id="p13_caprabo_esExplorer" type="hidden" value="no_ie"/><!--<![endif]-->
	
		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p13_caprabo_referenciasCentro.welcome" /></a></li>
						<li><spring:message code="p13_caprabo_referenciasCentro.referenciasCentro" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="p13_caprabo_AreaFiltro">
				<div id="p13_caprabo_filtroReferencia">
					<div class="textBox">
						<label id="p13_caprabo_lbl_referencia" class="etiquetaCampo"><spring:message code="p13_caprabo_referenciasCentro.reference" /></label>
						<input type=text id="p13_caprabo_fld_referencia" class="input100 controlReturn" value="${reference}"></input>
						<input type=hidden id="p13_caprabo_fld_refPagina" class="input100 controlReturn" value="${pagConsulta}"></input>
					</div>
					<div id="p13_caprabo_descripcionRef" class="textBox" style=" display: none;">
							<label id="p13_caprabo_lbl_descripcionRef" class="etiquetaCampo"><spring:message code="p13_caprabo_referenciasCentro.descripcionRef" /></label>
							<input type=text id="p13_caprabo_fld_descripcionRef" class="input360"></input>
						</div>
				</div>			
				<div id="p13_caprabo_filterButtons">
					
					<input type="button" id="p13_caprabo_btn_buscar" class="boton  botonHover" value='<spring:message code="p13_caprabo_referenciasCentro.find" />'></input>
					<input type="button" id="p13_caprabo_btn_reset" class="boton  botonHover" value='<spring:message code="p13_caprabo_referenciasCentro.reset" />'></input>
					
				</div>
			</div>
			
			<div id="AreaMaestrosFijoCaprabo" style="display:none;">
													 								
				<div id="p13_caprabo_maestroFijoEstructura">

					<div class="p13_caprabo_maestroFijoEstructuraField" >

						<div id="p13_caprabo_referenciasCentroPedir">
							
							<div id="p13_caprabo_referenciasCentroStock">
							
								 <div id="p13_caprabo_referenciasCentroStockMensajeError" style="display: none;">
									<span id="p13_caprabo_referenciasCentroStockMensajeErrorTexto"><spring:message code="p13_caprabo_referenciasCentro.errorWSVentasTienda" /></span>
								</div>
								<fieldset id="p13_caprabo_stockActualFieldsetOk" style="display: none;">
									<legend id="p13_caprabo_stockActualLegendOk"><spring:message code="p13_caprabo_referenciasCentro.stockOK" /></legend>
										<div id="p13_caprabo_divStockActualOkCorreccion" class="p13_caprabo_divCorreccionStockActualOk" style="display: none;"></div>
										<div class="p13_caprabo_divStockActualOk">
											<p class="p13_caprabo_parrafoStockActualOk"></p><br>
											<p class="p13_caprabo_parrafoStockActualOkDias"></p>
										</div>
								</fieldset>	
								<fieldset id="p13_caprabo_stockActualFieldsetErroneo" style="display: none;">
									<legend id="p13_caprabo_stockActualLegendErroneo"></legend>
									<div id="p13_caprabo_divStockActualErroneoCorreccion" class="p13_caprabo_divCorreccionStockActualErroneo" style="display: none;"></div>
									<div class="p13_caprabo_divStockActualErroneo">
										<div id="p13_caprabo_cantidadStockErroneo"><span class="p13_caprabo_parrafoStockActualErroneo"></span><span class="p13_caprabo_parrafoStockActualErroneoDias"></span></div>
									</div>
									<div id="p13_caprabo_divStockActualErroneoInfo" style="display: none;">
										<p class="p13_caprabo_stockActualErroneoInfo"><spring:message code="p13_caprabo_referenciasCentro.mensajeInfoStockActualErroneo" /></p>
									</div>
								</fieldset>	
									
							</div>
							  
							<div id="p13_caprabo_ChartDivider" class="p13_caprabo_divider"></div> 
							
							<div id="p13_caprabo_referenciasCentroPedir1">
								<fieldset id="p13_caprabo_pedidoAutomaticoFieldsetActivo">
								<legend id="p13_caprabo_pedidoAutomaticoLegendActivo"> <spring:message code="p13_caprabo_referenciasCentro.legendPedidoAutomatico" /> </legend>
									<div id="p13_caprabo_divActivoCorreccion" class="p13_caprabo_divCorreccionPedidoActivo" style="display: none;"></div>
									<div class="p13_caprabo_divPedidoAutomaticoActivo">
										<p class="p13_caprabo_parrafoPedidoAutomaticoActivo"><spring:message code="p13_caprabo_referenciasCentro.mensajePedidoAutomaticoActivo" /></p>
									</div>
								</fieldset>	
								<fieldset id="p13_caprabo_pedidoAutomaticoFieldsetNoActivo">
								<legend id="p13_caprabo_pedidoAutomaticoLegendNoActivo"> <spring:message code="p13_caprabo_referenciasCentro.legendPedidoAutomatico" /> </legend>
									<div id="p13_caprabo_divNoActivoCorreccion" class="p13_caprabo_divCorreccion" style="display: none;"></div>
									<div class="p13_caprabo_divPedidoAutomaticoNoActivo">
										<p id="p13_caprabo_parrafoPedidoAutomaticoNoActivoConEnlace" class="p13_caprabo_parrafoPedidoAutomaticoNoActivoConEnlace" style="display: none;"><spring:message code="p13_caprabo_referenciasCentro.mensajePedidoAutomaticoNoActivo1" /></p>
										<p id="p13_caprabo_parrafoPedidoAutomaticoNoActivoSinEnlace" class="p13_caprabo_parrafoPedidoAutomaticoNoActivoSinEnlace" style="display: none;"><spring:message code="p13_caprabo_referenciasCentro.mensajePedidoAutomaticoNoActivo2" /></p>
									</div>
								</fieldset>	
							</div>
							
							<div class="p13_caprabo_divider"></div> 
					
							<div id="p13_caprabo_referenciasCentroPedir3">	
							
								<div id="p13_caprabo_divFfppActivaUnitaria">
									
								</div>

								<div id="p13_caprabo_SustituyeARef">
									<fieldset id="p13_caprabo_SustituyeARefFieldset" style="display:none">
										<legend id="p13_caprabo_SustituyeARefLegend"> <spring:message code="p13_caprabo_referenciasCentro.legendSustituyeARef" /> </legend>	
										<div id="p13_caprabo_divSustituyeARefCorreccion" class="p13_caprabo_divSustituyeSustitutaCorreccion" style="display: none;"></div>
										<div class="p13_caprabo_divSustituyeARef">
											<p class="p13_caprabo_parrafoSustituyeARef"><spring:message code="p13_caprabo_referenciasCentro.mensajeSustituyeARef" /></p>
										</div>
									</fieldset>	
								</div>
								<div id="p13_caprabo_SustituidaPorRef">
									<fieldset id="p13_caprabo_SustituidaPorRefFieldset" style="display:none">
										<legend id="p13_caprabo_SustituidaPorRefLegend"> <spring:message code="p13_caprabo_referenciasCentro.legendSustituidaPorRef" /> </legend>	
										<div id="p13_caprabo_divSustituidaPorRefCorreccion" class="p13_caprabo_divSustituyeSustitutaCorreccion" style="display: none;"></div>
										<div class="p13_caprabo_divSustituidaPorRef">
											<p class="p13_caprabo_parrafoSustituidaPorRef"><spring:message code="p13_caprabo_referenciasCentro.mensajeSustituidaPorRef" /></p>
										</div>
									</fieldset>	
								</div>
								
							</div>
							
							<input type="hidden" id="p13_caprabo_fld_refActiva" value=""></input>
							<input type="hidden" id="p13_caprabo_fld_mapaHoy" value=""></input>
							<input type="hidden" id="p13_caprabo_numeroPedidosOtroDia" value=""></input>
							<input type="hidden" id="p13_caprabo_fld_tieneFoto" value="N"></input>
						</div>
					</div>
				</div>	
			</div>
			
			<div id=p13_caprabo_AreaPestanas style="display:none;">
				<div id="p13_caprabo_pestanas">
				   
				    <div id="p13_caprabo_pestanaImagenComercial">
				        <%@ include file="/WEB-INF/views/p15_referenciasCentroImagenComercialCaprabo.jsp" %>
				    </div>
				   
				</div>
				<%@ include file="/WEB-INF/views/p18_referenciasCentroPopupPedirCaprabo.jsp" %>
			</div>
			
		</div>	
										

<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>