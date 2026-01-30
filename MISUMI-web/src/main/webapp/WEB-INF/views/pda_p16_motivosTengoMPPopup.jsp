<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="actionRefValue" value="pdaP16MotivosTengoMPPopup.do" />
<c:if test="${procede eq 'pdaP115PrehuecosLineal'}">
    <c:set var="actionRefValue" value="pdaP115PrehuecosLineal.do" />
</c:if>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p16_motivosTengoMPPopup.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="${actionRefValue}" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPaginaResaltado">
		<form:form method="post" action="pdaP16MotivosTengoMPPopup.do" commandName="pdaDatosPopupMotivosTengoMP">
			<div id="pda_p16_AreaPopupMotivosTengoMP">
				<div class="barraTitulo" id="pda_p16_MotivosDivTitle">
					<c:choose>
						<c:when test="${pdaDatosPopupMotivosTengoMP.flgTipoListadoMotivos eq 'M'}">
							<div id="pda_p16_div_TituloMotivos">
								<span id="pda_p16_TituloMotivos" class="tituloTabla"><spring:message code="pda_p16_motivosTengoMPPopup.tituloMotivosMucho" /></span>
							</div>	
						</c:when>
						<c:otherwise>
							<div id="pda_p16_div_TituloMotivos">
								<span id="pda_p16_TituloMotivos" class="tituloTabla"><spring:message code="pda_p16_motivosTengoMPPopup.tituloMotivosPoco" /></span>
							</div>
						</c:otherwise>
					</c:choose>		
					<div id="pda_p16_botonCerrar">
					<c:choose>
						<c:when test="${pdaDatosPopupMotivosTengoMP.procede eq 'datosRef'}">
							<a class="barraTituloCerrar" href="./pdaP12DatosReferencia.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}">
								<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p16_motivosTengoMPPopup.cerrar" />" class="barraTituloIconoCerrar">
							</a>
						</c:when>
						<c:when test="${pdaDatosPopupMotivosTengoMP.procede eq 'segPedidos'}">
							<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}">
								<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p16_motivosTengoMPPopup.cerrar" />" class="barraTituloIconoCerrar">
							</a>
						</c:when>
						<c:when test="${pdaDatosPopupMotivosTengoMP.procede eq 'pdaP115PrehuecosLineal'}">
							<a class="barraTituloCerrar" href="./pdaP13SegPedidos.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}&procede=pdaP115PrehuecosLineal">
								<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p16_motivosTengoMPPopup.cerrar" />" class="barraTituloIconoCerrar">
							</a>
						</c:when>
						<c:otherwise>
							<a class="barraTituloCerrar" href="./pdaP15MovStocks.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}&procede="${pdaDatosPopupMotivosTengoMP.procede}>
								<img src="./misumi/images/dialog_cancel.gif?version=${misumiVersion}" title="<spring:message code="pda_p16_motivosTengoMPPopup.cerrar" />" class="barraTituloIconoCerrar">
							</a>						
						</c:otherwise>
					</c:choose>
					</div>	
				</div>
				<div id="pda_p16_AreaPopupMotivos">
					<div id="pda_p16_AreaMotivos">			
						<div id="pda_p16_AreaMotivosTabla" class="tablaJqgrid">
							<table id="pda_p16_AreaMotivosTablaEstructura" cellspacing="0" cellpadding="0" border="0">
								<thead class="tablaCabecera">
								    <tr>
								        <th class="pda_p16_AreaMotivosTablaTh"></th>
								        <th class="pda_p16_AreaMotivosTablaTh"></th>
								    </tr>
								</thead>
								<tbody class="tablaContent">
									<c:forEach items="${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.rows}" var="PdaMotivoTengoMP" varStatus="status">
										<tr> 							
								    		<td id="pda_p16_AreaMotivosTdId" class="pda_p16_AreaMotivosTablaTd">${PdaMotivoTengoMP.posicion}</td>
											<td id="pda_p16_AreaMotivosTdDesc" class="pda_p16_AreaMotivosTablaTd">${PdaMotivoTengoMP.textoMotivo}</td>
								    	</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="tablaPaginacion" cellspacing="0" cellpadding="0" border="0">
								<tbody>
									<tr>
										<td id="pagerP16Motivos_left" align="left">
											<table>
												<tbody>
													<tr>
														<td id="first_pagerp16Motivos" align="right">
															<c:choose>
																<c:when test="${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page eq '1'}">
																	<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP16Paginar.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}&procede=${pdaDatosPopupMotivosTengoMP.procede}&muchoPoco=${pdaDatosPopupMotivosTengoMP.flgTipoListadoMotivos}&pgMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page}&pgTotMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.total}&botPag=firstMotTMP">
																		<img src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
																	</a>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td id="pagerp16Motivos_center" align="center" class="paginacionCentro">
											<table class="paginacion" cellspacing="0" cellpadding="0" border="0">
												<tbody>
													<tr>
														<td id="prev_pagerp16Motivos" align="left">
															<c:choose>
																<c:when test="${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page eq '1'}">
																	<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP16Paginar.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}&procede=${pdaDatosPopupMotivosTengoMP.procede}&muchoPoco=${pdaDatosPopupMotivosTengoMP.flgTipoListadoMotivos}&pgMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page}&pgTotMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.total}&botPag=prevMotTMP">
																		<img src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
																	</a>
																</c:otherwise>
															</c:choose>
														</td>
														<td id="next_pagerp16Motivos" align="right">
															<c:choose>
																<c:when test="${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page eq pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.total}">
																	<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP16Paginar.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}&procede=${pdaDatosPopupMotivosTengoMP.procede}&muchoPoco=${pdaDatosPopupMotivosTengoMP.flgTipoListadoMotivos}&pgMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page}&pgTotMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.total}&botPag=nextMotTMP">
																		<img src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
																	</a>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td id="pagerp16Motivos_right" align="right">
											<table>
												<tbody>
													<tr>
														<td id="last_pagerp16Motivos" align="left">
															<c:choose>
																<c:when test="${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page eq pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.total}">
																	<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
																</c:when>
																<c:otherwise>
																	<a href="./pdaP16Paginar.do?codArt=${pdaDatosPopupMotivosTengoMP.codArt}&procede=${pdaDatosPopupMotivosTengoMP.procede}&muchoPoco=${pdaDatosPopupMotivosTengoMP.flgTipoListadoMotivos}&pgMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.page}&pgTotMot=${pdaDatosPopupMotivosTengoMP.pagMotivosTengoMP.total}&botPag=lastMotTMP">
																		<img src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
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
			</div>
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>