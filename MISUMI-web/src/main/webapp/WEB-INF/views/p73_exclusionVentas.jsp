<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p73ExclusionVentas.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/DiasServicio.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ExclusionVentas.js?version=${misumiVersion}" type="text/javascript"></script>

		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p73_exclusionVentas.welcome" /></a></li>
						<li>
							<spring:message code="p73_exclusionVentas.exclusionVentas" />
						</li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="p73_Area" style="display:none;">
			<div id="p73_AreaNuevo">
				<div id="p73_AreaEstructuraReferencia">
					<fieldset id="p73_estructuraReferenciaFieldset"><legend id="p73_estructuraReferenciaLegend"><spring:message code="p73_exclusionVentas.titBloque1Estructura" /></legend>
						<div id="p73_AreaEstructuraReferencia_1">
							<div id="p73_tipoBusqListado">
								<input type="radio" class="controlReturn" name="p73_rad_tipoFiltro" id="p73_rad_tipoFiltro" value="1" checked="checked" />
								<label id="p73_lbl_radioEstructura" class="etiquetaCampo" title="<spring:message code='p73_exclusionVentas.comertialStructure' />"><spring:message code="p73_exclusionVentas.comertialStructureAbrev" /></label>
								<input type="radio" class="controlReturn" name="p73_rad_tipoFiltro" id="p73_rad_tipoFiltro" value="2"/>
								<label id="p73_lbl_radioReferencia" class="etiquetaCampo" title="<spring:message code='p73_exclusionVentas.reference' />"><spring:message code="p73_exclusionVentas.referenceAbrev" /></label>
							</div>				
							<div id="p73_filtroEstructura">
								<div class="p73_filtroEstructuraField" >
									<div class="p73_comboBox controlReturn">
										<div id="p73_area_Dato" class="p73_datoEstructura">
											<label id="p73_lbl_area" class="etiquetaCampo"><spring:message code="p73_exclusionVentas.area" /></label>
											<select id="p73_cmb_area"></select>
										</div>	
										<div id="p73_area_Error" class="p73_errorEstructura">
											<img id="p73_area_imgError" class="p73_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
										</div>
									</div>
									<div class="p73_comboBox controlReturn">
										<div id="p73_seccion_Dato" class="p73_datoEstructura">
											<label id="p73_lbl_seccion" class="etiquetaCampo"><spring:message code="p73_exclusionVentas.section" /></label>
											<select id="p73_cmb_seccion"></select>
										</div>
										<div id="p73_seccion_Error" class="p73_errorEstructura">
											<img id="p73_seccion_imgError" class="p73_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
										</div>
									</div>								
									<div class="p73_comboBox controlReturn">
										<div id="p73_categoria_Dato" class="p73_datoEstructura">
											<label id="p73_lbl_categoria" class="etiquetaCampo"><spring:message code="p73_exclusionVentas.category" /></label>
											<select id="p73_cmb_categoria"></select>
										</div>	
										<div id="p73_categoria_Error" class="p73_errorEstructura">
											<img id="p73_categoria_imgError" class="p73_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
										</div>
									</div>
									<div class="p73_comboBox controlReturn">
										<div id="p73_subcategoria_Dato" class="p73_datoEstructura">
											<label id="p73_lbl_subcategoria" class="etiquetaCampo"><spring:message code="p73_exclusionVentas.subcategory" /></label>
											<select id="p73_cmb_subcategoria"></select>
										</div>	
										<div id="p73_subcategoria_Error" class="p73_errorEstructura">
											<img id="p73_subcategoria_imgError" class="p73_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
										</div>
									</div>
									<div class="p73_comboBox controlReturn">
										<div id="p73_segmento_Dato" class="p73_datoEstructura">
											<label id="p73_lbl_segmento" class="etiquetaCampo"><spring:message code="p73_exclusionVentas.segment" /></label>
											<select id="p73_cmb_segmento"></select>
										</div>	
										<div id="p73_segmento_Error" class="p73_errorEstructura">
											<img id="p73_segmento_imgError" class="p73_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
										</div>
									</div>
								</div>
							</div>			
							<div id="p73_filtroReferencia" style="display:none;">
								<div class="p73_textBox controlReturn">
									<div id="p73_segmento_Dato" class="p73_datoEstructura">
										<label id="p73_lbl_referencia" class="etiquetaCampo"><spring:message code="p73_exclusionVentas.reference" /></label>
										<input class="input200 controlReturn" type=text id="p73_fld_referencia"></input>
									</div>
									<div id="p73_referencia_Error" class="p73_errorEstructura">
										<img id="p73_referencia_imgError" class="p73_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
									</div>
								</div>
							</div>			
						</div>
						<div id="p73_AreaEstructuraReferencia_2">
							<div id="p73_imagenFlechaDiv">
								<img id="p73_imagenFlecha" src="./misumi/images/arrow_right_48.png?version=${misumiVersion}">
							</div>
						</div>
						<div id="p73_AreaEstructuraReferencia_3">
							<div id="p73_TablaEstructuraDiv">
								<fieldset id="p73_estructuraMultipleFieldset"><legend id="p73_estructuraMultipleLegend"><spring:message code="p73_exclusionVentas.titBloque1Estructura" /></legend>
									<div id="p73_TablaEstructuraMultipleDiv">
										<table id="p73_table_Estructura">
										</table>
									</div>	
								</fieldset>	
							</div>
						</div>
					</fieldset>	
				</div>
									
				<div id="p73_AreaFecha">
					<fieldset id="p73_fechaFieldset">
						<legend><spring:message code="p73_exclusionVentas.titBloque2" /></legend>
						<div id="p73_fechaBloque">
							<div class="textBox">
								<img width="1px" height="170px" style="vertical-align: middle;" src="./misumi/images/blanco.jpg?version=${misumiVersion}">
								<label id="p73_lbl_fecha" class="etiquetaCampo"><spring:message code="p73_exclusionVentas.fecha" /></label>
								<div id="p73_fechaDatePicker"></div>
							</div>								
						</div>
					</fieldset>
				</div>
			</div>

			<div id="p73_AreaBotones">
				<div id="p73_estructuraBotones">
					<div id="p73_buttons">
						<c:if test="${user.perfil != 3}">
							<input type="button" id="p73_btn_save" class="boton  botonHover" value='<spring:message code="p73_exclusionVentas.save" />'></input>
							<input type="button" id="p73_btn_delete" class="boton  botonHover" value='<spring:message code="p73_exclusionVentas.delete" />'></input>
						</c:if>
					</div>
				</div>
			</div>
			<div id="p73AreaErrores" style="display: none;">
				<span id="p73_erroresTexto"></span>
			</div>
			<div id="p73_AreaResultados" class="p73_AreaResultados">
				<table id="gridP73ExclusionVentas" class="gridP73"></table>
				<div id="pagerP73ExclusionVentas"></div>
				<!--  Variables para el calendario con días de servicio -->
				<input type="hidden" id="codCentroCalendario" value=""></input>
				<input type="hidden" id="codArticuloCalendario" value=""></input>
				<input type="hidden" id="identificadorCalendario" value=""></input>
				<input type="hidden" id="identificadorSIACalendario" value=""></input>
				<input type="hidden" id="clasePedidoCalendario" value=""></input>
				<input type="hidden" id="recargarParametrosCalendario" value=""></input>
				<input type="hidden" id="cargadoDSCalendario" value=""></input>
				<input type="hidden" id="esFresco" value=""></input>
			</div>
		</div>
		</div>		
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>