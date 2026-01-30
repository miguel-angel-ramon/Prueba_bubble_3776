<!-- ----- Include p01_header.jsp ----- -->

<%@ include file="/WEB-INF/views/includes/p01_header.jsp"%>


<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<%-- <script src="./misumi/scripts/utils/map.js?version=${misumiVersion}"></script> --%>

<%-- <script src="./misumi/scripts/p70DetalladoMostrador.js?version=${misumiVersion}" type="text/javascript"></script> --%>


<%-- <script src="./misumi/scripts/model/HistoricoVentaUltimoMes.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<%-- <script src="./misumi/scripts/model/Motivo.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<%-- <script src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<%-- <script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script> --%>
<%-- <script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<%-- <script src="./misumi/scripts/model/DetalladoMostrador.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<%-- <script src="./misumi/scripts/model/VAgruComerRefMostrador.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<%-- <script src="./misumi/scripts/model/DetalladoMostradorModif.js?version=${misumiVersion}" type="text/javascript"></script> --%>

<%-- <script src="./misumi/scripts/p75DetalladoMostrador.js?version=${misumiVersion}" type="text/javascript"></script> --%>

<%-- <script src="./misumi/scripts/model/PendientesRecibir.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<script src="./misumi/scripts/p56AyudaPopup.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/calendario.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.dialogextend.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/VOferta.js?version=${misumiVersion}" type="text/javascript"></script>
<%-- <script id="detalladoMostradorJsId" src="./misumi/scripts/model/DetalladoMostrador.js?version=${misumiVersion}" type="text/javascript" data-hasAnyRecordFromSIA="${hasAnyRecordFromSIA}"></script> --%>

<%-- <script src="./misumi/scripts/poc.js?version=${misumiVersion}" type="text/javascript"></script> --%>
<script src="./misumi/scripts/i18n/messages_es.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/i18nModule.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p70DetalladoMostradorMainModule.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p70DetalladoMostradorListModule.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/documentPopupModule.js?version=${misumiVersion}" type="text/javascript"></script>

