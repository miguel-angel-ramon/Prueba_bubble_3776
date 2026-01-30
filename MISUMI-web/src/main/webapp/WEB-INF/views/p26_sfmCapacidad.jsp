<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>      
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p26sfmCapacidad.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerParamSfmcap.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VArtSfm.js?version=${misumiVersion}t" type="text/javascript"></script>
<script src="./misumi/scripts/model/MotivoTengoMuchoPoco.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Motivo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>


		<!--  Miga de pan --> 
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p26_sfmCapacidad.welcome" /></a></li>
						<li>
							<c:choose>
								<c:when test="${user.centro.flgFacing == 'S'}">
								<spring:message code="p26_sfmCapacidad.SFMFAC" />
								</c:when>
								<c:when test="${user.centro.flgCapacidad == 'S'}">
									<spring:message code="p26_sfmCapacidad.SFMCAP" />
								</c:when>
								<c:otherwise>
									<spring:message code="p26_sfmCapacidad.SFM" />
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="AreaFiltro">
				<div id="p26_filtroEstructura">
					<div class="p26_filtroEstructuraField" >
								<div class="comboBox comboBoxMedium controlReturn">
									<label id="p26_lbl_area" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.area" /></label>
									<select id="p26_cmb_area"></select>
								</div>
								<div class="comboBox comboBoxMedium controlReturn">
									<label id="p26_lbl_seccion" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.section" /></label>
									<select id="p26_cmb_seccion"></select>
								</div>								
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p26_lbl_categoria" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.category" /></label>
									<select id="p26_cmb_categoria"></select>
								</div>
					</div>
					<div class="p26_filtroEstructuraField" >
								<div class="comboBox comboBoxLarge controlReturn">
									<label id="p26_lbl_subcategoria" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.subcategory" /></label>
									<select id="p26_cmb_subcategoria"></select>
								</div>
								<div class="comboBox comboBox200 controlReturn">
									<label id="p26_lbl_segmento" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.segment" /></label>
									<select id="p26_cmb_segmento"></select>
								</div>
								
					</div>
					<div class="p26_filtroEstructuraField" >
								<div id="p26_div_mmc" class="comboBox comboBoxExtraShort controlReturn" style="display:none;">
									<label id="p26_lbl_mmc" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.mmc" /></label>
									<select id="p26_cmb_mmc"><option value="null" selected="selected"><spring:message code="p26_sfmCapacidad.all" /></option><option value="S"><spring:message code="p26_sfmCapacidad.si" /></option><option value="N"><spring:message code="p26_sfmCapacidad.no" /></option></select>
								</div>										
								<div class="comboBox comboBox50 controlReturn">
									<label id="p26_lbl_pedir" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.apply" /></label>
									<select id="p26_cmb_pedir">
										<option value="null"><spring:message code="p26_sfmCapacidad.all" /></option><option value="S"><spring:message code="p26_sfmCapacidad.si" /></option><option value="N"><spring:message code="p26_sfmCapacidad.no" /></option>
									</select>
									<img id="p26_btn_ayudaActiva" class="botonAyuda" style="display:none;" title="Ayuda" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}">
								</div>
								<div id= "p26_div_loteSN" class="comboBox comboBox50 controlReturn">
									<label id="p26_lbl_loteSN" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.loteSN" /></label>
									<select id="p26_cmb_loteSN">
										<option value="null"><spring:message code="p26_sfmCapacidad.all" /></option><option value="S"><spring:message code="p26_sfmCapacidad.si" /></option><option value="N"><spring:message code="p26_sfmCapacidad.no" /></option>
									</select>
								</div>
					</div>
				</div>			
				<div class="p26_filtroEstructuraField" >
					<div id="p26_filtroReferencia" style="display:none;">
						<div style="text-align: left; padding:5px">
							<label id="p26_lbl_referencia" class="etiquetaCampo"><spring:message code="p26_sfmCapacidad.reference" /></label>
