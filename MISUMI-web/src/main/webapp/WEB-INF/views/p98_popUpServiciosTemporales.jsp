<script src="./misumi/scripts/p98PopUpServiciosTemporales.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/CalendarioCambioEstacional.js?version=${misumiVersion}" type="text/javascript"></script>

<div id="p98_popUpServiciosTemporales" style="display:none">
	<div id="p98_servicioCmb" class="comboBox comboBoxLarge controlReturnP98">
		<label id="p98_lbl_servicio" class="etiquetaCampo"><spring:message code="p98_calendario.servicio" /></label> 
		<select id="p98_cmb_servicio"></select> 			
	</div>
	<div id="p98_calendarios"></div>
	<div id="p98_buttons">	
		<div class="p98_formCalendarioCelda">
			<label id="p98_lbl_guardar" class="formDevolucionNegrita etiquetaCampo p98_formLetraGuardar"><spring:message code="p98_calendario.labelGuardar"/></label>
		</div>
		<div class="p98_formCalendarioCeldaFloppy">
			<img id="p98_btn_guardado" src="./misumi/images/floppy_30.gif?version=" class=" ">
		</div>
		<div class="p98_formCalendarioCelda">
			<label id="p96_lbl_cambios" class="formDevolucionNegrita etiquetaCampo p98_formLetraGuardar"><spring:message code="p98_calendario.labelCambios" /></label>
		</div>
	</div>
</div>

