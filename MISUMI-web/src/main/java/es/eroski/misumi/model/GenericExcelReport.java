/**
 * 
 */
package es.eroski.misumi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de proposito general que encapsula la lista de objetos a exportar a excel y 
 * las cabeceras (nombre de columnas) del listado
 * @author BICUGUAL
 *
 */
public class GenericExcelReport {

	private List<String> columnNames;
	private List<GenericExcelRow> datos=new ArrayList<GenericExcelRow>();
	
	public GenericExcelReport() {
		super();
	}

	public GenericExcelReport(List<String> columnNames, List<GenericExcelRow> datos) {
		super();
		this.columnNames = columnNames;
		this.datos = datos;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public List<GenericExcelRow> getDatos() {
		return datos;
	}
	public void setDatos(List<GenericExcelRow> datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		return "GenericExcelReport [" + (columnNames != null ? "columnNames=" + columnNames + ", " : "")
				+ (datos != null ? "datos=" + datos : "") + "]";
	}
	
	
	
	
	
}
