<%-- INICIO BLOQUE SFM --%>
<c:when test="${imagenComercial.metodo == esSfmRef}">
	<div id="pda_p12_datosReferencia_bloque5">
		<fieldset id="pda_p12_imagenComercial">
			<%-- INICIO BLOQUE LEYENDA Pintamos la leyenda de rojo/verde con link o negro sin link. El texto varía  según si es FFPP o no. --%>
			<c:choose>
				<%--Parte leyenda con link verde --%>
				<c:when test="${pdaDatosRef.flgColorImplantacion eq 'VERDE'}">
					<c:choose>
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
						<c:when test="${imagenComercial.tipoReferencia eq esMadreRef}">
							<legend id="pda_p12_imagenComercialLegend">
								<a id="pda_p12_imagenComercialSpanRojo" href="./pdaP17implantacionPopup.do?procede=capturaRestos&codArt=${pdaDatosRef.codArt}&implantacion=${pdaDatosRef.implantacion}&flgColorImplantacion=${pdaDatosRef.flgColorImplantacion}&origenGISAE=${origenGISAE}">
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

			<%-- INICIO BLOQUE DATOS --%>
			<div id="pda_p12_datosReferencia_bloque5_areaSfm">
				<div id="pda_p12_datosReferencia_bloque5_sfm">
					<%-- Miramos pedir y mostramos SFM con o sin Link --%>
					<c:choose>
						<%-- Con link --%>
						<c:when test="${pdaDatosRef.pedir eq 'S'}">
							<c:choose>
								<%-- El link sale normal --%>
								<c:when test="${empty guardadoSfm}">
									<label id="pda_p12_lbl_sfm" class="etiquetaCampoNegrita"><a
										href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><spring:message
												code="pda_p12_datosReferencia.sfm" /></a></label>
									<label id="pda_p12_lbl_sfmVal" class="valorCampo">${imagenComercial.sfm}</label>
								</c:when>
								<%-- El link sale en verde. Significa que se ha ido a SFM y se ha vuelto tras guardar. --%>
								<c:otherwise>
									<label id="pda_p12_lbl_sfm" class="etiquetaCampoNegrita"><a
										href="./pdaP21Sfm.do?codArt=${pdaDatosRef.codArt}&origen=1&origenGISAE=${origenGISAE}"><span
											class="pda_p12_enlaceSfmOk"><spring:message
													code="pda_p12_datosReferencia.sfm" /></span></a></label>
									<label id="pda_p12_lbl_sfmVal" class="valorCampo">${imagenComercial.sfm}</label>
								</c:otherwise>
							</c:choose>
						</c:when>
						<%-- Sin link --%>
						<c:otherwise>
							<label id="pda_p12_lbl_sfm" class="etiquetaCampoNegrita"><spring:message
									code="pda_p12_datosReferencia.sfm" /></label>
							<label id="pda_p12_lbl_sfmVal" class="valorCampo">${imagenComercial.sfm}</label>
						</c:otherwise>
					</c:choose>
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
			<%-- FIN BLOQUE DATOS --%>
		</fieldset>
	</div>
</c:when>
<%-- FIN BLOQUE SFM --%>



