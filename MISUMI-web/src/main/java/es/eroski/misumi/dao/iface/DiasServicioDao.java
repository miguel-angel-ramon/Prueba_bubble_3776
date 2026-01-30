package es.eroski.misumi.dao.iface;

import java.util.Date;
import java.util.List;

import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.FechaEntregaDiasServicio;

public interface DiasServicioDao  {

	public List<DiasServicio> findAll(DiasServicio diasServicio) throws Exception ;

	public DiasServicio recargaDiasServicio(DiasServicio diasServicio) throws Exception;
	
	public DiasServicio recargaDiasServicioOfer(DiasServicio diasServicio, String oferta) throws Exception;

	public String getPrimerDiaHabilitado(DiasServicio diasServicio, String idSession) throws Exception;

	public DiasServicio recargaDiasServicioGrupo(DiasServicio diasServicio) throws Exception;
	
	public void updateDiasEncargoCliente(DiasServicio diasServicio, List<Date> fechasVenta) throws Exception;
	
	public FechaEntregaDiasServicio cargarDiasServicioDServicio(Long centro, Long referencia) throws Exception;
	
	public void actualizarDiasServicio(Long centro,Long referencia,FechaEntregaDiasServicio fechasServicio, Long codFpMadre, String idSession) throws Exception;

}