<!--  Miga de pan -->
<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"><spring:message code="p70_detalladoMostrador.welcome"/></a></li>
				<li><spring:message code="p70_detalladoMostrador.detalladoMostrador" /></li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">
	<div id="p70_AreaFiltroMostrador">
		<div id="p70_filtroEstructura">
			<div class="p70_filtroEstructuraField2">
				<!-- Seccion -->
				<div class="comboBox comboBoxMediumShort controlReturnP70">
					<label id="p70_lbl_seccion" class="etiquetaCampo"><spring:message
							code="p70_detalladoMostrador.section" />
					</label>
					<select id="p70_cmb_seccion" disabled></select>
					<input id="p70_cmb_seccion_b" type="hidden" value=""/>
				</div>
				<!-- Categoria -->
				<div class="comboBox comboBoxMediumShort controlReturnP70">
					<label id="p70_lbl_categoria" class="etiquetaCampo">
						<spring:message code="p70_detalladoMostrador.category" />
					</label>
					<select id="p70_cmb_categoria" disabled></select>
					<input id="p70_cmb_categoria_b" type="hidden" value=""/>
				</div>

			</div>
			
			<!-- Checkbox "SoloVenta" -->
			<div id="p70_checkBoxIncluirPedido">
				<label id="p70_lbl_soloVenta" class="etiquetaCampo" title="<spring:message code='p70_detalladoMostrador.soloVenta'/>">
					<spring:message code="p70_detalladoMostrador.soloVenta"/>
				</label>
				<input id="p70_chk_soloVenta" type="checkbox" class="" />
			</div>

			<div class="p70_filtroEstructuraField2 p70_filtroEstructuraField3">
				<!-- Combo TipoAprov -->
				<div class="comboBox comboBoxExtraShort controlReturnP70">
					<label id="p70_lbl_tipoAprov" class="etiquetaCampo">
						<spring:message	code="p70_detalladoMostrador.tipoAprov" />
					</label>
					<select id="p70_cmb_tipoAprov">
						<option value="null"><spring:message code="p70_tipoAprov.all"/></option>
						<option value="C"><spring:message code="p70_tipoAprov.cen"/></option>
						<option value="D"><spring:message code="p70_tipoAprov.des"/></option>
					</select>
					<input id="p70_cmb_tipoAprov_b" type="hidden" value=""/>
				</div>
			</div>
			
			<div id="p70_fechaEspejo_div">
				<div id="p70_fechaEspejo_lbl">
					<label class="etiquetaCampo">
						<spring:message code="p70_detalladoMostrador.diaEspejo" />
					</label>
				</div>
				<div id="p70_fechaEspejo_txt">
					<input id="p70_fechaEspejo" type="text" readonly/>	
				</div>
			</div>
				
			<div class="p70_filtroEstructuraField2">
				<!-- Subcategoria -->
				<div class="comboBox comboBoxMedium controlReturnP70">
					<label id="p70_lbl_subcategoria" class="etiquetaCampo">
						<spring:message code="p70_detalladoMostrador.subcategory" />
					</label>
					<select id="p70_cmb_subcategoria" disabled></select>
					<input id="p70_cmb_subcategoria_b" type="hidden" value=""/>
				</div>
				
				<!-- Segmento -->
				<div class="comboBox comboBoxMedium controlReturnP70">
					<label id="p70_lbl_segmento" class="etiquetaCampo">
						<spring:message code="p70_detalladoMostrador.segmento" />
					</label>
					<select id="p70_cmb_segmento" disabled></select>
					<input id="p70_cmb_segmento_b" type="hidden" value=""/>
				</div>
			</div>
			
			<!-- Checkbox "GamaLocal" -->
			<div id="p70_checkBoxIncluirPedido">
				<label id="p70_lbl_gamaLocal" class="etiquetaCampo" title="<spring:message code='p70_detalladoMostrador.gamaLocal'/>">
					<spring:message code="p70_detalladoMostrador.gamaLocal"/>
				</label>
				<input id="p70_chk_gamaLocal" type="checkbox" class="" />
			</div>
			
		</div>

		<!-- Botonera superior (Buscar, Excel, Nuevo, Guardar) -->
		<div id="p70_filterButtonsMostrador">
 			
			<input type="button" id="p70_btn_buscar" class="boton botonHover" value='<spring:message code="p70_detalladoMostrador.find" />'/>
			<input type="button" id="p70_btn_excel" class="boton botonHover" value='<spring:message code="p70_detalladoMostrador.excel" />' disabled/>

			<c:if test="${user.perfil != 3}">
				<input type="button" id="p70_btn_consulta" class="boton botonHover" value='<spring:message code="p70_detalladoMostrador.new" />' disabled/>
				<input type="button" id="p70_btn_guardar"  class="boton botonHover" value='<spring:message code="p70_detalladoMostrador.save" />' disabled/>
			</c:if>

		</div>
	</div>
	
	<div id="p70_informacion">
		<div id="p70_textoYKingBomba" style="display: none">
			<div id="p70_textoKingBomba">
				<label>
					<spring:message	code="p70_detalladoMostrador.textoKingBomba" /> 
					<img id="p70_alerta" src="./misumi/images/dialog-error-16.gif?version=" style="display: none">
				</label>
			</div>
			<div id="p70_kingBomba">
				<label id="p70_cronoKingBomba" class="p70_bombaNoExplota"></label>
			</div>
		</div>
		<div id="p70_estados">
			<div id="p70_AreaEstadosPedido">
				<fieldset id="p70_informacionEstadosPedidoFieldset">
					<div class="p70_estadosPedido">
						<input type=text id="p70_inputSinGuardar" disabled="disabled"/>
						<label id="p70_lbl_estSinGuardar" class="etiquetaCampo"
							style="margin-right: 20px;">
							<spring:message code="p70_detalladoMostrador.prevision" />
						</label>
						
						<input type=text id="p70_inputGuardadoSinIntegrar" disabled="disabled"/>
						<label id="p70_lbl_estGuardadoSinIntegrar" class="etiquetaCampo"
							style="margin-right: 20px;">
							<spring:message code="p70_detalladoMostrador.guardadoSinCerrar"/>
						</label>
						
						<input type=text id="p70_inputIntegrada" disabled="disabled"/>
						<label id="p70_lbl_estIntegrado" class="etiquetaCampo"
							style="margin-right: 20px;">
							<spring:message code="p70_detalladoMostrador.cerrado" />
						</label>
						
						<input type=text id="p70_inputBloqueada" disabled="disabled"/>
						<label id="p70_lbl_estBloqueado" class="etiquetaCampo"
							style="margin-right: 20px;"><spring:message
							code="p70_detalladoMostrador.bloqueado"/>
						</label>
					</div>
				</fieldset>
			</div>
		
		</div>
		<div id="p70_AreaCajasEuros" style="display: none;">
			<fieldset id="p70_mostradorResumenFieldset" >
				<div id="p70_infoGestionCajasEurosDiv">

					<div id="p70_mostradorResumenFieldset">

						<!-- Etiquetas Euros, Euros PVP y Cajas  -->
						<div id="p70_mostradorTotalesLabel" >
							<div>
								<label>
									<spring:message code="p70_fieldset.gestionEuros.euros.costo" />
								</label>
							</div>
							<div>
								<label>
									<spring:message code="p70_fieldset.gestionEuros.euros.pvp" />
								</label>
							</div>
							<div>
								<label>
									<spring:message code="p70_fieldset.gestionEuros.cajas" />
								</label>
							</div>
						</div>

						<div id="p70_mostradorTotalesIniciales">
							<div>
								<span id="p70_inputEurosIniciales" ></span>
							</div>
							<div>
								<span id="p70_inputEurosPVPIniciales"></span>
							</div>
							<div>
								<span id="p70_inputCajasIniciales" /></span>
							</div>
						</div>

						<!-- FLECHAS de Euros, Euros PVP y Cajas  -->
						<div id="p70_mostradorTotalesFlechas">
							<div>
								<img id="p70_imgFlecha"
									src="./misumi/images/arrow_right_25.png?version=${misumiVersion}" />
							</div>
							<div >
								<img id="p70_imgFlecha"
									src="./misumi/images/arrow_right_25.png?version=${misumiVersion}" />
							</div>
							<div >
								<img id="p70_imgFlecha"
									src="./misumi/images/arrow_right_25.png?version=${misumiVersion}" />
							</div>
						</div>

						<div id="p70_mostradorTotalesFinales">
							<div >
								<span id="p70_inputEurosFinales"></span>
							</div>
							<div >
								<span id="p70_inputEurosPVPFinales"></span>
							</div>
							<div >
								<span id="p70_inputCajasFinales"></span>
							</div>
						</div>

					</div>
					
					<div id="p70_otraInformacion">
						<div id="p70_ventasDiaEspejo" >
							<label><spring:message code="p70_detalladoMostrador.ventas.espejo" /></label>
							<span id="p70_ventasEspejoValor"> </span><span>&euro;</span>
						</div>
						<div id="p70_fechasEntregaAvisoPedido" >
							<div id="p70_fechasEntrega">
								<label><spring:message code="p70_detalladoMostrador.fecha.entrega" /></label>
								<span id="p70_fechaEntregaValor"></span>
							</div>
							<div id="p70_fechasAviso">
								<span class="red"><spring:message code="p70_detalladoMostrador.vispera.festivo" /></span>
							</div>
							<div id="p70_fechasPedido">
								<label><spring:message code="p70_detalladoMostrador.siguiente.pedido" /></label>
								<span id="p70_fechaPedidoValor"></span>
							</div>
							<div id="p70_fechasDobleTransmision">
								<span class="red"><spring:message code="p70_detalladoMostrador.doble.transmision" /></span>
							</div>

						</div>
						<div id="p70_warning">
							<label><spring:message code="p70_detalladoMostrador.msgCantidades" /></label>
						</div>
					</div>

					</div>
			</fieldset>
		</div>
	</div>
	
	<!-- Area del GRID. Se genera dinamicamente en este div-->
	<div id="p70_AreaResultados" style="display:none;">
	</div>

	<%@ include file="/WEB-INF/views/p56_ayudaPopup.jsp"%>
	<%@ include file="/WEB-INF/views/p70_popUpReferenciasNoGama.jsp"%>

</div>

<div id="popUpOfertasDetalladoMostrador"></div>
<div id="documentPopup"></div>

<div id="p70_buscarConfirm" title="Confirmacion" style="display:none">
	<table>
		<tr>
			<td width='10%' style='text-align: center'><img src='./misumi/images/dialog-error-24.png' /></td>
			<td width='85%' style='text-align: left'><label><spring:message code="p70_detalladoMostrador.confirm.msgfila1" /></label></td>
		</tr>
		<tr>
			<td colspan="2"><label><spring:message code="p70_detalladoMostrador.confirm.msgfila2" /></label></td>
		</tr>
	</table>
</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>
