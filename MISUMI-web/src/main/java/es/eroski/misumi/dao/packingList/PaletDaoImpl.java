package es.eroski.misumi.dao.packingList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.packingList.iface.PaletDao;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PaletDaoImpl implements PaletDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<Palet> getEntradasPalets(Long centro, String mac) {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT * " 
											+ "FROM t_mis_entradas_palets "
											+ "WHERE cod_centro = ? "
											+ "AND mac 			= ? "
// Se comenta para PRUEBAS. Para no tener que estar dando de alta registros continuamente en BBDD en la tabla.											
											+ "AND fecha_gen 	= TRUNC(SYSDATE) "
											);

		params.add(centro);
		params.add(mac);

		query.append(where);

		//Se añade un order by según la columna clicada para la ordenación. Si no hay ordenación por columna (como cuando cargas la 1 vez el grid)
		//se ordena por pasillo/denominacion proveedor/estructura comercial/stock_actual
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append("ORDER BY hora_recepcion DESC");
		query.append(order);

		List<Palet> lista = new ArrayList<Palet>();
		try {
			lista = (List<Palet>) this.jdbcTemplate.query(query.toString(), this.rwPaletMapper, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return lista;
	}

	@Override
	public Boolean saveEntradaPalet(Palet paletRecepcionado, String mac){

		String insertSQL = "INSERT INTO t_mis_entradas_palets "
								+ " ( cod_centro, fecha_gen, mac, matricula"
								 + ", permiteactualizar, recepcionado, usuario"
								 + ", fec_recepcion, hora_recepcion, f_albaran"
								 + ", plataforma, visible, pertenececentro"
								 + ") "
						 + "VALUES "
						 		+ "( ?, ?, ?, ?"
						 		+ ", ?, ?, ?"
						 		+ ", ?, ?, ?"
						 		+ ", ?, ?, ?"
						 		+ ")";

		List<Object> values = new ArrayList<Object>();
		values.add(paletRecepcionado.getCeco());
		values.add(Utilidades.convertirStringAFechaSqlDate(paletRecepcionado.getFrecepcion(),Constantes.formatoFecha_YYYYMMdd));
		values.add(mac);
		values.add(paletRecepcionado.getMatricula());
		values.add(paletRecepcionado.isPermiteActualizar()?"S":"N");
		values.add(paletRecepcionado.getRecepcionado());
		values.add(paletRecepcionado.getUsuario());
		values.add(Utilidades.convertirStringAFechaSqlDate(paletRecepcionado.getFrecepcion(),Constantes.formatoFecha_YYYYMMdd));
		values.add(paletRecepcionado.getHrecepcion());
		values.add(Utilidades.convertirStringAFechaSqlDate(paletRecepcionado.getFechaalbaran(),Constantes.formatoFecha_TIMESTAMP));
		values.add(paletRecepcionado.getPlataforma());
		values.add("true".equals(paletRecepcionado.isVisible())?"S":"N");
		values.add("".equals(paletRecepcionado.getPertenececentro())?null:paletRecepcionado.getPertenececentro());
		
		
//		values.add(detalladoPedido.getPrecioCostoArticulo() != null
//				&& !("".equals(detalladoPedido.getPrecioCostoArticulo().toString()))
//						? new Double(detalladoPedido.getPrecioCostoArticulo().toString()) : new Double(0));

		// Ejecutamos el comando SQL
		try {
			this.jdbcTemplate.update(insertSQL, values.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(insertSQL.toString(), values, e);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}
	
	private RowMapper<Palet> rwPaletMapper = new RowMapper<Palet>() {
		public Palet mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			Palet palet = new Palet();
			
			palet.setCeco(resultSet.getInt("cod_centro"));
			palet.setMatricula(resultSet.getString("matricula"));
			palet.setRecepcionado(resultSet.getString("recepcionado"));
			palet.setUsuario(resultSet.getString("usuario"));
			palet.setFrecepcion(Utilidades.formatearFecha(resultSet.getDate("fec_recepcion"), "yyyyMMdd"));
			palet.setFrecepcionFormateada(Utilidades.formatearFecha(resultSet.getDate("fec_recepcion"), Constantes.FORMATOFECHA_ES));
			palet.setHrecepcion(resultSet.getInt("hora_recepcion"));
			if (palet.getHrecepcion()!=null){
				palet.setHrecepcionFormateada(Utilidades.formatearHora(palet.getHrecepcion()));
			}
			palet.setFechaalbaran(resultSet.getString("f_albaran"));
			palet.setPlataforma(resultSet.getInt("plataforma"));
			palet.setPertenececentro(resultSet.getString("pertenececentro"));
			
			return palet;
		}
	};

}
