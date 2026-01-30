package es.eroski.misumi.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userName;
	@NotEmpty
	private String code;
	@NotEmpty
	private String password;
	private Long perfil;
	private Centro centro;
	
	//Multicentro
	private boolean isMulticentro ;
	private ArrayList<Centro> listaCentros;
	
	//Indica si el combo de seleccion de centros debe estar activo o no (Solo para el perfil 3)
	private String selCentro;

	//Nombre usuario de tama�o restringido en cabecera
	private String userNameCab;

	//Identificación de la MAC para entradas desde pistola
	private String mac;
	
	//Identificación de la MAC para entradas desde pistola pero para la funcionalidad de PREHUECOS.
	private String macPrehueco;
	
	private String flgFocoInfoRef;
	
	//Parametros pistola IMPRESION
	private BigInteger idPistola;
	private String buzon;
	
	//MISUMI-266 
	private String flgCestas;
	
	//MISUMI-621
	private String flgAvisos;
	
	public User() {
	    super();
	}

	public User(String code,String userName, String password, Long perfil, Centro centro, String flgCestas, String flgAvisos) {
	    super();
	    this.code=code;
	    this.userName = userName;
	    this.password = password;
	    this.perfil = perfil;
	    this.centro = centro;
	    this.flgCestas = flgCestas;
	    this.flgAvisos = flgAvisos;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Centro getCentro() {
		return this.centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Long getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSelCentro() {
		return this.selCentro;
	}

	public void setSelCentro(String selCentro) {
		this.selCentro = selCentro;
	}
	
	public boolean isMulticentro() {
		return isMulticentro;
	}

	public void setMulticentro(boolean isMulticentro) {
		this.isMulticentro = isMulticentro;
	}

	public ArrayList<Centro> getListaCentros() {
		return listaCentros;
	}

	public void setListaCentros(ArrayList<Centro> listaCentros) {
		this.listaCentros = listaCentros;
	}

	public String getUserNameCab() {
		return this.userNameCab;
	}

	public void setUserNameCab(String userNameCab) {
		this.userNameCab = userNameCab;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getFlgFocoInfoRef() {
		return this.flgFocoInfoRef;
	}

	public void setFlgFocoInfoRef(String flgFocoInfoRef) {
		this.flgFocoInfoRef = flgFocoInfoRef;
	}

	public BigInteger getIdPistola() {
		return idPistola;
	}

	public void setIdPistola(BigInteger idPistola) {
		this.idPistola = idPistola;
	}

	public String getBuzon() {
		return buzon;
	}

	public void setBuzon(String buzon) {
		this.buzon = buzon;
	}

	public String getFlgCestas() {
		return flgCestas;
	}

	public void setFlgCestas(String flgCestas) {
		this.flgCestas = flgCestas;
	}

	public String getMacPrehueco() {
		return macPrehueco;
	}

	public void setMacPrehueco(String macPrehueco) {
		this.macPrehueco = macPrehueco;
	}

	public String getFlgAvisos() {
		return flgAvisos;
	}

	public void setFlgAvisos(String flgAvisos) {
		this.flgAvisos = flgAvisos;
	}
}