<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<script src="./misumi/scripts/p96Calendario.js?version=${misumiVersion}" type="text/javascript"></script>

<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/TCalendarioDia.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/CalendarioDia.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/TCalendarioDiaCambioServicio.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/CalendarioProcesosDiarios.js?version=${misumiVersion}" type="text/javascript"></script>
<!--  Miga de pan --> 
<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
		<li><a href="./welcome.do"><spring:message
					code="p96_calendario.welcome" /></a></li>
		<li><spring:message code="p96_devoluciones.devoluciones" /></li>
			</ul>
		</div>
	</div>
</div>	

<!--  Contenido página -->
<div id="contenidoPagina">
	<div id="p96_AreaFiltro">
		<div id="p96_filtroEstructura">
			<div class="p96_filtroEstructuraField">
				<div id="p96_anio" class="comboBox comboBoxLarge controlReturnAnioP96">
					<label id="p96_lbl_anio" class="etiquetaCampo"><spring:message code="p96_calendario.anio" /></label> 
					<select id="p96_cmb_anio"></select> 			
				</div>
				<!-- 
				<div id="p96_servicio" class="comboBox comboBoxMediumShort controlReturnServicioP96">
					<label id="p96_lbl_servicio" class="etiquetaCampo"><spring:message code="p96_calendario.servicio" /></label> 
					<select id="p96_cmb_servicio"></select> 			
				</div>
				 -->
			</div>
		</div>
		<fieldset id="p96_alerta" style="display: none;">
			<legend id="p96_alertTxt"><spring:message code="p96_calendario.alerta" /></legend>
			<div id="p96_alertaDiv">
				<div id="p96_alertaInfo"><spring:message code="p96_calendario.alerta.info" /></div>
				<div id="p96_Img">					
					<div id="p96_mesCalendario"></div>
					<div id="p96_diaCalendario"></div>
				</div>
			</div>
		</fieldset> 
		<fieldset id="p96_mensajeValidado" style="display: none;">
			<legend id="p96_mensajeValidadoTxt"></legend>
			<div id="p96_mensajeValidadoDiv">
				<div id="p96_mensajeValidadoInfo"><span id="p96_mensajeValidadoSpan"><spring:message code="p96_calendario.mensajeValidado.info" /></span></div>
				<div id="p96_ImgMensajeValidado">					
					<img id="p96_btn_validado" src="./misumi/images/mano_up_64x64.png?version="  class="p96_btn_ManoUp"> 
				</div>
			</div>
		</fieldset>
		<fieldset id="p96_mensajeNoValidado" style="display: none;">
			<legend id="p96_mensajeNoValidadoTxt"></legend>
			<div id="p96_mensajeNoValidadoDiv">
				<div id="p96_mensajeNoValidadoInfo"><span id="p96_mensajeNoValidadoSpan"><spring:message code="p96_calendario.mensajeNoValidado.info" /></span></div>
				<div id="p96_ImgMensajeNoValidado">					
					<img id="p96_btn_noValidado" src="./misumi/images/mano_down_64x64.png?version="  class="p96_btn_ManoDown"> 
				</div>
			</div>
		</fieldset>
		<!--  
		<fieldset id="p96_pg">
			<legend id="p96_pgTxt"><spring:message code="p96_calendario.pg" /><img src="./misumi/images/lupa_20x20.png?version=" class=""></legend>
			<div id="p96_pgDiv">
				<label>ENERO 2019</label>
			</div>
		</fieldset>
		-->
		<div id="p96_filterButtons">
			<div id="p96_filterButtonsInterno">
			<div id="p96_btn_calendarioAnualBloque" class="p96_BloqueBotones33">					
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_calendario" class="formDevolucionNegrita etiquetaCampo p96_formLetraAviso" ><spring:message code="p96_calendario.labelCalendario" /></label>
							</div>
						</div>
						<div class="p96_Bloque50Largo">
							<div class="p96_formCalendarioCelda">
								<img id="p96_btn_calendarioAnual" src="./misumi/images/calendarioAnual.png?version="  class="p96_btn p96_btn_change">
							</div>
						</div>
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_anual" class="formDevolucionNegrita etiquetaCampo p96_formLetraAviso" ><spring:message code="p96_calendario.labelAnual" /></label>
							</div>
						</div>
					
				</div>
				<div id="p96_btn_avisosBloque" class="p96_BloqueBotones33" style="visibility: hidden;">
					
						<div class="p96_Bloque25Largo p96_aviso">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_avisoVer" class="formDevolucionNegrita etiquetaCampo p96_formLetraAviso" ><spring:message code="p96_calendario.labelVer" /></label>
							</div>
						</div>
						<div class="p96_Bloque50Largo p96_aviso">
							<div class="p96_formCalendarioCelda">
								<img id="p96_btn_aviso" src="./misumi/images/dialog-error-30.png?version="  class="p96_btn_aviso">
							</div>
						</div>
						<div class="p96_Bloque25Largo p96_aviso">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_avisoAvisos" class="formDevolucionNegrita etiquetaCampo p96_formLetraAviso" ><spring:message code="p96_calendario.labelAviso" /></label>
							</div>
						</div>
					
				</div>
				<div id="p96_btn_cambiosEstacionalesBloque" class="p96_BloqueBotones33" style="visibility: hidden;">
					
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_visualizar" class="formDevolucionNegrita etiquetaCampo p96_formLetraVisualizar"><spring:message code="p96_calendario.labelVisualizar" /></label>
							</div>
						</div>
						<div class="p96_Bloque50Largo">
							<div class="p96_formCalendarioCelda">
								<img id="p96_btn_estacion" src="./misumi/images/change.png?version="  class="p96_btn p96_btn_change ">
							</div>
						</div>
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_estacional" class="formDevolucionNegrita etiquetaCampo p96_formLetraVisualizar" ><spring:message code="p96_calendario.labelCambioEstacional" /></label>
							</div>
						</div>
					
				</div>
				<div id="p96_btn_guardadoBloque" class="p96_BloqueBotones33" style="visibility: hidden;">
					
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_guardar" class="formDevolucionNegrita etiquetaCampo p96_formLetraGuardar"><spring:message code="p96_calendario.labelGuardar"/></label>
							</div>
						</div>
						<div class="p96_Bloque50Largo">
							<div class="p96_formCalendarioCelda">
								<img id="p96_btn_guardado" src="./misumi/images/floppy_30.gif?version="  class="p96_btn_floppy ">
							</div>
						</div>
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_cambios" class="formDevolucionNegrita etiquetaCampo p96_formLetraGuardar"><spring:message code="p96_calendario.labelCambios" /></label>
							</div>
						</div>
					
				</div>
				<div id="p96_btn_finalizadoBloque" class="p96_BloqueBotones33" style="visibility: hidden;">
					
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_validar" class="formDevolucionNegrita etiquetaCampo p96_formLetraValidar"><spring:message code="p96_calendario.labelValidar" /></label>
							</div>
						</div>
						<div class="p96_Bloque50Largo">
							<div class="p96_formCalendarioCelda">
								<img id="p96_btn_finalizado" src="./misumi/images/finalizar.png?version="  class="p96_btn p96_btn_finalizar ">
							</div>
						</div>
						<div class="p96_Bloque25Largo">
							<div class="p96_formCalendarioCelda">
								<label id="p96_lbl_calendario" class="formDevolucionNegrita etiquetaCampo p96_formLetraValidar"><spring:message code="p96_calendario.labelCalendario" /></label>
							</div>
						</div>
					
				</div>
			</div> 
		</div>
		<input type=hidden id="p96_origenPantalla" value="${origenPantalla}"></input>	
	</div>
	<div id="p96_AreaResultados" style="">
		<div id="">
			<div id="p96_filtroServicio">
					<div id="p96_servicio" class="comboBox comboBoxLarge controlReturnServicioP96">
						<label id="p96_lbl_servicio" class="etiquetaCampo"><spring:message code="p96_calendario.servicio" /></label> 
						<select id="p96_cmb_servicio"></select> 			
					</div>
			</div>
			<div id="p96_contadorFestivosLocales">
				<label id="p96_lbl_contador" class="etiquetaCampo"><spring:message code="p96_calendario.contador" /></label> 
				<label id="p96_lbl_diasRestantes" class="etiquetaCampo"></label> 
			</div>
		</div>

		<div id="p96_calendario" style='display:none'>
			<div id="p96_pagAnterior" class="p96_divFlecha p96_pagAnt_Des"></div>
			<div id="p96_estructuraCalendario">
				 <div id="p96_calendario_mes"></div>
				<div id="p96_calendario_diasDeLaSemana">
					<div class="p96_diaDeLaSemana"><spring:message code="p96_calendario.dias.lunes" /></div>
					<div class="p96_diaDeLaSemana"><spring:message code="p96_calendario.dias.martes" /></div>
					<div class="p96_diaDeLaSemana"><spring:message code="p96_calendario.dias.miercoles" /></div>
					<div class="p96_diaDeLaSemana"><spring:message code="p96_calendario.dias.jueves" /></div>
					<div class="p96_diaDeLaSemana"><spring:message code="p96_calendario.dias.viernes" /></div>
					<div class="p96_diaDeLaSemana"><spring:message code="p96_calendario.dias.sabado" /></div>
					<div class="p96_diaDeLaSemana"><spring:message code="p96_calendario.dias.domingo" /></div>
				</div>
				<div id="p96_calendario_dias">

				</div>
			</div>
			<div id="p96_pagSiguiente" class="p96_divFlecha p96_pagSig"></div>
		</div>
	</div>
	<div id="p96_estructuraDiaHidden" style="display:none;">
		<div id="formDiaEstructura" class="p96_dia">
			<div id="formDiaEstructuraDiaMes" class="p96_numeroDia">
				<label id="formDiaEstructuraDiaMesLbl" class="p96_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfo" class="p96_infoDia">
				<div id="formDiaEstructuraInfoSuministro" class="p96_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerrado" Style="text-align:right;" class="p96_suministro1 p96_desIluminar"></div><div class="p96_suministroWarning"><img id="formDiaEstructuraInfoImgWarning"  class="p96_avisoDia" style="display: none;" src="./misumi/images/dialog-error-16.gif"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbierto" class="p96_suministro2 p96_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstado" class="p96_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHidden" style="display:none">
				<input id="fechaCalendario" type="hidden"/>
				<input id="festivo" type="hidden"/>
				<input id="ponerDiaVerde" type="hidden"/>
				<input id="cerrado" type="hidden"/>
				<input id="servicioHabitual" type="hidden"/>
				<input id="cambioEstacional" type="hidden"/>
				<input id="cambioManual" type="hidden"/>
				<input id="ecambioPlataforma" type="hidden"/>
				<input id="esePuedeModificarServicio" type="hidden"/>
				<input id="eaprobadoCambio" type="hidden"/>
				<input id="suministro" type="hidden"/>
				<input id="flgServiciosBuscados" type="hidden"/>
				<input id="flgCambioManualServicioNulo" type="hidden"/>
				<input id="diaPasado" type="hidden"/> <!-- Indica si el dia es anterior al Sysdate -->
				<input id="verdePlataforma" type="hidden"/>
				<input id="puedeSolicitarServicio" type="hidden"/>
			</div>
		</div>
		<div id="formDiaEstructuraHidden" class="p96_diaHidden"></div>
	</div>
	<div id="formHiddensAnioEstructura" style="display:none;">
		<input id="tipoEjercicio" type="hidden"/>
		<input id="anioEjercicio" type="hidden"/>
		<input id="flgPendienteValidarEjercicio" type="hidden"/>
		<input id="fechaLimiteValidacion" type="hidden"/>
		<input id="strFechaLimiteValidacion" type="hidden"/>
		<input id="flgEjercicioValidado" type="hidden"/>
		<input id="flgCambioEstacional" type="hidden"/>
		<input id="flgModificarServicioCentro" type="hidden"/>
		<input id="flgModificarCalendarioCentro" type="hidden"/>
		<input id="flgModificarServicioTecnico" type="hidden"/>
		<input id="flgModificarCalendarioTecnico" type="hidden"/>
	</div>
</div>

<div id="p97_AreaPopupsServicios">
	<%@ include file="/WEB-INF/views/p97_popUpServicios.jsp" %>
</div>
<div id="p98_AreaPopupsServiciosTemporales">
	<%@ include file="/WEB-INF/views/p98_popUpServiciosTemporales.jsp" %>
</div>
<div id="p99_AreaPopupsCalendarioWarnings">
	<%@ include file="/WEB-INF/views/p99_popUpCalendarioWarnings.jsp" %>
</div>
<div id="p100_AreaPopupsCalendarioAnual">
	<%@ include file="/WEB-INF/views/p100_popUpCalendarioAnual.jsp" %>
</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>
