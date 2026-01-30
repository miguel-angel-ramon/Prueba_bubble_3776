package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.TDevolucionLinea;

@Controller
public class pdaP69OrdenDeRetiradaDetalleController {

	private static Logger logger = Logger.getLogger(pdaP69OrdenDeRetiradaDetalleController.class);

	@RequestMapping(value = "/pdaP69OrdenDeRetiradaDetalle",method = RequestMethod.POST)
	public String showForm(ModelMap model,
			@RequestParam(value="idDevolucion") String idDevolucion,
			@RequestParam(value="nCaducidad", required=false, defaultValue = "") String nCaducidad,
			@RequestParam(value="nLote", required=false, defaultValue = "") String nLote,
			@RequestParam(value="paginaActual", required=false, defaultValue = "") String paginaActual,
			@RequestParam(value="referenciaFiltro", required=false, defaultValue = "") String referenciaFiltro,
			@RequestParam(value="proveedorFiltro", required=false, defaultValue = "") String proveedorFiltro,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		TDevolucionLinea devolucionLinea = new TDevolucionLinea();
		devolucionLinea.setDevolucion(new Long(idDevolucion));
		devolucionLinea.setnCaducidad(nCaducidad);
		devolucionLinea.setnLote(nLote);

		//Creamos una lista de fechas
		String[] fechas = nCaducidad.split(",");
		List<String> caducidadConcatLst = new ArrayList<String>();
		for(int i=0;i<fechas.length;i=i+2){
			String fechaConcat1 = fechas[i].trim();
			String fechaConcat2  = "";
			if(i+1<fechas.length){
				fechaConcat2 = ", " + fechas[i+1].trim();
			}

			String fechaConcat = fechaConcat1 + fechaConcat2;
			caducidadConcatLst.add(fechaConcat);
		}

		//Creamos una lista de fechas
		String[] lotes = nLote.split(",");
		List<String> loteConcatLst = new ArrayList<String>();
		for(int i=0;i<lotes.length;i=i+3){
			String loteConcat1 = lotes[i].trim();
			String loteConcat2 = "";
			String loteConcat3 = "";
			
			if(i+1<lotes.length){
				 loteConcat2 = ", " + lotes[i+1].trim();
			}
			if(i+2<lotes.length){
				 loteConcat3 = ", " + lotes[i+2].trim();
			}

			String loteConcat = loteConcat1 + loteConcat2 +loteConcat3;
			loteConcatLst.add(loteConcat);
		}
		
		//Obtener los bultos de los proveedores
		List<String> devolucionConBultoPorProveedor = ((TDevolucionLinea) session.getAttribute("devolucionConBultoPorProveedor")).getBultoPorProveedorLst();
		devolucionLinea.setBultoPorProveedorLst(devolucionConBultoPorProveedor);
		
		model.addAttribute("devolucionLinea", devolucionLinea);
		model.addAttribute("caducidadConcatLst",caducidadConcatLst);
		model.addAttribute("loteConcatLst",loteConcatLst);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("referenciaFiltro",referenciaFiltro);
		model.addAttribute("proveedorFiltro",proveedorFiltro);

		model.addAttribute("actionP69","pdaP63RealizarDevolucionOrdenDeRetirada.do");

		String resultado = "pda_p69_ordenDeRetiradaDetalle";

		return resultado;
	}
}
