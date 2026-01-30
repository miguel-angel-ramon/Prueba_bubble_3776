package es.eroski.misumi.pdf.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import es.eroski.misumi.model.PescaPedirHoy;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VPescaMostrador;
import es.eroski.misumi.service.iface.InformeListadoService;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

public class PdfInformePescaView extends AbstractPdfView
{
  private static float PAGE_MARGIN_LEFT = 40;
  private static float PAGE_MARGIN_RIGHT = 20;
  private static float PAGE_MARGIN_TOP = 90;
  private static float PAGE_MARGIN_BOTTOM = 30;

  private static float TABLE_FONT_CABECERA_TITLE_SIZE = 12;
  private static float TABLE_FONT_CABECERA_COLUMN_SIZE = 10;
  private static float TABLE_FONT_CELDAS_SIZE = 10;
  private InformeListadoService informeListadoService;
  private PdfInformePescaHeaderFooterPageEvent pdfInformePescaHeaderFooterPageEvent;
  private MessageSource messageSource;

  @Autowired
  public void setInformeListadoService(InformeListadoService informeListadoService)
  {
    this.informeListadoService = informeListadoService;
  }

  @Autowired
  public void setPdfInformePescaHeaderFooterPageEvent(PdfInformePescaHeaderFooterPageEvent pdfInformePescaHeaderFooterPageEvent) {
    this.pdfInformePescaHeaderFooterPageEvent = pdfInformePescaHeaderFooterPageEvent;
  }

  @Resource
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    User userSession = (User)request.getSession().getAttribute("user");
    Long codCentro = userSession.getCentro().getCodCentro();
    List<String> listaSubcategoriasPesca = (ArrayList<String>) model.get("listaPescaMostrador");
   
    //Obtenemos el flag de los checkbox de habitual y no habitual. Valores posibles. S; N; S,N
    String flgHabitualCheck = (String)model.get("flgHabitual");
    
    String idSubcategoria = "";

