		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p41_AreaEncargosHorizontal" class="p41_AreaEncargosHorizontal">
				<div id="p41_AreaEncargos">
					<table id="gridP41Encargos"></table>
					<div id="pagerP41Encargos"></div>
					<div id="p41dialog-confirm" style="display:none;" title="<spring:message code='p41_pedidoAdicionalE.remove' />">
						<table id="p41_table_borrado">
							<tr>
								<td id="p41_td_img_borrado"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p41_td_borrado"><span class="p41_mensajeBorrado"></span><spring:message code="p41_pedidoAdicionalE.removeText" /></td>
							</tr>
						</table>
					</div>
					<div id="p41dialog-confirm-noExiste" style="display:none;" title="<spring:message code="p41_pedidoAdicionalE.noExiste" />">
						<table id="p41_table_noExiste">
							<tr>
								<td id="p41_td_img_noExiste"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p41_td_noExiste"><span class="p41_mensajeNoExiste"></span><spring:message code="p41_pedidoAdicionalE.noExisteText" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div> 
		</div>		