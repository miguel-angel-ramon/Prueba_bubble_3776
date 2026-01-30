package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class PedidoAdicionalAyuda2 implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codArt; 
	private Long codCentro; 
	private HistoricoVentaMedia historicoVentaMedia;
	
	public PedidoAdicionalAyuda2() {
		super();
	}

	public PedidoAdicionalAyuda2(Long codArt, Long codCentro,
			HistoricoVentaMedia historicoVentaMedia) {
		super();
		this.codArt = codArt;
		this.codCentro = codCentro;
		this.historicoVentaMedia = historicoVentaMedia;
	}

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public HistoricoVentaMedia getHistoricoVentaMedia() {
		return historicoVentaMedia;
	}

	public void setHistoricoVentaMedia(HistoricoVentaMedia historicoVentaMedia) {
		this.historicoVentaMedia = historicoVentaMedia;
	}
	
}