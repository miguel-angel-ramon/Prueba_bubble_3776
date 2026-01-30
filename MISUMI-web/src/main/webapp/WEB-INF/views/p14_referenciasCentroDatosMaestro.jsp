<script src="./misumi/scripts/p14ReferenciasCentroDM.js?version=${misumiVersion}" type="text/javascript"></script>
		<!--  Contenido página -->
		<div class="contenidoPestanas">
			<div id="p14_AreaDatosMaestro">
				<div id="p14_datosMaestroEstructura">
					<div class="p14_datosMaestroEstructuraField" >
						<div class="textBox">
							<label id="p14_lbl_tipoAprovisionamiento" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.tipoAprovisionamiento" /></label>
							<input type=text id="p14_fld_tipoAprovisionamiento" class="input140"></input>
						</div>
						<div class="textBox">
							<label id="p14_lbl_UFP" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.UFP" /></label>
							<input type=text id="p14_fld_UFP" class="input50"></input>
							<input type="hidden" id="p14_fld_grupo1"></input>
						</div>
						<div class="textBox">
							<label id="p14_lbl_unidadesCaja" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.unidadesCaja" /></label>
							<input type=text id="p14_fld_unidadesCaja" class="input50"></input>
						</div>
						<div class="textBox">
							<label id="p14_lbl_cc" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.cc" /></label>
							<input type=text id="p14_fld_cc" class="input50"></input>
						</div>
						<div class="textBox">
							<label id="p14_lbl_tipoRef" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.tipoRef" /></label>
							<input type=text id="p14_fld_tipoRef" class="input100"></input>
						</div>
					</div>
					
					<div class="p14_datosMaestroEstructuraField2" >
						<div class="textBox">
							<label id="p14_lbl_estructura" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.estructura" /></label>
							<label id="p14_lbl_estructuraVal" class="valorCampo"></label>
						</div>
						<div class="textBox" style="width:210px;"></div>
						<div class="textBox" id="soloRepartoShow" style="display: none;">
							<label id="p14_lbl_soloReparto" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.soloReparto" /></label>
							<label id="p14_lbl_soloRepartoVal" class="valorCampo"></label>
						</div>
					</div>
					<div class="p14_datosMaestroEstructuraField3" >
						<div class="textBox" id="mapaReferenciaShow" style="display: none;">
							<label id="p14_lbl_mapaReferencia" class="etiquetaCampo"><spring:message code="p14_referenciasCentroDM.mapaReferencia" /></label>
							<label id="p14_lbl_mapaReferenciaVal" class="valorCampo"></label>
						</div>
					</div>
					
				</div>	
			</div>
			<div id="p14_AreaGamaFinSemanaOferta">
				<div id="p14_gamaFinSemanaOfertaEstructura">
					<div class="p14_gamaFinSemanaOfertaEstructuraField" >
						<div id="p14_gamaFinSemanaTabla">
							<fieldset><legend><spring:message code="p14_referenciasCentroDM.gamaFinDeSemana" /></legend>
								<table id="p14_referenciasRelacionadasTablaEstructura" border="1" class="ui-widget">
									<thead class="ui-widget-header">
									    <tr>
									        <th class="p14_gamaFinSemanaTablaTh"><spring:message code="p14_referenciasCentroDM.lunesAbrev" /></th>
									        <th class="p14_gamaFinSemanaTablaTh"><spring:message code="p14_referenciasCentroDM.martesAbrev" /></th>
									        <th class="p14_gamaFinSemanaTablaTh"><spring:message code="p14_referenciasCentroDM.miercolesAbrev" /></th>
									        <th class="p14_gamaFinSemanaTablaTh"><spring:message code="p14_referenciasCentroDM.juevesAbrev" /></th>
									        <th class="p14_gamaFinSemanaTablaTh"><spring:message code="p14_referenciasCentroDM.viernesAbrev" /></th>
									        <th class="p14_gamaFinSemanaTablaTh"><spring:message code="p14_referenciasCentroDM.sabadoAbrev" /></th>
									        <th class="p14_gamaFinSemanaTablaTh"><spring:message code="p14_referenciasCentroDM.domingoAbrev" /></th>
									    </tr>
									</thead>
									<tbody class="ui-widget-content">
									    <tr>
									        <td id="p14_gamaFinSemanaTablaTdL" class="p14_gamaFinSemanaTablaTd"></td>
									        <td id="p14_gamaFinSemanaTablaTdM" class="p14_gamaFinSemanaTablaTd"></td>
									        <td id="p14_gamaFinSemanaTablaTdX" class="p14_gamaFinSemanaTablaTd"></td>
									        <td id="p14_gamaFinSemanaTablaTdJ" class="p14_gamaFinSemanaTablaTd"></td>
									        <td id="p14_gamaFinSemanaTablaTdV" class="p14_gamaFinSemanaTablaTd"></td>
									        <td id="p14_gamaFinSemanaTablaTdS" class="p14_gamaFinSemanaTablaTd"></td>
									        <td id="p14_gamaFinSemanaTablaTdD" class="p14_gamaFinSemanaTablaTd"></td>
									    </tr>
									</tbody>
								</table>
							</fieldset>	
						</div>
						<div id="p14_oferta">
							<fieldset><legend><spring:message code="p14_referenciasCentro.oferta" /></legend>
								<div class="textBox">
									<label id="p14_lbl_oferta" class="etiquetaCampo"><spring:message code="p14_referenciasCentro.ofertaNumero" /></label>
									<input type=text id="p14_fld_oferta" class="input75"></input>
								</div>
								<div class="textBox">
									<label id="p14_lbl_tipo" class="etiquetaCampo"><spring:message code="p14_referenciasCentro.ofertaTipo" /></label>
									<input type=text id="p14_fld_tipo" class="input150"></input>
								</div>
							</fieldset>
						</div>
						<div id="p14_accion">
							<fieldset>
								<div class="textBox">
									<label id="p14_lbl_accion" class="etiquetaCampoNegrita"><spring:message code="p14_referenciasCentro.accion" /></label>
									<label id="p14_lbl_accionVal" class="valorCampo"></label>
								</div>
								
							</fieldset>
						</div>
						<div id="p14_DatosEspecificosTextil">
							<fieldset>
								<div class="textBox p14_datosEspecificosTextilLinea">
									<label id="p14_lbl_estructuraTexil" class="etiquetaCampoNegrita"><spring:message code="p14_referenciasCentro.estructuraTexil" /></label>
									<label id="p14_lbl_estructuraTexilVal" class="valorCampo"></label>
								</div>
								<div class="textBox p14_datosEspecificosTextilLinea"">	
									<label id="p14_lbl_modeloProveedor" class="etiquetaCampoNegrita"><spring:message code="p14_referenciasCentro.modeloProveedor" /></label>
									<label id="p14_lbl_modeloProveedorVal" class="valorCampo"></label>
								</div>
								<div class="textBox p14_datosEspecificosTextilLinea"">	
									<label id="p14_lbl_talla" class="etiquetaCampoNegrita"><spring:message code="p14_referenciasCentro.talla" /></label>
									<label id="p14_lbl_tallaVal" class="valorCampo"></label>
								</div>
								<div class="textBox p14_datosEspecificosTextilLinea"">	
									<label id="p14_lbl_color" class="etiquetaCampoNegrita"><spring:message code="p14_referenciasCentro.color" /></label>
									<label id="p14_lbl_colorVal" class="valorCampo"></label>
								</div>
							</fieldset>
						</div>
					</div>
				</div>	
			</div>
		</div>		