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
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.PdfContentByte;
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

public class PdfInformeDevOrdenRecogidaProveedorView extends AbstractPdfView{
	private static float PAGE_MARGIN_LEFT = 40;
	private static float PAGE_MARGIN_RIGHT = 20;
	private static float PAGE_MARGIN_TOP = 140;
	private static float PAGE_MARGIN_BOTTOM = 30;

	private static float TABLE_FONT_CABECERA_TITLE_SIZE = 10;
	private static float TABLE_FONT_CABECERA_COLUMN_SIZE = 10;
	private static float TABLE_FONT_CELDAS_SIZE = 8;
	private DevolucionService devolucionService;
	private PdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent;
	private MessageSource messageSource;

	@Autowired
	public void setDevolucionService(DevolucionService devolucionService){
		this.devolucionService = devolucionService;
	}

	@Autowired
	public void setPdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent(PdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent) {
		this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent = pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent;
	}

	@Resource
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		User userSession = (User)request.getSession().getAttribute("user");
		Devolucion devolucion = (Devolucion)model.get("devolucion");
		String idSession = (String)model.get("idSession");

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
				devolucion.setIsOrdenRecogidaPDF(true);
				List<TDevolucionLinea> listDevolucionLinea = devolucionService.findLineasDevolucionPDF(idSession, devolucion); 
				List<TDevolucionLinea> listContadoresProveedor = devolucionService.findContadoresPorProveedorPDF(idSession, devolucion, false);
				List<TDevolucionLinea> listContadoresBultoPorProveedor = devolucionService.findContadoresBultoPorProveedorPDF(idSession, devolucion, false);
				List<TDevolucionLinea> listContadoresProveedorBulto = devolucionService.findContadoresPorProveedorBultoPDF(idSession, devolucion); 

				Long contadorReferenciasSinRetirada = devolucionService.findContadoresReferenciasSinRetiradaPDF(idSession, devolucion);

