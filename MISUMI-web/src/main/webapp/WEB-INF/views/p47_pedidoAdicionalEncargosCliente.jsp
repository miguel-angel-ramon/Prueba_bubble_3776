		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p47_AreaEncargosClienteHorizontal" class="p47_AreaHorizontal">			
				<div id="p47_AreaEncargosCliente">
					<table id="gridP47EncargosCliente"></table>
					<div id="pagerP47EncargosCliente"></div>
					<div id="p47dialog-confirm" style="display:none;" title="<spring:message code="p47_pedidoAdicionalEC.remove" />">
						<table id="p47_table_borrado">
							<tr>
								<td id="p47_td_img_borrado"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p47_td_borrado"><span class="p47_mensajeBorrado"></span><spring:message code="p47_pedidoAdicionalEC.removeText" /></td>
							</tr>
						</table>
					</div>
					<div id="p47dialog-confirm-noExiste" style="display:none;" title="<spring:message code="p47_pedidoAdicionalEC.noExiste" />">
						<table id="p47_table_noExiste">
							<tr>
								<td id="p47_td_img_noExiste"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
								<td id="p47_td_noExiste"><span class="p47_mensajeNoExiste"></span><spring:message code="p47_pedidoAdicionalEC.noExisteText" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div id="p47_AreaNotaPie">
					<spring:message code="p47_pedidoAdicionalEC.notaPie" />
				</div>
			</div>	
		</div>		