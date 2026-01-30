<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.dialogextend.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p50NuevoPedidoAdicional.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/HistoricoVentaUltimoMes.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/Motivo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRefPedidos.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/DiasServicio.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p51NuevoPedidoOF.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/model/NuevoPedidoOferta.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerOfertaPa.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VSicFPromociones.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p52NuevoPedidoREF.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/model/PedidoAdicionalCompleto.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VOferta.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/utils/calendario.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p56AyudaPopup.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p57AyudaPopup1.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p58AyudaPopup2.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p59StockPopup.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p66AyudaTextilPopUp.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/model/HistoricoUnidadesVenta.js?version=${misumiVersion}" type="text/javascript"></script>
	
		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p50_nuevoPedidoAdicional.welcome" /></a></li>
						<li><a href="./pedidoAdicional.do" ><spring:message code="p50_nuevoPedidoAdicional.pedidoAdicional" /></a></li>
						<li><spring:message code="p50_nuevoPedidoAdicional.nuevoPedidoAdicional" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="p50_AreaPestanas">
				<div id="p50_pestanas">
					<input type=hidden id="p50_pestanaOrigen" value="${pestanaOrigen}"></input>
					<input type=hidden id="p50_codArea" value="${codArea}"></input>
					<input type=hidden id="p50_codSeccion" value="${codSeccion}"></input>
					<input type=hidden id="p50_codCategoria" value="${codCategoria}"></input>
					<input type=hidden id="p50_referencia" value="${referencia}"></input>
					<!--  Variables para el calendario con días de servicio -->
					<input type="hidden" id="codCentroCalendario" value=""></input>
					<input type="hidden" id="codArticuloCalendario" value=""></input>
					<input type="hidden" id="identificadorCalendario" value=""></input>
					<input type="hidden" id="identificadorSIACalendario" value=""></input>
					<input type="hidden" id="clasePedidoCalendario" value=""></input>
					<input type="hidden" id="recargarParametrosCalendario" value=""></input>
					<input type="hidden" id="cargadoDSCalendario" value=""></input>
					<input type="hidden" id="esFresco" value=""></input>
					<input type="hidden" id="uuidCalendario" value=""></input>
				    <ul>
				        <li><a href="#p50_pestanaReferencia"><spring:message code="p50_pedidoAdicional.porReferencia" /></a><input type=hidden id="p50_pestanaReferenciaCargada"></input></li>
				        <div id="p50_pestanas_mensaje"><span><spring:message code="p50_nuevoPedidoAdicional.mensajePiladasOferta" /></span></div>
				    </ul>
				    <div id="p50_pestanaReferencia">
				    	<!--p52_nuevoPedidoReferencia.jsp-->
				        <%@ include file="/WEB-INF/views/p52_nuevoPedidoReferencia.jsp" %>
				    </div>
				</div>
			</div>
		</div>	
		<%@ include file="/WEB-INF/views/p56_ayudaPopup.jsp" %>	
		<%@ include file="/WEB-INF/views/p59_stockPopup.jsp" %>	
		<%@ include file="/WEB-INF/views/p66_ayudaTextilPopUp.jsp" %>	
		<%@ include file="/WEB-INF/views/p17_referenciasCentroPopupVentas.jsp"%>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>