				if (listDevolucionLinea != null && listDevolucionLinea.size() > 0){

					String seccion = (listDevolucionLinea.get(0).getSeccion() != null)?listDevolucionLinea.get(0).getSeccion():"";
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setImgLogoIzq(imgLogoIzq);
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setImgLogoDer(imgLogoDer);
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setNombreOrdenRecogida((devolucion.getTitulo1() != null)?devolucion.getTitulo1():"");

					//this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setFechaLimite((fechaDesde).concat("-").concat(fechaHasta));
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setFechaDesde(devolucion.getFechaDesde());
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setFechaHasta(devolucion.getFechaHasta());
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setCentro(new StringBuilder().append(userSession.getCentro().getCodCentro()).append("-").append(userSession.getCentro().getDescripCentro()).toString());

					if (devolucion.getTipoRMA() != null && !"".equals(devolucion.getTipoRMA())){
						this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setRma(devolucion.getCodRMA());
					}else{
						this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setRma(null);
					}

					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setLocalizador((devolucion.getLocalizador() != null)?devolucion.getLocalizador():"");
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setRecogida((devolucion.getRecogida() != null)?devolucion.getRecogida().toString():"");
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setFechaHora(new Date());

					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setMotivoDevolucion(devolucion.getMotivo());

					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.init(writer, PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.setMessageSource(this.messageSource);
					writer.setPageEvent(this.pdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent);

					document.setPageSize(PageSize.A4.rotate());
					document.setMargins(PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					document.open();
					document.resetPageCount();

					// estas variables controlan la visualizacion de ciertas columnas
					boolean esTextil = seccion.matches("^001[5-8].*"); // es textil si la seccion esta entre 15 y 18
					boolean conMotivoOrdenDeRetirada = devolucion.getMotivo().equals(Constantes.DEVOLUCIONES_ORDEN_RETIRADA);
					boolean esNoPrepararMercanciaYNoRecogida = (devolucion.getEstadoCab() > Constantes.DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA) && ("N").equals(devolucion.getFlgRecogida());

					// anchos relativos para cada columna
//					float codProvColumna = 6F;
//					float denominacionProveedorColumna = 11F;
					float marcaColumna = 5F;
//					float referenciaColumna = 9F;
					float referenciaColumna = 4F;
					float denominacionColumna = 10F;
					float modeloColumna = 6F;
					float descrTallaColumna = 6F;
					float descrColorColumna = 6F;
					float modeloProveedorColumna = 6F;
//					float formatoColumna = 8F;
					float formatoColumna = 3F;
					float loteColumna = 7F;
					float caducidadColumna = 9F;
//					float cantidadColumna = 7F;
					float cantidadColumna = 4F;
					float bultoColumna = 11F;
					float ubicacionColumna = 2F;

					// facilita el calculo de numero de columnas y array de anchos
					List<Float> columnWithList = new ArrayList<Float>();
//					columnWithList.add(codProvColumna);
//					columnWithList.add(denominacionProveedorColumna);
					columnWithList.add(marcaColumna);
					columnWithList.add(referenciaColumna);
					columnWithList.add(denominacionColumna);
					if(esTextil){
						columnWithList.add(modeloColumna);
						columnWithList.add(descrTallaColumna);
						columnWithList.add(descrColorColumna);
						columnWithList.add(modeloProveedorColumna);
					}
					columnWithList.add(formatoColumna);
					if(conMotivoOrdenDeRetirada){
						columnWithList.add(loteColumna);
						columnWithList.add(caducidadColumna);
					}
					columnWithList.add(cantidadColumna);
					columnWithList.add(bultoColumna);
					if(conMotivoOrdenDeRetirada || !esNoPrepararMercanciaYNoRecogida) {
						columnWithList.add(ubicacionColumna);
					}

					int numeroDeColumnas = columnWithList.size();
					float[] tableColumnWidths = new float[numeroDeColumnas];
					for(int it = 0; it<numeroDeColumnas; it++){
						tableColumnWidths[it] = columnWithList.get(it);
					}

					// se aplicaba un factor de correccion que creo que no es necesario
					//					codProvColumna = ((codProvColumna*100)/sumaTotalColumnasFinDeCampana)*anchoTotal

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

					// Si NO existen referencias a devolver se mostrará el mensaje "No existen referencias a devolver".
					if (contadorReferenciasSinRetirada.equals(new Long(listDevolucionLinea.size()))){
						
						PdfPTable tableTitle = new PdfPTable(3);
						PdfPCell cellCab = new PdfPCell(tableTitle);
						String strNoRefLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.noexisten.referencias", null, locale);

						Chunk chunkNoRefLabel = new Chunk(strNoRefLabel, fuenteCabeceraTitleTableLabel);

						Paragraph pRefDevolver = new Paragraph();
						pRefDevolver.add(chunkNoRefLabel);
						pRefDevolver.setAlignment(Element.ALIGN_LEFT);
						
						PdfPCell cellRefDevolver = new PdfPCell(pRefDevolver);
						cellRefDevolver.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellRefDevolver.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cellRefDevolver.setBorder(PdfPCell.NO_BORDER);
						cellRefDevolver.setColspan(3);
						cellRefDevolver.setPaddingTop(15);
						cellRefDevolver.setPaddingBottom(15);
						tableTitle.addCell(cellRefDevolver);

						cellCab = new PdfPCell(tableTitle);
						cellCab.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellCab.setBorder(PdfPCell.NO_BORDER);
						cellCab.setColspan(numeroDeColumnas);
						table.addCell(cellCab);
						
					}else{
						
						//TOTAL NUMERO REFERENCIAS
						int totalRef = listDevolucionLinea.size();
						int contadorProveedor = 0;
						
						String strTotalNumeroRefLabel = messageSource.getMessage("pdf_devoluciones.finCampana.total.numero.referencias", null, locale);
						Paragraph pTotalNumeroRef = new Paragraph();
						String strTotalNumeroRefText = "";
	
						if (totalRef==0 || (listDevolucionLinea.get(0).getBulto()!=null && !(new Long("0")).equals(listDevolucionLinea.get(0).getBulto()))){
							strTotalNumeroRefText = (listContadoresProveedor != null ? Integer.toString(listContadoresProveedor.get(contadorProveedor).getContador().intValue()) : "0");
						}else{//Cuando el bulto es cero saldrá el número de referencias acumulado con bulto=0
							strTotalNumeroRefText = contadorReferenciasSinRetirada.toString();
						}
						Chunk chunkTotalNumeroRefLabel = new Chunk(strTotalNumeroRefLabel, fuenteCabeceraTitleTableLabel);
						Chunk chunkTotalNumeroRefText = new Chunk(strTotalNumeroRefText, fuenteCabeceraTitleTableText);
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
	
						int j = 0;

						/* ********************************************** */
						/* Cabecera datos BULTO y NUMERO de REFERENCIAS.  */
						/* ********************************************** */
						String strBultoLabel = "";
						if (totalRef==0 || (listDevolucionLinea.get(0).getBulto()!=null && !(new Long("0")).equals(listDevolucionLinea.get(0).getBulto()))){
							strBultoLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.bulto", null, locale).concat("  ");
						}else{
							strBultoLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.referencias.sin.retirada", null, locale);
						}
	
						int intBultoActualPagina = 1;
						String strBultosTotal = ((listContadoresBultoPorProveedor != null && listContadoresBultoPorProveedor.size() > 0)? Integer.toString(listContadoresBultoPorProveedor.get(contadorProveedor).getContador().intValue()) : "0");
						String strBultoActualPagina = new Integer(intBultoActualPagina).toString();
						String strBultoText = strBultoActualPagina.concat(" de ").concat(strBultosTotal);
						Chunk chunkBultoLabel = new Chunk(strBultoLabel, fuenteCabeceraTitleTableLabel);
						Chunk chunkBultoText = new Chunk(strBultoText, fuenteCabeceraTitleTableText);
						
						String strNumeroRefLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.numero.referencias", null, locale);
						String strNumeroRefText = ((listContadoresProveedorBulto != null && listContadoresProveedorBulto.size() > 0)? Integer.toString(listContadoresProveedorBulto.get(j).getContador().intValue()) : "0");
						Chunk chunkNumeroRefLabel = new Chunk(strNumeroRefLabel, fuenteCabeceraTitleTableLabel);
						Chunk chunkNumeroRefText = new Chunk(strNumeroRefText, fuenteCabeceraTitleTableText);
						
						Paragraph pSeccionAndNumeroRef = new Paragraph();
						if (totalRef==0 || (listDevolucionLinea.get(0).getBulto()!=null && !(new Long("0")).equals(listDevolucionLinea.get(0).getBulto()))){
							pSeccionAndNumeroRef.add(chunkBultoLabel);
							pSeccionAndNumeroRef.add(chunkBultoText);
							pSeccionAndNumeroRef.add(new Chunk("        "));
							pSeccionAndNumeroRef.add(chunkNumeroRefLabel);
							pSeccionAndNumeroRef.add(chunkNumeroRefText);
						}else{
							pSeccionAndNumeroRef.add(chunkBultoLabel);
						}
						pSeccionAndNumeroRef.setAlignment(Element.ALIGN_LEFT);

						PdfPCell cellSeccion = new PdfPCell(pSeccionAndNumeroRef);
						cellSeccion.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellSeccion.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cellSeccion.setBorder(PdfPCell.NO_BORDER);
						cellSeccion.setColspan(3);
						cellSeccion.setPaddingTop(15);
						cellSeccion.setPaddingBottom(15);

						/* ********************************************** */
						/* 			Cabecera datos PROVEEDOR			  */
						/* ********************************************** */
						String strCodProveedorLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.codigo.proveedor", null, locale);
						String strCodProveedorText = listDevolucionLinea.get(0).getProveedorGenTrabajoFormated();
						Chunk chunkCodProveedorLabel = new Chunk(strCodProveedorLabel, fuenteCabeceraTitleTableLabel);
						Chunk chunkCodProveedorText = new Chunk(strCodProveedorText, fuenteCabeceraTitleTableText);
						
						String strDenomProveedorLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.denominacion.proveedor", null, locale);
						String strDenomProveedorText = listDevolucionLinea.get(0).getDenomProveedor() != null ? listDevolucionLinea.get(0).getDenomProveedor().trim() : "";
						Chunk chunkDenomProveedorLabel = new Chunk(strDenomProveedorLabel, fuenteCabeceraTitleTableLabel);
						Chunk chunkDenomProveedorText = new Chunk(strDenomProveedorText, fuenteCabeceraTitleTableText);

						Paragraph pProveedor = new Paragraph();
						pProveedor.add(chunkCodProveedorLabel);
						pProveedor.add(chunkCodProveedorText);
						pProveedor.add(new Chunk("        "));
						pProveedor.add(chunkDenomProveedorLabel);
						pProveedor.add(chunkDenomProveedorText);
						
						pProveedor.setAlignment(Element.ALIGN_LEFT);

						PdfPCell cellProveedor = new PdfPCell(pProveedor);
						cellProveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellProveedor.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cellProveedor.setBorder(PdfPCell.NO_BORDER);
						cellProveedor.setColspan(3);
						cellProveedor.setPaddingTop(15);
						cellProveedor.setPaddingBottom(15);

						tableTitle.addCell(cellSeccion);
						tableTitle.addCell(cellProveedor);
						

						
						/* ********************************************** */
						/* 				COLUMNAS de la tabla  			  */
						/* ********************************************** */
	
						PdfPCell cellCab = new PdfPCell(tableTitle);
						cellCab.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellCab.setBorder(PdfPCell.NO_BORDER);
						cellCab.setColspan(numeroDeColumnas);
						table.addCell(cellCab);
	
//						String strCodigoProveedor = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.codigo.proveedor", null, locale);
//						PdfPCell cellCodProv = new PdfPCell(new Phrase(strCodigoProveedor, fuenteCabeceraColumnasTable));
//						cellCodProv.setHorizontalAlignment(Element.ALIGN_CENTER);
//						cellCodProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
//						table.addCell(cellCodProv);
//						
//						String strDenominacionProveedor = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.denominacion.proveedor", null, locale);
//						PdfPCell cellDenProv = new PdfPCell(new Phrase(strDenominacionProveedor, fuenteCabeceraColumnasTable));
//						cellDenProv.setHorizontalAlignment(Element.ALIGN_CENTER);
//						cellDenProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
//						table.addCell(cellDenProv);
						
						String strMarca = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.marca", null, locale);
						PdfPCell cellMarca = new PdfPCell(new Phrase(strMarca, fuenteCabeceraColumnasTable));
						cellMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellMarca.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellMarca);
						
						String strCodArticulo = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.referencia", null, locale);
						PdfPCell cellCodArticulo = new PdfPCell(new Phrase(strCodArticulo, fuenteCabeceraColumnasTable));
						cellCodArticulo.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellCodArticulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellCodArticulo);
						
						String strDescriptionArt = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.denominacion", null, locale);
						PdfPCell cellDescriptionArt = new PdfPCell(new Phrase(strDescriptionArt, fuenteCabeceraColumnasTable));
						cellDescriptionArt.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellDescriptionArt.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellDescriptionArt);
						
						if(esTextil){
							addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.modelo", table, fuenteCabeceraColumnasTable, locale);
							addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.descrTalla", table, fuenteCabeceraColumnasTable, locale);
							addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.descrColor", table, fuenteCabeceraColumnasTable, locale);
							addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.modeloProveedor", table, fuenteCabeceraColumnasTable, locale);
						}
						
						String strFormato = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.formato", null, locale);
						PdfPCell cellFormato = new PdfPCell(new Phrase(strFormato, fuenteCabeceraColumnasTable));
						cellFormato.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellFormato.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellFormato);
						
