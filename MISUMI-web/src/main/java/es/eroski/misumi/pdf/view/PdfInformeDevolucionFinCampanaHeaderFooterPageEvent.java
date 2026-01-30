package es.eroski.misumi.pdf.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import es.eroski.misumi.control.WelcomeController;
import es.eroski.misumi.util.StackTraceManager;

@Service(value = "servicePdfInformeDevolucionFinCampanaHeaderFooterPageEvent")
public class PdfInformeDevolucionFinCampanaHeaderFooterPageEvent extends PdfPageEventHelper {

	private static Logger logger = Logger.getLogger(WelcomeController.class);
	
	private static float TABLE_FONT_CABECERA_TITULO_SIZE = 12;
	private static float TABLE_FONT_CABECERA_DATOS_SIZE = 10;
	
	private static float TABLE_CABECERA_TITULO_COLUMN_1_WIDTH_PORCEN = 20;
	private static float TABLE_CABECERA_TITULO_COLUMN_2_WIDTH_PORCEN = 60;
	private static float TABLE_CABECERA_TITULO_COLUMN_3_WIDTH_PORCEN = 20;
	private static float TABLE_CABECERA_DATOS_COLUMN_1_WIDTH_PORCEN = 72;
	private static float TABLE_CABECERA_DATOS_COLUMN_2_WIDTH_PORCEN = 28;

	private MessageSource messageSource;
	
    private Image imgLogoIzq;
    private Image imgLogoDer;
    private String nombreCampana;
    
    private Date fechaLimite;
    private Date fechaDesde;
    private Date fechaHasta;
    private String observaciones;
    private String centro;
    private String seccion;
    private String rma;
    
    private String localizador;
    private String abono;
    private String recogida;
    private Date fechaHora;
    
    private PdfTemplate template;
	
	private float marginLeft = 1;
	private float marginRight = 1;
	private float marginTop = 1;
	private float marginBottom = 1;
	
	private Font fontCabeceraTitulo;
	private Font fontCabeceraDatosLabel;
	private Font fontCabeceraDatosText;
	
	public void init(PdfWriter writer, float marginLeft, float marginRight, float marginTop, float marginBottom){
		
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		
        this.fontCabeceraTitulo = new Font();
        this.fontCabeceraTitulo.setSize(TABLE_FONT_CABECERA_TITULO_SIZE);
        this.fontCabeceraTitulo.setStyle(Font.BOLD);
        this.fontCabeceraTitulo.setStyle(Font.UNDERLINE);
        this.fontCabeceraTitulo.setColor(Color.RED);
        
        this.fontCabeceraDatosLabel = new Font();
        this.fontCabeceraDatosLabel.setSize(TABLE_FONT_CABECERA_DATOS_SIZE);
        this.fontCabeceraDatosLabel.setStyle(Font.BOLD);
        this.fontCabeceraDatosLabel.setColor(Color.BLACK);
        
        this.fontCabeceraDatosText = new Font();
        this.fontCabeceraDatosText.setSize(TABLE_FONT_CABECERA_DATOS_SIZE);
        this.fontCabeceraDatosText.setStyle(Font.BOLD);
        this.fontCabeceraDatosText.setColor(Color.RED);
        
		// Tenemos que iniciar el template cada vez
		// que escribimos un nuevo documento.
		
		if (template != null){
			// Si no es null reseteamos el template
			template.reset();
		}
		template = writer.getDirectContent().createTemplate(30, 16);
	}

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		
		List<Object> args = new ArrayList<Object>();
		
		Locale locale = LocaleContextHolder.getLocale();
		
        Rectangle pageSize = writer.getPageSize();
		
		// Tabla que contiene la cabecera
		PdfPTable tablaCabecera;
		tablaCabecera = new PdfPTable(1);
		try {
			tablaCabecera.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tablaCabecera.setTotalWidth(pageSize.getWidth() - (marginLeft + marginRight));
			tablaCabecera.setWidthPercentage(100); // table width 100%
			float[] columnWidths = {100}; // column widths relative
			tablaCabecera.setWidths(columnWidths);
		} catch (Exception e) {
			logger.error("Exporting pdf Informe Devolucion Fin Campaña: PdfInformeDevolucionFinCampanaHeaderFooterPageEvent.onStartPage tablaCabecera");
			logger.error(StackTraceManager.getStackTrace(e));
            throw new ExceptionConverter(e);
		}

