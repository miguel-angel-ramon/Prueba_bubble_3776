package es.eroski.misumi.control;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaDatosGuardados;
import es.eroski.misumi.model.pda.PdaDatosSfmCap;
import es.eroski.misumi.model.pda.PdaError;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.service.iface.VArtSfmService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class pdaP21SfmController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(pdaP21SfmController.class);

	@Value( "${tipoAprovisionamiento.descentralizado}" )
	private String tipoAprovisionamientoDescentralizado;

	@Resource 
	private MessageSource messageSource;

	@Autowired
	private VArtSfmService vArtSfmService;

	@Autowired
	private VAgruComerParamSfmcapService vAgruComerParamSfmcapService;

	@Autowired
	private VRelacionArticuloService vRelacionArticuloService;

	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;
	
	@Autowired
	private UtilidadesVegalsaService utilidadesVegalsaService;

	@RequestMapping(value = "/pdaP21Sfm", method = RequestMethod.GET)
	public String showForm(ModelMap model,
			@Valid final Long codArt,@Valid final String origen,
			@Valid final String procede,
			//			final Long codArt,final String origen,
			@RequestParam(value="codArtAnterior", required=false) String codArtAnterior, 
			@RequestParam(value="origenGISAE", required=false, defaultValue = "NO") String origenGISAE,
			@RequestParam(value="flgFacingCapacidad", required=false) String flgFacingCapacidad,
			HttpSession session,HttpServletRequest request, RedirectAttributes redirectAttributes) {

		PdaDatosSfmCap pdaDatosSfmCapResultado = new PdaDatosSfmCap();
		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();
		PdaError pdaError = new PdaError();

		//Obtenemos de sesión la información de usuario.
		User user = (User)session.getAttribute("user");
		try 
		{
			if (codArtAnterior != null && codArtAnterior.equals("anterior") && !origen.equals(Constantes.ORIGEN_CONSULTA)){
				//En este caso venimos de la ventana de error y no proviene de una consulta.
				//Tendremos que cargar la última visualizada por el usuario.
				pdaDatosSfmCapResultado = obtenerUltimaReferencia(session);

				//Rellenamos la referencia de caprabo. Si es Eroski, la de caprabo guardará la de eroski. Si no obtenemos la de caprabo
				pdaDatosSfmCapResultado.setCodArtCaprabo(pdaDatosSfmCapResultado.getCodArt());
				if(user.getCentro().esCentroCaprabo()){
					//Si es caprabo guardamos la descripción y articulo de caprabo.
					Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(),pdaDatosSfmCapResultado.getCodArt());
					String descArticuloCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);

					pdaDatosSfmCapResultado.setCodArtCaprabo(codArtCaprabo);
					pdaDatosSfmCapResultado.setDescArt(descArticuloCaprabo);
				}
			}else if (codArtAnterior != null && codArtAnterior.equals("anterior") && codArt != null && !codArt.equals("") && origen.equals(Constantes.ORIGEN_CONSULTA)){
				//En este caso venimos de la ventana de error y proviene de una consulta.
				//Tendremos que cargar la última visualizada por el usuario.
				redirectAttributes.addAttribute("codArt", codArt);
				return "redirect:pdaP12DatosReferencia.do";
			}else if (codArt != null && !codArt.equals("") && origen != null && origen.equals(Constantes.ORIGEN_CONSULTA)){
				//En este caso estamos llegando desde el enlace de la consulta.
				pdaDatosSfmCapResultado = obtenerResultado(codArt,user.getCentro().getCodCentro(),session, origen);

				//Rellenamos la referencia de caprabo. Si es Eroski, la de caprabo guardará la de eroski. Si no obtenemos la de caprabo
				pdaDatosSfmCapResultado.setCodArtCaprabo(pdaDatosSfmCapResultado.getCodArt());
				pdaDatosCab.setDescArtCab(pdaDatosSfmCapResultado.getDescArt());
				if(user.getCentro().esCentroCaprabo()){
					//Si es caprabo guardamos la descripción y articulo de caprabo.
					Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(),pdaDatosSfmCapResultado.getCodArt());
					String descArticuloCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);

					pdaDatosSfmCapResultado.setCodArtCaprabo(codArtCaprabo);
					pdaDatosSfmCapResultado.setDescArt(descArticuloCaprabo);

					pdaDatosCab.setDescArtCab(descArticuloCaprabo);
				}
				session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapResultado);
				pdaDatosCab.setOrigenConsulta(Constantes.ORIGEN_CONSULTA);				
				//Si se ha producido un error con la referencia lo mostramos.
				if (pdaDatosSfmCapResultado.getEsError() != null && !pdaDatosSfmCapResultado.getEsError().equals("")){
					pdaError.setDescError(pdaDatosSfmCapResultado.getEsError());
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA Referencia erronea");
					return "pda_p03_sfm_showMessage";
				}
			}else{
				List<PdaDatosSfmCap> lista = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");
				if (null != lista && !lista.isEmpty()){
					pdaDatosSfmCapResultado = (PdaDatosSfmCap) lista.get(0);

					//Refrescamos el total del objeto.
					pdaDatosSfmCapResultado.setTotal(lista.size());

					//Rellenamos la referencia de caprabo. Si es Eroski, la de caprabo guardará la de eroski. Si no obtenemos la de caprabo
					pdaDatosSfmCapResultado.setCodArtCaprabo(pdaDatosSfmCapResultado.getCodArt());
					if(user.getCentro().esCentroCaprabo()){
						//Si es caprabo guardamos la descripción y articulo de caprabo.
						Long codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(),pdaDatosSfmCapResultado.getCodArt());
						String descArticuloCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);

						pdaDatosSfmCapResultado.setCodArtCaprabo(codArtCaprabo);
						pdaDatosSfmCapResultado.setDescArt(descArticuloCaprabo);
					}					
				}
			}
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		pdaDatosCab.setOrigenGISAE(origenGISAE);
		model.addAttribute("procede", procede);
		model.addAttribute("pdaDatosCab", pdaDatosCab);
		model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
		if (null != pdaDatosSfmCapResultado.getFlgFacing() && pdaDatosSfmCapResultado.getFlgFacing().equals("S")) {
			if (null != pdaDatosSfmCapResultado.getFlgFacingCapacidad() && pdaDatosSfmCapResultado.getFlgFacingCapacidad().equals("S")) {
				//Se trata de un tipo Facing-Capacidad
				return "pda_p22_capacidad";
			} else {
				//Se trata de un tipo Facing puro
				return "pda_p27_facing";
			}
		}else{
			if (null == pdaDatosSfmCapResultado.getFlgCapacidad() || pdaDatosSfmCapResultado.getFlgCapacidad().equals("N")){
				//Se trata de un tipo SFM
				return "pda_p21_sfm";
			}else
			{
				//Se trata de un tipo Capacidad
				return "pda_p22_capacidad";
			}
		}
	}

	public PdaDatosSfmCap pdaP21Paginar(int posicion, String botPag, String nuevoSfm, String nuevaCobertura,
			HttpSession session) {

		PdaDatosSfmCap pdaDatosSfmCapResultado = new PdaDatosSfmCap();

		try 
		{
			//Paginamos a la página que corresponda.
			int posicionBusqueda= 1;
			if (botPag != null && botPag.equals("botonAnt"))
			{
				//Obtenemos el elemento de la posición anterior.
				posicionBusqueda = posicion-1;
			}
			else if (botPag != null && botPag.equals("botonSig"))
			{
				posicionBusqueda = posicion+1;
			}

			pdaDatosSfmCapResultado = obtenerReferenciaPorPosicion(session,posicionBusqueda);



		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}


		return pdaDatosSfmCapResultado;
	}

	public PdaDatosSfmCap validacionesSFM(int posicion, PdaDatosSfmCap pdaDatosSfmCap, PdaDatosSfmCap pdaDatosSfmCapActual,
			HttpSession session, String origenPantalla) {

		Locale locale = LocaleContextHolder.getLocale();
		List<PdaDatosSfmCap> listaSfmCap =new ArrayList<PdaDatosSfmCap>();

		try 
		{
			//Se cambia el control de modificado de ambos campos porque se recalcularán por javascript

			//Primero controlamos que no se hayan modificado ambos campos en cuyo caso se mostrará
			//un mensaje avisando de ello.
			//if (!pdaDatosSfmCap.getSfm().equals(pdaDatosSfmCapActual.getSfm()) && !pdaDatosSfmCap.getCoberturaSfm().equals(pdaDatosSfmCapActual.getCoberturaSfm()))
			//{
			//pdaDatosSfmCapActual.setEsError(this.messageSource.getMessage(
			//			"pda_p21_sfm.modificableSFM", null, locale));
			//}
			//Si se ha modificado alguno de los campos.
			//else 
			if (!pdaDatosSfmCap.getSfm().equals(pdaDatosSfmCapActual.getSfm()) || !pdaDatosSfmCap.getCoberturaSfm().equals(pdaDatosSfmCapActual.getCoberturaSfm()))
			{
				//Comprobamos el formato.
				try{
					//Aceptamos que venga separado tanto por comas como por puntos.
					Double sfmDouble = Double.parseDouble(pdaDatosSfmCap.getSfm().replace(',', '.'));
					Double limInfDouble = Double.parseDouble(pdaDatosSfmCapActual.getLmin().replace(',', '.'));
					Double limSupDouble = null;
					if (!(pdaDatosSfmCapActual.getLsf().equals(""))) {
						limSupDouble = Double.parseDouble(pdaDatosSfmCapActual.getLsf().replace(',', '.'));
					} 
					Double coberturaSfmDouble = Double.parseDouble(pdaDatosSfmCap.getCoberturaSfm().replace(',', '.'));
					Double ventaMediaDouble = Double.parseDouble(pdaDatosSfmCapActual.getVentaMedia().replace(',', '.'));

					//Se elimina el recálculo de SFM porque ya se hace desde pantalla
					//Si se ha modificado el SFM DIAS
					/*if (!pdaDatosSfmCap.getCoberturaSfm().equals(pdaDatosSfmCapActual.getCoberturaSfm()))
					{
						//Recalculamos el SFM antes de hacer las validaciones.
						if (ventaMediaDouble == 0)
						{
							sfmDouble = coberturaSfmDouble;
						}
						else
						{
							sfmDouble = coberturaSfmDouble*ventaMediaDouble;
						}
					}*/

					if (sfmDouble < limInfDouble)
					{
						//En este caso tenemos que mostrar el error de que el SFM no puede ser menor que el límite inferior
						pdaDatosSfmCapActual.setErrorIcono(this.messageSource.getMessage(
								"pda_p21_sfm.mensajeErrorLimiteInferior", null, locale));
						pdaDatosSfmCapActual.setIcono(Constantes.ICONO_ERROR);

						//Cargamos los valores originales tras el error.
						pdaDatosSfmCapActual.setSfm(pdaDatosSfmCapActual.getSfmOrig());
						pdaDatosSfmCapActual.setCoberturaSfm(pdaDatosSfmCapActual.getCoberturaSfmOrig());
					}
					else if ((pdaDatosSfmCapActual.getFlgFecsfm()!=null) && (pdaDatosSfmCapActual.getFlgFecsfm() == 1) && (limSupDouble != null) && (sfmDouble > limSupDouble))
					{
						//En este caso tenemos que mostrar el error de que el SFM no puede ser mayor que el límite superior
						pdaDatosSfmCapActual.setErrorIcono(this.messageSource.getMessage(
								"pda_p21_sfm.mensajeErrorLimiteSuperior", null, locale));
						pdaDatosSfmCapActual.setIcono(Constantes.ICONO_ERROR);

						//Cargamos los valores originales tras el error.
						pdaDatosSfmCapActual.setSfm(pdaDatosSfmCapActual.getSfmOrig());
						pdaDatosSfmCapActual.setCoberturaSfm(pdaDatosSfmCapActual.getCoberturaSfmOrig());
					}
					else
					{
						//En este caso no se muestra error alguno, se pone a modificado el icono y se recalcula el valor de cobertura.
						//Si se ha modificado el campo de SFM
						if (!pdaDatosSfmCap.getSfm().equals(pdaDatosSfmCapActual.getSfm()))
						{
							if (ventaMediaDouble == 0)
							{
								pdaDatosSfmCapActual.setCoberturaSfm(pdaDatosSfmCap.getSfm());
							}
							else
							{
								coberturaSfmDouble = sfmDouble/ventaMediaDouble;
							}
						}

						pdaDatosSfmCapActual.setSfm(Utilidades.convertirDoubleAString(sfmDouble.doubleValue(),"###0.000").replace(',', '.'));
						pdaDatosSfmCapActual.setCoberturaSfm(Utilidades.convertirDoubleAString(coberturaSfmDouble.doubleValue(),"###0.000").replace(',', '.'));

						if (!pdaDatosSfmCapActual.getSfm().equals(pdaDatosSfmCapActual.getSfmOrig()) ||
								!pdaDatosSfmCapActual.getCoberturaSfm().equals(pdaDatosSfmCapActual.getCoberturaSfmOrig())	)
						{
							pdaDatosSfmCapActual.setIcono(Constantes.ICONO_MODIFICADO);
						}
						else
						{
							pdaDatosSfmCapActual.setIcono("");
						}

						pdaDatosSfmCapActual.setErrorIcono("");
					}

					if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Sólo se guarda en la lista si no proviene de la consulta
						//Tenemos que guardar los cambios en sesión.
						if (session.getAttribute("listaSFMCap") != null)
						{
							listaSfmCap = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");
							listaSfmCap.set(posicion-1, pdaDatosSfmCapActual);
							session.setAttribute("listaSFMCap", listaSfmCap);
						}
					}else{
						session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapActual);
					}

					//Si va bien ponemos a vacío el error.
					pdaDatosSfmCapActual.setEsError("");
				}
				catch(Exception e) {
					pdaDatosSfmCapActual.setEsError(this.messageSource.getMessage(
							"pda_p21_sfm.formatoSFM", null, locale));
					return pdaDatosSfmCapActual;
				}
			}
			else
			{
				//Si no se ha modificado ningún campo y había algún error, lo quitamos.
				pdaDatosSfmCapActual.setEsError("");
				if (pdaDatosSfmCapActual.getIcono() != null && pdaDatosSfmCapActual.getIcono().equals(Constantes.ICONO_ERROR))
				{
					pdaDatosSfmCapActual.setIcono("");
					pdaDatosSfmCapActual.setErrorIcono("");

					if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Sólo se guarda en la lista si no proviene de la consulta
						//Tenemos que guardar los cambios en sesión.
						if (session.getAttribute("listaSFMCap") != null)
						{
							listaSfmCap = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");
							listaSfmCap.set(posicion-1, pdaDatosSfmCapActual);
							session.setAttribute("listaSFMCap", listaSfmCap);
						}else{
							session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapActual);
						}
					}
				}
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return pdaDatosSfmCapActual;
	}

	public PdaDatosSfmCap validacionesCapacidad(PdaDatosSfmCap pdaDatosSfmCap, PdaDatosSfmCap pdaDatosSfmCapActual, 
			HttpSession session, String origenPantalla) {

		Locale locale = LocaleContextHolder.getLocale();

		try 
		{
			//Comprobamos el formato.
			try{
				if (!pdaDatosSfmCap.getCapacidad().equals(pdaDatosSfmCapActual.getCapacidad()))
				{
					//No aceptamos decimales, el usuario tiene que introducir un entero.
					int sfmCapacidad = Integer.parseInt(pdaDatosSfmCap.getCapacidad());

					pdaDatosSfmCapActual.setCapacidad(String.valueOf(sfmCapacidad));

					//Obtenemos de sesión la información de usuario.
					User user = (User)session.getAttribute("user");

					//Obtenemos el facing
					pdaDatosSfmCapActual.setFacing(obtenerFacingCapacidad(user.getCentro().getCodCentro(),pdaDatosSfmCap.getCapacidad()));

					if (!pdaDatosSfmCapActual.getCapacidad().equals(pdaDatosSfmCapActual.getCapacidadOrig()))
					{
						pdaDatosSfmCapActual.setIcono(Constantes.ICONO_MODIFICADO);
					}
					else
					{
						pdaDatosSfmCapActual.setIcono("");
					}

					pdaDatosSfmCapActual.setErrorIcono("");

					//Si va bien ponemos a vacío el error.
					pdaDatosSfmCapActual.setEsError("");
				}
				else
				{
					//Si va bien ponemos a vacío el error.
					pdaDatosSfmCapActual.setEsError("");
				}

				if (Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Sólo se guarda en la lista si no proviene de la consulta
					session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapActual);
				}
			}
			catch(Exception e) {
				pdaDatosSfmCapActual.setEsError(this.messageSource.getMessage(
						"pda_p21_sfm.formatoCapacidad", null, locale));
				return pdaDatosSfmCapActual;
			}
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return pdaDatosSfmCapActual;
	}

	public PdaDatosSfmCap validacionesFacing(PdaDatosSfmCap pdaDatosSfmCap, PdaDatosSfmCap pdaDatosSfmCapActual, 
			HttpSession session, String origenPantalla) {

		Locale locale = LocaleContextHolder.getLocale();

		try 
		{
			//Comprobamos el formato.
			try{
				//Comprobamos si el facing NO es de textil
				if (pdaDatosSfmCap.getFacing() != null){
					if (!pdaDatosSfmCap.getFacing().equals(pdaDatosSfmCapActual.getFacing()))
					{
						//No aceptamos decimales, el usuario tiene que introducir un entero.
						int sfmFacing = Integer.parseInt(pdaDatosSfmCap.getFacing());

						pdaDatosSfmCapActual.setFacing(String.valueOf(sfmFacing));

						//Obtenemos de sesión la información de usuario.
						User user = (User)session.getAttribute("user");

						//Obtenemos el facing
						pdaDatosSfmCapActual.setFacing(pdaDatosSfmCap.getFacing());

						if (!pdaDatosSfmCapActual.getFacing().equals(pdaDatosSfmCapActual.getFacingOrig()))
						{
							pdaDatosSfmCapActual.setIcono(Constantes.ICONO_MODIFICADO);
						}
						else
						{
							pdaDatosSfmCapActual.setIcono("");
						}

						pdaDatosSfmCapActual.setErrorIcono("");

						//Si va bien ponemos a vacío el error.
						pdaDatosSfmCapActual.setEsError("");
					}
					else
					{
						//Si va bien ponemos a vacío el error.
						pdaDatosSfmCapActual.setEsError("");
					}
				}
				//Comprobamos si el facing es de textil
				if (pdaDatosSfmCap.getFacing_textil_0() != null || pdaDatosSfmCap.getFacing_textil_1() != null || pdaDatosSfmCap.getFacing_textil_2() != null){
					//Si hemos pulsado el check inactivar lote entonces ponemos los facing de todo el lote a cero.
					//No hace falta validar el facing por que los ponemos todos a cero
					int posicionTextil = (pdaDatosSfmCapActual.getPosicionTextil() - 1) * 3;
					if (pdaDatosSfmCap.isInactivarLote())
					{
						pdaDatosSfmCapActual.setInactivarLote(true);
						//Ponemos el facing de la madre a 0
						int facingMadre = Integer.parseInt(pdaDatosSfmCapActual.getFacing());
						if (facingMadre != 0){
							pdaDatosSfmCapActual.setFacing("0");
							pdaDatosSfmCapActual.setIcono(Constantes.ICONO_MODIFICADO);
						}
						//Ponemos el facing de las hijas a 0
						for(PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
							int facingHija = Integer.parseInt(hija.getFacing());
							if (facingHija != 0){
								hija.setFacing("0");
								hija.setIcono(Constantes.ICONO_MODIFICADO);
							}
						}
					}
					else
					{
						if (pdaDatosSfmCapActual.isInactivarLote()){
							//Ponemos el facing de las hijas que no están visibles
							//en la página como los facing originales
							for (int i = 0; i < pdaDatosSfmCapActual.getListaHijas().size(); i++){
								if ( i < posicionTextil || i > (posicionTextil + 2) ){
									PdaDatosSfmCap hija = pdaDatosSfmCapActual.getListaHijas().get(i);
									hija.setFacing(hija.getFacingOrig());
								}
							}
						}
						pdaDatosSfmCapActual.setInactivarLote(false);
					}
					//Comprobamos si se ha cambiado algún facing de las hijas
					if (pdaDatosSfmCapActual != null && pdaDatosSfmCapActual.getListaHijas() != null){
						//							if (((pdaDatosSfmCap.getFacing_textil_0() != null && pdaDatosSfmCapActual.getListaHijas().size() > posicionTextil && pdaDatosSfmCapActual.getListaHijas().get(posicionTextil) != null  && !pdaDatosSfmCap.getFacing_textil_0().equals(pdaDatosSfmCapActual.getListaHijas().get(posicionTextil).getFacing())) || Constantes.ICONO_ERROR.equals(pdaDatosSfmCapActual.getListaHijas().get(posicionTextil).getIcono())) ||
						//									((pdaDatosSfmCap.getFacing_textil_1() != null && pdaDatosSfmCapActual.getListaHijas().size() > (posicionTextil + 1) && pdaDatosSfmCapActual.getListaHijas().get(posicionTextil + 1) != null && !pdaDatosSfmCap.getFacing_textil_1().equals(pdaDatosSfmCapActual.getListaHijas().get(posicionTextil + 1).getFacing())) || Constantes.ICONO_ERROR.equals(pdaDatosSfmCapActual.getListaHijas().get(posicionTextil + 1).getIcono())) ||
						//									((pdaDatosSfmCap.getFacing_textil_2() != null && pdaDatosSfmCapActual.getListaHijas().size() > (posicionTextil + 2) && pdaDatosSfmCapActual.getListaHijas().get(posicionTextil + 2) != null && !pdaDatosSfmCap.getFacing_textil_2().equals(pdaDatosSfmCapActual.getListaHijas().get(posicionTextil + 2).getFacing())) || Constantes.ICONO_ERROR.equals(pdaDatosSfmCapActual.getListaHijas().get(posicionTextil + 2).getIcono())))
						//							{
						for (int i = 0; i < pdaDatosSfmCapActual.getListaHijas().size(); i++){
							PdaDatosSfmCap hija = pdaDatosSfmCapActual.getListaHijas().get(i);
							String facingTextil = null;
							if ( i == posicionTextil ){
								facingTextil = pdaDatosSfmCap.getFacing_textil_0();
							}
							else if ( i == (posicionTextil+1) ){
								facingTextil = pdaDatosSfmCap.getFacing_textil_1();
							}
							else if ( i == (posicionTextil+2) ){
								facingTextil = pdaDatosSfmCap.getFacing_textil_2();
							}
							else {
								facingTextil = hija.getFacing();
							}
							pdaDatosSfmCapActual = validarFacingTextilLote(pdaDatosSfmCapActual, session, i, facingTextil);
						}
						//								//Validamos el facing_textil_0
						//								posicionTextil = posicionTextil + 0;
						//								String facingTextil = pdaDatosSfmCap.getFacing_textil_0();
						//								pdaDatosSfmCapActual = validarFacingTextilLote(pdaDatosSfmCapActual, session, posicionTextil, facingTextil);
						//								
						//								//Validamos el facing_textil_1
						//								posicionTextil = posicionTextil + 1;
						//								facingTextil = pdaDatosSfmCap.getFacing_textil_1();
						//								pdaDatosSfmCapActual = validarFacingTextilLote(pdaDatosSfmCapActual, session, posicionTextil, facingTextil);
						//								
						//								//Validamos el facing_textil_2
						//								posicionTextil = posicionTextil + 1;
						//								facingTextil = pdaDatosSfmCap.getFacing_textil_2();
						//								pdaDatosSfmCapActual = validarFacingTextilLote(pdaDatosSfmCapActual, session, posicionTextil, facingTextil);

						//Si alguna hija tiene ICONO_ERROR
						//entonces pongo el mensaje de error en la madre
						boolean hijasFacingIconoError = false;
						for (PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
							hijasFacingIconoError = hijasFacingIconoError || Constantes.ICONO_ERROR.equals(hija.getIcono());
						}
						if (hijasFacingIconoError){
							//Pongo el mensaje de error en la madre
							pdaDatosSfmCapActual.setIcono(Constantes.ICONO_ERROR);
							pdaDatosSfmCapActual.setErrorIcono(this.messageSource.getMessage("pda_p27_facing.formatoFacing", null, locale));
							pdaDatosSfmCapActual.setEsError(this.messageSource.getMessage("pda_p27_facing.formatoFacing", null, locale));
						}
						else{
							//Quito el mensaje de error en la madre
							if (Constantes.ICONO_ERROR.equals(pdaDatosSfmCapActual.getIcono())){
								pdaDatosSfmCapActual.setIcono("");
								pdaDatosSfmCapActual.setErrorIcono("");
								pdaDatosSfmCapActual.setEsError("");
							}
						}

						//Si todas las hijas tienen Facing igual a 0 entonces
						//pongo el Facing de la madre a 0
						//si no, si la madre tiene facing igual a 0 lo pongo a 1
						if (!hijasFacingIconoError){
							boolean hijasFacingIgualA0 = true;
							for (PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
								int sfmFacingHija = Integer.parseInt(hija.getFacing());
								hijasFacingIgualA0 = hijasFacingIgualA0 && (sfmFacingHija == 0);
							}
							if (hijasFacingIgualA0){
								pdaDatosSfmCapActual.setFacing("0");
								pdaDatosSfmCapActual.setIcono(Constantes.ICONO_MODIFICADO);
							}
							else{
								int sfmFacingMadre = Integer.parseInt(pdaDatosSfmCapActual.getFacing());
								if (sfmFacingMadre == 0){
									pdaDatosSfmCapActual.setFacing("1");
									pdaDatosSfmCapActual.setIcono(Constantes.ICONO_MODIFICADO);
								}
							}
						}
						//							}
						//							else
						//							{
						//								//Si va bien ponemos a vacío el error.
						//								pdaDatosSfmCapActual.setEsError("");
						//							}
					}
				}

				if (Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Sólo se guarda en la lista si no proviene de la consulta
					session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapActual);
				}
			}
			catch(Exception e) {
				pdaDatosSfmCapActual.setEsError(this.messageSource.getMessage(
						"pda_p21_sfm.formatoFacing", null, locale));
				return pdaDatosSfmCapActual;
			}
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		return pdaDatosSfmCapActual;
	}

	public PdaDatosSfmCap validarFacingTextilLote(PdaDatosSfmCap pdaDatosSfmCapActual, 
			HttpSession session, int posicionTextil, String facingTextil){

		if (pdaDatosSfmCapActual != null && pdaDatosSfmCapActual.getListaHijas() != null && pdaDatosSfmCapActual.getListaHijas().size() > posicionTextil){
			PdaDatosSfmCap hija = pdaDatosSfmCapActual.getListaHijas().get(posicionTextil);
			try{
				if ( (facingTextil != null && !facingTextil.equals(hija.getFacing())) || Constantes.ICONO_ERROR.equals(hija.getIcono())){
					//No aceptamos decimales, el usuario tiene que introducir un entero.
					int sfmFacingHija = Integer.parseInt(facingTextil);
					int sfmFacingMadre = Integer.parseInt(pdaDatosSfmCapActual.getFacing());

					hija.setFacing(String.valueOf(sfmFacingHija));

					//Obtenemos de sesión la información de usuario.
					User user = (User)session.getAttribute("user");

					//Obtenemos el facing
					hija.setFacing(facingTextil);

					if (!hija.getFacing().equals(hija.getFacingOrig()))
					{
						hija.setIcono(Constantes.ICONO_MODIFICADO);
					}
					else
					{
						hija.setIcono("");
					}

					pdaDatosSfmCapActual.setErrorIcono("");

					//Si va bien ponemos a vacío el error.
					pdaDatosSfmCapActual.setEsError("");
				}
			}
			catch(Exception e) {
				hija.setFacing(facingTextil);
				hija.setIcono(Constantes.ICONO_ERROR);
			}
		}

		return pdaDatosSfmCapActual;
	}

	@RequestMapping(value = "/pdaP21Sfm", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result,
			ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
			@Valid final PdaDatosSfmCap pdaDatosSfmCap,
			@Valid final String procede,
			HttpServletRequest request,
			HttpServletResponse response) {

		PdaDatosSfmCap pdaDatosSfmCapResultado = new PdaDatosSfmCap();
		PdaDatosSfmCap pdaDatosSfmCapActual = new PdaDatosSfmCap();
		PdaError pdaError = new PdaError();
		Locale locale = LocaleContextHolder.getLocale();
		int posicion = 0;
		Long referenciaActual = null;
		String resultado = "pda_p21_sfm";
		String botPag = "";
		String origenPantalla = Constantes.ORIGEN_CONSULTA;

		try 
		{
			//Obtenemos de sesión la información de usuario.
			User user = (User)session.getAttribute("user");
			if (request.getParameter("posicion") != null){
				posicion = Integer.parseInt(request.getParameter("posicion"));
			}
			if (request.getParameter("referenciaActual") != null && !"".equals(request.getParameter("referenciaActual"))){
				referenciaActual = new Long(request.getParameter("referenciaActual"));
			}
			if (request.getParameter("actionAnt") != null || request.getParameter("actionSig") != null ||
					request.getParameter("actionAntTextil") != null || request.getParameter("actionSigTextil") != null ||
					request.getParameter("actionSave") != null || request.getParameter("actionReset") != null){

				//Se ha pulsado algún botón tendremos que realizar las validaciones.
				if (!Constantes.ORIGEN_CONSULTA.equals(request.getParameter("origenConsulta"))){
					pdaDatosSfmCapActual = (PdaDatosSfmCap) session.getAttribute("sfmCapReferenciaConsulta");
				}else{
					if (referenciaActual == null || "".equals(referenciaActual)){ //Si no le proporciona referencia hay que hallar la guardada en sesión
						//Se utiliza de este modo para volver desde la pantalla guardado
						pdaDatosSfmCapActual = obtenerReferenciaActualConsulta(session);
					}else{
						origenPantalla = Constantes.ORIGEN_CONSULTA;
						pdaDatosSfmCapActual = (PdaDatosSfmCap) session.getAttribute("sfmCapReferenciaConsulta");
					}
				}

				if (pdaDatosSfmCapActual.getCodArt()!= null){
					if (pdaDatosSfmCapActual.getFlgFacing().equals("S")){
						//Se trata de un tipo Facing, y tendremos que realizar las validaciones correspondientes.
						pdaDatosSfmCapActual = this.validacionesFacing(pdaDatosSfmCap, pdaDatosSfmCapActual, session, origenPantalla);

						//Si se ha producido un error con la validación lo mostramos.
						if (pdaDatosSfmCapActual.getEsError() != null && !pdaDatosSfmCapActual.getEsError().equals("")){
							//Si es un textil con hijas
							if (pdaDatosSfmCapActual.getListaHijas() != null && pdaDatosSfmCapActual.getListaHijas().size() > 0){
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapActual);
								return "pda_p27_facing";
							}
							pdaError.setDescError(pdaDatosSfmCapActual.getEsError());
							model.addAttribute("pdaError", pdaError);
							model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapActual);
							model.addAttribute("pdaDatosCab", pdaDatosCab);
							logger.info("PDA Error Validacion Facing");
							return "pda_p03_sfm_showMessage";
						}
					}else{
						if (pdaDatosSfmCapActual.getFlgCapacidad().equals("N")){
							//Se trata de un tipo SFM, y tendremos que realizar las validaciones correspondientes.
							pdaDatosSfmCapActual = this.validacionesSFM(posicion, pdaDatosSfmCap, pdaDatosSfmCapActual, session, origenPantalla);

							//Si se ha producido un error con la validación lo mostramos.
							if (pdaDatosSfmCapActual.getEsError() != null && !pdaDatosSfmCapActual.getEsError().equals("")){
								pdaError.setDescError(pdaDatosSfmCapActual.getEsError());
								model.addAttribute("pdaError", pdaError);
								model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Error Validacion SFM");
								return "pda_p03_sfm_showMessage";
							}
						}
						else{
							//Se trata de un tipo Capacidad, y tendremos que realizar las validaciones correspondientes.
							pdaDatosSfmCapActual = this.validacionesCapacidad(pdaDatosSfmCap, pdaDatosSfmCapActual, session, origenPantalla);

							//Si se ha producido un error con la validación lo mostramos.
							if (pdaDatosSfmCapActual.getEsError() != null && !pdaDatosSfmCapActual.getEsError().equals("")){
								pdaError.setDescError(pdaDatosSfmCapActual.getEsError());
								model.addAttribute("pdaError", pdaError);
								model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapActual);
								model.addAttribute("pdaDatosCab", pdaDatosCab);
								logger.info("PDA Error Validacion Capacidad");
								return "pda_p03_sfm_showMessage";
							}
						}
					}
				}
			}

			//Controlamos si se ha pulsado algún botón de paginación.
			if (request.getParameter("actionAnt") != null || request.getParameter("actionSig") != null){
				
				if (request.getParameter("actionAnt") != null){
					botPag = "botonAnt";
				}
				else if (request.getParameter("actionSig") != null){
					botPag = "botonSig";
				}

				pdaDatosSfmCapResultado = this.pdaP21Paginar(posicion, botPag, pdaDatosSfmCap.getSfm(), pdaDatosSfmCap.getCoberturaSfm(), session);

				if (pdaDatosSfmCapResultado.getCodArt()!= null){
					if (pdaDatosSfmCapResultado.getFlgFacing().equals("S")){
						if (null != pdaDatosSfmCapResultado.getFlgFacingCapacidad() && pdaDatosSfmCapResultado.getFlgFacingCapacidad().equals("S")) {
							//Se trata de un tipo Facing-Capacidad
							return "pda_p22_capacidad";
						} else {
							//Se trata de un tipo Facing puro
							return "pda_p27_facing";
						}
					}else{
						if (pdaDatosSfmCapResultado.getFlgCapacidad().equals("N")){
							//Se trata de un tipo SFM
							resultado = "pda_p21_sfm";
						}
						else{
							//Se trata de un tipo Capacidad
							resultado = "pda_p22_capacidad";
						}
					}
				}

				//Para el control de CAPTURA RESTOS
				model.addAttribute("procede", procede);
				
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);

				return resultado;
			}
			//Controlamos si se ha pulsado algún botón de paginación de textil.
			if (request.getParameter("actionAntTextil") != null || request.getParameter("actionSigTextil") != null){
				if (request.getParameter("actionAntTextil") != null){
					pdaDatosSfmCapActual.setPosicionTextil(pdaDatosSfmCapActual.getPosicionTextil()-1);
				}
				else if (request.getParameter("actionSigTextil") != null){
					pdaDatosSfmCapActual.setPosicionTextil(pdaDatosSfmCapActual.getPosicionTextil()+1);
				}

				//Guardamos en sesión el objeto de la referencia introducida.
				session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapActual);

				//Se trata de un tipo SFM
				resultado = "pda_p27_facing";

				//Para el control de CAPTURA RESTOS
				model.addAttribute("procede", procede);
				
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapActual);

				return resultado;
			}

			if (request.getParameter("actionReset") != null){
				pdaDatosSfmCapResultado = this.obtenerReferenciaPorPosicion(session,posicion);
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
				return "pda_p23_limpiarSfmCapacidad";
			}

			if (request.getParameter("actionSave") != null){
				if (!Constantes.ORIGEN_CONSULTA.equals(request.getParameter("origenConsulta"))){
					PdaDatosSfmCap datosGuardados = this.guardarRegistros(session, pdaDatosSfmCapActual, Constantes.ORIGEN_PRINCIPAL);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					model.addAttribute("pdaDatosSfmCap", datosGuardados);
					if (pdaDatosSfmCapActual.getFlgFacing().equals("S")){
						if (null != pdaDatosSfmCapActual.getFlgFacingCapacidad() && pdaDatosSfmCapActual.getFlgFacingCapacidad().equals("S")) {
							//Se trata de un tipo Facing-Capacidad
							return "pda_p22_capacidad";
						} else {
							//Se trata de un tipo Facing puro
							return "pda_p27_facing";
						}
					}else{
						if (pdaDatosSfmCapActual.getFlgCapacidad().equals("N")){
							//Se trata de un tipo SFM
							resultado = "pda_p21_sfm";
						}
						else{
							//Se trata de un tipo Capacidad
							resultado = "pda_p22_capacidad";
						}
					}
					return resultado;
				}else{
					PdaDatosSfmCap datosGuardados = this.guardarRegistros(session, pdaDatosSfmCapActual, Constantes.ORIGEN_CONSULTA);
					model.addAttribute("pdaDatosCab", pdaDatosCab);

					//Si el guardado es correcto vuelve a la pantalla de consulta.
					//En otro caso volverá a la pantalla de SFM mostrando el error.
					if (session.getAttribute("sfmCapReferenciaConsulta") != null){
						pdaDatosCab.setCodArtCab(String.valueOf(pdaDatosSfmCapActual.getCodArt()));
						pdaDatosCab.setCodArtCaprabo(pdaDatosSfmCapActual.getCodArtCaprabo().toString());
						pdaDatosCab.setOrigenConsulta(Constantes.ORIGEN_CONSULTA);
						model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapActual);

						if (null != datosGuardados.getIcono() && datosGuardados.getIcono().equals(1)){
							if (pdaDatosSfmCapActual.getFlgFacing().equals("S")){
								if (null != pdaDatosSfmCapResultado.getFlgFacingCapacidad() && pdaDatosSfmCapResultado.getFlgFacingCapacidad().equals("S")) {
									//Se trata de un tipo Facing-Capacidad
									return "pda_p22_capacidad";
								} else {
									//Se trata de un tipo Facing puro
									return "pda_p27_facing";
								}
							}else{
								if (pdaDatosSfmCapActual.getFlgCapacidad().equals("N")){
									//Se trata de un tipo SFM
									resultado = "pda_p21_sfm";
								}
								else{
									//Se trata de un tipo Capacidad
									resultado = "pda_p22_capacidad";
								}
							}
							return resultado;
						}else{				
							redirectAttributes.addAttribute("codArt", pdaDatosSfmCapActual.getCodArt());
							redirectAttributes.addAttribute("guardadoSfm", "SI");
							
							if (null != procede && !procede.isEmpty()){
								return "redirect:"+procede+".do";
							}else{
								return "redirect:pdaP12DatosReferencia.do";	
							}
							
						}

					}
				}
				return resultado;
			}

			if (request.getParameter("actionPrev") != null){
				redirectAttributes.addAttribute("codArt", request.getParameter("referenciaActual"));
				redirectAttributes.addAttribute("origenGISAE", request.getParameter("origenGISAE"));
				if (null != procede && !procede.isEmpty()){
					redirectAttributes.addAttribute("guardadoSFM", "S");
					return "redirect:"+procede+".do";
				}else{
					return "redirect:pdaP12DatosReferencia.do";	
				}
			}

			//Controlamos que me llega la referencia
			if (pdaDatosCab.getCodArtCab() != null && !pdaDatosCab.getCodArtCab().equals("")){
				//Llamamos al método que nos devuelve la referencia, con los controles, 
				//por si se trata de balanza, etiqueta propia de Eroski, de la tabla EANS o código de referencia normal.
				PdaArticulo pdaArticulo = obtenerReferenciaTratada(pdaDatosCab.getCodArtCab());
				String codigoError = pdaArticulo.getCodigoError();																				

				if (codigoError != null && codigoError.equals(Constantes.REF_ERRONEA_EAN)){
					pdaError.setDescError(this.messageSource.getMessage(
							"pda_p12_datosReferencia.noExisteReferencia", null, locale));
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
					model.addAttribute("pdaDatosCab",  new PdaDatosCabecera());
					logger.info("PDA Referencia erronea");
					return "pda_p03_sfm_showMessage";
				}

				//La función obtenerReferenciaTratada devuelve un código de artículo transformado a partir del código que haya
				//introducido el usuario en la pantalla. El código introducido puede ser de tipo EAN, caprabo o Eroski. Un código
				//de tipo EAN es un código que empieza por "#" que corresponde a un código de barras. Como los mismos productos de
				//distintos centros Eroski o caprabo usan el mismo código de barras, los códigos EAN serán iguales tanto para caprabo
				//como para Eroski.En el caso de introducir un código que no sea de tipo EAN (eroski o caprabo), o lo que es lo mismo,
				//un código que no tiene un "#" delante, la función devuelve el código de caprabo o eroski tal cual se ha introducido 
				//en la función. Si se introduce un EAN devuelve el código de EROSKI correspondiente a ese EAN. En la aplicación queremos
				//trabajar con  códigos de Eroski, por lo que si lo introducido en pantalla es un EAN o un código de eroski la función devolverá
				//justo el código que necesitamos. En el caso de introducir un código caprabo, al devolver el mismo código caprabo,
				//necesitaremos realizar la transformación correspondiente a código Eroski.Aunque sepamos que el centro es de tipo
				//caprabo, hay dos posibilidades, que lo introducido sea un EAN y baste con la función obtenerReferenciaTratada, pero
				//sea necesario obtener el código de caprabo, o que lo introducido sea un código caprabo, y necesitemos el código de
				//eroski para trabajar. En el caso de ser un EAN, si llega hasta este punto significa que ha obtenido el código de eroski,
				//porque si no hubiera devuelto un error. El EAN introducido y el código eroski serán distintos en el caso de haber 
				//introducido un EAN y en el caso de haber introducido un código caprabo iguales. De este modo sabremos que transformación hacer.

				//En principio el código de artículo será el que devuelva la función. Más tarde se tratará si es de caprabo
				Long codArtEroski = pdaArticulo.getCodArt();

				//Se calcula aunque sea un centro de eroski. En el caso de ser un centro de eroski codArtEroski = codArtCaprabo.
				//En caso contrario, codArtCaprabo != codArtEroski. Como en la descripción de la pda sale codArt + descripción
				//utilizamos el codArtCaprabo para devolver el código de artículo. En el caso de ser centro Eroski devolverá la
				//referencia de eroski y en el caso de ser caprabo la de caprabo
				Long codArtCaprabo = pdaArticulo.getCodArt();

				boolean isCaprabo = false;
				String descripCaprabo = "";
				
				//Si es un centro de caprabo
				if(user.getCentro().getEsCentroCaprabo()){
					//Se indica que el centro es de tipo caprabo
					isCaprabo = true;

					//IF: Si lo introducido es una referencia de caprabo, conseguir descripción caprabo y código eroski
					//ELSE: Si lo introducido es un EAN, conseguir el código caprabo y la descripción caprabo
					if( pdaDatosCab.getCodArtCab().equals(pdaArticulo.getCodArt().toString())){
						//La referencia de caprabo se obtiene fuera del if igual que se obtendría aquí. Por eso
						//está comentado

						//codArtCaprabo = pdaArticulo.getCodArt();

						//Se hace la conversión de caprabo a Eroski
						codArtEroski = utilidadesCapraboService.obtenerCodigoEroski(user.getCentro().getCodCentro(),codArtCaprabo);
						//Se obtiene la descripción de caprabo
						descripCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);
					}else{
						//La referencia de eroski se obtiene fuera del if igual que se obtendría aquí. Por eso
						//está comentado

						//codArtEroski = pdaArticulo.getCodArt();

						//Se obtiene el código de artículo de caprabo a partir de esa referencia eroski
						codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(user.getCentro().getCodCentro(), codArtEroski);

						//Se obtiene la descripción de caprabo a partir del código caprabo
						descripCaprabo = utilidadesCapraboService.obtenerDescArtCaprabo(codArtCaprabo);
					}

					//Insertar la descripción de caprabo en la cabecera
					pdaDatosCab.setDescArtCab(descripCaprabo);	
				}

				//Es posible que al introducir un código caprabo, por fallo de la bd, no tenga un código eroski relacionado
				//por eso hay que asegurarse de tratar el posible error.
				if(codArtEroski != null){
					//Insertar referencia caprabo, para que se pueda ver en la descripción de la pistola. En el caso de ser
					//centro de eroski, la referencia caprabo tendrá el valor de la referencia eroski y también se verá bien.
					pdaDatosCab.setCodArtCaprabo(codArtCaprabo.toString());		

					//Insertar la referencia de eroski
					pdaDatosCab.setCodArtCab(codArtEroski.toString());					

					pdaDatosSfmCapResultado = obtenerResultado(new Long(pdaDatosCab.getCodArtCab()),user.getCentro().getCodCentro(),session,Constantes.ORIGEN_PRINCIPAL);
					
					//Insertar 
					pdaDatosSfmCapResultado.setCodArt(codArtEroski);
					pdaDatosSfmCapResultado.setCodArtCaprabo(codArtCaprabo);
					
					if(user.getCentro().esCentroCaprabo()){
						pdaDatosSfmCapResultado.setDescArt(descripCaprabo);
					}else{
						//Insertar la descripción de eroski
						pdaDatosCab.setDescArtCab(pdaDatosSfmCapResultado.getDescArt());	
					}
					
					if (pdaDatosSfmCapResultado.getEsError() != null && !pdaDatosSfmCapResultado.getEsError().equals("")){
						pdaError.setDescError(pdaDatosSfmCapResultado.getEsError());
						model.addAttribute("pdaError", pdaError);
						model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
						model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
						logger.info("PDA Referencia erronea");
						return "pda_p03_sfm_showMessage";
					}			
				}else{
					pdaError.setDescError(this.messageSource.getMessage("pda_p21_datosReferencia.noExistenDatos", null, locale));
					model.addAttribute("pdaError", pdaError);
					model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
					model.addAttribute("pdaDatosCab", pdaDatosCab);
					logger.info("PDA NO EXISTE REFERENCIA EROSKI PARA REFERENCIA CAPRABO");
					return "pda_p03_showMessage";	
				}
			}else{
				//Si no me llega la referencia mostramos el mensaje de que no ha introducido ninguna referencia.
				pdaError.setDescError(this.messageSource.getMessage(
						"pda_p21_sfm.referenciaVacia", null, locale));
				model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pdaError", pdaError);
				logger.info("PDA Referencia no introducida");
				return "pda_p03_sfm_showMessage";
			}
		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		model.addAttribute("pdaDatosSfmCap", pdaDatosSfmCapResultado);
		model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

		if (pdaDatosSfmCapResultado.getFlgFacing()!=null && pdaDatosSfmCapResultado.getFlgFacing().equals("S")){
			if (null != pdaDatosSfmCapResultado.getFlgFacingCapacidad() && pdaDatosSfmCapResultado.getFlgFacingCapacidad().equals("S")) {
				//Se trata de un tipo Facing-Capacidad
				return "pda_p22_capacidad";
			} else {
				//Se trata de un tipo Facing puro
				return "pda_p27_facing";
			}
		}else{
			if (pdaDatosSfmCapResultado.getFlgCapacidad()!=null && pdaDatosSfmCapResultado.getFlgCapacidad().equals("N")){
				//Se trata de un tipo SFM
				return "pda_p21_sfm";
			}else{
				//Se trata de un tipo Capacidad
				return "pda_p22_capacidad";
			}
		}
	}

	private PdaDatosSfmCap obtenerUltimaReferencia(HttpSession session) throws Exception{


		PdaDatosSfmCap pdaDatosSfmCap = new PdaDatosSfmCap();
		List<PdaDatosSfmCap> listaSfmCap =new ArrayList<PdaDatosSfmCap>();
		HashMap<String, Integer> mapClavesSFMCap = new HashMap<String, Integer>();
		int indiceRegistroGuardado = 0;
		String codArt = "";

		if (session.getAttribute("hashMapClavesSFMCap") != null && session.getAttribute("listaSFMCap") != null)
		{
			mapClavesSFMCap = (HashMap<String, Integer>) session.getAttribute("hashMapClavesSFMCap");

			listaSfmCap = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");

			//Obtenemos la referencia a buscar.
			if (session.getAttribute("ultimaRef") != null)
			{
				codArt = (String) session.getAttribute("ultimaRef");

				if (mapClavesSFMCap.get(codArt) != null){ //Si la referencia se ha eliminado se mostrará la primera referencia
					indiceRegistroGuardado = ((Integer)mapClavesSFMCap.get(codArt)).intValue();
				}
				pdaDatosSfmCap = (PdaDatosSfmCap) listaSfmCap.get(indiceRegistroGuardado);				
			}
		}

		return pdaDatosSfmCap;
	}

	private PdaDatosSfmCap obtenerReferenciaPorPosicion(HttpSession session, int posicion) throws Exception{


		PdaDatosSfmCap pdaDatosSfmCap = new PdaDatosSfmCap();
		List<PdaDatosSfmCap> listaSfmCap =new ArrayList<PdaDatosSfmCap>();
		HashMap<String, Integer> mapClavesSFMCap = new HashMap<String, Integer>();

		if (session.getAttribute("hashMapClavesSFMCap") != null && session.getAttribute("listaSFMCap") != null)
		{
			mapClavesSFMCap = (HashMap<String, Integer>) session.getAttribute("hashMapClavesSFMCap");

			listaSfmCap = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");

			pdaDatosSfmCap = (PdaDatosSfmCap) listaSfmCap.get(posicion-1);

			//Refrescamos el total del objeto.
			pdaDatosSfmCap.setTotal(mapClavesSFMCap.size());

			//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
			session.setAttribute("ultimaRef", String.valueOf(pdaDatosSfmCap.getCodArt()));
		}

		return pdaDatosSfmCap;
	}

	private PdaDatosSfmCap obtenerReferenciaActualConsulta(HttpSession session) throws Exception{

		PdaDatosSfmCap pdaDatosSfmCap = new PdaDatosSfmCap();

		pdaDatosSfmCap = (PdaDatosSfmCap) session.getAttribute("sfmCapReferenciaConsulta");

		return pdaDatosSfmCap;
	}

	private String obtenerFacingCapacidad(Long codCentro, String strCapacidad)  throws Exception{

		String strFacing = "";

		try{

			//Calculamos el correspondiente Facing.
			Float porcentajeCapacidad = obtenerPorcentajeCapacidad(codCentro);
			Float facing;

			int sfmCapacidad = Integer.parseInt(strCapacidad);

			if (porcentajeCapacidad != null)
			{
				facing = porcentajeCapacidad*sfmCapacidad;
				if (facing <1)
				{
					strFacing = String.valueOf(1);
				}
				else
				{
					strFacing = Utilidades.convertirDoubleAString(facing.doubleValue(),"###0");
				}
			}

		}catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return strFacing;

	}

	private PdaDatosSfmCap obtenerResultado(Long codArt, Long codCentro,HttpSession session, String origen) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosSfmCap pdaDatosSfmCap = new PdaDatosSfmCap();
		pdaDatosSfmCap.setCodArt(codArt);
		boolean mostrarError = false;

		VDatosDiarioArt vDatosDiarioArtResul = obtenerDiarioArt(codArt);

		if (null != vDatosDiarioArtResul){
			//Obtenemos la descripción del artículo.
			if (vDatosDiarioArtResul != null && vDatosDiarioArtResul.getDescripArt() != null)
			{
				pdaDatosSfmCap.setDescArt(vDatosDiarioArtResul.getDescripArt());
			}

			//Comprobamos si se trata de un centro de autoservicio.
			pdaDatosSfmCap.setEsAutoServicio(esAutoServicio(codCentro));

			//Obtenemos la información a partir de la referencia.
			SfmCapacidadFacing sfmCapacidad = null;
			List<VArtSfm> listaSfm = null;
			VArtSfm vArtSfm = new VArtSfm();
			vArtSfm.setCodLoc(codCentro);
			vArtSfm.setCodArticulo(codArt);

			ReferenciasCentro referenciasCentro = new ReferenciasCentro();
			referenciasCentro.setCodArt(codArt);
			referenciasCentro.setCodCentro(codCentro);
			
			User user = (User) session.getAttribute("user");
			boolean tratamientoVegalsaAux = utilidadesVegalsaService.esTratamientoVegalsa(user.getCentro(), referenciasCentro.getCodArt());
			//Comprobación de si una referencia es FFPP 
			referenciasCentro = this.vRelacionArticuloService.obtenerFfppActivaOUnitaria(referenciasCentro, tratamientoVegalsaAux);

			//Rellenamos si se trata de un FFPP
			pdaDatosSfmCap.setEsFFPP(referenciasCentro.isTieneUnitaria());
			//Cambio para que compile la llamada pasando el flag, pediente resto implementacion en pistola
			VAgruComerParamSfmcap vAgruComerParamSfmcap = new VAgruComerParamSfmcap();
			vAgruComerParamSfmcap.setCodCentro(user.getCentro().getCodCentro());
			vAgruComerParamSfmcap.setGrupo1(vDatosDiarioArtResul.getGrupo1());
			vAgruComerParamSfmcap.setGrupo2(vDatosDiarioArtResul.getGrupo2());
			vAgruComerParamSfmcap.setGrupo3(vDatosDiarioArtResul.getGrupo3());
			vAgruComerParamSfmcap.setGrupo4(vDatosDiarioArtResul.getGrupo4());
			vAgruComerParamSfmcap.setGrupo5(vDatosDiarioArtResul.getGrupo5());
			String tipoEstructura = this.vAgruComerParamSfmcapService.findTipoEstructura(vAgruComerParamSfmcap);

			boolean bolFlgFacing = Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoEstructura) || Constantes.SFMCAP_TIPO_LISTADO_FACCAP.equals(tipoEstructura);
			 //Flag que indica si una estructura es Facing puro o de Facing-capacidad (flgFacingCapacidad igual a S) 
			String flgFacingCapacidad = Constantes.SFMCAP_TIPO_LISTADO_FACCAP.equals(tipoEstructura) ? "S" : "N";
			
			if (bolFlgFacing && Constantes.TIPO_COMPRA_VENTA_SOLO_VENTA.equals(vDatosDiarioArtResul.getTipoCompraVenta())){
				pdaDatosSfmCap.setEsError(this.messageSource.getMessage(
						"pda_p21_sfm.errorReferenciaSoloVenta", null, locale));
				mostrarError = true;
			} else if (bolFlgFacing) {
				sfmCapacidad = this.vArtSfmService.consultaFac(vArtSfm);
				this.vArtSfmService.aplicarCorreccionConsultaFacConLotes(vArtSfm, sfmCapacidad);
			}  else if (Constantes.SFMCAP_TIPO_LISTADO_SFM.equals(tipoEstructura)){
				sfmCapacidad = this.vArtSfmService.consultaSfm(vArtSfm);
			} else if(Constantes.SFMCAP_TIPO_LISTADO_CAP.equals(tipoEstructura)){
				sfmCapacidad = this.vArtSfmService.consultaCap(vArtSfm);
			}

			if (!mostrarError && sfmCapacidad != null){
				if (sfmCapacidad.getFlgFacing() != null){
					//El flag de facing me indica que tendrá que abrir la pantalla de facing
					pdaDatosSfmCap.setFlgFacing(sfmCapacidad.getFlgFacing());
				}

				if (sfmCapacidad.getFlgCapacidad() != null){
					//El flag de capacidad me indica la pantalla que tendrá que abrir.
					pdaDatosSfmCap.setFlgCapacidad(sfmCapacidad.getFlgCapacidad());
				}

				if (sfmCapacidad.getDatos() != null && sfmCapacidad.getDatos().size()>0){
					listaSfm = sfmCapacidad.getDatos();

					if (listaSfm != null) {
						//Obtenemos el primer y único elemento de la lista.
						VArtSfm campo = new VArtSfm();
						campo = (VArtSfm)listaSfm.get(0);

						//Comprobamos si ya teníamos guardada la referencia en sesión

						if (sfmCapacidad.getFlgFacing() != null && Constantes.FLG_SI_FACING_CENTRO.equals(sfmCapacidad.getFlgFacing())){ 
							//Facing
							//Control de sólo venta y tipo expositor para sacar por pantalla los errores correspondientes
							VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
							vDatosDiarioArt.setCodArt(codArt);
							if (this.vArtSfmService.esReferenciaExpositor(campo)) {
								pdaDatosSfmCap.setEsError(this.messageSource.getMessage(
										"pda_p21_sfm.errorReferenciaExpositor", null, locale));
								mostrarError = true;
							}else{
								if (campo.getFacingCentro() != null) {
									pdaDatosSfmCap.setFacing(Utilidades.convertirDoubleAString(campo.getFacingCentro().doubleValue(),"###0"));
								}
								if (campo.getFacingCentro() != null) {
									pdaDatosSfmCap.setFacingOrig(Utilidades.convertirDoubleAString(campo.getFacingCentro().doubleValue(),"###0"));
								}
								if (campo.getFacingPrevio() != null) {
									pdaDatosSfmCap.setFacingPrevio(Utilidades.convertirDoubleAString(campo.getFacingPrevio().doubleValue(),"###0"));
								}
								if (campo.getFlgEstrategica() != null) {
									pdaDatosSfmCap.setFlgEstrategica(campo.getFlgEstrategica());
								}

								//Peticion 52126 A?r Campos de TipoGama y CC
								pdaDatosSfmCap.setTipoGama(campo.getTipoGama());
								pdaDatosSfmCap.setCc(campo.getCc());

								//Cuando es Facing Capacidad tambien hay que mostrar el Stock y el stock dias
								if (campo.getStock() != null) { 
									pdaDatosSfmCap.setStock(Utilidades.convertirDoubleAString(campo.getStock().doubleValue(),"###0.00"));
								}
								if (campo.getDiasStock() != null) { 
									pdaDatosSfmCap.setDiasStock(Utilidades.convertirDoubleAString(campo.getDiasStock().doubleValue(),"###0.00"));
								}
								if (campo.getCapacidad() != null) { 
									pdaDatosSfmCap.setCapacidad(Utilidades.convertirDoubleAString(campo.getCapacidad().doubleValue(),"###0"));
								}

								//Flag de bloqueos
								if (campo.getFlgSfmFijo() != null) { 
									pdaDatosSfmCap.setFlgSfmFijo(campo.getFlgSfmFijo());
								}

								//Solo Imagen
								if (campo.getFlgSoloImagen() != null) { 
									pdaDatosSfmCap.setFlgSoloImagen(campo.getFlgSoloImagen());
								}
								if (campo.getMultiplicadorFacing() != null) { 
									pdaDatosSfmCap.setMultiplicadorFacing(Utilidades.convertirDoubleAString(campo.getMultiplicadorFacing().doubleValue(),"0.##"));
								}
								if (campo.getImagenComercialMin() != null) { 
									pdaDatosSfmCap.setImagenComercialMin(Utilidades.convertirDoubleAString(campo.getImagenComercialMin().doubleValue(),"0.##"));
								}

								pdaDatosSfmCap.setFlgFacingCapacidad(flgFacingCapacidad);
							}
							//Comprobamos si el area es textil
							if ( Constantes.AREA_TEXTIL.equals(campo.getCodN1()) ){
								pdaDatosSfmCap.setTextil(true);
								//Comprobamos si la referencia de textil esta activa
								if ( Constantes.REF_TEXTIL_PEDIR_SI.equals(campo.getPedir()) ){
									//pdaDatosSfmCap.setActiva(false);
									pdaDatosSfmCap.setActiva(true);
								}
								else{
									//pdaDatosSfmCap.setActiva(true);
									pdaDatosSfmCap.setActiva(false);
								}
								//Comprobamos si la referencia de textil es pedible
								if ( Constantes.REF_TEXTIL_PEDIBLE_SI.equals(campo.getPedible()) ){

									pdaDatosSfmCap.setTextilPedible(true);
								}
								else{
									pdaDatosSfmCap.setTextilPedible(false);
								}
								//Comprobamos si la referencia de textil es de tipo lote.
								//Para eso miramos que el número de referencias hijas del lote sea mayor que 0.
								if ( campo.getNivelLote() != null && campo.getNivelLote().longValue() > 0 ){
									pdaDatosSfmCap.setLote(true);
									pdaDatosSfmCap.setTotalTextil(campo.getNivelLote().intValue() / 3 + ((campo.getNivelLote().intValue() % 3)>0 ? 1 : 0) );
									pdaDatosSfmCap.setPosicionTextil(1);
									pdaDatosSfmCap.setCodN1(campo.getCodN1());
									pdaDatosSfmCap.setCodN2(campo.getCodN2());
									pdaDatosSfmCap.setCodN3(campo.getCodN3());
									pdaDatosSfmCap.setCodN4(campo.getCodN4());
									pdaDatosSfmCap.setCodN5(campo.getCodN5());
									pdaDatosSfmCap.setTemporada(campo.getTemporada());
									pdaDatosSfmCap.setAnioColeccion(campo.getAnioColeccion() != null && campo.getAnioColeccion().split(" ").length > 1 ? (campo.getAnioColeccion().split(" "))[1] : campo.getAnioColeccion());
									pdaDatosSfmCap.setTalla(campo.getTalla());
									pdaDatosSfmCap.setColor(campo.getColor());
									pdaDatosSfmCap.setModeloProveedor(campo.getModeloProveedor());
									pdaDatosSfmCap.setTempColNumOrden(campo.getTempColNumOrden());
									if (campo.getTempColNumOrden() != null){
										String[] tempColNumOrden = campo.getTempColNumOrden().split("-");
										if (tempColNumOrden.length == 3){
											pdaDatosSfmCap.setOrden(tempColNumOrden[2]);
										}
									}
									pdaDatosSfmCap.setInactivarLote(false);
									//Recorremos las hijas del lote y las introducimos en la lista
									for (int i = 1; i < listaSfm.size(); i++){
										VArtSfm campoHija = listaSfm.get(i);
										PdaDatosSfmCap hija = new PdaDatosSfmCap();
										hija.setCodArt(campoHija.getCodArticulo());
										hija.setDescArt(pdaDatosSfmCap.getDescArt());
										hija.setEsAutoServicio(pdaDatosSfmCap.getEsAutoServicio());
										hija.setFlgFacing(pdaDatosSfmCap.getFlgFacing());
										hija.setFlgCapacidad(pdaDatosSfmCap.getFlgCapacidad());
										hija.setTalla(campoHija.getTalla());
										if (campoHija.getFacingCentro() != null) { 
											hija.setFacing(Utilidades.convertirDoubleAString(campoHija.getFacingCentro().doubleValue(),"###0"));
										}
										if (campoHija.getFacingCentro() != null) { 
											hija.setFacingOrig(Utilidades.convertirDoubleAString(campoHija.getFacingCentro().doubleValue(),"###0"));
										}
										if (campoHija.getFacingPrevio() != null) { 
											hija.setFacingPrevio(Utilidades.convertirDoubleAString(campoHija.getFacingPrevio().doubleValue(),"###0"));
										}

										hija.setFlgEstrategica(pdaDatosSfmCap.getFlgEstrategica());
										if (campoHija.getStock() != null) { 
											hija.setStock(Utilidades.convertirDoubleAString(campoHija.getStock().doubleValue(),"###0.00"));
										}
										if (campoHija.getDiasStock() != null) { 
											hija.setDiasStock(Utilidades.convertirDoubleAString(campoHija.getDiasStock().doubleValue(),"###0.00"));
										}
										if (campoHija.getCapacidad() != null) { 
											hija.setCapacidad(Utilidades.convertirDoubleAString(campoHija.getCapacidad().doubleValue(),"###0"));
										}
										hija.setFlgFacingCapacidad(pdaDatosSfmCap.getFlgFacingCapacidad());
										hija.setLote(false);
										hija.setTotalTextil(0);
										hija.setPosicionTextil(1);
										hija.setCodN1(campoHija.getCodN1());
										hija.setCodN2(campoHija.getCodN2());
										hija.setCodN3(campoHija.getCodN3());
										hija.setCodN4(campoHija.getCodN4());
										hija.setCodN5(campoHija.getCodN5());
										hija.setTemporada(campoHija.getTemporada());
										hija.setAnioColeccion(campoHija.getAnioColeccion() != null && campoHija.getAnioColeccion().split(" ").length > 1 ? (campoHija.getAnioColeccion().split(" "))[1] : campoHija.getAnioColeccion());
										hija.setColor(campoHija.getColor());
										hija.setModeloProveedor(campoHija.getModeloProveedor());
										hija.setTempColNumOrden(campoHija.getTempColNumOrden());
										if (campoHija.getTempColNumOrden() != null){
											String[] tempColNumOrden = campoHija.getTempColNumOrden().split("-");
											if (tempColNumOrden.length == 3){
												hija.setOrden(tempColNumOrden[2]);
											}
										}
										pdaDatosSfmCap.addListaHijas(hija);
										//Introducimos el color en la madre
										//ya que la madre viene sin color (será un fallo???)
										if (pdaDatosSfmCap.getColor() == null || "".equals(pdaDatosSfmCap.getColor())){
											pdaDatosSfmCap.setColor(campoHija.getColor());
										}
									}
								}
							}
						}else{
							pdaDatosSfmCap.setTextil(false);
							//Guardamos la información a mostrar en el JSP.
							if (sfmCapacidad.getFlgCapacidad().equals("N"))
							{
								//Es SFM
								if (campo.getLmin() != null) { 
									pdaDatosSfmCap.setLmin(Utilidades.convertirDoubleAString(campo.getLmin().doubleValue(),"###0.000").replace(',', '.'));
								}	
								//Si la el limite inferior es superior al limite superior, hay que actuar sobre lo que se visualiza en la columna "Limite Superior". Cuando se de
								// esta situación se optara por dos opciones segun la diferencia entre la fecha actual y la fecha de creación
								if (campo.getLmin() > campo.getLsf()) {


									//Obtenemos la fecha actual
									long fechaActual = new Date().getTime(); 
									//Obtenemos la fecha Sfm
									long fechaSfm = campo.getFechaSfm().getTime(); 
									//Obtenemos  la diferencia entre las dos fechas 
									long diferencia = fechaActual - fechaSfm; 
									double diffDays = Math.floor(diferencia / (1000 * 60 * 60 * 24)); 


									if (diffDays <= 21) {
										//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""

										pdaDatosSfmCap.setLsf("");
									}else {
										//Si la diferencia entre fechas es mayor que 21, entonces el limite superior se igualara al limite inferior
										if (campo.getLmin() != null) { 
											pdaDatosSfmCap.setLsf(Utilidades.convertirDoubleAString(campo.getLmin().doubleValue(),"###0.000"));
										}
									}

								} else {
									if (campo.getLsf() != null) { 
										pdaDatosSfmCap.setLsf(Utilidades.convertirDoubleAString(campo.getLsf().doubleValue(),"###0.000"));
									}
								}

								if (campo.getSfm() != null) { 
									pdaDatosSfmCap.setSfm(Utilidades.convertirDoubleAString(campo.getSfm().doubleValue(),"###0.000").replace(',', '.'));
								}
								if (campo.getCoberturaSfm() != null) { 
									pdaDatosSfmCap.setCoberturaSfm(Utilidades.convertirDoubleAString(campo.getCoberturaSfm().doubleValue(),"###0.000").replace(',', '.'));
								}
								if (campo.getSfm() != null) { 
									pdaDatosSfmCap.setSfmOrig(Utilidades.convertirDoubleAString(campo.getSfm().doubleValue(),"###0.000").replace(',', '.'));
								}
								if (campo.getCoberturaSfm() != null) { 
									pdaDatosSfmCap.setCoberturaSfmOrig(Utilidades.convertirDoubleAString(campo.getCoberturaSfm().doubleValue(),"###0.000").replace(',', '.'));
								}
								if (campo.getVidaUtil() != null) { 
									pdaDatosSfmCap.setVidaUtil(Utilidades.convertirDoubleAString(campo.getVidaUtil().doubleValue(),"###0.00"));
								}
								if (campo.getVentaMedia() != null) { 
									pdaDatosSfmCap.setVentaMedia(Utilidades.convertirDoubleAString(campo.getVentaMedia().doubleValue(),"###0.000"));
								}
								if (campo.getVentaAnticipada() != null) { 
									pdaDatosSfmCap.setVentaAnticipada(Utilidades.convertirDoubleAString(campo.getVentaAnticipada().doubleValue(),"###0.00"));
								}

								//Flag de bloqueos
								if (campo.getFlgSfmFijo() != null) { 
									pdaDatosSfmCap.setFlgSfmFijo(campo.getFlgSfmFijo());
								}

								//Solo Imagen
								if (campo.getFlgSoloImagen() != null) { 
									pdaDatosSfmCap.setFlgSoloImagen(campo.getFlgSoloImagen());
								}
								if (campo.getMultiplicadorFacing() != null) { 
									pdaDatosSfmCap.setMultiplicadorFacing(Utilidades.convertirDoubleAString(campo.getMultiplicadorFacing().doubleValue(),"0.##"));
								}
								if (campo.getImagenComercialMin() != null) { 
									pdaDatosSfmCap.setImagenComercialMin(Utilidades.convertirDoubleAString(campo.getImagenComercialMin().doubleValue(),"0.##"));
								}
							}
							else
							{
								//Es Capacidad
								if (campo.getStock() != null) { 
									pdaDatosSfmCap.setStock(Utilidades.convertirDoubleAString(campo.getStock().doubleValue(),"###0.00"));
								}
								if (campo.getDiasStock() != null) { 
									pdaDatosSfmCap.setDiasStock(Utilidades.convertirDoubleAString(campo.getDiasStock().doubleValue(),"###0.00"));
								}
								if (campo.getCapacidad() != null) { 
									pdaDatosSfmCap.setCapacidad(Utilidades.convertirDoubleAString(campo.getCapacidad().doubleValue(),"###0"));
								}
								if (campo.getCapacidad() != null) { 
									pdaDatosSfmCap.setCapacidadOrig(Utilidades.convertirDoubleAString(campo.getCapacidad().doubleValue(),"###0"));
								}

								//Flag de bloqueos
								if (campo.getFlgSfmFijo() != null) { 
									pdaDatosSfmCap.setFlgSfmFijo(campo.getFlgSfmFijo());
								}

								//Solo Imagen
								if (campo.getFlgSoloImagen() != null) { 
									pdaDatosSfmCap.setFlgSoloImagen(campo.getFlgSoloImagen());
								}
								if (campo.getMultiplicadorFacing() != null) { 
									pdaDatosSfmCap.setMultiplicadorFacing(Utilidades.convertirDoubleAString(campo.getMultiplicadorFacing().doubleValue(),"0.##"));
								}
								if (campo.getImagenComercialMin() != null) { 
									pdaDatosSfmCap.setImagenComercialMin(Utilidades.convertirDoubleAString(campo.getImagenComercialMin().doubleValue(),"0.##"));
								}


								pdaDatosSfmCap.setFacing(this.obtenerFacingCapacidad(codCentro,pdaDatosSfmCap.getCapacidad()));

							}
						}

						if (!mostrarError){
							pdaDatosSfmCap.setFlgFecsfm(campo.getFlgFecsfm());

							//Sólo en el caso de que el origen no sea del enlace de la consulta, guardamos datos en sesión.
							if (!origen.equals(Constantes.ORIGEN_CONSULTA))
							{
								//Guardamos en sesión el objeto de la referencia introducida.


								//Guardamos en sesión el código de referencia, como la última que se ha mostrado.
								session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCap);
							}
						}
					}
				}
				else
				{
					//En este caso el pl/sql no me ha devuelto datos, y tendrá que mostrar el mensaje que corresponda.
					if (pdaDatosSfmCap.getEsAutoServicio().equals("S"))
					{
						pdaDatosSfmCap.setEsError(this.messageSource.getMessage(
								"pda_p21_sfm.emptyRecordsSfmCap", null, locale));

					}
					else
					{
						pdaDatosSfmCap.setEsError(this.messageSource.getMessage(
								"pda_p21_sfm.emptyRecordsSfm", null, locale));

					}
				}
			}else{
				if (pdaDatosSfmCap.getEsAutoServicio().equals("S"))
				{
					pdaDatosSfmCap.setEsError(this.messageSource.getMessage(
							"pda_p21_sfm.emptyRecordsSfmCap", null, locale));

				}
				else
				{
					pdaDatosSfmCap.setEsError(this.messageSource.getMessage(
							"pda_p21_sfm.emptyRecordsSfm", null, locale));

				}
			}
		}
		else
		{
			pdaDatosSfmCap.setEsError(this.messageSource.getMessage(
					"pda_p21_sfm.errorReferenciaNoValida", null, locale));
		}

		return pdaDatosSfmCap;
	}

	private PdaDatosSfmCap guardarRegistros(HttpSession session, PdaDatosSfmCap pdaDatosSfmCapActual, String origenPantalla) throws Exception {

		Locale locale = LocaleContextHolder.getLocale();
		PdaDatosGuardados datosGuardados = new PdaDatosGuardados();
		List<PdaDatosSfmCap> listadoRegistros = null;

		//Cargamos la lista de registros de sesión en el caso de tener una lista
		//o por el contrario creamos la lista nueva con el registro actual en caso de venir desde consultas
		//if (Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){
		listadoRegistros = new ArrayList<PdaDatosSfmCap>();
		listadoRegistros.add(pdaDatosSfmCapActual);
		if (pdaDatosSfmCapActual.getListaHijas() != null && pdaDatosSfmCapActual.getListaHijas().size() > 0){
			listadoRegistros.addAll(pdaDatosSfmCapActual.getListaHijas());
		}
		//}else{
		//listadoRegistros = (List<PdaDatosSfmCap>) session.getAttribute("listaSFMCap");
		//}

		List<VArtSfm> listaModificadosSfm = new ArrayList<VArtSfm>();
		List<VArtSfm> listaModificadosCap = new ArrayList<VArtSfm>();
		List<VArtSfm> listaModificadosFac = new ArrayList<VArtSfm>();
		List<VArtSfm> listaModificadosFacHijas = new ArrayList<VArtSfm>();
		// Obtenemos el usuario para enviarlo a la actualización
		User userSession = (User) session.getAttribute("user");
		String usuario = "";
		if (userSession != null && userSession.getCode() != null && !userSession.getCode().equals("")) {
			usuario = userSession.getCode();
		}
		if (listadoRegistros != null) {
			for (PdaDatosSfmCap sfmCap : listadoRegistros) {
				if (sfmCap.getIcono() != null && sfmCap.getIcono().equals(Constantes.ICONO_MODIFICADO)) {
					VArtSfm artSfm = new VArtSfm();
					artSfm.setCodLoc(userSession.getCentro().getCodCentro());
					artSfm.setCodArticulo(sfmCap.getCodArt());
					artSfm.setMarca(sfmCap.getDescArt());
					artSfm.setCodError(Constantes.SFMCAP_CODIGO_MENSAJE_MODIFICADO);
					if (null != sfmCap.getLmin()) {
						artSfm.setLmin(Double.parseDouble(sfmCap.getLmin().replace(',','.')));
					}
					if (null != sfmCap.getLsf() && !"".equals(sfmCap.getLsf())){
						if (sfmCap.getLsf().equals("")){
							artSfm.setLsf(Double.parseDouble("0"));
						} else {
							artSfm.setLsf(Double.parseDouble(sfmCap.getLsf().replace(',','.')));
						}
					}
					if (null != sfmCap.getSfm() && !("").equals( sfmCap.getSfm())){
						artSfm.setSfm(Double.parseDouble(sfmCap.getSfm().replace(',','.')));
					}
					if (null != sfmCap.getCoberturaSfm() && !("").equals( sfmCap.getCoberturaSfm())){
						artSfm.setCoberturaSfm(Double.parseDouble(sfmCap.getCoberturaSfm().replace(',', '.')));
					}
					if (null != sfmCap.getVentaMedia() && !("").equals( sfmCap.getVentaMedia())){
						artSfm.setVentaMedia(Double.parseDouble(sfmCap.getVentaMedia().replace(',', '.')));
					}
					if (null != sfmCap.getVentaAnticipada() && !("").equals( sfmCap.getVentaAnticipada())){
						artSfm.setVentaAnticipada(Double.parseDouble(sfmCap.getVentaAnticipada().replace(',', '.')));
					}
					if (null != sfmCap.getVidaUtil() && !("").equals( sfmCap.getVidaUtil())){
						Double d = new Double(Double.parseDouble(sfmCap.getVidaUtil().replace(',', '.')));
						artSfm.setVidaUtil(d.longValue());
					}
					if (null != sfmCap.getStock() && !("").equals( sfmCap.getStock())){
						artSfm.setStock(Double.parseDouble(sfmCap.getStock().replace(',', '.')));
					}
					if (null != sfmCap.getDiasStock() && !("").equals( sfmCap.getDiasStock())){
						artSfm.setDiasStock(Double.parseDouble(sfmCap.getDiasStock().replace(',', '.')));
					}
					if (null != sfmCap.getCapacidad() && !("").equals( sfmCap.getCapacidad())){
						artSfm.setCapacidad(Double.parseDouble(sfmCap.getCapacidad().replace(',', '.')));
					}
					artSfm.setFlgFecsfm(sfmCap.getFlgFecsfm());
					if (null != sfmCap.getSfmOrig() && !("").equals( sfmCap.getSfmOrig())){
						artSfm.setSfmOrig(Double.parseDouble(sfmCap.getSfmOrig().replace(',', '.')));
					}
					if (null != sfmCap.getCoberturaSfmOrig() && !("").equals( sfmCap.getCoberturaSfmOrig())){
						artSfm.setCoberturaSfmOrig(Double.parseDouble(sfmCap.getCoberturaSfmOrig().replace(',', '.')));
					}
					if (null != sfmCap.getCapacidadOrig() && !("").equals( sfmCap.getCapacidadOrig())){
						artSfm.setCapacidadOrig(Double.parseDouble(sfmCap.getCapacidadOrig().replace(',', '.')));
					}

					if (null != sfmCap.getFacing() && !("").equals(sfmCap.getFacing())){
						artSfm.setFacingCentro(new Long(sfmCap.getFacing()));
					}

					if (null != sfmCap.getFacingOrig() && !("").equals(sfmCap.getFacingOrig())){
						artSfm.setFacingCentroOrig(new Long(sfmCap.getFacingOrig()));
					}

					if (null != sfmCap.getFacingPrevio() && !("").equals(sfmCap.getFacingPrevio())){
						artSfm.setFacingPrevio(new Long(sfmCap.getFacingPrevio()));
					}

					if (null != sfmCap.getFlgEstrategica()){
						artSfm.setFlgEstrategica(sfmCap.getFlgEstrategica());
					}
					
					//Introducimos también el código de artículo caprabo. En el caso de ser un centro eroski,
					//el código artículo caprabo será igual al de eroski.
					if(null != sfmCap.getCodArtCaprabo()){
						artSfm.setCodArtCaprabo(sfmCap.getCodArtCaprabo());
					}

					if (sfmCap.getFlgFacing().equals("N")){
						if (sfmCap.getFlgCapacidad().equals("S")){
							listaModificadosCap.add(artSfm);
						} else {
							listaModificadosSfm.add(artSfm);
						}

					}else{
						if (artSfm.getCodArticulo().longValue() == pdaDatosSfmCapActual.getCodArt().longValue()){
							//Es una madre (en caso de que sea lote)
							listaModificadosFac.add(artSfm);
						}
						else{
							//Es una hija (en caso de que sea lote)
							listaModificadosFacHijas.add(artSfm);
						}
					}
				}
			}
		}
		//HashMap<String,Integer> map = (HashMap<String,Integer>)session.getAttribute("hashMapClavesSFMCap");
		SfmCapacidadFacing sfmCapacidadActualizacion = null;
		Integer totalGuardados = 0;
		if (listaModificadosSfm != null && !listaModificadosSfm.isEmpty()) {
			sfmCapacidadActualizacion = this.vArtSfmService.actualizacionSfm(listaModificadosSfm, usuario);
			List<VArtSfm> listaDatos = sfmCapacidadActualizacion.getDatos();
			for (VArtSfm artSfm : listaDatos) {
				Integer posicion = 0;

				if (listadoRegistros != null && 0 <= posicion.intValue() && posicion.intValue() < listadoRegistros.size()) {
					PdaDatosSfmCap sfmCap = listadoRegistros.get(posicion.intValue());
					if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Cuando viene de consulta sólo hay un registro, posicion 0
						sfmCap = pdaDatosSfmCapActual;
					}
					if (artSfm.getCodError() == null || artSfm.getCodError().equals(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO)){
						//Si no ha habido error en la actualización, es necesario actualizar el sfmOrigen del objeto
						//con el sfmOrigen que está guardado en BD. El sfmOrigen, coincidirá con el que se ve en la
						//pantalla. Hay que recordar que el objeto sfmCap está guardado en sesión y además luego se 
						//utiliza como modelo para construir el JSP. ES OBLIGATORIO ACTUALIZARLO para que no de errores
						//de inserción.						
						sfmCap.setSfmOrig(artSfm.getSfmOrig().toString());
						
						sfmCap.setIcono(Constantes.ICONO_GUARDADO);
						totalGuardados++;
					} else {
						sfmCap.setIcono(Constantes.ICONO_ERROR);
						switch (artSfm.getCodError().intValue()){
						case 1 :  sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.mensajeErrorLimiteInferior",null, locale));
						break;
						case 2 :  sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.mensajeErrorLimiteSuperior",null, locale));
						break;
						case 3 : sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacion",null, locale));
						break;
						case 4 : sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacionOrigen",null, locale));
						break;		 
						}
						sfmCap.setSfm(Utilidades.convertirDoubleAString(artSfm.getSfm(),"###0.000").replace( ",", "." ));
						sfmCap.setSfmOrig(Utilidades.convertirDoubleAString(artSfm.getSfmOrig(),"###0.000"));
						sfmCap.setCoberturaSfm(Utilidades.convertirDoubleAString(artSfm.getCoberturaSfm(), "###0.000").replace( ",", "." ));
						sfmCap.setCoberturaSfmOrig(Utilidades.convertirDoubleAString(artSfm.getCoberturaSfmOrig(), "###0.000"));
					}
				}
			}
		}
		if (listaModificadosCap != null && !listaModificadosCap.isEmpty()) {		
			sfmCapacidadActualizacion = this.vArtSfmService.actualizacionCap(listaModificadosCap, usuario);
			List<VArtSfm> listaDatos = sfmCapacidadActualizacion.getDatos();
			for (VArtSfm artSfm : listaDatos) {
				Integer posicion = 0;
				//if (map != null && !Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Cuando viene de consulta sólo hay un registro, posicion 0
				//	posicion = map.get(String.valueOf(artSfm.getCodArticulo()));
				//}
				if (listadoRegistros != null && 0 <= posicion.intValue() && posicion.intValue() < listadoRegistros.size()) {
					PdaDatosSfmCap sfmCap = listadoRegistros.get(posicion.intValue());
					if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Cuando viene de consulta sólo hay un registro, posicion 0
						sfmCap = pdaDatosSfmCapActual;
					}
					if (artSfm.getCodError() == null || artSfm.getCodError().equals(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO)){
						//Si no ha habido error en la actualización, es necesario actualizar el capacidadOriginal del objeto
						//con el capacidadOriginal que está guardado en BD. El capacidadOriginal, coincidirá con el que se ve en la
						//pantalla. Hay que recordar que el objeto sfmCap está guardado en sesión y además luego se 
						//utiliza como modelo para construir el JSP. ES OBLIGATORIO ACTUALIZARLO para que no de errores
						//de inserción.						
						sfmCap.setCapacidadOrig(artSfm.getCapacidadOrig().toString());
						
						sfmCap.setIcono(Constantes.ICONO_GUARDADO);
						totalGuardados++;
					} else {
						sfmCap.setIcono(Constantes.ICONO_ERROR);
						switch (artSfm.getCodError().intValue()){
						case 1 :
						case 2 :
						case 3 : sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacion",null, locale));
						break;
						case 4 : sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacionOrigen",null, locale));
						break;		 
						}
						sfmCap.setCapacidad(Utilidades.convertirDoubleAString(artSfm.getCapacidad(),"###0"));
						sfmCap.setCapacidadOrig(Utilidades.convertirDoubleAString(artSfm.getCapacidadOrig(),"###0"));
					}
				}
			}
		}
		if ( (listaModificadosFac != null && !listaModificadosFac.isEmpty()) || (listaModificadosFacHijas != null && !listaModificadosFacHijas.isEmpty()) ) {
			//Guardamos las hijas de las referencias lote
			PdaDatosSfmCap sfmCap = listadoRegistros.get(0);
			Integer contadorErroresHijas = 0;
			if ( listaModificadosFacHijas != null && !listaModificadosFacHijas.isEmpty() ) {
				sfmCapacidadActualizacion = this.vArtSfmService.actualizacionFac(listaModificadosFacHijas, usuario);
				if (sfmCapacidadActualizacion != null && sfmCapacidadActualizacion.getDatos() != null){
					List<VArtSfm> listaDatos = sfmCapacidadActualizacion.getDatos();
					for (VArtSfm artSfm : listaDatos) {
						if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Cuando viene de consulta sólo hay un registro, posicion 0
							//Sabemos que es una hija
							//Buscamos la hija dentro de la lista de hijas
							for (PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
								if (hija.getCodArt().longValue() == artSfm.getCodArticulo().longValue()){
									//Actualizamos los datos de la hija
									hija.setFacing(artSfm.getFacingCentro().toString());
									hija.setFacingOrig(artSfm.getFacingCentroOrig().toString());
									hija.setFacingPrevio(artSfm.getFacingPrevio().toString());
									hija.setFlgEstrategica(artSfm.getFlgEstrategica());
									if (artSfm.getCodError() != null){
										sfmCap.setIcono(Constantes.ICONO_ERROR);
										hija.setIcono(Constantes.ICONO_ERROR);
										contadorErroresHijas++;
										if (contadorErroresHijas.intValue() ==1){
											sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p27_facing.errorGuardar1",new Object[] { contadorErroresHijas.toString() }, locale));
										}
										else{
											sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p27_facing.errorGuardar2",new Object[] { contadorErroresHijas.toString() }, locale));
										}
									}
									else{
										hija.setIcono(null);
									}
								}
							}
						}
					}
					//Ordenamos las hijas poniendo primero las que tienen error.
					ArrayList<PdaDatosSfmCap> listaOrdenada = new ArrayList<PdaDatosSfmCap>();
					ArrayList<PdaDatosSfmCap> listaError = new ArrayList<PdaDatosSfmCap>();
					for (PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
						if (Constantes.ICONO_ERROR.equals(hija.getIcono())){
							listaError.add(hija);
						}
						else{
							listaOrdenada.add(hija);
						}									
					}
					listaError.addAll(listaOrdenada);
					sfmCap.setListaHijas(listaError);
					//Sacamos la primera página
					sfmCap.setPosicionTextil(1);
					//Descheckeamos el check
					sfmCap.setInactivarLote(false);
					//Si no ha habido errores al guardar las hijas
					//y no hay que guardar la madre entonces
					//ponemos en la madre el icono de guardado correctamente
					if (contadorErroresHijas == 0 && listaModificadosFac != null && listaModificadosFac.isEmpty()){
						sfmCap.setIcono(Constantes.ICONO_GUARDADO);
					}
				}
				else{
					if (sfmCapacidadActualizacion != null && sfmCapacidadActualizacion.getEstado() != null && sfmCapacidadActualizacion.getEstado().longValue() == 0){
					}
					else{
						sfmCap.setIcono(Constantes.ICONO_ERROR);
						sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacion",null, locale));
						sfmCap.setFacing(sfmCap.getFacingOrig());
						if (sfmCap.getListaHijas() != null){
							for (PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
								hija.setFacing(hija.getFacingOrig());
							}
						}
					}
				}
			}
			//Guardamos la referencia madre (sea de un lote o no)
			if ( listaModificadosFac != null && !listaModificadosFac.isEmpty() ) {
				VArtSfm artSfmMadre = listaModificadosFac.get(0);
				//Si se ha producido un error al guardar las hijas del lote (en caso de que la referencia sea lote)
				//volvemos a mirar si es necesario cambiar a la madre el facing
				//de 0 a 1 o de N a 0
				if (contadorErroresHijas > 0){
					//Si todas las hijas tienen Facing igual a 0 entonces
					//pongo el Facing de la madre a 0
					//si no, si la madre tiene facing igual a 0 lo pongo a 1
					boolean hijasFacingIgualA0 = true;
					for (PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
						int sfmFacingHija = Integer.parseInt(hija.getFacing());
						hijasFacingIgualA0 = hijasFacingIgualA0 && (sfmFacingHija == 0);
					}
					int sfmFacingOrigMadre = Integer.parseInt(pdaDatosSfmCapActual.getFacingOrig());
					if (hijasFacingIgualA0){
						if (sfmFacingOrigMadre != 0){
							artSfmMadre.setFacingCentro(new Long("0"));
						}
						else{
							if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Sólo se guarda en la lista si no proviene de la consulta
								PdaDatosSfmCap pdaDatosSfmCapCopia = (PdaDatosSfmCap) session.getAttribute("sfmCapReferenciaConsulta");
								pdaDatosSfmCapCopia.setFacing("0");
								if (Constantes.ICONO_MODIFICADO.equals(pdaDatosSfmCapCopia.getIcono())){
									pdaDatosSfmCapCopia.setIcono(null);
								}
								session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapCopia);
							}
							sfmCap.setFacing("0");
							if (Constantes.ICONO_MODIFICADO.equals(sfmCap.getIcono())){
								sfmCap.setIcono(null);
							}
							listaModificadosFac = null;
						}
					}
					else{
						if (sfmFacingOrigMadre == 0){
							artSfmMadre.setFacingCentro(new Long("1"));
						}
						else{
							if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Sólo se guarda en la lista si no proviene de la consulta
								PdaDatosSfmCap pdaDatosSfmCapCopia = (PdaDatosSfmCap) session.getAttribute("sfmCapReferenciaConsulta");
								pdaDatosSfmCapCopia.setFacing("1");
								if (Constantes.ICONO_MODIFICADO.equals(pdaDatosSfmCapCopia.getIcono())){
									pdaDatosSfmCapCopia.setIcono(null);
								}
								session.setAttribute("sfmCapReferenciaConsulta", pdaDatosSfmCapCopia);
							}
							sfmCap.setFacing("1");
							if (Constantes.ICONO_MODIFICADO.equals(sfmCap.getIcono())){
								sfmCap.setIcono(null);
							}
							listaModificadosFac = null;
						}
					}
				}
				if ( listaModificadosFac != null && !listaModificadosFac.isEmpty() ) {
					sfmCapacidadActualizacion = this.vArtSfmService.actualizacionFac(listaModificadosFac, usuario);
					if (sfmCapacidadActualizacion != null && sfmCapacidadActualizacion.getDatos() != null){
						List<VArtSfm> listaDatos = sfmCapacidadActualizacion.getDatos();
						Integer contadorErrores = 0;
						for (VArtSfm artSfm : listaDatos) {
							if (!Constantes.ORIGEN_CONSULTA.equals(origenPantalla)){ //Cuando viene de consulta sólo hay un registro, posicion 0
								//Es la madre
								//Actualizamos los datos de la madre
								sfmCap.setFacing(artSfm.getFacingCentro().toString());
								sfmCap.setFacingOrig(artSfm.getFacingCentroOrig().toString());
								sfmCap.setFacingPrevio(artSfm.getFacingPrevio().toString());
								sfmCap.setFlgEstrategica(artSfm.getFlgEstrategica());
								sfmCap.setInactivarLote(false);
							}
							if (artSfm.getCodError() == null || artSfm.getCodError().equals(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO)){
								if (!Constantes.ICONO_ERROR.equals(sfmCap.getIcono())){
									/*//Si no ha habido error en la actualización, es necesario actualizar el sfmOrigen del objeto
									//con el sfmOrigen que está guardado en BD. El sfmOrigen, coincidirá con el que se ve en la
									//pantalla. Hay que recordar que el objeto sfmCap está guardado en sesión y además luego se 
									//utiliza como modelo para construir el JSP. ES OBLIGATORIO ACTUALIZARLO para que no de errores
									//de inserción.						
									sfmCap.setSfmOrig(artSfm.getSfmOrig().toString());*/
									
									sfmCap.setIcono(Constantes.ICONO_GUARDADO);
									totalGuardados++;
								}
							} else {
								if (!Constantes.ICONO_ERROR.equals(sfmCap.getIcono())){
									sfmCap.setIcono(Constantes.ICONO_ERROR);
									switch (artSfm.getCodError().intValue()){
									//case 1 :
									//case 2 :
									//case 3 : sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacion",null, locale));
									//		 break;
									case 4 : sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacionOrigen",null, locale));
									break;
									default : sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacion",null, locale));
									break;
									}
								}

								sfmCap.setFacing(artSfm.getFacingCentro().toString());
								sfmCap.setFacingOrig(artSfm.getFacingCentroOrig().toString());
								sfmCap.setFacingPrevio(artSfm.getFacingPrevio().toString());
								sfmCap.setFlgEstrategica(artSfm.getFlgEstrategica());
							}
						}
					}
					else{
						sfmCap.setIcono(Constantes.ICONO_ERROR);
						sfmCap.setErrorIcono(this.messageSource.getMessage("pda_p21_sfm.errorActualizacion",null, locale));
						sfmCap.setFacing(sfmCap.getFacingOrig());
						if (sfmCap.getListaHijas() != null){
							for (PdaDatosSfmCap hija : pdaDatosSfmCapActual.getListaHijas()){
								hija.setFacing(hija.getFacingOrig());
							}
						}
					}
				}
			}
		}



		return listadoRegistros.get(0);
	}

	//Obtiene el código de artículo caprabo para todos los elementos de una lista.
	private void actualizarCodArtListaModificadosActualizacion(List<VArtSfm> listaModificadosActualizacion,boolean isCaprabo) throws Exception{
		if(isCaprabo){
			Long codArtCaprabo;
			for(VArtSfm vartSfm : listaModificadosActualizacion){
				codArtCaprabo = utilidadesCapraboService.obtenerCodigoCaprabo(vartSfm.getCodLoc(), vartSfm.getCodArticulo());
				vartSfm.setCodArticulo(codArtCaprabo);
			}								
		}
	}
}