						//Si la devolución es de orden de retirada,poner estas columnas
						if(conMotivoOrdenDeRetirada){
							String strLote = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.lote", null, locale);
							PdfPCell cellLote = new PdfPCell(new Phrase(strLote, fuenteCabeceraColumnasTable));
							cellLote.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellLote.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellLote);
							
							String strCaducidad = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.caducidad", null, locale);
							PdfPCell cellCaducidad = new PdfPCell(new Phrase(strCaducidad, fuenteCabeceraColumnasTable));
							cellCaducidad.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellCaducidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellCaducidad);						
						}
						
						/*
						String strTipo = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.tipo", null, locale);
			        	PdfPCell cellTipo = new PdfPCell(new Phrase(strTipo, fuenteCabeceraColumnasTable));
			        	cellTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
			        	cellTipo.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        	table.addCell(cellTipo);
			        	*/
						
						String strCantidad = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.cantidad", null, locale);
						PdfPCell cellCantidad = new PdfPCell(new Phrase(strCantidad, fuenteCabeceraColumnasTable));
						cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellCantidad);
						
//						String strBulto = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.bulto", null, locale);
//						PdfPCell cellBulto = new PdfPCell(new Phrase(strBulto, fuenteCabeceraColumnasTable));
//						cellBulto.setHorizontalAlignment(Element.ALIGN_CENTER);
//						cellBulto.setVerticalAlignment(Element.ALIGN_MIDDLE);
//						table.addCell(cellBulto);

						String strReferenciaCantidad = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.referenciaCantidad", null, locale);
						PdfPCell cellReferenciaCantidad = new PdfPCell(new Phrase(strReferenciaCantidad, fuenteCabeceraColumnasTable));
						cellReferenciaCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
						cellReferenciaCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cellReferenciaCantidad);

						if(!esNoPrepararMercanciaYNoRecogida || conMotivoOrdenDeRetirada){
							String strUbicacion = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.ubicacion", null, locale);
							PdfPCell cellUbicacion = new PdfPCell(new Phrase(strUbicacion, fuenteCabeceraColumnasTable));
							cellUbicacion.setHorizontalAlignment(Element.ALIGN_CENTER);
							cellUbicacion.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cellUbicacion);	
						}
						
						String strContinuaSiguientePagina = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.continuaSiguientePagina", null, locale);
						PdfPCell cellFooter = new PdfPCell(new Phrase(strContinuaSiguientePagina, fuenteCeldasTable));
						cellFooter.setColspan(numeroDeColumnas);
						table.addCell(cellFooter);
	
						table.setHeaderRows(3);
						table.setFooterRows(1);
						table.setSkipLastFooter(true);
	
						String strCellBultoAux = Utilidades.convertirDoubleAString(listDevolucionLinea.get(0).getBulto() != null ? listDevolucionLinea.get(0).getBulto().doubleValue() : 0.0D, "###0");
						Long lngCellProvrGenAux = listDevolucionLinea.get(0).getProvrGen();
						Long flgRefRetiradasAux = listDevolucionLinea.get(0).getFlgRefRetiradas();
	
						for (int i = 0; i < listDevolucionLinea.size(); i++) {
	
							TDevolucionLinea devolucionLinea = listDevolucionLinea.get(i);
	
							String strCellBulto = Utilidades.convertirDoubleAString(devolucionLinea.getBulto() != null ? devolucionLinea.getBulto().doubleValue() : 0.0D, "###0");
							Long lngCellProvrGen = devolucionLinea.getProvrGen();
							Long flgRefRetiradas = devolucionLinea.getFlgRefRetiradas();
	
							//Si la recogida es de tipo proveedor, se dibuja la cabecera de la tabla por cambio de PROVEEDOR-BULTO. Si no, se hace solo por BULTO.
								
							//TRATAMIENTO SALTO POR PROVEEDOR-BULTO. Los bultos a 0 van en la primera página en un sólo bloque
							//Si cambia de proveedor, bulto o paso de bulto=0 a las siguientes, hay que hacer salto de pagina
							if (new Long(1).equals(flgRefRetiradas)
								&& (!flgRefRetiradasAux.equals(flgRefRetiradas) || !lngCellProvrGenAux.equals(lngCellProvrGen) || !(strCellBultoAux.equals(strCellBulto)))) {
	
								if (!flgRefRetiradasAux.equals(flgRefRetiradas)){ //Se inicializan los contadores al pasar de bulto=0 al resto
									intBultoActualPagina = 0;
									j=0;
									contadorProveedor=0;
								}else{
									// Si se cambia de proveedor.
									if (!lngCellProvrGenAux.equals(lngCellProvrGen)){
										contadorProveedor++;
										intBultoActualPagina = 0;	
									}
	
									if (!strCellBultoAux.equals(strCellBulto) || !lngCellProvrGenAux.equals(lngCellProvrGen)){
										j++;
									}
								}
	
								table.setComplete(true);
								pTable.add(table);
								document.add(pTable);
	
								pTable.clear();
								if (writer.getVerticalPosition(true) < document.top()) {
									document.newPage();
								}
	
								flgRefRetiradasAux = flgRefRetiradas;
								lngCellProvrGenAux = lngCellProvrGen;
								strCellBultoAux = strCellBulto;
	
								//Volvemos a generar toda la tabla. Cambia el numero de referencias por proveedor en la cabecera.	
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

								/* ****************************************** */
								/* Cabecera datos TOTAL NUMERO de REFERENCIAS */
								/* ****************************************** */
								totalRef = listDevolucionLinea.size();
								//strTotalNumeroRefText = (new Long (totalRef) != null ? new Long (totalRef).toString() : "0");
								strTotalNumeroRefText = ((listContadoresProveedor != null && listContadoresProveedor.size() > 0)? Integer.toString(listContadoresProveedor.get(contadorProveedor).getContador().intValue()) : "0");
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

								/* ********************************************** */
								/* Cabecera datos BULTO y NUMERO de REFERENCIAS	  */
								/* ********************************************** */
								strBultoLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.bulto", null, locale).concat("  ");
								strBultosTotal = (listContadoresBultoPorProveedor != null ? Integer.toString(listContadoresBultoPorProveedor.get(contadorProveedor).getContador().intValue()) : "0");
								intBultoActualPagina++;
								strBultoActualPagina = new Integer(intBultoActualPagina).toString();
								
								strBultoText = strBultoActualPagina.concat(" de ").concat(strBultosTotal);
								chunkBultoLabel = new Chunk(strBultoLabel, fuenteCabeceraTitleTableLabel);
								chunkBultoText = new Chunk(strBultoText, fuenteCabeceraTitleTableText);
									
								strNumeroRefText = ((listContadoresProveedorBulto != null && listContadoresProveedorBulto.size() > 0)? Integer.toString(listContadoresProveedorBulto.get(j).getContador().intValue()) : "0");
								chunkNumeroRefLabel = new Chunk(strNumeroRefLabel, fuenteCabeceraTitleTableLabel);
								chunkNumeroRefText = new Chunk(strNumeroRefText, fuenteCabeceraTitleTableText);
									
								pSeccionAndNumeroRef = new Paragraph();	
								pSeccionAndNumeroRef.add(chunkBultoLabel);
								pSeccionAndNumeroRef.add(chunkBultoText);
								pSeccionAndNumeroRef.add(new Chunk("        "));
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
									
									
								/* ********************************************** */
								/* 			Cabecera datos PROVEEDOR			  */
								/* ********************************************** */
								strCodProveedorText = devolucionLinea.getProveedorGenTrabajoFormated();
								chunkCodProveedorLabel = new Chunk(strCodProveedorLabel, fuenteCabeceraTitleTableLabel);
								chunkCodProveedorText = new Chunk(strCodProveedorText, fuenteCabeceraTitleTableText);
									
								strDenomProveedorText = devolucionLinea.getDenomProveedor() != null ? devolucionLinea.getDenomProveedor().trim() : "";
								chunkDenomProveedorLabel = new Chunk(strDenomProveedorLabel, fuenteCabeceraTitleTableLabel);
								chunkDenomProveedorText = new Chunk(strDenomProveedorText, fuenteCabeceraTitleTableText);

								pProveedor = new Paragraph();
								pProveedor.add(chunkCodProveedorLabel);
								pProveedor.add(chunkCodProveedorText);
								pProveedor.add(new Chunk("        "));
								pProveedor.add(chunkDenomProveedorLabel);
								pProveedor.add(chunkDenomProveedorText);
									
								pProveedor.setAlignment(Element.ALIGN_LEFT);

								cellProveedor = new PdfPCell(pProveedor);
								cellProveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
								cellProveedor.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cellProveedor.setBorder(PdfPCell.NO_BORDER);
								cellProveedor.setColspan(3);
								cellProveedor.setPaddingTop(15);
								cellProveedor.setPaddingBottom(15);	

								tableTitle.addCell(cellSeccion);
								tableTitle.addCell(cellProveedor);

								/* ********************************************** */
								/* 				COLUMNAS de la tabla  			  */
								/* ********************************************** */
								cellCab = new PdfPCell(tableTitle);
								cellCab.setHorizontalAlignment(Element.ALIGN_CENTER);
								cellCab.setBorder(PdfPCell.NO_BORDER);
								cellCab.setColspan(numeroDeColumnas);
								table.addCell(cellCab);
	
//								strCodigoProveedor = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.codigo.proveedor", null, locale);
//								cellCodProv = new PdfPCell(new Phrase(strCodigoProveedor, fuenteCabeceraColumnasTable));
//								cellCodProv.setHorizontalAlignment(Element.ALIGN_CENTER);
//								cellCodProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
//								table.addCell(cellCodProv);
//								
//								strDenominacionProveedor = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.denominacion.proveedor", null, locale);
//								cellDenProv = new PdfPCell(new Phrase(strDenominacionProveedor, fuenteCabeceraColumnasTable));
//								cellDenProv.setHorizontalAlignment(Element.ALIGN_CENTER);
//								cellDenProv.setVerticalAlignment(Element.ALIGN_MIDDLE);
//								table.addCell(cellDenProv);
								
								table.addCell(cellMarca);
								table.addCell(cellCodArticulo);
								table.addCell(cellDescriptionArt);
								
								if (esTextil){
									addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.modelo", table, fuenteCabeceraColumnasTable, locale);
									addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.descrTalla", table, fuenteCabeceraColumnasTable, locale);
									addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.descrColor", table, fuenteCabeceraColumnasTable, locale);
									addCabeceraColumnaSimple("pdf_devoluciones.ordenRecogida.tabla.cabecera.modeloProveedor", table, fuenteCabeceraColumnasTable, locale);
								}
								
								table.addCell(cellFormato);
								
								//Si la devolución es de orden de retirada, poner estas columnas
								if(conMotivoOrdenDeRetirada){								
									String strLote = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.lote", null, locale);
									PdfPCell cellLote = new PdfPCell(new Phrase(strLote, fuenteCabeceraColumnasTable));
									cellLote.setHorizontalAlignment(Element.ALIGN_CENTER);
									cellLote.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cellLote);
									
									String strCaducidad = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.caducidad", null, locale);
									PdfPCell cellCaducidad = new PdfPCell(new Phrase(strCaducidad, fuenteCabeceraColumnasTable));
									cellCaducidad.setHorizontalAlignment(Element.ALIGN_CENTER);
									cellCaducidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cellCaducidad);								
								}
									/*strTipo = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.tipo", null, locale);
						        cellTipo = new PdfPCell(new Phrase(strTipo, fuenteCabeceraColumnasTable));
						        cellTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
						        cellTipo.setVerticalAlignment(Element.ALIGN_MIDDLE);
						        table.addCell(cellTipo);*/
								
								table.addCell(cellCantidad);
								
