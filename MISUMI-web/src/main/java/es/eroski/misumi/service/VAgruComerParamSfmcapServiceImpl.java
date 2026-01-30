package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VArtSfmDao;
import es.eroski.misumi.model.EstructuraCom;
import es.eroski.misumi.model.EstructuraDat;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAgruComerParamSfmcapService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;

@Service(value = "VAgruComerParamSfmcapService")
public class VAgruComerParamSfmcapServiceImpl implements VAgruComerParamSfmcapService {
	private static final long CERO_L = 0L;
	private static final String FLAG_SI = "S";
	private static final String FLAG_NO = "N";
	
    @Autowired
	protected VArtSfmDao vArtSfmDao;

	/**
	 * Consulta la estructura y prepara el resultado en el formato que se espera.
	 * @return Devuelve nulo en caso de fallo, o la lista si va bien.
	 */
	@SuppressWarnings("unchecked")
	@Override
	 public List<VAgruComerParamSfmcap> findAll(VAgruComerParamSfmcap vAgruComerParamSfmcap, Pagination pagination) throws Exception {
		List<VAgruComerParamSfmcap> retorno = null;
		Long codLoc = null;
		Long codN1 = null;
		Long codN2 = null;
		Long codN3 = null;
		Long codN4 = null;
		Long codN5 = null;
		if(vAgruComerParamSfmcap!=null){
			codLoc = vAgruComerParamSfmcap.getCodCentro();
			codN1 = vAgruComerParamSfmcap.getGrupo1();
			codN2 = vAgruComerParamSfmcap.getGrupo2();
			codN3 = vAgruComerParamSfmcap.getGrupo3();
			codN4 = vAgruComerParamSfmcap.getGrupo4();
			codN5 = vAgruComerParamSfmcap.getGrupo5();
		}
		EstructuraCom estructuraCom = this.vArtSfmDao.obtenerEstrCom(codLoc, codN1, codN2, codN3, codN4);
		if(estructuraCom!=null && estructuraCom.getDatos()!=null){
			
			int nivel = 0;// 0 si se filtra hasta por N5, lo que no tiene sentido
			if(codN1==null){
				nivel = 1; // si no filtramos por N1 estamos con resultados de nivel 1
			} else if(codN2==null){
				nivel = 2;
			} else if(codN3==null){
				nivel = 3;
			} else if(codN4==null){
				nivel = 4;
			} else if(codN5==null){
				nivel = 5;
			}  
			
			String flgStockFinal = FLAG_NO;
			String flgFacing = FLAG_NO;
			String flgCapacidad = FLAG_NO;
			String flgFacingCapacidad = FLAG_NO;
			if(Constantes.SFMCAP_TIPO_LISTADO_SFM.equals(estructuraCom.getTipoEstructura())){
				flgStockFinal = FLAG_SI;
			}else if(Constantes.SFMCAP_TIPO_LISTADO_FAC.equals(estructuraCom.getTipoEstructura())){
				flgFacing = FLAG_SI;
			}else if(Constantes.SFMCAP_TIPO_LISTADO_CAP.equals(estructuraCom.getTipoEstructura())){
				flgCapacidad = FLAG_SI;
			}else if(Constantes.SFMCAP_TIPO_LISTADO_FACCAP.equals(estructuraCom.getTipoEstructura())){
				flgFacing = FLAG_SI;
				flgFacingCapacidad = FLAG_SI;
			}
			
			retorno = new ArrayList<VAgruComerParamSfmcap>(estructuraCom.getDatos().size());
			for(EstructuraDat dato : estructuraCom.getDatos()){
				VAgruComerParamSfmcap vagru = new VAgruComerParamSfmcap();
				vagru.setCodCentro(codLoc);
				vagru.setDescripcion(dato.getDenominacion());
				vagru.setNivel("I" + nivel);
				// asignar valores de grupo
				// el nivel 0 no tiene sentido
				if(nivel==5){
					vagru.setGrupo1(codN1);
					vagru.setGrupo2(codN2);
					vagru.setGrupo3(codN3);
					vagru.setGrupo4(codN4);
					vagru.setGrupo5(dato.getCodigo());
				} else if(nivel==4){
					vagru.setGrupo1(codN1);
					vagru.setGrupo2(codN2);
					vagru.setGrupo3(codN3);
					vagru.setGrupo4(dato.getCodigo());
					vagru.setGrupo5(CERO_L);
				} else if(nivel==3){
					vagru.setGrupo1(codN1);
					vagru.setGrupo2(codN2);
					vagru.setGrupo3(dato.getCodigo());
					vagru.setGrupo4(CERO_L);
					vagru.setGrupo5(CERO_L);
				} else if(nivel==2){
					vagru.setGrupo1(codN1);
					vagru.setGrupo2(dato.getCodigo());
					vagru.setGrupo3(CERO_L);
					vagru.setGrupo4(CERO_L);
					vagru.setGrupo5(CERO_L);
				} else if(nivel==1){
					vagru.setGrupo1(dato.getCodigo());
					vagru.setGrupo2(CERO_L);
					vagru.setGrupo3(CERO_L);
					vagru.setGrupo4(CERO_L);
					vagru.setGrupo5(CERO_L);
				}
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
	public String findTipoEstructura(VAgruComerParamSfmcap vAgruComerSfmcap) throws Exception {
		String retorno = null;
		if(vAgruComerSfmcap!=null){
			Long codLoc = vAgruComerSfmcap.getCodCentro();
			Long codN1 = vAgruComerSfmcap.getGrupo1();
			Long codN2 = vAgruComerSfmcap.getGrupo2();
			Long codN3 = vAgruComerSfmcap.getGrupo3();
			Long codN4 = vAgruComerSfmcap.getGrupo4();
			EstructuraCom estructuraCom = this.vArtSfmDao.obtenerEstrCom(codLoc, codN1, codN2, codN3, codN4);
			if(estructuraCom!=null && estructuraCom.getDatos()!=null && !estructuraCom.getDatos().isEmpty()){
				retorno = estructuraCom.getTipoEstructura();
			}
		}
		return retorno;
	}
}
