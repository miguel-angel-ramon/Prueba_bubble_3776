CREATE OR REPLACE TRIGGER MISUMIADM.D_T_MIS_DETALLADO_VEGALSA
AFTER INSERT
ON MISUMIADM.T_MIS_DETALLADO_VEGALSA
FOR EACH ROW
DECLARE

BEGIN   

        --Si existe registro en T_MIS_DETALLADO_VEGALSA_MODIF para ese artículo, centro y fecha_pedido actualizamos la cantidad_propuesta y hora_limite
        UPDATE MISUMIADM.T_MIS_DETALLADO_VEGALSA_MODIF
           SET cantidad_propuesta   = :NEW.UNID_PROPUESTAS_FL_ORIGEN 
             , hora_limite          = :NEW.hora_limite
         WHERE cod_art              = :NEW.cod_art
           AND cod_centro           = :NEW.cod_centro
           AND fecha_pedido         = TRUNC(:NEW.fecha_pedido);


END;
/