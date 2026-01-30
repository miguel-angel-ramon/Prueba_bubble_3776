package es.eroski.misumi.service.prehuecos;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.prehuecos.iface.PrehuecosStockAlmacenDao;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosStockAlmacenService;

@Service
public class PrehuecosStockAlmacenServiceImpl implements PrehuecosStockAlmacenService{

	@Autowired
	private PrehuecosStockAlmacenDao prehuecosStockAlmacenDao;

	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac,int pagActual) throws SQLException{
		return prehuecosStockAlmacenDao.getReferenciasPrehuecosStockAlmacen(centro, mac, pagActual);
	}
	
	@Override
	public int updateEstadoPrehueco(Long codCentro, String mac, String codArt, int nuevoEstado) throws SQLException {
		    return prehuecosStockAlmacenDao.updateEstadoPrehueco(codCentro.toString(), mac, codArt, nuevoEstado);
		
	
		}
}