/**
 * 
 */
package es.eroski.misumi.model;

import java.util.List;

/**
 * P-52083
 * Bean de maniobra que trabaja como Wrapper para albergar la lista de secciones que obtendremos para cargar el combo
 * en el popup de referencias que se obtiene al realizar una busqueda alfanumerica. 
 * @author BICUGUAL
 *
 */
public class PopUp34ViewerManagerBean {

	//Lista de secciones
	public List<SeccionBean> lstSecciones;
	//Numero de secciones
	public NumLoteMpGrid numLoteMpGrid;
	
	public PopUp34ViewerManagerBean(List<SeccionBean> lstSecciones, NumLoteMpGrid numLoteMpGrid) {
		super();
		this.lstSecciones = lstSecciones;
		this.numLoteMpGrid = numLoteMpGrid;
	}

	public List<SeccionBean> getLstSecciones() {
		return lstSecciones;
	}

	public void setLstSecciones(List<SeccionBean> lstSecciones) {
		this.lstSecciones = lstSecciones;
	}

	public NumLoteMpGrid getNumLoteMpGrid() {
		return numLoteMpGrid;
	}

	public void setNumLoteMpGrid(NumLoteMpGrid numLoteMpGrid) {
		this.numLoteMpGrid = numLoteMpGrid;
	}
}
