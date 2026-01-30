<script src="./misumi/scripts/p29PopUpCantidadesNoServidasRef.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p29_popupCantidadesNoServidasRef" title="<spring:message code="p29_popUpCantidadesNoServidasRef.tituloPopup"/>">
				<div id="p29_AreaCabecera">
					<fieldset id="p29_FSET_AreaCabecera">
						
						<div id="p29_campana" class="p29_etiqValorCampoCampana">
							<label id="p29_lbl_campana" class="etiquetaCampoNegrita"><spring:message code="p29_popUpCantidadesNoServidasRef.campana" /></label>
							<label id="p29_lbl_campanaVal" class="valorCampo"></label>
							<input type="hidden" id="p29_fld_campanaVal" value=""></input>
						</div>
						<div id="p29_oferta" class="p29_etiqValorCampoCampana" style="display:none;">
							<label id="p29_lbl_oferta" class="etiquetaCampoNegrita"><spring:message code="p29_popUpCantidadesNoServidasRef.oferta" /></label>
							<label id="p29_lbl_ofertaVal" class="valorCampo"></label>
							<input type="hidden" id="p29_fld_ofertaVal" value=""></input>
						</div>
						<div class="p29_etiqValorCampoRef">
							<label id="p29_lbl_referencia" class="etiquetaCampoNegrita"><spring:message code="p29_popUpCantidadesNoServidasRef.referencia" /></label>
							<label id="p29_lbl_referenciaVal" class="valorCampo"></label>
							<input type="hidden" id="p29_fld_referenciaVal" value=""></input>
						</div>
						<div class="p29_etiqValorCampoDesc">
							<label id="p29_lbl_descripcion" class="etiquetaCampoNegrita"><spring:message code="p29_popUpCantidadesNoServidasRef.descripcion" /></label>
							<label id="p29_lbl_descripcionVal" class="valorCampo"></label>
							<input type="hidden" id="p29_fld_descripcionVal" value=""></input>
						</div>
						<div class="p29_oculto">
							<input type="hidden" id="p29_fld_tipoOCVal" value=""></input>
							<input type="hidden" id="p29_fld_fechaInicioVal" value=""></input>
							<input type="hidden" id="p29_fld_fechaFinVal" value=""></input>
						</div>
					</fieldset>	
				</div>   
			
				<div id="p29_AreaReferencias">
					<table id="gridp29CantNoServRef"></table>
					<div id="pagerGridp29"></div>
				</div> 
			</div>