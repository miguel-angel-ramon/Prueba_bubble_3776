package es.eroski.misumi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import es.eroski.misumi.model.ArtGamaRapid;
import es.eroski.misumi.model.CausaInactividad;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.EstadoCantidadExcel;
import es.eroski.misumi.model.GenericExcelFieldsVO;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.InformeHuecos;
import es.eroski.misumi.model.ListaDetallePedidoExcel;
import es.eroski.misumi.model.MensajeErrorExcel;
import es.eroski.misumi.model.ObjetoExcel;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.model.calendariovegalsa.DiaDetalleCalendarioVegalsa;
import es.eroski.misumi.util.iface.ExcelManager;
import es.eroski.misumi.util.iface.ExcelMostradorManager;
import net.sf.jxls.transformer.XLSTransformer;

@Service(value = "ExcelMostradorService")

public class ExcelMostradorManagerImpl implements ExcelMostradorManager {
	private static final String ALTA_CATALOGO_XLS = "/excel/altaCatalogo.xls";
	private static final String MIS_PEDIDOS_CANTIDADES_PEDIDAS_XLS = "/excel/misPedidosCantPed.xls";
	private static final String MIS_PEDIDOS_CANTIDADES_NO_SERVIDAS_XLS = "/excel/misPedidosCantNoServ.xls";
	private static final String MIS_PEDIDOS_CANTIDADES_CONFIRMADAS_XLS = "/excel/misPedidosCantConf.xls";
	private static final String DETALLE_PEDIDO_XLS = "/excel/detallePedido.xls";
	private static final String PEDIDO_ADICIONAL_XLS = "/excel/pedidoAdicional.xls";
	private static final String REFERENCIAS_CAMPANA_XLS = "/excel/referenciasCampana.xls";
	private static final String REFERENCIAS_CAMPANA_TEXTIL_XLS = "/excel/referenciasCampanaTextil.xls";
	private static final String REFERENCIAS_CAMPANA_TEXTIL_CAMPANA_XLS = "/excel/referenciasCampanaTextilCamp.xls";
	private static final String INFORME_HUECOS_XLS = "/excel/informeHuecos.xls";
	private static final String INTERTIENDA_XLS = "/excel/intertienda.xls";
	private static final String ALTA_CATALOGO_TEXTIL_XLS = "/excel/altaCatalogoTextil.xls";
	private static final String ENCARGOS_CLIENTE_XLS = "/excel/encargosCliente.xls";
	private static final String SFM_FAC_CAP_XLS = "/excel/sfmCapacidad.xls";
	private static final String ALTA_CATALOGO_RAPID_XLS = "/excel/altaCatalogoRapid.xls";
	private static final String DETALLE_CALENDARIO_VEGALSA = "/excel/detallePedidoCalendarioVegalsa.xls";
	 private static final String  ALARMAS_PLU_XLS = "/excel/alarmasPLU.xls";
	 
	private static Logger logger = Logger
			.getLogger(ExcelMostradorManagerImpl.class);
	
