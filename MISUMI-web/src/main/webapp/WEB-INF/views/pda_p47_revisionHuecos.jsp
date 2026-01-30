<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p47_revisionHuecos.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p47RevisionHuecos.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP47RevisionHuecos.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		
		<div id="contenidoPagina">
		
			<form:form method="post" id="pdaP47RevisionHuecos" action="pdaP47RevisionHuecos.do" commandName="pdaRevisionHuecos">
			<c:choose>
				<c:when test="${pdaRevisionHuecos.mostrarContenido}">
				
				
					<div id="pda_p47_revisionHuecos_titulo">
					<c:choose>
						<c:when test="${not empty (pdaRevisionHuecos.area)}">
						<label id="pda_p47_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p47_revisionHuecos.titulo" arguments="${pdaRevisionHuecos.area}" /></label>
						</c:when>
				</c:choose>	
					</div>
				

					<div id="pda_p47_revisionHuecos_bloqueMensajes">
						<c:choose>
						<c:when test="${not empty (pdaRevisionHuecos.guardadoCorrecto) && pdaRevisionHuecos.guardadoCorrecto == 'KO'}">
							<div id="pda_p47_revisionHuecos_bloqueError">
								<p class="pda_p47_error">${pdaRevisionHuecos.descError}</p>
							</div>
						</c:when>	
						<c:when test="${not empty (pdaRevisionHuecos.borradoCorrecto) && pdaRevisionHuecos.borradoCorrecto == 'KO'}">
							<div id="pda_p47_revisionHuecos_bloqueError">
								<p class="pda_p47_error">SE HA PRODUCIDO EL SIGUIENTE ERROR AL BORRAR LA REF ${pdaRevisionHuecos.codArt}: ${pdaRevisionHuecos.descError}</p>
							</div>
						</c:when>	
						<c:when test="${not empty (pdaRevisionHuecos.gisaeCorrecto) && pdaRevisionHuecos.gisaeCorrecto == 'KO'}">
							<div id="pda_p47_revisionHuecos_bloqueError">
								<p class="pda_p47_error"><spring:message code="pda_p47_revisionHuecos.errorGISAE" arguments="${pdaRevisionHuecos.descError}" /></p>
							</div>
						</c:when>
						<c:when test="${not empty (pdaRevisionHuecos.guardadoCorrecto) && pdaRevisionHuecos.guardadoCorrecto == 'OK'}">
							<div id="pda_p47_revisionHuecos_bloqueGuardado">
								<p class="pda_p47_guardado"><spring:message code="pda_p47_revisionHuecos.messageGuardado" /></p>
							</div>		
						</c:when>
						<c:when test="${not empty (pdaRevisionHuecos.borradoCorrecto) && pdaRevisionHuecos.borradoCorrecto == 'OK'}">
							<div id="pda_p47_revisionHuecos_bloqueBorrado">
								<p class="pda_p47_guardado"><spring:message code="pda_p47_revisionHuecos.messageBorrado" arguments="${pdaRevisionHuecos.refBorrada}" /></p>
							</div>		
						</c:when>
						<c:when test="${not empty (pdaRevisionHuecos.gisaeCorrecto) && pdaRevisionHuecos.gisaeCorrecto == 'OK'}">
							<div id="pda_p47_revisionHuecos_bloqueBorrado">
								<p class="pda_p47_guardado"><spring:message code="pda_p47_revisionHuecos.envioGISAE" /></p>
							</div>		
						</c:when>
						</c:choose>	
					</div>
					<div id="pda_p47_filterButtons">
							<c:choose>
						<c:when test="${not empty (pdaRevisionHuecos.area) &&  pdaRevisionHuecos.gisaeCorrecto != 'OK' }">
					<input type="submit" id="pda_p47_btn_save"  class="botonSubmitGrabar" name="actionSave" value='' title="<spring:message code="pda_p47_ventaAnticipada.guardarTitulo" />"/>
							<input type="submit" id="pda_p47_btn_reset"  class='botonSubmitLimpiar' name="actionReset" value='' title="<spring:message code="pda_p47_ventaAnticipada.limpiarTitulo" />"/>
				</c:when>
				</c:choose>	
							
							
					</div>
					
				</c:when>
				<c:when test="${pdaRevisionHuecos.mostrarConf}">
					<div id="pda_p47_revisionHuecos_bloqueMensajes">
						<p class="pda_p47_error"><spring:message code="pda_p47_revisionHuecos.confirmacion" /></p>
					</div>
					<div id="pda_p47_filterButtons">
							
							
							<input type="submit" id="pda_p47_btn_save"  class="botonBotonera controlReturn" name="actionConfirmarNo" value='NO' title="NO"/>
							<input type="submit" id="pda_p47_btn_reset"  class="botonBotonera controlReturn" name="actionConfirmarYes" value='SI' title="SI"/>
					</div>
				</c:when>
			</c:choose>
			<form:input path="mostrarConf" type="hidden" />
			<form:input path="mostrarContenido" type="hidden" />
			<form:input path="area" type="hidden" />
			<form:input path="codArt" type="hidden" />
			<form:input path="actionLogin" type="hidden" />
			<form:input path="actionVolver" type="hidden" />
			</form:form>
			
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>