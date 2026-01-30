package es.eroski.misumi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.InventarioRotativoDao;
import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.pda.PdaDatosInventarioLibre;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.InventarioRotativoService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service(value = "InventarioRotativoService")
public class InventarioRotativoServiceImpl implements InventarioRotativoService {
    @Autowired
	private InventarioRotativoDao inventarioRotativoDao;
    
	@Resource 
	private MessageSource messageSource;
	
    @Override
	 public void insert(InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.insert(inventarioRotativo);
	}
 
    @Override
	 public void insertUpdate(InventarioRotativo inventarioRotativo) throws Exception {
    	//Si existe el artículo en la tabla habrá que hacer una update y si no una insert
    	InventarioRotativo inventarioRotativoModif = new InventarioRotativo();
    	inventarioRotativoModif.setCodArticulo(inventarioRotativo.getCodArticulo());
    	inventarioRotativoModif.setCodArticuloRela(inventarioRotativo.getCodArticuloRela());
    	inventarioRotativoModif.setCodCentro(inventarioRotativo.getCodCentro());
    	inventarioRotativoModif.setCodMac(inventarioRotativo.getCodMac());

    	inventarioRotativoModif = this.inventarioRotativoDao.findOne(inventarioRotativo);
    	
    	if (inventarioRotativoModif != null){
    		//Update
        	inventarioRotativoModif.setFlgNoGuardar(inventarioRotativo.getFlgNoGuardar());
    		
    		if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(inventarioRotativo.getOrigenInventario())){
    			inventarioRotativoModif.setCamaraBandeja(inventarioRotativo.getCamaraBandeja()!=null?inventarioRotativo.getCamaraBandeja():new Long("0"));
    			inventarioRotativoModif.setCamaraStock(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"));
    		}else if (Constantes.INVENTARIO_LIBRE_SALA_VENTA.equals(inventarioRotativo.getOrigenInventario())){    			
    			inventarioRotativoModif.setSalaBandeja(inventarioRotativo.getSalaBandeja()!=null?inventarioRotativo.getSalaBandeja():new Long("0"));
    			inventarioRotativoModif.setSalaStock(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"));
    		}else{
    			inventarioRotativoModif.setCamaraBandeja(inventarioRotativo.getCamaraBandeja()!=null?inventarioRotativo.getCamaraBandeja():new Long("0"));
    			inventarioRotativoModif.setCamaraStock(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"));
    			inventarioRotativoModif.setSalaBandeja(inventarioRotativo.getSalaBandeja()!=null?inventarioRotativo.getSalaBandeja():new Long("0"));
    			inventarioRotativoModif.setSalaStock(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"));
    		}

			//Formateo de cantidades de bandejas
    		inventarioRotativoModif.setSalaBandeja(inventarioRotativoModif.getSalaBandeja()!=null?inventarioRotativoModif.getSalaBandeja():new Long("0"));
    		inventarioRotativoModif.setCamaraBandeja(inventarioRotativoModif.getCamaraBandeja()!=null?inventarioRotativoModif.getCamaraBandeja():new Long("0"));
			
    		//Formateo de cantidades de stock
    		inventarioRotativoModif.setSalaStock(inventarioRotativoModif.getSalaStock()!=null?inventarioRotativoModif.getSalaStock():new Double("0"));
    		inventarioRotativoModif.setCamaraStock(inventarioRotativoModif.getCamaraStock()!=null?inventarioRotativoModif.getCamaraStock():new Double("0"));

    		inventarioRotativoModif.setCodArea(inventarioRotativo.getCodArea());
    		inventarioRotativoModif.setCodSeccion(inventarioRotativo.getCodSeccion());

    		this.inventarioRotativoDao.update(inventarioRotativoModif);
    	}else{
    		//Insert
        	InventarioRotativo inventarioRotativoInsert = new InventarioRotativo();
        	inventarioRotativoInsert.setCodArticulo(inventarioRotativo.getCodArticulo());
        	inventarioRotativoInsert.setCodArticuloRela(inventarioRotativo.getCodArticuloRela());
        	inventarioRotativoInsert.setCodCentro(inventarioRotativo.getCodCentro());
        	inventarioRotativoInsert.setCodMac(inventarioRotativo.getCodMac());

        	inventarioRotativoInsert.setFlgNoGuardar(Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO);
        	inventarioRotativoInsert.setFlgStockPrincipal(inventarioRotativo.getFlgStockPrincipal());
        	inventarioRotativoInsert.setFlgUnica(inventarioRotativo.getFlgUnica());
        	inventarioRotativoInsert.setFlgVariasUnitarias(inventarioRotativo.getFlgVariasUnitarias());

    		if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(inventarioRotativo.getOrigenInventario())){
    			inventarioRotativoInsert.setCamaraBandeja(inventarioRotativo.getCamaraBandeja()!=null?inventarioRotativo.getCamaraBandeja():new Long("0"));
    			inventarioRotativoInsert.setCamaraStock(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"));
    		}else if (Constantes.INVENTARIO_LIBRE_SALA_VENTA.equals(inventarioRotativo.getOrigenInventario())){    			
    			inventarioRotativoInsert.setSalaBandeja(inventarioRotativo.getSalaBandeja()!=null?inventarioRotativo.getSalaBandeja():new Long("0"));
    			inventarioRotativoInsert.setSalaStock(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"));
    		}else{
    			inventarioRotativoInsert.setCamaraBandeja(inventarioRotativo.getCamaraBandeja()!=null?inventarioRotativo.getCamaraBandeja():new Long("0"));
    			inventarioRotativoInsert.setCamaraStock(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"));
    			inventarioRotativoInsert.setSalaBandeja(inventarioRotativo.getSalaBandeja()!=null?inventarioRotativo.getSalaBandeja():new Long("0"));
    			inventarioRotativoInsert.setSalaStock(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"));
    		}

			//Formateo de cantidades de bandejas
    		inventarioRotativoInsert.setSalaBandeja(inventarioRotativoInsert.getSalaBandeja()!=null?inventarioRotativoInsert.getSalaBandeja():new Long("0"));
    		inventarioRotativoInsert.setCamaraBandeja(inventarioRotativoInsert.getCamaraBandeja()!=null?inventarioRotativoInsert.getCamaraBandeja():new Long("0"));
			
    		//Formateo de cantidades de stock
    		inventarioRotativoInsert.setSalaStock(inventarioRotativoInsert.getSalaStock()!=null?inventarioRotativoInsert.getSalaStock():new Double("0"));
    		inventarioRotativoInsert.setCamaraStock(inventarioRotativoInsert.getCamaraStock()!=null?inventarioRotativoInsert.getCamaraStock():new Double("0"));

    		inventarioRotativoInsert.setCodArea(inventarioRotativo.getCodArea());
    		inventarioRotativoInsert.setCodSeccion(inventarioRotativo.getCodSeccion());
    		
    		inventarioRotativoInsert.setProporRefCompra(inventarioRotativo.getProporRefCompra()!=null?inventarioRotativo.getProporRefCompra():new Long(1));
    		inventarioRotativoInsert.setProporRefVenta(inventarioRotativo.getProporRefVenta()!=null?inventarioRotativo.getProporRefVenta():new Long(1));
    		
    		this.inventarioRotativoDao.insert(inventarioRotativoInsert);
    	}
    	this.updateRefConRelaciones(inventarioRotativo);
	}

    @Override
	 public void updateRefConRelaciones(InventarioRotativo inventarioRotativo) throws Exception {
    	//Si existe el artículo en la tabla con relaciones habrá que hacer una update
    	List<InventarioRotativo> listaInventarioRotativoModif = null;
    	InventarioRotativo inventarioRotativoModif = new InventarioRotativo();
    	inventarioRotativoModif.setCodArticuloRela(inventarioRotativo.getCodArticuloRela());
    	inventarioRotativoModif.setCodCentro(inventarioRotativo.getCodCentro());
    	inventarioRotativoModif.setCodMac(inventarioRotativo.getCodMac());

    	listaInventarioRotativoModif = this.inventarioRotativoDao.findAll(inventarioRotativoModif);
    	
    	if (listaInventarioRotativoModif != null){
    		for (InventarioRotativo invRot : listaInventarioRotativoModif){
    	    	if (invRot != null){
    	    		//Update
    	        	invRot.setFlgNoGuardar(inventarioRotativo.getFlgNoGuardar());
    	    		
    	    		if (Constantes.INVENTARIO_LIBRE_CAMARA_ALMACEN.equals(inventarioRotativo.getOrigenInventario())){
    	    			invRot.setCamaraBandeja(inventarioRotativo.getCamaraBandeja()!=null?inventarioRotativo.getCamaraBandeja():new Long("0"));
    	    			invRot.setCamaraStock(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"));
    	    		}else if (Constantes.INVENTARIO_LIBRE_SALA_VENTA.equals(inventarioRotativo.getOrigenInventario())){    			
    	    			invRot.setSalaBandeja(inventarioRotativo.getSalaBandeja()!=null?inventarioRotativo.getSalaBandeja():new Long("0"));
    	    			invRot.setSalaStock(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"));
    	    		}else{
    	    			invRot.setCamaraBandeja(inventarioRotativo.getCamaraBandeja()!=null?inventarioRotativo.getCamaraBandeja():new Long("0"));
    	    			invRot.setCamaraStock(inventarioRotativo.getCamaraStock()!=null?inventarioRotativo.getCamaraStock():new Double("0"));
    	    			invRot.setSalaBandeja(inventarioRotativo.getSalaBandeja()!=null?inventarioRotativo.getSalaBandeja():new Long("0"));
    	    			invRot.setSalaStock(inventarioRotativo.getSalaStock()!=null?inventarioRotativo.getSalaStock():new Double("0"));
    	    		}

    				//Formateo de cantidades de bandejas
    	    		invRot.setSalaBandeja(invRot.getSalaBandeja()!=null?invRot.getSalaBandeja():new Long("0"));
    	    		invRot.setCamaraBandeja(invRot.getCamaraBandeja()!=null?invRot.getCamaraBandeja():new Long("0"));
    				
    	    		//Formateo de cantidades de stock
    	    		invRot.setSalaStock(invRot.getSalaStock()!=null?invRot.getSalaStock():new Double("0"));
    	    		invRot.setCamaraStock(invRot.getCamaraStock()!=null?invRot.getCamaraStock():new Double("0"));

    	    		invRot.setCodArea(inventarioRotativo.getCodArea());
    	    		invRot.setCodSeccion(inventarioRotativo.getCodSeccion());

    	    		this.inventarioRotativoDao.update(invRot);
    	    	}
    		}
    	}
	}

    @Override
	 public void delete(InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.delete(inventarioRotativo);
	}

    @Override
	 public void deleteGuardados(InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.deleteGuardados(inventarioRotativo);
	}

    @Override
	 public void deleteRefSinRelaciones(InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.deleteRefSinRelaciones(inventarioRotativo);
	}

    @Override
	 public void deleteUnitario(InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.deleteUnitario(inventarioRotativo);
	}

    @Override
	 public void updateErrorAvisoArticulo(InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.updateErrorAvisoArticulo(inventarioRotativo);
	}

    @Override
	 public List<InventarioRotativo> findAll(InventarioRotativo inventarioRotativo) throws Exception {
		return this.inventarioRotativoDao.findAll(inventarioRotativo);
	}

    @Override
	 public List<InventarioRotativo> findAllPaginate(InventarioRotativo inventarioRotativo, Pagination pagination) throws Exception {
		return this.inventarioRotativoDao.findAllPaginate(inventarioRotativo, pagination);
	}

    @Override
	 public Long findAllCount(InventarioRotativo inventarioRotativo) throws Exception {
		return this.inventarioRotativoDao.findAllCount(inventarioRotativo);
	}
    
    @Override
	 public List<PdaDatosInventarioLibre> findAllPda(PdaDatosInventarioLibre pdaDatosInventarioLibre, boolean bolPrincipal) throws Exception {
		return this.inventarioRotativoDao.findAllPda(pdaDatosInventarioLibre, bolPrincipal);
	}
    
    @Override
    public LinkedHashMap<Long, String> findSeccionesPda(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception {
    	return this.inventarioRotativoDao.findSeccionesPda(pdaDatosInventarioLibre);
    }
    
    @Override
    public boolean existenDatosInventarioLibrePda(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
    	return this.inventarioRotativoDao.existenDatosInventarioLibrePda(pdaDatosInventarioLibre);
    }

    @Override
    public PdaDatosInventarioLibre findSumaCantidades(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
    	return this.inventarioRotativoDao.findSumaCantidades(pdaDatosInventarioLibre);
    }

    @Override
    public PdaDatosInventarioLibre findSumaCantidadesRefConRelaciones(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
    	return this.inventarioRotativoDao.findSumaCantidadesRefConRelaciones(pdaDatosInventarioLibre);
    }

    @Override
    public PdaDatosInventarioLibre actualizarCantidadesRef(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();
		if (pdaDatosInventarioLibre != null){
			PdaDatosInventarioLibre pdaDatosInvLib = this.inventarioRotativoDao.findCantidadesRef(pdaDatosInventarioLibre);
			if (pdaDatosInvLib != null){
				//Actualizamos las cantidades
				
				//SalaStock
				BigDecimal salaStock1 = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getSalaStock());
				BigDecimal salaStock2 = Utilidades.convertirStringABigDecimal(pdaDatosInvLib.getSalaStock());
				BigDecimal salaStock = salaStock1.add(salaStock2);
				BigDecimal roundedSalaStock = salaStock.setScale(2, BigDecimal.ROUND_HALF_UP);
				pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace('.', ','));
				
				//CamaraStock
				BigDecimal camaraStock1 = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getCamaraStock());
				BigDecimal camaraStock2 = Utilidades.convertirStringABigDecimal(pdaDatosInvLib.getCamaraStock());
				BigDecimal camaraStock = camaraStock1.add(camaraStock2);
				BigDecimal roundedCamaraStock = camaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);
				pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace('.', ','));
				
				BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);
				pdaDatosInventarioLibre.setTotalStock(Utilidades.convertirDoubleAString(roundedTotalStock.doubleValue(),"###0.00").replace('.', ','));
				
				if (Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibre.getFlgStockPrincipal())){
					//SalaBandeja
					Long salaBandeja1 = new Long(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
					Long salaBandeja2 = new Long(pdaDatosInvLib.getSalaBandeja()!=null?pdaDatosInvLib.getSalaBandeja():"0");
					Long salaBandeja = salaBandeja1 + salaBandeja2;
					pdaDatosInventarioLibre.setSalaBandeja(salaBandeja.toString());

					//CamaraBandeja
					Long camaraBandeja1 = new Long(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");
					Long camaraBandeja2 = new Long(pdaDatosInvLib.getCamaraBandeja()!=null?pdaDatosInvLib.getCamaraBandeja():"0");
					Long camaraBandeja = camaraBandeja1 + camaraBandeja2;
					pdaDatosInventarioLibre.setCamaraBandeja(camaraBandeja.toString());
					
					//Cálculo de campos calculados
					Long totalBandejas = camaraBandeja1 + salaBandeja1; 
					pdaDatosInventarioLibre.setTotalBandeja(totalBandejas.toString());
					Double diferencia = totalBandejas - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}else{
					Double diferencia = roundedTotalStock.doubleValue() - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}
				
				//Carga de descripciones de stock actual y diferencia
				//Stock actual
				if (pdaDatosInventarioLibre.getStockActual() != null){
					pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.stockActual", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getStockActual(),"###0.00").replace('.', ',')}, locale));
				}
				//Diferencia
				if (pdaDatosInventarioLibre.getDiferencia() != null){
					if (pdaDatosInventarioLibre.getDiferencia() > 0) {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.diferencia", new Object[]{"+" + Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
				
					} else {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.diferencia", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
					}
				}
			}
		}
    	return pdaDatosInventarioLibre;
    }

    @Override
    public PdaDatosInventarioLibre actualizarCantidadesRefConRelaciones(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();
		if (pdaDatosInventarioLibre != null){
			PdaDatosInventarioLibre pdaDatosInvLib = this.inventarioRotativoDao.findSumaCantidadesRefConRelaciones(pdaDatosInventarioLibre);
			if (pdaDatosInvLib != null){
				//Actualizamos las cantidades
				
				//SalaStock
				BigDecimal salaStock1 = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getSalaStock());
				BigDecimal salaStock2 = Utilidades.convertirStringABigDecimal(pdaDatosInvLib.getSalaStock());
				BigDecimal salaStock = salaStock1.add(salaStock2);
				BigDecimal roundedSalaStock = salaStock.setScale(2, BigDecimal.ROUND_HALF_UP);
				pdaDatosInventarioLibre.setSalaStock(Utilidades.convertirDoubleAString(roundedSalaStock.doubleValue(),"###0.00").replace(',', '.'));
				
				//CamaraStock
				BigDecimal camaraStock1 = Utilidades.convertirStringABigDecimal(pdaDatosInventarioLibre.getCamaraStock());
				BigDecimal camaraStock2 = Utilidades.convertirStringABigDecimal(pdaDatosInvLib.getCamaraStock());
				BigDecimal camaraStock = camaraStock1.add(camaraStock2);
				BigDecimal roundedCamaraStock = camaraStock.setScale(2, BigDecimal.ROUND_HALF_UP);
				pdaDatosInventarioLibre.setCamaraStock(Utilidades.convertirDoubleAString(roundedCamaraStock.doubleValue(),"###0.00").replace(',', '.'));
				
				BigDecimal roundedTotalStock = roundedCamaraStock.add(roundedSalaStock);
				pdaDatosInventarioLibre.setTotalStock(Utilidades.convertirDoubleAString(roundedTotalStock.doubleValue(),"###0.00").replace(',', '.'));
				
				if (Constantes.INVENTARIO_LIBRE_STOCK_PRINCIPAL_BANDEJAS.equals(pdaDatosInventarioLibre.getFlgStockPrincipal())){
					//SalaBandeja
					Long salaBandeja1 = new Long(pdaDatosInventarioLibre.getSalaBandeja()!=null?pdaDatosInventarioLibre.getSalaBandeja():"0");
					Long salaBandeja2 = new Long(pdaDatosInvLib.getSalaBandeja()!=null?pdaDatosInvLib.getSalaBandeja():"0");
					Long salaBandeja = salaBandeja1 + salaBandeja2;
					pdaDatosInventarioLibre.setSalaBandeja(salaBandeja.toString());

					//CamaraBandeja
					Long camaraBandeja1 = new Long(pdaDatosInventarioLibre.getCamaraBandeja()!=null?pdaDatosInventarioLibre.getCamaraBandeja():"0");
					Long camaraBandeja2 = new Long(pdaDatosInvLib.getCamaraBandeja()!=null?pdaDatosInvLib.getCamaraBandeja():"0");
					Long camaraBandeja = camaraBandeja1 + camaraBandeja2;
					pdaDatosInventarioLibre.setCamaraBandeja(camaraBandeja.toString());
					
					//Cálculo de campos calculados
					Long totalBandejas = camaraBandeja1 + salaBandeja1; 
					pdaDatosInventarioLibre.setTotalBandeja(totalBandejas.toString());
					Double diferencia = totalBandejas - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}else{
					Double diferencia = roundedTotalStock.doubleValue() - pdaDatosInventarioLibre.getStockActual();
					if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(pdaDatosInventarioLibre.getFlgVariasUnitarias())){
						//Si tiene referencias unitarias hay que mostrar diferencia 0
						diferencia = new Double(0);
					}
					pdaDatosInventarioLibre.setDiferencia(diferencia);
				}
				
				//Carga de descripciones de stock actual y diferencia
				//Stock actual
				if (pdaDatosInventarioLibre.getStockActual() != null){
					pdaDatosInventarioLibre.setDescStockActual(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.stockActual", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getStockActual(),"###0.00").replace('.', ',')}, locale));
				}
				//Diferencia
				if (pdaDatosInventarioLibre.getDiferencia() != null){
					if (pdaDatosInventarioLibre.getDiferencia() > 0) {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
							"pda_p42_inventarioLibre.diferencia", new Object[]{"+" + Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
				
					} else {
						pdaDatosInventarioLibre.setDescDiferencia(this.messageSource.getMessage(
								"pda_p42_inventarioLibre.diferencia", new Object[]{Utilidades.convertirDoubleAString(pdaDatosInventarioLibre.getDiferencia(),"###0.00").replace('.', ',')}, locale));
					}
				}
			}
		}
    	return pdaDatosInventarioLibre;
    }

    @Override
	public InventarioRotativo findOne(InventarioRotativo inventarioRotativo) throws Exception {
    	return this.inventarioRotativoDao.findOne(inventarioRotativo);
    }
    
    @Override
	 public List<PdaDatosInventarioLibre> findAllPdaGISAE(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception {
		return  this.inventarioRotativoDao.findAllPdaGISAE(pdaDatosInventarioLibre);
	}

    @Override
	 public void updateFlgNoGuardarPda(String flgNoGuardar, InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.updateFlgNoGuardarPda(flgNoGuardar, inventarioRotativo);
	}

    @Override
	 public void updateAvisoPda(String aviso, InventarioRotativo inventarioRotativo) throws Exception {
		this.inventarioRotativoDao.updateAvisoPda(aviso, inventarioRotativo);
	}

    @Override
	 public void updateAvisoPdaAll(String aviso, InventarioRotativo inventarioRotativo) throws Exception {

		List<PdaDatosInventarioLibre> listaInvLib = new ArrayList<PdaDatosInventarioLibre>();
		
    	//Actualizamos el aviso del inventario rotativo madre
    	InventarioRotativo invRot = new InventarioRotativo();
    	invRot.setCodCentro(inventarioRotativo.getCodCentro());
    	invRot.setCodMac(inventarioRotativo.getCodMac());
    	invRot.setCodArticulo(inventarioRotativo.getCodArticulo());
    	invRot.setCodArticuloRela(inventarioRotativo.getCodArticulo());
		InventarioRotativo existeInvRot = this.findOne(invRot);
		if (existeInvRot != null){
			
	    	if (Constantes.INVENTARIO_LIBRE_UNICA_SI.equals(existeInvRot.getFlgUnica())){
	    		//Si esa referencia es unica actualizamos el aviso
	        	InventarioRotativo invRotUnica = new InventarioRotativo();
	        	invRotUnica.setCodCentro(inventarioRotativo.getCodCentro());
	        	invRotUnica.setCodMac(inventarioRotativo.getCodMac());
	        	invRotUnica.setCodArticulo(inventarioRotativo.getCodArticuloRela());
	        	invRotUnica.setCodArticuloRela(inventarioRotativo.getCodArticuloRela());
	    		this.updateAvisoPda("", invRotUnica);
	    	}
	    	else if (Constantes.INVENTARIO_LIBRE_FLG_CANTIDADES_UNITARIAS_SI.equals(existeInvRot.getFlgVariasUnitarias())){
				//Buscamos las referencias madre de la unitaria
				List<PdaDatosInventarioLibre> listaInvLibUnitarias = new ArrayList<PdaDatosInventarioLibre>();
				
				PdaDatosInventarioLibre pdaDatosInventarioLibreUni = new PdaDatosInventarioLibre();
				pdaDatosInventarioLibreUni.setCodCentro(inventarioRotativo.getCodCentro());
				pdaDatosInventarioLibreUni.setCodArticulo(inventarioRotativo.getCodArticulo());
				pdaDatosInventarioLibreUni.setCodMac(inventarioRotativo.getCodMac());

				listaInvLibUnitarias = this.findAllPda(pdaDatosInventarioLibreUni, false);
				for (PdaDatosInventarioLibre invLibUni : listaInvLibUnitarias){
					//Aqui tratamos cada una de las referencias madres y la unitaria
					
		        	//Actualizamos el aviso del inventario rotativo madre
		        	InventarioRotativo invRotUni = new InventarioRotativo();
		        	invRotUni.setCodCentro(invLibUni.getCodCentro());
		        	invRotUni.setCodMac(invLibUni.getCodMac());
		        	invRotUni.setCodArticulo(invLibUni.getCodArticulo());
		        	invRotUni.setCodArticuloRela(invLibUni.getCodArticuloRela());
		    		this.updateAvisoPda("", invRotUni);
		    		
					//Actualizamos el aviso de las referencias hijas de las unitarias
					
					PdaDatosInventarioLibre pdaDatosInventarioLibreRel = new PdaDatosInventarioLibre();
					pdaDatosInventarioLibreRel.setCodCentro(invLibUni.getCodCentro());
					pdaDatosInventarioLibreRel.setCodArticulo(invLibUni.getCodArticuloRela());
					pdaDatosInventarioLibreRel.setCodMac(invLibUni.getCodMac());

					listaInvLib = this.findAllPda(pdaDatosInventarioLibreRel, false);
					for (PdaDatosInventarioLibre invLib : listaInvLib){
			        	//Actualizamos el aviso de la referencia hija
			        	InventarioRotativo invRotHija = new InventarioRotativo();
			        	invRotHija.setCodCentro(invLib.getCodCentro());
			        	invRotHija.setCodMac(invLib.getCodMac());
			        	invRotHija.setCodArticulo(invLib.getCodArticulo());
			        	invRotHija.setCodArticuloRela(invLib.getCodArticuloRela());
			    		this.updateAvisoPda("", invRotHija);
					}
				}
	    	}
	    	else{
	    		//Es una referencia madre, actualizamos el aviso de la madre y sus hijas
				PdaDatosInventarioLibre pdaDatosInventarioLibreRel = new PdaDatosInventarioLibre();
				pdaDatosInventarioLibreRel.setCodCentro(inventarioRotativo.getCodCentro());
				pdaDatosInventarioLibreRel.setCodArticulo(inventarioRotativo.getCodArticulo());
				pdaDatosInventarioLibreRel.setCodMac(inventarioRotativo.getCodMac());

				listaInvLib = this.findAllPda(pdaDatosInventarioLibreRel, false);
				for (PdaDatosInventarioLibre invLibRel : listaInvLib){
		    		//Actualizamos el aviso de la madre y cada una de las hijas 
		        	InventarioRotativo invRotMadre = new InventarioRotativo();
		        	invRotMadre.setCodCentro(invLibRel.getCodCentro());
		        	invRotMadre.setCodMac(invLibRel.getCodMac());
		        	invRotMadre.setCodArticulo(invLibRel.getCodArticulo());
		        	invRotMadre.setCodArticuloRela(invLibRel.getCodArticuloRela());
		    		this.updateAvisoPda("", invRotMadre);
				}
	    	}
		}
	}

}
