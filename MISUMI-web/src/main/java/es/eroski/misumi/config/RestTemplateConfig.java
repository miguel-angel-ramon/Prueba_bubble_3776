package es.eroski.misumi.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.WSBManager;

@Component
public class RestTemplateConfig {

//    private final String username = WSBManager.getProperty("ws.user2");
//    private final String password = WSBManager.getProperty("ws.pwd3");

    @Bean
    public RestTemplate restTemplate() {
    	RestTemplate restTemplate = new RestTemplate();

//        String auth = username + ":" + password;
//        String encodedAuth = DatatypeConverter.printBase64Binary(auth.getBytes());
//        String authHeader = "Basic " + new String(encodedAuth);
//
//        // Configurar los interceptores para los encabezados Authorization y X-APP-KEY
    	List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    	if (interceptors == null) {
    		interceptors = new ArrayList<ClientHttpRequestInterceptor>();
    	}
//    	interceptors.add(new HeaderRequestInterceptor("Authorization",authHeader)); // Interceptor para Authorization (fijo)
//    	
//    	 // Interceptor para Authorization (fijo)
//    	restTemplate.setInterceptors(interceptors);

        interceptors.add(new AuthorizationInterceptor()); // Interceptor para Authorization (fijo)
        interceptors.add(new XAppKeyInterceptor()); // Interceptor para X-APP-KEY (dinámico)

    	return restTemplate;

    }	

    // Interceptor para el encabezado de autorización (siempre el mismo)
    private static class AuthorizationInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public org.springframework.http.client.ClientHttpResponse intercept(
            org.springframework.http.HttpRequest request, 
            byte[] body, 
            org.springframework.http.client.ClientHttpRequestExecution execution) throws java.io.IOException {

            String authHeader = getAuthHeader(); // Obtener el encabezado de autorización
            request.getHeaders().set("Authorization", authHeader);  // Establecer el encabezado de autorización

            return execution.execute(request, body);
        }

        private String getAuthHeader() {
            // El encabezado de autorización es siempre el mismo
            String auth = WSBManager.getProperty("ws.user2") + ":" + WSBManager.getProperty("ws.pwd3");
            return "Basic " + new String(DatatypeConverter.printBase64Binary(auth.getBytes()));
        }
    }
    
    // Interceptor para el encabezado X-APP-KEY (dinámico, recuperado de la sesión HTTP)
    private static class XAppKeyInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public org.springframework.http.client.ClientHttpResponse intercept(
            org.springframework.http.HttpRequest request, 
            byte[] body, 
            org.springframework.http.client.ClientHttpRequestExecution execution) throws java.io.IOException {

            String appKeyHeader = getAppKeyFromSession(); // Obtener el X-APP-KEY de la sesión
            request.getHeaders().set(Constantes.X_APP_KEY, appKeyHeader);  // Establecer el encabezado X-APP-KEY

            return execution.execute(request, body);
        }

        private String getAppKeyFromSession() {
            // Obtener el valor del X-APP-KEY desde la sesión HTTP actual
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            
            // Suponiendo que el valor de X-APP-KEY está en la sesión
            String appKey = (String) request.getSession().getAttribute(Constantes.X_APP_KEY);

            // Si no se encuentra el valor en la sesión, puedes lanzar una excepción o manejar el caso
            if (appKey == null) {
            	appKey="";
//                throw new RuntimeException("No se ha encontrado el encabezado X-APP-KEY en la sesión.");
            }
            return appKey;
        }
    }

}
