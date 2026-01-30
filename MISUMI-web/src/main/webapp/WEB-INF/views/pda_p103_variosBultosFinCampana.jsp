<%@ include file="/WEB-INF/views/includes/includeTemplate.jsp"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/WEB-INF/views/includes/pda_p03_headerReducido.jsp" >
    <jsp:param value="pda_p103_variosBultosFinCampana.css?version=${misumiVersion}" name="cssFile"></jsp:param>
    <jsp:param value="pda_p103variosBultosFinCampana.js?version=${misumiVersion}" name="jsFile"></jsp:param>
    <jsp:param value="S" name="flechaMenu"></jsp:param>
	<jsp:param value="pdaP103VariosBultosFinCampana.do" name="actionRef"></jsp:param>
</jsp:include>
<script src="./misumi/scripts/utils/pdaUtils.js?version=${misumiVersion}" type="text/javascript"></script>
<fmt:setLocale value="es_ES"/>
<c:set var="conCosteMaximo" value="${0 lt devolucionCabecera.costeMaximo}" />
<!-- ----- Contenido de la JSP ----- -->

		<!--  Contenido página -->
		<div id="contenidoPagina">
			<form:form method="post" id="p103_form" action="pdaP103VariosBultosFinCampana.do" commandName="devolucionLinea">	
				<div id="pda_p103_tipoDeDevolucion_tituloGeneral">
					<div id="pda_p103_tipoDevolucion_titulo">
						<label class="pda_p103_lbl_negrita"><spring:message code="pda_p103_variosBultos.titulo"/></label>					
					</div>
				</div>
				<div id="pda_p103_descArticulo_bloque">
					<div id="pda_p103_descArticulo">
						<input id="pda_p103_fld_descripcionRef" name="descArtConCodigo" class="input215" type="text" disabled="disabled" value="${devolucionLinea.codArticulo} - ${devolucionLinea.denominacion}"/>
					</div>
				</div>
				<c:if test="${conCosteMaximo}">
					<div id="pda_p103_tipoDevolucion_euros_contenedor" class="${sumaEuros gt devolucionCabecera.costeMaximo ? 'pda_p103_red' : ''}">
						<div id="pda_p103_eurosInfo">
							<label class="pda_p103_lbl_Info"><spring:message code="pda_p103_tipoDeDevolucion.euros" /></label>
						</div>
						<div id="pda_p103_eurosDatos">
							<label id="pda_p103_euros_val">
								<fmt:formatNumber maxFractionDigits="2" value="${sumaEuros}" />
							</label>
						</div>
						<div id="pda_p103_eurosMaxInfo">
							<label class="pda_p103_lbl_Info"><spring:message code="pda_p103_tipoDeDevolucion.eurosMax" /></label>
						</div>
						<div id="pda_p103_eurosMaxDatos">
							<label id="pda_p103_euros_max_val">
								<fmt:formatNumber maxFractionDigits="2" value="${devolucionCabecera.costeMaximo}" />
							</label>
						</div>
					</div>
				</c:if>
				<div id="pda_p103_bloqueDatos">
					<div id="pda_p103_reg_cantidadCab">
						<label id="pda_p103_lbl_cantidadCab"><spring:message code="pda_p103_variosBultos.cantidad" /></label>
					</div>
					<div id="pda_p103_reg_blancoCab">
						<label></label>
					</div>
					<div id="pda_p103_reg_bultoCab">
						<label id="pda_p103_lbl_bultoCab"><spring:message code="pda_p103_variosBultos.bulto" /></label>
					</div>
				</div>	
				<div id="pda_p103_bloqueDatos">
					<c:forEach var="articulo" items="${devolucionLinea.listTDevolucionLinea}" varStatus="loopStatus">
						<div class="pda_p103_reg_cantidad">
							<c:if test="${articulo.estadoCerrado=='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].stock" id="pda_p103_cantidad${loopStatus.index}" readonly="readonly" class="input50 pda_p103_readonly" value="${articulo.stock}" onblur="javascript:events_103_calcularBulto(${loopStatus.index});"/>
							</c:if>	
							<c:if test="${articulo.estadoCerrado!='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].stock" id="pda_p103_cantidad${loopStatus.index}" class="input50" value="${articulo.stock}" onblur="javascript:events_103_calcularBulto(${loopStatus.index});"/>
							</c:if>	
						</div>
						<div class="pda_p103_reg_blanco">
							<label class="pda_p103_lbl_negrita"> </label>
						</div>
						<div class="pda_p103_reg_bulto">
							<c:if test="${articulo.estadoCerrado=='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].bulto" id="pda_p103_bulto${loopStatus.index}" readonly="readonly" class="input50 pda_p103_readonly" value="${articulo.bulto}" onblur="javascript:events_103_validarBulto(${loopStatus.index});"/>
							</c:if>	
							<c:if test="${articulo.estadoCerrado!='S'}">
								<input type="number" name="listTDevolucionLinea[${loopStatus.index}].bulto" id="pda_p103_bulto${loopStatus.index}" class="input50" value="${articulo.bulto}" onblur="javascript:events_103_validarBulto(${loopStatus.index});"/>
							</c:if>	
						</div>
						<div class="pda_p103_reg_blanco">
							<label class="pda_p103_lbl_negrita"> </label>
						</div>
						<div class="pda_p103_reg_eliminar" >
							<c:if test="${articulo.estadoCerrado=='S'}">
								<input type="" id="pda_p103_btn_eliminar${loopStatus.index}" style="display:none;" class="botonSubmitEliminar operacionDefecto" value='' onclick="javascript:events_p103_eliminar(${loopStatus.index});"/>
							</c:if>	
							<c:if test="${articulo.estadoCerrado!='S'}">
								<input type="" id="pda_p103_btn_eliminar${loopStatus.index}" class="botonSubmitEliminar operacionDefecto" value='' onclick="javascript:events_p103_eliminar(${loopStatus.index});"/>
							</c:if>	
							
						</div>
						<div class="pda_p103_reg_blanco">
							<label class="pda_p103_lbl_negrita"> </label>
						</div>
						<div class="pda_p103_reg_cajaBulto" onclick="javascript:events_p103_cajaBulto(${loopStatus.index});">
							<c:choose>
								<c:when test="${articulo.estadoCerrado!='N' || articulo.bulto==0}"> 
									<input type="" id="pda_p103_btn_cajaBulto${loopStatus.index}" style="display:none;" class="botonSubmitEstadoBulto operacionDefecto" value=''/>
								</c:when>
								<c:otherwise>
									<input type="" id="pda_p103_btn_cajaBulto${loopStatus.index}" class="botonSubmitEstadoBulto operacionDefecto" value=''/>
								</c:otherwise>
							</c:choose>
						</div>
						<input type="hidden" id="pda_p103_estadoCerrado${loopStatus.index}" name="listTDevolucionLinea[${loopStatus.index}].estadoCerrado" value="${articulo.estadoCerrado}">
					</c:forEach>
				</div>
				<c:if test="${conCosteMaximo}">
					<div id="pda_p103_bloqueDatos_costo">
						<div id="pda_p103_blanco_costo">
							<label id="pda_p103_lbl_costo"></label>
						</div>
						<div id="pda_p103_datos_costo_div">
							<label class="pda_p103_lbl_Info" ><spring:message code="pda_p103_finDeCampania.costo"/></label>
							<label id="pda_p103_coste_unitario" class="valorCampo" >
								<fmt:formatNumber maxFractionDigits="2" value="${devolucionLinea.costeUnitario}" />
							</label>
						</div>
					</div>
				</c:if>
				<c:if test="${devolucionLinea.cantidadMaximaLin != null}">
					<div id="pda_p103_bloqueDatos_cantidadMax">
						<div id="pda_p103_blanco_cantidad">
							<label id="pda_p103_lbl_cantidad"></label>
						</div>
						<div id="pda_p103_datos_cantidad_div">
							<label class="pda_p103_lbl_Info"><spring:message code="pda_p103_finDeCampania.cantmax"/></label>
							<label id="pda_p103_valorCampo_cantMax" class="valorCampo" >${fn:replace(devolucionLinea.cantidadMaximaLin,'.',',')}</label>																
						</div>
					</div>
				</c:if>
				<div id="pda_p103_bloqueAvisoPesoVariable">
					<label id="pda_p103_lbl_avisoPesoVariable" class="pda_p103_Aviso" style="display:none;"><spring:message code="pda_p103_variosBultos.avisoPesoVariable"/></label>
				</div>
				<div id="pda_p103_bloqueError">
					<label id="pda_p103_lbl_Error" class="pda_p103_Error" style="display:none;"><spring:message code="pda_p103_variosBultos.errorValidacion"/></label>
					<label id="pda_p103_lbl_Error2" class="pda_p103_Error2" style="display:none;"><spring:message code="pda_p103_variosBultos.errorBultosIguales"/></label>
					<label id="pda_p103_lbl_Error3" class="pda_p103_Error3" style="display:none;"><spring:message code="pda_p103_variosBultos.errorCantidadMax"/></label>
					<label id="pda_p103_lbl_Error4" class="pda_p103_Error4" style="display:none;"><spring:message code="pda_p103_variosBultos.bultoErroneo"/></label>	
				</div>
				<div id="pda_p103_filterButtons">
					<input type="" id="pda_p103_btn_save"  class="botonSubmitGrabar operacionDefecto" onclick="javascript:events_p103_guardar();" value=''/>
				</div>	
				<input type="hidden" name="codArticulo" value="${devolucionLinea.codArticulo}">
				<input type="hidden" name="devolucion" value="${devolucionCabecera.devolucion}">
				<input type="hidden" name="estructuraComercial" value="${devolucionLinea.estructuraComercial}">
				<input type="hidden" id="cantidadMax" name="cantidadMax" value="${devolucionLinea.cantidadMaximaLin}">
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