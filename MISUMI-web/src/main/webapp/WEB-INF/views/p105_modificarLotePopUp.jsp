<div id="p105_popup" style="display: none" title="<spring:message code="p105_popup.titulo"/>">
	<diV id="p105_formulario">
		<div id="p105_campos">
			<div id="p105_bloqueCodArt" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_codArtLote" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.codigoarticulo" />
					</label>
				</div>
				<div class="p105_fld">
					<input id="p105_fld_codArtLote" type="text">	
					<input id="p105_fld_descrCodArtLote" type="text">	
				</div>
			</div>
			<div id="p105_bloqueFechaIni" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_fechaIni" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.fechaini" />
					</label>
				</div>
				<div class="p105_fld">
					<input id="p105_fld_fechaIni" type="text"/>	
				</div>
			</div>
			<div id="p105_bloqueFechaFin" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_fechaIni" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.fechafin" />
					</label>
				</div>
				<div class="p105_fld">
					<input id="p105_fld_fechaFin" type="text"/>	
				</div>
			</div>
			<div id="p105_bloqueImg1" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_img1" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.imagen1" />
					</label>
				</div>
				<div class="p105_fld">
					<input id="p105_fld_img1" class="p105_seleccionImagen" type="file">	
				</div>
			</div>
			<div id="p105_bloqueRadio" class="p105_bloque">
				<div class="p105_radio">
					<input type="radio" id="p105_radioImg" checked="checked" name="p105_rad_contenidoLote" value="1"/>
					<label id="p105_lbl_radioImg" class="etiquetaCampo" title=""><spring:message code="p105_lote.popup.radio.img" /></label>
				</div>
				<div class="p105_radio">
					<input type="radio" id="p105_radioTxt" name="p105_rad_contenidoLote" value="2"/>
					<label id="p105_lbl_radioTxt" class="etiquetaCampo" title=""><spring:message code="p105_lote.popup.radio.txt" /></label>
				</div>
			</div>
			<div id="p105_bloqueImg2" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_img2" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.imagen2" />
					</label>
				</div>
				<div class="p105_fld">
					<input id="p105_fld_img2" class="p105_seleccionImagen" type="file">	
				</div>
			</div>
			<div id="p105_bloqueContenidoLote" class="p105_bloque" style="display:none;">
				<fieldset id="p105_fieldset">
					<legend><spring:message code="p105_lote.popup.legend" /></legend>
					<div id="p105_sinArticuloLote">
						<spring:message code="p105_lote.popup.contenido.lote.mensaje" />						
					</div>
					<div id="p105_nuevosArticulosLote" data-count="0" style="display:none">
					</div>
				</fieldset>
				<div class="p105_mas">
					<input id="p105_btn_mas" type="button" value="<spring:message code="p105_lote.popup.btn.nuevo" />"/>
				</div>
			</div>
			<div id="p105_bloqueFlgEroski" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_flgEroski" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.flgEroski" />
					</label>
				</div>
				<div class="p105_fld">
					<div class="comboBox comboBoxExtraShort"> 
						<select id="p105_fld_flgEroski" class=""><option value="S"><spring:message code="p105_lote.popup.flgEroski.si" /></option><option value="N"><spring:message code="p105_lote.popup.flgEroski.no" /></option></select>
					</div>					
				</div>
			</div>
			<div id="p105_bloqueFlgCaprabo" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_flgCaprabo" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.flgCaprabo" />
					</label>
				</div>
				<div class="p105_fld">
					<div class="comboBox comboBoxExtraShort">
						<select id="p105_fld_flgCaprabo" class=""><option value="S"><spring:message code="p105_lote.popup.flgCaprabo.si" /></option><option value="N"><spring:message code="p105_lote.popup.flgCaprabo.no" /></option></select>
					</div>
				</div>
			</div>
			<div id="p105_bloqueOrden" class="p105_bloque">
				<div class="p105_lbl">
					<label id="p105_lbl_orden" class="etiquetaCampo">
						<spring:message code="p105_lote.popup.orden" />
					</label>
				</div>
				<div class="p105_fld">
					<input id="p105_fld_orden" class="" type="text">	
				</div>
			</div>
		</div>
		<div id="p105_botonera">
			<input type="button" id="p105_btn_new" class="boton  botonHover" value='<spring:message code="p105_lote.popup.btn.nuevo" />'></input>
		</div>
	</div>
</div>

<div id="p105_estructuraNuevoArticuloLote" style="display:none">
	<div class="p105_nuevoArticuloLote">
		<div class="p105_producto">
			<div class="p105_lbl">
				<label id="p105_lbl_productoTitulo" class="etiquetaCampo">
					<spring:message code="p105_lote.popup.producto.titulo" />
				</label>
			</div>
			<div class="p105_fld p105_loteinput">
				<input data-ori="" class="p105_inputTitle input90" type="text">	
			</div>
		</div>
		<div class="p105_producto">
			<div class="p105_lbl">
				<label id="p105_lbl_productoDescripcion" class="etiquetaCampo">
					<spring:message code="p105_lote.popup.producto.descripcion" />
				</label>
			</div>
			<div class="p105_fld p105_loteinput">
				<input data-ori="" class="p105_inputDescr" type="text">	
			</div>
		</div>
		<div class="p105_quitarProducto">
			<img id="" class="p105_btn_cross" src="./misumi/images/dialog-cancel-14.png?version=" onclick="borrarContenidoLote(this)">
		</div>
	</div>
</div>