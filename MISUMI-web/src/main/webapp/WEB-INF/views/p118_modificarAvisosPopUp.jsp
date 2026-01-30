<div id="p118_popup" style="display: none" title="<spring:message code="p118_popup.titulo"/>">
	<diV id="p118_formulario">
		<div id="p118_campos">
			<input id="p118_fld_codAviso" type="text">	
			<div id="p118_bloqueFechaIni" class="p118_bloque">
				<div class="p118_lbl_fecha">
					<label id="p118_lbl_fechaIni" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.fechaIni" />
					</label>
				</div>
				<div class="p118_fld_fecha">
					<input id="p118_fld_fechaIni" type="text"/>	
				</div>
				<div class="p118_lbl_hora">
					<label id="p118_lbl_horaIni" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.horaIni" />
					</label>
				</div>
				<div class="p118_fld_hora">
					<input type="text" id="p118_fld_horaIni" maxlength="5" width="50px;"/>	
				</div>
			</div>
			<div id="p118_bloqueFechaFin" class="p118_bloque">
				<div class="p118_lbl_fecha">
					<label id="p118_lbl_fechaIni" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.fechaFin" />
					</label>
				</div>
				<div class="p118_fld_fecha">
					<input id="p118_fld_fechaFin" type="text"/>	
				</div>
				<div class="p118_lbl_hora">
					<label id="p118_lbl_horaFin" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.horaFin" />
					</label>
				</div>
				<div class="p118_fld_hora">
					<input type="text" id="p118_fld_horaFin" maxlength="5" width="50px;"/>	
				</div>
			</div>
			<div id="p118_bloqueMensajePc" class="p118_bloque">
				<div class="p118_lbl_mensajePc">
					<label id="p118_lbl_mensajePc" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.mensajePc" />
					</label>
				</div>
				<div class="p118_fld_mensajePc">
					<textarea id="p118_fld_mensajePc_val" rows="3" cols="80" maxlength="1000"></textarea>
				</div>
			</div>
			<div id="p118_bloqueMensajePda" class="p118_bloque">
				<div class="p118_lbl_mensajePda">
					<label id="p118_lbl_mensajePc" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.mensajePda" />
					</label>
				</div>
				<div class="p118_fld_mensajePda">
					<textarea id="p118_fld_mensajePda_val" rows="1" cols="80" maxlength="200"></textarea>
				</div>
			</div>
			<div id="p118_bloqueSociedadesAfectadas" class="p118_bloque">
				<div class="p118_lbl_sociedadesAfectadas">
					<label id="p118_lbl_sociedadesAfectadas" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.sociedadesAfectadas" />
					</label>
				</div>
				<div class="p118_lbl_eroski">
					<label id="p118_lbl_eroski" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.eroski" />
					</label>
				</div>
				<div class="p118_fld_eroski">
					<input id="p118_fld_eroski" type="checkbox"/>	
				</div>
				<div class="p118_lbl_cpb">
					<label id="p118_lbl_cpb" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.cpb" />
					</label>
				</div>
				<div class="p118_fld_cpb">
					<input id="p118_fld_cpb" type="checkbox"/>	
				</div>
				<div class="p118_lbl_vegalsa">
					<label id="p118_lbl_vegalsa" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.vegalsa" />
					</label>
				</div>
				<div class="p118_fld_vegalsa">
					<input id="p118_fld_vegalsa" type="checkbox"/>	
				</div>
				<div class="p118_lbl_mercat">
					<label id="p118_lbl_mercat" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.mercat" />
					</label>
				</div>
				<div class="p118_fld_mercat">
					<input id="p118_fld_mercat" type="checkbox"/>	
				</div>
			</div>
			<div id="p118_bloqueNegocios" class="p118_bloque">
				<div class="p118_lbl_negocios">
					<label id="p118_lbl_negocios" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.negocios" />
					</label>
				</div>
				<div class="p118_lbl_hiper">
					<label id="p118_lbl_hiper" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.hiper" />
					</label>
				</div>
				<div class="p118_fld_hiper">
					<input id="p118_fld_hiper" type="checkbox"/>	
				</div>
				<div class="p118_lbl_super">
					<label id="p118_lbl_super" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.super" />
					</label>
				</div>
				<div class="p118_fld_super">
					<input id="p118_fld_super" type="checkbox"/>	
				</div>
				<div class="p118_lbl_franquicia">
					<label id="p118_lbl_franquicia" class="etiquetaCampo">
						<spring:message code="p118_aviso.popup.franquicia" />
					</label>
				</div>
				<div class="p118_fld_franquicia">
					<input id="p118_fld_franquicia" type="checkbox"/>	
				</div>
			</div>
		</div>
		<div id="p118_botonera">
			<input type="button" id="p118_btn_new" class="boton  botonHover" value='<spring:message code="p118_aviso.popup.btn.nuevo" />'></input>
		</div>
	</div>
</div>
