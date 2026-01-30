<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p42_inventarioLibre.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p42InventarioLibre.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP42InventarioLibre.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" id="pdaP42InventarioLibre" action="pdaP42InventarioLibre.do" commandName="pdaDatosInvLib">
			<div id="pda_p42_titulo">
			<c:choose>
				<c:when test="${empty (pdaDatosInvLib.origenGISAE)}">
					
						<label id="pda_p42_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p42_inventarioLibre.titulo" /></label>
					
				</c:when>
				<c:otherwise>
					<label id="pda_p42_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p42_inventario.titulo" /></label>
				</c:otherwise>
				</c:choose>
			</div>
			<c:choose>
				<c:when test="${not empty (pdaDatosInvLib.codArticulo)}">
				
				
					<div id="pda_p42_bloque1">
						<div id="pda_p42_bloque1_pagAnt">
							<c:choose>
								<c:when test="${pdaDatosInvLib.posicion > 1}">
									<input type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
								</c:when>
								<c:otherwise>
									<input type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>
								</c:otherwise>
							</c:choose>
						</div>	
						<div id="pda_p42_bloque1_pagNum">
							<p class="pda_p42_numReferencias"><spring:message code="pda_p42_inventarioLibre.numReferencias" arguments="${pdaDatosInvLib.posicion},${pdaDatosInvLib.total}" /></p>
						</div>
						<div id="pda_p42_bloque1_pagSig">
							<c:choose>
								<c:when test="${pdaDatosInvLib.total > pdaDatosInvLib.posicion}">
									<input type="submit" name="actionSig" value='' class='botonSubmitSig'/>
								</c:when>
								<c:otherwise>
									<input type="submit" name="actionSig" value='' class='botonSubmitSigDes' disabled="disabled"/>
								</c:otherwise>
							</c:choose>
						</div>					
					</div>
					<div id="pda_p42_bloqueError">
					<c:choose>
						<c:when test="${!empty (pdaDatosInvLib.error)}">					
							<p id="pda_p42_error" class="pda_p42_error">${pdaDatosInvLib.error}</p>
						</c:when>
						<c:when test="${!empty (pdaDatosInvLib.aviso)}">					
							<p id="pda_p42_aviso" class="pda_p42_aviso">${pdaDatosInvLib.aviso}</p>
						</c:when>
					</c:choose>	
					</div>
					<div id="pda_p42_bloque2">
						<c:choose>
							
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '25_PDA_ROTACION')) && empty (pdaDatosInvLib.origenGISAE) && !empty (pdaDatosInvLib.tipoRotacion)}">
								<div id="pda_p42_bloque2_descConTipoRot">	
									<form:input path="descArtConCodigo" id="pda_p42_fld_descripcionRef" class="input185" disabled="true" type="text"/>
								</div>
								<div id="pda_p42_bloque2_tipoRot">
									<c:choose> 
										<c:when test="${pdaDatosInvLib.tipoRotacion=='AR'}">
											<form:input path="tipoRotacion" id="pda_p42_fld_tipoRotacion" class="pda_p42_tipoRotacionAR" readonly="true" type="text"/>
										</c:when>
										<c:when test="${pdaDatosInvLib.tipoRotacion=='MR'}">
											<form:input path="tipoRotacion" id="pda_p42_fld_tipoRotacion" class="pda_p42_tipoRotacionMR" readonly="true" type="text"/>
										</c:when>	
										<c:when test="${pdaDatosInvLib.tipoRotacion=='BR'}">
											<form:input path="tipoRotacion" id="pda_p42_fld_tipoRotacion" class="pda_p42_tipoRotacionBR" readonly="true" type="text"/>
										</c:when>	
										<c:otherwise>
											<form:input path="tipoRotacion" id="pda_p42_fld_tipoRotacion" class="pda_p42_tipoRotacionUndef" readonly="true" type="text"/>
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:otherwise>
								<div id="pda_p42_bloque2_label">
									<label id="pda_p42_lbl_descripcionRef" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.descripcionRef" /></label>
								</div>
								<div id="pda_p42_bloque2_campo">	
									<form:input path="descArtConCodigo" id="pda_p42_fld_descripcionRef" class="input185" disabled="true" type="text"/>
								</div>
							</c:otherwise>
						</c:choose>			
					</div>		
					<div id="pda_p42_bloque3">
						<div id="pda_p42_stockActual">
							<form:input path="descStockActual" id="pda_p42_fld_descStockActual" class="input90" type="text" readonly="true"/>
						</div>
						<div id="pda_p42_diferencia">
							<form:input path="descDiferencia" id="pda_p42_fld_descDiferencia" class="input90" type="text" readonly="true"/>
						</div>
					</div>
					<div class="pda_p42_bloqueRegistro">
						<c:choose>
							<c:when test="${pdaDatosInvLib.flgStockPrincipal eq 'B'}">
								<div id="pda_p42_reg_origenInventarioCab" class="pda_p42_reg_origenInventarioCabConBandejas">&nbsp;</div>
								<div id="pda_p42_reg_bandejasCab" class="pda_p42_reg_bandejasCab">
									<label id="pda_p42_lbl_bandejas" class="etiquetaCampoNegrita"><spring:message code="pda_p42_inventarioLibre.bandejas" /></label>
								</div>
							</c:when>
							<c:otherwise>
								<div id="pda_p42_reg_origenInventarioCab" class="pda_p42_reg_origenInventarioCabSinBandejas">&nbsp;</div>
							</c:otherwise>
						</c:choose>		
						<div id="pda_p42_reg_stockCab" class="pda_p42_reg_stockCab">
							<label id="pda_p42_lbl_stock" class="etiquetaCampoNegrita"><spring:message code="pda_p42_inventarioLibre.stock" /></label>
						</div>
					</div>
					<div class="pda_p42_bloqueRegistro">
						<c:choose>
							<c:when test="${pdaDatosInvLib.flgStockPrincipal eq 'B'}">
								<div id="pda_p42_reg_origenInventarioCamara" class="pda_p42_reg_origenInventarioConBandejas">
									<label id="pda_p42_lbl_camaraAlmacen" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.camaraAlmacen" /></label>
								</div>
								<div id="pda_p42_reg_camaraBandeja" class="pda_p42_reg_bandejas">
									<c:choose>
										<c:when test="${'S' == pdaDatosInvLib.flgUnica}">
											<form:input path="camaraBandeja" id="pda_p42_fld_camaraBandeja" class="input50" type="number"/>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${'S' == pdaDatosInvLib.flgVariasUnitarias}">
													<input id="pda_p42_fld_camaraBandejaLink" class="input50 inputVariasUnitarias" type="text" value="" readonly="true"/>
												</c:when>
												<c:otherwise>
													<input id="pda_p42_fld_camaraBandejaLink" class="input50" type="text" value="${pdaDatosInvLib.camaraBandeja}" readonly="true"/>
												</c:otherwise>											
											</c:choose>	
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:otherwise>
								<div id="pda_p42_reg_origenInventarioCamara" class="pda_p42_reg_origenInventarioSinBandejas">
									<label id="pda_p42_lbl_camaraAlmacen" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.camaraAlmacen" /></label>
								</div>
							</c:otherwise>	
						</c:choose>		
						<div id="pda_p42_reg_camaraStock" class="pda_p42_reg_stock">
							<c:choose>
								<c:when test="${'S' == pdaDatosInvLib.flgUnica}">
									<form:input path="camaraStock" id="pda_p42_fld_camaraStock" class="input50" type="number" step="0.001"/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${'S' == pdaDatosInvLib.flgVariasUnitarias}">
											<input id="pda_p42_fld_camaraStockLink" class="input50 inputVariasUnitarias" type="text" value="" readonly="true"/>
										</c:when>
										<c:otherwise>
											<input id="pda_p42_fld_camaraStockLink" class="input50" type="number" value="${pdaDatosInvLib.camaraStock}" readonly="true"/>
										</c:otherwise>											
									</c:choose>	
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="pda_p42_bloqueRegistro">
						<c:choose>
							<c:when test="${pdaDatosInvLib.flgStockPrincipal eq 'B'}">
								<div id="pda_p42_reg_origenInventarioSala" class="pda_p42_reg_origenInventarioConBandejas">
									<label id="pda_p42_lbl_salaVenta" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.salaVenta" /></label>
								</div>
								<div id="pda_p42_reg_salaBandeja" class="pda_p42_reg_bandejas">
									<c:choose>
										<c:when test="${'S' == pdaDatosInvLib.flgUnica}">
											<form:input path="salaBandeja" id="pda_p42_fld_salaBandeja" class="input50" type="number"/>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${'S' == pdaDatosInvLib.flgVariasUnitarias}">
													<input id="pda_p42_fld_salaBandejaLink" class="input50 inputVariasUnitarias" type="number" value="" readonly="true"/>
												</c:when>
												<c:otherwise>
													<input id="pda_p42_fld_salaBandejaLink" class="input50" type="number" value="${pdaDatosInvLib.salaBandeja}" readonly="true"/>
												</c:otherwise>											
											</c:choose>	
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:otherwise>
								<div id="pda_p42_reg_origenInventarioSala" class="pda_p42_reg_origenInventarioSinBandejas">
									<label id="pda_p42_lbl_salaVenta" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.salaVenta" /></label>
								</div>
							</c:otherwise>	
						</c:choose>		
						<div id="pda_p42_reg_salaStock" class="pda_p42_reg_stock">
							<c:choose>
								<c:when test="${'S' == pdaDatosInvLib.flgUnica}">
									<form:input path="salaStock" id="pda_p42_fld_salaStock" class="input50" type="number" step="0.001"/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${'S' == pdaDatosInvLib.flgVariasUnitarias}">
											<input id="pda_p42_fld_salaStockLink" class="input50 inputVariasUnitarias" type="number" value="" readonly="true"/>
										</c:when>
										<c:otherwise>
											<input id="pda_p42_fld_salaStockLink" class="input50" type="number" value="${pdaDatosInvLib.salaStock}" readonly="true"/>
										</c:otherwise>											
									</c:choose>	
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<c:choose>
						<c:when test="${pdaDatosInvLib.flgStockPrincipal eq 'B'}">
							<div class="pda_p42_bloqueSeparadorTotalesB">&nbsp;</div>
						</c:when>
						<c:otherwise>
							<div class="pda_p42_bloqueSeparadorTotalesS">&nbsp;</div>
						</c:otherwise>	
					</c:choose>		
					<div class="pda_p42_bloqueRegistro">
						<c:choose>
							<c:when test="${pdaDatosInvLib.flgStockPrincipal eq 'B'}">
								<div id="pda_p42_reg_origenInventarioTotal" class="pda_p42_reg_origenInventarioConBandejas">
									<label id="pda_p42_lbl_total" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.total" /></label>
								</div>
								<div id="pda_p42_reg_totalBandeja" class="pda_p42_reg_bandejas">
									<c:choose>
										<c:when test="${'S' == pdaDatosInvLib.flgUnica}">
											<input id="pda_p42_fld_totalBandeja" class="input50" type="number" value="${pdaDatosInvLib.totalBandeja}" readonly="true"/>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${'S' == pdaDatosInvLib.flgVariasUnitarias}">
													<input id="pda_p42_fld_totalBandeja" class="input50 inputVariasUnitarias" type="number" value="" readonly="true"/>
												</c:when>
												<c:otherwise>
													<input id="pda_p42_fld_totalBandeja" class="input50" type="number" value="${pdaDatosInvLib.totalBandeja}" readonly="true"/>
												</c:otherwise>											
											</c:choose>	
										</c:otherwise>
									</c:choose>
								</div>
							</c:when>
							<c:otherwise>
								<div id="pda_p42_reg_origenInventarioTotal" class="pda_p42_reg_origenInventarioSinBandejas">
									<label id="pda_p42_lbl_total" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.total" /></label>
								</div>
							</c:otherwise>	
						</c:choose>		
						<div id="pda_p42_reg_totalStock" class="pda_p42_reg_stock">
							<c:choose>
								<c:when test="${'S' == pdaDatosInvLib.flgUnica}">
									<input id="pda_p42_fld_totalStock" class="input50" type="number" value="${pdaDatosInvLib.totalStock}" readonly="true"/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${'S' == pdaDatosInvLib.flgVariasUnitarias}">
											<input id="pda_p42_fld_totalStock" class="input50 inputVariasUnitarias" type="number" value="" readonly="true"/>
										</c:when>
										<c:otherwise>
											<input id="pda_p42_fld_totalStock" class="input50" type="number" value="${pdaDatosInvLib.totalStock}" readonly="true"/>
										</c:otherwise>											
									</c:choose>	
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				
					<c:if test="${user.perfil != 3}">
						<div id="pda_p42_inventarioLibre_filterButtons">
							<div class="pda_p42_inventarioLibre_ButtonNoGuardarInline">
								<label id="p42_lbl_noGuardar" class="etiquetaCampo"><spring:message code="pda_p42_inventarioLibre.noGuardar" /></label>
								<form:checkbox path="chkNoGuardar" />
							</div>				
							<div class="pda_p42_inventarioLibre_ButtonInline">
								<input id="pda_p42_btn_guardarTodo" type="submit" name="actionGrabarTodo" value='' class='botonSubmitGrabarTodo'/>
							</div>
							<div class="pda_p42_inventarioLibre_ButtonInline">
								<input id="pda_p42_btn_guardar" type="submit" name="actionGrabar" value='' class='botonSubmitGrabar'/>
							</div>
							<c:choose>
							<c:when test="${empty (pdaDatosInvLib.origenGISAE)}">
								<div class="pda_p42_inventarioLibre_ButtonInline">
									<input type="submit" name="actionLimpiarTodo" value='' class='botonSubmitLimpiarTodo'/>
								</div>
								<div class="pda_p42_inventarioLibre_ButtonInline">
									<input type="submit" name="actionLimpiar" value='' class='botonSubmitLimpiar'/>
								</div>
							</c:when>
							</c:choose>
							</div>	
					</c:if>
			
					<input type="hidden" id="posicion" name="posicion" value="${pdaDatosInvLib.posicion}">
					<input type="hidden" id="referenciaActual" name="referenciaActual" value="${pdaDatosInvLib.codArticulo}">
					<input type="hidden" id="pda_p42_fld_stockActual" name="pda_p42_fld_stockActual" value="${pdaDatosInvLib.stockActual}">
					<input type="hidden" id="pda_p42_fld_diferencia" name="pda_p42_fld_diferencia" value="${pdaDatosInvLib.diferencia}">
					<input type="hidden" id="pda_p42_fld_mmc" name="pda_p42_fld_mmc" value="${pdaDatosInvLib.mmc}">
					<input type="hidden" id="pda_p42_fld_camaraBandeja_tmp" name="pda_p42_fld_camaraBandeja_tmp" value="${pdaDatosInvLib.camaraBandeja}">
					<input type="hidden" id="pda_p42_fld_camaraStock_tmp" name="pda_p42_fld_camaraStock_tmp" value="${pdaDatosInvLib.camaraStock}">
					<input type="hidden" id="pda_p42_fld_salaBandeja_tmp" name="pda_p42_fld_salaBandeja_tmp" value="${pdaDatosInvLib.salaBandeja}">
					<input type="hidden" id="pda_p42_fld_salaStock_tmp" name="pda_p42_fld_salaStock_tmp" value="${pdaDatosInvLib.salaStock}">
					<input type="hidden" id="pda_p42_fld_kgs" name="pda_p42_fld_kgs" value="${pdaDatosInvLib.kgs}">
				</c:when>						
			</c:choose>	
			<input type="hidden" id="pda_p42_fld_codArtCab" name="pda_p42_fld_codArtCab" value="">
			<input type="hidden" id="pda_p42_fld_seccion" name="pda_p42_fld_seccion" value="${pdaDatosCab.seccion}">
			<input type="hidden" id="actionLogin" name="actionLogin" value="">
			<input type="hidden" id="actionNuevo" name="actionNuevo" value="">
			<input type="hidden" id="actionSeccion" name="actionSeccion" value="">
			<input type="hidden" id="actionVolver" name="actionVolver" value="">
			<input type="hidden" id="origenGISAE" name="origenGISAE" value="${pdaDatosInvLib.origenGISAE}"/>
			<input type="hidden" id="origenContinuar" name="origenContinuar" value="${origenContinuar}"/>

			</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>