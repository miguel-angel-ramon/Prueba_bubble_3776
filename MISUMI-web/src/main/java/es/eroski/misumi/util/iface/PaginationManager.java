package es.eroski.misumi.util.iface;

import java.util.List;

import es.eroski.misumi.model.ui.Page;

public interface PaginationManager<T> {

	public Page<T> paginate(Page<T> resource, List<T> list, int max, int records, int page);
	
	public abstract int getPaginationLimit(int size, int max, int page);
	
	public int getTotalPages(int records, int max);

}