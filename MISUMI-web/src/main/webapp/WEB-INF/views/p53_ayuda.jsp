<!-- ----- Contenido de la JSP ----- -->
	
			<div id="p53_AreaPestanas">
				<div id="p53_pestanas">
				    <ul>
						<li><a href="#p53_pestanaAyuda1"><span id="p53_fld_pestanaAyuda1"></span></a><input type=hidden id="p53_pestanaAyuda1Cargada"></input></li>
				        <li><a href="#p53_pestanaAyuda2"><span id="p53_fld_pestanaAyuda2"><spring:message code="p53_ayuda.ayuda2" /></span></a><input type=hidden id="p53_pestanaAyuda2Cargada"></input></li>
				    </ul>
				    <div id="p53_pestanaAyuda1">
				        <%@ include file="/WEB-INF/views/p54_ayuda1.jsp" %>
				    </div>
				    <div id="p53_pestanaAyuda2">
				        <%@ include file="/WEB-INF/views/p55_ayuda2.jsp" %>
				    </div>
				</div>
			</div>
		
