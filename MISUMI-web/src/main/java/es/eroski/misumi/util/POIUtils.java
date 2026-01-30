/**
 * 
 */
package es.eroski.misumi.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * Utilidades para la exportacion de datos a Excel utilizando libreria POI
 * 
 * @author BICUGUAL
 *
 */
public class POIUtils {

	/**
	 * Funcion que devuelve un estilo para el background
	 *
	 * @param workbook
	 * @param color
	 * @return
	 */
	public static CellStyle getStyleBackground(Workbook workbook, short color) {

		CellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		return cellStyle;
	}

	public static CellStyle changeCellBackgroundColorWithPattern(Cell cell, short color) {
	   
		CellStyle cellStyle = cell.getCellStyle();
	    
		if(cellStyle == null) {
	        cellStyle = cell.getSheet().getWorkbook().createCellStyle();
	    }
	    cellStyle.setFillBackgroundColor(color);
	    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    return cellStyle;
	}
	
	
	/**
	 * Funcion que devuelve una fuente con el color de texto para un estilo
	 * 
	 * @param workbook
	 * @param color
	 * @return
	 */
	public static Font getStyleTextColor(Workbook workbook, short color) {
		Font font = workbook.createFont();
		font.setColor(color);
		return font;
	}

}
