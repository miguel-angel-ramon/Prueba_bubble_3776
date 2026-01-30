<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/includes/pda_p03_headerReducido.jsp" >
    <jsp:param value="pda_p102_variosBultosOrdenRetirada.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p102variosBultosOrdenRetirada.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
	<jsp:param value="pdaP102VariosBultosOrdenRetirada.do" name="actionRef"></jsp:param>
</jsp:include>
<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" id="p102_form" action="pdaP102VariosBultosOrdenRetirada.do" commandName="devolucionLinea">	
				<div id="pda_p102_tipoDeDevolucion_tituloGeneral">
					<div id="pda_p102_tipoDevolucion_titulo">
						<label class="pda_p102_lbl_negrita"><spring:message code="pda_p102_variosBultos.titulo"/></label>					
					</div>
				</div>
				<div id="pda_p102_descArticulo_bloque">
					<div id="pda_p102_descArticulo">
						<input id="pda_p102_fld_descripcionRef" name="descArtConCodigo" class="input215" type="text" disabled="disabled" value="${devolucionLinea.codArticulo} - ${devolucionLinea.denominacion}"/>
					</div>
				</div>
				<div id="pda_p102_bloqueDatos">
					<div id="pda_p102_reg_cantidadCab">
						<label id="pda_p102_lbl_cantidadCab"><spring:message code="pda_p102_variosBultos.cantidad" /></label>
					</div>
					<div id="pda_p102_reg_blancoCab">
						<label id="pda_p102_lbl_cantidadCab"></label>
					</div>
					<div id="pda_p102_reg_bultoCab">
						<label id="pda_p102_lbl_bultoCab"><spring:message code="pda_p102_variosBultos.bulto" /></label>
					</div>
				</div>	
				<div id="pda_p102_bloqueDatos">
					<c:forEach var="articulo" items="${devolucionLinea.listTDevolucionLinea}" varStatus="loopStatus">
						<div class="pda_p102_reg_cantidad">
							<c:if test="${articulo.estadoCerrado=='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].stock" id="pda_p102_cantidad${loopStatus.index}" readonly="readonly" class="input50 pda_p102_readonly" value="${articulo.stock}" onblur="javascript:events_102_calcularBulto(${loopStatus.index});"/>
							</c:if>	
							<c:if test="${articulo.estadoCerrado!='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].stock" id="pda_p102_cantidad${loopStatus.index}" class="input50" value="${articulo.stock}" onblur="javascript:events_102_calcularBulto(${loopStatus.index});"/>
							</c:if>		
						</div>
						<div class="pda_p102_reg_blanco">
							<label class="pda_p102_lbl_negrita"> </label>
						</div>
						<div class="pda_p102_reg_bulto">
							<c:if test="${articulo.estadoCerrado=='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].bulto" id="pda_p102_bulto${loopStatus.index}" readonly="readonly" class="input50 pda_p102_readonly" value="${articulo.bulto}" onblur="javascript:events_102_validarBulto(${loopStatus.index});"/>
							</c:if>	
							<c:if test="${articulo.estadoCerrado!='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].bulto" id="pda_p102_bulto${loopStatus.index}" class="input50" value="${articulo.bulto}" onblur="javascript:events_102_validarBulto(${loopStatus.index});"/>
							</c:if>
						</div>
						<div class="pda_p102_reg_blanco">
							<label class="pda_p102_lbl_negrita"> </label>
						</div>
						<div class="pda_p102_reg_eliminar">
							<c:if test="${articulo.estadoCerrado=='S'}">
								<input type="" id="pda_p102_btn_eliminar${loopStatus.index}" style="display:none;" class="botonSubmitEliminar operacionDefecto" value='' onclick="javascript:events_p102_eliminar(${loopStatus.index});"/>
							</c:if>
							<c:if test="${articulo.estadoCerrado!='S'}">
								<input type="" id="pda_p102_btn_eliminar${loopStatus.index}" class="botonSubmitEliminar operacionDefecto" value='' onclick="javascript:events_p102_eliminar(${loopStatus.index});"/>
							</c:if>
						</div>
						<div class="pda_p102_reg_blanco">
							<label class="pda_p102_lbl_negrita"> </label>
						</div>
						<div class="pda_p102_reg_cajaBulto" >
							<c:if test="${articulo.estadoCerrado=='N'}">
								<input type="" id="pda_p102_btn_cajaBulto${loopStatus.index}" class="botonSubmitEstadoBulto operacionDefecto" onclick="javascript:events_p102_cajaBulto(${loopStatus.index});" value=''/>
							</c:if>	
							<c:if test="${articulo.estadoCerrado!='N'}">
								<input type="" id="pda_p102_btn_cajaBulto${loopStatus.index}" style="display:none;" class="botonSubmitEstadoBulto operacionDefecto" onclick="javascript:events_p102_cajaBulto(${loopStatus.index});" value=''/>
							</c:if>
						</div>
						<input type="hidden" id="pda_p102_estadoCerrado${loopStatus.index}"  name="listTDevolucionLinea[${loopStatus.index}].estadoCerrado" value="${articulo.estadoCerrado}">
					</c:forEach>
				</div>
				<div id="pda_p102_bloqueAvisoPesoVariable">
					<label id="pda_p102_lbl_avisoPesoVariable" class="pda_p102_Aviso" style="display:none;"><spring:message code="pda_p102_variosBultos.avisoPesoVariable"/></label>
				</div>
				<div id="pda_p102_bloqueError">
					<label id="pda_p102_lbl_Error" class="pda_p102_Error" style="display:none;"><spring:message code="pda_p102_variosBultos.errorValidacion"/></label>
					<label id="pda_p102_lbl_Error2" class="pda_p102_Error2" style="display:none;"><spring:message code="pda_p102_variosBultos.errorBultosIguales"/></label>
					<label id="pda_p102_lbl_Error3" class="pda_p102_Error3" style="display:none;"><spring:message code="pda_p102_variosBultos.bultoErroneo"/></label>
				</div>
				<div id="pda_p102_filterButtons">
					<input type="" id="pda_p102_btn_save" class="botonSubmitGrabar operacionDefecto" onclick="javascript:events_p102_guardar();" value=''/>
				</div>	
				<input type="hidden" name="codArticulo" value="${devolucionLinea.codArticulo}">
				<input type="hidden" name="devolucion" value="${devolucionCabecera.devolucion}">
				<input type="hidden" name="estructuraComercial" value="${devolucionLinea.estructuraComercial}">
				<input type="hidden" id="flgPesoVariable" name="flgPesoVariable" value="${devolucionLinea.flgPesoVariable}">
				<input type="hidden" id="provrGen" name="provrGen" value="${devolucionLinea.provrGen}">
				<input type="hidden" id="provrTrabajo" name="provrTrabajo" value="${devolucionLinea.provrTrabajo}">
				<input type="hidden" id="listaBultos" name="listaBultos" value="${listaBultos}">
				<input type="hidden" id="origenPantalla" name="origenPantalla" value="${origenPantalla}">
				<input type="hidden" id="selProv" name="selProv" value="${selectProv}">
			</form:form>
		</div>		
<!-- ----- Include pda_p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/pda_p02_footer.jsp" %>