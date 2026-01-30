<!-- ----- Include p01_header.jsp ----- -->

<%@ include file="/WEB-INF/views/includes/p01_header.jsp"%>


<!-- ----- Contenido de la JSP ----- -->
<script
	src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}">
</script>

<script
	src="./misumi/scripts/jqueryCore/jquery.dialogextend.min.js?version=${misumiVersion}">
</script>

<script src="./misumi/scripts/utils/map.js?version=${misumiVersion}"></script>

<script
	src="./misumi/scripts/p70DetalladoPedido.js?version=${misumiVersion}"
	type="text/javascript">
</script>
	
<script
	src="./misumi/scripts/model/HistoricoVentaUltimoMes.js?version=${misumiVersion}"
	type="text/javascript">
</script>
	
<!--  
<script src="./misumi/scripts/model/Motivo.js?version=${misumiVersion}"
	type="text/javascript">
</script>
-->

<script
	src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}"
	type="text/javascript">
</script>
	
<script
	src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}">
</script>
	
<script
	src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}"
	type="text/javascript">
</script>

<script
	src="./misumi/scripts/model/DetalladoPedido.js?version=${misumiVersion}"
	type="text/javascript">
</script>

<script
	src="./misumi/scripts/model/DetalladoPedidoModif.js?version=${misumiVersion}"
	type="text/javascript">
</script>

<script
	src="./misumi/scripts/p75DetalladoPedidoSinOferta.js?version=${misumiVersion}"
	type="text/javascript">
</script>
	
<script
	src="./misumi/scripts/p76DetalladoPedidoOferta.js?version=${misumiVersion}"
	type="text/javascript">
</script>

<script
	src="./misumi/scripts/p114PopUpPluPendiente.js?version=${misumiVersion}"
	type="text/javascript">
</script>

<script
	src="./misumi/scripts/model/PendientesRecibir.js?version=${misumiVersion}"
	type="text/javascript">
</script>

<!-- 
<script src="./misumi/scripts/model/VOferta.js?version=${misumiVersion}"
	type="text/javascript"></script>
-->

<script
	src="./misumi/scripts/utils/calendario.js?version=${misumiVersion}"
	type="text/javascript">
</script>

<script src="./misumi/scripts/p56AyudaPopup.js?version=${misumiVersion}"
	type="text/javascript">
</script>
	
<script id="detalladoPedidoJsId"
	src="./misumi/scripts/model/DetalladoPedido.js?version=${misumiVersion}"
	type="text/javascript" data-isCentroVegalsa="${isCentroVegalsa}"
	data-hasAnyRecordFromSIA="${hasAnyRecordFromSIA}">
