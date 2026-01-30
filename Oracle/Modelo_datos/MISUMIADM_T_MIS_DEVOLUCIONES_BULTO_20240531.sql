DROP TABLE MISUMIADM.T_MIS_DEVOLUCIONES_BULTO;
DROP PUBLIC SYNONYM T_MIS_DEVOLUCIONES_BULTO;

CREATE TABLE MISUMIADM.T_MIS_DEVOLUCIONES_BULTO
    ( idsesion              VARCHAR2(100 BYTE) NOT NULL
    , fecha_gen             DATE DEFAULT SYSDATE NOT NULL
    , devolucion            NUMBER(12,0) NOT NULL
    , bulto                 NUMBER(2,0) NOT NULL
	, cod_articulo          NUMBER(12,0) NOT NULL
    , bulto_ori             NUMBER(2,0)
    , stock_devuelto        NUMBER(15,3)
    , stock_devuelto_ori    NUMBER(15,3)
    , cod_error             NUMBER(5,0)
    , desc_error            VARCHAR2(3200 BYTE)
    , estado                VARCHAR2(1)
    , stock_devuelto_bandejas NUMBER(15)
    , creation_date         DATE DEFAULT SYSDATE NOT NULL
    , CONSTRAINT DBU_PK PRIMARY KEY (idsesion, fecha_gen, devolucion, bulto, cod_articulo)
   );

COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.idsesion IS 'Identificador de sesión que se conecta.'; 
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.fecha_gen IS 'Fecha generación .'; 
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.devolucion IS 'Código de devolución.'; 
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.bulto IS 'Bulto introducido por el centro.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.cod_articulo IS 'Código de artículo.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.bulto_ori IS 'Bulto original.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.stock_devuelto_ori IS 'Stock devuelto por el centro ORIGINAL.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.stock_devuelto IS 'Stock devuelto por el centro y modificado por el centro.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.cod_error IS 'Código de error.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.desc_error IS 'Descripción del error.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.estado IS 'Estado del bulto. Podrá tener valores "S" o "N".';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.stock_devuelto_bandejas IS 'Stock devuelto en bandejas.';
COMMENT ON COLUMN misumiadm.t_mis_devoluciones_bulto.creation_date IS 'Fecha de creacion del registro.';

CREATE INDEX MISUMIADM.DBU_I ON MISUMIADM.T_MIS_DEVOLUCIONES_BULTO(fecha_gen);

CREATE PUBLIC SYNONYM T_MIS_DEVOLUCIONES_BULTO FOR MISUMIADM.T_MIS_DEVOLUCIONES_BULTO;

GRANT DELETE, INSERT, UPDATE ON T_MIS_DEVOLUCIONES_BULTO TO MISUMIUSR;
