<script src="./misumi/scripts/p30PopUpInfoReferencia.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Motivo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Articulo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PendientesRecibir.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ParamStockFinalMin.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>

		<!--  Contenido página -->
			<div id="p30_popupInfoReferencia" title="<spring:message code="p30_popupInfoReferencia.infoReferencia"/>" style="display:none" class="controlReturnP30">
				
					<fieldset id="p30_FSET_DescripcionBasica">
						<legend id="p30_LGEN_DescripcionBasica"> <spring:message code="p30_popupInfoReferencia.descripcionBasica" /> </legend>
					<div id="p30_AreaDescripcionBasica" class="p30_fieldsetContenido">
						<div id="p30_pedidoAutomatico">
							<fieldset id="p30_pedidoAutomaticoFieldsetDepositoBrita">
								<legend id="p30_pedidoAutomaticoLegendDepositoBrita"> <spring:message code="p30_referenciasCentro.legendAprovisionamiento" /> </legend>
								<p class="p30_parrafoPedidoAutomaticoDepositoBrita"><spring:message code="p30_referenciasCentro.mensajeAprovisionamientoReferenciaDepositoBrita" /></p>
							</fieldset>	
							<fieldset id="p30_pedidoAutomaticoFieldsetPorCatalogo">
								<legend id="p30_pedidoAutomaticoLegendPorCatalogo"> <spring:message code="p30_referenciasCentro.legendAprovisionamiento" /> </legend>
								<p class="p30_parrafoPedidoAutomaticoPorCatalogo"><spring:message code="p30_referenciasCentro.mensajeAprovisionamientoReferenciaPorCatalogo" /></p>
							</fieldset>	
							<fieldset id="p30_pedidoAutomaticoFieldsetActivo">
								<legend id="p30_pedidoAutomaticoLegendActivo"> <spring:message code="p30_referenciasCentro.legendPedidoAutomatico" /> </legend>
								<p class="p30_parrafoPedidoAutomaticoActivo"><spring:message code="p30_referenciasCentro.mensajePedidoAutomaticoActivo" /></p>
							</fieldset>	
							<fieldset id="p30_pedidoAutomaticoFieldsetActivoVegalsa">
								<legend id="p30_pedidoAutomaticoLegendActivoVegalsa"> <spring:message code="p30_referenciasCentro.legendPedidoAutomaticoActivoVegalsa" /> </legend>
								<input type=hidden id="p30_fld_TextoFechaBasico" value="<spring:message code='p30_referenciasCentro.mensajePedidoAutomaticoActivoVegalsa'/>"></input>
								<p id="p30_TextoFecha" class="p30_parrafoPedidoAutomaticoActivo"></p>
							</fieldset>	
							<fieldset id="p30_pedidoAutomaticoFieldsetNoActivo">
								<legend id="p30_pedidoAutomaticoLegendNoActivo"> <spring:message code="p30_referenciasCentro.legendPedidoAutomatico" /> </legend>
								<p id="p30_parrafoPedidoAutomaticoNoActivo" class="p30_parrafoPedidoAutomaticoNoActivo"><spring:message code="p30_referenciasCentro.mensajePedidoAutomaticoNoActivo1" /></p>
								
								<div id="p30_AreaPopupPedir">
									<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" id="p30_MotNoActivaDivTitle">
										<span id="p30_TituloMotNoActiva" class="ui-dialog-title"><spring:message code="p30_popupInfoReferencia.tituloMotNoActiva" /></span>
									</div>
									<!-- <table id="gridP30Motivos"></table>
									<div id="pagerP30Motivos"/>-->
									<div id="p30_AreaPopupPedirGeneral">
										<div id="p30_AreaMotivosGeneral">			
											<div id="p30_AreaMotivosGeneralTabla">
												<table id="gridP30Motivos"></table>
												<div id="pagerP30Motivos"></div>
											</div>
										</div>
									</div>
									<c:choose>
										<c:when test="${!user.centro.esCentroCaprabo || (user.centro.esCentroCaprabo && user.centro.esCentroCapraboNuevo)}">
										<!-- <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" id="p30_mmcDivTitle" style="display:none"> -->
										<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" id="p30_mmcDivTitle">
											<span id="p30_TituloMotivosMMC" class="ui-dialog-title"><spring:message code="p30_popupInfoReferencia.tituloMMC" /></span>
										</div>
									 	<div id="p30_AreaPopupPedirMMC" style="display:none">
											<div id="p30_AreaMotivosMMC">			
												<div id="p30_AreaMotivosMMCTabla">
													<table id=gridP30MMC></table>
													<div id="pagerP30MotivosMMC"></div>
												</div>
											</div>  
										</div>
										</c:when>
									</c:choose>	 
								</div>
							</fieldset>	
							<input type="hidden" id="p30_fld_refActiva" value=""></input>
							<input type="hidden" id="p30_fld_mapaHoy" value=""></input>
							<input type="hidden" id="p30_fld_grupo1" value=""></input>
						</div>   
	
						<div id="p30_maestros1">
						<div id="p30_datosPedido">
							<div class="p30_etiqValorCampo">
								<label id="p30_lbl_referencia" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.referencia" /></label>
								<label id="p30_lbl_referenciaVal" class="valorCampo"></label>
								<input type=hidden id="p30_fld_referenciaEroski"/>
							</div>
							<div class="p30_etiqValorCampo">
								<label id="p30_lbl_estructura" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.estructura" /></label>
								<label id="p30_lbl_estructuraVal" class="valorCampo"></label>
							</div>
							<div class="p30_etiqValorCampo">
								<label id="p30_lbl_mapa" class="etiquetaCampoNegrita" style="display:none;"><spring:message code="p30_popupInfoReferencia.mapa" /></label>
								<label id="p30_lbl_mapaVal" class="valorCampo" style="display:none;"></label>
							</div>
							<div class="p30_etiqValorCampo">
								<label id="p30_lbl_tipoReferencia" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.tipoReferencia" /></label>
								<label id="p30_lbl_tipoReferenciaVal" class="valorCampo"></label>
								<label id="p30_lbl_soloReparto" class="etiquetaCampoNegrita" style="display:none;"><spring:message code="p30_popupInfoReferencia.soloReparto" /></label>
								<label id="p30_lbl_soloRepartoVal" class="valorCampo" style="display:none;"></label>
							</div>
							<div id="p30_DatosEspecificosTextil">
								<div id="p30_estructura" class="textBox p30_datosEspecificosTextilLinea">
									<label id="p30_lbl_estructuraTexil" class="etiquetaCampoNegrita"><spring:message code="p30_referenciasCentro.estructuraTexil" /></label>
									<label id="p30_lbl_estructuraTexilVal" class="valorCampo"></label>
								</div>
								<div id="p30_estructura" class="textBox p30_datosEspecificosTextilLinea"">	
									<label id="p30_lbl_modeloProveedor" class="etiquetaCampoNegrita"><spring:message code="p30_referenciasCentro.modeloProveedor" /></label>
									<label id="p30_lbl_modeloProveedorVal" class="valorCampo"></label>
								</div>
								<div id="p30_talla" class="textBox p30_datosEspecificosTextilLinea"">	
									<label id="p30_lbl_talla" class="etiquetaCampoNegrita"><spring:message code="p30_referenciasCentro.talla" /></label>
									<label id="p30_lbl_tallaVal" class="valorCampo"></label>
								</div>
								<div id="p30_color" class="textBox p30_datosEspecificosTextilLinea"">	
									<label id="p30_lbl_color" class="etiquetaCampoNegrita"><spring:message code="p30_referenciasCentro.color" /></label>
									<label id="p30_lbl_colorVal" class="valorCampo"></label>
								</div>		
							</div>
							<div class="p30_etiqValorCampo">
								<div class="p30_divStock">
									<label id="p30_lbl_stockActual" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.stockActual" /></label>
									<label id="p30_lbl_stockActualVal" class="valorCampo"></label>
								</div>
								<div id="p30_stockPlataforma" class="p30_divStock">
									<label id="p30_lbl_stockPlataforma" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.stockPlataforma" /></label>
									<label id="p30_lbl_stockPlataformaVal" class="valorCampo"></label>
								</div>
							</div>
							<div class="p30_etiqValorCampo">
								<label id="p30_respuestaWS" style="display: inline; color: #D8000C !important; font-size: inherit;"></label>
							</div>
							<div class="p30_etiqValorCampo">
								<label id="p30_lbl_descripcion" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.descripcion" /></label>
								<label id="p30_lbl_descripcionVal" class="valorCampo"></label>
							</div>
							<div id="p30_referenciasCentroDescripcionMensajeError" style="display:none;">
								<span id="p30_referenciasCentroDescripcionMensajeErrorTexto"><spring:message code="p30_Vegalsa_referenciasCentro.errorWSFacingVegalsa" /></span>
							</div>
							<div id="p30_divPvp" class="p30_etiqValorCampo" style="display:none">
								<label id="p30_lbl_pvp" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.pvp" /></label>
								<label id="p30_lbl_pvpVal" class="valorCampo"></label>
							</div>
							<div class="p30_etiqValorCampo">
								<div id="p30_divPvpOferta" class="p30_divMitdad" style="display:none">
									<label id="p30_lbl_pvpOferta" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.pvpOferta" /></label>
									<label id="p30_lbl_pvpOfertaVal" class="valorCampo"></label>
								</div>
								<div id="p30_divOferta" class="p30_divMitdad" style="display:none">
									<label id="p30_lbl_oferta" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.oferta" /></label>
									<label id="p30_lbl_ofertaVal" class="valorCampo"></label>
								</div>
							</div>
							<div id="p30_divPendienteRecibir1" class="p30_etiqValorCampo" style="display:none">
								<label id="p30_lbl_pendienteRecibir1" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.pendienteRecibir1" /></label>
								<label id="p30_lbl_pendienteRecibir1Val" class="valorCampo"></label>
							</div>
							<div id="p30_divPendienteRecibir2" class="p30_etiqValorCampo" style="display:none">
								<label id="p30_lbl_pendienteRecibir2" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.pendienteRecibir2"/></label>
								<label id="p30_lbl_pendienteRecibir2Val" class="valorCampo"></label>
							</div>
							<div id="p30_divMAC" class="p30_etiqValorCampo"  style="display:none;padding-top: 15px;">
								<label id="p30_lbl_montajeAdicionalCentro" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.montajeAdicionalCentro" /></label>
								<label id="p30_lbl_montajeAdicionalCentroVal" class="valorCampo"></label>
							</div>
							<div id="p30_divEspacio" class="p30_etiqValorCampo"  style="display:none">
								<label id="p30_lbl_espacio" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.espacio" /></label>
								<label id="p30_lbl_espacioVal" class="valorCampo"></label>
							</div>
							<div id="p30_divMA" class="p30_etiqValorCampo"  style="display:none;padding-top: 15px;">
								<label id="p30_lbl_montajeAdicional" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.montajeAdicional" /></label>
								<label id="p30_lbl_montajeAdicionalVal" class="valorCampo"></label>
							</div>
							<div id="p30_divAccion" class="p30_etiqValorCampo" style="display:none">
								<label id="p30_lbl_accion" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.accion"/></label>
								<label id="p30_lbl_accionVal" class="valorCampo"></label>
							</div>
							<div id="p30_divImportante" class="p30_etiqValorCampo" style="display:none">
								<label id="p30_lbl_importante" class="etiquetaCampoNegrita"><spring:message code="p30_popupInfoReferencia.importante"/></label>
								<label id="p30_lbl_importanteVal" class="valorCampo"></label>
							</div>
						</div>
						<div id="p30_imgTablasPedido">
							<div id="p30_AreaFotos">
								<div id="p30_fieldset_foto">
									<div id="p30_fieldset_leyenda"><spring:message code="p30_referenciasCentroM.foto" /></div>
									<img id="p30_img_referencia" src="./misumi/images/pixel.png" />
								</div>
							</div>
							<div id="p30_tabla" style="display:none">
									<fieldset id="">
										<legend id="p30_lgen_tabla"><spring:message code="p30_puedePedirRef.proxPed" /></legend>
										<table id="p30_puedePedirTablaEstructura" border="1" class="ui-widget">
											<thead class="ui-widget-header">
												<tr>
		<!-- 										   	<th class="p30_puedePedirRefTablaTh"><spring:message code="p30_puedePedirRef.fecha" /></th> -->
													<th class="p30_puedePedirRefTablaTh"><spring:message code="p30_puedePedirRef.transmision" /></th>
		<!-- 										    <th class="p30_puedePedirRefTablaTh"><spring:message code="p30_puedePedirRef.fecha" /></th> -->
												    <th class="p30_puedePedirRefTablaTh"><spring:message code="p30_puedePedirRef.camion" /></th>
		<!-- 										    <th class="p30_puedePedirRefTablaTh"><spring:message code="p30_puedePedirRef.fecha" /></th> -->
												    <th class="p30_puedePedirRefTablaTh"><spring:message code="p30_puedePedirRef.venta" /></th>
												</tr>
											</thead>
											<tbody id="p30_puedePedirTablaFilas" class="ui-widget-content">
												<tr id="p30_puedePedirRefData" style="display:none"> 
		<!-- 											<td id="p30_puedePedirRefDiaTransmision" class="p30_puedePedirRefTablaTd"></td> -->
												    <td id="p30_puedePedirRefFechaTransmision" class="p30_puedePedirRefTablaTd"></td>
		<!-- 										    <td id="p30_puedePedirRefDiaTransporte" class="p30_puedePedirRefTablaTd"></td> -->
												    <td id="p30_puedePedirRefFechaTransporte" class="p30_puedePedirRefTablaTd"></td>
		<!-- 										    <td id="p30_puedePedirRefDiaVenta" class="p30_puedePedirRefTablaTd"></td> -->
												    <td id="p30_puedePedirRefFechaVenta" class="p30_puedePedirRefTablaTd"></td>
												 </tr>
											</tbody>
										</table>
									</fieldset>
							</div>
						</div>						
						</div>	
						<div id="p30_AreaDescripcionBasicaBotonera">
							
							<input type="button" id="p30_btn_masInfoRef" class="boton  botonHover" value='<spring:message code="p30_popupInfoReferencia.masInfoRef" />'></input>
							<c:if test="${user.perfil != 3 && misumi:contieneOpcion(user.centro.opcHabil, '140_INCLUSION_EXCLUSION_GAMA')}">
								<input type="button" id="p30_btn_incluirRefGama" class="boton  botonHover" value='<spring:message code="p30_popupInfoReferencia.incluirRefGama" />'></input>
								<input type="button" id="p30_btn_deshacerIncluirRefGama" class="boton  botonHover" value='<spring:message code="p30_popupInfoReferencia.deshacerIncluirRefGama" />'></input>
								<input type="button" id="p30_btn_excluirRefGama" class="boton  botonHover" value='<spring:message code="p30_popupInfoReferencia.excluirRefGama" />'></input>
								<input type="button" id="p30_btn_deshacerExcluirRefGama" class="boton  botonHover" value='<spring:message code="p30_popupInfoReferencia.deshacerExcluirRefGama" />'></input>
								<div id="p30_AreaMensajeIncluirExcluir">
									<label id="p30_lbl_mensajeIncluirExcluir"></label>
								</div>
							</c:if>
						</div>
						</div>
					</fieldset>	
				
				
				
					<fieldset id="p30_FSET_UltimosPedidos">
						<legend id="p30_LGEN_UltimosPedidos"> <spring:message code="p30_popupInfoReferencia.ultimosPedidos" /> </legend>
						<div id="p30_AreaUltimosPedidos"  class="p30_fieldsetContenido">
						<div id="p30_ultimosPedidos">
								<table id="gridP30UPed"></table>
						</div>
						<div id="p30_ultimosPedidosNoExisten">	
							<fieldset id="p30_FSET_ultimosPedidosNoExisten">
								<legend id="p30_FSET_ultimosPedidosNoExistenLegend"> <spring:message code="p30_popupInfoReferencia.ultimosPedidos" /> </legend>
								<p id="p30_FSET_ultimosPedidosNoExistenParrafo"></p>
							</fieldset>
							
						</div>
						<div id="p30_AreaUltimosPedidosBotonera">
							<input type="button" id="p30_btn_masInfoPedidos" class="boton  botonHover" value='<spring:message code="p30_popupInfoReferencia.masInfoPedidos" />'></input>
						</div>	
						</div>
					</fieldset>	
				
				
			</div>
			
			