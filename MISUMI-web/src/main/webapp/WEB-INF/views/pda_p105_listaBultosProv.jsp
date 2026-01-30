<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/includes/pda_p04_headerReducido.jsp">
    <jsp:param name="cssFile" value="pda_p105_listaBultosProv.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda_p105listaBultosProv.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP105ListaBultosProv.do"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			
			<div id="pda_p105_listaBultosProv_contenido">
				<form:form id="pdaP105ListaBultosProv" method="post" action="pdaP105ListaBultosProv.do" modelAttribute="pdaSeleccionBulto">
				
					<div id="pda_p105_listaBultosProv_bloqueError">
						<p class="pda_p105_error">${mensajeError}</p>
					</div>
					<div id="pda_p105_tipoDevolucion_tituloLink">
						<input type="hidden" id="tipoDevolucion" value="${devolucionCabecera.tipoDevolucion}"/>
						<input type="hidden" id="devolucion" name="devolucion" value="${devolucionCabecera.devolucion}"/>
						<input type="hidden" id="proveedor" name="proveedor" value=""/>
						<input type="hidden" id="descProveedor" name="descProveedor" value="${descProveedor}"/>
						<input type="hidden" id="tituloCabecera" name="tituloCabecera" value="${devolucionCabecera.titulo1}"/>
						<a href="#" id="pdaP105Titulo">${devolucionCabecera.titulo1}</a>
					</div>
					<div id="pda_p105_listaProv_titulo" class="pda_p105_titulo_descripcion">
 						<label id="pda_p105_lbl_titulo_prov" class="etiquetaCampoNegrita"><spring:message code="pda_p105_listaBultosProv.titulo" /></label>
						<label id="pda_p01_lbl_desc_prov" class="etiquetaCampoNegrita">${descProveedor}</label>
					</div>

					<div id="pda_p105_listaProveedoresSeleccion_bloque">
						<table id="pda_105_listaProveedores">
							<tbody class="tablaContent">
			  					<c:if test="${existeBulto}">
									<c:set var='finalizarDev' value='S'/>
 									<c:forEach items="${pagBultos.rows}" var="bulto">	
										<tr>
									        <td id="pda_p105_listaBultosDesc" class="pda_p105_titulo_descripcion">
												<c:choose>
													<c:when test="${devolucionCabecera.motivo == 'Retirada de calidad'}">
														<a href="./pdaP63RealizarDevolucionOrdenDeRetirada.do?devolucion=${devolucionCabecera.devolucion}&origenPantalla=pdaP105&selectProv=${descProveedor}&bultoSeleccionado=${bulto.bulto}" class="inputSinBorde">${bulto.bulto}</a>
													</c:when>
													<c:otherwise>
														<a href="./pdaP62RealizarDevolucionFinCampania.do?devolucion=${devolucionCabecera.devolucion}&origenPantalla=pdaP105&selectProv=${descProveedor}&bultoSeleccionado=${bulto.bulto}" class="inputSinBorde">${bulto.bulto}</a>
													</c:otherwise>
												</c:choose>
												
												<input id="borrarBulto${bulto.bulto}" class="botonSubmitBorrarBulto" onclick="javascript:events_p105_borrarBulto(${bulto.bulto});" value=''/>
																								
												<c:choose>
													<c:when test="${bulto.estadoCerrado eq 'S'}">
														<input id="abrirCaja${bulto.bulto}" class="botonSubmitAbrirCaja" onclick="javascript:events_p105_abrirCaja(${bulto.bulto});" value=''/>
														<img src="./misumi/images/tick_82x82.png?version=${misumiVersion}" style="width:12px;height:12px;">
													</c:when>
													<c:otherwise>
														<c:set var='finalizarDev' value='N'/>
													</c:otherwise>															
												</c:choose>

									        </td>
									    </tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>

						<input type="hidden" id="mostrarFinDev" name="mostrarFinDev" value="${mostrarFinDev}">
						<input type="hidden" id="bulto" name="bulto" value="">
						<input type="hidden" id="accion" name="accion" value="">
						<input type="hidden" id="selProv" name="selProv" value="${descProveedor}">
					</div>

				</form:form>
			</div>

			
			<c:choose>
				<c:when test="${pagBultos.total>0}">
					<div id="pda_p105_listaBultosProv_areaFlechas">
						<div id="bloqueFlechas">
							<div id="pagPrimera" class="paginacion">
								<c:choose>
									<c:when test="${pagBultos.page eq '1'}">
										<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP105Paginar.do?pgBulto=${pagBultos.page}&pgTotBulto=${pagBultos.total}&botPag=firstBulto&devolucion=${devolucionCabecera.devolucion}&descProveedor=${descProveedor}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p105_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>
							</div>
							<div id="pagAnt" class="paginacion">
								<c:choose>
									<c:when test="${pagBultos.page eq '1'}">
										<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP105Paginar.do?pgBulto=${pagBultos.page}&pgTotBulto=${pagBultos.total}&botPag=prevBulto&devolucion=${devolucionCabecera.devolucion}&descProveedor=${descProveedor}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p105_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="numeroPagina" class="paginacion">
								${pagBultos.page}/${pagBultos.total}
							</div>
							<div id="pagSig" class="paginacion">
								<c:choose>
									<c:when test="${pagBultos.page eq pagBultos.total}">
										<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP105Paginar.do?pgBulto=${pagBultos.page}&pgTotBulto=${pagBultos.total}&botPag=nextBulto&devolucion=${devolucionCabecera.devolucion}&descProveedor=${descProveedor}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p105_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="pagUltima" class="paginacion">
								<c:choose>
									<c:when test="${pagBultos.page eq pagBultos.total}">
										<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP105Paginar.do?pgBulto=${pagBultos.page}&pgTotBulto=${pagBultos.total}&botPag=lastBulto&devolucion=${devolucionCabecera.devolucion}&descProveedor=${descProveedor}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p105_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<c:if test="${mostrarFinDev eq 'SI'}">
								<div id="bloqueBoton">
									<div id="pda_p105_btn_fin">
										<div class="botonSubmitGrabar"></div>
									</div>					
								</div>
							</c:if>
						</div>
					</div>
				</c:when>
			</c:choose>

		</div>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>