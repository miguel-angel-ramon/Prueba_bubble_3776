package es.eroski.misumi.model;

import java.io.Serializable;

public class ParamStockFinalMinimo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc; 
	private Long codN1;
	private Long codN2;
	private Long codN3;
	private Long codN4;
	private Long codN5;

	
	public ParamStockFinalMinimo() {
		super();
	}


	public ParamStockFinalMinimo(Long codLoc, Long codN1, Long codN2,
			Long codN3, Long codN4, Long codN5) {
		super();
		this.codLoc = codLoc;
		this.codN1 = codN1;
		this.codN2 = codN2;
		this.codN3 = codN3;
		this.codN4 = codN4;
		this.codN5 = codN5;
	}


	public Long getCodLoc() {
		return this.codLoc;
	}


	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}


	public Long getCodN1() {
		return this.codN1;
	}


	public void setCodN1(Long codN1) {
		this.codN1 = codN1;
	}


	public Long getCodN2() {
		return this.codN2;
	}


	public void setCodN2(Long codN2) {
		this.codN2 = codN2;
	}


	public Long getCodN3() {
		return this.codN3;
	}


	public void setCodN3(Long codN3) {
		this.codN3 = codN3;
	}


	public Long getCodN4() {
		return this.codN4;
	}


	public void setCodN4(Long codN4) {
		this.codN4 = codN4;
	}


	public Long getCodN5() {
		return this.codN5;
	}


	public void setCodN5(Long codN5) {
		this.codN5 = codN5;
	}


}