//								cellBulto = new PdfPCell(new Phrase(strBulto, fuenteCabeceraColumnasTable));
//								cellBulto.setHorizontalAlignment(Element.ALIGN_CENTER);
//								cellBulto.setVerticalAlignment(Element.ALIGN_MIDDLE);
//								table.addCell(cellBulto);

								table.addCell(cellReferenciaCantidad);

								if(conMotivoOrdenDeRetirada || !esNoPrepararMercanciaYNoRecogida){
									String strUbicacion = this.messageSource.getMessage("pdf_devoluciones.ordenRecogida.tabla.cabecera.ubicacion", null, locale);
									PdfPCell cellUbicacion = new PdfPCell(new Phrase(strUbicacion, fuenteCabeceraColumnasTable));
									cellUbicacion.setHorizontalAlignment(Element.ALIGN_CENTER);
									cellUbicacion.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cellUbicacion);
								}									

								cellFooter = new PdfPCell(new Phrase(strContinuaSiguientePagina, fuenteCeldasTable));
								cellFooter.setColspan(numeroDeColumnas);
								table.addCell(cellFooter);
	
								table.setHeaderRows(3);
								table.setFooterRows(1);
								table.setSkipLastFooter(true);
							}
								

							/* *************************** */
							/* Líneas del DETALLE del PDF. */
							/* *************************** */
							
							// SÓLO se mostrarán en el PDF las referencias que tienen cantidad para devolver.
							if (devolucionLinea.getStockDevuelto() != null && devolucionLinea.getStockDevuelto() != 0){
//								PdfPCell cell11 = new PdfPCell(new Phrase(devolucionLinea.getProveedorGenTrabajoFormated(), fuenteCeldasTable));
//								cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
//								cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
//								table.addCell(cell11);
//		
//								String strCellDescProv = devolucionLinea.getDenomProveedor() != null ? devolucionLinea.getDenomProveedor().trim() : "";
//								PdfPCell cell12 = new PdfPCell(new Phrase(strCellDescProv, fuenteCeldasTable));
//								cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
//								cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);
//								table.addCell(cell12);	

								String strCellMarca = devolucionLinea.getMarca() != null ? devolucionLinea.getMarca().trim() : "";
								PdfPCell cell3 = new PdfPCell(new Phrase(strCellMarca, fuenteCeldasTable));
								cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
								table.addCell(cell3);
		
								String strCellCodArticulo = devolucionLinea.getCodArticulo() != null ? devolucionLinea.getCodArticulo().toString() : "";
								PdfPCell cell1 = new PdfPCell(new Phrase(strCellCodArticulo, fuenteCeldasTable));
								cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
								table.addCell(cell1);
		
								String strCellDescriptionArt = devolucionLinea.getDenominacion() != null ? devolucionLinea.getDenominacion().trim() : "";
								PdfPCell cell2 = new PdfPCell(new Phrase(strCellDescriptionArt, fuenteCeldasTable));
								cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
								table.addCell(cell2);
		
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
		
								String strCellFormato = Utilidades.convertirDoubleAString(devolucionLinea.getFormato() != null ? devolucionLinea.getFormato().doubleValue() : 0.0D, "###0.###");
								String strCellTipo = devolucionLinea.getTipoFormato() != null ? devolucionLinea.getTipoFormato().toString() : "";
		
								String strCellFormatoTipo = strCellFormato + " " + strCellTipo;
		
								PdfPCell cell4 = new PdfPCell(new Phrase(strCellFormatoTipo, fuenteCeldasTable));
								cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
								table.addCell(cell4);
		
								/*PdfPCell cell5 = new PdfPCell(new Phrase(strCellTipo, fuenteCeldasTable));
					          	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					          	cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
					          	table.addCell(cell5);
					          	*/

								//Si la devolución es de orden de retirada, poner estas columnas
								if (conMotivoOrdenDeRetirada){
									String strCellLote = devolucionLinea.getnLote() != null ? devolucionLinea.getnLote().trim() : "";
									String strCellLoteFmt = strCellLote.replaceAll(",", "\r\n");
									PdfPCell cell10 = new PdfPCell(new Phrase(strCellLoteFmt, fuenteCeldasTable));
									cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cell10);
									
									String strCellCaducidad = devolucionLinea.getnCaducidad() != null ? devolucionLinea.getnCaducidad().trim() : "";
									String strCellCaducidadFmt = strCellCaducidad.replaceAll(",", "\r\n");
									PdfPCell cell9 = new PdfPCell(new Phrase(strCellCaducidadFmt, fuenteCeldasTable));
									cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cell9);						
								}

								String strCellCantidad = Utilidades.convertirDoubleAString(devolucionLinea.getStockDevuelto()!= null ? devolucionLinea.getStockDevuelto().doubleValue() : 0.0D, "###0.###");
								PdfPCell cell6 = new PdfPCell(new Phrase(strCellCantidad, fuenteCeldasTable));
								cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
								table.addCell(cell6);
		
