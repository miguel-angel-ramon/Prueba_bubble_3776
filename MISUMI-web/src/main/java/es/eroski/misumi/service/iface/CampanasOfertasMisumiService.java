package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.CampanasOfertasMisumi;


public interface CampanasOfertasMisumiService {

	  public List<CampanasOfertasMisumi> findAll(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception  ;
	  public List<CampanasOfertasMisumi> findCampanas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception  ;
	  public List<CampanasOfertasMisumi> findOfertas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception  ;
}
