<script src="./misumi/scripts/p24PopUpAlbaranElectronico.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p24_popupAlbaranElectronico" title="<spring:message code="p24_popUpAlbaranElectronico.tituloPopup"/>">
				<div id="p24_AreaCabecera">
					<fieldset id="p24_FSET_AreaCabecera">
						
						<div class="p24_etiqValorCampo">
							<label id="p24_lbl_fechaPedido" class="etiquetaCampoNegrita"><spring:message code="p24_popUpAlbaranElectronico.fechaPedido" /></label>
							<label id="p24_lbl_fechaReposicion" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.fechaReposicion" /></label>
							<label id="p24_lbl_fechaPedidoVal" class="valorCampo"></label>
							<input type="hidden" id="p24_fld_fechaPedidoVal" value=""></input>
						</div>
						<div id="p24_divArea" class="p24_etiqValorCampo">
							<label id="p24_lbl_area" class="etiquetaCampoNegrita"><spring:message code="p24_popUpAlbaranElectronico.area" /></label>
							<label id="p24_lbl_areaVal" class="valorCampo"></label>
							<input type="hidden" id="p24_fld_areaVal" value=""></input>
						</div>
						<div id="p24_divMapa" class="p24_etiqValorCampo" style="display: none">
							<label id="p24_lbl_mapa" class="etiquetaCampoNegrita"><spring:message code="p24_popUpAlbaranElectronico.mapa" /></label>
							<label id="p24_lbl_mapaVal" class="valorCampo"></label>
							<input type="hidden" id="p24_fld_mapaVal" value=""></input>
						</div>
						<div class="p24_etiqValorCampo">
							<label id="p24_lbl_seccion" class="etiquetaCampoNegrita"><spring:message code="p24_popUpAlbaranElectronico.seccion" /></label>
							<label id="p24_lbl_seccionVal" class="valorCampo"></label>
							<input type="hidden" id="p24_fld_seccionVal" value=""></input>
						</div>
						<div class="p24_etiqValorCampo">
							<label id="p24_lbl_categoria" class="etiquetaCampoNegrita"><spring:message code="p24_popUpAlbaranElectronico.categoria" /></label>
							<label id="p24_lbl_categoriaVal" class="valorCampo"></label>
							<input type="hidden" id="p24_fld_categoriaVal" value=""></input>
						</div>
					</fieldset>	
				</div>   
			
				<div id="p24_AreaReferencias">
					<table id="gridP24AlbElec"></table>
					<div id="pagerGridP24"></div>
					<div id="p24_AreaLegendas">
				<img src='./misumi/images/dialog-accept-24.png?version=${misumiVersion}' class="p24_IconoLegendas"/><span class="p24_TextoLegendas"><spring:message code="p24_popUpAlbaranElectronico.entradaConfirmada" /></span> 
				<img src='./misumi/images/dialog-accept-yellow-24.png?version=${misumiVersion}' class="p24_IconoLegendas"/><span class="p24_TextoLegendas"><spring:message code="p24_popUpAlbaranElectronico.albaranIntegrado" /></span>
				<img src='./misumi/images/dialog-cancel-24.png?version=${misumiVersion}' class="p24_IconoLegendas"/><span class="p24_TextoLegendas"><spring:message code="p24_popUpAlbaranElectronico.albaranNoIntegrado" /></span>
				</div>
				</div> 
				
			</div>