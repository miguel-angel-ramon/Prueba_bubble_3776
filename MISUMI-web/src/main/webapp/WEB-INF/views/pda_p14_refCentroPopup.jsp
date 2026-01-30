<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p14_refCentroPopup.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pda_p14RefCentroPopUp.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="pdaP14refCentroPopup.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPaginaResaltado" onfocus="javascript:controlVentana(event);">
		<form:form method="post" action="pdaP14refCentroPopup.do" commandName="pdaDatosPopupReferencia" >
			<div id="pda_p14_AreaPopupPedir" onfocus="javascript:controlVentana(event);">
				<div class="barraTitulo" id="pda_p14_MotNoActivaDivTitle">
					<div id="pda_p14_div_TituloMotNoActiva" onfocus="javascript:controlVentana(event);">
						<span id="pda_p14_TituloMotNoActiva" class="tituloSeccion"><spring:message code="pda_p14_refCentroPopup.tituloMotNoActiva" /></span>
					</div>	
					<div id="pda_p14_botonCerrar">
						<c:choose>
							<c:when test="${pdaDatosPopupReferencia.procede eq 'datosRef'}">
								<a class="barraTituloCerrar" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupReferencia.codArt}">
									<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
								</a>
							</c:when>
							<c:when test="${pdaDatosPopupReferencia.procede eq 'segPedidos'}">
								<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupReferencia.codArt}">
									<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
								</a>
							</c:when>
							<c:when test="${pdaDatosPopupReferencia.procede eq 'pdaP115PrehuecosLineal'}">
								<c:choose>
									<c:when test="${origen eq 'PH'}">
										<a class="barraTituloCerrar" href="./pdaP115PrehuecosLineal.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}">
											<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
										</a>
									</c:when>
									<c:otherwise>
										<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&origen=${origen}">
											<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
										</a>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${pdaDatosPopupReferencia.procede eq 'pdaP115PrehuecosLineal'}">
								<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&origen=${origen}">
									<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
								</a>
							</c:when>
							<c:otherwise>
								<a class="barraTituloCerrar" href="./pdaP15MovStocks.do?codArt=${pdaDatosPopupReferencia.codArt}">
									<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p14_refCentroPopup.cerrar" />" class="barraTituloIconoCerrar">
								</a>						
							</c:otherwise>
						</c:choose>
					</div>	
				</div>
				<c:choose>
					<c:when test="${pdaDatosPopupReferencia.flgNoActivo eq 'S'}">
						<div id="pda_p14_AreaPopupPedirGeneral">
							<div id="pda_p14_AreaMotivosGeneral">			
								<div id="pda_p14_AreaMotivosGeneralTabla" class="tablaJqgrid" onfocus="javascript:controlVentana(event);">
									<table id="pda_p14_AreaMotivosGeneralTablaEstructura" cellspacing="0" cellpadding="0" border="0">
										<thead class="tablaCabecera">
										    <tr>
										        <th class="pda_p14_AreaMotivosGeneralTablaTh"></th>
										        <th class="pda_p14_AreaMotivosGeneralTablaTh"></th>
										    </tr>
										</thead>
										<tbody class="tablaContent">
											<c:forEach items="${pdaDatosPopupReferencia.pagNoActivo.rows}" var="PdaMotivo" varStatus="status">
												<tr> 							
										    		<td id="pda_p14_AreaMotivosGeneralTdId" class="pda_p14_AreaMotivosGeneralTablaTd">${PdaMotivo.posicion}</td>
										        	<c:choose>
														<c:when test="${PdaMotivo.tipoMensaje eq 'SF'}">
															<td id="pda_p14_AreaMotivosGeneralTdDesc" class="pda_p14_AreaMotivosGeneralTablaTd">
															<div class="pda_p14_MotivoTextosStyle"  onfocus="javascript:controlVentana(event);">
															${PdaMotivo.textoMotivo.texto1} ${PdaMotivo.textoMotivo.texto2}
															</div>
															</td>
														</c:when>
														<c:when test="${PdaMotivo.tipoMensaje eq 'NS'}">
															<td id="pda_p14_AreaMotivosGeneralTdDesc" class="pda_p14_AreaMotivosGeneralTablaTd">
															<div class="pda_p14_MotivoTextosStyle"  onfocus="javascript:controlVentana(event);">
																<span class="pda_p14_MotivoMapaHoySPedirS">${PdaMotivo.textoMotivo.texto1}</span>
																${PdaMotivo.textoMotivo.texto2}
															</div>
															
															</td>
														</c:when>
														<c:when test="${PdaMotivo.tipoMensaje eq 'WS'}">
															<td id="pda_p14_AreaMotivosGeneralTdDesc">
																<div id="pda_p14_MotivoTextosStyle" class="pda_p14_MotivosTxt"  onfocus="javascript:controlVentana(event);">
																	<span class="pda_p14_MotivoTexto1">${PdaMotivo.textoMotivo.texto1}</span>
																	<span class="pda_p14_MotivoTexto2"> ${PdaMotivo.textoMotivo.texto2}</span>
																</div>
															</td>
														</c:when>
													</c:choose>
										    	</tr>
											</c:forEach>
										</tbody>
									</table>
									<table class="tablaPaginacion" cellspacing="0" cellpadding="0">
										<tbody>
											<tr>
												<td id="pagerP14Motivos_left" align="left">
													<table>
														<tbody>
															<tr>
																<td id="first_pagerp14Motivos" align="right" style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagNoActivo.page eq '1'}">
																			<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=firstNoAct">
																				<img id="pda_p14_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td id="pagerp14Motivos_center" align="center" class="paginacionCentro">
													<table class="paginacion" cellspacing="0" cellpadding="0" >
														<tbody>
															<tr>
																<td id="prev_pagerp14Motivos" align="left" style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagNoActivo.page eq '1'}">
																			<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=prevNoAct">
																				<img id="pda_p14_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
																<td id="next_pagerp14Motivos" align="right" style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagNoActivo.page eq pdaDatosPopupReferencia.pagNoActivo.total}">
																			<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=nextNoAct">
																				<img id="pda_p14_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
																
															</tr>
														</tbody>
													</table>
												</td>
												<td id="pagerp14Motivos_right" align="right" >
													<table>
														<tbody>
															<tr>
																<td id="last_pagerp14Motivos" align="left" style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagNoActivo.page eq pdaDatosPopupReferencia.pagNoActivo.total}">
																			<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=lastNoAct">
																				<img id="pda_p14_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>					
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${pdaDatosPopupReferencia.flgMMC eq 'S'}">
						<div class="barraTitulo" id="pda_p14_mmcDivTitle" onfocus="javascript:controlVentana(event);">
							<div id="pda_p14_div_TituloMotivosMMC">
								<span id="pda_p14_TituloMotivosMMC" class="tituloSeccion"><spring:message code="pda_p14_refCentroPopup.tituloMMC" /></span>
							</div>	
						</div>
					 	<div id="pda_p14_AreaPopupPedirMMC">
							<div id="pda_p14_AreaMotivosMMC">			
								<div id="pda_p14_AreaMotivosMMCTabla" class="tablaJqgrid" onfocus="javascript:controlVentana(event);">
									<table id="pda_p14_AreaMotivosMMCTablaEstructura" cellspacing="0" cellpadding="0" border="0">
										<thead class="tablaCabecera">
										    <tr>
										        <th class="pda_p14_AreaMotivosMMCTablaTh"></th>
										        <th class="pda_p14_AreaMotivosMMCTablaTh"></th>
										    </tr>
										</thead>
										<tbody class="tablaContent">
											<c:forEach items="${pdaDatosPopupReferencia.pagMMC.rows}" var="PdaMotivo" varStatus="status">
												<tr> 							
										    		<td id="pda_p14_AreaMotivosMMCTdId" class="pda_p14_AreaMotivosMMCTablaTd">${PdaMotivo.posicion}</td>
										        	<c:choose>
														<c:when test="${PdaMotivo.tipoMensaje eq 'SF'}">
															<td id="pda_p14_AreaMotivosMMCTdDesc" class="pda_p14_AreaMotivosMMCTablaTd">
															<div class="pda_p14_MotivoTextosStyle" onfocus="javascript:controlVentana(event);">
															${PdaMotivo.textoMotivo.texto1} ${PdaMotivo.textoMotivo.texto2}
															</div>															
															</td>
														</c:when>
														<c:when test="${PdaMotivo.tipoMensaje eq 'NS'}">
															<td id="pda_p14_AreaMotivosMMCTdDesc" class="pda_p14_AreaMotivosMMCTablaTd">
															<div class="pda_p14_MotivoTextosStyle" onfocus="javascript:controlVentana(event);">
															<span class="pda_p14_MotivoMapaHoySPedirS">${PdaMotivo.textoMotivo.texto1}</span> ${PdaMotivo.textoMotivo.texto2}
															</div>
															</td>
														</c:when>
														<c:when test="${PdaMotivo.tipoMensaje eq 'WS'}">
															<td id="pda_p14_AreaMotivosMMCTdDesc">
															<div class=pda_p14_MotivoTextosStyle onfocus="javascript:controlVentana(event);">
															<span class="pda_p14_MotivoTexto1">${PdaMotivo.textoMotivo.texto1}</span>
															<span class="pda_p14_MotivoTexto2"> ${PdaMotivo.textoMotivo.texto2}</span>
															</div>
															</td>
														</c:when>
													</c:choose>
										    	</tr>
											</c:forEach>
										</tbody>
									</table>
									<table class="tablaPaginacion" cellspacing="0" cellpadding="0" border="0">
										<tbody>
											<tr>
												<td id="pagerP14MotivosMMC_left" align="left">
													<table>
														<tbody>
															<tr>
																<td id="first_pagerp14MotivosMMC" align="right"  style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagMMC.page eq '1'}">
																			<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=firstMMC">
																				<img id="pda_p14_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td id="pagerp14MotivosMMC_center" align="center" class="paginacionCentro">
													<table class="paginacion" cellspacing="0" cellpadding="0" border="0">
														<tbody>
															<tr>
																<td id="prev_pagerp14MotivosMMC" align="left"  style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagMMC.page eq '1'}">
																			<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=prevMMC">
																				<img id="pda_p14_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
																<td id="next_pagerp14MotivosMMC" align="right"  style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagMMC.page eq pdaDatosPopupReferencia.pagMMC.total}">
																			<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=nextMMC">
																				<img id="pda_p14_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
																
															</tr>
														</tbody>
													</table>
												</td>
												<td id="pagerp14MotivosMMC_right" align="right">
													<table>
														<tbody>
															<tr>
																<td id="last_pagerp14MotivosMMC" align="left"  style="padding-top: 0px; padding-bottom: 0px;"  onfocus="javascript:controlVentana(event);">
																	<c:choose>
																		<c:when test="${pdaDatosPopupReferencia.pagMMC.page eq pdaDatosPopupReferencia.pagMMC.total}">
																			<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																		</c:when>
																		<c:otherwise>
																			<a href="./pdaP14Paginar.do?codArt=${pdaDatosPopupReferencia.codArt}&procede=${pdaDatosPopupReferencia.procede}&pgNoAct=${pdaDatosPopupReferencia.pagNoActivo.page}&pgTotNoAct=${pdaDatosPopupReferencia.pagNoActivo.total}&pgMMC=${pdaDatosPopupReferencia.pagMMC.page}&pgTotMMC=${pdaDatosPopupReferencia.pagMMC.total}&botPag=lastMMC">
																				<img id="pda_p14_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>  
						</div> 
					</c:when>
				</c:choose>
				<form:hidden path="codArt" id="pda_p14_fld_codArt" />
				<form:hidden path="procede" id="pda_p14_fld_procede" />
				<c:choose>
					<c:when test="${pdaDatosPopupReferencia.tratamientoVegalsa}">
						<span class="pda_p14_MotivoTexto1"><spring:message code="pda_p14_refCentroPopup.mensajeFechaMMCNoActivoVegalsa" />&nbsp;${pdaDatosPopupReferencia.fechaMmcStr}</span>
						<c:if test="${pdaDatosPopupReferencia.soloReparto eq 'S'}">
							&nbsp;<span class="pda_p14_MotivoTexto1"><spring:message code="pda_p14_refCentroPopup.mensajeSoloRepartoNoActivoVegalsa" /></span>
						</c:if>
						<br>
						<c:if test="${not empty pdaDatosPopupReferencia.mapaReferencia}">
							<span class="pda_p14_MotivoTexto1"><spring:message code="pda_p14_refCentroPopup.mensajeMapaVegalsa" />&nbsp;${pdaDatosPopupReferencia.mapaReferencia}</span>
						</c:if>
					</c:when>
				</c:choose>
			</div>
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>