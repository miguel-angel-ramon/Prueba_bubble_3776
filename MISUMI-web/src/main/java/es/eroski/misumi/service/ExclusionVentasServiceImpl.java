package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;

import es.eroski.misumi.dao.iface.CentroRefAprovDao;
import es.eroski.misumi.dao.iface.ExclusionVentasSIADao;
import es.eroski.misumi.dao.iface.VDatosDiarioArtDao;
import es.eroski.misumi.model.CentroRefAprov;
import es.eroski.misumi.model.ExclusionVentas;
import es.eroski.misumi.model.ExclusionVentasSIA;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.service.iface.ExclusionVentasService;
import es.eroski.misumi.util.Utilidades;


@Service(value = "ExclusionVentasService")
public class ExclusionVentasServiceImpl implements ExclusionVentasService {
	@Autowired
	private ExclusionVentasSIADao exclusionVentasSIADao;

	@Autowired
	private VDatosDiarioArtDao vDatosDiarioArtDao;

	@Autowired
	private CentroRefAprovDao centroRefAprovDao;

	private DataSourceTransactionManager transactionManager;


	@Autowired
	public void setDataSourceTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;

	}

	@Override
	public List<ExclusionVentas> findAll(ExclusionVentas exclusionVentas) throws Exception {
		ExclusionVentasSIA exclusionVentasSIA = exclusionVentasSIADao.consultaExclusion(exclusionVentas);
		return exclusionVentasSIA.getDatos();
	}

	@Override
	@Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NOT_SUPPORTED)
	public List<ExclusionVentas> removeAll(List<ExclusionVentas> listaExclusionVentas) throws Exception {
		MessageSource messageSource = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");

		List<ExclusionVentas> listaExcluidos = new ArrayList<ExclusionVentas>();
		for(ExclusionVentas exclusionVentas: listaExclusionVentas){
			List<ExclusionVentas> listaExclusion = new ArrayList<ExclusionVentas>();
			listaExclusion.add(exclusionVentas);

			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = transactionManager.getTransaction(def);
			try {
				ExclusionVentasSIA exclusionVentasSIA = this.exclusionVentasSIADao.borrarExclusion(listaExclusion);
				if (null != exclusionVentasSIA && null != exclusionVentasSIA.getEstado() && exclusionVentasSIA.getEstado().equals(new Long("0"))){
					if (null != exclusionVentasSIA.getDatos() && !exclusionVentasSIA.getDatos().isEmpty()){
						listaExcluidos.addAll(exclusionVentasSIA.getDatos());
					}
					transactionManager.commit(status);
				} else if (null != exclusionVentasSIA) {
					exclusionVentas.setCodError(exclusionVentasSIA.getEstado());
					exclusionVentas.setDescripError(exclusionVentasSIA.getDescEstado());
					listaExcluidos.add(exclusionVentas);
					transactionManager.rollback(status);
				} else {
					exclusionVentas.setCodError(new Long("9"));
					exclusionVentas.setDescripError(messageSource.getMessage("p73_exclusionVentas.errorWSBorrarExclusionVentas", null, LocaleContextHolder.getLocale()));
					listaExcluidos.add(exclusionVentas);
					transactionManager.rollback(status);
				}
			} catch (Exception e) {
				exclusionVentas.setCodError(new Long("9"));
				exclusionVentas.setDescripError(messageSource.getMessage("p73_exclusionVentas.errorWSBorrarExclusionVentas", null, LocaleContextHolder.getLocale()));
				listaExcluidos.add(exclusionVentas);
				transactionManager.rollback(status);
			}
		}
		return listaExcluidos;

	}
	@Override
	@Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NOT_SUPPORTED)
	public List<ExclusionVentas> insertAll(List<ExclusionVentas> listaExclusionVentas) throws Exception {
		MessageSource messageSource = (MessageSource) ContextLoader.getCurrentWebApplicationContext().getBean("messageSource");

		List<ExclusionVentas> listaInsertados = new ArrayList<ExclusionVentas>();
		for(ExclusionVentas exclusionVentas: listaExclusionVentas){
			CentroRefAprov centroRefAprov = null;
			if (null != exclusionVentas.getCodArt()){
				VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
				vDatosDiarioArt.setCodArt(exclusionVentas.getCodArt());
				List<VDatosDiarioArt> listaRef = this.vDatosDiarioArtDao.findAll(vDatosDiarioArt);
				if (!listaRef.isEmpty()){
					VDatosDiarioArt aux = listaRef.get(0);
					centroRefAprov = new CentroRefAprov();
					centroRefAprov.setCodCentro(exclusionVentas.getCodCentro());
					centroRefAprov.setGrupo1(aux.getGrupo1());
					centroRefAprov.setGrupo2(aux.getGrupo2());
					centroRefAprov.setGrupo3(aux.getGrupo3());
					centroRefAprov.setGrupo4(aux.getGrupo4());
					centroRefAprov.setGrupo5(aux.getGrupo5());
					centroRefAprov.setFecha(Utilidades.convertirStringAFecha(exclusionVentas.getFecha()));
				} else {
					//Error referencia no existe;
					exclusionVentas.setCodError(new Long("9"));
					exclusionVentas.setDescripError(messageSource.getMessage("p73_exclusionVentas.errorReferenciaExclusionVentas", null, LocaleContextHolder.getLocale()));
					listaInsertados.add(exclusionVentas);
				}
			} else {
				centroRefAprov = new CentroRefAprov();
				centroRefAprov.setCodCentro(exclusionVentas.getCodCentro());
				centroRefAprov.setGrupo1(exclusionVentas.getGrupo1());
				centroRefAprov.setGrupo2(exclusionVentas.getGrupo2());
				centroRefAprov.setGrupo3(exclusionVentas.getGrupo3());
				centroRefAprov.setGrupo4(exclusionVentas.getGrupo4());
				centroRefAprov.setGrupo5(exclusionVentas.getGrupo5());
				centroRefAprov.setFecha(Utilidades.convertirStringAFecha(exclusionVentas.getFecha()));
			}
			List<ExclusionVentas> listaExclusion = new ArrayList<ExclusionVentas>();
			listaExclusion.add(exclusionVentas);

			if (null != centroRefAprov){
				List<String> ambitos = this.centroRefAprovDao.findAll(centroRefAprov);

				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				TransactionStatus status = transactionManager.getTransaction(def);
				try {
					ExclusionVentasSIA exclusionVentasSIA = this.exclusionVentasSIADao.insertarExclusion(listaExclusion);
					if (null != exclusionVentasSIA && null != exclusionVentasSIA.getEstado() && exclusionVentasSIA.getEstado().equals(new Long("0"))){
						if (null != exclusionVentasSIA.getDatos() && !exclusionVentasSIA.getDatos().isEmpty()){
							listaInsertados.addAll(exclusionVentasSIA.getDatos());
						}
						transactionManager.commit(status);
					} else if (null != exclusionVentasSIA) {
						exclusionVentas.setCodError(exclusionVentasSIA.getEstado());
						exclusionVentas.setDescripError(exclusionVentasSIA.getDescEstado());
						listaInsertados.add(exclusionVentas);
						transactionManager.rollback(status);
					} else {
						exclusionVentas.setCodError(new Long("9"));
						exclusionVentas.setDescripError(messageSource.getMessage("p73_exclusionVentas.errorWSInsertarExclusionVentas", null, LocaleContextHolder.getLocale()));
						listaInsertados.add(exclusionVentas);
						transactionManager.rollback(status);
					}

				} catch (Exception e) {
					exclusionVentas.setCodError(new Long("9"));
					exclusionVentas.setDescripError(messageSource.getMessage("p73_exclusionVentas.errorWSInsertarExclusionVentas", null, LocaleContextHolder.getLocale()));
					listaInsertados.add(exclusionVentas);
					transactionManager.rollback(status);
				}
			}
		}
		return listaInsertados;
	}
}