</script>
<script src="./misumi/scripts/i18n/messages_es.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/i18nModule.js?version=${misumiVersion}" type="text/javascript"></script>
<!--  Miga de pan -->
<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"><spring:message
							code="p70_detalladoPedido.welcome" />
					</a>
				</li>
				
				<li><spring:message code="p70_detalladoPedido.detalladoPedido"/></li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">
	<div id="p70_AreaFiltro">
		<div id="p70_filtroEstructura">
			<div class="p70_filtroEstructuraField">
				<div class="comboBox comboBoxMedium controlReturnP70">
					<label id="p70_lbl_seccion" class="etiquetaCampo"><spring:message
							code="p70_detalladoPedido.section" /></label> <select
						id="p70_cmb_seccion"></select> <input id="p70_cmb_seccion_b"
						type="hidden" value=""></input>
				</div>
				<div class="comboBox comboBoxMedium controlReturnP70">
					<label id="p70_lbl_categoria" class="etiquetaCampo"><spring:message
							code="p70_detalladoPedido.category" /></label> <select
						id="p70_cmb_categoria"></select> <input id="p70_cmb_categoria_b"
						type="hidden" value=""></input>
				</div>
			</div>
		</div>

		<div id="p70_filtroReferencia" style="display: none;">
			<div style="text-align: left; padding: 5px">
				<label id="p70_lbl_referencia" class="etiquetaCampo"><spring:message
						code="p70_detalladoPedido.reference" /></label> <input
					class="controlReturnP70" type=text id="p70_fld_referencia"
					class="input250"></input>
			</div>
		</div>
		<!-- MISUMI-300 -->
		<c:if test="${isCentroVegalsa}">
			<div id="p70_filtroMapa" style="display: none;">
				<div id="p70_mapa" class="comboBox comboBoxLarge">
					<label id="p70_lbl_mapa" class="etiquetaCampo"> <spring:message
							code="p70_detalladoPedido.lblSelectorMapa" /></label> <select
						id="p70_cmb_mapa">
						<option value="0" selected="selected">&nbsp;</option>
						<c:forEach var="item" items="${lstMapas}">
							<option value="${item.codigo}"><c:out
									value="${item.descripcion}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
		</c:if>

		<!-- MISUMI-349 -->
		<div id="p70_checkBoxIncluirPedido" style="display: none;">
			<input id="p70_chk_IncluirPedido" class="controlReturnP70" type="checkbox"> 
			<label id="p70_lbl_IncluirPedido" class="etiquetaCampo">
				<!-- MISUMI-379 -->
				<c:if test="${!isCentroVegalsa}">
					<spring:message	code="p70_incluirPropuesta.pedido" />
				</c:if>
				<c:if test="${isCentroVegalsa}">
					<spring:message	code="p70_incluirPropuesta.pedidoCentroVegalsa" />
				</c:if>
				<!-- FIN MISUMI-379 -->
			</label>
				
		</div>

		<div id="p70_filterButtons">
			<div class="p70_tipoBusqListadoField">
				<input type="radio" class="controlReturnP70"
					name="p70_rad_tipoFiltro" id="p70_rad_tipoFiltro" value="1"
					checked="checked" /> <label id="p70_lbl_radioEstructura"
					class="etiquetaCampo"
					title="<spring:message code='p70_detalladoPedido.comertialStructure' />"><spring:message
						code="p70_detalladoPedido.comertialStructureAbrev" /></label> <input
					type="radio" class="controlReturnP70" name="p70_rad_tipoFiltro"
					id="p70_rad_tipoFiltro" value="2" /> <label
					id="p70_lbl_radioReferencia" class="etiquetaCampo"
					title="<spring:message code='p70_detalladoPedido.reference' />"><spring:message
						code="p70_detalladoPedido.referenceAbrev" /></label>

				<!-- MISUMI-300 -->
				<c:if test="${isCentroVegalsa}">
					<input type="radio" class="controlReturnP70"
						name="p70_rad_tipoFiltro" id="p70_rad_tipoFiltro" value="3" />
					<label id="p70_lbl_radioEstructura" class="etiquetaCampo"
						title="<spring:message code='p70_detalladoPedido.titleCheckMapa' />"><spring:message
							code='p70_detalladoPedido.lblCheckMapa' /></label>
				</c:if>

			</div>
			<input type="button" id="p70_btn_buscar" class="boton  botonHover"
				value='<spring:message code="p70_detalladoPedido.find" />'></input>
			<input type="button" id="p70_btn_excel" class="boton  botonHover"
				value='<spring:message code="p70_detalladoPedido.excel" />'></input>

					<c:choose>
						<c:when test="${user.perfil != 3}">
							<c:choose>
								<c:when test="${hasAnyRecordFromSIA == true}">
									<input type="button" id="p70_btn_nuevo" class="boton botonHover" value='<spring:message code="p70_detalladoPedido.new" />'>
								</c:when>
								<c:otherwise>
									<input type="button" class="boton botonHover" value='<spring:message code="p70_detalladoPedido.new" />' disabled/>
								</c:otherwise>
							</c:choose>
							<input type="button" id="p70_btn_guardar" class="boton botonHover" value='<spring:message code="p70_detalladoPedido.save" />'/>
						</c:when>
						<c:otherwise>
							<input type="button" class="boton botonHover" value='<spring:message code="p70_detalladoPedido.new" />' disabled/>
							<input type="button" class="boton botonHover" value='<spring:message code="p70_detalladoPedido.save" />' disabled/>
						</c:otherwise>
					</c:choose>

		</div>
	</div>
	<div id="p70_informacion">
		<div id="p70_textoYKingBomba" style="display: none">
			<div id="p70_textoKingBomba">
				<label id="p70_lblKingBomba"><spring:message
						code="p70_detalladoPedido.textoKingBomba" /> <img id="p70_alerta"
					src="./misumi/images/dialog-error-16.gif?version="
					style="display: none" class=""></label>
			</div>
			<div id="p70_kingBomba">
				<label id="p70_cronoKingBomba" class="p70_bombaNoExplota"></label>
			</div>
			<input id="p70_modificado" value="N" style="display: none" />
		</div>
		<div id="p70_estados">
			<div id="p70_AreaEstadosPedido">
				<fieldset id="p70_informacionEstadosPedidoFieldset">
					<div class="p70_estadosPedido">
						<input type=text id="p70_inputSinGuardar" disabled="disabled"></input>
						<label id="p70_lbl_estSinGuardar" class="etiquetaCampo"
							style="margin-right: 20px;"><spring:message
								code="p70_detalladoPedido.sinGuardar" /></label> <input type=text
							id="p70_inputGuardadoSinIntegrar" disabled="disabled"></input> <label
							id="p70_lbl_estGuardadoSinIntegrar" class="etiquetaCampo"
							style="margin-right: 20px;"><spring:message
								code="p70_detalladoPedido.guardadoSinIntegrar" /></label> <input
							type=text id="p70_inputIntegrada" disabled="disabled"></input> <label
							id="p70_lbl_estIntegrado" class="etiquetaCampo"
							style="margin-right: 20px;"><spring:message
								code="p70_detalladoPedido.integrado" /></label> <input type=text
							id="p70_inputBloqueada" disabled="disabled"></input> <label
							id="p70_lbl_estBloqueado" class="etiquetaCampo"
							style="margin-right: 20px;"><spring:message
								code="p70_detalladoPedido.bloqueado" /></label>
					</div>
				</fieldset>
				<c:if
					test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && (misumi:contieneOpcion(user.centro.opcHabil, '50_DETALLADO_GESTION_EUROS') || misumi:contieneOpcion(user.centro.opcHabil, '51_DETALLADO_GESTION_EUROS_CONS')) && !misumi:contieneOpcion(user.centro.opcHabil, '121_RESUMEN_VEGALSA')}">
					<div class="p70_btn_eurosCajas">
						<img id="p70_img_eurosCajas"
							src="./misumi/images/cajasEuros_128x128.png?version=${misumiVersion}" />
					</div>
				</c:if>
				<c:if test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && (misumi:contieneOpcion(user.centro.opcHabil, '121_RESUMEN_VEGALSA'))}">
					<div class="p70_btn_eurosCajas2">
						<img id="p70_img_cajasVegalsa" src="./misumi/images/cajasVegalsa.png?version=${misumiVersion}" />
					</div>
					<c:if test="${(misumi:contieneOpcion(user.centro.opcHabil, '51_DETALLADO_GESTION_EUROS_CONS'))}">
						<div class="p70_btn_eurosCajas3">
							<img id="p70_img_eurosVegalsa" src="./misumi/images/eurosVegalsa.png?version=${misumiVersion}" />
						</div>
					</c:if>
				</c:if>
			</div>
			<div id="p70_AreaInformacionCajas" style="display: none;">
				<fieldset id="p70_informacionCajasFieldset">
					<div class="p70_informacionCajas">
						<input id="p70_wsResult" type="hidden"
							value="${lengthDetallePedido}"></input> <label
							id="p70_lbl_informacionSeccion" class="etiquetaCampo"></label> <label
							id="p70_lbl_informacionCajas" class="etiquetaValor"></label>
					</div>
				</fieldset>
			</div>
		</div>
		<c:if
			test="${user.perfil == 1 || user.perfil == 4 || (user.perfil == 2)}">
			<c:if
				test="${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '50_DETALLADO_GESTION_EUROS')}">
				<div id="p70_AreaCajasEuros" style="display: none;">
					<fieldset id="p70_CajasEurosFieldset">
						<legend>
							<spring:message code="p70_fieldset.gestionEuros" />
						</legend>
						<div id="p70_infoGestionCajasEurosDiv">


							<div id="p70_eurosCajasVal">

								<div id="p70_eurosCajasLabel">
									<div class="p70_labelEuros">
										<label id="p70_labelEuros"
											class="p70_tamanioLetraNegrita etiquetaCampo"><spring:message
												code="p70_fieldset.gestionEuros.euros" /></label>
									</div>
									<div class="p70_labelCajas">
										<label id="p70_lbl_CajasIniciales"
											class="p70_tamanioLetraNegrita etiquetaCampo"><spring:message
												code="p70_fieldset.gestionEuros.cajas" /></label>
									</div>
								</div>

								<div id="p70_eurosCajasIniciales">
									<div class="p70_valorEurosIniciales">
										<label id="p70_inputEurosIniciales"
											class="p70_tamanioLetra valorCampo"></label>
									</div>
									<div class="p70_valorCajasIniciales">
										<label id="p70_inputCajasIniciales"
											class="p70_tamanioLetra valorCampo" /></label>
									</div>
								</div>

								<div id="p70_eurosCajasflechas">
									<div class="p70_flechaEuros">
										<img id="p70_imgFlecha"
											src="./misumi/images/arrow_right_25.png?version=${misumiVersion}" />
									</div>
									<div class="p70_flechaCajas">
										<img id="p70_imgFlecha"
											src="./misumi/images/arrow_right_25.png?version=${misumiVersion}" />
									</div>
								</div>

								<div id="p70_eurosCajasFinales">
									<div class="p70_valorEurosFinales">
										<input type=text id="p70_inputEurosFinales"
											style="font-size: 12pt;" class="p70_tamanioLetra input100" />
										<input type=text id="p70_inputEurosFinalesHidden"
											class="input40" style="display: none;" />
									</div>
									<div class="p70_valorCajasFinales">
										<label id="p70_inputCajasFinales"
											class="p70_tamanioLetra valorCampo" /></label> <input type=text
											id="p70_inputCajasFinalesHidden" class="input40"
											style="display: none;" />
									</div>
								</div>

							</div>

							<!-- 
												<div id="p70_eurosCajasVal">
													<div id="p70_euros">
														<div class="p70_labelEuros">														
															<label id="p70_lbl_EurosIniciales" class="p70_tamanioLetra etiquetaCampo" ><spring:message code="p70_fieldset.gestionEuros.euros" /></label>
														</div>
				 										<div class="p70_valorEuros">														
															<label id="p70_inputEurosIniciales" class="valorCampo"></label>
														</div>
														<div class="p70_flechaEuros">
															<img id="p70_imgFlecha" src="./misumi/images/arrow_right_25.png?version=${misumiVersion}" />															
														</div>
														<div class="p70_valorEuros">
															<input type=text id="p70_inputEurosFinales" class="input60"/>
															<input type=text id="p70_inputEurosFinalesHidden" class="input40" style="display:none;"/>
														</div>										
													</div>
													
													<div id="p70_cajas">
														<div class="p70_labelCajas">
															<label id="p70_lbl_CajasIniciales" class="p70_tamanioLetra etiquetaCampo"><spring:message code="p70_fieldset.gestionEuros.cajas" /></label>
														</div>
														<div class="p70_valorCajas">
															<label id="p70_inputCajasIniciales" class="valorCampo"/></label>
														</div>
														<div class="p70_flechaCajas">
															<img id="p70_imgFlecha" src="./misumi/images/arrow_right_25.png?version=${misumiVersion}" />
														</div>	
														<div class="p70_valorCajas">
															<label id="p70_inputCajasFinales" class="valorCampo"/></label>
															<input type=text id="p70_inputCajasFinalesHidden" class="input40" style="display:none;"/>
														</div>	
													</div>
													
												</div>
												-->

							<div id="p70_parametrizacionEurosCajas">
								<div id="p70_parametrizacionCajasEurosTitle">
									<label id="p70_lbl_parametrizacionCajasEurosTitle"> <spring:message
											code="p70_gestionEuros.parametrizacion" />
									</label>
								</div>
								<div id="p70_parametrizacionCajasEurosLabels">
									<label id="" class="p70_label"><spring:message
											code="p70_gestionEuros.parametrizacion.oferta" /></label> <label
										id="" class="p70_label"><spring:message
											code="p70_gestionEuros.parametrizacion.rotacion" /></label> <label
										id="" class="p70_label"> <spring:message
											code="p70_gestionEuros.parametrizacion.respetar" /></label>
								</div>
								<div id="p70_parametrizacionCajasEurosCheck1">
									<div class="">
										<input id="p70_chk_enOferta" type="checkbox" class="" checked />
										<label id="p70_lbl_enOferta" class="etiquetaCampo"><spring:message
												code="p70_gestionEuros.parametrizacion.oferta.enOferta" /></label>
									</div>
									<div class="">
										<input id="p70_chk_altaRotacion" type="checkbox" class=""
											checked /> <label id="p70_lbl_altaRotacion"
											class="etiquetaCampo"><spring:message
												code="p70_gestionEuros.parametrizacion.rotacion.altaRotacion" /></label>
									</div>
									<div class="">
										<input id="p70_chk_imc" type="checkbox" class="" checked /> <label
											id="p70_lbl_imc" class="etiquetaCampo"><spring:message
												code="p70_gestionEuros.parametrizacion.respetar.imc" /></label>
									</div>
								</div>
								<div id="p70_parametrizacionCajasEurosCheck2">
									<div class="">
										<input id="p70_chk_sinOferta" type="checkbox" class="" checked />
										<label id="p70_lbl_sinOferta" class="etiquetaCampo"><spring:message
												code="p70_gestionEuros.parametrizacion.oferta.sinOferta" /></label>
									</div>
									<div class="">
										<input id="p70_chk_mediaRotacion" type="checkbox" class=""
											checked /> <label id="p70_lbl_mediaRotacion"
											class="etiquetaCampo"><spring:message
												code="p70_gestionEuros.parametrizacion.rotacion.mediaRotacion" /></label>
									</div>
									<input type="button" id="p70_btn_calcular"
										class="boton  botonHover"
										value="<spring:message code="p70_gestionEuros.btn.calcular" />">
								</div>
								<div id="p70_parametrizacionCajasEurosCheck3">
									<div class="" style="visibility: hidden;">
										<input id="" type="checkbox" class="" checked /> <label id=""
											class="etiquetaCampo"></label>
									</div>
									<div class="">
										<input id="p70_chk_bajaRotacion" type="checkbox" class=""
											checked /> <label id="p70_lbl_bajaRotacion"
											class="etiquetaCampo"><spring:message
												code="p70_gestionEuros.parametrizacion.rotacion.bajaRotacion" /></label>
									</div>
									<input type="button" id="p70_btn_propuestaInicial"
										class="boton  botonHover"
										value="<spring:message code="p70_gestionEuros.btn.propuestaInicial" />">
								</div>
							</div>
						</div>
						<!-- <div id="p70_infoGestionCajasEurosDivBotonera">
												<input type="button" id="" class="boton  botonHover" value="<spring:message code="p70_gestionEuros.btn.calcular" />">
												<input type="button" id="" class="boton  botonHover" value="<spring:message code="p70_gestionEuros.btn.propuestaInicial" />">
											</div>-->
					</fieldset>
				</div>
			</c:if>
		</c:if>
	</div>
	<!-- AREA DE PESTAÑAS 	-->

	<div id="p70_AreaPestanas" style="display: none;">
		<div id="p70_pestanas">
			<ul>
				<li id="p70_pestanaSinOfertaTab"><a
					href="#p70_pestanaSinOferta"><span
						id="p70_fld_contadorSinOferta"></span></a><input type=hidden
					id="p70_pestanaSinOfertaCargada"></input></li>
				<li id="p70_pestanaOfertaTab"><a href="#p70_pestanaOferta"><span
						id="p70_fld_contadorOferta"></span></a><input type=hidden
					id="p70_pestanaOfertaCargada"></input></li>
				<label id="p70_lbl_msgCantidades" class="etiquetaCampo"
					style="margin-right: 20px; display: none;"><spring:message
						code="p70_detalladoPedido.msgCantidades" /></label>
			</ul>
			<div id="p70_pestanaSinOferta">
				<%@ include file="/WEB-INF/views/p75_detalladoPedidoSinOferta.jsp"%>
			</div>
			<div id="p70_pestanaOferta">
				<%@ include file="/WEB-INF/views/p76_detalladoPedidoOferta.jsp"%>
			</div>
		</div>
	</div>




	<div id="p70_AreaResultados" style="display: none;">

		<table id="gridP70PedidoDatos"></table>
		<div id="pagerP70PedidoDatos"></div>
		<table id="gridP70PedidoDatosTextil"></table>
		<div id="pagerP70PedidoDatosTextil"></div>

		<div id="p70_AreaNotaPie" style="display: none;">
			<spring:message code="p70_detalladoPedido.notaPie" />
		</div>
		<!--  
				<div id="p70_AreaNotaCarne" style="display: none;">
					<spring:message code="p70_detalladoPedido.notaCarne" />
				</div>
				-->
		<div id="p70_AreaNotaDexenx" style="display: none;">
			<spring:message code="p70_detalladoPedido.notaDexenx" />
		</div>
		<div id="p70_AreaNotaRedondeo" style="display: none;">
			<spring:message code="p70_detalladoPedido.notaRedondeo" />
		</div>
		<div id="p70_AreaNotaBloqueoAlAlza" style="display: none;">
			<spring:message code="p70_detalladoPedido.notaBloqueoAlAlza" />
		</div>
		<input type="hidden" id="p70_fld_Selecc" value=""></input>

	</div>
	<!--  
			<div id="p70_AreaLeyenda">
				<label id="p70_OfertaLeyenda"><spring:message code="p70_detalladoPedido.leyenda.titulo" /></label>
				<label><spring:message code="p70_detalladoPedido.leyenda.descripcion" /></label>
			</div>
			-->

	<%@ include file="/WEB-INF/views/p56_ayudaPopup.jsp"%>
	<%@ include file="/WEB-INF/views/p17_referenciasCentroPopupVentas.jsp"%>
	<%@ include file="/WEB-INF/views/p71_detalladoPedidoNuevo.jsp"%>
	<%@ include file="/WEB-INF/views/p77_popUpDetalladoPedidoArbol.jsp"%>
	<%@ include file="/WEB-INF/views/p114_popUpPluPendiente.jsp" %>
</div>
<div id="excellPopup"></div>
<div id="excellWindow"></div>
<div id="resumenVegalsa"></div>

<div id="p70dialog-confirm" style="display:none;" title="<spring:message code="p70_popUpPluPendiente.tituloPopup"/>">
	<div id="p70_confirm">
		<label id="p70_lbl_mensajeGuardado" class="p70_lbl_mensajeGuardado"></label>
		<label id="p70_lbl_mensajePlu" class="p70_lbl_mensajePlu"></label>
	</div>
</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>