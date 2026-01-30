		<!--  Contenido página -->
		<div id="p62_AreaModificacion" title="<spring:message code="p62_modificarPedidoEncargoCliente.titulo"/>" tabindex="0">
			<div id="p62_AreaReferenciaHorizontal" class="p62_AreaReferenciaHorizontal">
				<div id="p62_AreaBloque1">
					<fieldset>
						<legend><spring:message code="p62_modificarPedidoEncargoCliente.titBloque1" /></legend>
						<div id="p62_bloque1">
							<div class="textBoxMin">
								<label id="p62_lbl_tipoPedidoAdicional" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.tipoPedidoAdicional" /></label>
								<input type=text id="p62_fld_tipoPedidoAdicional" class="input140" value="<spring:message code="p62_modificarPedidoEncargoCliente.encargoCliente"/>"></input>
							</div>
							<div class="textBoxMin">
								<label id="p62_lbl_referencia" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.reference" /></label>
								<input type=text id="p62_fld_referenciaFormateada" class="input100"></input>
								<input type=hidden id="p62_fld_referencia" ></input>
								<input type=hidden id="p62_fld_generica" ></input>
							</div>
							<div class="textBoxMin">
								<label id="p62_lbl_denominacion" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.denominacion" /></label>
								<input type=text id="p62_fld_denominacion" class="input225"></input>
							</div>
							<div class="textBoxMin">
								<label id="p62_lbl_aprov" class="etiquetaCampo" title="<spring:message code='p62_modificarPedidoEncargoCliente.tipoAprovisionamiento' />"><spring:message code="p62_modificarPedidoEncargoCliente.tipoAprovisionamientoAbrev" /></label>
								<input type=text id="p62_fld_aprov" class="input20"></input>
							</div>
						</div>
					</fieldset>
				</div>
				<div id="p62_AreaBloque2">
					<fieldset> 
						<legend><spring:message code="p62_modificarPedidoEncargoCliente.titBloque2" /></legend>
							<div id="p62_bloque2">
								<div class="p62_bloque2AreaDatosCampos">
									<div id="p62_bloque2AreaDatosCampos1Linea" class="p62_bloque2AreaDatosCampos1Linea" >
										<div class="textBox">
											<label id="p62_lbl_uc" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.uc" /></label>
											<input type=text id="p62_fld_uc" class="input40"></input>
										</div>
										<div class="textBox">
											<label id="p62_lbl_cantidad" class="etiquetaCampo"></label>
											<input type=text id="p62_fld_cantidad" class="input40"></input>
										</div>		
										<div id="p62_AreaBloqueEspecificacion" style="display: inline-block;">								
											<div class="textBoxMin">
												<label id="p62_lbl_pesoDesde" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.pesoDe" /></label>
												<input type=text id="p62_fld_pesoDesde" class="input40"></input>
											</div>
											<div class="textBox">
												<label id="p62_lbl_pesoHasta" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.pesoA" /></label>
												<input type=text id="p62_fld_pesoHasta" class="input40"></input>
											</div>
											<div class="textBox">
												<label id="p62_lbl_especificacion" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.descripcion" /></label>
												<input type=text id="p62_fld_especificacion" class="input300"></input>
											</div>
										</div>
										<div id="p62_AreaBloqueNoEspecificacion" style="display: inline-block;">
											<spring:message code="p62_modificarPedidoEncargoCliente.referenciaSinEspecificaciones" />
										</div>
									</div>
								</div>
							</div>
					</fieldset>
				</div>				


