package es.eroski.misumi.pdf.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

@Service(value = "servicePdfEntradasDescentralizadasHeaderFooterPageEvent")
public class PdfInformeEntradasDescentralizadasHeaderFooterPageEvent extends PdfPageEventHelper {

	private static Logger logger = Logger.getLogger(WelcomeController.class);
	
	private static float TABLE_FONT_CABECERA_TITULO_SIZE = 12;
	private static float TABLE_FONT_CABECERA_DATOS_SIZE = 10;
	
	private static float TABLE_CABECERA_TITULO_COLUMN_1_WIDTH_PORCEN = 20;
	private static float TABLE_CABECERA_TITULO_COLUMN_2_WIDTH_PORCEN = 60;
	private static float TABLE_CABECERA_TITULO_COLUMN_3_WIDTH_PORCEN = 20;
	private static float TABLE_CABECERA_DATOS_COLUMN_1_WIDTH_PORCEN = 50;
	private static float TABLE_CABECERA_DATOS_COLUMN_2_WIDTH_PORCEN = 50;

	private MessageSource messageSource;
	
    private Image imgLogoIzq;
    private Image imgLogoDer;

    
    private String fechaPedido;
    private Long numeroPedido;
    private String proveedor; //proveedor  gen - proveedor trabajo - denom
    private String fechaTarifa;
    private String albaran;
    private Long numIncidencia;
    
    
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
			logger.error("Exporting pdf Informe Entrada Descentralizada: PdfInformeEntradaDescentralizadaHeaderFooterPageEvent.onStartPage tablaCabecera");
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

