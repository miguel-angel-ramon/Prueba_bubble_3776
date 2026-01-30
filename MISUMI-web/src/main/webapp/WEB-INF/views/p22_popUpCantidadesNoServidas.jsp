<script src="./misumi/scripts/p22PopUpCantidadesNoServidas.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p22_popupCantidadesNoServidas" title="<spring:message code="p22_popUpCantidadesNoServidas.tituloPopup"/>">
				<div id="p22_AreaCabecera">
					<fieldset id="p22_FSET_AreaCabecera">
						
						<div class="p22_etiqValorCampo">
							<label id="p22_lbl_fechaPedido" class="etiquetaCampoNegrita"><spring:message code="p22_popUpCantidadesNoServidas.fechaPedido" /></label>
							<label id="p22_lbl_fechaReposicion" class="etiquetaCampoNegrita"><spring:message code="p21_popUpCantidadesPedidas.fechaReposicion" /></label>
							<label id="p22_lbl_fechaPedidoVal" class="valorCampo"></label>
							<input type="hidden" id="p22_fld_fechaPedidoVal" value=""></input>
						</div>
						<div id="p22_divArea" class="p22_etiqValorCampo">
							<label id="p22_lbl_area" class="etiquetaCampoNegrita"><spring:message code="p22_popUpCantidadesNoServidas.area" /></label>
							<label id="p22_lbl_areaVal" class="valorCampo"></label>
							<input type="hidden" id="p22_fld_areaVal" value=""></input>
						</div>
						<div id="p22_divMapa" class="p22_etiqValorCampo" style="display: none;" >
							<label id="p22_lbl_mapa" class="etiquetaCampoNegrita"><spring:message code="p22_popUpCantidadesNoServidas.mapa" /></label>
							<label id="p22_lbl_mapaVal" class="valorCampo"></label>
							<input type="hidden" id="p22_fld_mapaVal" value=""></input>
						</div>		
						<div class="p22_etiqValorCampo">
							<label id="p22_lbl_seccion" class="etiquetaCampoNegrita"><spring:message code="p22_popUpCantidadesNoServidas.seccion" /></label>
							<label id="p22_lbl_seccionVal" class="valorCampo"></label>
							<input type="hidden" id="p22_fld_seccionVal" value=""></input>
						</div>
						<div class="p22_etiqValorCampo">
							<label id="p22_lbl_categoria" class="etiquetaCampoNegrita"><spring:message code="p22_popUpCantidadesNoServidas.categoria" /></label>
							<label id="p22_lbl_categoriaVal" class="valorCampo"></label>
							<input type="hidden" id="p22_fld_categoriaVal" value=""></input>
						</div>
						<div class="p22_etiqValorCampo">
							<label id="p22_lbl_totalReferenciasNoServidas" class="etiquetaCampoNegrita"><spring:message code="p22_popUpCantidadesNoServidas.totalReferenciasNoServidas" /></label>
							<label id="p22_lbl_totalReferenciasNoServidasVal" class="valorCampo"></label>
						</div>
						<div class="p22_etiqValorCampo">
							<label id="p22_lbl_totalCajasNoServidas" class="etiquetaCampoNegrita"><spring:message code="p22_popUpCantidadesNoServidas.totalCajas" /></label>
							<label id="p22_lbl_totalCajasNoServidasVal" class="valorCampo"></label>
						</div>
					</fieldset>	
				</div>   
			
				<div id="p22_AreaReferencias">
					<table id="gridP22CantNoServ"></table>
					<div id="pagerGridP22"></div>
					<div id="p22AreaBotonExcel">
						<input type="button" id="p22_btn_exportExcel" class="boton  botonHover" value="<spring:message code="p22_popUpCantidadesNoServidas.exportarAExcel" />"/>
					</div>					
				</div> 
			</div>