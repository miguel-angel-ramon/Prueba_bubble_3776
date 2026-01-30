package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CampanasOfertasMisumi;

public interface CampanasOfertasMisumiDao  {

	public List<CampanasOfertasMisumi> findAll(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception ;
	public List<CampanasOfertasMisumi> findCampanas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception ;
	public List<CampanasOfertasMisumi> findOfertas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception ;

}
