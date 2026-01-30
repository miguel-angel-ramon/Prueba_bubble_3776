<%-- SI ES PLANOGRAMA --%>
<c:when test="${imagenComercial.metodo == esPlanogramaRef}">
	<div id="pda_p12_datosReferencia_bloque5">
		<fieldset id="pda_p12_imagenComercial">
			<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP, esMadreRef o CAJA EXP. --%>
			<c:choose>
				<%--Parte leyenda con link VERDE --%>
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'VERDE'}">
					<legend id="pda_p12_imagenComercialLegend">
						<c:choose>
							<c:when test="${pdaDatosRef.parametrizadoAyudaFacing eq 'S'}">
								<a id="pda_p12_imagenComercialSpanVerde"
									href="./pdaP119AyudaFacing.do?codArt=${pdaDatosRef.codArt}&descArt=${pdaDatosCab.descArtCabConCodigoGenerico}&impl=${pdaDatosRef.implantacion}
										&flgColorImpl=${pdaDatosRef.flgColorImplantacion}&tipoRef=${imagenComercial.tipoReferencia}&guardadoImc=${guardadoImc}
										&facAncho=${imagenComercial.facingAncho}&facAlto=${imagenComercial.facingAlto}&cap=${imagenComercial.capacidad}&fac=${imagenComercial.facing}
										&imc=${imagenComercial.imc}&mult=${imagenComercial.multiplicador}">
							</c:when>
							<c:otherwise>
								<a id="pda_p12_imagenComercialSpanVerde"
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when	test="${imagenComercial.tipoReferencia eq esCajaExpositoraRef}">
								<spring:message	code="pda_p12_datosReferencia.implantacionCajaExp"/>
							</c:when>
							<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
								<spring:message code="pda_p12_datosReferencia.implantacionCajas"/>
							</c:when>
							<c:otherwise>
								<spring:message code="pda_p12_datosReferencia.implantacionFFPP"/>
							</c:otherwise>
						</c:choose>
						</a>
					</legend>
				</c:when>

				<%--Parte leyenda con link ROJO --%>
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'ROJO'}">
					<legend id="pda_p12_imagenComercialLegend">
						<c:choose>
							<c:when test="${pdaDatosRef.parametrizadoAyudaFacing eq 'S'}">
								<a id="pda_p12_imagenComercialSpanRojo"
									href="./pdaP119AyudaFacing.do?codArt=${pdaDatosRef.codArt}&descArt=${pdaDatosCab.descArtCabConCodigoGenerico}&impl=${pdaDatosRef.implantacion}
										&flgColorImpl=${pdaDatosRef.flgColorImplantacion}&tipoRef=${imagenComercial.tipoReferencia}&guardadoImc=${guardadoImc}
										&facAncho=${imagenComercial.facingAncho}&facAlto=${imagenComercial.facingAlto}&cap=${imagenComercial.capacidad}&fac=${imagenComercial.facing}
										&imc=${imagenComercial.imc}&mult=${imagenComercial.multiplicador}">
							</c:when>
							<c:otherwise>
								<a
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}"
									id="pda_p12_imagenComercialSpanRojo">
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when
								test="${imagenComercial.tipoReferencia eq esCajaExpositoraRef}">
								<spring:message	code="pda_p12_datosReferencia.implantacionCajaExp"/>
							</c:when>
							<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
								<spring:message code="pda_p12_datosReferencia.implantacionCajas"/>
							</c:when>
							<c:otherwise>
								<spring:message code="pda_p12_datosReferencia.implantacionFFPP"/>
							</c:otherwise>
						</c:choose>
						</a>
					</legend>
				</c:when>

				<%--Parte leyenda con link AZUL --%>
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'AZUL'}">
					<legend id="pda_p12_imagenComercialLegend">
						<a id="pda_p12_imagenComercialSpanAzul"
							href="./pdaP119AyudaFacing.do?codArt=${pdaDatosRef.codArt}&descArt=${pdaDatosCab.descArtCabConCodigoGenerico}&impl=${pdaDatosRef.implantacion}
										&flgColorImpl=${pdaDatosRef.flgColorImplantacion}&tipoRef=${imagenComercial.tipoReferencia}&guardadoImc=${guardadoImc}
										&facAncho=${imagenComercial.facingAncho}&facAlto=${imagenComercial.facingAlto}&cap=${imagenComercial.capacidad}&fac=${imagenComercial.facing}
										&imc=${imagenComercial.imc}&mult=${imagenComercial.multiplicador}">
							<c:choose>
								<c:when	test="${imagenComercial.tipoReferencia eq esCajaExpositoraRef}">
									<spring:message	code="pda_p12_datosReferencia.implantacionCajaExp"/>
								</c:when>
								<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
									<spring:message	code="pda_p12_datosReferencia.implantacionCajas"/>
								</c:when>
								<c:otherwise>
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP"/>
								</c:otherwise>
							</c:choose>
						</a>
					</legend>
				</c:when>

				<%--Parte leyenda sin link NEGRA --%>
				<c:otherwise>
					<c:choose>
						<c:when
							test="${imagenComercial.tipoReferencia eq esCajaExpositoraRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<spring:message	code="pda_p12_datosReferencia.implantacionCajaExp"/>
							</legend>
						</c:when>
						<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<spring:message code="pda_p12_datosReferencia.implantacionCajas"/>
							</legend>
						</c:when>
						<c:otherwise>
							<legend id="pda_p12_imagenComercialLegend">
								<spring:message code="pda_p12_datosReferencia.implantacionFFPP"/>
							</legend>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<%-- FIN BLOQUE LEYENDA --%>

			<%-- INICIO BLOQUE DATOS --%>
			<div id="pda_p12_datosReferencia_bloque5_areaPlanograma">
				<c:choose>
					<%-- INICIO BLOQUES Si es CAJA EXPOSITORA --%>
					<c:when
						test="${imagenComercial.tipoReferencia == esCajaExpositoraRef}">
						<%-- BLOQUE DE IMPL.CAJA ALTO X ANCHO --%>
						<c:if test="${imagenComercial.facingAlto > 0}">
							<div id="pda_p12_datosReferencia_bloque5_ImplCajas">
								<%-- Miramos si hay que mostrar el link para acceder al planograma --%>
								<c:choose>
									<%-- Con link --%>
									<c:when
										test="${misumi:contieneOpcion(user.centro.opcHabil, '45_MODIF_IMC_PLANOS') && imagenComercial.facingAncho > 0}">
										<label class="etiquetaCampoNegrita"> <spring:message
												code="pda_p12_datosReferencia.implCajas" />
										</label>
										<c:choose>
											<%-- El link sale normal --%>
											<c:when test="${empty guardadoImc}">
												<a href="./pdaP33Imc.do?codArt=${pdaDatosRef.codArt}&procede=${procede}">
													<span class="enlaceNormal"> 
														<spring:message code="pda_p12_datosReferencia.implCajas.ancho.sinImpl" />&nbsp; 
														${imagenComercial.facingAncho}&nbsp;
														<spring:message code="pda_p12_datosReferencia.implCajas.alto" />&nbsp;
														${imagenComercial.facingAlto}
												</span>
												</a>
											</c:when>
											<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
											<c:otherwise>
												<a href="./pdaP33Imc.do?codArt=${pdaDatosRef.codArt}&procede=${procede}">
													<span class="enlaceVerde"> 
														<spring:message code="pda_p12_datosReferencia.implCajas.ancho.sinImpl" />&nbsp;
														${imagenComercial.facingAncho}&nbsp;
														<spring:message code="pda_p12_datosReferencia.implCajas.alto" />&nbsp;
														${imagenComercial.facingAlto}&nbsp;
												</span>
												</a>
											</c:otherwise>
										</c:choose>
									</c:when>
									<%-- Sin link --%>
									<c:otherwise>
										<label class="etiquetaCampoNegrita"> <spring:message
												code="pda_p12_datosReferencia.implCajas.ancho" />
										</label>
										${imagenComercial.facingAncho}
										<label class="etiquetaCampoNegrita"> <spring:message
												code="pda_p12_datosReferencia.implCajas.alto" />
										</label>
										${imagenComercial.facingAlto}
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
						<%-- BLOQUE CAP LIN Y FAC LIN --%>
						<div id="pda_p12_datosReferencia_bloque5_facCap">
							<%-- CAPACIDAD SIN LINK --%>
							<div id="pda_p12_datosReferencia_bloque5_capacidad">
								<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita">
									<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
								</label> <label id="pda_p12_lbl_capacidadVal" class="valorCampo">
									${imagenComercial.capacidad} </label>
							</div>
							<%-- VALOR DE FACING SIN LINK --%>
							<div id="pda_p12_datosReferencia_bloque5_facingDerecha">
								<label id="pda_p12_lbl_facing" class="etiquetaCampoNegrita">
									<spring:message code="pda_p12_datosReferencia.facingLineal" />
								</label> <label id="pda_p12_lbl_facingVal" class="valorCampo">
									${imagenComercial.facing} </label>
							</div>
						</div>
						<%-- BLOQUE DE MULTIPLICADOR E IMC --%>
						<div id="pda_p12_datosReferencia_bloque5_multipImcDerecha">
							<%-- VALOR MULTIP SI NO ES FFPP--%>
							<c:if test="${imagenComercial.tipoReferencia ne esFFPPRef}">
								<div id="pda_p12_datosReferencia_bloque5_multipDerecha">
									<label class="etiquetaCampoNegrita"> <spring:message
											code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
									</label> <label class="valorCampo">
										${imagenComercial.multiplicador} </label>
								</div>
							</c:if>
							<%-- VALOR IMC --%>
							<div id="pda_p12_datosReferencia_bloque5_imcDerecha">
								<label class="etiquetaCampoNegrita"> <spring:message
										code="pda_p12_datosReferencia.legendIMCImplantacion1" />
								</label> <label class="valorCampo"> ${imagenComercial.imc} </label>
							</div>
						</div>

						<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
						<c:if
							test="${(pdaDatosRef.pedidoActivo eq 'S') && (pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
							<div id="pda_p12_datosReferencia_bloque5_cap1fac1">
								<div id="pda_p12_datosReferencia_bloque5_cap1">
									<label id="pda_p12_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.capacidad1" /></label> <label
										id="pda_p12_lbl_capacidad1Val" class="valorCampo"><spring:bind
											path="pdaDatosRef.capacidad1">${status.value}</spring:bind></label>
								</div>
								<div id="pda_p12_datosReferencia_bloque5_fac1">
									<label id="pda_p12_lbl_facing1" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.facing1" /></label> <label
										id="pda_p12_lbl_facing1Val" class="valorCampo"><spring:bind
											path="pdaDatosRef.facing1">${status.value}</spring:bind></label>
								</div>
							</div>
						</c:if>
						<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
					</c:when>
					<%-- FIN BLOQUES Si es CAJA EXPOSITORA --%>


					<%-- INICIO BLOQUES MADRE --%>
					<c:when test="${imagenComercial.tipoReferencia == esMadreRef}">
						<%-- BLOQUE CAP LIN Y FAC LIN/FAC.LIN (ALTO X ANCHO) --%>
						<div id="pda_p12_datosReferencia_bloque5_facCap">
							<%-- CAPACIDAD SIN LINK --%>
							<div id="pda_p12_datosReferencia_bloque5_capacidad">
								<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita">
									<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
								</label>
								<label id="pda_p12_lbl_capacidadVal" class="valorCampo">
									${imagenComercial.capacidad}
								</label>
							</div>
							<c:choose>
								<c:when test="${imagenComercial.facingAlto > 0}">
									<%-- VALOR DE FACING SIN LINK --%>
									<div id="pda_p12_datosReferencia_bloque5_facingDerecha">
										<%-- Miramos si hay que mostrar el link para acceder al planograma --%>
										<c:choose>
											<%-- Con link --%>
											<c:when	test="${misumi:contieneOpcion(user.centro.opcHabil, '45_MODIF_IMC_PLANOS')
													&& imagenComercial.facingAncho > 0}">
												<c:choose>
													<%-- El link sale normal --%>
													<c:when test="${empty guardadoImc}">
														<a href="./pdaP33Imc.do?codArt=${pdaDatosRef.codArt}&procede=${procede}">
															<span class="enlaceNormal"> <spring:message
																	code="pda_p12_datosReferencia.facingLinealVPlanoP" />&nbsp;
																${imagenComercial.facing}(${imagenComercial.facingAncho}*${imagenComercial.facingAlto})
														</span>
														</a>
													</c:when>
													<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
													<c:otherwise>
														<a href="./pdaP33Imc.do?codArt=${pdaDatosRef.codArt}&procede=${procede}">
															<span class="enlaceVerde"> <spring:message
																	code="pda_p12_datosReferencia.facingLinealVPlanoP" />&nbsp;
																${imagenComercial.facing}(${imagenComercial.facingAncho}*${imagenComercial.facingAlto})
														</span>
														</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<%-- Sin link --%>
											<c:otherwise>
												<label id="pda_p12_lbl_facing" class="etiquetaCampoNegrita">
													<spring:message	code="pda_p12_datosReferencia.facingLinealVPlanoP"/>
												</label>
												<label id="pda_p12_lbl_facingVal" class="valorCampo">
													${imagenComercial.facing}(${imagenComercial.facingAncho}*${imagenComercial.facingAlto})
												</label>
											</c:otherwise>
										</c:choose>
									</div>
								</c:when>
								<c:otherwise>
									<%-- VALOR DE FACING SIN LINK --%>
									<div id="pda_p12_datosReferencia_bloque5_facingDerecha">
										<label id="pda_p12_lbl_facing" class="etiquetaCampoNegrita">
											<spring:message code="pda_p12_datosReferencia.facingLineal" />
										</label>
										<label id="pda_p12_lbl_facingVal" class="valorCampo">
											${imagenComercial.facing}
										</label>
									</div>
								</c:otherwise>
							</c:choose>
						</div>

						<%-- BLOQUE DE MULTIPLICADOR E IMC --%>
						<div id="pda_p12_datosReferencia_bloque5_multipImcDerecha">
							<%-- VALOR MULTIP SI NO ES FFPP--%>
							<c:if test="${imagenComercial.tipoReferencia ne esFFPPRef}">
								<div id="pda_p12_datosReferencia_bloque5_multipDerecha">
									<label class="etiquetaCampoNegrita"> <spring:message
											code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
									</label> <label class="valorCampo">
										${imagenComercial.multiplicador} </label>
								</div>
							</c:if>
							<%-- VALOR IMC --%>
							<div id="pda_p12_datosReferencia_bloque5_imcDerecha">
								<label class="etiquetaCampoNegrita"> <spring:message
										code="pda_p12_datosReferencia.legendIMCImplantacion1" />
								</label> <label class="valorCampo"> ${imagenComercial.imc} </label>
							</div>
						</div>

						<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
						<c:if
							test="${(pdaDatosRef.pedidoActivo eq 'S') && (pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
							<div id="pda_p12_datosReferencia_bloque5_cap1fac1">
								<div id="pda_p12_datosReferencia_bloque5_cap1">
									<label id="pda_p12_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.capacidad1" /></label> <label
										id="pda_p12_lbl_capacidad1Val" class="valorCampo"><spring:bind
											path="pdaDatosRef.capacidad1">${status.value}</spring:bind></label>
								</div>
								<div id="pda_p12_datosReferencia_bloque5_fac1">
									<label id="pda_p12_lbl_facing1" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.facing1" /></label> <label
										id="pda_p12_lbl_facing1Val" class="valorCampo"><spring:bind
											path="pdaDatosRef.facing1">${status.value}</spring:bind></label>
								</div>
							</div>
						</c:if>
						<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
					</c:when>
					<%-- FIN BLOQUES Si es MADRE --%>


					<%-- INICIO BLOQUES FFPP --%>
					<c:when test="${imagenComercial.tipoReferencia == esFFPPRef}">
						<%-- BLOQUE CAP LIN Y FAC LIN --%>
						<div id="pda_p12_datosReferencia_bloque5_facCap">
							<%-- CAPACIDAD SIN LINK --%>
							<div id="pda_p12_datosReferencia_bloque5_capacidad">
								<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita">
									<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
								</label> <label id="pda_p12_lbl_capacidadVal" class="valorCampo">
									${imagenComercial.capacidad} </label>
							</div>
							<%-- VALOR DE FACING SIN LINK --%>
							<div id="pda_p12_datosReferencia_bloque5_facingDerecha">
								<label id="pda_p12_lbl_facing" class="etiquetaCampoNegrita">
									<spring:message code="pda_p12_datosReferencia.facingLineal" />
								</label> <label id="pda_p12_lbl_facingVal" class="valorCampo">
									${imagenComercial.facing} </label>
							</div>
						</div>
						<%-- BLOQUE DE MULTIPLICADOR E IMC --%>

						<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
						<c:if
							test="${(pdaDatosRef.pedidoActivo eq 'S') && (pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
							<div id="pda_p12_datosReferencia_bloque5_cap1fac1">
								<div id="pda_p12_datosReferencia_bloque5_cap1">
									<label id="pda_p12_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.capacidad1" /></label> <label
										id="pda_p12_lbl_capacidad1Val" class="valorCampo"><spring:bind
											path="pdaDatosRef.capacidad1">${status.value}</spring:bind></label>
								</div>
								<div id="pda_p12_datosReferencia_bloque5_fac1">
									<label id="pda_p12_lbl_facing1" class="etiquetaCampoNegrita"><spring:message
											code="pda_p12_datosReferencia.facing1" /></label> <label
										id="pda_p12_lbl_facing1Val" class="valorCampo"><spring:bind
											path="pdaDatosRef.facing1">${status.value}</spring:bind></label>
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