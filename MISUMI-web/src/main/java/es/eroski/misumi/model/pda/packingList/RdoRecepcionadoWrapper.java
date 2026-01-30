package es.eroski.misumi.model.pda.packingList;

public class RdoRecepcionadoWrapper{

	private RdoRecepcionadoOK rdoRecepcionadoOK;
	private RdoApiPackingKO rdoRecepcionadoKO;
	
	public RdoRecepcionadoWrapper(){
		super();
		
		this.rdoRecepcionadoOK = new RdoRecepcionadoOK();
		this.rdoRecepcionadoKO = new RdoApiPackingKO();
	}
	
	public RdoRecepcionadoWrapper(RdoRecepcionadoOK rdoRecepcionadoOK, RdoApiPackingKO rdoRecepcionadoKO) {
		super();
		this.rdoRecepcionadoOK = rdoRecepcionadoOK;
		this.rdoRecepcionadoKO = rdoRecepcionadoKO;
	}

	public RdoApiPackingKO getRdoRecepcionadoKO() {
		return rdoRecepcionadoKO;
	}

	public void setRdoRecepcionadoKO(RdoApiPackingKO rdoRecepcionadoKO) {
		this.rdoRecepcionadoKO = rdoRecepcionadoKO;
	}

	public RdoRecepcionadoOK getRdoRecepcionadoOK() {
		return rdoRecepcionadoOK;
	}

	public void setRdoRecepcionadoOK(RdoRecepcionadoOK rdoRecepcionadoOK) {
		this.rdoRecepcionadoOK = rdoRecepcionadoOK;
	}

}
