<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p95_descripcionDetalle.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p95DescripcionDetalle.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP95DescripcionDetalle.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">							
				<c:choose>
					<c:when test="${not empty reposicionDatosTalla}">
						<div id="pda_p95_descripcionDetalle_descripcionReposicionDatosTalla">
							<div>
								<label id="pda_p95_descripcionDetalle_lbl_referencia" class="p95_info">
									<spring:message code="pda_p95_descripcionDetalle.referencia"/> 
								</label>					
								<label>${reposicionDatosTalla.codArt}</label>
							</div>
							<div>
								<label id="pda_p95_descripcionDetalle_lbl_descripcion" class="p95_info">
									<spring:message code="pda_p95_descripcionDetalle.descripcion"/>
								</label>
								<label>${descripcion}</label>
							</div>
							<!-- 
							<div>
								<label id="pda_p95_descripcionDetalle_lbl_proveedor" class="p95_info">
									<spring:message code="pda_p95_descripcionDetalle.proveedor"/>
								</label>
								<label>${reposicionDatosTalla.proveedor}</label>							
							</div>
							 -->
							 <div>
								<label id="pda_p95_descripcionDetalle_lbl_estructura" class="p95_info">
									<spring:message code="pda_p95_descripcionDetalle.estructura"/>
								</label>
								<label>${reposicionDatosTalla.estrEroski}</label>	
							</div>
							 <c:choose>
							 	<c:when test="${reposicionDatosTalla.proveedor eq 'SONAE'}">
									<div>
										<label id="pda_p95_descripcionDetalle_lbl_sonae" class="p95_info">
											<spring:message code="pda_p95_descripcionDetalle.sonae"/>
										</label>	
										<label>${reposicionDatosTalla.refSonae}</label>
									</div>	
							 	</c:when>
							 	<c:otherwise>
							 		<div>
										<label id="pda_p95_descripcionDetalle_lbl_modelo" class="p95_info">
											<spring:message code="pda_p95_descripcionDetalle.modelo"/>						
										</label>
										<label>${reposicionDatosTalla.modeloEroski}</label>
									</div>
							 	</c:otherwise>
							 </c:choose>
						</div>					
					</c:when>
					<c:otherwise>
						<div id="pda_p95_descripcionDetalle_descripcion">		
							<div id="pda_p95_descripcionDetalle_descripcion">						
								<p id="pda_p95_descripcionDetalle_lbl_descripcion">${descripcion}</p>
							</div>	
						</div>							
					</c:otherwise>
				</c:choose>
				
			<form id="pda_p95_descripcionDetalle_form" action="${origen}" method="get">
				<input type="hidden" id="seccion" name="seccion" value="${seccion}">		
				<input type="hidden" id="pagina" name="pagina" value="${paginaActual}">		
				<input type="hidden" id="pgSubList" name="pgSubList" value="${pgSubList}">						
			</form>						
		</div>