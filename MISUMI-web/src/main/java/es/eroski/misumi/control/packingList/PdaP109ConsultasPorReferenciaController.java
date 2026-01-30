/**
 * Controller de la opción de "CONSULTAS POR REFERENCIA".
 */
package es.eroski.misumi.control.packingList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import es.eroski.misumi.model.pda.packingList.FechaReferencia;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.packingList.RdoListarArticulosWrapper;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.service.iface.FotosReferenciaService;
import es.eroski.misumi.service.iface.VAgruComerRefService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.service.packingList.iface.ConsultaReferenciaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.SessionUtils;
import es.eroski.misumi.util.StackTraceManager;

@Controller
public class PdaP109ConsultasPorReferenciaController{

	private static Logger logger = Logger.getLogger(PdaP109ConsultasPorReferenciaController.class);

	private ArrayList<String> listaBotones = new ArrayList<String>(Arrays.asList("firstPalet", "prevPalet", "nextPalet", "lastPalet"));

	private final String resultadoOK = "/pda/packingList/pda_p109_consultasPorReferencia";
	private final String resultadoKO = "/pda/packingList/pda_p111_errorConsultasPorReferencia";


	@Autowired
	private ConsultaReferenciaService ConsultaReferenciaService;
	
	@Autowired
	private FotosReferenciaService fotosReferenciaService; 
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
	
	
	@Autowired
	private VAgruComerRefService vAgruComerRefService;
	
	@RequestMapping(value = "/pdaP109ConsultasReferencia.do", method = RequestMethod.GET)
	public String showForm(ModelMap model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

		Date fechaActual = new Date();

		SimpleDateFormat sdfFormatted = new SimpleDateFormat("dd/MM/yyyy");

		String fechaFormateada = sdfFormatted.format(fechaActual);

		boolean redireccionadoAP107 = false;
		
		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		SessionUtils.limpiarAtributosLista(session);

		try {

			FechaReferencia fechaReferencia = new FechaReferencia();
			fechaReferencia.setCodCentro(codCentro);
			String fechaFestiva = ConsultaReferenciaService.obtenerUltimaFechaFestiva(fechaReferencia);
			if (fechaFestiva == null || fechaFestiva.trim().isEmpty()) {
				Calendar calendar = Calendar.getInstance();
	            calendar.setTime(fechaActual);
	            calendar.add(Calendar.DAY_OF_MONTH, -2); 

	            Date nuevaFecha = calendar.getTime();

	          
	            fechaFestiva = sdfFormatted.format(nuevaFecha); 
		    }else{
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = inputFormat.parse(fechaFestiva);
			fechaFestiva = sdfFormatted.format(date);
			}

			pdaDatosCab.setFechaHasta(fechaFormateada);
			pdaDatosCab.setFechaDesde(fechaFestiva);
			Page<Palet> pagPalet = Paginate.paginarListaDatos(session, "1", "2", "firstPalet",
					Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

			model.addAttribute("existeEntradasPalets", redireccionadoAP107);
			model.addAttribute("isRegistroEmty", true);

			model.addAttribute("pdaDatosCab", pdaDatosCab);
			model.addAttribute("pagPalet", pagPalet);

		} catch (Exception e) {
			e.printStackTrace();
			return resultadoKO;
		}

		return resultadoOK;
	}
	
	@RequestMapping(value = "/pdaP109ConsultasReferencia.do", method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab, BindingResult result, ModelMap model,
			HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pgArticulos", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pgTotArticulos", defaultValue = "1") int totalPages) {

		String matriculaInput = pdaDatosCab.getCodArtCab();
		
		if (matriculaInput == null || matriculaInput.trim().isEmpty()) {
	        matriculaInput = "0";
	    }


		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfFormatted = new SimpleDateFormat("dd/MM/yyyy");
		
		String fechaDesdeInput = pdaDatosCab.getFechaDesde();
		String fechaHastaInput = pdaDatosCab.getFechaHasta();
		

		User user = (User) session.getAttribute("user");
		Long codCentro = user.getCentro().getCodCentro();
		String cecoInput = codCentro != null ? codCentro.toString() : "";

		boolean isRegistroEmty = false;

		if (matriculaInput != null && !matriculaInput.isEmpty()) {
			try {

				if (matriculaInput.startsWith(Constantes.REF_PISTOLA)) {

					matriculaInput = matriculaInput.substring(1, matriculaInput.length());
				}
				RdoListarArticulosWrapper rdoListarArticulosWrapper = ConsultaReferenciaService
						.listarArticulosPorMatricula(matriculaInput, cecoInput, fechaDesdeInput, fechaHastaInput); 

				List<ConsultaMatricula> listaArticulos = rdoListarArticulosWrapper.getResultado().getItems();

				for (ConsultaMatricula articulo : listaArticulos) {

					if (articulo.getFechaAlbaran() != null) {
						int fechaInt = articulo.getFechaAlbaran();
						String fechaStr = String.valueOf(fechaInt);
						
						try {

							Date date = sdf.parse(fechaStr);


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


				Page<ConsultaMatricula> pagPalet = Paginate.paginarListaDatos(session, String.valueOf(pageNumber),
						String.valueOf("2"), "firstPalet", Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

				model.addAttribute("isRegistroEmty", isRegistroEmty);
				model.addAttribute("existeEntradasPalets", true);
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

	@RequestMapping(value = "/pdaP109Paginar", method = RequestMethod.GET)
	public String pdaP107Paginar(ModelMap model, HttpSession session, @Valid final String pgPalet,
			@Valid final String pgTotPalet, @Valid final String botPag) {

		PdaDatosCabecera pdaDatosCab = new PdaDatosCabecera();

		try {

			Page<ConsultaMatricula> pagPalet = Paginate.paginarListaDatos(session, pgPalet, pgTotPalet, botPag,
					Constantes.DEVOLUCIONES_PDA_PAGS, listaBotones);

			model.addAttribute("existeEntradasPalets", true);
			model.addAttribute("pagPalet", pagPalet);
			model.addAttribute("pdaDatosCab", pdaDatosCab);

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultadoOK;
	}

	@RequestMapping(value = "/pdaP109FotoAmpliadaDatosReferencia", method = RequestMethod.GET)
	public String showForm(ModelMap model, @RequestParam(value = "codArticulo") String codArticulo,
			@RequestParam(value = "tieneFoto") String tieneFoto, @RequestParam(value = "fechaDesde") String fechaDesde,
			@RequestParam(value = "fechaHasta") String fechaHasta,
			@RequestParam(value = "pestanaDatosRef", required = false, defaultValue = "") String pestanaDatosRef,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String strEstructura = "";
		String tipoReferencia = "";

		String resultado = "/pda/packingList/pda_p109_fotoAmpliadaDatosReferencia";

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

			model.addAttribute("estructura", strEstructura);
			model.addAttribute("tipoReferencia", tipoReferencia);
			model.addAttribute("codArticuloFoto", codArticulo);
			model.addAttribute("tieneFoto", tieneFoto);
			model.addAttribute("fechaDesde", fechaDesde);
			model.addAttribute("fechaHasta", fechaHasta);
			model.addAttribute("actionP20", pestanaDatosRef + ".do");
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}

		return resultado;

	}

}