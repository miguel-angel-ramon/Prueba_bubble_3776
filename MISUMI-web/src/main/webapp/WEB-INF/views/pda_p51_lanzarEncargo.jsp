<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
   <jsp:param value="pda_p51_lanzarEncargo.css?version=${misumiVersion}"name="cssFile"></jsp:param>
   <jsp:param value="S" name="flechaMenu"></jsp:param>
   <jsp:param value="pda_P51LanzarEncargo.js?version=${misumiVersion}" name="jsFile"></jsp:param>
   <jsp:param value="pdaP51LanzarEncargos.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- Página que se muestra al clicar el link lanzar encargo pet 55700. Al clicar datos referencia y buscar
	 una referencia, en el caso de poder realizarse un encargo aparece un link que permite realizar encargos y
	 trae a esta pantalla. -->
<div id="contenidoPagina">
	<form:form method="post" action="pdaP51LanzarEncargos.do?origenGISAE=${origenGISAE}" commandName="pedidoAdicionalCompletoPda">		 	
		 <div id="pda_p51_lanzarEncargo_bloque1">
			<label id="pda_p51_lanzarEncargoTitulo" class="etiquetaCampo tituloP51LanzarEncargo"><spring:message code="pda_p51_lanzarEncargo.titulo" /></label>
		</div>
		<div id="pda_p51_lanzarEncargo_bloque2">
			<input id="pda_p51_fld_descripcionRef" name="descArtConCodigo" type="text" disabled="disabled" value="${pedidoAdicionalCompletoPda.codArtCaprabo}-${pedidoAdicionalCompletoPda.descArt}"/>
		</div>
		 <div id="pda_p51_lanzarEncargo_bloque3">

			<div class="pda_p51_lanzarEncargo_bloque3_subBloque_reducido">
				<div class="pda_p51_lanzarEncargo_lbl">
					<label class="etiquetaCampo"><spring:message code="pda_p51_lanzarEncargo.fechaEntrega" /></label>
				</div>
				<div class="pda_p51_lanzarEncargo_flecha">
					<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
				</div>
				<div id="pda_p51_lanzarEncargoFechaEntrega" class="pda_p51_lanzarEncargo_info">
					<label id="pda_p51_lbl_lanzarEncargoFechaEntrega" class="etiquetaCampo">${pedidoAdicionalCompletoPda.strFechaIni}</label>
				</div>
			</div>

			<div class="pda_p51_lanzarEncargo_bloque3_subBloque_reducido">
				<div class="pda_p51_lanzarEncargo_lbl">
					<label class="etiquetaCampo pda_p51_lanzarEncargoTitulo"><spring:message code="pda_p51_lanzarEncargo.UC" /></label>
				</div>
				<div class="pda_p51_lanzarEncargo_flecha">
					<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
				</div>
				<div id="pda_p51_lanzarEncargoUc" class="pda_p51_lanzarEncargo_info input">
					<label id="pda_p51_lbl_lanzarEncargoUc" class="etiquetaCampo">${fn:replace(pedidoAdicionalCompletoPda.uniCajas,'.',',')}</label>
				</div>
			</div>
			<div class="pda_p51_lanzarEncargo_bloque3_subBloque">
				<div class="pda_p51_lanzarEncargo_lbl">
					<label class="etiquetaCampo pda_p51_lanzarEncargoTitulo"><spring:message code="pda_p51_lanzarEncargo.cantidad" /></label>
				</div>
				<div class="pda_p51_lanzarEncargo_flecha">
					<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
				</div>
				<div class="pda_p51_lanzarEncargo_info input">
					<!-- <input id="unidadesPedidasShow" class="input90" value="${fn:replace(pedidoAdicionalCompletoPda.cantidad1,'.',',')}" type="text"/>-->
					<c:set var="cantidad1"><fmt:formatNumber type="number"  maxFractionDigits="0" value="${pedidoAdicionalCompletoPda.cantidad1}"/></c:set>
					<input id="unidadesPedidasShow" class="input90" value="${cantidad1}" type="number"/>
				</div>
			</div>
			<div class="pda_p51_lanzarEncargo_bloque3_subBloque">
				<div class="pda_p51_lanzarEncargo_lbl">
					<label class="etiquetaCampo pda_p51_lanzarEncargoTitulo"><spring:message code="pda_p51_lanzarEncargo.accion" /></label>
				</div>
				<div class="pda_p51_lanzarEncargo_flecha">
					<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
				</div>
				<div class="pda_p51_lanzarEncargo_info input">
					<form:select path="tratamiento"  id="pda_p51_cmb_tratamiento" class="input90" >
						<form:option value="A"><spring:message code="pda_p51_lanzarEncargo.aniadir"/></form:option>
						<form:option value="S"><spring:message code="pda_p51_lanzarEncargo.sustituir"/></form:option>
  						<form:option value="F"><spring:message code="pda_p51_lanzarEncargo.fijaModificar"/></form:option>
					</form:select>
				</div>
			</div>
			<div class="pda_p51_lanzarEncargo_bloque3_subBloque_reducido">
				<div class="pda_p51_lanzarEncargo_lbl">
					<label class="etiquetaCampo pda_p51_lanzarEncargoTitulo"><spring:message code="pda_p51_lanzarEncargo.pdteRecibir" /></label>
				</div>
				<div class="pda_p51_lanzarEncargo_flecha">
					<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
				</div>
				<div class="pda_p51_lanzarEncargo_info pda_p51_lanzarEncargoText">
					<label class="etiquetaCampo">${pedidoAdicionalCompletoPda.cantHoy}/${pedidoAdicionalCompletoPda.cantFutura}
					</label>
				</div>
			</div>
			<div class="pda_p51_lanzarEncargo_bloque3_subBloque_reducido">
				<div class="pda_p51_lanzarEncargo_lbl">
					<label class="etiquetaCampo pda_p51_lanzarEncargoTitulo"><spring:message code="pda_p51_lanzarEncargo.proxDiaPedido" /></label>
				</div>
				<div class="pda_p51_lanzarEncargo_flecha">
					<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
				</div>
				<div class="pda_p51_lanzarEncargo_info pda_p51_lanzarEncargoText">
					<label class="etiquetaCampo">${pedidoAdicionalCompletoPda.fechaProximaEntrega}
					</label>
				</div>
			</div>
			<c:if test="${pedidoAdicionalCompletoPda.showExcluirAndCajas}">	
				<div class="pda_p51_lanzarEncargo_bloque3_subBloque">
					<div class="pda_p51_lanzarEncargo_lbl">
						<label class="etiquetaCampo pda_p51_lanzarEncargoTitulo"><spring:message code="pda_p51_lanzarEncargo.excluir" /></label>
					</div>
					<div class="pda_p51_lanzarEncargo_flecha">
						<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
					</div>
					<div class="pda_p51_lanzarEncargo_info cajas">				
						<c:choose> 
							<c:when test="${pedidoAdicionalCompletoPda.excluir == 'S'}">
								<div id="cajaExcluir1" class="cajaSi cajaVerde">						
									<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.SI" /></label>
								</div>
								<div id="cajaExcluir2" class="cajaNo">							
									<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.NO" /></label>
								</div>					
							</c:when>							
							<c:otherwise>
								<div id="cajaExcluir1" class="cajaSi">					
									<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.SI" /></label>
								</div>
								<div id="cajaExcluir2" class="cajaNo cajaVerde">
									<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.NO" /></label>
								</div>
							</c:otherwise>
						</c:choose>							
					</div>
				</div>
				<c:choose>
					<c:when test="${!user.centro.esCentroCaprabo && !user.centro.esCentroCapraboEspecial}">
						<div class="pda_p51_lanzarEncargo_bloque3_subBloque">
							<div class="pda_p51_lanzarEncargo_lbl">
								<label class="etiquetaCampo pda_p51_lanzarEncargoTitulo"><spring:message code="pda_p51_lanzarEncargo.cajas" /></label>
							</div>			
							<div class="pda_p51_lanzarEncargo_flecha">
								<img src="./misumi/images/arrow_right_25.gif?version=${misumiVersion}">
							</div>
							<div class="pda_p51_lanzarEncargo_info cajas">	
								<c:choose> 
									<c:when test="${(pedidoAdicionalCompletoPda.flgAvisoEncargoLote != 'H') && (pedidoAdicionalCompletoPda.flgAvisoEncargoLote != 'L')}">
										<c:choose> 
											<c:when test="${pedidoAdicionalCompletoPda.cajas == 'S'}">
												<div id="cajaCajas1" class="cajaSi cajaVerde">							
													<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.SI" /></label>
												</div>
												<div id="cajaCajas2" class="cajaNo">						
													<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.NO" /></label>
												</div>					
											</c:when>							
											<c:otherwise>
												<div id="cajaCajas1" class="cajaSi">							
													<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.SI" /></label>
												</div>
												<div id="cajaCajas2" class="cajaNo cajaVerde">						
													<label class="linkCaja"><spring:message code="pda_p51_lanzarEncargo.NO" /></label>
												</div>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose> 
											<c:when test="${(pedidoAdicionalCompletoPda.flgAvisoEncargoLote == 'L')}">
												<div id="cajaCajas1Block" class="cajaSiBlock cajaVerde">							
													<label class="linkCajaBlock"><spring:message code="pda_p51_lanzarEncargo.SI" /></label>
												</div>
												<div id="cajaCajasBlock" class="cajaNoBlock">						
													<label class="linkCajaBlock"><spring:message code="pda_p51_lanzarEncargo.NO" /></label>
												</div>		
											</c:when>
											<c:otherwise>
												<div id="cajaCajas1Block" class="cajaSiBlock">							
													<label class="linkCajaBlock"><spring:message code="pda_p51_lanzarEncargo.SI" /></label>
												</div>
												<div id="cajaCajas2Block" class="cajaNoBlock cajaVerde">						
													<label class="linkCajaBlock"><spring:message code="pda_p51_lanzarEncargo.NO" /></label>
												</div>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>	
							</div>
						</div>
					</c:when>
				</c:choose>	
			</c:if>
			<c:if test="${pedidoAdicionalCompletoPda.fechaNoDisponible}">	
				<div id="pda_p51_lanzarEncargo_msg">
					<spring:message code="pda_p51_lanzarEncargo.fechaNoDisponibleParte1" />
					${pedidoAdicionalCompletoPda.unidadesPedidas}&nbsp;
					<spring:message code="pda_p51_lanzarEncargo.fechaNoDisponibleParte2" />
				</div>
			</c:if>
		</div>
		<div id="pda_p51_lanzarEncargo_bloque4">
			<input id="lanzarEncargoBtn" class="boton" value="<spring:message code="pda_p51_lanzarEncargo.boton"/>" type="submit">
		</div>
		<div id="hiddenInputs">
			<form:input id="unidadesPedidas" path="cantidad1" class="input90" value="${pedidoAdicionalCompletoPda.cantidad1}" type="text"/>
			<form:input id="excluir" path="excluir" class="input90" value="${pedidoAdicionalCompletoPda.excluir}" type="text"/>
			<form:input id="cajas" path="cajas" class="input90" value="${pedidoAdicionalCompletoPda.cajas}" type="text"/>
		</div>
	</form:form>
</div>
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>