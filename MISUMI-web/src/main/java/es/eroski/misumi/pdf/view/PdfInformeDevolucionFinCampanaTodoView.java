package es.eroski.misumi.pdf.view;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

public class PdfInformeDevolucionFinCampanaTodoView extends AbstractPdfView
{
	private static float PAGE_MARGIN_LEFT = 40;
	private static float PAGE_MARGIN_RIGHT = 20;
	private static float PAGE_MARGIN_TOP = 140;
	private static float PAGE_MARGIN_BOTTOM = 30;

	private static float TABLE_FONT_CABECERA_TITLE_SIZE = 10;
	private static float TABLE_FONT_CABECERA_COLUMN_SIZE = 10;
	private static float TABLE_FONT_CELDAS_SIZE = 8;
	private DevolucionService devolucionService;
	private PdfInformeDevolucionFinCampanaHeaderFooterPageEvent pdfInformeDevolucionFinCampanaHeaderFooterPageEvent;
	private MessageSource messageSource;

	@Autowired
	public void setDevolucionService(DevolucionService devolucionService)
	{
		this.devolucionService = devolucionService;
	}

	@Autowired
	public void setPdfInformeDevolucionFinCampanaHeaderFooterPageEvent(PdfInformeDevolucionFinCampanaHeaderFooterPageEvent pdfInformeDevolucionFinCampanaHeaderFooterPageEvent) {
		this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent = pdfInformeDevolucionFinCampanaHeaderFooterPageEvent;
	}

	@Resource
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		User userSession = (User)request.getSession().getAttribute("user");
		Devolucion devolucion = (Devolucion)model.get("devolucion");
		String idSession = (String)model.get("idSession");
		String tipoImpresion = (String)model.get("tipoImpresion");

		String codArticulo = "";
		List<Object> args = new ArrayList<Object>();

