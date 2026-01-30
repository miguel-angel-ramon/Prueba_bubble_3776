package es.eroski.misumi.service.packingList.iface;

import java.sql.SQLException;
import java.util.List;

import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.packingList.RdoListarArticulosWrapper;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoWrapper;

/**
 * Interfaz para el servicio de consulta de matrícula y palets.
 * 
 * @author BICAGAAN
 *
 */
public interface ConsultaMatriculaService {

    /**
     * Obtiene la lista de palets a los que se ha dado entrada en el día de hoy.
     * @param centro Centro
     * @param mac Dirección MAC
     * @return Lista de palets
     * @throws SQLException Si hay un error con la base de datos
     */
    public RdoValidarCecoWrapper validarCeco(String ceco);

    /**
     * Recupera los artículos listados por matrícula.
     * Este método se usa para obtener una lista de artículos filtrados por matrícula, fecha, y otros parámetros de búsqueda.
     * @param numRows Número de filas
     * @param page Número de página para paginación
     * @param code Código del artículo
     * @param description Descripción del artículo
     * @param matricula Matricula de los artículos
     * @param startDate Fecha de inicio del rango de búsqueda
     * @param endDate Fecha de fin del rango de búsqueda
     * @return Wrapper con los artículos encontrados
     */
    public RdoListarArticulosWrapper listarArticulosPorMatricula(String matricula, String ceco);
	
}
