		<!--  Contenido página -->
		<div id="p60_AreaModificacion" title="<spring:message code="p60_modificarPedidoUnico.titulo"/>" tabindex="0">
			<div id="p60_AreaReferenciaHorizontal" class="p60_AreaReferenciaHorizontal">
				<div id="p60_AreaBloque1">
					<fieldset>
						<legend><spring:message code="p60_modificarPedidoUnico.titBloque1" /></legend>
						<div id="p60_filtroBloque1">
							<div class="textBoxMin">
								<label id="p60_lbl_tipoPedidoAdicional" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.tipoPedidoAdicional" /></label>
								<input type=text id="p60_fld_tipoPedidoAdicional" class="input140 controlReturnP60"></input>
							</div>
							<div class="textBoxMin">
								<label id="p60_lbl_referencia" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.reference" /></label>
								<input type=text id="p60_fld_referenciaVisualizada" class="input100 controlReturnP60"></input>
								<input type=hidden id="p60_fld_referencia_unitaria" ></input>
								<input type=hidden id="p60_fld_referencia" ></input>
							</div>
							<div class="textBoxMin">
								<label id="p60_lbl_denominacion" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.denominacion" /></label>
								<input type=text id="p60_fld_denominacionVisualizada" class="input225 controlReturnP60"></input>
								<input type=hidden id="p60_fld_denominacion" ></input>
							</div>
							<div class="textBoxMin">
								<label id="p60_lbl_aprov" class="etiquetaCampo" title="<spring:message code='p60_modificarPedidoUnico.tipoAprovisionamiento' />"><spring:message code="p60_modificarPedidoUnico.tipoAprovisionamientoAbrev" /></label>
								<input type=text id="p60_fld_aprov" class="input20 controlReturnP60"></input>
							</div>
						</div>
					</fieldset>
				</div>
				<div id="p60_AreaBloque2">
					<fieldset>
						<legend><spring:message code="p60_modificarPedidoUnico.titBloque2" /></legend>
						<div id="p60_filtroBloque2">
							<div id="p60_div_numeroOferta" class="textBoxMin">
								<label id="p60_lbl_numeroOferta" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.numOferta" /></label>
								<input type=text id="p60_fld_numeroOferta" class="input175 controlReturnP60"></input>
								<input type=hidden id="p60_fld_tipoOferta" ></input>
								<input type=hidden id="p60_fld_sinOferta" ></input>
							</div>
							<div class="textBoxMin">
								<label id="p60_lbl_fechaInicio" class="etiquetaCampo"></label>
								<div id="p60_fechaInicioDatePicker"></div>
							</div>								
							<div id="p60_div_fechaFin" class="textBoxMin">
								<label id="p60_lbl_fechaFin" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.fechaFin" /></label>
								<div id="p60_fechaFinDatePicker"></div>
							</div>	
							<div id="p60_filtroBloque2Excluir">
								<div id="p60_leyendaReferenciaNueva" style="display: none">
									<label id="p60_tituloLeyendaReferenciaNueva" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.referenciaNueva" /></label>
									<label id="p60_mensajeLeyendaReferenciaNueva" class="valorCampo"></label>
									<label id="p60_ReferenciaNueva" style="display: none"><spring:message code="p60_modificarPedidoUnico.mensajeReferenciaNueva" /></label>
									<label id="p60_ReferenciaNuevaBloqueo" style="display: none"><spring:message code="p60_modificarPedidoUnico.mensajeReferenciaNuevaBloqueo" /></label>
								</div>
								<div id="p60_excluir">
									<div id="p60_div_cmb_excluir" class="comboBox comboBoxExtraShort controlReturnP60">
										<label id="p60_lbl_excluir" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.excluir" /></label>
										<select id="p60_cmb_excluir"><option value="S"><spring:message code="p60_modificarPedidoUnico.si" /></option><option value="N"><spring:message code="p60_modificarPedidoUnico.no" /></option></select>
									</div>
								</div>
								<div id="p60_tratamiento" style="display: none;">
									<div id="p60_div_tratamiento" class="comboBox comboBoxShort controlReturnP60">
										<label id="p60_lbl_tratamiento" class="etiquetaCampo"><spring:message code='p60_modificarPedidoUnico.tratamiento'/></label>
										<select id="p60_cmb_tratamiento"></select>
									</div>	
								</div>		
								<div id="p60_rellenoExcluir" style="display: none">&nbsp;</div>
								<div id="p60_fechaNoDisponible" style="display: none;"><spring:message code="p60_modificarPedidoUnico.fechaNoDisponible" /></div>
								<div id="p60_encargoMensajesFechas" style="display: none;">
									<div id="p60_encargoFechasBloquedasEncargos">
										<div id="p60_encargoFechasBloquedasEncargosImg"></div>
										<label class="p60_mensajesBloqueoCalendarios"><spring:message code="p60_modificarPedidoUnico.msgFechasBloqueadasParaEncargos" /></label>
									</div>
								</div>
								<div id="p60_montajeMensajesFechas" style="display: none;">
									<div id="p60_montajeFechasBloquedasEncargos" style="display: none;">
										<div id="p60_montajeFechasBloquedasEncargosImg"></div>
										<label class="p60_mensajesBloqueoCalendarios"><spring:message code="p60_modificarPedidoUnico.msgFechasBloqueadasParaEncargos" /></label>
									</div>
									<div id="p60_montajeFechasBloquedasMontajes" style="display: none;">
										<div id="p60_montajeFechasBloquedasMontajesImg"></div>
										<label class="p60_mensajesBloqueoCalendarios"><spring:message code="p60_modificarPedidoUnico.msgFechasBloqueadasParaMontajes" /></label>
									</div>
									<div id="p60_montajeFechasBloquedasMantenimiento" style="display: none;">
										<div id="p60_montajeFechasBloquedasMantenimientoImg"></div>
										<label class="p60_mensajesBloqueoCalendarios"><spring:message code="p60_modificarPedidoUnico.msgFechasBloqueadasParaMantenimiento" /></label>
									</div>
								</div>
							</div>
						</div>
						
					</fieldset>
				</div>
				<div id="p60_AreaBloque3">
					<fieldset> 
						<legend><spring:message code="p60_modificarPedidoUnico.titBloque3" /></legend>
							<div class="p60_bloque3AreaDatos">
								<div class="p60_bloque3AreaDatosCampos">
									<div id="p60_bloque3AreaDatosCampos1Linea" class="p60_bloque3AreaDatosCampos1Linea" >
										<div id="p60_bloque3AreaDatosCampos1_1" class="p60_bloque3AreaDatosCampos1">
											<div id="p60_div_cmb_cajas" class="comboBox comboBoxExtraShort controlReturnP60">
												<label id="p60_lbl_cajas" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.cajas" /></label>
												<select id="p60_cmb_cajas"><option value="S"><spring:message code="p60_modificarPedidoUnico.si" /></option><option value="N"><spring:message code="p60_modificarPedidoUnico.no" /></option></select>
											</div>
										</div>
										<div id="p60_bloque3AreaDatosCampos1_2" class="p60_bloque3AreaDatosCampos1">
											<div class="textBox">
												<label id="p60_lbl_stock" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.stock" /></label>
												<input type=text id="p60_fld_stock" class="input40 controlReturnP60"></input>
											</div>
										</div>
										<div id="p60_bloque3AreaDatosCampos1_3" class="p60_bloque3AreaDatosCampos1">
											<div class="textBox">
												<label id="p60_lbl_uc" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.uc" /></label>
												<input type=text id="p60_fld_uc" class="input40 controlReturnP60"></input>
											</div>
										</div>
									</div>
									<div id="p60_bloqueFrescos" class="p60_bloque3AreaDatosCampos1Linea" >
										<div id="p60_bloqueFrescosCampoMax" class="p60_bloque3AreaDatosCampos1">
											<div class="textBox">
												<label id="p60_lbl_min" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.min" /></label>
												<input type=text id="p60_fld_min" class="input40 controlReturnP60"></input>
											</div>
										</div>
										<div id="p60_bloqueFrescosCampoMin" class="p60_bloque3AreaDatosCampos1">
											<div class="textBox">
												<label id="p60_lbl_max" class="etiquetaCampo"><spring:message code="p60_modificarPedidoUnico.max" /></label>
												<input type=text id="p60_fld_max" class="input40 controlReturnP60"></input>
											</div>
											
										</div>
									</div>
									<div id="p60_bloque3AreaDatosCampos2Linea" class="p60_bloque3AreaDatosCampos2Linea" >
										<div id="p60_bloque3AreaDatosCampos2_1" class="p60_bloque3AreaDatosCampos1">
											<div class="textBox">
												<label id="p60_lbl_cantidad1" class="etiquetaCampo"></label>
												<input type=text id="p60_fld_cantidad1" class="input40 controlReturnP60"></input>
												<input type=hidden id="p60_fld_cantidad1_ant" ></input>
											</div>										
										</div>	
										<div id="p60_bloque3AreaDatosCampos2_2" class="p60_bloque3AreaDatosCampos1">
											<div id="p60_div_cantidad2" class="textBox">
												<label id="p60_lbl_cantidad2" class="etiquetaCampo"></label>
												<input type=text id="p60_fld_cantidad2" class="input40 controlReturnP60"></input>
											</div>
										</div>	
										<div id="p60_bloque3AreaDatosCampos2_3" class="p60_bloque3AreaDatosCampos1">
											<div id="p60_div_cantidad3" class="textBox">
												<label id="p60_lbl_cantidad3" class="etiquetaCampo"></label>
												<input type=text id="p60_fld_cantidad3" class="input40 controlReturnP60"></input>
											</div>
										</div>	
									</div>
									<div id="p60_bloque3AreaDatosCampos3Linea" class="p60_bloque3AreaDatosCampos2Linea" >
										<div id="p60_bloque3AreaDatosCampos2_4" class="p60_bloque3AreaDatosCampos1">
											<div id="p60_div_cantidad4" class="textBox">
												<label id="p60_lbl_cantidad4" class="etiquetaCampo"></label>
												<input type=text id="p60_fld_cantidad4" class="input40 controlReturnP60"></input>
											</div>
										</div>	
										<div id="p60_bloque3AreaDatosCampos2_5" class="p60_bloque3AreaDatosCampos1">
											<div id="p60_div_cantidad5" class="textBox">
												<label id="p60_lbl_cantidad5" class="etiquetaCampo"></label>
												<input type=text id="p60_fld_cantidad5" class="input40 controlReturnP60"></input>
											</div>
										</div>	
									</div>
									
									<div id="p60_div_fechaHasta">
										<label id="p60_lbl_fechaHasta" class="etiquetaCampo"></label>
									</div>
								</div>
								<div class="p60_bloque3AreaDatosAyuda">
									<%@ include file="/WEB-INF/views/p53_ayuda.jsp" %>
								</div>
							</div>
					</fieldset>
				</div>
			</div>	
		</div> 
