

<c:set var = "origenGisae" value = "N"/>

<c:choose>
	<%-- SI es Facing VEGALSA --%>
	<c:when test="${data.tratamientoVegalsa eq true}">
		<div id="pda_p118_datosReferencia_bloque5">
			<fieldset id="pda_p118_imagenComercial">
				<%-- INICIO BLOQUE LEYENDA --%>
				<c:choose>
					<c:when test="${data.flgColorImplantacion eq 'VERDE'}">
						<legend id="pda_p118_imagenComercialLegendVerde"> 
							<span>
								<a id="pda_p118_imagenComercialSpanVerde" href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</a>
							</span>
						</legend>
					</c:when>
					
					<c:when test="${data.flgColorImplantacion eq 'ROJO'}">
						<legend id="pda_p118_imagenComercialLegendRojo">
							<span>
								<a id="pda_p118_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</a>
							</span>
						</legend>
					</c:when>
					
					<c:otherwise>
						<legend id="pda_p118_imagenComercialLegend">
							<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
						</legend>
					</c:otherwise>
				</c:choose>
				<%-- FIN BLOQUE LEYENDA --%>
				
				<%-- INICIO BLOQUE DATOS --%>
				<div id="pda_p118_datosReferencia_bloque5_areaPlanograma">
				
					<%-- BLOQUE CAP LIN y FAC LIN --%>
					<div id="pda_p118_datosReferencia_bloque5_facCap">
						<%-- CAPACIDAD SIN Link --%>						
						<div id="pda_p118_datosReferencia_bloque5_capacidad">						
							<label id="pda_p118_lbl_capacidad" class="etiquetaCampoNegrita">
								<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
							</label>
							<label id="pda_p118_lbl_capacidadVal" class="valorCampo">
								${imc.capacidad}
							</label>
						</div>								
	
						<%-- VALOR de FACING SIN Link --%>
						<div id="pda_p118_datosReferencia_bloque5_facingDerecha">
	
							<%-- Comprobar si hay que mostrar el link para acceder al planograma --%>
							<c:choose>
								<%-- CON Link --%>
								<c:when test="${imc.facingModificable eq 1}">
									<c:choose> 
										<%-- El link sale Azul --%>
										<c:when test="${empty guardadoImc}">
											<a href="./pdaP33Imc.do?codArt=${data.codArt}&procede=pdaP118DatosRefDetalleReferencia">
												<span class="enlaceNormal"> 
													<spring:message code="pda_p12_datosReferencia.facingLineal"/>&nbsp;${imc.facing}
												</span>
											</a>
										</c:when>
										<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
										<c:otherwise>
											<a href="./pdaP33Imc.do?codArt=${data.codArt}&procede=pdaP118DatosRefDetalleReferencia">
												<span class="enlaceVerde"> 
													<spring:message code="pda_p12_datosReferencia.facingLineal"/>&nbsp;${imc.facing}
												</span>
											</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<%-- SIN Link --%>
								<c:otherwise>
									<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita">
										<spring:message code="pda_p12_datosReferencia.facingLineal"/>&nbsp;
									</label>
									<label id="pda_p118_lbl_facingVal" class="valorCampo">
										${imc.facing}
									</label>
								</c:otherwise>
							</c:choose>
	
						</div>
						
						<div id="pda_p118_datosReferencia_bloque5_multipDerecha">
							<label class="etiquetaCampoNegrita"> 
								<spring:message	code="pda_p12_datosReferencia.fondo" />
							</label> 
							<label class="valorCampo">
								${imc.multiplicador} 
							</label>
						</div>
						
					</div>
					
				</div>
			</fieldset>
		</div>
	</c:when>					
	
	<%-- ------------------------------------------------------------------------------------ --%>

	<%-- SI ES PLANOGRAMA --%>
	<c:when test="${imc.metodo == esPlanogramaRef}">
		<div id="pda_p118_datosReferencia_bloque5">
			<fieldset id="pda_p118_imagenComercial">
				<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP, esMadreRef o CAJA EXP. --%>
				<c:choose>
					<%--Parte leyenda con link verde --%>
					<c:when test="${data.flgColorImplantacion eq 'VERDE'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
									<legend id="pda_p118_imagenComercialLegend">
										<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
											<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
										</a>
									</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda con link roja --%>
					<c:when test="${data.flgColorImplantacion eq 'ROJO'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
									</a>
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda sin link negra --%>
					<c:otherwise>
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
								</legend>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<%-- FIN BLOQUE LEYENDA --%>
				
				<%-- INICIO BLOQUE DATOS --%>
				<div id="pda_p118_datosReferencia_bloque5_areaPlanograma">
					<c:choose>
						<%-- INICIO BLOQUES Si es CAJA EXPOSITORA --%>
						<c:when test="${imc.tipoReferencia == esCajaExpositoraRef}">
							<%-- BLOQUE DE IMPL.CAJA ALTO X ANCHO --%>
							<c:if test="${imc.facingAlto > 0}">
								<div id ="pda_p118_datosReferencia_bloque5_ImplCajas">
								<%-- Miramos si hay que mostrar el link para acceder al planograma --%>
									<c:choose>
										<%-- Con link --%>
										<c:when test="${misumi:contieneOpcion(user.centro.opcHabil, '45_MODIF_IMC_PLANOS') && imc.facingAncho > 0}">
											<label class="etiquetaCampoNegrita"> 
												<spring:message code="pda_p12_datosReferencia.implCajas" /> 
											</label>
											<c:choose> 
												<%-- El link sale normal --%>
												<c:when test="${empty guardadoImc}">
													<a href="./pdaP33Imc.do?codArt=${data.codArt}&procede=pdaP118DatosRefDetalleReferencia">
														<span class="enlaceNormal"> 
															<spring:message code="pda_p12_datosReferencia.implCajas.ancho.sinImpl" />&nbsp; 
															${imc.facingAncho}&nbsp;
															<spring:message code="pda_p12_datosReferencia.implCajas.alto" />&nbsp;
															${imc.facingAlto}
														</span>
													</a>
												</c:when>
												<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
												<c:otherwise>
													<a href="./pdaP33Imc.do?codArt=${data.codArt}&procede=pdaP118DatosRefDetalleReferencia">
														<span class="enlaceVerde"> 
															<spring:message code="pda_p12_datosReferencia.implCajas.ancho.sinImpl" />&nbsp;
															${imc.facingAncho}&nbsp;
															<spring:message code="pda_p12_datosReferencia.implCajas.alto" />&nbsp;
															${imc.facingAlto}&nbsp;
														</span>
													</a>
												</c:otherwise>
											</c:choose>
										</c:when>
										<%-- Sin link --%>
										<c:otherwise>
											<label class="etiquetaCampoNegrita"> 
												<spring:message code="pda_p12_datosReferencia.implCajas.ancho" /> 
											</label>
											${imc.facingAncho}
											<label class="etiquetaCampoNegrita"> 
												<spring:message code="pda_p12_datosReferencia.implCajas.alto" /> 
											</label>
											${imc.facingAlto}
										</c:otherwise>
									</c:choose>
								</div>	
							</c:if>																			
							<%-- BLOQUE CAP LIN Y FAC LIN --%>
							<div id="pda_p118_datosReferencia_bloque5_facCap">
								<%-- CAPACIDAD SIN LINK --%>						
								<div id="pda_p118_datosReferencia_bloque5_capacidad">						
										<label id="pda_p118_lbl_capacidad" class="etiquetaCampoNegrita">
											<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
										</label>
										<label id="pda_p118_lbl_capacidadVal" class="valorCampo">
											${imc.capacidad}
										</label>
								</div>
								<%-- VALOR DE FACING SIN LINK --%>
								<div id="pda_p118_datosReferencia_bloque5_facingDerecha">
									<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita">
										<spring:message code="pda_p12_datosReferencia.facingLineal" />
									</label> 
									<label id="pda_p118_lbl_facingVal" class="valorCampo">
										${imc.facing}
									</label>
								</div>
							</div>											
							<%-- BLOQUE DE MULTIPLICADOR E IMC --%>
							<div id="pda_p118_datosReferencia_bloque5_multipImcDerecha">
								<%-- VALOR MULTIP SI NO ES FFPP--%>
								<c:if test="${imc.tipoReferencia ne esFFPPRef}">
									<div id="pda_p118_datosReferencia_bloque5_multipDerecha">
										<label class="etiquetaCampoNegrita"> 
											<spring:message	code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
										</label> 
										<label class="valorCampo">
											${imc.multiplicador} 
										</label>
									</div>
								</c:if>
								<%-- VALOR IMC --%>
								<div id="pda_p118_datosReferencia_bloque5_imcDerecha">
									<label class="etiquetaCampoNegrita"> <spring:message
											code="pda_p12_datosReferencia.legendIMCImplantacion1" />
									</label> <label class="valorCampo"> ${imc.imc} </label>
								</div>
							</div>
							
							<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
							<c:if test = "${(data.pedidoActivo eq 'S') && (data.capacidad1 ne null && data.capacidad1 ne 0 && data.capacidad1 ne '') || (data.facing1 ne null && data.facing1 ne 0 && data.facing1 ne '')}">
								<div id="pda_p118_datosReferencia_bloque5_cap1fac1">
									<div id="pda_p118_datosReferencia_bloque5_cap1">
										<label id="pda_p118_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
										<label id="pda_p118_lbl_capacidad1Val" class="valorCampo">${data.capacidad1}</label>
									</div>
									<div id="pda_p118_datosReferencia_bloque5_fac1">
										<label id="pda_p118_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
										<label id="pda_p118_lbl_facing1Val" class="valorCampo">${data.facing1}</label>
									</div>
								</div>
							</c:if>		
							<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>	
						</c:when>
						<%-- FIN BLOQUES Si es CAJA EXPOSITORA --%>
						
						
						<%-- INICIO BLOQUES MADRE --%>
						<c:when test="${imc.tipoReferencia == esMadreRef}">													
							<%-- BLOQUE CAP LIN Y FAC LIN/FAC.LIN (ALTO X ANCHO) --%>
							<div id="pda_p118_datosReferencia_bloque5_facCap">
								<%-- CAPACIDAD SIN LINK --%>						
								<div id="pda_p118_datosReferencia_bloque5_capacidad">						
										<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita">
											<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
										</label>
										<label id="pda_p12_lbl_capacidadVal" class="valorCampo">
											${imc.capacidad}
										</label>
								</div>								
								<c:choose>
									<c:when test="${imc.facingAlto > 0}">
										<%-- VALOR DE FACING SIN LINK --%>
										<div id="pda_p118_datosReferencia_bloque5_facingDerecha">
										<%-- Miramos si hay que mostrar el link para acceder al planograma --%>
											<c:choose>
												<%-- Con link --%>
												<c:when test="${misumi:contieneOpcion(user.centro.opcHabil, '45_MODIF_IMC_PLANOS') && imc.facingAncho > 0}">
													<c:choose> 
														<%-- El link sale normal --%>
														<c:when test="${empty guardadoImc}">
															<a href="./pdaP33Imc.do?codArt=${data.codArt}&procede=pdaP118DatosRefDetalleReferencia">
																<span class="enlaceNormal"> 
																	<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoP" />&nbsp; 
																	${imc.facing}(${imc.facingAncho}*${imc.facingAlto})
																</span>
															</a>
														</c:when>
														<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
														<c:otherwise>
															<a href="./pdaP33Imc.do?codArt=${data.codArt}&procede=pdaP118DatosRefDetalleReferencia">
																<span class="enlaceVerde"> 
																	<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoP" />&nbsp; 
																	${imc.facing}(${imc.facingAncho}*${imc.facingAlto})
																</span>
															</a>
														</c:otherwise>
													</c:choose>
												</c:when>
												<%-- Sin link --%>
												<c:otherwise>
													<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita">
														<spring:message code="pda_p12_datosReferencia.facingLinealVPlanoP" />
													</label> 
													<label id="pda_p118_lbl_facingVal" class="valorCampo">
														${imc.facing}(${imc.facingAncho}*${imc.facingAlto})
													</label>
												</c:otherwise>
											</c:choose>
										</div>
									</c:when>
									<c:otherwise>
										<%-- VALOR DE FACING SIN LINK --%>
										<div id="pda_p118_datosReferencia_bloque5_facingDerecha">
											<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita">
												<spring:message code="pda_p12_datosReferencia.facingLineal" />
											</label> 
											<label id="pda_p118_lbl_facingVal" class="valorCampo">
												${imc.facing}
											</label>
										</div>
									</c:otherwise>									
								</c:choose>
							</div>
							
							<%-- BLOQUE DE MULTIPLICADOR E IMC --%>
							<div id="pda_p118_datosReferencia_bloque5_multipImcDerecha">
								<%-- VALOR MULTIP SI NO ES FFPP--%>
								<c:if test="${imc.tipoReferencia ne esFFPPRef}">
									<div id="pda_p118_datosReferencia_bloque5_multipDerecha">
										<label class="etiquetaCampoNegrita"> 
											<spring:message	code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
										</label> 
										<label class="valorCampo">
											${imc.multiplicador} 
										</label>
									</div>
								</c:if>
								<%-- VALOR IMC --%>
								<div id="pda_p118_datosReferencia_bloque5_imcDerecha">
									<label class="etiquetaCampoNegrita"> <spring:message
											code="pda_p12_datosReferencia.legendIMCImplantacion1" />
									</label> <label class="valorCampo"> ${imc.imc} </label>
								</div>
							</div>
							
							<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
							<c:if test = "${(data.pedidoActivo eq 'S') && (data.capacidad1 ne null && data.capacidad1 ne 0 && data.capacidad1 ne '') || (data.facing1 ne null && data.facing1 ne 0 && data.facing1 ne '')}">
								<div id="pda_p118_datosReferencia_bloque5_cap1fac1">
									<div id="pda_p118_datosReferencia_bloque5_cap1">
										<label id="pda_p118_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
										<label id="pda_p118_lbl_capacidad1Val" class="valorCampo">${data.capacidad1}</label>
									</div>
									<div id="pda_p118_datosReferencia_bloque5_fac1">
										<label id="pda_p118_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
										<label id="pda_p118_lbl_facing1Val" class="valorCampo">${data.facing1}</label>
									</div>
								</div>
							</c:if>		
							<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>																																																
						</c:when>
						<%-- FIN BLOQUES Si es MADRE --%>
						
						
						<%-- INICIO BLOQUES FFPP --%>
						<c:when test="${imc.tipoReferencia == esFFPPRef}">
							<%-- BLOQUE CAP LIN Y FAC LIN --%>
							<div id="pda_p118_datosReferencia_bloque5_facCap">
								<%-- CAPACIDAD SIN LINK --%>						
								<div id="pda_p118_datosReferencia_bloque5_capacidad">						
										<label id="pda_p118_lbl_capacidad" class="etiquetaCampoNegrita">
											<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
										</label>
										<label id="pda_p118_lbl_capacidadVal" class="valorCampo">
											${imc.capacidad}
										</label>
								</div>
								<%-- VALOR DE FACING SIN LINK --%>
								<div id="pda_p118_datosReferencia_bloque5_facingDerecha">
									<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita">
										<spring:message code="pda_p12_datosReferencia.facingLineal" />
									</label> 
									<label id="pda_p118_lbl_facingVal" class="valorCampo">
										${imc.facing}
									</label>
								</div>
							</div>											
							<%-- BLOQUE DE MULTIPLICADOR E IMC --%>
							
							<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
							<c:if test = "${(data.pedidoActivo eq 'S') && (data.capacidad1 ne null && data.capacidad1 ne 0 && data.capacidad1 ne '') || (data.facing1 ne null && data.facing1 ne 0 && data.facing1 ne '')}">
								<div id="pda_p118_datosReferencia_bloque5_cap1fac1">
									<div id="pda_p118_datosReferencia_bloque5_cap1">
										<label id="pda_p118_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
										<label id="pda_p118_lbl_capacidad1Val" class="valorCampo">${data.capacidad1}</label>
									</div>
									<div id="pda_p118_datosReferencia_bloque5_fac1">
										<label id="pda_p118_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
										<label id="pda_p118_lbl_facing1Val" class="valorCampo">${data.facing1}</label>
									</div>
								</div>
							</c:if>		
							<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>		
						</c:when>
						<%-- FIN BLOQUES FFPP --%>
					</c:choose>
				</div>
				<%-- FIN BLOQUE DATOS --%>
			</fieldset>
		</div>
	</c:when>
 	
	<%-- ------------------------------------------------------------------------------------ --%>
		
	<%-- SI ES SFM --%>
	
	<c:when test="${imc.metodo == esSfmRef}">
		<div id="pda_p118_datosReferencia_bloque5">
			<fieldset id="pda_p118_imagenComercial">
				<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP o no. --%>
				<c:choose>
					<%--Parte leyenda con link verde --%>
					<c:when test="${data.flgColorImplantacion eq 'VERDE'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda con link roja --%>
					<c:when test="${data.flgColorImplantacion eq 'ROJO'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda sin link negra --%>
					<c:otherwise>
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
								</legend>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<%-- FIN BLOQUE LEYENDA --%>
	
				<%-- INICIO BLOQUE DATOS --%>
				<div id="pda_p118_datosReferencia_bloque5_areaSfm">
					<div id="pda_p118_datosReferencia_bloque5_sfm">
						<%-- Miramos pedir y mostramos SFM con o sin Link --%>
						<c:choose>
							<%-- Con link --%>
							<c:when test="${data.pedir eq 'S'}">
								<c:choose>
									<%-- El link sale normal --%>
									<c:when test="${empty guardadoSfm}">
										<label id="pda_p118_lbl_sfm" class="etiquetaCampoNegrita"><a
											href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message
													code="pda_p12_datosReferencia.sfm" /></a></label>
										<label id="pda_p118_lbl_sfmVal" class="valorCampo">${imc.sfm}</label>
									</c:when>
									<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
									<c:otherwise>
										<label id="pda_p118_lbl_sfm" class="etiquetaCampoNegrita"><a
											href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}"><span
												class="pda_p118_enlaceSfmOk"><spring:message
														code="pda_p12_datosReferencia.sfm" /></span></a></label>
										<label id="pda_p118_lbl_sfmVal" class="valorCampo">${imc.sfm}</label>
									</c:otherwise>
								</c:choose>
							</c:when>
							<%-- Sin link --%>
							<c:otherwise>
								<label id="pda_p118_lbl_sfm" class="etiquetaCampoNegrita"><spring:message
										code="pda_p12_datosReferencia.sfm" /></label>
								<label id="pda_p118_lbl_sfmVal" class="valorCampo">${imc.sfm}</label>
							</c:otherwise>
						</c:choose>
					</div>
					<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
					<c:if test = "${(data.pedidoActivo eq 'S') && (data.capacidad1 ne null && data.capacidad1 ne 0 && data.capacidad1 ne '') || (data.facing1 ne null && data.facing1 ne 0 && data.facing1 ne '')}">
						<div id="pda_p118_datosReferencia_bloque5_cap1fac1">
							<div id="pda_p118_datosReferencia_bloque5_cap1">
								<label id="pda_p118_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
								<label id="pda_p118_lbl_capacidad1Val" class="valorCampo">${data.capacidad1}</label>
							</div>
							<div id="pda_p118_datosReferencia_bloque5_fac1">
								<label id="pda_p118_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
								<label id="pda_p118_lbl_facing1Val" class="valorCampo">${data.facing1}</label>
							</div>
						</div>
					</c:if>		
					<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>					
				</div>
				<%-- FIN BLOQUE DATOS --%>
			</fieldset>
		</div>
	</c:when>	
	
	<%-- ------------------------------------------------------------------------------------ --%>
		
	<%-- SI ES FAC CAP --%>
	
	<c:when test="${imc.metodo == esFacCapRef}">
		<div id="pda_p118_datosReferencia_bloque5">
			<fieldset id="pda_p118_imagenComercial">
				<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP o no. --%>
				<c:choose>
					<%--Parte leyenda con link verde --%>
					<c:when test="${data.flgColorImplantacion eq 'VERDE'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda con link roja --%>
					<c:when test="${data.flgColorImplantacion eq 'ROJO'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda sin link negra --%>
					<c:otherwise>
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
								</legend>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<%-- FIN BLOQUE LEYENDA --%>
	
				<%-- INICIO BLOQUE DATOS --%>
				<div id="pda_p12_datosReferencia_bloque5_areaFacCap">
					<%-- VALOR DE CAPACIDAD Y VALOR DE FACING CON Y SIN LINK --%>
					<div id="pda_p118_datosReferencia_bloque5_facCap">
						<%-- VALOR DE CAPACIDAD SIN LINK --%>
						<div id="pda_p118_datosReferencia_bloque5_capacidad">
							<label id="pda_p118_lbl_capacidad" class="etiquetaCampoNegrita"><spring:message
									code="pda_p12_datosReferencia.capacidadLineal" /></label> 
							<label id="pda_p118_lbl_capacidadVal" class="valorCampo">${imc.capacidad}</label>
						</div>
						<%-- VALOR DE FACING --%>
						<div id="pda_p118_datosReferencia_bloque5_facingDerecha">
							<c:choose>
								<%-- FACING CON LINK --%>
								<c:when test="${data.MMC eq 'S'}">
									<c:if test="${empty guardadoSfm }">
										<label id="pda_p118_lbl_facingEnlace"
											class="etiquetaCampoNegrita"><a
											href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}">
											<spring:message	code="pda_p12_datosReferencia.facingLineal" /></a></label>
										<label id="pda_p118_lbl_facingVal" class="valorCampo">${imc.facing}</label>
									</c:if>
									<c:if test="${not empty guardadoSfm }">
										<label id="pda_p118_lbl_facingEnlace"
											class="etiquetaCampoNegrita"><a
											href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}"><span
												class="pda_p118_enlaceSfmOk"><spring:message
														code="pda_p12_datosReferencia.facingLineal" /></span></a></label>
										<label id="pda_p118_lbl_facingVal" class="valorCampo">${imc.facing}</label>
									</c:if>
								</c:when>
								<%-- FACING SIN LINK --%>
								<c:otherwise>
									<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.facingLineal" /></label>
									<label id="pda_p118_lbl_facingVal" class="valorCampo">${imc.facing}</label>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					
						<%-- VALOR DE MULTIPLICADOR E IMC --%>
						<div id="pda_p118_datosReferencia_bloque5_multipImcDerecha">
							<%-- VALOR MULTIP SI NO ES FFPP--%>
							<c:if test="${imc.tipoReferencia ne esFFPPRef}">
								<div id="pda_p118_datosReferencia_bloque5_multipDerecha">
									<label class="etiquetaCampoNegrita"> <spring:message
											code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
									</label> <label class="valorCampo">
										${imc.multiplicador} </label>
								</div>
							</c:if>
							<%-- VALOR IMC --%>
							<div id="pda_p118_datosReferencia_bloque5_imcDerecha">
								<label class="etiquetaCampoNegrita"> <spring:message
										code="pda_p12_datosReferencia.legendIMCImplantacion1" />
								</label> <label class="valorCampo"> ${imc.imc} </label>
							</div>
						</div>
					
					<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
					<c:if test = "${(data.pedidoActivo eq 'S') && (data.capacidad1 ne null && data.capacidad1 ne 0 && data.capacidad1 ne '') || (data.facing1 ne null && data.facing1 ne 0 && data.facing1 ne '')}">
						<div id="pda_p118_datosReferencia_bloque5_cap1fac1">
							<div id="pda_p118_datosReferencia_bloque5_cap1">
								<label id="pda_p118_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
								<label id="pda_p118_lbl_capacidad1Val" class="valorCampo">${data.capacidad1}</label>
							</div>
							<div id="pda_p118_datosReferencia_bloque5_fac1">
								<label id="pda_p118_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
								<label id="pda_p118_lbl_facing1Val" class="valorCampo">${data.facing1}</label>
							</div>
						</div>
					</c:if>		
					<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>	
				</div>
				<%-- FIN BLOQUE DATOS --%>
			</fieldset>
		</div>
	</c:when>

	<%-- ------------------------------------------------------------------------------------ --%>

	<%-- SI ES CAP --%>
	<c:when test="${imc.metodo == esCapRef}">
		<div id="pda_p118_datosReferencia_bloque5">
			<fieldset id="pda_p118_imagenComercial">
				<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP, esMadreRef o CAJA EXP. --%>
				<c:choose>
					<%--Parte leyenda con link verde --%>
					<c:when test="${data.flgColorImplantacion eq 'VERDE'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message
											code="pda_p12_datosReferencia.implantacionCajaExp" />
									</a>
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p12_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda con link roja --%>
					<c:when test="${data.flgColorImplantacion eq 'ROJO'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
									</a>
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda sin link negra --%>
					<c:otherwise>
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
								</legend>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<%-- FIN BLOQUE LEYENDA --%>
	
				<%-- INICIO BLOQUE DATOS --%>
				<div id="pda_p118_datosReferencia_bloque5_areaCap">
					<%-- VALOR DE CAPACIDAD Y VALOR DE FACING CON Y SIN LINK --%>
					<div id="pda_p118_datosReferencia_bloque5_facCap">
						<%-- VALOR DE CAPACIDAD CON Y SIN LINK --%>
						<div id="pda_p118_datosReferencia_bloque5_capacidad">
							<c:choose>
								<%-- CAPACIDAD CON LINK --%>
								<c:when test="${data.MMC eq 'S'}">
									<c:if test="${empty guardadoSfm}">
										<label id="pda_p118_lbl_capacidadEnlace"
											class="etiquetaCampoNegrita"> <a
											href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message
													code="pda_p12_datosReferencia.capacidadLineal" /></a>
										</label>
										<label id="pda_p118_lbl_capacidadVal" class="valorCampo">${imc.capacidad}</label>
									</c:if>
									<c:if test="${not empty guardadoSfm}">
										<label id="pda_p118_lbl_capacidadEnlace"
											class="etiquetaCampoNegrita"> <a
											href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}">
												<span class="pda_p118_enlaceSfmOk"><spring:message
														code="pda_p12_datosReferencia.capacidadLineal" /></span>
										</a>
										</label>
										<label id="pda_p118_lbl_capacidadVal" class="valorCampo">${imc.capacidad}</label>
									</c:if>
								</c:when>
								<%-- CAPACIDAD SIN LINK --%>
								<c:otherwise>
									<label id="pda_p118_lbl_capacidad" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.capacidadLineal" /></label>
									<label id="pda_p118_lbl_capacidadVal" class="valorCampo">${imc.capacidad}</label>
								</c:otherwise>
							</c:choose>
						</div>
						<%-- VALOR DE FACING SIN LINK --%>
						<div id="pda_p118_datosReferencia_bloque5_facingDerecha">
							<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita"><spring:message
									code="pda_p12_datosReferencia.facing" /></label> 
							<label id="pda_p118_lbl_facingVal" class="valorCampo">${imc.facing}</label>
						</div>
					</div>
					<%-- VALOR DE MULTIPLICADOR E IMC --%>
					<div id="pda_p118_datosReferencia_bloque5_multipImcDerecha">
						<%-- VALOR MULTIP SI NO ES FFPP--%>
						<c:if test="${imc.tipoReferencia ne esFFPPRef}">
							<div id="pda_p118_datosReferencia_bloque5_multipDerecha">
								<label class="etiquetaCampoNegrita"> <spring:message
										code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
								</label> <label class="valorCampo">
									${imc.multiplicador} </label>
							</div>
						</c:if>
						<%-- VALOR IMC --%>
						<div id="pda_p118_datosReferencia_bloque5_imcDerecha">
							<label class="etiquetaCampoNegrita"> <spring:message
									code="pda_p12_datosReferencia.legendIMCImplantacion1" />
							</label> <label class="valorCampo"> ${imc.imc} </label>
						</div>
					</div>
					
					<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
					<c:if test = "${(data.pedidoActivo eq 'S') && (data.capacidad1 ne null && data.capacidad1 ne 0 && data.capacidad1 ne '') || (data.facing1 ne null && data.facing1 ne 0 && data.facing1 ne '')}">
						<div id="pda_p118_datosReferencia_bloque5_cap1fac1">
							<div id="pda_p118_datosReferencia_bloque5_cap1">
								<label id="pda_p118_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
								<label id="pda_p118_lbl_capacidad1Val" class="valorCampo">${data.capacidad1}</label>
							</div>
							<div id="pda_p118_datosReferencia_bloque5_fac1">
								<label id="pda_p118_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
								<label id="pda_p118_lbl_facing1Val" class="valorCampo">${data.facing1}</label>
							</div>
						</div>
					</c:if>		
					<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>	
				</div>
				<%-- FIN BLOQUE DATOS --%>
			</fieldset>
		</div>
	</c:when>

	<%-- ------------------------------------------------------------------------------------ --%>
	
	<%-- SI ES FAC --%>
	<c:when test="${imc.metodo == esFacNoAliRef}">
		<div id="pda_p118_datosReferencia_bloque5">
			<fieldset id="pda_p118_imagenComercial">
				<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP, esMadreRef o CAJA EXP. --%>
				<c:choose>
					<%--Parte leyenda con link verde --%>
					<c:when test="${data.flgColorImplantacion eq 'VERDE'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message
											code="pda_p12_datosReferencia.implantacionCajaExp" />
									</a>
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanVerde"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda con link roja --%>
					<c:when test="${data.flgColorImplantacion eq 'ROJO'}">
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
									</a>
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
									</a>
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<a id="pda_p118_imagenComercialSpanRojo"
										href="./pdaP17implantacionPopup.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&implantacion=${data.implantacion}&flgColorImplantacion=${data.flgColorImplantacion}&origenGISAE=${origenGISAE}">
										<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
									</a>
								</legend>
							</c:otherwise>
						</c:choose>
					</c:when>
					<%--Parte leyenda sin link negra --%>
					<c:otherwise>
						<c:choose>
							<c:when test="${imc.tipoReferencia eq esCajaExpositoraRef}">
								<legend id="pda_p118_imagenComercialLegend"> 
									<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
								</legend>
							</c:when>
							<c:when test="${imc.tipoReferencia eq esMadreRef}">
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</legend>
							</c:when>
							<c:otherwise>
								<legend id="pda_p118_imagenComercialLegend">
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
								</legend>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<%-- FIN BLOQUE LEYENDA --%>
				<%-- INICIO CONTENIDO --%>
				<div id="pda_p118_datosReferencia_bloque5_areaFacing">
					<%-- VALOR FACING CON Y SIN LINK --%>
					<div id="pda_p118_datosReferencia_bloque5_facing">
						<c:choose>
							<%-- FACING CON LINK --%>
							<c:when test="${data.MMC eq 'S'}">
								<c:if test="${empty guardadoSfm }">
									<label id="pda_p118_lbl_facingEnlace"
										class="etiquetaCampoNegrita"><a
										href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message
												code="pda_p12_datosReferencia.facing" /></a></label>
									<label id="pda_p118_lbl_facingVal" class="valorCampo">${imc.facing}</label>
								</c:if>
								<c:if test="${not empty guardadoSfm }">
									<label id="pda_p118_lbl_facingEnlace"
										class="etiquetaCampoNegrita"><a
										href="./pdaP21Sfm.do?procede=pdaP118DatosRefDetalleReferencia&codArt=${data.codArt}&origen=1&origenGISAE=${origenGISAE}"><span
											class="pda_p118_enlaceSfmOk"><spring:message
													code="pda_p12_datosReferencia.facing" /></span></a></label>
									<label id="pda_p118_lbl_facingVal" class="valorCampo">${imc.facing}</label>
								</c:if>
							</c:when>
							<%-- FACING SIN LINK --%>
							<c:otherwise>
								<label id="pda_p118_lbl_facing" class="etiquetaCampoNegrita"><spring:message
										code="pda_p12_datosReferencia.facing" /></label>
								<label id="pda_p118_lbl_facingVal" class="valorCampo">${imc.facing}</label>
							</c:otherwise>
						</c:choose>
					</div>
					<%-- VALOR MULTIP E IMC --%>
					<div id="pda_p118_datosReferencia_bloque5_multipImc">
						<%-- VALOR MULTIP SI NO ES FFPP--%>
						<c:if test="${imc.tipoReferencia ne esFFPPRef}">
							<div id="pda_p118_datosReferencia_bloque5_multip">
								<label class="etiquetaCampoNegrita"> <spring:message
										code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
								</label> 
								<label class="valorCampo">${imc.multiplicador}</label>
							</div>
						</c:if>
						<%-- VALOR IMC --%>
						<div id="pda_p118_datosReferencia_bloque5_imc">
							<label class="etiquetaCampoNegrita"> <spring:message
									code="pda_p12_datosReferencia.legendIMCImplantacion1" />
							</label> 
							<label class="valorCampo"> ${imc.imc} </label>
						</div>
					</div>
					
					<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
					<c:if test = "${(data.pedidoActivo eq 'S') && (data.capacidad1 ne null && data.capacidad1 ne 0 && data.capacidad1 ne '') || (data.facing1 ne null && data.facing1 ne 0 && data.facing1 ne '')}">
						<div id="pda_p118_datosReferencia_bloque5_cap1fac1">
							<div id="pda_p118_datosReferencia_bloque5_cap1">
								<label id="pda_p118_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
								<label id="pda_p118_lbl_capacidad1Val" class="valorCampo">${data.capacidad1}</label>
							</div>
							<div id="pda_p118_datosReferencia_bloque5_fac1">
								<label id="pda_p118_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
								<label id="pda_p118_lbl_facing1Val" class="valorCampo">${data.facing1}</label>
							</div>
						</div>
					</c:if>		
					<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>	
				</div>
				<%-- FIN CONTENIDO --%>
			</fieldset>
		</div>
	</c:when>
	
</c:choose>					