		// Tabla que contiene el logo izquierdo, el titulo y el logo derecho
		PdfPTable tablaCabeceraTitulo;
		try {
			tablaCabeceraTitulo = new PdfPTable(3);
			
			tablaCabeceraTitulo.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tablaCabeceraTitulo.setTotalWidth(pageSize.getWidth() - (marginLeft + marginRight));
			tablaCabeceraTitulo.setWidthPercentage(100); // table width 100%
			float[] columnWidths = {TABLE_CABECERA_TITULO_COLUMN_1_WIDTH_PORCEN, TABLE_CABECERA_TITULO_COLUMN_2_WIDTH_PORCEN, TABLE_CABECERA_TITULO_COLUMN_3_WIDTH_PORCEN}; // column widths relative
			tablaCabeceraTitulo.setWidths(columnWidths);

			// logo izquierda
			Chunk chunkLogoIzq = new Chunk(imgLogoIzq, 0, 0, true);
			Paragraph pLogoIzq = new Paragraph();
			pLogoIzq.add(chunkLogoIzq);
			PdfPCell cellLogoIzq = new PdfPCell();
			cellLogoIzq.setBorder(PdfPCell.NO_BORDER);
			cellLogoIzq.addElement(pLogoIzq);
			tablaCabeceraTitulo.addCell(cellLogoIzq);
			
			// título
			args.clear();
			args.add(nombreCampana);
			String strTituloCampana = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.titulo", args.toArray(), locale);
			Chunk chunkTituloCampana = new Chunk(strTituloCampana, this.fontCabeceraTitulo);
			Paragraph pTituloCampana = new Paragraph();
			pTituloCampana.add(chunkTituloCampana);
			pTituloCampana.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellTitulo = new PdfPCell();
			cellTitulo.addElement(pTituloCampana);
			cellTitulo.setBorder(PdfPCell.NO_BORDER);
			cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCabeceraTitulo.addCell(cellTitulo);
			
			// logo derecha
			Chunk chunkLogoDer = new Chunk(imgLogoDer, 0, 0, true);
			Paragraph pLogoDer = new Paragraph();
			pLogoDer.add(chunkLogoDer);
			pLogoDer.setAlignment(Element.ALIGN_RIGHT);
			//pLogoDer.setIndentationLeft(95);
			PdfPCell cellLogoDer = new PdfPCell();
			cellLogoDer.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellLogoDer.setBorder(PdfPCell.NO_BORDER);
			cellLogoDer.addElement(pLogoDer);
			tablaCabeceraTitulo.addCell(cellLogoDer);

			//tablaCabeceraTitulo.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getTop() - 10, writer.getDirectContent());
		} catch (Exception e) {
			logger.error("Exporting pdf Informe Devolucion Fin Campaña: PdfInformeDevolucionFinCampanaHeaderFooterPageEvent.onStartPage tablaCabeceraTitulo");
			logger.error(StackTraceManager.getStackTrace(e));
            throw new ExceptionConverter(e);
		}
		PdfPCell cellCabeceraTitulo = new PdfPCell(tablaCabeceraTitulo);
		cellCabeceraTitulo.setBorder(PdfPCell.NO_BORDER);
		cellCabeceraTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaCabecera.addCell(cellCabeceraTitulo);
		
