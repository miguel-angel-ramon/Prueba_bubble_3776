package es.eroski.misumi.pdf.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VPescaMostrador;
import es.eroski.misumi.service.iface.InformeListadoService;
import es.eroski.misumi.util.Utilidades;

public class PdfInformeListadoPescaMostradorView extends AbstractPdfView {

	private static float MARGIN_LEFT = 40;
	private static float MARGIN_RIGHT = 20;
	private static float MARGIN_TOP = 120;
	private static float MARGIN_BOTTOM = 40;
	
	private InformeListadoService informeListadoService;
	private HeaderFooterPageEvent headerFooterPageEvent;
	private MessageSource messageSource;

	@Autowired
	public void setInformeListadoService(InformeListadoService informeListadoService) {
		this.informeListadoService = informeListadoService;
	}
	  
	@Autowired
	public void setHeaderFooterPageEvent(HeaderFooterPageEvent headerFooterPageEvent) {
		this.headerFooterPageEvent = headerFooterPageEvent;
	}
	
	@Resource
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User userSession = (User)request.getSession().getAttribute("user");
		Long codCentro = userSession.getCentro().getCodCentro();
		List<String> listaPescaMostrador = (ArrayList<String>) model.get("listaPescaMostrador");
		
		String idSubcategoria = "";
		
		Locale locale = LocaleContextHolder.getLocale();

		List<Object> args = new ArrayList<Object>();
		
