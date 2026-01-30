package es.eroski.misumi.util;

import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



/**
 * 
 * @author Ibermatica
 */
public class WSBManager {

	private static Logger logger = Logger.getLogger(WSBManager.class);

	private static Properties wsProperties = null;
	private static String ficheroConfiguracion = "ws.properties";

    private WSBManager(){
		
	}
	private static synchronized void cargar() throws MissingResourceException {
		wsProperties = new Properties();
		try {
			wsProperties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(ficheroConfiguracion));
			PropertyConfigurator.configure(wsProperties);

		} catch (Exception e) {
			logger.log(Level.ERROR, e.getStackTrace());
		}
	}
	public static synchronized  String getProperty(String name) {
			if (wsProperties == null) {
				cargar();
			}
			return wsProperties.getProperty(name);
	
	}

	
}