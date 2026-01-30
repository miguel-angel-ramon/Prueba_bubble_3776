package es.eroski.misumi.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


public class GZipServletFilter implements Filter {

	private String includePathPatterns = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.includePathPatterns = filterConfig.getInitParameter("includePathPatterns");
	}

	@Override
	public void destroy() {
	}

	/* El filtro está diseñado para comprimir en gzip todas las peticiones al servidor 
	 * que sean de tipo pda*.do, pda *.css, pda*.js. En ocasiones, el servidor en vez 
	 * de comprimir el fichero entero en un gzip y enviarselo al cliente con la cabecera
	 * Content-Encoding:gzip, decide enviarselo a trozos (chunked) con la cabecera 
	 * Transfer-Encoding:chunked, pero sin la cabecera Content-Encoding:gzip. Esto se debe
	 * a que el servidor, empieza a enviar bits de información cuando todavía no sabe el 
	 * Content-Length (tamaño del archivo) del archivo  que va a enviar, por lo cual comprime
	 * el archivo en .gzip pero ignora la cabecera Content-Encoding:gzip e introduce la cabecera
	 * Transfer-encoding: chunked. Por este motivo, siempre que envía los datos en tipo chunked,
	 * el cliente no sabe qué tipo  de comprimido le está llegando, por lo  que no lo sabe descomprimir
	 * el archivo y mostrar su información y descarga un fichero de tipo 'pda*.do' que realmente es un 
	 * archivo de tipo .gzip que contiene el html que debería mostrar. 
	 * 
	 * En resumen siempre que el servidor sabe el Content-Length de la respuesta antes de enviar los datos,
	 * envía el comprimido con la cabecera Content-Encoding:gzip y todo funciona con normalidad. Sin embargo
	 * por una causa que desconocemos (y random) en ocasiones el servidor no espera a saber el Content-Length
	 * y envía la información a cachos (chunked) y sin la cabecera Content-Encoding:gzip, por lo cual el cliente
	 * no sabe descomprimirlo.
	 * 
	 * Cuando la información le llega al cliente con la cabecera de repsuesta Transfer-Encoding:chunked, como el
	 * cliente no sabe qué tipo de compresión utiliza,se descarga el archivo. Para solucionar el error se ha decidido
	 * no comprimir el archivo en estos casos y que de esta forma el cliente sepa gestionar los datos chunked sin comprimir
	 * y muestre el html correctamente (que esto sí sabe hacerlo). 
	 * 
	 * En principio, lo lógico era que aunque fuera chunked, la cabecera Content-Encoding:gzip también apareciera, al estar 
	 * haciéndose la compresión, pero al llegar al cliente no aparecía. Más tarde, se intentó que cuando fuera chuncked, lo 
	 * comprimiera y se insertara a mano la cabecera Content-Encoding:gzip, pero no se consiguió que el problema se solucionara
	 * dando los mismos fallos.Además en las respuestas veíamos que la cabecera Content-Encoding:gzip no aparecía. 
	 *  
	 * Para solucionar este problema, ha sido necesario actualizar la librería de los servlets a la versión 
	 * 3.0.1, porque si no HttpServletResponse no tiene la función getHeader() y no podemos saber cuando el 
	 * Transfer-Encoding es de tipo chunked.*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String path = httpRequest.getRequestURI();
		if (acceptsGZipEncoding(httpRequest) && acceptsGZipPattern(httpRequest) && !chunked(httpResponse)) {
			httpResponse.addHeader("Content-Encoding", "gzip");
			GZipServletResponseWrapper gzipResponse = new GZipServletResponseWrapper(httpResponse);
			chain.doFilter(request, gzipResponse);
			gzipResponse.close();
		} else {			
				chain.doFilter(request, response);			
		}
		/*Imprime las cabeceras del response y sus datos.
		 * if(path.endsWith("pdaP62RealizarDevolucionFinCampania.do")){
			System.out.println("----------------------- 1 response--------------------");
			Iterator a = httpResponse.getHeaderNames().iterator();

			while(a.hasNext()){
				Object n = a.next();
				System.out.println(n.toString()+": "+httpResponse.getHeader(n.toString()));
				if(("chunked").equals(httpResponse.getHeader(n.toString()))){
					System.out.println("CC");
				}
			}
		}*/
	}

	//Función que mira si el Accept-Encoding es gzip
	private boolean acceptsGZipEncoding(HttpServletRequest httpRequest) {
		String acceptEncoding = httpRequest.getHeader("Accept-Encoding");

		return acceptEncoding != null && acceptEncoding.indexOf("gzip") != -1;
	}

	/*Función que mira si el transfer encoding es chuncked*/
	private boolean chunked(HttpServletResponse httpResponse) {
		String transferEncoding = httpResponse.getHeader("Transfer-Encoding");

		return transferEncoding != null && transferEncoding.equals("chunked");
	}

	//Función que controla si la llamada al servidor tiene el patrón pda*.do, pda *.css, pda*.js
	private boolean acceptsGZipPattern(HttpServletRequest httpRequest) {
		String path = httpRequest.getRequestURI();

		if (path != null && this.includePathPatterns != null) {
			Pattern pattern = Pattern.compile(this.includePathPatterns);
			return pattern.matcher(path).matches();
		}
		else{
			return true;
		}
	}

}

