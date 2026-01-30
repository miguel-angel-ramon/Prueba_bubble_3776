package es.eroski.misumi.util.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public interface DocumentManager{

	public  void downloadManual(MessageSource messageSource,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception;

	public void downloadHorarios(MessageSource messageSource,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws Exception;
}
