package es.eroski.misumi.service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ImprimirEtiquetaDao;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaRequestType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ImprimirEtiquetaResponseType;
import es.eroski.misumi.model.imprimirEtiquetaTiendaWS.ReferenciaRequestType;
import es.eroski.misumi.service.iface.ImprimirEtiquetaService;
import es.eroski.misumi.service.iface.NumeroEtiquetaImpresoraService;


@Service(value = "ImprimirEtiquetaService")
public class ImprimirEtiquetaServiceImpl implements ImprimirEtiquetaService {

    @Autowired
	private HttpSession httpSession;

	@Autowired
	private ImprimirEtiquetaDao imprimirEtiquetaDao;

	@Autowired
	private NumeroEtiquetaImpresoraService numeroEtiquetaImpresoraService;

	@Override
	 public ImprimirEtiquetaResponseType imprimirEtiquetaWS(ImprimirEtiquetaRequestType imprimirEtiquetaRequest) throws Exception{
			// TODO Auto-generated method stub
		return this.imprimirEtiquetaDao.imprimirEtiquetaWS(imprimirEtiquetaRequest);
	}


	@Override
	public ImprimirEtiquetaRequestType createImprimirEtiquetaRequestType() throws Exception {
		ImprimirEtiquetaRequestType imprimirEtiquetarequest = new ImprimirEtiquetaRequestType();
		
		//Obtenemos de sesión la información de usuario.
		User user = (User)httpSession.getAttribute("user");

		String macPrehueco=user.getMacPrehueco();
		String mac=user.getMac();
		
		mac = (macPrehueco!=null && !macPrehueco.isEmpty()?macPrehueco:mac);
		
		//Codigo de centro
		imprimirEtiquetarequest.setCodigoCentro(BigInteger.valueOf(user.getCentro().getCodCentro()));
		
		//Buzón
		if (StringUtils.isNotBlank(user.getBuzon())){
			//Sólo seteamos el campo buzon cuando este relleno.
			//Si es null o cadena vacía entonces no lo seteamos
			//para que en el XML de envío al web service no vaya
			//el campo buzon por que si lo enviamos y está vacío
			//nos dará un error. 
			//Para que el campo buzon no vaya en el XML cuando es null
			//lo conseguimos poniendo la propiedad MinOccurs=0 para
			//el campo buzon en el objeto ImprimirEtiquetaRequestType.
			imprimirEtiquetarequest.setBuzon(user.getBuzon());
		}
		
		//IdPistola
		imprimirEtiquetarequest.setIdPistola(user.getIdPistola());

		//Lista de referencias
		HashMap<String, Integer> mapReferencias = numeroEtiquetaImpresoraService.getMapNumEtiquetaInBBDD(user.getCentro().getCodCentro(),mac);
		ArrayList<ReferenciaRequestType> listaRef = new ArrayList<ReferenciaRequestType>(); 
		Iterator<Entry<String, Integer>> it = mapReferencias.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> e = it.next();
			ReferenciaRequestType referencia = new ReferenciaRequestType();
			
			//Código de la referencia
			referencia.setCodigoReferencia(new BigInteger(e.getKey()));

			//Cantidad para la referencia
			referencia.setCantidad(new BigInteger(e.getValue().toString()));
			
			listaRef.add(referencia);
		}
		ReferenciaRequestType[] referencias = new ReferenciaRequestType[listaRef.size()];
		referencias = listaRef.toArray(referencias);
		imprimirEtiquetarequest.setReferencias(referencias);

		return imprimirEtiquetarequest;
	}


	@Override
	public boolean isOkImprimirEtiquetaResponse(ImprimirEtiquetaResponseType imprimirEtiquetaResponse) throws Exception {
		boolean resultado = false;
		if (imprimirEtiquetaResponse != null && imprimirEtiquetaResponse.getCodigoRespuesta() != null && imprimirEtiquetaResponse.getCodigoRespuesta().equals("OK")){
			resultado = true;
		}
		return resultado;
	}
	
}
