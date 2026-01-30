<script src="./misumi/scripts/p28PopUpRefCampanas.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p28_popupRefCampanas" title="<spring:message code="p28_popUpRefCampanas.tituloPopup"/>">
				<div id="p28_AreaCabecera">
					<fieldset id="p28_FSET_AreaCabecera">
						<div id="p28_campana" class="p28_etiqValorCampoCampanaOferta">
							<label id="p28_lbl_campana" class="etiquetaCampoNegrita"><spring:message code="p28_popUpRefCampanas.campana" /></label>
							<label id="p28_lbl_campanaVal" class="valorCampo"></label>
							<input type="hidden" id="p28_fld_campanaVal" value=""></input>
						</div>
						<div id="p28_oferta" class="p28_etiqValorCampoCampanaOferta">
							<label id="p28_lbl_oferta" class="etiquetaCampoNegrita"><spring:message code="p28_popUpRefCampanas.oferta" /></label>
							<label id="p28_lbl_ofertaVal" class="valorCampo"></label>
							<input type="hidden" id="p28_fld_ofertaVal" value=""></input>
						</div>
						<div class="p28_etiqValorCampo">
							<label id="p28_lbl_area" class="etiquetaCampoNegrita"><spring:message code="p28_popUpRefCampanas.area" /></label>
							<label id="p28_lbl_areaVal" class="valorCampo"></label>
							<input type="hidden" id="p28_fld_areaVal" value=""></input>
						</div>
						<div class="p28_etiqValorCampo">
							<label id="p28_lbl_seccion" class="etiquetaCampoNegrita"><spring:message code="p28_popUpRefCampanas.seccion" /></label>
							<label id="p28_lbl_seccionVal" class="valorCampo"></label>
							<input type="hidden" id="p28_fld_seccionVal" value=""></input>
						</div>
						<div class="p28_etiqValorCampo">
							<label id="p28_lbl_categoria" class="etiquetaCampoNegrita"><spring:message code="p28_popUpRefCampanas.categoria" /></label>
							<label id="p28_lbl_categoriaVal" class="valorCampo"></label>
							<input type="hidden" id="p28_fld_categoriaVal" value=""></input>
						</div>
						<div class="p28_etiqValorCampo">
							<label id="p28_lbl_subcategoria" class="etiquetaCampoNegrita"><spring:message code="p28_popUpRefCampanas.subcategoria" /></label>
							<label id="p28_lbl_subcategoriaVal" class="valorCampo"></label>
							<input type="hidden" id="p28_fld_subcategoriaVal" value=""></input>
						</div>
						<div class="p28_etiqValorCampo"  style="display:none;">
							<label id="p28_lbl_segmento" class="etiquetaCampoNegrita"><spring:message code="p28_popUpRefCampanas.segmento" /></label>
							<label id="p28_lbl_segmentoVal" class="valorCampo"></label>
							<input type="hidden" id="p28_fld_segmentoVal" value=""></input>
						</div>
						<div class="p28_oculto">
							<input type="hidden" id="p28_fld_tipoOCVal" value=""></input>
							<input type="hidden" id="p28_fld_fechaInicioVal" value=""></input>
							<input type="hidden" id="p28_fld_fechaFinVal" value=""></input>
							<input type="hidden" id="p28_fld_codArtVal" value=""></input>
						</div>
					</fieldset>	
				</div>   
			
				<div id="p28_AreaReferencias">
					<table id="gridP28Ref"></table>
					<div id="pagerGridP28"></div>
					<div id="p28AreaBotonExcel">
						<input type="button" id="p28_btn_exportExcel" class="boton  botonHover" value="<spring:message code="p28_popUpRefCampanas.exportarAExcel" />"/>
					</div>					
				</div> 
				<div id="excellPopup"></div>
				<div id="excellWindow"></div>	
			</div>