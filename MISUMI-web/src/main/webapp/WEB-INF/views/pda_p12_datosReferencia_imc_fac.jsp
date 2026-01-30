<%-- SI ES FAC NO ALI --%>
<c:when test="${imagenComercial.metodo == esFacNoAliRef}">
	<div id="pda_p12_datosReferencia_bloque5">
		<fieldset id="pda_p12_imagenComercial">
			<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP, esMadreRef o CAJA EXP. --%>
			<c:choose>
				<%--Parte leyenda con link verde --%>
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'VERDE'}">
					<c:choose>
						<c:when test="${imagenComercial.tipoReferencia eq esCajaExpositoraRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<a id="pda_p12_imagenComercialSpanVerde"
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message
										code="pda_p12_datosReferencia.implantacionCajaExp" />
								</a>
							</legend>
						</c:when>
						<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<a id="pda_p12_imagenComercialSpanVerde"
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</a>
							</legend>
						</c:when>
						<c:otherwise>
							<legend id="pda_p12_imagenComercialLegend">
								<a id="pda_p12_imagenComercialSpanVerde"
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
								</a>
							</legend>
						</c:otherwise>
					</c:choose>
				</c:when>
				<%--Parte leyenda con link roja --%>
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'ROJO'}">
					<c:choose>
						<c:when test="${imagenComercial.tipoReferencia eq esCajaExpositoraRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<a id="pda_p12_imagenComercialSpanRojo"
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message
										code="pda_p12_datosReferencia.implantacionCajaExp" />
								</a>
							</legend>
						</c:when>
						<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<a id="pda_p12_imagenComercialSpanRojo"
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
								</a>
							</legend>
						</c:when>
						<c:otherwise>
							<legend id="pda_p12_imagenComercialLegend">
								<a id="pda_p12_imagenComercialSpanRojo"
									href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
									<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
								</a>
							</legend>
						</c:otherwise>
					</c:choose>
				</c:when>
				<%--Parte leyenda sin link negra --%>
				<c:otherwise>
					<c:choose>
						<c:when test="${imagenComercial.tipoReferencia eq esCajaExpositoraRef}">
							<legend id="pda_p12_imagenComercialLegend"> 
								<spring:message code="pda_p12_datosReferencia.implantacionCajaExp" />
							</legend>
						</c:when>
						<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<spring:message code="pda_p12_datosReferencia.implantacionCajas" />
							</legend>
						</c:when>
						<c:otherwise>
							<legend id="pda_p12_imagenComercialLegend">
								<spring:message code="pda_p12_datosReferencia.implantacionFFPP" />
							</legend>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<%-- FIN BLOQUE LEYENDA --%>
			<%-- INICIO CONTENIDO --%>
			<div id="pda_p12_datosReferencia_bloque5_areaFacing">
				<%-- VALOR FACING CON Y SIN LINK --%>
				<div id="pda_p12_datosReferencia_bloque5_facing">
					<c:choose>
						<%-- FACING CON LINK --%>
						<c:when test="${pdaDatosRef.MMC eq 'S'}">
							<c:if test="${empty guardadoSfm }">
								<label id="pda_p12_lbl_facingEnlace"
									class="etiquetaCampoNegrita"><a
									href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message
											code="pda_p12_datosReferencia.facing" /></a></label>
								<label id="pda_p12_lbl_facingVal" class="valorCampo">${imagenComercial.facing}</label>
							</c:if>
							<c:if test="${not empty guardadoSfm }">
								<label id="pda_p12_lbl_facingEnlace"
									class="etiquetaCampoNegrita"><a
									href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><span
										class="pda_p12_enlaceSfmOk"><spring:message
												code="pda_p12_datosReferencia.facing" /></span></a></label>
								<label id="pda_p12_lbl_facingVal" class="valorCampo">${imagenComercial.facing}</label>
							</c:if>
						</c:when>
						<%-- FACING SIN LINK --%>
						<c:otherwise>
							<label id="pda_p12_lbl_facing" class="etiquetaCampoNegrita"><spring:message
									code="pda_p12_datosReferencia.facing" /></label>
							<label id="pda_p12_lbl_facingVal" class="valorCampo">${imagenComercial.facing}</label>
						</c:otherwise>
					</c:choose>
				</div>
				<%-- VALOR MULTIP E IMC --%>
				<div id="pda_p12_datosReferencia_bloque5_multipImc">
					<%-- VALOR MULTIP SI NO ES FFPP--%>
					<c:if test="${imagenComercial.tipoReferencia ne esFFPPRef}">
						<div id="pda_p12_datosReferencia_bloque5_multip">
							<label class="etiquetaCampoNegrita"> <spring:message
									code="pda_p12_datosReferencia.facingLinealVPlanoPMultipli" />
							</label> <label class="valorCampo">
								${imagenComercial.multiplicador} </label>
						</div>
					</c:if>
					<%-- VALOR IMC --%>
					<div id="pda_p12_datosReferencia_bloque5_imc">
						<label class="etiquetaCampoNegrita"> <spring:message
								code="pda_p12_datosReferencia.legendIMCImplantacion1" />
						</label> <label class="valorCampo"> ${imagenComercial.imc} </label>
					</div>
				</div>
				
				<%-- INICIO BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>
				<c:if test = "${(pdaDatosRef.pedidoActivo eq 'S') && (pdaDatosRef.capacidad1 ne null && pdaDatosRef.capacidad1 ne 0 && pdaDatosRef.capacidad1 ne '') || (pdaDatosRef.facing1 ne null && pdaDatosRef.facing1 ne 0 && pdaDatosRef.facing1 ne '')}">
					<div id="pda_p12_datosReferencia_bloque5_cap1fac1">
						<div id="pda_p12_datosReferencia_bloque5_cap1">
							<label id="pda_p12_lbl_capacidad1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.capacidad1" /></label>
							<label id="pda_p12_lbl_capacidad1Val" class="valorCampo"><spring:bind path="pdaDatosRef.capacidad1">${status.value}</spring:bind></label>
						</div>
						<div id="pda_p12_datosReferencia_bloque5_fac1">
							<label id="pda_p12_lbl_facing1" class="etiquetaCampoNegrita"><spring:message code="pda_p12_datosReferencia.facing1" /></label>
							<label id="pda_p12_lbl_facing1Val" class="valorCampo"><spring:bind path="pdaDatosRef.facing1">${status.value}</spring:bind></label>
						</div>
					</div>
				</c:if>		
				<%-- FIN BLOQUE CAP1. FAC1. VALORES OBTENIDOS SIN NUEVO PROCEDIMIENTO 124. --%>	
			</div>
			<%-- FIN CONTENIDO --%>
		</fieldset>
	</div>
</c:when>