<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p27_facing.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p27Facing.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP21Sfm.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" action="pdaP21Sfm.do" commandName="pdaDatosSfmCap">
			<div id="pda_p27_facing_titulo">
				<label id="pda_p27_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p27_facing.titulo" /></label>
			</div>
			<div id="pda_p27_facing_bloqueMensajes">

				<c:choose>
				<c:when test="${pdaDatosSfmCap.lote}">
				
					<!-- Referencias que forman parte de un lote de textil -->
				
					
					<c:choose>
					<c:when test="${pdaDatosSfmCap.icono eq '1'}">
						<div id="pda_p27_facing_bloqueError">
							<div class="pda_p27_textil_lote_error">${pdaDatosSfmCap.errorIcono}</div>
						</div>
					</c:when>	
					<c:when test="${pdaDatosSfmCap.icono eq '3'}">
						<div id="pda_p27_facing_bloqueGuardado">
							<div class="pda_p27_textil_lote_guardado"><spring:message code="pda_p21_sfm.messageGuardado" /></div>
						</div>		
					</c:when>
					
					</c:choose>	
					
				</c:when>
				<c:otherwise>
				
					<!-- Referencias que NO forman parte de un lote de textil -->
				
					<c:choose>
					
					<c:when test="${pdaDatosSfmCap.icono eq '1'}">
						<div id="pda_p27_facing_bloqueError">
							<p class="pda_p27_error">${pdaDatosSfmCap.errorIcono}</p>
						</div>
					</c:when>	
					<c:when test="${pdaDatosSfmCap.icono eq '3'}">
						<div id="pda_p27_facing_bloqueGuardado">
							<p class="pda_p27_guardado"><spring:message code="pda_p21_sfm.messageGuardado" /></p>
						</div>		
					</c:when>
					</c:choose>	
					
				</c:otherwise>
				</c:choose>	
			</div>
					
					
			<c:choose>
			<c:when test="${pdaDatosSfmCap.lote}">
			
				<!-- Referencias que forman parte de un lote de textil -->
				
				<div id="pda_p27_textil_lote_facing_bloque1">
					<div id="pda_p27_textil_lote_facing_bloque1_campo">
						${pdaDatosSfmCap.descArt}
					</div>	
				</div>
				<div id="pda_p27_textil_lote_facing_bloque2">
					<div id="pda_p27_textil_lote_facing_bloque2_campo">
						${pdaDatosSfmCap.temporada}${pdaDatosSfmCap.anioColeccion} &nbsp ${pdaDatosSfmCap.codN2}/${pdaDatosSfmCap.codN3}/${pdaDatosSfmCap.codN4}/${pdaDatosSfmCap.codN5} &nbsp ${pdaDatosSfmCap.orden}
					</div>
				</div>
				<div id="pda_p27_textil_lote_facing_bloque3">
					<div id="pda_p27_textil_lote_facing_bloque3_campo">
						${pdaDatosSfmCap.modeloProveedor}
					</div>
				</div>
				
				<div id="pda_p27_textil_lote_facing_bloque4">
					<div id="pda_p27_textil_lote_facing_bloque4_pagAnt">
						<c:choose>
							<c:when test="${pdaDatosSfmCap.posicionTextil > 1}">
								<input type="submit" name="actionAntTextil" value='' class='botonSubmitTextilLoteAnt'/>
							</c:when>
							<c:otherwise>
								<input type="submit" name="actionAntTextil" value='' class='botonSubmitTextilLoteAntDes' disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</div>	
					<div id="pda_p27_textil_lote_facing_bloque4_pagNum">
						<div>${pdaDatosSfmCap.color}</div>
						<div><spring:message code="pda_p27_facing.paginacion" arguments="${pdaDatosSfmCap.posicionTextil},${pdaDatosSfmCap.totalTextil}" /></div>
					</div>	
					<div id="pda_p27_textil_lote_facing_bloque4_pagSig">
						<c:choose>
							<c:when test="${pdaDatosSfmCap.totalTextil > pdaDatosSfmCap.posicionTextil}">
								<input type="submit" name="actionSigTextil" value='' class='botonSubmitTextilLoteSig'/>
							</c:when>
							<c:otherwise>
								<input type="submit" name="actionSigTextil" value='' class='botonSubmitTextilLoteSigDes' disabled="disabled"/>
							</c:otherwise>
						</c:choose>
					</div>					
				</div>
				
				<div id="pda_p27_textil_lote_facing_bloque5">
					<c:choose>
						<c:when test="${pdaDatosSfmCap.activa}">
							<div id="pda_p27_textil_lote_facing_bloque5_1">
								<label id="pda_p27_textil_lote_facing_activo" class="pda_p27_textil_lote_activo"><spring:message code="pda_p27_facing.referenciaTextilLoteActivo" /></label>
							</div>	
							<div id="pda_p27_textil_lote_facing_bloque5_2">
								<div class="pda_p27_textil_lote_facing_bloque5_2_checkbox_text">
									<c:choose>
										<c:when test="${ pdaDatosSfmCap.facingOrig eq '0'}">
											<span class="pda_p27_textil_lote_mensaje_activacion"><spring:message code="pda_p27_facing.manianaInactivo" /></span>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="inactivarLote" id="pda_p27_textil_lote_inactivar" value="${pdaDatosSfmCap.inactivarLote}" />
											<a href="#" id="pda_p27_textil_lote_inactivar_enlace"><spring:message code="pda_p27_facing.referenciaTextilLoteInactivar" /></a>
										</c:otherwise>
									</c:choose>
								</div>
							</div>	
						</c:when>
						<c:when test="${!pdaDatosSfmCap.textilPedible}">
							<label id="pda_p27_lbl_facing" class="pda_p27_etiquetaCampoNegritaRojo"><spring:message code="pda_p27_facing.referenciaTextilNoPedible" /></label>
						</c:when>
						<c:otherwise>
							<div id="pda_p27_textil_lote_facing_bloque5_1">
							<label id="pda_p27_textil_lote_facing_no_activo" class="pda_p27_textil_lote_inactivo"><spring:message code="pda_p27_facing.referenciaTextilLoteInactivo" /></label>
							</div>
							<c:choose>
								<c:when test="${pdaDatosSfmCap.facingOrig ne '0'}">
									<div id="pda_p27_textil_lote_facing_bloque5_2">
									<div class="pda_p27_textil_lote_facing_bloque5_2_checkbox_text">
											<span class="pda_p27_textil_lote_mensaje_activacion"><spring:message code="pda_p27_facing.manianaActivo" /></span>
									</div>
									</div>
								</c:when>
		
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div id="pda_p27_textil_lote_facing_bloque4_5_6">
					<table id="pda_p27_textil_lote_facing_bloque4_5_6_tabla" border="1">
						<thead class="tablaCabecera">
						    <tr>
						        <th class="pda_p27_textil_lote_refer_th"><spring:message code="pda_p27_facing.refer" /></th>
						        <th class="pda_p27_textil_lote_talla_th"><spring:message code="pda_p27_facing.talla" /></th>
						        <th class="pda_p27_textil_lote_facing_th"><spring:message code="pda_p27_facing.facing" /></th>
						    </tr>
						</thead>
						<tbody class="tablaContent">

							<c:set var="posicionInicioTabla" value="${(pdaDatosSfmCap.posicionTextil-1) * 3}"/> 
							<c:set var="posicionFinalTabla" value="${(pdaDatosSfmCap.posicionTextil) * 3}"/> 
							<c:forEach var="articulo" items="${pdaDatosSfmCap.listaHijas}" varStatus="loopStatus">
								<c:if test="${loopStatus.index >= posicionInicioTabla && loopStatus.index < posicionFinalTabla}">
									<tr class="">
								    	<td id="pda_p27_textil_lote_refer" class="pda_p27_textil_lote_refer_td">
								    		${articulo.codArt}
								    	</td>
								        <td id="pda_p27_textil_lote_talla" class="pda_p27_textil_lote_talla_td">
								    		${articulo.talla}
								    	</td>
										<c:choose>
											<c:when test="${articulo.icono eq '1'}">
										        <td id="pda_p27_textil_lote_facing" class="pda_p27_textil_lote_facing_td pda_p27_textil_lote_facing_error_td">
													<input type="number" name="facing_textil_${loopStatus.index - posicionInicioTabla}" id="pda_p27_fld_facing_${loopStatus.index - posicionInicioTabla}" class="input50 inputEditable inputFacingResaltado" value="${articulo.facing}"/>
													<input id="pda_p27_fld_facing_${loopStatus.index - posicionInicioTabla}_tmp" type="hidden" value="${articulo.facingOrig}"/>
										    	</td>
											</c:when>
											<c:otherwise>
										        <td id="pda_p27_textil_lote_facing" class="pda_p27_textil_lote_facing_td">
										        	<c:choose>
										        		<c:when test="${pdaDatosSfmCap.flgSfmFijo eq 'B'}">
															<input name="facing_textil_${loopStatus.index - posicionInicioTabla}" id="pda_p27_fld_facing_${loopStatus.index - posicionInicioTabla}" class="input50" disabled="true" value="${articulo.facing}"/>
														</c:when>
														<c:otherwise>
															<c:choose> 	
													        	<c:when test="${pdaDatosSfmCap.inactivarLote eq true}">
													        		<input type="number" name="facing_textil_${loopStatus.index - posicionInicioTabla}" id="pda_p27_fld_facing_${loopStatus.index - posicionInicioTabla}" class="input50 inputEditable" value="0"/>
													        	</c:when>
													        	<c:otherwise>
													        		<input type="number" name="facing_textil_${loopStatus.index - posicionInicioTabla}" id="pda_p27_fld_facing_${loopStatus.index - posicionInicioTabla}" class="input50 inputEditable" value="${articulo.facing}"/>
													        	</c:otherwise>
												        	</c:choose>
														</c:otherwise>
													</c:choose>
													
													<input id="pda_p27_fld_facing_${loopStatus.index - posicionInicioTabla}_tmp" type="hidden" value="${articulo.facingOrig}"/>
										    	</td>
											</c:otherwise>
										</c:choose>	
								    </tr>
								</c:if>
							</c:forEach>

						</tbody>
					</table>
				</div>
				
			</c:when>
			<c:otherwise>
			
				<!-- Referencias que NO forman parte de un lote de textil -->
			
				<div id="pda_p27_facing_bloque1">
					<div id="pda_p27_facing_bloque1_campo">	
						<form:input path="descArtConCodigo" id="pda_p27_fld_descripcionRef" class="input225" disabled="true" type="text"/>
					</div>	
				</div>		
				<div id="pda_p27_facing_bloque3">
					<div id="pda_p27_facing_Estado">&nbsp;
						<!--<div id="pda_p27_facing_EstadoLabel">
							<label id="pda_p27_lbl_Estado" class="etiquetaCampoVerticalNegrita"><spring:message code="pda_p27_facing.Estado" /></label>
						</div>	
						<div id="pda_p27_facing_EstadoIcono">&nbsp;
							<c:choose>
								<c:when test="${pdaDatosSfmCap.icono eq '1'}">
									<img src="./misumi/images/dialog-error-24.gif?version=${misumiVersion}" class="pda_p27_imagen_estado">
								</c:when>
								<c:when test="${pdaDatosSfmCap.icono eq '2'}">
									<img src="./misumi/images/modificado.gif?version=${misumiVersion}" class="pda_p27_imagen_estado">
								</c:when>
								<c:when test="${pdaDatosSfmCap.icono eq '3'}">
									<img src="./misumi/images/floppy.gif?version=${misumiVersion}" class="pda_p27_imagen_estado">
								</c:when>
							</c:choose>
						</div>	-->
					</div>
				</div>
				<div id="pda_p27_facing_bloque4_5_provisional">
					<div id="pda_p27_facing_bloque4_provisional">
						<c:choose>
							<c:when test="${pdaDatosSfmCap.textil}">
								<div id="pda_p27_textilPedible">
									<c:if test="${pdaDatosSfmCap.textilPedible}">
										<label id="pda_p27_lbl_facing" class="pda_p27_etiquetaCampoNegrita"><spring:message code="pda_p27_facing.referenciaTextilPedible" /></label>
									</c:if>
									<c:if test="${!pdaDatosSfmCap.textilPedible}">
										<label id="pda_p27_lbl_facing" class="pda_p27_etiquetaCampoNegritaRojo"><spring:message code="pda_p27_facing.referenciaTextilNoPedible" /></label>
									</c:if>
								</div>
							</c:when>
							<c:otherwise>
							
							<c:choose>
							<c:when test="${pdaDatosSfmCap.tipoGama eq 'Voluntaria'}">
								<div id="pda_p27_tipoGamaconCC">
									<div class="pda_p27_lbl_50">
										<label id="pda_p27_lbl_tipoGama" class="etiquetaCampoNegrita"><spring:message code="pda_p27_facing.gama" /></label>
									</div>
									<div class="pda_p27_input_70">
										<form:input path="tipoGama" id="pda_p27_fld_tipoGama" class="input70" disabled="true" type="text"/>
									</div>
								</div>
								<div id="pda_p27_cc">
									<label id="pda_p27_lbl_cc" class="etiquetaCampoNegrita"><spring:message code="pda_p27_facing.cc" /></label>
									<form:input path="cc" id="pda_p27_fld_cc" class="input40" disabled="true" type="text"/>
								</div>
							</c:when>
							<c:otherwise>
								<div id="pda_p27_tipoGama">
									<div class="pda_p27_lbl_80">
										<label id="pda_p27_lbl_tipoGama" class="etiquetaCampoNegrita"><spring:message code="pda_p27_facing.gama" /></label>
									</div>
									<div class="pda_p27_input_70">
										<form:input path="tipoGama" id="pda_p27_fld_tipoGama" class="input70" disabled="true" type="text"/>
									</div>
								</div>
							</c:otherwise>
							</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
					<div id="pda_p27_facing_bloque5_provisional">
						<div id="pda_p27_facing_bloque5_1_provisional">
							<c:choose>
								<c:when test="${pdaDatosSfmCap.facingPrevio ne 0}">
									<div id="pda_p27_facingPrevio">
										<div class="pda_p27_lbl_80">
											<label id="pda_p27_lbl_facing" class="etiquetaCampo"><spring:message code="pda_p27_facing.facingPrevio" /></label>
										</div>
										<div class="pda_p27_input_70">
											<form:input path="facingPrevio" id="pda_p27_fld_facingPrevio" class="input70" disabled="true" type="text"/>
										</div>
									</div>
								</c:when>
							</c:choose>
						</div>
						<div id="pda_p27_facing_bloque5_2_provisional">
							<div id="pda_p27_facing_bloque4_5_6" class="pda_p27_facing_bloque4_5_6_con_multiplicador <c:if test='${pdaDatosSfmCap.esFFPP}'>pda_p27_facing_bloque4_5_6_sin_multiplicador</c:if>" >
								<div id="pda_p27_facing_bloque4">
									<div id="pda_p27_facing">
										<div class="pda_p27_lbl_80">
											<label id="pda_p27_lbl_capacidad" class="etiquetaCampo"><spring:message code="pda_p27_facing.facing" /></label>
										</div>
										<div class="pda_p27_input_30">
										<c:choose>
											<c:when test="${pdaDatosSfmCap.flgSfmFijo eq 'B'}">
													<form:input path="facing" id="pda_p27_fld_facing" class="input30" disabled="true" type="text"/>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${pdaDatosSfmCap.icono eq '1'}">
														<form:input path="facing" id="pda_p27_fld_facing" class="input30 inputResaltado"  type="number"/>
													</c:when>
													<c:otherwise>
														<form:input path="facing" id="pda_p27_fld_facing" class="input30 inputEditable"  type="number"/>
													</c:otherwise>
												</c:choose>	
											</c:otherwise>
										</c:choose>
										</div>
									</div>
								</div>
								<c:if test="${not pdaDatosSfmCap.esFFPP}">
									<div id="pda_p27_facing_con_multiplicador">
										<div class="pda_p27_lbl_80">
											<label id="pda_p27_lbl_multiplicador" class="etiquetaCampo"><spring:message code="pda_p27_facing.multiplicador" /></label>
										</div>
										<div class="pda_p27_input_30">
											<form:input path="multiplicadorFacing" id="pda_p27_fld_multiplicador" class="input30" disabled="true" type="text"/>
										</div>
									</div>
								</c:if>
							</div>
							<c:if test="${not pdaDatosSfmCap.esFFPP}">
								<div id="pda_p27_facing_bloque_con_multiplicador_llave">
										<img src="./misumi/images/llave-50-gris.gif?version=${misumiVersion}" class="pda_p27_imagen_llave">
								</div>
							</c:if>
							<div id="pda_p27_facing_bloque_con_multiplicador_Imagen">
								<div id="pda_p27_imagenMinimaComercial_con_multiplicador">
									<label id="pda_p27_lbl_imagenMinimaComercial" class="etiquetaCampo"><spring:message code="pda_p27_facing.imagenMinimaComercial" /></label>
									<form:input path="imagenComercialMin" id="pda_p27_fld_imagenMinimaComercial_con_multiplicador"  disabled="true"  type="text"/>
								</div>
							</div>
						</div>
					</div>
				</div>

		</c:otherwise>
		</c:choose>	
			
			
			
		 <c:if test="${user.perfil != 3}">
			<div id="pda_p27_filterButtons">
				<input type="submit" id="pda_p27_btn_save"  class="botonSubmitGrabar" name="actionSave" value=""/>

			</div>
		</c:if>
		
			<input type="hidden" name="posicion" value="${pdaDatosSfmCap.posicion}">
			<input type="hidden" name="referenciaActual" value="${pdaDatosSfmCap.codArt}">
			<input type="hidden" id="pda_p27_fld_fac_origen" name="origenConsulta" value="${pdaDatosCab.origenConsulta}">
			<input type="hidden" name="origenGISAE" value="${pdaDatosCab.origenGISAE}">
			<input type="hidden" name="procede" value="${procede}">
			</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>