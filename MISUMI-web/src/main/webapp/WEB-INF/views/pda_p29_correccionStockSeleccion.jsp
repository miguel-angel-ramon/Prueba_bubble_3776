<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p03_headerReducido.jsp" >
    <jsp:param value="pda_p29_correccionStockSeleccion.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p29correccionStockSeleccion.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
        <jsp:param value="pdaP28CorreccionStockInicio.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<form:form method="post" action="pdaP29CorreccionStockSeleccion.do" commandName="pdaSeleccionStock" name="pdaSeleccionStockForm">
			<div id="pda_p29_correccionStockSeleccion_titulo">
				<label id="pda_p29_lbl_titulo" class="etiquetaCampoNegritaReducido"><spring:message code="pda_p29_correccionStockSeleccion.titulo" /></label>
			</div>
			<c:choose>
				<c:when test="${listaRelacion.pageCount > 1}">
							<div id="pda_p21_sfm_bloque2">
								<div id="pda_p21_sfm_bloque2_pagAnt">
									<c:choose>
										<c:when test="${listaRelacion.page > 0}">
											<input type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
										</c:when>
										<c:otherwise>
											<input type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>
										</c:otherwise>
									</c:choose>
								</div>	
								<div id="pda_p21_sfm_bloque2_pagNum">
									<p class="pda_p21_numReferencias"><spring:message code="pda_p21_sfm.numReferencias" arguments="${listaRelacion.page + 1},${listaRelacion.pageCount}" /></p>
								</div>
								<div id="pda_p21_sfm_bloque2_pagSig">
									<c:choose>
										<c:when test="${listaRelacion.pageCount > listaRelacion.page + 1}">
											<input type="submit" name="actionSig" value='' class='botonSubmitSig'/>
										</c:when>
										<c:otherwise>
											<input type="submit" name="actionSig" value='' class='botonSubmitSigDes' disabled="disabled"/>
										</c:otherwise>
									</c:choose>
								</div>					
							</div>
				</c:when>
			</c:choose>
			<div id="pda_p29_correccionStockSeleccion_bloqueGuardado">
				<p class="pda_p29_guardado">${mensajeGuardado}</p>
			</div>
			<div id="pda_p29_correccionStockSeleccion_bloqueError">
				<p class="pda_p29_error">${mensajeError}</p>
			</div>
			<div id="pda_p29_correccionStockSeleccion_bloque1">
			<table id="pda_29_referenciasMadre">

								<tbody class="tablaContent">
								    <c:forEach var="referencia" items="${listaRelacion.pageList}" varStatus="loopStatus">
									<tr>							
								    	<td id="pda_29_referenciasMadreCod">
								    	<form:radiobutton path="codArt" value="${referencia.codigoReferencia}" onclick="javascript:form_submit();"  />
								        <td id="pda_p29_referenciasMadreDesc" >
								        <input type="text" id="pda_p21_fld_descripcionRef" class="input125 inputSinBorde" disabled="disabled" type="text" value="${referencia.codigoReferencia}-${referencia.descripcion}"/>
								       </td>
								        <td id="pda_p29_referenciasMadreStock">
								        <c:out value="${referencia.stock}"/>
								        </td>
								    </tr>
								
								</c:forEach>
								</tbody>
							</table>
				<form:hidden path="codArtOrig" />
				<form:hidden path="codArtSel" />
				<form:hidden path="MMC" />
				<form:hidden path="origen" />
			</div>		
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>