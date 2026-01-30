package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class InformeListado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean existe;	//Control de existencia de informes para el centro

	private Long informeHuecosCount;
	
	private List<InformeHuecos> listaInformeHuecos; //Lista con los informes de huecos a mostrar por centro
	
	private List<InformeListadoPesca> listaInformeListadoPesca; //Lista con los informes de pescado a mostrar por centro

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	public Long getInformeHuecosCount() {
		return informeHuecosCount;
	}

	public void setInformeHuecosCount(Long informeHuecosCount) {
		this.informeHuecosCount = informeHuecosCount;
	}

	public List<InformeHuecos> getListaInformeHuecos() {
		return listaInformeHuecos;
	}

	public void setListaInformeHuecos(List<InformeHuecos> listaInformeHuecos) {
		this.listaInformeHuecos = listaInformeHuecos;
	}

	public List<InformeListadoPesca> getListaInformeListadoPesca() {
		return listaInformeListadoPesca;
	}

	public void setListaInformeListadoPesca(List<InformeListadoPesca> listaInformeListadoPesca) {
		this.listaInformeListadoPesca = listaInformeListadoPesca;
	}
	
}
