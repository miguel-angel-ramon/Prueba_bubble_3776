    package es.eroski.misumi.util;
    
    import java.io.ByteArrayOutputStream;
    
    import javax.xml.namespace.QName;
    import javax.xml.rpc.handler.GenericHandler;
    import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;
    
   public class LogHandler extends GenericHandler {
	   
		private static Logger logger = Logger.getLogger(LogHandler.class);
   
       private String timeStamp;
       
       public LogHandler(){
           timeStamp = Long.toString(System.currentTimeMillis());
       }
   
       @Override
       public QName[] getHeaders() {
           return null;
       }
   
       @Override
       public boolean handleFault(MessageContext context) {
           logger.error(timeStamp + "_fault: " + getStringMessage(context));
           
           return super.handleFault(context);
       }
       
       private String getStringMessage(MessageContext context){
           String res = null;
           
           try {
               SOAPMessageContext ctx = (SOAPMessageContext) context;
               
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               ctx.getMessage().writeTo(stream);
               byte[] items = stream.toByteArray();
               
               res = new String(items);
           }
           catch(Exception e){
               // nothing - just ensuring the method will not throw an exception in case something is wrong.
           }
   
           return res;
       }
   
       @Override
       public boolean handleRequest(MessageContext context) {
          logger.debug(getStringMessage(context));
           
           return super.handleRequest(context);
       }
   
       @Override
       public boolean handleResponse(MessageContext context) {
           logger.debug(getStringMessage(context));
           
           return super.handleResponse(context);
       }
   
   }
   