class GZipServletResponseWrapper extends HttpServletResponseWrapper {

	private GZipServletOutputStream gzipOutputStream = null;
	private PrintWriter printWriter = null;

	public GZipServletResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
	}

	public void close() throws IOException {

		// PrintWriter.close does not throw exceptions.
		// Hence no try-catch block.
		if (this.printWriter != null) {
			this.printWriter.close();
		}

		if (this.gzipOutputStream != null) {
			this.gzipOutputStream.close();
		}
	}

	/**
	 * Flush OutputStream or PrintWriter
	 *
	 * @throws IOException
	 */

	@Override
	public void flushBuffer() throws IOException {

		// PrintWriter.flush() does not throw exception
		if (this.printWriter != null) {
			this.printWriter.flush();
		}

		IOException exception1 = null;
		try {
			if (this.gzipOutputStream != null) {
				this.gzipOutputStream.flush();
			}
		} catch (IOException e) {
			exception1 = e;
		}

		IOException exception2 = null;
		try {
			super.flushBuffer();
		} catch (IOException e) {
			exception2 = e;
		}

		if (exception1 != null)
			throw exception1;
		if (exception2 != null)
			throw exception2;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (this.printWriter != null) {
			throw new IllegalStateException("PrintWriter obtained already - cannot get OutputStream");
		}
		if (this.gzipOutputStream == null) {
			this.gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
		}
		return this.gzipOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.printWriter == null && this.gzipOutputStream != null) {
			throw new IllegalStateException("OutputStream obtained already - cannot get PrintWriter");
		}
		if (this.printWriter == null) {
			this.gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
			this.printWriter = new PrintWriter(
					new OutputStreamWriter(this.gzipOutputStream, getResponse().getCharacterEncoding()));
		}
		return this.printWriter;
	}

	@Override
	public void setContentLength(int len) {
		// ignore, since content length of zipped content
		// does not match content length of unzipped content.
	}
}

class GZipServletOutputStream extends ServletOutputStream {
	private GZIPOutputStream gzipOutputStream = null;

	public GZipServletOutputStream(OutputStream output) throws IOException {
		super();
		this.gzipOutputStream = new GZIPOutputStream(output);
	}

	@Override
	public void close() throws IOException {
		this.gzipOutputStream.close();
	}

	@Override
	public void flush() throws IOException {
		this.gzipOutputStream.flush();
	}

	@Override
	public void write(byte b[]) throws IOException {
		this.gzipOutputStream.write(b);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		this.gzipOutputStream.write(b, off, len);
	}

	@Override
	public void write(int b) throws IOException {
		this.gzipOutputStream.write(b);
	}
}