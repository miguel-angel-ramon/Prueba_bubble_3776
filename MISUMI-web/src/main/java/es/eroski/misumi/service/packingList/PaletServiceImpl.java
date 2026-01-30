package es.eroski.misumi.service.packingList;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.CharSetUtils;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.eroski.misumi.config.HeaderRequestInterceptor;
import es.eroski.misumi.dao.packingList.iface.PaletDao;
import es.eroski.misumi.exception.ApiPackingListException;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.packingList.RdoListarMatriculas;
import es.eroski.misumi.model.pda.packingList.RdoApiPackingKO;
import es.eroski.misumi.model.pda.packingList.RdoRecepcionadoOK;
import es.eroski.misumi.model.pda.packingList.RdoRecepcionadoWrapper;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoOK;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoWrapper;
import es.eroski.misumi.service.packingList.iface.PaletService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.WSBManager;

@Service
public class PaletServiceImpl implements PaletService{

	@Autowired
	private PaletDao paletDao;

	@Autowired
	private RestTemplate restTemplate;

	URI uri = null;

    private final String urlApiPackingList = WSBManager.getProperty("ws.packingListVega");

	@Override
	public List<Palet> getEntradasPalets(Long centro, String mac) throws SQLException{
		return paletDao.getEntradasPalets(centro, mac);
	}

	@Override
	public void saveEntradaPalet(Palet palet, String mac){
		paletDao.saveEntradaPalet(palet, mac);
	}
	
	@Override
	public RdoValidarCecoWrapper validarCeco(String ceco){
		
		RdoValidarCecoOK rdoValidarCecoOK = new RdoValidarCecoOK();
		RdoApiPackingKO rdoValidarCecoKO = new RdoApiPackingKO();

        String tipoRespuesta = "";

//		uri = UriComponentsBuilder.fromHttpUrl("https://packinglistapiprueba.vegalsa.es/validarCeco")
		uri = UriComponentsBuilder.fromHttpUrl(urlApiPackingList+"/validarCeco")
        		.queryParam("ceco", ceco)
        		.build()
        		.toUri();
		final String contenido = "{\"ceco\":\"" + ceco + "\"}";

        // Verificar si el encabezado X-APP-KEY está presente
//        if (!hasAppKeyHeader()) {

			try{
	//			rdoValidarCeco = this.restTemplate.getForObject(uri, RdoValidarCeco.class);
	
	//				String url = "https://packinglistapiprueba.vegalsa.es/recepcionarPalet?matricula=612801774";

			    // Realizar la solicitud PUT
				rdoValidarCecoOK = this.restTemplate.execute(uri, HttpMethod.GET, new RequestCallback() {
			            @Override
			            public void doWithRequest(ClientHttpRequest request) throws IOException {
			                request.getHeaders().setContentLength(contenido.length());
			                // Escribir el cuerpo de la solicitud
			                OutputStreamWriter writer = new OutputStreamWriter(request.getBody(), "UTF-8");
			                writer.write(contenido);
			                writer.flush();
			                writer.close();		            }
				        }, new ResponseExtractor<RdoValidarCecoOK>() {
				            @Override
				            public RdoValidarCecoOK extractData(ClientHttpResponse response) throws IOException {
				                ObjectMapper objectMapper = new ObjectMapper();
				                // Manejar la respuesta
				                if (response.getStatusCode() == HttpStatus.OK) {
				                    System.out.println("Respuesta exitosa");
				                    return objectMapper.readValue(response.getBody(), RdoValidarCecoOK.class);
				                } else {
				                	throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
				                }
				            }
				        });
	
		        // Obtener el cuerpo de la respuesta
	//			rdoRecepcionado = responseRecepcionado.getBody();
//		        tipoRespuesta = rdoValidarCecoOK.getTipoRespuesta();
//	
//				if (Constantes.PACKING_LIST_OK.equals(tipoRespuesta)){
//					String token = rdoValidarCecoOK.getToken();
//					
////					boolean alreadyAdded = false;
////
////					for (ClientHttpRequestInterceptor interceptor : this.restTemplate.getInterceptors()) {
////					    if (interceptor instanceof HeaderRequestInterceptor) {
////					        HeaderRequestInterceptor headerInterceptor = (HeaderRequestInterceptor) interceptor;
////					        if (headerInterceptor.getHeaderName().equals("X-APP-KEY")) {
////					            alreadyAdded = true;
////					            break;
////					        }
////					    }
////					}
////					
////					if (!alreadyAdded) {
////					    this.restTemplate.getInterceptors().clear();  // Limpiar interceptores previos.
////					    this.restTemplate.getInterceptors().add(new HeaderRequestInterceptor("X-APP-KEY", token));  // Agregar el nuevo interceptor
////					}
//		        }
				
			}catch (HttpClientErrorException e) {
				rdoValidarCecoKO = ApiPackingListException.ApiPackingListHandler(e, contenido);
			}catch (Exception e){
				e.printStackTrace();
			}
		// Si ya existe el Token, no es necesario volver a validar el ceco.
//        }else{
//        	rdoValidarCecoOK.setTipoRespuesta(Constantes.PACKING_LIST_OK);
//        }
			
		return new RdoValidarCecoWrapper(rdoValidarCecoOK, rdoValidarCecoKO);
    }
    