		// Tabla que contiene la fecha límite de devolución, observaciones, centro
		// localizador, abono, recogida, fecha y hora
		PdfPTable tablaCabeceraDatos;
		try {
			tablaCabeceraDatos = new PdfPTable(2);
			
			tablaCabeceraDatos.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tablaCabeceraDatos.setTotalWidth(pageSize.getWidth() - (marginLeft + marginRight));
			tablaCabeceraDatos.setWidthPercentage(100); // table width 100%
			float[] columnWidths = {TABLE_CABECERA_DATOS_COLUMN_1_WIDTH_PORCEN, TABLE_CABECERA_DATOS_COLUMN_2_WIDTH_PORCEN}; // column widths relative
			tablaCabeceraDatos.setWidths(columnWidths);

			String strFechaLimiteFormato = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.fecha.limite.formato", null, locale);
	        SimpleDateFormat formatoFechaLimite = new SimpleDateFormat(strFechaLimiteFormato);//Martes 4-ABR
	        if (fechaLimite == null){
	        	fechaLimite = new Date();
	        }
			String strFechaLimite = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.fecha.limite", null, locale);
	        //String strFechaLimiteFormateada = formatoFechaLimite.format(fechaHora).toUpperCase();
	        String strFechaDesdeFormateada = formatoFechaLimite.format(fechaDesde).toUpperCase();
			String strFechaHastaFormateada = formatoFechaLimite.format(fechaHasta).toUpperCase();
			Chunk chunkFechaLimite = new Chunk(strFechaLimite, this.fontCabeceraDatosLabel);
			Chunk chunkFechaDesdeFormateada = new Chunk(strFechaDesdeFormateada, this.fontCabeceraDatosText);
			Chunk chunkFechaHastaFormateada = new Chunk(strFechaHastaFormateada, this.fontCabeceraDatosText);
			Paragraph pFechaLimite = new Paragraph();
			pFechaLimite.add(chunkFechaLimite);
			pFechaLimite.add(chunkFechaDesdeFormateada);
			pFechaLimite.add(new Chunk(" / "));
			pFechaLimite.add(chunkFechaHastaFormateada);
			pFechaLimite.setAlignment(Element.ALIGN_LEFT);
			pFechaLimite.setIndentationLeft(2);
			
			// observaciones
			String strObservacionesLabel = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.observaciones", null, locale);
			Chunk chunkObservacionesLabel = new Chunk(strObservacionesLabel, this.fontCabeceraDatosLabel);
			Chunk chunkObservacionesText = new Chunk(observaciones, this.fontCabeceraDatosText);
			Paragraph pObservaciones = new Paragraph();
			pObservaciones.add(chunkObservacionesLabel);
			pObservaciones.add(chunkObservacionesText);
			pObservaciones.setAlignment(Element.ALIGN_LEFT);
			pObservaciones.setIndentationLeft(2);
			
			// centro
			String strCentroLabel = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.centro", null, locale);
			Chunk chunkCentroLabel = new Chunk(strCentroLabel, this.fontCabeceraDatosLabel);
			Chunk chunkCentroText = new Chunk(centro, this.fontCabeceraDatosText);
			Paragraph pCentro = new Paragraph();
			pCentro.add(chunkCentroLabel);
			pCentro.add(chunkCentroText);
			pCentro.setAlignment(Element.ALIGN_LEFT);
			pCentro.setIndentationLeft(2);

			// seccion
			String strSeccionLabel = messageSource.getMessage("pdf_devoluciones.finCampana.seccion", null, locale);
			Chunk chunkSeccionLabel = new Chunk(strSeccionLabel, this.fontCabeceraDatosLabel);
			Chunk chunkSeccionText = new Chunk(seccion, this.fontCabeceraDatosText);
			Paragraph pSeccion = new Paragraph();
			pSeccion.add(chunkSeccionLabel);
			pSeccion.add(chunkSeccionText);
			pSeccion.setAlignment(Element.ALIGN_LEFT);
			pSeccion.setIndentationLeft(2);
			
			// RMA
			Paragraph pRma = new Paragraph();
			if (rma!= null && !"".equals(rma)) {
				String strRmaLabel = messageSource.getMessage("pdf_devoluciones.finCampana.rma", null, locale);
				Chunk chunkRmaLabel = new Chunk(strRmaLabel, this.fontCabeceraDatosLabel);
				Chunk chunkRmaText =  new Chunk(rma, this.fontCabeceraDatosText);
				pRma.add(chunkRmaLabel);
				pRma.add(chunkRmaText);
				pRma.setAlignment(Element.ALIGN_LEFT);
				pRma.setIndentationLeft(2);
			}
			// celda datos izquierda
			PdfPCell cellDatosIzq = new PdfPCell();
			cellDatosIzq.addElement(pFechaLimite);
			cellDatosIzq.addElement(pObservaciones);
			cellDatosIzq.addElement(pCentro);
			cellDatosIzq.addElement(pSeccion);
			if (rma != null && !"".equals(rma))
				cellDatosIzq.addElement(pRma);
			cellDatosIzq.setBorder(PdfPCell.NO_BORDER);
			cellDatosIzq.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaCabeceraDatos.addCell(cellDatosIzq);
			
			
			// localizador
			// Para dibujar el rectangulo que rodea al localizador lo hago a través de una tabla.
			// La tabla tendrá el 100% del ancho posible y tendrá 2 columnas:
			//     La columna izquierda tendrá el texto del localizador y 
			//     un borde para dibujar el rectangulo. Además, el ancho
			//     de está columna habrá que calcularlo para que el
			//     rectangulo se ajuste al texto.
			//     La columna derecha estará vacía y sin borde.
			PdfPTable tablaLocalizador = new PdfPTable(2);
			tablaLocalizador.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaLocalizador.getDefaultCell().setFixedHeight(TABLE_FONT_CABECERA_DATOS_SIZE+2);
			tablaLocalizador.setWidthPercentage(100); // table width 100%

			String strLocalizadorLabel = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.localizador", null, locale);
			Chunk chunkLocalizadorLabel = new Chunk(strLocalizadorLabel, this.fontCabeceraDatosLabel);
			Chunk chunkLocalizadorText = new Chunk(localizador, this.fontCabeceraDatosText);
			Paragraph pLocalizador = new Paragraph();
			pLocalizador.add(chunkLocalizadorLabel);
			pLocalizador.add(chunkLocalizadorText);
			pLocalizador.setAlignment(Element.ALIGN_LEFT);
			
			//Calculo los porcentajes de las columnas izquierda y derecha
	        float totalLocalizadorPoints = chunkLocalizadorLabel.getWidthPoint() + chunkLocalizadorText.getWidthPoint();
	        float totalLocalizadorRowWidth = tablaCabeceraDatos.getTotalWidth()*(TABLE_CABECERA_DATOS_COLUMN_2_WIDTH_PORCEN/100);
	        float columnaLocalizadorIzq = totalLocalizadorPoints + 10;
	        float columnaLocalizadorDer = totalLocalizadorRowWidth - columnaLocalizadorIzq;
			float[] tablaLocalizadorColumnWidths = {columnaLocalizadorIzq, columnaLocalizadorDer}; // column widths relative
			tablaLocalizador.setWidths(tablaLocalizadorColumnWidths);
			
			tablaLocalizador.addCell(pLocalizador);
	        PdfPCell cellLocalizadorVacio = new PdfPCell();
	        cellLocalizadorVacio.setBorder(PdfPCell.NO_BORDER);
			tablaLocalizador.addCell(cellLocalizadorVacio);
			
			// abono
			String strAbono = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.abono", null, locale);
			Chunk chunkAbonoLabel = new Chunk(strAbono, this.fontCabeceraDatosLabel);
			Chunk chunkAbonoText = new Chunk(abono, this.fontCabeceraDatosText);
			Paragraph pAbono = new Paragraph();
			pAbono.add(chunkAbonoLabel);
			pAbono.add(chunkAbonoText);
			pAbono.setAlignment(Element.ALIGN_LEFT);
			pAbono.setIndentationLeft(2);
			
			// recogida
			String strRecogidaLabel = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.recogida", null, locale);
			Chunk chunkRecogidaLabel = new Chunk(strRecogidaLabel, this.fontCabeceraDatosLabel);
			Chunk chunkRecogidaText = new Chunk(recogida, this.fontCabeceraDatosText);
			Paragraph pRecogida = new Paragraph();
			pRecogida.add(chunkRecogidaLabel);
			pRecogida.add(chunkRecogidaText);
			pRecogida.setAlignment(Element.ALIGN_LEFT);
			pRecogida.setIndentationLeft(2);
			
			// fecha
			String strFechaFormato = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.fecha.formato", null, locale);
	        SimpleDateFormat formatoFecha = new SimpleDateFormat(strFechaFormato);//6-10-2016
	        if (fechaHora == null){
		        fechaHora = new Date();
	        }
	        String strFechaFormateada = formatoFecha.format(fechaHora).toUpperCase();
			String strFecha = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.fecha", null, locale);
			Chunk chunkFechaLabel = new Chunk(strFecha, this.fontCabeceraDatosLabel);
			Chunk chunkFechaText = new Chunk(strFechaFormateada, this.fontCabeceraDatosText);
			Paragraph pFecha = new Paragraph();
			pFecha.add(chunkFechaLabel);
			pFecha.add(chunkFechaText);
			pFecha.setAlignment(Element.ALIGN_LEFT);
			pFecha.setIndentationLeft(2);
			
			// hora
			String strHoraFormato = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.hora.formato", null, locale);
	        SimpleDateFormat formatoHora = new SimpleDateFormat(strHoraFormato);//6-10-2016
	        if (fechaHora == null){
		        fechaHora = new Date();
	        }
	        String strHoraFormateada = formatoHora.format(fechaHora);
			String strHora = messageSource.getMessage("pdf_devoluciones.finCampana.cabecera.hora", null, locale);
			Chunk chunkHoraLabel = new Chunk(strHora, this.fontCabeceraDatosLabel);
			Chunk chunkHoraText = new Chunk(strHoraFormateada, this.fontCabeceraDatosText);
			Paragraph pHora = new Paragraph();
			pHora.add(chunkHoraLabel);
			pHora.add(chunkHoraText);
			pHora.setAlignment(Element.ALIGN_LEFT);
			pHora.setIndentationLeft(2);
			
			// celda datos derecha
			PdfPCell cellDatosDer = new PdfPCell();
			cellDatosDer.addElement(tablaLocalizador);
			cellDatosDer.addElement(pAbono);
			cellDatosDer.addElement(pRecogida);
			cellDatosDer.addElement(pFecha);
			cellDatosDer.addElement(pHora);
			cellDatosDer.setBorder(PdfPCell.NO_BORDER);
			cellDatosDer.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaCabeceraDatos.addCell(cellDatosDer);

			//tablaCabeceraDatos.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getTop() - 10, writer.getDirectContent());
		} catch (Exception e) {
			logger.error("Exporting pdf Informe Devolucion Fin Campaña: PdfInformeDevolucionFinCampanaHeaderFooterPageEvent.onStartPage tablaCabeceraDatos");
			logger.error(StackTraceManager.getStackTrace(e));
            throw new ExceptionConverter(e);
		}
		PdfPCell cellCabeceraDatos = new PdfPCell(tablaCabeceraDatos);
		cellCabeceraDatos.setBorder(PdfPCell.NO_BORDER);
		cellCabeceraDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaCabecera.addCell(cellCabeceraDatos);
		tablaCabecera.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getTop() - 10, writer.getDirectContent());
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
    	
    	// Añado el pie de página.
    	// En este pie de página, además de añadir 
    	// el número de página correspondiente,  añado un template 
    	// para que cuando se cierre el documento 
    	// (que es cuando salta el evento onCloseDocument)
    	// escriba el número total de páginas
    	// en cada una de las páginas.
    	// Esto se hace así por que el número total de páginas
    	// sólo lo conozco cuando se cierra el documento.
    	
        PdfPTable table = new PdfPTable(2);
        try {
    		Locale locale = LocaleContextHolder.getLocale();
    		List<Object> args = new ArrayList<Object>();
    		args.add(document.getPageNumber()+1);
            Rectangle pageSize = document.getPageSize();
    		String strPagina = messageSource.getMessage("pdf_pescaMostrador.pie.pagina", args.toArray(), locale);
    		
            table.setWidths(new int[]{92, 8});
            table.setTotalWidth(pageSize.getWidth() - (marginLeft + marginRight));
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(strPagina);
            PdfPCell cell = new PdfPCell(Image.getInstance(template));
            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);
            table.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getBottom(marginBottom) + 5, writer.getDirectContent());
        }
        catch(DocumentException e) {
			logger.error("Exporting pdf Informe Devolucion Fin Campaña: PdfInformeDevolucionFinCampanaHeaderFooterPageEvent.onEndPage");
			logger.error(StackTraceManager.getStackTrace(e));
            throw new ExceptionConverter(e);
        }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        super.onCloseDocument(writer, document);

    	// Añado el número total de páginas.
        // Este número total de páginas, que sólo lo conozco cuando 
        // se cierra el documento, se escribe en el template que
        // añadimos anteriormente a todas las páginas en el evento onEndPage.

        try {
            ColumnText.showTextAligned(template, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber())),
                    2, 2, 0);        
        }
        catch(Exception e) {
			logger.error("Exporting pdf Informe Devolucion Fin Campaña: PdfInformeDevolucionFinCampanaHeaderFooterPageEvent.onCloseDocument");
			logger.error(StackTraceManager.getStackTrace(e));
            throw new ExceptionConverter(e);
        }
    }
    
    public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Image getImgLogoIzq() {
		return imgLogoIzq;
	}

	public void setImgLogoIzq(Image imgLogoIzq) {
		this.imgLogoIzq = imgLogoIzq;
	}

	public Image getImgLogoDer() {
		return imgLogoDer;
	}

	public void setImgLogoDer(Image imgLogoDer) {
		this.imgLogoDer = imgLogoDer;
	}

	public String getNombreCampana() {
		return nombreCampana;
	}

	public void setNombreCampana(String nombreCampana) {
		this.nombreCampana = nombreCampana;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	
	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}
	
	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getRma() {
		return rma;
	}

	public void setRma(String rma) {
		this.rma = rma;
	}

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	public String getAbono() {
		return abono;
	}

	public void setAbono(String abono) {
		this.abono = abono;
	}

	public String getRecogida() {
		return recogida;
	}

	public void setRecogida(String recogida) {
		this.recogida = recogida;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public float getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}

	public float getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(float marginRight) {
		this.marginRight = marginRight;
	}

	public float getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(float marginTop) {
		this.marginTop = marginTop;
	}

	public float getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}

}