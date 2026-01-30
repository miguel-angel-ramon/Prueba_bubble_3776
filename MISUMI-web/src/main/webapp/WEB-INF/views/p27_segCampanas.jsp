<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p27SegCampanas.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/SeguimientoCampanas.js?version=${misumiVersion}" type="text/javascript"></script>
	
		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p01_header.welcome" /></a></li>
						<li><a href="./selPedidosCampanas.do" ><spring:message code="p27_segCampanas.misPedidos" /></a></li>
						<li><spring:message code="p27_segCampanas.seguimientoDiarioCampanas" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="p27_AreaFiltro">
				<div id="p27_filtroEstructura">
					<div class="p27_filtroEstructuraField" >
						<div id="p27_filtroCampana" class="comboBox comboBoxExtraLarge controlReturn">
							<div class="p27_filtroCampanaOfertaLabel">
								<label id="p27_lbl_comboCampana" class="etiquetaCampo"><spring:message code="p27_segCampanas.comboCampana" /></label>
							</div>
							<div class="p27_filtroCampanaOfertaCmb">	
								<select id="p27_cmb_campana"></select>
								<input id="p27_cmb_campana_tmp" type="hidden" value=""></input>
							</div>	
						</div>								
						<div id="p27_filtroOferta" class="comboBox comboBoxExtraLarge controlReturn" style="display: none;">
							<div class="p27_filtroCampanaOfertaLabel">
								<label id="p27_lbl_comboOferta" class="etiquetaCampo"><spring:message code="p27_segCampanas.comboOferta" /></label>
							</div>
							<div class="p27_filtroCampanaOfertaCmb">	
								<select id="p27_cmb_oferta"></select>
								<input id="p27_cmb_oferta_tmp" type="hidden" value=""></input>
							</div>	
						</div>
					</div>
				</div>	
				<div id="p27_filtroReferencia">
					<div class="textBoxMin">
						<label id="p27_lbl_referencia" class="etiquetaCampo"><spring:message code="p27_segCampanas.reference" /></label>
						<input type=text id="p27_fld_referencia" class="input100 controlReturn" value=""></input>
						<input type=hidden id="p27_fld_referencia_tmp" value=""></input>
					</div>
				</div>			
				<div id="p27_filterButtons">
					<c:choose>
						<c:when test="${ ( user.code == 'i1251' || user.code == 'I1251' || user.code == 's305060' || user.code == 'S305060' ) }">
							<div class="p27_tipoBusqListadoField">
						</c:when>
						<c:otherwise>
							<div class="p27_tipoBusqListadoField" style="display:none;">
						</c:otherwise>
					</c:choose>			
						<input type="radio" class="controlReturn" name="p27_rad_tipoFiltro" id="p27_rad_tipoFiltro" value="1" checked="checked"  />
						<label id="p27_lbl_radioCampana" class="etiquetaCampo" title="<spring:message code='p27_segCampanas.campana' />"><spring:message code="p27_segCampanas.campana" /></label>
						<input type="radio" class="controlReturn" name="p27_rad_tipoFiltro" id="p27_rad_tipoFiltro" value="2"/>
						<label id="p27_lbl_radioOferta" class="etiquetaCampo" title="<spring:message code='p27_segCampanas.oferta' />"><spring:message code="p27_segCampanas.oferta" /></label>
						<input id="p27_rad_tipoFiltro_tmp" type="hidden" value=""></input>
					</div>
					<input type="button" id="p27_btn_buscar" class="boton  botonHover" value='<spring:message code="p27_segCampanas.find" />'></input>
					<input type="button" id="p27_btn_reset" class="boton  botonHover" value='<spring:message code="p27_segCampanas.reset" />'></input>
					
				</div>
			</div>
			<div id="p27AreaErrores" style="display: none;">
				<span id="p27_erroresTexto"><spring:message code="p27_segCampanas.errorCargaDatos" /></span>
			</div>
			<div id="p27AreaResultados"  class="p27AreaResultados" style="display: none;">
				<table id="gridP27"></table>
			</div>
		</div>	
		<div class="p27_AreaPopups">	
			<div id="p27_AreaPopupsRefCampanas">
				<%@ include file="/WEB-INF/views/p28_popUpRefCampanas.jsp" %>
			</div>
			<div id="p27_AreaPopupsCantNoServ">
				<%@ include file="/WEB-INF/views/p29_popUpCantidadesNoServidasRef.jsp" %>
			</div>	
 		</div>
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>