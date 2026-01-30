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

public class PdfInformeDevolucionCreadaCentroProveedorView extends AbstractPdfView
{
	private static float PAGE_MARGIN_LEFT = 40;
	private static float PAGE_MARGIN_RIGHT = 20;
	private static float PAGE_MARGIN_TOP = 140;
	private static float PAGE_MARGIN_BOTTOM = 30;

	private static float TABLE_FONT_CABECERA_TITLE_SIZE = 10;
	private static float TABLE_FONT_CABECERA_COLUMN_SIZE = 10;
	private static float TABLE_FONT_CELDAS_SIZE = 8;
	private DevolucionService devolucionService;
	private PdfInformeDevolucionCreadaCentroHeaderFooterPageEvent pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent;
	private MessageSource messageSource;

	@Autowired
	public void setDevolucionService(DevolucionService devolucionService)
	{
		this.devolucionService = devolucionService;
	}

	@Autowired
	public void setPdfInformeDevolucionCreadaCentroHeaderFooterPageEvent(PdfInformeDevolucionCreadaCentroHeaderFooterPageEvent pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent) {
		this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent = pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent;
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
//		String tipoImpresion = (String)model.get("tipoImpresion");

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
				List<TDevolucionLinea> listContadoresProveedor = devolucionService.findContadoresPorProveedorPDF(idSession, devolucion); 


				if(listDevolucionLinea != null && listDevolucionLinea.size() > 0){


					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setImgLogoIzq(imgLogoIzq);
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setImgLogoDer(imgLogoDer);
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setNombreCampana((devolucion.getTitulo1() != null)?devolucion.getTitulo1():"");

					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setFechaLimite(new Date());
											
					
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setEstadoDevolucion(devolucion.getEstadoCab());
					
			
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setObservaciones((devolucion.getDescripcion() != null)?devolucion.getDescripcion():"");
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setCentro(new StringBuilder().append(userSession.getCentro().getCodCentro()).append("-").append(userSession.getCentro().getDescripCentro()).toString());
					//La seccion es comun para toda la devolucion. Aparecera en la cabecera
					String seccion = (listDevolucionLinea.get(0).getSeccion() != null)?listDevolucionLinea.get(0).getSeccion():"";
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setSeccion(seccion); //Cogemos la seccion de la primera linea. Sera Igual para todas  

					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setLocalizador((devolucion.getLocalizador() != null)?devolucion.getLocalizador():"");
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setAbono((devolucion.getAbono() != null)?devolucion.getAbono():"");
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setRecogida((devolucion.getRecogida() != null)?devolucion.getRecogida():"");
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setFechaHora(new Date());

					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.init(writer, PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent.setMessageSource(this.messageSource);
					writer.setPageEvent(this.pdfInformeDevolucionCreadaCentroHeaderFooterPageEvent);

					document.setPageSize(PageSize.A4.rotate());
					document.setMargins(PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					document.open();
					document.resetPageCount();

					// estas variables controlan la visualizacion de ciertas columnas
			        boolean esTextil = seccion.matches("^001[5-8].*"); // es textil si la seccion esta entre 15 y 18
			        
			        // anchos relativos para cada columna
					float codProvColumna = 7F;
					float denominacionProveedorColumna = 11F;
					float marcaColumna = 6F;
					float codigoColumna = 6F;
					float denominacionColumna = 13F;
					float modeloColumna = 6F;
					float descrTallaColumna = 6F;
					float descrColorColumna = 6F;
					float modeloProveedorColumna = 6F;
					float formatoColumna = 8F;
			        // MISUMI-283: NUEVA COLUMNA - CANT. MAX. DEVOLVER
			        float cantMaxDevolverColumna = 9F;

					float stockActualColumna = 7F;
					float cantidadADevolverCentroColumna = 9F;
					float bultoColumna = 5F;
					
					List<Float> columnWithList = new ArrayList<Float>();
					columnWithList.add(codProvColumna);
					columnWithList.add(denominacionProveedorColumna);
					columnWithList.add(marcaColumna);
					columnWithList.add(codigoColumna);
					columnWithList.add(denominacionColumna);
					if(esTextil){
						columnWithList.add(modeloColumna);
						columnWithList.add(descrTallaColumna);
						columnWithList.add(descrColorColumna);
						columnWithList.add(modeloProveedorColumna);
					}
					columnWithList.add(formatoColumna);
					
			        // MISUMI-283: NUEVA COLUMNA - CANT. MAX. DEVOLVER
					columnWithList.add(cantMaxDevolverColumna);
					
					columnWithList.add(stockActualColumna);
					columnWithList.add(cantidadADevolverCentroColumna);
					columnWithList.add(bultoColumna);
					
					int numeroDeColumnas = columnWithList.size();
					float[] tableColumnWidths = new float[numeroDeColumnas];
					for(int it = 0; it<numeroDeColumnas; it++){
						tableColumnWidths[it] = columnWithList.get(it);
					}
	
					// factor de correccion que creo que no es necesario
					// codProvColumna = ((codProvColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal;	

					PdfPTable table = new PdfPTable(numeroDeColumnas);
					table.setComplete(false);
					table.setWidthPercentage(100.0F);
					table.setWidths(tableColumnWidths);
						
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
					String strTotalNumeroRefLabel = messageSource.getMessage("pdf_devoluciones.creadaCentro.total.numero.referencias", null, locale);
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
					float totalPageWidth = document.getPageSize().getWidth() - PAGE_MARGIN_LEFT - PAGE_MARGIN_RIGHT;
					float columnaIzq = (totalPageWidth - totalNumeroRefWidth - 20) / 2;
					float columnaCentro = totalNumeroRefWidth + 20;
					float columnaDer = (totalPageWidth - totalNumeroRefWidth - 20) / 2;
					float[] tableTitleColumnWidths = {columnaIzq, columnaCentro, columnaDer}; // column widths relative
					tableTitle.setWidths(tableTitleColumnWidths);
					PdfPCell cellTitle1 = new PdfPCell();
					cellTitle1.setBorder(PdfPCell.NO_BORDER);
					PdfPCell cellTitle3 = new PdfPCell();
					cellTitle3.setBorder(PdfPCell.NO_BORDER);
					tableTitle.addCell(cellTitle1);
					tableTitle.addCell(cellTitle2);
					tableTitle.addCell(cellTitle3);


					String strNumeroRefLabel = messageSource.getMessage("pdf_devoluciones.creadaCentro.numero.referenciasProveedor", null, locale);
					int j = 0;
					String strNumeroRefText = (listContadoresProveedor != null ? Integer.toString(listContadoresProveedor.get(j).getContador().intValue()) : "0");
					Chunk chunkNumeroRefLabel = new Chunk(strNumeroRefLabel, fuenteCabeceraTitleTableLabel);
					Chunk chunkNumeroRefText = new Chunk(strNumeroRefText, fuenteCabeceraTitleTableText);
					Paragraph pSeccionAndNumeroRef = new Paragraph();	
					pSeccionAndNumeroRef.add(chunkNumeroRefLabel);
					pSeccionAndNumeroRef.add(chunkNumeroRefText);
					pSeccionAndNumeroRef.setAlignment(Element.ALIGN_LEFT);    
					PdfPCell cellSeccion = new PdfPCell(pSeccionAndNumeroRef);
					cellSeccion.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellSeccion.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellSeccion.setBorder(PdfPCell.NO_BORDER);
					cellSeccion.setColspan(3);
					cellSeccion.setPaddingTop(15);
					cellSeccion.setPaddingBottom(15);
					tableTitle.addCell(cellSeccion);

					PdfPCell cellCab = new PdfPCell(tableTitle);
					cellCab.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCab.setBorder(PdfPCell.NO_BORDER);
					cellCab.setColspan(numeroDeColumnas);
					table.addCell(cellCab);


					String strCodigoProveedor = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.codigo.proveedor", null, locale);
					PdfPCell cellCodProv = new PdfPCell(new Phrase(strCodigoProveedor, fuenteCabeceraColumnasTable));
					cellCodProv.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCodProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCodProv);
					String strDenominacionProveedor = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.denominacion.proveedor", null, locale);
					PdfPCell cellDenProv = new PdfPCell(new Phrase(strDenominacionProveedor, fuenteCabeceraColumnasTable));
					cellDenProv.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellDenProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellDenProv);
					String strMarca = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.denominacion.marca", null, locale);
					PdfPCell cellMarca = new PdfPCell(new Phrase(strMarca, fuenteCabeceraColumnasTable));
					cellMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellMarca.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellMarca);
					String strCodigo = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.codigo", null, locale);
					PdfPCell cellCodigo = new PdfPCell(new Phrase(strCodigo, fuenteCabeceraColumnasTable));
					cellCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCodigo.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCodigo);
					String strDenominacion = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.denominacion", null, locale);
					PdfPCell cellDen = new PdfPCell(new Phrase(strDenominacion, fuenteCabeceraColumnasTable));
					cellDen.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellDen.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellDen);
					if(esTextil){
						addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.modelo", table, fuenteCabeceraColumnasTable, locale);
						addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.descrTalla", table, fuenteCabeceraColumnasTable, locale);
						addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.descrColor", table, fuenteCabeceraColumnasTable, locale);
						addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.modeloProveedor", table, fuenteCabeceraColumnasTable, locale);
					}
					String strFormato = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.formato", null, locale);
					PdfPCell cellFormato = new PdfPCell(new Phrase(strFormato, fuenteCabeceraColumnasTable));
					cellFormato.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellFormato.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellFormato);
					
					// MISUMI-283: NUEVA COLUMNA - CANT. MAX. DEVOLVER
					String strCantMaxDevolver = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.cantMaxDevolver", null, locale);
					PdfPCell cellCantMaxDevolver = new PdfPCell(new Phrase(strCantMaxDevolver, fuenteCabeceraColumnasTable));
					cellCantMaxDevolver.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCantMaxDevolver.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCantMaxDevolver);
					
					String strStockActual = this.messageSource.getMessage("pdf_devoluciones.finCampana.tabla.cabecera.stock.actual", null, locale);
					PdfPCell cellStockActual = new PdfPCell(new Phrase(strStockActual, fuenteCabeceraColumnasTable));
					cellStockActual.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellStockActual.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellStockActual);	
					String strCantidadDevolverCentro = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.cantidad.a.devolver.centro", null, locale);
					PdfPCell cellCantidadDevolverCentro = new PdfPCell(new Phrase(strCantidadDevolverCentro, fuenteCabeceraColumnasTable));
					cellCantidadDevolverCentro.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCantidadDevolverCentro.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCantidadDevolverCentro);
					String strBulto = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.bulto", null, locale);
					PdfPCell cellBulto = new PdfPCell(new Phrase(strBulto, fuenteCabeceraColumnasTable));
					cellBulto.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellBulto.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellBulto);
					
					String strContinuaSiguientePagina = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.continuaSiguientePagina", null, locale);
					PdfPCell cellFooter = new PdfPCell(new Phrase(strContinuaSiguientePagina, fuenteCeldasTable));
					cellFooter.setColspan(numeroDeColumnas);
					table.addCell(cellFooter);

					table.setHeaderRows(3);
					table.setFooterRows(1);
					table.setSkipLastFooter(true);


					String strCellCodProvAux = listDevolucionLinea.get(0).getProveedorFormated();

					for (int i = 0; i < listDevolucionLinea.size(); i++) {

						TDevolucionLinea devolucionLinea = listDevolucionLinea.get(i);

						String strCellCodProv = devolucionLinea.getProveedorFormated();
						String strCellFormato = devolucionLinea.getFormatoFormated(); 

						// MISUMI-283: NUEVA COLUMNA - CANT. MAX. DEVOLVER
						String strCellCantMaxDevolver = "";
						Double doubleCellCantMaxDevolver = devolucionLinea.getCantidadMaximaLin(); 

						if (doubleCellCantMaxDevolver!=null && !doubleCellCantMaxDevolver.equals(new Double(0.0))){
							if (doubleCellCantMaxDevolver.doubleValue() % 1.0 == 0){
								strCellCantMaxDevolver = String.valueOf(doubleCellCantMaxDevolver.intValue());
							}else{
								strCellCantMaxDevolver = String.valueOf(doubleCellCantMaxDevolver);
							}
						}
						
						if (!(strCellCodProvAux.equals(strCellCodProv))) { //Cambia de proveedor, hay que hacer salto de pagina

							table.setComplete(true);
							pTable.add(table);
							document.add(pTable);

							pTable.clear();
							if (writer.getVerticalPosition(true) < document.top()) {
								document.newPage();
							}

							strCellCodProvAux = strCellCodProv;

							table = new PdfPTable(numeroDeColumnas);
							table.setComplete(false);
							table.setWidthPercentage(100.0F);
							table.setWidths(tableColumnWidths);

							fuenteCabeceraTitleTableLabel = new Font();
							fuenteCabeceraTitleTableLabel.setSize(TABLE_FONT_CABECERA_TITLE_SIZE);
							fuenteCabeceraTitleTableLabel.setStyle(Font.BOLD);
							fuenteCabeceraTitleTableText = new Font();
							fuenteCabeceraTitleTableText.setSize(TABLE_FONT_CABECERA_TITLE_SIZE);
							fuenteCabeceraTitleTableText.setStyle(Font.BOLD);
							fuenteCabeceraTitleTableText.setColor(Color.RED);
							fuenteCabeceraColumnasTable = new Font();
							fuenteCabeceraColumnasTable.setSize(TABLE_FONT_CABECERA_COLUMN_SIZE);
							fuenteCabeceraColumnasTable.setStyle(1);
							fuenteCeldasTable = new Font();
							fuenteCeldasTable.setSize(TABLE_FONT_CELDAS_SIZE);

							// TOTAL NUMERO REFERENCIAS
							totalRef = listDevolucionLinea.size();
							strTotalNumeroRefLabel = messageSource.getMessage("pdf_devoluciones.creadaCentro.total.numero.referencias", null, locale);
							strTotalNumeroRefText = (new Long (totalRef) != null ? new Long (totalRef).toString() : "0");
							chunkTotalNumeroRefLabel = new Chunk(strTotalNumeroRefLabel, fuenteCabeceraTitleTableLabel);
							chunkTotalNumeroRefText = new Chunk(strTotalNumeroRefText, fuenteCabeceraTitleTableText);
							pTotalNumeroRef = new Paragraph();
							pTotalNumeroRef.add(chunkTotalNumeroRefLabel);
							pTotalNumeroRef.add(chunkTotalNumeroRefText);
							pTotalNumeroRef.setAlignment(Element.ALIGN_LEFT);
							cellTitle2 = new PdfPCell(pTotalNumeroRef);
							cellTitle2.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellTitle2.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cellTitle2.setBorder(PdfPCell.BOX);
							tableTitle = new PdfPTable(3);
							totalNumeroRefWidth = chunkTotalNumeroRefLabel.getWidthPoint() + chunkTotalNumeroRefText.getWidthPoint();
							totalPageWidth = document.getPageSize().getWidth() - PAGE_MARGIN_LEFT - PAGE_MARGIN_RIGHT;
							columnaIzq = (totalPageWidth - totalNumeroRefWidth - 20) / 2;
							columnaCentro = totalNumeroRefWidth + 20;
							columnaDer = (totalPageWidth - totalNumeroRefWidth - 20) / 2;
							//tableTitleColumnWidths = {columnaIzq, columnaCentro, columnaDer}; // column widths relative
							tableTitle.setWidths(tableTitleColumnWidths);
							cellTitle1 = new PdfPCell();
							cellTitle1.setBorder(PdfPCell.NO_BORDER);
							cellTitle3 = new PdfPCell();
							cellTitle3.setBorder(PdfPCell.NO_BORDER);
							tableTitle.addCell(cellTitle1);
							tableTitle.addCell(cellTitle2);
							tableTitle.addCell(cellTitle3);


							strNumeroRefLabel = messageSource.getMessage("pdf_devoluciones.creadaCentro.numero.referenciasProveedor", null, locale);
							j++;
							strNumeroRefText = (listContadoresProveedor != null ? Integer.toString(listContadoresProveedor.get(j).getContador().intValue()) : "0");
							chunkNumeroRefLabel = new Chunk(strNumeroRefLabel, fuenteCabeceraTitleTableLabel);
							chunkNumeroRefText = new Chunk(strNumeroRefText, fuenteCabeceraTitleTableText);
							pSeccionAndNumeroRef = new Paragraph();	
							pSeccionAndNumeroRef.add(chunkNumeroRefLabel);
							pSeccionAndNumeroRef.add(chunkNumeroRefText);
							pSeccionAndNumeroRef.setAlignment(Element.ALIGN_LEFT);    
							cellSeccion = new PdfPCell(pSeccionAndNumeroRef);
							cellSeccion.setHorizontalAlignment(Element.ALIGN_LEFT);
							cellSeccion.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cellSeccion.setBorder(PdfPCell.NO_BORDER);
							cellSeccion.setColspan(3);
							cellSeccion.setPaddingTop(15);
							cellSeccion.setPaddingBottom(15);
							tableTitle.addCell(cellSeccion);

							cellCab = new PdfPCell(tableTitle);
							cellCab.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellCab.setBorder(PdfPCell.NO_BORDER);
							cellCab.setColspan(numeroDeColumnas);
							table.addCell(cellCab);


							strCodigoProveedor = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.codigo.proveedor", null, locale);
							cellCodProv = new PdfPCell(new Phrase(strCodigoProveedor, fuenteCabeceraColumnasTable));
							cellCodProv.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellCodProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellCodProv);
							strDenominacionProveedor = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.denominacion.proveedor", null, locale);
							cellDenProv = new PdfPCell(new Phrase(strDenominacionProveedor, fuenteCabeceraColumnasTable));
							cellDenProv.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellDenProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellDenProv);
							strMarca = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.denominacion.marca", null, locale);
							cellMarca = new PdfPCell(new Phrase(strMarca, fuenteCabeceraColumnasTable));
							cellMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellMarca.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellMarca);
							strCodigo = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.codigo", null, locale);
							cellCodigo = new PdfPCell(new Phrase(strCodigo, fuenteCabeceraColumnasTable));
							cellCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellCodigo.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellCodigo);
							strDenominacion = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.denominacion", null, locale);
							cellDen = new PdfPCell(new Phrase(strDenominacion, fuenteCabeceraColumnasTable));
							cellDen.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellDen.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellDen);
							if(esTextil){
								addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.modelo", table, fuenteCabeceraColumnasTable, locale);
								addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.descrTalla", table, fuenteCabeceraColumnasTable, locale);
								addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.descrColor", table, fuenteCabeceraColumnasTable, locale);
								addCabeceraColumnaSimple("pdf_devoluciones.creadaCentro.tabla.cabecera.modeloProveedor", table, fuenteCabeceraColumnasTable, locale);
							}
							strFormato = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.formato", null, locale);
							cellFormato = new PdfPCell(new Phrase(strFormato, fuenteCabeceraColumnasTable));
							cellFormato.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellFormato.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellFormato);
							
							// MISUMI-283: NUEVA COLUMNA - CANT. MAX. DEVOLVER
							strCantMaxDevolver = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.cantMaxDevolver", null, locale);
							cellCantMaxDevolver = new PdfPCell(new Phrase(strCantMaxDevolver, fuenteCabeceraColumnasTable));
							cellCantMaxDevolver.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellCantMaxDevolver.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellCantMaxDevolver);
							
							strStockActual = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.stock.actual", null, locale);
							cellStockActual = new PdfPCell(new Phrase(strStockActual, fuenteCabeceraColumnasTable));
							cellStockActual.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellStockActual.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellStockActual);
							strCantidadDevolverCentro = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.cantidad.a.devolver.centro", null, locale);
							cellCantidadDevolverCentro = new PdfPCell(new Phrase(strCantidadDevolverCentro, fuenteCabeceraColumnasTable));
							cellCantidadDevolverCentro.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellCantidadDevolverCentro.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellCantidadDevolverCentro);
							strBulto = this.messageSource.getMessage("pdf_devoluciones.creadaCentro.tabla.cabecera.bulto", null, locale);
							cellBulto = new PdfPCell(new Phrase(strBulto, fuenteCabeceraColumnasTable));
							cellBulto.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellBulto.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellBulto);
				

							strContinuaSiguientePagina = this.messageSource.getMessage("pdf_devoluciones.finCampana.continuaSiguientePagina", null, locale);
							cellFooter = new PdfPCell(new Phrase(strContinuaSiguientePagina, fuenteCeldasTable));
							cellFooter.setColspan(numeroDeColumnas);
							table.addCell(cellFooter);

							table.setHeaderRows(3);
							table.setFooterRows(1);
							table.setSkipLastFooter(true);
						}

						PdfPCell cell1 = new PdfPCell(new Phrase(devolucionLinea.getProveedorGenTrabajoFormated(), fuenteCeldasTable));
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell1);

						String strCellDescProv = devolucionLinea.getDenomProveedor() != null ? devolucionLinea.getDenomProveedor().trim() : "";
						PdfPCell cell2 = new PdfPCell(new Phrase(strCellDescProv, fuenteCeldasTable));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell2);

						String strCellMarca = devolucionLinea.getMarca() != null ? devolucionLinea.getMarca().trim() : "";
						PdfPCell cell11 = new PdfPCell(new Phrase(strCellMarca, fuenteCeldasTable));
						cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell11);

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
						
						if(esTextil){
							String strCellModelo = devolucionLinea.getModelo()==null ? "" : devolucionLinea.getModelo().trim();
							addCeldaCentrada(strCellModelo, table, fuenteCeldasTable);
							
							String strCellDescrTalla = devolucionLinea.getDescrTalla()==null ? "" : devolucionLinea.getDescrTalla().trim();
							addCeldaCentrada(strCellDescrTalla, table, fuenteCeldasTable);
							
							String strCellDescrColor = devolucionLinea.getDescrColor()==null ? "" : devolucionLinea.getDescrColor().trim();
							addCeldaCentrada(strCellDescrColor, table, fuenteCeldasTable);
							
							String strCellModeloProveedor = devolucionLinea.getModeloProveedor()==null ? "" : devolucionLinea.getModeloProveedor().trim();
							addCeldaCentrada(strCellModeloProveedor, table, fuenteCeldasTable);
						}

						PdfPCell cell5 = new PdfPCell(new Phrase(strCellFormato, fuenteCeldasTable));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell5);

						// MISUMI-283: NUEVA COLUMNA - CANT. MAX. DEVOLVER
						PdfPCell pdfCellCantMaxDevolver = new PdfPCell(new Phrase(strCellCantMaxDevolver, fuenteCeldasTable));
						pdfCellCantMaxDevolver.setHorizontalAlignment(Element.ALIGN_CENTER);
						pdfCellCantMaxDevolver.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(pdfCellCantMaxDevolver);
						
						String strCellStock = Utilidades.convertirDoubleAString(devolucionLinea.getStockActual() != null ? devolucionLinea.getStockActual().doubleValue() : 0.0D, "###0.###");
						PdfPCell cell6 = new PdfPCell(new Phrase(strCellStock, fuenteCeldasTable));
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell6);
					


						String strCellCantidadDevolverCentro = Utilidades.convertirDoubleAString(devolucionLinea.getStockDevuelto()!= null ? devolucionLinea.getStockDevuelto().doubleValue() : 0.0D, "###0.###");
						if(devolucion.getEstadoCab() == Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
							if (devolucionLinea.getStockDevuelto().doubleValue() == 0.000) {
							//if(("0,000").equals(strCellCantidadDevolverCentro)){
								strCellCantidadDevolverCentro = "";
							}
						}
						PdfPCell cell8 = new PdfPCell(new Phrase(strCellCantidadDevolverCentro, fuenteCeldasTable));
						cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell8);

						String strCellUniCaja = Utilidades.convertirDoubleAString(devolucionLinea.getBulto() != null ? devolucionLinea.getBulto().doubleValue() : 0.0D, "###0");
						if(devolucion.getEstadoCab() == Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA){
							if(("0").equals(strCellUniCaja)){
								strCellUniCaja = "";
							}
						}
						PdfPCell cell9 = new PdfPCell(new Phrase(strCellUniCaja, fuenteCeldasTable));
						cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell9);

					}

					table.setComplete(true);
					pTable.add(table);
					document.add(pTable);

				}

			}


			document.close();

		} catch (Exception e) {
			logger.error("Exporting pdf Informe Devolucion Creada por el centro: PdfInformeDevolucionCreadaCentroView.buildPdfDocument");
			logger.error(StackTraceManager.getStackTrace(e));
			throw e;
		}
	}

	/**
	 * Genera la cabecera de una columna normal
	 * y la anyade a la tabla centrada.
	 * Usa {@link #addCeldaCentrada(String, PdfPTable, Font)}.
	 * @param labelKey Clave en messages para la columna
	 * @param table
	 * @param fuente
	 * @param locale
	 * @return Celda generada
	 */
	private PdfPCell addCabeceraColumnaSimple(String labelKey, PdfPTable table, Font fuente, Locale locale) {
		String strLabel = this.messageSource.getMessage(labelKey, null, locale);
		return addCeldaCentrada(strLabel, table, fuente);
	}

	/**
	 * Genera una celda centrada y la anyade a la tabla.
	 * Al contenido NO le aplica trim, pero conviene para que venga bien centrado.
	 * @param contenido Contenido a incluir.
	 * @param table
	 * @param fuente
	 * @return Celda generada
	 */
	private PdfPCell addCeldaCentrada(String contenido, PdfPTable table, Font fuente) {
		PdfPCell cell = new PdfPCell(new Phrase(contenido, fuente));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		return cell;
	}
}