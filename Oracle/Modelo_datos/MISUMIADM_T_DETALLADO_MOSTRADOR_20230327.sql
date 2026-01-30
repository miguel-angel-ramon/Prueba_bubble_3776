UPDATE MISUMIADM.t_detallado_mostrador
SET pdte_recibir_venta = NULL
WHERE 1=1;

ALTER TABLE MISUMIADM.T_DETALLADO_MOSTRADOR
MODIFY pdte_recibir_venta        NUMBER(15,3);
