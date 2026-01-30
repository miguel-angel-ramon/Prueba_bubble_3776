<script src="./misumi/scripts/model/User.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Centro.js?version=${misumiVersion}" type="text/javascript"></script>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>

<div id="p85_popup" style="display:none"  title="">
	<div id="p85_componenteFormularios">
		<div id="p85_pagAnterior" class="p85_divFlecha"></div>
		<div id="p85_listaFormularios">	
		</div>
		<div id="p85_pagSiguiente" class="p85_divFlecha"></div>
	</div>
	<div id="p85_paginacionFormulario">
		<div id="p85_paginaActual" class="formDevolucionNegrita"></div>					
		<div id="p85_paginaTotal" class="formDevolucionNegrita"></div>
	</div>
</div>	

<div id="estructurasFormDevol" style="display:none;">
	<div id ="formDevolEstructura1" class="card">
		<div class="formDevolucionBloqueTipo1">
			<div class="formDevolucionBloqueDescDevolucion">
				<div class="formDevolucionCelda">
					<label id="formDevolEstructura1Titulo1" class="formDevolucionNegrita formDevolucionColorBlack etiquetaCampo"></label>
				</div>
			</div>		
		</div>
		<div id="formDevolucionPeriodoFechas1" class="formDevolucionBloqueTipo1">
			<div class="formDevolucionBloque50 formDevolucionBloque123">
				<div class="formDevolucionCelda">
					<label class="formDevolucionNegrita etiquetaCampo"><spring:message code="p85_devoluciones.formulario.periodoEnvio" /></label>
				</div>
			</div>
			<div class="formDevolucionBloque50 formDevolucionBloque151">					
				<div class="formDevolucionCelda">
					<label id="formDevolEstructura1FechaDesde" class="formDevolucionColorRed formDevolucionNegrita etiquetaCampo"></label>
					<br>
					<label id="formDevolEstructura1FechaHasta" class="formDevolucionColorRed formDevolucionNegrita etiquetaCampo"></label>
				</div>
			</div>		
		</div>
		<div id="formDevolucionPeriodoMotivo1" class="formDevolucionBloqueTipo1 formDevolucionCeldaOculto">
			<div class="formDevolucionBloqueCreadaPorCentro">
				<div class="formDevolucionCelda">
					<label id="formDevolEstructura1Motivo" class="formDevolucionColorRed formDevolucionNegrita etiquetaCampo"></label>
				</div>
			</div>
		</div>
		<div class="formDevolucionBloqueTipo1">
			<div class="formDevolucionBloque30o50">
				<div class="formDevolucionCelda">
					<label class="formDevolucionNegrita etiquetaCampo"><spring:message code="p85_devoluciones.formulario.tarea" /></label>
				</div>
			</div>
			<div class="formDevolucionBloque70o50">					
				<div class="formDevolucionCelda">
					
					<div id="formDevolEstructura1Tarea"></div>
				</div>
			</div>			
		</div>
		<div class="formDevolucionBloqueTipo2">
			
			<div class="formDevolucionBloque100Tipo2">					
				<div class="formDevolucionBloque33">
					<div class="formDevolucionBloque33interno formDevolucionBloque33oculto" id="formDevolucionEstructura1DivBorrar">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1BorrarDev" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura1Borrar" class="formDevolucionBloque50Largo">
							<a href="#" id="formDevolEstructura1BorrarLink">
								<img src="./misumi/images/denegarDevolucion.jpg" alt="<spring:message code="p85_devoluciones.formulario.eliminar" />">
							</a>					
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1BorrarDevolucion" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
					</div>
					<div class="formDevolucionBloque33interno formDevolucionBloque33oculto" id="formDevolucionEstructura1DivDuplicar">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1DuplicarDev" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura1Duplicar" class="formDevolucionBloque50Largo">
							<a href="#" id="formDevolEstructura1DuplicarLink">
								<img src="./misumi/images/copy-dev2.png" alt="<spring:message code="p85_devoluciones.formulario.duplicar" />">
							</a>					
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1DuplicarDevolucion" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
					</div>
				</div>
				<div class="formDevolucionBloque33">
					<div class="formDevolucionBloque33interno">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1FichaCons" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura1Ficha" class="formDevolucionBloque50Largo formDevolucionBloque33oculto">
							<a href="#" id="formDevolEstructura1FichaLink">
								<img src="./misumi/images/ficheroDevolucion2.jpg" alt="<spring:message code="p85_devoluciones.formulario.editar" />">
							</a>					
						</div>
						<div id="formDevolEstructura1FichaLapiz" class="formDevolucionBloque50Largo formDevolucionBloque33oculto">
							<a href="#" id="formDevolEstructura1FichaLapizLink">
								<img src="./misumi/images/ficheroDevolucionLapiz2.jpg" alt="<spring:message code="p85_devoluciones.formulario.editar" />">
							</a>					
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1FichaCantidad" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
					</div>
				</div>
				<div class="formDevolucionBloque33">
					<div class="formDevolucionBloque33interno">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1ImprImprimir" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura1Impr" class="formDevolucionBloque50Largo">
							<a href="#" id="formDevolEstructura1ImprLink">
								<img src="./misumi/images/imprimirDevolucion2.jpg" alt="<spring:message code="p85_devoluciones.formulario.imprimir" />">
							</a>					
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1ImprDevolucion" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>									
					</div>
				</div>
				<div class="formDevolucionBloque33">
					<div class="formDevolucionBloque33interno formDevolucionBloque33oculto" id="formDevolucionEstructura1DivBanderaFin">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1BanderaFin" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura1Bandera" class="formDevolucionBloque50Largo">
							<c:choose>
								<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '27_PC_DEVOLUCIONES_PROCEDIMIENTO'))}">
									<a href="#" id="formDevolEstructura1BanderaLink">
										<img src="./misumi/images/finalizar2.png" alt="<spring:message code="p85_devoluciones.formulario.finalizar" />">
									</a>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '27_PDA_DEVOLUCIONES_PROCEDIMIENTO'))}">
											<img id="p85_img_FinalizarDevolDeshabilitado" src='./misumi/images/finalizar2.png'/>
										</c:when>
										<c:otherwise>
											<a href="#" id="formDevolEstructura1BanderaLink">
												<img src="./misumi/images/finalizar2.png" alt="<spring:message code="p85_devoluciones.formulario.finalizar" />">
											</a>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura1BanderaTarea" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>									
					</div>
				</div>
			</div>										
		</div>			
	</div>	
	
	<div id ="formDevolEstructura2" class="card">
		<div class="formDevolucionBloqueTipo1">
			<div class="formDevolucionBloqueDescDevolucion">
				<div class="formDevolucionCelda">
					<label id="formDevolEstructura2Titulo1" class="formDevolucionNegrita formDevolucionColorBlack etiquetaCampo"></label>
				</div>
			</div>		
		</div>
		<div id="formDevolucionPeriodoFechas2" class="formDevolucionBloqueTipo1">
			<div class="formDevolucionBloque50 formDevolucionBloque123">
				<div class="formDevolucionCelda">
					<label class="formDevolucionNegrita etiquetaCampo"><spring:message code="p85_devoluciones.formulario.periodoEnvio" /></label>
				</div>
			</div>
			<div class="formDevolucionBloque50 formDevolucionBloque151">					
				<div  class="formDevolucionCelda">
					<label id="formDevolEstructura2FechaDesde" class="formDevolucionColorRed formDevolucionNegrita etiquetaCampo"></label>
					<br>
					<label id="formDevolEstructura2FechaHasta" class="formDevolucionColorRed formDevolucionNegrita etiquetaCampo"></label>
				</div>
			</div>			
		</div>
		<div id="formDevolucionPeriodoMotivo2" class="formDevolucionBloqueTipo1 formDevolucionCeldaOculto">
			<div class="formDevolucionBloqueCreadaPorCentro">
				<div class="formDevolucionCelda">
					<label id="formDevolEstructura2Motivo" class="formDevolucionColorRed formDevolucionNegrita etiquetaCampo"></label>
				</div>
			</div>
		</div>
		<div class="formDevolucionBloqueTipo1">
			<div class="formDevolucionBloqueDescDevolucion">
				<div class="formDevolucionCelda">							
					<label id="formDevolEstructura2AbonadoIncidencia" class="formDevolucionNegrita etiquetaCampo"></label>
				</div>
			</div>			
		</div>
		<div class="formDevolucionBloqueTipo2">
			<div class="formDevolucionBloque100Tipo2">					
				<div class="formDevolucionBloque33">
					<div class="formDevolucionBloque33interno formDevolucionBloque33oculto" id="formDevolucionEstructura2DivDuplicar">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura2DuplicarDev" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura2Duplicar" class="formDevolucionBloque50Largo">
							<a href="#" id="formDevolEstructura2DuplicarLink">
								<img src="./misumi/images/copy-dev2.png" alt="<spring:message code="p85_devoluciones.formulario.duplicar" />">
							</a>					
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura2DuplicarDevolucion" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
					</div>
				</div>
				<div class="formDevolucionBloque33">
					<div class="formDevolucionBloque33interno">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura2FichaCons" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura2Ficha" class="formDevolucionBloque50Largo">
							<a href="#" id="formDevolEstructura2FichaLink">
								<img src="./misumi/images/ficheroDevolucion2.jpg" alt="<spring:message code="p85_devoluciones.formulario.editar" />">
							</a>					
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura2FichaCantidad" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
					</div>
				</div>
				<div class="formDevolucionBloque33">
					<div class="formDevolucionBloque33interno">
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura2ImprImprimir" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>
						<div id="formDevolEstructura2Impr" class="formDevolucionBloque50Largo">
							<a href="#" id="formDevolEstructura2ImprLink">
								<img src="./misumi/images/imprimirDevolucion2.jpg" alt="<spring:message code="p85_devoluciones.formulario.imprimir" />">
							</a>					
						</div>
						<div class="formDevolucionBloque25Largo">
							<div class="formDevolucionCelda">
								<label id="formDevolEstructura2ImprDevolucion" class="formDevolucionNegrita etiquetaCampo formLetraImpresora"></label>
							</div>
						</div>									
					</div>
				</div>
				<div class="formDevolucionBloque33">
					<!-- Div vacio necesario para que se vean 4 divs y los botones de editar e imprimir no se muevan. -->
				</div>
			</div>										
		</div>				
	</div>	
