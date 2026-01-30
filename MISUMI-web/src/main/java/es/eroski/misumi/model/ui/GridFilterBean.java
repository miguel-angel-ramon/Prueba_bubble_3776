package es.eroski.misumi.model.ui;

import java.io.Serializable;


/**
 * @author BICUGUAL
 *
 */
@SuppressWarnings("serial")
public class GridFilterBean implements Serializable {

	private Long page;
	private Long max;
	private String sord;
	private String sidx;
	private Boolean search;
	
	private FilterBean filtros;

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}

	public FilterBean getFiltros() {
		return filtros;
	}

	public void setFiltros(FilterBean filtros) {
		this.filtros = filtros;
	}
	
}