package es.eroski.misumi.model;

import java.io.Serializable;

public class ParamCentrosOpc implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codLoc;
	private String opcHabil;

	public ParamCentrosOpc() {
	    super();
	}

	public ParamCentrosOpc(Long codLoc, String opcHabil) {
		super();
		this.codLoc = codLoc;
		this.opcHabil = opcHabil;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public String getOpcHabil() {
		return this.opcHabil;
	}

	public void setOpcHabil(String opcHabil) {
		this.opcHabil = opcHabil;
	}
}