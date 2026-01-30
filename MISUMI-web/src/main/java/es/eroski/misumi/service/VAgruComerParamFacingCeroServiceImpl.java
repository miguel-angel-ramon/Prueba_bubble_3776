package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.ECAreaFacingCero;
import es.eroski.misumi.dao.iface.VAgruComerRefDao;
import es.eroski.misumi.dao.iface.VArtSfmDao;
import es.eroski.misumi.model.AreaFacingCero;
import es.eroski.misumi.model.EstructuraCom;
import es.eroski.misumi.model.EstructuraDat;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAgruComerParamFacingCeroService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;

@Service(value = "VAgruComerParamFacingCeroService")
public class VAgruComerParamFacingCeroServiceImpl implements VAgruComerParamFacingCeroService {
	private static final long CERO_L = 0L;
	private static final String FLAG_SI = "S";
	private static final String FLAG_NO = "N";
	
    @Autowired
	protected VArtSfmDao vArtSfmDao;

	@Autowired
	private VAgruComerRefDao agruComerRefDao;

	/**
	 * Consulta la estructura y prepara el resultado en el formato que se espera.
	 * @return Devuelve nulo en caso de fallo, o la lista si va bien.
	 */
	@SuppressWarnings("unchecked")
	@Override
	 public List<VAgruComerParamSfmcap> findAllAreas(AreaFacingCero areasFacingCero, Pagination pagination) throws Exception {
		List<VAgruComerParamSfmcap> retorno = null;
		
//		EstructuraCom estructuraCom = this.vArtSfmDao.obtenerEstrCom(codLoc, codN1, codN2, codN3, codN4);
		
		if (null != areasFacingCero && new Long(0).equals(areasFacingCero.getCodError())){
		
			String flgStockFinal = FLAG_NO;
			String flgFacing = FLAG_NO;
			String flgCapacidad = FLAG_NO;
			String flgFacingCapacidad = FLAG_NO;
			
			retorno = new ArrayList<VAgruComerParamSfmcap>(areasFacingCero.getLstAreaReferenciasFacingCero().size());
			
			int nivel = 1;

			for (ECAreaFacingCero area: areasFacingCero.getLstAreaReferenciasFacingCero()){
				String descArea = agruComerRefDao.getDescripcionArea(area.getNivel1());
				
				VAgruComerParamSfmcap vagru = new VAgruComerParamSfmcap();
				vagru.setCodCentro(area.getCodCentro());
				vagru.setNivel("I"+1);
				vagru.setGrupo1(new Long(area.getNivel1()));
				vagru.setGrupo2(CERO_L);
				vagru.setGrupo3(CERO_L);
				vagru.setGrupo4(CERO_L);
				vagru.setGrupo5(CERO_L);
				vagru.setDescripcion(descArea);
				
				// asignar valores tipoEstado
				vagru.setFlgStockFinal(flgStockFinal);
				vagru.setFlgFacing(flgFacing);
				vagru.setFlgCapacidad(flgCapacidad);
				vagru.setFlgFacingCapacidad(flgFacingCapacidad);
				
				retorno.add(vagru);
			}
			
			// Ordenacion
			Transformer criterioOrdenacionTransformer = new CriterioOrdenacionAgruComerTransformer(nivel);
			Comparator<Object> sortComparator = ComparatorUtils.transformedComparator(ComparatorUtils.nullLowComparator(ComparatorUtils.naturalComparator()), criterioOrdenacionTransformer);
			if(pagination!=null && "desc".equalsIgnoreCase(pagination.getAscDsc())){
				sortComparator = ComparatorUtils.reversedComparator(sortComparator);
			}
			Collections.sort(retorno, sortComparator);
			
			// recortar lista segun paginacion
			retorno = Paginate.aplicarLimitesPaginacion(pagination, retorno);
			
		}
		return retorno;
	}
	
	/**
	 * Extrae el criterio de ordenacion
	 * @author bihealga
	 *
	 */
	private static final class CriterioOrdenacionAgruComerTransformer implements Transformer {
		private final int nivel;
		public CriterioOrdenacionAgruComerTransformer(int nivel){
			this.nivel = nivel;
		}
		@Override
		public Object transform(Object arg0) {
			Object ordenable = null;
			if(arg0 instanceof VAgruComerParamSfmcap){
				VAgruComerParamSfmcap agruComer = (VAgruComerParamSfmcap) arg0;
				switch(nivel){
				case 5:
					ordenable = agruComer.getGrupo5();
					break;
				case 4:
					ordenable = agruComer.getGrupo4();
					break;
				case 3:
					ordenable = agruComer.getGrupo3();
					break;
				case 2:
					ordenable = agruComer.getGrupo2();
					break;
				case 1:
					ordenable = agruComer.getGrupo1();
					break;
				default:
					ordenable = agruComer.getDescripcion();
				}
			}
			return ordenable;
		}
		
	}

	/**
	 * Extraemos la estructura unicamente y nos ahorramos algunos mapeos y ordenacion.
	 * Si no hay datos devolvemos null.
	 */
	@Override
	public String findTipoEstructura(Long codCentro, Long grupo1) throws Exception {
		String retorno = null;
		
		Long codLoc = codCentro;
		Long codN1 = grupo1;
		Long codN2 = CERO_L;
		Long codN3 = CERO_L;
		Long codN4 = CERO_L;

		EstructuraCom estructuraCom = this.vArtSfmDao.obtenerEstrCom(codLoc, codN1, codN2, codN3, codN4);
		if(estructuraCom!=null && estructuraCom.getDatos()!=null && !estructuraCom.getDatos().isEmpty()){
			retorno = estructuraCom.getTipoEstructura();
		}
		
		return retorno;
	}
}
