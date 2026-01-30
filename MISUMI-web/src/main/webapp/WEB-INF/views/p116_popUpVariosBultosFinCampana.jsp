<script src="./misumi/scripts/p116PopUpVariosBultosFinCampana.js?version=${misumiVersion}" type="text/javascript"></script>
		
<div id="p116_popUpVariosBultos" title="<spring:message code="p116_popUpVariosBultos.titulo"/>" style="display:none;">
	<div id="p116_datos">
		<div id="p116_lbl_descripcion" >
			<div>
				<label id="p116_lbl_descripcion"><spring:message code="p116_popUpVariosBultos.descripcion"/></label>
			</div>
		</div>
		<div id="p116_datos_descripcion" >
			<div>
				<label id="p116_lbl_refDescripcion"></label>
			</div>
		</div>
		<div id="p116_bloqueDatosCab">
			<div id="p116_reg_cantidadCab">
				<label id="p116_lbl_cantidadCab"><spring:message code="p116_popUpVariosBultos.cantidad"/></label>
			</div>
			<div id="p116_reg_blancoCab">
				<label></label>
			</div>
			<div id="p116_reg_bultoCab">
				<label id="p116_lbl_bultoCab"><spring:message code="p116_popUpVariosBultos.bulto"/></label>
			</div>
		</div>	
		<div class="p116_bloqueDatos0">
			<div class="p116_reg_cantidad">
				<input id="p116_fld_cantidad0" class="input60 inputTexto" value="" type="text" onblur="javascript:events_116_calcularBulto(0);"/>
			</div>
			<div class="p116_reg_blanco">
				<label> </label>
			</div>
			<div class="p116_reg_bulto">
				<input id="p116_fld_bulto0" class="input60 inputTexto" value="" type="text" onchange="javascript:events_116_validarBulto(0);"/>
			</div>
			<div class="p116_reg_eliminar">
				<img id="p116_btn_eliminar0" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p116_eliminar(0);"/>
			</div> 
			<input id="p116_estadoCerrado0" type="hidden"/>
		</div>
		<div class="p116_bloqueDatos1">
			<div class="p116_reg_cantidad">
				<input id="p116_fld_cantidad1" class="input60 inputTexto" value="" type="text" onblur="javascript:events_116_calcularBulto(1);"/>
			</div>
			<div class="p116_reg_blanco">
				<label> </label>
			</div>
			<div class="p116_reg_bulto">
				<input id="p116_fld_bulto1" class="input60 inputTexto" value="" type="text" onchange="javascript:events_116_validarBulto(1);"/>
			</div>
			<div class="p116_reg_eliminar">
				<img id="p116_btn_eliminar1" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p116_eliminar(1);"/>
			</div> 
			<input id="p116_estadoCerrado1" type="hidden"/>
		</div>
		<div class="p116_bloqueDatos2">
			<div class="p116_reg_cantidad">
				<input id="p116_fld_cantidad2" class="input60 inputTexto" value="" type="text" onblur="javascript:events_116_calcularBulto(2);"/>
			</div>
			<div class="p116_reg_blanco">
				<label> </label>
			</div>
			<div class="p116_reg_bulto">
				<input id="p116_fld_bulto2" class="input60 inputTexto" value="" type="text" onchange="javascript:events_116_validarBulto(2);"/>
			</div>
			<div class="p116_reg_eliminar">
				<img id="p116_btn_eliminar2" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p116_eliminar(2);"/>
			</div> 
			<input id="p116_estadoCerrado2" type="hidden"/>
		</div>
		<div class="p116_bloqueDatos3">
			<div class="p116_reg_cantidad">
				<input id="p116_fld_cantidad3" class="input60 inputTexto" value="" type="text" onblur="javascript:events_116_calcularBulto(3);"/>
			</div>
			<div class="p116_reg_blanco">
				<label> </label>
			</div>
			<div class="p116_reg_bulto">
				<input id="p116_fld_bulto3" class="input60 inputTexto" value="" type="text" onchange="javascript:events_116_validarBulto(3);"/>
			</div>
			<div class="p116_reg_eliminar">
				<img id="p116_btn_eliminar3" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p116_eliminar(3);"/>
			</div>
			<input id="p116_estadoCerrado3" type="hidden"/>
		</div>
		<div class="p116_bloqueDatos4">
			<div class="p116_reg_cantidad">
				<input id="p116_fld_cantidad4" class="input60 inputTexto" value="" type="text" onblur="javascript:events_116_calcularBulto(4);"/>
			</div>
			<div class="p116_reg_blanco">
				<label> </label>
			</div>
			<div class="p116_reg_bulto">
				<input id="p116_fld_bulto4" class="input60 inputTexto" value="" type="text" onchange="javascript:events_116_validarBulto(4);"/>
			</div>
			<div class="p116_reg_eliminar">
				<img id="p116_btn_eliminar4" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p116_eliminar(4);"/>
			</div>
			<input id="p116_estadoCerrado4" type="hidden"/>
		</div>
		<div id="p116_bloqueAvisoPesoVariable">
			<label id="p116_lbl_avisoPesoVariable" class="p116_Aviso" style="display:none;"><spring:message code="p116_variosBultos.avisoPesoVariable"/></label>
		</div>
		<div id="p116_bloqueDatos_cantidadMax">
			<div id="p116_blanco_cantidad">
				<label id="p116_lbl_cantidad"></label>
			</div>
			<div id="p116_datos_cantidad_div">
				<label class="p116_lbl_Info"><spring:message code="p116_variosBultos.cantmax"/></label>
				<label id="p116_valorCampo_cantMax" class="valorCampo" ></label>
			</div>
		</div>
		<div id="p116_bloqueError">
			<label id="p116_lbl_Error" class="p116_Error" style="display:none;"><spring:message code="p116_variosBultos.errorValidacion"/></label>
			<label id="p116_lbl_Error2" class="p116_Error2" style="display:none;"><spring:message code="p116_variosBultos.errorBultosIguales"/></label>
			<label id="p116_lbl_Error3" class="p116_Error3" style="display:none;"><spring:message code="p116_variosBultos.errorCantidadMax"/></label>
			<label id="p116_lbl_Error4" class="p116_Error4" style="display:none;"><spring:message code="p116_variosBultos.errorWS"/></label>
			<label id="p116_lbl_Error5" class="p116_Error5" style="display:none;"><spring:message code="p116_variosBultos.bultoIncorrecto"/></label>
		</div>
	</div>
	<div id="p116_boton">
		<img id="p116_btn_guardado" src="./misumi/images/floppy_30.gif?version=" style="" class="">
	</div>
</div>
<input type="hidden" id="p116_fld_devolucion" value=""></input>
<input type="hidden" id="p116_fld_codArt" value=""></input>
<input type="hidden" id="p116_fld_flgPesoVariable" value=""></input>
<input type="hidden" id="p116_fld_StockDevuelto_Selecc" value=""></input>
<input type="hidden" id="p116_fld_StockDevueltoOrig_Selecc" value=""></input>
<input type="hidden" id="p116_fld_StockDevueltoTmp_Selecc" value=""></input>
<input type="hidden" id="p116_fld_Bulto_Selecc" value=""></input>
<input type="hidden" id="p116_cantidadMax" value=""></input>
<input type="hidden" id="p116_listaBultos" value=""></input>