<!-- 							<input type=text id="p26_fld_referencia" class="input250 controlReturn"></input> -->
							<input type=text id="p26_fld_referencia" class="input250"/>
							<input id="p26_txt_flgStock" type="hidden"/>
							<input id="p26_txt_flgCapacidad" type="hidden"/>
							<input id="p26_txt_flgFacing" type="hidden"/>
							<input id="p26_txt_flgFacingCapacidad" type="hidden"/>
						</div>
					</div>			
					<div id="p26_filterButtons">
						<div class="p26_tipoBusqListadoField">
							<input type="radio" class="controlReturn" name="p26_rad_tipoFiltro" id="p26_rad_tipoFiltro" value="1" checked="checked" />
							<label id="p26_lbl_radioEstructura" class="etiquetaCampo" title="<spring:message code='p70_detalladoPedido.comertialStructure' />"><spring:message code="p26_sfmCapacidad.comertialStructureAbrev" /></label>
							<input type="radio" class="controlReturn" name="p26_rad_tipoFiltro" id="p26_rad_tipoFiltro" value="2"/>
							<label id="p26_lbl_radioReferencia" class="etiquetaCampo" title="<spring:message code='p70_detalladoPedido.reference' />"><spring:message code="p26_sfmCapacidad.referenceAbrev" /></label>
						</div>
						<input type="button" id="p26_btn_buscar" class="boton  botonHover" value='<spring:message code="p26_sfmCapacidad.find" />'></input>		
						<input type="button" id="p26_btn_excel" class="boton botonNarrow  botonHover" value='<spring:message code="p26_sfmCapacidad.excel" />'></input>
						<c:if test="${user.perfil != 3}">	
							<input type="button" id="p26_btn_save" class="boton  botonHover" value='<spring:message code="p26_sfmCapacidad.save" />'></input>
						</c:if>
						<input type="button" id="p26_btn_reset" class="boton  botonHover" value='<spring:message code="p26_sfmCapacidad.reset" />'></input>
					</div>
				</div>

			</div>
			
			
<!-- 			<div id="" style="display:inline-block; border:1px solid #888888;margin-bottom:-5px"> -->
				
<!-- 				<img style="float:left" id="imgError" src="./misumi/images/dialog-error-24.png?version=" title=""> -->
<!-- 				<div style="padding:5px;display:inline-block" id="XX">SOLO SE MOSTRARAN LOS HUECOS CONSULTANDO A NIVEL DE SEGMENTO</div> -->
<!-- 			</div> -->
		
		<div id="p26_Area_mensajes" style="display: none;">
		
			<div class="msgHuecos" id="p26_mensajeHuecos">
					<div id="num_huecos_lbl" class="info"> </div>		
			</div>
			
			<div id="p26_AreaEstados" style="display: none;">
					<div class="p26_estados">
						<input type=text id="p26_inputBloqueada" disabled="disabled"></input>
						<label id="p26_lbl_estBloqueado" class="etiquetaCampo" style="margin-right:20px;"><spring:message code="p26_sfmCapacidad.SoloConsulta" /></label>
					</div>
			</div>
			
			<div id="p26_AreaSoloImagen" style="display: none;">
				<div class="p26_soloImagen">
					<input type=text id="p26_inputSoloImagen" disabled="disabled"></input>
					<label id="p26_lbl_soloImagen" class="etiquetaCampo" style="margin-right:20px;"><spring:message code="p26_sfmCapacidad.SoloImagen" /></label>
					<img id="p26_btn_ayudaSoloImagen" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}" class="botonAyuda" title="<spring:message code="p26_sfmCapacidad.ayuda" />"/>
				</div>
			</div>
			
			
		</div>
		
			<div id="p26AreaErrores" style="display: none;">
				<span id="p26_erroresTexto"><spring:message code="p26_sfmCapacidad.errorActualizacion" /></span>
			</div>
			<div id="AreaResultados" class="p26_AreaResultados" style="display: none;">
				<table id="gridP26Sfm" class="gridP26"></table>
				<div id="pagerP26Sfm"></div>
				<table id="gridP26Cap" class="gridP26"></table>
				<div id="pagerP26Cap"></div>
				<table id="gridP26Fac" class="gridP26"></table>
				<div id="pagerP26Fac"></div>
				<table id="gridP26FacTextil" class="gridP26"></table>
				<div id="pagerP26FacTextil"></div>
				<div id="p26_AreaNotaPie" style="display: none;">
					<spring:message code="p26_sfmCapacidad.notaPie" />
				</div>
				<input type="hidden" id="p26_fld_SFM_Selecc" value=""></input>
			</div>
			
				<%@ include file="/WEB-INF/views/p31_popupMotivosTengoMuchoPoco.jsp" %>
				
		</div>	
		<div id="excellPopup"></div>
		<div id="excellWindow"></div>		
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>