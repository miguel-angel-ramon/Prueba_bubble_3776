package es.eroski.misumi.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eroski.misumi.dao.iface.CestasNavidadArticuloDao;
import es.eroski.misumi.dao.iface.CestasNavidadDao;
import es.eroski.misumi.model.CestasNavidad;
import es.eroski.misumi.model.CestasNavidadArticulo;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.CestasNavidadService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;

@Service(value = "CestasNavidadService")
public class CestasNavidadServiceImpl implements CestasNavidadService {

	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Resource 
	private MessageSource messageSource;
	@Autowired
	private CestasNavidadDao cestasNavidadDao;

	@Autowired
	private CestasNavidadArticuloDao cestasNavidadArticuloDao;

	@Override
	public List<CestasNavidad> findAll(Long codCentro, boolean esCentroCaprabo) throws Exception {
		return this.cestasNavidadDao.findAll(codCentro, esCentroCaprabo);
	}

	@Override
	public CestasNavidad findOne(Long codCentro,Long codArtLote, boolean esCentroCaprabo) throws Exception {
		return this.cestasNavidadDao.findOne(codCentro, codArtLote, esCentroCaprabo);
	}

	@Override
	public String findDescrition(Long codArtLote) throws Exception {
		return this.cestasNavidadDao.findDescription(codArtLote);
	}

	@Override
	public List<CestasNavidad> findAll(Pagination pagination) throws Exception {
		return this.cestasNavidadDao.findAll(pagination);
	}

	@Override
	public int updateBorradoLinea(CestasNavidad cestasNavidad) throws Exception {
		// TODO Auto-generated method stub
		return this.cestasNavidadDao.updateBorradoLinea(cestasNavidad);
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return this.cestasNavidadDao.delete();
	}

	@Override
	public List<CestasNavidadArticulo> findAllCestasNavidadArticulo(Long codArtLote) throws Exception {
		// TODO Auto-generated method stub
		return this.cestasNavidadArticuloDao.findAll(codArtLote);
	}

	@Override
	@Transactional(value="transactionManagerMisumi", rollbackFor= {Exception.class})
	public String updateLinea(CestasNavidad cestasNavidad) throws Exception {
		// TODO Auto-generated method stub
		//Ponemos que la fecha de actualización es ahora
		cestasNavidad.setLastUpdate(new Date());

		int codError = this.cestasNavidadDao.updateLinea(cestasNavidad);		
		if(codError == 0 && cestasNavidad.getLstModificados() != null && cestasNavidad.getLstModificados().size() > 0){
			codError = this.cestasNavidadArticuloDao.updateArticuloLote(cestasNavidad.getLstModificados());
		}

		if(codError == 0  && cestasNavidad.getLstNuevos() != null && cestasNavidad.getLstNuevos().size() > 0){
			codError = this.cestasNavidadArticuloDao.newArticuloLote(cestasNavidad.getLstNuevos());
		}

		if(codError == 0  && cestasNavidad.getLstBorrados() != null && cestasNavidad.getLstBorrados().size() > 0){
			codError = this.cestasNavidadArticuloDao.deleteArticuloLote(cestasNavidad.getLstBorrados());
		}

		//Si ha habido error, lanzamos excepcion para que haya rollback
		if(codError > 0){
			throw new Exception(msgError(codError));
		}
		return msgError(codError);
	}

	@Override
	@Transactional(value="transactionManagerMisumi", rollbackFor= {Exception.class})
	public String insertLinea(CestasNavidad cestasNavidad) throws Exception{
		// TODO Auto-generated method stub
		VDatosDiarioArt vDatosDiarioArtRes = null;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();

		vDatosDiarioArt.setCodArt(cestasNavidad.getCodArtLote());
		vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);

		int codError = 0;

		//Si existe la referencia, creamos el lote. Si no, devolvemos error.
		if(vDatosDiarioArtRes != null){
			codError = this.cestasNavidadDao.insertLinea(cestasNavidad);
		}else{
			codError = Constantes.COD_ERROR_NO_EXISTE_ARTICULO;
		}

		if(codError == 0){
			if(cestasNavidad.getLstNuevos() != null && cestasNavidad.getLstNuevos().size() > 0){
				codError = codError + this.cestasNavidadArticuloDao.newArticuloLote(cestasNavidad.getLstNuevos());
			}
		}

		//Si ha habido error, lanzamos excepcion para que haya rollback
		if(codError > 0){
			throw new Exception(msgError(codError));
		}

		return msgError(codError);
	}

	@Override
	public void resetCestasNavidad() throws Exception {
		// TODO Auto-generated method stub

		//Actualizamos los checkeados a N.
		CestasNavidad cestaNavidad = new CestasNavidad();
		cestaNavidad.setBorrar("N");
		this.cestasNavidadDao.updateBorradoLinea(cestaNavidad);

		//Quitamos los guardados
		this.cestasNavidadDao.updateLinea(cestaNavidad);
	}

	private String msgError(int codError){
		Locale locale = LocaleContextHolder.getLocale();

		String msgError = null;
		switch (codError){
		case Constantes.COD_ERROR_OK:
			msgError = "OK-" + this.messageSource.getMessage(
					"p105.ok", null, locale);
			break;
		case Constantes.COD_ERROR_INSERT_LOTE: 
			msgError = "KO-" + this.messageSource.getMessage(
					"p105.error.insert", null, locale);
			break;
		case Constantes.COD_ERROR_UPDATE_LOTE:
			msgError = "KO-" +this.messageSource.getMessage(
					"p105.error.modify", null, locale);
			break;
		case Constantes.COD_ERROR_INSERT_DUP_LOTE:
			msgError = "KO-" +this.messageSource.getMessage(
					"p105.error.dupli", null, locale);
			break;
		case Constantes.COD_ERROR_UPDATE_ARTICULO_LOTE:
			msgError = "KO-" +this.messageSource.getMessage(
					"p105.error.contenido.modify", null, locale);
			break;
		case Constantes.COD_ERROR_INSERT_ARTICULO_LOTE:
			msgError = "KO-" +this.messageSource.getMessage(
					"p105.error.contenido.insert", null, locale);
			break;
		case Constantes.COD_ERROR_DELETE_ARTICULO_LOTE:
			msgError = "KO-" +this.messageSource.getMessage(
					"p105.error.contenido.delete", null, locale);
			break;
		case Constantes.COD_ERROR_NO_EXISTE_ARTICULO:
			msgError = "KO-" +this.messageSource.getMessage(
					"p105.error.articulo", null, locale);
			break;
		}
		return msgError;
	}

	@Override
	public int countSeleccionados() {
		// TODO Auto-generated method stub
		return this.cestasNavidadDao.countSeleccionados();
	}

	@Override
	public int updateFechas(CestasNavidad cestasNavidad) {
		// TODO Auto-generated method stub
		return this.cestasNavidadDao.updateFechas(cestasNavidad);
	}

}
