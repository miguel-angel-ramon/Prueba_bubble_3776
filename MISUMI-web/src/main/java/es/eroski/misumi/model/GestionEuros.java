package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class GestionEuros implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long codLoc;
	private String respetarIMC;
	private Double precioCostoFinal;
	private Double precioCostoFinalFinal;
	private List<GestionEurosRefs> gestionEurosRefsLst;
	
	public GestionEuros(Long codLoc, String respetarIMC, Double precioCostoFinal, Double precioCostoFinalFinal,
			List<GestionEurosRefs> gestionEurosRefsLst) {
		super();
		this.codLoc = codLoc;
		this.respetarIMC = respetarIMC;
		this.precioCostoFinal = precioCostoFinal;
		this.precioCostoFinalFinal = precioCostoFinalFinal;
		this.gestionEurosRefsLst = gestionEurosRefsLst;
	}

	public GestionEuros(Long codLoc, String respetarIMC, Double precioCostoFinal, Double precioCostoFinalFinal) {
		super();
		this.codLoc = codLoc;
		this.respetarIMC = respetarIMC;
		this.precioCostoFinal = precioCostoFinal;
		this.precioCostoFinalFinal = precioCostoFinalFinal;
	}

	public GestionEuros() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCodLoc() {
		return codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public String getRespetarIMC() {
		return respetarIMC;
	}

	public void setRespetarIMC(String respetarIMC) {
		this.respetarIMC = respetarIMC;
	}

	public Double getPrecioCostoFinal() {
		return precioCostoFinal;
	}

	public void setPrecioCostoFinal(Double precioCostoFinal) {
		this.precioCostoFinal = precioCostoFinal;
	}

	public Double getPrecioCostoFinalFinal() {
		return precioCostoFinalFinal;
	}

	public void setPrecioCostoFinalFinal(Double precioCostoFinalFinal) {
		this.precioCostoFinalFinal = precioCostoFinalFinal;
	}

	public List<GestionEurosRefs> getGestionEurosRefsLst() {
		return gestionEurosRefsLst;
	}

	public void setGestionEurosRefsLst(List<GestionEurosRefs> gestionEurosRefsLst) {
		this.gestionEurosRefsLst = gestionEurosRefsLst;
	}

	
}