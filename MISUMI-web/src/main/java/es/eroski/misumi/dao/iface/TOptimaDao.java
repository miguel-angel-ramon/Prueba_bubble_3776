package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.TOptima;

public interface TOptimaDao {

	public abstract List<TOptima> getHuecos(TOptima tOptima) throws Exception;

	public abstract void borrarHuecos(TOptima tOptima) throws Exception;
	
	public abstract void insertHueco(TOptima tOptima) throws Exception;
	
	public abstract void updateHueco(TOptima tOptima) throws Exception;

}