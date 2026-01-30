
		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p52_AreaReferenciaHorizontal" class="p52_AreaReferenciaHorizontal controlReturnP52">
				<div id="p52_AreaBloque1" >
					<fieldset>
						<legend><spring:message code="p52_nuevoPedidoAdicionalREF.titBloque1" /></legend>
						<div id="p52_filtroBloque1">
							<div class="comboBox comboBoxMedium">
								<label id="p52_lbl_tipoPedidoAdicional" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.tipoPedidoAdicional" /></label>
								<select id="p52_cmb_tipoPedidoAdicional"></select>
							</div>
							<div class="textBox">
								<label id="p52_lbl_referencia" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.reference" /></label>
								<input type=text id="p52_fld_referencia" class="input85 controlReturnP52" ></input>
								<input type="hidden" id="p52_fld_referenciaEroski" ></input>
							</div>
							<div class="textBox">
								<label id="p52_lbl_denominacion" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.denominacion" /></label>
								<input type=text id="p52_fld_denominacion" class="input225"></input>
								<input type=hidden id="p52_fld_denominacionEroski" ></input>
							</div>
							<div class="textBox">
								<label id="p52_lbl_aprov" class="etiquetaCampo" title="<spring:message code='p52_nuevoPedidoAdicionalREF.tipoAprovisionamiento'/>"><spring:message code="p52_nuevoPedidoAdicionalREF.tipoAprovisionamientoAbrev" /></label>
								<input type=text id="p52_fld_aprov" class="input15" value=""></input>
							</div>								
						</div>
						<div id="p52_AreaTextilLote" style="display: none;">
							<label id="p52_notaTextilLote"><spring:message code="p52_nuevoPedidoAdicionalREF.referenciaLoteTextil" /></label>
						</div>
					</fieldset>
				</div>
				<div id="p52_AreaBloque2EncargoCliente" style="display: none">
					<fieldset> 
						<legend id="p52_div_legend2EncargoCliente"><spring:message code="p52_nuevoPedidoAdicionalREF.titBloque2EncargoCliente" /></legend>
							<div class="p52_bloque2EncargoClienteAreaDatosCampos">
								<div id="p52_div_stock_enc_cli">
									<div id="p52_div_stock_enc_cli" class="textBoxMin">
										<label id="p52_lbl_stock_enc_cli" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.stock" /></label>
										<input type=text id="p52_fld_stock_enc_cli" class="input40 conStock"></input>
									</div>
								</div>
								<div id="p52_div_cantidad_enc_cli" class="textBoxMin">
									<div id="p52_div_cantidad_enc_cli" class="textBoxMin">
										<label id="p52_lbl_cantidad_enc_cli" class="etiquetaCampo">Vi 5</label>
										<input type=text id="p52_fld_cantidad_enc_cli" class="input40"></input>
										<label id="p52_lbl_descPedido"></label>
									</div>
								</div>
							</div>
					</fieldset>
				</div>
				<div id="p52_AreaBloque2">
					<fieldset>
						<legend>
							<span id="p52_AreaBloque2Legend" style="display: block; font-weight: bold"><spring:message code="p52_nuevoPedidoAdicionalREF.titBloque2"/></span>
 							<span id="p52_AreaBloque2LegendEncargo" style="display: none;font-weight: bold"><spring:message code="p52_nuevoPedidoAdicionalREF.titBloque2Encargo"/></span>
						</legend>
						<div id="p52_filtroBloque2">
							<div id="p52_div_numeroOferta" class="comboBox comboBoxMediumShort">
								<label id="p52_lbl_numeroOferta" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.numOferta" /></label>
								<select id="p52_cmb_numeroOferta"></select>
							</div>
							<div id="p52_div_fechaInicio" class="textBox">
								<div style="display: inline-block; position:relative;">
									<label id="p52_lbl_fechaInicio" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.fechaInicio" /></label>
									<img id="p52_btn_ayudaActiva" class="botonAyudaNR" title="Ayuda" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}">
								</div>
								<div id="p52_fechaInicioDatePicker" class="p52_datepicker"></div>
							</div>								
							<div id="p52_div_fechaFin" class="textBoxMin">
								<label id="p52_lbl_fechaFin" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.fechaFin" /></label>
								<div id="p52_fechaFinDatePicker" class="p52_datepicker"></div>
							</div>								
							<div id="p52_filtroBloque2Excluir">
								<div id="p52_leyendaReferenciaNueva">
									<label id="p52_tituloLeyendaReferenciaNueva" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.referenciaNueva" /></label>
									<span id="p52_mensajeLeyendaReferenciaNueva" class="valorCampo"></span>
									<label id="p52_ReferenciaNueva" style="display: none"><spring:message code="p52_nuevoPedidoAdicionalREF.mensajeReferenciaNueva" /></label>
									<label id="p52_ReferenciaNuevaBloqueo" style="display: none"><spring:message code="p52_nuevoPedidoAdicionalREF.mensajeReferenciaNuevaBloqueo" /></label>
								</div>
								<div id="p52_excluir">
									<div class="comboBoxMin comboBoxExtraShort">
										<label id="p52_lbl_excluir" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.excluir" /></label>
										<select id="p52_cmb_excluir"></select>
									</div>	
								</div>	
								<div id="p52_rellenoExcluir">&nbsp;</div>
								<div id="p52_fechaNoDisponible" style="display: none;"><spring:message code="p52_nuevoPedidoAdicionalREF.fechaNoDisponible" /></div>
								<div id="p52_rellenoMensajesFechas" style="display: none;">&nbsp;</div>
								<div id="p52_encargoMensajesFechas" style="display: none;">
									<div id="p52_encargoFechasBloquedasEncargos">
										<div id="p52_encargoFechasBloquedasEncargosImg"></div>
										<label class="p52_mensajesBloqueoCalendarios"><spring:message code="p52_nuevoPedidoAdicionalREF.msgFechasBloqueadasParaEncargos" /></label>
									</div>
								</div>
								<div id="p52_montajeMensajesFechas" style="display: none;">
									<div id="p52_montajeFechasBloquedasEncargos" style="display: none;">
										<div id="p52_montajeFechasBloquedasEncargosImg"></div>
										<label class="p52_mensajesBloqueoCalendarios"><spring:message code="p52_nuevoPedidoAdicionalREF.msgFechasBloqueadasParaEncargos" /></label>
									</div>
									<div id="p52_montajeFechasBloquedasMontajes" style="display: none;">
										<div id="p52_montajeFechasBloquedasMontajesImg"></div>
										<label class="p52_mensajesBloqueoCalendarios"><spring:message code="p52_nuevoPedidoAdicionalREF.msgFechasBloqueadasParaMontajes" /></label>
									</div>
								</div>
							</div>
							<div id="p52_tratamiento" class="textBoxMin" style="display: none">
								<div id="p52_div_tratamiento" class="comboBox comboBoxShort">
									<label id="p52_lbl_tratamiento" class="etiquetaCampo"><spring:message code='p52_nuevoPedidoAdicionalREF.tratamiento'/></label>
									<select id="p52_cmb_tratamiento"></select>
								</div>	
							</div>
							<div id="p52_AreaFotos" style="display:none">
								<fieldset>
									<legend><spring:message code="p52_referenciasCentroM.foto" /></legend>
									<img id="p52_img_referencia" src="./misumi/images/pixel.png" />
								</fieldset>
							</div>
							<div id="p52_filtroBloque2Lote">
								<div id="p52_leyendaReferenciaLote">
									<label id="p52_ReferenciaLote" style="display: none;" ><spring:message code="p52_nuevoPedidoAdicionalREF.mensajeReferenciaLote" /></label>
									<label id="p52_ReferenciaHijaDeLote" style="display: none;" ><spring:message code="p52_nuevoPedidoAdicionalREF.mensajeReferenciaHijaDeLote" /></label>
								</div>
							</div>
							
						</div>
					</fieldset>
				</div>
				<div id="p52_AreaBloque3EncargoCliente" style="display: none">
					<input type="hidden" id="encargoClienteEspecialCalendario" value=""></input>
					<input type="hidden" id="encargoClientePrimFechaEntregaCalendario" value=""></input>
					<input type="hidden" id="encargoClienteUnidadesPedirCalendario" value=""></input>
					<fieldset>
						<legend><spring:message code="p52_nuevoPedidoAdicionalREF.titBloque3EncargoCliente" /></legend>
						<div id="p52_bloque3EncargoClienteFecha">
							<div id="p52_div_fechaEntregaEncCli" class="textBox">
								<div style="display: inline-block; position:relative;">
									<label id="p52_lbl_fechaEntregaEncCli" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.fechaEncargo" /></label>