</div>



<div id="p85dialog-rellenarRma" style="display:none;" title="<spring:message code="p85_devoluciones.rellenarRma" />">
	<table id="p85_table_rma">
		<tr>
			<td id="p85_td_img_rellenarRma"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
			<td id="p85_td_rellenarRma"><span class="p85_mensajeRellenarRma"></span><spring:message code="p85_devoluciones.rellenarRmaText" /></td>
		</tr>
	</table>
</div>

<div id="p85dialog-confirmFinalizar" style="display:none;" title="<spring:message code="p85_devoluciones.finalizar" />">
	<table id="p85_table_finalizar">
		<tr>
			<td id="p85_td_img_finalizar"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
			<td id="p85_td_finalizar"><span class="p85_mensajeFinalizar"></span></td>
		</tr>
	</table>
</div>
					
<div id="p85dialog-confirmRellenarHuecos" style="display:none;" title="<spring:message code="p85_devoluciones.finalizar" />">
	<table id="p85_table_rellenarHuecos">
		<tr>
			<td id="p42_td_img_rellenarHuecos"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
			<td id="p42_td_rellenarHuecos"><span class="p85_mensajeRellenarHuecos"></span><spring:message code="p85_devoluciones.rellenarHuecosText" /></td>
		</tr>
	</table>
</div>

<div id="p85dialog-confirmDuplicarDev" style="display:none;" title="<spring:message code="p85_devoluciones.duplicarDev" />">
	<table id="p85_table_duplicarDev">
		<tr>
			<td id="p85_td_img_duplicarDev"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
			<td id="p85_td_duplicarDev"><span class="p85_mensajeDuplicarDev"></span><spring:message code="p85_devoluciones.duplicarDevText" /></td>
		</tr>
	</table>
</div>
					
<div id="p85dialog-confirmEliminarDev" style="display:none;" title="<spring:message code="p85_devoluciones.eliminarDev" />">
	<table id="p85_table_eliminarDev">
		<tr>
			<td id="p85_td_img_eliminarDev"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
			<td id="p85_td_eliminarDev"><span class="p85_mensajeEliminarDev"></span><spring:message code="p85_devoluciones.eliminarDevText" /></td>
		</tr>
	</table>
</div>
					
<input type="hidden" id="p85_fld_finalizarPulsado" value="N"></input>

	


	