package es.eroski.misumi.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Constantes {

	public final int INITIAL_BUFFER_SIZE = 3000 ;
	public final int POSICIONES_DECIMALES = 3;
	public final int NUMERO_DIAS_HISTORICO_VENTA_MES = 30;
	public final int DIAS_MOVIMIENTO_STOCK = 15;
	public final int DIAS_MOVIMIENTO_STOCK_DETALLE = 30;
	public final int NUMERO_DIAS_MAYOR_VENTA = 90;
	public final int DIAS_MOVIMIENTO_PISTOLA = 5;
	public final Float MOVIMIENTO_STOCK_NO_ASIGNADO = new Float(-99);
	public final int DIAS_STOCK_NO_SERVIDO = 21;
	public final Float STOCK_NO_ASIGNADO = new Float(-999999);
	public final int NUMERO_CARACTERES_USUARIO_CABECERA = 40;
	public final String FLG_CAPACIDAD_AUTOSERVICIO = "S";
	public final String FLG_CAPACIDAD_NO_AUTOSERVICIO = "N";
	public final String FLG_SI_FACING_CENTRO = "S";
	public final String FLG_NO_FACING_CENTRO = "N";
	public final String VALOR_CAMPO_BUSQUEDA_BORR_UNI_AUX= "CONFIRMACION_ENVIADO";
	public final String SFMCAP_TIPO_LISTADO_FAC= "FAC";
	public final String SFMCAP_TIPO_LISTADO_CAP= "CAP";
	public final String SFMCAP_TIPO_LISTADO_FACCAP= "FAC_CAP";
	public final String SFMCAP_TIPO_LISTADO_SFM= "SFM";
	public final Long SFMCAP_CODIGO_GUARDADO_CORRECTO = new Long("0");
	public final Long SFMCAP_CODIGO_MENSAJE_GUARDADO = new Long("8");
	public final Long SFMCAP_CODIGO_MENSAJE_MODIFICADO = new Long("9");
	public static final String WINDOWS_CE = "Windows CE";
	public final int PAGS_MOTIVOS = 2;
	public static final String ICONO_ERROR = "1";
	public static final String ICONO_MODIFICADO = "2";
	public static final String ICONO_GUARDADO = "3";
	public static final String ORIGEN_PRINCIPAL = "0";
	public static final String ORIGEN_CONSULTA = "1";
	public static final String REF_PISTOLA = "#";
	public static final String REF_BALANZA = "26";
	public static final String REF_BALANZA_ESPECIAL = "24";
	public static final String REF_EROSKI = "2200";
	public static final String REF_ERRONEA_EAN = "ERROR_EAN";
	public static final String BORRADO_ERRONEO_PANTALLA = "1";
	public static final String BORRADO_ERRONEO_BLOQUEADO_PANTALLA = "1B";
	public static final String MODIFICADO_CORRECTO_PANTALLA = "8";
	public static final String MODIFICADO_ERRONEO_PANTALLA = "2";
	public static final String MODIFICADO_FEC_ERRONEA_PANTALLA = "3";
	public static final String MODIFICADO_NO_MODIFICABLE = "4";
	public static final String MODIFICADO_FEC_INICIO_ERROR = "5";
	public static final String TIPO_PEDIDO_ENCARGO = "E";
	public static final String TIPO_PEDIDO_PILADA = "P";
	public static final String PEDIDO_MODIFICABLE_NO = "N";
	public static final String PEDIDO_MODIFICABLE_SI = "S";
	public static final String PEDIDO_MODIFICABLE_PARCIAL = "P";
	public static final String PEDIDO_MODIFICABLE_BLOQUEO = "B";
	public static final String PEDIDO_MODIFICABLE_BLOQUEO_TOTAL_MODIF = "T";
	public static final String PEDIDO_MODIFICABLE_BLOQUEO_PARCIAL_MODIF = "Q";
	public static final String PEDIDO_NO_BORRABLE = "N";
	public static final String PEDIDO_BORRABLE = "S";
	public static final String PEDIDO_BORRABLE_MODIF = "M";
	public static final String PEDIDO_ADICIONAL_MAC = "S";
	public static final String PEDIDO_ADICIONAL_NO_MAC = "N";
	public static final String PEDIDO_ESTADO_NO_ACTIVA = "NOACT";
	public static final String REF_CATALOGO_BAJA = "B";
	public static final String REF_CATALOGO_ALTA = "A";
	public static final String REF_TEXTIL_PEDIBLE_SI = "S";
	public static final String REF_TEXTIL_PEDIBLE_NO = "N";
	public static final String REF_TEXTIL_PEDIR_SI = "S";
	public static final String REF_TEXTIL_PEDIR_NO = "N";
	public static final String REF_TEXTIL_LOTE_SI = "S";
	public static final String REF_TEXTIL_LOTE_NO = "N";
	public static final String REF_TEXTIL_LOTE_TODOS = "T";
	
	//OPCIONES de Menú DATOS REFERENCIA
	public static final String MENU_PDA_DATOS_REFERENCIA_DATOS = "DR";
	public static final String MENU_PDA_DATOS_REFERENCIA_PEDIDOS = "SP";
	public static final String MENU_PDA_DATOS_REFERENCIA_STOCK = "MS";
	
	//Opciones de Menú
	public static final String MENU_PDA_DATOS_REFERENCIA = "PDA_DR";
	public static final String MENU_PDA_SFM = "PDA_SFM";
	public static final String MENU_PDA_VENTA_ANTICIPADA= "PDA_VA";
	public static final String MENU_PDA_QUE_HACER_REF= "PDA_QHR";
	public static final String MENU_PDA_PL = "PDA_PL";
	
	//Pantalla Menu CAPTURA_RESTOS
	public static final String MENU_PDA_CAPTURA_RESTOS= "CR";
	//Pantalla Menu SACADA_RESTOS
	public static final String MENU_PDA_SACADA_RESTOS= "SR";

	public static final String MENU_PDA_PREHUECOS= "PH";
	public static final String MENU_PDA_PREHUECOSPED= "PHPed";
	public static final String MENU_PDA_PREHUECOSSTOCK= "PHStock";
	//Pestanas pedido adicional
	public static final String PESTANA_PEDIDO_ENCARGO = "1";
	public static final String PESTANA_PEDIDO_MONTAJE= "2";
	public static final String PESTANA_PEDIDO_MONTAJE_OFERTA= "3";
	
	//Tipos de listado pedidoAdicional
	public final String PED_ADI_TIPO_LISTADO_FRESCO_PURO= "FP";
	public final String PED_ADI_TIPO_LISTADO_ALIMENTACION= "AFI";
	
	//Tipos de listado listado gama centro
	public final String LIS_GAMA_TIPO_LISTADO_DATOS_GENERALES= "1";
	public final String LIS_GAMA_TIPO_LISTADO_SFM= "2";
	public final String LIS_GAMA_TIPO_LISTADO_TEXTIL= "3";

	//Seguimiento de pedidos
	public final int FILAS_ULTIMOS_ENVIOS_PDA = 3;
	
	//Clases de pedido
	public final String CLASE_PEDIDO_ENCARGO = "1";
	public final String CLASE_PEDIDO_MONTAJE = "2";
	public final String CLASE_PEDIDO_MONTAJE_ADICIONAL = "3";
	public final String CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL = "7";
	public final String CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA = "8";
	public final String CLASE_PEDIDO_EMPUJE = "6";
	public final String CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4 = "4";
	public final String CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5 = "5";
	public final String CLASE_PEDIDO_ENCARGO_CLIENTE = "7";
	
	public final Long PED_ADI_CODIGO_MENSAJE_CORRECTO = new Long("0");
	public final Long PED_ADI_CODIGO_MENSAJE_GUARDADO = new Long("8");
	public final Long PED_ADI_CODIGO_MENSAJE_MODIFICADO = new Long("9");
	
	public final String PANTALLA_LISTADOS = "1";
	public final String PANTALLA_OFERTAS = "2";
	public final String PANTALLA_CALENDARIO = "3";
	public final String PANTALLA_NUEVO_REFERENCIA = "4";
	
	public final String SI_OFERTA = "SI";
	public final String NO_OFERTA = "NO";
	
	public final int DIAS_ELIMINAR = 1;
	
	public final Long PERFIL_CENTRO = new Long("3"); //Todos los perfiles distintos de 3 son de perfil CENTRAL

	public static final String BORRADO_LOGICO = "BL";
	public static final String MODIFICADO = "M";
	
	public static final Integer NUM_DIAS_REFERENCIA_NUEVA = 5;
	public static final Integer NUM_DIAS_REFERENCIA_NUEVA_BLOQUEO_PILADA = 3;
	public static final Integer NUM_DIAS_INTERVALO_FRESCOS_MODIFICACION = 3;
	
	//Time out del web service StockActual en milisegundos
	//public static final int TIMEOUT_WS = 15000;
	public static final int TIMEOUT_WS = 20000;
	public static final int TIMEOUT_STOCK_TIENDA_WS = 4000;
	public static final int TIMEOUT_VENTAS_TIENDA_WS = 4000;
	
	public static final String LISTA_PEDIDOS_POR_CENTRO = "listaPedidosPorCentro";
	public static final String LISTA_PEDIDOS_POR_OFERTA = "listaPedidosPorOferta";
	
	public static final String TIPO_COMPRA_VENTA_SOLO_VENTA = "V";
	public static final String TIPO_COMPRA_VENTA_SOLO_COMPRAVENTA = "T";
	public static final String REFERENCIA_EXPOSITOR = "S";
	
	//Correcciones de stock en pistola
	public static final Integer NUM_REGISTROS_PANTALLA_CORR_STOCKS = 6;
	public static final Integer NUM_REGISTROS_PANTALLA_CORR_STOCKS_TEXTIL = 3;
	public static final String STOCK_TIENDA_RESULTADO_OK = "OK";
	public static final String STOCK_TIENDA_RESULTADO_KO = "KO";
	public static final String STOCK_TIENDA_RESULTADO_WARN = "WN";
	public static final String STOCK_TIENDA_CONSULTA_BASICA = "CB";
	//public static final String STOCK_TIENDA_CONSULTA_BASICA_PISTOLA = "CV";
	public static final String STOCK_TIENDA_CONSULTA_BASICA_PISTOLA = "CB";
	public static final String STOCK_TIENDA_CONSULTA_CORRECION = "CC";
	public static final String STOCK_TIENDA_ELECCION_REFERENCIA_MADRE = "CE";
	public static final String STOCK_TIENDA_PORCION_CONSUMIDOR = "PC";
	public static final String STOCK_TIENDA_TIPO_UNITARIA = "U";
	public static final String STOCK_TIENDA_TIPO_COMPRA = "C";
	public static final String STOCK_TIENDA_TIPO_ESTANDAR = "S";
	public static final String STOCK_PRINCIPAL_STOCK = "S";
	public static final String STOCK_PRINCIPAL_BANDEJAS = "B";
	
	
	//Origen de consultas de corrección de stocks
	public static final String ORIGEN_DATOS_REF = "1";
	public static final String ORIGEN_SEGUIMIENTO_PEDIDOS = "2";
	
	//Tipos de negocio del centro
	public static final String CENTRO_NEGOCIO_HIPER = "H";
	
	//Avisos para el centro
	public static final String TABLON_ANUNCIOS_VALIDAR_CANTIDADES_EXTRA = "1";
	public static final String TABLON_ANUNCIOS_PEDIDO_ADICIONAL = "2";
	public static final String TABLON_ANUNCIOS_DEVOLUCIONES = "3";
	public static final String TABLON_ANUNCIOS_CALENDARIO = "4";
	public static final String TABLON_ANUNCIOS_CALENDARIO_PLATAFORMA = "5";
	public static final String TABLON_ANUNCIOS_DEVOLUCIONES_URGENTE = "6";
	public static final String TABLON_ANUNCIOS_CALENDARIO_AVISO_KO = "7";
	public static final String TABLON_ANUNCIOS_ENTRADAS = "8";
	public static final String TABLON_ANUNCIOS_AVISO_PLU = "9";
	public static final String TABLON_ANUNCIOS_AVISO_MOSTRADOR = "10";
	public static final String TABLON_ANUNCIOS_AVISO_FACING_CERO = "11";
	public static final String TABLON_AJUSTE_PEDIDOS = "12";
	
	//Campañas
	public static final String SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA = "C";
	public static final String SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_OFERTA = "O";
	public static final String SEGUIMIENTO_CAMPANAS_DATOS_CARGADOS = "S";
	public static final String SEGUIMIENTO_CAMPANAS_FILTRO_ESTRUCTURA = "G";
	public static final String SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA = "A";
	
	//Códigos de bloqueo
	public static final String COD_TP_BLOQUEO_ENCARGO = "E";
	public static final String COD_TP_BLOQUEO_MONTAJE = "P";
	public static final String COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE = "EP";	
	public static final String COD_TP_BLOQUEO_DETALLADO = "D";	
	public static final String COD_TP_BLOQUEO_TODOS = "T";
	public static final String CODIGO_ERROR_BLOQUEO_PILADA_WS = "2001";
	
	//Códigos de validados en pedidos no gestionados por PBL
	public static final String NO_GESTIONADO_PBL_VALIDADOS_SI = "S";
	public static final String NO_GESTIONADO_PBL_VALIDADOS_NO = "N";
	public static final String NO_GESTIONADO_PBL_VALIDADOS_TODOS = "T";
	
	//Tipos de Area
	public static final String AREA_FRESCOS = "1";
	public static final String AREA_ALIMENTACION = "2";
	public static final String AREA_TEXTIL = "3";
	public static final String AREA_BAZAR = "4";
	public static final String AREA_ELECTRO = "5";
	
	//Códigos de motivos de stock
	public static final String TENGO_MUCHO_POCO_MOTIVO_MUCHO = "M";
	public static final String TENGO_MUCHO_POCO_MOTIVO_POCO = "P";
	public static final String TENGO_MUCHO_POCO_MOTIVO_LINK = "L"; //Para obtención del mostrado del link
	
	public final int PAGS_MOTIVOS_TENGO_MUCHO_POCO_PISTOLA = 2;
	public final String ERROR_TENGO_MUCHO_POCO_PISTOLA = "Error";
	
	//Inventario libre
	public final String INVENTARIO_LIBRE_CAMARA_ALMACEN = "CA";
	public final String INVENTARIO_LIBRE_SALA_VENTA = "SV";
	public final String INVENTARIO_LIBRE_UNICA_SI = "S";
	public final String INVENTARIO_LIBRE_UNICA_NO = "N";
	public final String INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS = "B";
	public final String INVENTARIO_LIBRE_STOCK_PRINCIPAL_STOCK = "S";
	public final String INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI = "S";
	public final String INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO = "N";
	public final String INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS = "------";
	public final String INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI = "S";
	public final String INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_NO = "N";
	
	//Relación artículo
	public static final int RELACION_ARTICULO_COMPRAVENTA = 1;
	
	//Encargos cliente
	public static final String ENCARGOS_CLIENTE_ESPECIAL = "E";
	public static final String ENCARGOS_CLIENTE_NORMAL = "N";
	public static final Long ENCARGOS_CLIENTE_PRIMER_NIVEL = new Long(1);
	public static final Long ENCARGOS_CLIENTE_SEGUNDO_NIVEL = new Long(2);
	public static final String ENCARGOS_CLIENTE_ANULADO = "ANULADO";
	public static final Long TIPO_PEDIDO_ENCARGO_CLIENTE = new Long(3);
	
	public static final int SECCION_CARNICERIA = 2;
	public static final int SECCION_CHARCUTERIA = 4;
	public static final int SECCION_PESCADERIA = 7;
	public static final int CATEGORIA_QUESOS_MOSTRADOR = 1;
	public static final int CATEGORIA_ESPECIALIDADES = 12;
	public static final int CATEGORIA_QUESOS_RECIEN_CORTADOS = 25;
	public static final int SUBCATEGORIA_RECIEN_CORTADO = 28;
	
	public static final String BLOQUEOS_MODO_FECHA_ENTREGA = "FE";
	public static final String BLOQUEOS_MODO_FECHA_TRANSMISION = "FT";
	
	//Referencia por catálogo
	public static final String REFERENCIA_POR_CATALOGO_AREA = "5";
	public static final String REFERENCIA_POR_CATALOGO_CC = "89";
	
	//Tipo de rotación
	public static final String TIPO_ROTACION_ALTA = "AR";
	public static final String TIPO_ROTACION_MEDIA = "MR";
	public static final String TIPO_ROTACION_BAJA = "BR";
	
	//Parametrización de campaña par mostrado de columnas en excel de seguimiento de campañas
	public static final String PARAM_CENTROS_OPC_CAMPANA = "CAMPANA";
	
	public static final String PARAM_AYUDA_UBICAR_ALTAS = "145_AYUDA_UBICAR_ALTAS";

	public static final String ORIGEN_DETALLADO = "ORIGEN_DETALLADO";

	//Imprimir Etiquetas PDA
	public static final String HASH_MAP_NUMERO_ETIQUETA_IN_SESSION = "hashMapNumeroEtiquetaInSession";
	public static final String IS_NUMERO_ETIQUETA_IN_SESSION_ENVIADOS = "impresoraActiva";
	
	//Excell
	public static final int TAM_AREA_IMPRESION = 33000;
	public static final int NUM_CAMPOS_CAB_EXCEL_SFM = 7;
	public static final int ANCHO_PREDETERMINADO_EXCEL_SFM = 2800;
	
	//Tipo aprovisionamiento
	public static final String TIPO_APROVISIONAMIENTO_CENTRALIZADO = "C";
	public static final String TIPO_APROVISIONAMIENTO_DESCENTRALIZADO = "D";
	public static final String TIPO_APROVISIONAMIENTO_GRUPAJE = "G";

	//Caprabo
	public static final Long CODIGO_SOCIEDAD_CAPRABO = new Long("125");
	public static final Long CODIGO_FLG_CAPRABO_ESPECIAL_NO = new Long("0");
	public static final Long CODIGO_FLG_CAPRABO_ESPECIAL_SI = new Long("1");
	public static final Long CODIGO_FLG_CAPRABO_NUEVO_NO = new Long("0");
	public static final Long CODIGO_FLG_CAPRABO_NUEVO_SI = new Long("1");

	//Devoluciones
	public static final String PAGINACION_POR_REFERENCIA = "1";
	public static final String PAGINACION_POR_PROVEEDOR = "2";
	public static final String PAGINACION_TODO = "3";
	public static final String DEVOLUCIONES_PDA_LISTA = "LISTA_DEV";
	public static final String DEVOLUCIONES_MOSTRAR_HISTORICO = "S";
	public static final String DEVOLUCIONES_NO_MOSTRAR_HISTORICO = "N";
	public static final Long DEVOLUCIONES_PDA_DEVOLUCIONES_PENDIENTES_ESTADO = new Long("1");
	public static final int DEVOLUCIONES_PDA_PAGS_DEVOLUCIONES = 3;
	public static final int DEVOLUCIONES_PDA_PAGS_ORDEN_RETIRADA = 1;
	public static final int DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA_CON_IMAGEN = 1;
	public static final int DEVOLUCIONES_PDA_PAGS_FIN_CAMPANA = 2;
	public static final int DEVOLUCIONES_PDA_PAGS = 3;
	public static final String DEVOLUCIONES_ORIGEN_PANTALLA_DEV_OK = "DEV_OK";
	public static final String DEVOLUCIONES_ORIGEN_PANTALLA_DEV_KO = "DEV_KO";
	public static final String DEVOLUCIONES_ORIGEN_PANTALLA_ORDEN_RETIRADA = "ORDEN_RET";
	public static final String DEVOLUCIONES_ORIGEN_PANTALLA_FIN_CAMPANIA = "FIN_CAM";
	public static final String DEVOLUCIONES_PDA_ACCION_FILTRAR = "Filtrar";
	public static final String DEVOLUCIONES_PDA_ACCION_VERCABECERA = "VerCabecera";
	public static final String DEVOLUCIONES_PDA_ACCION_VERFOTO = "VerFoto";
	public static final String DEVOLUCIONES_PDA_ACCION_VERCABECERADESDEERROROK= "VerCabeceraErrOk";
	public static final String DEVOLUCIONES_PDA_ACCION_MASINFO= "MasInfo";
	public static final String DEVOLUCIONES_PDA_ACCION_STOCKLINK= "StockLink";
	public static final String DEVOLUCIONES_PDA_ACCION_STOCKDEVUELTOLINK = "StockDevueltoLink";
	public static final String DEVOLUCIONES_PDA_ACCION_SIGUIENTE_VACIO = "SiguienteVacio";
	public static final String DEVOLUCIONES_PDA_ACCION_PRIMERA_PAGINA = "PrimeraPagina";
	public static final String DEVOLUCIONES_PDA_ACCION_ANTERIOR_PAGINA = "AnteriorPagina";
	public static final String DEVOLUCIONES_PDA_ACCION_SIGUIENTE_PAGINA = "SiguientePagina";
	public static final String DEVOLUCIONES_PDA_ACCION_ULTIMA_PAGINA = "UltimaPagina";
	public static final String DEVOLUCIONES_PDA_ACCION_GUARDAR = "Guardar";
	public static final String DEVOLUCIONES_PDA_ACCION_FINALIZAR = "Finalizar";
	public static final String DEVOLUCIONES_PDA_ACCION_FINALIZAR_RMA = "FinalizarRma";
	public static final String DEVOLUCIONES_PDA_ACCION_FINALIZAR_POPUP = "FinalizarPopup";
	public static final String DEVOLUCIONES_PDA_ACCION_ORDEN_RETIRADA = "OrdenRetirada";
	public static final String DEVOLUCIONES_PDA_ACCION_FIN_CAMPANA = "FinCampana";
	public static final String DEVOLUCIONES_PDA_ACCION_CARGAR_BULTOS = "CargarBultos";
	public static final String DEVOLUCIONES_PDA_ACCION_FINALIZAR_POPUP_REFERENCIAS_SIN_INFORMAR = "FinalizarPopupReferenciasSinInformar";
	public static final String DEVOLUCIONES_PDA_ACCION_CERRAR_BULTO = "CerrarBulto";
	public final Long DEVOLUCIONES_PDA_CODIGO_FINALIZAR_CORRECTO = new Long("0");
	public final Long DEVOLUCIONES_PDA_CODIGO_FINALIZAR_REFERENCIAS_SIN_INFORMAR = new Long("4");
	public static final String DEVOLUCIONES_PDA_PERMISO_27 = "27_PDA_DEVOLUCIONES_PROCEDIMIENTO";

	public static final String DEVOLUCIONES_ORDEN_RETIRADA = "Orden Retirada";
	
	//Devoluciones estados
	public static final long DEVOLUCIONES_ESTADO_PREPARAR_MERCANCIA = 1;
	public static final long DEVOLUCIONES_ESTADO_PLATAFORMA = 2;
	public static final long DEVOLUCIONES_ESTADO_ABONADO = 3;
	public static final long DEVOLUCIONES_ESTADO_INCIDENCIA = 4;
	
	//Devoluciones avisos
	public static final long DEVOLUCIONES_AVISOS_COD_ERROR_OK = 0;
	public static final String DEVOLUCIONES_AVISOS_FLG_SI = "S";
	public static final String DEVOLUCIONES_AVISOS_FLG_NO = "N";
	public static final String DEVOLUCIONES_AVISOS_FLG_FRESCOS_SI = "S";
	public static final String DEVOLUCIONES_AVISOS_FLG_FRESCOS_NO = "N";
	public static final String DEVOLUCIONES_AVISOS_FLG_ALI_SI = "S";
	public static final String DEVOLUCIONES_AVISOS_FLG_ALI_NO = "N";
	public static final String DEVOLUCIONES_AVISOS_FLG_NO_ALI_SI = "S";
	public static final String DEVOLUCIONES_AVISOS_FLG_NO_ALI_NO = "N";
	public static final String DEVOLUCIONES_FORMATO1="Caja Original Cerrada";
	public static final String DEVOLUCIONES_FORMATO2="Unidades/Kgs";
	
	public static final String DEVOLUCIONES_AVISOS_FLG_URGENTE_FRESCOS_SI = "S";
	public static final String DEVOLUCIONES_AVISOS_FLG_URGENTE_ALI_SI = "S";
	public static final String DEVOLUCIONES_AVISOS_FLG_URGENTE_NO_ALI_SI = "S";
	//Calendario avisos
	public static final long CALENDARIO_AVISOS_COD_ERROR_OK = 0;
	public static final String CALENDARIO_AVISOS_FLG_SI = "S";
	public static final String CALENDARIO_AVISOS_FLG_NO = "N";
	
	//Entradas avisos
	public static final long ENTRADAS_AVISOS_COD_ERROR_OK = 0;
	public static final String ENTRADAS_AVISOS_FLG_SI = "S";
	public static final String ENTRADAS_AVISOS_FLG_FRESCOS_SI = "S";
	public static final String ENTRADAS_AVISOS_FLG_ALI_SI = "S";
	public static final String ENTRADAS_AVISOS_FLG_NO_ALI_SI = "S";
	
	
	//Opciones/Permisos para PC y Pistola
	public static final String BUSCADOR_1 = "1_BUSCADOR";
	public static final String SFM_CAP_FAC_2 = "2_SFM/CAP/FAC";
	public static final String MIS_PEDIDOS_3 = "3_MIS_PEDIDOS";
	public static final String DETALLADO_4 = "4_DETALLADO";
	public static final String PEDIDOS_ADICIONAL_5 = "5_PEDIDOS_ADICIONAL";
	public static final String LISTADO_GAMA_6 = "6_LISTADO_GAMA";
	public static final String ONSULTA_DATOS_REF_7 = "7_CONSULTA_DATOS_REF";
	public static final String INTERTIENDA_8 = "8_INTERTIENDA";
	public static final String EXCLUSION_VENTA_9 = "9_EXCLUSION_VENTA";
	public static final String VENTA_ANTICIPADA_10 = "10_VENTA_ANTICIPADA";
	public static final String ENCARGOS_CLIENTE_11 = "11_ENCARGOS_CLIENTE";
	public static final String CESTAS_12 = "12_CESTAS";
	public static final String PESCA_13 = "13_PESCA";
	public static final String CAJAS_CAMPANA_14 = "14_CAJAS_CAMPANA";
	public static final String DEVOLUCIONES_15 = "15_DEVOLUCIONES";
	public static final String CAMPANA_16 = "16_CAMPANA";
	public static final String PDA_SFM_17 = "17_PDA_SFM";
	public static final String PDA_VENTA_ANTICIPADA_18 = "18_PDA_VENTA_ANTICIPADA";
	public static final String PDA_QUE_HACER_19 = "19_PDA_QUE_HACER";
	public static final String PDA_REVISION_HUECOS_20 = "20_PDA_REVISION_HUECOS";
	public static final String PDA_INVENTARIO_LIBRE_21 = "21_PDA_INVENTARIO_LIBRE";
	public static final String PDA_ENCARGO_PISTOLA_22 = "22_PDA_ENCARGO_PISTOLA";
	public static final String PDA_IMPRESORA_23 = "23_PDA_IMPRESORA";
	public static final String PDA_CONTROL_STOCK_24 = "24_PDA_CONTROL_STOCK";
	public static final String PDA_ROTACION_25 = "25_PDA_ROTACION";
	public static final String PDA_DEVOLUCIONES_26 = "26_PDA_DEVOLUCIONES";
	public static final String PDA_DEVOLUCIONES_FOTO_27 = "27_PDA_DEVOLUCIONES_FOTO";
	public static final String PDA_DEVOLUCIONES_SIN_FOTO_28 = "28_PDA_DEVOLUCIONES";
	public static final String PDA_PACKING_LIST = "125_PDA_PACKING_LIST";
	public static final String PC_27_DEVOLUCIONES_PROCEDIMIENTO = "27_PC_DEVOLUCIONES_PROCEDIMIENTO";
	public static final String PERMISO_DEVOLUCIONES_PC = "15_DEVOLUCIONES";
	public static final String PERMISO_DEVOLUCIONES_PC_DUPLICAR = "36_DUPLICAR_DEVOLUCIONES";
	public static final String PERMISO_DEVOLUCIONES_PC_ELIMINAR = "37_ELIMINAR_DEVOLUCIONES";
	public static final String PERMISO_CALENDARIO = "40_CALENDARIO";
	public static final String PERMISO_FACING_0 = "48_AVISO_FACING_0";
	public static final String PERMISO_MOSTRADOR = "52_DETALLADO_MOSTRADOR";
	public static final String PERMISO_GAMA_RAPID = "65_LISTADO_RAPID";
	public static final String PERMISO_ENTRADAS = "70_DESCENTRALIZADO";
	public static final String PERMISO_LOG = "99_LOG";
	public static final String PERMISO_AVISOS_PLU = "107_ALARMAS_PLU";
	//Permiso para revisar los prehuecos del lineal y revisar antes de caer en HUECOS
	public static final String PERMISO_127_PDA_PREHUECOS="127_PDA_PREHUECOS";
	public static final String PERMISO_31_CORTE_PEDIDO="31_MIS_PEDIDOS_CORTE_PEDIDO";	
	
	
	public static final String COLUMNA_ESTADO = "Estado";
	public static final String PROVEEDOR_REF_PERMANENTES = "REF_PERMANENTES";
	
	public static final String V_REFERENCIA_PEDIR_SIA_TIPO_HORIZONTE = "HORIZONTE";
	

	/** Constante para comprobar si la aplicacion esta corriendo en WEB2.
	 * Se desea alterar en comportamiento de la aplicacion cuando esta corre
	 * en el contexto MISUMI-web2.
	 */
	public static final String CONTEXTO_WEB2 = "/MISUMI-web2";
	
	//GAMA LIBRE EXCLUIDA
	public static final String GAMALIBRE_PONER= "P";
	public static final String GAMALIBRE_QUITAR= "Q";
	
	//CENTRO CAPRABO EROSKI BALEARES GOOGLE
	public static final String CENTRO_EROSKI = "Eroski+";
	public static final String CENTRO_CAPRABO = "Caprabo+";
	public static final String CENTRO_BALEARES = "Baleares";
	public static final String CENTRO_MERCAT = "Mercat+";
	
	
	public static final String HASH_MAP_CLAVES_SFM_IN_SESSION = "hashMapClavesSfmInSession";
	
	//Desglose del facing
	public static final String TIPO_PLANOGRAMA = "P";
	
	//Planogramado
	public static final String PLANOGRAMA_FORZADO_MISUMI = "FORZADO_MISUMI"; 
	
	//Tipo UFP
	public static final String TIPO_UFP = "U";
	
	//Cantidad de impresoras a mostrar por paginación de impresoras
	public static final Integer NUMERO_IMPRESORAS_POR_PAGINACION_BLANCOYNEGRO = 4;
	
	//Devolucion creada por el centro
	public static final String DEVOL_CREADA_CENTRO = "Creada por el centro";
	
	//Plataforma caprabo ORION
	public static final int PLATAFORMA_2418 = 2418;
	public static final int PLATAFORMA_2415 = 2415;
	public static final int PLATAFORMA_7951 = 7951;
	public static final int PLATAFORMA_7956 = 7956;
	public static final int PLATAFORMA_7952 = 7952;
	public static final int PLATAFORMA_7957 = 7957;
	
	// Tipo RMA
	public static final String TIPO_RMA_TIENDA = "T";
//	public static final String TIPO_RMA_CENTRO = "C";
	
	//Pet. Misumi-124
	public static final int IMC_PLANOGRAMA = 1;
	public static final int IMC_SFM = 2;
	
	//MISUMI-146
	public static final String VENTA_REPO = "2";
	public static final String VENTA_REPO_AREA_TEXTIL = "TEXTIL";

	//Calendario warnings
	public static final String CALENDARIO_WARNING_LEIDO = "S";
	public static final String CALENDARIO_WARNING_NO_LEIDO = "N";
	
	//MISUMI-171
	public static final Long AGRUP_BAZAR = 281L;
	public static final Long AGRUP_ELECTRO = 279L;
	
	//TIPO DE REFERENCIA IMC
	public static final String TIPO_REFERENCIA_IMC_CAJA_EXPOSITORA = "1";
	public static final String TIPO_REFERENCIA_IMC_UNITARIA = "2";
	public static final String TIPO_REFERENCIA_IMC_FFPP = "3";
	
	//Parametrizacion cestas
	public static final int COD_ERROR_OK = 0;
	public static final int COD_ERROR_INSERT_LOTE = 1;
	public static final int COD_ERROR_UPDATE_LOTE = 2;
	public static final int COD_ERROR_INSERT_DUP_LOTE = 3;
	public static final int COD_ERROR_UPDATE_ARTICULO_LOTE = 4;
	public static final int COD_ERROR_INSERT_ARTICULO_LOTE = 5;
	public static final int COD_ERROR_DELETE_ARTICULO_LOTE = 6;
	public static final int COD_ERROR_NO_EXISTE_ARTICULO = 7;
	
	public static final int OFERTA_4000 = 4000;
	public static final List<Long> OFERTA_ANNO = Collections.unmodifiableList(Arrays.asList(new Long [] {new Long(2010), new Long(9999)}));
	
	public static final int ANIO_9999 = 9999;
	
	// Nº por defecto de teclas por balanza.
	public final int NUM_TECLAS_BALANZA = 208;
	// Vegalsa
	public static final Long CODIGO_SOCIEDAD_VEGALSA = new Long("13");

	// Tipo Etiqueta Facing VEGALSA
	public static final int WS_TIPO_ETIQUETA_OK = 0;
	
	// Facing VEGALSA
	public static final String CONSULTAR_FACING = "CF";
	public static final String MODIFICAR_FACING = "MF";
	public static final int WS_FACING_VEGALSA_OK = 0;
	public static final int WS_FACING_VEGALSA_KO = 1;

	// Facing Alto x Ancho
	public static final String MEDIDAS_FACING = "AA";
	public static final String MEDIDAS_FACING_CAP = "AC";
	public static final String MEDIDAS_FACING_CAP_ET = "AD";
	public static final int WS_FACING_ALTO_ANCHO_OK = 0;
	public static final int WS_FACING_ALTO_ANCHO_KO = 1;
	
	// Facing Modificable.
	public static final String ENVIAR_GISAE_ALTO_ANCHO_46 = "46_MODIF_IMC_PLANOS_VEGALSA";
	public static final String ENVIAR_GISAE_ALTO_ANCHO_47 = "47_ENVIAR_GISA_ALTO_ANCHO";
	public static final String ENVIAR_GISAE_ALTO_ANCHO_CAP_47 = "47_ENVIAR_GISA_ALTO_ANCHO_CAP";
	public static final String ENVIAR_GISAE_ALTO_ANCHO_CAP_ET_47 = "47_ENVIAR_GISA_ALTO_ANCHO_CAP_ET";

	// Movimientos Stock
	public static final String MOVIMIENTOS_STOCK_130 = "130_MOVIMIENTOS_STOCK";
	public static final String PDA_MOVIMIENTOS_STOCK_130 = "130_PDA_MOVIMIENTOS_STOCK";

	public static final String SI = "SI";
	public static final String NO = "NO";
	
	// Modificación FACING PISTOLA
	public static final String MOD_FACING_PISTOLA = "S";

	// Mensaje de error si no se ha podido guardar el nuevo valor de PLUs
	public static final String ERROR_MAX_PLU = "Error-1. El número máximo de PLUs no se ha podido actualizar en la BBDD.";
	public static final String UPDATE_CERO = "No existe el registro. Se deberá realizar el alta.";
	public static final String ERROR_NO_EXISTE = "0";
	public static final String ERROR_NO_ESTA_WS_GISAE = "1";

	public static final String NO_ES_RECARGA = "N";
	
	public static final String FLG_SIA_SI = "S";
	
	// Incluir/Excluir GAMA
	public static final String INCLUIR = "INCLUIR";
	public static final String EXCLUIR = "EXCLUIR";
	public static final String ERROR_WS_INCLUIR_EXCLUIR = "Error de conexión, en estos momentos no se puede ejecutar la operación";
	
	// Incluir/Excluir GAMA
	public static final String ABRIR_BULTO = "A";
	public static final String BORRAR_BULTO = "B";
	public static final String CERRAR_BULTO = "C";

	public static final String ACCION_ABRIR_BULTO = "AbrirBulto";
	public static final String ACCION_BORRAR_BULTO = "BorrarBulto";
	

	// Proveedores
	public static final int PROVEEDORES_PDA_PAGS_MAX = 6;
	public static final int TAM_MAX_DESC_PROV = 28;
	
	// Estado del bulto.
	public static final String ESTADO_CERRADO = "S"; // Bulto Cerrado=No está abierto.
	public static final String ESTADO_ABIERTO = "N"; // Bulto Abierto=No está cerrado.
	
	// Proveedor SIN BULTOS.
	public static final String PROV_SIN_BULTOS = "El proveedor seleccionado NO tiene BULTOS.";

	public static final String CON_DATOS_BULTO = "S"; // Bulto con datos cargados.
	public static final String SIN_DATOS_BULTO = "N"; // Bulto sin datos cargados.
	
	// Constantes para el desarrollo de PackingList a la hora de obtener
	// el resultado de la invocación a un servicio del API. 
	public static final String PACKING_LIST_OK = "OK";
	public static final String PACKING_LIST_ERROR = "ERROR";
	public static final String X_APP_KEY = "X-APP-KEY";
	
	public static final String FIRST_RECORD = "FIRST";
	public static final String PREV_RECORD = "PREV";
	public static final String NEXT_RECORD = "NEXT";
	public static final String LAST_RECORD = "LAST";
	
	public static final String FORMATOFECHA_ES = "dd/MM/yyyy";
	public static final String formatoFecha_YYYYMMdd = "yyyyMMdd";
	public static final String formatoFecha_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String PC = "PC";
	public static final String PDA = "PDA";
	
	// Continuar la operativa con lo que tuviera ya registrado. En este caso NO BORRAR datos.
	public static final String CONTINUAR="C";
	// Significa empezar desde cero. BORRAR datos.
	public static final String EMPEZAR="E";
	// Mostrar la página en la que se selecciona una opción; empezar desde cero o continuar.
	public static final String SELECCIONAR="S";
	public static final String VOLVER="V";
	
	// Sufijo que se añade a la columna MAC a la hora de registrar/consultar datos en la tabla T_MIS_PREHUECOS_LINEAL.
	public static final String PREHUECOS="_PREHUECOS";

	public static final String MOSTRAR_FFPP_S = "S";
	public static final String MOSTRAR_FFPP_N = "N";
	
	public static final String PEDIR_S = "S";
	public static final String PEDIR_N = "N";
	
	public static final String IMPLANTACION_VERDE = "VERDE";
	public static final String IMPLANTACION_ROJO = "ROJO";
	public static final String IMPLANTACION_AZUL = "AZUL";
	
	public static final String UNIDADES = "UNIDADES";
	public static final String CAJAS = "CAJAS";
	public static final String NUM_CAJAS_IC = "N\u00BA Cajas:";

	public static final String NUM_FAC_IC = "Fac.Lin:";
	public static final Long MAPA_5 = new Long(5);
	public static final Long MAPA_52 = new Long(52);
}