package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eroski.misumi.dao.iface.ArtGamaRapidDao;
import es.eroski.misumi.dao.iface.KosmosDao;
import es.eroski.misumi.model.ArtGamaRapid;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ListadoGamaRapidService;

@Service(value = "ListadoGamaRapidService")
public class ListadoGamaRapidServiceImpl implements ListadoGamaRapidService {

	@Autowired
	private ArtGamaRapidDao artGamaRapidDao;
	
	@Autowired
	private KosmosDao kosmosDao;
	
	@Override
	@Transactional
	public void cargarDatosCentro(Long codCentro) throws Exception {
		if (!artGamaRapidDao.centroCargado(codCentro)){
			List<ArtGamaRapid> articulos = artGamaRapidDao.obtenerDatosCentro(codCentro);
			OfertaPVP ofertaPVP;
			for (ArtGamaRapid articulo: articulos){
				//Completo precios
				ofertaPVP = new OfertaPVP(articulo.getCodArticulo(), articulo.getCodCentro(), new Date());			
				ofertaPVP = kosmosDao.obtenerDatosPVP(ofertaPVP);

				//Si el procedimiento devuelve null, no se trata.
				if(ofertaPVP != null){
					articulo.setPrecioCosto(kosmosDao.obtenerCosto(ofertaPVP));
					articulo.setPvp(ofertaPVP.getTarifa());
					articulo.setPvpOferta(ofertaPVP.getPvpOfer());
				}
				
				// Cargo articulo
				//artGamaRapidDao.cargarArticuloCentro(articulo);
			}
			// Cargo articulos
			artGamaRapidDao.cargarArticulosCentro(articulos);
		}
	}

	@Override
	public List<ArtGamaRapid> findAll(ArtGamaRapid artGamaRapid, Pagination pagination) throws Exception {
		
		// De momento de forma asincrona la cargar el centro.
		// Comprobamos si hay registro para el dia de hoy
		// cargarDatosCentro(artGamaRapid.getCodCentro());

		List<ArtGamaRapid> lista = new ArrayList<ArtGamaRapid>();

		lista = this.artGamaRapidDao.findAll(artGamaRapid, pagination);

		return lista;
	}

	@Override
	public Long findAllCont(ArtGamaRapid artGamaRapid) throws Exception {
		return this.artGamaRapidDao.findAllCont(artGamaRapid);
	}

	@Override
	public List<GenericExcelVO> findAllExcel(ArtGamaRapid artGamaRapid, String[] columnModel) throws Exception {
		List<GenericExcelVO> lista = this.artGamaRapidDao.findAllExcel(artGamaRapid, columnModel);

		return lista;
	}

	@Override
	public List<ArtGamaRapid> findArticulo(ArtGamaRapid artGamaRapid) throws Exception {
		Pagination pagination = null;
		List<ArtGamaRapid> lista = this.artGamaRapidDao.findAll(artGamaRapid, pagination);

		return lista;
	}

}
