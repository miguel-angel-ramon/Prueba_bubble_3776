<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp">
	<jsp:param value="pda_p50_avisos.css?version=${misumiVersion}"
		name="cssFile"></jsp:param>
	<jsp:param value="S" name="flechaMenu"></jsp:param>
	<jsp:param value="pdaWelcome.do" name="actionRef"></jsp:param>
</jsp:include>
<div id="contenidoPagina">
	<!-- <fieldset>
		<legend><spring:message code="pda_p50_fieldset.aviso" /></legend>-->
		<c:forEach var="aviso" items="${avisosList}">
			<div class="aviso">${aviso.getMensaje()}</div>
		</c:forEach>
	<!-- </fieldset>-->
</div>