		ServletContext context =  request.getSession().getServletContext();
		URL urlLogo = context.getResource("/misumi/images/eroskigrupomisumi.jpg");
		Image imgLogo = Image.getInstance(urlLogo);
		imgLogo.scalePercent(80);
		this.headerFooterPageEvent.setImgLogo(imgLogo);
		this.headerFooterPageEvent.setTiendaNombre(userSession.getCentro().getDescripCentro());
		this.headerFooterPageEvent.init(writer, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);
		this.headerFooterPageEvent.setMessageSource(messageSource);
		if (listaPescaMostrador != null && listaPescaMostrador.size() > 1){
			idSubcategoria = listaPescaMostrador.get(0);
			this.headerFooterPageEvent.setDescSubcategoria(informeListadoService.obtenerDescSubcategoria(codCentro, idSubcategoria));
		}
		writer.setPageEvent(this.headerFooterPageEvent);

		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);
		document.open();
		document.resetPageCount();

		if (listaPescaMostrador != null){
				for (int i = 0; i < listaPescaMostrador.size(); i++){
					
					idSubcategoria = listaPescaMostrador.get(i);
					
				List<VPescaMostrador> listado = informeListadoService.findAllVPescaMostrador(codCentro, idSubcategoria, null);
					
					PdfPTable table = new PdfPTable(11);
					table.setWidthPercentage(100); // table width 100%
					float[] tableColumnWidths = {7, 27, 7, 10, 7, 7, 7, 7, 7, 7, 7}; // column widths relative
					table.setWidths(tableColumnWidths);
					Font fuenteCabeceraTable = new Font();
					fuenteCabeceraTable.setSize(10);
					fuenteCabeceraTable.setStyle(Font.BOLD);
					
					// Pie de la tabla cuando la tabla es muy grande y 
					// continua en la siguiente página
					String strContinuaSiguientePagina = messageSource.getMessage("pdf_pescaMostrador.continuaSiguientePagina", null, locale);
					PdfPCell cellFooter = new PdfPCell(new Phrase(strContinuaSiguientePagina));
					cellFooter.setColspan(11);
					table.addCell(cellFooter);
					
					// Cabecera de la tabla
					String strReferencia = messageSource.getMessage("pdf_pescaMostrador.referencia", null, locale);
					PdfPCell cellRef = new PdfPCell(new Phrase(strReferencia, fuenteCabeceraTable));
					cellRef.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellRef);
					String strDenominacion = messageSource.getMessage("pdf_pescaMostrador.denominacion", null, locale);
					PdfPCell cellDen = new PdfPCell(new Phrase(strDenominacion, fuenteCabeceraTable));
					cellDen.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellDen);
					String strUnidadesCaja = messageSource.getMessage("pdf_pescaMostrador.unidadesCaja", null, locale);
					PdfPCell cellUC = new PdfPCell(new Phrase(strUnidadesCaja, fuenteCabeceraTable));
					cellUC.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellUC);
					String strCodigo = messageSource.getMessage("pdf_pescaMostrador.codigo", null, locale);
					PdfPCell cellCodigo = new PdfPCell(new Phrase(strCodigo, fuenteCabeceraTable));
					cellCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellCodigo);
					String strImporteTirado = messageSource.getMessage("pdf_pescaMostrador.importeTirado", null, locale);
					PdfPCell cellImpTirado = new PdfPCell(new Phrase(strImporteTirado, fuenteCabeceraTable));
					cellImpTirado.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellImpTirado);
					String strPorcentajeTirado = messageSource.getMessage("pdf_pescaMostrador.porcentajeTirado", null, locale);
					PdfPCell cellPorTirado = new PdfPCell(new Phrase(strPorcentajeTirado, fuenteCabeceraTable));
					cellPorTirado.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellPorTirado);
					String strPendienteRecibir = messageSource.getMessage("pdf_pescaMostrador.pendienteRecibir", null, locale);
					PdfPCell cellPendRecibir = new PdfPCell(new Phrase(strPendienteRecibir, fuenteCabeceraTable));
					cellPendRecibir.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellPendRecibir);
					String strPropuestaRecibir = messageSource.getMessage("pdf_pescaMostrador.propuestaRecibir", null, locale);
					PdfPCell cellPropPedir = new PdfPCell(new Phrase(strPropuestaRecibir, fuenteCabeceraTable));
					cellPropPedir.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellPropPedir);
					String strRealPedir = messageSource.getMessage("pdf_pescaMostrador.realPedir", null, locale);
					PdfPCell cellRealPedir = new PdfPCell(new Phrase(strRealPedir, fuenteCabeceraTable));
					cellRealPedir.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellRealPedir);
					String strOfertaVigor = messageSource.getMessage("pdf_pescaMostrador.ofertaVigor", null, locale);
					PdfPCell cellOfertaVigor = new PdfPCell(new Phrase(strOfertaVigor, fuenteCabeceraTable));
					cellOfertaVigor.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellOfertaVigor);
					String strOfertaFutura = messageSource.getMessage("pdf_pescaMostrador.ofertaFutura", null, locale);
					PdfPCell cellOfertaFutura = new PdfPCell(new Phrase(strOfertaFutura, fuenteCabeceraTable));
					cellOfertaFutura.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellOfertaFutura);
					
					table.setHeaderRows(1);
					table.setFooterRows(1);
					table.setSkipLastFooter(true);
					
					Font fuenteCeldasTable = new Font();
					fuenteCeldasTable.setSize(10);
					for (VPescaMostrador vPescaMostrador : listado) {
						
						String strCellCodArt = vPescaMostrador.getCodArt() != null ? vPescaMostrador.getCodArt().toString() : "";
						PdfPCell cell1 = new PdfPCell(new Phrase(strCellCodArt, fuenteCeldasTable));
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell1);
						
						String strCellDenominacion = vPescaMostrador.getDenominacion() != null ? vPescaMostrador.getDenominacion().trim() : "";
						PdfPCell cell2 = new PdfPCell(new Phrase(strCellDenominacion, fuenteCeldasTable));
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell2);
						
						String strCellUnidadesCaja = Utilidades.convertirDoubleAString(vPescaMostrador.getUnidadesCaja()!=null?vPescaMostrador.getUnidadesCaja().doubleValue():0,"###0.00");
						PdfPCell cell3 = new PdfPCell(new Phrase(strCellUnidadesCaja, fuenteCeldasTable));
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell3);
						
						// añado el código de barras
						PdfContentByte cb = writer.getDirectContent();
						BarcodeEAN codeEAN = new BarcodeEAN();
						String strCellEan = vPescaMostrador.getEan() != null ? vPescaMostrador.getEan().toString() : "";
						codeEAN.setCode(strCellEan);
						codeEAN.setCodeType(BarcodeEAN.EAN13);
						Image codeEANImage = codeEAN.createImageWithBarcode(cb, null, null);
						codeEANImage.scalePercent(80);
						Paragraph pEAN = new Paragraph();
						pEAN.add(new Chunk(codeEANImage, 0, 0, true));
						pEAN.setAlignment(Element.ALIGN_CENTER);
						PdfPCell cell4 = new PdfPCell();
						cell4.addElement(pEAN);
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell4);
						
						String strCellImporteTirado = Utilidades.convertirDoubleAString((vPescaMostrador.getImporteTirado()!=null?vPescaMostrador.getImporteTirado().doubleValue():0),"###0.00");
						PdfPCell cell5 = new PdfPCell(new Phrase(strCellImporteTirado, fuenteCeldasTable));
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell5);
						
						String strCellPorImpTirado = vPescaMostrador.getPorImpTirado() != null ? vPescaMostrador.getPorImpTirado().toString() : "";
						PdfPCell cell6 = new PdfPCell(new Phrase(strCellPorImpTirado, fuenteCeldasTable));
						cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell6);
						
						String strCellPedMananaCajas = vPescaMostrador.getPedMananaCajas() != null ? vPescaMostrador.getPedMananaCajas().toString() : "";
						PdfPCell cell7 = new PdfPCell(new Phrase(strCellPedMananaCajas, fuenteCeldasTable));
						cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell7);
						
						String strCellPropPedir = vPescaMostrador.getPropPedir() != null ? vPescaMostrador.getPropPedir().toString() : "";
						PdfPCell cell8 = new PdfPCell(new Phrase(strCellPropPedir, fuenteCeldasTable));
						cell8.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell8);
						
						String strCellRealPedir = "";
						PdfPCell cell9 = new PdfPCell(new Phrase(strCellRealPedir, fuenteCeldasTable));
						cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell9);
						
						String strCellOfertaVigorIni = vPescaMostrador.getOfertaVigorIni() != null ? vPescaMostrador.getOfertaVigorIni().trim() : "";
						String strCellOfertaVigorFin = vPescaMostrador.getOfertaVigorFin() != null ? vPescaMostrador.getOfertaVigorFin().trim() : "";
						String strCellOfertaVigor = strCellOfertaVigorIni + "\n" + strCellOfertaVigorFin;
						PdfPCell cell10 = new PdfPCell(new Phrase(strCellOfertaVigor, fuenteCeldasTable));
						cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell10);
						
						String strCellOfertaFuturaIni = vPescaMostrador.getOfertaFuturaIni() != null ? vPescaMostrador.getOfertaFuturaIni().trim() : "";
						String strCellOfertaFuturaFin = vPescaMostrador.getOfertaFuturaFin() != null ? vPescaMostrador.getOfertaFuturaFin().trim() : "";
						String strCellOfertaFutura = strCellOfertaFuturaIni + "\n" + strCellOfertaFuturaFin;
						PdfPCell cell11 = new PdfPCell(new Phrase(strCellOfertaFutura, fuenteCeldasTable));
						cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell11);
						
	                }
					
					document.add(table);
					
					if (i < listaPescaMostrador.size() - 1){
						if (listaPescaMostrador != null && (i+1) < listaPescaMostrador.size()){
							idSubcategoria = listaPescaMostrador.get(i+1);
							this.headerFooterPageEvent.setDescSubcategoria(informeListadoService.obtenerDescSubcategoria(codCentro, idSubcategoria));
						}
						document.newPage();
					}
				}
	    	}
		
	}

}
