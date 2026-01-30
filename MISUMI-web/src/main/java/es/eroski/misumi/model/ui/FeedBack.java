package es.eroski.misumi.model.ui;

public class FeedBack {

	/*private static final String[] REPLACEMENT = new String[Character.MAX_VALUE];
	static {
	    for(int i=Character.MIN_VALUE;i<Character.MAX_VALUE;i++){
	        REPLACEMENT[i] = Character.toString((char) i);
	    }
    	// substitute
    	REPLACEMENT['á'] =  "\\u00E1";
    	REPLACEMENT['é'] =  "\\u00E9";
    	REPLACEMENT['í'] =  "\\u00ED";
    	REPLACEMENT['ó'] =  "\\u00F3";
    	REPLACEMENT['ú'] =  "\\u00fa";
    	REPLACEMENT['Á'] =  "\\u00C1";
    	REPLACEMENT['É'] =  "\\u00C9";
    	REPLACEMENT['Í'] =  "\\u00CD";
    	REPLACEMENT['Ó'] =  "\\u00D3";
    	REPLACEMENT['Ú'] =  "\\u00DA";
    	REPLACEMENT['ñ'] =  "\\u00F1";
    	REPLACEMENT['Ñ'] =  "\\u00D1";
    	REPLACEMENT['º'] =  "\\u00D2";
	}*/
	
	private String INFO = "INFO";
	private String ERROR = "ERROR";
	
	private String message = null;
	private String messageType = this.INFO;
	private String redirect = null;
	
	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public FeedBack(String feedBack){
		this.message = feedBack;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String feedBack) {
		this.message = feedBack;
	}

	public String getRedirect() {
		return this.redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getINFO() {
		return this.INFO;
	}

	public void setINFO(String iNFO) {
		this.INFO = iNFO;
	}

	public String getERROR() {
		return this.ERROR;
	}

	public void setERROR(String eRROR) {
		this.ERROR = eRROR;
	}

	
	
	
		
}