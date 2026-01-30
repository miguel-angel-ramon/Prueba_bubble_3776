package es.eroski.misumi.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

public class ListadoRefSegundaReposicionSalida implements Serializable, Comparable<ListadoRefSegundaReposicionSalida>{
	
	private static final long serialVersionUID = 1L;
	private String codN1;
	private String descCodN1;
	private String codN2;
	private String descCodN2;
	private String codN3;
	private String descCodN3;
	private String codN4;
	private String descCodN4;
	private String codN5;
	private String descCodN5;
	private String referencia;
	private String referenciaDesc;
	private String facing;
	private String capacidad;
	private String cajaExpositora;
	private Double tendencia;
	private Double ventaPrevista;
	
	public ListadoRefSegundaReposicionSalida() {
	    super();
	}

	public ListadoRefSegundaReposicionSalida(String codN1, String descCodN1, String codN2, String descCodN2,String codN3, String descCodN3, 
			String codN4, String descCodN4,	String codN5, String descCodN5, String referencia, String referenciaDesc, String facing, String capacidad, 
			String cajaExpositora, Double tendencia, Double ventaPrevista) {
		super();
		this.codN1 = codN1;
		this.descCodN1 = descCodN1;
		this.codN2 = codN2;
		this.descCodN2 = descCodN2;
		this.codN3 = codN3;
		this.descCodN3 = descCodN3;
		this.codN4 = codN4;
		this.descCodN4 = descCodN4;
		this.codN5 = codN5;
		this.descCodN5 = descCodN5;
		this.referencia = referencia;
		this.referenciaDesc = referenciaDesc;
		this.facing = facing;
		this.capacidad = capacidad;
		this.cajaExpositora = cajaExpositora;
		this.tendencia = tendencia;
		this.ventaPrevista = ventaPrevista;
	}

	public String getCodN1() {
		return codN1;
	}

	public void setCodN1(String codN1) {
		this.codN1 = codN1;
	}

	public String getDescCodN1() {
		return descCodN1;
	}

	public void setDescCodN1(String descCodN1) {
		this.descCodN1 = descCodN1;
	}

	public String getCodN2() {
		return codN2;
	}

	public void setCodN2(String codN2) {
		this.codN2 = codN2;
	}

	public String getDescCodN2() {
		return descCodN2;
	}

	public void setDescCodN2(String descCodN2) {
		this.descCodN2 = descCodN2;
	}

	public String getCodN3() {
		return codN3;
	}

	public void setCodN3(String codN3) {
		this.codN3 = codN3;
	}

	public String getDescCodN3() {
		return descCodN3;
	}

	public void setDescCodN3(String descCodN3) {
		this.descCodN3 = descCodN3;
	}

	public String getCodN4() {
		return codN4;
	}

	public void setCodN4(String codN4) {
		this.codN4 = codN4;
	}

	public String getDescCodN4() {
		return descCodN4;
	}

	public void setDescCodN4(String descCodN4) {
		this.descCodN4 = descCodN4;
	}

	public String getCodN5() {
		return codN5;
	}

	public void setCodN5(String codN5) {
		this.codN5 = codN5;
	}

	public String getDescCodN5() {
		return descCodN5;
	}

	public void setDescCodN5(String descCodN5) {
		this.descCodN5 = descCodN5;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getReferenciaDesc() {
		return referenciaDesc;
	}

	public void setReferenciaDesc(String referenciaDesc) {
		this.referenciaDesc = referenciaDesc;
	}

	public String getFacing() {
		return facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getCajaExpositora() {
		return cajaExpositora;
	}

	public void setCajaExpositora(String cajaExpositora) {
		this.cajaExpositora = cajaExpositora;
	}

	public Double getTendencia() {
		return tendencia;
	}

	public void setTendencia(Double tendencia) {
		this.tendencia = tendencia;
	}

	public Double getVentaPrevista() {
		return ventaPrevista;
	}

	public void setVentaPrevista(Double ventaPrevista) {
		this.ventaPrevista = ventaPrevista;
	}
	
	@Override
	public int compareTo(ListadoRefSegundaReposicionSalida o) {
		
		return 1;
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof ListadoRefSegundaReposicionSalida)){
			return false;
		}
		ListadoRefSegundaReposicionSalida npr = (ListadoRefSegundaReposicionSalida) obj;
		return new EqualsBuilder().append(this.codN1, npr.codN1).append(this.codN2, npr.codN2).append(this.codN3, npr.codN3).isEquals();
	}
}