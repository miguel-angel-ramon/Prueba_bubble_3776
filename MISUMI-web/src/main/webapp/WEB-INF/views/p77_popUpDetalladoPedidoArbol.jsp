<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<script src="./misumi/scripts/p77PopUpDetalladoPedidoArbol.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/DetallePedidoLista.js?version=${misumiVersion}" type="text/javascript"></script>

<div id="p77_popUpDetalladoPedidoArbol" style="display: none">
	<!-- <div id="p77_parametrizacionEurosCajas">
		<div id="p77_parametrizacionCajasEurosTitle">
			<label id="p77_lbl_parametrizacionCajasEurosTitle">
				<spring:message code="p77_gestionEuros.parametrizacion" />
			</label>
		</div>
		<div id="p77_parametrizacionCajasEurosLabels">
			<label id="" class="p77_label textBoxFieldSet">
				<spring:message code="p77_gestionEuros.parametrizacion.oferta" />
			</label> 
			<label id="" class="p77_label textBoxFieldSet">
				<spring:message code="p77_gestionEuros.parametrizacion.rotacion" />
			</label> 
			<label id="" class="p77_label textBoxFieldSet"> 
				<spring:message code="p77_gestionEuros.parametrizacion.respetar" />
			</label>
		</div>
		<div id="p77_parametrizacionCajasEurosCheck1">
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" /> 
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.oferta.todasRefs" />
				</label>
			</div>
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" />
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.rotacion.todasRefs" />
				</label>
			</div>
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" /> 
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.respetar.imc" />
				</label>
			</div>
		</div>
		<div id="p77_parametrizacionCajasEurosCheck2">
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" /> 
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.oferta.enOferta" />
				</label>
			</div>
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" />
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.rotacion.altaRotacion" />
				</label>
			</div>
		</div>
		<div id="p77_parametrizacionCajasEurosCheck3">
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" /> 
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.oferta.sinOferta" />
				</label>
			</div>
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" /> 
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.rotacion.mediaRotacion" />
				</label>
			</div>
		</div>
		<div id="p77_parametrizacionCajasEurosCheck4">
			<div class="textBoxFieldSet">
				<input id="p15_chk_montaje1Cabecera" type="checkbox" class="" /> 
				<label id="p15_lbl_montaje1Cabecera" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.parametrizacion.rotacion.bajaRotacion" />
				</label>
			</div>
		</div>
	</div>-->
	<div id="p77AreaResultados"  class="" style="display: none;">
		<table id="gridP77"></table>
	</div>
	
	<c:if test="${user.perfil == 1 || user.perfil == 4 || user.perfil == 2}">
		<c:if test = "${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '50_DETALLADO_GESTION_EUROS')}">
			<div id="p77_infoGestionCajasEurosDivBotonera">
					<input type="button" id="p77_btn_calcular" class="boton  botonHover" value="<spring:message code="p77_gestionEuros.btn.calcular" />">
					<input type="button" id="p77_btn_propuestaInicial" class="boton  botonHover" value="<spring:message code="p77_gestionEuros.btn.propuestaInicial" />">
			</div>
		</c:if>
	</c:if>

	<!-- <div id="p77_arbolEurosCajas">
		<div id="p77_arbolEurosCajasParte1">
			<div id="p77_arbol">
				<label id="p77_lbl_arbol" class="etiquetaCampo">
					<spring:message code="p77_gestionEuros.arbol.titulo" />
				</label>
			</div>
			<div id="p77_eurosCajasVal">
				<div id="p77_euros">
					<div class="textBox">
						<label id="" class="etiquetaCampo">
							<spring:message code="p77_fieldset.gestionEuros.eurosIniciales" />
						</label> 
						<input type=text id="" class="input40"></input>
					</div>
					<div class="textBox">
						<label id="" class="etiquetaCampo">
							<spring:message code="p77_fieldset.gestionEuros.cajasIniciales" />
						</label> 
						<input type=text id="" class="input40"></input>
					</div>
				</div>
				<div id="p77_cajas">
					<div class="textBox">
						<label id="" class="etiquetaCampo">
							<spring:message code="p77_fieldset.gestionEuros.eurosFinales" />
						</label> 
						<input type=text id="" class="input40"></input>
					</div>
					<div class="textBox">
						<label id="" class="etiquetaCampo">
							<spring:message code="p77_fieldset.gestionEuros.cajasFinales" />
						</label> 
						<input type=text id="" class="input40"></input>
					</div>
				</div>
			</div>
		</div>
		<div id="p77_arbolEurosCajasParte2">
			<div id="p77_eurosCajasConBorde">
				<div>
				
				</div>
				<div>
				
				</div>
			</div>
			<div id="p77_eurosCajasSinBorde">
				<div>
				
				</div>
				<div>
				
				</div>
			</div>
		</div>
	</div>-->
</div>