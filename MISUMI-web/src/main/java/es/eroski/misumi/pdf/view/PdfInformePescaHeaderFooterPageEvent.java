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

@Service(value = "servicePdfInformePescaHeaderFooterPageEvent")
public class PdfInformePescaHeaderFooterPageEvent extends PdfPageEventHelper {

	private static Logger logger = Logger.getLogger(WelcomeController.class);
	
	private static float TABLE_FONT_PIE_ASTERISCO_SIZE = 8;
	private static float TABLE_FONT_PIE_PAGINA_SIZE = 12;
	  
	private MessageSource messageSource;
	
    private Image imgLogoIzq;
    private Image imgLogoDer;
    private String tiendaNombre;
    private String descSubcategoria;
    private Date fechaHora;
    private boolean piePedirHoyOK;
    private String habitual;
    
    private PdfTemplate template;
	
	private float marginLeft = 1;
	private float marginRight = 1;
	private float marginTop = 1;
	private float marginBottom = 1;
	
	private Font fuentePieAsterisco = new Font();
	private Font fuentePieAsteriscoError = new Font();
	private Font fuentePiePagina = new Font();
	
	public void init(PdfWriter writer, float marginLeft, float marginRight, float marginTop, float marginBottom){
		
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		
		// Tenemos que iniciar el template cada vez
		// que escribimos un nuevo documento.
		
		if (template != null){
			// Si no es null reseteamos el template
			template.reset();
		}
		template = writer.getDirectContent().createTemplate(30, 16);
		
		fuentePieAsterisco.setSize(TABLE_FONT_PIE_ASTERISCO_SIZE);
		fuentePieAsterisco.setColor(Color.BLACK);
		fuentePieAsteriscoError.setSize(TABLE_FONT_PIE_ASTERISCO_SIZE);
		fuentePieAsteriscoError.setColor(Color.RED);
		fuentePiePagina.setSize(TABLE_FONT_PIE_PAGINA_SIZE);
		//fuentePiePagina.setStyle(Font.BOLD);
	}

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		
		List<Object> args = new ArrayList<Object>();
		
		Locale locale = LocaleContextHolder.getLocale();
		
        Rectangle pageSize = writer.getPageSize();
		