//								strCellBulto = Utilidades.convertirDoubleAString(devolucionLinea.getBulto() != null ? devolucionLinea.getBulto().doubleValue() : 0.0D, "###0");
//								PdfPCell cell7 = new PdfPCell(new Phrase(strCellBulto, fuenteCeldasTable));
//								cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
//								cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
//								table.addCell(cell7);

								// Referencia/Cantidad (Código EAN de la referencia)
								if (devolucionLinea.getCodEAN() != null){
									PdfContentByte cb = writer.getDirectContent();
									Barcode39 codeEAN = new Barcode39();
									codeEAN.setStartStopText(false);
									strCellCantidad=strCellCantidad.replace(",", ".");
									String strCellEan = devolucionLinea.getCodEAN()+"/"+strCellCantidad;
									codeEAN.setCode(strCellEan);

									try{
										Image codeEANImage = codeEAN.createImageWithBarcode(cb, null, null);
										codeEANImage.scalePercent(80);
										
										Paragraph pEAN = new Paragraph();
										pEAN.add(new Chunk(codeEANImage, 0, 0, true));
										pEAN.setAlignment(Element.ALIGN_CENTER);

										PdfPCell cell7 = new PdfPCell();
										cell7.addElement(pEAN);
										cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
										cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
										table.addCell(cell7);

									}catch(Exception e){
										logger.error("Cod Articulo: "+strCellCodArticulo);
										logger.error("EAN Code: "+strCellEan);
										logger.error("EAN Generating... Exporting pdf Informe Devolucion Orden Recogida: PdfInformeDevOrdenRecogidaProveedorView.buildPdfDocument");
										logger.error(StackTraceManager.getStackTrace(e));
										throw e;
									}
								}else{
									Paragraph pEAN = new Paragraph();
									pEAN.add(new Chunk(""));
									pEAN.setAlignment(Element.ALIGN_CENTER);

									PdfPCell cell7 = new PdfPCell();
									cell7.addElement(pEAN);
									cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cell7);
								}
								
								if(conMotivoOrdenDeRetirada || !esNoPrepararMercanciaYNoRecogida){
									String strCellUbicacion = devolucionLinea.getUbicacion() != null ? devolucionLinea.getUbicacion().trim() : "";
									PdfPCell cell8 = new PdfPCell(new Phrase(strCellUbicacion, fuenteCeldasTable));
									cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
									cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cell8);
								}
								
							}
						}
					}

					table.setComplete(true);
					pTable.add(table);
					document.add(pTable);
				}

			}
			document.close();

		} catch (Exception e) {
			logger.error("Exporting pdf Informe Devolucion Orden Recogida: PdfInformeDevOrdenRecogidaProveedorView.buildPdfDocument");
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