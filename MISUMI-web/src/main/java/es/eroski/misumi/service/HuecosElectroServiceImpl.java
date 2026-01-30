/**
 * 
 */
package es.eroski.misumi.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.HuecosElectroDao;
import es.eroski.misumi.service.iface.HuecosElectroService;

/**
 * @author BICUGUAL
 *
 */
@Service
public class HuecosElectroServiceImpl implements HuecosElectroService{

	private static Logger logger = Logger.getLogger(HuecosElectroServiceImpl.class);
	
	@Autowired
	public HuecosElectroDao huecosDao;
	
	

	@Override
	public Integer getNumHuecosFinalSeg(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3, Long codGrupo4,
			Long codGrupo5) throws Exception {
		Integer numHuecos=null;
		try {
			numHuecos=huecosDao.getHuecosFinalSegByCodCentro(codCentro, codGrupo1, codGrupo2, codGrupo3, codGrupo4, codGrupo5);
		}
		catch(EmptyResultDataAccessException erdaex){
			System.out.println(erdaex);
			return null;
		}
		catch(Exception e){
			logger.info("Error al realizar la busqueda de huecos:"+" "+e);
		}
		return numHuecos;
	}
	
	@Override
	public Integer getNumHuecosFinalSubCat(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3, Long codGrupo4) throws Exception {
		Integer numHuecos=null;
		try {
			numHuecos=huecosDao.getHuecosFinalSubCatByCodCentro(codCentro, codGrupo1, codGrupo2, codGrupo3, codGrupo4);
		}
		catch(EmptyResultDataAccessException erdaex){
			System.out.println(erdaex);
			return null;
		}
		catch(Exception e){
			logger.info("Error al realizar la busqueda de huecos:"+" "+e);
		}
		return numHuecos;
	}
	
	
	
}
