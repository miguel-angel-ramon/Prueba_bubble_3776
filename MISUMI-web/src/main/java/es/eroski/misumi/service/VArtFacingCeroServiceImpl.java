package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VArtFacingCeroDao;
import es.eroski.misumi.dao.iface.VArtSfmDao;
import es.eroski.misumi.dao.iface.VUltimoPedidoNsrDao;
import es.eroski.misumi.model.AreaFacingCeroSIALista;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.SfmCapacidadFacingPagina;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;
import es.eroski.misumi.service.iface.VArtFacingCeroService;
import es.eroski.misumi.service.iface.VArtSfmService;
import es.eroski.misumi.service.iface.VRelacionArticuloService;
import es.eroski.misumi.service.iface.VSurtidoTiendaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.Utilidades;
import es.eroski.misumi.util.VArtSfmComparator;
import es.eroski.misumi.util.iface.PaginationManager;

@Service(value = "VArtFacingCeroService")
public class VArtFacingCeroServiceImpl implements VArtFacingCeroService {
    
	@Autowired
	private VArtFacingCeroDao vArtFacingCeroDao;

	private PaginationManager<VArtSfm> paginationManager = new PaginationManagerImpl<VArtSfm>();

	private List<VArtSfm> formatearLsf(List<VArtSfm> listaFacingCero) throws Exception {

		for (int i = 0, tam = listaFacingCero.size(); i < tam; i++) {

			if(listaFacingCero.get(i).getFechaSfmDDMMYYYY().length()!=0){
				//Formateamos la fechaSFM a date
				String getfechaFacingCero = listaFacingCero.get(i).getFechaSfmDDMMYYYY().substring(0,2) +""+ listaFacingCero.get(i).getFechaSfmDDMMYYYY().substring(2,4) +""+ listaFacingCero.get(i).getFechaSfmDDMMYYYY().substring(4);
				Calendar fechaFacingCero = Calendar.getInstance();
				fechaFacingCero.setTime(Utilidades.convertirStringAFecha(getfechaFacingCero));

				//Obtenemos la fecha actual
				Calendar fechaActual = Calendar.getInstance();

				//Obtenemos  la diferencia entre las dos fechas 
				long c = 24*60*60*1000;

				long diffDays = (long) Math.floor((fechaActual.getTimeInMillis() - fechaFacingCero.getTimeInMillis())/(c));

				if (diffDays <= 21) {
					//Si la diferencia entre fechas es menor o igual que 21, entonces el limite superior se pondra a ""
					listaFacingCero.get(i).setLsf(-100.0);
				}else {
					//sfmCapacidadFacingPagina.getDatos().getRows().get(i).setLsf(sfmCapacidadFacingPagina.getDatos().getRows().get(i).getLmin());
				}
			}
		}

		return listaFacingCero;
	}
	
	@Override
	public SfmCapacidadFacing consultaFacingCero(Long codCentro, String area) throws Exception {
		SfmCapacidadFacing sfmCapacidad = this.vArtFacingCeroDao.obtenerFacingCero(codCentro, area);
		return sfmCapacidad;
	}

	@Override
	public List<VArtSfm> ordenarLista(List<VArtSfm> listaFacingCero, String index, String sortOrder) throws Exception {
		
		if (listaFacingCero != null && index != null && !index.equals("")){
			Comparator<VArtSfm> comparator = VArtSfmComparator.getComparator(index);
			if (null != listaFacingCero && listaFacingCero.size() > 0){
				//Formateamos el campo Lsf segun fecha
				listaFacingCero = this.formatearLsf(listaFacingCero);
				Collections.sort(listaFacingCero, comparator);
				if ("desc".equals(sortOrder)) {//El 'sort' siempre ordena en ascendente, por lo tanto si se quiere 
					//ordenar en descendente le damos la vuelta
					Collections.reverse(listaFacingCero);
				}
			}
		}
		return listaFacingCero;
	}
	
	@Override
	public SfmCapacidadFacing grabarRefValidadas(List<VArtSfm> listaRefsValidadas) throws Exception{
		SfmCapacidadFacing sfmCapacidad = this.vArtFacingCeroDao.grabarRefsValidadas(listaRefsValidadas);
		return sfmCapacidad;
	}

	@Override
	public SfmCapacidadFacingPagina crearListaPaginada(SfmCapacidadFacing facingCeroCapacidadGuardada, List<VArtSfm> listaFacingCero, Long page, Long max, String index, String sortOrder) throws Exception {
		SfmCapacidadFacingPagina sfmCapacidadFacingPagina = new SfmCapacidadFacingPagina();
		List<VArtSfm> subListaFacingCero = new ArrayList<VArtSfm>();
		Page<VArtSfm> listaFacingCeroPaginada = null;
		
		int records = 0;
		Pagination pagination = new Pagination(max,page);
		
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
		Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
		
		if (listaFacingCero != null){ 
			records = listaFacingCero.size();
			if (elemInicio <= records){
				if (elemFinal > records){
					elemFinal = new Long(records);
				}
				subListaFacingCero = (listaFacingCero).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
			}
		}
		
		if (subListaFacingCero != null && subListaFacingCero.size() > 0 && facingCeroCapacidadGuardada != null) {
			listaFacingCeroPaginada = this.paginationManager.paginate(new Page<VArtSfm>(), subListaFacingCero, max.intValue(), records, page.intValue());	
			sfmCapacidadFacingPagina.setDatos(listaFacingCeroPaginada);
			sfmCapacidadFacingPagina.setEstado(facingCeroCapacidadGuardada.getEstado());
			sfmCapacidadFacingPagina.setDescEstado(facingCeroCapacidadGuardada.getDescEstado());
			sfmCapacidadFacingPagina.setFlgCapacidad(facingCeroCapacidadGuardada.getFlgCapacidad());
			sfmCapacidadFacingPagina.setFlgFacing(facingCeroCapacidadGuardada.getFlgFacing());
		} else {
			sfmCapacidadFacingPagina.setDatos(new Page<VArtSfm>());
			sfmCapacidadFacingPagina.setEstado(new Long(0));
			sfmCapacidadFacingPagina.setDescEstado("");
		}
		return sfmCapacidadFacingPagina;
	}
	
}
