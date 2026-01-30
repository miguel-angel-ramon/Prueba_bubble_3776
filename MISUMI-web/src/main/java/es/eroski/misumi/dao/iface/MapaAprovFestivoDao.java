package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.MapaAprovFestivo;

public interface MapaAprovFestivoDao {

	List<MapaAprovFestivo> getFechasPedido(MapaAprovFestivo mapaAprovFestivo) throws Exception;

}