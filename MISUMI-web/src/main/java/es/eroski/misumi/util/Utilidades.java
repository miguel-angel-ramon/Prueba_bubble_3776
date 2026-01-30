package es.eroski.misumi.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.NuevoPedidoOferta;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.service.iface.FotosReferenciaService;

@Component
public class Utilidades  {
	
	private static Logger logger = Logger.getLogger(Utilidades.class);
	

	@Autowired
	private Utilidades(FotosReferenciaService fotosReferenciaService, ServletContext context){
		Utilidades.fotosReferenciaServiceStatic = fotosReferenciaService;
		Utilidades.context = context;
	}
	
	private static FotosReferenciaService fotosReferenciaServiceStatic;
	private static ServletContext context;
	
	private static final String formatoFecha_ES = "dd/MM/yyyy";
	private static final String formatoFechaNumericoDDMMYYYY = "ddMMyyyy";
	private static final String formatoFechaHora =   "dd/MM/yyyy HH:mm:ss";
	private static final String formatoFecha_dd_MM_YYYY = "dd-MM-yyyy";
	private static final String formatoFecha_YYYY_MM_dd = "yyyy-MM-dd";

	/**
	 * Convierte una fecha en formato DDMMYYYY a otra dependiente del idioma.
	 *
	 */
	public static String formatearFecha(String fechaDDMMYYYY, Locale locale) {

		String fechaPantalla = "";
		if (fechaDDMMYYYY != null){
			Date fecha = convertirStringAFecha(fechaDDMMYYYY);
			final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
			sdfFormateador.applyPattern(obtenerFormatoFecha(locale));
			fechaPantalla = sdfFormateador.format(fecha);
		}

		return fechaPantalla;
	}

	
	
	/**
	 * Convierte una fecha a formato dd-MM-yyyy.
	 *
	 */
	public static String formatearFecha_dd_MM_yyyy(Date fecha) {

		String fechaPantalla = "";
		final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
		sdfFormateador.applyPattern(formatoFecha_dd_MM_YYYY);
		fechaPantalla = sdfFormateador.format(fecha);
		return fechaPantalla;
	}
	
	/**
	 * Convierte una fecha a formato dd/MM/yyyy.
	 *
	 */
	public static String formatearFecha_ddMMyyyyBarra(Date fecha) {

		String fechaPantalla = "";
		final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
		sdfFormateador.applyPattern(formatoFecha_ES);
		fechaPantalla = sdfFormateador.format(fecha);
		return fechaPantalla;
	}
	
	/**
	 * Convierte una fecha a formato DDMMYYYY.
	 *
	 */
	public static String formatearFecha(Date fecha) {
		return formatearFecha(fecha,formatoFechaNumericoDDMMYYYY);
	}

	public static String formatearFecha(Date fecha, String formato) {
		String fechaPantalla = "";
		final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
		sdfFormateador.applyPattern(formato);
		fechaPantalla = sdfFormateador.format(fecha);
		return fechaPantalla;
	}

	/**
	 * Convierte una fecha a formato DDMMYYYY.
	 * 
	 * @param fecha
	 * @return
	 */
	public static String formatearFechaES(Date fecha) {

		String fechaPantalla = "";
		final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
		sdfFormateador.applyPattern(formatoFecha_ES);
		fechaPantalla = sdfFormateador.format(fecha);
		return fechaPantalla;
	}
	
	/**
	 * Convierte una fecha a formato DD/MM/YYYY HH:mm:ss.
	 *
	 */
	public static String formatearFechaHora(Date fecha) {

		String fechaPantalla = "";
		final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
		sdfFormateador.applyPattern(formatoFechaHora);
		fechaPantalla = sdfFormateador.format(fecha);
		return fechaPantalla;
	}


