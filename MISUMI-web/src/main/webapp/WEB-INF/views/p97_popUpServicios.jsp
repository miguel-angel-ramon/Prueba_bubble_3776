<script src="./misumi/scripts/p97PopUpServicios.js?version=${misumiVersion}" type="text/javascript"></script>

<div id="p97_popUpServicios" style="display:none">
	<div id="p97_serviciosHabitualesNoHabituales">
		<fieldset id="p97_habitual" >
			<legend id="p97_servicioHabitualTxt" >
				<div class="p97_legendText">
					<spring:message code="p97_popUpServicios.servicioHabitual" />				
				</div>
				<div class="p97_legendImg">
					<img class="p97_img" src="./misumi/images/factory_64x64.png?version=${misumiVersion}"  class="">
				</div> 
			</legend>
			<div id="p97_listaCheckServicioHabitualDiv"></div>
		</fieldset>
		<div id="p97_flecha">
			<img src="./misumi/images/arrow_right_48.png?version=${misumiVersion}">
		</div>
		<fieldset id="p97_estacional" >
			<legend id="p97_servicioEstacionalTxt">
				<div class="p97_legendText">
					<spring:message code="p97_popUpServicios.servicioEstacional" />
				</div>
				<div class="p97_legendImg">
					<img class="p97_img" id="" src="./misumi/images/factory_64x64.png?version=${misumiVersion}"  class="">
				</div> 
			</legend>
			<div id="p97_listaCheckServicioEstacionalDiv"></div>
		</fieldset>
		<div id="p97_flecha2">
			<img src="./misumi/images/arrow_right_48.png?version=${misumiVersion}">
		</div>
		<fieldset id="p97_temporal" >
			<legend id="p97_servicioTemporalTxt">
				<div class="p97_legendText">
					<spring:message code="p97_popUpServicios.servicioTemporal" />
				</div>
				<div class="p97_legendImg">
					<img class="p97_img" id="" src="./misumi/images/shop_64x64.png?version=${misumiVersion}"  class="">
				</div> 
			</legend>
			<div id="p97_listaCheckServicioTemporalDiv"></div>
		</fieldset>
			<div id="p97_flecha3">
			<img src="./misumi/images/arrow_right_48.png?version=${misumiVersion}">
		</div>
		<fieldset id="p97_plataforma" >
			<legend id="p97_servicioPlataformaTxt">
				<div class="p97_legendText">
					<spring:message code="p97_popUpServicios.servicioPlataforma" />
				</div>
				<div class="p97_legendImg">
					<img class="p97_img" id="" src="./misumi/images/officeMan.png?version=${misumiVersion}"  class="">
				</div> 
			</legend>
			<div id="p97_listaCheckServicioPlataformaDiv"></div>
		</fieldset>
		
		<div id="p97_checksOriginales" style="display:none"></div>
	</div>
	<div id="p97_serviciosHabitualesNoHabitualesBtn">
		<img id="p97_btn_tick" class="p97_btn" src="./misumi/images/tick_82x82.png?version=">
		<!--<img id="p97_btn_cross" class="p97_btn" src="./misumi/images/cruz_82x82.png?version=">-->
	</div>
	<!-- <div id="p97_save">		
		<div class="p97_formCalendarioCelda">
			<label id="p97_lbl_guardar" class="formDevolucionNegrita etiquetaCampo p97_formLetraGuardar"><spring:message code="p97_calendario.labelGuardar"/></label>
		</div>
		<div class="p97_formCalendarioCeldaFloppy">
			<img id="p97_btn_guardado" src="./misumi/images/floppy_30.gif?version=">
		</div>
		<div class="p97_formCalendarioCelda">
			<label id="p97_lbl_cambios" class="formDevolucionNegrita etiquetaCampo p97_formLetraGuardar"><spring:message code="p97_calendario.labelCambios" /></label>
		</div>
	</div>-->
	<div style="display:none">
		<input type="hidden" id="diaServiciosSeleccionado"/>
	</div>
</div>