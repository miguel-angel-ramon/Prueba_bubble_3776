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
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaLinea;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

public class PdfInformeEntradasDescentralizadasView extends AbstractPdfView
{
	private static float PAGE_MARGIN_LEFT = 40;
	private static float PAGE_MARGIN_RIGHT = 20;
	private static float PAGE_MARGIN_TOP = 140;
	private static float PAGE_MARGIN_BOTTOM = 30;

	private static float TABLE_FONT_CABECERA_TITLE_SIZE = 10;
	private static float TABLE_FONT_CABECERA_COLUMN_SIZE = 10;
	private static float TABLE_FONT_CELDAS_SIZE = 8;
	private PdfInformeEntradasDescentralizadasHeaderFooterPageEvent pdfInformeEntradasDescentralizadasHeaderFooterPageEvent;
	private MessageSource messageSource;



	@Autowired
	public void setPdfInformeEntradasDescentralizadasHeaderFooterPageEvent(PdfInformeEntradasDescentralizadasHeaderFooterPageEvent pdfInformeEntradasDescentralizadasHeaderFooterPageEvent) {
		this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent = pdfInformeEntradasDescentralizadasHeaderFooterPageEvent;
	}

	@Resource
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		User userSession = (User)request.getSession().getAttribute("user");
		Entrada entrada = (Entrada)model.get("entrada");
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

			if (entrada != null) {

					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setImgLogoIzq(imgLogoIzq);
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setImgLogoDer(imgLogoDer);
					
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setNumeroPedido(entrada.getCodCabPedido());
					String proveedor = entrada.getCodProvGen()+"-"+entrada.getCodProvTrab()+"/"+entrada.getDenomProvTrab();
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setProveedor(proveedor);
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setAlbaran(entrada.getCodAlbProv());
					//this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setFechaTarifa(entrada.getFechaTarifaStr().replaceAll("-", "/"));
					//this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setFechaPedido(entrada.getFechaEntradaStr().replaceAll("-", "/"));
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setFechaTarifa(entrada.getFechaTarifaStr());
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setFechaPedido(entrada.getFechaEntradaStr());
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setNumIncidencia(entrada.getNumIncidencia());


					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.init(writer, PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent.setMessageSource(this.messageSource);
					writer.setPageEvent(this.pdfInformeEntradasDescentralizadasHeaderFooterPageEvent);

					document.setPageSize(PageSize.A4.rotate());
					document.setMargins(PAGE_MARGIN_LEFT, PAGE_MARGIN_RIGHT, PAGE_MARGIN_TOP, PAGE_MARGIN_BOTTOM);
					document.open();
					document.resetPageCount();

					float codReferenciaColumna = 7F;
					float denominacionColumna = 11F;
					float ucColumna = 5F;
					float cajasPedidasColumna = 8F;
					float cajasRecepcionadasColumna = 9F;
					//float bandejasPedidasColumna = 8F;
					//float bandejasRecepcionadasColumna = 9F;
					float unidadesPedidasColumna = 8F;
					float unidadesRecepcionadasColumna = 10F;
					
					/*float[] tableColumnWidths = {codReferenciaColumna, denominacionColumna, ucColumna, cajasPedidasColumna, cajasRecepcionadasColumna,
							bandejasPedidasColumna, bandejasRecepcionadasColumna, unidadesPedidasColumna, unidadesRecepcionadasColumna};*/
					float[] tableColumnWidths = {codReferenciaColumna, denominacionColumna, ucColumna, cajasPedidasColumna, cajasRecepcionadasColumna,
							  unidadesPedidasColumna, unidadesRecepcionadasColumna};

					int numeroColumnas = tableColumnWidths.length;
					PdfPTable table = new PdfPTable(numeroColumnas);
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

					// Cabeceras de la tabla
					String strReferencia = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.referencia", null, locale);
					PdfPCell cellReferencia = new PdfPCell(new Phrase(strReferencia, fuenteCabeceraColumnasTable));
					cellReferencia.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellReferencia.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellReferencia);

					String strDenominacion = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.denominacion", null, locale);
					PdfPCell cellDenominacion = new PdfPCell(new Phrase(strDenominacion, fuenteCabeceraColumnasTable));
					cellDenominacion.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellDenominacion.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellDenominacion);
					
