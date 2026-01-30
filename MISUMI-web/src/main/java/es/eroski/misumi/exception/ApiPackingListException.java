package es.eroski.misumi.exception;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import es.eroski.misumi.model.pda.packingList.RdoApiPackingKO;

public class ApiPackingListException {

	private static Logger logger = Logger.getLogger(ApiPackingListException.class);
	private static String PALET_REMONTADO = "PALET_REMONTADO";
	
	public static RdoApiPackingKO ApiPackingListHandler(HttpClientErrorException e, String requestBody){
		
		String resultado = "";
		String codigoError = "";
		
        // Verificar el código de estado antes de deserializar el cuerpo
        HttpStatus statusCode = e.getStatusCode();
        int statusCodeValue = statusCode.value();
		String responseBody = e.getResponseBodyAsString();

		RdoApiPackingKO rdoRecepcionadoKO = new RdoApiPackingKO();
	    ObjectMapper objectMapper = new ObjectMapper();

        if (statusCode == HttpStatus.BAD_REQUEST) {
            // Hubo un error del cliente (código 4xx)
            logger.error("Error del cliente: " + statusCode);
            logger.error("Request Body: " + requestBody);
            logger.error("Response Body: " + responseBody);
    	} else if (statusCodeValue >= 500 && statusCodeValue < 600) {
            // Hubo un error del servidor (código 5xx)
    		logger.error("Error del servidor: " + statusCode);
            logger.error("Request Body: " + requestBody);
    		logger.error("Response Body: " + responseBody);
        } else {
            // Otros códigos de estado
        	logger.error("Código de estado: " + statusCode);
            logger.error("Request Body: " + requestBody);
        	logger.error("Response Body: " + e.getResponseBodyAsString());
        }

		try{
			Map<String, Object> mapRdoApiPackingList = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
			rdoRecepcionadoKO = objectMapper.convertValue(mapRdoApiPackingList, RdoApiPackingKO.class);
//	        tipoRespuesta = rdoRecepcionadoKO.getTipoRespuesta();
	        resultado = rdoRecepcionadoKO.getResultado();
			codigoError = rdoRecepcionadoKO.getCodigoError();
			
			// En la captura de la respuesta en caso de ERROR se contempla una excepción, sobre todo para la recepción de palet.
			// Si el codigo_error que devuelve el servicio tiene como valor "PALET_REMONTADO", sustituiremos el valor de la propiedad
			// 'respuesta' por la frase, "El palet está remontado en "+<valor campo 'resultado' del JSON>
			if (codigoError != null && !codigoError.isEmpty() && PALET_REMONTADO.equalsIgnoreCase(codigoError)){
				rdoRecepcionadoKO.setResultado("El palet est\u00E1 remontado en "+resultado);
			}
				
        } catch (JsonMappingException je) {
        	logger.error("Error al mapear JSON: " + je.getMessage());
        } catch (IOException ie) {
        	logger.error("Error de E/S: " + ie.getMessage());
        }

		return rdoRecepcionadoKO;
	}
	
}
