package es.eroski.misumi.util.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//import org.apache.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;



//import ch.qos.logback.classic.LoggerContext;
//import ch.qos.logback.classic.joran.JoranConfigurator;
//import es.eroski.misumi.util.StackTraceManager;


public class ExternalConfigLoaderContextListener implements ServletContextListener,
HttpSessionListener  {
//	private static final Logger logger = LoggerFactory.getLogger(ExternalConfigLoaderContextListener.class);
//	private static Logger logger = Logger.getLogger(ExternalConfigLoaderContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {	
		//String configLocation = sce.getServletContext().getInitParameter("CONFIGDIR");		
//		if(configLocation == null){			
//			configLocation = System.getenv("CONFIGDIR"); 	
//			}		
		try{		
//			 LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//			ch.qos.logback.classic.Logger log = lc.getLogger(ExternalConfigLoaderContextListener.class);
//			 
//			    StatusPrinter.print(lc);
		//	 LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
				//new LogBackConfigLoader( "logback.xml");
//				new LogBackConfigLoader(Thread.currentThread().getContextClassLoader()
//				.getResourceAsStream("logback.xml"));
//			Thread.currentThread().getContextClassLoader()
//			.getResourceAsStream("logback.xml");
//			    LoggerContext context = (LoggerContext) LoggerFactory
//		                .getILoggerFactory();
//		        context.reset();
//		        JoranConfigurator configurator = new JoranConfigurator();
//		        configurator.setContext(context);
//		        URL url = new URL("file:/logback.xml");
//		                
//		        configurator.doConfigure(url);

				}catch(Exception e){		
				//	logger.error("Unable to read config file", e);		
					}	}	
	@Override	
	public void contextDestroyed(ServletContextEvent sce) {		
		
	}
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
