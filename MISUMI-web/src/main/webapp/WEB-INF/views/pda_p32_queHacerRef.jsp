<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p01_header.jsp" >
    <jsp:param value="pda_p32_queHacerRef.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
    <jsp:param value="pdaP32QueHacerRef.do" name="actionRef"></jsp:param>
</jsp:include>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		
		<div id="contenidoPagina">
			<div id="pda_p32_que_hacer_ref_titulo">
				<label id="pda_p32_lbl_titulo" class="etiquetaEnlaceDeshabilitado"><spring:message code="pda_p32_queHacerRef.titulo" /></label>
			</div>
			<c:choose>
				<c:when test="${not empty (pdaQueHacerRef)}">
					<div class="pda_p32_que_hacer_ref_bloque">
							<input id="pda_p32_fld_descripcionRef" name="descArtConCodigo" class="input225" type="text" disabled="disabled" value="${pdaDatosCab.descArtCabConCodigo}"/>	
					</div>	
					<c:choose>
						<c:when test="${not empty (pdaQueHacerRef.denomInformeRef)}">
							<div id="pda_p32_que_hacer_ref_bloque_denomInformeRef">
								<label id="pda_p32_lbl_referencia" class="pda_p32_que_hacer_ref_label"><spring:message code="pda_p32_queHacerRef.referencia" />:
									<span id="pda_p32_lbl_referenciaVal" class="pda_p32_que_hacer_ref_campo" ><spring:bind path="pdaQueHacerRef.denomInformeRef">${status.value}</spring:bind></span>
								</label>
							</div>
						</c:when>
					</c:choose>		
					<c:choose>
						<c:when test="${not empty (pdaQueHacerRef.refProveedor)}">
							<div id="pda_p32_que_hacer_ref_bloque_refProveedor">
								<label id="pda_p32_lbl_modelo" class="pda_p32_que_hacer_ref_label"><spring:message code="pda_p32_queHacerRef.modelo" />:
									<span id="pda_p32_lbl_modeloVal" class="pda_p32_que_hacer_ref_campo" ><spring:bind path="pdaQueHacerRef.refProveedor">${status.value}</spring:bind></span>
								</label>	
							</div>
						</c:when>
					</c:choose>		
					<c:choose>
						<c:when test="${not empty (pdaQueHacerRef.converArt)}">
							<div id="pda_p32_que_hacer_ref_bloque_converArt">
								<label id="pda_p32_que_hacer_ref_campo_converArt" class="pda_p32_que_hacer_ref_campo" ><spring:bind path="pdaQueHacerRef.converArt">${status.value}</spring:bind></label>
							</div>
						</c:when>
					</c:choose>		
					<c:choose>
						<c:when test="${not empty (pdaQueHacerRef.talla)}">
							<div class="pda_p32_que_hacer_ref_bloque">
								<label id="pda_p32_lbl_talla" class="pda_p32_que_hacer_ref_label"><spring:message code="pda_p32_queHacerRef.talla" />:
									<span id="pda_p32_lbl_tallaVal" class="pda_p32_que_hacer_ref_campo" ><spring:bind path="pdaQueHacerRef.talla">${status.value}</spring:bind></span>
								</label>	
							</div>
						</c:when>
					</c:choose>		
					<c:choose>
						<c:when test="${not empty (pdaQueHacerRef.color)}">
							<div class="pda_p32_que_hacer_ref_bloque">
								<label id="pda_p32_lbl_color" class="pda_p32_que_hacer_ref_label"><spring:message code="pda_p32_queHacerRef.color" />:
									<span id="pda_p32_lbl_colorVal" class="pda_p32_que_hacer_ref_campo" ><spring:bind path="pdaQueHacerRef.color">${status.value}</spring:bind></span>
								</label>	
							</div>
						</c:when>
					</c:choose>		
					<div id="pda_p32_que_hacer_ref_bloque_accion">
						<label id="pda_p32_lbl_accion" class="pda_p32_que_hacer_ref_label"><spring:message code="pda_p32_queHacerRef.accion" />:
						<c:choose>
						<c:when test="${not empty (pdaQueHacerRef.accion)}">
							<span id="pda_p32_lbl_accionVal"><spring:bind path="pdaQueHacerRef.accion">${status.value}</spring:bind></span>
						</c:when>
						<c:otherwise>
							<span id="pda_p32_lbl_accionVal" ><spring:message code="pda_p32_queHacerRef.sinAccion" /></span>
						</c:otherwise>
						</c:choose>
						</label>
					</div>
					<div class="pda_p32_que_hacer_ref_bloque">
						<label id="pda_p32_lbl_oferta" class="pda_p32_que_hacer_ref_label"><spring:message code="pda_p32_queHacerRef.stockActual" />:
						<c:choose>
						<c:when test="${pdaQueHacerRef.stockActual eq 'Error'}">
							<span id="pda_p32_lbl_stockActualVal" class="pda_p32_que_hacer_ref_error" ><spring:message code="pda_p32_queHacerRef.errorStock" /></span>
						</c:when>
						<c:otherwise>
							<span id="pda_p32_lbl_stockActualVal" class="pda_p32_que_hacer_ref_campo" ><spring:bind path="pdaQueHacerRef.stockActual">${status.value}</spring:bind></span>
						</c:otherwise>
						</c:choose>
						</label>
					</div>
				</c:when>
			</c:choose>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>