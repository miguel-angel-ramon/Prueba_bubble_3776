package es.eroski.misumi.service.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.SfmCapacidadFacingPagina;
import es.eroski.misumi.model.VArtSfm;


public interface VArtSfmService {

	public SfmCapacidadFacing consultaSfm(VArtSfm vArtSfm) throws Exception;
	public SfmCapacidadFacing consultaCap(VArtSfm vArtSfm) throws Exception;
	public SfmCapacidadFacing consultaFac(VArtSfm vArtSfm) throws Exception;
	public void aplicarCorreccionConsultaFacConLotes(VArtSfm vArtSfm, SfmCapacidadFacing sfmCapacidad) throws Exception;

	public SfmCapacidadFacing actualizacionSfm(List<VArtSfm> listaModificadosActualizacion, String usuario) throws Exception;
	public SfmCapacidadFacing actualizacionCap(List<VArtSfm> listaModificadosActualizacion, String usuario) throws Exception;
	public SfmCapacidadFacing actualizacionFac(List<VArtSfm> listaModificadosActualizacion, String usuario) throws Exception;
	
	public Long recalcularCapacidad(VArtSfm vArtSfm) throws Exception;
	
	public boolean esReferenciaExpositor(VArtSfm vArtSfm) throws Exception;
	
	public SfmCapacidadFacing calcularCampoNSR(SfmCapacidadFacing sfmCapacidadFacing) throws Exception;
	
	public SfmCapacidadFacingPagina calcularCampoNSR(SfmCapacidadFacingPagina sfmCapacidadFacingPagina) throws Exception;
	
	public SfmCapacidadFacingPagina calcularCampoMuchoPoco(SfmCapacidadFacingPagina sfmCapacidadFacingPagina, Long codFpMadre, HttpSession session) throws Exception;
	
	public List<VArtSfm> formatearLsf(List<VArtSfm> listaSfm) throws Exception;
	
	public List<VArtSfm> ordenarLista(List<VArtSfm> listaSfm, String index, String sortOrder) throws Exception;

	public SfmCapacidadFacingPagina crearListaPaginada(SfmCapacidadFacing sfmCapacidadGuardada, List<VArtSfm> listaSfm, Long page, Long max, String index, String sortOrder) throws Exception;

	public String calcularPedir(VArtSfm vArtSfm) throws Exception;
	
}
