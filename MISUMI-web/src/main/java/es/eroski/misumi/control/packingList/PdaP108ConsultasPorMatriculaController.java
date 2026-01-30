/**
 * Controller de la opción de "CONSULTAS POR MATRICULAS".
 */
package es.eroski.misumi.control.packingList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.packingList.ConsultaMatricula;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.packingList.RdoListarArticulosWrapper;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.packingList.iface.ConsultaMatriculaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.SessionUtils;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP108ConsultasPorMatriculaController{

	private static Logger logger = Logger.getLogger(PdaP108ConsultasPorMatriculaController.class);

	private ArrayList<String> listaBotones = new ArrayList<String>(Arrays.asList("firstPalet", "prevPalet", "nextPalet", "lastPalet"));

	private final String resultadoOK = "/pda/packingList/pda_p108_consultasPorMatricula";
	private final String resultadoKO = "/pda/packingList/pda_p111_errorPackingList";


	@Autowired
	private ConsultaMatriculaService ConsultaMatriculaService;
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService; 
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@RequestMapping(value = "/pdaP108ConsultasMatricula.do", method = RequestMethod.GET)
	public String showForm(ModelMap model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

		boolean redireccionadoAP107 = false;

		SessionUtils.limpiarAtributosLista(session);

		redireccionadoAP107 = true;

		try {

			Page<Palet> pagPalet = Paginate.paginarListaDatos(session, "1", "2", "firstPalet",
					Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

			model.addAttribute("existeEntradasPalets", redireccionadoAP107);
			model.addAttribute("pdaDatosCab", pdaDatosCab);
			model.addAttribute("pagPalet", pagPalet);

		} catch (Exception e) {
			e.printStackTrace();
			return resultadoKO;
		}

		return resultadoOK;
	}

	
	@RequestMapping(value = "/pdaP108ConsultasMatricula.do", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result, ModelMap model,
			HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pgPalet", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pgTotPalet", defaultValue = "2") int totalPages) {

		String matriculaInput = pdaDatosCab.getMatricula();
		
		String urlInputPalet = pdaDatosCab.getUrlPalet();
		
		
		if (urlInputPalet != null && !urlInputPalet.trim().isEmpty()) {

	        urlInputPalet = urlInputPalet.replace("pdaP107Paginar.do", "pdaP107EntradasPalets.do");
	        
	    }
		
		if (matriculaInput == null || matriculaInput.trim().isEmpty()) {
	        matriculaInput = "9999999999999999999999999999999999999"; 
	    }
		
		if (matriculaInput != null) {
	        matriculaInput = matriculaInput.trim().replaceAll(",$", "");
	    }
		
		Boolean soyPaletInput = pdaDatosCab.getSoyPalet();
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String cecoInput = codCentro != null ? codCentro.toString() : "";

		boolean redireccionadoAP107 = false;

		SessionUtils.limpiarAtributosLista(session);
		
		session.setAttribute("urlBasePalet", urlInputPalet);

		if (matriculaInput != null && !matriculaInput.isEmpty()) {
			try {

				if (matriculaInput.startsWith(Constantes.REF_PISTOLA)) {

					matriculaInput = matriculaInput.substring(1, matriculaInput.length());
				}

				RdoListarArticulosWrapper rdoListarArticulosWrapper = ConsultaMatriculaService
						.listarArticulosPorMatricula(matriculaInput, cecoInput);

				List<ConsultaMatricula> listaArticulos = rdoListarArticulosWrapper.getResultado().getItems();

				for (ConsultaMatricula articulo : listaArticulos) {

					if (articulo.getFechaAlbaran() != null) {
						int fechaInt = articulo.getFechaAlbaran();

						String fechaStr = String.valueOf(fechaInt);

						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						try {

							Date date = sdf.parse(fechaStr);

							SimpleDateFormat sdfFormatted = new SimpleDateFormat("dd/MM/yyyy");
							articulo.setFormattedFechaAlbaran(sdfFormatted.format(date));
						} catch (Exception e) {
							articulo.setFormattedFechaAlbaran("");

						}
					}

					long codArtEroski = articulo.getArticulo();

					FotosReferencia fotosReferencia = new FotosReferencia();
					fotosReferencia.setCodReferencia(codArtEroski);
					if (fotosReferenciaService.checkImage(fotosReferencia)) {

						articulo.setTieneFoto(true);
					} else {

						articulo.setTieneFoto(false);
					}

				}

				session.setAttribute("listaMatriculas", listaArticulos);

				if (!listaArticulos.isEmpty() && listaArticulos != null) {
					redireccionadoAP107 = true;
				}

				Page<ConsultaMatricula> pagPalet = Paginate.paginarListaDatos(session, String.valueOf(pageNumber),
						String.valueOf(totalPages), "firstPalet", Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

				model.addAttribute("urlInputPalet", urlInputPalet);
				model.addAttribute("paraPalet", soyPaletInput);
				model.addAttribute("existeEntradasPalets", redireccionadoAP107);
				model.addAttribute("pdaDatosCab", pdaDatosCab);
				model.addAttribute("pagPalet", pagPalet);

			} catch (Exception e) {
				model.addAttribute("mensajeError", "Error al consultar los artículos: " + e.getMessage());
				return "resultadoKO";
			}
		} else {
			model.addAttribute("mensajeError", "El campo matrícula debe estar informado.");
			return "resultadoKO";
		}

		return resultadoOK;
	}

	
	@RequestMapping(value = "/pdaP108Paginar", method = RequestMethod.GET)
	public String pdaP107Paginar(ModelMap model, HttpSession session, 
	        @Valid final String pgPalet,
	        @Valid final String pgTotPalet, @Valid final String botPag,
	        @Valid final String paraPalet,
	        @RequestParam(value = "urlInputPalet", defaultValue = "") String urlInputPalet) {

	    PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

	    try {
	        if (urlInputPalet == null || urlInputPalet.isEmpty()) {
	            Object urlFromSession = session.getAttribute("urlBasePalet");
	            if (urlFromSession != null) {
	                urlInputPalet = urlFromSession.toString();
	            }
	        }

	    } catch (Exception e) {
	        logger.error("Error al obtener el parámetro urlInputPalet: " + e.getMessage());
	        urlInputPalet = "";
	    }

	    try {

	        Page<ConsultaMatricula> pagPalet = Paginate.paginarListaDatos(session, pgPalet, pgTotPalet, botPag,
	                Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

	        model.addAttribute("urlInputPalet", urlInputPalet);
	        model.addAttribute("paraPalet", paraPalet);
	        model.addAttribute("existeEntradasPalets", true);
	        model.addAttribute("pagPalet", pagPalet);
	        model.addAttribute("pdaDatosCab", pdaDatosCab);

	    } catch (Exception e) {
	        logger.error("Error al procesar la paginación: " + e.getMessage());
	    }

	    return resultadoOK;
	}


	@RequestMapping(value = "/pdaP108FotoAmpliadaDatosReferencia", method = RequestMethod.GET)
	public String showForm(ModelMap model, @RequestParam(value = "codArticulo") String codArticulo,
			@RequestParam(value = "tieneFoto") String tieneFoto, @RequestParam(value = "matricula") String matricula,
			@RequestParam(value = "pgTotPalet") String pgTotPalet,
			@RequestParam(value = "pgPalet") String pgPalet,
			@RequestParam(value = "pestanaDatosRef", required = false, defaultValue = "") String pestanaDatosRef,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String strEstructura = "";
		String tipoReferencia = "";

		String resultado = "/pda/packingList/pda_p108_fotoAmpliadaDatosReferencia";

		try {
			Long longCodArticulo = new Long(codArticulo);

			VDatosDiarioArt vDatosDiarioArtRes = null;
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(longCodArticulo);
			vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

			if (vDatosDiarioArtRes != null) {

				List<VAgruComerRef> listVAgrucomerRef = this.vAgruComerRefService
						.findAll(new VAgruComerRef("I3", vDatosDiarioArtRes.getGrupo1(), vDatosDiarioArtRes.getGrupo2(),
								vDatosDiarioArtRes.getGrupo3(), null, null, null), null);
				VAgruComerRef vAgruComeref = listVAgrucomerRef.size() > 0 ? listVAgrucomerRef.get(0) : null;

				strEstructura = vDatosDiarioArtRes.getGrupo1() + "-" + vDatosDiarioArtRes.getGrupo2() + "-"
						+ vDatosDiarioArtRes.getGrupo3() + "-"
						+ (vAgruComeref != null ? vAgruComeref.getDescripcion() : null);

				if (vDatosDiarioArtRes.getTipoCompraVenta().equals("C")) {
					tipoReferencia = "COMPRA";
				} else if (vDatosDiarioArtRes.getTipoCompraVenta().equals("V")) {
					tipoReferencia = "VENTA";
				} else if (vDatosDiarioArtRes.getTipoCompraVenta().equals("T")) {
					tipoReferencia = "COMPRA/VENTA";
				}
			}

			model.addAttribute("pgPalet", pgPalet);
			model.addAttribute("pgTotPalet", pgTotPalet);
			model.addAttribute("estructura", strEstructura);
			model.addAttribute("tipoReferencia", tipoReferencia);
			model.addAttribute("codArticuloFoto", codArticulo);
			model.addAttribute("tieneFoto", tieneFoto);
			model.addAttribute("matricula", matricula);
			model.addAttribute("actionP20", pestanaDatosRef + ".do");
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;

	}

}