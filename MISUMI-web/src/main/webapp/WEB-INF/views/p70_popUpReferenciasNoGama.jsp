<!--  Contenido página -->

<div id="popUpReferenciasNoGama" style="display: none">
	<div id="p70_componente_referencias_no_gama">
		<div id="p70_componente_referencias_no_gama_area_filtros">

			<label for="p70_rng_txt_referencia" class="etiquetaCampo"> 
				<spring:message code="p70_popUpReferenciasNoGama.referencia" />
			</label> 
			<input type="text" id="p70_rng_txt_referencia" class="input525" value="">
			<input type="button" id="p70_rng_btn_buscar" class="boton botonHover" value="Buscar">
		</div>

		<div id="p70_AreaReferencias">
			<div id="p70_componenteFotos">
				<div id="p70_pagAnterior" class="p70_divFlecha p70_pagAnt_Des"></div>
				<div id="p70_gridRng"></div>
				<div id="p70_pagSiguiente" class="p70_divFlecha p70_pagSig_Des"></div>
			</div>
			<div id="p70_paginacionFotos">
				<div id="p70_paginaActual" class="formPieFotoLabel">0</div>
				<div id="p70_paginaTotal" class="formPieFotoLabel">/0</div>
				<input id="p70_currentPage" type="hidden">
				<input id="p70_totalPage" type="hidden">
			</div>

		</div>
	</div>
</div>
