/**
 * 
 */
package es.eroski.misumi.model.ui;

import java.io.Serializable;

/**
 * @author BICUGUAL
 * Representacion de todos los datos que viajan del jqgrid cuando se realiza una busqueda mediante la toolbar del grid
 */
@SuppressWarnings("serial")
public class FilterBean implements Serializable {
	
	private String groupOp;
	private RulesBean[] rules;
	
	public FilterBean() {
		super();
	}
	
	public FilterBean(String groupOp, RulesBean[] rules) {
		super();
		this.groupOp = groupOp;
		this.rules = rules;
	}
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public RulesBean[] getRules() {
		return rules;
	}
	public void setRules(RulesBean[] rules) {
		this.rules = rules;
	}
	
	
}