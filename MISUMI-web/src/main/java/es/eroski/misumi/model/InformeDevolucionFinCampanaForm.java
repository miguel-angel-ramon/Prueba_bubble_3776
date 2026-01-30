package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class InformeDevolucionFinCampanaForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;

	private List<String> listaReferencias;

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public List<String> getListaReferencias() {
		return listaReferencias;
	}

	public void setListaReferencias(List<String> listaReferencias) {
		this.listaReferencias = listaReferencias;
	}

}
