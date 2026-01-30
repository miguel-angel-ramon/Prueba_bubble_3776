<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/includes/pda_p04_headerReducido.jsp">
    <jsp:param name="cssFile" value="pda_p104_listaProveedores.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda_p104listaProveedores.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="S"></jsp:param>
    <jsp:param name="actionRef" value="pdaP104ListaProveedores.do"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">

			<div id="pda_p104_listaProveedores_contenido">
				<form:form id="pdaP104ListaProveedores" method="post" action="pdaP104ListaProveedores.do" modelAttribute="pdaSeleccionProveedor">
					<div id="pda_p104_listaProveedores_bloqueError">
						<p class="pda_p104_error">${mensajeError}</p>
					</div>
					<div id="pda_p104_tipoDevolucion_tituloLink">
						<input type="hidden" id="tipoDevolucion" value="${devolucionCabecera.tipoDevolucion}"/>
						<input type="hidden" id="devolucion" name="devolucion" value="${devolucionCabecera.devolucion}"/>
						<input type="hidden" id="proveedor" name="proveedor" value=""/>
						<input type="hidden" id="descProveedor" name="descProveedor" value=""/>
						<input type="hidden" id="tituloCabecera" name="tituloCabecera" value="${devolucionCabecera.titulo1}"/>
						<a href="#" id="pdaP104Titulo">${devolucionCabecera.titulo1}</a>
					</div>
					<div id="pda_p104_relleno_titulo" class="pda_p104_relleno_titulo">
						<span id="pda_p104_lbl_titulo_relleno" class="etiquetaCampoNegrita">&nbsp;</span>
					</div>
					<div id="pda_p104_listaBultos_titulo" class="pda_p104_titulo_bulto">
						<label id="pda_p104_lbl_titulo_bulto" class="etiquetaCampoNegrita"><spring:message code="pda_p104_listaProvBultos.titulo" /></label>
					</div>
					<div id="pda_p104_listaProv_titulo" class="pda_p104_titulo_descripcion">
 						<label id="pda_p104_lbl_titulo_prov" class="etiquetaCampoNegrita"><spring:message code="pda_p104_listaProveedores.titulo" /></label>
					</div>

					<div id="pda_p104_listaProveedoresSeleccion_bloque">

						<table id="pda_104_listaProveedores">
							<tbody class="tablaContent">
 								<c:choose>
			  						<c:when test="${existeProveedor}">
										<c:set var='finalizarDev' value='S'/>
 										<c:forEach items="${pagProveedor.rows}" var="proveedor">	
											<tr>
												<td>
													<c:choose>
														<c:when test="${proveedor.finalizado eq 'SI'}">
															<div id="imgTick">
																<img src="./misumi/images/tick_82x82.png?version=${misumiVersion}" style="width:12px;height:12px;">
															</div>
														</c:when>
														<c:otherwise>
															<c:set var='finalizarDev' value='N'/>
															<div id="pda_p104_relleno_prov" class="pda_p104_relleno_prov">
																<span id="pda_p104_lbl_titulo_relleno" class="etiquetaCampoNegrita">&nbsp;</span>
															</div>
														</c:otherwise>
													</c:choose>
												</td>
										    	<td id="pda_104_listaProveedoresCod">
													<input type="hidden" id="descProv${proveedor.codigo}" name="descProv" value="${proveedor.descripcion}">
										    		<form:radiobutton path="codProveedor" value="${proveedor.codigo}" onclick="javascript:events_p104_cargarPaginaBultosProv(${proveedor.codigo});"/>
										    	</td>
										        <td id="pda_p104_listaProveedoresDesc" class="input200 pda_p104_titulo_descripcion">
													<c:choose>
														<c:when test="${devolucionCabecera.motivo == 'Retirada de calidad'}">
															<a href="./pdaP63RealizarDevolucionOrdenDeRetirada.do?devolucion=${devolucionCabecera.devolucion}&origenPantalla=pdaP104&selectProv=${proveedor.descripcion}" class="inputSinBorde">${proveedor.descripcion}</a>
														</c:when>
														<c:otherwise>
															<a href="./pdaP62RealizarDevolucionFinCampania.do?devolucion=${devolucionCabecera.devolucion}&origenPantalla=pdaP104&selectProv=${proveedor.descripcion}" class="inputSinBorde">${proveedor.descripcion}</a>
														</c:otherwise>
													</c:choose>
										       </td>
										    </tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<div id="pda_p60_noHayProveedoresError" class="mens_error">
											<spring:message code="pda_p104_noHayProveedores.error"/>
										</div>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>

						<input type="hidden" id="accion" name="accion" value="">
						<input type="hidden" id="selProv" name="selProv" value="${proveedor.descripcion}">
						<input type="hidden" id="mostrarFinDev" name="mostrarFinDev" value="${mostrarFinDev}">

					</div>

				</form:form>
			</div>
			
			<c:choose>
				<c:when test="${pagProveedor.total>0}">
					<div id="pda_p104_listaProveedores_areaFlechas">
						<div id="bloqueFlechas">
							<div id="pagPrimera" class="paginacion">
								<c:choose>
									<c:when test="${pagProveedor.page eq '1'}">
										<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP104Paginar.do?pgProv=${pagProveedor.page}&pgTotProv=${pagProveedor.total}&botPag=firstProv&devolucion=${devolucionCabecera.devolucion}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p104_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>
							</div>
							<div id="pagAnt" class="paginacion">
								<c:choose>
									<c:when test="${pagProveedor.page eq '1'}">
										<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP104Paginar.do?pgProv=${pagProveedor.page}&pgTotProv=${pagProveedor.total}&botPag=prevProv&devolucion=${devolucionCabecera.devolucion}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p104_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="numeroPagina" class="paginacion">
								${pagProveedor.page}/${pagProveedor.total}
							</div>
							<div id="pagSig" class="paginacion">
								<c:choose>
									<c:when test="${pagProveedor.page eq pagProveedor.total}">
										<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP104Paginar.do?pgProv=${pagProveedor.page}&pgTotProv=${pagProveedor.total}&botPag=nextProv&devolucion=${devolucionCabecera.devolucion}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p104_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="pagUltima" class="paginacion">
								<c:choose>
									<c:when test="${pagProveedor.page eq pagProveedor.total}">
										<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP104Paginar.do?pgProv=${pagProveedor.page}&pgTotProv=${pagProveedor.total}&botPag=lastProv&devolucion=${devolucionCabecera.devolucion}&tituloDevol=${devolucionCabecera.titulo1}">
											<img id="pda_p104_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<c:if test="${mostrarFinDev eq 'SI'}">
								<div id="bloqueBoton">
									<div id="pda_p104_btn_fin">
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