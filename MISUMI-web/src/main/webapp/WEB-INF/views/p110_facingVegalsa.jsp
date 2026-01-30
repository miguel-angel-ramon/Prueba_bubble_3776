<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p110FacingVegalsa.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>


		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p12_altasCatalogo.welcome" /></a></li>
						<li><spring:message code="p110_facingVegalsa.facingVegalsa" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="AreaFiltro">
							
				<div id="p12_filtroEstructura">
					<div class="p12_filtroEstructuraField" >
								<div class="comboBox comboBoxMedium controlReturn">
									<label id="p110_lbl_area" class="etiquetaCampo"><spring:message code="p110_facingVegalsa.area" /></label>
									<select id="p110_cmb_area"></select>
								</div>
								<div class="comboBox comboBoxMedium controlReturn">
									<label id="p110_lbl_seccion" class="etiquetaCampo"><spring:message code="p110_facingVegalsa.section" /></label>
									<select id="p110_cmb_seccion"></select>
								</div>								
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p110_lbl_categoria" class="etiquetaCampo"><spring:message code="p110_facingVegalsa.category" /></label>
									<select id="p110_cmb_categoria"></select>
								</div>
					</div>
					<div class="p12_filtroEstructuraField" >
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p110_lbl_subcategoria" class="etiquetaCampo"><spring:message code="p110_facingVegalsa.subcategory" /></label>
									<select id="p110_cmb_subcategoria"></select>
								</div>
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p110_lbl_segmento" class="etiquetaCampo"><spring:message code="p110_facingVegalsa.segment" /></label>
									<select id="p110_cmb_segmento"></select>
								</div>
					</div>
					<div class="p12_filtroEstructuraField" >
								<div class="comboBox comboBoxShort controlReturn">
									<label id="p110_lbl_mmc" class="etiquetaCampo"><spring:message code="p110_facingVegalsa.mmc" /></label>
									<select id="p110_cmb_mmc"><option value="null" selected="selected"><spring:message code="p110_facingVegalsa.all" /></option><option value="S"><spring:message code="p110_facingVegalsa.si" /></option><option value="N"><spring:message code="p110_facingVegalsa.no" /></option></select>
								</div>
								<div class="comboBox comboBoxShort controlReturn">
									<label id="p110_lbl_catalogo" class="etiquetaCampo" style="margin-left:10px;"><spring:message code="p110_facingVegalsa.catalogo" /></label>
									<select id="p110_cmb_catalogo"><option value="null" selected="selected"><spring:message code="p110_facingVegalsa.all" /></option><option value="A"><spring:message code="p110_facingVegalsa.a" /></option><option value="B"><spring:message code="p110_facingVegalsa.b" /></option></select>
								</div>										
								<div class="comboBox comboBoxShort controlReturn">
									<label id="p110_lbl_tipoAprov" class="etiquetaCampo">&nbsp;&nbsp;&nbsp;<spring:message code="p110_facingVegalsa.tipoAprov" /></label>
									<select id="p110_cmb_tipoAprov"><option value="null" selected="selected"><spring:message code="p110_facingVegalsa.all" /></option><option value="C"><spring:message code="p110_facingVegalsa.c" /></option><option value="G"><spring:message code="p110_facingVegalsa.g" /></option><option value="D"><spring:message code="p110_facingVegalsa.d" /></option></select>
								</div>	
								<div style="display: inline-block;">
									<label id="p110_lbl_facing0" class="etiquetaCampo">&nbsp;&nbsp;&nbsp;<spring:message code="p110_facingVegalsa.facing0" /></label>
									<input id="p110_chk_facing0" type="checkbox" onchange="javascript:habilitarChecksFiltro();"/> 
								</div>										
					</div>
					<div class="p12_filtroEstructuraField">
								<div style="display: inline-block;width: 260px;">
									<input id="p110_chk_fondoAlimentado" type="checkbox"/> 
									<label id="p110_lbl_fondoAlimentado" class="etiquetaCampo">&nbsp;<spring:message code="p110_facingVegalsa.fondoAlimentado" /></label>
								</div>
								<div style="display: inline-block;width: 200px;">
									<input id="p110_chk_conStock" type="checkbox"/>
									<label id="p110_lbl_conStock" class="etiquetaCampo">&nbsp;<spring:message code="p110_facingVegalsa.conStock" /></label> 
								</div>
								<div style="display: inline-block;width: 290px;">
									<input id="p110_chk_gama" type="checkbox" onchange="javascript:comprobarComboMMC();"/> 
									<label id="p110_lbl_gama" class="etiquetaCampo">&nbsp;<spring:message code="p110_facingVegalsa.gama" /></label>
								</div>
								
					</div>
					
					
										
				</div>			
				
				<div class="p110_filterButtons">
									<input type="button" id="p110_btn_buscar" class="boton  botonHover" value='<spring:message code="p12_altasCatalogo.find" />'></input>
									<input type="button" id="p110_btn_exportExcel" class="boton  botonHover" value='<spring:message code="p12_altasCatalogo.excel" />'></input>
									<input type="button" id="p110_btn_reset" class="boton  botonHover" value='<spring:message code="p12_altasCatalogo.reset" />'></input>
								</div>	
				

			</div>
			
			<div id=AreaResultados  style="display: none;">
				<table id="gridP110"></table>
				<div id="pagerP110"></div>
			</div>
			
		
		</div>		
		<div id="excellPopup"></div>
		<div id="excellWindow"></div>	
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>