package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.TOptima;

public interface TOptimaService {

	public abstract List<TOptima> getHuecos(TOptima tOptima) throws Exception;

	public abstract void deleteHuecos(TOptima tOptima) throws Exception;
	
	public abstract void insertHueco(TOptima tOptima) throws Exception;
	
	public abstract List<TOptima> sendGISAE(TOptima tOptima) throws Exception;

}