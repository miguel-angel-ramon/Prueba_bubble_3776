package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class GenericExcelFieldsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public List<Object> fields;
	
	public GenericExcelFieldsVO(){
		super();
		this.fields = new ArrayList<Object>();
	};
	
	public void addField(Object field){
		fields.add(field);
	}
	
	public Object getField(int i){
		return fields.get(i);
	}
}