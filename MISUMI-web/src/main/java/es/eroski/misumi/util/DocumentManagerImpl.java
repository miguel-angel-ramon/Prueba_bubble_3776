package es.eroski.misumi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.util.iface.DocumentManager;
@Service(value = "DocumentService")
public class DocumentManagerImpl implements DocumentManager{
	private static final String  MANUAL = "/manual/ManualUsuario.doc";
	private static final String  HORARIOS = "/horarios/horariosPlataforma.xls";

	private static Logger logger = Logger
			.getLogger(DocumentManagerImpl.class);
	
	@Override
	public void downloadManual(MessageSource messageSource,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("application/octet-stream; charset=utf-8");
		String manualName = messageSource.getMessage("p01_header.manual", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+manualName+".doc");
		OutputStream out = null;
		
		try
		{
			InputStream archivo = this.getClass().getClassLoader().getResourceAsStream(MANUAL);

		    int longitud = archivo.available();
		    byte[] datos = new byte[longitud];
		    archivo.read(datos);
		    archivo.close();
		    
			out = response.getOutputStream(); 	
			out.write(datos);
			out.flush();
			out.close();

		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}
	
	@Override
	public void downloadHorarios(MessageSource messageSource,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("application/octet-stream; charset=utf-8");
		String horariosName = messageSource.getMessage("p70_detallePedido.horarios", null,
				LocaleContextHolder.getLocale());
		
		response.setHeader("Content-Disposition",
				"attachment; filename="+horariosName+".xls");
		OutputStream out = null;
		
		try
		{
			InputStream archivo = this.getClass().getClassLoader().getResourceAsStream(HORARIOS);

		    int longitud = archivo.available();
		    byte[] datos = new byte[longitud];
		    archivo.read(datos);
		    archivo.close();
		    
			out = response.getOutputStream(); 	
			out.write(datos);
			out.flush();
			out.close();

		} catch (IOException e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		} 
			finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					//logger.error(StackTraceManager.getStackTrace(e));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw e;
				}
			}
		}
	}
	
	
}
