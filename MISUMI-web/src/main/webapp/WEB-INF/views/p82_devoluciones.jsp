<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>

<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/model/Devolucion.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/DevolucionLinea.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p85PopUpFormDevol.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p86PopUpCrearDevolucion.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/p82Devoluciones.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/DevolucionPlataforma.js?version=${misumiVersion}" type="text/javascript"></script>

<!--  Miga de pan --> 
<div id="migaDePan">
	<div class="breadCrumbHolder module">
		<div id="breadCrumb" class="breadCrumb module">
			<ul>
				<li><a href="./welcome.do"><spring:message code="p82_devoluciones.welcome" /></a></li>
				<li><spring:message code="p82_devoluciones.devoluciones" /></li>
			</ul>
		</div>
	</div>
</div>		
			
<!--  Contenido página -->
<div id="contenidoPagina">									
<!-- -->
	<div id="p82_AreaFiltro">
		<div id="p82_filtroEstructura">
			<div class="p82_filtroEstructuraField">
				<div id="p82_descDevol" class="comboBox comboBoxExtraLarge controlReturn">
					<label id="p82_lbl_DescDevol" class="etiquetaCampo"><spring:message code="p82_devoluciones.descripDevolucion" /></label> 
					<select id="p82_cmb_DescDevol"></select> 
					<input id="p82_cmb_DescDevol_b" type="hidden" value=""></input>
				</div>
				<div id="p82_referencia">
					<label id="p82_lbl_referencia" class="etiquetaCampo"><spring:message code="p82_devoluciones.referencia" /></label> 
					<input id="p82_cmb_referencia_b" type="text" class="input100 controlReturn" value=""></input>
				</div>
			</div>
		</div>
		<div id="p82_filterButtons">
			<div class="p82_Historico">
				<div class="p82_HistoricoHelp">
					<input id="p82_chk_historico" type="checkbox" class="controlReturn" /> 
					<label id="p82_lbl_historico" class="etiquetaCampo" for="p40_chk_historico"><spring:message code="p82_devoluciones.historico" /></label>
				</div>
				<img id="p82_btn_ayudaHistorico" src="./misumi/images/dialog-help-24.png?version=${misumiVersion}" class="botonAyuda" title="<spring:message code="p82_devoluciones.ayuda" />" />
			</div>
			<input type="button" id="p82_btn_buscar" class="boton  botonHover" value='<spring:message code="p82_devoluciones.find" />'></input>
			<input type=hidden id="p82_origenPantalla" value="${origenPantalla}"></input>
		</div>
	</div>
	<div id="p82_AreaResultados" style="display:none;">
		<!-- style="display: none;>-->
		<div class="p82_bloque1">
			<div class="p82_seccion240">
				<div class="p82_seccion30">
					<div class="formDevolucionCelda">	
						<c:choose>
							<c:when test="${(user.centro.codCentro == null || user.centro.codCentro != null && user.centro.codCentro != '' && user.centro.controlOpciones == 'S' && user.centro.opcHabil != null && user.centro.opcHabil != '' && misumi:contieneOpcion(user.centro.opcHabil, '35_CREAR_DEVOLUCIONES'))}">
								<img id="p82_img_crearDevolucion" src='./misumi/images/plus.png?version=${misumiVersion}' />
							</c:when>
							<c:otherwise>
								<img id="p82_img_crearDevolucionDeshabilitado" src='./misumi/images/plus.png?version=${misumiVersion}' />
							</c:otherwise>						
						</c:choose>			
					</div>
				</div>
				<div class="p82_seccion70">
					<div class="formDevolucionCelda">
						<label class="p82_lbl etiquetaCampo"><spring:message code="p82_devoluciones.nuevaDevolucion" /></label>
					</div>
				</div>
			</div>
			<div class="p82_seccion752">
				<div class="p82_seccion50">
					<div class="formDevolucionCelda">
						<label class="p82_lbl etiquetaCampo"><spring:message code="p82_devoluciones.prepararMercancia" /></label>
					</div>
				</div>
				<div class="p82_seccion50">
					
				</div>
			</div>
		</div>
		<div class="p82_bloque1">
			<div class="p82_seccion240">
				<div class="p82_seccion30"></div>
				<div class="p82_seccion70">
					<div class="formDevolucionCelda">
						<label class="p82_lbl etiquetaCampo"><spring:message code="p82_devoluciones.centro" /></label>
					</div>
				</div>
			</div>
			<div class="p82_seccion752">
				<div class="p82_seccion50 p82_elipse">
					<div class="formDevolucionCelda">
						<img id="estadoDevolucion1" class="estadoClickable" src=''/>
					</div>
				</div>
				<div class="p82_seccion50"></div>
			</div>
		</div>
		<div id="p82_bloque2">
			<div class="p82_seccion616">
				<div class="p82_seccion616sub1">
					<div class="p82_seccion240"></div>
					<div class="p82_seccion376 p82_flechaRotada"></div>
				</div>
				<div class="p82_seccion616sub2">
					<div class="p82_seccion240Dos">
						<div class="p82_seccion30">	</div>
						<div class="p82_seccion70">
							<div class="formDevolucionCelda">
								<label class="p82_lbl etiquetaCampo"><spring:message code="p82_devoluciones.plataforma" /></label>
							</div>
						</div>						
					</div>
					<div id="p82_plataformaNube" class="p82_seccion376">
						<div class="formDevolucionCelda">
							<img id="estadoDevolucion2" class="estadoClickable" src=''/>
						</div>
					</div>
				</div>
			</div>
			<div class="p82_seccion376Dos">
				<div class="p82_seccion50">
					<div class="p82_seccion50Dos">
						<div class="p82_seccion10"></div>
						<div class="p82_seccion90 p82_flechaVerde"></div>
					</div>
					<div class="p82_seccion50Dos">
						<div class="p82_seccion10"></div>
						<div class="p82_seccion90 p82_flechaRoja"></div>
					</div>
				</div>
				<div class="p82_seccion50">
					<div class="p82_seccion50Dos">
						<div class="p82_seccion10">
							<div class="formDevolucionCelda">
								<label id="p82_lbl_abonado" class="p82_lbl etiquetaCampo"><spring:message code="p82_devoluciones.abonado" /></label>
							</div>
						</div>
						<div class="p82_elipse p82_seccion90">					
							<div class="formDevolucionCelda">
								<img id="estadoDevolucion3" class="estadoClickable" src=''/>
							</div>
						</div>
					</div>
					<div class="p82_seccion50Dos">
						<div id="p82_incidencias" class="p82_seccion10">
							<div class="formDevolucionCelda">
								<label id="p82_lbl_incidencias" class="p82_lbl etiquetaCampo"><spring:message code="p82_devoluciones.incidencias" /></label>
							</div>
						</div>
						<div class="p82_elipse p82_seccion90">
							<div class="formDevolucionCelda">
								<img id="estadoDevolucion4" class="estadoClickable" src=''/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="p88_AreaPopupsCantidadDevueltaCentroKgs">
	<%@ include file="/WEB-INF/views/p88_popUpCantidadDevueltaCentroKgs.jsp" %>
