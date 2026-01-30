package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.AvisosSiecDao;
import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.AvisosSiec;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class AvisosSiecDaoImpl implements AvisosSiecDao{

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(AvisosSiecDaoImpl.class);

	private RowMapper<AvisosSiec> rwAvisosSiecMap = new RowMapper<AvisosSiec>() {
		public AvisosSiec mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			AvisosSiec avisosSiec= new AvisosSiec();
			avisosSiec.setCodAviso(resultSet.getString("ROWID"));
			avisosSiec.setFechaIni(resultSet.getDate("FECHA_INICIO"));
			avisosSiec.setHoraIni(resultSet.getString("HORA_INICIO"));
			avisosSiec.setFechaFin(resultSet.getDate("FECHA_FIN"));
			avisosSiec.setHoraFin(resultSet.getString("HORA_FIN"));
			avisosSiec.setMensajePc(resultSet.getString("MENSAJE_PC"));
			avisosSiec.setMensajePda(resultSet.getString("MENSAJE_PDA"));
			avisosSiec.setFlgHiper(resultSet.getString("HIPER"));
			avisosSiec.setFlgSuper(resultSet.getString("SUPER"));
			avisosSiec.setFlgFranquicia(resultSet.getString("FRANQUICIA"));
			avisosSiec.setFlgEroski(resultSet.getString("EROSKI"));
			avisosSiec.setFlgCpb(resultSet.getString("CPB"));
			avisosSiec.setFlgMercat(resultSet.getString("MERCAT"));
			avisosSiec.setFlgVegalsa(resultSet.getString("VEGALSA"));
			return avisosSiec;
		}
	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<AvisosSiec> findAll(Pagination pagination) throws Exception {

		StringBuffer query = new StringBuffer("SELECT ROWID,S.FECHA_INICIO,TO_CHAR(S.HORA_INICIO, 'HH24:MI') HORA_INICIO,S.FECHA_FIN,TO_CHAR(S.HORA_FIN, 'HH24:MI') HORA_FIN,"
				+ "	S.MENSAJE_PC,S.MENSAJE_PDA,S.HIPER,S.SUPER,S.FRANQUICIA,S.EROSKI,S.CPB,S.MERCAT,S.VEGALSA "
				+ "FROM T_MIS_MENSAJES_SIEC S WHERE S.FECHA_FIN IS NULL OR S.FECHA_FIN >=TRUNC(SYSDATE)");

		List<Object> params = new ArrayList<Object>();
		//Se añade un order by según la columna clicada para la ordenación. Si no hay ordenación por columna (como cuando cargas la 1 vez el grid)
		//se ordena por 
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null && !(pagination.getSort().equals(""))) {
				order.append(" order by " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				query.append(order);
			} else {
				order.append(" order by S.FECHA_INICIO, HORA_INICIO, S.FECHA_FIN, HORA_FIN ");
				query.append(order);
			}
		}else{
			order.append(" order by S.FECHA_INICIO, HORA_INICIO, S.FECHA_FIN, HORA_FIN");
			query.append(order);
		}
		List<AvisosSiec> lista = null;
		try {
			lista =  this.jdbcTemplate.query(query.toString(),this.rwAvisosSiecMap, params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return lista;
	}

	//Función que sirve para realizar ordenaciones por columna, de esta forma, llega el código de columna con nombre
	//jqgrid y lo transforma a código SQL
	private String getMappedField (String fieldName) {
		if (fieldName.equals("fechaIni")){
			return "S.FECHA_INICIO";
		}else if(fieldName.equals("horaIni")){
			return "HORA_INICIO";
		}else if(fieldName.equals("fechaFin")){
			return "S.FECHA_FIN";
		}else if(fieldName.equals("horaFin")){
			return "S.HORA_FIN";
		}else if(fieldName.equals("mensajePc")){
			return "S.MENSAJE_PC";
		}else if(fieldName.equals("mensajePda")){
			return "S.MENSAJE_PDA";
		}else if(fieldName.equals("flgEroski")){
			return "S.EROSKI";
		}else if(fieldName.equals("flgCpb")){
			return "S.CPB";
		}else if(fieldName.equals("flgVegalsa")){
			return "S.VEGALSA";
		}else if(fieldName.equals("flgMercat")){
			return "S.MERCAT";
		}else if(fieldName.equals("flgHiper")){
			return "S.HIPER";
		}else if(fieldName.equals("flgSuper")){
			return "S.SUPER";
		}else if(fieldName.equals("flgFranquicia")){
			return "S.FRANQUICIA";
		}
		return "S.FECHA_INICIO, HORA_INICIO, S.FECHA_FIN, HORA_FIN";
	}
	
	@Override
	public int deleteRows(AvisosSiec avisosSiec) throws Exception{
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("DELETE FROM T_MIS_MENSAJES_SIEC WHERE ROWID = ? ");
		try {
			 List<String> idsBorrar=avisosSiec.getLstBorrar();
			 for(int i=0;i<idsBorrar.size();i++){
				 params = new ArrayList<Object>();
				 params.add(idsBorrar.get(i));
				 this.jdbcTemplate.update(query.toString(), params.toArray());
			 }
			return 0;
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return 1;
		}
	}
	
	@Override
	public List<Aviso> obtenerAvisosSiec(Long centro) throws Exception{
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT COD_CENTRO,MENSAJE FROM V_MIS_MENSAJES_SIEC_CENTROS WHERE COD_CENTRO= ? "
				+ "AND SYSDATE BETWEEN FECHA_INICIO AND FECHA_FIN");
		
		params.add(centro);
		
		List<Aviso> lista = null;
		try {
			lista =  this.jdbcTemplate.query(query.toString(),this.rwAvisosSiecWelcomeMap, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}
	
	private RowMapper<Aviso> rwAvisosSiecWelcomeMap = new RowMapper<Aviso>() {
		public Aviso mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Aviso avisosSiec= new Aviso();
			avisosSiec.setCodCentro(resultSet.getLong("COD_CENTRO"));
			avisosSiec.setMensaje(resultSet.getString("MENSAJE"));
			return avisosSiec;
		}
	};
	
	@Override
	public AvisosSiec findAvisoSiec(String codAviso) throws Exception{
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT ROWID,S.FECHA_INICIO,TO_CHAR(S.HORA_INICIO, 'HH24:MI') HORA_INICIO,S.FECHA_FIN,TO_CHAR(S.HORA_FIN, 'HH24:MI') HORA_FIN,"
				+ "	S.MENSAJE_PC,S.MENSAJE_PDA,S.HIPER,S.SUPER,S.FRANQUICIA,S.EROSKI,S.CPB,S.MERCAT,S.VEGALSA "
				+ "FROM T_MIS_MENSAJES_SIEC S WHERE ROWID = ?");
		
		params.add(codAviso);
		AvisosSiec avisosSiec = null;
		try {
			avisosSiec =  this.jdbcTemplate.queryForObject(query.toString(),this.rwAvisoSiecMap, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return avisosSiec;
	}
	
	private RowMapper<AvisosSiec> rwAvisoSiecMap = new RowMapper<AvisosSiec>() {
		public AvisosSiec mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			AvisosSiec avisosSiec= new AvisosSiec();
			avisosSiec.setCodAviso(resultSet.getString("ROWID"));
			avisosSiec.setFechaIni(resultSet.getDate("FECHA_INICIO"));
			avisosSiec.setHoraIni(resultSet.getString("HORA_INICIO"));
			avisosSiec.setFechaFin(resultSet.getDate("FECHA_FIN"));
			avisosSiec.setHoraFin(resultSet.getString("HORA_FIN"));
			avisosSiec.setMensajePc(resultSet.getString("MENSAJE_PC"));
			avisosSiec.setMensajePda(resultSet.getString("MENSAJE_PDA"));
			avisosSiec.setFlgHiper(resultSet.getString("HIPER"));
			avisosSiec.setFlgSuper(resultSet.getString("SUPER"));
			avisosSiec.setFlgFranquicia(resultSet.getString("FRANQUICIA"));
			avisosSiec.setFlgEroski(resultSet.getString("EROSKI"));
			avisosSiec.setFlgCpb(resultSet.getString("CPB"));
			avisosSiec.setFlgMercat(resultSet.getString("MERCAT"));
			avisosSiec.setFlgVegalsa(resultSet.getString("VEGALSA"));
			return avisosSiec;
		}
	};
	
	@Override
	public String updateLinea(AvisosSiec avisosSiec, String user){
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		StringBuffer where =  new StringBuffer(" WHERE ROWID = ? ");
		StringBuffer query = new StringBuffer(" UPDATE T_MIS_MENSAJES_SIEC SET ");

		try {
			//Fechas
			query.append(" FECHA_INICIO = ? ");
			params.add(avisosSiec.getFechaIni());
			query.append(" , HORA_INICIO = ? ");
			Date horaInicio=new Date();
			String[] horasMinutosIni = avisosSiec.getHoraIni().split(":");
			horaInicio.setHours(Integer.parseInt(horasMinutosIni[0]));
			horaInicio.setMinutes(Integer.parseInt(horasMinutosIni[1]));
			horaInicio.setSeconds(0);
			params.add(horaInicio);
			query.append(" , FECHA_FIN = ? ");
			params.add(avisosSiec.getFechaFin());
			query.append(" , HORA_FIN = ? ");
			Date horaFin=new Date();
			String[] horasMinutosFin = avisosSiec.getHoraFin().split(":");
			if(horasMinutosFin.length==2){
				horaFin.setHours(Integer.parseInt(horasMinutosFin[0]));
				horaFin.setMinutes(Integer.parseInt(horasMinutosFin[1]));
				horaFin.setSeconds(0);
				params.add(horaFin);
			}else{
				params.add(null);
			}
			
			//params.add(avisosSiec.getHoraFin());
			//Mensajes
			query.append(" , MENSAJE_PC = ? ");
			params.add(avisosSiec.getMensajePc());
			query.append(" , MENSAJE_PDA = ? ");
			params.add(avisosSiec.getMensajePda());
			//Negocios
			query.append(" , HIPER = ? ");
			params.add(avisosSiec.getFlgHiper());
			query.append(" , SUPER = ? ");
			params.add(avisosSiec.getFlgSuper());
			query.append(" , FRANQUICIA = ? ");
			params.add(avisosSiec.getFlgFranquicia());
			//Sociedades afectadas
			query.append(" , EROSKI = ? ");
			params.add(avisosSiec.getFlgEroski());
			query.append(" , CPB = ? ");
			params.add(avisosSiec.getFlgCpb());
			query.append(" , MERCAT = ? ");
			params.add(avisosSiec.getFlgMercat());
			query.append(" , VEGALSA = ? ");
			params.add(avisosSiec.getFlgVegalsa());
			//LAST_UPDATE_DATE y LAST_UPDATE_BY
			query.append(" , LAST_UPDATE_DATE = SYSDATE ");
			query.append(" , LAST_UPDATED_BY = ? ");
			params.add(user);
			//ROWID
			query.append(where);
			params.add(avisosSiec.getCodAviso());
			
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return "0";
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return "1";
		}
	}

	
	@Override
	public String insertLinea(AvisosSiec avisosSiec, String user) {
		// TODO Auto-generated method stub
		StringBuffer insert = new StringBuffer(
				"INSERT INTO T_MIS_MENSAJES_SIEC (FECHA_INICIO, HORA_INICIO, FECHA_FIN, HORA_FIN, "
				+ "MENSAJE_PC, MENSAJE_PDA, HIPER, SUPER, FRANQUICIA, EROSKI, CPB, MERCAT, VEGALSA,"
				+ "CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY) ");
		insert.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,SYSDATE,?) ");

		List<Object> params = new ArrayList<Object>();
		try{
			
			params.add(avisosSiec.getFechaIni());
			Date horaInicio=new Date();
			String[] horasMinutosIni = avisosSiec.getHoraIni().split(":");
			horaInicio.setHours(Integer.parseInt(horasMinutosIni[0]));
			horaInicio.setMinutes(Integer.parseInt(horasMinutosIni[1]));
			horaInicio.setSeconds(0);
			params.add(horaInicio);
			params.add(avisosSiec.getFechaFin());
			if(avisosSiec.getHoraFin().equals("")){
				params.add(null);
			}else{
				Date horaFin=new Date();
				String[] horasMinutosFin = avisosSiec.getHoraFin().split(":");
				horaFin.setHours(Integer.parseInt(horasMinutosFin[0]));
				horaFin.setMinutes(Integer.parseInt(horasMinutosFin[1]));
				horaFin.setSeconds(0);
				params.add(horaFin);
			}
			
			params.add(avisosSiec.getMensajePc());
			params.add(avisosSiec.getMensajePda());
			params.add(avisosSiec.getFlgHiper());
			params.add(avisosSiec.getFlgSuper());
			params.add(avisosSiec.getFlgFranquicia());
			params.add(avisosSiec.getFlgEroski());
			params.add(avisosSiec.getFlgCpb());
			params.add(avisosSiec.getFlgMercat());
			params.add(avisosSiec.getFlgVegalsa());
			params.add(user);
			params.add(user);
			
			this.jdbcTemplate.update(insert.toString(), params.toArray());
			return "0";
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(insert.toString(), params ,e);
			return "1";
		}		
	}
}
