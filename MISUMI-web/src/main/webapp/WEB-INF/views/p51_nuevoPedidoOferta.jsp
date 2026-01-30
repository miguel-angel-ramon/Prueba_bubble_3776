

		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p51_AreaOfertaHorizontal" class="p51_AreaOfertaHorizontal">
				<div class="p51_filtroNumOferta">
					<fieldset> <legend><spring:message code="p51_nuevoPedidoAdicionalOF.titNumOferta" /></legend>
						<div class="p51_filtroEstructura">
							<div class="comboBox controlReturnP51">
								<label id="p51_lbl_numoferta" class="etiquetaCampo"><spring:message code="p51_nuevoPedidoAdicionalOF.numOferta" /></label>
								<select id="p51_cmb_numeroOferta"></select>
							</div>
							<div class="textBox">
								<label id="p51_lbl_fechaInicio" class="etiquetaCampo"><spring:message code="p51_nuevoPedidoAdicionalOF.fechaInicio" /></label>
								<div id="p51_fechaInicioDatePicker"></div>
							</div>								
							<div class="textBox">
								<label id="p51_lbl_fechaFin" class="etiquetaCampo"><spring:message code="p51_nuevoPedidoAdicionalOF.fechaFin" /></label>
								<div id="p51_fechaFinDatePicker"></div>
							</div>
						</div>							
					</fieldset>
				</div>
				<div class="p51_filtroEstructuraReferencia">
				<fieldset> <legend><spring:message code="p51_nuevoPedidoAdicionalOF.filter" /></legend>
					<div id="p51_areaCondicionFiltro" class="p51_filtroEstructura">
						<div id="p51_filterEstructura">
							<div class="comboBoxMin comboBoxMedium controlReturnP51">
								<label id="p51_lbl_seccion" class="etiquetaCampo"><spring:message code="p51_nuevoPedidoAdicionalOF.section" /></label>
								<select id="p51_cmb_seccion"></select>
							</div>								
							<div class="comboBoxMin comboBoxMedium controlReturnP51">
								<label id="p51_lbl_categoria" class="etiquetaCampo"><spring:message code="p51_nuevoPedidoAdicionalOF.category" /></label>
								<select id="p51_cmb_categoria"></select>
							</div>
							<!-- <c:if test="${user.centro != null && user.centro.negocio != null && user.centro.negocio eq 'H'}">
								<div id="p51_filtroGondola" class="comboBoxMin comboBoxShort controlReturnP51">
									<label id="p51_lbl_gondola" class="etiquetaCampo"><spring:message code="p51_nuevoPedidoAdicionalOF.gondola" /></label>
									<select id="p51_cmb_gondola"></select>
								</div>
							</c:if> -->
						</div>
						<div id="p51_filtroReferencia" style="display:none;">
							<div style="text-align: left; padding:5px">
								<label id="p51_lbl_referencia" class="etiquetaCampo"><spring:message code="p51_nuevoPedidoAdicionalOF.reference" /></label>
								<input type=text id="p51_fld_referencia" class="input250 controlReturnP51"></input>
								<input id="p51_txt_flgFrescoPuro" type="hidden"/>
								<input id="p51_txt_tipoOferta" type="hidden"/>
							</div>
						</div>	
						<div id="p51_filterButtons">
							<div class="p51_tipoBusqListadoField">
								<input type="radio" class="controlReturnP51" name="p51_rad_tipoFiltro" id="p51_rad_tipoFiltro" value="1" checked="checked" />
								<label id="p51_lbl_radioEstructura" class="etiquetaCampo" title="<spring:message code='p51_nuevoPedidoAdicionalOF.comertialStructure' />"><spring:message code="p51_nuevoPedidoAdicionalOF.comertialStructureAbr" /></label>
								<input type="radio" class="controlReturnP51" name="p51_rad_tipoFiltro" id="p51_rad_tipoFiltro" value="2"/>
								<label id="p51_lbl_radioReferencia" class="etiquetaCampo" title="<spring:message code='p51_nuevoPedidoAdicionalOF.reference' />"><spring:message code="p51_nuevoPedidoAdicionalOF.referenceAbr" /></label>
							</div>
							<input type="button" id="p51_btn_find" class="p51boton  botonHover" value='<spring:message code="p51_nuevoPedidoAdicionalOF.find" />'></input>
							<input type="button" id="p51_btn_save" class="p51boton  botonHover" value='<spring:message code="p51_nuevoPedidoAdicionalOF.save" />'></input>
							<input type="button" id="p51_btn_cancel" class="p51boton  botonHover" value='<spring:message code="p51_nuevoPedidoAdicionalOF.back" />'></input>
						</div>
						<div id="p51dialogCancel-confirm" style="display:none;" title="<spring:message code="p51_nuevoPedidoAdicionalOF.cancel" />">
							<table id="p51_table_canceladoborrado">
								<tr>
									<td id="p51_td_img_cancelado"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
									<td id="p51_td_cancelado"><span class="p51_mensajeCancelado"></span><spring:message code="p51_nuevoPedidoAdicionalOF.cancelText" /></td>
								</tr>
							</table>
						</div>
					</div>
				</fieldset>
				</div>
				<div id="p51_AreaOferta" style="display: none;">
				<fieldset> 
				<legend>
					<div id="p51_areaOfertaTitulo">
						<spring:message code="p51_nuevoPedidoAdicionalOF.seleccRefer" />
					</div>
					<div id="p51_areaOfertaAyuda">
						<img id="p52_btn_ayudaNuevoOferta" class="botonAyudaNuevoOf" title="Ayuda" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}">
					</div>
				</legend>
				<div id="p51_AreaOfertaDatos">
					<table id="gridP51FP" class="gridP51"></table>
					<div id="pagerP51FP"></div>
					<table id="gridP51AFI" class="gridP51"></table>
					<div id="pagerP51AFI"></div>
					<input type="hidden" id="p51_fld_NuevoOf_Selecc" value=""></input>
				</div>
				</fieldset>
				</div>
			</div>
		</div> 
	