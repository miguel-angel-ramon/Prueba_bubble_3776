<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp">
    <jsp:param name="cssFile" value="pda/packingList/pda_p111_errorConsultasPorReferencia.css?version=${misumiVersion}"></jsp:param>
    <jsp:param name="jsFile" value="pda/packingList/pda_p111ErrorConsultasPorReferencia.js?version=${misumiVersion}"></jsp:param>
    <jsp:param name="flechaMenu" value="N"></jsp:param>
    <jsp:param name="actionRef" value="pdaWelcome.do"></jsp:param>
</jsp:include>

<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">

			<div id="pda_p111_errorPackingList_titulo">
				<label id="pda_p111_errorPackingList_lbl_titulo"><spring:message code="pda_p109_consultasPorReferencia.titulo" /></label>
			</div>

			<div id="pda_p111_errorPackingList_contenido">
				<form:form id="pdaP111ErrorPackingList" action="${controlVolver}" method="get">
					<table id='table_msg' style='border-collapse: separate; margin: 2px 2px 2px 2px;'>
						<tr>
							<td width="15%" style='text-align: center'>
								<img src='./misumi/images/dialog-error-24.png' />
							</td>
						</tr>
					</table>
					<div id="pda_p111_errorPackingList_bloqueError">
						<p class="pda_p111_error">${mensajeError}</p>
					</div>
					<input type="hidden" id="controlVolver" name="controlVolver" value="${controlVolver}"/>
				</form:form>
			</div>

		</div>

<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>