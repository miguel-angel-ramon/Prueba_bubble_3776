package es.eroski.misumi.aop;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.Ordered;

import es.eroski.misumi.control.LoginController;


public class LoggingAdviceImpl implements LoggingAdvice, Ordered {

   // private static Logger logger = LoggerFactory.getLogger(LoggingAdviceImpl.class);
	private static Logger logger = Logger.getLogger(LoginController.class);//LoggerFactory.getLogger(p12AltasCatalogoController.class);

	@Override
	public Object daoLogCall(ProceedingJoinPoint call) throws Throwable {
		String methodName =call.getSignature().getDeclaringTypeName()+ " method " + call.getSignature().getName();		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    logger.debug("Calling :" + methodName+" - @: "+sdf.format(Calendar.getInstance().getTime()));
		long initialMillis = System.currentTimeMillis();
		Object ret = call.proceed();
		long finalMillis = System.currentTimeMillis();
		logger.debug("Returning :" + methodName+" - @: "+sdf.format(Calendar.getInstance().getTime()));
		logger.debug("Total time invoking " + methodName+" is: "+(finalMillis-initialMillis)/1000+" seconds.");
		return ret;

	}

	@Override
	public int getOrder() {
		return 1;
	}

	
}