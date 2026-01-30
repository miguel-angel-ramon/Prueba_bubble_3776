<script src="./misumi/scripts/p64PopUpSelRefPlataforma.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/EncargoClientePlataforma.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p64_popupSelRefPlataforma" title="<spring:message code="p64_popUpSelRefPlataforma.tituloPopup"/>">
				<div id ="p64_componenteFiltro">
<!-- 					<div id="p64_tituloEstructura"></div> -->
					<div id="p64_AreaFiltro">
						<div class="comboBox comboBoxLarge">
							<label id="p64_lbl_seccion" class="etiquetaCampo"><spring:message code="p64_popUpSelRefPlataforma.seccion" /></label>
							<select id="p64_cmb_seccion"></select>
						</div>								
						<div class="comboBox comboBoxLarge">
							<label id="p64_lbl_categoria" class="etiquetaCampo"><spring:message code="p64_popUpSelRefPlataforma.categoria" /></label>
							<select id="p64_cmb_categoria"></select>
						</div>
					</div>
				
			   <!-- <div id="p64_AreaReferencias">
						<table id="gridP64SelRefPlataforma"></table>
						<div id="pagerP64"></div>
					</div>--> 
					<div id="p64_AreaReferencias">
						<div id="p64_componenteFotos">
							<div id="p64_pagAnterior" class="p64_divFlecha"></div>
							<div id="p64_listaFotos"></div>
							<div id="p64_pagSiguiente" class="p64_divFlecha"></div>
						</div>
						<div id="p64_paginacionFotos">
							<div id="p64_paginaActual" class="formPieFotoLabel"></div>					
							<div id="p64_paginaTotal" class="formPieFotoLabel"></div>
						</div>	
						<div id="p64_AreaNotaPie">
							<label id="p64_lbl_notaPie" class="etiquetaCampoNegrita"></label>
						</div>
						<div id="p64_leyenda">
							<div class="p64_leyendaAlerta">
								<div class="p64_leyendaAlertaParteEsfera esferaVerde">
									<div class="p64_textoCentradoHV p64_colorTexto">
										<spring:message code="p64_leyenda.espe"/>
									</div>
								</div>
								<div class="p64_leyendaAlertaParteFlecha"></div>
								<div class=p64_leyendaAlertaParteTexto>
									<div class="p64_textoCentradoV">
										<spring:message code="p64_leyenda.espeDef"/>
									</div>
								</div>
							</div>
							<div class="p64_leyendaAlerta">
								<div class="p64_leyendaAlertaParteEsfera esferaVerde">
									<div class="p64_textoCentradoHV p64_colorTexto">
										<spring:message code="p64_leyenda.vitri"/>
									</div>
								</div>
								<div class="p64_leyendaAlertaParteFlecha"></div>
								<div class="p64_leyendaAlertaParteTexto">
									<div class="p64_textoCentradoV">
										<spring:message code="p64_leyenda.vitriDef"/>
									</div>
								</div>
							</div>
					  	</div>
					</div> 
				</div>
			</div>
<!-- La estructura de las fotos -->
<div id="estructurasP64FotoBuscadorUniversal" style="display:none">
	<div id ="formP64FotoBUEstructura1" class="fotoBUP64">
		<div class="formBloqueFotos">
			<div id="formP64FotoBUEstructura1FotoProducto" class="formBloqueFotoProducto">
				<img id="formP64FotoBUEstructura1FotoProductoImg" class="imagenProducto" src="">
			</div>
			<div class="formBloqueFotosAlertas">
				<div id="formP64FotoBUEstructura1FotoAlerta1" class="fotoAlerta"></div>
				<div id="formP64FotoBUEstructura1FotoAlerta2" class="fotoAlerta"></div>
				<div id="formP64FotoBUEstructura1InfoAlerta1" class="fotoAlerta">
					<div class="p64_textoCentradoHV formUCLabel"><spring:message code="p34_formFoto.UC"/></div>
					<div id="formP64FotoBUEstructura1InfoAlerta1UC" class="p64_textoCentradoHV formFotoLabel"></div>
				</div>	
			</div>			
		</div>
		<div class="formBloqueInfo">
			<label id="formP64FotoBUEstructura1InfoProducto" class="formFotoLabel"></label>
		</div>
		<div style="display:none">
			<div id="formP64FotoBUEstructura1CodArt"></div>
			<div id="formP64FotoBUEstructura1DescArt"></div>
		</div>
	</div>
</div>