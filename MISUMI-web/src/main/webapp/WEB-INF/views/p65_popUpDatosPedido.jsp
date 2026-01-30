<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p65PopUpDatosPedido.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p65_popupDatosPedido" class="controlReturnP65" title="<spring:message code="p65_popUpDatosPedido.tituloPopup"/>" style="display:none">
				<div id="p65_AreaBloque1">
					<div id="p65_div_uc" class="textBoxMin">
						<label id="p65_lbl_uc" class="etiquetaCampo"><spring:message code="p65_popUpDatosPedido.uc" /></label>
						<input type=text id="p65_fld_uc" class="input40"></input>
					</div>
					<div id="p65_div_titulo" class="textBox">
						<label id="p65_lbl_unidadesPedir" class="p65_lbl_unidadesPedir"></label><label class="p65_lbl_unidadesPedir"><spring:message code="p65_popUpDatosPedido.unidadesPedir"/></label>
						<img id="p65_btn_ayudaUnidadesPedir" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}" class="botonAyudaUnidades" title="<spring:message code="p65_popUpDatosPedido.ayuda" />"/>
					</div>
					<div id="p65_div_unidadesPedir" class="textBoxMin">
						<input type=text id="p65_fld_unidadesPedir" class="input40"></input>
					</div>

				</div>			
				<div id="p65_AreaBloque2">
					<fieldset>
						<legend><spring:message code="p65_popUpDatosPedido.tituloNoSeAjustaANecesidades" /></legend>
						<div id="p65_AreaBloqueEspecificacion">
							<div id="p65_AreaBloque2_2Titulo">
								<label id="p65_lbl_tituloAreaBloque2_2" class="etiquetaCampoNegrita"><spring:message code="p65_popUpDatosPedido.descripcion" /></label>
							</div>
							<div id="p65_AreaBloque2_2Datos">
								<textarea id="p65_fld_descripcion" rows="3" cols="80"></textarea>
							</div>
						</div>
						<div id="p65_AreaBloqueNoEspecificacion" style="display: none;">
							<spring:message code="p65_popUpDatosPedido.referenciaSinEspecificaciones" />
						</div>
						 
					</fieldset>
				</div>
				<br/>
				<br/>
				<hr/>
				<div id="p65_AreaBloque3">
					<div>
						<label><spring:message code="p65_popUpDatosPedido.tituloEspecificidad" /></label>
					</div>
					<br/>
					<div id="p65_AreaNotaPie">
						<label id="p65_lbl_notaPie" class="etiquetaCampoNegrita"><spring:message code="p65_popUpDatosPedido.Nota" /></label>
					</div>
				</div>
			</div>