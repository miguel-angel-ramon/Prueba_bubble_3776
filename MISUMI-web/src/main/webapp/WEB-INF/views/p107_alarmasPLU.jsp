<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>

<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p107AlarmasPLU.js?version=${misumiVersion}" type="text/javascript"></script>

<!--  Miga de pan -->

<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"><spring:message
							code="p107_alarmas_plu.welcome" /></a></li>
				<li><spring:message code="p107_alarmas_plu" /></li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">
	<!-- -->
	<div id="p107_AreaFiltro">
		<div id="p107_filtroEstructura">
			<div class="p107_filtroEstructuraField" >
				<div class="comboBox comboBoxMedium controlReturn">
					<label id="p107_lbl_area" class="etiquetaCampo"><spring:message code="p107_alarmas_plu.area" /></label>
					<select id="p107_cmb_area"></select>
				</div>
				<div class="comboBox comboBoxMedium controlReturn">
					<label id="p107_lbl_seccion" class="etiquetaCampo"><spring:message code="p107_alarmas_plu.section" /></label>
					<select id="p107_cmb_seccion"></select>
				</div>								
				<div class="comboBox comboBoxMedium controlReturn">
					<label id="p107_lbl_agrupacion" class="etiquetaCampo"><spring:message code="p107_alarmas_plu.agrupacion" /></label>
					<select id="p107_cmb_agrupacion"></select>
				</div>
										
			</div>
		</div>
		<div id="p107_AreaLeyendasYBotones">
			<fieldset id="p107_legendsFieldset" >
				<div id="p107_legends">
					<input type=text disabled="disabled" id="p107_legend1"></input>
					<label class="etiquetaCampo" ><spring:message code="p107_alarmas_plu.legend1" /></label>
					<input type=text disabled="disabled" id="p107_legend2"></input>
					<label class="etiquetaCampo" ><spring:message code="p107_alarmas_plu.legend2" /></label>
					<input type=text disabled="disabled" id="p107_legend3"></input>
					<label class="etiquetaCampo" ><spring:message code="p107_alarmas_plu.legend3" /></label>
				</div>
			</fieldset>
			<div id="p107_filterButtons">
				<input type="button" id="p107_btn_search" class="boton  botonHover" value='<spring:message code="p107_alarmas_plu.search" />'></input>
				<input type="button" id="p107_btn_clean" class="boton  botonHover" value='<spring:message code="p107_alarmas_plu.clean" />'></input>
				<input type="button" id="p107_btn_excel" class="boton  botonHover" value='<spring:message code="p107_alarmas_plu.excel" />'></input>
				<input type="button" id="p107_btn_save" class="boton  botonHover" value='<spring:message code="p107_alarmas_plu.save" />'></input>
				<input type=hidden id="p107_origenPantalla" value="${origenPantalla}"></input>
			</div>
		</div>
	</div>
	<hr class="p107_hr"/>
	<div id="p107_AreaMensajes" style="display:none; text-align: center;">
		<label class="etiquetaCampo p107_bold" style="color: red"><spring:message code="p107_alarmas_plu.msg_guardar" /></label>
	</div>
	<hr class="p107_hr"/>
	<div id="p107_AreaResultados" class="p107_hidden">
		<fieldset id="p107_countersFieldset" >
			<div id="p107_counters">
				<label class="etiquetaCampo p107_bold"><spring:message code="p107_alarmas_plu.counter1" /></label>
				<input class='p107_box p107_bold p107_center' disabled="disabled" id="p107_counter1" type="text"/>
				<label class="etiquetaCampo p107_bold"><spring:message code="p107_alarmas_plu.counter2" /></label>
				<input class='p107_box p107_bold p107_center' disabled="disabled" id="p107_counter2" type="text"/>
				<label class="etiquetaCampo p107_bold"><spring:message code="p107_alarmas_plu.counter3" /></label>
				<input class='p107_box p107_bold p107_center' disabled="disabled" id="p107_counter3" type="text"/>
				<div id="p107_alert_referencias" class="p107_center p107_bold p107_backgroundYellow"></div>
			</div>
		</fieldset>	
		<div id="p107_AreaFreePlus">
			<fieldset id="p107_free_plus_fieldset">
				<div id="p107_free_plus_div">
					<label class="etiquetaCampo p107_bold"><spring:message code="p107_alarmas_plu.free_plus1" /></label>
					<label class="etiquetaCampo p107_bold"><a id="p107_free_plus" href="#"></a></label>
					<label class="etiquetaCampo p107_bold"><spring:message code="p107_alarmas_plu.free_plus4" /></label>
					<input class='p107_box p107_center p107_box_sin_margin' id="p107_plu_max_libre" name="p107_plu_max_libre" type="text"/>
					<input id="p107_plu_max_libre_copia" type="hidden"/>
					<input id="p107_plu_max_asignado" type="hidden"/>
				</div>
			</fieldset>
			<fieldset id="p107_asign_fieldset">
				<div id="p107_asign">
					<label class="etiquetaCampo p107_bold"><spring:message code="p107_alarmas_plu.free_plus2" /></label>
					<input class='p107_box p107_center' id="p107_plu_libre" type="text"/>
					<label class="etiquetaCampo p107_bold"><spring:message code="p107_alarmas_plu.free_plus3" /></label>
					<input class='p107_box2 p107_center' id="p107_referencia" type="text"/>
					<input type="button" id="p107_btn_asignar" class="boton botonHover p107_hidden" value='Asignar' />
					<input type="button" id="p107_btn_alta" class="boton botonHover p107_hidden" value='Dar de alta en este grupo' />
					<label id="p107_mensaje_asignar_plu" class="etiquetaCampo p107_bold p107_yellow p107_hidden"><spring:message code="p107_alarmas_plu.reference_warning" /></label>
				</div>
			</fieldset>
		</div>
		<div id="p107_AreaAlarmas"  style="margin-top: 55px;">
			<table id="gridP107AlarmasPLU"></table>
			<div id="pagerGridp107"></div>
		</div>
	</div>
</div>
<div id="p107dialog-confirm" style="display:none;" title="<spring:message code="p107_alarmas_plu.preguntaEliminar" />">
	<table id="p107_table_dialog_confirm">
		<tr>
			<td id="p107_td_img_dialogConfirm">
				<img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}'/>
			</td>
			<td id="p107_td_dialogConfirm">
				<span id="p107_mensajeDialogConfirm"><spring:message code="p107_alarmas_plu.preguntaEliminarTxt" /></span>
			</td>
		</tr>
	</table>
</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>