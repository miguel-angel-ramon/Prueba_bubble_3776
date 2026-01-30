CREATE OR REPLACE VIEW V_MIS_OFER_MOSTRADOR
(cod_centro, tipo_mensaje, ano_oferta, num_oferta, fecha_ini, fecha_fin, tipo_oferta, cod_art, unid_cobro, unid_venta, pvp, fecha_gen, mecanica)
AS 
SELECT o.cod_centro, tipo_mensaje, ano_oferta, num_oferta, fecha_ini, fecha_fin, tipo_oferta, cod_art, unid_cobro, unid_venta, pvp/1000 pvp, fecha_gen
     , CASE WHEN tipo_oferta IN (2,7) AND unid_cobro > 100 then '2ªAl ' ||to_char(100 - (unid_cobro - 100) ) ||'%'
            WHEN tipo_oferta IN (2,7) THEN to_char(unid_venta) || 'X'|| to_char(unid_cobro) 
            ELSE (SELECT doftip
                  FROM t_sup_oftip
                  WHERE coftip = tipo_oferta
                 )
       END mecanica
FROM oferta o
WHERE o.fecha_gen IN (SELECT MAX(o2.fecha_gen)
                      FROM oferta o2
                      WHERE o2.cod_centro    = o.cod_centro
                      and o2.cod_art         = o.cod_art
                      and o2.ano_oferta      = o.ano_oferta
                      and o2.num_oferta      = o.num_oferta
                     )
AND fecha_ini >= trunc(SYSDATE-60)
AND fecha_fin BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE+365)
AND tipo_mensaje <>'B';
/
