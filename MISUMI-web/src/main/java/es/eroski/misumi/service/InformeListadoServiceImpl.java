package es.eroski.misumi.service;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VPescaMostradorDao;
import es.eroski.misumi.dao.iface.VPescaMostradorSIADao;
import es.eroski.misumi.model.InformeListado;
import es.eroski.misumi.model.InformeListadoPesca;
import es.eroski.misumi.model.PescaPedirHoy;
import es.eroski.misumi.model.VPescaMostrador;
import es.eroski.misumi.service.iface.InformeListadoService;
import es.eroski.misumi.service.iface.InventarioRotativoGisaeService;


@Service(value = "informeListadoService")
public class InformeListadoServiceImpl implements InformeListadoService {

	@Autowired
	private InventarioRotativoGisaeService inventarioRotativoGisaeService;

	@Autowired
	private VPescaMostradorDao vPescaMostradorDao;

	@Autowired
	private VPescaMostradorSIADao vPescaMostradorSIADao;

	@Override
	public Long getInformeListadoCount(Long codCentro) throws Exception{
		Long contador = (long) 0;

		VPescaMostrador vPescaMostrador = new VPescaMostrador();
		vPescaMostrador.setCodCentro(codCentro);

		contador = contador + this.vPescaMostradorDao.getInformeListadoPescaCount(vPescaMostrador);

		return contador;
	}

	@Override
	public List<InformeListadoPesca> findAllInformeListadoPesca(Long codCentro) throws Exception {
		//En este método se tratan todos los informes.
		//Se guardan en una lista todos los códigos de los informes para mostrarlos. 

		List<InformeListadoPesca> listaInformes = new ArrayList<InformeListadoPesca>();

		VPescaMostrador vPescaMostrador = new VPescaMostrador();
		vPescaMostrador.setCodCentro(codCentro);

		listaInformes = this.vPescaMostradorDao.findAllInformeListadoPesca(vPescaMostrador);

		return listaInformes;
	}

	@Override
	public List<InformeListadoPesca> findAllInformeListadoPesca(Long codCentro,String flgHabitual) throws Exception {
		//En este método se tratan todos los informes.
		//Se guardan en una lista todos los códigos de los informes para mostrarlos. 

		List<InformeListadoPesca> listaInformes = new ArrayList<InformeListadoPesca>();

		VPescaMostrador vPescaMostrador = new VPescaMostrador();
		vPescaMostrador.setCodCentro(codCentro);
		vPescaMostrador.setFlgHabitual(flgHabitual);

		listaInformes = this.vPescaMostradorDao.findAllInformeListadoPesca(vPescaMostrador);

		return listaInformes;
	}

	@Override
	public List<VPescaMostrador> findAllVPescaMostrador(Long codCentro, List<String> listaSubcategoria) throws Exception {

		//En este método se tratan todos los informes de una lista de subcategorias.
		//Se guardan en una lista todos los códigos de los informes de un centro. 

		List<VPescaMostrador> listaVPescaMostrador = this.vPescaMostradorDao.findAll(codCentro, listaSubcategoria);

		return listaVPescaMostrador;
	}

	@Override
	public List<VPescaMostrador> findAllVPescaMostrador(Long codCentro, String idSubcategoria, String flgHabitual)
			throws Exception {
		//En este método se tratan todos los informes de una subcategoria.
		//Se guardan en una lista todos los códigos de los informes de una subcategoria para mostrarlos. 

		List<VPescaMostrador> listaVPescaMostrador = new ArrayList<VPescaMostrador>();

		VPescaMostrador vPescaMostrador = new VPescaMostrador();
		vPescaMostrador.setCodCentro(codCentro);
		vPescaMostrador.setIdentificaSubcat(idSubcategoria);
		vPescaMostrador.setFlgHabitual(flgHabitual);

		listaVPescaMostrador = this.vPescaMostradorDao.findAll(vPescaMostrador);

		return listaVPescaMostrador;
	}

	@Override
	public InformeListado obtenerInformeListado(Long codCentro) throws Exception {
		InformeListado informeListado = new InformeListado();

		// Miramos si existe informe huecos
		informeListado.setInformeHuecosCount(this.inventarioRotativoGisaeService.getInformeHuecosCount(codCentro));

		// Miramos si existe informe pesca listado
		informeListado.setListaInformeListadoPesca(this.findAllInformeListadoPesca(codCentro));

		if ((informeListado.getInformeHuecosCount() != null && informeListado.getInformeHuecosCount() > 0) || (informeListado.getListaInformeListadoPesca() != null &&  informeListado.getListaInformeListadoPesca().size() > 0)){
			informeListado.setExiste(true);
		} else {
			informeListado.setExiste(false);
		}

		return informeListado;
	}


