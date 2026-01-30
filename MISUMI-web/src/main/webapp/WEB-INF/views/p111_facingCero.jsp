<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>      
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p111facingCero.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerParamSfmcap.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VArtSfm.js?version=${misumiVersion}t" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>

		<!--  Miga de pan --> 
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p01_header.welcome" /></a></li>
						<li><spring:message code="p111_facingCero.facingCero" /></li>
					</ul>
				</div>
			</div>
		</div>	

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="AreaFiltro">
				<div id="p111_filtroEstructura" style="display:inline-block;">
					<div class="p111_filtroEstructuraField" >
						<div class="comboBox comboBoxMedium">
							<label id="p111_lbl_area" class="etiquetaCampo"><spring:message code="p111_facingCero.area" /></label>
							<select id="p111_cmb_area"></select>
						</div>
					</div>

				</div>
				<div class="p111_filtroEstructuraField" style="display:inline-block;">
					<div id="p111_filterButtons">
						<input type="button" id="p111_btn_buscar" class="boton botonHover" value='<spring:message code="p111_facingCero.find" />'></input>		
<!-- 						<input type="button" id="p111_btn_reset" class="boton botonHover" value='<spring:message code="p111_facingCero.reset" />'></input>
 -->
						<input type="button" id="p111_btn_excel" class="boton botonNarrow botonHover" value='<spring:message code="p111_facingCero.excel" />'></input>
						<c:if test="${user.perfil != 3}">
							<input type="button" id="p111_btn_save" class="boton botonHover" value='<spring:message code="p111_facingCero.save" />'></input>
						</c:if>
						<img id="p111_btn_ayudaActiva" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}" class="botonAyuda" title="<spring:message code="p12_altasCatalogo.ayuda" />"/>
						<c:if test="${user.perfil != 3}">
							<input type="button" id="p111_btn_finRevision" class="boton botonNarrow botonHover" value='<spring:message code="p111_facingCero.finRevision" />'></input>
						</c:if>
					</div>
				</div>
				
			</div>

			<div id="p111_Area_mensajes" style="display: none;">
		
				<div class="msgHuecos" id="p111_mensajeHuecos">
					<div id="num_huecos_lbl" class="info">
					</div>		
				</div>
				
				<div id="p111_AreaEstados" style="display: none;">
					<div class="p111_estados">
						<input type=text id="p111_inputBloqueada" disabled="disabled"></input>
					</div>
				</div>

				<div id="p111_AreaSoloImagen" style="display: none;">
					<div class="p111_soloImagen">
						<input type=text id="p111_inputSoloImagen" disabled="disabled"></input>
						<label id="p111_lbl_soloImagen" class="etiquetaCampo" style="margin-right:20px;"><spring:message code="p26_sfmCapacidad.SoloImagen" /></label>
						<img id="p111_btn_ayudaSoloImagen" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}" class="botonAyuda" title="<spring:message code="p26_sfmCapacidad.ayuda" />"/>
					</div>
				</div>

			</div>
			
			<div id="p111AreaErrores" style="display: none;">
				<span id="p111_erroresTexto"><spring:message code="p111_facingCero.errorActualizacion" /></span>
			</div>

			<div id="AreaResultados" class="p111_AreaResultados" style="display: none;">
				<table id="gridP111FacingCero" class="gridp111"></table>
				<div id="pagerP111FacingCero"></div>
				<div id="p111_AreaNotaPie" style="display: none;">
					<spring:message code="p111_facingCero.notaPie" />
				</div>
				<input type="hidden" id="p111_fld_FacingCero_Selecc" value=""></input>
			</div>

		</div>
			
		<div id="excellPopup"></div>
		<div id="excellWindow"></div>

		<div id="p111dialog-confirm" style="display:none;" title="<spring:message code="p111_facingCero.preguntaFinRevision" />">
			<table id="p111_table_dialog_confirm">
				<tr>
					<td id="p111_td_img_dialogConfirm">
						<img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}'/>
					</td>
					<td id="p111_td_dialogConfirm">
						<span id="p111_mensajeDialogConfirm"><spring:message code="p111_facingCero.preguntaFinRevisionTxt" /></span>
					</td>
				</tr>
			</table>
		</div>
		
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>