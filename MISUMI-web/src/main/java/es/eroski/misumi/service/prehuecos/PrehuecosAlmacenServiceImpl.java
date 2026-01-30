package es.eroski.misumi.service.prehuecos;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.prehuecos.iface.PrehuecosAlmacenDao;
import es.eroski.misumi.model.pda.prehuecos.AlmacenPrehuecos;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosAlmacenService;

@Service
public class PrehuecosAlmacenServiceImpl implements PrehuecosAlmacenService{

	@Autowired
	private PrehuecosAlmacenDao prehuecosAlmacenDao;

	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac, String codArt) throws SQLException{
		return prehuecosAlmacenDao.getReferenciasPrehuecosStockAlmacen(centro, mac, codArt);
	}
	
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacen(Long centro, String mac, String codArt) throws SQLException{
		return prehuecosAlmacenDao.getReferenciasPrehuecosAlmacen(centro, mac, codArt);
	}
	
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacenActualizada(Long centro, String mac, String codArt) throws SQLException{
		return prehuecosAlmacenDao.getReferenciasPrehuecosAlmacenActualizada(centro, mac, codArt);
	}
	
}
