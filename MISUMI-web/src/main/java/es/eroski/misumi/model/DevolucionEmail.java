package es.eroski.misumi.model;

public class DevolucionEmail {
	private String email;
	private Long codError;
	private String msgError;
	
	public DevolucionEmail(String email, Long codError, String msgError) {
		super();
		this.email = email;
		this.codError = codError;
		this.msgError = msgError;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getMsgError() {
		return msgError;
	}
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
}
