<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<script src="./misumi/scripts/p84PopUpDevolOrdenRetirada.js?version=${misumiVersion}" type="text/javascript"></script>

		<!--  Contenido página -->
			<div id="p84_popUpDevolOrdenRetirada" style="display:none;">
				<div id="p84_devolOrdenRetirada">
					<div id="p84_AreaCabecera">				
						<div id="p84_AreaFechaLocalizador">
							<div id="p84_AreaFecha">
								<div id="p84_TextoFecha">
									<label id="p84_lbl_TextoFecha"><spring:message code="p84_popUpDevolOrdenRetirada.fechasDevolucion" /></label>
								</div>
								<div id="p84_Fecha">
									<label id="p84_lbl_ValorFecha" class="valorCampo"></label>
								</div>
							</div>
							<div id="p84_AreaLocalizador">
								<div id="p84_Localizador">
									<label id="p84_lbl_etiquetaLocalizador" class="etiquetaCampoNegritaNegro"><spring:message code="p84_popUpDevolOrdenRetirada.localizador" /></label>
									<label id="p84_lbl_ValorLocalizador" class="etiquetaCampoNegritaNegro"></label>
								</div>
								<div id="p84_AbonoRecogida">
									<div id="p84_valorAbonoRecogida">
										<label id="p84_lbl_ValorAbono" class="etiquetaCampoNegrita"></label></br>
										<label id="p84_lbl_ValorRecogida"class="etiquetaCampoNegrita" ></label>	
									</div>
									<div id="p84_etiquetaAbonoRecogida">
										<label id="p84_lbl_etiquetaAbono" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.abono" /></label></br>
										<label id="p84_lbl_etiquetaRecogida" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.recogida" /></label>
									</div>		
								</div>	
							</div>
					
						</div>
						<div id="p84_AreaTitulo">
							<div id="p84_etiquetaTitulo">
								<label id="p84_lbl_Texto" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.titulo" /></label>
							</div>
							<div id="p84_valorTitulo">
								<label id="p84_lbl_ValorTitulo" class="etiquetaCampoNegrita"></label>
							</div>
						</div>
						<div id="p84_AreaObservaciones">
							<div id="p84_etiquetaObservaciones">
								<label id="p84_lbl_Texto" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.observaciones" /></label>
							
							</div>
							<div id="p84_valorObservaciones">
								<label id="p84_lbl_ValorObservaciones" class="etiquetaCampoNegrita"></label>
							</div>
						</div>
						
						<div id="p84_AreaCombos">
							<div id="p84_AreaComboProveedor" class="comboBox comboBoxExtraLarge controlReturnP84">
								<label id="p84_lbl_proveedor" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.proveedor" /></label>
								<select id="p84_cmb_proveedor"></select>
							</div>
							<div id="p84_AreaRma">
								<label id="p84_lbl_rma" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.rma" /></label>
								<input type=text id="p84_input_rma" class="p70_tamanioLetra input100" />
								<input type=text id="p84_input_rmaHidden" style="display:none;"/>
							</div>
							<div id="p84_MensajeAvisoAreaCombos">
								<div id="p84_etiquetaMensajeAvisoRevisar">
									<label id="p84_lbl_TextoMensajeAvisoRevisar"><spring:message code="p84_popUpDevolOrdenRetirada.mensajeRefSinRevisar" /></label>
								</div>
							</div>
						</div>
					
					</div>   
			
					<div id="p84_AreaReferencias" class="p84_AreaResultados">
						<table id="gridP84DevolOrdenRetirada"></table>
						<div id="pagerGridp84"></div>
					</div> 
					<div id="p84_MensajeAvisoAreaPie">
						<div id="p84_etiquetaMensajeAviso">
							<label id="p84_lbl_TextoMensajeAviso"><spring:message code="p84_popUpDevolOrdenRetirada.mensajeAviso" /></label>
						</div>
					</div>
					<div id="p84_AreaPie">
						<!-- <div id="p84_AreaRadioMarco">
							<div id="p84_AreaRadio">
								<input type="radio" name="p84_rad_tipoimpresion" value="1" id="p84_rad_tipoimpresion" checked="checked" />
								<label  class="etiquetaCampoNegritaPequeña" ><spring:message code="p84_popUpDevolOrdenRetirada.hojaReferencia" /></label>
								<input type="radio" name="p84_rad_tipoimpresion" value="2" id="p84_rad_tipoimpresion"/>
								<label  class="etiquetaCampoNegritaPequeña" ><spring:message code="p84_popUpDevolOrdenRetirada.hojaProveedor" /></label>
								<input type="radio" name="p84_rad_tipoimpresion" value="3" id="p84_rad_tipoimpresion"/>
								<label  class="etiquetaCampoNegritaPequeña" ><spring:message code="p84_popUpDevolOrdenRetirada.hojaTodo" /></label>
							</div>	
	
							<div id="p84_AreaRadioImpresora">	
								<img id="p84_btn_impresora"  src="./misumi/images/impresora.jpg?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios"/>		
							</div>	
						</div>-->	
						<div id="p84_AreaGuardado">
							<img id="p84_btn_guardado" src="./misumi/images/floppy_30.gif?version=${misumiVersion}" style="vertical-align: middle;" class="imagenTablonAnuncios"/>
						</div>	
						<div id="p84_AreaCosteMaximo" style="display: none;">
							<div id="p84_etiquetaCosteMaximo">
								<label id="p84_lbl_TextoCosteMaximo" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.costeMaximo" /></label>
							</div>
							<div id="p84_valorCosteMaximo">
								<label id="p84_lbl_ValorCosteMaximo" class="etiquetaCampoNegrita"></label>
								<input type=text id="p84_lbl_ValorCosteMaximoHidden" class="input40" style="display:none;"/>
							</div>
						</div>
						<div id="p84_AreaSuma" style="display: none;">
							<div id="p84_etiquetaSuma">
								<label id="p84_lbl_TextoSuma" class="etiquetaCampoNegrita"><spring:message code="p84_popUpDevolOrdenRetirada.suma" /></label>
							</div>
							<div id="p84_valorSuma">
								<input type=text id="p84_lbl_ValorSuma" style="font-size: 12pt;" class="p70_tamanioLetra input100" readonly="readonly" />
								<input type=hidden id="p84_lbl_ValorSumaSinFormateo"/>
							</div>
						</div>						
					</div>
				</div>	
			</div>
			<input type="hidden" id="p84_fld_StockDevuelto_Selecc" value=""></input>
			<div id="a"></div>