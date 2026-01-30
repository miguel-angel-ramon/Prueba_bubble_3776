<script src="./misumi/scripts/p103PopUpEntradaDar.js?version=${misumiVersion}" type="text/javascript"></script>
<!--  Contenido página -->
<div id="p103_popUpEntradaDar" style="display:none;">
	<div id="p103_entradaDar">
		<div id="p103_AreaCabeceraEntrada">	
			<div id="p103_areaCabeceraNoEditable">
				<div class="p103_areaCabeceraNoEditableFila">			
					<div class="p103_areaCabeceraNoEditableParteMitad">
						<div class="p103_areaCabeceraNoEditableParteMitad">
							<label class="p103_lbl_titulo p103_lbl_titulo_bloque"><spring:message code="p103_dar.entrada.formulario.numPedido" /></label>						
						</div>
						<div class="p103_areaCabeceraNoEditableParteMitad">
							<div>
								<label id="p103_areaCabecera_numPed" class="p103_lbl_dato"> </label>
							</div>
						</div>
					</div>
					<div class="p103_areaCabeceraNoEditableParteMitad"> 
						<div class="p103_areaCabeceraNoEditableParteMitad">
							<label class="p103_lbl_titulo p103_lbl_titulo_bloque"><spring:message code="p103_dar.entrada.formulario.fechaTarifa"/></label>
						</div>
						<div class="p103_areaCabeceraNoEditableParteMitad">					
							<label id="p103_areaCabecera_fechaTarifa" class="p103_lbl_dato"></label>
						</div>
					</div>
				</div>
				<div class="p103_areaCabeceraNoEditableFila">
					<div class="p103_areaCabeceraNoEditableParteCuarto">
						<label class="p103_lbl_titulo p103_lbl_titulo_bloque"><spring:message code="p103_dar.entrada.formulario.proveedor" /> </label>
					</div>
					<label id="p103_areaCabecera_proveedor" class="p103_lbl_dato"> </label>
				</div>
				<div class="p103_areaCabeceraNoEditableFila">
					<div class="p103_areaCabeceraNoEditableParteCuarto">
						<label class="p103_lbl_titulo p103_lbl_titulo_bloque"><spring:message code="p103_dar.entrada.formulario.tipoRecepcion"/></label>
					</div>
					<label id="p103_areaCabecera_tipoRecepcion" class="p103_lbl_dato"></label>
				</div>
			</div>
			<div id="p103_areaCabeceraEditable">
				<div class="p103_areaCabeceraEditableParte">
					<label class="p103_lbl_titulo"><spring:message code="p103_dar.entrada.formulario.fechaEntrada" /></label>
					<label id="p103_lbl_areaCabecera_fechaEntrada" class="p103_lbl_dato"></label>
					<input id="p103_areaCabecera_datepicker_fechaEntrada" type="text"  class="input100" tabindex="-1" readonly="true">
					<input id="p103_areaCabecera_datepicker_fechaEntrada_orig" type="text" style="display:none"/>
				</div>
				<div class="p103_areaCabeceraEditableParte">
					<label class="p103_lbl_titulo" ><spring:message code="p103_dar.entrada.formulario.albaran" /></label>
					<label id="p103_lbl_areaCabecera_albaran" class="p103_lbl_dato"></label>
					<input id="p103_areaCabecera_albaran" type="text" class="p103_lbl_dato input100">
					<input id="p103_areaCabecera_albaran_orig" type="text" style="display:none"/>
				</div>
				<div class="p103_areaCabeceraEditableParte">
					<label class="p103_lbl_titulo"><spring:message code="p103_dar.entrada.formulario.numIncidencia" /></label>
					<label id="p103_lbl_areaCabecera_numeroIncidencia" class="p103_lbl_dato"></label>
					<input id="p103_areaCabecera_numeroIncidencia" type="text" class="p103_lbl_dato input100"/>
					<input id="p103_areaCabecera_numeroIncidencia_orig" type="text" style="display:none;"/>
				</div>
			</div>
		</div>   
		<div id="p103_AreaReferencias" class="p84_AreaResultados">
			<table id="gridP103DarEntrada"></table>
			<div id="pagerGridp103"></div>
		</div> 
				
		<div id="p103_AreaPie">
			<div id="p103_AreaGuardado">
				<img id="p103_btn_guardado" src="./misumi/images/floppy_30.gif?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios"/>
			</div>					
		</div>
	</div>	
</div>
<!-- Guarda la posición del input de unidades recepcionadas en el que estamos antes de mostrar el 
error para luego al pulsar el aceptar del error, el foco vuelva a ese input. -->
<input type="hidden" id="p103_fld_totalUnidadesRecepcionadas_Selecc" value=""></input>
