package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import es.eroski.misumi.model.ReferenciasPedido;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VMisSeguimientos;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.TreeGridPost;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.iface.VMisSeguimientosService;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/misPedidos")
public class p20MisPedidosController {

	private static Logger logger = Logger.getLogger(p20MisPedidosController.class);

	@Autowired
	private VMisSeguimientosService vMisSeguimientosService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map<String, String> model,
			@RequestParam(required = false, defaultValue = "") String reference,
			@RequestParam(required = false, defaultValue = "") String flgMasInfPedidos
			) {
		if (!"".equals(reference) || reference!=null){
			model.put("reference20", reference);
		}else{
			model.put("reference20", "");
		}
		model.put("flgMasInfPedidos", flgMasInfPedidos);
		return "p20_misPedidos";
	}



	@RequestMapping(value = "/loadAllData", method = RequestMethod.POST)
	public  @ResponseBody Page<ReferenciasPedido> loadAllData(
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

		logger.info("loadAllData - centerId = "+centerId);
		logger.info("loadAllData - referencia = "+referencia);
		logger.info("loadAllData - referenciaATransformar = "+referenciaATransformar);
		logger.info("loadAllData - area = "+area);
		logger.info("loadAllData - seccion = "+seccion);
		logger.info("loadAllData - categoria = "+categoria);
		logger.info("loadAllData - mapa = "+mapa);
		
		List<ReferenciasPedido> listaPedidos = new ArrayList<ReferenciasPedido>();

		VMisSeguimientos vMisSeguimientos = new VMisSeguimientos();
		vMisSeguimientos.setCodCentro(new Long(centerId));

		//Si no existe una referencia eroski para la referencia caprabo, 
		if(!(referencia == "" && referenciaATransformar != "")){				
			//Si me llega la referencia, tendremos que realizar un filtrado especial,
			//filtrando por centro+referencia+tipo=A.
			if (referencia != null && !referencia.equals(""))
			{
				//Obtenemos de la sesión la información del usuario
				User user = (User)session.getAttribute("user");

				vMisSeguimientos.setCodArt(new Long(referencia));
				vMisSeguimientos.setTipo("A");
			}
			else
			{
				if(area !=null && !area.equals("null"))
				{
					//nivel 1
					//vMisSeguimientos.setNivel(new Long(0));
					vMisSeguimientos.setGrupo1(new Long(area));
					vMisSeguimientos.setTipo("A");

					//nivel 2
					if(seccion!=null && !seccion.equals("null")){
						//vMisSeguimientos.setNivel(new Long(1));
						vMisSeguimientos.setGrupo2(new Long(seccion));
						vMisSeguimientos.setTipo("S");
					}

					//nivel 3
					if(categoria!=null && !categoria.equals("null")){
						//vMisSeguimientos.setNivel(new Long(2));
						vMisSeguimientos.setGrupo3(new Long(categoria));
						vMisSeguimientos.setTipo("C");
					}

				}else{
					if (mapa!=null && !mapa.equals("null")){
						if (mapa.equals("0")){
							vMisSeguimientos.setTipo("M");
						}else{
							vMisSeguimientos.setTipo("V");
							vMisSeguimientos.setMapa(mapa);
						}
					}else{
						//Si no filtramos por referencia el tipo sera G
						vMisSeguimientos.setTipo("G");
					}
				}

			}
			if (rbody.getParentid().equals("") && rbody.getNodeid().equals("")) //Filtrado segun el nivel
			{

				//nivel 0
				vMisSeguimientos.setNivel(new Long(0));
				vMisSeguimientos.setParentident(new Long(0));

			} else {

				Long nivel = new Long(rbody.getN_level()) + 1;
				vMisSeguimientos.setNivel(nivel);
				vMisSeguimientos.setParentident(new Long(rbody.getNodeid()));
				//listaPedidos = this.vMisSeguimientosService.findAllReferenciasPedido(vMisSeguimientos);
			}
			listaPedidos = this.vMisSeguimientosService.findAllReferenciasPedido(vMisSeguimientos);


		}
		Page<ReferenciasPedido> pedidos = new Page<ReferenciasPedido>(); 

		pedidos.setPage("1");
		pedidos.setRecords("1");
		pedidos.setTotal("1");
		pedidos.setRows(listaPedidos);

		return pedidos;
	}

	@RequestMapping(value = "/loadDescReferencia", method = RequestMethod.POST)
	public  @ResponseBody String loadDescReferencia(
			@RequestBody VDatosDiarioArt vDatosDiarioArt,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		String referencia = "";

		//Si me llega la referencia, tendremos que obtener la descripcion de la referencia.
		if (vDatosDiarioArt.getCodArt() != null && !vDatosDiarioArt.getCodArt().equals(""))
		{
			//Obtenemos de la sesión la información del usuario
			User user = (User)session.getAttribute("user");

			//Si el centro es de caprabo, devolver descripción de artículo de caprabo
			if(user.getCentro().getEsCentroCaprabo()){
				referencia = utilidadesCapraboService.obtenerDescArtCaprabo(vDatosDiarioArt.getCodArt());						
			}else{

				VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(vDatosDiarioArt.getCodArt());
				if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null)
				{
					referencia = vDatosDiarioArtResul.getDescripArt();
				}
			}
		}

		return referencia;
	}

	@RequestMapping(value = "/loadWsAlbaran", method = RequestMethod.POST)
	public  @ResponseBody VMisSeguimientos loadWsAlbaran(
			@RequestParam(value = "centerId", required = true) String centerId,
			@RequestParam(value = "referencia", required = false) String referencia,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		VMisSeguimientos vMisSeguimientos = new VMisSeguimientos();
		vMisSeguimientos.setCodCentro(new Long(centerId));


		if (referencia != null && !referencia.equals(""))
		{
			//Obtenemos de la sesion la información del usuario
			User user = (User)session.getAttribute("user");

			if(user.getCentro().getEsCentroCaprabo()){
				//Se guarda la referencia transformada en el código de articulo
				vMisSeguimientos.setCodArt(utilidadesCapraboService.obtenerCodigoEroski(Long.parseLong(centerId),Long.parseLong(referencia)));

				//La referencia de caprabo se guarda en código de artículo a transformar
				vMisSeguimientos.setCodArtATransformar(new Long(referencia));	
			}else{
				//En el caso de Eroski, las dos referencias son iguales
				vMisSeguimientos.setCodArt(new Long(referencia));
				vMisSeguimientos.setCodArtATransformar(new Long(referencia));
			}
		}
		//Mientras la referencia de caprabo tenga referencia de eroski asignada, se calcula el albaran, en el caso
		//de buscar la referencia de eroski asignada y no encontrarla, no necesitaremos buscar el albaran pero no
		//querra decir que el webservice ha devuelto error, por lo cual devolvemos error 0
		if(!(vMisSeguimientos.getCodArt() == null && vMisSeguimientos.getCodArtATransformar() !=  null)){
			vMisSeguimientos = this.vMisSeguimientosService.recargaDatosTienda(vMisSeguimientos);
		}else{
			vMisSeguimientos.setCodError(Long.parseLong("0"));
		}

		return vMisSeguimientos;
	}

	private VDatosDiarioArt obtenerDiarioArt(Long codArt) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(codArt);
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		return vDatosDiarioArtRes;
	}
}