<script src="./misumi/scripts/model/QueryReferenciasByDescr.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p34PopUpSelReferencias.js?version=${misumiVersion}" type="text/javascript"></script>

<!--  Contenido página -->
<div id="p34_popupSelReferenciasFoto" title="<spring:message code="p64_popUpSelRefPlataforma.tituloPopup"/>" style="display: none;">
	<div id="p34_componenteFiltroFotos">
		<div id="p34_areaGamaActiva" style="display:none">
			<label id="p34_lbl_gamaActiva"></label>
		</div>
		<div id="p34_AreaFiltroFoto">
			<div class="comboBox comboBoxLarge controlReturnP34">
				<label id="p34_lbl_seccionFoto" class="etiquetaCampo"><spring:message code="p34_popUpSelRefPlataforma.seccion" /></label>
				<select id="p34_cmb_seccionFoto"></select>
			</div>
			<div class="comboBox comboBoxLarge">
				<label id="p34_lbl_categoria" class="etiquetaCampo"><spring:message code="p34_popUpSelRefPlataforma.categoria" /></label>
				<select id="p34_cmb_categoria"></select>
			</div>
			<!-- <div id="p34_tituloEstructuraFoto"></div>-->		
		</div>
		<div id="p34_AreaReferenciasFotos">
				<div id="p34_componenteFotos">
					<div id="p34_pagAnterior" class="p34_divFlecha"></div>
					<div id="p34_listaFotos"></div>
					<div id="p34_pagSiguiente" class="p34_divFlecha"></div>
				</div>
				<div id="p34_paginacionFotos">
					<div id="p34_paginaActual" class="formPieFotoLabel"></div>					
					<div id="p34_paginaTotal" class="formPieFotoLabel"></div>
				</div>	
				<div id="p34_leyenda">
					<div class="p34_leyendaAlerta">
						<div class="p34_leyendaAlertaParteEsfera tick"></div>
						<div class="p34_leyendaAlertaParteFlecha"></div>
						<div class=p34_leyendaAlertaParteTexto>
							<div id="p34_leyendaTxtRefActiva" class="p34_textoCentradoV">
								<spring:message code="p34_leyenda.tickDef"/>
							</div>
						</div>
					</div>
					<div id="p34_leyendaInactiva" class="p34_leyendaAlerta">
						<div class="p34_leyendaAlertaParteEsfera cruz"></div>
						<div class="p34_leyendaAlertaParteFlecha"></div>
						<div class="p34_leyendaAlertaParteTexto">
							<div class="p34_textoCentradoV">
								<spring:message code="p34_leyenda.cruzDef"/>
							</div>
						</div>
					</div>
					<div id="p34_leyendaLot" style = "display:none" class="p34_leyendaAlerta">
						<div class="p34_leyendaAlertaParteEsfera esferaVerde">
							<div class="p34_textoCentradoHV p64_colorTexto">
								<spring:message code="p34_leyenda.lot"/>
							</div>
						</div>
						<div class="p34_leyendaAlertaParteFlecha"></div>
						<div class="p34_leyendaAlertaParteTexto">
							<div class="p34_textoCentradoV">
								<spring:message code="p34_leyenda.lotDef"/>
							</div>
						</div>
					</div>
					<div id="p34_leyendaMP" style = "display:none" class="p34_leyendaAlerta">
						<div class="p34_leyendaAlertaParteEsfera esferaVerde">
							<div class="p34_textoCentradoHV p64_colorTexto">
								<spring:message code="p34_leyenda.mod"/>
							</div>
						</div>
						<div class="p34_leyendaAlertaParteFlecha"></div>
						<div class="p34_leyendaAlertaParteTexto">
							<div class="p34_textoCentradoV">
								<spring:message code="p34_leyenda.modDef"/>
							</div>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>

<div id="p34_popupSelReferencias" title="<spring:message code="p64_popUpSelRefPlataforma.tituloPopup"/>" style="display: none;">
	<div id="p34_AreaFiltro">
		<div id="p34_filtroEstructura">
			<div class="p34_filtroEstructuraField" >
				<div class="comboBox comboBoxLarge controlReturnP34">
					<label id="p34_lbl_seccion" class="etiquetaCampo"><spring:message code="p64_popUpSelRefPlataforma.seccion" /></label>
					<select id="p34_cmb_seccion"></select>
				</div>
			</div>
		</div>			
	</div>
	<div id="p34_AreaReferencias">
		<table id="gridP34SelReferencias"></table>
		<div id="pagerP34"></div>
		
		<table id="gridP34Textil"></table>
		<div id="pagerP34Textil"></div>
	</div> 
</div>

<!-- La estructura de las fotos -->
<div id="estructurasFotoBuscadorUniversal" style="display:none">
	<div id ="formFotoBUEstructura1" class="fotoBU">
		<div class="formBloqueFotos">
			<div id="formFotoBUEstructura1FotoProducto" class="formBloqueFotoProducto">
				<img id="formFotoBUEstructura1FotoProductoImg" class="imagenProducto" src="">
			</div>
			<div class="formBloqueFotosAlertas">
				<div id="formFotoBUEstructura1FotoAlerta1" class="fotoAlerta"></div>
				<div id="formFotoBUEstructura1FotoAlerta2" class="fotoAlerta"></div>
				<div id="formFotoBUEstructura1InfoAlerta1" class="fotoAlerta">
					<!-- <label class=formUCLabel><spring:message code="p34_formFoto.UC"/></label>
					<br>
					<label id="formFotoBUEstructura1InfoAlerta1UC" class=formFotoLabel></label>-->
				</div>	
			</div>			
		</div>
		<div class="formBloqueInfo">
			<label id="formFotoBUEstructura1InfoProducto" class="formFotoLabel"></label>
		</div>
		<div style="display:none">
			<div id="formFotoBUEstructura1CodArt"></div>
			<div id="formFotoBUEstructura1Lote"></div>
			<div id="formFotoBUEstructura1ModeloProveedor"></div>
		</div>
	</div>
</div>