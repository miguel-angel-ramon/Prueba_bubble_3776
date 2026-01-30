package es.eroski.misumi.pdf.view;

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
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import es.eroski.misumi.control.WelcomeController;
import es.eroski.misumi.util.StackTraceManager;

@Service(value = "headerFooterPageEvent")
public class HeaderFooterPageEvent extends PdfPageEventHelper {

	private static Logger logger = Logger.getLogger(WelcomeController.class);
	
	private MessageSource messageSource;
	
    private BaseFont font;
    private float fontSize = 12;
    
    private Image imgLogo;
    private String tiendaNombre;
    private String descSubcategoria;
    
    private PdfTemplate template;
	
	private float marginLeft = 1;
	private float marginRight = 1;
	private float marginTop = 1;
	private float marginBottom = 1;
	
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
		template = writer.getDirectContent().createTemplate(50, 50);
		if (font == null){
			try {
				font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			} catch (Exception e) {
				logger.error(StackTraceManager.getStackTrace(e));
			}
		}
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
			Chunk chunkLogo = new Chunk(imgLogo, 0, 0, true);
			Paragraph pLogo = new Paragraph();
			pLogo.add(chunkLogo);
			PdfPCell cellLogo = new PdfPCell();
			cellLogo.setBorder(PdfPCell.NO_BORDER);
			cellLogo.addElement(pLogo);
			tablaLogoTiendaFecha.addCell(cellLogo);
			
			// tienda y título
			args.clear();
			args.add(tiendaNombre);
			String strTienda = messageSource.getMessage("pdf_pescaMostrador.tienda", args.toArray(), locale);
			Chunk chunkTienda = new Chunk(strTienda);
			String strTitulo = messageSource.getMessage("pdf_pescaMostrador.titulo", null, locale);
			Chunk chunkTitulo = new Chunk(strTitulo);
			chunkTitulo.setUnderline((float) 0.5, -2);
			Paragraph pTienda = new Paragraph();
			Paragraph pTitulo = new Paragraph();
			pTienda.add(chunkTienda);
			pTitulo.add(chunkTitulo);
			pTienda.setAlignment(Element.ALIGN_CENTER);
			pTitulo.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellTitulo = new PdfPCell();
			cellTitulo.addElement(pTienda);
			cellTitulo.addElement(pTitulo);
			cellTitulo.setBorder(PdfPCell.NO_BORDER);
			cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaLogoTiendaFecha.addCell(cellTitulo);
			
			// fecha y hora
			String strFechaFormato = messageSource.getMessage("pdf_pescaMostrador.fecha.formato", null, locale);
	        SimpleDateFormat formatoFecha = new SimpleDateFormat(strFechaFormato);//6-10-2016
			String strHoraFormato = messageSource.getMessage("pdf_pescaMostrador.hora.formato", null, locale);
	        SimpleDateFormat formatoHora = new SimpleDateFormat(strHoraFormato);//6-10-2016
	        Date now = new Date();
	        String strFechaActual = formatoFecha.format(now);
	        String strHoraActual = formatoHora.format(now);
			args.clear();
			args.add(strFechaActual);
			String strFecha = messageSource.getMessage("pdf_pescaMostrador.fecha", args.toArray(), locale);
			Chunk chunkFecha = new Chunk(strFecha);
			args.clear();
			args.add(strHoraActual);
			String strHora = messageSource.getMessage("pdf_pescaMostrador.hora", args.toArray(), locale);
			Chunk chunkHora = new Chunk(strHora);
			Paragraph pFecha = new Paragraph();
			Paragraph pHora = new Paragraph();
			pFecha.add(chunkFecha);
			pHora.add(chunkHora);
			pFecha.setAlignment(Element.ALIGN_LEFT);
			pFecha.setIndentationLeft(100);
			pHora.setAlignment(Element.ALIGN_LEFT);
			pHora.setIndentationLeft(100);
			PdfPCell cellFechaHora = new PdfPCell();
			cellFechaHora.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellFechaHora.setBorder(PdfPCell.NO_BORDER);
			cellFechaHora.addElement(pFecha);
			cellFechaHora.addElement(pHora);
			tablaLogoTiendaFecha.addCell(cellFechaHora);

			// añado una fila vacia en la tabla 
			// para separar la subcategoria
			PdfPCell cellBlank = new PdfPCell(new Phrase(" "));
			cellBlank.setBorder(PdfPCell.NO_BORDER);
			cellBlank.setColspan(3);
			tablaLogoTiendaFecha.addCell(cellBlank);
			
			// añado la subcategoria
			Chunk chunkSubcategoria = new Chunk(descSubcategoria);
			chunkSubcategoria.setUnderline((float) 0.5, -2);
			Paragraph pSubcategoria = new Paragraph();
			pSubcategoria.add(chunkSubcategoria);
			pSubcategoria.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cellDescSubcategoria = new PdfPCell(pSubcategoria);
			cellDescSubcategoria.setBorder(PdfPCell.NO_BORDER);
			cellDescSubcategoria.setColspan(3);
			tablaLogoTiendaFecha.addCell(cellDescSubcategoria);
			
			tablaLogoTiendaFecha.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getTop(marginTop) + 90, writer.getDirectContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
    	
    	// Añado el pie de página.
    	// En este pie de página, además de añadir 
    	// el número de página correspondiente,  añado un template 
    	// para que cuando se cierre el documento 
    	// (que es cuando salta el evento onCloseDocument)
    	// escriba el número total de páginas.
    	// Esto se hace así por que el número total de páginas
    	// sólo lo conozco cuando se cierra el documento.
    	
        PdfContentByte cb = writer.getDirectContent();
        cb.beginText();
        try {
    		Locale locale = LocaleContextHolder.getLocale();
    		List<Object> args = new ArrayList<Object>();
    		args.add(document.getPageNumber()+1);
    		// Buscamos el texto a poner, 
    		// pasandole como argumento el número de página correspondiente.
    		String strPagina = messageSource.getMessage("pdf_pescaMostrador.pie.pagina", args.toArray(), locale);
            Rectangle pageSize = document.getPageSize();
            cb.setFontAndSize(font, fontSize);
            cb.setTextMatrix(pageSize.getLeft(710), pageSize.getBottom(30));
            cb.showText(strPagina);
            // Añadimos el template.
            // Para ello necesitamos saber en que posición ponerlo.
            // Como lo vamos a poner al final del texto "Pagina N de ", 
            // la posición x será la posición en la que ponemos 
            // el texto + la longitud del texto (que depende del font usado).
            cb.addTemplate(template, pageSize.getLeft(710) + font.getWidthPoint(strPagina, fontSize), pageSize.getBottom(30));
        } catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
        }
        cb.endText();
        
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        super.onCloseDocument(writer, document);

    	// Añado el número total de páginas.
        // Este número total de páginas, que sólo lo conozco cuando 
        // se cierra el documento, se escribe en el template que
        // añadimos anteriormente a todas las páginas en el evento onEndPage.
        
        template.beginText();
        try {
            template.setFontAndSize(font, fontSize);
            template.setTextMatrix(0f, 0f);
            template.showText("" + writer.getPageNumber());
        } catch (Exception e) {
            logger.error(e);
        }
        template.endText();
    }
    
    public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Image getImgLogo() {
		return imgLogo;
	}

	public void setImgLogo(Image imgLogo) {
		this.imgLogo = imgLogo;
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