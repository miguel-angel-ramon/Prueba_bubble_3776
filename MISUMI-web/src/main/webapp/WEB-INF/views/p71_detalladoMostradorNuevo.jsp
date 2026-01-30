<script src="./misumi/scripts/p71DetalladoPedidoNuevo.js?version=${misumiVersion}" type="text/javascript"></script> 
		<!--  Contenido página -->
		<div id="p71_AreaModificacion" title="<spring:message code="p71_detalladoPedidoNuevo.titulo"/>">
			<div id="p71_AreaReferenciaHorizontal" class="p71_AreaReferenciaHorizontal">
				<div id="p71_AreaBloque1">
				<fieldset> <legend><spring:message code="p71_detalladoPedidoNuevo.tituloBloque1" /></legend>
					<div id="p71_filtroBloque1">
						<div class="textBox">
							<label id="p71_lbl_referencia" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.reference" /></label>
							<input type=text id="p71_fld_referencia" class="input100"></input>
						</div>
						<div class="textBox">
							<label id="p71_lbl_descripcionRef" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.descripcionRef" /></label>
							<input type=text id="p71_fld_descripcionRef" disabled="disabled" class="input200"></input>
						</div>
					</div>
					<div id="p71_filtroBloque2">
						<div class="textBox">
							<label id="p71_lbl_stock" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.stock" /></label>
							<input type=text id="p71_fld_stock" disabled="disabled"  class="input75"></input>
						</div>					
						<div class="textBox">
							<div id="p71_divLabelCajasPendH">
								<label id="p71_lbl_cajasPendH" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.cajasPendientesRecibirHoy" /></label>
							</div>	
							<div id="p71_divInputCajasPendH">
								<input type=text id="p71_fld_cajasPendH" disabled="disabled"  class="input75"></input>
							</div>	
						</div>
						<div class="textBox">
							<div id="p71_divLabelCajasPendM">
								<label id="p71_lbl_cajasPendM" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.cajasPendientesRecibirManana" /></label>
							</div>
							<div id="p71_divInputCajasPendM">
								<input type=text id="p71_fld_cajasPendM" disabled="disabled"  class="input75"></input>
							</div>
							<input id="p71_txtgrupo1" type="hidden"/>	
							<input id="p71_txtgrupo2" type="hidden"/>
							<input id="p71_txtgrupo3" type="hidden"/>
							<input id="p71_txtgrupo4" type="hidden"/>
							<input id="p71_txtgrupo5" type="hidden"/>
							<input id="p71_ufp" type="hidden"/>
							<input id="p71_tipoUfp" type="hidden"/>
							<input id="p71_dexenx" type="hidden"/>
							<input id="p71_flgOferta" type="hidden"/>
							<!-- <input id="p71_dexenxEntero" type="hidden"/>	 -->				
							<input id="p71_fld_referenciaEroski" type="hidden"/>
							<input id="p71_fld_descripcionRefEroski" type="hidden"/>
							<input id="p71_fld_esCaprabo" type="hidden"/>
						</div>
					</div>
				</fieldset>
				</div>
				<div id="p71_AreaBloque2">
				<fieldset> <legend><spring:message code="p71_detalladoPedidoNuevo.tituloBloque2" /></legend>	
					<div id="p71_filtroBloque3">
						<div class="textBox">
							<label id="p71_lbl_uc" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.uc" /></label>
							<input type=text id="p71_fld_uc" disabled="disabled"  class="input75"></input>
						</div>
						<div class="textBox">
							<label id="p71_lbl_cajas" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.cajas" /></label>
							<input type=text id="p71_fld_cajas" class="input75" maxlength="3"></input>
							<label id="p71_lbl_dexenx" class="etiquetaCampo" style="display:none"></label>
						</div>
					</div>
					<div id="p71_filtroBloque4">
						<div class="textBox">
							<label id="p71_lbl_cajas" class="etiquetaCampo"><spring:message code="p71_detalladoPedidoNuevo.siguienteDiaPedido" /></label>
							<input type=text id="p71_fld_nextDay" disabled="disabled" class="input75"></input>
							<input id="p71_fld_nextDay_BD" type="hidden"></input>
						</div>
					</div>
				 </fieldset>
				</div>
				<div id="p71_AreaBloque3">
					<div id="p71_insertado" style="display:none">
					</div>
					<div id="p71_error" style="display:none">
					</div>
				</div>
			</div>	
		</div> 
