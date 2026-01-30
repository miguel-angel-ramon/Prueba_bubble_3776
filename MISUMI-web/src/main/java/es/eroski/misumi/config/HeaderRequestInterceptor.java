package es.eroski.misumi.config;

import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import es.eroski.misumi.util.WSBManager;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
//    private final String headerName;
//    private final String headerValue;

//    private final String token;
//    private final String username = WSBManager.getProperty("ws.user2");
//    private final String password = WSBManager.getProperty("ws.pwd3");

    private final String headerName;
    private final String headerValue;

//    public HeaderRequestInterceptor(String token/*, String headerName, String headerValue*/) {
//        this.token = token;
////        this.headerName = headerName;
////        this.headerValue = headerValue;
//    }

    public HeaderRequestInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    	try{
    		
//    		if (!request.getHeaders().containsKey("Authorization") || !request.getHeaders().containsKey("X-APP-KEY")){
    		if (!request.getHeaders().containsKey(headerName)) {
        		HttpHeaders headers = request.getHeaders();
        		headers.set(headerName, headerValue);
    		}

//        String auth = username + ":" + password;
//        String encodedAuth = DatatypeConverter.printBase64Binary(auth.getBytes());
//        String authHeader = "Basic " + new String(encodedAuth);
//        headers.set("X-APP-KEY", this.token);
//        headers.add("Authorization", authHeader);
//        headers.setContentType(MediaType.TEXT_PLAIN);

        	return execution.execute(request, body);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}

    }

	public String getHeaderName() {
		return headerName;
	}

	public String getHeaderValue() {
		return headerValue;
	}

}
