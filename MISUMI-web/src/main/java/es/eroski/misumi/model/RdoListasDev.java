package es.eroski.misumi.model;

import java.util.List;

public class RdoListasDev {

	List<TDevolucionLinea> listTDevolucionLinea;
	List<TDevolucionBulto> listTDevolucionLineaBultoCantidad;
	
	public RdoListasDev() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RdoListasDev(List<TDevolucionLinea> listaDev, List<TDevolucionBulto> listaBulto) {
		this.listTDevolucionLinea = listaDev;
		this.listTDevolucionLineaBultoCantidad = listaBulto;
	}

	public List<TDevolucionLinea> getListTDevolucionLinea() {
		return listTDevolucionLinea;
	}

	public List<TDevolucionBulto> getListTDevolucionLineaBultoCantidad() {
		return listTDevolucionLineaBultoCantidad;
	}

}
