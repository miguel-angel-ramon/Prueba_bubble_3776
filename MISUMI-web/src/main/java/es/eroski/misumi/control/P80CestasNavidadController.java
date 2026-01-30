package es.eroski.misumi.control;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CestasNavidad;
import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CestasNavidadService;
import es.eroski.misumi.service.iface.StockPlataformaService;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;

@Controller
@RequestMapping("/cestasNavidad")
public class P80CestasNavidadController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(P80CestasNavidadController.class);

	@Autowired
	private CestasNavidadService cestasNavidadService;

	@Autowired
	private StockPlataformaService stockPlataformaService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	/**
	 * Metodo que devuelve una lista tipo CestaNavidad con los lotes de navidad de los centros
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getLotesNavidad", method = RequestMethod.GET)
	public @ResponseBody List <CestasNavidad> lotesNavidad(
			HttpSession session, HttpServletResponse response) throws Exception {

		User usuarioSession = (User) session.getAttribute("user");
		List<CestasNavidad> lotesNavidad= null;

		//B�squeda de Lotes de Navidad
		try {
			User user = (User) session.getAttribute("user");

			//Carga Lista con los lotes de Navidad
			//Se calcula el código y la descripción que se va a mostrar en pantalla dependiendo si es un
			//centro de Caprabo o no
			boolean esCentroCaprabo = utilidadesCapraboService.esCentroCaprabo(usuarioSession.getCentro().getCodCentro(), user.getCode());
			lotesNavidad = this.cestasNavidadService.findAll(usuarioSession.getCentro().getCodCentro(), esCentroCaprabo);

			if (lotesNavidad != null) {

				for (CestasNavidad cesta: lotesNavidad) {

					//Si la descripcion viene sin informar, consultamos en la tabla V_DATOS_DIARIO_ART
					if( cesta.getDescripcionLotePantalla()==null || cesta.getDescripcionLotePantalla().isEmpty())
						cesta.setDescripcionLotePantalla(this.cestasNavidadService.findDescrition(cesta.getCodArtLote()));

					StockPlataforma stockplataforma = new StockPlataforma();
					// Obtenemos el Stock desde PK_ARPMISUMI.P_OBT_STOCK_PLATAFORMA
					stockplataforma.setCodCentro(usuarioSession.getCentro().getCodCentro());
					stockplataforma.setCodArt(cesta.getCodArtLote());

					stockplataforma = this.stockPlataformaService.find(stockplataforma);
					cesta.setStock(stockplataforma.getStock());

				}

			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return lotesNavidad;
	}

	/**
	 * Metodo que devuelve un objeto CestaNavidad
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getCestaNavidad", method = RequestMethod.GET)
	public @ResponseBody CestasNavidad cestaNavidad( 
			@RequestParam(value = "codArtLote", required = true) Long codArtLote,
			HttpSession session, HttpServletResponse response) throws Exception {

		User usuarioSession = (User) session.getAttribute("user");
		CestasNavidad cestaNavidad= null;

		//B�squeda de Lotes de Navidad
		try {
			User user = (User) session.getAttribute("user");

			//Carga Lista con los lotes de Navidad desde la tabla CESTAS_NAVIDAD
			//Se calcula el código y la descripción que se va a mostrar en pantalla dependiendo si es un
			//centro de Caprabo o no
			boolean esCentroCaprabo = utilidadesCapraboService.esCentroCaprabo(usuarioSession.getCentro().getCodCentro(), user.getCode());
			cestaNavidad = this.cestasNavidadService.findOne(usuarioSession.getCentro().getCodCentro(), codArtLote, esCentroCaprabo);

			//Si no hay imagen enseñar articulos
			//if(cestaNavidad.getImagen2() == null){
			cestaNavidad.setLstArticulos(this.cestasNavidadService.findAllCestasNavidadArticulo(codArtLote));
			//}

			//Si la descripcion viene sin informa, consultamos en la tabla V_DATOS_DIARIO_ART
			if( cestaNavidad.getDescripcionLotePantalla()==null || cestaNavidad.getDescripcionLotePantalla().isEmpty())
				cestaNavidad.setDescripcionLotePantalla(this.cestasNavidadService.findDescrition(codArtLote));


			// Obtenemos el Stock desde PK_ARPMISUMI.P_OBT_STOCK_PLATAFORMA
			StockPlataforma stockplataforma = new StockPlataforma();
			stockplataforma.setCodCentro(usuarioSession.getCentro().getCodCentro());
			stockplataforma.setCodArt(codArtLote);

			stockplataforma = this.stockPlataformaService.find(stockplataforma);
			cestaNavidad.setStock(stockplataforma.getStock());


		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}

		return cestaNavidad;
	}
}