<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p60_realizarDevolucion.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p60RealizarDevolucion.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP60RealizarDevolucion.do" name="actionRef"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">

			<div id="pda_p60_realizarDevolucion_titulo">
				<label id="pda_p60_lbl_titulo"><spring:message code="pda_p60_realizarDevolucion.titulo" /></label>
			</div>

			<div id="pda_p60_realizarDevolucion_contenido">
				<form:form method="post" action="#" modelAttribute="devolucion">			
					<div id="pda_p60_realizarDevolucion_areaLinks">
						<c:choose>
  							<c:when test="${existeDevolucion}">
								<c:forEach items="${pagDevolucion.rows}" var="devolucion">	
									<!-- Comprobar si el centro está parametrizado -->
									<c:choose>
										<c:when test = "${user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '27_PDA_DEVOLUCIONES_PROCEDIMIENTO')}">
											<c:choose>
												<c:when test="${devolucion.motivo == 'Retirada de calidad'}">
													<a href="./pdaP104ListaProveedores.do?devolucion=${devolucion.devolucion}&tipoDevolucion=OrdenRetirada" class="enlaceDevol">
												</c:when>
												<c:otherwise>
													<a href="./pdaP104ListaProveedores.do?devolucion=${devolucion.devolucion}&tipoDevolucion=FinCampana" class="enlaceDevol">
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${devolucion.motivo == 'Retirada de calidad'}">
													<a href="./pdaP63RealizarDevolucionOrdenDeRetirada.do?devolucion=${devolucion.devolucion}" class="enlaceDevol">
												</c:when>
												<c:otherwise>
													<a href="./pdaP62RealizarDevolucionFinCampania.do?devolucion=${devolucion.devolucion}" class="enlaceDevol">
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>									

   							 		<div class="bloqueRadioButtonYLink">
   							 			<div id="pda_p60_datosDevolucion">
   							 				<c:choose>
   							 					<c:when test="${devolucion.motivo eq 'Creada por el centro'}">
   							 						<span class="colorFechas"><spring:message code="pda_p61_realizarDevolucionCabecera.creadaPorCentro" /></span>
   							 					</c:when>
   							 					<c:otherwise>   							 					
   							 						<span class="colorFechas">(${devolucion.fechaDesdeStr}/${devolucion.fechaHastaStr})</span>
   							 					</c:otherwise>
   							 				</c:choose>
   							 				<br>
   							 				<span>${devolucion.titulo1}</span>
   							 			</div>
   							 			<c:choose>
											<c:when test="${devolucion.fechaDeDevolucionPasada eq true}">
		   							 			<div id="pda_p60_fueraFechasError" class="mens_errorFueraFechas">
													<spring:message code="pda_p60_realizarDevolucion.mensajeFueraFechas"/>
												</div>
											</c:when>
										</c:choose>	
   							 		</div>				

								</c:forEach>
							</c:when>
							<c:otherwise>
								<div id="pda_p60_noHayDevolucionesError" class="mens_error">
									<spring:message code="pda_p60_noHayDevoluciones.error"/>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</form:form>
			</div>
			<c:choose>
				<c:when test="${pagDevolucion.total>0}">
					<div id="pda_p60_realizarDevolucion_areaFlechas">
						<div id="bloqueFlechas">
							<div id="pagPrimera" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucion.page eq '1'}">
										<img src="./misumi/images/pager_first_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP60Paginar.do?codArtCab=${pdaDatosCab.codArtCab}&pgDev=${pagDevolucion.page}&pgTotDev=${pagDevolucion.total}&botPag=firstDev" class="enlaceDevol">
											<img id="pda_p60_lnk_first" src="./misumi/images/pager_first_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>
							</div>
							<div id="pagAnt" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucion.page eq '1'}">
										<img src="./misumi/images/pager_prev_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP60Paginar.do?codArtCab=${pdaDatosCab.codArtCab}&pgDev=${pagDevolucion.page}&pgTotDev=${pagDevolucion.total}&botPag=prevDev" class="enlaceDevol">
											<img id="pda_p60_lnk_prev" src="./misumi/images/pager_prev_24.gif?version=${misumiVersion}" class="botonPaginacion enlaceDevol">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="numeroPagina" class="paginacion">
								${pagDevolucion.page}/${pagDevolucion.total}
							</div>
							<div id="pagSig" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucion.page eq pagDevolucion.total}">
										<img src="./misumi/images/pager_next_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP60Paginar.do?codArtCab=${pdaDatosCab.codArtCab}&pgDev=${pagDevolucion.page}&pgTotDev=${pagDevolucion.total}&botPag=nextDev" class="enlaceDevol">
											<img id="pda_p60_lnk_next" src="./misumi/images/pager_next_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
							<div id="pagUltima" class="paginacion">
								<c:choose>
									<c:when test="${pagDevolucion.page eq pagDevolucion.total}">
										<img src="./misumi/images/pager_last_des_24.gif?version=${misumiVersion}" class="botonPaginacionDeshabilitado">
									</c:when>
									<c:otherwise>
										<a href="./pdaP60Paginar.do?codArtCab=${pdaDatosCab.codArtCab}&pgDev=${pagDevolucion.page}&pgTotDev=${pagDevolucion.total}&botPag=lastDev" class="enlaceDevol">
											<img id="pda_p60_lnk_last" src="./misumi/images/pager_last_24.gif?version=${misumiVersion}" class="botonPaginacion">
										</a>
									</c:otherwise>
								</c:choose>						
							</div>
						</div>
					</div>
				</c:when>
			</c:choose>
		</div>
		
		<!-- Popup de carga -->
		<div id="popupCargando" style="display:none; position:absolute;">
		    Cargando...
		</div>

<script type="text/javascript">
	window.onload = function () {
	    var links = document.getElementsByTagName("a");
	
	    for (var i = 0; i < links.length; i++) {
	        if (isElementWithClass(links[i], "enlaceDevol")) {
	            if (links[i].attachEvent) {
	                // Para IE6-8
	                links[i].attachEvent("onclick", mostrarPopupCargando);
	            } else {
	                // Navegadores modernos
	                links[i].addEventListener("click", mostrarPopupCargando, false);
	            }
	        }
	    }
	};

	function mostrarPopupCargando(e) {
	    var popup = document.getElementById("popupCargando");
	
		if (popup) {
	        popup.style.display = "block";
	    }
	}
	
	function isElementWithClass(elem, className) {
	    if (!elem || !elem.className){
			return false;
	    }
	    
	    var classes = elem.className.split(/\s+/);
	    for (var i = 0; i < classes.length; i++) {
	        if (classes[i] === className){
				return true;
	        }
	    }
	    return false;
	}
</script>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>