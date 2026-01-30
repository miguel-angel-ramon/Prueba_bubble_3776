<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp"%>

<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p105ModificarLotePopUp.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p106ModificarFechasPopUp.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p104ParametrizacionCestas.js?version=${misumiVersion}" type="text/javascript"></script>

<!--  Miga de pan -->

<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"><spring:message
							code="p104_parametrizacion.cestas.welcome" /></a></li>
				<li><spring:message code="p104_parametrizacion.cestas" /></li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">
	<!-- -->
	<div id="p104_AreaFiltro">
		<div id="p104_filtroEstructura"></div>
		<div id="p104_filterButtons">
			<input type="button" id="p104_btn_buscar" class="boton  botonHover" value='<spring:message code="p104_parametrizacion.cestas.find" />'></input>
			<input type="button" id="p104_btn_new" class="boton  botonHover" value='<spring:message code="p104_parametrizacion.cestas.new" />'></input>
			<input type="button" id="p104_btn_modify" class="boton  botonHover" value='<spring:message code="p104_parametrizacion.cestas.modify" />'></input>
			<input type="button" id="p104_btn_delete" class="boton  botonHover" value='<spring:message code="p104_parametrizacion.cestas.delete" />'></input>
			<input type=hidden id="p104_origenPantalla" value="${origenPantalla}"></input>
		</div>
	</div>
	<div id="p104_AreaResultados" style="display:none">
		<div id="p104_AreaCestas" class="p104_AreaCestas">
			<table id="gridP104ParametrizacionCestas"></table>
			<div id="pagerGridp104"></div>
		</div>
	</div>
</div>
<div id="p105_AreaPopupModificarLote">
		<%@ include file="/WEB-INF/views/p105_modificarLotePopUp.jsp" %>
</div>
<div id="p105_AreaPopupModificarFechas">
		<%@ include file="/WEB-INF/views/p106_modificarFechasPopUp.jsp" %>
</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>