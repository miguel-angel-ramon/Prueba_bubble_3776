<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- METODO --%>
<c:set var = "esPlanogramaRef" value = "1"/>
<c:set var = "esSfmRef" value = "2"/>
<c:set var = "esFacCapRef" value = "3"/>
<c:set var = "esCapRef" value = "4"/>
<c:set var = "esFacNoAliRef" value = "5"/>
					
<%-- TIPO REFERENCIA --%>
<c:set var = "esCajaExpositoraRef" value = "1"/>
<c:set var = "esMadreRef" value = "2"/>
<c:set var = "esFFPPRef" value = "3"/>

<jsp:include page="/WEB-INF/views/includes/pda_p03_headerReducido.jsp" >
    <jsp:param name="cssFile" value="pda/ayudaFacing/pda_p119_ayudaFacing.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFileComun" value="pda_p33Imc.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFilePropio" value="pda_p33ImcCajaExp.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP119AyudaFacing.do"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" action="pdaP119AyudaFacing.do" commandName="pdaDatosImc">
				<div id="pda_p119_ayudaFacing_bloqueDescSegmento">
					<span id=pda_p119_descSegmento>${pdaDatosPopupImplantacion.estructura}</span>
				</div>
							
				<div id="pda_p119_ayudaFacing_bloqueReferencia">
					<form:input id="pda_p119_fld_descripcionRef" path="descArtConCodigo" class="input225" disabled="true" type="text"/>
				</div>

				<div id="pda_p119_ayudaFacing_bloqueImplantacion">
					<c:choose>
						<%-- Parte leyenda con link VERDE --%>
						<c:when test="${pdaDatosPopupImplantacion.flgColorImplantacion eq 'VERDE'}">
							<a href="./pdaP120ImpPopup.do?codArt=${pdaDatosCab.codArtCab}&descArt=${pdaDatosImc.descArtConCodigo}&impl=${pdaDatosPopupImplantacion.implantacion}
									&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}&tipoRef=${pdaDatosImc.tipoReferencia}&facAncho=${pdaDatosImc.facAncho}
									&facAlto=${pdaDatosImc.facAlto}&cap=${pdaDatosImc.capacidad}&fac=${pdaDatosImc.facing}&imc=${pdaDatosImc.imc}
									&mult=${pdaDatosImc.multiplicador}">
								<span id="pda_p119_ayudaFacingSpanVerde">${pdaDatosPopupImplantacion.tituloImplantacion}</span>
							</a>
						</c:when>
						<%-- Parte leyenda con link ROJO --%>
						<c:when test="${pdaDatosPopupImplantacion.flgColorImplantacion eq 'ROJO'}">
							<a href="./pdaP120ImpPopup.do?codArt=${pdaDatosCab.codArtCab}&descArt=${pdaDatosImc.descArtConCodigo}&impl=${pdaDatosPopupImplantacion.implantacion}
									&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}&tipoRef=${pdaDatosImc.tipoReferencia}&facAncho=${pdaDatosImc.facAncho}
									&facAlto=${pdaDatosImc.facAlto}&cap=${pdaDatosImc.capacidad}&fac=${pdaDatosImc.facing}&imc=${pdaDatosImc.imc}
									&mult=${pdaDatosImc.multiplicador}">
								<span id="pda_p119_ayudaFacingSpanRojo">${pdaDatosPopupImplantacion.tituloImplantacion}</span>
							</a>
						</c:when>
						<%-- Parte leyenda con link AZUL --%>
						<c:when test="${pdaDatosPopupImplantacion.flgColorImplantacion eq 'AZUL'}">
							<a href="./pdaP120ImpPopup.do?codArt=${pdaDatosCab.codArtCab}&descArt=${pdaDatosImc.descArtConCodigo}&impl=${pdaDatosPopupImplantacion.implantacion}
									&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}&tipoRef=${pdaDatosImc.tipoReferencia}&facAncho=${pdaDatosImc.facAncho}
									&facAlto=${pdaDatosImc.facAlto}&cap=${pdaDatosImc.capacidad}&fac=${pdaDatosImc.facing}&imc=${pdaDatosImc.imc}
									&mult=${pdaDatosImc.multiplicador}">
								<span id="pda_p119_ayudaFacingSpanAzul>">${pdaDatosPopupImplantacion.tituloImplantacion}</span>
							</a>
						</c:when>
						<%-- Parte leyenda con link NEGRO --%>
						<c:otherwise>
							<span class="enlaceNormal">${pdaDatosPopupImplantacion.tituloImplantacion}</span>
						</c:otherwise>
					</c:choose>
				</div>

			<%-- INICIO BLOQUE DATOS --%>
			<fieldset id="pda_p119_imagenComercial">
				<div id="pda_p119_datosReferencia_areaPlanograma">

					<c:choose>
						<%-- INICIO BLOQUES Si es CAJA EXPOSITORA --%>
						<c:when	test="${pdaDatosImc.tipoReferencia == esCajaExpositoraRef}">
							<%-- BLOQUE DE IMPL.CAJA ALTO X ANCHO --%>
							<c:if test="${pdaDatosImc.facAlto > 0}">
								<div id="pda_p119_datosReferencia_ImplCajas">
									<%-- Miramos si hay que mostrar el link para acceder al planograma --%>
									<c:choose>
										<%-- Con link --%>
										<c:when	test="${misumi:contieneOpcion(user.centro.opcHabil, '45_MODIF_IMC_PLANOS')
													&& pdaDatosImc.facAncho > 0}">
											<label class="etiquetaCampoNegrita">
												<spring:message	code="pda_p119_datosReferencia.implCajas"/>
											</label>
											<c:choose>
												<%-- El link sale normal --%>
												<c:when test="${empty pdaDatosImc.guardadoImc}">
													<a href="./pdaP33Imc.do?codArt=${pdaDatosImc.codArt}
															&procede=pdaP119AyudaFacing
															&impl=${pdaDatosPopupImplantacion.implantacion}
															&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}">
														<span class="enlaceNormal">
															<spring:message	code="pda_p119_datosReferencia.implCajas.ancho.sinImpl"/>
																&nbsp;${pdaDatosImc.facAncho}&nbsp;
															<spring:message code="pda_p119_datosReferencia.implCajas.alto"/>
																&nbsp;${pdaDatosImc.facAlto}
														</span>
													</a>
												</c:when>
												<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
												<c:otherwise>
													<a href="./pdaP33Imc.do?codArt=${pdaDatosImc.codArt}
															&procede=pdaP119AyudaFacing
															&impl=${pdaDatosPopupImplantacion.implantacion}
															&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}">
														<span class="enlaceVerde">
															<spring:message	code="pda_p119_datosReferencia.implCajas.ancho.sinImpl"/>
																&nbsp;${pdaDatosImc.facAncho}&nbsp;
															<spring:message code="pda_p119_datosReferencia.implCajas.alto"/>
																&nbsp;${pdaDatosImc.facAlto}&nbsp;
														</span>
													</a>
												</c:otherwise>
											</c:choose>
										</c:when>
										<%-- Sin link --%>
										<c:otherwise>
											<label class="etiquetaCampoNegrita">
												<spring:message	code="pda_p119_datosReferencia.implCajas.ancho"/>
											</label>
											${pdaDatosImc.facAncho}
											<label class="etiquetaCampoNegrita">
												<spring:message	code="pda_p119_datosReferencia.implCajas.alto"/>
											</label>
											${pdaDatosImc.facAlto}
										</c:otherwise>
									</c:choose>
								</div>
							</c:if>
							<%-- BLOQUE CAP LIN Y FAC LIN --%>
							<div id="pda_p119_datosReferencia_facCap">
								<%-- CAPACIDAD SIN LINK --%>
								<div id="pda_p119_datosReferencia_capacidad">
									<label id="pda_p119_lbl_capacidad" class="etiquetaCampoNegrita">
										<spring:message code="pda_p119_datosReferencia.capacidadLineal"/>
									</label>
									<label id="pda_p119_lbl_capacidadVal" class="valorCampo">
										${pdaDatosImc.capacidad}
									</label>
								</div>
								<%-- VALOR DE FACING SIN LINK --%>
								<div id="pda_p119_datosReferencia_facingDerecha">
									<label id="pda_p119_lbl_facing" class="etiquetaCampoNegrita">
										<spring:message code="pda_p119_datosReferencia.facingLineal"/>
									</label>
									<label id="pda_p119_lbl_facingVal" class="valorCampo">
										${pdaDatosImc.facing}
									</label>
								</div>
							</div>
							<%-- BLOQUE DE MULTIPLICADOR E IMC --%>
							<div id="pda_p119_datosReferencia_multipImcDerecha">
								<%-- VALOR MULTIP SI NO ES FFPP--%>
								<c:if test="${pdaDatosImc.tipoReferencia ne esFFPPRef}">
									<div id="pda_p119_datosReferencia_multipDerecha">
										<label class="etiquetaCampoNegrita">
											<spring:message	code="pda_p119_datosReferencia.facingLinealVPlanoPMultipli"/>
										</label>
										<label class="valorCampo">
											${pdaDatosImc.multiplicador}
										</label>
									</div>
								</c:if>
								<%-- VALOR IMC --%>
								<div id="pda_p119_datosReferencia_imcDerecha">
									<label class="etiquetaCampoNegrita">
										<spring:message	code="pda_p119_datosReferencia.legendIMCImplantacion1"/>
									</label>
									<label class="valorCampo">
										${pdaDatosImc.imc}
									</label>
								</div>
							</div>
	
							<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
							<c:if
								test="${(pdaDatosRef.pedidoActivo eq 'S') && (pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
								<div id="pda_p119_datosReferencia_cap1fac1">
									<div id="pda_p119_datosReferencia_cap1">
										<label id="pda_p119_lbl_capacidad1" class="etiquetaCampoNegrita">
											<spring:message code="pda_p119_datosReferencia.capacidad1"/>
										</label>
										<label id="pda_p119_lbl_capacidad1Val" class="valorCampo">
											<spring:bind path="pdaDatosRef.capacidad1">${status.value}</spring:bind>
										</label>
									</div>
									<div id="pda_p119_datosReferencia_fac1">
										<label id="pda_p119_lbl_facing1" class="etiquetaCampoNegrita">
											<spring:message code="pda_p119_datosReferencia.facing1"/>
										</label>
										<label id="pda_p119_lbl_facing1Val" class="valorCampo">
											<spring:bind path="pdaDatosRef.facing1">${status.value}</spring:bind>
										</label>
									</div>
								</div>
							</c:if>
							<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
						</c:when>
						<%-- FIN BLOQUES Si es CAJA EXPOSITORA --%>
	
	
						<%-- INICIO BLOQUES MADRE --%>
						<c:when test="${pdaDatosImc.tipoReferencia == esMadreRef}">
							<%-- BLOQUE CAP LIN Y FAC LIN/FAC.LIN (ALTO X ANCHO) --%>
							<div id="pda_p119_datosReferencia_facCap">
								<%-- CAPACIDAD SIN LINK --%>
								<div id="pda_p119_datosReferencia_capacidad">
									<label id="pda_p119_lbl_capacidad" class="etiquetaCampoNegrita">
										<spring:message code="pda_p119_datosReferencia.capacidadLineal" />
									</label>
									<label id="pda_p119_lbl_capacidadVal" class="valorCampo">
										${pdaDatosImc.capacidad}
									</label>
								</div>
								<c:choose>
									<c:when test="${pdaDatosImc.facAlto > 0}">
										<%-- VALOR DE FACING SIN LINK --%>
										<div id="pda_p119_datosReferencia_facingDerecha">
											<%-- Miramos si hay que mostrar el link para acceder al planograma --%>
											<c:choose>
												<%-- Con link --%>
												<c:when	test="${misumi:contieneOpcion(user.centro.opcHabil, '45_MODIF_IMC_PLANOS')
															&& pdaDatosImc.facAncho > 0}">
													<c:choose>
														<%-- El link sale normal --%>
														<c:when test="${empty guardadoImc}">
															<a href="./pdaP33Imc.do?codArt=${pdaDatosImc.codArt}
																	&procede=pdaP119AyudaFacing
																	&impl=${pdaDatosPopupImplantacion.implantacion}
																	&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}">
																<span class="enlaceNormal">
																	<spring:message	code="pda_p119_datosReferencia.facingLinealVPlanoP" />&nbsp;
																	${pdaDatosImc.facing}(${pdaDatosImc.facAncho}*${pdaDatosImc.facAlto})
																</span>
															</a>
														</c:when>
														<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
														<c:otherwise>
															<a href="./pdaP33Imc.do?codArt=${pdaDatosImc.codArt}
																	&procede=pdaP119AyudaFacing
																	&impl=${pdaDatosPopupImplantacion.implantacion}
																	&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}">
																<span class="enlaceVerde">
																	<spring:message	code="pda_p119_datosReferencia.facingLinealVPlanoP"/>&nbsp;
																	${pdaDatosImc.facing}(${pdaDatosImc.facAncho}*${pdaDatosImc.facAlto})
																</span>
															</a>
														</c:otherwise>
													</c:choose>
												</c:when>
												<%-- Sin link --%>
												<c:otherwise>
													<label id="pda_p119_lbl_facing" class="etiquetaCampoNegrita">
														<spring:message	code="pda_p119_datosReferencia.facingLinealVPlanoP"/>
													</label>
													<label id="pda_p119_lbl_facingVal" class="valorCampo">
														${pdaDatosImc.facing}(${pdaDatosImc.facAncho}*${pdaDatosImc.facAlto})
													</label>
												</c:otherwise>
											</c:choose>
										</div>
									</c:when>
									<c:otherwise>
										<%-- VALOR DE FACING SIN LINK --%>
										<div id="pda_p119_datosReferencia_facingDerecha">
											<label id="pda_p119_lbl_facing" class="etiquetaCampoNegrita">
												<spring:message code="pda_p119_datosReferencia.facingLineal" />
											</label>
											<label id="pda_p119_lbl_facingVal" class="valorCampo">
												${pdaDatosImc.facing}
											</label>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
	
							<%-- BLOQUE DE MULTIPLICADOR E IMC --%>
							<div id="pda_p119_datosReferencia_multipImcDerecha">
								<%-- VALOR MULTIP SI NO ES FFPP--%>
								<c:if test="${pdaDatosImc.tipoReferencia ne esFFPPRef}">
									<div id="pda_p119_datosReferencia_multipDerecha">
										<label class="etiquetaCampoNegrita"> <spring:message
												code="pda_p119_datosReferencia.facingLinealVPlanoPMultipli" />
										</label>
										<label class="valorCampo">${pdaDatosImc.multiplicador}</label>
									</div>
								</c:if>
								<%-- VALOR IMC --%>
								<div id="pda_p119_datosReferencia_imcDerecha">
									<label class="etiquetaCampoNegrita">
										<spring:message	code="pda_p119_datosReferencia.legendIMCImplantacion1"/>
									</label>
									<label class="valorCampo">${pdaDatosImc.imc}</label>
								</div>
							</div>
	
							<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
							<c:if
								test="${(pdaDatosRef.pedidoActivo eq 'S') && (pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
								<div id="pda_p119_datosReferencia_cap1fac1">
									<div id="pda_p119_datosReferencia_cap1">
										<label id="pda_p119_lbl_capacidad1" class="etiquetaCampoNegrita">
											<spring:message	code="pda_p119_datosReferencia.capacidad1"/>
										</label>
										<label id="pda_p119_lbl_capacidad1Val" class="valorCampo">
											<spring:bind path="pdaDatosRef.capacidad1">${status.value}</spring:bind>
										</label>
									</div>
									<div id="pda_p119_datosReferencia_fac1">
										<label id="pda_p119_lbl_facing1" class="etiquetaCampoNegrita">
											<spring:message	code="pda_p119_datosReferencia.facing1"/>
										</label>
										<label id="pda_p119_lbl_facing1Val" class="valorCampo">
											<spring:bind path="pdaDatosRef.facing1">${status.value}</spring:bind>
										</label>
									</div>
								</div>
							</c:if>
							<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
						</c:when>
						<%-- FIN BLOQUES Si es MADRE --%>
					</c:choose>
				</div>
			</fieldset>
			<%-- FIN BLOQUE DATOS --%>

				<%-- Bloque lista de referencias --%>
				<div class="pda_p119_bloqueRegistro">
					<div id="pda_p119_rotacion_titulo">
						<label class="etiquetaCampoNegrita">
							<spring:message code="pda_p119_datosReferencia.rotacion"/>
							&nbsp;&nbsp;
							<spring:message	code="pda_p119_datosReferencia.fac"/>
						</label>
					</div>
				</div>
				<div class="pda_p119_bloqueRegistro">
					<div id="pda_p119_refsExc_titulo">
						<label class="etiquetaCampoNegrita">
							<spring:message	code="pda_p119_datosReferencia.exc"/>
						</label>
					</div>
				</div>

				<c:choose>
					<c:when test="${existeRefsAyudaFacing}">
						<div id="pda_p119_refs_ayuda_facing">
							<c:forEach items="${listaRefsAyudaFacing}" var="referencia">
								<div class="fila-ref clearfix">
									<div class="col-articulo">
										<a href="./pdaP118DatosRefDetalleReferencia.do?codArt=${referencia.codArticulo}&descArt=${pdaDatosImc.descArtConCodigo}&impl=${pdaDatosPopupImplantacion.implantacion}
									&flgColorImpl=${pdaDatosPopupImplantacion.flgColorImplantacion}&tipoRef=${pdaDatosImc.tipoReferencia}&facAncho=${pdaDatosImc.facAncho}
									&facAlto=${pdaDatosImc.facAlto}&cap=${pdaDatosImc.capacidad}&fac=${pdaDatosImc.facing}&imc=${pdaDatosImc.imc}
									&mult=${pdaDatosImc.multiplicador}&procede=ayudaFacing&codArtProc=${pdaDatosCab.codArtCab}">
											${referencia.codArticulo} - ${referencia.descArticulo}
										</a>
									</div>
										<div class="col-rotacion">
											<c:choose>
												<c:when test="${referencia.rotacion == 'AR'}">
							          				<span class="rotacion-ar">${referencia.rotacion}</span>
							        			</c:when>
							        			<c:when test="${referencia.rotacion == 'MR'}">
													<span class="rotacion-mr">${referencia.rotacion}</span>
												</c:when>
												<c:when test="${referencia.rotacion == 'BR'}">
													<span class="rotacion-br">${referencia.rotacion}</span>
												</c:when>
												<c:otherwise>
													${referencia.rotacion}
												</c:otherwise>
											</c:choose>
							    		</div>
							    	<div class="col-facing">${referencia.facingExcedente}</div>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<div style="color: red; font-weight: bold; text-align: center; padding: 20px;">
							<spring:message code="pda_p108_noHayMatricula.error" />
                        </div>
					</c:otherwise>
				</c:choose>
				<%-- FIN bloque lista de referencias --%>
				
				<input type="hidden" id="pda_p33_procede" name="procede" value="${procede}">
				<input type="hidden" id="pda_p33_refActual" name="referenciaActual" value="${imagenComercial.codArt}">
				<input type="hidden" id="pda_p33_tipoReferencia" value="${imagenComercial.tipoReferencia}">
				<input type="hidden" id="pda_p33_fld_facingOrig" name="referenciaActual" value="${imagenComercial.facing}">
				<input type="hidden" id="pda_p33_fld_capacidadOrig" value="${imagenComercial.capacidad}">
				<input type="hidden" id="pda_p33_fld_facAnchoOrig" value="${imagenComercial.facAncho}">
				<input type="hidden" id="pda_p33_fld_facAltoOrig" value="${imagenComercial.facAlto}">
				<input type="hidden" id="pda_p33_fld_tratamientoVegalsa" value="${imagenComercial.tratamientoVegalsa}">
			</form:form>
		</div>		
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>