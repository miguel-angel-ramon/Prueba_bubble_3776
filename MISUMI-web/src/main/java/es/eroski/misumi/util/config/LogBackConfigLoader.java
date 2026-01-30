package es.eroski.misumi.util.config;


//import ch.qos.logback.classic.LoggerContext;
//import ch.qos.logback.classic.joran.JoranConfigurator;
//import ch.qos.logback.core.joran.spi.JoranException;


public class LogBackConfigLoader {
	//private Logger logger = LoggerFactory.getLogger(LogBackConfigLoader.class);
	//private static Logger logger = Logger.getLogger(LogBackConfigLoader.class); 


//	public LogBackConfigLoader(String externalConfigFileLocation)
//			throws IOException, JoranException {
//		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//		File externalConfigFile = new File(externalConfigFileLocation);
//		if (!externalConfigFile.exists()) {
//			throw new IOException(
//					"Logback External Config File Parameter does not reference a file that exists");
//		} else {
//			if (!externalConfigFile.isFile()) {
//				throw new IOException(
//						"Logback External Config File Parameter exists, but does not reference a file");
//			} else {
//				if (!externalConfigFile.canRead()) {
//					throw new IOException(
//							"Logback External Config File exists and is a file, but cannot be read.");
//				} else {
//					JoranConfigurator configurator = new JoranConfigurator();
//					configurator.setContext(lc);
//					lc.reset();
//					configurator.doConfigure(externalConfigFileLocation);
//					logger.info("Configured Logback with config file from: "
//							+ externalConfigFileLocation);
//				}
//			}
//		}
//	}
}
