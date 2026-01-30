<script src="./misumi/scripts/p23PopUpCantidadesConfirmadas.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p23_popupCantidadesConfirmadas" title="<spring:message code="p23_popUpCantidadesConfirmadas.tituloPopup"/>">
				<div id="p23_AreaCabecera">
					<fieldset id="p23_FSET_AreaCabecera">
						
						<div id="p23_divFecha" class="p23_etiqValorCampo">
							<label id="p23_lbl_fechaPedido" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.fechaPedido" /></label>
							<label id="p23_lbl_fechaReposicion" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.fechaReposicion" /></label>
							<label id="p23_lbl_fechaPedidoVal" class="valorCampo"></label>
							<input type="hidden" id="p23_fld_fechaPedidoVal" value=""></input>
						</div>
						<div id="p23_divArea" class="p23_etiqValorCampo">
							<label id="p23_lbl_area" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.area" /></label>
							<label id="p23_lbl_areaVal" class="valorCampo"></label>
							<input type="hidden" id="p23_fld_areaVal" value=""></input>
						</div>
						<div id="p23_divMapa" class="p23_etiqValorCampo" style="display: none">
							<label id="p23_lbl_mapa" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.mapa" /></label>
							<label id="p23_lbl_mapaVal" class="valorCampo"></label>
							<input type="hidden" id="p23_fld_mapaVal" value=""></input>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_seccion" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.seccion" /></label>
							<label id="p23_lbl_seccionVal" class="valorCampo"></label>
							<input type="hidden" id="p23_fld_seccionVal" value=""></input>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_categoria" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.categoria" /></label>
							<label id="p23_lbl_categoriaVal" class="valorCampo"></label>
							<input type="hidden" id="p23_fld_categoriaVal" value=""></input>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalReferenciasBajoPedido" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalReferenciasBajoPedido" /></label>
							<label id="p23_lbl_totalReferenciasBajoPedidoVal" class="valorCampo"></label>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalCajasBajoPedido" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalCajas" /></label>
							<label id="p23_lbl_totalCajasBajoPedidoVal" class="valorCampo"></label>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalReferenciasEmpuje" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalReferenciasEmpuje" /></label>
							<label id="p23_lbl_totalReferenciasEmpujeVal" class="valorCampo"></label>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalCajasEmpuje" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalCajas" /></label>
							<label id="p23_lbl_totalCajasEmpujeVal" class="valorCampo"></label>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalReferenciasImplantacionCabecera" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalReferenciasImplantacionCabecera" /></label>
							<label id="p23_lbl_totalReferenciasImplantacionCabeceraVal" class="valorCampo"></label>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalCajasImplantacionCabecera" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalCajas" /></label>
							<label id="p23_lbl_totalCajasImplantacionCabeceraVal" class="valorCampo"></label>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalReferenciasIntertienda" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalReferenciasIntertienda" /></label>
							<label id="p23_lbl_totalReferenciasIntertiendaVal" class="valorCampo"></label>
						</div>
						<div class="p23_etiqValorCampo">
							<label id="p23_lbl_totalCajasIntertienda" class="etiquetaCampoNegrita"><spring:message code="p23_popUpCantidadesConfirmadas.totalCajas" /></label>
							<label id="p23_lbl_totalCajasIntertiendaVal" class="valorCampo"></label>
						</div>
						
					</fieldset>	
				</div>   
			
				<div id="p23_AreaReferencias">
					<table id="gridP23CantConf"></table>
					<div id="pagerGridP23"></div>
					<div id="p23AreaBotonExcel">
						<input type="button" id="p23_btn_exportExcel" class="boton  botonHover" value="<spring:message code="p23_popUpCantidadesConfirmadas.exportarAExcel" />"/>
					</div>					
				</div> 
			</div>