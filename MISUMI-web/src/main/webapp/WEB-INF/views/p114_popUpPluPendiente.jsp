<script src="./misumi/scripts/p114PopUpPluPendiente.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
	<div id="p114_popupPluPendiente" title="<spring:message code="p114_popUpPluPendiente.tituloPopup"/>">
		<div id="p114_AreaDatos">
			<div id="p114_BloqueCodArt" class="p114_BloqueCodArt">
				<div class="p114_label_buscador">
					<label id="p114_lbl_codArt" class="etiquetaCampo p114_bold"><spring:message code="p114_popUpPluPendiente.referencia" /></label>
					<input class="p114_box_CodArt" id="p114_codArtVal" type="text" disabled="disabled"></input>
				</div>
			</div>
			<div id="p114_BloqueDescArt" class="p114_BloqueDescArt">
				<div class="p114_label_buscador">
					<label id="p114_lbl_descArt" class="etiquetaCampo p114_bold"><spring:message code="p114_popUpPluPendiente.denominacion" /></label>
					<input class="p114_box_DescArt" id="p114_descArtVal" type="text" disabled="disabled"></input>
				</div>
			</div>
			<div id="p114_div_mensajeErrorGuardado" class="p114_BloqueMensajeErrorGuardado">
				<label id="p114_lbl_mensajeErrorGuardado" class="p114_lbl_mensajeErrorGuardado p114_label_rojo"></label>
			</div>
			<div id="p114_BloqueAgrupacionBalanza" class="p114_BloqueAgrupacionBalanza">
				<div class="p114_label_buscador">
					<label id="p114_lbl_agrupacionBalanza" class="etiquetaCampo p114_bold"><spring:message code="p114_popUpPluPendiente.agrupacion" /></label>
					<label id="p114_agrupacionBalanza" class="p114_agrupacionBalanza"></label>
				</div>
			</div>
			<div id="p114_BloquePLUsLibres" class="p114_BloquePLUsLibres">
				<div class="p114_label_buscador">
					<label class="etiquetaCampo p114_bold"><spring:message code="p114_popUpPluPendiente.free_plus1" /></label>
					<label class="etiquetaCampo p114_bold"><a id="p114_plus_libres" href="#"></a></label>
					<label class="etiquetaCampo p114_bold"><spring:message code="p114_popUpPluPendiente.free_plus4" /></label>
					<input class='p114_box p114_center p114_box_sin_margin' id="p114_plu_max_libre" name="p114_plu_max_libre" type="text" disabled="disabled"/>
					<input id="p114_plu_max_libre_copia" type="hidden"/>
					<input id="p114_plu_max_asignado" type="hidden"/>
				</div>
			</div>
			<div id="p114_BloquePlu" class="p114_BloquePlu">
				<div class="p114_label_buscador">
					<label id="p114_lbl_plu_propuesto" class="etiquetaCampo"><spring:message code="p114_popUpPluPendiente.free_plus2" /></label>
					<input id="p114_plu_propuesto" type="text" class='p114_box p114_center'/>
					<label id="p114_mensaje_plu_propuesto" class="etiquetaCampo p114_bold p107_yellow p114_hidden"></label>
				</div>
			</div>
			<div class="p114_BloqueNavegacion">
				<div class="p114_componenteFlechas">
					<div id="p114_pagAnterior">
						<img src="./misumi/images/pager_prev_24.gif?version=">
					</div>
					<div id="p114_indiceLista"><label id="p114_lbl_indiceLista"></label></div>
					<div id="p114_pagSiguiente">
						<img src="./misumi/images/pager_next_24.gif?version=">
					</div>
				</div>	
			</div>	
		</div>	
	</div>
	
	