	@Override
    public RdoRecepcionadoWrapper recepcionarPalet(String matricula, String sesion) throws Exception {

        RdoRecepcionadoOK rdoRecepcionadoOK = new RdoRecepcionadoOK();
		RdoApiPackingKO rdoRecepcionadoKO = new RdoApiPackingKO();

        String tipoRespuesta = "";

//		uri = UriComponentsBuilder.fromHttpUrl("https://packinglistapiprueba.vegalsa.es/recepcionarPalet")
		uri = UriComponentsBuilder.fromHttpUrl(urlApiPackingList+"/recepcionarPalet")
        		.queryParam("matricula", matricula)
        		.build()
        		.toUri();

		final String contenido = "{\"matricula\":\"" + matricula + "\"}";

		try{

//		    this.restTemplate.getInterceptors().clear();  // Limpiar interceptores previos.
//	        List<ClientHttpRequestInterceptor> interceptors = this.restTemplate.getInterceptors();
//
//	     // Si la lista está vacía, agregar el nuevo interceptor.
//	        if (interceptors.size() == 0) {
//	            interceptors.add(new HeaderRequestInterceptor("X-APP-KEY", sesion));
//	        } else {
//	            // Si ya existe al menos un interceptor, puedes actualizar el existente.
//	            boolean found = false;
//	            for (int i = 0; i < interceptors.size(); i++) {
//	                if (interceptors.get(i) instanceof HeaderRequestInterceptor) {
//	                    HeaderRequestInterceptor headerInterceptor = (HeaderRequestInterceptor) interceptors.get(i);
//	                    if ("X-APP-KEY".equals(headerInterceptor.getHeaderName())) {
//	                        // Actualizar el interceptor
//	                        interceptors.set(i, new HeaderRequestInterceptor("X-APP-KEY", sesion));
//	                        found = true;
//	                        break;
//	                    }
//	                }
//	            }
//	            // Si no se encontró el interceptor, agregamos uno nuevo.
//	            if (!found) {
//	                interceptors.add(new HeaderRequestInterceptor("X-APP-KEY", sesion));
//	            }
//	        }
//
//	        this.restTemplate.setInterceptors(interceptors);

	        // Realizar la solicitud PUT
			rdoRecepcionadoOK = restTemplate.execute(uri, HttpMethod.PUT, new RequestCallback() {
	            @Override
	            public void doWithRequest(ClientHttpRequest request) throws IOException {
	                request.getHeaders().setContentLength(contenido.length());
	                // Escribir el cuerpo de la solicitud
	                OutputStreamWriter writer = new OutputStreamWriter(request.getBody(), "UTF-8");
	                writer.write(contenido);
	                writer.flush();
	                writer.close();		            }
	        }, new ResponseExtractor<RdoRecepcionadoOK>() {
	            @Override
	            public RdoRecepcionadoOK extractData(ClientHttpResponse response) throws IOException {
	                ObjectMapper objectMapper = new ObjectMapper();
	                // Manejar la respuesta
	                if (response.getStatusCode() == HttpStatus.OK) {
	                    System.out.println("Respuesta exitosa");
	                    return objectMapper.readValue(response.getBody(), RdoRecepcionadoOK.class);
	                } else {
	                	throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
	                }
	            }
	        });

	        // Obtener el cuerpo de la respuesta
//			rdoRecepcionado = responseRecepcionado.getBody();
	        tipoRespuesta = rdoRecepcionadoOK.getTipoRespuesta();

			if (Constantes.PACKING_LIST_OK.equals(tipoRespuesta)){
	        }

		}catch (HttpServerErrorException se){
			throw new HttpServerErrorException(se.getStatusCode(), se.getStatusText());
		}catch (HttpClientErrorException e) {
			rdoRecepcionadoKO = ApiPackingListException.ApiPackingListHandler(e, contenido);
		}catch (Exception e){
			throw new Exception(e);
		}

		return new RdoRecepcionadoWrapper(rdoRecepcionadoOK, rdoRecepcionadoKO);
	}
    
	@Override
	public String listarArticulos(int numRows, int page, String code, String description, String matricula, String startDate, String endDate){
		RdoListarMatriculas rdoListarMatriculas = new RdoListarMatriculas();
		
		RdoApiPackingKO rdoListarArticulosKO = new RdoApiPackingKO();

		String contenido="";
		
		uri = UriComponentsBuilder.fromHttpUrl(urlApiPackingList+"/listarArticulos")
        		.queryParam("numRows", numRows)
        		.queryParam("page", page)
        		.queryParam("code", code)
        		.queryParam("description", description)
        		.queryParam("matricula", matricula)
        		.queryParam("startDate", startDate)
        		.queryParam("endDate", endDate)
        		.build()
        		.toUri();
		try{
			rdoListarMatriculas = this.restTemplate.getForObject(uri, RdoListarMatriculas.class);			
			if (Constantes.PACKING_LIST_OK.equals(rdoListarMatriculas.getTipoRespuesta())){
			}
		}catch (HttpClientErrorException e) {
			rdoListarArticulosKO = ApiPackingListException.ApiPackingListHandler(e,contenido);
		}catch (Exception e){
			e.printStackTrace();
		}

//		return new RdoRecepcionadoWrapper(rdoListarArticulosOK, rdoListarArticulosKO);
		return rdoListarMatriculas.getTipoRespuesta();
    }

	/**
	 * Permite conocer si se ha añadido el token en el interceptor del cliente Rest.
	 * @return
	 */
	private boolean hasAppKeyHeader() {
	    List<ClientHttpRequestInterceptor> interceptors = this.restTemplate.getInterceptors();
	    for (ClientHttpRequestInterceptor interceptor : interceptors) {
	        if (interceptor instanceof HeaderRequestInterceptor) {
	            HeaderRequestInterceptor headerInterceptor = (HeaderRequestInterceptor) interceptor;
	            if ("X-APP-KEY".equals(headerInterceptor.getHeaderName())) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
}
