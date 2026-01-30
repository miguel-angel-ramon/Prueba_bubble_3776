<script
	src="./misumi/scripts/model/GridFilters.js?version=${misumiVersion}"></script>
<script
	src="./misumi/scripts/model/Filters.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/Rules.js?version=${misumiVersion}"></script>
<script
	src="./misumi/scripts/p109CalendarioVegalsaResumenPopUp.js?version=${misumiVersion}"
	type="text/javascript"></script>

<div id="p109_calendario_vegalsa_resumen_popup">
	<div id="p109_legend" >

		<div id="p109_legend_green">
		</div>
		<div class="p109_legend_lbl">
			<label><spring:message code="p109_calendario.legend.green" /></label>
		</div>
		<div id="p109_legend_red">
		</div>
		<div class="p109_legend_lbl">
			<label><spring:message code="p109_calendario.legend.red" /></label>
		</div>
		<div id="p109_legend_btn">
			<input type="button" id="p109_bexdata"
				class="boton botonNarrow  botonHover"
				value='<spring:message code="p109_calendario.exportar"/>' />
		</div>
	</div>

	<table id="gridP109"></table>
	<div id="pagerP109"></div>

</div>

<div id="p109_dialog-confirm" title="Confirmación"  style='display:none'>
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
		<spring:message code="p109_calendario.confirmacion"/>
	</p>
</div>

<div id="excellPopup"></div>
<div id="excellWindow"></div>	