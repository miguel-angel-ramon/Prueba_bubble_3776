<div id="p86_popup" style="display: none" title="<spring:message code="p86_popup.titulo"/>">
	<div id="p86_formularioCrearDevolucion">
			<div id="p86_componenteTituloObservaciones">
				<fieldset>
					<legend><spring:message code="p86_legend.tituloObservacionesRecogeAbona" /></legend>
					<div id="p86_bloqueTitulo" class="p86_centradoVertical">
						<div class="p86_30W">
							<label id="p86_lbl_titulo" class="etiquetaCampo"><spring:message code="p86_componenteTituloObservaciones.titulo" /></label>
						</div>
						<div class="p86_70W">
							<input type=text id="p86_fld_titulo" class="p86_95W" />
						</div>
					</div>
					<div id="p86_bloqueObservaciones" class="p86_centradoVertical">
						<div class="p86_30W">
							<label id="p86_lbl_observaciones" class="etiquetaCampo"><spring:message code="p86_componenteTituloObservaciones.observaciones" /></label>
						</div>
						<div class="p86_70W">
							<textarea id="p86_txtArea_Observaciones" class="p86_95W"></textarea>
						</div>
					</div>
					<div  id="p86_bloqueRecogePlataforma" class="p86_centradoVertical">
						<div class="p86_30W">
							<label id="p86_lbl_recoge" class="etiquetaCampo"><spring:message code="p86_bloqueRecogePlataforma.recoge" /></label>
						</div>
						<div class="p86_70W">						
							<div class="p86_20W">
								<div class="comboBoxP86Adjust">
									<select id="p86_cmb_recoge" class="p86_95W "></select>
								</div>
							</div>
							<div class="p86_10W p86_flecha"> 		
								&nbsp;						
							</div>
							<div class="p86_70W">
								<div class="p86_30W">
									<label id="p86_lbl_plataforma" class="etiquetaCampo"><spring:message code="p86_bloqueRecogePlataforma.plataforma" /></label>
								</div>
								<div class="p86_70W">
									<input type=text id="p86_fld_plataforma" class="p86_90W" disabled="disabled"/>
								</div>
							</div>
						</div>
					</div>
					<div id="p86_bloqueAbonoPlataforma" class="p86_centradoVertical">
						<div class="p86_30W">
							<label id="p86_lbl_abono" class="etiquetaCampo"><spring:message code="p86_bloqueRecogePlataforma.abono" /></label>
						</div>
						<div class="p86_70W">						
							<div class="p86_20W">
								<div class="comboBoxP86Adjust">
									<select id="p86_cmb_abono" class="p86_95W"></select>
								</div>
							</div>
							<div class="p86_10W p86_flecha"> 
								&nbsp;								
							</div>
							<div class="p86_70W">
								<div class="p86_30W">
									<label id="p86_lbl_plataforma2" class="etiquetaCampo"><spring:message code="p86_bloqueRecogePlataforma.plataforma" /></label>
								</div>
								<div class="p86_70W">
									<input type=text id="p86_fld_plataforma2" class="p86_90W" disabled="disabled"/>
								</div>
							</div>
						</div>
					</div>
					<div id="p86_bloqueRma" class="p86_centradoVertical">
						<div class="p86_30W">
							<label id="p86_lbl_rma" class="etiquetaCampo"><spring:message code="p86_componenteRma.rma" /></label>
						</div>
						<div class="p86_12W">
							<input type=text id="p86_fld_rma" class="p86_95W" />
						</div>
					</div>
					<div id="p86_bloqueInfo" class="p86_10H">
						<label id="p86_lbl_campoObligatorio" class="etiquetaCampo"><spring:message code="p86_bloqueInfo.campoObligatorio" /></label>
					</div>
				</fieldset>
			</div>
			<div id="p86componenteCantidadBultoRef">
				<fieldset>
					<legend><spring:message code="p86_legend.refCantBulto" /></legend>
					<div id="p86_bloqueTipoDevolucion" class="p86_centradoVertical">
						<div class="p86_30W">
							<label id="p82_lbl_DescDevol" class="etiquetaCampo"><spring:message code="p86_bloqueRefDenom.tipoDevol" /></label> 
						</div>
						<div id="p86_combo_tipo" class="p86_70W comboBoxP86Adjust ">
							<select id="p86_cmb_TipoDevol"></select> 
							<input id="p86_cmb_TipoDevol_b" type="hidden" value=""></input>
						</div>
					</div>
					<div id="p86_bloqueRefDenom" class="p86_centradoVertical">
						<div class="p86_30W">
							<div class="p86_60W">
								<label id="p86_lbl_ref" class="etiquetaCampo"><spring:message code="p86_bloqueRefDenom.ref" /></label>
							</div>
							<div class="p86_40W">
								<input type=text id="p86_fld_ref" class="p86_95W"/>
							</div>
						</div>
						<div class="p86_20W">
						</div>
						<div class="p86_50W">
							<div class="p86_25W p86_05ML">
								<label id="p86_lbl_denom" class="etiquetaCampo"><spring:message code="p86_bloqueRefDenom.denom" /></label>
							</div>
							<div class="p86_70W">
								<input type=text id="p86_fld_denom" class="p86_90W" disabled="disabled"/>
							</div>
						</div>
					</div>
					<div id="p86_bloqueCantStkActual">
						<div id="p86_bloqueCant" class="p86_centradoVertical">
							<div class="p86_30W">
								<div class="p86_60W">
									<label id="p86_lbl_cant" class="etiquetaCampo"><spring:message code="p86_bloqueCantStkActual.cantidad" /></label>
								</div>
								<div class="p86_40W">
									<input type=text id="p86_fld_cant" class="p86_95W" disabled="disabled"/>
								</div>
							</div>
							<div class="p86_20W">
								<div class="p86_50W p86_10ML">
									<label id="p86_lbl_band" class="etiquetaCampo"><spring:message code="p86_bloqueCantStkActual.bandejas" /></label>
								</div>
								<div class="p86_40W">
									<input type=text id="p86_fld_band" class="p86_95W" disabled="disabled"/>
								</div>
							</div>
							<div id="p86_bloqueStk" class="p86_50W">
								<div class="p86_25W p86_05ML">
									<label id="p86_lbl_stk" class="etiquetaCampo"><spring:message code="p86_bloqueCantStkActual.stk" /></label>
								</div>
								<div class="p86_70W">
									<input type=text id="p86_fld_stk" class="p86_90W" disabled="disabled"/>
								</div>
							</div>
						</div>					
					</div>
					<div id="p86_bloqueBultoBtn" class="">
						<div id="p86_bloqueBulto" class="p86_centradoVertical">
							<div class="p86_30W">
								<div class="p86_60W">
									<label id="p86_lbl_bulto" class="etiquetaCampo"><spring:message code="p86_bloqueCantStkActual.bulto" /></label>
								</div>
								<div class="p86_40W">
									<input type=text id="p86_fld_bulto" class="p86_95W" disabled="disabled"/>
								</div>
							</div>
							<div class="p86_20W">
							</div>
							<div class="p86_50W p86_centradoVerticalInput">
								<div id="p86_bloqueBtn" class="p86_90W p86_10ML">
									<input id="p86_btn_anadir" class="boton  botonHover" value="<spring:message code="p86_btn_crearDevolucion.anadir" />" type="button">
									<input id="p86_btn_borrar" class="boton  botonHover" value="<spring:message code="p86_btn_crearDevolucion.borrar" />" type="button">
									<input id="p86_btn_guardar" class="boton  botonHover" value="<spring:message code="p86_btn_crearDevolucion.guardar" />" type="button">
								</div>														
							</div>
						</div>
					</div>
					<div id="p86_bloqueTabla" class="p86_centradoVertical">
						<table id="gridP86CrearDevolucion"></table>
						<div id="pagerGridp86"></div>
					</div>
					<div id="p86_bloqueInfoDos" class="p86_10H">
						<label id="p86_lbl_campoObligatorioDos" class="etiquetaCampo"><spring:message code="p86_bloqueInfo.campoObligatorio" /></label>
					</div>
				</fieldset>
			</div>
			<input type="hidden" id="p86_fld_StockDevuelto_Selecc" value=""></input>
		<!-- <div id="p86_componenteGrid">
			<fieldset>
				<legend><spring:message code="p86_legend.tabla" /></legend>
				<table id="gridP86CrearDevolucion"></table>
			</fieldset>		
		</div>-->
	</div>
</div>
<div id="p86_hiddens" style="display:none;">
	<input type=hidden id="p86_seccionReferencias" class=""/>
	<input type=hidden id="p86_fld_marca" class=""/>
	<input type=hidden id="p86_cantidadMaximaFilaNueva" class=""/>
</div>
