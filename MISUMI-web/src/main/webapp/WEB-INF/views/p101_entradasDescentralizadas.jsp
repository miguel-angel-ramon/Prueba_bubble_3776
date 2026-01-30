<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp"%>

<!-- ----- Contenido de la JSP ----- -->
<!--  Miga de pan -->

<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"><spring:message
							code="p101_entradas.welcome" /></a></li>
				<li><spring:message
						code="p101_entradas.entradasDescentralizadas" /></li>
			</ul>
		</div>
	</div>
</div>

<!--  Contenido página -->
<div id="contenidoPagina">
	<!-- -->
	<div id="p101_AreaFiltro">
		<div id="p101_filtroEstructura">
			<div class="p101_filtroEstructuraField">
				<div id="p101_descEntradasDescentralziadas" class="p101_filtroElem comboBox comboBoxExtraLarge controlReturnP103">
					<label id="p101_lbl_descEntradasDescentralziadas" class="etiquetaCampo">
						<spring:message code="p101_entradas.descripEntradasDescentralziadas" />
					</label> 
					<select id="p101_cmb_descEntradasDescentralizadas"></select> 
						<input id="p101_cmb_descEntradasDescentralizadas_b" type="hidden" value=""/>
				</div>
				<div id="p101_referencia" class="p101_filtroElem">
					<label id="p101_lbl_referencia" class="etiquetaCampo">
						<spring:message code="p101_entradas.referencia" />
					</label> 
					<input id="p101_referencia_b" type="text" class="input100 controlReturnP103" value=""></input>
				</div>
			</div>
			<div class="p101_filtroEstructuraField">
				<div id="p101_provGenTrabajo" class="p101_filtroElem">
					<label id="p101_lbl_provGen" class="etiquetaCampo">
						<spring:message code="p101_entradas.provGen" />
					</label> 
					<input id="p101_provGen_b" type="text" class="input100 controlReturnP103" value=""></input>
				</div>
				<div id="p101_albaranProveedor" class="p101_filtroElem">
					<label id="p101_lbl_albaranProveedor" class="etiquetaCampo">
						<spring:message code="p101_entradas.albaranProveedor" />
					</label> 
					<input id="p101_albaranProveedor_b" type="text" class="input100 controlReturnP103" value=""/>
				</div>
			</div>
			<div id="p101_provGenTrabajo" class="p101_filtroElem">
				<label id="p101_lbl_provTrabajo" class="etiquetaCampo">
					<spring:message code="p101_entradas.provTrabajo" />
				</label> 
				<input id="p101_provTrabajo_b" type="text" class="input100 controlReturnP103" value=""/>
			</div>
		</div>
		<div id="p101_filterButtons">
			<div class="p101_Historico">
				<div class="p101_HistoricoHelp">
					<input id="p101_chk_historico" type="checkbox"
						class="" /> 
					<label id="p101_lbl_historico" class="etiquetaCampo" for="p40_chk_historico">
						<spring:message code="p101_entradas.historico" />
					</label>
				</div>
				<img id="p101_btn_ayudaHistorico"
					src="./misumi/images/dialog-help-24.png?version=${misumiVersion}"
					class="botonAyuda"
					title="<spring:message code="p101_entradas.ayuda" />" />
			</div>
			<input type="button" id="p101_btn_buscar" class="boton  botonHover"
				value='<spring:message code="p101_entradas.find" />'/> 
			<input type=hidden id="p101_origenPantalla" value="${origenPantalla}"/>
		</div>
	</div>
	<div id="p101_AreaResultados">
		<div class="p101_elipse">
			<div class="p101_lbl_elipse">
				<label id="formLblElipseDar">
					<spring:message code="p101_entradas.elipse.lbl.dar" />
				</label>
			</div>
			<div class="p101_img_elipse">
				<div class="formCellElipse">
					<img id="estadoEntrada1" class="estadoClickable" src='' />
				</div>
			</div>
		</div>
		<div class="p101_flecha"></div>
				<div class="p101_elipse">
			<div class="p101_lbl_elipse">
				<label id="formLblElipseConfirmar">
					<spring:message code="p101_entradas.elipse.lbl.confirmar" />
				</label>
			</div>
			<div class="p101_img_elipse">
				<div class="formCellElipse">
					<img id="estadoEntrada2" class="estadoClickable" src='' />
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Scripts -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/Entrada.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p101EntradasDescentralizadas.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p102PopUpFormEntradasDescentralizadas.js?version=${misumiVersion}" type="text/javascript"></script>

<div id="p102_AreaPopupsFormEntradasDescentralizadas">
	<%@ include file="/WEB-INF/views/p102_popUpFormEntradasDescentralizadas.jsp"%>
</div>
<div id="p103_AreaPopupsEntradaDar">
	<%@ include file="/WEB-INF/views/p103_popUpEntradaDar.jsp" %>
</div>

<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp"%>