<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp"%>

<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/p11Welcome.js?version=${misumiVersion}"
	type="text/javascript"></script>

<!--  Miga de pan -->

<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li>Inicio</li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">
	<div id="welcome">
		<div id="p11_aviso" style="display: none;">
			<fieldset id="p11_avisoFielset">
				<legend id="p11_avisoLegend">
					<spring:message code="p11_wellcome.aviso" />
				</legend>
				<p id="p11_avisoMens"></p>
			</fieldset>
		</div>
		<div id="p11_avisoValidarCantidadesExtraIcono" style="display: none;">
			<img src="./misumi/images/blanco.jpg?version=${misumiVersion}"
				style="vertical-align: middle;" height="40px" width="1px" /> <img
				src="./misumi/images/tablonanuncios.png?version=${misumiVersion}"
				style="vertical-align: middle;" class="imagenTablonAnunciosLista" />
		</div>
		<div id="p11_mensajesAvisos" style="display: none;">
			<div id="p11_cabeceraAvisos" style="display: none;">
				<div class="p11_avisosMensajeTitulo">
					<p id="p11_parrafoCabeceraAvisos">
						<spring:message code="p11_welcome.mensajeAvisos" />
					</p>
				</div>

			</div>
			<div id="p11_avisoDevolucionesUrgente" style="display: none;">
			</div>
			<div id="p11_avisoDevoluciones" style="display: none;">
				<div class="p11_avisosMensaje">
					<p id="p11_parrafoDevoluciones">
						<spring:message
							code="p11_welcome.mensajeDevolucionesDisponiblesDeEstadoUno" />
					</p>
				</div>
			</div>
			<div id="p11_avisoValidarCantidadesExtra" style="display: none;">
				<div class="p11_avisosMensaje">
					<p id="p11_parrafoAvisoValidarCantidadesExtra">
						<spring:message
							code="p11_welcome.mensajeRecordatorioValidarCantidadesExtra" />
					</p>
				</div>
			</div>
			<div id="p11_avisoPedidoAdicional" style="display: none;">
				<div class="p11_avisosMensaje"></div>
			</div>
			<div id="p11_avisoCalendario" style="display: none;">
				<div class="p11_avisosMensaje"></div>
			</div>
			<div id="p11_avisoCalendarioKO" style="display: none;">
				<div class="p11_avisosMensaje"></div>
			</div>
			<div id="p11_avisoEntradas" style="display: none;">
				<div class="p11_avisosMensaje"></div>
			</div>
			<div id="p11_avisoPLU" style="display: none;">
				<div class="p11_avisosMensaje">
					<p id="p11_parrafoAvisoPLU">
						<spring:message
							code="p11_welcome.p11_mensajeRecordatorioAlarmasPLU" />
					</p>
				</div>
			</div>
			<div id="p11_avisoMostrador" style="display: none;">
				<div class="p11_avisosMensaje"></div>
			</div>
			<div id="p11_avisoFacingCero" style="display: none;">
				<div class="p11_avisosMensaje"></div>
			</div>

			<div id="p11_avisoAjustePedidos" style="display: none;" >
			</div>

		</div>
	</div>
</div>

<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>