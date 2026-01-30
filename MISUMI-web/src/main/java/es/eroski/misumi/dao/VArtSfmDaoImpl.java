package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VArtSfmDao;
import es.eroski.misumi.model.EstructuraCom;
import es.eroski.misumi.model.EstructuraDat;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class VArtSfmDaoImpl implements VArtSfmDao{
	 private JdbcTemplate jdbcTemplate;
	 private static final int OBTENER_SFMFACCAP_OUT_INDEX = 12;
	 private static final String APR_R_SFMCAP_ESTR_REG_TYPENAME = "APR_R_SFMCAP_ESTR_REG";

	 private static Logger logger = Logger.getLogger(VArtSfmDaoImpl.class);

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	 } 
	    
		 
	/**
	 * Llama a un procedimiento que obtiene Sfm, Fac y Cap
	 *  <li>   PROCEDURE p_apr_obtener_sfm_fac_cap ( p_cod_loc        IN  localizaciones.cod_loc%TYPE
                                        , p_cod_articulo   IN  articulos.cod_articulo%TYPE
                                        , p_nivel          IN  niveles_estr_art.nivel%TYPE
                                        , p_cod_n1         IN  niveles_estr_art.cod_n1%TYPE
                                        , p_cod_n2         IN  niveles_estr_art.cod_n2%TYPE
                                        , p_cod_n3         IN  niveles_estr_art.cod_n3%TYPE
                                        , p_cod_n4         IN  niveles_estr_art.cod_n4%TYPE
                                        , p_cod_n5         IN  niveles_estr_art.cod_n5%TYPE
                                        , p_tabla          OUT apr_r_sfmcap_estr_reg
                                        );  

    <li>TYPE      apr_r_sfmcap_ESTR_reg AS OBJECT (
    datos              apr_t_r_sfmcap_estr_dat,
    estado             NUMBER(5),
    desc_estado        VARCHAR2(32000)
    )
    <li>TYPE      apr_t_r_sfmcap_ESTR_dat IS TABLE OF apr_r_sfmcap_ESTR_dat
    <li>TYPE      apr_r_sfmcap_ESTR_dat AS OBJECT (
    son los campos de apr_r_sfmcap_dat mas tipo_estructura	     VARCHAR2(10)	
	 */
	@Override
	public SfmCapacidadFacing obtenerSfmFacCap(
			final Long codLoc, 
			final Long codArticulo,
			final Number nivel,
			final String codN1,
			final String codN2,
			final String codN3,
			final String codN4,
			final String codN5,
			final String pedir,
			final String lote,
			final Long codArticuloLote
			){
		SfmCapacidadFacing salida = null;

		// completamos el nivel si no viene informado
		final Integer pNivel;
		if(nivel!=null){
			pNivel = nivel.intValue();
		} else if(StringUtils.isNotEmpty(codN5)){
			pNivel = 5;
		}else if(StringUtils.isNotEmpty(codN4)){
			pNivel = 4;
		}else if(StringUtils.isNotEmpty(codN3)){
			pNivel = 3;
		}else if(StringUtils.isNotEmpty(codN2)){
			pNivel = 2;
		}else if(StringUtils.isNotEmpty(codN1)){
			pNivel = 1;
		}else {
			pNivel = null;
		}

		//Control de problemas con valores "null" que provienen de pantalla
		final String paramPedir = (((null!=pedir) && (!pedir.equals("null"))) ? pedir : null);
		final String paramLote = (((null!=lote) && (!lote.equals("null"))) ? lote : null);

		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {
					cs = con.prepareCall("{call PK_APR_MISUMI_IMC.P_APR_OBTENER_SFM_FAC_CAP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");

					int paramIndex = 1;
					cs.setObject(paramIndex++, codLoc, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, codArticulo, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, pNivel, OracleTypes.INTEGER);
					cs.setString(paramIndex++, codN1);
					cs.setString(paramIndex++, codN2);
					cs.setString(paramIndex++, codN3);
					cs.setString(paramIndex++, codN4);
					cs.setString(paramIndex++, codN5);
					cs.setString(paramIndex++, paramPedir);
					cs.setString(paramIndex++, paramLote);
					cs.setObject(paramIndex++, codArticuloLote, OracleTypes.INTEGER);

					cs.registerOutParameter(paramIndex, OracleTypes.STRUCT, APR_R_SFMCAP_ESTR_REG_TYPENAME);

				} catch (SQLException e) {
					logger.error("Fallo preparando consulta de Sfm, Fac y Cap", e);             
				}
				return cs;
			}
		};
		CallableStatementCallback<SfmCapacidadFacing> csCallback = new CallableStatementCallback<SfmCapacidadFacing>() {

			public SfmCapacidadFacing doInCallableStatement(CallableStatement cs) {
				SfmCapacidadFacing ret = null;
				try {
					cs.execute();
					Struct struct = (Struct)cs.getObject(OBTENER_SFMFACCAP_OUT_INDEX);
					ret = obtenerEstructuraVartSfm(struct);
				} catch (SQLException e) {
					logger.error("Fallo ejecutando consulta de Sfm, Fac y Cap", e);
				}
				return ret;
			}
		};

		try {
			salida = this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return salida;
	}



	/**
	 * Mapeo del resutado
	 * <p>Descripcion del modelo a mapear como resultante:
	 * <code>
	 * <li>  p_tabla     apr_r_sfmcap_ESTR_reg
	 * <li>apr_r_sfmcap_ESTR_reg AS OBJECT (
	    <p>    datos              apr_t_r_sfmcap_ESTR_dat,
	    <p>    estado             NUMBER(5),
	    <p>    desc_estado        VARCHAR2(32000)
		<p>)
		<li> apr_t_r_sfmcap_ESTR_dat IS TABLE OF apr_r_sfmcap_ESTR_dat
		<li> apr_r_sfmcap_ESTR_dat AS OBJECT (... 
		igual que apr_r_sfmcap_dat con un campo nuevo: tipo_estructura VARCHAR2)
	 * </code>
	 * @param estructuraResultado = (STRUCT)cs.getObject(idParametroResultado)
	 */
	private SfmCapacidadFacing obtenerEstructuraVartSfm(Struct estructuraResultado){
		ResultSet rs;
		SfmCapacidadFacing sfmCapacidad = new SfmCapacidadFacing();
		List<VArtSfm> listaVArtSfm = new ArrayList<VArtSfm>();

		if(estructuraResultado!=null) {
			try{
				//Obtenci�n del par�metro de salida
				Object[] attributes = estructuraResultado.getAttributes();

				//Obtenci�n de los datos de la estructura
				BigDecimal estado = (BigDecimal)attributes[1];
				String descEstado = (String)attributes[2];

				//Control de error en la obtencion de datos
				if (estado.intValue() == 0){ //El proceso se ha ejecutado correctamente
					//Obtenci�n de los datos de salida
					Array listaDatos = (Array)attributes[0];
					if (listaDatos!=null){
						rs = listaDatos.getResultSet();
						int rowNum = 0;
						//Recorrido del listado de datos
						while (rs.next()) {
							Struct estructuraDatos = (Struct) rs.getObject(2);
							VArtSfm vartSfm = this.mapRow(estructuraDatos, rowNum);
							listaVArtSfm.add(vartSfm);
							rowNum++;
						}
					}

					sfmCapacidad.setDatos(listaVArtSfm);
				}

				sfmCapacidad.setEstado(estado.longValue());
				sfmCapacidad.setDescEstado(descEstado);

			} catch (Exception e) {
				logger.error("Error de mapeo estructura sfm", e);
			}
		}

		return sfmCapacidad;
	}

	/**
	 * Mapeo de estructura
	 * <p>Descripcion del modelo a mapear como resultante:
	 * <code>
		<li> apr_r_sfmcap_ESTR_dat AS OBJECT (... 
		igual que apr_r_sfmcap_dat con un campo nuevo: tipo_estructura VARCHAR2)
	 * </code>
	 * @param estructuraDatos
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	private VArtSfm mapRow(Struct estructuraDatos, int rowNum) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		//Obtenci�n de datos de la estructura de base de datos
		BigDecimal codLoc_BD = ((BigDecimal)objectInfo[0]);
		BigDecimal codArticulo_BD = (BigDecimal)objectInfo[1];
		BigDecimal nivel_BD = null; //No viene en la estructura de BD
		String denomInforme_BD = (String)objectInfo[2];
		String codN1_BD = (String)objectInfo[3];
		String descCodN1_BD = (String)objectInfo[4];
		String codN2_BD = (String)objectInfo[5];
		String descCodN2_BD = (String)objectInfo[6];
		String codN3_BD = (String)objectInfo[7];
		String descCodN3_BD = (String)objectInfo[8];
		String codN4_BD = (String)objectInfo[9];
		String descCodN4_BD = (String)objectInfo[10];
		String codN5_BD = (String)objectInfo[11];
		String descCodN5_BD = (String)objectInfo[12];
		String marca_BD = (String)objectInfo[13];
		BigDecimal lmin_BD = (BigDecimal)objectInfo[14];
		BigDecimal lsf_BD = (BigDecimal)objectInfo[15];
		BigDecimal sfm_BD = (BigDecimal)objectInfo[16];
		BigDecimal coberturaSfm_BD = (BigDecimal)objectInfo[17];
		BigDecimal ventaMedia_BD = (BigDecimal)objectInfo[18];
		BigDecimal ventaAnticipada_BD = (BigDecimal)objectInfo[19];
		BigDecimal uc_BD = (BigDecimal)objectInfo[20];
		BigDecimal stock_BD = (BigDecimal)objectInfo[21];
		BigDecimal diasStock_BD = (BigDecimal)objectInfo[22];
		BigDecimal capacidad_BD = (BigDecimal)objectInfo[23];
		BigDecimal facingCentro_BD = (BigDecimal)objectInfo[24];
		BigDecimal facingPrevio_BD = (BigDecimal)objectInfo[25];
		String flgEstrategica_BD = (String)objectInfo[27];
		String tipoGama_BD = (String)objectInfo[28];
		String tipoAprov_BD = (String)objectInfo[29];
		String pedir_BD = (String)objectInfo[30];


		Timestamp fechaSfm_BD = (Timestamp)objectInfo[31];
		BigDecimal vidaUtil_BD = (BigDecimal)objectInfo[34];
		BigDecimal flgFecsfm_BD = (BigDecimal)objectInfo[36];
		BigDecimal codError_BD = (BigDecimal)objectInfo[37];
		String descError_BD = (String)objectInfo[38];

		BigDecimal cc_BD = (BigDecimal)objectInfo[39];
		String tipoExpositor_BD = (String)objectInfo[40];

		//Campos para textil
		String temporada_BD = (String)objectInfo[48]; //DESC_TEMPORADA_ABR
		String anioColeccion_BD = (String)objectInfo[49]; //ANIO_COLECCION
		String talla_BD = (String)objectInfo[43]; //TALLA
		String color_BD = (String)objectInfo[44]; //COLOR
		String lote_BD = (String)objectInfo[45]; //LOTE
		String modeloProveedor_BD = (String)objectInfo[42]; //REF_PROVEEDOR
		String tempColNumOrden_BD = (String)objectInfo[52]; //CONVERT_ART
		String pedible_BD = (String)objectInfo[53]; //PEDIBLE

		String porcionConsumidor_BD = (String)objectInfo[54]; //PORCION_CONSUMIDOR
		BigDecimal nivelLote_BD = ((BigDecimal)objectInfo[55]); //NIVEL_LOTE

		String flgMuchoPoco_BD = (String)objectInfo[56]; //FLG_MUCHO_POCO
		String flgFoto_BD = (String)objectInfo[57]; //FLG_FOTO
		String flgNueva_BD = (String)objectInfo[58]; //FLG_NUEVA
		String flgSfmFijo_BD = (String)objectInfo[59]; //FLG_SFM_FIJO
		BigDecimal ccEstr_BD = ((BigDecimal)objectInfo[60]); //CC_ESTR
		String flgNsr_BD = (String)objectInfo[61]; //FLG_NSR
		String flgUfp_BD = (String)objectInfo[62]; //FLG_UFP
		String flgSoloImagen_BD = (String)objectInfo[63]; //FLG_SOLO_IMAGEN
		BigDecimal multiplicadorFacing_BD = (BigDecimal)objectInfo[64]; //MULTIPLICADOR_FACING
		BigDecimal imagenComercialMin_BD = (BigDecimal)objectInfo[65]; //IMAGEN_COMERCIAL_MIN
		String tipoEstructura = (String) objectInfo[66];

		//Transformaci�n de datos para estructura de VartSfm
		Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		Long nivel = ((nivel_BD != null && !("".equals(nivel_BD.toString())))?new Long(nivel_BD.toString()):null);
		String denomInforme = denomInforme_BD;
		String codN1 = (codN1_BD != null && !"".equals(codN1_BD))? Integer.parseInt(codN1_BD)+"" : "";
		String descCodN1 = descCodN1_BD;
		String codN2 = (codN2_BD != null && !"".equals(codN2_BD))? Integer.parseInt(codN2_BD)+"" : "";
		String descCodN2 = descCodN2_BD;
		String codN3 = (codN3_BD != null && !"".equals(codN3_BD))? Integer.parseInt(codN3_BD)+"" : "";
		String descCodN3 = descCodN3_BD;
		String codN4 = (codN4_BD != null && !"".equals(codN4_BD))? Integer.parseInt(codN4_BD)+"" : "";
		String descCodN4 = descCodN4_BD;
		String codN5 = (codN5_BD != null && !"".equals(codN5_BD))? Integer.parseInt(codN5_BD)+"" : "";
		String descCodN5 = descCodN5_BD;
		String marca = marca_BD;
		Double lmin = ((lmin_BD != null && !("".equals(lmin_BD.toString())))?new Double(lmin_BD.toString()):null);
		Double lsf = ((lsf_BD != null && !("".equals(lsf_BD.toString())))?new Double(lsf_BD.toString()):null);
		Double sfm = ((sfm_BD != null && !("".equals(sfm_BD.toString())))?new Double(sfm_BD.toString()):null);
		Double coberturaSfm = ((coberturaSfm_BD != null && !("".equals(coberturaSfm_BD.toString())))?new Double(coberturaSfm_BD.toString()):null);
		Double ventaMedia = ((ventaMedia_BD != null && !("".equals(ventaMedia_BD.toString())))?new Double(ventaMedia_BD.toString()):null);
		Double ventaAnticipada = ((ventaAnticipada_BD != null && !("".equals(ventaAnticipada_BD.toString())))?new Double(ventaAnticipada_BD.toString()):null);
		Double uc = ((uc_BD != null && !("".equals(uc_BD.toString())))?new Double(uc_BD.toString()):null);
		Double stock = ((stock_BD != null && !("".equals(stock_BD.toString())))?new Double(stock_BD.toString()):null);
		Double diasStock = ((diasStock_BD != null && !("".equals(diasStock_BD.toString())))?new Double(diasStock_BD.toString()):null);
		Double capacidad = ((capacidad_BD != null && !("".equals(capacidad_BD.toString())))?new Double(capacidad_BD.toString()):null);
		Long facingCentro = ((facingCentro_BD != null && !("".equals(facingCentro_BD.toString())))?new Long(facingCentro_BD.toString()):null);
		Long facingPrevio = ((facingPrevio_BD != null && !("".equals(facingPrevio_BD.toString())))?new Long(facingPrevio_BD.toString()):null);
		String flgEstrategica = flgEstrategica_BD;
		String tipoGama = tipoGama_BD;
		String tipoAprov = tipoAprov_BD;
		String pedir = pedir_BD;
		Date fechaSfm = ((fechaSfm_BD != null )?new Date(fechaSfm_BD.getTime()):null);
		Long vidaUtil = ((vidaUtil_BD != null && !("".equals(vidaUtil_BD.toString())))?new Long(vidaUtil_BD.toString()):null);
		Long flgFecsfm = ((flgFecsfm_BD != null && !("".equals(flgFecsfm_BD.toString())))?new Long(flgFecsfm_BD.toString()):null);
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		Long cc = ((cc_BD != null && !("".equals(cc_BD.toString())))?new Long(cc_BD.toString()):null);
		String tipoExpositor = tipoExpositor_BD;


		//Campos para textil
		String temporada = ( temporada_BD != null && ("GP-OI".equals(temporada_BD) || "GP-PV".equals(temporada_BD)) ? "GP" : temporada_BD );
		String anioColeccion = ( anioColeccion_BD == null ? "" : ( temporada_BD != null && ("OI".equals(temporada_BD) || "PV".equals(temporada_BD)) ? anioColeccion_BD : "9999" ) );
		String talla = talla_BD;
		String color = color_BD;
		String lote = lote_BD;
		String modeloProveedor = modeloProveedor_BD;
		String tempColNumOrden = tempColNumOrden_BD;
		String pedible = ( pedible_BD != null && "S".equals(pedible_BD.toUpperCase()) ? "S" : "N" );
		String porcionConsumidor = porcionConsumidor_BD;
		Long nivelLote = ((nivelLote_BD != null && !("".equals(nivelLote_BD.toString())))?new Long(nivelLote_BD.toString()):0);

		String flgMuchoPoco = flgMuchoPoco_BD;
		String flgFoto = flgFoto_BD;
		String flgNueva = flgNueva_BD;
		String flgSfmFijo = flgSfmFijo_BD;

		Long ccEstr = ((ccEstr_BD != null && !("".equals(ccEstr_BD.toString())))?new Long(ccEstr_BD.toString()):null);

		String flgNsr = flgNsr_BD;
		String flgUfp = flgUfp_BD;

		String flgSoloImagen = flgSoloImagen_BD;
		Double multiplicadorFacing = ((multiplicadorFacing_BD != null && !("".equals(multiplicadorFacing_BD.toString())))?new Double(multiplicadorFacing_BD.toString()):null);
		Double imagenComercialMin = ((imagenComercialMin_BD != null && !("".equals(imagenComercialMin_BD.toString())))?new Double(imagenComercialMin_BD.toString()):null);

		String clave = obtenerClaveRegistro(codN1, codN2, codN3, codN4, codN5, codArticulo);


		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
		String fechaSfmDDMMYYYY = (fechaSfm != null?df.format(fechaSfm ):"");

		VArtSfm vArtSfm =  new VArtSfm(codLoc, codArticulo, nivel, denomInforme,
				codN1, descCodN1, codN2, descCodN2,
				codN3, descCodN3, codN4, descCodN4,
				codN5, descCodN5, marca, lmin,
				lsf, sfm, coberturaSfm, ventaMedia,
				ventaAnticipada, uc, stock, diasStock,
				capacidad, fechaSfm, flgFecsfm, codError, descError, clave, rowNum, null, sfm, coberturaSfm, 
				capacidad, vidaUtil, facingCentro, facingCentro, facingPrevio, flgEstrategica, tipoGama, tipoAprov, pedir, cc, tipoExpositor,
				temporada, anioColeccion, talla, color, lote, modeloProveedor, tempColNumOrden, pedible, fechaSfmDDMMYYYY, porcionConsumidor,
				null, nivelLote
				); 

		vArtSfm.setFlgMuchoPoco(flgMuchoPoco);
		vArtSfm.setFlgFoto(flgFoto);
		vArtSfm.setFlgNueva(flgNueva);
		vArtSfm.setFlgSfmFijo(flgSfmFijo);
		vArtSfm.setCcEstr(ccEstr);
		vArtSfm.setFlgNsr(flgNsr);
		vArtSfm.setFlgUfp(flgUfp);
		vArtSfm.setFlgSoloImagen(flgSoloImagen);
		vArtSfm.setMultiplicadorFacing(multiplicadorFacing);
		vArtSfm.setImagenComercialMin(imagenComercialMin);
		vArtSfm.setTipoEstructura(tipoEstructura);

		return vArtSfm;
	}

	/**
	 * @param codN1
	 * @param codN2
	 * @param codN3
	 * @param codN4
	 * @param codN5
	 * @param codArticulo
	 * @return
	 */
	private String obtenerClaveRegistro(String codN1, String codN2, String codN3, String codN4, String codN5, Long codArticulo){

		StringBuffer claveRegistroBuff = new StringBuffer();
		claveRegistroBuff.append((codN1!=null && !"".equals(codN1))?codN1:"");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN2!=null && !"".equals(codN2))?codN2:"");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN3!=null && !"".equals(codN3))?codN3:"");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN4!=null && !"".equals(codN4))?codN4:"");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codN5!=null && !"".equals(codN5))?codN5:"");
		claveRegistroBuff.append("**");
		claveRegistroBuff.append((codArticulo!=null)?codArticulo:"");

		return claveRegistroBuff.toString();
	}


	/**
	 * Implementa la llamada al procedimiento que actualiza SfmFacCap.
	 * <p><code> p_apr_actualizar_sfm_fac_cap (p_tabla IN OUT apr_r_sfmcap_estr_reg) </code>
	 */
	@Override
	public SfmCapacidadFacing actualizacionSfmFacCap(List<VArtSfm> listaActualizacion, final String tipoEstructura, String usuario) {

		SfmCapacidadFacing salida = null;
		//Obtenci�n de par?metros de actualizaci�n
		final List<VArtSfm> listadoModificadosActualizacion = new ArrayList<VArtSfm>(); //Datos que se enviar?n al procedimiento
		final String usuarioRecibido = usuario;
		//Carga de lista de modificados
		for (int i=0; i<listaActualizacion.size(); i++){
			VArtSfm vartSfm = listaActualizacion.get(i);
			if (vartSfm != null && Constantes.SFMCAP_CODIGO_MENSAJE_MODIFICADO.equals(vartSfm.getCodError())){
				//Se guardan los modificados para pasarlos al procedimiento
				listadoModificadosActualizacion.add(vartSfm);
			}
		}

		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {

					//Crear estructura para actualización
					Struct itemConsulta = crearEstructuraActualizacionSfmFacCap(listadoModificadosActualizacion, tipoEstructura, usuarioRecibido, con);

					cs = con.prepareCall("{call PK_APR_MISUMI_IMC.p_apr_actualizar_sfm_fac_cap(?) }");
					cs.registerOutParameter(1, OracleTypes.STRUCT, APR_R_SFMCAP_ESTR_REG_TYPENAME);
					cs.setObject(1, itemConsulta);

				} catch (SQLException e) {
					logger.error("Error preparando actualizacion de SFM FAC CAP", e);
				}
				return cs;
			}
		};
		CallableStatementCallback<SfmCapacidadFacing> csCallback = new CallableStatementCallback<SfmCapacidadFacing>() {

			public SfmCapacidadFacing doInCallableStatement(CallableStatement cs) {
				SfmCapacidadFacing ret = null;
				try {
					cs.execute();
					Struct struct = (Struct) cs.getObject(1);
					ret = obtenerEstructuraVartSfm(struct);
				} catch (SQLException e) {
					logger.error("Fallo actualizando SFM FAC CAP", e);
				}
				return ret;
			}
		};


		try {
			salida = this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}


		return salida;
	}


	/**
	 * Preparar el mapeo de objeto java a BD.
	 * <p>Descripcion del modelo a mapear como resultante.
	 * <code>
	 * <li>  p_tabla     apr_r_sfmcap_ESTR_reg
	 * <li>apr_r_sfmcap_ESTR_reg AS OBJECT (
	    <p>    datos              apr_t_r_sfmcap_ESTR_dat,
	    <p>    estado             NUMBER(5),
	    <p>    desc_estado        VARCHAR2(32000)
		<p>)
		<li> apr_t_r_sfmcap_ESTR_dat IS TABLE OF apr_r_sfmcap_ESTR_dat
		<li> apr_r_sfmcap_ESTR_dat AS OBJECT (... 
		igual que apr_r_sfmcap_dat con un campo nuevo: tipo_estructura VARCHAR2)
	 * </code>
	 * @param listaModificadosActualizacion
	 * @param usuario
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	private Struct crearEstructuraActualizacionSfmFacCap(List<VArtSfm> listaModificadosActualizacion, String tipoEstructura, String usuario, Connection con) throws SQLException {

		//Transformaci�n de conexi�n a conexi�n de oracle. Necesari para definici�n del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		int numeroElementos = listaModificadosActualizacion.size();
		Object[] objectTabla = new Object[numeroElementos];

		for (int i=0; i<numeroElementos; i++){

			VArtSfm vArtSfm = (VArtSfm)listaModificadosActualizacion.get(i);

			Object[] objectInfo = new Object[67];

			//S�lo se informan los datos necesarios
			objectInfo[0] = (vArtSfm.getCodLoc()!=null?new BigDecimal(vArtSfm.getCodLoc()):null);
			objectInfo[1] = (vArtSfm.getCodArticulo()!=null?new BigDecimal(vArtSfm.getCodArticulo()):null);
			objectInfo[3] = vArtSfm.getCodN1();
			objectInfo[5] = vArtSfm.getCodN2();
			objectInfo[7] = vArtSfm.getCodN3();
			objectInfo[9] = vArtSfm.getCodN4();
			objectInfo[11] = vArtSfm.getCodN5();
			objectInfo[14] = (vArtSfm.getLmin()!=null?new BigDecimal(vArtSfm.getLmin().toString()):null);
			objectInfo[15] = (vArtSfm.getLsf()!=null?new BigDecimal(vArtSfm.getLsf().toString()):null);
			objectInfo[16] = (vArtSfm.getSfm()!=null?new BigDecimal(vArtSfm.getSfm().toString()):null);
			objectInfo[17] = (vArtSfm.getCoberturaSfm()!=null?new BigDecimal(vArtSfm.getCoberturaSfm().toString()):null);
			objectInfo[23] = (vArtSfm.getCapacidad()!=null?new BigDecimal(vArtSfm.getCapacidad().toString()):null);
			objectInfo[24] = (vArtSfm.getFacingCentro()!=null?new BigDecimal(vArtSfm.getFacingCentro().toString()):null);
			objectInfo[25] = (vArtSfm.getFacingPrevio()!=null?new BigDecimal(vArtSfm.getFacingPrevio().toString()):null);
			objectInfo[26] = (vArtSfm.getFacingCentroOrig()!=null?new BigDecimal(vArtSfm.getFacingCentroOrig().toString()):null);
			objectInfo[27] = vArtSfm.getFlgEstrategica();


			objectInfo[32] = (vArtSfm.getSfmOrig()!=null?new BigDecimal(vArtSfm.getSfmOrig().toString()):null);
			objectInfo[33] = (vArtSfm.getCapacidadOrig()!=null?new BigDecimal(vArtSfm.getCapacidadOrig().toString()):null);
			objectInfo[35] = usuario;
			//TABLA apr_t_r_sfmcap_estr_dat 
			//STRUCT apr_r_sfmcap_estr_REG toda la estructura
			//STRUCT apr_r_sfmcap_estr_dat registro
			// posibles valores "SFM" "CAP" "FAC"
			objectInfo[66] = tipoEstructura;// tipo_estructura;

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_SFMCAP_ESTR_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTabla[i] = itemObjectStruct;

		}

		Object[] objectConsulta = new Object[3]; //Tiene 3 campos pero solo nos interesa la lista
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_SFMCAP_ESTR_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		objectConsulta[0] = array;

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor(APR_R_SFMCAP_ESTR_REG_TYPENAME,conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);

		return itemConsulta;
	}

		
	@Override
	public Long recalcularCapacidad(final Long codLoc, final Long codArticulo, final Long facingCentro) {
    	
		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {



					cs = con.prepareCall("{call PK_APR_MISUMI_IMC.P_APR_RECALCULO_CAPACIDAD(?, ?, ?, ?, ?, ?) }");

					int paramIndex = 1;
					cs.setLong(paramIndex++, codLoc);
					cs.setObject(paramIndex++, codArticulo, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, facingCentro, OracleTypes.INTEGER);

					cs.registerOutParameter(paramIndex++, Types.INTEGER);
					cs.registerOutParameter(paramIndex++, Types.INTEGER);
					cs.registerOutParameter(paramIndex, Types.VARCHAR);


				} catch (SQLException e) {
					logger.error("Fallo preparando el recalculo de capacidad", e);
				}
				return cs;
			}
		};
		
		CallableStatementCallback<Long> csCallback = new CallableStatementCallback<Long>() {

			public Long doInCallableStatement(CallableStatement cs) {
				Long ret = null;
				try {
					cs.execute();
					ret = obtenerResultadoCapacidad(cs);
				} catch (SQLException e) {
					logger.error("Fallo ejecutando el recalculo de capacidad", e);                
				}
				return ret;
			}
		};


		Long salida = null;
		try {
			salida = (Long) this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}
		
	    return salida;
    }

	private Long obtenerResultadoCapacidad(CallableStatement cs){

		Long resultado = null;

		try{
			//Obtenci�n del par�metro de error
			int resultadoError = cs.getInt(5);
			if (resultadoError == 0 && !cs.wasNull()){ //Si hay error se ignora el dato de capacidad
				//Obtenci�n del par�metro de salida
				long lngResultado = cs.getLong(4);
				if(!cs.wasNull()){
					resultado = lngResultado;
				}
			}

		} catch (Exception e) {
			logger.error("Fallo obteniendo el resultado del recalculo", e);
		}

		return resultado;
	}
	
	/** Obtiene la estructura para cargar las combos de Area, Seccion, Categoria, Subcategoria.
	 * Combos necesarios para la pagina Sfm Fac Cap.
	 * <p> Estructura devuelta
	 * <code>
	 * <li> apr_r_estructura_reg AS OBJECT (
	    	DATOS              apr_t_r_estructura_dat,
	    	TIPO_ESTRUCTURA    VARCHAR2(10)
    	)
	 * <li>apr_t_r_estructura_dat IS TABLE OF apr_r_estructura_dat
	 * <li>APR_R_ESTRUCTURA_DAT AS OBJECT (
		    CODIGO    	                NUMBER(4),
		    DENOMINACION	            VARCHAR2(50 BYTE)
		)
	 * </code>
	 */
	@Override
	public EstructuraCom obtenerEstrCom(
			final Long codLoc, 
			final Long codN1, 
			final Long codN2, 
			final Long codN3, 
			final Long codN4
			){

		CallableStatementCreator csCreator = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) {
				CallableStatement cs = null;
				try {
					cs = con.prepareCall("{call PK_APR_MISUMI_IMC.P_APR_OBTENER_ESTR_COM(?, ?, ?, ?, ?, ?) }");

					int paramIndex = 1;
					cs.setObject(paramIndex++, codLoc, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, codN1, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, codN2, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, codN3, OracleTypes.INTEGER);
					cs.setObject(paramIndex++, codN4, OracleTypes.INTEGER);

					cs.registerOutParameter(paramIndex, OracleTypes.STRUCT, "APR_R_ESTRUCTURA_REG");

				} catch (SQLException e) {
					logger.error("Fallo preparando consulta de estructura com", e);             
				}
				return cs;
			}
		};
		CallableStatementCallback<EstructuraCom> csCallback = new CallableStatementCallback<EstructuraCom>() {

			public EstructuraCom doInCallableStatement(CallableStatement cs) {
				EstructuraCom ret = null;
				try {
					cs.execute();
					Struct struct = (Struct)cs.getObject(6);
					ret = obtenerEstructuraCom(struct);
				} catch (SQLException e) {
					logger.error("Fallo ejecutando consulta de estructura com", e);
				}
				return ret;
			}
		};

		EstructuraCom salida = null;
		try {
			salida = this.jdbcTemplate.execute(csCreator,csCallback);
		} catch (Exception e) {
			logger.error("#####################################################");
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return salida;
	}

	/**
	 * Mapea a objeto java.
	 * <p> Estructura a Mapear
	 * <code>
	 * <li> apr_r_estructura_reg AS OBJECT (
	    	DATOS              apr_t_r_estructura_dat,
	    	TIPO_ESTRUCTURA    VARCHAR2(10)
    	)
	 * <li>apr_t_r_estructura_dat IS TABLE OF apr_r_estructura_dat
	 * <li>APR_R_ESTRUCTURA_DAT AS OBJECT (
		    CODIGO    	                NUMBER(4),
		    DENOMINACION	            VARCHAR2(50 BYTE)
		)
	 * </code>
	 * @param struct
	 * @return
	 * @throws SQLException
	 */
	private EstructuraCom obtenerEstructuraCom(Struct struct) throws SQLException {
		EstructuraCom retorno = null;
		if(struct!=null){
			Object[] atributes = struct.getAttributes();
			Array arrayDatos = (Array) atributes[0]; // DATOS
			String tipoEstructura = (String) atributes[1]; // TIPO_ESTRUCTURA

			List<EstructuraDat> datos = new ArrayList<EstructuraDat>();
			if(arrayDatos!=null){
				ResultSet rsDatos = arrayDatos.getResultSet();

				while(rsDatos.next()){
					Struct datStruct = (Struct) rsDatos.getObject(2);
					EstructuraDat dato = this.mapEstructuraDat(datStruct);
					datos.add(dato);
				}
			}

			retorno = new EstructuraCom();
			retorno.setDatos(datos);
			retorno.setTipoEstructura(tipoEstructura);
		}
		return retorno;
	}

	/**
	 * Mapea a objeto java.
	 * <p> Estructura a Mapear
	 * <code>
	 * <li>APR_R_ESTRUCTURA_DAT AS OBJECT (
		    CODIGO    	                NUMBER(4),
		    DENOMINACION	            VARCHAR2(50 BYTE)
		)
	 * </code>
	 * @param struct
	 * @return
	 * @throws SQLException
	 */
	private EstructuraDat mapEstructuraDat(Struct struct) throws SQLException {
		Object[] atributes = struct.getAttributes();
		BigDecimal codigo = (BigDecimal) atributes[0];
		String denominacion = (String) atributes[1];
		EstructuraDat dato = new EstructuraDat();
		if(codigo!=null){
			dato.setCodigo(codigo.longValue());
		}
		dato.setDenominacion(denominacion);
		return dato;
	}

	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public String obtenerMetodosBoton(Long codCentro) {
		String query = " SELECT PK_APR_MISUMI_IMC.f_obtener_metodos_boton(?) FROM DUAL";

		List<Object> params = new ArrayList<Object>(1);
		params.add(codCentro);

		String salida = null;
		try{

			salida = this.jdbcTemplate.queryForObject(query, params.toArray(), String.class);
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query, params,e);
		}
		return salida;
	}

}
