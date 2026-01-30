		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p46_AreaVCHorizontal" class="p46_AreaVCHorizontal">			
				<div id="p46_AreaBeforeVC">
					<div id="p46_filterDescripcion">
						<div class="comboBoxMin comboBoxMediumLarge controlReturnP46">
							<label id="p46_lbl_descripcion" class="etiquetaCampo"><spring:message code="p46_pedidoAdicionalVC.descripcion" /></label>
							<select id="p46_cmb_descripcion"></select>
							<input type="hidden" id="p46_fld_fechaHasta" value=""></input>
						</div>
						<div id="p46_div_fechaHasta">
							<label id="p46_lbl_fechaHasta" class="etiquetaCampo"></label>
						</div>
					</div>
					<c:if test="${user.perfil != 3}">
						<div id="p46_filterValidar">	
							<input type="button" id="p46_btn_validar" class="boton botonNarrow  botonHover" value='<spring:message code="p46_pedidoAdicionalVC.validar" />'></input>
						</div>
					</c:if>
					<c:if test="${user.perfil == 3}">
						<div id="p46_divSustitutoValidar">	
							<label id="p46_labelSustitutoValidar"></label>
						</div>
					</c:if>
				</div>
				<div id="p46_AreaVC">
					<table id="gridP46VC"></table>
					<div id="pagerP46VC"></div>
					<input type="hidden" id="p46_fld_ValidarCant_Selecc" value=""></input>
				</div>
			</div>
		</div>		