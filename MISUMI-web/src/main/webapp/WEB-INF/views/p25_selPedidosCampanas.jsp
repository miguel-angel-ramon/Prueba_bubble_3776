<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>

<!-- ----- Contenido de la JSP ----- -->
	
		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p01_header.welcome" /></a></li>
						<li><spring:message code="p25_selPedidosCampanas.selPedidosCampanas" /></li>
					</ul>
				</div>
			</div>
		</div>	
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="selPedidosCampanas">
				<div id="p25_selOpcionPedidosCampanas">
						<p class="p25_leyendaSelPedidosCampana"><spring:message code="p25_selPedidosCampanas.leyendaSelPedidosCampana" /></p>
				</div>
				<div id="p25_selOpcionPedidos" onclick="window.location = './misPedidos.do'">
					<div id="p25_selOpcionPedidosIcono">
						<img src="./misumi/images/selPedidos.png?version=${misumiVersion}" style="vertical-align: middle;" class="p25_imagenSelPedidosCampanas"/>
					</div>
					<div id="p25_selOpcionPedidosLeyenda">
						<p class="p25_leyendaSelOpcion"><spring:message code="p25_selPedidosCampanas.leyendaSelOpcionPedidos" /></p>
					</div>
				</div>
				<c:if test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '16_CAMPANA'))}">
					<div id="p25_selOpcionCampanas" onclick="window.location = './segCampanas.do'">					
						<div id="p25_selOpcionCampanasIcono">
							<img src="./misumi/images/selCampanas.png?version=${misumiVersion}" style="vertical-align: middle;" class="p25_imagenSelPedidosCampanas"/>
						</div>
						<div id="p25_selOpcionCampanasLeyenda">
							<p class="p25_leyendaSelOpcion"><spring:message code="p25_selPedidosCampanas.leyendaSelOpcionCampanas" /></p>
						</div>
					</div>	
				</c:if>
			</div>
		</div>		
	
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>