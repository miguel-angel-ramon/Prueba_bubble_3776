/**
 * 
 */
package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author BICUGUAL
 *
 */
public class DetalladoMostrador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<VMisDetalladoMostrador> rows;
	private DetalladoMostradorInfo userData;
	
	public DetalladoMostrador() {
		super();
	}
	
	public List<VMisDetalladoMostrador> getRows() {
		return rows;
	}
	public DetalladoMostradorInfo getUserData() {
		return userData;
	}
	public void setRows(List<VMisDetalladoMostrador> rows) {
		this.rows = rows;
	}
	public void setUserData(DetalladoMostradorInfo userData) {
		this.userData = userData;
	}
	
	

}
