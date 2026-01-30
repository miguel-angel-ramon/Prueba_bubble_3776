<script src="./misumi/scripts/p112popupRefConsumoRapido.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script>

		<!--  Contenido página -->
			<div id="p112_popupRefConsumoRapido" title="<spring:message code="p112_popupRefConsumoRapido.title" />" tabindex="0" class="controlReturnP112" style="display:none">
				<div id="p112_AreaBuscador">
					<div class="p112_BloqueBuscador1">
						<div id="p112_area_Dato" class="p112_datoEstructura">
							<div class="p112_label_buscador">
								<label id="p112_lbl_area" class="etiquetaCampo"><spring:message code="general.buscador.area" /></label>
							</div>
							<select id="p112_cmb_area"></select>
						</div>	
						<div id="p112_seccion_Dato" class="p112_datoEstructura">
							<div class="p112_label_buscador">
								<label id="p112_lbl_seccion" class="etiquetaCampo"><spring:message code="general.buscador.seccion" /></label>
							</div>
							<select id="p112_cmb_seccion"></select>
						</div>
					</div>
					<div class="p112_BloqueBuscador2">
						<div id="p112_fechaBloque">
							<div class="textBox">
								<img width="1px" height="170px" style="vertical-align: middle;" src="./misumi/images/blanco.jpg?version=${misumiVersion}">
								<label id="p112_lbl_fecha_inicio" class="etiquetaCampo"><spring:message code="general.campo.fecha_inicio" /></label>
								<div id="p112_fecha_inicio"></div>
							</div>								
						</div>
					</div>
					<div class="p112_BloqueBuscador2">
						<div id="p112_fechaBloque">
							<div class="textBox">
								<img width="1px" height="170px" style="vertical-align: middle;" src="./misumi/images/blanco.jpg?version=${misumiVersion}">
								<label id="p112_lbl_fecha_fin" class="etiquetaCampo"><spring:message code="general.campo.fecha_fin" /></label>
								<div id="p112_fecha_fin"></div>
							</div>								
						</div>
					</div>
				</div>
				<div id="p112_AreaBotones">
					<input type="button" id="p112_btn_excel" class="boton botonHover p112_btn_buscador" value='<spring:message code="general.buscador.excel" />'></input>
					<input type="button" id="p112_btn_buscar" class="boton botonHover p112_btn_buscador" value='<spring:message code="general.buscador.buscar" />'></input>
				</div>
				<div id="p112AreaErrores" style="display: none;">
					<span id="p112_erroresTexto"></span>
				</div>
				<div id="p112_AreaListado" style="display: table;">
					<table id="gridP112" class="gridP112"></table>
					<div id="pagerP112"></div>
				</div>
			</div>
			
			