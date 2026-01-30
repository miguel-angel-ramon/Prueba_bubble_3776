CREATE OR REPLACE TRIGGER MISUMIADM.TR_INS_DETALLADO_BEFORE BEFORE INSERT
ON MISUMIADM.T_DETALLADO_PEDIDO
REFERENCING NEW AS NEW OLD AS OLD FOR EACH ROW
DECLARE
  V_FECHA DATE;
  V_SIG   NUMBER;
BEGIN
  -- se calcula la fecha de entrega
  IF :NEW.FEC_ENTREGA IS NULL THEN
    SELECT CASE
             WHEN TO_CHAR(SYSDATE, 'd') = '1' THEN
             -- se pide el lunes
             -- TRUNC(SYSDATE) + R2.PED_LUN
              R2.PED_LUN
             WHEN TO_CHAR(SYSDATE, 'd') = '2' THEN
             -- se pide el martes
              R2.PED_MAR
             WHEN TO_CHAR(SYSDATE, 'd') = '3' THEN
             -- se pide el miercoles
              R2.PED_MIE
             WHEN TO_CHAR(SYSDATE, 'd') = '4' THEN
             -- se pide el jueves
              R2.PED_JUE
             WHEN TO_CHAR(SYSDATE, 'd') = 5 THEN
             -- se pide el viernes
              R2.PED_VIE
             WHEN TO_CHAR(SYSDATE, 'd') = 6 THEN
             -- se pide el sabado
              R2.PED_SAB
             ELSE
             -- se pide el domingo
              R2.PED_DOM
           END SIG_PEDIDO
      INTO V_SIG
      FROM V_REFERENCIA_ACTIVA2 R2
     WHERE :NEW.CENTRO = R2.COD_CENTRO
       AND :NEW.REFERENCIA = R2.COD_ART
       AND ROWNUM < 2;
       -- se calcula el siguiente día de entrega dependiendo de los festivos del centro
    IF V_SIG IS NOT NULL AND v_sig > 0 THEN
      SELECT MAX(FECHA_FESTIVO)
         INTO v_fecha
        FROM (SELECT FECHA_FESTIVO, ESTADO
                FROM FESTIVO_CENTRO F
               WHERE F.COD_CENTRO = :NEW.CENTRO
                 AND F.FECHA_FESTIVO > TRUNC(SYSDATE)
                 AND ESTADO = 'B'
                 AND FECHA_GEN =
                     (SELECT MAX(F2.FECHA_GEN)
                        FROM FESTIVO_CENTRO F2
                       WHERE F2.COD_CENTRO = F.COD_CENTRO
                         AND F2.FECHA_FESTIVO = F.FECHA_FESTIVO)
               ORDER BY FECHA_FESTIVO)
       WHERE ROWNUM < v_sig + 1;
    END IF;
  
    :NEW.FEC_ENTREGA := V_FECHA;
  END IF;
EXCEPTION
  WHEN OTHERS THEN
    NULL;
  
END;
/
-- Inicio MISUMI-393 ICE
-- Inicio SIA-2135 ICE
--ALTER TRIGGER MISUMIADM.TR_INS_DETALLADO_BEFORE DISABLE; 
-- Fin SIA-2135 ICE
ALTER TRIGGER MISUMIADM.TR_INS_DETALLADO_BEFORE ENABLE;
-- Fin MISUMI-393 ICE
/