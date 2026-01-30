<script src="./misumi/scripts/p87PopUpDevolCreadaCentro.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		
			<div id="p87_popUpDevolCreadaCentro" style="display:none;">
				<div id="p87_devolCreadaCentro">
					<div id="p87_AreaCabecera">
						<div id="p87_AreaFechaLocalizador">
							<div id="p87_AreaFecha">
								<label id="p87_lbl_TextoDevolCreadaCentro"><spring:message code="p87_popUpDevolCreadaCentro.creadaPorCentro" /></label>
							<!--
								<div id="p87_TextoFecha">
									<label id="p87_lbl_TextoFecha"><spring:message code="p87_popUpDevolCreadaCentro.fechasDevolucion" /></label>
								</div>
								<div id="p87_Fecha">
									<label id="p87_lbl_ValorFecha" class="valorCampo"></label>
								</div>
							-->
									
							</div>
							<div id="p87_AreaLocalizador">
								<div id="p87_Localizador">
									<label id="p87_lbl_etiquetaLocalizador" class="etiquetaCampoNegritaNegro"><spring:message code="p87_popUpDevolCreadaCentro.localizador" /></label>
									<label id="p87_lbl_ValorLocalizador" class="etiquetaCampoNegritaNegro"></label>
								</div>
								<div id="p87_AbonoRecogida">
									<div id="p87_valorAbonoRecogida">
										<label id="p87_lbl_ValorAbono" class="etiquetaCampoNegrita"></label></br>
										<label id="p87_lbl_ValorRecogida"class="etiquetaCampoNegrita" ></label>	
									</div>
									<div id="p87_etiquetaAbonoRecogida">
										<label id="p87_lbl_etiquetaAbono" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.abono" /></label></br>
										<label id="p87_lbl_etiquetaRecogida" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.recogida" /></label>
										
									</div>
								</div>
								
							</div>
					
						</div>
					
						<div id="p87_AreaTitulo">
							<div id="p87_etiquetaTitulo">
								<label id="p87_lbl_TextoTitulo" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.titulo" /></label>
							</div>
							<div id="p87_valorTitulo">
								<label id="p87_lbl_ValorTitulo" class="etiquetaCampoNegrita"></label>
							</div>
						</div>
						<div id="p87_AreaObservaciones">
							<div id="p87_etiquetaObservaciones">
								<label id="p87_lbl_TextoObservaciones" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.observaciones" /></label>
							
							</div>
							<div id="p87_valorObservaciones">
								<label id="p87_lbl_ValorObservaciones" class="etiquetaCampoNegrita"></label>
							</div>
						</div>
					
						<div id="p87_AreaCombos">
							<div id="p87_AreaComboProveedor" class="comboBox comboBoxExtraLarge controlReturnP87">
								<label id="p87_lbl_proveedor" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.proveedor" /></label>
								<select id="p87_cmb_proveedor"></select>
							</div>
							<div id="p87_AreaRma">
								<label id="p87_lbl_rma" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.rma" /></label>
								<input type=text id="p87_input_rma" class="p70_tamanioLetra input100" />
								<input type=text id="p87_input_rmaHidden" style="display:none;"/>
							</div>	
						</div>
					</div>   
				
					<div id="p87_AreaReferencias" class="p87_AreaResultados">
						<table id="gridP87DevolCreadaCentro"></table>
						<div id="pagerGridp87"></div>
					</div> 
					
					<div id="p87_AreaPie">
						
						<div id="p87_AreaGuardado">
							<img id="p87_btn_guardado" src="./misumi/images/floppy_30.gif?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios"/>
						</div>	
						<div id="p87_AreaCosteMaximo" style="display: none;">
							<div id="p87_etiquetaCosteMaximo">
								<label id="p87_lbl_TextoCosteMaximo" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.costeMaximo" /></label>
							</div>
							<div id="p87_valorCosteMaximo">
								<label id="p87_lbl_ValorCosteMaximo" class="etiquetaCampoNegrita"></label>
								<input type=text id="p87_lbl_ValorCosteMaximoHidden" class="input40" style="display:none;"/>
							</div>
						</div>
						<div id="p87_AreaSuma" style="display: none;">
							<div id="p87_etiquetaSuma">
								<label id="p87_lbl_TextoSuma" class="etiquetaCampoNegrita"><spring:message code="p87_popUpDevolCreadaCentro.suma" /></label>
							</div>
							<div id="p87_valorSuma">
								<input type=text id="p87_lbl_ValorSuma" style="font-size: 12pt;" class="p70_tamanioLetra input100" readonly="readonly" />
								<input type=hidden id="p87_lbl_ValorSumaSinFormateo"/>
							</div>
						</div>
					</div>
			</div>	
		</div>
		<input type="hidden" id="p87_fld_StockDevolver_Selecc" value=""></input>