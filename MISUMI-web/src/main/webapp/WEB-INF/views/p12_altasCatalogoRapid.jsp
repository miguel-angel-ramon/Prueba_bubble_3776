<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p12AltasCatalogoRapid.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ArtGamaRapid.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>


		<!--  Miga de pan --> 
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p12_altasCatalogoRapid.welcome" /></a></li>
						<li><spring:message code="p12_altasCatalogoRapid.altasCatalogo" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="AreaFiltro">
						
				<div id="p12_rapid_filtroEstructura">
					<div class="p12_filtroEstructuraField" >
						<div class="comboBox comboBoxMedium controlReturn">
							<label id="p12_lbl_area" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.area" /></label>
							<select id="p12_cmb_area"></select>
						</div>
						<div class="comboBox comboBoxMedium controlReturn">
							<label id="p12_lbl_seccion" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.section" /></label>
							<select id="p12_cmb_seccion"></select>
						</div>								
						<div class="comboBox comboBoxLarge controlReturn">
							<label id="p12_lbl_categoria" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.category" /></label>
							<select id="p12_cmb_categoria"></select>
						</div>
					</div>
					<div class="p12_filtroEstructuraField" >
						<div class="comboBox comboBoxLarge controlReturn">
							<label id="p12_lbl_subcategoria" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.subcategory" /></label>
							<select id="p12_cmb_subcategoria"></select>
						</div>
						<div class="comboBox comboBoxLarge controlReturn">
							<label id="p12_lbl_segmento" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.segment" /></label>
							<select id="p12_cmb_segmento"></select>
						</div>
					</div>																									
				</div>	
						
				<div id="p12_filtroReferencia" style="display:none;">
					<div style="text-align: left; padding:5px">
						<label id="p12_lbl_referencia" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.reference" /></label>
						<input class="controlReturn" type=text id="p12_fld_referencia" class="input250"></input>
					</div>
				</div>		
				
				<div id="p12_filterButtons">
					<div class="p12_tipoBusquedaField">
						<input type="radio" class="controlReturn" name="p12_rad_tipoFiltro" id="p12_rad_tipoFiltro" value="1" checked="checked" />
						<label id="p12_lbl_radioEstructura" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.comertialStructureAbr" /></label>
						<input type="radio" class="controlReturn" name="p12_rad_tipoFiltro" id="p12_rad_tipoFiltro" value="2"/>
						<label id="p12_lbl_radioReferencia" class="etiquetaCampo"><spring:message code="p12_altasCatalogoRapid.referenceAbr" /></label>
					</div>	
					<input type="button" id="p12_btn_buscar" class="boton  botonHover" value='<spring:message code="p12_altasCatalogoRapid.find" />'></input>
					<input type="button" id="p12_btn_exportExcel" class="boton  botonHover" value='<spring:message code="p12_altasCatalogoRapid.excel" />'></input>
					<input type="button" id="p12_btn_reset" class="boton  botonHover" value='<spring:message code="p12_altasCatalogoRapid.reset" />'></input>				
				</div>
			</div>
			
			<div id=AreaResultados  style="display: none;">
				<table id="gridP12"></table>
				<div id="pagerP12"></div>		
			</div>
			
		
		</div>		
		<div id="excellPopup"></div>
		<div id="excellWindow"></div>	
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>