	try {
	    Locale locale = LocaleContextHolder.getLocale();

	    ServletContext context = request.getSession().getServletContext();
	    URL urlLogoIzq = context.getResource("/misumi/images/misumi_icono.jpg");
	    Image imgLogoIzq = Image.getInstance(urlLogoIzq);
	    imgLogoIzq.scalePercent(100.0F);
	    URL urlLogoDer = context.getResource("/misumi/images/eroskigrupo.jpg");
	    Image imgLogoDer = Image.getInstance(urlLogoDer);
	    imgLogoDer.scalePercent(80.0F);
	    this.pdfInformePescaHeaderFooterPageEvent.setImgLogoIzq(imgLogoIzq);
	    this.pdfInformePescaHeaderFooterPageEvent.setImgLogoDer(imgLogoDer);
	    this.pdfInformePescaHeaderFooterPageEvent.setFechaHora(new Date());
	    this.pdfInformePescaHeaderFooterPageEvent.setTiendaNombre(userSession.getCentro().getDescripCentro());
	    this.pdfInformePescaHeaderFooterPageEvent.setHabitual("");
	    this.pdfInformePescaHeaderFooterPageEvent.init(writer, PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
	    this.pdfInformePescaHeaderFooterPageEvent.setMessageSource(this.messageSource);
	    
	    //Esto lo hago para que el funcionamiento sea igual que antes del cambio de la petición 59808. 
	    //Busco todas los códigos de la columna V_PESCA_MOSTRADOR.FLG_HABITUAL
	    //para poner en la cabecera el título HABITUAL, NO HABITUAL o sin título.
		List<String> listaCodHab = Arrays.asList(flgHabitualCheck.split(","));//informeListadoService.listaCodigosHabituales(codCentro, listaSubcategoriasPesca);
		if (listaCodHab != null && listaCodHab.size() > 0){
			//Título HABITUAL
			if ("S".equals(listaCodHab.get(0))){
			    this.pdfInformePescaHeaderFooterPageEvent.setHabitual(this.messageSource.getMessage("pdf_pescaMostrador.habitual.si", null, locale));
			    if (listaCodHab.contains("NULL")){
		    		listaCodHab.remove("NULL");
			    	if (!listaCodHab.contains("N")){
			    		listaCodHab.add("N");
			    	}
			    }
			}
			//Título NO HABITUAL
			if ("N".equals(listaCodHab.get(0))){
			    this.pdfInformePescaHeaderFooterPageEvent.setHabitual(this.messageSource.getMessage("pdf_pescaMostrador.habitual.no", null, locale));
			    if (listaCodHab.contains("NULL")){
		    		listaCodHab.remove("NULL");
			    }
			}
			//Título vacío (para que funcione como antes de la petición 59808)
			if ("NULL".equals(listaCodHab.get(0))){
				//Si entramos aquí es porque todos los registros tienen flg_habitual=NULL
				//No se sacará el título de HABITUAL o NO HABITUAL
			    this.pdfInformePescaHeaderFooterPageEvent.setHabitual("");
	    		listaCodHab.remove("NULL");
	    		listaCodHab.add("N");
			}
		}
		
	    //Busco todas las referencias que pertenecen al codCentro y
	    //están dentro de alguna subcategoria de la lista listaSubcategoriasPesca,
		List<VPescaMostrador> listadoAll = informeListadoService.findAllVPescaMostrador(codCentro, listaSubcategoriasPesca);
	    //luego busco las referencias que son para pedir hoy
		PescaPedirHoy pescaPedirHoyAll = informeListadoService.findPescaPedirHoy(codCentro, listadoAll);
		//y al final saco un mensaje en el pie del PDF dependiendo del resultado.
		if (pescaPedirHoyAll != null && pescaPedirHoyAll.getCodError() != null && pescaPedirHoyAll.getCodError().longValue() == 0){
			this.pdfInformePescaHeaderFooterPageEvent.setPiePedirHoyOK(true);
		}
		else{
			this.pdfInformePescaHeaderFooterPageEvent.setPiePedirHoyOK(false);
		}
		
	    this.pdfInformePescaHeaderFooterPageEvent.setMessageSource(this.messageSource);
	    
	    writer.setPageEvent(this.pdfInformePescaHeaderFooterPageEvent);

	    document.setPageSize(PageSize.A4.rotate());
	    document.setMargins(PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
	    document.open();
	    document.resetPageCount();

	    Paragraph pTable = new Paragraph();

		if (listaSubcategoriasPesca != null) {
			// Peticion 59808: Ordenar las referencias por flgHabitual=S (HABITUAL)
			// y luego las que son flgHabitual=N (NO HABITUAL).
			// Las que son flgHabitual=NULL se consideran como N
			for (int j = 0; j < listaCodHab.size(); j++) {

				String flgHabitual = listaCodHab.get(j);
				if (j > 0){
					//Si el elemento en el que estamos en la iteración no es 
					//el primer elemento de la lista de códigos habitual
					//estamos en los NO HABITUALES y hay que inicializar algunos parámetros.
					// Aquí cambiamos la cabecera para que ponga el String de NO HABITUAL
					this.pdfInformePescaHeaderFooterPageEvent.setHabitual(this.messageSource.getMessage("pdf_pescaMostrador.habitual.no", null, locale));
					writer.setPageEvent(this.pdfInformePescaHeaderFooterPageEvent); //Cambiamos la cabecera para ponerle el título de NO HABITUAL
					document.newPage(); //Creamos una nueva página para que coja la nueva cabecera con el título de NO HABITUAL
				    pTable.clear(); //Vaciamos la lista de HABITUALES del paragraph pTable para volverla a llenar con la lista de NO HABITUALES
				}

				for (int i = 0; i < listaSubcategoriasPesca.size(); i++) {
					idSubcategoria = (String) listaSubcategoriasPesca.get(i);

					List<VPescaMostrador> listado = informeListadoService.findAllVPescaMostrador(codCentro, idSubcategoria, flgHabitual);

					listado = informeListadoService.searchPescaPedirHoy(listado, pescaPedirHoyAll);

					PdfPTable table = new PdfPTable(11);
					table.setComplete(false);
					table.setWidthPercentage(100.0F);
					float[] tableColumnWidths = { 7, 27, 7, 10, 7, 7, 7, 7, 7, 7, 7 };
					table.setWidths(tableColumnWidths);
					Font fuenteCabeceraTitleTable = new Font();
					fuenteCabeceraTitleTable.setSize(TABLE_FONT_CABECERA_TITLE_SIZE);
					Font fuenteCabeceraColumnasTable = new Font();
					fuenteCabeceraColumnasTable.setSize(TABLE_FONT_CABECERA_COLUMN_SIZE);
					fuenteCabeceraColumnasTable.setStyle(Font.BOLD);
					Font fuenteCeldasTable = new Font();
					fuenteCeldasTable.setSize(TABLE_FONT_CELDAS_SIZE);

					String descSubcategoria = this.informeListadoService.obtenerDescSubcategoria(codCentro, idSubcategoria);
					Chunk chunkSubcategoria = new Chunk(descSubcategoria, fuenteCabeceraTitleTable);
					chunkSubcategoria.setUnderline(0.5F, -2.0F);
					PdfPCell cellTitle = new PdfPCell(new Phrase(chunkSubcategoria));
					cellTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellTitle.setBorder(0);
					cellTitle.setColspan(11);
					cellTitle.setFixedHeight(25);
					table.addCell(cellTitle);

					String strReferencia = this.messageSource.getMessage("pdf_pescaMostrador.referencia", null, locale);
					PdfPCell cellRef = new PdfPCell(new Phrase(strReferencia, fuenteCabeceraColumnasTable));
					cellRef.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellRef);
					String strDenominacion = this.messageSource.getMessage("pdf_pescaMostrador.denominacion", null, locale);
					PdfPCell cellDen = new PdfPCell(new Phrase(strDenominacion, fuenteCabeceraColumnasTable));
					cellDen.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellDen);
					String strUnidadesCaja = this.messageSource.getMessage("pdf_pescaMostrador.unidadesCaja", null, locale);
					PdfPCell cellUC = new PdfPCell(new Phrase(strUnidadesCaja, fuenteCabeceraColumnasTable));
					cellUC.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellUC);
					String strCodigo = this.messageSource.getMessage("pdf_pescaMostrador.codigo", null, locale);
					PdfPCell cellCodigo = new PdfPCell(new Phrase(strCodigo, fuenteCabeceraColumnasTable));
					cellCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellCodigo);
					String strImporteTirado = this.messageSource.getMessage("pdf_pescaMostrador.importeTirado", null, locale);
					PdfPCell cellImpTirado = new PdfPCell(new Phrase(strImporteTirado, fuenteCabeceraColumnasTable));
					cellImpTirado.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellImpTirado);
					String strPorcentajeTirado = this.messageSource.getMessage("pdf_pescaMostrador.porcentajeTirado", null, locale);
					PdfPCell cellPorTirado = new PdfPCell(new Phrase(strPorcentajeTirado, fuenteCabeceraColumnasTable));
					cellPorTirado.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellPorTirado);
					String strPendienteRecibir = this.messageSource.getMessage("pdf_pescaMostrador.pendienteRecibir", null, locale);
					PdfPCell cellPendRecibir = new PdfPCell(new Phrase(strPendienteRecibir, fuenteCabeceraColumnasTable));
					cellPendRecibir.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellPendRecibir);
					String strPropuestaRecibir = this.messageSource.getMessage("pdf_pescaMostrador.propuestaRecibir", null, locale);
					PdfPCell cellPropPedir = new PdfPCell(new Phrase(strPropuestaRecibir, fuenteCabeceraColumnasTable));
					cellPropPedir.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellPropPedir);
					String strRealPedir = this.messageSource.getMessage("pdf_pescaMostrador.realPedir", null, locale);
					PdfPCell cellRealPedir = new PdfPCell(new Phrase(strRealPedir, fuenteCabeceraColumnasTable));
					cellRealPedir.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellRealPedir);
					String strOfertaVigor = this.messageSource.getMessage("pdf_pescaMostrador.ofertaVigor", null, locale);
					PdfPCell cellOfertaVigor = new PdfPCell(new Phrase(strOfertaVigor, fuenteCabeceraColumnasTable));
					cellOfertaVigor.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellOfertaVigor);
					String strOfertaFutura = this.messageSource.getMessage("pdf_pescaMostrador.ofertaFutura", null, locale);
					PdfPCell cellOfertaFutura = new PdfPCell(new Phrase(strOfertaFutura, fuenteCabeceraColumnasTable));
					cellOfertaFutura.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellOfertaFutura);

					String strContinuaSiguientePagina = this.messageSource.getMessage("pdf_pescaMostrador.continuaSiguientePagina", null, locale);
					PdfPCell cellFooter = new PdfPCell(new Phrase(strContinuaSiguientePagina, fuenteCeldasTable));
					cellFooter.setColspan(11);
					table.addCell(cellFooter);

					table.setHeaderRows(3);
					table.setFooterRows(1);
					table.setSkipLastFooter(true);

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

						String strCellUnidadesCaja = Utilidades.convertirDoubleAString(vPescaMostrador.getUnidadesCaja() != null ? vPescaMostrador.getUnidadesCaja().doubleValue() : 0.0D, "###0.00");
						PdfPCell cell3 = new PdfPCell(new Phrase(strCellUnidadesCaja, fuenteCeldasTable));
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell3);

						PdfContentByte cb = writer.getDirectContent();
						BarcodeEAN codeEAN = new BarcodeEAN();
						String strCellEan = vPescaMostrador.getEan() != null ? vPescaMostrador.getEan().toString() : "";
						codeEAN.setCode(strCellEan);
						codeEAN.setCodeType(1);
						Image codeEANImage = codeEAN.createImageWithBarcode(cb, null, null);
						codeEANImage.scalePercent(80.0F);
						Paragraph pEAN = new Paragraph();
						pEAN.add(new Chunk(codeEANImage, 0.0F, 0.0F, true));
						pEAN.setAlignment(Element.ALIGN_CENTER);
						PdfPCell cell4 = new PdfPCell();
						cell4.addElement(pEAN);
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell4);

						String strCellImporteTirado = Utilidades.convertirDoubleAString(vPescaMostrador.getImporteTirado() != null ? vPescaMostrador.getImporteTirado().doubleValue() : 0.0D, "###0.00");
						PdfPCell cell5 = new PdfPCell(new Phrase(strCellImporteTirado, fuenteCeldasTable));
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell5);

						String strCellPorImpTirado = vPescaMostrador.getPorImpTirado() != null ? vPescaMostrador.getPorImpTirado().toString() : "";
						PdfPCell cell6 = new PdfPCell(new Phrase(strCellPorImpTirado, fuenteCeldasTable));
						cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell6);

						String strCellPedMananaCajas = vPescaMostrador.getPedMananaCajas() != null ? (vPescaMostrador.isPedirHoy() ? vPescaMostrador.getPedMananaCajas().toString() + "*" : vPescaMostrador.getPedMananaCajas().toString()) : "";
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

					table.setComplete(true);
					pTable.add(table);
				}

				document.add(pTable);
			}
		}
	    document.close();
	} catch (Exception e) {
		logger.error("Exporting pdf Informe Pesca Mostrador: PdfInformePescaView.buildPdfDocument");
		logger.error(StackTraceManager.getStackTrace(e));
        throw e;
	}
  }
}