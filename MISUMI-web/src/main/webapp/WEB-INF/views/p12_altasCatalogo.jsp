<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p12AltasCatalogo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VArtCentroAlta.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>


		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p12_altasCatalogo.welcome" /></a></li>
						<li><spring:message code="p12_altasCatalogo.altasCatalogo" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="AreaFiltro">
				<div class="p12_tipoBusqListado">
					<div class="p12_tipoListadoField">
					<fieldset> <legend><spring:message code="p12_altasCatalogo.listType" /></legend>
						<input type="radio" class="controlReturn" name="p12_rad_tipoListado" id="p12_rad_tipoListado" value="1" checked="checked" />
						<label id="p12_lbl_radioDatosGen" class="etiquetaCampo" style="margin-right:20px;"><spring:message code="p12_altasCatalogo.generalData" /></label>
						<input type="radio" class="controlReturn" name="p12_rad_tipoListado" id="p12_rad_tipoListado" value="2"/>
						<label id="p12_lbl_radioSFM" class="etiquetaCampo" style="margin-right: 20px;"><spring:message code="p12_altasCatalogo.SFM" /></label>
						<input type="radio" class="controlReturn" name="p12_rad_tipoListado" id="p12_rad_tipoListado" value="3" radDesTextil="S"/>
						<label id="p12_lbl_radioDesglose" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.desgloseTextil" /></label>
					</fieldset>
					</div>
				</div>				
				<div id="p12_filtroEstructura">
					<div class="p12_filtroEstructuraField" >
								<div class="comboBox comboBoxMedium controlReturn">
									<label id="p12_lbl_area" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.area" /></label>
									<select id="p12_cmb_area"></select>
								</div>
								<div class="comboBox comboBoxMedium controlReturn">
									<label id="p12_lbl_seccion" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.section" /></label>
									<select id="p12_cmb_seccion"></select>
								</div>								
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p12_lbl_categoria" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.category" /></label>
									<select id="p12_cmb_categoria"></select>
								</div>
					</div>
					<div class="p12_filtroEstructuraField" >
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p12_lbl_subcategoria" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.subcategory" /></label>
									<select id="p12_cmb_subcategoria"></select>
								</div>
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p12_lbl_segmento" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.segment" /></label>
									<select id="p12_cmb_segmento"></select>
								</div>
					</div>
					<div class="p12_filtroEstructuraField" >
						<div class="comboBox comboBoxMediumExtraShort controlReturn">
							<label id="p12_lbl_mmc" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.mmc" /></label>
							<select id="p12_cmb_mmc"><option value="null" selected="selected"><spring:message code="p12_altasCatalogo.all" /></option><option value="S"><spring:message code="p12_altasCatalogo.si" /></option><option value="N"><spring:message code="p12_altasCatalogo.no" /></option></select>
								
							<label id="p12_lbl_pedir" class="etiquetaCampo" style="margin-left:10px;"><spring:message code="p12_altasCatalogo.apply" /></label>
							<select id="p12_cmb_pedir"><option value="null" selected="selected"><spring:message code="p12_altasCatalogo.all" /></option><option value="S"><spring:message code="p12_altasCatalogo.si" /></option><option value="N"><spring:message code="p12_altasCatalogo.no" /></option></select>
							<img id="p12_btn_ayudaActiva" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}" class="botonAyuda" title="<spring:message code="p12_altasCatalogo.ayuda" />"/>

							<label id="p12_lbl_catalogo" class="etiquetaCampo" style="margin-left:10px;"><spring:message code="p12_altasCatalogo.catalogo" /></label>
							<select id="p12_cmb_catalogo"><option value="null" selected="selected"><spring:message code="p12_altasCatalogo.all" /></option><option value="A"><spring:message code="p12_altasCatalogo.alta" /></option><option value="B"><spring:message code="p12_altasCatalogo.baja" /></option></select>

							<!-- Checkbox "Facing 0" -->
<!-- 
							<label id="p12_lbl_facingCero" class="etiquetaCampo"  style="margin-left:10px;" title="<spring:message code='p12_altasCatalogo.facingCero'/>">
								<spring:message code="p12_altasCatalogo.facingCero"/>
							</label>
							<input id="p12_chk_facingCero" type="checkbox" class="controlReturnP40" />
 -->
 							
							<div id="p12_div_activableFacing" class="comboBox controlReturn">
								<label id="p12_lbl_activableFacing" class="etiquetaCampo">&nbsp;&nbsp;&nbsp;<spring:message code="p12_altasCatalogo.activableFacing" /></label>
								<select id="p12_cmb_activableFacing"><option value="null" selected="selected"><spring:message code="p12_altasCatalogo.all" /></option><option value="S"><spring:message code="p12_altasCatalogo.si" /></option><option value="N"><spring:message code="p12_altasCatalogo.no" /></option></select>
							</div>

							<div id="p12_div_loteSN" class="comboBox controlReturn">
								<label id="p12_lbl_loteSN" class="etiquetaCampo">&nbsp;&nbsp;&nbsp;<spring:message code="p12_altasCatalogo.loteSN" /></label>
								<select id="p12_cmb_loteSN"><option value="null" selected="selected"><spring:message code="p12_altasCatalogo.all" /></option><option value="S"  selected="selected"><spring:message code="p12_altasCatalogo.si" /></option><option value="N"><spring:message code="p12_altasCatalogo.no" /></option></select>
							</div>										
						</div>
						
					</div>
										
				</div>			
				<div id="p12_filtroReferencia" style="display:none;">
					<div style="text-align: left; padding:5px">
						<label id="p12_lbl_referencia" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.reference" /></label>
						<input class="controlReturn" type=text id="p12_fld_referencia" class="input250"></input>
					</div>
				</div>		

				<div id="p12_filterButtons">
					<div class="p12_tipoBusquedaField">
						<input type="radio" class="controlReturn" name="p12_rad_tipoFiltro" id="p12_rad_tipoFiltro" value="1" checked="checked" />
						<label id="p12_lbl_radioEstructura" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.comertialStructureAbr" /></label>
						<input type="radio" class="controlReturn" name="p12_rad_tipoFiltro" id="p12_rad_tipoFiltro" value="2"/>
						<label id="p12_lbl_radioReferencia" class="etiquetaCampo"><spring:message code="p12_altasCatalogo.referenceAbr" /></label>
					</div>	
					<input type="button" id="p12_btn_buscar" class="boton  botonHover" value='<spring:message code="p12_altasCatalogo.find" />'></input>
					<input type="button" id="p12_btn_exportExcel" class="boton  botonHover" value='<spring:message code="p12_altasCatalogo.excel" />'></input>
					<input type="button" id="p12_btn_reset" class="boton  botonHover" value='<spring:message code="p12_altasCatalogo.reset" />'></input>
					
				</div>

			</div>
			
			<div id=AreaResultados  style="display: none;">
				<table id="gridP12"></table>
				<div id="pagerP12"></div>
				<table id="gridP12Textil"></table>
				<div id="pagerP12Textil"></div>
			
			</div>

		</div>		
		<div id="excellPopup"></div>
		<div id="excellWindow"></div>	
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>