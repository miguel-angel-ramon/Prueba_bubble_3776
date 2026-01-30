		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p43_AreaMontajeAdicionalOfertaHorizontal" class="p43_AreaMontajeAdicionalOfertaHorizontal">	
				<div id="p43_AreaBeforeMO" style="display: none;">
					<div id="p43_filterOferta">
						<div class="comboBoxMin comboBoxExtraLarge controlReturnP43">
							<label id="p43_lbl_oferta" class="etiquetaCampo"><spring:message code="p43_pedidoAdicionalMO.oferta" /></label>
							<select id="p43_cmb_oferta"></select>
						</div>
					</div>	
					<div id="p43_filterPromocion">
						<div class="comboBoxMin comboBoxExtraLarge controlReturnP43">
							<label id="p43_lbl_promocion" class="etiquetaCampo"><spring:message code="p43_pedidoAdicionalMO.promocion" /></label>
							<select id="p43_cmb_promocion"></select>
						</div>	
					</div>	
				</div>				
				<div id="p43_AreaMontajeAdicionalOferta">
					<table id="gridP43MontajeAdicionalOferta"></table>
					<div id="pagerP43MontajeAdicionalOferta"></div>
					<div id="p43dialog-confirm" style="display:none;" title="<spring:message code="p43_pedidoAdicionalMO.remove" />">
						<table id="p43_table_borrado">
							<tr>
								<td id="p43_td_img_borrado"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p43_td_borrado"><span class="p43_mensajeBorrado"></span><spring:message code="p43_pedidoAdicionalMO.removeText" /></td>
							</tr>
						</table>
					</div>
					<div id="p43dialog-confirm-noExiste" style="display:none;" title="<spring:message code="p43_pedidoAdicionalMO.noExiste" />">
						<table id="p43_table_noExiste">
							<tr>
								<td id="p43_td_img_noExiste"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p43_td_noExiste"><span class="p43_mensajeNoExiste"></span><spring:message code="p43_pedidoAdicionalMO.noExisteText" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>	
		</div>		