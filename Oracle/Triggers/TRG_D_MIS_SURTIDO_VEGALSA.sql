create or replace TRIGGER MISUMIADM.D_MIS_SURTIDO_VEGALSA 
AFTER INSERT OR UPDATE
ON MISUMIADM.T_MIS_SURTIDO_VEGALSA
FOR EACH ROW
DECLARE
  v_existe      NUMBER;
  v_reg			NUMBER;
  v_existe_tex  NUMBER;
  v_pedir       VARCHAR2(1);
  v_uc_lot_txt  t_mis_surtido_tienda.uc_lot_txt%TYPE;
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

   IF :NEW.marca_maestro_centro = 'S' THEN
        v_pedir := 'S';
    ELSE
        v_pedir := 'N';
    END IF;


   IF v_existe_tex = 1 THEN
      v_uc_lot_txt := 1; -- Es un LOTE
   ELSE
     v_uc_lot_txt := :NEW.uc;--:NEW.uni_caja_serv; -- = UNI_CAJA_SERV
   END IF;

   -- AHORA, COMPROBAMOS SI EL CENTRO/ARTICULO EXISTE EN T_MIS_SURTIDO_TIENDA
    BEGIN
         SELECT DISTINCT(1) INTO v_existe FROM v_referencias_pedir_sia tst
         WHERE tst.cod_centro = :NEW.cod_centro
           AND tst.cod_art = :NEW.cod_art;
    EXCEPTION
       WHEN no_data_found THEN
         v_existe:= 0;
    END;

    IF v_existe = 0 THEN -- Si el CENTRO/ARTICULO NO EXISTE en t_mis_surtido_tienda
	  BEGIN
		SELECT DISTINCT(1) INTO v_reg FROM t_mis_surtido_tienda tst
         WHERE tst.cod_centro = :NEW.cod_centro
           AND tst.cod_art = :NEW.cod_art;
        EXCEPTION
       WHEN no_data_found THEN
         v_reg:= 0;
            END;
	
		  IF v_reg = 0 THEN
		  
		  INSERT INTO MISUMIADM.t_mis_surtido_tienda
		  (cod_centro,		cod_art,		uni_caja_serv         
		  ,uni_forma_ped,		tipo_aprov,		marca_maestro_centro,		catalogo
		  ,fecha_gen,gama,		 flg_estrategica,		flg_txl_pedible,		pedir
		  ,flg_activa,		uc_lot_txt,		last_update_date,		creation_date)
		  VALUES
		  (:NEW.cod_centro	         ,:NEW.cod_art	         ,:NEW.uc	      ,:NEW.ufp
		  ,:NEW.tipo_aprov            ,:NEW.marca_maestro_centro	            ,:NEW.catalogo_cen  ,:NEW.fecha_gen
		  ,:NEW.marca_maestro_centro	           ,'N'   ,'N'  ,v_pedir	 ,:NEW.marca_maestro_centro
		  ,v_uc_lot_txt	           ,SYSDATE, SYSDATE);
	  
			ELSE
			
		 UPDATE MISUMIADM.t_mis_surtido_tienda  mst
		   SET  uni_caja_serv       	 = :NEW.uc
			  , uni_forma_ped 			 = :NEW.ufp
			  , tipo_aprov             	 = :NEW.tipo_aprov
			  , marca_maestro_centro 	 = :NEW.marca_maestro_centro
			  , catalogo           		 = :NEW.catalogo_cen
			  , fecha_gen	          	 = :NEW.fecha_gen
			  , gama      				 = :NEW.marca_maestro_centro
			  , flg_estrategica	       	 = 'N'
			  , flg_txl_pedible	      	 = 'N'
			  , pedir	       			 = v_pedir
			  , flg_activa            	 = :NEW.marca_maestro_centro
			  , uc_lot_txt         		 = v_uc_lot_txt
			  , last_update_date	     = SYSDATE
		   WHERE mst.cod_centro = :NEW.cod_centro
			 AND mst.cod_art    = :NEW.cod_art
			 AND mst.fecha_gen <= :NEW.fecha_gen;  -- SIA-2604 ICE
	  
			END IF;
	END IF;  
	  END;
	  /