<%-- 									<img id="p52_btn_ayudaActivaEncCli" class="botonAyudaNR" title="Ayuda" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}"> --%>
								</div>
								<div id="p52_fechaEntregaEncCliDatePicker" class="p52_datepicker"></div>
							</div>								
						</div>
						<div id="p52_bloque3EncargoClienteCliente">
							<fieldset id="p52_estructuraClienteFieldset"><legend id="p73_estructuraClienteLegend"><spring:message code="p52_nuevoPedidoAdicionalREF.titCliente" /></legend>
								<div id="p52_AreaEstructuraCliente">
									<div id="p52_clienteNombre_div" class="p52_clienteLabelDatoEstructura">
										<div id="p52_clienteNombre_label" class="p52_clienteLabelEstructura">
											<label id="p52_lbl_clienteNombre" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.clienteNombre" /></label>
										</div>	
										<div id="p52_clienteNombre_dato" class="p52_clienteDatoEstructura">
											<input class="input200 controlReturnP52" type=text id="p52_fld_clienteNombre"></input>
										</div>	
									</div>
									<div id="p52_clientePrimerApellido_div" class="p52_clienteLabelDatoEstructura">
										<div id="p52_clientePrimerApellido_label" class="p52_clienteLabelEstructura">
											<label id="p52_lbl_clientePrimerApellido" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.clientePrimerApellido" /></label>
										</div>	
										<div id="p52_clientePrimerApellido_dato" class="p52_clienteDatoEstructura">
											<input class="input200 controlReturnP52" type=text id="p52_fld_clientePrimerApellido"></input>
										</div>	
									</div>
									<div id="p52_clienteTelefono_div" class="p52_clienteLabelDatoEstructura">
										<div id="p52_clienteTelefono_label" class="p52_clienteLabelEstructura">
											<label id="p52_lbl_clienteTelefono" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.clienteTelefono" /></label>
										</div>	
										<div id="p52_clienteTelefono_dato" class="p52_clienteDatoEstructura">
											<input class="input150 controlReturnP52" type=text id="p52_fld_clienteTelefono"></input>
										</div>	
									</div>
								</div>
							</fieldset>	
						</div>						
						<div id="p52_bloque3EncargoClienteCentro">
							<fieldset id="p52_estructuraCentroFieldset"><legend id="p73_estructuraCentroLegend"><spring:message code="p52_nuevoPedidoAdicionalREF.titCentro" /></legend>
								<div id="p52_AreaEstructuraCentro">
									<div id="p52_centroContacto_div" class="p52_centroLabelDatoEstructura">
										<div id="p52_centroContacto_label" class="p52_centroLabelEstructura">
											<label id="p52_lbl_centroContacto" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.centroContacto" /></label>
										</div>	
										<div id="p52_centroContacto_dato" class="p52_centroDatoEstructura">
											<input class="input200 controlReturnP52" type=text id="p52_fld_centroContacto"></input>
										</div>	
									</div>
								</div>
							</fieldset>	
						</div>						
					</fieldset>
				</div>
				<div id="p52_AreaBloque3">
					<fieldset> 
						<legend id="p52_div_legend3"><spring:message code="p52_nuevoPedidoAdicionalREF.titBloque3"/></legend>
							<div class="p52_bloque3AreaDatos">
								<div class="p52_bloque3AreaDatosCampos">
									<div id="p52_cajas" class="textBoxMin" style="display: none">
										<div id="p52_div_cajas" class="comboBox comboBoxExtraShort">
										<label id="p52_lbl_cajas" class="etiquetaCampo"><spring:message code='p52_nuevoPedidoAdicionalREF.cajas'/></label>
										<select id="p52_cmb_cajas"></select>
										</div>	
									</div>	
									<div id="p52_div_stock">
										<div id="p52_div_stock" class="textBoxMin">
											<label id="p52_lbl_stock" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.stock" /></label>
											<input type=text id="p52_fld_stock" class="input40 conStock"></input>
										</div>
									</div>
									<div id="p52_div_stock_plat" style="display: none">
										<div id="p52_div_stock_plat" class="textBoxMin">
											<label id="p52_lbl_stock_plat" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.stockPlataforma" /></label>
											<input type=text id="p52_fld_stock_plat" class="input40"></input>
										</div>
									</div>
									<div id="p52_div_uc">
										<div id="p52_div_uc" class="textBoxMin">
											<label id="p52_lbl_uc" class="etiquetaCampo"><spring:message code="p52_nuevoPedidoAdicionalREF.uc" /></label>
											<input type=text id="p52_fld_uc" class="input40"></input>
										</div>
									</div>
									<div id="p52_div_frescoPuro" class="textBoxMin">
										<div id="p52_div_cantidad1" class="textBoxMin" style="display:none">
											<label id="p52_lbl_cantidad1" class="etiquetaCampo"></label>
											<input type=text id="p52_fld_cantidad1" class="input40 conAyuda"></input>
										</div>
										<div id="p52_div_cantidad2" class="textBoxMin" style="display:none">
											<label id="p52_lbl_cantidad2" class="etiquetaCampo"></label>
											<input type=text id="p52_fld_cantidad2" class="input40 conAyuda"></input>
										</div>
										<div id="p52_div_cantidad3" class="textBoxMin" style="display:none">
											<label id="p52_lbl_cantidad3" class="etiquetaCampo"></label>
											<input type=text id="p52_fld_cantidad3" class="input40 conAyuda"></input>
										</div>
									</div>
																		
									<div id="p52_div_alimentacion" style="display:none;" class="textBoxMin">
										<div id="p52_div_capMax" class="textBoxMin">
											<label id="p52_lbl_capMax" class="etiquetaCampo"><spring:message code='p52_nuevoPedidoAdicionalREF.implantacionInicial'/>:</label>
											<input type=text id="p52_fld_capMax" class="input40 conAyuda"></input>
										</div>
										<div id="p52_div_impMin" class="textBoxMin">
											<label id="p52_lbl_impMin" class="etiquetaCampo"><spring:message code='p52_nuevoPedidoAdicionalREF.implantacionFinal'/>:</label>
											<input type=text id="p52_fld_impMin" class="input40 conAyuda"></input>
										</div>
									</div>
								</div>
								<div class="p52_bloque3AreaDatosBotonera">
									<input type="button" id="p52_btn_anadir" class="boton  botonHover controlReturnP52" value='<spring:message code="p52_nuevoPedidoAdicionalREF.anadirPedidosAdicionales" />'></input>
									<input type="button" id="p52_btn_borrar" class="boton  botonHover" value='<spring:message code="p52_nuevoPedidoAdicionalREF.borrar" />'></input>
									<input type="button" id="p52_btn_guardar" class="boton  botonHover" value='<spring:message code="p52_nuevoPedidoAdicionalREF.guardar" />'></input>
									<input type="button" id="p52_btn_cancelar" class="boton  botonHover" value='<spring:message code="p52_nuevoPedidoAdicionalREF.volver" />'></input>
								</div>									
							</div>	
							<div id="p52_bloque3AreaDatosPedidosAdicionales">
								<table id="gridP52PedidosAdicionales"></table>
								<div id="pagerP52PedidosAdicionales"></div>
								<div id="p52dialog-confirm" style="display:none;" title="<spring:message code="p52_nuevoPedidoAdicionalREF.remove" />">
									<table id="p52_table_borrado">
										<tr>
											<td id="p52_td_img_borrado"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
											<td id="p52_td_borrado"><span class="p52_mensajeBorrado"></span><spring:message code="p52_nuevoPedidoAdicionalREF.removeText" /></td>
										</tr>
									</table>
								</div>
							</div>
							<div id="p52dialogCancel-confirm" style="display:none;" title="<spring:message code="p52_nuevoPedidoAdicionalREF.cancel" />">
								<table id="p52_table_canceladoborrado">
									<tr>
										<td id="p52_td_img_cancelado"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
										<td id="p52_td_cancelado"><span class="p52_mensajeCancelado"></span><spring:message code="p52_nuevoPedidoAdicionalREF.cancelText" /></td>
									</tr>
								</table>
							</div>
					</fieldset>
				</div>
			</div>	
			<input type="hidden" id="p52_fld_tieneFoto" value="N"></input>
			<input type="hidden" id="p52_campo_foco_vueltaPopUpTextil" value=""></input>
		</div> 
		<%@ include file="/WEB-INF/views/p64_popUpSelRefPlataforma.jsp" %>	
		<%@ include file="/WEB-INF/views/p65_popUpDatosPedido.jsp" %>