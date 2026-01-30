package es.eroski.misumi.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ImagenComercialDao;
import es.eroski.misumi.dao.iface.StockTiendaDao;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ConsultarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockRequestType;
import es.eroski.misumi.model.stockTiendaWS.ModificarStockResponseType;
import es.eroski.misumi.model.stockTiendaWS.ReferenciaType;
import es.eroski.misumi.service.iface.StockTiendaService;
import es.eroski.misumi.util.Constantes;

@Service(value = "CorreccionStockService")
public class StockTiendaServiceImpl implements StockTiendaService {

    @Autowired
	private StockTiendaDao correccionStockDao;
    
    @Autowired
	private ImagenComercialDao imagenComercialDao;
    
    @Override
    public ConsultarStockResponseType consultaStock(ConsultarStockRequestType stockTiendaRequest,  HttpSession session) throws Exception{
    	return correccionStockDao.consultaStock(stockTiendaRequest, session);
    }
    
    @Override
    public ModificarStockResponseType modificarStock(ModificarStockRequestType modificarStockRequest, HttpSession session) throws Exception{
    	return correccionStockDao.modificarStock(modificarStockRequest, session);
    }
    
    @Override
    public String consultarPcPesoVariable(Long codArticulo){
    	String retorno = "N";
    	if(codArticulo!=null){
    		retorno = imagenComercialDao.consultarPcPesoVariable(codArticulo);
    	}
    	return retorno;
    }

	@Override
	public Double consultarStockInicial(Long codCentro, Long codArticulo) throws Exception {
    	return correccionStockDao.getStockInicial(codCentro, codArticulo);
	}
	
    @Override
    public Double consultaPVP(Long codCentro, Long codArticulo,  HttpSession session) throws Exception{
    	Double output = null;
		ConsultarStockRequestType requestType = new ConsultarStockRequestType();
		requestType.setCodigoCentro(BigInteger.valueOf(codCentro));
		requestType.setTipoMensaje(Constantes.STOCK_TIENDA_CONSULTA_BASICA);
		BigInteger[] listaRef = {BigInteger.valueOf(codArticulo)}; 
		requestType.setListaCodigosReferencia(listaRef);
		
    	ConsultarStockResponseType response = correccionStockDao.consultaStock(requestType, session);
    	if (response != null){
	    	ReferenciaType[] listaRefs = response.getListaReferencias();
	    	if(listaRefs != null && listaRefs.length>0){
	    		ReferenciaType ref = listaRefs[0];
	    		BigDecimal pvp = ref.getPVP();
	    		if (pvp!=null){
	    			output = pvp.doubleValue();
	    		}
	    	}
    	}
    	
    	return output;
    }
}