					String strUC = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.uc", null, locale);
					PdfPCell cellUC = new PdfPCell(new Phrase(strUC, fuenteCabeceraColumnasTable));
					cellUC.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellUC.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellUC);
					
					String strCajasPedidas = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.cajas.pedidas", null, locale);
					PdfPCell cellCajasPedidas = new PdfPCell(new Phrase(strCajasPedidas, fuenteCabeceraColumnasTable));
					cellCajasPedidas.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCajasPedidas.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCajasPedidas);
					
					String strUnidadesPedidas = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.unidades.pedidas", null, locale);
					PdfPCell cellUnidadesPedidas = new PdfPCell(new Phrase(strUnidadesPedidas, fuenteCabeceraColumnasTable));
					cellUnidadesPedidas.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellUnidadesPedidas.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellUnidadesPedidas);
					
					/*String strBandejasPedidas = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.bandejas.pedidas", null, locale);
					PdfPCell cellBandejasPedidas = new PdfPCell(new Phrase(strBandejasPedidas, fuenteCabeceraColumnasTable));
					cellBandejasPedidas.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellBandejasPedidas.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellBandejasPedidas);*/
					
					String strCajasRecepcionadas = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.cajas.recepcionadas", null, locale);
					PdfPCell cellCajasRecepcionadas = new PdfPCell(new Phrase(strCajasRecepcionadas, fuenteCabeceraColumnasTable));
					cellCajasRecepcionadas.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCajasRecepcionadas.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellCajasRecepcionadas);	


					String strUnidadesRecepcionadas = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.unidades.recepcionadas", null, locale);
					PdfPCell cellUnidadesRecepcionadas = new PdfPCell(new Phrase(strUnidadesRecepcionadas, fuenteCabeceraColumnasTable));
					cellUnidadesRecepcionadas.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellUnidadesRecepcionadas.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellUnidadesRecepcionadas);
					
					/*String strBandejasRecepcionadas = this.messageSource.getMessage("pdf_entradas.entrada.tabla.columna.bandejas.recepcionadas", null, locale);
					PdfPCell cellBandejasRecepcionadas = new PdfPCell(new Phrase(strBandejasRecepcionadas, fuenteCabeceraColumnasTable));
					cellBandejasRecepcionadas.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellBandejasRecepcionadas.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cellBandejasRecepcionadas);*/
					
					table.setHeaderRows(0);
					table.setFooterRows(1);
					table.setSkipLastFooter(true);
					
					
					for (int i=0; i < entrada.getLstEntradaLinea().size(); i++) {
						EntradaLinea linea = entrada.getLstEntradaLinea().get(i);
						
						String codArticuloStr = linea.getCodArticulo() != null ? linea.getCodArticulo().toString() : "";
						PdfPCell cell1 = new PdfPCell(new Phrase(codArticuloStr, fuenteCeldasTable));
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell1);
						
						String denomArticuloStr = linea.getDenomCodArticulo() != null ? linea.getDenomCodArticulo().toString() : "";
						PdfPCell cell2 = new PdfPCell(new Phrase(denomArticuloStr, fuenteCeldasTable));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell2);
						
						String ucStr = linea.getUc() != null ? linea.getUc().toString() : "";
						PdfPCell cell3 = new PdfPCell(new Phrase(ucStr, fuenteCeldasTable));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell3);
						
						String numeroCajasPedidasStr = linea.getNumeroCajasPedidas() != null ? linea.getNumeroCajasPedidas().toString() : "";
						PdfPCell cell4 = new PdfPCell(new Phrase(numeroCajasPedidasStr, fuenteCeldasTable));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell4);
						
						String totalUnidadesPedidasStr = linea.getTotalUnidadesPedidas() != null ? linea.getTotalUnidadesPedidas().toString() : "";
						PdfPCell cell8 = new PdfPCell(new Phrase(totalUnidadesPedidasStr, fuenteCeldasTable));
						cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell8);
						
						/*String bandejasPedidasStr = linea.getTotalBandejasPedidas() != null ? linea.getTotalBandejasPedidas().toString() : "";
						PdfPCell cell6 = new PdfPCell(new Phrase(bandejasPedidasStr, fuenteCeldasTable));
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell6);*/
						
						String numeroCajasRecepcionadaStr = linea.getNumeroCajasRecepcionadas() != null ? linea.getNumeroCajasRecepcionadas().toString() : "";
						PdfPCell cell5 = new PdfPCell(new Phrase(numeroCajasRecepcionadaStr, fuenteCeldasTable));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell5);
						
						String totalUnidadesRecepcionadasStr = linea.getTotalUnidadesRecepcionadas() != null ? linea.getTotalUnidadesRecepcionadas().toString() : "";
						PdfPCell cell9 = new PdfPCell(new Phrase(totalUnidadesRecepcionadasStr, fuenteCeldasTable));
						cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell9);
						
						/*String totalBandejasRecepcionadasStr = linea.getTotalBandejasRecepcionadas() != null ? linea.getTotalBandejasRecepcionadas().toString() : "";
						PdfPCell cell7 = new PdfPCell(new Phrase(totalBandejasRecepcionadasStr, fuenteCeldasTable));
						cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell7);*/
					}
					table.setComplete(true);
					pTable.add(table);
					document.add(pTable);

			}


			document.close();

		} catch (Exception e) {
			logger.error("Exporting pdf Informe Entrada Descentralizada: PdfInformeEntradasDescentralizadasView.buildPdfDocument");
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