package es.eroski.misumi.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.DelegatingFilterProxy;

import es.eroski.misumi.model.User;
import es.eroski.misumi.util.StackTraceManager;



public class SecurityFilter extends DelegatingFilterProxy  {
	//private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	 private static Logger logger = Logger.getLogger(SecurityFilter.class);

//	public void doFilter(ServletRequest request, ServletResponse response,
//			FilterChain filterChain) {
//		HttpServletResponse resp = (HttpServletResponse) response;
//		HttpServletRequest req = (HttpServletRequest) request;
//		try {
//			filterChain.doFilter(request, response);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	

	private static final String ERROR = "/error.do";
	
	private static final String ERROR_PDA = "/errorPda.do";

	private static final String DENIED = "/login.do";
	
	private static final String DENIED_PDA = "/pdaSesionCaducada.do";

	private static final String LOGIN = "/login.do";
	
	private static final String LOGIN_PDA = "/pdawelcome.do";

	private static final String ATTRIBUTE = "user";
	
	private static final String PREFIJO_PDA = "/pda";
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) {
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpServletRequest req = (HttpServletRequest)request;
	
		
		try {
			if (req.getServletPath().endsWith("/getImage.do") || req.getServletPath().endsWith(ERROR) || req.getServletPath().endsWith(DENIED) || req.getServletPath().endsWith(DENIED_PDA) || req.getServletPath().endsWith(LOGIN) || req.getServletPath().endsWith(LOGIN_PDA) ||(req.getSession(false)==null &&  (req.getServletPath().endsWith(LOGIN) || req.getServletPath().endsWith(DENIED) || req.getServletPath().endsWith(DENIED_PDA))) ||(req.getServletPath().endsWith(LOGIN) && req.getSession(false).getAttribute(ATTRIBUTE)!=null)){
				filterChain.doFilter(request, response);

			}else{
				if((req.getSession(false)!=null && req.getSession(false).getAttribute(ATTRIBUTE)!=null) || (((req.getServletPath().endsWith(LOGIN)||!req.getServletPath().endsWith(".do"))) && req.getParameter("userName")==null && req.getParameter("password")==null)){
					//Control de limpiado de variables de sesión por menu
					if (req.getParameter("menu")!=null || req.getParameter("limpiarSesion")!=null){
						limpiarSesion(req);
					}
					

					filterChain.doFilter(request, response);
				}else{		
					if (req.getServletPath() != null && req.getServletPath().contains(PREFIJO_PDA)){
						//Peticion 55001. Corrección errores del LOG.
						if (!resp.isCommitted()){
							resp.sendRedirect(req.getContextPath() + DENIED_PDA);
						}
					}else{
						if(req.getSession(false)==null){
							//Peticion 55001. Corrección errores del LOG.
							if (!resp.isCommitted()){
								resp.sendRedirect(req.getContextPath() + DENIED + "?sessionTimeout=yes");
							}
						}else{
							//Peticion 55001. Corrección errores del LOG.
							if (!resp.isCommitted()){
								resp.sendRedirect(req.getContextPath() + DENIED);
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			if (!e.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException")){
				logger.error("#####################################################");
				logger.error("URL: "+ req.getServletPath());
				logger.error("Method: "+ req.getMethod());
				logger.error("Mapa de Parametros");
				logger.error("-------------------------------");
			    Map map = req.getParameterMap();
			    int count = 1;
			    for (Object key: map.keySet()){
			    	String keyStr = (String)key;
			    	String[] value = (String[])map.get(keyStr);
			    	logger.error("Parámetro " + count + "  -> " + (String)key + "   :   " + Arrays.toString(value));
			    	count ++;
			    }
			    
			    logger.error("-------------------------------");
			    User user = (User) req.getSession().getAttribute(ATTRIBUTE);
				if (user!=null && user.getCentro()!=null){
					if (null != user.getCentro()){
						logger.error("Centro: " + user.getCentro().getCodCentro() );	
					}
					logger.error("Usuario: " + user.getCode() );
				}
				
				logger.error("-------------------------------");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
			try {
				if (req.getServletPath() != null && req.getServletPath().contains(PREFIJO_PDA)){
					//Peticion 55001. Corrección errores del LOG.
					if (!resp.isCommitted()){
						resp.sendRedirect(req.getContextPath() + ERROR_PDA);
					}
				}else{
					//Peticion 55001. Corrección errores del LOG.
					if (!resp.isCommitted()){
						resp.sendRedirect(req.getContextPath() + ERROR);
					}
				}				
			} catch (IOException e1) {				
			}
		} finally{
			if(req.getHeader("Content-Type")!=null && req.getHeader("Content-Type").contains("application/json")){
				resp.setHeader("Content-Type", "application/json; charset=UTF-8");
			}
			if(req.getHeader("Accept")!=null && req.getHeader("Accept").contains("application/json")){
				resp.setHeader("Content-Type", "application/json; charset=UTF-8");
			}
		}
	}

	private void limpiarSesion(HttpServletRequest request){
		String opcionMenu = request.getParameter("menu");

		request.getSession().removeAttribute("listaSFMCap");
		request.getSession().removeAttribute("hashMapClavesSFMCap");
		request.getSession().removeAttribute("listaInvLib");
		request.getSession().removeAttribute("hashMapClavesInvLib");
		//Petición 55700. 
		request.getSession().removeAttribute("pedidoPda");
		
		//Petición 58291.
		request.getSession().removeAttribute("fila");
		request.getSession().removeAttribute("estructuraComercial");
		request.getSession().removeAttribute("paginaActual");
		request.getSession().removeAttribute("referenciaFiltro");
		request.getSession().removeAttribute("proveedorFiltro");
		request.getSession().removeAttribute("devolucion");
		request.getSession().removeAttribute("consultaStock");
		
		//Petición misumi-36
		request.getSession().removeAttribute("impresorasResponse");
		
		//Petición MISUMI-146
		request.getSession().removeAttribute("tipoListado");
		request.getSession().removeAttribute("area");
		request.getSession().removeAttribute("paginaStk");
		request.getSession().removeAttribute("reposicionLineasPaginada");

		//Peticion MISUMI-195
		request.getSession().removeAttribute("seccionStk");
		request.getSession().removeAttribute("secciones");
		
		//request.getSession().removeAttribute(Constantes.IS_NUMERO_ETIQUETA_IN_SESSION_ENVIADOS);
		//request.getSession().removeAttribute(Constantes.HASH_MAP_NUMERO_ETIQUETA_IN_SESSION);
	}
}