	public  void exportAltaCatalogo(List<GenericExcelVO> lista,
			String[] headers, String[] model,MessageSource messageSource,VArtCentroAlta vArtCentroAlta,
			VAgruComerRef vAgruComerGrupoArea,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,VAgruComerRef vAgruComerGrupoSubCategory,
			VAgruComerRef vAgruComerGrupoSegment, String marcaMaestroCentro, String pedir, String pedible, String tipolistado,
			HttpServletResponse response
			) throws Exception{
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p12_altasCatalogo.altasCatalogoExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String tipoListadoLabel = messageSource.getMessage("p01_header.tipolistado", null,
					LocaleContextHolder.getLocale());
			String areaLabel = messageSource.getMessage("p12_altasCatalogo.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p12_altasCatalogo.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p12_altasCatalogo.category", null,
					LocaleContextHolder.getLocale());
			String subcategoryLabel = messageSource.getMessage("p12_altasCatalogo.subcategory", null,
					LocaleContextHolder.getLocale());
			String segmentLabel = messageSource.getMessage("p12_altasCatalogo.segment", null,
					LocaleContextHolder.getLocale());
			String mmcLabel = messageSource.getMessage("p12_altasCatalogo.mmc", null,
					LocaleContextHolder.getLocale());
			String rotLabel = messageSource.getMessage("p12_altasCatalogo.rot", null,
					LocaleContextHolder.getLocale());
			String applyLabel = messageSource.getMessage("p12_altasCatalogo.apply", null,
					LocaleContextHolder.getLocale());
			String referenceLabel = messageSource.getMessage("p12_altasCatalogo.reference", null,
					LocaleContextHolder.getLocale());
			String pedibleLabel = messageSource.getMessage("p12_altasCatalogo.activableFacing", null,
					LocaleContextHolder.getLocale());
			
			
			String mmcSelected=null;
			if (marcaMaestroCentro!=null){
				if ("S".equals(marcaMaestroCentro)){
					mmcSelected= messageSource.getMessage("p12_altasCatalogo.si", null,
							LocaleContextHolder.getLocale());
				}else{
					mmcSelected= messageSource.getMessage("p12_altasCatalogo.no", null,
							LocaleContextHolder.getLocale());
				}
			}else{
				mmcSelected= messageSource.getMessage("p12_altasCatalogo.all", null,
						LocaleContextHolder.getLocale());
			}
			
			String applySelected=null;
			if (pedir!=null){
				if ("S".equals(pedir)){
					applySelected= messageSource.getMessage("p12_altasCatalogo.si", null,
							LocaleContextHolder.getLocale());
				}else{
					applySelected= messageSource.getMessage("p12_altasCatalogo.no", null,
							LocaleContextHolder.getLocale());
				}
			}else{
				applySelected= messageSource.getMessage("p12_altasCatalogo.all", null,
						LocaleContextHolder.getLocale());
			}
			
			String pedibleSelected=null;
			if (vAgruComerGrupoArea != null && Constantes.AREA_TEXTIL.equals(vAgruComerGrupoArea.getGrupo1().toString())) {
				if (pedible!=null){
					if (Constantes.REF_TEXTIL_PEDIBLE_SI.equals(pedible)){
						pedibleSelected= messageSource.getMessage("p12_altasCatalogo.si", null,
								LocaleContextHolder.getLocale());
					}else{
						pedibleSelected= messageSource.getMessage("p12_altasCatalogo.no", null,
								LocaleContextHolder.getLocale());
					}
				}else{
					pedibleSelected= messageSource.getMessage("p12_altasCatalogo.all", null,
							LocaleContextHolder.getLocale());
				}
			}
			
			String tipoListadoSelected=null;
			if (tipolistado!=null){
				if (Constantes.LIS_GAMA_TIPO_LISTADO_DATOS_GENERALES.equals(tipolistado)){
					tipoListadoSelected= messageSource.getMessage("p12_altasCatalogo.generalData", null,
							LocaleContextHolder.getLocale());
				}else{
					tipoListadoSelected= messageSource.getMessage("p12_altasCatalogo.SFM", null,
							LocaleContextHolder.getLocale());
				}
			}
			
			map.put("tipoListadoLabel", tipoListadoLabel);
			map.put("centroLabel", centroLabel);
			map.put("areaLabel", areaLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("subcategoryLabel", subcategoryLabel);
			map.put("segmentLabel", segmentLabel);
			map.put("mmcLabel", mmcLabel);
			map.put("rotLabel", rotLabel);
			map.put("applyLabel", applyLabel);
			map.put("pedibleLabel", pedibleLabel);
			map.put("referenceLabel", referenceLabel);
			map.put("centro", vArtCentroAlta.getCentro());
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("vAgruComerGrupoSubCategory", vAgruComerGrupoSubCategory);
			map.put("vAgruComerGrupoSegment", vAgruComerGrupoSegment);
			map.put("mmcSelected", mmcSelected);
			map.put("rot", vArtCentroAlta.getTipoRotacion());
			map.put("applySelected", applySelected);
			map.put("pedibleSelected", pedibleSelected);
			map.put("reference", vArtCentroAlta.getCodArticulo());
			map.put("excelTitle", excelName);
			map.put("tipoListadoSelected", tipoListadoSelected);
			
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(ALTA_CATALOGO_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);

			Sheet sheet = resultWorkbook.getSheetAt(0);
			// Ponemos algunas columnas más estrechas:
			int size = 1500;
			sheet.setColumnWidth(5, (int) size);	//Activa
			sheet.setColumnWidth(6, (int) size); //MMC
			sheet.setColumnWidth(8, (int) size); //Stock
			sheet.setColumnWidth(9, (int) (size + 500)); //Rotación
			sheet.setColumnWidth(10, (int) size); //Aprov
			sheet.setColumnWidth(11, (int) size); //U/C
			sheet.setColumnWidth(16, (int) size); // SFM
			sheet.setColumnWidth(19, (int) size); // IMC
			sheet.setColumnWidth(21, (int) size); //UFP

			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		   // transformer.transformXLS(templateFileName, map, "d:/AAresultUU.xls");
		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}
	
	public  void exportGamaRapid(List<GenericExcelVO> lista,
			String[] headers, String[] model,MessageSource messageSource,ArtGamaRapid artGamaRapid,
			VAgruComerRef vAgruComerGrupoArea,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,VAgruComerRef vAgruComerGrupoSubCategory,
			VAgruComerRef vAgruComerGrupoSegment,HttpServletResponse response) throws Exception{
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p12_altasCatalogoRapid.altasCatalogoExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String areaLabel = messageSource.getMessage("p12_altasCatalogoRapid.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p12_altasCatalogoRapid.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p12_altasCatalogoRapid.category", null,
					LocaleContextHolder.getLocale());
			String subcategoryLabel = messageSource.getMessage("p12_altasCatalogoRapid.subcategory", null,
					LocaleContextHolder.getLocale());
			String segmentLabel = messageSource.getMessage("p12_altasCatalogoRapid.segment", null,
					LocaleContextHolder.getLocale());
			String referenceLabel = messageSource.getMessage("p12_altasCatalogoRapid.reference", null,
					LocaleContextHolder.getLocale());
			
			map.put("centroLabel", centroLabel);
			map.put("areaLabel", areaLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("subcategoryLabel", subcategoryLabel);
			map.put("segmentLabel", segmentLabel);
			map.put("referenceLabel", referenceLabel);
			map.put("centro", artGamaRapid.getCodCentro()+"-"+artGamaRapid.getDescCentro());
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("vAgruComerGrupoSubCategory", vAgruComerGrupoSubCategory);
			map.put("vAgruComerGrupoSegment", vAgruComerGrupoSegment);
			
			map.put("reference", artGamaRapid.getCodArticulo());
			map.put("excelTitle", excelName);		
			
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(ALTA_CATALOGO_RAPID_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);

			Sheet sheet = resultWorkbook.getSheetAt(0);
			// Ponemos algunas columnas mas estrechas:
			int size = 2000;
			sheet.setColumnWidth(5, (int) (size + 1100));	
			sheet.setColumnWidth(9, (int) size);	
			sheet.setColumnWidth(10, (int) (size + 1100));	
			sheet.setColumnWidth(11, (int) size); 
			sheet.setColumnWidth(12, (int) size); 
			sheet.setColumnWidth(13, (int) size); 

			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}

	@Override
	public void exportMisPedidosCantPed(List<GenericExcelVO> lista,
			String[] headers, String[] model, MessageSource messageSource,
			Centro centro,
			String fechaPedidoDDMMYYYY,
			VAgruComerRef vAgruComerGrupoArea,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,
			SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p21_popUpCantidadesPedidas.cantidadesPedidasExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String fechaPedidoLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.fechaPedido", null,
					LocaleContextHolder.getLocale());
			String areaLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.area", null,
					LocaleContextHolder.getLocale());
			// MISUMI-319
			String mapaLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.mapa", null,
					LocaleContextHolder.getLocale()); 
			// --------
			String sectionLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.categoria", null,
					LocaleContextHolder.getLocale());		
			String totalReferenciasLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.totalReferencias", null,
					LocaleContextHolder.getLocale());
			String totalRefBajoPedidoLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.totalReferenciasBajoPedido", null,
					LocaleContextHolder.getLocale());
			String totalRefEmpujeLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.totalReferenciasEmpuje", null,
					LocaleContextHolder.getLocale());
			String totalRefImplCabLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.totalReferenciasImplantacionCabecera", null,
					LocaleContextHolder.getLocale());
			String totalRefIntertiendaLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.totalReferenciasIntertienda", null,
					LocaleContextHolder.getLocale());
			
			String totalCajasLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.totalCajas", null,
					LocaleContextHolder.getLocale());

			//C�lculo de totales para mostrar en el excel en modo string
			seguimientoMiPedidoDetalle.setPedTotalCajasBajoPedidoExcel((seguimientoMiPedidoDetalle.getPedTotalCajasBajoPedido()));
			seguimientoMiPedidoDetalle.setPedTotalCajasEmpujeExcel((seguimientoMiPedidoDetalle.getPedTotalCajasEmpuje()));
			seguimientoMiPedidoDetalle.setPedTotalCajasImplCabExcel((seguimientoMiPedidoDetalle.getPedTotalCajasImplCab()));
			seguimientoMiPedidoDetalle.setPedTotalCajasIntertiendaExcel((seguimientoMiPedidoDetalle.getPedTotalCajasIntertienda()));
			
			map.put("centroLabel", centroLabel);
			map.put("fechaPedidoLabel", fechaPedidoLabel);
			// MISUMI-319
			map.put("mapaLabel", mapaLabel); 
			map.put("areaLabel", areaLabel); 
			// ----------
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("totalReferenciasLabel", totalReferenciasLabel);
			map.put("totalRefBajoPedidoLabel", totalRefBajoPedidoLabel);
			map.put("totalRefEmpujeLabel", totalRefEmpujeLabel);
			map.put("totalRefImplCabLabel", totalRefImplCabLabel);
			map.put("totalRefIntertiendaLabel", totalRefIntertiendaLabel);
			map.put("totalCajasLabel", totalCajasLabel);
			
			//Obtenci�n del centro
			map.put("centro", centro);
			map.put("fechaPedido", Utilidades.formatearFecha(fechaPedidoDDMMYYYY, LocaleContextHolder.getLocale()));
			// MISUMI-319
			map.put("codMapa", seguimientoMiPedidoDetalle.getCodMapa()); 
			map.put("descMapa", seguimientoMiPedidoDetalle.getDescMapa()); 
			//------------
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("seguimientoMiPedidoDetalle", seguimientoMiPedidoDetalle);
			map.put("excelTitle", excelName);

			
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(MIS_PEDIDOS_CANTIDADES_PEDIDAS_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			Sheet sheet = resultWorkbook.getSheetAt(0);
			sheet.getPrintSetup().setLandscape(true);
			sheet.setColumnWidth(0, (int) 4000);
            sheet.setColumnWidth(1, (int) 11000);
			for (int i = 2; i < model.length; i++){
	            sheet.setColumnWidth(i, (int) 2300);
			}
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();

		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}

	@Override
	public void exportMisPedidosCantNoServ(List<GenericExcelVO> lista,
			String[] headers, String[] model, MessageSource messageSource,
			Centro centro,
			String fechaPedidoDDMMYYYY,
			VAgruComerRef vAgruComerGrupoArea,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,
			SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p22_popUpCantidadesNoServidas.cantidadesNoServidasExcelTitle", null,
				LocaleContextHolder.getLocale());
		String fileName = messageSource.getMessage("p22_popUpCantidadesNoServidas.cantidadesNoServidasExcelFileName", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+fileName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String fechaPedidoLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.fechaPedido", null,
					LocaleContextHolder.getLocale());
			// MISUMI-319
			String mapaLabel = messageSource.getMessage("p22_popUpCantidadesNoServidas.xls.mapa", null,
					LocaleContextHolder.getLocale()); 
			// ----------
			String areaLabel = messageSource.getMessage("p22_popUpCantidadesNoServidas.xls.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p22_popUpCantidadesNoServidas.xls.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p22_popUpCantidadesNoServidas.xls.categoria", null,
					LocaleContextHolder.getLocale());
			String totalReferenciasLabel = messageSource.getMessage("p22_popUpCantidadesNoServidas.xls.totalReferencias", null,
					LocaleContextHolder.getLocale());
			String totalRefNoServidasLabel = messageSource.getMessage("p22_popUpCantidadesNoServidas.xls.totalReferenciasNoServidas", null,
					LocaleContextHolder.getLocale());
			String totalCajasLabel = messageSource.getMessage("p22_popUpCantidadesNoServidas.xls.totalCajas", null,
					LocaleContextHolder.getLocale());

			//C�lculo de totales para mostrar en el excel en modo string
			seguimientoMiPedidoDetalle.setNsrTotalCajasNoServidasExcel((seguimientoMiPedidoDetalle.getNsrTotalCajasNoServidas()));

			map.put("centroLabel", centroLabel);
			map.put("fechaPedidoLabel", fechaPedidoLabel);
			// MISUMI-319
			map.put("mapaLabel", mapaLabel); 
			map.put("areaLabel", areaLabel); 
			// -----------
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("totalReferenciasLabel", totalReferenciasLabel);
			map.put("totalRefNoServidasLabel", totalRefNoServidasLabel);
			map.put("totalCajasLabel", totalCajasLabel);
			
			//Obtenci�n del centro
			map.put("centro", centro);
			// MISUMI-319
			map.put("codMapa", seguimientoMiPedidoDetalle.getCodMapa()); 
			map.put("descMapa", seguimientoMiPedidoDetalle.getDescMapa()); 
			// ----------
			map.put("fechaPedido", Utilidades.formatearFecha(fechaPedidoDDMMYYYY, LocaleContextHolder.getLocale()));
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("seguimientoMiPedidoDetalle", seguimientoMiPedidoDetalle);
			map.put("excelTitle", excelName);

			
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(MIS_PEDIDOS_CANTIDADES_NO_SERVIDAS_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			Sheet sheet = resultWorkbook.getSheetAt(0);
			sheet.getPrintSetup().setLandscape(true);
			sheet.setColumnWidth(0, (int) 4000);
            sheet.setColumnWidth(1, (int) 11000);
			sheet.setColumnWidth(2, (int) 2200);
			sheet.setColumnWidth(3, (int) 9000);
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();

		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}

	@Override
	public void exportMisPedidosCantConf(List<GenericExcelVO> lista,
			String[] headers, String[] model, MessageSource messageSource,
			Centro centro,
			String fechaPedidoDDMMYYYY,
			VAgruComerRef vAgruComerGrupoArea,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,
			SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p23_popUpCantidadesConfirmadas.cantidadesConfirmadasExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String fechaPedidoLabel = messageSource.getMessage("p21_popUpCantidadesPedidas.xls.fechaPedido", null,
					LocaleContextHolder.getLocale());
			// MISUMI-319
			String mapaLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.mapa", null,
					LocaleContextHolder.getLocale()); 
			// ---------
			String areaLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.categoria", null,
					LocaleContextHolder.getLocale());
			String totalReferenciasLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.totalReferencias", null,
					LocaleContextHolder.getLocale());
			String totalRefBajoPedidoLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.totalReferenciasBajoPedido", null,
					LocaleContextHolder.getLocale());
			String totalRefEmpujeLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.totalReferenciasEmpuje", null,
					LocaleContextHolder.getLocale());
			String totalRefImplCabLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.totalReferenciasImplantacionCabecera", null,
					LocaleContextHolder.getLocale());
			String totalRefIntertiendaLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.totalReferenciasIntertienda", null,
					LocaleContextHolder.getLocale());
			String totalCajasLabel = messageSource.getMessage("p23_popUpCantidadesConfirmadas.xls.totalCajas", null,
					LocaleContextHolder.getLocale());

			//C�lculo de totales para mostrar en el excel en modo string
			seguimientoMiPedidoDetalle.setConfTotalCajasBajoPedidoExcel((seguimientoMiPedidoDetalle.getConfTotalCajasBajoPedido()));
			seguimientoMiPedidoDetalle.setConfTotalCajasEmpujeExcel((seguimientoMiPedidoDetalle.getConfTotalCajasEmpuje()));
			seguimientoMiPedidoDetalle.setConfTotalCajasImplCabExcel((seguimientoMiPedidoDetalle.getConfTotalCajasImplCab()));
			seguimientoMiPedidoDetalle.setConfTotalCajasIntertiendaExcel((seguimientoMiPedidoDetalle.getConfTotalCajasIntertienda()));

			map.put("centroLabel", centroLabel);
			map.put("fechaPedidoLabel", fechaPedidoLabel);
			// MISUMI-319
			map.put("mapaLabel", mapaLabel); 
			map.put("areaLabel", areaLabel); 
			// ----------
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("totalReferenciasLabel", totalReferenciasLabel);
			map.put("totalRefBajoPedidoLabel", totalRefBajoPedidoLabel);
			map.put("totalRefEmpujeLabel", totalRefEmpujeLabel);
			map.put("totalRefImplCabLabel", totalRefImplCabLabel);
			map.put("totalRefIntertiendaLabel", totalRefIntertiendaLabel);
			map.put("totalCajasLabel", totalCajasLabel);
			
			//Obtenci�n del centro
			map.put("centro", centro);
			// MISUMI-319
			map.put("codMapa", seguimientoMiPedidoDetalle.getCodMapa()); 
			map.put("descMapa", seguimientoMiPedidoDetalle.getDescMapa()); 
			// ----------
			map.put("fechaPedido", Utilidades.formatearFecha(fechaPedidoDDMMYYYY, LocaleContextHolder.getLocale()));
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("seguimientoMiPedidoDetalle", seguimientoMiPedidoDetalle);
			map.put("excelTitle", excelName);

			
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(MIS_PEDIDOS_CANTIDADES_CONFIRMADAS_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			Sheet sheet = resultWorkbook.getSheetAt(0);
			sheet.getPrintSetup().setLandscape(true);
			sheet.setColumnWidth(0, (int) 4000);
            sheet.setColumnWidth(1, (int) 11000);
			for (int i = 2; i < model.length; i++){
	            sheet.setColumnWidth(i, (int) 2300);
			}
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();

		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}

	@Override
	public void exportDetalleMostrador(List<GenericExcelVO> lista,
			String[] headers, String[] columnModel,Integer[] widths, MessageSource messageSource,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p70_detalladoPedido.detallePedidoExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		

		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<ListaDetallePedidoExcel> listaExcel = new ArrayList<ListaDetallePedidoExcel>();
			
			//Buscar la posición en la que hay que pintar la columna cantidad
			List<String> listColumns = Arrays.asList(columnModel);
			int posColCantidad = listColumns.indexOf("cantidad");
			int posColEstado = listColumns.indexOf("estadoPedido");
			int posColMensajeError = listColumns.size();
			
			for (GenericExcelVO genericExcelVO : lista) {
				
				ListaDetallePedidoExcel objetoExcelLista = new ListaDetallePedidoExcel();
				
				Class<? extends Object> clase = genericExcelVO.getClass();
				
				//Informamos la listaAnterior de la clase ListaExcel
				List<ObjetoExcel> listaAnterior = new ArrayList<ObjetoExcel>();
				for (int i=0; i <posColCantidad; i++){ 
					
					int indFiled = i+1; //los getField empiezan por getField1
					
					ObjetoExcel field = new ObjetoExcel();
					Method getterField = clase.getMethod("getField" + indFiled);
					field.setValor((Object)getterField.invoke(genericExcelVO, new Object[0]));
					
					listaAnterior.add(field);
				}
				//Añadimos la la primera lista (listaAnterior)
				objetoExcelLista.setListaAnterior(listaAnterior);
				
			
				List<EstadoCantidadExcel> listaEstadoCantidad = new ArrayList<EstadoCantidadExcel>();
				if (posColCantidad != -1) {	//Existe la columna cantidad
					//Informamos la listaColorCantidad, que tendra uno o cero elementos	

					EstadoCantidadExcel field = new EstadoCantidadExcel();
					
					Method getterFieldCantidad = clase.getMethod("getField" + (posColCantidad + 1));
					Object cantidad = (Object)getterFieldCantidad.invoke(genericExcelVO, new Object[0]);
					field.setCantidad(cantidad);
					
					Method getterFieldEstado = clase.getMethod("getField41");
					field.setEstado((Object)getterFieldEstado.invoke(genericExcelVO, new Object[0]));	
		
					//Para controlar si se pinta cantidad en el excel en los estados I y B.
					//Si el valor del empuje es 0 o si la cantidad es mayor a 0, se muestra dato o lo
					//que es lo mismo, si el valor del empuje es > 0 y la cantidad es 0 no se muestra.
					Method getterFieldUnifFlEmpuje = clase.getMethod("getField42");
					String valorUnidEmpujeStr = (String)(Object)getterFieldUnifFlEmpuje.invoke(genericExcelVO, new Object[0]); 
					
					boolean mostrarEmpuje = false;
					
					Integer valorUnidEmpuje = null;
					if(valorUnidEmpujeStr != null){
						valorUnidEmpuje =  Integer.parseInt(valorUnidEmpujeStr);	
					}else{
						valorUnidEmpuje = new Integer(0);
					}
					
					String cantidadStr = ((String)cantidad).trim();
					String regex = "[0-9]+";
					
					Integer cantidadInt = -1;
					if (cantidadStr.matches(regex)) {
						cantidadInt = Integer.parseInt(cantidadStr);
					}
										
					mostrarEmpuje = (((new Integer(0)).compareTo(valorUnidEmpuje) < 0)  && ((new Integer(0)).equals(cantidadInt)));
					if(mostrarEmpuje && (("I").equals(field.getEstado()) || ("B").equals(field.getEstado()))){
						field.setEstado("EMPUJE");	
					}
					
					listaEstadoCantidad.add(field);

				}	
				//Añadimos la lista del medio (listaColorCantidad)
				objetoExcelLista.setListaEstadoCantidad(listaEstadoCantidad);
				
				
				//Informamos la listaAnterior de la clase ListaExcel
				List<ObjetoExcel> listaPosterior = new ArrayList<ObjetoExcel>();
				int indFiled = 0;
				//for (int i = posColCantidad + 1; i < 40; i++){ //los getField terminan en  getField41
				for (int i = posColCantidad + 1; i < posColMensajeError; i++){ //los getField terminan en  getField41
					
					indFiled = i+1; //los getField empiezan por getField1 
					
					ObjetoExcel field = new ObjetoExcel();
					Method getterField = clase.getMethod("getField" + indFiled);
					field.setValor((Object)getterField.invoke(genericExcelVO, new Object[0]));
					
					listaPosterior.add(field);
				}
				//Añadimos la la primera lista (listaPosterior)
				objetoExcelLista.setListaPosterior(listaPosterior);

				
				//Tratamiento para la columana de error. Siempre sera la ultima
				List<MensajeErrorExcel> listaMensajeError = new ArrayList<MensajeErrorExcel>();
				MensajeErrorExcel field = new MensajeErrorExcel();
				
				Method getterFieldMensajeError = clase.getMethod("getField" + (indFiled + 1));
				field.setMensajeError((Object)getterFieldMensajeError.invoke(genericExcelVO, new Object[0]));		
				listaMensajeError.add(field);
				
				//Añadimos la lista con el mensaje de error (listaMensajeError)
				objetoExcelLista.setListaMensajeError(listaMensajeError);
				
				
				
				
				//Vamos metiendo a la lisa el objeto de tres listas
				listaExcel.add(objetoExcelLista);
			}
			
			
			
			

			map.put("excelTitle", excelName);
			
			
			List<String> listHeaders = Arrays.asList(headers);
			listHeaders = new ArrayList<String>(listHeaders);
			listHeaders.add(Constantes.COLUMNA_ESTADO);
			
			String[] newHeaders = new String[listHeaders.size()];
			newHeaders = listHeaders.toArray(newHeaders);
			
			map.put("headers", newHeaders);
			
			//map.put("posiciones", headers);
			//map.put("model", model);
			map.put("records", listaExcel);
			
		    XLSTransformer transformer = new XLSTransformer();
		    
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(DETALLE_PEDIDO_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
			for(Integer i = 0; i < widths.length; i++){
				//sheet.setColumnWidth(i, (widths[i] * 36));
				sheet.setColumnWidth(i, (widths[i] * 33));
				
			}
			sheet.setColumnWidth(widths.length, 5000); //Para la columana estado. Esta no viene de pantalla
			
			//Fijar tamaños de columnas
			/*
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
            sheet.setColumnWidth(0, (int) 0);
            sheet.setColumnWidth(1, (int) 3000);
            sheet.setColumnWidth(2, (int) 3000);
            sheet.setColumnWidth(3, (int) 9800);
            sheet.setColumnWidth(4, (int) 1900);
            sheet.setColumnWidth(5, (int) 2600);
            sheet.setColumnWidth(6, (int) 2600);
            sheet.setColumnWidth(7, (int) 1500);
            sheet.setColumnWidth(8, (int) 3000);
            sheet.setColumnWidth(9, (int) 3000);
            sheet.setColumnWidth(10, (int) 2600);
            */
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		
		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
		
	}
	
	public  void exportPedidoAdicional(List<GenericExcelVO> lista,
			String[] headers, String[] model,Integer[] widths, MessageSource messageSource,Centro centro,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory, Boolean mac, Long referencia, Long clasePedido, String descripcion, 
			String descPeriodo, String espacioPromo,
			HttpServletResponse response
			) throws Exception{
		response.setContentType("text/xls; charset=utf-8");
		String excelNameResource = "p40_pedidoAdicional.pedidoAdicionalExcelTitle_" + clasePedido;
		String excelName = messageSource.getMessage(excelNameResource, null,
				LocaleContextHolder.getLocale());
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p40_pedidoAdicional.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p40_pedidoAdicional.category", null,
					LocaleContextHolder.getLocale());
			String macLabel = messageSource.getMessage("p40_pedidoAdicional.mca", null,
					LocaleContextHolder.getLocale());
			String referenceLabel = messageSource.getMessage("p40_pedidoAdicional.reference", null,
					LocaleContextHolder.getLocale());
			String descriptionLabel = messageSource.getMessage("p46_pedidoAdicionalVC.descripcion", null,
					LocaleContextHolder.getLocale());
			String ofertaLabel = messageSource.getMessage("p43_pedidoAdicionalMO.oferta", null,
					LocaleContextHolder.getLocale());
			String promocionLabel = messageSource.getMessage("p43_pedidoAdicionalMO.promocion", null,
					LocaleContextHolder.getLocale());
			
			
			String macSelected=null;
			if (mac!=null){
				if (mac){
					macSelected= messageSource.getMessage("p40_pedidoAdicional.si", null,
							LocaleContextHolder.getLocale());
				}else{
					macSelected= messageSource.getMessage("p40_pedidoAdicional.no", null,
							LocaleContextHolder.getLocale());
				}
			}else{
				macSelected= messageSource.getMessage("p40_pedidoAdicional.no", null,
						LocaleContextHolder.getLocale());
			}
			
			
			map.put("centroLabel", centroLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("referenceLabel", referenceLabel);
			map.put("macLabel", macLabel);
			map.put("descriptionLabel", descriptionLabel);
			map.put("ofertaLabel", ofertaLabel);
			map.put("promocionLabel", promocionLabel);
			map.put("centro", centro);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("description", descripcion);
			map.put("macSelected", macSelected);
			map.put("oferta", descPeriodo);
			map.put("promocion", espacioPromo);
			map.put("reference", referencia);
			map.put("excelTitle", excelName);
			
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(PEDIDO_ADICIONAL_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
			for(Integer i = 0; i < widths.length; i++){
				sheet.setColumnWidth(i, (widths[i] * 41));
			}
           // sheet.setColumnWidth(0, (int) 2500);
            //sheet.setColumnWidth(1, (int) 9000);
			//for (int i = 2; i < model.length; i++){
	        //    s
			//}
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}

	@Override
	public void exportMisCampanaOfer(List<GenericExcelVO> lista,
			String[] headers, String[] model, MessageSource messageSource,
			Centro centro, String campana, String campanaCompleta, 
			String oferta, String ofertaCompleta, 
			String tipoOC, 
			VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,
			VAgruComerRef vAgruComerGrupoSubcategory,
			VAgruComerRef vAgruComerGrupoSegmento, String textil,
			boolean mostrarColumnasSegCamp,
			HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p28_popUpRefCampanas.misCampanasOferExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String campanaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.campana", null,
					LocaleContextHolder.getLocale());
			String ofertaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.oferta", null,
					LocaleContextHolder.getLocale());
			String areaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.categoria", null,
					LocaleContextHolder.getLocale());
			String subcategoryLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.subcategoria", null,
					LocaleContextHolder.getLocale());
			String segmentoLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.segmento", null,
					LocaleContextHolder.getLocale());

			map.put("centroLabel", centroLabel);
			map.put("campanaLabel", campanaLabel);
			map.put("ofertaLabel", ofertaLabel);
			map.put("areaLabel", areaLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("subcategoryLabel", subcategoryLabel);
			map.put("segmentoLabel", segmentoLabel);
			
			//Cabeceras de listado
			String cabReferenciaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.referencia", null,
					LocaleContextHolder.getLocale());
			String cabDescripcionLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.descripcion", null,
					LocaleContextHolder.getLocale());
			String cabDenominacionLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.denominacion", null,
					LocaleContextHolder.getLocale());
			String cabEmpujeImpLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.empujeImp", null,
					LocaleContextHolder.getLocale());
			String cabServidoLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.servido", null,
					LocaleContextHolder.getLocale());
			String cabCampanaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.campana", null,
					LocaleContextHolder.getLocale());
			String cabNumNsrLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.numNsr", null,
					LocaleContextHolder.getLocale());
			String cabUnidadesNsrLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.unidadesNsr", null,
					LocaleContextHolder.getLocale());
			String cabVentasActualesLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.ventasActuales", null,
					LocaleContextHolder.getLocale());
			String cabPrevisionTotalLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.previsionTotal", null,
					LocaleContextHolder.getLocale());
			String cabStockCentroLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.stockCentro", null,
					LocaleContextHolder.getLocale());
			String cabStockDiasLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.stockDias", null,
					LocaleContextHolder.getLocale());
			String cabColorLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.color", null,
					LocaleContextHolder.getLocale());
			String cabTallaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.talla", null,
					LocaleContextHolder.getLocale());
			String cabModeloProveedorLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.modeloProveedor", null,
					LocaleContextHolder.getLocale());
			String cabSeccionLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.seccion", null,
					LocaleContextHolder.getLocale());
			String cabCategoriaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.categoria", null,
					LocaleContextHolder.getLocale());
			String cabSubcategoriaLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.subcategoria", null,
					LocaleContextHolder.getLocale());
			String cabSegmentoLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.segmento", null,
					LocaleContextHolder.getLocale());
			String cabModeloLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.modelo", null,
					LocaleContextHolder.getLocale());
			
			
			map.put("cabSeccionLabel", cabSeccionLabel);
			map.put("cabCategoriaLabel", cabCategoriaLabel);
			map.put("cabSubcategoriaLabel", cabSubcategoriaLabel);
			map.put("cabSegmentoLabel", cabSegmentoLabel);
			map.put("cabReferenciaLabel", cabReferenciaLabel);
			map.put("cabDenominacionLabel", cabDenominacionLabel);
			map.put("cabDescripcionLabel", cabDescripcionLabel);
			map.put("cabEmpujeImpLabel", cabEmpujeImpLabel);
			map.put("cabServidoLabel", cabServidoLabel);
			map.put("cabCampanaLabel", cabCampanaLabel);
			map.put("cabNumNsrLabel", cabNumNsrLabel);
			map.put("cabUnidadesNsrLabel", cabUnidadesNsrLabel);
			map.put("cabVentasActualesLabel", cabVentasActualesLabel);
			map.put("cabPrevisionTotalLabel", cabPrevisionTotalLabel);
			map.put("cabStockCentroLabel", cabStockCentroLabel);
			map.put("cabStockDiasLabel", cabStockDiasLabel);
			map.put("cabColorLabel", cabColorLabel);
			map.put("cabTallaLabel", cabTallaLabel);
			map.put("cabModeloProveedorLabel", cabModeloProveedorLabel);
			map.put("cabModeloLabel", cabModeloLabel);
			
			//Obtención del centro
			map.put("centro", centro);
			map.put("campana", campanaCompleta);
			map.put("oferta", ofertaCompleta);
			map.put("tipoOC", tipoOC.toUpperCase());
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("vAgruComerGrupoSubcategory", vAgruComerGrupoSubcategory);
			map.put("vAgruComerGrupoSegmento", vAgruComerGrupoSegmento);
			map.put("excelTitle", excelName);
			map.put("textil", textil);

			
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);

			//Columnas parametrizadas porque  hay referencias en las que no se pueden calcular las UC
			if (mostrarColumnasSegCamp){
				
				String cabEmpujeImpCajasLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.empujeImpCajas", null,
						LocaleContextHolder.getLocale());
				String cabServidoCajasLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.servidoCajas", null,
						LocaleContextHolder.getLocale());
				String cabCampanaCajasLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.campanaCajas", null,
						LocaleContextHolder.getLocale());
				String cabNsrCajasLabel = messageSource.getMessage("p28_popUpRefCampanas.xls.cab.NsrCajas", null,
						LocaleContextHolder.getLocale());
				
				map.put("cabEmpujeImpCajasLabel", cabEmpujeImpCajasLabel);
				map.put("cabServidoCajasLabel", cabServidoCajasLabel);
				map.put("cabCampanaCajasLabel", cabCampanaCajasLabel);
				map.put("cabNsrCajasLabel", cabNsrCajasLabel);
				map.put("mostrarColumnasSegCamp", "S");

			}else{
				map.put("mostrarColumnasSegCamp", "N");
			}
			//Fin de columnas parametrizadas
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = null;
		    if (textil == null){
		    	templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(REFERENCIAS_CAMPANA_XLS);
		    }else{
		    	if (mostrarColumnasSegCamp){
		    		templateIS = this.getClass().getClassLoader()
		    				.getResourceAsStream(REFERENCIAS_CAMPANA_TEXTIL_XLS);
		    	}else{
		    		templateIS = this.getClass().getClassLoader()
		    				.getResourceAsStream(REFERENCIAS_CAMPANA_TEXTIL_CAMPANA_XLS);
		    	}
		    }

		    org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);

			//Fijar tamaños de columnas
			Sheet sheet = resultWorkbook.getSheetAt(0);
			sheet.getPrintSetup().setLandscape(true);
            sheet.setColumnWidth(0, (int) 3200);
            sheet.setColumnWidth(1, (int) 5500);
            sheet.setColumnWidth(2, (int) 3000);
            sheet.setColumnWidth(3, (int) 3000);
            sheet.setColumnWidth(4, (int) 3000);
            sheet.setColumnWidth(5, (int) 3000);
            sheet.setColumnWidth(6, (int) 3000);
            sheet.setColumnWidth(7, (int) 3000);
            sheet.setColumnWidth(8, (int) 3000);
            sheet.setColumnWidth(9, (int) 3000);
            sheet.setColumnWidth(10, (int) 3000);
            sheet.setColumnWidth(11, (int) 3000);
            sheet.setColumnWidth(12, (int) 3000);
            sheet.setColumnWidth(13, (int) 3000);
			
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();

		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}
	
	@Override
	public void exportInformeHuecos(List<InformeHuecos> listaHuecos, List<CausaInactividad> listaCausas,MessageSource messageSource, Centro centro,HttpServletResponse response)
			throws Exception {
		
		response.setHeader("Content-Disposition",
				"attachment; filename=informeHuecos.xls");
		OutputStream out = null;

		try {
		
			SimpleDateFormat df = new SimpleDateFormat("E-dd-M");
			Object[] args = new Object[] { df.format(new Date()) };

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", messageSource.getMessage("p33_informeHuecosPopup.excel.title", args ,LocaleContextHolder.getLocale()));
			map.put("centroTitle", messageSource.getMessage("p33_informeHuecosPopup.excel.centro", null,LocaleContextHolder.getLocale()));
			map.put("tipoIncidencia1Texto", messageSource.getMessage("p33_informeHuecosPopup.excel.tipoIncidencia1.texto", null,LocaleContextHolder.getLocale()));
			map.put("tipoIncidencia1Texto", messageSource.getMessage("p33_informeHuecosPopup.excel.tipoIncidencia1.texto", null,LocaleContextHolder.getLocale()));
			map.put("tipoIncidencia2Texto", messageSource.getMessage("p33_informeHuecosPopup.excel.tipoIncidencia2.texto", null,LocaleContextHolder.getLocale()));
			map.put("tipoIncidencia3Texto", messageSource.getMessage("p33_informeHuecosPopup.excel.tipoIncidencia3.texto", null,LocaleContextHolder.getLocale()));
			map.put("seccion", messageSource.getMessage("p33_informeHuecosPopup.excel.seccion", null,LocaleContextHolder.getLocale()));
			map.put("pasillo", messageSource.getMessage("p33_informeHuecosPopup.excel.pasillo", null,LocaleContextHolder.getLocale()));
			map.put("referencia", messageSource.getMessage("p33_informeHuecosPopup.excel.referencia", null,LocaleContextHolder.getLocale()));
			map.put("denominacion", messageSource.getMessage("p33_informeHuecosPopup.excel.denominacion", null,LocaleContextHolder.getLocale()));
			map.put("mmc", messageSource.getMessage("p33_informeHuecosPopup.excel.mmc", null,LocaleContextHolder.getLocale()));
			map.put("abcat", messageSource.getMessage("p33_informeHuecosPopup.excel.abcat", null,LocaleContextHolder.getLocale()));
			map.put("tipoIncidencia", messageSource.getMessage("p33_informeHuecosPopup.excel.tipoIncidencia", null,LocaleContextHolder.getLocale()));
			map.put("fechaUltimaOferta", messageSource.getMessage("p33_informeHuecosPopup.excel.fechaUltimaOferta", null,LocaleContextHolder.getLocale()));
			map.put("oferta", messageSource.getMessage("p33_informeHuecosPopup.excel.oferta", null,LocaleContextHolder.getLocale()));
			map.put("cc", messageSource.getMessage("p33_informeHuecosPopup.excel.cc", null,LocaleContextHolder.getLocale()));
			map.put("ventaMedia", messageSource.getMessage("p33_informeHuecosPopup.excel.ventaMedia", null,LocaleContextHolder.getLocale()));
			map.put("tipoAprov", messageSource.getMessage("p33_informeHuecosPopup.excel.tipoAprov", null,LocaleContextHolder.getLocale()));
			map.put("stockTeorico", messageSource.getMessage("p33_informeHuecosPopup.excel.stockTeorico", null,LocaleContextHolder.getLocale()));
			map.put("causaInactividad", messageSource.getMessage("p33_informeHuecosPopup.excel.causaInactividad", null,LocaleContextHolder.getLocale()));
			map.put("codigo", messageSource.getMessage("p33_informeHuecosPopup.excel.codigo", null,LocaleContextHolder.getLocale()));
			map.put("denominacion", messageSource.getMessage("p33_informeHuecosPopup.excel.denominacion", null,LocaleContextHolder.getLocale()));
			
			map.put("centro", centro.getCodCentro() + " - " + centro.getDescripCentro());
			map.put("listaHuecos", listaHuecos);
			map.put("listaCausas", listaCausas);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(INFORME_HUECOS_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);

			//Fijar tamaños de columnas
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
            sheet.setColumnWidth(0, (int) 2800);
            sheet.setColumnWidth(1, (int) 2300);
            sheet.setColumnWidth(2, (int) 2700);
            sheet.setColumnWidth(3, (int) 11000);
            sheet.setColumnWidth(4, (int) 1000);
            sheet.setColumnWidth(5, (int) 1000);
            sheet.setColumnWidth(6, (int) 1000);
            sheet.setColumnWidth(7, (int) 3000);
            sheet.setColumnWidth(8, (int) 1000);
            sheet.setColumnWidth(9, (int) 1000);
            sheet.setColumnWidth(10, (int) 1000);
            sheet.setColumnWidth(11, (int) 1500);
            sheet.setColumnWidth(12, (int) 1500);
            sheet.setColumnWidth(13, (int) 1000);
			
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
			} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				} 
					finally {
					if (out != null) {
						try {
							out.flush();
							out.close();
						} catch (IOException e) {
							//logger.error(StackTraceManager.getStackTrace(e));
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							throw e;
						}
					}
				}
	}
	
	
	public  void exportAltaCatalogoTextil(List<VArtCentroAlta> lista,
			String[] headers, String[] model,MessageSource messageSource,VArtCentroAlta vArtCentroAlta,
			VAgruComerRef vAgruComerGrupoArea,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,VAgruComerRef vAgruComerGrupoSubCategory,
			VAgruComerRef vAgruComerGrupoSegment, String marcaMaestroCentro, String pedir, String pedible, String tipolistado,
			HttpServletResponse response
			) throws Exception{
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p12_altasCatalogo.altasCatalogoExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String tipoListadoLabel = messageSource.getMessage("p01_header.tipolistado", null,
					LocaleContextHolder.getLocale());
			String areaLabel = messageSource.getMessage("p12_altasCatalogo.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p12_altasCatalogo.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p12_altasCatalogo.category", null,
					LocaleContextHolder.getLocale());
			String subcategoryLabel = messageSource.getMessage("p12_altasCatalogo.subcategory", null,
					LocaleContextHolder.getLocale());
			String segmentLabel = messageSource.getMessage("p12_altasCatalogo.segment", null,
					LocaleContextHolder.getLocale());
			String mmcLabel = messageSource.getMessage("p12_altasCatalogo.mmc", null,
					LocaleContextHolder.getLocale());
			String applyLabel = messageSource.getMessage("p12_altasCatalogo.apply", null,
					LocaleContextHolder.getLocale());
			String referenceLabel = messageSource.getMessage("p12_altasCatalogo.reference", null,
					LocaleContextHolder.getLocale());
			String pedibleLabel = messageSource.getMessage("p12_altasCatalogo.activableFacing", null,
					LocaleContextHolder.getLocale());
			
			
			String mmcSelected=null;
			if (marcaMaestroCentro!=null){
				if ("S".equals(marcaMaestroCentro)){
					mmcSelected= messageSource.getMessage("p12_altasCatalogo.si", null,
							LocaleContextHolder.getLocale());
				}else{
					mmcSelected= messageSource.getMessage("p12_altasCatalogo.no", null,
							LocaleContextHolder.getLocale());
				}
			}else{
				mmcSelected= messageSource.getMessage("p12_altasCatalogo.all", null,
						LocaleContextHolder.getLocale());
			}
			
			String applySelected=null;
			if (pedir!=null){
				if ("S".equals(pedir)){
					applySelected= messageSource.getMessage("p12_altasCatalogo.si", null,
							LocaleContextHolder.getLocale());
				}else{
					applySelected= messageSource.getMessage("p12_altasCatalogo.no", null,
							LocaleContextHolder.getLocale());
				}
			}else{
				applySelected= messageSource.getMessage("p12_altasCatalogo.all", null,
						LocaleContextHolder.getLocale());
			}
			
			String pedibleSelected=null;
			if (vAgruComerGrupoArea != null && Constantes.AREA_TEXTIL.equals(vAgruComerGrupoArea.getGrupo1().toString())) {
				if (pedible!=null){
					if (Constantes.REF_TEXTIL_PEDIBLE_SI.equals(pedible)){
						pedibleSelected= messageSource.getMessage("p12_altasCatalogo.si", null,
								LocaleContextHolder.getLocale());
					}else{
						pedibleSelected= messageSource.getMessage("p12_altasCatalogo.no", null,
								LocaleContextHolder.getLocale());
					}
				}else{
					pedibleSelected= messageSource.getMessage("p12_altasCatalogo.all", null,
							LocaleContextHolder.getLocale());
				}
			}
			
			String tipoListadoSelected=null;
			tipoListadoSelected= messageSource.getMessage("p12_altasCatalogo.desgloseTextil", null,
							LocaleContextHolder.getLocale());
			
			map.put("tipoListadoLabel", tipoListadoLabel);
			map.put("centroLabel", centroLabel);
			map.put("areaLabel", areaLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("subcategoryLabel", subcategoryLabel);
			map.put("segmentLabel", segmentLabel);
			map.put("mmcLabel", mmcLabel);
			map.put("applyLabel", applyLabel);
			map.put("pedibleLabel", pedibleLabel);
			map.put("referenceLabel", referenceLabel);
			map.put("centro", vArtCentroAlta.getCentro());
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("vAgruComerGrupoSubCategory", vAgruComerGrupoSubCategory);
			map.put("vAgruComerGrupoSegment", vAgruComerGrupoSegment);
			map.put("mmcSelected", mmcSelected);
			map.put("applySelected", applySelected);
			map.put("pedibleSelected", pedibleSelected);
			map.put("reference", vArtCentroAlta.getCodArticulo());
			map.put("excelTitle", excelName);
			map.put("tipoListadoSelected", tipoListadoSelected);
			
			//map.put("headers", headers);
			
			map.put("referencia", messageSource.getMessage("p12_altasCatalogo.excel.referencia", null,LocaleContextHolder.getLocale()));
			map.put("denominacion", messageSource.getMessage("p12_altasCatalogo.excel.denominacion", null,LocaleContextHolder.getLocale()));
			map.put("activa", messageSource.getMessage("p12_altasCatalogo.excel.activa", null,LocaleContextHolder.getLocale()));
			map.put("mmc", messageSource.getMessage("p12_altasCatalogo.excel.mmc", null,LocaleContextHolder.getLocale()));
			map.put("stock", messageSource.getMessage("p12_altasCatalogo.excel.stock", null,LocaleContextHolder.getLocale()));
			map.put("aprov", messageSource.getMessage("p12_altasCatalogo.excel.aprov", null,LocaleContextHolder.getLocale()));
			map.put("uc", messageSource.getMessage("p12_altasCatalogo.excel.uc", null,LocaleContextHolder.getLocale()));
			map.put("linFacing", messageSource.getMessage("p12_altasCatalogo.excel.linFacing", null,LocaleContextHolder.getLocale()));
			map.put("numOferta", messageSource.getMessage("p12_altasCatalogo.excel.numOferta", null,LocaleContextHolder.getLocale()));
			map.put("pedible", messageSource.getMessage("p12_altasCatalogo.excel.pedible", null,LocaleContextHolder.getLocale()));
			map.put("modeloProveedor", messageSource.getMessage("p12_altasCatalogo.excel.modeloProveedor", null,LocaleContextHolder.getLocale()));
			map.put("talla", messageSource.getMessage("p12_altasCatalogo.excel.talla", null,LocaleContextHolder.getLocale()));
			map.put("color", messageSource.getMessage("p12_altasCatalogo.excel.color", null,LocaleContextHolder.getLocale()));
			map.put("codigoOrdenacionLote", messageSource.getMessage("p12_altasCatalogo.excel.codigoOrdenacionLote", null,LocaleContextHolder.getLocale()));
			map.put("accion", messageSource.getMessage("p12_altasCatalogo.excel.accion", null,LocaleContextHolder.getLocale()));
			
			
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(ALTA_CATALOGO_TEXTIL_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			
			
			//Fijar tamaños de columnas
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);

            sheet.setColumnWidth(0, (int) 3200);
            sheet.setColumnWidth(1, (int) 5500);
            sheet.setColumnWidth(2, (int) 3000);
            sheet.setColumnWidth(3, (int) 3000);
            sheet.setColumnWidth(4, (int) 3000);
            sheet.setColumnWidth(5, (int) 3000);
            sheet.setColumnWidth(6, (int) 3000);
            sheet.setColumnWidth(7, (int) 3000);
            sheet.setColumnWidth(8, (int) 3000);
            sheet.setColumnWidth(9, (int) 3000);
            sheet.setColumnWidth(10, (int) 3000);
            sheet.setColumnWidth(11, (int) 3000);
            sheet.setColumnWidth(12, (int) 3000);
            sheet.setColumnWidth(13, (int) 9000);


			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		   // transformer.transformXLS(templateFileName, map, "d:/AAresultUU.xls");
		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	@Override
	public void exportIntertienda(List<GenericExcelVO> lista,
			String[] headers, String[] model, MessageSource messageSource,
			String centro, String region, String zona, 
			String provincia, String referencia, 
			String descripcion, String tipoB,
			HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p72_intertienda.intertiendaExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p72_intertienda.xls.centro", null,
					LocaleContextHolder.getLocale());
			String regionLabel = messageSource.getMessage("p72_intertienda.xls.region", null,
					LocaleContextHolder.getLocale());
			String zonaLabel = messageSource.getMessage("p72_intertienda.xls.zona", null,
					LocaleContextHolder.getLocale());
			String provinciaLabel = messageSource.getMessage("p72_intertienda.xls.provincia", null,
					LocaleContextHolder.getLocale());
			
			String referenciaLabel = messageSource.getMessage("p72_intertienda.xls.referencia", null,
					LocaleContextHolder.getLocale());
	
			map.put("centroLabel", centroLabel);
			map.put("regionLabel", regionLabel);
			map.put("zonaLabel", zonaLabel);
			map.put("provinciaLabel", provinciaLabel);
			map.put("referenciaLabel", referenciaLabel);
		
			
			//Cabeceras de listado
			String cabCentro = messageSource.getMessage("p72_intertienda.xls.cab.centro", null,
					LocaleContextHolder.getLocale());
			String cabStockActualLabel = messageSource.getMessage("p72_intertienda.xls.cab.stockActual", null,
					LocaleContextHolder.getLocale());
			String cabVentaMediaLabel = messageSource.getMessage("p72_intertienda.xls.cab.ventaMedia", null,
					LocaleContextHolder.getLocale());

			map.put("cabCentro", cabCentro);
			map.put("cabStockActual", cabStockActualLabel);
			map.put("cabVentaMedia", cabVentaMediaLabel);
			
			
			//Obtención del centro/region/zona/provincia/referencia/descripcion/ExcelName
			//map.put("centro", centro);
			map.put("centro", centro);
			map.put("region", region);
			map.put("zona", zona);
			map.put("provincia", provincia);
			map.put("referencia", referencia);
			map.put("descripcion", descripcion);
			map.put("excelTitle", excelName);
			map.put("tipoB", tipoB.toUpperCase());//tipo de busqueda C-Centro, R-Region, P-Provincia
		
			map.put("headers", headers);
			map.put("model", model);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(INTERTIENDA_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);

			//Fijar tamaños de columnas
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
			Integer width = 0;
			if (headers.length <= 3){
				width= 9000;
			} else if (headers.length == 4){
				width = 7000;
			} else {
				width = 5000;
			}

            sheet.setColumnWidth(0, (int) width);
            sheet.setColumnWidth(1, (int) width);
            sheet.setColumnWidth(2, (int) width);
            sheet.setColumnWidth(3, (int) width);
            sheet.setColumnWidth(4, (int) width);

			
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();

		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}
	
	@Override
	public void exportEncargosCliente(List<TEncargosClte> lista
			,Centro centro,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory, Boolean mac, Long referencia,
			HttpServletResponse response
			) throws Exception{
		MessageSource messageSource = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p40_pedidoAdicional.encargoClienteExcelTitle", null,
				LocaleContextHolder.getLocale());
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p40_pedidoAdicional.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p40_pedidoAdicional.category", null,
					LocaleContextHolder.getLocale());
			String macLabel = messageSource.getMessage("p40_pedidoAdicional.mca", null,
					LocaleContextHolder.getLocale());
			String referenceLabel = messageSource.getMessage("p40_pedidoAdicional.reference", null,
					LocaleContextHolder.getLocale());
			String estadoLabel = messageSource.getMessage("p47_pedidoAdicionalEC.estado", null,
					LocaleContextHolder.getLocale());
			String referenciaLabel = messageSource.getMessage("p47_pedidoAdicionalEC.referencia", null,
					LocaleContextHolder.getLocale());
			String denominacionLabel = messageSource.getMessage("p47_pedidoAdicionalEC.denominacion", null,
					LocaleContextHolder.getLocale());
			String entregaLabel = messageSource.getMessage("p47_pedidoAdicionalEC.entrega", null,
					LocaleContextHolder.getLocale());
			String cantidadClienteLabel = messageSource.getMessage("p47_pedidoAdicionalEC.cantidadCliente", null,
					LocaleContextHolder.getLocale());
			String cantidadPedidaLabel = messageSource.getMessage("p47_pedidoAdicionalEC.cantidadPedida", null,
					LocaleContextHolder.getLocale());
			String cantidadServidaLabel = messageSource.getMessage("p47_pedidoAdicionalEC.cantidadServida", null,
					LocaleContextHolder.getLocale());
			String cantidadNoServidaLabel = messageSource.getMessage("p47_pedidoAdicionalEC.cantidadNoServida", null,
					LocaleContextHolder.getLocale());
			String especificacionesLabel = messageSource.getMessage("p47_pedidoAdicionalEC.especificaciones", null,
					LocaleContextHolder.getLocale());
			String contactoClienteLabel = messageSource.getMessage("p47_pedidoAdicionalEC.contactoCliente", null,
					LocaleContextHolder.getLocale());
			String precioLabel = messageSource.getMessage("p47_pedidoAdicionalEC.precio", null,
					LocaleContextHolder.getLocale());
			String localizadorLabel = messageSource.getMessage("p47_pedidoAdicionalEC.localizador", null,
					LocaleContextHolder.getLocale());
			String pendienteEstado = messageSource.getMessage("p47_pedidoAdicionalEC.estado.pendiente", null,
					LocaleContextHolder.getLocale());
