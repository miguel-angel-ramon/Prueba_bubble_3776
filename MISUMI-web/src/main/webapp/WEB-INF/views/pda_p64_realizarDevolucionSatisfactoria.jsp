<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p64_realizarDevolucionSatisfactoria.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p64RealizarDevolucionSatisfactoria.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="N" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP64RealizarDevolucionSatisfactoria.do" name="actionRef"></jsp:param>
</jsp:include>

	<!-- ----- Contenido de la JSP ----- -->
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="pda_p64_devolucionSatisfactoria_titulo">
				<div id="pda_p64_titulo" href="#">${devolucion.titulo1}</div>
			</div>
			<div id="pda_p64_devolucionSatisfactoria_contenido">
				<div id="pda_p64_devolucionSatisfactoria_msg">
					<div id="pda_p64_devolucionSatisfactoria_header" class="colorMsg">
						<spring:message code="pda_p64_devolucionSatisfactoria.msg1" />
					</div>
				</div>
				<form id="pda_p64_devolucionSatisfactoria_form" action="${actionP64Volver}" method="post">
				<input type="hidden" id="devolucion" name="devolucion" value="${devolucion.devolucion}">
				<input type="hidden" id="accion" name="accion" value="">
				<input type="hidden" id="origenPantalla" name="origenPantalla" value="pdaP64">
				</form>
				
			</div>
		</div>