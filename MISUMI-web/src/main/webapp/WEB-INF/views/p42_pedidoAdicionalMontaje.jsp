		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p42_AreaMontajeAdicionalHorizontal" class="p42_AreaMontajeAdicionalHorizontal">			
				<div id="p42_AreaBeforeM" style="display: none;">
					<div id="p42_filterOferta">
						<div class="comboBoxMin comboBoxExtraLarge controlReturnP42">
							<label id="p42_lbl_oferta" class="etiquetaCampo"><spring:message code="p42_pedidoAdicionalM.oferta" /></label>
							<select id="p42_cmb_oferta"></select>
						</div>
					</div>	
					<div id="p42_filterPromocion">
						<div class="comboBoxMin comboBoxExtraLarge controlReturnP42">
							<label id="p42_lbl_promocion" class="etiquetaCampo"><spring:message code="p42_pedidoAdicionalM.promocion" /></label>
							<select id="p42_cmb_promocion"></select>
						</div>	
					</div>	
				</div>		
				<div id="p42_AreaMontajeAdicional">
					<table id="gridP42MontajeAdicional"></table>
					<div id="pagerP42MontajeAdicional"></div>
					<div id="p42dialog-confirm" style="display:none;" title="<spring:message code="p42_pedidoAdicionalM.remove" />">
						<table id="p42_table_borrado">
							<tr>
								<td id="p42_td_img_borrado"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p42_td_borrado"><span class="p42_mensajeBorrado"></span><spring:message code="p42_pedidoAdicionalM.removeText" /></td>
							</tr>
						</table>
					</div>
					<div id="p42dialog-confirm-noExiste" style="display:none;" title="<spring:message code="p42_pedidoAdicionalM.noExiste" />">
						<table id="p42_table_noExiste">
							<tr>
								<td id="p42_td_img_noExiste"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p42_td_noExiste"><span class="p42_mensajeNoExiste"></span><spring:message code="p42_pedidoAdicionalM.noExisteText" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>	
		</div>		