
<div id="p102_popup" style="display:none"  title="">
	<div id="p102_componenteFormularios">
		<div id="p102_pagAnterior" class="p102_divFlecha"></div>
		<div id="p102_listaFormularios">	
		</div>
		<div id="p102_pagSiguiente" class="p102_divFlecha"></div>
	</div>
	<div id="p102_paginacionFormulario">
		<div id="p102_paginaActual" class="formEntradaNegrita"></div>					
		<div id="p102_paginaTotal" class="formEntradaNegrita"></div>
	</div>
</div>	


<div id="estructurasFormEntradasDescentralizadas" style="display:none;">
	<div id ="formEntradaEstructura" class="card">
		<div class="formEntradaBloqueInfo formEntradaBloqueTabla">
			<div class="formEntradaCelda">
				<label id="formEntradaEstructuraLblCodCabPedido" class="formEntradaNegrita formEntradaColorBlack etiquetaCampo"></label>
			</div>		
		</div>
		<div class="formEntradaBloqueInfo">
			<div class="formEntradaBloqueInfoDescrLbl">
				<div class="formEntradaCelda">
					<label class="formEntradaNegrita etiquetaCampo"><spring:message code="p102_entrada.formulario.proveedor" /></label>
				</div>
			</div>
			<div class="formEntradaBloqueInfoDescrInfo">					
				<div class="formEntradaCelda">
					<div id="formEntradaEstructuraProveedor">
						<label id="formEntradaEstructuraLblProveedor" class="formEntradaNegrita formEntradaColorBlack"></label>
					</div>
				</div>
			</div>			
		</div>
		<div id="formatoEntradaPendienteDar" class="formEntradaBloqueInfo formEntradaCeldaOculto">
			<div class="formEntradaBloqueInfoDescrLbl">
				<div class="formEntradaCelda">
					<label class="formEntradaNegrita etiquetaCampo"><spring:message code="p102_entrada.formulario.tarea" /></label>
				</div>
			</div>
			<div class="formEntradaBloqueInfoDescrInfo">					
				<div class="formEntradaCelda">
					<div id="formEntradaEstructuraTarea">
						<label class="formEntradaColorRed formEntradaNegrita">
							<spring:message code="p102_entrada.formulario.pendiente.dar" />
						</label>
					</div>
				</div>
			</div>			
		</div>
		<div id="formatoEntradaConfirmar" class="formEntradaBloqueInfo formEntradaBloqueTabla formEntradaCeldaOculto">
			<div class="formEntradaCelda">
				<label class="formEntradaColorGreen formEntradaNegrita etiquetaCampo">
					<spring:message code="p102_entrada.formulario.entrada.confirmada" />
				</label>
			</div>
		</div>
		<div class="formEntradaBloqueImagenesCarta">			
			<div class="formEntradaBloqueEstructuraImagenTexto">
				<div class="formEntradaBloqueTextoImagen">
					<label id="formEntradaEstructuraFichaCons" class="formEntradaNegrita etiquetaCampo formLetraImagen">
						<spring:message code="p102_entrada.formulario.modificacion" />
					</label>
				</div>
				<div id="formEntradaEstructuraFichaLapiz" class="formEntradaLapiz formEntradaBloqueImagen">
					<a href="#" id="formEntradaEstructuraFichaLapizLink">
						<img src="./misumi/images/ficheroDevolucionLapiz2.jpg" alt="<spring:message code="p102_entrada.formulario.editar" />">
					</a>					
				</div>
				<div class="formEntradaBloqueTextoImagen">
					<label id="formEntradaEstructuraFichaCantidad" class="formEntradaNegrita etiquetaCampo formLetraImagen">
						<spring:message code="p102_entrada.formulario.cantidades" />
					</label>
				</div>
			</div>
			<div class="formEntradaBloqueEstructuraImagenTexto">
				<div class="formEntradaBloqueTextoImagen">
					<label id="formEntradaEstructuraImprImprimir" class="formEntradaNegrita etiquetaCampo formLetraImagen">
						<spring:message code="p102_entrada.formulario.imprimir" />
					</label>
				</div>
				<div id="formEntradaEstructuraImpresion" class="formEntradaImprimir formEntradaBloqueImagen">
					<a href="#" id="formEntradaEstructuraImprLink">
						<img src="./misumi/images/imprimirDevolucion2.jpg" alt="<spring:message code="p102_entrada.formulario.imprimir" />">
					</a>					
				</div>
				<div class="formEntradaBloqueTextoImagen">
					<label id="formEntradaEstructuraImprEntrada" class="formEntradaNegrita etiquetaCampo formLetraImagen">
						<spring:message code="p102_entrada.formulario.entrada" />
					</label>			
				</div>									
			</div>
			<div class="formEntradaBloqueEstructuraImagenTexto">
				<div class="formEntradaBloqueTextoImagen">
					<label id="formEntradaEstructuraBanderaFin" class="formEntradaNegrita etiquetaCampo formLetraImagen">
						<spring:message code="p102_entrada.formulario.finalizar" />
					</label>
				</div>
				<div id="formEntradaEstructuraFin" class="formEntradaFinalizar formEntradaBloqueImagen">
					<a href="#" id="formEntradaEstructuraBanderaLink">
						<img src="./misumi/images/finalizar2.png" alt="<spring:message code="p102_entrada.formulario.finalizar" />">
					</a>					
				</div>
				<div class="formEntradaBloqueTextoImagen">
					<label id="formEntradaEstructuraBanderaTarea" class="formEntradaNegrita etiquetaCampo formLetraImagen">
						<spring:message code="p102_entrada.formulario.tarea" />
					</label>
				</div>									
			</div>										
		</div>		
	</div>	
</div>



<div id="p102dialog-confirmFinalizar" style="display:none;" title="<spring:message code="p102_entrada.finalizar" />">
	<table id="p102_table_finalizar">
		<tr>
			<td id="p102_td_img_finalizar"><img src='./misumi/images/dialog-confirm.png?version=${misumiVersion}' /></td>
			<td id="p102_td_finalizar"><span class="p102_mensajeFinalizar"><spring:message code="p103_entrada.finalizar.msg" /></span></td>
		</tr>
	</table>
</div>			
					
					
<input type="hidden" id="p102_fld_finalizarPulsado" value="N"></input>

	


	