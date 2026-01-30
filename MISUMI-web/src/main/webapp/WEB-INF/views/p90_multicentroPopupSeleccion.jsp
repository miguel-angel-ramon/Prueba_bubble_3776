<script src="./misumi/scripts/p90MulticentroPopupSeleccion.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/Region.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Centro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Intertienda.js?version=${misumiVersion}" type="text/javascript"></script>



<!--  Contenido página -->
<div id="p90_multicentrosPopupSeleccion" title="<spring:message code="p90_header.multiCentro" />" class="controlReturnP90" style="display:none">
		
	<div id="p90_areaCentroRegionZona">
		<div id="p90_areaCentro">
				<label for="p90_rad_centro" id="p90_lbl_centro" class="etiquetaCampo">Centro</label>
				<input type="text" id="p90_fld_centro"  autocomplete="off"><span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span>
			<input type="hidden" id="p90_fld_centroId" value="">
			<div id="p90_areaRegionZona">
				<div id="p90_filtroRegion" class="comboBox">
					<label for="p90_rad_regionZona" id="p90_lbl_region" class="etiquetaCampo">Región</label>
					<select id="p90_cmb_region"></select>
					<label for="p90_rad_regionZona" id="p90_lbl_zona" class="etiquetaCampo">Zona</label>
					<select id="p90_cmb_zona"></select>
				</div>
			</div>
	</div>
	
	<div id="p90_areaFlecha">
		<img id="p90_flecha" src="./misumi/images/arrow_right_green_70.png?version=${misumiVersion}" />
	</div>
	
	<div id="p90_areaListaCentros">
			<div id="listaCentros">
				<table>	
					<tbody>
					</tbody> 
				</table>
			</div>
			
			<div id="p90AreoBotones">
				<input type="button" id="p90_btn_aceptar" class="boton  botonHover" value="Aceptar">
				<input type="button" id="p90_btn_limpiar" class="boton  botonHover" value="Limpiar">
			</div>
		</div>
			
	</div>
	
</div>
			
			