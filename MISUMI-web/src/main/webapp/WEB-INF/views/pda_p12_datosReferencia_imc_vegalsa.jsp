<%-- SI ES PLANOGRAMA --%>
<c:when test="${pdaDatosRef.tratamientoVegalsa eq true}">

	<div id="pda_p12_datosReferencia_bloque5">
		<fieldset id="pda_p12_imagenComercial">
			<%-- INICIO BLOQUE LEYENDA --%>
			<c:choose>
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'VERDE'}">
					<legend id="pda_p12_imagenComercialLegendVerde">
						<span>
							<a id="pda_p12_imagenComercialSpanVerde" href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
								<c:choose>
									<c:when test="${not empty pdaDatosRef.msgUNIDADESCAJAS}">
										IMPLANTACION EN ${pdaDatosRef.msgUNIDADESCAJAS}
									</c:when>
									<c:otherwise>
										<spring:message code="pda_p12_datosReferencia.implantacionCajas"/>
									</c:otherwise>
								</c:choose>
							</a>
						</span>
					</legend>
				</c:when>
				
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'ROJO'}">
					<legend id="pda_p12_imagenComercialLegendRojo">
						<span>
							<a id="pda_p12_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
								<c:choose>
									<c:when test="${not empty pdaDatosRef.msgUNIDADESCAJAS}">
										IMPLANTACION EN ${pdaDatosRef.msgUNIDADESCAJAS}
									</c:when>
									<c:otherwise>
										<spring:message code="pda_p12_datosReferencia.implantacionCajas"/>
									</c:otherwise>
								</c:choose>
							</a>
						</span>
					</legend>
				</c:when>
				
				<c:otherwise>
					<legend id="pda_p12_imagenComercialLegend">
						<c:choose>
							<c:when test="${not empty pdaDatosRef.msgUNIDADESCAJAS}">
								IMPLANTACION EN ${pdaDatosRef.msgUNIDADESCAJAS}
							</c:when>
							<c:otherwise>
								<spring:message code="pda_p12_datosReferencia.implantacionCajas"/>
							</c:otherwise>
						</c:choose>
					</legend>
				</c:otherwise>
			</c:choose>
			<%-- FIN BLOQUE LEYENDA --%>
			
			<%-- INICIO BLOQUE DATOS --%>
			<div id="pda_p12_datosReferencia_bloque5_areaPlanograma">
			
				<%-- BLOQUE CAP LIN y FAC LIN --%>
				<div id="pda_p12_datosReferencia_bloque5_facCap">
					<%-- CAPACIDAD SIN Link --%>						
					<div id="pda_p12_datosReferencia_bloque5_capacidad">						
						<label id="pda_p12_lbl_capacidad" class="etiquetaCampoNegrita">
							<spring:message code="pda_p12_datosReferencia.capacidadLineal" />
						</label>
						<label id="pda_p12_lbl_capacidadVal" class="valorCampo">
							${imagenComercial.capacidad}
						</label>
					</div>								

					<%-- VALOR de FACING SIN Link --%>
					<div id="pda_p12_datosReferencia_bloque5_facingDerecha">

						<%-- Comprobar si hay que mostrar el link para acceder al planograma --%>
						<c:choose>
							<%-- CON Link --%>
							<c:when test="${imagenComercial.facingModificable eq 1}">
								<c:choose> 
									<%-- El link sale Azul --%>
									<c:when test="${empty guardadoImc}">
										<a href="./pdaP33Imc.do?codArt=${pdaDatosRef.codArt}&procede=${procede}">
											<span class="enlaceNormal">
												<c:choose>
													<c:when test="${not empty pdaDatosRef.msgUNIDADESCAJAS}">
														${pdaDatosRef.msgUNIDADESCAJASFacing}&nbsp;${imagenComercial.facing}
													</c:when>
													<c:otherwise>
														<spring:message code="pda_p12_datosReferencia.facingLinealInicial"/>&nbsp;${imagenComercial.facing}
													</c:otherwise>
												</c:choose>
											</span>
										</a>
									</c:when>
									<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
									<c:otherwise>
										<a href="./pdaP33Imc.do?codArt=${pdaDatosRef.codArt}&procede=${procede}">
											<span class="enlaceVerde">
												<c:choose>
													<c:when test="${not empty pdaDatosRef.msgUNIDADESCAJAS}">
														${pdaDatosRef.msgUNIDADESCAJASFacing}&nbsp;${imagenComercial.facing}
													</c:when>
													<c:otherwise>
														<spring:message code="pda_p12_datosReferencia.facingLinealInicial"/>&nbsp;${imagenComercial.facing}
													</c:otherwise>
												</c:choose>
											</span>
										</a>
									</c:otherwise>
								</c:choose>
							</c:when>
							<%-- SIN Link --%>
							<c:otherwise>
								<label id="pda_p12_lbl_facing" class="etiquetaCampoNegrita">
									<spring:message code="pda_p12_datosReferencia.facingLinealInicial"/>&nbsp;
								</label>
								<label id="pda_p12_lbl_facingVal" class="valorCampo">
									${imagenComercial.facing}
								</label>
							</c:otherwise>
						</c:choose>

					</div>
					
					<div id="pda_p12_datosReferencia_bloque5_multipDerecha">
						<label class="etiquetaCampoNegrita"> 
							<spring:message	code="pda_p12_datosReferencia.fondo" />
						</label> 
						<label class="valorCampo">
							${imagenComercial.multiplicador} 
						</label>
					</div>
					
				</div>
				
			</div>
		</fieldset>
	</div>
</c:when>