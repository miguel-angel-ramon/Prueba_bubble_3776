package es.eroski.misumi.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.formula.functions.T;

import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.iface.PaginationManager;

/**
 * Utilidades de paginacion
 *
 */
public class Paginate {

	private static final String prefijoLista = "lista";
	
    public static String getQueryLimits(Pagination pagination , String query){
		String queryAux="";
		if (pagination.getPage()!=null && pagination.getRows()!=null){
			Long paginationRows = pagination.getRows();	
			queryAux= "SELECT * "
					+ "FROM (SELECT rownum rnum, a.* "
						  + "FROM (" + query + ") a"
						  + ") "
					+ "WHERE rnum > " + (paginationRows*(pagination.getPage()-1))
					+" AND rnum < " +(paginationRows*(pagination.getPage())+1);
			return queryAux;
		}else{
			Long paginationRows = pagination.getRows();	
			queryAux= "SELECT * FROM (SELECT rownum rnum, a.*  FROM (" + query + ")a) where rnum > 1 and rnum < " +(paginationRows+1);
			return queryAux;
		}
    }


	/**
	 * Recorta la lista pasada por argumento aplicando los limites de la paginacion.
	 * @param <T>
	 * @param pagination
	 * @param retorno
	 * @return
	 */
	public static <H> List<H> aplicarLimitesPaginacion(Pagination pagination, List<H> lista) {
		List<H> retorno = lista;
		if(lista!=null && pagination!=null && pagination.getRows()!=null){
			int fromIndex = 0;
			int toIndex = retorno.size();
			int rows = pagination.getRows().intValue();
			int numPage = pagination.getPage()==null ? 0 : pagination.getPage().intValue() -1;
			numPage = Math.max(numPage, 0);
			fromIndex = Math.max(fromIndex, rows * numPage);
			toIndex = Math.min(toIndex, rows * (numPage + 1));
			retorno = lista.subList(fromIndex , toIndex );
		}
		return retorno;
	}
	
	public static <U> Page<U> paginarListaDatos(HttpSession session, String pag, String pgTot, String botPag, int regsPorPagina, ArrayList<String> listaBotones) throws Exception{

		List<U> lista = (List<U>) SessionUtils.getListaSesion(session, prefijoLista);

		Page<U> result = null;
		int records = lista==null || lista.size()==0?0:lista.size();
		int page = 1;

		if (lista!= null && records>0) {

			page = Integer.parseInt(pag);
			int pageTot = Integer.parseInt(pgTot);

			if (botPag.equalsIgnoreCase(listaBotones.get(0))){
				page = 1;
			}else if (botPag.equalsIgnoreCase(listaBotones.get(1))){
				page = page -1;
			}else if (botPag.equalsIgnoreCase(listaBotones.get(2))){
				page = page +1;
			}else if (botPag.equalsIgnoreCase(listaBotones.get(3))){
				page = pageTot;
			}
			
			if (lista.size()>Constantes.DEVOLUCIONES_PDA_PAGS){
				lista = Paginate.obtenerSubLista(lista, page, pageTot, records, regsPorPagina);
			}

			PaginationManager<U> paginationManager = new PaginationManagerImpl<U>();
			result = paginationManager.paginate(new Page<U>(), lista, regsPorPagina, records, page);	
		} else {
			return new Page<U>();
		}

		return result;
	}

	private static <R> List<R> obtenerSubLista(List<R> lista, int pag, int pagTot, int records, int regsPorPagina) throws Exception{

		int inicio = regsPorPagina*(pag-1);
		int fin = 0;	

		if (pag == pagTot){
			fin = records;
		}else{
			fin = regsPorPagina*pag;
		}

		//Si el registro final calculado es mayor que el número de registros se corrige para hacerlo coincidir con el 
		//registro final
		if (fin>records){
			fin = records;
		}

		return (lista).subList(inicio, fin);
	}

}
