<script src="./misumi/scripts/p15ReferenciasCentroIC.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VOferta.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/calendario.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/HistoricoUnidadesVenta.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/model/ImagenComercial.js?version=${misumiVersion}" type="text/javascript"></script>

		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p15_AreaVentasVertical" >			
				<div id="p15_AreaVentasMedia">
					<div id="p15_ventasMediaEstructura">
						<div class="p15_ventasMediaEstructuraField" >
							<div id="p15_ventasMediaTabla">
								<fieldset><legend><spring:message code="p15_referenciasCentroIC.ventasMedias" /> <!-- - <span id="p15_ventasMediaTipoVenta"></span>--></legend>
									<table id="p15_ventasMediaTablaEstructura" border="1" class="ui-widget">
										<thead class="ui-widget-header">
										    <tr>
										        <th class="p15_ventasMediaTablaTh"><spring:message code="p15_referenciasCentroIC.tarifa" /></th>
										        <th class="p15_ventasMediaTablaTh"><spring:message code="p15_referenciasCentroIC.competencia" /></th>
										        <th class="p15_ventasMediaTablaTh"><spring:message code="p15_referenciasCentroIC.oferta" /></th>
										        <th class="p15_ventasMediaTablaTh"><spring:message code="p15_referenciasCentroIC.anticipada" /></th>
										        <th class="p15_ventasMediaTablaTh"><spring:message code="p15_referenciasCentroIC.ventaMediaTotal" /></th>
										    </tr>
										</thead>
										<tbody class="ui-widget-content">
										    <tr> 													<td id="p15_ventasMediaTdTarifa" class="p15_ventasMediaTablaTd"></td>
										        <td id="p15_ventasMediaTdCompetencia" class="p15_ventasMediaTablaTd"></td>
										        <td id="p15_ventasMediaTdOferta" class="p15_ventasMediaTablaTd"></td>
										        <td id="p15_ventasMediaTdAnticipada" class="p15_ventasMediaTablaTd"></td>
										        <td id="p15_ventasMediaTdMedia" class="p15_ventasMediaTablaTd"></td>
										    </tr>
										</tbody>
									</table>
								</fieldset>	
							</div>
						</div>
					</div>	
				</div>
				
				<div id="p15_AreaVentasUltimoMes">
					<fieldset>
						<legend>
							<spring:message code="p15_AreaVentasUltimoMes.leyenda" />
						</legend>
						<div id="p15_ventasUltimoMesEstructura">
							<div class="p15_ventasUltimoMesEstructuraField">
								<div id="p15_ventasUltimoMesTabla">
									<div id="p15_tituloTabla">
										<div id="p15_mes">
											<label id="p53_lbl_mesVentasEnUltimasOfertas"
												class="etiquetaCampo"> <spring:message
													code="p15_AreaVentasUltimoMes.mes" />
											</label>
										</div>
										<div id="p15_mesAnio" class="comboBox comboBoxShortLarge">
											<select id="p15_cmb_mesAnio"></select>
										</div>
									</div>
									<div id="p15_tablaCalendario">
										<table id="p15_ventasUltimasOfertasTablaEstructura" border="1"
											class="ui-widget">
											<thead class="ui-widget-header">
												<tr>
													<th class="ventasUltimasOfertasTablaTh"><spring:message
															code="p15_referenciasCentroIC.lunesAbrev" /></th>
													<th class="ventasUltimasOfertasTablaTh"><spring:message
															code="p15_referenciasCentroIC.martesAbrev" /></th>
													<th class="ventasUltimasOfertasTablaTh"><spring:message
															code="p15_referenciasCentroIC.miercolesAbrev" /></th>
													<th class="ventasUltimasOfertasTablaTh"><spring:message
															code="p15_referenciasCentroIC.juevesAbrev" /></th>
													<th class="ventasUltimasOfertasTablaTh"><spring:message
															code="p15_referenciasCentroIC.viernesAbrev" /></th>
													<th class="ventasUltimasOfertasTablaTh"><spring:message
															code="p15_referenciasCentroIC.sabadoAbrev" /></th>
													<th class="ventasUltimasOfertasTablaTh"><spring:message
															code="p15_referenciasCentroIC.domingoAbrev" /></th>
												</tr>
											</thead>
											<tbody class="ui-widget-content">
												<tr>
													<td id="p15_ventasUltimasOfertasDiaTd11" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd12" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd13" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd14" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd15" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd16" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd17" class="tdDay">&nbsp;</td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasCantidadMesTd11"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd12"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd13"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd14"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd15"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd16"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd17"
														class="ventasUltimasOfertasTablaTd"></td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasDiaTd21" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd22" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd23" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd24" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd25" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd26" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd27" class="tdDay">&nbsp;</td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasCantidadMesTd21"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd22"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd23"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd24"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd25"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd26"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd27"
														class="ventasUltimasOfertasTablaTd"></td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasDiaTd31" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd32" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd33" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd34" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd35" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd36" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd37" class="tdDay">&nbsp;</td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasCantidadMesTd31"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd32"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd33"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd34"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd35"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd36"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd37"
														class="ventasUltimasOfertasTablaTd"></td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasDiaTd41" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd42" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd43" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd44" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd45" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd46" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd47" class="tdDay">&nbsp;</td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasCantidadMesTd41"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd42"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd43"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd44"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd45"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd46"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd47"
														class="ventasUltimasOfertasTablaTd"></td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasDiaTd51" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd52" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd53" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd54" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd55" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd56" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd57" class="tdDay">&nbsp;</td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasCantidadMesTd51"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd52"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd53"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd54"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd55"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd56"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd57"
														class="ventasUltimasOfertasTablaTd"></td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasDiaTd61" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd62" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd63" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd64" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd65" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd66" class="tdDay">&nbsp;</td>
													<td id="p15_ventasUltimasOfertasDiaTd67" class="tdDay">&nbsp;</td>
												</tr>
												<tr>
													<td id="p15_ventasUltimasOfertasCantidadMesTd61"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd62"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd63"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd64"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd65"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd66"
														class="ventasUltimasOfertasTablaTd"></td>
													<td id="p15_ventasUltimasOfertasCantidadMesTd67"
														class="ventasUltimasOfertasTablaTd"></td>
												</tr>
											</tbody>
										</table>
									</div>
									<div id="p15_tablaCalendarioHidden">
										<input id="p15_ventasUltimasOfertasCantidadMesFechaTd11"
											value=" " type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd12" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd13" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd14" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd15" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd16" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd17" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd21" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd22" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd23" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd24" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd25" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd26" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd27" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd31" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd32" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd33" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd34" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd35" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd36" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd37" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd41" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd42" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd43" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd44" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd45" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd46" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd47" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd51" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd52" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd53" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd54" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd55" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd56" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd57" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd61" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd62" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd63" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd64" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd65" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd66" value=" "
											type="hidden"> <input
											id="p15_ventasUltimasOfertasCantidadMesFechaTd67" value=" "
											type="hidden">
									</div>
									<div id="p15_ventasUltimasOfertasTablaMensajePeriodoEnOferta">
										<div id="p15_ventasUltimoMesTablaMensajeOfertasTexto">
											<span class="ofertaDay"><spring:message
													code="p15_referenciasCentroIC.diasVentaEnOfertaAzul" /></span>
										</div>
										<div id="p15_ventasUltimoMesTablaMensajeAnticipadasTexto">
											<span class="ventaAnticipadaDay"><spring:message
													code="p15_referenciasCentroIC.diasVentaAnticipadaEnRojo" /></span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
			<div id="p15_AreaStockFinalMinimo" style="display: none;">
				<div id="p15_stockFinalMinimoEstructura">
					<div class="p15_stockFinalMinimoEstructuraField" >
						<div id="p15_stockFinalMinimoTabla">
							<fieldset><legend><spring:message code="p15_referenciasCentroIC.stockFinalMinimo" /></legend>
							<div class="p15_EstructuraLinealBloque1" style="display:none;">
								<div class="p15_divImplantacion">
									<p id="p15_ParrafoImplantacionFac" class="p15_ParrafoImplantacion"></p>
								</div>
							</div>
								<table id="p15_stockFinalMinimoTablaEstructura" border="1" class="ui-widget">
									<thead class="ui-widget-header">
									    <tr>
									        <th class="p15_stockFinalMinimoTablaThEtiqueta">&nbsp;</th>
									        <th class="p15_stockFinalMinimoTablaTh"><spring:message code="p15_referenciasCentroIC.SFM" /></th>
									    </tr>
									</thead>
									<tbody class="ui-widget-content">
									    <tr>
									        <td id="p15_stockFinalMinimoUnidadesTdEtiqueta" class="p15_stockFinalMinimoTablaTdEtiqueta"><spring:message code="p15_referenciasCentroIC.unidades" /></td>
									        <td id="p15_stockFinalMinimoUnidadesTdS" class="p15_stockFinalMinimoTablaTd"></td>
									    </tr>
									    <tr>
									        <td id="p15_stockFinalMinimoDiasVentaTdEtiqueta" class="p15_stockFinalMinimoTablaTdEtiqueta"><spring:message code="p15_referenciasCentroIC.diasDeVenta" /></td>
									        <td id="p15_stockFinalMinimoDiasVentaTdS" class="p15_stockFinalMinimoTablaTd"></td>
									    </tr>
									</tbody>
								</table>
								<div id="p15_stockFinalMinimoMensaje" style="display: none;">
									<span id="p15_stockFinalMinimoMensajeTexto1"><spring:message code="p15_referenciasCentroIC.IMPORTANTE" />:</span>
									<span id="p15_stockFinalMinimoMensajeTexto2"></span>
								</div>
							</fieldset>	
							
						</div>
						
					</div>
				</div>	
			</div>
			
			<div id="p15_AreaFacing" style="display: none;">
				<div id="p15_facingEstructura">
					<div class="p15_facingEstructuraField" >
						<div id="p15_facingTabla">
							<fieldset><legend><spring:message code="p15_referenciasCentroIC.FacingCentro" /></legend>
								<table id="p15_facingTablaEstructura" border="1" class="ui-widget">
									<thead class="ui-widget-header">
									    <tr>
									        <th class="p15_facingTablaThEtiqueta">&nbsp;</th>
									        <th class="p15_facingTablaTh"><spring:message code="p15_referenciasCentroIC.unid" /></th>
									    </tr>
									</thead>
									<tbody class="ui-widget-content">
									    <tr>
									        <td id="p15_facingCentroTdEtiqueta" class="p15_facingTablaTdEtiqueta"><spring:message code="p15_referenciasCentroIC.FacingCentro" /></td>
									        <td id="p15_facingCentroTdS" class="p15_facingTablaTd"></td>
									    </tr>
									    <tr id="p15_tr_facing_previo"  style="display: none;">
									        <td id="p15_facingPrevioTdEtiqueta" class="p15_facingTablaTdEtiqueta"><spring:message code="p15_referenciasCentroIC.FacingPrevio" /></td>
									        <td id="p15_facingPrevioTdS" class="p15_facingTablaTd"></td>
									    </tr>
									</tbody>
								</table>
								<div id="p15_facingMensaje" style="display: none;">
									<span id="p15_facingMensajeTexto1"><spring:message code="p15_referenciasCentroIC.IMPORTANTE" />:</span>
									<span id="p15_facingMensajeTexto2"></span>
								</div>
								
							</fieldset>	
						</div>
					</div>
				</div>	
			</div>
			
			<!--   <div id="p15_AreaPlanogramasVentasHorizontal"> -->
				<div id="p15_AreaPlanogramas" style="display: none;">
					<div id="p15_planogramasEstructura">
						<div class="p15_planogramasEstructuraField" >
							<fieldset><legend><spring:message code="p15_referenciasCentroIC.planogramas" /></legend>
								<div class="p15_planogramasDescripcion">
									<input type=text id="p15_fld_descripcionPlanograma" class="p15_planogramasDes" value="Descripción del planograma"></input>
									<input type=text id="p15_fld_tipoPlanograma" class="p15_planogramasTipo" value=""></input>
								</div>
								<div class="p15_planogramasEstructuraLinealField">
									<fieldset>
										<legend><spring:message code="p15_referenciasCentroIC.lineal"/></legend>
										
										<div class="p15_EstructuraLinealBloque1" style="display:none;">
											<div class="p15_divImplantacion">
												<p id="p15_ParrafoImplantacion" class="p15_ParrafoImplantacion"></p>
											</div>
										</div>
										<div class="p15_EstructuraLinealBloque2" style="display:none;">
											<div class="textBoxFieldSet">
												<label id="p15_lbl_linealCapacidad" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.capacidad" /></label>
												<input type=text id="p15_fld_linealCapacidad" class="input50"></input>
											</div>
											<div class="textBoxFieldSet">
												<label id="p15_lbl_linealFacing" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.facing" /></label>
												<input type=text id="p15_fld_linealFacing" class="input50"></input>
											</div>
											<div id="p15_tb_fecha" class="textBoxFieldSet">
												<label id="p15_lbl_linealFecha" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.fecha" /></label>
												<input type=text id="p15_fld_linealFecha" class="input100"></input>
											</div>
										</div>
										<div id="p15_referenciasCentroImagenComercialMensajeError" style="display:none;">
											<span id="p15_referenciasCentroImagenComercialMensajeErrorTexto"><spring:message code="p15_Vegalsa_referenciasCentro.errorWSFacingVegalsa" /></span>
										</div>
										<div class="p15_EstructuraLinealBloque2FacingX" style="display:none;">											
											<label id="p15_lbl_desgloseEnUnidadesTitle" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.enUnidades" /></label>																				
											<div id="p15_EstructuraLinealBloque2_1FacingX">
												<div id="p15_EstructuraLinealBloque2_1_1FacingX">
													<div class="textBoxFieldSet" id="p15_EstructuraLinealBloque2_1_1FacingX_capacidad">
														<label id="p15_lbl_linealCapacidadFacingX" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.capacidad" /></label>
														<input type=text id="p15_fld_linealCapacidadFacingX" class="input50"></input>
													</div>
													<div class="textBoxFieldSet" id="p15_EstructuraLinealBloque2_1_1FacingX_facing">
														<label id="p15_lbl_linealFacingFacingX" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.facing" /></label>
														<input type=text id="p15_fld_linealFacingFacingX" maxlength="3" class="input50"></input>
													</div>
												</div>
												<div id="p15_EstructuraLinealBloque2_1_2FacingX">
													<div class="textBoxFieldSet" id="p15_linealMultiplicadorFacingXDiv">
														<label id="p15_lbl_linealMultiplicadorFacingX" class="etiquetaCampo"></label>
														<input type=text id="p15_fld_linealMultiplicadorFacingX" class="input50"></input>
													</div>
												</div>
											</div>
											
											<div class="textBoxFieldSet" id="p15_linealImagenLLaveFacingXDiv">
												<img src="./misumi/images/llave-50-gris.gif?version=${misumiVersion}" class="p15_fld_linealImagenLLave">
					 							<c:if test="${user.perfil != 3}">
													<div id="p15_guardarCambiosFacingVegalsaDiv" style="display:inline-block;vertical-align:top;">
														<img id="p15_btn_guardarFacingVegalsa" src="./misumi/images/floppy_30.gif?version=20190712" style="margin-top:8px">
													</div>
												</c:if>												
											</div>
											<div class="textBoxFieldSet" id="p15_lbl_linealImcFacingXDiv" style="display:none;">
												<label id="p15_lbl_linealImcFacingX" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.imc" /></label>
											</div>
											<div class="textBoxFieldSet" id="p15_fld_linealImcFacingXDiv" style="display:none;">
												<input type=text id="p15_fld_linealImcFacingX" class="input40"></input>
											</div>
											<div id="p15_tb_fechaFacingX" class="textBoxFieldSet" style="display:none;">
												<input type=text id="p15_fld_linealFechaFacingX" class="input70"></input>
											</div>											
											<div id="p15_datosOriginales" style="display:none">
												<input type="hidden" id="p15_capacidadOrig" value=''/>
												<input type="hidden" id="p15_facingOrig" value=''/>
												<input type="hidden" id="p15_uc" value=''/>
												<input type="hidden" id="p15_altoOrig" value=''/>
												<input type="hidden" id="p15_anchoOrig" value=''/>
											</div>
										</div>
										<div class="p15_EstructuraLinealBloque3" style="display: none;"> <!-- Estructura facing pura -->
											<div id="p15_EstructuraLinealFacingPrevio" style="display:none" class="textBoxFieldSet">
												<label id="p15_lbl_linealFacingPrevio" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.FacingPrevio" /></label>
												<input type=text id="p15_fld_linealFacingPrevio" class="input50"></input>
											</div>
											<div id="p15_EstructuraLinealBloque3_1FacingPuro">
												<div id="p15_EstructuraLinealBloque3_1_1FacingPuro">
													<div class="textBoxFieldSet" id="p15_EstructuraLinealBloque3_1_1FacingPuro_facing">
														<label id="p15_lbl_linealFacingPuro" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.facing" /></label>
														<input type=text id="p15_fld_linealFacingPuro" class="input50"></input>
													</div>						
													<div class="textBoxFieldSet" id="p15_linealMultiplicadorFacingPuroDiv">
														<label id="p15_lbl_linealMultiplicadorFacingPuro" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.multiplicador" /></label>
														<input type=text id="p15_fld_linealMultiplicadorFacingPuro" class="input50"></input>
													</div>
												</div>
											</div>
											<div class="textBoxFieldSet" id="p15_linealImagenLLaveFacingPuroDiv">
												<img src="./misumi/images/llave-50-gris.gif?version=${misumiVersion}" class="p15_fld_linealImagenLLave">
											</div>
											<div class="textBoxFieldSet" id="p15_lbl_linealImcFacingPuroDiv">
												<label id="p15_lbl_linealImcFacingPuro" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.imc" /></label>
											</div>
											<div class="textBoxFieldSet" id="p15_fld_linealImcFacingPuroDiv">
												<input type=text id="p15_fld_linealImcFacingPuro" class="input40"></input>
											</div>

											<div id="p15_tb_fechaFacingPuro" style="display:none" class="textBoxFieldSet">
												<label id="p15_lbl_linealFechaBloque3" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.fecha" /></label>
												<input type=text id="p15_fld_linealFechaBloque3" class="input70"></input>
											</div>
										</div>
										<div id="p15_EstructuraLinealBloqueDesgloseFacing" style="display:none">
											<label id="p15_lbl_desgloseFacingTitle" class="etiquetaCampo"></label>
											<label id="p15_lbl_guardado" style="visibility:hidden" class="etiquetaCampo"></label>
											<div id="p15_lblImg_desgloseFacing">
												<div id="p15_desgloseFacingAlto">
													<label id="p15_lbl_desgloseFacingAlto"><spring:message code="p15_referenciasCentroIC.desgloseFacingAlto" /></label>	
													<label id="p15_lbl_desgloseFacingValorAlto"></label>
													<input id="p15_lbl_desgloseFacingValorAltoInput" type="text" maxlength="3" class="input15">											
												</div>
												<div id="p15_desgloseFacingImg">
													<img id="p15_img_desgloseFacingPale" src="./misumi/images/pale.png?version=" style="vertical-align: middle;">
													<img id="p15_img_desgloseFacingCajaExpositora" src="./misumi/images/cajasExpositoras.png?version=" style="vertical-align: middle;">
													<img id="p15_img_desgloseFacingCaja" src="./misumi/images/caja.png?version=" style="vertical-align: middle;">
												</div>
												<div id="p15_guardarCambios">
													<input type="text" id="p15_fld_planogramado" class="input100" disabled="disabled" value="<spring:message code="p15_referenciasCentroIC.planogramado" />">
													<img id="p15_btn_guardadoPlanograma" src="./misumi/images/floppy_30.gif?version=20190712" style="vertical-align: middle;">									
												</div>
												<div id="p15_desgloseFacingMul">
													<label id="p15_lbl_desgloseFacingMultiplicador"><spring:message code="p15_referenciasCentroIC.desgloseFacingMultiplicador" /></label>	
													<label id="p15_lbl_desgloseFacingValorMultiplicador"></label>
													<input id="p15_lbl_desgloseFacingValorMultiplicadorInput" type="text" class="input15">	
												</div>
											</div>
											<div id="p15_desgloseFacingAncho">
												<label id="p15_lbl_desgloseFacingAncho"><spring:message code="p15_referenciasCentroIC.desgloseFacingAncho" /></label>		
												<label id="p15_lbl_desgloseFacingValorAncho"></label>
												<input id="p15_lbl_desgloseFacingValorAnchoInput" type="text"  maxlength="3" class="input15">	
											</div>
										</div>
										<div id="p15_EstructuraLinealBloqueFacingVegalsa" style="display:none">
											<label id="p15_lbl_guardadoVegalsa" style="visibility:hidden" class="etiquetaCampo"></label>
										</div>
										
									</fieldset>
								</div>
								<div class="p15_planogramasEstructuraMontaje1Field">	
									<fieldset><legend><spring:message code="p15_referenciasCentroIC.montaje1" /></legend>
										<div>
											<div class="textBoxFieldSet" id="p15_lbl_montaje1CapacidadDiv">
												<label id="p15_lbl_montaje1Capacidad" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.capacidad" /></label>
												<input type=text id="p15_fld_montaje1Capacidad" class="input50"></input>
											</div>
											<div class="textBoxFieldSet campoNoVegalsa" id="p15_lbl_montaje1FacingDiv">
												<label id="p15_lbl_montaje1Facing" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.facing" /></label>
												<input type=text id="p15_fld_montaje1Facing" class="input50"></input>
											</div>
											<div class="textBoxFieldSet campoNoVegalsa">
												<input id="p15_chk_montaje1Cabecera" type="checkbox" class=""/>
												<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.cabecera" /></label>
											</div>
											<div class="textBoxFieldSet campoNoVegalsa">
												<input id="p15_chk_montaje1Oferta" type="checkbox" class=""/>
												<label id="p15_lbl_montaje1Oferta" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.oferta" /></label>
											</div>
											<div class="textBoxFieldSet campoNoVegalsa">
												<input id="p15_chk_montaje1Campana" type="checkbox" class=""/>
												<label id="p15_lbl_montaje1Campana" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.campana" /></label>
											</div>
										</div>
										<div id="p15_vegalsa_div">
											<div class="textBoxFieldSet block_container" id="p15_lbl_montaje1OfertaDiv">
												<div class="input100">
													<label id="p15_lbl_montaje1Oferta" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.oferta" /></label>
												</div>
												<div class="input400">
													<input type=text id="p15_fld_montaje1Oferta" class="input375" disabled></input>
												</div>
											</div>
											<div class="textBoxFieldSet block_container" id="p15_lbl_montaje1EspacioPromDiv">
												<div class="input100">
													<label id="p15_lbl_montaje1EspacioProm" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.espacioprom" /></label>
												</div>
												<div class="input400">
													<input type=text id="p15_fld_montaje1EspacioProm" class="input375" disabled></input>
												</div>
											</div>											
										</div>
									</fieldset>
								</div>	
									<div id="p15_vegalsa_extra_div">
										<div class="textBoxFieldSet block_container_left" id="p15_lbl_montaje1IncPrevVentaDiv">
											<div class="input50">
												<label id="p15_lbl_montaje1IncPrevVenta" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.incPrevVenta" /></label>
											</div>
											<div class="input40">
												<input type=text id="p15_fld_montaje1IncPrevVenta" class="input40" disabled></input>
											</div>
										</div>											
										<div class="textBoxFieldSet block_container_left" id="p15_lbl_montaje1SMEstaticoDiv">
											<div class="input50">
												<label id="p15_lbl_montaje1SMEstatico" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.smEstatico" /></label>
											</div>
											<div class="input40">
												<input type=text id="p15_fld_montaje1SMEstatico" class="input40" disabled></input>
											</div>
										</div>											
									</div>
								<!-- MISUMI-428 -->
								<div id="p15_montajeAdicionalCentroBloque_div" class="p15_planogramasEstructuraMontaje1Field" style="display: none;">	
									<fieldset><legend><spring:message code="p15_referenciasCentroIC.montajeCentro" /></legend>
										<div id="p15_montajeAdicionalCentro_div">
											<div class="textBoxFieldSet" id="p15_montajeCapacidadCentroDiv">
												<label id="p15_lbl_montajeCapacidadCentro" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.capacidad" /></label>
												<input type=text id="p15_input_montajeCapacidadCentro" class="input50"></input>
											</div>
											<div class="textBoxFieldSet" id="p15_montajeFechaInicioCentroDiv">
												<label id="p15_lbl_montajeFechaInicioCentro" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.FechaInicio" /></label>
												<input type=text id="p15_input_montajeFechaInicioCentro" class="input75"></input>
											</div>
											<div class="textBoxFieldSet" id="p15_montajeFechaFinCentroDiv">
												<label id="p15_lbl_montajeFechaFinCentro" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.FechaFin" /></label>
												<input type=text id="p15_input_montajeFechaFinCentro" class="input75"></input>
											</div>
											<div class="textBoxFieldSet" id="p15_montajeOfertaCentroDiv">
												<label id="p15_lbl_montajeOfertaCentro" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.oferta" /></label>
												<input type=text id="p15_input_montajeOfertaCentro" class="input75"></input>
											</div>
										</div>
									</fieldset>
								</div>
								
								<div class="p15_planogramasEstructuraMontaje2Field" style="display:none;">	
									<fieldset><legend><spring:message code="p15_referenciasCentroIC.montaje2" /></legend>
										<div class="textBoxFieldSet" id="p15_lbl_montaje2CapacidadDiv">
											<label id="p15_lbl_montaje2Capacidad" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.capacidad" /></label>
											<input type=text id="p15_fld_montaje2Capacidad" class="input50"></input>
										</div>
										<div class="textBoxFieldSet" id="p15_lbl_montaje2FacingDiv">
											<label id="p15_lbl_montaje2Facing" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.facing" /></label>
											<input type=text id="p15_fld_montaje2Facing" class="input50"></input>
										</div>
										<div class="textBoxFieldSet">
											<input id="p15_chk_montaje2Cabecera" type="checkbox" class=""/>
											<label id="p15_lbl_montaje2Cabecera" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.cabecera" /></label>
										</div>
										<div class="textBoxFieldSet">
											<input id="p15_chk_montaje2Oferta" type="checkbox" class=""/>
											<label id="p15_lbl_montaje2Oferta" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.oferta" /></label>
										</div>
										<div class="textBoxFieldSet">
											<input id="p15_chk_montaje2Campana" type="checkbox" class=""/>
											<label id="p15_lbl_montaje2Campana" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.campana" /></label>
										</div>
									</fieldset>
								</div>	
								<div id="p15_planogramasMultiplicadorFacing" style="display:none;">
									<div class="textBox">
										<label id="p15_lbl_multiplicadorFacing" class="etiquetaCampo"><spring:message code="p15_referenciasCentroIC.multiplicadorFacing" /></label>
										<input type=text id="p15_fld_multiplicadorFacing" class="input50"></input>
									</div>
								</div>
								<div id="p15_planogramasEstructuraLinealMensaje" style="display: none;">
									<span id="p15_planogramasEstructuraLinealMensajeTexto1"><spring:message code="p15_referenciasCentroIC.IMPORTANTE" />:</span>
									<span id="p15_planogramasEstructuraLinealMensajeTexto2"></span>
								</div>
							</fieldset>	
						</div>	
					</div>	
				</div>
		<!-- 	</div>  -->
			<%@ include file="/WEB-INF/views/p17_referenciasCentroPopupVentas.jsp" %>
		</div>		
		<input id="p15_permiso_modif_imc_plano" type="hidden" value="${misumi:contieneOpcion(user.centro.opcHabil, '45_MODIF_IMC_PLANOS')}"></input>
		
		<div id="p15dialog-confirm" style="display:none;" title="<spring:message code="p15_referenciasCentroIC.preguntaGuardar" />">
			<table id="p15_table_dialog_confirm">
				<tr>
					<td id="p15_td_img_dialogConfirm"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
					<td id="p15_td_dialogConfirm"><span id="p15_mensajeDialogConfirm"><spring:message code="p15_referenciasCentroIC.preguntaGuardarTxt" /></span></td>
				</tr>
			</table>
		</div>