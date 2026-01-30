<%-- METODO --%>
<c:set var = "esPlanogramaRef" value = "1"/>
<c:set var = "esSfmRef" value = "2"/>
<c:set var = "esFacCapRef" value = "3"/>
<c:set var = "esCapRef" value = "4"/>
<c:set var = "esFacNoAliRef" value = "5"/>
					
<%-- TIPO REFERENCIA --%>
<c:set var = "esCajaExpositoraRef" value = "1"/>
<c:set var = "esMadreRef" value = "2"/>
<c:set var = "esFFPPRef" value = "3"/>
		
<c:set var = "imagenComercial" value = "${pdaDatosRef.imagenComercial}"/>

<c:if test="${procede eq 'pdaP115PrehuecosLineal'}">
	<%request.setAttribute("procede", "pdaP115PrehuecosLineal");%>
</c:if>

<c:choose>

	<%-- SI es Facing VEGALSA --%>
	<%@ include file="/WEB-INF/views/pda_p12_datosReferencia_imc_vegalsa.jsp" %>					

	<%-- SI ES PLANOGRAMA --%>
	<%@ include file="/WEB-INF/views/pda_p12_datosReferencia_imc_planograma.jsp" %>
	
	<%-- SI ES SFM --%>
	<%@ include file="/WEB-INF/views/pda_p12_datosReferencia_imc_sfm.jsp" %>
	
	<%-- SI ES FAC CAP --%>
	<%@ include file="/WEB-INF/views/pda_p12_datosReferencia_imc_facCap.jsp" %>

	<%-- SI ES CAP --%>
	<%@ include file="/WEB-INF/views/pda_p12_datosReferencia_imc_cap.jsp" %>
	
	<%-- SI ES FAC --%>
	<%@ include file="/WEB-INF/views/pda_p12_datosReferencia_imc_fac.jsp" %>
</c:choose>					