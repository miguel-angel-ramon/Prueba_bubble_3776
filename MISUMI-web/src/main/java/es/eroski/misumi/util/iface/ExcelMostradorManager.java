package es.eroski.misumi.util.iface;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.ArtGamaRapid;
import es.eroski.misumi.model.CausaInactividad;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelFieldsVO;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.InformeHuecos;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.VArtSfm;

@Service
public interface ExcelMostradorManager {

	public void exportAltaCatalogo(List<GenericExcelVO> lista, String[] headers, String[] model,
			MessageSource messageSource, VArtCentroAlta vArtCentroAlta, VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection, VAgruComerRef vAgruComerGrupoCategory,
			VAgruComerRef vAgruComerGrupoSubCategory, VAgruComerRef vAgruComerGrupoSegment, String marcaMaestroCentro,
			String pedir, String pedible, String tipolistado, HttpServletResponse response) throws Exception;

	public void exportGamaRapid(List<GenericExcelVO> lista, String[] headers, String[] model,
			MessageSource messageSource, ArtGamaRapid artGamaRapid, VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection, VAgruComerRef vAgruComerGrupoCategory,
			VAgruComerRef vAgruComerGrupoSubCategory, VAgruComerRef vAgruComerGrupoSegment,
			HttpServletResponse response) throws Exception;

	public void exportMisPedidosCantPed(List<GenericExcelVO> lista, String[] headers, String[] model,
			MessageSource messageSource, Centro centro, String fechaPedidoDDMMYYYY, VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection, VAgruComerRef vAgruComerGrupoCategory,
			SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle, HttpServletResponse response) throws Exception;

	public void exportMisPedidosCantNoServ(List<GenericExcelVO> lista, String[] headers, String[] model,
			MessageSource messageSource, Centro centro, String fechaPedidoDDMMYYYY, VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection, VAgruComerRef vAgruComerGrupoCategory,
			SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle, HttpServletResponse response) throws Exception;

	public void exportMisPedidosCantConf(List<GenericExcelVO> lista, String[] headers, String[] model,
			MessageSource messageSource, Centro centro, String fechaPedidoDDMMYYYY, VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection, VAgruComerRef vAgruComerGrupoCategory,
			SeguimientoMiPedidoDetalle seguimientoMiPedidoDetalle, HttpServletResponse response) throws Exception;

	public void exportDetalleMostrador(List<GenericExcelVO> lista, String[] headers, String[] columnModel,
			Integer[] widths, MessageSource messageSource, HttpServletResponse response) throws Exception;

	public void exportPedidoAdicional(List<GenericExcelVO> lista, String[] headers, String[] model, Integer[] widths,
			MessageSource messageSource, Centro centro, VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory, Boolean mac, Long referencia, Long clasePedido, String descripcion,
			String oferta, String promocion, HttpServletResponse response) throws Exception;

	public void exportMisCampanaOfer(List<GenericExcelVO> lista, String[] headers, String[] model,
			MessageSource messageSource, Centro centro, String campana, String campanaCompleta, String oferta,
			String ofertaCompleta, String tipoOC, VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection, VAgruComerRef vAgruComerGrupoCategory,
			VAgruComerRef vAgruComerGrupoSubcategory, VAgruComerRef vAgruComerGrupoSegmento, String textil,
			boolean mostrarColumnasSegCamp, HttpServletResponse response) throws Exception;

	public void exportInformeHuecos(List<InformeHuecos> listaHuecos, List<CausaInactividad> listaCausas,
			MessageSource messageSource, Centro centro, HttpServletResponse response) throws Exception;

	public void exportIntertienda(List<GenericExcelVO> lista, String[] headers, String[] model,
			MessageSource messageSource, String centro, String region, String zona, String provincia, String referencia,
			String descripcion, String tipoB, HttpServletResponse response) throws Exception;

	public void exportEncargosCliente(List<TEncargosClte> lista, Centro centro, VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory, Boolean mac, Long referencia, HttpServletResponse response)
					throws Exception;

	public void exportAltaCatalogoTextil(List<VArtCentroAlta> lista, String[] headers, String[] model,
			MessageSource messageSource, VArtCentroAlta vArtCentroAlta, VAgruComerRef vAgruComerGrupoArea,
			VAgruComerRef vAgruComerGrupoSection, VAgruComerRef vAgruComerGrupoCategory,
			VAgruComerRef vAgruComerGrupoSubCategory, VAgruComerRef vAgruComerGrupoSegment, String marcaMaestroCentro,
			String pedir, String pedible, String tipolistado, HttpServletResponse response) throws Exception;

	public void exportSfmCapacidad(List<VArtSfm> lista, String[] model, Integer[] widths, MessageSource messageSource,
			Centro centro, VArtSfm vArtSfm, VAgruComerRef vAgruComerGrupoArea, VAgruComerRef vAgruComerGrupoSection,
			VAgruComerRef vAgruComerGrupoCategory, VAgruComerRef vAgruComerGrupoSubCategory,
			VAgruComerRef vAgruComerGrupoSegment, String marcaMaestroCentro, String pedir, String loteSN,
			String tipolistado, HttpServletResponse response) throws Exception;

	public  void exportAlarmasPLU(List<GenericExcelFieldsVO> list, String[] headers, MessageSource messageSource,
			String centro, String area, String seccion, String agrupacion,
			HttpServletResponse response
			) throws Exception;
}