<!-- p62_AreaBloque3 -->
				<div id="p62_AreaBloque3">
						<div id="p62_bloque3">
							<div id="p62_bloque3EncargoClienteFecha">
								<fieldset>
								<legend><spring:message code="p62_modificarPedidoEncargoCliente.titBloque3_1" /></legend>
									<div id="p62_div_fechaEntregaEncCli" class="textBoxMin">
										<div id="p62_mensajesFechas">
											<div id="p62_mensajeFechaEntrega">
												<div id="p62_mensajeFechaEntregaImg"></div>
												<label class="p62_mensajeFechaEntregaLabel"><spring:message code="p62_modificarPedidoEncargoCliente.fechaEntrega" /></label>
											</div>
											<div id="p62_mensajeNuevaFechaEntrega">
												<div id="p62_mensajeNuevaFechaEntregaImg"></div>
												<label class="p62_mensajeNuevaFechaEntregaLabel"><spring:message code="p62_modificarPedidoEncargoCliente.nuevaFechaEntrega"/></label>
											</div>
										</div>						
										<div id="p62_fechaEntregaEncCliDatePicker" class="p62_datepicker"></div>
										<input type="hidden" id="encargoClienteEspecialCalendario" value=""></input>
										<input type="hidden" id="encargoClientePrimFechaEntregaCalendario" value=""></input>
										<input type="hidden" id="encargoClienteUnidadesPedirCalendario" value=""></input>
										<input type="hidden" id="encargoClienteFechaVentaModificadaCalendario" value=""></input>
									</div>
								</fieldset>									
							</div>
							<div id="p62_bloque3EncargoClienteTCC">
								<div id="p62_bloque3EncargoClienteTexto" >
								</div>
							
								<div id="p62_bloque3EncargoClienteCliente">
									<fieldset id="p62_estructuraClienteFieldset"><legend id="p62_estructuraClienteLegend"><spring:message code="p62_modificarPedidoEncargoCliente.titBloque3_3" /></legend>
										<div id="p62_AreaEstructuraCliente">
											<div id="p62_clienteNombre_div" class="p62_clienteLabelDatoEstructura">
												<div id="p62_clienteNombre_label" class="p62_clienteLabelEstructura">
													<label id="p62_lbl_clienteNombre" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.clienteNombre" /></label>
												</div>	
												<div id="p62_clienteNombre_dato" class="p62_clienteDatoEstructura">
													<input class="input200" type=text id="p62_fld_clienteNombre"></input>
												</div>	
											</div>
											<div id="p62_clientePrimerApellido_div" class="p62_clienteLabelDatoEstructura">
												<div id="p62_clienteApellido_label" class="p62_clienteLabelEstructura">
													<label id="p62_lbl_clienteApellido" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.clientePrimerApellido" /></label>
												</div>	
												<div id="p62_clienteApellido_dato" class="p62_clienteDatoEstructura">
													<input class="input200" type=text id="p62_fld_clienteApellido"></input>
												</div>	
											</div>
											<div id="p62_clienteTelefono_div" class="p62_clienteLabelDatoEstructura">
												<div id="p62_clienteTelefono_label" class="p62_clienteLabelEstructura">
													<label id="p62_lbl_clienteTelefono" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.clienteTelefono" /></label>
												</div>	
												<div id="p62_clienteTelefono_dato" class="p62_clienteDatoEstructura">
													<input class="input150" type=text id="p62_fld_clienteTelefono"></input>
												</div>	
											</div>
										</div>
									</fieldset>	
								</div>
							
								<div id="p62_bloque3EncargoClienteCentro">
									<fieldset id="p62_estructuraCentroFieldset"><legend id="p62_estructuraCentroLegend"><spring:message code="p62_modificarPedidoEncargoCliente.titBloque3_2" /></legend>
										<div id="p62_AreaEstructuraCentro">
											<div id="p62_centroContacto_div" class="p62_centroLabelDatoEstructura">
												<div id="p62_centroContacto_label" class="p62_centroLabelEstructura">
													<label id="p62_lbl_centroContacto" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.centroContacto" /></label>
												</div>	
												<div id="p62_centroContacto_dato" class="p62_centroDatoEstructura">
													<input class="input200" type=text id="p62_fld_centroContacto"></input>
												</div>	
											</div>
										</div>
									</fieldset>	
								</div>	
							
							</div>
							
							
						</div>
				</div>