			String strTituloEntradaDescentralizada = messageSource.getMessage("pdf_entradas.entrada.cabecera.titulo", null, locale);
			Chunk chunkTituloEntrada = new Chunk(strTituloEntradaDescentralizada, this.fontCabeceraTitulo);
			Paragraph pTituloEntrada = new Paragraph();
			pTituloEntrada.add(chunkTituloEntrada);
			pTituloEntrada.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellTitulo = new PdfPCell();
			cellTitulo.addElement(pTituloEntrada);
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
			logger.error("Exporting pdf Informe Devolucion Orden Recogida: PdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.onStartPage tablaCabeceraTitulo");
			logger.error(StackTraceManager.getStackTrace(e));
            throw new ExceptionConverter(e);
		}
		PdfPCell cellCabeceraTitulo = new PdfPCell(tablaCabeceraTitulo);
		cellCabeceraTitulo.setBorder(PdfPCell.NO_BORDER);
		cellCabeceraTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaCabecera.addCell(cellCabeceraTitulo);
		
		// Tabla que contiene:
		// Número de Pedido, Fecha Pedido, Proveedor
		//la fecha límite de devolución, centro, localizador, recogida, fecha y hora
		PdfPTable tablaCabeceraDatos;
		try {
			tablaCabeceraDatos = new PdfPTable(2);
			
			tablaCabeceraDatos.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tablaCabeceraDatos.setTotalWidth(pageSize.getWidth() - (marginLeft + marginRight));
			tablaCabeceraDatos.setWidthPercentage(100); // table width 100%
			float[] columnWidths = {TABLE_CABECERA_DATOS_COLUMN_1_WIDTH_PORCEN, TABLE_CABECERA_DATOS_COLUMN_2_WIDTH_PORCEN}; // column widths relative
			tablaCabeceraDatos.setWidths(columnWidths);
		
			//Numero de pedido:
			String strNumeroPedido = messageSource.getMessage("pdf_entradas.entrada.cabecera.numeroPedido", null, locale);
			Chunk chunkNumeroPedidoLabel = new Chunk(strNumeroPedido, this.fontCabeceraDatosLabel);
			Chunk chunkNumeroPedidoText = new Chunk(numeroPedido.toString(), this.fontCabeceraDatosText);
			Paragraph pNumeroPedido = new Paragraph();
			pNumeroPedido.add(chunkNumeroPedidoLabel);
			pNumeroPedido.add(chunkNumeroPedidoText);
			pNumeroPedido.setAlignment(Element.ALIGN_LEFT);
			pNumeroPedido.setIndentationLeft(2);

			//-------------------------------------
			//Fecha de Tarifa
			String strFechaTarifa = messageSource.getMessage("pdf_entradas.entrada.cabecera.fecha.tarifa", null, locale);
			if (fechaTarifa == null){
	        	fechaTarifa = "";
	        }
			Chunk chunkFechaTarifaLabel = new Chunk(strFechaTarifa, this.fontCabeceraDatosLabel);	
			Chunk chunkFechaTarifaText = new Chunk(fechaTarifa, this.fontCabeceraDatosText);
			Paragraph pFechaTarifa = new Paragraph();
			pFechaTarifa.add(chunkFechaTarifaLabel);
			pFechaTarifa.add(chunkFechaTarifaText);
			pFechaTarifa.setAlignment(Element.ALIGN_LEFT);
			pFechaTarifa.setIndentationLeft(2);
			
			//Fecha de Pedido
			String strFechaPedido = messageSource.getMessage("pdf_entradas.entrada.cabecera.fecha.pedido", null, locale);
			if (fechaPedido == null){
	        	fechaPedido = "";
	        }
			
			Chunk chunkFechaPedidoLabel = new Chunk(strFechaPedido, this.fontCabeceraDatosLabel);	
			Chunk chunkFechaPedidoText = new Chunk(fechaPedido, this.fontCabeceraDatosText);
			Paragraph pFechaPedido = new Paragraph();
			pFechaPedido.add(chunkFechaPedidoLabel);
			pFechaPedido.add(chunkFechaPedidoText);
			pFechaPedido.setAlignment(Element.ALIGN_LEFT);
			pFechaPedido.setIndentationLeft(2);
	        
			// Proveedor
			String strProveedor = messageSource.getMessage("pdf_entradas.entrada.cabecera.proveedor", null, locale);
			Chunk chunkProveedorLabel = new Chunk(strProveedor, this.fontCabeceraDatosLabel);
			Chunk chunkProveedorText = new Chunk(proveedor, this.fontCabeceraDatosText);
			Paragraph pProveedor = new Paragraph();
			pProveedor.add(chunkProveedorLabel);
			pProveedor.add(chunkProveedorText);
			pProveedor.setAlignment(Element.ALIGN_LEFT);
			pProveedor.setIndentationLeft(2);
			
			// Albaran
			String strAlbaran = messageSource.getMessage("pdf_entradas.entrada.cabecera.albaran", null, locale);
			Chunk chunkAlbaranLabel = new Chunk(strAlbaran, this.fontCabeceraDatosLabel);
			
			String albaranTxt = albaran != null ? albaran.toString() : "";
			Chunk chunkAlbaranText = new Chunk(albaranTxt, this.fontCabeceraDatosText);
			
			Paragraph pAlbaran = new Paragraph();
			pAlbaran.add(chunkAlbaranLabel);
			pAlbaran.add(chunkAlbaranText);
			pAlbaran.setAlignment(Element.ALIGN_LEFT);
			pAlbaran.setIndentationLeft(2);
			
			// Numero Incidencia
			String strIncidencia = messageSource.getMessage("pdf_entradas.entrada.cabecera.numeroIncidencia", null, locale);
			Chunk chunkIncidenciaLabel = new Chunk(strIncidencia, this.fontCabeceraDatosLabel);
			
			String numIncidenciaTxt = numIncidencia != null ? numIncidencia.toString() : "";
			Chunk chunkIncidenciaText = new Chunk(numIncidenciaTxt, this.fontCabeceraDatosText);
			
			Paragraph pNumIncidencia = new Paragraph();
			pNumIncidencia.add(chunkIncidenciaLabel);
			pNumIncidencia.add(chunkIncidenciaText);
			pNumIncidencia.setAlignment(Element.ALIGN_LEFT);
			pNumIncidencia.setIndentationLeft(2);
						
			// celda datos izquierda
			PdfPCell cellDatosIzq = new PdfPCell();
			cellDatosIzq.addElement(pNumeroPedido);
			cellDatosIzq.addElement(pProveedor);
			cellDatosIzq.addElement(pAlbaran);
			cellDatosIzq.setBorder(PdfPCell.NO_BORDER);
			cellDatosIzq.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaCabeceraDatos.addCell(cellDatosIzq);
			
			// celda datos derecha
			PdfPCell cellDatosDer = new PdfPCell();
			cellDatosDer.addElement(pFechaTarifa);
			cellDatosDer.addElement(pFechaPedido);
			cellDatosDer.addElement(pNumIncidencia);

			cellDatosDer.setBorder(PdfPCell.NO_BORDER);
			cellDatosDer.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaCabeceraDatos.addCell(cellDatosDer);
			
//			// localizador
//			// Para dibujar el rectangulo que rodea al localizador lo hago a través de una tabla.
//			// La tabla tendrá el 100% del ancho posible y tendrá 2 columnas:
//			//     La columna izquierda tendrá el texto del localizador y 
//			//     un borde para dibujar el rectangulo. Además, el ancho
//			//     de está columna habrá que calcularlo para que el
//			//     rectangulo se ajuste al texto.
//			//     La columna derecha estará vacía y sin borde.
//			PdfPTable tablaLocalizador = new PdfPTable(2);
//			tablaLocalizador.setHorizontalAlignment(Element.ALIGN_LEFT);
//			tablaLocalizador.getDefaultCell().setFixedHeight(TABLE_FONT_CABECERA_DATOS_SIZE+2);
//			tablaLocalizador.setWidthPercentage(100); // table width 100%
//
//			String strLocalizadorLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.localizador", null, locale);
//			Chunk chunkLocalizadorLabel = new Chunk(strLocalizadorLabel, this.fontCabeceraDatosLabel);
//			Chunk chunkLocalizadorText = new Chunk(localizador, this.fontCabeceraDatosText);
//			Paragraph pLocalizador = new Paragraph();
//			pLocalizador.add(chunkLocalizadorLabel);
//			pLocalizador.add(chunkLocalizadorText);
//			pLocalizador.setAlignment(Element.ALIGN_LEFT);
//			
//			//Calculo los porcentajes de las columnas izquierda y derecha
//	        float totalLocalizadorPoints = chunkLocalizadorLabel.getWidthPoint() + chunkLocalizadorText.getWidthPoint();
//	        float totalLocalizadorRowWidth = tablaCabeceraDatos.getTotalWidth()*(TABLE_CABECERA_DATOS_COLUMN_2_WIDTH_PORCEN/100);
//	        float columnaLocalizadorIzq = totalLocalizadorPoints + 10;
//	        float columnaLocalizadorDer = totalLocalizadorRowWidth - columnaLocalizadorIzq;
//			float[] tablaLocalizadorColumnWidths = {columnaLocalizadorIzq, columnaLocalizadorDer}; // column widths relative
//			tablaLocalizador.setWidths(tablaLocalizadorColumnWidths);
//			
//			tablaLocalizador.addCell(pLocalizador);
//	        PdfPCell cellLocalizadorVacio = new PdfPCell();
//	        cellLocalizadorVacio.setBorder(PdfPCell.NO_BORDER);
//			tablaLocalizador.addCell(cellLocalizadorVacio);
//			
//			// recogida
//			String strRecogidaLabel = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.recogida", null, locale);
//			Chunk chunkRecogidaLabel = new Chunk(strRecogidaLabel, this.fontCabeceraDatosLabel);
//			Chunk chunkRecogidaText = new Chunk(recogida, this.fontCabeceraDatosText);
//			Paragraph pRecogida = new Paragraph();
//			pRecogida.add(chunkRecogidaLabel);
//			pRecogida.add(chunkRecogidaText);
//			pRecogida.setAlignment(Element.ALIGN_LEFT);
//			pRecogida.setIndentationLeft(2);
//			
//			// fecha
//			String strFechaFormato = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.fecha.formato", null, locale);
//	        SimpleDateFormat formatoFecha = new SimpleDateFormat(strFechaFormato);//6-10-2016
//	        if (fechaHora == null){
//		        fechaHora = new Date();
//	        }
//	        String strFechaFormateada = formatoFecha.format(fechaHora).toUpperCase();
//			String strFecha = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.fecha", null, locale);
//			Chunk chunkFechaLabel = new Chunk(strFecha, this.fontCabeceraDatosLabel);
//			Chunk chunkFechaText = new Chunk(strFechaFormateada, this.fontCabeceraDatosText);
//			Paragraph pFecha = new Paragraph();
//			pFecha.add(chunkFechaLabel);
//			pFecha.add(chunkFechaText);
//			pFecha.setAlignment(Element.ALIGN_LEFT);
//			pFecha.setIndentationLeft(2);
//			
//			// hora
//			String strHoraFormato = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.hora.formato", null, locale);
//	        SimpleDateFormat formatoHora = new SimpleDateFormat(strHoraFormato);//6-10-2016
//	        if (fechaHora == null){
//		        fechaHora = new Date();
//	        }
//	        String strHoraFormateada = formatoHora.format(fechaHora);
//			String strHora = messageSource.getMessage("pdf_devoluciones.ordenRecogida.cabecera.hora", null, locale);
//			Chunk chunkHoraLabel = new Chunk(strHora, this.fontCabeceraDatosLabel);
//			Chunk chunkHoraText = new Chunk(strHoraFormateada, this.fontCabeceraDatosText);
//			Paragraph pHora = new Paragraph();
//			pHora.add(chunkHoraLabel);
//			pHora.add(chunkHoraText);
//			pHora.setAlignment(Element.ALIGN_LEFT);
//			pHora.setIndentationLeft(2);
//			
//			// celda datos derecha
//			PdfPCell cellDatosDer = new PdfPCell();
//			cellDatosDer.addElement(tablaLocalizador);
//			cellDatosDer.addElement(pRecogida);
//			cellDatosDer.addElement(pFecha);
//			cellDatosDer.addElement(pHora);
//			cellDatosDer.setBorder(PdfPCell.NO_BORDER);
//			cellDatosDer.setHorizontalAlignment(Element.ALIGN_LEFT);
//			tablaCabeceraDatos.addCell(cellDatosDer);
//
			//tablaCabeceraDatos.writeSelectedRows(0, -1, pageSize.getLeft(marginLeft), pageSize.getTop() - 10, writer.getDirectContent());
		} catch (Exception e) {
			logger.error("Exporting pdf Informe Devolucion Orden Recogida: PdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.onStartPage tablaCabeceraDatos");
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
			logger.error("Exporting pdf Informe Devolucion Orden Recogida: PdfInformeDevolucionOrdenRecogidaHeaderFooterPageEvent.onEndPage");
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
			logger.error("Exporting pdf Informe Entrada Descentralizada: PdfInformeEntradasDescentralizadasHeaderFooterPageEvent.onCloseDocument");
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

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Long getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Long numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getFechaTarifa() {
		return fechaTarifa;
	}

	public void setFechaTarifa(String fechaTarifa) {
		this.fechaTarifa = fechaTarifa;
	}

	public String getAlbaran() {
		return albaran;
	}

	public void setAlbaran(String albaran) {
		this.albaran = albaran;
	}

	public Long getNumIncidencia() {
		return numIncidencia;
	}

	public void setNumIncidencia(Long numIncidencia) {
		this.numIncidencia = numIncidencia;
	}



}