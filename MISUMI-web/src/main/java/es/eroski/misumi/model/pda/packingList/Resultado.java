package es.eroski.misumi.model.pda.packingList;

import java.io.Serializable;
import java.util.List;

public class Resultado implements Serializable {

    private static final long serialVersionUID = 1L;

	private int total;
	private String last;
	private int page;
	private List<ConsultaMatricula> items;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<ConsultaMatricula> getItems() {
		return items;
	}

	public void setItems(List<ConsultaMatricula> items) {
		this.items = items;
	}

}
