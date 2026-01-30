package es.eroski.misumi.util;

import java.util.List;

import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.util.iface.PaginationManager;



public class PaginationManagerImpl<T> implements PaginationManager<T> {
	//private static Logger logger = LoggerFactory.getLogger(PaginationManagerImpl.class);
	//private static Logger logger = Logger.getLogger(PaginationManagerImpl.class);

	@Override
	public Page<T> paginate (Page<T> resource, List<T> list, int max, int records, int page){
		resource.setRows(list);
		resource.setRecords(records+"");
		resource.setPage(page+"");
		int total = this.getTotalPages(records, max);
		resource.setTotal(total+"");
		
		return resource;
	}
	
	public int getPaginationLimit(int size, int max, int page){
		if ((page*max) <= size){
			size = (page*max);
		}
		return size;
	}
	
	public int getTotalPages(int records, int max){
		int total = records/max;
		if(records%max != 0){
			total++;
		}
		return total;
	}
	
}