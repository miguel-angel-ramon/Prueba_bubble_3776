<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp"%>

<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p118ModificarAvisosPopUp.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p117GestionAvisosSiec.js?version=${misumiVersion}" type="text/javascript"></script>

<!--  Miga de pan -->

<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"><spring:message
							code="p117_gestionAvisosSiec.welcome" /></a></li>
				<li><spring:message code="p117_gestionAvisosSiec" /></li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">
	<!-- -->
	<div id="p117_AreaFiltro">
		<div id="p117_filtroEstructura"></div>
		<div id="p117_filterButtons">
			<input type="button" id="p117_btn_buscar" class="boton  botonHover" value='<spring:message code="p117_gestionAvisosSiec.find" />'></input>
			<input type="button" id="p117_btn_new" class="boton  botonHover" value='<spring:message code="p117_gestionAvisosSiec.new" />'></input>
			<input type="button" id="p117_btn_delete" class="boton  botonHover" value='<spring:message code="p117_gestionAvisosSiec.delete" />'></input>
			<input type=hidden id="p117_origenPantalla" value="${origenPantalla}"></input>
		</div>
	</div>
	<div id="p117_AreaResultados">
		<div id="p117_AreaAvisos" class="p117_AreaAvisos">
			<table id="gridP117GestionAvisosSiec"></table>
			<div id="pagerGridp117"></div>
		</div>
	</div>
</div>
<div id="p118_AreaPopupModificarAvisos">
		<%@ include file="/WEB-INF/views/p118_modificarAvisosPopUp.jsp" %>
</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>