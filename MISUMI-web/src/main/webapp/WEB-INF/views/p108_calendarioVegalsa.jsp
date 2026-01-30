<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp"%>
<script
	src="./misumi/scripts/p108CalendarioVegalsa.js?version=${misumiVersion}"
	type="text/javascript"></script>

<!-- ----- Contenido de la JSP ----- -->
<script
	src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script
	src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<!--  Miga de pan -->
<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"> <spring:message
							code="p108_calendario.vegalsa.welcome" />
				</a></li>
				<li><spring:message code="p108_calendario.vegalsa" /></li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">

	<div id="p108_AreaFiltro">
		<div id="p108_mapa" class="comboBox comboBoxLarge">
			<label id="p108_lbl_mapa" class="etiquetaCampo"> <spring:message
					code="p108_calendario.selector.mapa" />
			</label> <select id="p108_cmb_mapa"></select>
		</div>
		<div id="p108_legend">
			<div id="p108_legend_green"> </div>
			<div id="p108_legend_green_lbl"><label><spring:message code="p108_calendario.legend.green"/></label></div>
		</div>
		<div id="p108_button">
			<input type="button" id="p108_resumen_btn" class="boton  botonHover" value='<spring:message code="p108_calendario.btn.resumen" />'></input>
		</div>
	</div>
	<div id="p108_AreaResultados">
		<div id="p108_calendario" style='display: none'>
			<div id="p108_pagAnterior" class="p108_divFlecha p108_pagAnt"></div>
			<div id="p108_estructuraCalendario">
				<div id="p108_calendario_mes"></div>
				<div id="p108_calendario_diasDeLaSemana">
					<div class="p108_diaDeLaSemana">
						<spring:message code="p108_calendario.dias.lunes" />
					</div>
					<div class="p108_diaDeLaSemana">
						<spring:message code="p108_calendario.dias.martes" />
					</div>
					<div class="p108_diaDeLaSemana">
						<spring:message code="p108_calendario.dias.miercoles" />
					</div>
					<div class="p108_diaDeLaSemana">
						<spring:message code="p108_calendario.dias.jueves" />
					</div>
					<div class="p108_diaDeLaSemana">
						<spring:message code="p108_calendario.dias.viernes" />
					</div>
					<div class="p108_diaDeLaSemana">
						<spring:message code="p108_calendario.dias.sabado" />
					</div>
					<div class="p108_diaDeLaSemana">
						<spring:message code="p108_calendario.dias.domingo" />
					</div>
				</div>
				<div id="p108_calendario_dias">

				</div>
			</div>
			<div id="p108_pagSiguiente" class="p108_divFlecha p108_pagSig"></div>
		</div>

	</div>


</div>
<div id="p109_calendario_vegalsa_resumen">
	<%@ include file="/WEB-INF/views/p109_calendarioVegalsaResumenPopUp.jsp" %>
</div>

<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>
