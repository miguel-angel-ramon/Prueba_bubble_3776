<script src="./misumi/scripts/p115PopUpVariosBultosOrdenRetirada.js?version=${misumiVersion}" type="text/javascript"></script>
		
<div id="p115_popUpVariosBultos" title="<spring:message code="p115_popUpVariosBultos.titulo"/>" style="display:none;">
	<div id="p115_datos">
		<div id="p115_lbl_descripcion" >
			<div>
				<label id="p115_lbl_descripcion"><spring:message code="p115_popUpVariosBultos.descripcion"/></label>
			</div>
		</div>
		<div id="p115_datos_descripcion" >
			<div>
				<label id="p115_lbl_refDescripcion"></label>
			</div>
		</div>
		<div id="p115_bloqueDatosCab">
			<div id="p115_reg_cantidadCab">
				<label id="p115_lbl_cantidadCab"><spring:message code="p115_popUpVariosBultos.cantidad"/></label>
			</div>
			<div id="p115_reg_blancoCab">
				<label></label>
			</div>
			<div id="p115_reg_bultoCab">
				<label id="p115_lbl_bultoCab"><spring:message code="p115_popUpVariosBultos.bulto"/></label>
			</div>
		</div>	
		<div class="p115_bloqueDatos0">
			<div class="p115_reg_cantidad">
				<input id="p115_fld_cantidad0" class="input60" value="" type="text" onblur="javascript:events_115_calcularBulto(0);"/>
			</div>
			<div class="p115_reg_blanco">
				<label> </label>
			</div>
			<div class="p115_reg_bulto">
				<input id="p115_fld_bulto0" class="input60" value="" type="text" onchange="javascript:events_115_validarBulto(0);"/>
			</div>
			<div class="p115_reg_eliminar">
				<img id="p115_btn_eliminar0" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p115_eliminar(0);"/>
			</div> 
			<input id="p115_estadoCerrado0" type="hidden"/>
		</div>
		<div class="p115_bloqueDatos1">
			<div class="p115_reg_cantidad">
				<input id="p115_fld_cantidad1" class="input60" value="" type="text" onblur="javascript:events_115_calcularBulto(1);"/>
			</div>
			<div class="p115_reg_blanco">
				<label> </label>
			</div>
			<div class="p115_reg_bulto">
				<input id="p115_fld_bulto1" class="input60" value="" type="text" onchange="javascript:events_115_validarBulto(1);"/>
			</div>
			<div class="p115_reg_eliminar">
				<img id="p115_btn_eliminar1" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p115_eliminar(1);"/>
			</div> 
			<input id="p115_estadoCerrado1" type="hidden"/>
		</div>
		<div class="p115_bloqueDatos2">
			<div class="p115_reg_cantidad">
				<input id="p115_fld_cantidad2" class="input60" value="" type="text" onblur="javascript:events_115_calcularBulto(2);"/>
			</div>
			<div class="p115_reg_blanco">
				<label> </label>
			</div>
			<div class="p115_reg_bulto">
				<input id="p115_fld_bulto2" class="input60" value="" type="text" onchange="javascript:events_115_validarBulto(2);"/>
			</div>
			<div class="p115_reg_eliminar">
				<img id="p115_btn_eliminar2" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p115_eliminar(2);"/>
			</div>
			<input id="p115_estadoCerrado2" type="hidden"/>
		</div>
		<div class="p115_bloqueDatos3">
			<div class="p115_reg_cantidad">
				<input id="p115_fld_cantidad3" class="input60" value="" type="text" onblur="javascript:events_115_calcularBulto(3);"/>
			</div>
			<div class="p115_reg_blanco">
				<label> </label>
			</div>
			<div class="p115_reg_bulto">
				<input id="p115_fld_bulto3" class="input60" value="" type="text" onchange="javascript:events_115_validarBulto(3);"/>
			</div>
			<div class="p115_reg_eliminar">
				<img id="p115_btn_eliminar3" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p115_eliminar(3);"/>
			</div> 
			<input id="p115_estadoCerrado3" type="hidden"/>
		</div>
		<div class="p115_bloqueDatos4">
			<div class="p115_reg_cantidad">
				<input id="p115_fld_cantidad4" class="input60" value="" type="text" onblur="javascript:events_115_calcularBulto(4);"/>
			</div>
			<div class="p115_reg_blanco">
				<label> </label>
			</div>
			<div class="p115_reg_bulto">
				<input id="p115_fld_bulto4" class="input60" value="" type="text" onchange="javascript:events_115_validarBulto(4);"/>
			</div>
			<div class="p115_reg_eliminar">
				<img id="p115_btn_eliminar4" src="./misumi/images/dialog-cancel-14.png?version=" onclick="javascript:events_p115_eliminar(4);"/>
			</div> 
			<input id="p115_estadoCerrado4" type="hidden"/>
		</div>
		<div id="p115_bloqueAvisoPesoVariable">
			<label id="p115_lbl_avisoPesoVariable" class="p115_Aviso" style="display:none;"><spring:message code="p115_variosBultos.avisoPesoVariable"/></label>
		</div>
		<div id="p115_bloqueError">
			<label id="p115_lbl_Error" class="p115_Error" style="display:none;"><spring:message code="p115_variosBultos.errorValidacion"/></label>
			<label id="p115_lbl_Error2" class="p115_Error2" style="display:none;"><spring:message code="p115_variosBultos.errorBultosIguales"/></label>
			<label id="p115_lbl_Error3" class="p115_Error3" style="display:none;"><spring:message code="p115_variosBultos.errorWS"/></label>
			<label id="p115_lbl_Error5" class="p115_Error5" style="display:none;"><spring:message code="p115_variosBultos.bultoIncorrecto"/></label>
		</div>
	</div>
	<div id="p115_boton">
		<img id="p115_btn_guardado" src="./misumi/images/floppy_30.gif?version=" style="" class="">
	</div>
</div>
<input type="hidden" id="p115_fld_devolucion" value=""></input>
<input type="hidden" id="p115_fld_codArt" value=""></input>
<input type="hidden" id="p115_fld_flgPesoVariable" value=""></input>
<input type="hidden" id="p115_fld_StockDevuelto_Selecc" value=""></input>
<input type="hidden" id="p115_fld_StockDevueltoOrig_Selecc" value=""></input>
<input type="hidden" id="p115_fld_StockDevueltoTmp_Selecc" value=""></input>
<input type="hidden" id="p115_fld_Bulto_Selecc" value=""></input>
<input type="hidden" id="p115_listaBultos" value=""></input>
