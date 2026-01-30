CREATE OR REPLACE TRIGGER UNIDINADM.D_T_SURTIDO_TIENDA
AFTER INSERT OR UPDATE
ON UNIDINADM.SURTIDO_TIENDA
FOR EACH ROW
DECLARE
  v_existe      NUMBER;
  v_existe_tex  NUMBER;
  v_flg_activa  t_mis_surtido_tienda.flg_activa%TYPE;
  v_uc_lot_txt  t_mis_surtido_tienda.uc_lot_txt%TYPE;
  v_desc_gama   t_mis_surtido_tienda.desc_gama%TYPE;
BEGIN   

   -- Bien sea insercion/actualizacion se actualizan los siguientes campos
   -- y se cargaran estos valores y no lo que venga de surtido_tienda:

   -- CAMPO: UC_LOT_TXT
   BEGIN
      SELECT 1 INTO v_existe_tex FROM v_referencias_lote_textil
      WHERE cod_articulo_lote = :NEW.cod_art;
   EXCEPTION
      WHEN others THEN
          v_existe_tex := 0;
   END;       
   
   IF v_existe_tex = 1 THEN
      v_uc_lot_txt := 1; -- Es un LOTE
   ELSE
     v_uc_lot_txt := :NEW.uni_caja_serv; -- = UNI_CAJA_SERV
   END IF;
   
   -- CAMPO: DESC_GAMA
   BEGIN
      SELECT descripcion INTO v_desc_gama FROM tipos_gama
      WHERE cod_tp_gama = :NEW.tipo_gama;
   exception
      WHEN others THEN
            v_desc_gama := NULL;
   END;   
   
   -- CAMPO: FLG_ACTIVA
    IF :NEW.tipo_aprov = 'D' THEN
        v_flg_activa := :NEW.pedir;
    ELSIF :NEW.tipo_aprov IN ('C','G') AND :NEW.pedir='S' AND pk_mis_carga_mapas.f_obtener_mapa(:NEW.cod_centro,:NEW.cod_art) = 'S' THEN
       v_flg_activa := 'S';
    ELSE
       v_flg_activa := 'N';
    END IF;     
    
   -- AHORA, COMPROBAMOS SI EL CENTRO/ARTICULO EXISTE EN T_MIS_SURTIDO_TIENDA
    BEGIN
         SELECT 1 INTO v_existe FROM t_mis_surtido_tienda tst
         WHERE tst.cod_centro = :NEW.cod_centro
           AND tst.cod_art = :NEW.cod_art;
    EXCEPTION 
       WHEN no_data_found THEN
         v_existe:= 0;
    END;   
   
    IF v_existe = 0 THEN -- Si el CENTRO/ARTICULO NO EXISTE en t_mis_surtido_tienda
      INSERT INTO MISUMIADM.t_mis_surtido_tienda
      (cod_centro	               ,cod_art	               ,uni_caja_serv	            ,tipo_aprov	          
      ,marca_maestro_centro	     ,catalogo	             ,pedir                     ,tipo_gama
      ,fecha_gen	               ,flg_estrategica        ,gama	                    ,stk_final_min_lun	  
      ,stk_final_min_mar	       ,stk_final_min_mie      ,stk_final_min_jue	        ,stk_final_min_vie	  
      ,stk_final_min_sab	       ,stk_final_min_dom	     ,capacidad	                ,uni_forma_ped	      
      ,redondeo	                 ,dia_max_stk            ,prod_rel	                ,flg_txl_pedible	    
      ,cod_art_caprabo           ,uc_lot_txt             ,desc_gama                 ,flg_activa)
      VALUES
      (:NEW.cod_centro	         ,:NEW.cod_art	         ,:NEW.uni_caja_serv	      ,:NEW.tipo_aprov	 
      ,:NEW.marca_maestro_centro ,:NEW.catalogo	         ,:NEW.pedir                ,:NEW.tipo_gama
      ,:NEW.fecha_gen	           ,:NEW.flg_estrategica   ,:NEW.gama	                ,:NEW.stk_final_min_lun	  
      ,:NEW.stk_final_min_mar	   ,:NEW.stk_final_min_mie ,:NEW.stk_final_min_jue	  ,:NEW.stk_final_min_vie	  
      ,:NEW.stk_final_min_sab	   ,:NEW.stk_final_min_dom ,:NEW.capacidad	          ,:NEW.uni_forma_ped	      
      ,:NEW.redondeo	           ,:NEW.dia_max_stk       ,:NEW.prod_rel	            ,:NEW.flg_txl_pedible	    
      ,:NEW.cod_art_caprabo      ,v_uc_lot_txt           ,v_desc_gama               ,v_flg_activa);
    ELSE  -- Si ya existe, se actualiza
       UPDATE MISUMIADM.t_mis_surtido_tienda  mst
       SET  uni_caja_serv        = :NEW.uni_caja_serv
          , tipo_aprov           = :NEW.tipo_aprov
          , marca_maestro_centro = :NEW.marca_maestro_centro 
          , catalogo             = :NEW.catalogo
          , pedir                = :NEW.pedir
          , tipo_gama            = :NEW.tipo_gama 
          , fecha_gen	           = :NEW.fecha_gen
          , flg_estrategica      = :NEW.flg_estrategica
          , gama	               = :NEW.gama	
          , stk_final_min_lun	   = :NEW.stk_final_min_lun
          , stk_final_min_mar	   = :NEW.stk_final_min_mar
          , stk_final_min_mie    = :NEW.stk_final_min_mie
          , stk_final_min_jue	   = :NEW.stk_final_min_jue
          , stk_final_min_vie	   = :NEW.stk_final_min_vie
          , stk_final_min_sab	   = :NEW.stk_final_min_sab	
          , stk_final_min_dom	   = :NEW.stk_final_min_dom
          , capacidad	           = :NEW.capacidad
          , uni_forma_ped	       = :NEW.uni_forma_ped
          , redondeo             = :NEW.redondeo
          , dia_max_stk          = :NEW.dia_max_stk
          , prod_rel	           = :NEW.prod_rel
          , flg_txl_pedible	     = :NEW.flg_txl_pedible
          , cod_art_caprabo      = :NEW.cod_art_caprabo
          , desc_gama            = v_desc_gama
          , uc_lot_txt           = v_uc_lot_txt
          , flg_activa           = v_flg_activa
          , last_update_date     = sysdate
       WHERE mst.cod_centro = :NEW.cod_centro
         AND mst.cod_art    = :NEW.cod_art
		 AND mst.fecha_gen <= :NEW.fecha_gen;  -- SIA-2604 ICE
    END IF; 
    
END;
/