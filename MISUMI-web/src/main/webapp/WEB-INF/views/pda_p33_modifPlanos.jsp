<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- TIPO REFERENCIA --%>
<c:set var = "esCajaExpositoraRef" value = "1"/>
<c:set var = "esUnitariaRef" value = "2"/>
<c:set var = "esFFPPRef" value = "3"/>
		
<c:set var = "imagenComercial" value = "${pdaDatosImc}"/>		

<c:choose>

	<%-- SI ES CAJA EXPOSITORA --%>
	<%@ include file="/WEB-INF/views/pda_p33_modifPlanosCajaExp.jsp" %>

	<%-- SI ES UNITARIA --%>
	<%@ include file="/WEB-INF/views/pda_p33_modifPlanosUnitaria.jsp" %>

	<%-- SI ES FFPP --%>
	<%@ include file="/WEB-INF/views/pda_p33_modifPlanosFFPP.jsp" %>

	<%-- Si es Centro VEGALSA --%>
	<%@ include file="/WEB-INF/views/pda_p33_modifPlanosVegalsa.jsp" %>

</c:choose>

<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/utils/json2.js?version=${misumiVersion}" type="text/javascript"></script>

<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>					