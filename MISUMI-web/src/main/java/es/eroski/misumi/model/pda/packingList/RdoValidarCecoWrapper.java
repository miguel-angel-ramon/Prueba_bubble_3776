package es.eroski.misumi.model.pda.packingList;

public class RdoValidarCecoWrapper{

	private RdoValidarCecoOK rdoValidarCecoOK;
	private RdoApiPackingKO rdoValidarCecoKO;
	
	public RdoValidarCecoWrapper(){
		super();
		
		this.rdoValidarCecoOK = new RdoValidarCecoOK();
		this.rdoValidarCecoKO = new RdoApiPackingKO();
	}
	
	public RdoValidarCecoWrapper(RdoValidarCecoOK rdoValidarCecoOK, RdoApiPackingKO rdoValidarCecoKO) {
		super();
		this.rdoValidarCecoOK = rdoValidarCecoOK;
		this.rdoValidarCecoKO = rdoValidarCecoKO;
	}

	public RdoValidarCecoOK getRdoValidarCecoOK() {
		return rdoValidarCecoOK;
	}

	public void setRdoValidarCecoOK(RdoValidarCecoOK rdoValidarCecoOK) {
		this.rdoValidarCecoOK = rdoValidarCecoOK;
	}

	public RdoApiPackingKO getRdoValidarCecoKO() {
		return rdoValidarCecoKO;
	}

	public void setRdoValidarCecoKO(RdoApiPackingKO rdoValidarCecoKO) {
		this.rdoValidarCecoKO = rdoValidarCecoKO;
	}

}