		// Tabla que contiene el logo, la tienda, el titulo, la hora y la fecha
		PdfPTable tablaLogoTiendaFecha;
		try {
			tablaLogoTiendaFecha = new PdfPTable(3);
			
			tablaLogoTiendaFecha.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tablaLogoTiendaFecha.setTotalWidth(pageSize.getWidth() - (marginLeft + marginRight));
			tablaLogoTiendaFecha.setWidthPercentage(100); // table width 100%
			float[] columnWidths = {60, 100, 60}; // column widths relative
			tablaLogoTiendaFecha.setWidths(columnWidths);

			// logo
			Chunk chunkLogoIzq = new Chunk(imgLogoIzq, 0, 0, true);
			Paragraph pLogoIzq = new Paragraph();
			pLogoIzq.add(chunkLogoIzq);
			PdfPCell cellLogoIzq = new PdfPCell();
			cellLogoIzq.setBorder(PdfPCell.NO_BORDER);
			cellLogoIzq.addElement(pLogoIzq);
			tablaLogoTiendaFecha.addCell(cellLogoIzq);
			
			// tienda y título
			args.clear();
			args.add(tiendaNombre);
			String strTienda = messageSource.getMessage("pdf_pescaMostrador.tienda", args.toArray(), locale);
			Chunk chunkTienda = new Chunk(strTienda);
			String strTitulo = messageSource.getMessage("pdf_pescaMostrador.titulo", null, locale);
			Chunk chunkTitulo = new Chunk(strTitulo);
			chunkTitulo.setUnderline(0.5F, -2.0F);
			Chunk chunkHabitual = new Chunk(habitual);
			chunkHabitual.setUnderline(0.5F, -2.0F);
			Paragraph pTienda = new Paragraph();
			Paragraph pTitulo = new Paragraph();
			Paragraph pHabitual = new Paragraph();
			pTienda.add(chunkTienda);
			pTitulo.add(chunkTitulo);
			pHabitual.add(chunkHabitual);
			pTienda.setAlignment(Element.ALIGN_CENTER);
			pTitulo.setAlignment(Element.ALIGN_CENTER);
			pHabitual.setAlignment(Element.ALIGN_CENTER);
			pHabitual.setSpacingBefore(20);
			PdfPCell cellTitulo = new PdfPCell();
			cellTitulo.addElement(pTienda);
			cellTitulo.addElement(pTitulo);
			cellTitulo.addElement(pHabitual);
			cellTitulo.setBorder(PdfPCell.NO_BORDER);
			cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaLogoTiendaFecha.addCell(cellTitulo);
			
			// fecha y hora
			Chunk chunkLogoDer = new Chunk(imgLogoDer, 0, 0, true);
			String strFechaFormato = messageSource.getMessage("pdf_pescaMostrador.fecha.formato", null, locale);
	        SimpleDateFormat formatoFecha = new SimpleDateFormat(strFechaFormato);//6-10-2016
			String strHoraFormato = messageSource.getMessage("pdf_pescaMostrador.hora.formato", null, locale);
	        SimpleDateFormat formatoHora = new SimpleDateFormat(strHoraFormato);//6-10-2016
	        if (fechaHora == null){
		        fechaHora = new Date();
	        }
	        String strFechaActual = formatoFecha.format(fechaHora);
	        String strHoraActual = formatoHora.format(fechaHora);
			args.clear();
			args.add(strFechaActual);
			String strFecha = messageSource.getMessage("pdf_pescaMostrador.fecha", args.toArray(), locale);
			Chunk chunkFecha = new Chunk(strFecha);
			args.clear();
			args.add(strHoraActual);
			String strHora = messageSource.getMessage("pdf_pescaMostrador.hora", args.toArray(), locale);
			Chunk chunkHora = new Chunk(strHora);
			Paragraph pLogoDer = new Paragraph();
			Paragraph pFecha = new Paragraph();
			Paragraph pHora = new Paragraph();
			pLogoDer.add(chunkLogoDer);
			pFecha.add(chunkFecha);
			pHora.add(chunkHora);
			pLogoDer.setAlignment(Element.ALIGN_LEFT);
			pLogoDer.setIndentationLeft(85);
			pFecha.setAlignment(Element.ALIGN_LEFT);
			pFecha.setIndentationLeft(100);
			pHora.setAlignment(Element.ALIGN_LEFT);
			pHora.setIndentationLeft(100);
			PdfPCell cellLogoFechaHora = new PdfPCell();
			cellLogoFechaHora.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellLogoFechaHora.setBorder(PdfPCell.NO_BORDER);
			cellLogoFechaHora.addElement(pLogoDer);
			cellLogoFechaHora.addElement(pFecha);
			cellLogoFechaHora.addElement(pHora);
			tablaLogoTiendaFecha.addCell(cellLogoFechaHora);

			tablaLogoTiendaFecha.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getTop() - 10, writer.getDirectContent());
		} catch (Exception e) {
			logger.error("Exporting pdf Informe Pesca Mostrador: PdfInformePescaHeaderFooterPageEvent.onStartPage");
			logger.error(StackTraceManager.getStackTrace(e));
            throw new ExceptionConverter(e);
		}
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
            Font fontPagina = new Font();
            fontPagina.setColor(Color.BLACK);
			Chunk chunkPagina = new Chunk(strPagina, fuentePiePagina);
			Phrase phrasePagina = new Phrase(strPagina, fuentePiePagina);
			Chunk chunkImage = new Chunk(Image.getInstance(template),0,0);
			Phrase pImage = new Phrase(chunkImage);
    		
    		float anchoPie = pageSize.getWidth() - (marginLeft + marginRight);
    		int anchoTextoPagina = (int) (ColumnText.getWidth(phrasePagina) + ColumnText.getWidth(pImage) + 10);
    		int anchoTextoPie = (int) (anchoPie - anchoTextoPagina);
            table.setWidths(new int[]{anchoTextoPie, anchoTextoPagina});
            table.setTotalWidth(anchoPie);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(16);
            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            if (this.piePedirHoyOK){
    			String strPedirHoy = messageSource.getMessage("pdf_pescaMostrador.pie.pedir.hoy.asterisco", null, locale);
    			Chunk chunkPedirHoy = new Chunk(strPedirHoy, fuentePieAsterisco);
    			Paragraph pTituloPedirHoy = new Paragraph();
    			pTituloPedirHoy.add(chunkPedirHoy);
    			pTituloPedirHoy.setAlignment(Element.ALIGN_LEFT);
    			PdfPCell cellPedirHoy = new PdfPCell();
    			cellPedirHoy.addElement(pTituloPedirHoy);
    			cellPedirHoy.setBorder(PdfPCell.NO_BORDER);
    			cellPedirHoy.setHorizontalAlignment(Element.ALIGN_LEFT);
    			table.addCell(cellPedirHoy);
            }
            else{
    			String strPedirHoyError = messageSource.getMessage("pdf_pescaMostrador.pie.pedir.hoy.error", null, locale);
    			Chunk chunkPedirHoyError = new Chunk(strPedirHoyError, fuentePieAsteriscoError);
    			Paragraph pTituloPedirHoyError = new Paragraph();
    			pTituloPedirHoyError.add(chunkPedirHoyError);
    			pTituloPedirHoyError.setAlignment(Element.ALIGN_LEFT);
    			PdfPCell cellPedirHoyError = new PdfPCell();
    			cellPedirHoyError.addElement(pTituloPedirHoyError);
    			cellPedirHoyError.setBorder(PdfPCell.NO_BORDER);
    			cellPedirHoyError.setHorizontalAlignment(Element.ALIGN_LEFT);
    			table.addCell(cellPedirHoyError);
            }
			Paragraph pTituloPagina = new Paragraph();
			pTituloPagina.add(chunkPagina);
			pTituloPagina.setAlignment(Element.ALIGN_LEFT);
			pTituloPagina.add(pImage);
			PdfPCell cellPagina = new PdfPCell();
			cellPagina.addElement(pTituloPagina);
			cellPagina.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cellPagina);
            table.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getBottom(marginBottom), writer.getDirectContent());
        }
        catch(DocumentException e) {
			logger.error("Exporting pdf Informe Pesca Mostrador: PdfInformePescaHeaderFooterPageEvent.onEndPage");
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
                    new Phrase(String.valueOf(writer.getPageNumber()),fuentePiePagina),
                    0, 0, 0);        
        }
        catch(Exception e) {
			logger.error("Exporting pdf Informe Pesca Mostrador: PdfInformePescaHeaderFooterPageEvent.onCloseDocument");
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

	public String getTiendaNombre() {
		return tiendaNombre;
	}

	public void setTiendaNombre(String tiendaNombre) {
		this.tiendaNombre = tiendaNombre;
	}

	public String getDescSubcategoria() {
		return descSubcategoria;
	}

	public void setDescSubcategoria(String descSubcategoria) {
		this.descSubcategoria = descSubcategoria;
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

	public boolean isPiePedirHoyOK() {
		return piePedirHoyOK;
	}

	public void setPiePedirHoyOK(boolean piePedirHoyOK) {
		this.piePedirHoyOK = piePedirHoyOK;
	}

	public String getHabitual() {
		return habitual;
	}

	public void setHabitual(String habitual) {
		this.habitual = habitual;
	}

}