		try {
			Locale locale = LocaleContextHolder.getLocale();

			ServletContext context = request.getSession().getServletContext();
			URL urlLogoIzq = context.getResource("/misumi/images/misumi_icono.jpg");
			Image imgLogoIzq = Image.getInstance(urlLogoIzq);
			imgLogoIzq.scalePercent(100.0F);
			URL urlLogoDer = context.getResource("/misumi/images/eroskigrupo.jpg");
			Image imgLogoDer = Image.getInstance(urlLogoDer);
			imgLogoDer.scalePercent(80.0F);


			Paragraph pTable = new Paragraph();

			if (devolucion != null) {

				//Obtiene una devolución con sus líneas de devolución
				List<TDevolucionLinea> listDevolucionLinea = devolucionService.findLineasDevolucionPDF(idSession, devolucion); 


				if(listDevolucionLinea != null && listDevolucionLinea.size() > 0){


					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setImgLogoIzq(imgLogoIzq);
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setImgLogoDer(imgLogoDer);
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setNombreCampana((devolucion.getTitulo1() != null)?devolucion.getTitulo1():"");

					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setFechaLimite(new Date());
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setFechaDesde(devolucion.getFechaDesde());
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setFechaHasta(devolucion.getFechaHasta());
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setObservaciones((devolucion.getDescripcion() != null)?devolucion.getDescripcion():"");
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setCentro(new StringBuilder().append(userSession.getCentro().getCodCentro()).append("-").append(userSession.getCentro().getDescripCentro()).toString());
					//La seccion es comun para toda la devolucion. Aparecera en la cabecera
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setSeccion((listDevolucionLinea.get(0).getSeccion() != null)?listDevolucionLinea.get(0).getSeccion():""); //Cogemos la seccion de la primera linea. Sera Igual para todas  

					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setLocalizador((devolucion.getLocalizador() != null)?devolucion.getLocalizador():"");
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setAbono((devolucion.getAbono() != null)?devolucion.getAbono():"");
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setRecogida((devolucion.getRecogida() != null)?devolucion.getRecogida():"");
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setFechaHora(new Date());

					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.init(writer, PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent.setMessageSource(this.messageSource);
					writer.setPageEvent(this.pdfInformeDevolucionFinCampanaHeaderFooterPageEvent);

					document.setPageSize(PageSize.A4.rotate());
					document.setMargins(PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					document.open();
					document.resetPageCount();


					int codProvColumna = 8;
					int denominacionProveedorColumna = 15;
					int marcaColumna = 6;
					int codigoColumna = 6;
					int denominacionColumna = 16;
					int stockActualColumna = 11;
					int cantidadPropuestaADejarColumna = 11;
					int cantidadADevolverCentroColumna = 10;
					int bultoColumna = 6;
					int formatoDevolucionColumna = 10;

					int anchoTotal = codProvColumna+denominacionProveedorColumna+marcaColumna+codigoColumna+denominacionColumna+stockActualColumna+cantidadPropuestaADejarColumna+cantidadADevolverCentroColumna+bultoColumna+formatoDevolucionColumna;
					int numeroDeColumnas = 10;			

					//Si el motivo es fin de campaña, se calcula la tabla con 7 columnas, por lo que es necesario recalcular los tamaños de cada columna con el ancho original
					if(devolucion.getEstadoCab() > Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
						//Indicamos que van a haber 7 columnas y no 9
						numeroDeColumnas = 8;

						//Indicamos que las dos últimas columnas son vacías
						stockActualColumna = 0;
						cantidadPropuestaADejarColumna = 0;

						int sumaTotalColumnasFinDeCampana = codProvColumna+denominacionProveedorColumna+marcaColumna+codigoColumna+denominacionColumna+stockActualColumna+cantidadPropuestaADejarColumna+cantidadADevolverCentroColumna+bultoColumna+formatoDevolucionColumna;

						codProvColumna = ((codProvColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;
						denominacionProveedorColumna = ((denominacionProveedorColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;
						marcaColumna = ((marcaColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;					
						codigoColumna = ((codigoColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;
						denominacionColumna = ((denominacionColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;
						stockActualColumna = ((stockActualColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;	
						cantidadPropuestaADejarColumna = ((cantidadPropuestaADejarColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;	
						cantidadADevolverCentroColumna = ((cantidadADevolverCentroColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;	
						bultoColumna = ((bultoColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;	
						formatoDevolucionColumna = ((formatoDevolucionColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;	
					}

					PdfPTable table = new PdfPTable(numeroDeColumnas);
					table.setComplete(false);
					table.setWidthPercentage(100.0F);
					//Si es distinto a preparar mercancia
					if(devolucion.getEstadoCab() > Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
						float[] tableColumnWidths = { codProvColumna, denominacionProveedorColumna, marcaColumna, codigoColumna, denominacionColumna, cantidadADevolverCentroColumna,bultoColumna,formatoDevolucionColumna};
						table.setWidths(tableColumnWidths);
					}else{
						float[] tableColumnWidths = { codProvColumna, denominacionProveedorColumna, marcaColumna, codigoColumna, denominacionColumna, stockActualColumna,cantidadPropuestaADejarColumna,cantidadADevolverCentroColumna,bultoColumna,formatoDevolucionColumna};
						table.setWidths(tableColumnWidths);
					}
					Font fuenteCabeceraTitleTableLabel = new Font();
					fuenteCabeceraTitleTableLabel.setSize(TABLE_FONT_CABECERA_TITLE_SIZE);
					fuenteCabeceraTitleTableLabel.setStyle(Font.BOLD);
					Font fuenteCabeceraTitleTableText = new Font();
					fuenteCabeceraTitleTableText.setSize(TABLE_FONT_CABECERA_TITLE_SIZE);
					fuenteCabeceraTitleTableText.setStyle(Font.BOLD);
					fuenteCabeceraTitleTableText.setColor(Color.RED);
					Font fuenteCabeceraColumnasTable = new Font();
					fuenteCabeceraColumnasTable.setSize(TABLE_FONT_CABECERA_COLUMN_SIZE);
					fuenteCabeceraColumnasTable.setStyle(1);
					Font fuenteCeldasTable = new Font();
					fuenteCeldasTable.setSize(TABLE_FONT_CELDAS_SIZE);

					// TOTAL NUMERO REFERENCIAS
					int totalRef = listDevolucionLinea.size();
					String strTotalNumeroRefLabel = messageSource.getMessage("pdf_devoluciones.finCampana.total.numero.referencias", null, locale);
					String strTotalNumeroRefText = (new Long (totalRef) != null ? new Long (totalRef).toString() : "0");
					Chunk chunkTotalNumeroRefLabel = new Chunk(strTotalNumeroRefLabel, fuenteCabeceraTitleTableLabel);
					Chunk chunkTotalNumeroRefText = new Chunk(strTotalNumeroRefText, fuenteCabeceraTitleTableText);
					Paragraph pTotalNumeroRef = new Paragraph();
					pTotalNumeroRef.add(chunkTotalNumeroRefLabel);
					pTotalNumeroRef.add(chunkTotalNumeroRefText);
					pTotalNumeroRef.setAlignment(Element.ALIGN_LEFT);
					PdfPCell cellTitle2 = new PdfPCell(pTotalNumeroRef);
					cellTitle2.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellTitle2.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellTitle2.setBorder(PdfPCell.BOX);
					PdfPTable tableTitle = new PdfPTable(3);
					float totalNumeroRefWidth = chunkTotalNumeroRefLabel.getWidthPoint() + chunkTotalNumeroRefText.getWidthPoint();
					float totalPageWidth = document.getPageSize().getWidth() - this.PAGE_MARGIN_LEFT - this.PAGE_MARGIN_RIGHT;
					float columnaIzq = (totalPageWidth - totalNumeroRefWidth - 20) / 2;
					float columnaCentro = totalNumeroRefWidth + 20;
					float columnaDer = (totalPageWidth - totalNumeroRefWidth - 20) / 2;
					float[] tableTitleColumnWidths = {columnaIzq, columnaCentro, columnaDer}; // column widths relative
					tableTitle.setWidths(tableTitleColumnWidths);
					PdfPCell cellTitle1 = new PdfPCell();
					cellTitle1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cellTitle3 = new PdfPCell();
					cellTitle3.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cellTitleVacio = new PdfPCell();
					cellTitleVacio.setBorder(PdfPCell.NO_BORDER);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);

					tableTitle.addCell(cellTitle1);
					tableTitle.addCell(cellTitle2);
					tableTitle.addCell(cellTitle3);		        

					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);
					tableTitle.addCell(cellTitleVacio);     


					PdfPCell cellCab = new PdfPCell(tableTitle);
					cellCab.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCab.setBorder(PdfPCell.NO_BORDER);
					cellCab.setColspan(numeroDeColumnas);
					table.addCell(cellCab);


					String strCodigoProveedor = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.codigo.proveedor", null, locale);
					PdfPCell cellCodProv = new PdfPCell(new Phrase(strCodigoProveedor, fuenteCabeceraColumnasTable));
					cellCodProv.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCodProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCodProv);
					String strDenominacionProveedor = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.denominacion.proveedor", null, locale);
					PdfPCell cellDenProv = new PdfPCell(new Phrase(strDenominacionProveedor, fuenteCabeceraColumnasTable));
					cellDenProv.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellDenProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellDenProv);
					String strMarca = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.denominacion.marca", null, locale);
					PdfPCell cellMarca = new PdfPCell(new Phrase(strMarca, fuenteCabeceraColumnasTable));
					cellMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellMarca.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellMarca);	
					String strCodigo = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.codigo", null, locale);
					PdfPCell cellCodigo = new PdfPCell(new Phrase(strCodigo, fuenteCabeceraColumnasTable));
					cellCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCodigo.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCodigo);
					String strDenominacion = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.denominacion", null, locale);
					PdfPCell cellDen = new PdfPCell(new Phrase(strDenominacion, fuenteCabeceraColumnasTable));
					cellDen.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellDen.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellDen);
					if(devolucion.getEstadoCab() == Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
						String strStockActual = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.stock.actual", null, locale);
						PdfPCell cellStockActual = new PdfPCell(new Phrase(strStockActual, fuenteCabeceraColumnasTable));
						cellStockActual.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellStockActual.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellStockActual);
						String strCantidadPropuestaDejar = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.cantidad.propuesta.a.dejar", null, locale);
						PdfPCell cellCantidadPropuestaDejar = new PdfPCell(new Phrase(strCantidadPropuestaDejar, fuenteCabeceraColumnasTable));
						cellCantidadPropuestaDejar.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellCantidadPropuestaDejar.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellCantidadPropuestaDejar);
					}
					String strCantidadDevolverCentro = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.cantidad.a.devolver.centro", null, locale);
					PdfPCell cellCantidadDevolverCentro = new PdfPCell(new Phrase(strCantidadDevolverCentro, fuenteCabeceraColumnasTable));
					cellCantidadDevolverCentro.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCantidadDevolverCentro.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCantidadDevolverCentro);
					String strBulto = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.bulto", null, locale);
					PdfPCell cellBulto = new PdfPCell(new Phrase(strBulto, fuenteCabeceraColumnasTable));
					cellBulto.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellBulto.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellBulto);
					String strFormatoDevolucion = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.formato.devolucion", null, locale);
					PdfPCell cellFormatoDevolucion = new PdfPCell(new Phrase(strFormatoDevolucion, fuenteCabeceraColumnasTable));
					cellFormatoDevolucion.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellFormatoDevolucion.setVerticalAlignment(Element.ALIGN_MIDDLE);	      
					table.addCell(cellFormatoDevolucion);

					String strContinuaSiguientePagina = this.messageSource.getMessage("pdf_devoluciones.finCampana.continuaSiguientePagina", null, locale);
					PdfPCell cellFooter = new PdfPCell(new Phrase(strContinuaSiguientePagina, fuenteCeldasTable));
					cellFooter.setColspan(11);
					table.addCell(cellFooter);

					table.setHeaderRows(3);
					table.setFooterRows(1);
					table.setSkipLastFooter(true);



					for (int i = 0; i < listDevolucionLinea.size(); i++) {

						TDevolucionLinea devolucionLinea = listDevolucionLinea.get(i);

						String strCellCodProv = devolucionLinea.getProveedorFormated(); 
						PdfPCell cell1 = new PdfPCell(new Phrase(strCellCodProv, fuenteCeldasTable));
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell1);

						String strCellDescProv = devolucionLinea.getDenomProveedor() != null ? devolucionLinea.getDenomProveedor().trim() : "";
						PdfPCell cell2 = new PdfPCell(new Phrase(strCellDescProv, fuenteCeldasTable));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell2);

						String strCellMarca = devolucionLinea.getMarca() != null ? devolucionLinea.getMarca().trim() : "";
						PdfPCell cell10 = new PdfPCell(new Phrase(strCellMarca, fuenteCeldasTable));
						cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell10);

						String strCellCodArt = devolucionLinea.getCodArticulo() != null ? devolucionLinea.getCodArticulo().toString() : "";
						PdfPCell cell3 = new PdfPCell(new Phrase(strCellCodArt, fuenteCeldasTable));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell3);

						String strCellDescArt = devolucionLinea.getDenominacion() != null ? devolucionLinea.getDenominacion().trim() : "";
						PdfPCell cell4 = new PdfPCell(new Phrase(strCellDescArt, fuenteCeldasTable));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell4);

						if(devolucion.getEstadoCab() == Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
							String strCellStock = Utilidades.convertirDoubleAString(devolucionLinea.getStockActual() != null ? devolucionLinea.getStockActual().doubleValue() : 0.0D, "###0.###");
							PdfPCell cell5 = new PdfPCell(new Phrase(strCellStock, fuenteCeldasTable));
							cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell5);

							String strCellCantidadPropuestaDejar = Utilidades.convertirDoubleAString(devolucionLinea.getStockTienda() != null ? devolucionLinea.getStockTienda().doubleValue() : 0.0D, "###0.###");
							PdfPCell cell6 = new PdfPCell(new Phrase(strCellCantidadPropuestaDejar, fuenteCeldasTable));
							cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell6);
						}

						String strCellCantidadDevolverCentro = Utilidades.convertirDoubleAString(devolucionLinea.getStockDevuelto()!= null ? devolucionLinea.getStockDevuelto().doubleValue() : 0.0D, "###0.###");
						if(devolucion.getEstadoCab() == Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
							if (devolucionLinea.getStockDevuelto().doubleValue() == 0.000) {
							//if(("0").equals(strCellCantidadDevolverCentro)){
								strCellCantidadDevolverCentro = "";
							}
						}
						PdfPCell cell7 = new PdfPCell(new Phrase(strCellCantidadDevolverCentro, fuenteCeldasTable));
						cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell7);

						String strCellUniCaja = Utilidades.convertirDoubleAString(devolucionLinea.getBulto() != null ? devolucionLinea.getBulto().doubleValue() : 0.0D, "###0");
						if(devolucion.getEstadoCab() == Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
							if(("0").equals(strCellUniCaja)){
								strCellUniCaja = "";
							}
						}
						PdfPCell cell8 = new PdfPCell(new Phrase(strCellUniCaja, fuenteCeldasTable));
						cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell8);

						String strCellFormatoDev = devolucionLinea.getFormatoDevuelto()!= null ? devolucionLinea.getFormatoDevuelto().trim() : "";
						PdfPCell cell9 = new PdfPCell(new Phrase(strCellFormatoDev, fuenteCeldasTable));
						cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell9);


					}


					table.setComplete(true);
					pTable.add(table);

				}

			}

			document.add(pTable);
			document.close();

		} catch (Exception e) {
			logger.error("Exporting pdf Informe Devolucion Fin Campaña: PdfInformeDevolucionFinCampanaView.buildPdfDocument");
			logger.error(StackTraceManager.getStackTrace(e));
			throw e;
		}
	}
}