</div>

<div id="p115_AreaPopupsVariosbultos">
	<%@ include file="/WEB-INF/views/p115_popUpVariosBultosOrdenRetirada.jsp" %>
</div>

<div id="p116_AreaPopupsVariosbultos">
	<%@ include file="/WEB-INF/views/p116_popUpVariosBultosFinCampana.jsp" %>
</div>

<div id="p85_AreaPopupsFormDevol">
	<%@ include file="/WEB-INF/views/p85_popUpFormDevol.jsp" %>
	<input id="p85_36_duplicar_devoluciones" type="hidden" value="${misumi:contieneOpcion(user.centro.opcHabil, permisoDevolucionesPCDuplicar)}"></input>
	<input id="p85_37_eliminar_devoluciones" type="hidden" value="${misumi:contieneOpcion(user.centro.opcHabil, permisoDevolucionesPCEliminar)}"></input>
</div>
<div id="p86_AreaPopupsFormCrearDevol">
	<%@ include file="/WEB-INF/views/p86_popUpCrearDevolucion.jsp" %>
</div>
<div id="p83_AreaPopupsDevolFinCampana">
	<%@ include file="/WEB-INF/views/p83_popUpDevolFinCampana.jsp" %>
	<input id="listaBultos" type="hidden" name="listaBultos" value="${listaBultos}">
	<input id="prepararMercancia" type="hidden" name="prepararMercancia" value="0">
	<input id="p83_27_pc_devoluciones_procedimiento" type="hidden" value="${misumi:contieneOpcion(user.centro.opcHabil, permisoPCDevolucionesProcedimiento)}"></input>
</div>
<div id="p84_AreaPopupsDevolOrdenRetirada">
	<%@ include file="/WEB-INF/views/p84_popUpDevolOrdenRetirada.jsp" %>
	<input id="listaBultos" type="hidden" name="listaBultos" value="${listaBultos}">
	<input id="prepararMercancia" type="hidden" name="prepararMercancia" value="0">
	<input id="p84_27_pc_devoluciones_procedimiento" type="hidden" value="${misumi:contieneOpcion(user.centro.opcHabil, permisoPCDevolucionesProcedimiento)}"></input>
</div>
<div id="p87_AreaPopupsDevolCreadaCentro">
	<%@ include file="/WEB-INF/views/p87_popUpDevolCreadaCentro.jsp" %>
</div>



		

		

<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>