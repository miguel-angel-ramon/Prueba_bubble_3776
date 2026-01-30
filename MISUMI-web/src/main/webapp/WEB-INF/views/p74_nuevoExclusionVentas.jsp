<script src="./misumi/scripts/p74NuevoExclusionVentas.js" type="text/javascript"></script> 
<script src="./misumi/scripts/model/DiasServicio.js" type="text/javascript"></script>
		<!--  Contenido página -->
		<div id="p74_AreaNuevo" title="<spring:message code="p74_nuevoExclusionVentas.titulo"/>" tabindex="0">
			<div id="p74_AreaEstructuraReferencia" class="p74_AreaEstructuraReferencia">
				<div id="p74_AreaBloque1">
					<fieldset id="p74_estructuraReferenciaFieldset"><legend id="p74_estructuraReferenciaLegend"><spring:message code="p74_nuevoExclusionVentas.titBloque1Estructura" /></legend>
						<div id="p74_AreaBloque1_1">
							<div id="p74_tipoBusqListado">
								<input type="radio" class="controlReturn" name="p74_rad_tipoFiltro" id="p74_rad_tipoFiltro" value="1" checked="checked" />
								<label id="p74_lbl_radioEstructura" class="etiquetaCampo" title="<spring:message code='p74_nuevoExclusionVentas.comertialStructure' />"><spring:message code="p74_nuevoExclusionVentas.comertialStructureAbrev" /></label>
								<input type="radio" class="controlReturn" name="p74_rad_tipoFiltro" id="p74_rad_tipoFiltro" value="2"/>
								<label id="p74_lbl_radioReferencia" class="etiquetaCampo" title="<spring:message code='p74_nuevoExclusionVentas.reference' />"><spring:message code="p74_nuevoExclusionVentas.referenceAbrev" /></label>
							</div>				
							<div id="p74_filtroEstructura">
								<div class="p74_filtroEstructuraField" >
									<div class="comboBox comboBoxMedium controlReturn">
										<label id="p74_lbl_area" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.area" /></label>
										<select id="p74_cmb_area"></select>
									</div>
									<div class="comboBox comboBoxMedium controlReturn">
										<label id="p74_lbl_seccion" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.section" /></label>
										<select id="p74_cmb_seccion"></select>
									</div>								
									<div class="comboBox comboBoxMedium controlReturn">
										<label id="p74_lbl_categoria" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.category" /></label>
										<select id="p74_cmb_categoria"></select>
									</div>
									<div class="comboBox comboBoxMedium controlReturn">
										<label id="p74_lbl_subcategoria" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.subcategory" /></label>
										<select id="p74_cmb_subcategoria"></select>
									</div>
									<div class="comboBox comboBoxMedium controlReturn">
										<label id="p74_lbl_segmento" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.segment" /></label>
										<select id="p74_cmb_segmento"></select>
									</div>
								</div>
							</div>			
							<div id="p74_filtroReferencia" style="display:none;">
								<div class="textBox controlReturn">
									<label id="p74_lbl_referencia" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.reference" /></label>
									<input class="input200 controlReturn" type=text id="p74_fld_referencia"></input>
								</div>
							</div>			
						</div>
						<div id="p74_AreaBloque1_2">
							<div id="p74_imagenFlechaDiv">
								<img id="p74_imagenFlecha" src="./misumi/images/arrow_right_48.png">
							</div>
						</div>
						<div id="p74_AreaBloque1_3">
							<div id="p74_TablaEstructuraDiv">
								<fieldset id="p74_estructuraMultipleFieldset"><legend id="p74_estructuraMultipleLegend"><spring:message code="p74_nuevoExclusionVentas.titBloque1Estructura" /></legend>
    								<div id="p74_TablaEstructuraMarcarTodosDiv">
    									<input type="checkbox" id="p74_chk_selectAll"/><label id="p74_lbl_referencia" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.marcarTodos" /></label>
    								</div>	
									<div id="p74_TablaEstructuraMultipleDiv">
										<table id="p74_table_Estructura">
										</table>
									</div>	
								</fieldset>	
							</div>
						</div>
					</fieldset>	
				</div>
									
				<div id="p74_AreaBloque2">
					<fieldset id="p74_fechaFieldset">
						<legend><spring:message code="p74_nuevoExclusionVentas.titBloque2" /></legend>
						<div id="p74_fechaBloque">
							<div class="textBox">
								<label id="p74_lbl_fecha" class="etiquetaCampo"><spring:message code="p74_nuevoExclusionVentas.fecha" /></label>
								<div id="p74_fechaDatePicker"></div>
							</div>								
						</div>
					</fieldset>
				</div>
			</div>	
		</div> 
