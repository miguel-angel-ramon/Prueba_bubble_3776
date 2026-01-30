<script src="./misumi/scripts/p21PopUpCantidadesPedidas.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p21_popupCantidadesPedidas" title="<spring:message code="p21_popUpCantidadesPedidas.tituloPopup"/>">
				<div id="p21_AreaCabecera">
					<fieldset id="p21_FSET_AreaCabecera">
						
						<div id="p21_divFecha" class="p21_etiqValorCampo">
							<label id="p21_lbl_fechaPedido" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.fechaPedido" /></label>
							<label id="p21_lbl_fechaReposicion" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.fechaReposicion" /></label>
							<label id="p21_lbl_fechaPedidoVal" class="valorCampo"></label>
							<input type="hidden" id="p21_fld_fechaPedidoVal" value=""></input>
						</div>
						<div id="p21_divArea" class="p21_etiqValorCampo">
							<label id="p21_lbl_area" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.area" /></label>
							<label id="p21_lbl_areaVal" class="valorCampo"></label>
							<input type="hidden" id="p21_fld_areaVal" value=""></input>
						</div>
						<div id="p21_divMapa" class="p21_etiqValorCampo" style="display: none">
							<label id="p21_lbl_mapa" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.mapa" /></label>
							<label id="p21_lbl_mapaVal" class="valorCampo"></label>
							<input type="hidden" id="p21_fld_mapaVal" value=""></input>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_seccion" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.seccion" /></label>
							<label id="p21_lbl_seccionVal" class="valorCampo"></label>
							<input type="hidden" id="p21_fld_seccionVal" value=""></input>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_categoria" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.categoria" /></label>
							<label id="p21_lbl_categoriaVal" class="valorCampo"></label>
							<input type="hidden" id="p21_fld_categoriaVal" value=""></input>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalReferenciasBajoPedido" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalReferenciasBajoPedido" /></label>
							<label id="p21_lbl_totalReferenciasBajoPedidoVal" class="valorCampo"></label>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalCajasBajoPedido" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalCajas" /></label>
							<label id="p21_lbl_totalCajasBajoPedidoVal" class="valorCampo"></label>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalReferenciasEmpuje" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalReferenciasEmpuje" /></label>
							<label id="p21_lbl_totalReferenciasEmpujeVal" class="valorCampo"></label>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalCajasEmpuje" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalCajas" /></label>
							<label id="p21_lbl_totalCajasEmpujeVal" class="valorCampo"></label>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalReferenciasImplantacionCabecera" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalReferenciasImplantacionCabecera" /></label>
							<label id="p21_lbl_totalReferenciasImplantacionCabeceraVal" class="valorCampo"></label>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalCajasImplantacionCabecera" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalCajas" /></label>
							<label id="p21_lbl_totalCajasImplantacionCabeceraVal" class="valorCampo"></label>
						</div>
						
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalReferenciasIntertienda" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalReferenciasIntertienda" /></label>
							<label id="p21_lbl_totalReferenciasIntertiendaVal" class="valorCampo"></label>
						</div>
						<div class="p21_etiqValorCampo">
							<label id="p21_lbl_totalCajasIntertienda" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.totalCajas" /></label>
							<label id="p21_lbl_totalCajasIntertiendaVal" class="valorCampo"></label>
						</div>
						
					</fieldset>	
				</div>   
			
				<div id="p21_AreaReferencias">
					<table id="gridP21CantPed"></table>
					<div id="pagerGridP21"></div>
					<div id="p21AreaBotonExcel">
						<input type="button" id="p21_btn_exportExcel" class="boton  botonHover" value="<spring:message code="p21_popUpCantidadesPedidas.exportarAExcel" />"/>
					</div>					
				</div> 
			</div>