	/**
	 * Convierte una fecha en formato DDMMYYYY a Date.
	 *
	 */
	public static Date convertirStringAFecha (String fechaDDMMYYYY){
		//Formatear fecha de pantalla que vendría en formato DDMMYYYY
		int dia = Integer.valueOf(fechaDDMMYYYY.substring(0, 2));
		int mes =Integer.valueOf(fechaDDMMYYYY.substring(2, 4)) - 1;
		int anyo =Integer.valueOf(fechaDDMMYYYY.substring(4));
		GregorianCalendar diaTransformado = new GregorianCalendar();
		diaTransformado.set(anyo, mes, dia);
		diaTransformado.set(Calendar.HOUR_OF_DAY, 0);
		diaTransformado.set(Calendar.MINUTE, 0);
		diaTransformado.set(Calendar.SECOND, 0);
		diaTransformado.set(Calendar.MILLISECOND, 0);

		return diaTransformado.getTime();
	}

	/**
	 * Devuelve un String con el formato de la fecha en función del idioma.
	 * 
	 */
	public static String obtenerFormatoFecha(Locale locale) {
		//Dependiendo del locale debería devolver un formato diferente
		return formatoFecha_ES;
	}

	/**
	 * Devuelve una cadena restringida a una longitud determinada
	 *
	 */
	public static String obtenerCadenaRestringida(String cadena, int longitud){
		String cadenaResultado = "";
		int longitudCadenaOrigen = 0;
		if (longitud > 0){
			if (cadena != null ){
				longitudCadenaOrigen = cadena.length();
				if (longitudCadenaOrigen > longitud){
					cadenaResultado = cadena.substring(0, longitud);
				}else{
					cadenaResultado = cadena;
				}
			}
		}

		return cadenaResultado;
	}

	/**
	 * Comparador de objetos VArtSfm
	 * 
	 */
	public static class IconosComparator implements Comparator<Object> {

		public int compare(final Object o1, final Object o2) {

			final VArtSfm registro1 = (VArtSfm) o1;
			final VArtSfm registro2 = (VArtSfm) o2;

			Long codError1 = (registro1.getCodError() == null ? new Long(0):registro1.getCodError());
			Long codError2 = (registro2.getCodError() == null ? new Long(0):registro2.getCodError());
			return codError2.compareTo(codError1);
		}
	}

	/**
	 * Comparador de objetos NuevoPedidoOferta
	 * 
	 */
	public static class NuevoPedidoOfertaComparator implements Comparator<Object> {

		public int compare(final Object o1, final Object o2) {

			final NuevoPedidoOferta registro1 = (NuevoPedidoOferta) o1;
			final NuevoPedidoOferta registro2 = (NuevoPedidoOferta) o2;

			Long codError1 = (registro1.getCodError() == null ? new Long(0):registro1.getCodError());
			Long codError2 = (registro2.getCodError() == null ? new Long(0):registro2.getCodError());
			return codError2.compareTo(codError1);
		}
	}


	/**
	 * Convierte un double en String según el patrón que nos llegue
	 *
	 */
	public static String convertirDoubleAString (double valor, String patron){

		String resultado = "";
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("es","ES"));
		decimalFormat.applyPattern(patron);

		resultado = decimalFormat.format(valor);

