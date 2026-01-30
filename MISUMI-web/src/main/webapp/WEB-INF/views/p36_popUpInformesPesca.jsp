<script src="./misumi/scripts/p36PopUpInformesPesca.js?version=${misumiVersion}" type="text/javascript"></script>
<!--  Contenido página -->
<div id="p36_popupInformesPesca" title="<spring:message code="p36_popUpInformesPesca.tituloPopup"/>" style="display: none;">

	<div id="p36_informesPesca">
		<div id="p36_checkBoxInformesPesca">	
			<div class="p36_informesPescaTitulo">	
				<label><spring:message code="p36_popUpInformesPesca.refAMostrar" /></label>
			</div>
			<div id="p36_cuerpoCheckBox">	
				<div class="p36_checkBox">
					<input id="p36_checkBoxHabitual" class="p36_check" type="checkbox" name="" value="S">
					<label class="p36_lbl"><spring:message code="p36_popUpInformesPesca.habitual"/></label>
				</div>
				<div class="p36_checkBox">
					<input id="p36_checkBoxNoHabitual" class="p36_check" type="checkbox" name="" value="N">
					<label class="p36_lbl"><spring:message code="p36_popUpInformesPesca.noHabitual"/></label>
				</div>
			</div>
		</div>
		<div id="p36_cabeceraInformesPesca">	
			<div class="p36_informesPescaTitulo">	
				<p id="p36_parrafoCabeceraInformesPesca"><spring:message code="p36_popUpInformesPesca.ordenMostrar" /></p>
			</div>
		</div>
		<div id="p36_cuerpoInformesPesca">
			<form id="p36_formListadoPescaMostrador" action="./p36ListadoPescaMostrador/getPdf.do" method="post" target="_blank">
				<div id="p36_divListaOrdenable" class="p36_informesPescaLista">
				</div>
			</form>
		</div>
		<div id="p36_cuerpoNota">
			<p id="p36_cuerpoNotaParrafo"><spring:message code="p36_popUpInformesPesca.nota" /></p>
		</div>		
	</div>	
	
</div>