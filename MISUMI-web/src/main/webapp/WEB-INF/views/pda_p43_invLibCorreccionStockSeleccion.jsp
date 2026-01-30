<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p43_invLibCorreccionStockSeleccion.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p43invLibCorreccionStockSeleccion.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP43invLibCorreccionStockSeleccion.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
		<form:form method="post" action="pdaP43InvLibCorreccionStockSeleccion.do" commandName="pdaSeleccionStock" >
			<div id="pda_p43_correccionStockSeleccion_titulo">
			<c:choose>
				<c:when test="${empty (pdaSeleccionStock.origenGISAE)}">
					
						<label id="pda_p43_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p43_correccionStockSeleccion.titulo" /></label>
					
				</c:when>
				<c:otherwise>
					<label id="pda_p43_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p42_inventario.titulo" /></label>
				</c:otherwise>
				</c:choose>
			</div>
			
			<div id="pda_p43_correccionStockSeleccion_titulo2">
			<c:choose>
				<c:when test="${pdaSeleccionStock.origenInventario == 'CA'}">
				<label id="pda_p43_lbl_titulo2" class="etiquetaCampoNegritaReducido"><spring:message code="pda_p43_correccionStockSeleccion.camaraAlmacen" /></label>
				</c:when>
				<c:otherwise>
				<label id="pda_p43_lbl_titulo2" class="etiquetaCampoNegritaReducido"><spring:message code="pda_p43_correccionStockSeleccion.salaVenta" /></label>
				</c:otherwise>
			</c:choose>	
			</div>
			<c:choose>
				<c:when test="${listaRelacion.pageCount > 1}">
							<div id="pda_p43_correccionStockSeleccion_bloque2">
								<div id="pda_p43_correccionStockSeleccion_bloque2_pagAnt">
									<c:choose>
										<c:when test="${listaRelacion.page > 0}">
											<input type="submit" name="actionAnt" value='' class='botonSubmitAnt'/>
										</c:when>
										<c:otherwise>
											<input type="submit" name="actionAnt" value='' class='botonSubmitAntDes' disabled="disabled"/>
										</c:otherwise>
									</c:choose>
								</div>	
								<div id="pda_p43_correccionStockSeleccion_bloque2_pagNum">
									<p class="pda_p43_numReferencias"><spring:message code="pda_p43_correccionStockSeleccion.numReferencias" arguments="${listaRelacion.page + 1},${listaRelacion.pageCount}" /></p>
								</div>
								<div id="pda_p43_correccionStockSeleccion_bloque2_pagSig">
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
			<div id="pda_p43_correccionStockSeleccion_bloqueGuardado">
				<p class="pda_p43_guardado">${mensajeGuardado}</p>
			</div>
			<div id="pda_p43_correccionStockSeleccion_bloqueError">
				<p class="pda_p43_error">${mensajeError}</p>
			</div>
			<div id="pda_p43_correccionStockSeleccion_bloque1">
			<table id="pda_43_referenciasMadre">

								<tbody class="tablaContent">
								    <c:forEach var="referencia" items="${listaRelacion.pageList}" varStatus="loopStatus">
									<tr>							
								    	<td id="pda_43_referenciasMadreCod">
								    	<form:radiobutton path="codArt" value="${referencia.codigoReferencia}"  onclick="javascript: form_submit()"  />
								        <td id="pda_p43_referenciasMadreDesc" >
								        <input type="text" id="pda_p43_fld_descripcionRef" class="input125 inputSinBorde" disabled="disabled" type="text" value="${referencia.codigoReferencia}-${referencia.descripcion}"/>
								       </td>
								        <td id="pda_p43_referenciasMadreStock">
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
				<form:hidden path="origenInventario" />
				<form:hidden path="origenGISAE" />
				<form:hidden path="noGuardar" />
				<form:hidden path="seccion" />
			</div>		
		</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>