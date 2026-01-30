package es.eroski.misumi.model;

import java.util.List;

public class DevolucionTipos {
	private Long pCodError;
	private String pDescError;
	private List<DevolucionTipo> devolucionTipoLst;
	
	public DevolucionTipos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DevolucionTipos(Long pCodError, String pDescError, List<DevolucionTipo> devolucionTipoLst) {
		super();
		this.pCodError = pCodError;
		this.pDescError = pDescError;
		this.devolucionTipoLst = devolucionTipoLst;
	}
	
	public Long getpCodError() {
		return pCodError;
	}
	public void setpCodError(Long pCodError) {
		this.pCodError = pCodError;
	}
	public String getpDescError() {
		return pDescError;
	}
	public void setpDescError(String pDescError) {
		this.pDescError = pDescError;
	}
	public List<DevolucionTipo> getDevolucionTipoLst() {
		return devolucionTipoLst;
	}
	public void setDevolucionTipoLst(List<DevolucionTipo> devolucionTipoLst) {
		this.devolucionTipoLst = devolucionTipoLst;
	}
}
