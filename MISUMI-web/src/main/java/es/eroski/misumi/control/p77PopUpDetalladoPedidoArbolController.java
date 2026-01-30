package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.DetalladoEuros;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoLista;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.GestionEurosSIA;
import es.eroski.misumi.model.VMisDetalladoEuros;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.TreeGridPost;
import es.eroski.misumi.service.iface.DetalladoPedidoService;
import es.eroski.misumi.service.iface.VMisDetalladoEurosService;

@Controller
@RequestMapping("/detalladoPedido/arbol")
public class p77PopUpDetalladoPedidoArbolController {

	@Autowired
	private VMisDetalladoEurosService vMisDetalladoEurosService;

	@Autowired
	private DetalladoPedidoService detalladoPedidoService;

	@RequestMapping(value = "/loadAllData", method = RequestMethod.POST)
	public  @ResponseBody Page<DetalladoEuros> loadAllData(
			@RequestBody TreeGridPost rbody,
			@RequestParam(value = "centerId", required = true) String centerId,
			@RequestParam(value = "referencia", required = false) String referencia,
			@RequestParam(value ="referenciaATransformar", required = false) String referenciaATransformar,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "seccion", required = false) String seccion,
			@RequestParam(value = "categoria", required = false) String categoria,
			@RequestParam(value = "mapa", required = false) String mapa,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception{


		List<DetalladoEuros> listaDetalladoEuros = new ArrayList<DetalladoEuros>();

		VMisDetalladoEuros vMisDetalladoEuros = new VMisDetalladoEuros();
		vMisDetalladoEuros.setCodCentro(new Long(centerId));

		//Si no existe una referencia eroski para la referencia caprabo, 
		if(!(referencia == "" && referenciaATransformar != "")){				
			//Si me llega la referencia, tendremos que realizar un filtrado especial,
			//filtrando por centro+referencia+tipo=A.
			if (referencia != null && !referencia.equals(""))
			{
				vMisDetalladoEuros.setCodArt(new Long(referencia));
				vMisDetalladoEuros.setTipo("A");
			}
			else
			{
				if(area !=null && !area.equals("null"))
				{
					//nivel 1
					//vMisSeguimientos.setNivel(new Long(0));
					vMisDetalladoEuros.setGrupo1(new Long(area));
					vMisDetalladoEuros.setTipo("A");

					//nivel 2
					if(seccion!=null && !seccion.equals("null")){
						//vMisSeguimientos.setNivel(new Long(1));
						vMisDetalladoEuros.setGrupo2(new Long(seccion));
						vMisDetalladoEuros.setTipo("S");
					}

					//nivel 3
					if(categoria!=null && !categoria.equals("null")){
						//vMisSeguimientos.setNivel(new Long(2));
						vMisDetalladoEuros.setGrupo3(new Long(categoria));
						vMisDetalladoEuros.setTipo("C");
					}

				}else{
					if(mapa !=null && !mapa.equals("null") && !mapa.equals("0"))	{
						vMisDetalladoEuros.setMapa(new Long(mapa));
						vMisDetalladoEuros.setTipo("D");
					}else{
						//Si no filtramos por referencia el tipo ser� G
						vMisDetalladoEuros.setTipo("G");
					}
				}

			}
			if (rbody.getParentid().equals("") && rbody.getNodeid().equals("")) //Filtrado seg�n el nivel
			{

				//nivel 0
				vMisDetalladoEuros.setNivel(new Long(0));
				vMisDetalladoEuros.setParentident(new Long(0));

			} else {

				Long nivel = new Long(rbody.getN_level()) + 1;
				vMisDetalladoEuros.setNivel(nivel);
				vMisDetalladoEuros.setParentident(new Long(rbody.getNodeid()));
				//listaPedidos = this.vMisSeguimientosService.findAllReferenciasPedido(vMisSeguimientos);
			}

			vMisDetalladoEuros.setIdSesion(session.getId());
			listaDetalladoEuros = this.vMisDetalladoEurosService.findAllReferenciasDetalladoEuros(vMisDetalladoEuros);
		}
		Page<DetalladoEuros> detalladoEuros = new Page<DetalladoEuros>(); 

		detalladoEuros.setPage("1");
		detalladoEuros.setRecords("1");
		detalladoEuros.setTotal("1");
		detalladoEuros.setRows(listaDetalladoEuros);

		return detalladoEuros;
	}	

	@RequestMapping(value="/calcularGestionEuros", method = RequestMethod.POST)
	public @ResponseBody GestionEurosSIA calcularGestionEuros(
			@RequestBody DetallePedidoLista detallePedidoLista,			
			HttpSession session, HttpServletResponse response) throws Exception{	

		List<GestionEurosRefs> lstGestionEurosRefs = null;
		GestionEurosSIA gestionEurosSIA = null;
		try {
			if(detallePedidoLista.getDatos() != null){
				for(DetallePedido detPed:detallePedidoLista.getDatos()){
					
					//Reseteamos el campo Aviso y Diferencia antes del Calcular
					detalladoPedidoService.updatePrevioCalcular(detPed, session.getId());
					
					//Obtenemos las referencias del grid a modificar
					lstGestionEurosRefs = this.detalladoPedidoService.findSessionInfoGestionEurosRefs(detPed, session.getId(), null);

					if(lstGestionEurosRefs != null && lstGestionEurosRefs.size() > 0){
						//Calculamos la gestion de euros
						GestionEuros gestionEuros = new GestionEuros(detPed.getCodCentro(), detPed.getRespetarIMC(), detPed.getPrecioCostoFinal(), detPed.getPrecioCostoFinalFinal(), lstGestionEurosRefs);
						gestionEurosSIA = detalladoPedidoService.gestionEuros(gestionEuros);

						if(gestionEurosSIA.getGestionEuros() != null){
							detalladoPedidoService.updateGridStateGestionEurosRefs(gestionEurosSIA.getGestionEuros(), session.getId());
						}	
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gestionEurosSIA;
	}
	
	
	
	
	
}