// Inicio MISUMI-298
//			String comprometidoEstado = messageSource.getMessage("p47_pedidoAdicionalEC.estado.comprometido", null,
			String enTramiteEstado = messageSource.getMessage("p47_pedidoAdicionalEC.estado.enTramite", null,
// FIN MISUMI-298
					LocaleContextHolder.getLocale());
			String confirmadoEstado = messageSource.getMessage("p47_pedidoAdicionalEC.estado.confirmado", null,
					LocaleContextHolder.getLocale());
			String confNoServEstado = messageSource.getMessage("p47_pedidoAdicionalEC.estado.confir.noserv", null,
					LocaleContextHolder.getLocale());
			String noServidoEstado = messageSource.getMessage("p47_pedidoAdicionalEC.estado.noservido", null,
					LocaleContextHolder.getLocale());
			
			String macSelected=null;
			if (mac!=null){
				if (mac){
					macSelected= messageSource.getMessage("p40_pedidoAdicional.si", null,
							LocaleContextHolder.getLocale());
				}else{
					macSelected= messageSource.getMessage("p40_pedidoAdicional.no", null,
							LocaleContextHolder.getLocale());
}
			}else{
				macSelected= messageSource.getMessage("p40_pedidoAdicional.no", null,
						LocaleContextHolder.getLocale());
			}
			
			
			map.put("centroLabel", centroLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("referenceLabel", referenceLabel);
			map.put("macLabel", macLabel);
			map.put("estadoLabel", estadoLabel);
			map.put("referenciaLabel", referenciaLabel);
			map.put("denominacionLabel", denominacionLabel);
			map.put("entregaLabel", entregaLabel);
			map.put("cantidadClienteLabel", cantidadClienteLabel);
			map.put("cantidadPedidaLabel", cantidadPedidaLabel);
			map.put("cantidadServidaLabel", cantidadServidaLabel);
			map.put("cantidadNoServidaLabel", cantidadNoServidaLabel);
			map.put("especificacionesLabel", especificacionesLabel);
			map.put("contactoClienteLabel", contactoClienteLabel);
			map.put("precioLabel", precioLabel);
			map.put("localizadorLabel", localizadorLabel);
			map.put("pendienteEstado", pendienteEstado);
// Inicio MISUMI-298
//			map.put("comprometidoEstado", comprometidoEstado);
			map.put("comprometidoEstado", enTramiteEstado);
// FIN MISUMI-298
			map.put("confirmadoEstado", confirmadoEstado);
			map.put("noServidoEstado", noServidoEstado);
			map.put("centro", centro);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("macSelected", macSelected);
			map.put("reference", referencia);
			map.put("excelTitle", excelName);
			map.put("records", lista);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(ENCARGOS_CLIENTE_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			
			//Fijar tamaños de columnas
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);

            sheet.setColumnWidth(0, (int) 3200);
            sheet.setColumnWidth(1, (int) 3000);
            sheet.setColumnWidth(2, (int) 9000);
            sheet.setColumnWidth(3, (int) 2400);
            sheet.setColumnWidth(4, (int) 500);
            sheet.setColumnWidth(5, (int) 500);
            sheet.setColumnWidth(6, (int) 500);
            sheet.setColumnWidth(7, (int) 500);
            sheet.setColumnWidth(8, (int) 3000);
            sheet.setColumnWidth(9, (int) 3000);
            sheet.setColumnWidth(10, (int) 2800);
            sheet.setColumnWidth(11, (int) 2800);
 
			
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}

	
	public  void exportSfmCapacidad(List<VArtSfm> lista,String[] model,Integer[] widths,
			MessageSource messageSource,Centro centro,VArtSfm vArtSfm,
			VAgruComerRef vAgruComerGrupoArea,VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory,VAgruComerRef vAgruComerGrupoSubCategory,
			VAgruComerRef vAgruComerGrupoSegment, String marcaMaestroCentro, String pedir, String loteSN, String tipoListado,
			HttpServletResponse response
			) throws Exception{
		
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p26_sfmCapacidad.sfmCapacidadExcelTitle", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());
			String tipoListadoLabel = messageSource.getMessage("p01_header.tipolistado", null,
					LocaleContextHolder.getLocale());
			String areaLabel = messageSource.getMessage("p26_sfmCapacidad.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p26_sfmCapacidad.section", null,
					LocaleContextHolder.getLocale());
			String categoryLabel = messageSource.getMessage("p26_sfmCapacidad.category", null,
					LocaleContextHolder.getLocale());
			String subcategoryLabel = messageSource.getMessage("p26_sfmCapacidad.subcategory", null,
					LocaleContextHolder.getLocale());
			String segmentLabel = messageSource.getMessage("p26_sfmCapacidad.segment", null,
					LocaleContextHolder.getLocale());
			String mmcLabel = messageSource.getMessage("p26_sfmCapacidad.mmc", null,
					LocaleContextHolder.getLocale());
			String applyLabel = messageSource.getMessage("p26_sfmCapacidad.apply", null,
					LocaleContextHolder.getLocale());
			String loteSNLabel = messageSource.getMessage("p26_sfmCapacidad.loteSN", null,
					LocaleContextHolder.getLocale());
			String referenceLabel = messageSource.getMessage("p26_sfmCapacidad.reference", null,
					LocaleContextHolder.getLocale());
			
			
			String mmcSelected=null;
			if (marcaMaestroCentro!=null){
				if ("S".equals(marcaMaestroCentro)){
					mmcSelected= messageSource.getMessage("p26_sfmCapacidad.si", null,
							LocaleContextHolder.getLocale());
				}else if ("N".equals(marcaMaestroCentro)){
					mmcSelected= messageSource.getMessage("p26_sfmCapacidad.no", null,
							LocaleContextHolder.getLocale());	
				}else {
					mmcSelected= messageSource.getMessage("p26_sfmCapacidad.all", null,
						LocaleContextHolder.getLocale());
				}
			}
			
			String applySelected=null;
			if (pedir!=null){
				if ("S".equals(pedir)){
					applySelected= messageSource.getMessage("p26_sfmCapacidad.si", null,
							LocaleContextHolder.getLocale());
				}else if ("N".equals(pedir)){
					applySelected= messageSource.getMessage("p26_sfmCapacidad.no", null,
							LocaleContextHolder.getLocale());
				}else{
					applySelected= messageSource.getMessage("p26_sfmCapacidad.all", null,
						LocaleContextHolder.getLocale());
				}
			}
			String loteSNSelected=null;
			if (loteSN!=null){
				if ("S".equals(loteSN)){
					loteSNSelected= messageSource.getMessage("p26_sfmCapacidad.si", null,
							LocaleContextHolder.getLocale());
				}else if ("N".equals(loteSN)){
					loteSNSelected= messageSource.getMessage("p26_sfmCapacidad.no", null,
							LocaleContextHolder.getLocale());
				}else{
					loteSNSelected= messageSource.getMessage("p26_sfmCapacidad.all", null,
						LocaleContextHolder.getLocale());
				}
			}
			String tipoListadoSelected=null;
			if (Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(tipoListado)){
				tipoListadoSelected= messageSource.getMessage("p26_sfmCapacidad.listadoFAC", null,
						LocaleContextHolder.getLocale());
			}else if (Constantes.SFMCAP_TIPO_LISTADO_CAP.equals(tipoListado)){
				tipoListadoSelected= messageSource.getMessage("p26_sfmCapacidad.listadoCAP", null,
						LocaleContextHolder.getLocale());
			}else if (Constantes.SFMCAP_TIPO_LISTADO_SFM.equals(tipoListado)){
				tipoListadoSelected= messageSource.getMessage("p26_sfmCapacidad.listadoSFM", null,
						LocaleContextHolder.getLocale());
			}
			
			map.put("tipoListadoLabel", tipoListadoLabel);
			map.put("centroLabel", centroLabel);
			map.put("areaLabel", areaLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("categoryLabel", categoryLabel);
			map.put("subcategoryLabel", subcategoryLabel);
			map.put("segmentLabel", segmentLabel);
			map.put("mmcLabel", mmcLabel);
			map.put("applyLabel", applyLabel);
			map.put("loteSNLabel", loteSNLabel);
			map.put("referenceLabel", referenceLabel);
			map.put("centro", centro);
			map.put("vAgruComerGrupoArea", vAgruComerGrupoArea);
			map.put("vAgruComerGrupoSection", vAgruComerGrupoSection);
			map.put("vAgruComerGrupoCategory", vAgruComerGrupoCategory);
			map.put("vAgruComerGrupoSubCategory", vAgruComerGrupoSubCategory);
			map.put("vAgruComerGrupoSegment", vAgruComerGrupoSegment);
			map.put("mmcSelected", mmcSelected);
			map.put("applySelected", applySelected);
			map.put("loteSNSelected", loteSNSelected);
			map.put("reference", vArtSfm.getCodArticulo());
			map.put("excelTitle", excelName);
			map.put("tipoListadoSelected", tipoListadoSelected);
			
			//Obtenemos las cabeceras 
			
		
			//Tratar el Estado, no se muestra en el Excel
			boolean tieneEstado=false;
			int posicionCampoEstado = 0;
			int numField=model.length;
			for (int i=0; i<numField; i++){ 
				if (model[i].equals("mensaje")){
					tieneEstado=true;
					posicionCampoEstado = i;
				}
			}
			
			//Tratar headers, quitandole el Estado		
			if (tieneEstado){
				numField--;
			}
			
			String[] headers=new String[numField];
			int j=0;
			for (int i=0; i<model.length; i++){ 	
				if (!(model[i].equals("mensaje"))){
					headers[j]=messageSource.getMessage("p26_sfmCapacidad.xsl." + model[i], null,
					LocaleContextHolder.getLocale());
					j++;
				} 
			}
			map.put("headers", headers);
			
			//Generamos la lista generica de excel
			List<GenericExcelVO> listaExcel= new ArrayList<GenericExcelVO>();
			if (null!=lista){


				GenericExcelVO fieldExcel = new GenericExcelVO(null,null,null,null,null,null,null,null,null,null,
														   null,null,null,null,null,null,null,null,null,null,
														   null,null,null,null,null,null,null,null,null,null,
											   null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
				
				for (VArtSfm listaVArtSfm : lista) {

					Class<? extends Object> claseVArtSfm = listaVArtSfm.getClass();
					
					fieldExcel = new GenericExcelVO(null,null,null,null,null,null,null,null,null,null,
							   null,null,null,null,null,null,null,null,null,null,
							   null,null,null,null,null,null,null,null,null,null,
							   null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
					
					String getField = "";
					String setField = "";
					int indField=0;	
					for (int i=0; i<model.length; i++){ 
						if (!tieneEstado || i != posicionCampoEstado){
							//Generamos el getter basandonos en el model 
							getField="get" + model[i].substring(0,1).toUpperCase() + model[i].substring(1);
							Method getterField = claseVArtSfm.getMethod(getField);
							
							//Generamos el setter
							indField++; //los getField empiezan por getField1	
							setField="setField" + indField;	
							Method setterField = fieldExcel.getClass().getMethod(setField, Object.class);
							setterField.invoke(fieldExcel, (Object)getterField.invoke(listaVArtSfm, new Object[0]));
						}						
					}
					
					listaExcel.add(fieldExcel);
				}
				
			}
			
			map.put("records", listaExcel);
			
		    XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(SFM_FAC_CAP_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			
			
			//Fijar tamaños de columnas
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
			sheet.getPrintSetup().setLandscape(true);
			//Calcular tamaño del cm
			double tamcm=0;
			for(Integer i = 0; i < widths.length; i++){
				if (!tieneEstado || i != posicionCampoEstado){
					tamcm=tamcm+widths[i];
				}
			}
			
			double tamAreaimpr=Constantes.TAM_AREA_IMPRESION;
			int numCamposCab=Constantes.NUM_CAMPOS_CAB_EXCEL_SFM;
			int anchoPredeterminado=Constantes.ANCHO_PREDETERMINADO_EXCEL_SFM;
			if (numField<numCamposCab) {
				tamAreaimpr=tamAreaimpr-(numCamposCab-numField)*anchoPredeterminado;
			}
			double factor=tamAreaimpr/tamcm;
			int indWidth=0;
			for(Integer i = 0; i < widths.length; i++){
				if (!tieneEstado || i != posicionCampoEstado){
					sheet.setColumnWidth(indWidth, ((widths[i]) * (int)factor));
					indWidth++;
				}
			}
			for(Integer i = indWidth; i <= numCamposCab; i++){
				if (!tieneEstado || i != posicionCampoEstado){
					sheet.setColumnWidth(indWidth, (anchoPredeterminado));
					indWidth++;
				}
			}
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		   
		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}
	

	/**
	 * Devuelve el estilo de celda para las filas del Excel DETALLE_CALENDARIO_VEGALSA.
	 * Asigna el color de texto de la siguente manera: Marcador = "X" verde Marcador = "E" rojo 
	 * @param cell
	 * @param marcador
	 * @return
	 */
	private CellStyle getStyleByMarcador(Cell cell, String marcador) {
		
		final short GREEN_STYLE = IndexedColors.GREEN.getIndex();
		final short RED_STYLE = IndexedColors.RED.getIndex();
		
		// Creacion de estilo
		CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		// Creacion de fuente dinamica
		Font font = cell.getSheet().getWorkbook().createFont();
		
		if ("E".equalsIgnoreCase(marcador)) {
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);		
			font.setColor(RED_STYLE);
		} 
		else if ("X".equalsIgnoreCase(marcador)) {
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);		
			font.setColor(GREEN_STYLE);
		}
		//Asignacion de fuente al estilo
		cellStyle.setFont(font);
		
		return cellStyle;
	}

	public  void exportAlarmasPLU(List<GenericExcelFieldsVO> lista, String[] headers, MessageSource messageSource,
			String centro, String area, String seccion, String agrupacion,
			HttpServletResponse response
			) throws Exception{
		logger.info("ExcelManager - exportAlarmasPLU");
		response.setContentType("text/xls; charset=utf-8");
		String excelName = messageSource.getMessage("p107_alarmas_plu.excel_title", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+excelName+".xls");
		OutputStream out = null;
		
		try {			
			Map<String, Object> map = new HashMap<String, Object>();
			String centroLabel = messageSource.getMessage("p01_header.centro", null,
					LocaleContextHolder.getLocale());

			String areaLabel = messageSource.getMessage("p107_alarmas_plu.area", null,
					LocaleContextHolder.getLocale());
			String sectionLabel = messageSource.getMessage("p107_alarmas_plu.section", null,
					LocaleContextHolder.getLocale());
			String agrupacionLabel = messageSource.getMessage("p107_alarmas_plu.agrupacion", null,
					LocaleContextHolder.getLocale());

			// Campos superiores
			map.put("centroLabel", centroLabel);
			map.put("areaLabel", areaLabel);
			map.put("sectionLabel", sectionLabel);
			map.put("agrupacionLabel", agrupacionLabel);
			
			map.put("centro", centro);
			map.put("area", area);
			map.put("seccion", seccion);
			map.put("agrupacion", agrupacion);
			
			// Cabeceras
			
			map.put("headers", Arrays.copyOf(headers, headers.length-1));
			
			// Registros
			List<GenericExcelFieldsVO> listaToSend = new ArrayList<GenericExcelFieldsVO>();
			for (GenericExcelFieldsVO item : lista){
				final GenericExcelFieldsVO item2 = new GenericExcelFieldsVO();
				item2.fields = Arrays.asList(Arrays.copyOf(item.fields.toArray(),headers.length-1));
				listaToSend.add(item2);
			}
			map.put("records", listaToSend);
			
			// Nombre del fichero
			map.put("excelTitle", excelName);
			
			XLSTransformer transformer = new XLSTransformer();
		    InputStream templateIS = this.getClass().getClassLoader()
					.getResourceAsStream(ALARMAS_PLU_XLS);
		 
			org.apache.poi.ss.usermodel.Workbook resultWorkbook = transformer
					.transformXLS(templateIS, map);
			
			//TAMAÑOS DE LAS COLUMNAS DINAMICOS
			org.apache.poi.ss.usermodel.Sheet sheet = resultWorkbook.getSheetAt(0);
			
			CellStyle cellStyleRed = sheet.getWorkbook().createCellStyle();
			cellStyleRed.setFillForegroundColor(IndexedColors.CORAL.getIndex()); 
			cellStyleRed.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			CellStyle cellStyleYellow = sheet.getWorkbook().createCellStyle();
			cellStyleYellow.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); 
			cellStyleYellow.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			CellStyle cellStyleGreen = sheet.getWorkbook().createCellStyle();
			cellStyleGreen.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); 
			cellStyleGreen.setFillPattern(CellStyle.SOLID_FOREGROUND);

			for (int i=0; i<headers.length; i++){
				sheet.autoSizeColumn(i);

				for (int j=0; j<lista.size(); j++){
					final Object elem = lista.get(j).getField(i);
					if (i==headers.length-1){
						// estado grid
						Long statusGrid = (Long)elem;
						for (int k=0; k<headers.length-1; k++){
							final Cell cell = sheet.getRow(9+j).getCell(k);
							if (statusGrid==1L){
								cell.setCellStyle(cellStyleRed);
							}else if (statusGrid == 2L){
								cell.setCellStyle(cellStyleYellow);
							}else if (statusGrid==3L){
								cell.setCellStyle(cellStyleGreen);
							}
						}
					}
					
					if (elem==null){
						continue;
					}else{
						final Boolean isNumber = NumberUtils.isNumber(elem.toString());
						final Cell cell = sheet.getRow(9+j).getCell(i);

						if (isNumber || elem.toString().length()==1){
							//CellUtil.setAlignment(cell, resultWorkbook, CellStyle.ALIGN_CENTER);
							CellStyle cs = cell.getCellStyle();
							cs.setAlignment(CellStyle.ALIGN_CENTER);
							cell.setCellStyle(cs);
						}else{
							//CellUtil.setAlignment(cell, resultWorkbook, CellStyle.ALIGN_LEFT);	
							CellStyle cs = cell.getCellStyle();
							cs.setAlignment(CellStyle.ALIGN_LEFT);
							cell.setCellStyle(cs);
						}
						
					}
				}
			}
            
			out = response.getOutputStream(); 	
			resultWorkbook.write(out);
			out.close();
		   
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}

	
}
