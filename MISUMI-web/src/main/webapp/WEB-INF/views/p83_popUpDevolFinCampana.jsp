<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>

<script src="./misumi/scripts/p83PopUpDevolFinCampana.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
			<div id="p83_popUpDevolFinCampana" style="display:none;">
				<div id="p83_devolFinCampana">
					<div id="p83_AreaCabecera">
						<div id="p83_AreaFechaLocalizador">
							<div id="p83_AreaFecha">
								<div id="p83_TextoFecha">
									<label id="p83_lbl_TextoFecha"><spring:message code="p83_popUpDevolFinCampana.fechasDevolucion" /></label>
								</div>
								<div id="p83_Fecha">
									<label id="p83_lbl_ValorFecha" class="valorCampo"></label>
								</div>
							</div>
							<div id="p83_AreaLocalizador">
								<div id="p83_Localizador">
									<label id="p83_lbl_etiquetaLocalizador" class="etiquetaCampoNegritaNegro"><spring:message code="p83_popUpDevolFinCampana.localizador" /></label>
									<label id="p83_lbl_ValorLocalizador" class="etiquetaCampoNegritaNegro"></label>
								</div>
								<div id="p83_AbonoRecogida">
									<div id="p83_valorAbonoRecogida">
										<label id="p83_lbl_ValorAbono" class="etiquetaCampoNegrita"></label></br>
										<label id="p83_lbl_ValorRecogida"class="etiquetaCampoNegrita" ></label>	
									</div>
									<div id="p83_etiquetaAbonoRecogida">
										<label id="p83_lbl_etiquetaAbono" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.abono" /></label></br>
										<label id="p83_lbl_etiquetaRecogida" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.recogida" /></label>
									</div>
								</div>
							</div>
						</div>
						<div id="p83_AreaTitulo">
							<div id="p83_etiquetaTitulo">
								<label id="p83_lbl_TextoTitulo" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.titulo" /></label>
							</div>
							<div id="p83_valorTitulo">
								<label id="p83_lbl_ValorTitulo" class="etiquetaCampoNegrita"></label>
							</div>
						</div>
						<div id="p83_AreaObservaciones">
							<div id="p83_etiquetaObservaciones">
								<label id="p83_lbl_TextoObservaciones" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.observaciones" /></label>
							
							</div>
							<div id="p83_valorObservaciones">
								<label id="p83_lbl_ValorObservaciones" class="etiquetaCampoNegrita"></label>
							</div>
						</div>
						
						<div id="p83_AreaCombos">
							<div id="p83_AreaComboProveedor" class="comboBox comboBoxExtraLarge controlReturnP83">
								<label id="p83_lbl_proveedor" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.proveedor" /></label>
								<select id="p83_cmb_proveedor"></select>
							</div>
							<div id="p83_AreaRma">
								<label id="p83_lbl_rma" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.rma" /></label>
								<input type=text id="p83_input_rma" class="p70_tamanioLetra input100" />
								<input type=text id="p83_input_rmaHidden" style="display:none;"/>
							</div>
							<div id="p83_MensajeAvisoAreaCombos">
								<div id="p83_etiquetaMensajeAvisoRevisar">
									<label id="p83_lbl_TextoMensajeAvisoRevisar"><spring:message code="p83_popUpDevolFinCampana.mensajeRefSinRevisar" /></label>
								</div>
							</div>
						</div>
					</div>   
				
					<div id="p83_AreaReferencias" class="p83_AreaResultados">
						<table id="gridP83DevolFinCampana"></table>
						<div id="pagerGridp83"></div>
					</div> 
					<div id="p83_MensajeAvisoAreaPie">
						<div id="p83_etiquetaMensajeAviso">
							<label id="p83_lbl_TextoMensajeAviso"><spring:message code="p83_popUpDevolFinCampana.mensajeAviso" /></label>
						</div>
					</div>
					<div id="p83_AreaPie">
						<!-- <div id="p83_AreaRadioMarco">
							<div id="p83_AreaRadio">
								<input type="radio"  name="p83_rad_tipoimpresion" value="1" id="p83_rad_tipoimpresion" checked="checked" />
								<label  class="etiquetaCampoNegritaPequeña" ><spring:message code="p83_popUpDevolFinCampana.hojaReferencia" /></label>
								<input type="radio" name="p83_rad_tipoimpresion" value="2" id="p83_rad_tipoimpresion"/>
								<label  class="etiquetaCampoNegritaPequeña" ><spring:message code="p83_popUpDevolFinCampana.hojaProveedor" /></label>
								<input type="radio" name="p83_rad_tipoimpresion" value="3" id="p83_rad_tipoimpresion"/>
								<label  class="etiquetaCampoNegritaPequeña" ><spring:message code="p83_popUpDevolFinCampana.hojaTodo" /></label>
							</div>	
							<div id="p83_AreaRadioImpresora">
								
								<img id="p83_btn_impresora"  src="./misumi/images/impresora.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios"/>
								
							</div>	
						</div>-->	
						<div id="p83_AreaGuardado">
							<img id="p83_btn_guardado" src="./misumi/images/floppy_30.gif?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios"/>
						</div>	
						<div id="p83_AreaCosteMaximo" style="display: none;">
							<div id="p83_etiquetaCosteMaximo">
								<label id="p83_lbl_TextoCosteMaximo" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.costeMaximo" /></label>
							</div>
							<div id="p83_valorCosteMaximo">
								<label id="p83_lbl_ValorCosteMaximo" class="etiquetaCampoNegrita"></label>
								<input type=text id="p83_lbl_ValorCosteMaximoHidden" class="input40" style="display:none;"/>
							</div>
						</div>
						<div id="p83_AreaSuma" style="display: none;">
							<div id="p83_etiquetaSuma">
								<label id="p83_lbl_TextoSuma" class="etiquetaCampoNegrita"><spring:message code="p83_popUpDevolFinCampana.suma" /></label>
							</div>
							<div id="p83_valorSuma">
								<input type=text id="p83_lbl_ValorSuma"  class="p83_tamanioLetra input100" readonly="readonly" />
								<input type=hidden id="p83_lbl_ValorSumaSinFormateo"/>
							</div>
						</div>
					</div>
			</div>	
		</div>
		<input type="hidden" id="p83_fld_StockDevuelto_Selecc" value=""></input>