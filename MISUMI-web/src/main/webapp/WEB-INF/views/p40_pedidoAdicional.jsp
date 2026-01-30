<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.dialogextend.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/utils/map.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p40PedidoAdicional.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PedidoAdicionalE.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PedidoAdicionalM.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PedidoAdicionalMO.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PedidoAdicionalEC.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PedidoAdicionalEM.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PedidoAdicionalVC.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRefPedidos.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p61ModificarPedidoBloque.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p60ModificarPedidoUnico.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p62ModificarEncargoCliente.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/PedidoAdicionalCompleto.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/DiasServicio.js?version=${misumiVersion}" type="text/javascript"></script>	
<script src="./misumi/scripts/p47PedidoAdicionalEC.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p46PedidoAdicionalVC.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p45PedidoAdicionalEmpuje.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p43PedidoAdicionalMO.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p42PedidoAdicionalM.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p41PedidoAdicionalE.js?version=${misumiVersion}" type="text/javascript"></script>  
<script src="./misumi/scripts/utils/calendario.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p53Ayuda.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VOferta.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p54Ayuda1.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p55Ayuda2.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p56AyudaPopup.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p57AyudaPopup1.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/p58AyudaPopup2.js?version=${misumiVersion}" type="text/javascript"></script> 
<script src="./misumi/scripts/model/ReferenciasCentro.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/MotivoBloqueo.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VDatosDiarioArt.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/HistoricoUnidadesVenta.js?version=${misumiVersion}" type="text/javascript"></script>

		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p40_pedidoAdicional.welcome" /></a></li>
						<li><spring:message code="p40_pedidoAdicional.pedidoAdicional" /></li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="p40_filtroEstructuraReferencia">
				<div id="p40_filtroEstructura">
					<div class="p40_filtroEstructuraField" >
								<div class="comboBox comboBox110 controlReturnP40">
									<label id="p40_lbl_seccion" class="etiquetaCampo"><spring:message code="p40_pedidoAdicional.section" /></label>
									<select id="p40_cmb_seccion"></select>
								</div>								
								<div class="comboBox comboBox110 controlReturnP40">
									<label id="p40_lbl_categoria" class="etiquetaCampo"><spring:message code="p40_pedidoAdicional.category" /></label>
									<select id="p40_cmb_categoria"></select>
								</div>
					</div>
				</div>			
				<div id="p40_filtroReferencia">
					<div id="p40_filtroReferenciaField">
						<label id="p40_lbl_referencia" class="etiquetaCampo"><spring:message code="p40_pedidoAdicional.reference" /></label>
						<input class="input100 controlReturnP40" type=text id="p40_fld_referencia"></input>
					</div>
				</div>	
				<div class="p40_MCABusqListado">
					<div class="p40_MCABusqListadoField">
						<input id="p40_chk_mca" type="checkbox" class="controlReturnP40"/>
						<label id="p40_lbl_mca" class="etiquetaCampo" for="p40_chk_mca"><spring:message code="p40_pedidoAdicional.mca" /></label>
					</div>	
					<img id="p40_btn_ayudaMca" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}" class="botonAyuda" title="<spring:message code="p40_pedidoAdicional.ayuda" />"/>
				</div>		
				<div id="p40_filterButtons">
					<div class="p40_tipoBusqListadoField">
						<input type="radio" class="controlReturnP40" name="p40_rad_tipoFiltro" id="p40_rad_tipoFiltro" value="1" checked="checked" />
						<label id="p40_lbl_radioEstructura" class="etiquetaCampo" title="<spring:message code='p40_pedidoAdicional.comertialStructure' />"><spring:message code="p40_pedidoAdicional.comertialStructureAbr" /></label>
						<input type="radio" class="controlReturnP40" name="p40_rad_tipoFiltro" id="p40_rad_tipoFiltro" value="2"/>
						<label id="p40_lbl_radioReferencia" class="etiquetaCampo" title="<spring:message code='p40_pedidoAdicional.reference' />"><spring:message code="p40_pedidoAdicional.referenceAbr" /></label>
					</div>
					<input type="button" id="p40_btn_buscar" class="boton botonNarrow  botonHover" value='<spring:message code="p40_pedidoAdicional.find" />'></input>
					<input type="button" id="p40_btn_excel" class="boton botonNarrow  botonHover" value='<spring:message code="p40_pedidoAdicional.excel" />'></input>
					<c:if test="${user.perfil != 3}">
						<input type="button" id="p40_btn_nuevo" class="boton botonNarrow botonHover" value='<spring:message code="p40_pedidoAdicional.new" />'></input>
						<input type="button" id="p40_btn_modificar" class="boton botonNarrow botonHover" value='<spring:message code="p40_pedidoAdicional.modify" />'></input>
						<input type="button" id="p40_btn_borrar" class="boton botonNarrow botonHover" value='<spring:message code="p40_pedidoAdicional.delete" />'></input>
					</c:if>
				</div>

			</div>
			<div id="p40AreaErrores" style="display: none;">
				<span id="p40_erroresTexto"></span>
			</div>
			<div id="p40_AreaPestanas" style="display:none;">
				<div id="p40_pestanas">
					<input type=hidden id="p40_flagCancelarNuevo" value="${flagCancelarNuevo}"></input>
					<input type=hidden id="p40_pestanaOrigen" value="${pestanaOrigen}"></input>
					<input type=hidden id="p40_codArea" value="${codArea}"></input>
					<input type=hidden id="p40_codSeccion" value="${codSeccion}"></input>
					<input type=hidden id="p40_codCategoria" value="${codCategoria}"></input>
					<input type=hidden id="p40_referencia" value="${referencia}"></input>
					<input type=hidden id="p40_mac" value="${mac}"></input>
					<input type=hidden id="p40_flgReferenciaCentro" value="${flgReferenciaCentro}"></input>
					<input type=hidden id="p40_descPeriodo" value="${descPeriodo}"></input>
					<input type=hidden id="p40_pantallaOrigen" value="${pantallaOrigen}"></input>
					<!--  Variables para el calendario con días de servicio -->
					<input type="hidden" id="codCentroCalendario" value=""></input>
					<input type="hidden" id="codArticuloCalendario" value=""></input>
					<input type="hidden" id="identificadorCalendario" value=""></input>
					<input type="hidden" id="identificadorSIACalendario" value=""></input>
					<input type="hidden" id="identificadorVegalsaCalendario" value=""></input>
					<input type="hidden" id="clasePedidoCalendario" value=""></input>
					<input type="hidden" id="recargarParametrosCalendario" value=""></input>
					<input type="hidden" id="cargadoDSCalendario" value=""></input>
					<input type="hidden" id="esFresco" value=""></input>
				    <ul>
						<li id="p40_pestanaEncargoTab"><a href="#p40_pestanaEncargos"><span id="p40_fld_contadorEncargos"></span></a><input type=hidden id="p40_pestanaEncargosCargada"></input></li>
						<li id="p40_pestanaEmpujeTab"><a href="#p40_pestanaEmpuje"><span id="p40_fld_contadorEmpuje"></span></a><input type=hidden id="p40_pestanaEmpujeCargada"></input></li>
				        <li id="p40_pestanaMontajeTab"><a href="#p40_pestanaMontaje"><span id="p40_fld_contadorMontaje"></span></a><input type=hidden id="p40_pestanaMontajeCargada"></input></li>
				        <li id="p40_pestanaMontajeOfertaTab"><a href="#p40_pestanaMontajeOferta"><span id="p40_fld_contadorMontajeOferta"></span></a><input type=hidden id="p40_pestanaMontajeOfertaCargada"></input></li>
						<li id="p40_pestanaValidarCantTab"><a href="#p40_pestanaValidarCant"><span id="p40_fld_contadorValidarCant"></span></a><input type=hidden id="p40_pestanaValidarCantCargada"></input></li>
						<li id="p40_pestanaEncargoClienteTab"><a href="#p40_pestanaEncargosCliente"><span id="p40_fld_contadorEncargosCliente"></span></a><input type=hidden id="p40_pestanaEncargosClienteCargada"></input></li>
				    </ul>
				    <div id="p40_pestanaEncargos">
				        <%@ include file="/WEB-INF/views/p41_pedidoAdicionalEncargos.jsp" %>
				    </div>
				    <div id="p40_pestanaMontaje">
				        <%@ include file="/WEB-INF/views/p42_pedidoAdicionalMontaje.jsp" %>
				    </div>
				    <div id="p40_pestanaMontajeOferta">
				        <%@ include file="/WEB-INF/views/p43_pedidoAdicionalMontajeOferta.jsp" %>
				    </div>
				      <div id="p40_pestanaEmpuje">
				        <%@ include file="/WEB-INF/views/p45_pedidoAdicionalEmpuje.jsp" %>
				    </div>
				    <div id="p40_pestanaValidarCant">
				        <%@ include file="/WEB-INF/views/p46_pedidoAdicionalValidarCant.jsp" %>
				    </div>
				    <div id="p40_pestanaEncargosCliente">
				        <%@ include file="/WEB-INF/views/p47_pedidoAdicionalEncargosCliente.jsp" %>
				    </div>
				</div>
			</div>
			<div id="p40_AreaPopups">
				<%@ include file="/WEB-INF/views/p60_modificarPedidoUnico.jsp" %>
				<%@ include file="/WEB-INF/views/p61_modificarPedidoBloque.jsp" %>
				<%@ include file="/WEB-INF/views/p62_modificarEncargoCliente.jsp" %>
				<%@ include file="/WEB-INF/views/p63_popUpRefCompraVenta.jsp" %>
				<%@ include file="/WEB-INF/views/p67_popUpGestionAdicional.jsp" %>
			</div>
		</div>	
		<%@ include file="/WEB-INF/views/p56_ayudaPopup.jsp" %>	
				<%@ include file="/WEB-INF/views/p17_referenciasCentroPopupVentas.jsp"%>
		<%@ include file="/WEB-INF/views/p74_motivosBloqueo.jsp" %>
		<div id="excellPopup"></div>
		<div id="excellWindow"></div>		
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>