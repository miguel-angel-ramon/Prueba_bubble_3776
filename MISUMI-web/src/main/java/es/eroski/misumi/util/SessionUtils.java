package es.eroski.misumi.util;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * Utilidades de session
 *
 */
public class SessionUtils {

	/**
	 * Obtiene una lista cuyo nombre de Lista comience por "lista", que se encuentren en sesión.
	 * @param session
	 * @return
	 */
	public static List<?> getListaSesion(HttpSession session, String prefijoLista) {
        Enumeration<String> nombreAtributos = session.getAttributeNames();

        while (nombreAtributos.hasMoreElements()) {
            String nombreAtributo = (String) nombreAtributos.nextElement();
            if (nombreAtributo.startsWith(prefijoLista)) {
                Object atributoLista = session.getAttribute(nombreAtributo);
                if (atributoLista instanceof List) {
                	return (List<?>) atributoLista;
                }
            }
        }
        return null;
	}

	/**
	 * 
	 * @param session
	 */
	public static void limpiarAtributosLista(HttpSession session) {
        if (session != null) {
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                // Verificar si el nombre del atributo comienza con "lista"
                if (attributeName.startsWith("lista") || attributeName.startsWith("urlBasePalet")) {
                    session.removeAttribute(attributeName);
                }
            }
        }
    }

    /**
     * Comprueba el estado de generación del Excel en sesión.
     * @param session HttpSession actual
     * @return "0" si no se está generando, "1" si se reinicia el estado
     */
    public static String comprobarGeneracionExcel(HttpSession session) {
        String listadoExcelGenerado = (String) session.getAttribute("listadoExcelGenerado");

        if ("0".equals(listadoExcelGenerado)) {
            return listadoExcelGenerado;
        } else {
            session.setAttribute("listadoExcelGenerado", "0");
            return "1";
        }
    }
}
