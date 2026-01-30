package es.eroski.misumi.service.packingList;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.eroski.misumi.config.HeaderRequestInterceptor;
import es.eroski.misumi.dao.packingList.iface.ReferenciaDao2;
import es.eroski.misumi.exception.ApiPackingListException;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.packingList.RdoApiPackingKO;
import es.eroski.misumi.model.pda.packingList.RdoListarArticulosWrapper;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoOK;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoWrapper;
import es.eroski.misumi.service.packingList.iface.ConsultaMatriculaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.WSBManager;

@Service
public class ConsultaMatriculaServiceImpl implements ConsultaMatriculaService{

	@Autowired
	private RestTemplate restTemplate;

	URI uri = null;

    private final String urlApiPackingList = WSBManager.getProperty("ws.packingListVega");


	@Override
	public RdoValidarCecoWrapper validarCeco(String ceco) {

		RdoValidarCecoOK rdoValidarCecoOK = new RdoValidarCecoOK();
		RdoApiPackingKO rdoValidarCecoKO = new RdoApiPackingKO();

		String tipoRespuesta = "";

		uri = UriComponentsBuilder.fromHttpUrl(urlApiPackingList + "/validarCeco").queryParam("ceco", ceco).build()
				.toUri();
		final String contenido = "{\"ceco\":\"" + ceco + "\"}";

		try {

			rdoValidarCecoOK = this.restTemplate.execute(uri, HttpMethod.GET, new RequestCallback() {
				@Override
				public void doWithRequest(ClientHttpRequest request) throws IOException {
					request.getHeaders().setContentLength(contenido.length());
					// Escribir el cuerpo de la solicitud
					OutputStreamWriter writer = new OutputStreamWriter(request.getBody(), "UTF-8");
					writer.write(contenido);
					writer.flush();
					writer.close();
				}
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

			tipoRespuesta = rdoValidarCecoOK.getTipoRespuesta();

			if (Constantes.PACKING_LIST_OK.equals(tipoRespuesta)) {
				String token = rdoValidarCecoOK.getToken();
				this.restTemplate.getInterceptors().add(new HeaderRequestInterceptor("X-APP-KEY", token));
			}

		} catch (HttpClientErrorException e) {
			rdoValidarCecoKO = ApiPackingListException.ApiPackingListHandler(e, contenido);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new RdoValidarCecoWrapper(rdoValidarCecoOK, rdoValidarCecoKO);
	}
    
	
	@Override
	public RdoListarArticulosWrapper listarArticulosPorMatricula(String matricula, String ceco) {

		RdoValidarCecoWrapper respuestaCeco = validarCeco(ceco);
		RdoValidarCecoOK rdoValidarCecoOK = respuestaCeco.getRdoValidarCecoOK();

		String token = null;
		if (rdoValidarCecoOK != null && Constantes.PACKING_LIST_OK.equals(rdoValidarCecoOK.getTipoRespuesta())) {
			token = rdoValidarCecoOK.getToken();
		} else {

			throw new RuntimeException("No se pudo obtener el token para la validación del ceco.");
		}

		if (token != null) {
			this.restTemplate.getInterceptors().add(new HeaderRequestInterceptor("X-APP-KEY", token));
		}


		String uri = UriComponentsBuilder.fromHttpUrl(urlApiPackingList + "/listarArticulos")
				.queryParam("matricula", matricula) 
				.build().toUriString(); 
		try {
			return this.restTemplate.getForObject(uri, RdoListarArticulosWrapper.class);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al obtener la lista de artículos", e);
		}
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