<!-- p62_AreaBloque4 -->

				<div id="p62_AreaBloque4">
					<fieldset id="p62_AreaBloque4Fieldset">
						<legend><spring:message code="p62_modificarPedidoEncargoCliente.titBloque4" /></legend>
						<div id="p62_bloque4_1">
							<div id="p62_bloque4_1_1">
								<div id="p62_bloque4_1_1_1">
									<spring:message code="p62_modificarPedidoEncargoCliente.estadoPendiente" />
								</div>
								<div id="p62_bloque4_1_1_2">
 									<spring:message code="p62_modificarPedidoEncargoCliente.estadoEnTramite" />
								</div>						
								<div id="p62_bloque4_1_1_3">
									<spring:message code="p62_modificarPedidoEncargoCliente.estadoConfirmada" />
								</div>						
							</div>			
							<div id="p62_bloque4_1_2">
								<div id="p62_bloque4_1_2_1">
									<div id="p62_localizadorPrecio">
										<div id="p62_localizador_div" class="p62_localizadoPrecioLabelDatoEstructura">
											<div id="p62_localizador_label" class="p62_localizadorPrecioLabelEstructura">
												<label id="p62_lbl_localizador" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.localizador" /></label>
											</div>	
											<div id="p62_localizador_dato" class="p62_localizadoPrecioDatoEstructura">
												<input class="input200" type=text id="p62_fld_localizador"></input>
											</div>	
										</div>
										<div id="p62_precio_div" class="p62_localizadorPrecioLabelDatoEstructura">
											<div id="p62_precio_label" class="p62_localizadorPrecioLabelEstructura">
												<label id="p62_lbl_precio" class="etiquetaCampo"><spring:message code="p62_modificarPedidoEncargoCliente.precioEuros" /></label>
											</div>	
											<div id="p62_precio_dato" class="p62_localizadoPrecioDatoEstructura">
												<input class="input200" type=text id="p62_fld_precio"></input>
											</div>	
										</div>
									</div>
								</div>
								<div id="p62_bloque4_1_2_2">
									<spring:message code="p62_modificarPedidoEncargoCliente.estadoNoServido" />
								</div>						
														
							</div>
						</div>
						<div id="p62_bloque4_2">
							<fieldset id="p62_mensajeFieldsetPendiente">
								<div class="p62_divMensajePendiente">
									<p class="p62_parrafoMensajePendiente"><spring:message code="p62_modificarPedidoEncargoCliente.mensajePendiente"/></p>
								</div>
							</fieldset>
							<fieldset id="p62_mensajeFieldsetEnCurso">
								<div class="p62_divMensajeEnCurso">
									<p class="p62_parrafoMensajeEnCurso"><spring:message code="p62_modificarPedidoEncargoCliente.mensajeEnCurso"/></p>
								</div>
							</fieldset>	
							<fieldset id="p62_mensajeFieldsetConfirmado">
								<div class="p62_divMensajeConfirmado">
									<p class="p62_parrafoMensajeConfirmado"><spring:message code="p62_modificarPedidoEncargoCliente.mensajeConfirmado"/></p>
								</div>
							</fieldset>	
							<fieldset id="p62_mensajeFieldsetConfirmadoNsr">
								<div class="p62_divMensajeConfirmadoNsr">
									<p class="p62_parrafoMensajeConfirmadoNsr"><spring:message code="p62_modificarPedidoEncargoCliente.mensajeConfirmadoNsr"/></p>
								</div>
							</fieldset>	
							<fieldset id="p62_mensajeFieldsetNsr">
								<div class="p62_divMensajeNsr">
									<p class="p62_parrafoMensajeNsr"><spring:message code="p62_modificarPedidoEncargoCliente.mensajeNsr"/></p>
								</div>
							</fieldset>	
						</div>
					</fieldset>
				</div>

			</div>	
		</div> 
