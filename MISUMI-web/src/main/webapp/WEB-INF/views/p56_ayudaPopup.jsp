<%-- <script src="./misumi/scripts/p56ayudaPopup.js?version=${misumiVersion}" type="text/javascript"></script> --%>

<!-- ----- Contenido de la JSP ----- -->
<div id="p56_AreaAyuda" style="display: none" title="<spring:message code="p56_ayuda.ayuda"/>">
	<div id="p56_infoCalendario">
<!-- Sección de combobox -->
		<div id="p56_AreaOferta">
			<div id="p56_combo" class="comboBox comboBoxExtraLarge">
				<label id="p56_lbl_ventasEnUltimasOfertas" class="etiquetaCampo"><spring:message
						code="p56_ayuda.ventasEnUltimasOfertas" /> </label> <select
					id="p56_cmb_ventasEnUltimasOfertas"></select>
			</div>
			<!-- Sección de precio periodo -->
			<div id="p56_AreaPrecioPeriodo">
				<label id="p56_lbl_totalVentas" class="etiquetaCampo"><spring:message
						code="p56_ayuda.totalVentas" /> </label>
				<div id="p56_arrow"></div>
				<label id="p56_lbl_cantidadTotalVentas" class="etiquetaCampo">
				</label> <label id="p56_lbl_precio" class="etiquetaCampo p56_precioMoneda"><spring:message
						code="p56_ayuda.precio" /> </label>
				<div id="p56_arrow"></div>
				<label id="p56_lbl_cantidadMoneda"
					class="etiquetaCampo p56_precioMoneda"></label> <label
					id="p56_lbl_moneda" class="etiquetaCampo p56_precioMoneda"><spring:message
						code="p56_ayuda.moneda" /> </label>
			</div>
		</div>

		<!-- Sección de precio periodo -->
		<!-- <div id="p56_AreaPrecioPeriodo">
			<label id="p56_lbl_precio" class="etiquetaCampo p56_precioMoneda"><spring:message
					code="p56_ayuda.precio" /> </label> <label id="p56_lbl_cantidadMoneda"
				class="etiquetaCampo p56_precioMoneda"></label> <label
				id="p56_lbl_moneda" class="etiquetaCampo p56_precioMoneda"><spring:message
					code="p56_ayuda.moneda" /> </label> <label id="p56_lbl_periodo"
				class="etiquetaCampo"><spring:message
					code="p56_ayuda.periodo" /> </label> <label id="p56_lbl_periodoOferta"
				class="etiquetaCampo"> </label>
		</div>-->

		<!-- Sección calendario -->
		<div id="p56_AreaCalendario">
			<div id="p56_tituloTabla">
				<div id="p56_showTitle">
					<div id="p56_pagAnt"></div>
				</div>
			</div>
			<div id="p56_tablaCalendario">
				<table id="p56_ventasUltimasOfertasTablaEstructura" border="1"
					class="ui-widget">
					<thead class="ui-widget-header">
						<tr>
							<th class="ventasUltimasOfertasTablaTh"><spring:message
									code="p56_ayuda.tablaUltimasOfertas.lunesAbrev" /></th>
							<th class="ventasUltimasOfertasTablaTh"><spring:message
									code="p56_ayuda.tablaUltimasOfertas.martesAbrev" /></th>
							<th class="ventasUltimasOfertasTablaTh"><spring:message
									code="p56_ayuda.tablaUltimasOfertas.miercolesAbrev" /></th>
							<th class="ventasUltimasOfertasTablaTh"><spring:message
									code="p56_ayuda.tablaUltimasOfertas.juevesAbrev" /></th>
							<th class="ventasUltimasOfertasTablaTh"><spring:message
									code="p56_ayuda.tablaUltimasOfertas.viernesAbrev" /></th>
							<th class="ventasUltimasOfertasTablaTh"><spring:message
									code="p56_ayuda.tablaUltimasOfertas.sabadoAbrev" /></th>
							<th class="ventasUltimasOfertasTablaTh"><spring:message
									code="p56_ayuda.tablaUltimasOfertas.domingoAbrev" /></th>
						</tr>
					</thead>
					<tbody class="ui-widget-content">
						<tr>
							<td id="p56_ventasUltimasOfertasDiaTd11" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd12" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd13" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd14" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd15" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd16" class="tdDay">1 Mes</td>
							<td id="p56_ventasUltimasOfertasDiaTd17" class="tdDay">dd</td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasCantidadMesTd11"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd12"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd13"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd14"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd15"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd16"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd17"
								class="ventasUltimasOfertasTabla56Td"></td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasDiaTd21" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd22" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd23" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd24" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd25" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd26" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd27" class="tdDay">dd</td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasCantidadMesTd21"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd22"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd23"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd24"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd25"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd26"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd27"
								class="ventasUltimasOfertasTabla56Td"></td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasDiaTd31" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd32" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd33" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd34" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd35" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd36" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd37" class="tdDay">dd</td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasCantidadMesTd31"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd32"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd33"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd34"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd35"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd36"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd37"
								class="ventasUltimasOfertasTabla56Td"></td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasDiaTd41" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd42" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd43" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd44" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd45" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd46" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd47" class="tdDay">dd</td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasCantidadMesTd41"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd42"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd43"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd44"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd45"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd46"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd47"
								class="ventasUltimasOfertasTabla56Td"></td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasDiaTd51" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd52" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd53" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd54" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd55" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd56" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd57" class="tdDay">dd</td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasCantidadMesTd51"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd52"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd53"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd54"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd55"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd56"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd57"
								class="ventasUltimasOfertasTabla56Td"></td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasDiaTd61" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd62" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd63" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd64" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd65" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd66" class="tdDay">dd</td>
							<td id="p56_ventasUltimasOfertasDiaTd67" class="tdDay">dd</td>
						</tr>
						<tr>
							<td id="p56_ventasUltimasOfertasCantidadMesTd61"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd62"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd63"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd64"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd65"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd66"
								class="ventasUltimasOfertasTabla56Td"></td>
							<td id="p56_ventasUltimasOfertasCantidadMesTd67"
								class="ventasUltimasOfertasTabla56Td"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="p56_tituloTabla2">
				<div id="p56_showTitle2">
					<div id="p56_pagSig"></div>
				</div>
			</div>
			<div id="p56_tablaCalendarioHidden">
				<input id="p56_ventasUltimasOfertasCantidadMesFechaTd11" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd12" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd13" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd14" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd15" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd16" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd17" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd21" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd22" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd23" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd24" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd25" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd26" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd27" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd31" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd32" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd33" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd34" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd35" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd36" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd37" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd41" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd42" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd43" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd44" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd45" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd46" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd47" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd51" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd52" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd53" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd54" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd55" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd56" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd57" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd61" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd62" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd63" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd64" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd65" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd66" value=" "
					type="hidden"> <input
					id="p56_ventasUltimasOfertasCantidadMesFechaTd67" value=" "
					type="hidden">
			</div>
		</div>
	</div>

	<!-- No hay información -->
	<div id="p56_sinVentasOfertas">
		<label id="p56_lbl_sinVentasOfertas" class="etiquetaCampo"><spring:message
				code="p56_ayuda.sinVentasOfertas" /></label>
	</div>

	<div id="leyenda">
		<label id="p57_lbl_pendienteRecibirCarga"><spring:message
				code="p56_referenciasCentroM.pendienteRecibir" /></label> <label
			id="p57_lbl_pendienteRecibir"><spring:message
				code="p56_referenciasCentroM.pendienteRecibir1" /></label> <label
			id="p57_cantidades_hoy"> </label> <label
			id="p57_lbl_pendienteRecibir2"><spring:message
				code="p56_referenciasCentroM.pendienteRecibir2" /></label> <label
			id="p57_cantidades_mannana"></label>
	</div>
</div>