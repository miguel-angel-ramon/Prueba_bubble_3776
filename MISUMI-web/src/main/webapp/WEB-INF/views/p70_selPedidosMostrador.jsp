<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>

<!-- ----- Contenido de la JSP ----- -->
	
		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p01_header.welcome" /></a></li>
						<li><spring:message code="p70_selPedidosMostrador.selPedidosMostrador" /></li>
					</ul>
				</div>
			</div>
		</div>	
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="selPedidosMostrador">
				<div id="p70_selOpcionPedidosMostrador">
						<p class="p70_leyendaSelPedidosMostrador"><spring:message code="p70_selPedidosMostrador.leyendaSelPedidosMostrador" /></p>
				</div>
				<div id="p70_selOpcionPedidos" onclick="events_enlaceDetalladoPedido();">
					<div id="p70_selOpcionPedidosIcono">
						<img src="./misumi/images/selDetallado.png?version=${misumiVersion}" style="vertical-align: middle;" class="p70_imagenSelPedidosMostrador"/>
					</div>
					<div id="p70_selOpcionPedidosLeyenda">
						<p class="p70_leyendaSelOpcion"><spring:message code="p70_selPedidosMostrador.leyendaSelOpcionPedidos" /></p>
					</div>
				</div>
				<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '52_DETALLADO_MOSTRADOR'))}">
					<c:choose>
						<c:when test="${user.centro.estadoEstructurasMostrador != null && user.centro.estadoEstructurasMostrador.codMensajeEstado == 0}">
							<div id="p70_selOpcionMostrador" onclick="window.location = './detalladoMostrador.do?center=${user.centro.codCentro}'">		
								<div id="p70_selOpcionMostradorIcono">
									<img src="./misumi/images/selMostrador.png?version=${misumiVersion}" style="vertical-align: middle;" class="p70_imagenSelPedidosMostrador"/>
								</div>
								<div id="p70_selOpcionMostradorLeyenda">
									<p class="p70_leyendaSelOpcion"><spring:message code="p70_selPedidosMostrador.leyendaSelOpcionMostrador" /></p>									
								</div>
							</div>	
						</c:when>
						<c:otherwise>
							<div id="p70_selOpcionMostrador" style="cursor: not-allowed;">		
								<div id="p70_selOpcionMostradorIcono">
									<img src="./misumi/images/selMostrador.png?version=${misumiVersion}" style="vertical-align: middle; cursor: not-allowed;" class="p70_imagenSelPedidosMostrador"/>
								</div>
								<div id="p70_selOpcionMostradorLeyenda">
									<p class="p70_leyendaSelOpcion" style="cursor: not-allowed;">
										<spring:message code="p70_selPedidosMostrador.leyendaSelOpcionMostrador" />
										&nbsp;
										<c:choose>
											<c:when test="${user.centro.estadoEstructurasMostrador != null && user.centro.estadoEstructurasMostrador.codMensajeEstado == 1}">			
												<strong style="color:red; font-weight: bold;"><spring:message code="p70_selPedidosMostrador.aviso.1" /></strong>
											</c:when>
											<c:otherwise>
												<strong style="color:red; font-weight: bold;"><spring:message code="p70_selPedidosMostrador.aviso.2" /></strong>								
											</c:otherwise>
										 </c:choose>						
									</p>									
								</div>
							</div>	
						</c:otherwise>
					 </c:choose>	
				</c:if>
			</div>
		</div>		
	
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>