<div id="p98_calendario" style="display:none;">
	<div id="p98_servicioHabitualSelec" class="p98_diasDeLaSemanaHab">
		<label id="p98_lbl_servicioHabitual" class="etiquetaCampo p98_servicioHabitual"><spring:message code="p98_calendario.servicio.habitual" /></label> 
		<label id="p98_lbl_servicioHabitualSelec" class="etiquetaCampo p98_servicioHabitual"></label> 
	</div>
	<div id="p98_fechaCalendarioSelec" class="p98_diasDeLaSemanaFechaIniFin">
		<div id="p98_fechaInicioServ" class="p98_fechaInicio">
			<label id="p98_lbl_fechaInicio" class="etiquetaCampo"><spring:message code="p98_calendario.fecha.inicio" /></label>
			<input type="text" id="p98_datepickerInicio">
		</div> 
		<div id="p98_fechaFinServ" class="p98_fechaFin">
			<label id="p98_lbl_fechaFin" class="etiquetaCampo"><spring:message code="p98_calendario.fecha.fin" /></label>
			<input type="text" id="p98_datepickerFin">
		</div>
		<div class="p98_noLoQuiero">
			<label id="p98_lbl_noLoQuiero" class="etiquetaCampo"><spring:message code="p98_calendario.fecha.noLoQuiero" /></label>
			<input type="checkbox" id="p98_checkNoLoQuiero" class="p98_checkBoxClicable">
		</div>
	</div>
	<div id="p98_calendario_diasDeLaSemana" class="p98_diasBloque">
		<div id="p98_Lunes" class="p98_diaDeLaSemana"><spring:message code="p98_calendario.dias.lunes" /></div>
		<div id="p98_Martes" class="p98_diaDeLaSemana"><spring:message code="p98_calendario.dias.martes" /></div>
		<div id="p98_Miercoles" class="p98_diaDeLaSemana"><spring:message code="p98_calendario.dias.miercoles" /></div>
		<div id="p98_Jueves" class="p98_diaDeLaSemana"><spring:message code="p98_calendario.dias.jueves" /></div>
		<div id="p98_Viernes" class="p98_diaDeLaSemana"><spring:message code="p98_calendario.dias.viernes" /></div>
		<div id="p98_Sabado" class="p98_diaDeLaSemana"><spring:message code="p98_calendario.dias.sabado" /></div>
		<div id="p98_Domingo" class="p98_diaDeLaSemana"><spring:message code="p98_calendario.dias.domingo" /></div>
	</div>
	<div id="p98_calendario_dias" class="p98_diasBloqueSemana">
		<div id="formDiaEstructuraLunes" class="p98_dia">
			<div id="formDiaEstructuraDiaMesLunes" class="p98_numeroDia">
				<label id="formDiaEstructuraDiaMesLblLunes" class="p98_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfoLunes" class="p98_infoDia">
				<div id="formDiaEstructuraInfoSuministroLunes" class="p98_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerradoLunes" class="p98_suministro1 p98_desIluminar"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbiertoLunes" class="p98_suministro2 p98_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstadoLunes" class="p98_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHiddenLunes" style="display:none">
				<input id="servicioLunes" type="hidden"/>
			</div>
		</div>
		<div id="formDiaEstructuraMartes" class="p98_dia">
			<div id="formDiaEstructuraDiaMes" class="p98_numeroDia">
				<label id="formDiaEstructuraDiaMesLblMartes" class="p98_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfoMartes" class="p98_infoDia">
				<div id="formDiaEstructuraInfoSuministroMartes" class="p98_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerradoMartes" class="p98_suministro1 p98_desIluminar"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbiertoMartes" class="p98_suministro2 p98_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstadoMartes" class="p98_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHiddenMartes" style="display:none">
				<input id="servicioMartes" type="hidden"/>
			</div>
		</div>
		<div id="formDiaEstructuraMiercoles" class="p98_dia">
			<div id="formDiaEstructuraDiaMesMiercoles" class="p98_numeroDia">
				<label id="formDiaEstructuraDiaMesLblMiercoles" class="p98_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfoMiercoles" class="p98_infoDia">
				<div id="formDiaEstructuraInfoSuministroMiercoles" class="p98_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerradoMiercoles" class="p98_suministro1 p98_desIluminar"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbiertoMiercoles" class="p98_suministro2 p98_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstadoMiercoles" class="p98_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHiddenMiercoles" style="display:none">
				<input id="servicioMiercoles" type="hidden"/>
			</div>
		</div>
		<div id="formDiaEstructuraJueves" class="p98_dia">
			<div id="formDiaEstructuraDiaMesJueves" class="p98_numeroDia">
				<label id="formDiaEstructuraDiaMesLblJueves" class="p98_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfoJueves" class="p98_infoDia">
				<div id="formDiaEstructuraInfoSuministroJueves" class="p98_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerradoJueves" class="p98_suministro1 p98_desIluminar"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbiertoJueves" class="p98_suministro2 p98_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstadoJueves" class="p98_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHiddenJueves" style="display:none">
				<input id="servicioJueves" type="hidden"/>
			</div>
		</div>
		<div id="formDiaEstructuraViernes" class="p98_dia">
			<div id="formDiaEstructuraDiaMesViernes" class="p98_numeroDia">
				<label id="formDiaEstructuraDiaMesLblViernes" class="p98_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfoViernes" class="p98_infoDia">
				<div id="formDiaEstructuraInfoSuministroViernes" class="p98_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerradoViernes" class="p98_suministro1 p98_desIluminar"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbiertoViernes" class="p98_suministro2 p98_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstadoViernes" class="p98_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHiddenViernes" style="display:none">
				<input id="servicioViernes" type="hidden"/>
			</div>
		</div>
		<div id="formDiaEstructuraSabado" class="p98_dia">
			<div id="formDiaEstructuraDiaMesSabado" class="p98_numeroDia">
				<label id="formDiaEstructuraDiaMesLblSabado" class="p98_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfoSabado" class="p98_infoDia">
				<div id="formDiaEstructuraInfoSuministroSabado" class="p98_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerradoSabado" class="p98_suministro1 p98_desIluminar"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbiertoSabado" class="p98_suministro2 p98_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstadoSabado" class="p98_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHiddenSabado" style="display:none">
				<input id="servicioSabado" type="hidden"/>
			</div>
		</div>
		<div id="formDiaEstructuraDomingo" class="p98_dia">
			<div id="formDiaEstructuraDiaMesDomingo" class="p98_numeroDia p98_festivoDomingo">
				<label id="formDiaEstructuraDiaMesLblDomingo" class="p98_letraDia"></label>
			</div>
			<div id="formDiaEstructuraInfoDomingo" class="p98_infoDia">
				<div id="formDiaEstructuraInfoSuministroDomingo" class="p98_suministroDia">
					<div id="formDiaEstructuraInfoSuministroCamionCerradoDomingo" class="p98_suministro1 p98_desIluminar"></div>
					<div id="formDiaEstructuraInfoSuministroCamionAbiertoDomingo" class="p98_suministro2 p98_desIluminar"></div>
				</div>
				<div id="formDiaEstructuraEstadoDomingo" class="p98_estadoDia">
					<!-- <label id="formDiaEstructuraEstadoLbl"></label>-->
				</div>
			</div>
			<div id="formDiaEstructuraInfoHiddenDomingo" style="display:none">
				<input id="servicioDomingo" type="hidden"/>
			</div>
		</div>
	</div>
	<div id="p98_servicioTemporalSelec" class="p98_diasDeLaSemana">
		<label id="p98_lbl_servicioTemporal" class="etiquetaCampo p98_servicioTemporal"><spring:message code="p98_calendario.servicio.temporal" /></label> 
		<label id="p98_lbl_servicioTemporalSelec" class="etiquetaCampo p98_servicioTemporal"></label> 
	</div>
	
	<div id="p98dialog-confirmCerrarPopUp" style="display:none;" title="<spring:message code="p98_calendario.cerrarPopUp" />">
		<table id="p85_table_rellenarHuecos">
			<tr>
				<td id="p98_td_img"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
				<td id="p98_td_txt"><span class="p98_txt"></span><spring:message code="p98_calendario.mensajePopUpCerrar" /></td>
			</tr>
		</table>
	</div>
	
</div>