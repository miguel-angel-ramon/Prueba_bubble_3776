CREATE OR REPLACE FORCE EDITIONABLE VIEW V_MIS_DETALLADO_MOSTRADOR
    ( nivel, ident, parentid, id_sesion, centro, area, denom_area, seccion, denom_seccion, categoria, denom_categoria, subcategoria
    , denom_subcategoria, segmento, denom_segmento, seccion_grid, referencia, descripcion, referencia_eroski, descripcion_eroski
    , fecha_transmision, fecha_venta, unidades_caja, precio_costo_articulo, precio_costo_inicial, precio_costo_final
    , euros_pvp_inicial, euros_pvp_final, cod_necesidad
    , fecha_sgte_transmision, dias_cubre_pedido, tipo_gama, pdte_recibir_manana
    , empuje_pdte_recibir, pdte_recibir_venta, marca_compra, marca_venta, referencia_compra, pvp_tarifa, iva, tirado, tirado_parasitos
    , prevision_venta, propuesta_pedido, propuesta_pedido_ant, redondeo_propuesta
    , total_ventas_espejo, total_ventas_espejo_tarifa
    , total_ventas_espejo_oferta, tot_importe_ventas_espejo, multiplicador_ventas, orden, oferta_ab, oferta_cd, oferta_ab_inicio
    , oferta_ab_fin, oferta_cd_inicio, oferta_cd_fin, pvp, dia_espejo, hora_limite, estado, estado_grid, descripcion_error
    , flg_sia, margen, tiene_ventas, pdte_recibir_venta_grid
    ) AS 
  SELECT nivel, ident, parentid, id_sesion, centro, area, denom_area, seccion, denom_seccion, categoria, denom_categoria, subcategoria
       , denom_subcategoria, segmento, denom_segmento, seccion_grid, referencia, descripcion, referencia_eroski, descripcion_eroski
       , fecha_transmision, fecha_venta, unidades_caja, precio_costo_articulo, precio_costo_inicial, precio_costo_final
       , ROUND(pvp * prevision_venta, 3) euros_pvp_inicial, ROUND(pvp * propuesta_pedido, 3) euros_pvp_final, cod_necesidad
       , fecha_sgte_transmision, dias_cubre_pedido, tipo_gama, pdte_recibir_manana
       , CASE WHEN empuje_pdte_recibir = 'S' THEN TO_CHAR(pdte_recibir_manana)||'E'
              WHEN pdte_recibir_manana <> 0 THEN TO_CHAR(pdte_recibir_manana) 
         END empuje_pdte_recibir, pdte_recibir_venta, marca_compra, marca_venta, referencia_compra, pvp_tarifa, iva, tirado, tirado_parasitos
       , prevision_venta, propuesta_pedido, propuesta_pedido_ant, redondeo_propuesta 
        -- inicio 22/03/2023 en el caso NIVEL 1 calcular las ventas espejo en cajas
       , CASE WHEN NIVEL = 1 AND UNIDADES_CAJA >0 AND TOTAL_VENTAS_ESPEJO > 0 THEN ROUND(TOTAL_VENTAS_ESPEJO/UNIDADES_CAJA,2)
         ELSE TOTAL_VENTAS_ESPEJO -- fin 22/03/2023 
         END TOTAL_VENTAS_ESPEJO
        --MISUMI-464 ABirdu INICIO 
       , CASE WHEN NIVEL=1 AND UNIDADES_CAJA >0 AND TOTAL_VENTAS_ESPEJO_TARIFA > 0 THEN ROUND(TOTAL_VENTAS_ESPEJO_TARIFA/UNIDADES_CAJA,2)
         ELSE TOTAL_VENTAS_ESPEJO_TARIFA
         END TOTAL_VENTAS_ESPEJO_TARIFA
       , CASE WHEN NIVEL=1 AND UNIDADES_CAJA >0 AND TOTAL_VENTAS_ESPEJO_OFERTA > 0 THEN ROUND(TOTAL_VENTAS_ESPEJO_OFERTA/UNIDADES_CAJA,2)
         ELSE TOTAL_VENTAS_ESPEJO_OFERTA
         END TOTAL_VENTAS_ESPEJO_OFERTA
        --MISUMI-464 ABirdu FIN
       , TOT_IMPORTE_VENTAS_ESPEJO, MULTIPLICADOR_VENTAS, ORDEN 
        -- calculamos la fecha inicio y fecha fin oferta ab
       , SUBSTR(DECODE(TO_CHAR(OFERTA_AB_INICIO,'D'),'3','X',TO_CHAR(OFERTA_AB_INICIO,'DY')),1,1)|| to_char(OFERTA_AB_INICIO,' dd/mm-')
         ||SUBSTR(DECODE(TO_CHAR(OFERTA_AB_FIN,'D'),'3','X',TO_CHAR(OFERTA_AB_FIN,'DY')),1,1)|| to_char(OFERTA_AB_FIN,' dd/mm') oferta_ab 
        -- calculamos la fecha inicio y fecha fin oferta cd
       , SUBSTR(DECODE(TO_CHAR(OFERTA_cd_INICIO,'D'),'3','X',TO_CHAR(OFERTA_cd_INICIO,'DY')),1,1)|| to_char(OFERTA_cd_INICIO,' dd/mm-')||
         SUBSTR(DECODE(TO_CHAR(OFERTA_cd_FIN,'D'),'3','X',TO_CHAR(OFERTA_cd_FIN,'DY')),1,1)|| to_char(OFERTA_cd_FIN,' dd/mm') oferta_cd
       , oferta_ab_inicio, oferta_ab_fin, oferta_cd_inicio, oferta_cd_fin, pvp, dia_espejo, hora_limite, estado, estado_grid, descripcion_error, flg_sia
       , CASE WHEN NVL(PVP,0)=0 or nvl(iva,0)=0 THEN 0
         ELSE ROUND((((NVL(PVP,0) /(1 +(IVA / 100)) - NVL(PRECIO_COSTO_ARTICULO,0)) / NVL(PVP,0)) * 100),2)
         END margen
       , (select 'S' from t_detallado_mostrador t where t.id_sesion = a.id_sesion and t.referencia_compra=a.referencia_compra and t.marca_compra='N' AND ROWNUM<2) TIENE_VENTAS
       , CASE WHEN nvl(PDTE_RECIBIR_VENTA,0) > 0 AND redondeo_propuesta = 'S' THEN 'R '||PDTE_RECIBIR_VENTA||'E'
              WHEN nvl(PDTE_RECIBIR_VENTA,0) > 0 AND redondeo_propuesta = 'N' THEN PDTE_RECIBIR_VENTA||'E'
              WHEN nvl(PDTE_RECIBIR_VENTA,0) = 0 AND redondeo_propuesta = 'S' THEN 'R'
         ELSE NULL
         END pdte_recibir_venta_grid 
  FROM (-- 22/03/2023 eliminar el nivel 0 ya no es necesario
    /*        select 0 NIVEL, TO_NUMBER(LPAD(AREA,2,'0')||
                        LPAD(SECCION,2,'0')||LPAD(CATEGORIA,2,'0')||LPAD(SUBCATEGORIA,2,'0') )*10
                        IDENT, 0 PARENTID, ID_SESION, CENTRO, 
                        AREA   , DENOM_AREA ,
                       SECCION             , DENOM_SECCION        , CATEGORIA     , DENOM_CATEGORIA  ,  SUBCATEGORIA, DENOM_SUBCATEGORIA, 
                       null segmento, null denom_segmento,
                       SUBCATEGORIA||'-'||DENOM_SUBCATEGORIA SECCION_GRID,
                       0 REFERENCIA, null   DESCRIPCION ,
                       SUBCATEGORIA REFERENCIA_EROSKI, DENOM_SUBCATEGORIA DESCRIPCION_EROSKI, 
                        null    FECHA_TRANSMISION   , null  FECHA_VENTA , null  UNIDADES_CAJA   , null  PRECIO_COSTO_ARTICULO   ,
                        null    PRECIO_COSTO_INICIAL    , null  PRECIO_COSTO_FINAL  , 
                        NULL    EUROS_PVP_INICIAL,  NULL EUROS_PVP_FINAL,
                        null    COD_NECESIDAD   ,
                        null    FECHA_SGTE_TRANSMISION  , null  DIAS_CUBRE_PEDIDO   , null  TIPO_GAMA   ,
                        null    PDTE_RECIBIR_MANANA , null  EMPUJE_PDTE_RECIBIR ,null   PDTE_RECIBIR_VENTA  ,
                        null    MARCA_COMPRA    , null  MARCA_VENTA ,0  REFERENCIA_COMPRA   ,
                        null    PVP_TARIFA  , null  IVA ,null   TIRADO  , null  TIRADO_PARASITOS    ,null   PREVISION_VENTA ,
                        null    PROPUESTA_PEDIDO    ,null   PROPUESTA_PEDIDO_ANT    ,null   REDONDEO_PROPUESTA  ,
                        null    TOTAL_VENTAS_ESPEJO ,null   TOT_IMPORTE_VENTAS_ESPEJO   ,null   MULTIPLICADOR_VENTAS    ,
                        null    ORDEN   ,null   OFERTA_AB_INICIO    ,null   OFERTA_AB_FIN   ,null   OFERTA_CD_INICIO    ,
                        null    OFERTA_CD_FIN   ,null   PVP ,
                        null    DIA_ESPEJO  , NULL  HORA_LIMITE , null  ESTADO  ,
                        null    ESTADO_GRID , null  DESCRIPCION_ERROR   , FLG_SIA, NULL MARGEN
        from T_DETALLADO_MOSTRADOR
        GROUP BY  AREA             , DENOM_AREA
                , SECCION             , DENOM_SECCION        , CATEGORIA     , DENOM_CATEGORIA, SUBCATEGORIA, DENOM_SUBCATEGORIA, ID_SESION, CENTRO, FLG_SIA
UNION */
        SELECT CASE WHEN marca_compra = 'N' THEN 2 ELSE 1 END nivel
             , TO_NUMBER(LPAD(area,2,'0')||LPAD(SECCION,2,'0')||LPAD(CATEGORIA,2,'0')||LPAD(SUBCATEGORIA,2,'0')
                         ||referencia_compra
                         ||CASE WHEN marca_compra = 'N' THEN REFERENCIA ELSE 0 END) IDENT
             , TO_NUMBER(LPAD(AREA,2,'0')||LPAD(SECCION,2,'0')||LPAD(CATEGORIA,2,'0')||LPAD(SUBCATEGORIA,2,'0')
                         ||CASE WHEN MARCA_COMPRA = 'N' THEN REFERENCIA_COMPRA*10 ELSE 0 END) PARENTID
             , ID_SESION, CENTRO, AREA, DENOM_AREA, SECCION , DENOM_SECCION  , CATEGORIA, DENOM_CATEGORIA ,  SUBCATEGORIA, DENOM_SUBCATEGORIA, segmento, denom_segmento,
                        SUBCATEGORIA||'-'||DENOM_SUBCATEGORIA SECCION_GRID,
                       REFERENCIA, DESCRIPCION  , REFERENCIA REFERENCIA_EROSKI, DESCRIPCION   DESCRIPCION_EROSKI,
                       FECHA_TRANSMISION    , FECHA_VENTA   , UNIDADES_CAJA , 
                       CASE WHEN MARCA_COMPRA = 'N' THEN
                                (SELECT T2.PRECIO_COSTO_ARTICULO * T2.MULTIPLICADOR_VENTAS
                                 FROM T_DETALLADO_MOSTRADOR T2
                                 WHERE T2.ID_SESION = T.ID_SESION
                                   AND T2.REFERENCIA_COMPRA = T.REFERENCIA
                                   AND T2.MARCA_COMPRA = 'S')
                            ELSE PRECIO_COSTO_ARTICULO  END PRECIO_COSTO_ARTICULO,
                       round(PRECIO_COSTO_ARTICULO * PREVISION_VENTA,3) PRECIO_COSTO_INICIAL    , 
                       round(PRECIO_COSTO_ARTICULO * PROPUESTA_PEDIDO,3)PRECIO_COSTO_FINAL  , 
                       round(PVP * PREVISION_VENTA,3) EUROS_PVP_INICIAL, 
                       round(PVP * PROPUESTA_PEDIDO,3) EUROS_PVP_FINAL,
                       COD_NECESIDAD    ,
                       FECHA_SGTE_TRANSMISION   , DIAS_CUBRE_PEDIDO , TIPO_GAMA , 
                       CASE WHEN PDTE_RECIBIR_MANANA > 0 AND UNIDADES_CAJA > 0 THEN ROUND(PDTE_RECIBIR_MANANA/ UNIDADES_CAJA,2) 
                            ELSE PDTE_RECIBIR_MANANA  END PDTE_RECIBIR_MANANA,
                       EMPUJE_PDTE_RECIBIR  , 
                       CASE WHEN PDTE_RECIBIR_VENTA > 0 AND UNIDADES_CAJA > 0 THEN ROUND(PDTE_RECIBIR_VENTA/ UNIDADES_CAJA,2) 
                            ELSE PDTE_RECIBIR_VENTA  END PDTE_RECIBIR_VENTA,
                       MARCA_COMPRA , MARCA_VENTA   , REFERENCIA_COMPRA , PVP_TARIFA    , IVA   ,
                       TIRADO, TIRADO_PARASITOS  , PREVISION_VENTA   , PROPUESTA_PEDIDO  , PROPUESTA_PEDIDO_ANT  ,
                       REDONDEO_PROPUESTA   , 
                       CASE WHEN MARCA_COMPRA = 'S' THEN 
                                TOTAL_VENTAS_ESPEJO +
                              NVL((SELECT SUM(NVL(t2.TOTAL_VENTAS_ESPEJO * T2.MULTIPLICADOR_VENTAS ,0))
                                 FROM T_DETALLADO_MOSTRADOR T2
                                 WHERE T2.ID_SESION = T.ID_SESION
                                   AND T2.REFERENCIA_COMPRA = T.REFERENCIA
                                   AND T2.MARCA_COMPRA = 'N'),0) -- CAMBIO 14/03/2023
                            ELSE TOTAL_VENTAS_ESPEJO END  TOTAL_VENTAS_ESPEJO   , 
                       --MISUMI-464 ABirdu  INICIO
                       CASE WHEN MARCA_COMPRA = 'S' THEN 
                                TOTAL_VENTAS_ESPEJO_TARIFA +
                              NVL((SELECT SUM(NVL(t2.TOTAL_VENTAS_ESPEJO_TARIFA * T2.MULTIPLICADOR_VENTAS ,0))
                                 FROM T_DETALLADO_MOSTRADOR T2
                                 WHERE T2.ID_SESION = T.ID_SESION
                                   AND T2.REFERENCIA_COMPRA = T.REFERENCIA
                                   AND T2.MARCA_COMPRA = 'N'),0) 
                            ELSE TOTAL_VENTAS_ESPEJO_TARIFA END  TOTAL_VENTAS_ESPEJO_TARIFA   , 
                       CASE WHEN MARCA_COMPRA = 'S' THEN 
                                TOTAL_VENTAS_ESPEJO_OFERTA +
                              NVL((SELECT SUM(NVL(t2.TOTAL_VENTAS_ESPEJO_OFERTA * T2.MULTIPLICADOR_VENTAS ,0))
                                 FROM T_DETALLADO_MOSTRADOR T2
                                 WHERE T2.ID_SESION = T.ID_SESION
                                   AND T2.REFERENCIA_COMPRA = T.REFERENCIA
                                   AND T2.MARCA_COMPRA = 'N'),0) 
                            ELSE TOTAL_VENTAS_ESPEJO_OFERTA END  TOTAL_VENTAS_ESPEJO_OFERTA   ,                             
                       --MISUMI-464 ABirdu  FIN
                       CASE WHEN MARCA_COMPRA = 'S' THEN 
                                TOT_IMPORTE_VENTAS_ESPEJO +
                              NVL((SELECT SUM(NVL(t2.TOT_IMPORTE_VENTAS_ESPEJO ,0))
                                 FROM T_DETALLADO_MOSTRADOR T2
                                 WHERE T2.ID_SESION = T.ID_SESION
                                   AND T2.REFERENCIA_COMPRA = T.REFERENCIA
                                   AND T2.MARCA_COMPRA = 'N'),0) -- CAMBIO 14/03/2023
                            ELSE TOT_IMPORTE_VENTAS_ESPEJO END  
                       TOT_IMPORTE_VENTAS_ESPEJO    , MULTIPLICADOR_VENTAS  ,
                       ORDEN, 
----------------------------------------------------------------
                       ----- oferta AB son las que estÃ¡n de oferta hoy o empiezan maÃ±ana
                       -- en e caso de varias me quedo con la fecha de inicio menor
                       -- SE OBTIENE LA FECHA INICIO AB
                       (select MAX(O.FECHA_INI)
                       from v_mis_ofer_mostrador o
                       where O.cod_centro=T.CENTRO
                       and O.cod_art=T.REFERENCIA
                       and O.fecha_fin >= trunc(sysdate)
                       and O.fecha_ini <= trunc(sysdate+1)
                       AND FECHA_INI IN (SELECT MAX(FECHA_INI) FROM V_MIS_OFER_MOSTRADOR O2
                                         WHERE O.COD_CENTRO=O2.COD_CENTRO
                                          AND O.COD_ART = O2.COD_ART
                                          AND O2.fecha_fin >= trunc(sysdate)
                                          AND O2.fecha_ini <= trunc(sysdate+1)
                                        )
                       ) OFERTA_AB_INICIO
                       -- SE OBTIENE LA FECHA FIN AB
                       , (select MAX(O.FECHA_FIN)
                       from v_mis_ofer_mostrador o
                       where O.cod_centro=T.CENTRO
                       and O.cod_art=T.REFERENCIA
                       and O.fecha_fin >= trunc(sysdate)
                       and O.fecha_ini <= trunc(sysdate+1)
                       AND FECHA_INI IN (SELECT MAX(FECHA_INI) FROM V_MIS_OFER_MOSTRADOR O2
                                         WHERE O.COD_CENTRO=O2.COD_CENTRO
                                          AND O.COD_ART = O2.COD_ART
                                          AND O2.fecha_fin >= trunc(sysdate)
                                          AND O2.fecha_ini <= trunc(sysdate+1))) OFERTA_AB_FIN  , 
 --------------------------------------------------------------------------------                                         
                      -- SE OBTIENE LA FECHA INICIO CD
                       (select MIN(O.FECHA_INI)
                       from v_mis_ofer_mostrador o
                       where O.cod_centro=T.CENTRO
                       and O.cod_art=T.REFERENCIA
                       and O.fecha_fin >= trunc(sysdate+2)
                       and O.fecha_ini between trunc(sysdate+2) and FECHA_VENTA+1
                       AND FECHA_INI IN (SELECT MIN(FECHA_INI) FROM V_MIS_OFER_MOSTRADOR O2
                                         WHERE O.COD_CENTRO=O2.COD_CENTRO
                                          AND O.COD_ART = O2.COD_ART
                                          AND O2.fecha_fin >= trunc(sysdate+2)
                                          AND O2.fecha_ini between trunc(sysdate+2) and 
                                                 NVL(FECHA_VENTA+1, TRUNC(SYSDATE)+4) -- CAMBIO 14/03/2023
                                          )
                                      ) OFERTA_CD_INICIO   , 
                       (select MIN(O.FECHA_FIN)
                       from v_mis_ofer_mostrador o
                       where O.cod_centro=T.CENTRO
                       and O.cod_art=T.REFERENCIA
                       and O.fecha_fin >= trunc(sysdate+2)
                       and O.fecha_ini  between trunc(sysdate+2) and FECHA_VENTA+1
                       AND FECHA_INI IN (SELECT MIN(FECHA_INI) FROM V_MIS_OFER_MOSTRADOR O2
                                         WHERE O.COD_CENTRO=O2.COD_CENTRO
                                          AND O.COD_ART = O2.COD_ART
                                          AND O2.fecha_fin >= trunc(sysdate+2)
                                          AND O2.fecha_ini  between trunc(sysdate+2) and 
                                                 NVL(FECHA_VENTA+1, TRUNC(SYSDATE)+4) -- CAMBIO 14/03/2023
                                          )
                                     ) OFERTA_CD_FIN   ,
                       NVL((select PVP
                       from v_mis_ofer_mostrador o
                       where O.cod_centro=T.CENTRO
                       and O.cod_art=T.REFERENCIA
                       and NVL(FECHA_VENTA, TRUNC(SYSDATE)+4) BETWEEN O.fecha_INI AND O.FECHA_FIN -- CAMBIO 14/03/2023
                       AND FECHA_INI IN (SELECT MIN(FECHA_INI) FROM V_MIS_OFER_MOSTRADOR O2
                                         WHERE O.COD_CENTRO=O2.COD_CENTRO
                                          AND O.COD_ART = O2.COD_ART
                                          AND NVL(FECHA_VENTA, TRUNC(SYSDATE)+4) 
                                          BETWEEN O2.fecha_INI AND O2.FECHA_FIN -- MISUMI-532. ECG. 17.04.2024
                                        )
                        AND ROWNUM <2                                                  
                                                  ), t.pvp_tarifa)
                       PVP  ,  DIA_ESPEJO   , HORA_LIMITE   , ESTADO    , ESTADO_GRID   , DESCRIPCION_ERROR , FLG_SIA, 
                        CASE WHEN NVL(PVP,0)=0 or nvl(iva,0)=0 THEN 0
                         ELSE
                           round((((NVL(PVP,0) /(1 +(IVA / 100)) - NVL(PRECIO_COSTO_ARTICULO,0)) / NVL(PVP,0)) * 100),2)
                        END MARGEN
         FROM t_detallado_mostrador t
) a;
/