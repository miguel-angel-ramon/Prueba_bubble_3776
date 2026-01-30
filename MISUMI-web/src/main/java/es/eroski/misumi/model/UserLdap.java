package es.eroski.misumi.model;

import java.io.Serializable;

public class UserLdap implements Serializable{
	private static final long serialVersionUID = 1L;

	private String code;
    private String name;
    private String surname;

    public UserLdap(){
    	
    }
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
   
}