	@Override
	public InformeListado obtenerInformeListado(Long codCentro,String flgHabitual) throws Exception {
		InformeListado informeListado = new InformeListado();

		// Miramos si existe informe huecos
		informeListado.setInformeHuecosCount(this.inventarioRotativoGisaeService.getInformeHuecosCount(codCentro));

		if(flgHabitual != null){
			// Miramos si existe informe pesca listado
			informeListado.setListaInformeListadoPesca(this.findAllInformeListadoPesca(codCentro,flgHabitual));
		}

		if ((informeListado.getInformeHuecosCount() != null && informeListado.getInformeHuecosCount() > 0) || (informeListado.getListaInformeListadoPesca() != null &&  informeListado.getListaInformeListadoPesca().size() > 0)){
			informeListado.setExiste(true);
		} else {
			informeListado.setExiste(false);
		}

		return informeListado;
	}

	@Override
	public String obtenerDescSubcategoria(Long codCentro, String idSubcategoria) throws Exception {
		//En este método se tratan todos los informes.
		//Se guardan en una lista todos los códigos de los informes para mostrarlos. 

		StringBuilder sbDescSubcategoria = new StringBuilder("");

		List<InformeListadoPesca> listaInformes = new ArrayList<InformeListadoPesca>();

		VPescaMostrador vPescaMostrador = new VPescaMostrador();
		vPescaMostrador.setCodCentro(codCentro);
		vPescaMostrador.setIdentificaSubcat(idSubcategoria);

		listaInformes = this.vPescaMostradorDao.findAllInformeListadoPesca(vPescaMostrador);

		if (listaInformes != null && listaInformes.size() >= 1){
			InformeListadoPesca informe = listaInformes.get(0);
			sbDescSubcategoria.append(informe.getIdentificaSubcat()).append(" ").append(informe.getDescSubcategoria());
		}

		return sbDescSubcategoria.toString();
	}

	@Override
	public PescaPedirHoy findPescaPedirHoy(Long codCentro, List<VPescaMostrador> listaPesca) throws Exception {

		PescaPedirHoy resultadoPescaPedirHoy = null;

		//Buscamos las referencias del listado que tengan CANTIDAD_PENDIENTE PARA HOY <> 0.
		List<VPescaMostrador> listaPescaPendientesDistintoCero = new ArrayList<VPescaMostrador>();
		for (int i = 0, tamano = listaPesca.size(); i < tamano ; i++){
			VPescaMostrador pesca = listaPesca.get(i);
			if (pesca != null && pesca.getPedMananaCajas() != null && pesca.getPedMananaCajas().longValue() != 0){
				listaPescaPendientesDistintoCero.add(pesca);
			}
		}

		//Llamamos al servicio para saber los pedidos de hoy
		if (listaPescaPendientesDistintoCero != null && listaPescaPendientesDistintoCero.size() > 0){
			PescaPedirHoy pescaPedirHoy = new PescaPedirHoy();
			pescaPedirHoy.setCodCentro(codCentro);
			pescaPedirHoy.setListaPesca(listaPescaPendientesDistintoCero);
			resultadoPescaPedirHoy = vPescaMostradorSIADao.findPescaPedirHoy(pescaPedirHoy);
		}
		else{
			//En este caso no hay referencias que tengan CANTIDAD_PENDIENTE PARA HOY <>0
			//Ponemos codError a 0 para devolver un resultado OK.
			resultadoPescaPedirHoy = new PescaPedirHoy();
			resultadoPescaPedirHoy.setCodCentro(codCentro);
			resultadoPescaPedirHoy.setListaPesca(null);
			resultadoPescaPedirHoy.setCodError(0L);
			resultadoPescaPedirHoy.setDescError(null);
		}

		return resultadoPescaPedirHoy;
	}

	@Override
	public List<VPescaMostrador> searchPescaPedirHoy(List<VPescaMostrador> listaPesca, PescaPedirHoy pescaPedirHoy) throws Exception {
		//Si no se ha producido ningún error recorremos la lista devuelta por el servicio
		//para saber que referencias son para hoy y marcarlos como PedirHoy=true
		if (listaPesca != null && pescaPedirHoy != null && pescaPedirHoy.getListaPesca() != null && pescaPedirHoy.getCodError() != null && pescaPedirHoy.getCodError().longValue() == 0){
			for (VPescaMostrador resultadoPesca : pescaPedirHoy.getListaPesca()){
				for (VPescaMostrador pesca : listaPesca){
					if (pesca != null && resultadoPesca != null && pesca.getCodArt() != null && resultadoPesca.getCodArt() != null && pesca.getCodArt().longValue() == resultadoPesca.getCodArt().longValue()){
						pesca.setPedirHoy(true);
						break;
					}
				}
			}
		}

		return listaPesca;
	}

	@Override
	public List<String> listaCodigosHabituales(Long codCentro, List<String> listaSubcategoria) throws Exception {
		//Devuelve los códigos que existen en la columna V_PESCA_MOSTRADOR.FLG_HABITUAL
		//para un centro y una lista de subcategorias dada.
		List<String> listaCodHab = this.vPescaMostradorDao.listaCodigosHabituales(codCentro, listaSubcategoria);

		return listaCodHab;
	}

	@Override
	public Long countAllInformeListadoPesca(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		return this.vPescaMostradorDao.countAllInformeListadoPesca(codCentro);
	}

}
