DROP TABLE MISUMIADM.T_DETALLADO_MOSTRADOR;

CREATE TABLE MISUMIADM.T_DETALLADO_MOSTRADOR
( id_sesion                 VARCHAR2(100)
, centro                    NUMBER(4)
, area                      NUMBER(2)
, denom_area                VARCHAR2(200)
, seccion                   NUMBER(2)
, denom_seccion             VARCHAR2(200)
, categoria                 NUMBER(2)
, denom_categoria           VARCHAR2(200)
, subcategoria              NUMBER(2)
, denom_subcategoria        VARCHAR2(200)
, segmento                  NUMBER(2)
, denom_segmento            VARCHAR2(200)
, referencia                NUMBER(12)
, descripcion               VARCHAR2(50)
, referencia_eroski         NUMBER(12)
, descripcion_eroski        VARCHAR2(50)
, fecha_transmision         DATE
, fecha_venta               DATE
, unidades_caja             NUMBER
, precio_costo_articulo     NUMBER
, cod_necesidad             NUMBER(12)
, fecha_sgte_transmision    DATE
, dias_cubre_pedido         NUMBER(3)
, tipo_gama                 VARCHAR2(50)
, pdte_recibir_manana       NUMBER
, empuje_pdte_recibir       VARCHAR2(1)
, pdte_recibir_venta        NUMBER(13)
, marca_compra              VARCHAR2(1)
, marca_venta               VARCHAR2(1)
, referencia_compra         NUMBER(12)
, pvp_tarifa                NUMBER
, iva                       NUMBER
, tirado                    NUMBER
, tirado_parasitos          NUMBER
, prevision_venta           NUMBER
, propuesta_pedido          NUMBER -- Cantidad a pedir.
, propuesta_pedido_ant      NUMBER
, redondeo_propuesta        VARCHAR2(1)
, total_ventas_espejo       NUMBER
, tot_importe_ventas_espejo NUMBER
, multiplicador_ventas      NUMBER
, orden                     NUMBER(4)
, oferta_ab_inicio          DATE
, oferta_ab_fin             DATE
, oferta_cd_inicio          DATE
, oferta_cd_fin             DATE
, pvp                       NUMBER
, nivel                     NUMBER(1)
, dia_espejo                DATE
, hora_limite               VARCHAR2(50 BYTE)
, estado                    VARCHAR2(1 BYTE)
, estado_grid               NUMBER
, descripcion_error         VARCHAR2(300)
, flg_sia                   VARCHAR2(1) DEFAULT 'S' NOT NULL
, fecha_creacion            DATE   DEFAULT SYSDATE NOT NULL
, last_update_date          DATE   DEFAULT SYSDATE NOT NULL
);

