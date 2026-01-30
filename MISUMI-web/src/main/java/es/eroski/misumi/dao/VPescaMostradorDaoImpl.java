package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VPescaMostradorDao;
import es.eroski.misumi.model.InformeListadoPesca;
import es.eroski.misumi.model.VPescaMostrador;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VPescaMostradorDaoImpl implements VPescaMostradorDao {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<VPescaMostrador> rwVPescaMostradorMap = new RowMapper<VPescaMostrador>() {
		public VPescaMostrador mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new VPescaMostrador(resultSet.getLong("COD_CENTRO"), resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"), resultSet.getLong("SUBCATEGORIA"),
					resultSet.getLong("SEGMENTO"), resultSet.getString("DESC_SUBCATEGORIA"),
					resultSet.getLong("ORDEN_LISTADO"), resultSet.getString("IDENTIFICA_SUBCAT"),
					resultSet.getLong("COD_ART"), resultSet.getString("DENOMINACION"),
					resultSet.getDouble("UNIDADES_CAJA"), resultSet.getLong("EAN"),
					resultSet.getDouble("IMPORTE_TIRADO"), resultSet.getLong("POR_IMP_TIRADO"),
					resultSet.getLong("PROP_PEDIR"), resultSet.getLong("PED_MANANA_CAJAS"),
					resultSet.getString("OFERTA_VIGOR_INI"), resultSet.getString("OFERTA_VIGOR_FIN"),
					resultSet.getString("OFERTA_FUTURA_INI"), resultSet.getString("OFERTA_FUTURA_FIN"),
					resultSet.getString("FLG_HABITUAL"), false);
		}

	};

	private RowMapper<InformeListadoPesca> rwInformeListadoPescaMap = new RowMapper<InformeListadoPesca>() {
		public InformeListadoPesca mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new InformeListadoPesca(resultSet.getLong("ORDEN_LISTADO"), 
					resultSet.getString("IDENTIFICA_SUBCAT"),
					resultSet.getString("DESC_SUBCATEGORIA"));
		}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<VPescaMostrador> findAll(VPescaMostrador vPescaMostrador) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, "
				+ " DESC_SUBCATEGORIA, ORDEN_LISTADO, IDENTIFICA_SUBCAT, COD_ART, "
				+ " DENOMINACION, UNIDADES_CAJA, EAN, IMPORTE_TIRADO, POR_IMP_TIRADO, "
				+ " PROP_PEDIR, PED_MANANA_CAJAS, OFERTA_VIGOR_INI, OFERTA_VIGOR_FIN, OFERTA_FUTURA_INI, OFERTA_FUTURA_FIN, FLG_HABITUAL " + " FROM V_PESCA_MOSTRADOR ");

		if (vPescaMostrador != null) {
			if (vPescaMostrador.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(vPescaMostrador.getCodCentro());
			}
			if (vPescaMostrador.getArea() != null) {
				where.append(" AND AREA = ? ");
				params.add(vPescaMostrador.getArea());
			}
			if (vPescaMostrador.getSeccion() != null) {
				where.append(" AND SECCION = ? ");
				params.add(vPescaMostrador.getSeccion());
			}
			if (vPescaMostrador.getCategoria() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(vPescaMostrador.getCategoria());
			}
			if (vPescaMostrador.getSubcategoria() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(vPescaMostrador.getSubcategoria());
			}
			if (vPescaMostrador.getSegmento() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(vPescaMostrador.getSegmento());
			}
			if (vPescaMostrador.getDescSubcategoria() != null) {
				where.append(" AND DESC_SUBCATEGORIA = ? ");
				params.add(vPescaMostrador.getDescSubcategoria());
			}
			if (vPescaMostrador.getOrdenListado() != null) {
				where.append(" AND ORDEN_LISTADO = ? ");
				params.add(vPescaMostrador.getOrdenListado());
			}
			if (vPescaMostrador.getIdentificaSubcat() != null) {
				where.append(" AND IDENTIFICA_SUBCAT = ? ");
				params.add(vPescaMostrador.getIdentificaSubcat());
			}
			if (vPescaMostrador.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(vPescaMostrador.getCodArt());
			}
			if (vPescaMostrador.getDenominacion() != null) {
				where.append(" AND DENOMINACION = ? ");
				params.add(vPescaMostrador.getDenominacion());
			}
			if (vPescaMostrador.getUnidadesCaja() != null) {
				where.append(" AND UNIDADES_CAJA = ? ");
				params.add(vPescaMostrador.getUnidadesCaja());
			}
			if (vPescaMostrador.getEan() != null) {
				where.append(" AND EAN = ? ");
				params.add(vPescaMostrador.getEan());
			}
			if (vPescaMostrador.getImporteTirado() != null) {
				where.append(" AND IMPORTE_TIRADO = ? ");
				params.add(vPescaMostrador.getImporteTirado());
			}
			if (vPescaMostrador.getPorImpTirado() != null) {
				where.append(" AND POR_IMP_TIRADO = ? ");
				params.add(vPescaMostrador.getPorImpTirado());
			}
			if (vPescaMostrador.getPropPedir() != null) {
				where.append(" AND PROP_PEDIR = ? ");
				params.add(vPescaMostrador.getPropPedir());
			}
			if (vPescaMostrador.getPedMananaCajas() != null) {
				where.append(" AND PED_MANANA_CAJAS = ? ");
				params.add(vPescaMostrador.getPedMananaCajas());
			}
			if (vPescaMostrador.getOfertaVigorIni() != null) {
				where.append(" AND OFERTA_VIGOR_INI = ? ");
				params.add(vPescaMostrador.getOfertaVigorIni());
			}
			if (vPescaMostrador.getOfertaVigorFin() != null) {
				where.append(" AND OFERTA_VIGOR_FIN = ? ");
				params.add(vPescaMostrador.getOfertaVigorFin());
			}
			if (vPescaMostrador.getOfertaFuturaIni() != null) {
				where.append(" AND OFERTA_FUTURA_INI = ? ");
				params.add(vPescaMostrador.getOfertaFuturaIni());
			}
			if (vPescaMostrador.getOfertaFuturaFin() != null) {
				where.append(" AND OFERTA_FUTURA_FIN = ? ");
				params.add(vPescaMostrador.getOfertaFuturaFin());
			}
			if (vPescaMostrador.getFlgHabitual() != null) {
				where.append(" AND NVL(FLG_HABITUAL,'N') = ? ");
				params.add(vPescaMostrador.getFlgHabitual());
			}
		}

		query.append(where);

		List<VPescaMostrador> vPescaMostradorLista = null;
		
		try {
			vPescaMostradorLista = (List<VPescaMostrador>) this.jdbcTemplate.query(query.toString(),
					this.rwVPescaMostradorMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}

		return vPescaMostradorLista;
	}

	@Override
	public Long getInformeListadoPescaCount(VPescaMostrador vPescaMostrador) throws Exception{
		
		return (long) this.findAllInformeListadoPesca(vPescaMostrador).size();
	}
	
	@Override
	public List<InformeListadoPesca> findAllInformeListadoPesca(VPescaMostrador vPescaMostrador) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" SELECT DISTINCT ORDEN_LISTADO, IDENTIFICA_SUBCAT, DESC_SUBCATEGORIA FROM V_PESCA_MOSTRADOR ");

		if (vPescaMostrador != null) {
			if (vPescaMostrador.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(vPescaMostrador.getCodCentro());
			}
			if (vPescaMostrador.getIdentificaSubcat() != null) {
				where.append(" AND IDENTIFICA_SUBCAT = ? ");
				params.add(vPescaMostrador.getIdentificaSubcat());
			}
			if (vPescaMostrador.getFlgHabitual() != null) {
				where.append(" AND FLG_HABITUAL IN(");
				
				//Calculamos el número de parámetros.
				String[] parametros = vPescaMostrador.getFlgHabitual().split(",");
				if(parametros.length > 1){
					where.append("?,?");
					params.add(parametros[0]);
					params.add(parametros[1]);
				}else{
					where.append("?");
					params.add(vPescaMostrador.getFlgHabitual());					
				}
				where.append(")");
			}

		}

		query.append(where);

		order.append(" ORDER BY ORDEN_LISTADO ASC, IDENTIFICA_SUBCAT ASC, DESC_SUBCATEGORIA ASC ");
		
		query.append(order);
		
		List<InformeListadoPesca> listaInformeListadoPesca = null;
		
		try {
			listaInformeListadoPesca = (List<InformeListadoPesca>) this.jdbcTemplate.query(query.toString(),
					this.rwInformeListadoPescaMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		return listaInformeListadoPesca;
	}

	@Override
	public List<VPescaMostrador> findAll(Long codCentro, List<String> listaSubcategoria) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, "
				+ " DESC_SUBCATEGORIA, ORDEN_LISTADO, IDENTIFICA_SUBCAT, COD_ART, "
				+ " DENOMINACION, UNIDADES_CAJA, EAN, IMPORTE_TIRADO, POR_IMP_TIRADO, "
				+ " PROP_PEDIR, PED_MANANA_CAJAS, OFERTA_VIGOR_INI, OFERTA_VIGOR_FIN, OFERTA_FUTURA_INI, OFERTA_FUTURA_FIN, FLG_HABITUAL "
				+ " FROM V_PESCA_MOSTRADOR ");

		if (codCentro != null && listaSubcategoria != null) {
			
			where.append(" AND COD_CENTRO = ? ");
			params.add(codCentro);
			
			StringBuffer subcategorias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			for (String subcategoria : listaSubcategoria) {
				subcategorias.append( subcategorias.length() == 0 ? "" : ", ").append("'").append(subcategoria).append("'");
			}
			where.append(" AND IDENTIFICA_SUBCAT IN ( ").append(subcategorias).append(" )");
		}

		query.append(where);

		List<VPescaMostrador> vPescaMostradorLista = null;

		try {
			vPescaMostradorLista = (List<VPescaMostrador>) this.jdbcTemplate.query(query.toString(), this.rwVPescaMostradorMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		return vPescaMostradorLista;
	}

	@Override
	public List<String> listaCodigosHabituales(Long codCentro, List<String> listaSubcategoria) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT DISTINCT NVL(FLG_HABITUAL,'NULL') HABITUAL "
				+ " FROM V_PESCA_MOSTRADOR ");

		if (codCentro != null && listaSubcategoria != null) {
			
			where.append(" AND COD_CENTRO = ? ");
			params.add(codCentro);
			
			StringBuffer subcategorias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			for (String subcategoria : listaSubcategoria) {
				subcategorias.append( subcategorias.length() == 0 ? "" : ", ").append("'").append(subcategoria).append("'");
			}
			where.append(" AND IDENTIFICA_SUBCAT IN ( ").append(subcategorias).append(" )");
		}

		query.append(where);

		//Ordenamos por 'S' (HABILITADOS), luego por 'N' (NO HABILITADOS) y luego por NULL (el resto)
		order.append(" ORDER BY DECODE(HABITUAL,'S',0,DECODE(HABITUAL,'N',1,2)) ASC ");
		
		query.append(order);
		

		List<String> listaCodHab = null;

		try {
			listaCodHab = this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		return listaCodHab;
	}

	@Override
	public Long countAllInformeListadoPesca(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer query = new StringBuffer(" SELECT COUNT(*) FROM (SELECT DISTINCT ORDEN_LISTADO "
				+ " FROM V_PESCA_MOSTRADOR WHERE COD_CENTRO = ? )");
		params.add(codCentro);
		
		Long cont = null;   
 	    try {     		
 	    	cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());         	
 		} catch (Exception e){ 			
 			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
 		}
 	    return cont;		
	}
}
