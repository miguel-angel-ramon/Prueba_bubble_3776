<script src="./misumi/scripts/p16ReferenciasCentroM.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/model/MovimientoStock.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PendientesRecibir.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/StockNoServido.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p15_AreaMovimientos1Horizontal" class="p15_AreaMovimientosHorizontal">
				
				<div id="p16_AreaNSR">
					<table id="gridP16NSR"></table>
					<div id="pagerP16NSR"></div>
				</div>
				
				<div id="p16_AreaPendienteRecibir" style="display: none;">
					<div id="p16_pendienteRecibirEstructura">
						<div class="p16_pendienteRecibirEstructuraField" >
							<div class="p16_pendienteRecibir">
								<label id="p16_lbl_pendienteRecibir1" class="etiquetaCampo"><spring:message code="p16_referenciasCentroM.pendienteRecibir1" /></label>
							</div>
							<div class="p16_pendienteRecibirIN">
								<input type=text id="p16_fld_pendienteRecibir1" class="input75"></input>
							</div>
						</div>
						<div class="p16_pendienteRecibirEstructuraField" >
							<div class="p16_pendienteRecibir">
								<label id="p16_lbl_pendienteRecibir2" class="etiquetaCampo"><spring:message code="p16_referenciasCentroM.pendienteRecibir2" /></label>
							</div>
							<div class="p16_pendienteRecibirIN">
								<input type=text id="p16_fld_pendienteRecibir2" class="input75"></input>
							</div>
						</div>	
					</div>	
				</div>
			
			</div> <!-- "p15_AreaMovimientos1Horizontal" -->

			<div id="p15_AreaMovimientos2Horizontal" class="p15_AreaMovimientosHorizontal">
				<div id="p16_AreaStock">
					<div id="p16_stockEstructura">
						<div class="p16_stockEstructuraField" >
							<div id="p16_stockTabla">
								<fieldset><legend><spring:message code="p16_referenciasCentroM.stock" /></legend>
									<table id="p16_stockTablaEstructura" border="1" class="ui-widget">
										<thead class="ui-widget-header">
										    <tr>
										        <th class="p16_stockTablaTh"><spring:message code="p16_referenciasCentroM.lunesAbrev" /></th>
										        <th class="p16_stockTablaTh"><spring:message code="p16_referenciasCentroM.martesAbrev" /></th>
										        <th class="p16_stockTablaTh"><spring:message code="p16_referenciasCentroM.miercolesAbrev" /></th>
										        <th class="p16_stockTablaTh"><spring:message code="p16_referenciasCentroM.juevesAbrev" /></th>
										        <th class="p16_stockTablaTh"><spring:message code="p16_referenciasCentroM.viernesAbrev" /></th>
										        <th class="p16_stockTablaTh"><spring:message code="p16_referenciasCentroM.sabadoAbrev" /></th>
										        <th class="p16_stockTablaTh"><spring:message code="p16_referenciasCentroM.domingoAbrev" /></th>
										    </tr>
										</thead>
										<tbody class="ui-widget-content">
											<tr>
												<td id="p16_stockDTd1" class="tdDay"></td>
										        <td id="p16_stockDTd2" class="tdDay"></td>
										        <td id="p16_stockDTd3" class="tdDay"></td>
										        <td id="p16_stockDTd4" class="tdDay"></td>
										        <td id="p16_stockDTd5" class="tdDay"></td>
										        <td id="p16_stockDTd6" class="tdDay"></td>
										        <td id="p16_stockDTd7" class="tdDay"></td>
										    </tr>
										    <tr>
												<td id="p16_stockTd1" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd2" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd3" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd4" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd5" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd6" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd7" class="p16_stockTablaTd"></td>
										    </tr>
										    <tr>
												<td id="p16_stockDTd8" class="tdDay"></td>
										        <td id="p16_stockDTd9" class="tdDay"></td>
										        <td id="p16_stockDTd10" class="tdDay"></td>
										        <td id="p16_stockDTd11" class="tdDay"></td>
										        <td id="p16_stockDTd12" class="tdDay"></td>
										        <td id="p16_stockDTd13" class="tdDay"></td>
										        <td id="p16_stockDTd14" class="tdDay"></td>
										    </tr>
										    <tr>
												<td id="p16_stockTd8" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd9" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd10" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd11" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd12" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd13" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd14" class="p16_stockTablaTd"></td>
										    </tr>
										    <tr>
												<td id="p16_stockDTd15" class="tdDay"></td>
										        <td id="p16_stockDTd16" class="tdDay"></td>
										        <td id="p16_stockDTd17" class="tdDay"></td>
										        <td id="p16_stockDTd18" class="tdDay"></td>
										        <td id="p16_stockDTd19" class="tdDay"></td>
										        <td id="p16_stockDTd20" class="tdDay"></td>
										        <td id="p16_stockDTd21" class="tdDay"></td>
										    </tr>

										    <tr>
												<td id="p16_stockTd15" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd16" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd17" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd18" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd19" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd20" class="p16_stockTablaTd"></td>
										        <td id="p16_stockTd21" class="p16_stockTablaTd"></td>
										    </tr>
										</tbody>
									</table>
									<div id="p16_stockTablaMensajesPie">
										<div id="p16_stockTablaMensajeErrorHoy" style="display: none;">
											<span id="p16_stockTablaMensajeErrorHoyTexto"><spring:message code="p16_referenciasCentroM.errorWSStockTiendaMensaje" /></span>
										</div>
										<div id="p16_stockTablaMensajeRoturas">
											<span id="p16_stockTablaMensajeRoturasTexto"><spring:message code="p16_referenciasCentroM.roturasEnRojo" /></span>
										</div>
									</div>
								</fieldset>	
							</div>
						</div>
					</div>	
				</div>
				<div id="p16_AreaFotos">
					<div id="p16_fieldset_foto">
						<div id="p16_fieldset_leyenda"><spring:message code="p16_referenciasCentroM.foto" /></div>
						<img id="p16_img_referencia" src="./misumi/images/pixel.png" />
					</div>
				</div>
			</div>
			
			<div id="p15_AreaMovimientos3Horizontal" class="p15_AreaMovimientosHorizontal">			
				<div id="p16_AreaMovimientos">
					<table id="gridP16Movimientos"></table>
					<div id="pagerP16Movimientos"></div>
				</div>
			</div>
		</div>
		