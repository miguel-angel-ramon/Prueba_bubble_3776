package es.eroski.misumi.util;

import java.io.PrintWriter;
import java.io.StringWriter;

//import org.apache.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class StackTraceManager {
	//private static Logger logger = LoggerFactory.getLogger(StackTraceManager.class);
	//private static Logger logger = Logger.getLogger(StackTraceManager.class);

	private StackTraceManager(){
		
	}
	public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}