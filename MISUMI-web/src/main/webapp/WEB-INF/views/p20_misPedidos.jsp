<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p20MisPedidos.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/SeguimientoMiPedido.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VDatosDiarioArt.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRefPedidos.js?version=${misumiVersion}" type="text/javascript"></script>


	<input id="p20_permiso_ajuste_pedidos" type="hidden" value="${misumi:contieneOpcion(user.centro.opcHabil, '31_MIS_PEDIDOS_CORTE_PEDIDO')}"></input>
	
		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p01_header.welcome" /></a></li>
						<li><a href="./selPedidosCampanas.do" ><spring:message code="p20_misPedidos.misPedidos" /></a></li>
						<li><spring:message code="p20_misPedidos.seguimientoDiarioPedidos" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="AreaFiltro">
				<div id="p20_filtroReferencia">
					<div class="textBox">
						<label id="p20_lbl_referencia" style="display:none" class="etiquetaCampo"><spring:message code="p20_misPedidos.reference" /></label>
						<input type=text id="p20_fld_referencia" class="input250 controlReturn" value="${reference20}"></input>
						<input type=hidden id="p20_fld_referencia_tmp" class="input250" value=""></input>
						<label id="p20_lbl_descripcionRef" style="display: none;" class="etiquetaCampo"><spring:message code="p20_misPedidos.descripcionRef" /></label>
						<input type=text id="p20_fld_descripcionRef" style="display: none;" class="input225"></input>
					</div>
				</div>
				
				<div id="p20_filtroEstructura" >
					<!-- <div class="comboBox comboBox110 controlReturnP40">
						<label id="p20_lbl_area" class="etiquetaCampo"><spring:message code="p40_pedidoAdicional.area" /></label>
						<select id="p20_cmb_area"></select>
					</div>	-->
					
					<div id="p20_cmb_seccion_div" class="comboBox comboBox110 controlReturnP40">
						<label id="p20_lbl_seccion" class="etiquetaCampo"><spring:message code="p40_pedidoAdicional.section" /></label>
						<select id="p20_cmb_seccion"></select>
					</div>								
					<div id="p20_cmb_categoria_div" class="comboBox comboBox110 controlReturnP40">
						<label id="p20_lbl_categoria" class="etiquetaCampo"><spring:message code="p40_pedidoAdicional.category" /></label>
						<select id="p20_cmb_categoria"></select>
					</div>
				</div>	
				<div id="p20_filtroMapa">
					<div class="comboBox comboBox110 controlReturnP40" >
						<label id="p20_lbl_mapa" class="etiquetaCampo"><spring:message code="p40_pedidoAdicional.map" /></label>
						<select id="p20_cmb_mapa"></select>
					</div>	
				</div>
				<div id="p20_filterButtons">
					<div class="p40_tipoBusqListadoField">
						<input type="radio" class="controlReturnP40" name="p20_rad_tipoFiltro" id="p20_rad_tipoFiltro" value="1" />
						<label id="p40_lbl_radioEstructura" class="etiquetaCampo" title="<spring:message code='p40_pedidoAdicional.comertialStructure' />"><spring:message code="p40_pedidoAdicional.comertialStructureAbr" /></label>
						<input type="radio" class="controlReturnP40" name="p20_rad_tipoFiltro" id="p20_rad_tipoFiltro" value="2"/>
						<label id="p40_lbl_radioReferencia" class="etiquetaCampo" title="<spring:message code='p40_pedidoAdicional.reference' />"><spring:message code="p40_pedidoAdicional.referenceAbr" /></label>
						<input type="radio" class="controlReturnP40" style="display:none" name="p20_rad_tipoFiltro" id="p20_rad_tipoFiltro" value="3" checked="checked" />
						<label id="p40_lbl_radioMapa" style="display:none" class="etiquetaCampo" title="<spring:message code='p40_pedidoAdicional.map' />"><spring:message code="p40_pedidoAdicional.mapAbr" /></label>						
					</div>
					<input type="button" id="p20_btn_buscar" class="boton  botonHover" value='<spring:message code="p20_misPedidos.find" />'></input>
					<input type="button" id="p20_btn_reset" class="boton  botonHover" value='<spring:message code="p20_misPedidos.reset" />'></input>
					
				</div>
			</div>
			<div id="p20AreaErrores" style="display: none;">
				<span id="p20_erroresTexto"><spring:message code="p20_misPedidos.errorWSAlbaranes" /></span>
			</div>
			<div id="p20AreaResultados"  class="p20AreaResultados" style="display: none;">
				<table id="gridP20"></table>
				<input id="p20_RefEroski" value="" type="hidden">
				<input id="p20_RefCaprabo" value="" type="hidden">
			</div>
			
			<!-- No borrar este comentario. Es el area para las acciones de página  
			<div id=AreaAccionesPagina>
					<input type="button" id="pXX_btn_accion1" class="boton  botonHover" value="Acción 1"></input>
					<input type="button" id="pXX_btn_accion2" class="boton  botonHover" value="Acción 2"></input>
					<input type="button" id="pXX_btn_accion3" class="boton  botonHover" value="Acción 3"></input>
			</div> 
			-->
			<input type=hidden id="p30_flgMasInfPedidos" value="${flgMasInfPedidos}"></input>
		</div>	
		<div id="p20_AreaPopups">	
		<%@ include file="/WEB-INF/views/p21_popUpCantidadesPedidas.jsp" %>
 		<%@ include file="/WEB-INF/views/p22_popUpCantidadesNoServidas.jsp" %>
 		<%@ include file="/WEB-INF/views/p23_popUpCantidadesConfirmadas.jsp" %>
 		<%@ include file="/WEB-INF/views/p24_popUpAlbaranElectronico.jsp" %>
 		</div>
		<div id="excellPopup"></div>
		<div id="excellWindow"></div>	
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>