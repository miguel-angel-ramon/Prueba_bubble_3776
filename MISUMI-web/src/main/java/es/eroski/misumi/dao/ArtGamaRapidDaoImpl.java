package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ArtGamaRapidDao;
import es.eroski.misumi.model.ArtGamaRapid;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class ArtGamaRapidDaoImpl implements ArtGamaRapidDao {

	private JdbcTemplate jdbcTemplate;

	private static Logger logger = Logger.getLogger(ArtGamaRapidDaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Boolean centroCargado(Long codCentro) throws Exception {
		String query = "SELECT COUNT(1) FROM T_MIS_LISTADO_GAMA_RAPID WHERE COD_CENTRO = " + codCentro
				+ " AND FECHA_GEN = trunc(sysdate)";

		if (this.jdbcTemplate.queryForInt(query) > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public List<ArtGamaRapid> obtenerDatosCentro(Long codCentro) throws Exception {

		StringBuffer query = new StringBuffer(
				"SELECT S.COD_CENTRO, S.COD_ART, D.DESCRIP_ART, S.DESC_GAMA, D.GRUPO1, D.GRUPO2, D.GRUPO3, D.GRUPO4, D.GRUPO5,");
		query.append(" (select descripcion from marcas m where m.cod_marca = d.cod_marca) desc_marca,");
		query.append(" D.FORMATO,");
		query.append(" S.UNI_CAJA_SERV,");
		query.append(
				" (SELECT KILOS_UNI FROM DATOS_DIARIO_ART D2 WHERE D2.COD_ART = D.COD_ART AND D2.FECHA_GEN = D.FECHA_GEN) KILOS_UNI,");
		query.append(" (select descripcion from v_agru_comer_ref va where va.grupo1=d.grupo1 and nivel = 'I1') area,");
		query.append(
				" (select descripcion from v_agru_comer_ref va where va.grupo1=d.grupo1 and va.grupo2=d.grupo2 and nivel = 'I2') seccion,");
		query.append(
				" (select descripcion from v_agru_comer_ref va where va.grupo1=d.grupo1 and va.grupo2=d.grupo2 and va.grupo3=d.grupo3 and nivel = 'I3') categoria,");
		query.append(
				" (select descripcion from v_agru_comer_ref va where va.grupo1=d.grupo1 and va.grupo2=d.grupo2 and va.grupo3=d.grupo3 and va.grupo4=d.grupo4 and nivel = 'I4') subcategoria,");
		query.append(
				" (select descripcion from v_agru_comer_ref va where va.grupo1=d.grupo1 and va.grupo2=d.grupo2 and va.grupo3=d.grupo3 and va.grupo4=d.grupo4 and va.grupo5=d.grupo5 and nivel = 'I5') segmento");
		query.append(" FROM V_SURTIDO_TIENDA s, V_DATOS_DIARIO_ART D");
		query.append(" WHERE COD_CENTRO = ?");
		query.append(" and s.marca_maestro_centro = 'S'");
		query.append(" AND S.CATALOGO = 'A'");
		query.append(" AND S.COD_ART = D.COD_ART");
		query.append(" AND tipo_compra_venta IN ('T', 'C')");
		query.append(
				" and exists (select 's' from vm_origen_suministro_rapid v where cod_loc_dest = s.cod_centro and referencia_tienda = s.cod_art)");

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);

		List<ArtGamaRapid> aticulos = null;

		logger.info(query.toString());

		try {
			aticulos = (List<ArtGamaRapid>) this.jdbcTemplate.query(query.toString(), this.rdArtGamaRapidMap,
					params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return aticulos;
	}

	@Override
	public void cargarArticuloCentro(ArtGamaRapid articulo) throws Exception {
		StringBuffer insert = new StringBuffer(
				"INSERT INTO T_MIS_LISTADO_GAMA_RAPID (COD_CENTRO, COD_ART, DESCRIP_ART, GRUPO1,");
		insert.append(" GRUPO2, GRUPO3, GRUPO4, GRUPO5, AREA, SECCION, CATEGORIA, SUBCATEGORIA,");
		insert.append(" SEGMENTO, TP_GAMA, MARCA, UNI_CAJA_SERV, PRECIO_COSTO, PVP, PVP_OFERTA, FORMATO, KILOS_UNI)");
		insert.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		List<Object> params = new ArrayList<Object>();
		params.add(articulo.getCodCentro());
		params.add(articulo.getCodArticulo());
		params.add(articulo.getDescriptionArt());
		params.add(articulo.getGrupo1());
		params.add(articulo.getGrupo2());
		params.add(articulo.getGrupo3());
		params.add(articulo.getGrupo4());
		params.add(articulo.getGrupo5());
		params.add(articulo.getArea());
		params.add(articulo.getSeccion());
		params.add((null!=articulo.getCategoria())?articulo.getCategoria():"NO EXISTE");
		params.add((null!=articulo.getSubcategoria())?articulo.getSubcategoria():"NO EXISTE");
		params.add((null!=articulo.getSegmento())?articulo.getSegmento():"NO EXISTE");
		params.add(articulo.getTpGama());
		params.add(articulo.getMarca());
		params.add(articulo.getUniCajaServ());
		params.add(articulo.getPrecioCosto());
		params.add(articulo.getPvp());
		params.add(articulo.getPvpOferta());
		params.add(articulo.getFormato());
		params.add(articulo.getKgUnidad());

		this.jdbcTemplate.update(insert.toString(), params.toArray());
	}

	@Override
	public void cargarArticulosCentro(final List<ArtGamaRapid> articulos) throws Exception {
		StringBuffer query = new StringBuffer(
				"INSERT INTO T_MIS_LISTADO_GAMA_RAPID (COD_CENTRO, COD_ART, DESCRIP_ART, GRUPO1,");
		query.append(" GRUPO2, GRUPO3, GRUPO4, GRUPO5, AREA, SECCION, CATEGORIA, SUBCATEGORIA,");
		query.append(" SEGMENTO, TP_GAMA, MARCA, UNI_CAJA_SERV, PRECIO_COSTO, PVP, PVP_OFERTA, FORMATO, KILOS_UNI)");
		query.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		if (articulos != null && articulos.size() > 0) {
			jdbcTemplate.batchUpdate(query.toString(), new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ArtGamaRapid articulo = articulos.get(i);

					if (null != articulo.getCodCentro()) {
						ps.setLong(1, articulo.getCodCentro());
					} else {
						ps.setNull(1, Types.NUMERIC);
					}
					if (null != articulo.getCodArticulo()) {
						ps.setLong(2, articulo.getCodArticulo());
					} else {
						ps.setNull(2, Types.NUMERIC);
					}
					ps.setString(3, articulo.getDescriptionArt());
					if (null != articulo.getGrupo1()) {
						ps.setLong(4, articulo.getGrupo1());
					} else {
						ps.setNull(4, Types.NUMERIC);
					}
					if (null != articulo.getGrupo2()) {
						ps.setLong(5, articulo.getGrupo2());
					} else {
						ps.setNull(5, Types.NUMERIC);
					}
					if (null != articulo.getGrupo3()) {
						ps.setLong(6, articulo.getGrupo3());
					} else {
						ps.setNull(6, Types.NUMERIC);
					}
					if (null != articulo.getGrupo4()) {
						ps.setLong(7, articulo.getGrupo4());
					} else {
						ps.setNull(7, Types.NUMERIC);
					}
					if (null != articulo.getGrupo5()) {
						ps.setLong(8, articulo.getGrupo5());
					} else {
						ps.setNull(8, Types.NUMERIC);
					}				
					ps.setString(9, articulo.getArea());
					ps.setString(10, articulo.getSeccion());
					ps.setString(11, (null!=articulo.getCategoria())?articulo.getCategoria():"NO EXISTE");
					ps.setString(12, (null!=articulo.getSubcategoria())?articulo.getSubcategoria():"NO EXISTE");
					ps.setString(13, (null!=articulo.getSegmento())?articulo.getSegmento():"NO EXISTE");
					ps.setString(14, articulo.getTpGama());
					ps.setString(15, articulo.getMarca());
					if (null != articulo.getUniCajaServ()) {
						ps.setDouble(16, articulo.getUniCajaServ());
					} else {
						ps.setNull(16, Types.NUMERIC);
					}
					if (null != articulo.getPrecioCosto()) {
						ps.setDouble(17, articulo.getPrecioCosto());
					} else {
						ps.setNull(17, Types.NUMERIC);
					}
					if (null != articulo.getPvp()) {
						ps.setDouble(18, articulo.getPvp());
					} else {
						ps.setNull(18, Types.NUMERIC);
					}
					if (null != articulo.getPvpOferta()) {
						ps.setDouble(19, articulo.getPvpOferta());
					} else {
						ps.setNull(19, Types.NUMERIC);
					}
					ps.setString(20, articulo.getFormato());
					if (null != articulo.getKgUnidad()) {
						ps.setDouble(21, articulo.getKgUnidad());
					} else {
						ps.setNull(21, Types.NUMERIC);
					}
				}

				@Override
				public int getBatchSize() {
					return articulos.size();
				}
			});
		}
	}

	@Override
	public List<ArtGamaRapid> findAll(ArtGamaRapid artGamaRapid, Pagination pagination) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE FECHA_GEN = trunc(sysdate) ");
		StringBuffer query = new StringBuffer("SELECT COD_CENTRO, COD_ART, DESCRIP_ART, GRUPO1, "
				+ " GRUPO2, GRUPO3, GRUPO4, GRUPO5, AREA, SECCION, CATEGORIA, SUBCATEGORIA, "
				+ " SEGMENTO, TP_GAMA, MARCA, UNI_CAJA_SERV, PRECIO_COSTO, PVP, PVP_OFERTA, FORMATO, KILOS_UNI"
				+ " FROM T_MIS_LISTADO_GAMA_RAPID ");

		if (artGamaRapid != null) {
			if (artGamaRapid.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(artGamaRapid.getCodCentro());
			}
			if (artGamaRapid.getGrupo1() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(artGamaRapid.getGrupo1());
			}
			if (artGamaRapid.getGrupo2() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(artGamaRapid.getGrupo2());
			}
			if (artGamaRapid.getGrupo3() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(artGamaRapid.getGrupo3());
			}
			if (artGamaRapid.getGrupo4() != null) {
				where.append(" AND GRUPO4 = ? ");
				params.add(artGamaRapid.getGrupo4());
			}
			if (artGamaRapid.getGrupo5() != null) {
				where.append(" AND GRUPO5 = ? ");
				params.add(artGamaRapid.getGrupo5());
			}
			if (artGamaRapid.getCodArticulo() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(artGamaRapid.getCodArticulo());
			}
		}

		query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + getMappedField(pagination.getSort(), 0) + " " + pagination.getAscDsc());
				query.append(order);
			}
		}

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
		}

		List<ArtGamaRapid> varAprpvLista = null;

		try {
			varAprpvLista = (List<ArtGamaRapid>) this.jdbcTemplate.query(query.toString(), this.rwArtGamaRapidMap,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return varAprpvLista;
	}

	@Override
	public Long findAllCont(ArtGamaRapid artGamaRapid) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE FECHA_GEN = trunc(sysdate) ");
		StringBuffer query = new StringBuffer("SELECT COUNT(1) FROM T_MIS_LISTADO_GAMA_RAPID ");

		if (artGamaRapid != null) {
			if (artGamaRapid.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(artGamaRapid.getCodCentro());
			}
			if (artGamaRapid.getGrupo1() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(artGamaRapid.getGrupo1());
			}
			if (artGamaRapid.getGrupo2() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(artGamaRapid.getGrupo2());
			}
			if (artGamaRapid.getGrupo3() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(artGamaRapid.getGrupo3());
			}
			if (artGamaRapid.getGrupo4() != null) {
				where.append(" AND GRUPO4 = ? ");
				params.add(artGamaRapid.getGrupo4());
			}
			if (artGamaRapid.getGrupo5() != null) {
				where.append(" AND GRUPO5 = ? ");
				params.add(artGamaRapid.getGrupo5());
			}
			if (artGamaRapid.getCodArticulo() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(artGamaRapid.getCodArticulo());
			}
		}

		query.append(where);

		Long cont = null;

		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	private String getMappedField(String fieldName, int target) {
		if (fieldName.toUpperCase().equals("CODARTICULO")) {
			return "COD_ART";
		} else if (fieldName.toUpperCase().equals("DESCRIPTIONART")) {
			return "DESCRIP_ART";
		} else if (fieldName.toUpperCase().equals("TPGAMA")) {
			return "TP_GAMA";
		} else if (fieldName.toUpperCase().equals("MARCA")) {
			return "MARCA";
		} else if (fieldName.toUpperCase().equals("AREA")) {
			return "GRUPO1 || '-' || AREA";
		} else if (fieldName.toUpperCase().equals("SECCION")) {
			return "GRUPO2 || '-' || SECCION";
		} else if (fieldName.toUpperCase().equals("CATEGORIA")) {
			return "GRUPO3 || '-' || CATEGORIA";
		} else if (fieldName.toUpperCase().equals("SUBCATEGORIA")) {
			return "GRUPO4 || '-' || SUBCATEGORIA";
		} else if (fieldName.toUpperCase().equals("SEGMENTO")) {
			return "GRUPO5 || '-' || SEGMENTO";
		} else if (fieldName.toUpperCase().equals("UNICAJASERV")) {
			return "nvl(UNI_CAJA_SERV,0)";
		} else if (fieldName.toUpperCase().equals("PRECIOCOSTO")) {
			return "nvl(PRECIO_COSTO, 0)";
		} else if (fieldName.toUpperCase().equals("PVP")) {
			return "nvl(PVP, 0)";
		} else if (fieldName.toUpperCase().equals("PVPOFERTA")) {
			return "nvl(PVP_OFERTA, 0)";
		} else if (fieldName.toUpperCase().equals("FORMATO")) {
			return "FORMATO";
		} else if (fieldName.toUpperCase().equals("KGUNIDAD")) {
			return "nvl(KILOS_UNI, 0)";
		} else {
			return fieldName;
		}
	}

	@Override
	public List<GenericExcelVO> findAllExcel(ArtGamaRapid artGamaRapid, String[] columnModel) throws Exception {
		String nombreCampoColmodel = "";
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE FECHA_GEN = trunc(sysdate) ");

		String fields = "null";
		List<String> listColumns = Arrays.asList(columnModel);
		for (int i = 0; i < listColumns.size(); i++) {
			nombreCampoColmodel = this.getMappedField(listColumns.get(i), 1).toUpperCase();
			fields = fields + ", " + nombreCampoColmodel;
		}

		StringBuffer query = new StringBuffer("SELECT ");
		query.append(fields);
		query.append(" FROM T_MIS_LISTADO_GAMA_RAPID ");

		if (artGamaRapid != null) {
			if (artGamaRapid.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(artGamaRapid.getCodCentro());
			}
			if (artGamaRapid.getGrupo1() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(artGamaRapid.getGrupo1());
			}
			if (artGamaRapid.getGrupo2() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(artGamaRapid.getGrupo2());
			}
			if (artGamaRapid.getGrupo3() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(artGamaRapid.getGrupo3());
			}
			if (artGamaRapid.getGrupo4() != null) {
				where.append(" AND GRUPO4 = ? ");
				params.add(artGamaRapid.getGrupo4());
			}
			if (artGamaRapid.getGrupo5() != null) {
				where.append(" AND GRUPO5 = ? ");
				params.add(artGamaRapid.getGrupo5());
			}
			if (artGamaRapid.getCodArticulo() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(artGamaRapid.getCodArticulo());
			}
		}

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by grupo1, grupo2, grupo3, grupo4, grupo5, cod_art");
		query.append(order);

		List<GenericExcelVO> lista = null;

		try {
			lista = this.jdbcTemplate.query(query.toString(), this.rwExcelArtGamaRapidMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return lista;
	}

	private RowMapper<ArtGamaRapid> rwArtGamaRapidMap = new RowMapper<ArtGamaRapid>() {
		public ArtGamaRapid mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ArtGamaRapid centroAlta = new ArtGamaRapid(resultSet.getLong("COD_CENTRO"), null,
					resultSet.getLong("COD_ART"), resultSet.getString("DESCRIP_ART").trim(),
					resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"), resultSet.getLong("GRUPO3"),
					resultSet.getLong("GRUPO4"), resultSet.getLong("GRUPO5"), resultSet.getString("AREA"),
					resultSet.getString("SECCION"), resultSet.getString("CATEGORIA"),
					resultSet.getString("SUBCATEGORIA"), resultSet.getString("SEGMENTO"), resultSet.getString("MARCA"),
					resultSet.getString("TP_GAMA"), resultSet.getDouble("UNI_CAJA_SERV"),
					resultSet.getDouble("PRECIO_COSTO"), resultSet.getDouble("PVP"), resultSet.getDouble("PVP_OFERTA"),
					resultSet.getDouble("KILOS_UNI"), resultSet.getString("FORMATO"));
			return centroAlta;
		}
	};

	private RowMapper<ArtGamaRapid> rdArtGamaRapidMap = new RowMapper<ArtGamaRapid>() {
		public ArtGamaRapid mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ArtGamaRapid centroAlta = new ArtGamaRapid(resultSet.getLong("COD_CENTRO"), null,
					resultSet.getLong("COD_ART"), resultSet.getString("DESCRIP_ART").trim(),
					resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"), resultSet.getLong("GRUPO3"),
					resultSet.getLong("GRUPO4"), resultSet.getLong("GRUPO5"), resultSet.getString("AREA"),
					resultSet.getString("SECCION"), resultSet.getString("CATEGORIA"),
					resultSet.getString("SUBCATEGORIA"), resultSet.getString("SEGMENTO"),
					resultSet.getString("DESC_MARCA"), resultSet.getString("DESC_GAMA"),
					resultSet.getDouble("UNI_CAJA_SERV"), null, null, null, resultSet.getDouble("KILOS_UNI"),
					resultSet.getString("FORMATO"));
			return centroAlta;
		}
	};

	private RowMapper<GenericExcelVO> rwExcelArtGamaRapidMap = new RowMapper<GenericExcelVO>() {
		public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 2),
					Utilidades.obtenerValorExcel(resultSet, 3), Utilidades.obtenerValorExcel(resultSet, 4),
					Utilidades.obtenerValorExcel(resultSet, 5), Utilidades.obtenerValorExcel(resultSet, 6),
					Utilidades.obtenerValorExcel(resultSet, 7), Utilidades.obtenerValorExcel(resultSet, 8),
					Utilidades.obtenerValorExcel(resultSet, 9), Utilidades.obtenerValorExcel(resultSet, 10),
					Utilidades.obtenerValorExcel(resultSet, 11), Utilidades.obtenerValorExcel(resultSet, 12),
					Utilidades.obtenerValorExcel(resultSet, 13), Utilidades.obtenerValorExcel(resultSet, 14),
					Utilidades.obtenerValorExcel(resultSet, 15), Utilidades.obtenerValorExcel(resultSet, 16),
					Utilidades.obtenerValorExcel(resultSet, 17), Utilidades.obtenerValorExcel(resultSet, 18),
					Utilidades.obtenerValorExcel(resultSet, 19), Utilidades.obtenerValorExcel(resultSet, 20),
					Utilidades.obtenerValorExcel(resultSet, 21), Utilidades.obtenerValorExcel(resultSet, 22),
					Utilidades.obtenerValorExcel(resultSet, 23), Utilidades.obtenerValorExcel(resultSet, 24),
					Utilidades.obtenerValorExcel(resultSet, 25), Utilidades.obtenerValorExcel(resultSet, 26),
					Utilidades.obtenerValorExcel(resultSet, 27), Utilidades.obtenerValorExcel(resultSet, 28),
					Utilidades.obtenerValorExcel(resultSet, 29), Utilidades.obtenerValorExcel(resultSet, 30),
					Utilidades.obtenerValorExcel(resultSet, 31), Utilidades.obtenerValorExcel(resultSet, 32),
					Utilidades.obtenerValorExcel(resultSet, 33), Utilidades.obtenerValorExcel(resultSet, 34),
					Utilidades.obtenerValorExcel(resultSet, 35), Utilidades.obtenerValorExcel(resultSet, 36),
					Utilidades.obtenerValorExcel(resultSet, 37), Utilidades.obtenerValorExcel(resultSet, 38),
					Utilidades.obtenerValorExcel(resultSet, 39), Utilidades.obtenerValorExcel(resultSet, 40),
					Utilidades.obtenerValorExcel(resultSet, 41), Utilidades.obtenerValorExcel(resultSet, 42),
					Utilidades.obtenerValorExcel(resultSet, 43), Utilidades.obtenerValorExcel(resultSet, 44),
					Utilidades.obtenerValorExcel(resultSet, 45), Utilidades.obtenerValorExcel(resultSet, 46),
					Utilidades.obtenerValorExcel(resultSet, 47), Utilidades.obtenerValorExcel(resultSet, 48));
		}
	};
}
