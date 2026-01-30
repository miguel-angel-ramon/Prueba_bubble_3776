<!-- ----- Include p01_header.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p01_header.jsp" %>
<!-- ----- Contenido de la JSP ----- -->
<script src="./misumi/scripts/jqueryCore/jquery.jqGrid.min.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/jqueryCore/jquery.filter_input.js?version=${misumiVersion}"></script>
<script src="./misumi/scripts/p113ListadoRefSegundaReposicion.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/VAgruComerRef.js?version=${misumiVersion}" type="text/javascript"></script>
<script src="./misumi/scripts/model/ListadoRefSegundaReposicion.js?version=${misumiVersion}" type="text/javascript"></script>

		<!--  Miga de pan --> 
		
		<div id="migaDePan">
			<div class="breadCrumbHolder module">
				<div id="breadCrumb" class="breadCrumb module">
					<ul>
						<li><a href="./welcome.do" ><spring:message code="p113_listadoRefsegundaReposicion.welcome" /></a></li>
						<li>
							<spring:message code="p113_listadoRefsegundaReposicion.segundaReposicion" />
						</li>
					</ul>
				</div>
			</div>
		</div>		
			
		<!--  Contenido página -->
		<div id="contenidoPagina">
			<div id="p113_Area">
				<div id="p113_AreaNuevo">
					<div id="p113_AreaEstructuraReferencia">
						<fieldset id="p113_estructuraReferenciaFieldsetMes">
							<div class="p113_mes">
								<label id="p113_lbl_mes_title" class="etiquetaCampoNegritaIzq"><spring:message code="p113_listadoRefsegundaReposicion.mes" /></label>
							</div>
							<div class="p113_mes">
								<!--  <input onclick="javascript:cambiarValoresChk()" id="p113_chk_mes" type="checkbox"/> -->
								<input type="radio" class="controlReturn" name="p113_rad_mes" id="p113_rad_mes" value="${mesValue}" checked="checked" />
								<label id="p113_lbl_mes" class="etiquetaCampoNegritaIzq">${mes}</label>
							</div>
							<div class="p113_mes">
								<!--<input onclick="javascript:cambiarValoresChk()" id="p113_chk_mes1" type="checkbox"/>-->
								<input type="radio" class="controlReturn" name="p113_rad_mes" id="p113_rad_mes" value="${mes1Value}"/>
								<label id="p113_lbl_mes1" class="etiquetaCampoNegritaIzq">${mes1}</label> 
							</div>
							<div class="p113_mes">
								<!--<input onclick="javascript:cambiarValoresChk()" id="p113_chk_mes2" type="checkbox"/> -->
								<input type="radio" class="controlReturn" name="p113_rad_mes" id="p113_rad_mes" value="${mes2Value}"/>
								<label id="p113_lbl_mes2" class="etiquetaCampoNegritaIzq">${mes2}</label>
							</div>
						</fieldset>
						<fieldset id="p113_estructuraReferenciaFieldset"><legend id="p113_estructuraReferenciaLegend"><spring:message code="p113_listadoRefsegundaReposicion.titBloque1Estructura" /></legend>
							<div id="p113_AreaEstructuraReferencia_1">
								<div id="p113_filtroEstructura">
									<div class="p113_filtroEstructuraField" >
										<div class="p113_comboBox controlReturn">
											<div id="p113_area_Dato" class="p113_datoEstructura">
												<label id="p113_lbl_area" class="etiquetaCampo"><spring:message code="p113_listadoRefsegundaReposicion.area" /></label>
												<select id="p113_cmb_area"></select>
											</div>	
											<div id="p113_area_Error" class="p113_errorEstructura">
												<img id="p113_area_imgError" class="p113_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
											</div>
										</div>
										<div class="p113_comboBox controlReturn">
											<div id="p113_seccion_Dato" class="p113_datoEstructura">
												<label id="p113_lbl_seccion" class="etiquetaCampo"><spring:message code="p113_listadoRefsegundaReposicion.section" /></label>
												<select id="p113_cmb_seccion"></select>
											</div>
											<div id="p113_seccion_Error" class="p113_errorEstructura">
												<img id="p113_seccion_imgError" class="p113_imgErrorEstructura" src="./misumi/images/dialog-error-24.png?version=${misumiVersion}" title=""/>
											</div>
										</div>								
										
									</div>
								</div>			
							</div>
							<div id="p113_AreaEstructuraReferencia_2">
								<div id="p113_imagenFlechaDiv">
									<img id="p113_imagenFlecha" src="./misumi/images/arrow_right_48.png?version=${misumiVersion}">
								</div>
							</div>
							<div id="p113_AreaEstructuraReferencia_3">
								<div id="p113_TablaEstructuraDiv">
									<fieldset id="p113_estructuraMultipleFieldset"><legend id="p113_estructuraMultipleLegend"><spring:message code="p113_listadoRefsegundaReposicion.titBloque1Estructura" /></legend>
										<div id="p113_TablaEstructuraMultipleDiv">
											<table id="p113_table_Estructura">
											</table>
										</div>	
									</fieldset>	
								</div>
							</div>
						</fieldset>	
					</div>
										
				</div>
	
				<div id="p113_AreaBotones">
					<div id="p113_estructuraBotones">
						<div id="p113_buttons">
							<c:if test="${user.perfil != 3}">
								<input type="button" id="p113_btn_search" class="boton  botonHover" value='<spring:message code="p113_listadoRefsegundaReposicion.search" />'></input>
								<input type="button" id="p113_btn_excel" class="boton  botonHover" value='<spring:message code="p113_listadoRefsegundaReposicion.export" />'></input>
								<input type="button" id="p113_btn_clean" class="boton  botonHover" value='<spring:message code="p113_listadoRefsegundaReposicion.clean" />'></input>
							</c:if>
						</div>
					</div>
				</div>
				
				<div id="p113_AreaResultados" style="display: none;">
					<table id="gridP113"></table>
					<div id="pagerP113"></div>

				</div>
			</div>
		</div>		
<!-- ----- Include p02_footer.jsp ----- -->
<%@ include file="/WEB-INF/views/includes/p02_footer.jsp" %>