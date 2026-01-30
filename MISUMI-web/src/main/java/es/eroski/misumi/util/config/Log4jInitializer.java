package es.eroski.misumi.util.config;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Ibermatica
 * 
 *         Listener that Initializes Log4j.
 * 
 */
public class Log4jInitializer implements ServletContextListener,
		HttpSessionListener {


    private static Logger logger = LoggerFactory.getLogger(Log4jInitializer.class);
    
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	    logger.info("WAR Context is being destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			
			String fileName = Thread.currentThread().getContextClassLoader().getResource("log4j.xml").getFile();
			DOMConfigurator.configureAndWatch(fileName, 30000L);
			
		} catch (Exception e) {

		}
		  logger.info("WAR Context is initialized");
	}

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {

	}
}