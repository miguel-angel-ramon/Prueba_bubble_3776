package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class PedidoBasicInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

    private Date fechaPed;
    private String confirmadas;
    private String empuje;
    private String impCab;
    private String nsr;
    private String intertienda;
    
    private Double cajaNormal;
	private Double cajaEmpuje;
    private Double cajaImpl;
    private Double cajaNsr;
    private Double cajaIntertienda;
    
    private String formato;
    

 
	
    

	public String getIntertienda() {
		return intertienda;
	}

	public void setIntertienda(String intertienda) {
		this.intertienda = intertienda;
	}

	public PedidoBasicInfo() {
		super();
	}

	public PedidoBasicInfo(	Date fechaPed, String confirmadas,
			String empuje, String impCab,
            String nsr, String intertienda, Double cajaNormal, Double cajaEmpuje, Double cajaImpl, Double cajaNsr, Double cajaIntertienda, String formato) {
		super();
		
		this.fechaPed = fechaPed;
		this.confirmadas = confirmadas;
		this.empuje = empuje;
		this.impCab = impCab;
		this.nsr = nsr;
		this.intertienda = intertienda;	
		this.cajaNormal = cajaNormal;
		this.cajaEmpuje = cajaEmpuje;
		this.cajaImpl = cajaImpl;
		this.cajaNsr = cajaNsr;
		this.cajaIntertienda = cajaIntertienda;
		this.formato = formato;	
	
	}

	public Date getFechaPed() {
		return this.fechaPed;
	}

	public void setFechaPed(Date fechaPed) {
		this.fechaPed = fechaPed;
	}

	public String getConfirmadas() {
		return this.confirmadas;
	}

	public void setConfirmadas(String confirmadas) {
		this.confirmadas = confirmadas;
	}

	public String getEmpuje() {
		return this.empuje;
	}

	public void setEmpuje(String empuje) {
		this.empuje = empuje;
	}

	public String getImpCab() {
		return this.impCab;
	}

	public void setImpCab(String impCab) {
		this.impCab = impCab;
	}

	public String getNsr() {
		return this.nsr;
	}

	public void setNsr(String nsr) {
		this.nsr = nsr;
	}
	
	public Double getCajaNormal() {
		return cajaNormal;
	}

	public void setCajaNormal(Double cajaNormal) {
		this.cajaNormal = cajaNormal;
	}

	public Double getCajaEmpuje() {
		return cajaEmpuje;
	}

	public void setCajaEmpuje(Double cajaEmpuje) {
		this.cajaEmpuje = cajaEmpuje;
	}

	public Double getCajaImpl() {
		return cajaImpl;
	}

	public void setCajaImpl(Double cajaImpl) {
		this.cajaImpl = cajaImpl;
	}

	public Double getCajaNsr() {
		return cajaNsr;
	}

	public void setCajaNsr(Double cajaNsr) {
		this.cajaNsr = cajaNsr;
	}

	public Double getCajaIntertienda() {
		return cajaIntertienda;
	}

	public void setCajaIntertienda(Double cajaIntertienda) {
		this.cajaIntertienda = cajaIntertienda;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	
}