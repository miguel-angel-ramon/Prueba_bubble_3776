package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class ReposicionCmbSeccion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<ReposicionSeccion> repoSeccionLst;
	private Long codError;
	private String descError;
	
	public ReposicionCmbSeccion(List<ReposicionSeccion> repoSeccionLst, Long codError, String descError) {
		super();
		this.repoSeccionLst = repoSeccionLst;
		this.codError = codError;
		this.descError = descError;
	}
	
	public List<ReposicionSeccion> getRepoSeccionLst() {
		return repoSeccionLst;
	}
	public void setRepoSeccionLst(List<ReposicionSeccion> repoSeccionLst) {
		this.repoSeccionLst = repoSeccionLst;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
}
