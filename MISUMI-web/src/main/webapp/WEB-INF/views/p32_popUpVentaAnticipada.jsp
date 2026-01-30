<script src="./misumi/scripts/model/VentaAnticipada.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p32PopUpVentaAnticipada.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>

		<!--  Contenido página -->
			<div id="p32_popupVentaAnticipada" title="<spring:message code="p32_ventaAnticipada.title" />" tabindex="0" class="controlReturnP32" style="display:none;">
				<div id="p32_AreaDatosProducto">
						<div class="p32_etiqValorCampoCab">
								<label id="p30_lbl_referencia" class="etiquetaCampo"><spring:message code="p32_ventaAnticipada.referencia" /></label>
								<input type=text id="p32_fld_referencia" class="input85" ></input>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a id="p32_btn_informe" class="p32_enlace_informe"><spring:message code="p32_ventaAnticipada.informe" /></a>
						</div>
						<div class="p32_etiqValorCampoCab">
							<label id="p30_lbl_stockActual" class="etiquetaCampo"><spring:message code="p32_ventaAnticipada.descripcion" /></label>
							<input type=text id="p32_fld_denominacion" class="input300" disabled="disabled"></input>
							<input type="button" id="p32_btn_aceptar" class="boton botonNarrow botonHover" value='<spring:message code="p32_ventaAnticipada.aceptar" />'></input>
						
						</div>
				</div>
				<div id="p32_AreaDatosVentaAnticipada" style="display:none">	
				<div class="p32_etiqValorCampo">
				<div class="etiquetaCampoNegritaIzq"><spring:message code="p32_ventaAnticipada.prevision" /></div>
				</div>
				<div class="p32_etiqValorCampo">
				<spring:message code="p32_ventaAnticipada.anticipar" /> <div id="p32_lbl_fechaGen"></div>
				</div>
				<div class="p32_etiqValorCampo">
							<label id="p30_lbl_stockActual" class="etiquetaCampoNegrita"><spring:message code="p32_ventaAnticipada.unidades" /></label>
							<input type="text" id="p32_fld_unidades" class="input40"></input>
							<input type="hidden" id="p32_fld_existe"/>
							<input type="hidden" id="p32_fld_flgEnvioAC"/>
							<input type="hidden" id="p32_fld_fechaGen"/>
							<input type="hidden" id="p32_fld_referenciaBuscada"/>
						</div>
				<div class="p32_etiqValorCampo">
				<input type="button" id="p32_btn_save" class="boton botonNarrow botonHover" value='<spring:message code="p32_ventaAnticipada.guardar" />'></input>
					
				</div>
				<div id="p32_ventaAnticipada_bloqueGuardado" class="p32_guardado"><spring:message code="p32_ventaAnticipada.messageGuardado" /></div>	
				</div>
				
			</div>
			
<%@ include file="/WEB-INF/views/p112_popUpRefConsumoRapido.jsp" %>