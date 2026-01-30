package es.eroski.misumi.service.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.DiasServicio;

public interface DiasServicioService {

	  public List<DiasServicio> findAll(DiasServicio diasServicio) throws Exception;
	  public DiasServicio recargaDiasServicio(DiasServicio diasServicio) throws Exception;
	  public DiasServicio recargaDiasServicioOfer(DiasServicio diasServicio, String oferta) throws Exception;
	  public DiasServicio recargaDiasServicioGrupo(DiasServicio diasServicio) throws Exception;
	  /**
	   * 
	   * @param diasServicio
	   * @param idSession
	   * @param codFpMadre
	   * @param session
	   * @return
	   * @throws Exception
	   */
	  public DiasServicio cargarDiasServicio(DiasServicio diasServicio,	String idSession, Long codFpMadre, HttpSession session) throws Exception;
	  /**
	   * 
	   * @param diasServicio
	   * @param idSession
	   * @return
	   * @throws Exception
	   */
	  public List<DiasServicio> obtenerDiasServicio(DiasServicio diasServicio, String idSession) throws Exception;
	  /**
	   * 
	   * @param diasServicio
	   * @param codFpMadre
	   * @param idSession
	   * @return
	   * @throws Exception
	   */
	  public String getPrimerDiaHabilitado(DiasServicio diasServicio, Long codFpMadre, String idSession) throws Exception;
	  public DiasServicio findOne(DiasServicio diasServicio) throws Exception;
	  /**
	   * 
	   * @param centro
	   * @param referencia
	   * @param codFpMadre
	   * @param idSession
	   * @throws Exception
	   */
	  public void actualizarDiasServicio(Long centro, Long referencia, Long codFpMadre, String idSession) throws Exception;
}