COMMENT ON COLUMN misumiadm.t_detallado_mostrador.id_sesion IS 'Identificador de sesion en Java.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.centro IS 'Codigo de centro.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.area IS 'Codigo de Area.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.denom_area IS 'Denominacion area.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.seccion IS 'Codigo de seccion';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.denom_seccion IS 'Denominacion seccion';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.categoria IS 'Codigo de CATEGORIA';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.denom_categoria IS 'Denominación Categoria';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.subcategoria IS 'Codigo de SUBCATEGORIA';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.denom_subcategoria IS 'Denominacion Subcategoria';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.segmento IS 'Codigo de Segmento';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.denom_segmento IS 'Denominiacion Segmento';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.referencia IS 'Se trata de la referencia tienda (cod_art).';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.descripcion IS 'Descripcion de la referencia';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.FECHA_TRANSMISION IS 'Fecha de transmision';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.FECHA_VENTA IS 'Sera la Fecha en que el centro puede vender la mercancia de la transmision de hoy';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.UNIDADES_CAJA IS 'UNIDADES CAJA';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.precio_costo_articulo IS 'Precio Costo del articulo';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.FECHA_SGTE_TRANSMISION IS 'Fecha en la que se realizara la siguiente transmision de pedido';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.DIAS_CUBRE_PEDIDO IS 'Dia que cubre este pedido';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.TIPO_GAMA IS 'Tipo de gama';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.PDTE_RECIBIR_MANANA IS 'Cantidad pendiente de recibir mañana';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.EMPUJE_PDTE_RECIBIR IS 'Marca que indica si dentro del pendiente de recibir mañana existe un empuje';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.PDTE_RECIBIR_VENTA IS 'Pendiente de recibir a Fecha de Venta. Todos los que tengan esto aparecera son EMPUJES';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.marca_compra IS 'Indicador para saber si es una referencia de compra. Si valor "S" se trata de una referencia de compra o compra/venta.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.marca_venta IS 'Indicador para saber si es una referencia de venta. Si valor "N" en marca_compra y aqui valor "S" se trata de una venta.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.referencia_compra IS 'Referencia de compra en el caso de que se trate una referencia SOLO VENTA se indicará a que referencia de compra pertenece';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.pvp_tarifa IS 'Precio tarifa de la referencia';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.iva IS 'IVA';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.tirado IS 'Se trata de un porcentaje de tirado.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.tirado_parasitos IS 'Se trata de un porcentaje de tirado por parasitos.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.PREVISION_VENTA IS 'Prevision de venta calculado desde Aprovisionamiento';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.PROPUESTA_PEDIDO IS 'Cantidad a pedir. UNICO CAMPO MODIFICABLE';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.REDONDEO_PROPUESTA IS 'Indicador de si la propuesta de pedido es la prevision de venta y esta redondeada';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.TOTAL_VENTAS_ESPEJO IS 'Total de ventas realizadas en el dia espejo';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.TOT_IMPORTE_VENTAS_ESPEJO IS 'total de importe de ventas realizadas en el dia espejo';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.MULTIPLICADOR_VENTAS IS 'Multiplicador de ventas solo en el caso de ser referencias SOLO VENTA';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.ORDEN IS 'Orden en el que se tienen que mostrar las referencias, primero sera en orden de estructura comercial y luego a nivel de este orden';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.OFERTA_AB_INICIO IS 'Fecha inicio de oferta A_B';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.OFERTA_AB_FIN IS 'Fecha Fin de oferta A_B';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.OFERTA_CD_INICIO IS 'Fecha inicio de oferta CD';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.OFERTA_CD_FIN IS 'Fecha Fin de oferta CD';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.PVP IS 'Será el PVP resultante de la referencia Día que cubre este pedido';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.NIVEL IS 'Nivel a mostrar la referencia. Nivel 1 referencias compra/compra venta. Nivel 2 Referencias SOLO VENTA';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.DIA_ESPEJO IS 'Dia del que se toma referencia para proponer los datos del detallado.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.hora_limite IS 'Hora de fin de la bomba en MISUMI.';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.ESTADO IS 'Estado en la que se encuentra el PEDIDO. P-Pendiente(negro). R-Revisada (verde). B-Bloqueada (fucsia). I.Integrada (rojo). Solo se podra modificar la cantidad cuando estan en estado P y R';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.ESTADO_GRID IS 'Estado en el que se encuentra el registro. Si esta vacio es que NO se ha modificado. 1- NO AFECTA . 2-Modificado lapiz. 3. Guardado Correctamente. 4- No afecta. 5.- Pone R y Modificado lapiz. <0- error. En estos casos se mostrara el mensaje de error posicionandote encima del icono con un TOOLTIP';
COMMENT ON COLUMN misumiadm.t_detallado_mostrador.DESCRIPCION_ERROR IS 'Descripcion del Error. Este se visualizara si el campo ESTADO_GRID < 0';

DROP INDEX misumiadm.t_detallado_mostrador_uk;
CREATE UNIQUE INDEX misumiadm.t_detallado_mostrador_uk ON misumiadm.t_detallado_mostrador
  (id_sesion, centro, referencia, referencia_compra);


DROP INDEX misumiadm.t_detallado_mostrador_uk;
CREATE INDEX misumiadm.t_detallado_mostrador_idx ON misumiadm.t_detallado_mostrador
  (id_sesion, centro, area, seccion, categoria, subcategoria);

DROP PUBLIC SYNONYM t_detallado_mostrador;

CREATE PUBLIC SYNONYM t_detallado_mostrador FOR misumiadm.t_detallado_mostrador;

GRANT DELETE, INSERT, UPDATE ON t_detallado_mostrador TO misumiusr;



