package es.eroski.misumi.control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.model.pda.PdaDatosCabecera;
import es.eroski.misumi.model.pda.PdaSeleccionStock;
import es.eroski.misumi.model.pda.PdaStock;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.RelacionArticuloService;
import es.eroski.misumi.service.iface.StockPlataformaService;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Controller
public class PdaP28CorreccionStockInicioController extends pdaConsultasController{

	private static Logger logger = Logger.getLogger(PdaP28CorreccionStockInicioController.class);

	@Autowired
	private RelacionArticuloService relacionArticuloService;

	@Autowired
	private StockTiendaService correccionStockService;

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Autowired
	private StockPlataformaService stockPlataformaService;
	
	@RequestMapping(value = "/pdaP28CorreccionStockInicio",method = RequestMethod.GET)
	public String showForm(ModelMap model,@Valid final Long codArt,
			@Valid final String procede,
			@Valid final String origen,
			@Valid final String mmc,/*,  @Valid String selectProv*/ 
			@RequestParam(value = "calculoCC", required = false, defaultValue = "N") String calculoCC,
			@RequestParam(value = "origenPantalla", required = false, defaultValue = "") String origenPantalla,
			@RequestParam(value = "selectProv", required = false, defaultValue = "") String selectProv,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "pda_p28_correccionStockInicio";
		int indiceArticuloInicialPagina = 0;
		int indiceArticuloFinalPagina = 0;
		int totalArticulos = 0;
		
		
		
		if ((selectProv == null || "".equals(selectProv) || " ".equals(selectProv) || "null".equals(selectProv))
			    && (origenPantalla == null || !origenPantalla.contains("pdaP118PrehuecosAlmacen"))) {

			    origenPantalla = (String) model.get("origenPantalla");
			    selectProv = (String) model.get("selectProv");
			}


		/*if (selectProv==null || "".equals(selectProv) || " ".equals(selectProv)
			|| "null".equals(selectProv) && !origenPantalla.contains("pdaP118PrehuecosAlmacen")){
//			origenPantalla = request.getParameter("origenPantalla");
//			selectProv = request.getParameter("selectProv");
			
			origenPantalla = (String)model.get("origenPantalla");
			selectProv = (String)model.get("selectProv");
		}*/
		
		try {
			//Si viene de la opción DATOS REFERENCIA, pestaña Datos, Pedidos o Stock y además hay que calcular el CC, se calcula el CC.
			//Este código se ha introducido con la petición MISUMI-26. Antes, se calculaba el stock con CC antes de pinchar el link del stock que llama
			//precisamente a este controlador. Como en muchas ocasiones, ese link no se clicaba, se estaba haciendo una llamada al WS extra no necesaria.
			//Ahora el CC se calcula aquí, ahorrandonos por cada referencia una posible llamada al ws con el valor CC, en el caso de que no se pinche el
			//link.
			if((Constantes.MENU_PDA_DATOS_REFERENCIA_DATOS.equals(origen) || Constantes.MENU_PDA_DATOS_REFERENCIA_PEDIDOS.equals(origen)
				|| Constantes.MENU_PDA_DATOS_REFERENCIA_STOCK.equals(origen)
				|| Constantes.MENU_PDA_CAPTURA_RESTOS.equals(origen)
				|| Constantes.MENU_PDA_SACADA_RESTOS.equals(origen)
				|| Constantes.MENU_PDA_PREHUECOS.equals(origen)
				) && ("S").equals(calculoCC)){
				User user = (User) session.getAttribute("user");

				ConsultarStockRequestType stockTiendaRequest = new ConsultarStockRequestType();
				stockTiendaRequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
				stockTiendaRequest.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_CORRECION); //En este caso es una consulta basica, al 
				List<BigInteger> listaReferencias = new ArrayList<BigInteger>();
				listaReferencias.add(BigInteger.valueOf(codArt));

				//WS se le pasara una lista de referencias
				List<Long> referenciasMismoModeloProveedor = this.relacionArticuloService.findRefMismoModeloProveedor(codArt);
				for (Long articulo : referenciasMismoModeloProveedor) {
					String strArticulo = articulo + "";
					if (!strArticulo.equals(codArt + "")) { //El articulo consultado por el usuario  (el de la cabecera) ya esta metido en la lista
						listaReferencias.add(BigInteger.valueOf(articulo));
					}
				}

				stockTiendaRequest.setListaCodigosReferencia(listaReferencias.toArray(new BigInteger[listaReferencias.size()]));
				ConsultarStockResponseType stockTiendaResponse = this.correccionStockService.consultaStock(stockTiendaRequest,session);

				//Insertamos en la sesión el stock.
				if (stockTiendaResponse.getCodigoRespuesta().equals("OK")){
					session.setAttribute("consultaStock", stockTiendaResponse);
				}else{
					session.removeAttribute("consultaStock");
					//Si venimos de Captura Restos
					if (Constantes.MENU_PDA_CAPTURA_RESTOS.equals(origen)){
						resultado = "redirect:pdaP98CapturaRestos.do";
					}else if (Constantes.MENU_PDA_SACADA_RESTOS.equals(origen)){
						resultado = "redirect:pdaP99SacadaRestos.do";
					} else{
						resultado = "redirect:pdaP12DatosReferencia.do";
					}
					return resultado;
				}
			}

			ConsultarStockResponseType stockTiendaResponse = (ConsultarStockResponseType) session.getAttribute("consultaStock");

			if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_ELECCION_REFERENCIA_MADRE)){

				List<ReferenciaType> listaMadres = Arrays.asList(stockTiendaResponse.getListaReferencias());
				PagedListHolder<ReferenciaType> listHolder = new PagedListHolder<ReferenciaType>(listaMadres);
				listHolder.setPageSize(Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS);
				session.setAttribute("listaRelaciones", listHolder);
				PdaSeleccionStock pdaSeleccionStock = new PdaSeleccionStock();
				pdaSeleccionStock.setCodArtOrig(codArt);
				pdaSeleccionStock.setOrigen(origen);
				pdaSeleccionStock.setMMC(mmc);
				if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
					String mensajeError = stockTiendaResponse.getDescripcionRespuesta();
					model.addAttribute("mensajeError", mensajeError);
				} else if (stockTiendaResponse.getListaReferencias().length > 0){	
					boolean hasWarning = false;
					for (ReferenciaType referencia : stockTiendaResponse.getListaReferencias()){
						if (referencia.getCodigoError().equals(BigInteger.valueOf(new Long(2))) && !hasWarning){
							String mensajeError = referencia.getMensajeError();
							model.addAttribute("mensajeError", mensajeError);
							hasWarning = true;
						}

					}	
				}
				model.addAttribute("pdaSeleccionStock", pdaSeleccionStock);
				model.addAttribute("listaRelacion", listHolder);
				resultado = "pda_p29_correccionStockSeleccion";
			} else if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_PORCION_CONSUMIDOR)){
				PdaArticulo pdaArticulo = new PdaArticulo();
				pdaArticulo.setCodArtOrig(codArt);
				pdaArticulo.setOrigen(origen);
				pdaArticulo.setMMC(mmc);
				String pesoVariable = this.correccionStockService.consultarPcPesoVariable(codArt);
				pdaArticulo.setPesoVariable(pesoVariable);
				model.addAttribute("pdaDatosCab", new PdaDatosCabecera());
				session.setAttribute("pdaArticuloStock", pdaArticulo);
				resultado = "pda_p28_correccionStockInicio";
			} else if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_CORRECION) || stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_BASICA) || stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA)){
				PdaStock stock = new PdaStock();
				stock.setCodArtOrig(codArt);
				stock.setOrigen(origen);
				stock.setMMC(mmc);
				if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
					stock.setDescripcionError(stockTiendaResponse.getDescripcionRespuesta());
				} else if (stockTiendaResponse.getListaReferencias().length > 0){	
					boolean hasWarning = false;
					for (ReferenciaType referencia : stockTiendaResponse.getListaReferencias()){
						if (referencia.getCodigoError().equals(BigInteger.valueOf(new Long(2))) && !hasWarning){
							stock.setDescripcionError(referencia.getMensajeError());
							hasWarning = true;
						}

					}	
				}

				boolean referenciasTextil = false;

				if (stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_BASICA) || stockTiendaResponse.getTipoMensaje().equals(Constantes.STOCK_TIENDA_CONSULTA_BASICA_PISTOLA)) { //
					referenciasTextil = true;
				}

				//Una vez tenemos la lista a mostrar, eliminamos los elementos que tienen error 1.
				ReferenciaType[] listaFiltradaSinErrores = stockTiendaResponse.getListaReferencias().clone();

				for(ReferenciaType referenciaType:stockTiendaResponse.getListaReferencias()){
					if((new BigInteger("1")).equals(referenciaType.getCodigoError())){
						listaFiltradaSinErrores = (ReferenciaType[]) ArrayUtils.removeElement(listaFiltradaSinErrores, referenciaType);
					}
				}			   
				stock.setListaArticulos(this.obtenerListado(listaFiltradaSinErrores, referenciasTextil));

				int numeroPaginas = 1;
				if (referenciasTextil) {
					//Cálculo de los artículos de la página 
					indiceArticuloInicialPagina = 0;
					totalArticulos = stock.getListaArticulos().size();
					if (totalArticulos >= Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL){
						indiceArticuloFinalPagina = Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL;
					}else{
						indiceArticuloFinalPagina = totalArticulos;
					}

					stock.setListaArticulosPagina(new ArrayList<PdaArticulo>(stock.getListaArticulos().subList(indiceArticuloInicialPagina, indiceArticuloFinalPagina)));
					stock.setTotalArticulos(stock.getListaArticulos().size());
					stock.setPosicionGrupoArticulos(1);
					//Cálculo de total de páginas
					numeroPaginas = stock.getListaArticulos().size()/Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL;
					if ((numeroPaginas*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL)<stock.getListaArticulos().size()){
						numeroPaginas++;
					}
				} else {
					//Cálculo de los artículos de la página 
					indiceArticuloInicialPagina = 0;
					totalArticulos = stock.getListaArticulos().size();
					if (totalArticulos >= Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS){
						indiceArticuloFinalPagina = Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
					}else{
						indiceArticuloFinalPagina = totalArticulos;
					}

					stock.setListaArticulosPagina(new ArrayList<PdaArticulo>(stock.getListaArticulos().subList(indiceArticuloInicialPagina, indiceArticuloFinalPagina)));
					stock.setTotalArticulos(stock.getListaArticulos().size());
					stock.setPosicionGrupoArticulos(1);
					//Cálculo de total de páginas
					numeroPaginas = stock.getListaArticulos().size()/Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS;
					if ((numeroPaginas*Constantes.NUM_REGISTROS_PANTALLA_CORR_STOCKS)<stock.getListaArticulos().size()){
						numeroPaginas++;
					}
				}

				//Si solo existe una referencia y es de electro o bazar, se pinta el stock plataforma.
				if(stock.getListaArticulosPagina().size() == 1){
					User user = (User) session.getAttribute("user");
					
					PdaArticulo pdaArticulo = stock.getListaArticulosPagina().get(0);
					VDatosDiarioArt vDatosDiarioArt = obtenerDatosDiarioArt(pdaArticulo);

					if(vDatosDiarioArt != null && vDatosDiarioArt.getGrupo1().longValue() == new Long(Constantes.AREA_BAZAR).longValue() || vDatosDiarioArt.getGrupo1().longValue() == new Long(Constantes.AREA_ELECTRO).longValue()) {
						Long codArtPlat = pdaArticulo.getCodArt() != null ? pdaArticulo.getCodArt() : (pdaArticulo.getCodArtOrig() != null ? pdaArticulo.getCodArtOrig() : null);
						
						if(codArtPlat != null){
							Double stockPlataforma = this.obtenerStockPlataforma(codArtPlat, user.getCentro().getCodCentro());
							pdaArticulo.setStockPlataforma(stockPlataforma);
						}
					}
				}

				stock.setTotalPaginas(numeroPaginas);
				stock.setTipoMensaje(stockTiendaResponse.getTipoMensaje());
				model.addAttribute("pdaStock", stock);
				session.setAttribute("pdaStock", stock);
				resultado = "pda_p31_correccionStockPCN";
			}

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		model.addAttribute("origenPantalla", origenPantalla);
		model.addAttribute("selectProv", selectProv);
		model.addAttribute("procede", procede);
		model.addAttribute("codArt", codArt);
		
		session.setAttribute("origenPantalla", origenPantalla);
		session.setAttribute("selectProv", selectProv);
		

		return resultado;
	}

	@RequestMapping(value = "/pdaP28CorreccionStockInicio",method = RequestMethod.POST)
	public String processForm(@Valid final PdaDatosCabecera pdaDatosCab
			, ModelMap model,RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "pda_p30_correccionStockPCS";

		String origenPantalla = request.getParameter("origenPantalla");
		String selectProv = request.getParameter("selProv");

//	    String queryString = request.getQueryString();
	    
		boolean entroSesion = false;
		
		if (origenPantalla == null || "".equals(origenPantalla) || " ".equals(origenPantalla)
			|| "null".equals(origenPantalla)){
			
			origenPantalla = (String)session.getAttribute("origenPantalla");
			selectProv = (String)session.getAttribute("selectProv");
			entroSesion = true;
		}
		
		try {
			if (null != request.getParameter("actionNo") || null != request.getParameter("actionYes")){
//				String actionReset = request.getParameter("actionReset");
				ConsultarStockResponseType stockTiendaResponse = (ConsultarStockResponseType) session.getAttribute("consultaStock");
				ReferenciaType referencia = stockTiendaResponse.getListaReferencias()[0];
				PdaArticulo articulo = 	(PdaArticulo)session.getAttribute("pdaArticuloStock");
				articulo.setCodArt(referencia.getCodigoReferencia().longValue());
				articulo.setDescArt(referencia.getDescripcion());
				articulo.setPrecio(Utilidades.convertirDoubleAString(referencia.getPVP().floatValue(),"###0.000"));
				articulo.setTipo(referencia.getTipoReferencia());
				articulo.setKgs(Utilidades.convertirDoubleAString((referencia.getStock().floatValue())/((referencia.getBandejas()!=null && referencia.getBandejas().floatValue() != 0)?referencia.getBandejas().floatValue():1),"###0.000"));
				if (stockTiendaResponse.getCodigoRespuesta().equals(Constantes.STOCK_TIENDA_RESULTADO_WARN)){
					articulo.setDescripcionError(stockTiendaResponse.getDescripcionRespuesta());
				} else if (stockTiendaResponse.getListaReferencias().length > 0){	
					boolean hasWarning = false;
					for (ReferenciaType ref : stockTiendaResponse.getListaReferencias()){
						if (ref.getCodigoError().equals(BigInteger.valueOf(new Long(2))) && !hasWarning){
							articulo.setDescripcionError(ref.getMensajeError());
							hasWarning = true;
						}

					}	
				}
				model.addAttribute("pdaDatosCab", new PdaDatosCabecera());

				if (null != request.getParameter("actionYes")){
					articulo.setStock(Utilidades.convertirDoubleAString(new Float(0),"###0.00").replace(',', '.'));
					articulo.setUnidades(new BigInteger("0"));
				} else {
					articulo.setStock(Utilidades.convertirDoubleAString(referencia.getStock().floatValue(),"###0.00").replace(',', '.'));
					articulo.setUnidades(referencia.getBandejas());
				}

				model.addAttribute("pdaArticulo", articulo);
				
				if (null != request.getParameter("procede") && request.getParameter("procede").equalsIgnoreCase("pdaP115PrehuecosLineal")){
					model.addAttribute("procede", "pdaP115PrehuecosLineal");
				}
				session.setAttribute("pdaArticuloStock", articulo);
			}

			if (request.getParameter("actionPrev") != null){
				PdaArticulo articulo = 	(PdaArticulo)session.getAttribute("pdaArticuloStock");
				redirectAttributes.addAttribute("codArt", articulo.getCodArtOrig());
				if (articulo.getOrigen().equals("DR")){
					resultado = "redirect:pdaP12DatosReferencia.do?origenPantalla="+origenPantalla;
				}else if(articulo.getOrigen().equals("SP")){
					resultado = "redirect:pdaP13SegPedidos.do";
				// Devolución Fin de Campaña.
				}else if(articulo.getOrigen().equals("DVFN")){
					resultado = "redirect:pdaP62StockLinkVuelta.do?origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				// Devolución Orden de Retirada.
				}else if(articulo.getOrigen().equals("DVOR")){
					resultado = "redirect:pdaP63StockLinkVuelta.do?origenPantalla="+origenPantalla+"&selectProv="+selectProv;
				}else if(articulo.getOrigen().equals("REPO1")){
					resultado = "redirect:pdaP91ListadoRepoVuelta.do";
				}else if(articulo.getOrigen().equals("REPO2")){
					resultado = "redirect:pdaP92ListadoRepoAntVuelta.do";
				}else if(articulo.getOrigen().equals(Constantes.MENU_PDA_CAPTURA_RESTOS)){
					resultado = "redirect:pdaP98CapturaRestos.do";
				}else if(articulo.getOrigen().equals(Constantes.MENU_PDA_SACADA_RESTOS)){
					resultado = "redirect:pdaP99SacadaRestos.do";
				}else{
					resultado = "redirect:pdaP15MovStocks.do";
				}
			}

		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		if (!entroSesion){
			model.addAttribute("origenPantalla", origenPantalla);
			model.addAttribute("selectProv", selectProv);
		}
		
//		if (!"pda_p30_correccionStockPCS".equals(resultado)){
//			session.setAttribute(origenPantalla, "");
//			session.setAttribute(selectProv,"");
//		}

		return resultado;
	}

	private ArrayList<PdaArticulo> obtenerListado(ReferenciaType[] listaReferencias, boolean referenciasTextil) throws Exception{

		ArrayList<PdaArticulo> listaArticulos = new ArrayList<PdaArticulo>();
		for (ReferenciaType referencia : listaReferencias){

			PdaArticulo articulo = new PdaArticulo();

			if (referenciasTextil) { //Las referencias tratadas son de textil, se accede a la vista V_DATOS_ESPECIFICOS_TEXTIL para completar la información de la referencia
				List<PdaArticulo> listaArticulosTextil = this.relacionArticuloService.findDatosEspecificosTextil(referencia.getCodigoReferencia().longValue());

				for (PdaArticulo articuloTextil : listaArticulosTextil){
					articulo.setTemporada(articuloTextil.getTemporada());
					articulo.setEstructura(articuloTextil.getEstructura());
					articulo.setNumOrden(articuloTextil.getNumOrden());
					articulo.setModeloProveedor(articuloTextil.getModeloProveedor());
					articulo.setDescrTalla(articuloTextil.getDescrTalla());
					articulo.setDescrColor(articuloTextil.getDescrColor());
					articulo.setDenominacion(articuloTextil.getDenominacion());
				}
			}

			articulo.setCodArt(referencia.getCodigoReferencia().longValue());
			articulo.setDescArt(referencia.getDescripcion());
			if (null != referencia.getPVP()){
				articulo.setPrecio(Utilidades.convertirDoubleAString(referencia.getPVP().floatValue(),"###0.000").replace(',', '.'));
			}
			if (null != referencia.getBandejas()){
				articulo.setUnidades(referencia.getBandejas());
			}
			if (referenciasTextil) {
				articulo.setStock(referencia.getStock().intValue() +"");
			} else {
				articulo.setStock(Utilidades.convertirDoubleAString(referencia.getStock().floatValue(),"###0.000").replace(',', '.'));
			}
			articulo.setTipo(referencia.getTipoReferencia());
			articulo.setKgs(Utilidades.convertirDoubleAString(new Float(0),"###0.000").replace(',', '.'));
			listaArticulos.add(articulo);
		}
		return listaArticulos;
	}

	public VDatosDiarioArt obtenerDatosDiarioArt(PdaArticulo pdaArticulo) throws Exception{
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = null;

		Long codArt = pdaArticulo.getCodArt() != null ? pdaArticulo.getCodArt() : (pdaArticulo.getCodArtOrig() != null ? pdaArticulo.getCodArtOrig() : null);
		if(codArt != null){
			vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(codArt);
			vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
		}

		return vDatosDiarioArtRes;
	}
	
	private Double obtenerStockPlataforma(Long codArt, Long codCentro) throws Exception {
		Double result = null;
		try{
			StockPlataforma sp = new StockPlataforma();
			sp.setCodCentro(codCentro);
			sp.setCodArt(codArt);


			StockPlataforma spSalida = this.stockPlataformaService.find(sp);
			if (spSalida!=null && !spSalida.equals("") && spSalida.getStock()!=null && !spSalida.getStock().equals("")){

				result = spSalida.getStock().doubleValue();
			}
		} catch (Exception e) {
			result = new Double(-9999);
		}
		return result;
	}
	
}