		return resultado;
	}

	/**
	 * Obtiene el valor formateado a String del dato del resultset pasado por parámetro 
	 *
	 */
	public static Object obtenerValorExcel (ResultSet resultSet, int indice) {

		try{
			ResultSetMetaData rsmd = resultSet.getMetaData();
			if (Types.NUMERIC == rsmd.getColumnType(indice)){
				return resultSet.getBigDecimal(indice);
			}else if (Types.TIMESTAMP == rsmd.getColumnType(indice)){
				return obtenerFechaFormateada(resultSet.getDate(indice));
			}else{
				return resultSet.getString(indice);
			}
		}catch (Exception e) {
			return "";
		}
	}

	/**
	 * Obtiene el valor en formateado a String del dato del resultset pasado por parámetro 
	 *
	 */
	public static Object obtenerValorExcelString (ResultSet resultSet, int indice) {

		try{
//			ResultSetMetaData rsmd = resultSet.getMetaData();
			return resultSet.getString(indice);

		}catch (Exception e) {
			return "";
		}
	}

	/**
	 * Dada una fecha devuelve el número del día de la semana, siendo el Lunes el primer día.
	 *
	 */
	public static Integer getNumeroDiaSemana(Date fecha, Locale locale) {

		Integer intIndice = null;
		try{
			if (fecha != null){
				Calendar c = Calendar.getInstance(locale);
				c.setTime(fecha);
				intIndice = c.get(Calendar.DAY_OF_WEEK) - 1 ;
				if (intIndice == 0){
					intIndice = 7;
				}
			}
		}catch (Exception e){
			intIndice = null;
		}

		return intIndice;
	}

	public static String normalizar(String input) {
		MessageSource messageSource = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");
		// Cadena de caracteres original a sustituir.
		String original = messageSource.getMessage("cadenaCaracteresEspeciales", null, LocaleContextHolder.getLocale());
		// Cadena de caracteres ASCII que reemplazarán los originales.
		String ascii = messageSource.getMessage("cadenaSinCaracteresEspeciales", null, LocaleContextHolder.getLocale());
		String output = input;
		for (int i=0; i<original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}//for i
		return output;
	}

	public static String obtenerFechaFormateada(Date f){
		Locale locale = LocaleContextHolder.getLocale();
		MessageSource message = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");
		Integer day = getNumeroDiaSemana(f, locale);
		String dayofWeekProp = "calendario."+day;
		String dayofWeek = message.getMessage(dayofWeekProp, null, locale);
		SimpleDateFormat df = new SimpleDateFormat("MMM", locale);
		String fechaFormateada = dayofWeek+" "+f.getDate()+"-"+df.format(f).toUpperCase();
		return fechaFormateada;
	}

	/**
	 * Da formato a la fecha  01-01-2001
	 **/
	public static String obtenerFechaFormateadaddMMyyyy(Date f){
		if (f==null){
			return "";
		}
		Locale locale = LocaleContextHolder.getLocale();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", locale);
		String fechaFormateada = df.format(f);
		return fechaFormateada;
	}
	
	/**
	 * Da formato a la fecha  X 8-MAR 2017
	 **/
	public static String obtenerFechaFormateadaEEEddMMMyyyy(Date f){
		Locale locale = LocaleContextHolder.getLocale();
		MessageSource message = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");
		Integer day = getNumeroDiaSemana(f, locale);
		String dayofWeekProp = "calendario."+day;
		String dayofWeek = message.getMessage(dayofWeekProp, null, locale);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM yyyy", locale);
		String fechaFormateada = dayofWeek+" "+df.format(f).toUpperCase();
		return fechaFormateada;
	}

	/**
	 * Devuelve un Date
	 * 
	 * @param fechaHora
	 * @return
	 */
	public static java.sql.Date convertirStringAFechaSqlDate(String fecha) {
		java.sql.Date fechaDt = null;

		fechaDt = convertirStringAFechaSqlDate(fecha, formatoFecha_YYYY_MM_dd);
		return fechaDt;
	}

	public static java.sql.Date convertirStringAFechaSqlDate(String fecha, String formatoFecha) {
		java.sql.Date fechaDt = null;

		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha);
			Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(fecha);
			} catch (ParseException e) {
				return null;
			}
			fechaDt = new java.sql.Date(parsedDate.getTime());
		}
		return fechaDt;
	}

	/**
	 * Devuelve un Date
	 * 
	 * @param fechaHora
	 * @return
	 */
	public static java.sql.Date convertirFechaAFechaSqlDate(Date fecha) {
		Format formatterDDMMYYYY = new SimpleDateFormat(formatoFecha_YYYY_MM_dd);
		String fechaStr=formatterDDMMYYYY.format(fecha);
		java.sql.Date fechaDt = null;

		if (fechaStr != null && !fechaStr.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha_YYYY_MM_dd);
			Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(fechaStr);
			} catch (ParseException e) {
				return null;
			}
			fechaDt = new java.sql.Date(parsedDate.getTime());
		}
		return fechaDt;
	}

	/**
	 * Convierte un formato X 08-MAR 2017 a Date
	 * @throws ParseException 
	 **/
	public static Date convertirStringEEEddMMMyyyyADate(String fechaEEEEddMMMyyyy){
		String fechaddMMMyyyy = fechaEEEEddMMMyyyy.substring(2);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM yyyy");
		Date varDate = null;
		try {
			varDate = df.parse(fechaddMMMyyyy);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return varDate;
	}

	/**
	 * Rellena una cadena de caracteres por la derecha con un caracter.
	 *
	 *  Rellena una cadena de caracteres por la derecha con un cierto
	 *  caracter hasta llegar a una longitud de cadena dada. Si la cadena
	 *  inicial excede de la longitud dada, no se añadirá ningún caracter.
	 *
	 *  @param cadena   cadena de caracteres que hay que rellenar
	 *  @param fill     carácter de relleno
	 *  @param longTotal longitud final de la cadena.
	 *  @return String  cadena resultado del relleno por la derecha.
	 */
	public static String rellenarDerecha(final String cadena, final char fill, final int longTotal) {

		String aux = ((cadena == null) || (cadena.equals("null"))) ? "" : cadena;
		final int lg = ((cadena == null) || (cadena.equals("null"))) ? 0 : cadena.length();

		if (lg >= longTotal) {
			return aux;
		}

		for (int i = lg; i < longTotal; i++) {
			aux += fill;
		}

		return aux;
	}

	/**
	 * Rellenar por la izq con un caracter.
	 *
	 *  Rellena una cadena de caracteres por la izquierda con un cierto
	 *  caracter hasta llegar a una longitud de cadena dada. Si la cadena
	 *  inicial excede de la longitud dada, no se añadirá ningún caracter.
	 *
	 *  @param cadena   cadena de caracteres que hay que rellenar
	 *  @param fill     carácter de relleno
	 *  @param longTotal longitud final de la cadena.
	 *  @return String  cadena resultado del relleno por la derecha.
	 */
	public static String rellenarIzquierda(final String cadena, final char fill, final int longTotal) {

		String aux = ((cadena == null) || (cadena.equals("null"))) ? "" : cadena;
		final int lg = ((cadena == null) || (cadena.equals("null"))) ? 0 : cadena.length();

		if (lg >= longTotal) {
			return aux;
		}

		for (int i = lg; i < longTotal; i++) {
			aux = fill + aux;
		}

		return aux;
	}

	/**
	 * Convierte un String a BigDecimal.
	 * El número que representa el String está con coma decimal y se convierte a BigDecimal.
	 * Si el String no es numérico devuelve un cero.
	 *
	 */
	public static BigDecimal convertirStringABigDecimal(String cadena){

		BigDecimal resultado = new BigDecimal("0");

		try{
			if (cadena != null && cadena != ""){
				resultado = new BigDecimal(cadena.replace(',', '.'));
			}
		}
		catch(NumberFormatException e){
			resultado = new BigDecimal("0");
		}

		return resultado;
	}

	/**
	 * Convierte un String a int.
	 * Si el String no es numérico devuelve el número porDefecto.
	 *
	 */
	public static int convertirStringAInt(String cadena, int porDefecto){

		int resultado = porDefecto;

		try{
			if (cadena != null && cadena != ""){
				resultado = Integer.parseInt(cadena);
			}
		}
		catch(NumberFormatException e){
			resultado = porDefecto;
		}

		return resultado;
	}
	
	/**
	 * Pinta las sentencia SQL y los parametros de esta en el log cuando salta un error de SQL.
	 *
	 */
	public static void mostrarMensajeErrorSQL(String strSQL, List<Object> params, Exception e){
		logger.error("######################## Error SQL ############################");
		logger.error("Sentencia SQL: " + strSQL );	
		if (null!=params) {	
			logger.error("Parametros: " + params.toString());
		}
		logger.error( StackTraceManager.getStackTrace(e));
		logger.error("###############################################################");
	}

	public static void mostrarMensajeSinRegistrosSQL(String strSQL, List<Object> params, String cadenaError, Exception e){
		logger.error("######################## Error SQL ############################");
		logger.error("Sentencia SQL: " + strSQL );	
		if (null!=params) {	
			logger.error("Parametros: " + params.toString());
		}
		logger.error(StackTraceManager.getStackTrace(e));
		logger.error("###############################################################");
	}
	
	//Función que sirve para redimensionar la imágen introducida. Como parámetros se introduce:
	// -> la imagen a redimensionar.
	// -> El nuevo ancho a redimensionar. Si es nulo se adapta en proporción al largo.  Si el largo también es nulo la imagen original se adapta al hueco de la pantalla.
	// -> El nuevo largo a redimensionar. Si es nulo, se adapta en proporción al ancho. Si el largo también es nulo la imagen original se adapta al hueco de la pantalla.
	// -> El ancho máximo de imagen que entra en la pantalla. Si la redimensión de la imagen sigue siendo mayor que esta proporcion se redimensiona el ancho.
	// -> El largo máximo de imagen que entra en la pantalla. Si la redimensión de la imagen sigue siendo mayor que esta proporcion se redimensiona el largo.
	public static BufferedImage redimensionarImagen(BufferedImage imagen, Float nuevoAncho, Float nuevoLargo, Float anchoPantallaPx, Float largoPantallaPx){
		//Calculamos los valores originales de ancho y largo de la imagen.
		int originalLargo = imagen.getHeight();
		int originalAncho = imagen.getWidth();

		//Creamos variable para guardar las proporciones.
		float proporcion;

		//Si el usuario quiere que el nuevo ancho o largo sea fijo, pero que el nuevo largo o ancho se adapte en proporción al nuevo ancho o largo
		if((nuevoAncho != null && nuevoLargo == null) || (nuevoLargo != null && nuevoAncho == null)){
			//Si el usuario quiere que el nuevo ancho sea fijo, pero que el nuevo largo se adapte en proporción al nuevo ancho.
			if(nuevoAncho != null){
				proporcion = nuevoAncho / originalAncho;
				nuevoLargo = originalLargo * proporcion;	
			}
			//Si el usuario quiere que el nuevo largo sea fijo, pero que el nuevo ancho se adapte en proporción al nuevo largo.
			else{
				//Si el nuevo largo es menor que el largo original, se redimensiona. Si no, se deja como está.
				proporcion = nuevoLargo / originalLargo;
				nuevoAncho = originalAncho * proporcion;	
			}
		}else if(nuevoLargo == null && nuevoAncho == null){
			nuevoAncho = Float.parseFloat(String.valueOf(originalAncho));
			nuevoLargo = Float.parseFloat(String.valueOf(originalAncho));
		}

		//Si las nuevas proporciones son más grandes que el tamaño de la pantalla en ancho y/o largo,
		//se redimensiona hasta que las proporciones entren en la pantalla.
		while (true){
			//Si el ancho y largo son mayores al tamaño de pantalla
			if(nuevoAncho > anchoPantallaPx && nuevoLargo > largoPantallaPx){
				//Buscamos la diferencia de proporción del largo y ancho de la
				//pantalla con las de la imagen y nos quedamos con la que más 
				//se acerca al tamaño de la pantalla. De esta forma conseguimos
				//que el tamaño de la imagen sea el más cercano al tamaño de la 
				//pantalla.
				float diferenciaAncho = nuevoAncho - anchoPantallaPx;
				float diferenciaLargo = nuevoLargo - largoPantallaPx;

				if(diferenciaAncho < diferenciaLargo){
					proporcion = anchoPantallaPx / nuevoAncho;
					nuevoAncho = anchoPantallaPx;
					nuevoLargo = nuevoLargo * proporcion;
				}else{
					proporcion = largoPantallaPx / nuevoLargo;
					nuevoLargo = largoPantallaPx;
					nuevoAncho =  nuevoAncho * proporcion;				
				}
			}else if(nuevoAncho > anchoPantallaPx){
				proporcion = anchoPantallaPx/nuevoAncho;
				nuevoAncho = anchoPantallaPx;
				nuevoLargo = nuevoLargo * proporcion;
			}else if(nuevoLargo > largoPantallaPx){
				proporcion = largoPantallaPx/nuevoLargo;
				nuevoLargo = largoPantallaPx;
				nuevoAncho = nuevoAncho * proporcion;
			}else{
				break;
			}
		}

		//Se crea la nueva imagen con las nuevas proporciones de ancho y largo.
		BufferedImage dimg = new BufferedImage((int)nuevoAncho.longValue(), (int)nuevoLargo.longValue(), imagen.getType());  
		Graphics2D g = dimg.createGraphics();  
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
		g.drawImage(imagen, 0, 0, (int)nuevoAncho.longValue(), (int)nuevoLargo.longValue(), 0, 0, originalAncho, originalLargo, null);  
		g.dispose();
		return dimg;
	}

	/**
	 * Funcion que carga una imagen y se asegura de que se cierran todos los Streams involucrados
	 * @param codArticulo
	 * @param response
	 * @param bi
	 * @param tipoImagen
	 * @throws IOException
	 */
	public static void cargarImagen(Long codArticulo, HttpServletResponse response, String tipoImagen) {
		OutputStream out = null;
		InputStream imagen = null;

		try{
			if (codArticulo !=null){
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(codArticulo);
				fotosReferencia = fotosReferenciaServiceStatic.findImage(fotosReferencia);
				imagen = fotosReferencia.getFoto();
			}else{
				// Si no se indica codigo de articulo, se carga la imagen de que no hay foto
				imagen = Utilidades.class.getClassLoader().getResourceAsStream("/fotos/nofoto.png");
			}
			out = response.getOutputStream();
			
			BufferedImage bi = ImageIO.read(imagen);
			ImageIO.write(bi, tipoImagen, out);
			
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
			imagen = Utilidades.class.getClassLoader().getResourceAsStream("/fotos/nofoto.png");
			BufferedImage bi;
			try {
				bi = ImageIO.read(imagen);
				ImageIO.write(bi, tipoImagen, out);
				out.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (imagen!=null){
				try {
					imagen.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	/**
	 * Funcion que carga una imagen en la pistola realizando las redimensiones requeridas y se asegura de que se cierran todos los Streams involucrados
	 * @param codArticulo
	 * @param response
	 * @param bi
	 * @param tipoImagen
	 * @throws IOException
	 */
	public static void cargarImagenPistola(Long codArticulo, HttpServletResponse response, String tipoImagen, Float nuevoAnchoImagen, Float nuevoLargoImagen, Float anchoPantalla, Float largoPantalla) throws Exception {
		OutputStream out = null;
		InputStream imagen = null;
		MemoryCacheImageOutputStream memoryCacheIOS = null;
		
		try{
			response.setContentType("image/"+tipoImagen);

			if (codArticulo !=null){
				FotosReferencia fotosReferencia = new FotosReferencia();
				fotosReferencia.setCodReferencia(codArticulo);
				fotosReferencia = fotosReferenciaServiceStatic.findImage(fotosReferencia);
				imagen = fotosReferencia.getFoto();
			}else{
				// Si no se indica codigo de articulo, se carga la imagen de que no hay foto
				imagen = Utilidades.class.getClassLoader().getResourceAsStream("/fotos/nofoto.png");
			}

			out = response.getOutputStream();
			memoryCacheIOS = new MemoryCacheImageOutputStream(out);
			
			BufferedImage bi = ImageIO.read(imagen);
			BufferedImage imgRedimensionada = Utilidades.redimensionarImagen(bi,nuevoAnchoImagen,nuevoLargoImagen,anchoPantalla, largoPantalla);
			
			//Se usa MemoryCacheImageOutputStream porque si no casca en la pistola.
			ImageIO.write(imgRedimensionada, tipoImagen, memoryCacheIOS);
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		} finally {
			if (memoryCacheIOS != null) {
	            try {
	            	memoryCacheIOS.flush();
	            	memoryCacheIOS.close();
	            } catch (IOException e) {
	                e.printStackTrace();
					throw e;
	            }
	        }			
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
			if (imagen!=null){
				try {
					imagen.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
	        // Sugerir recolección de basura.
//	        System.gc();
		}
	}
	
	public static String removeAccents(String text) {
	    return text == null ? null : Normalizer.normalize(text, Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	

	/**
	 * Comprueba si el contexto en el que corre la aplicacion es el configurado en
	 * Constantes.CONTEXTO_WEB2
	 * 
	 * @return Devuelve TRUE en caso afirmativo, en contrario devuelve FALSE
	 */
	public static Boolean isThisWeb2Context() {
		return context.getContextPath().equalsIgnoreCase(Constantes.CONTEXTO_WEB2);			
	}
	
	/**
	 * 
	 * @param lista
	 * @return
	 */
	public static List<VArtSfm> formatearLsfExport(List<VArtSfm> lista){

		/* aplicar condiciones limite superior*/
		for (int i = 0; i < lista.size();i++) {

			if(lista.get(i).getFechaSfmDDMMYYYY().length()!=0){
				//Formateamos la fechaSFM a date
				String getfechaSfm = lista.get(i).getFechaSfmDDMMYYYY().substring(0,2) +""+ lista.get(i).getFechaSfmDDMMYYYY().substring(2,4)
									+""+ lista.get(i).getFechaSfmDDMMYYYY().substring(4);
				Calendar fechaSfm = Calendar.getInstance();
				fechaSfm .setTime(Utilidades.convertirStringAFecha(getfechaSfm));

				//Obtenemos la fecha actual
				Calendar fechaActual = Calendar.getInstance();

				//Obtenemos  la diferencia entre las dos fechas 
				long c = 24*60*60*1000;

				long diffDays = (long) Math.floor((fechaActual.getTimeInMillis() - fechaSfm.getTimeInMillis())/(c));

				if (diffDays <= 21) {
					//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""
					lista.get(i).setLsf(null);
//				}else {
					//sfmCapacidadFacingPagina.getDatos().getRows().get(i).setLsf(sfmCapacidadFacingPagina.getDatos().getRows().get(i).getLmin());
				}
			}
		}

		return lista;
	}

	/**
	 * Formatea la cadena entera y devuelve un String con formato "HH:MM".
	 * @param cadena
	 * @return
	 */
	public static String formatearHora(int cadena){
        // Extraer horas, minutos y segundos
        int horas = cadena / 10000;
        int minutos = (cadena % 10000) / 100;

        // Crear un objeto Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minutos);
        
        // Formatear a HH:MM
        String formatoHora = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        
        return formatoHora;
	}
	
	/**
	 * Elimina los elementos que no tienen valor de la lista.
	 * @param lista
	 * @return
	 */
	public static LinkedHashMap<String, String> limpiaLista(LinkedHashMap<String,String> lista){
	
		LinkedHashMap<String,String> nuevaLista = new LinkedHashMap<String,String>();
		
		// Recorrer la lista y se queda solo con los valores no nulos.
		for (Entry<String, String> entry : lista.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
	
		    if (!key.isEmpty()){
			    nuevaLista.put(key, value);
//			    nuevaLista.put(key, value.substring(0, 20));
		    }
		}
		
		return nuevaLista;
	}

	public static double getOrZero(Double valor) {
	    return valor != null ? valor : 0.0;
	}
	
}

