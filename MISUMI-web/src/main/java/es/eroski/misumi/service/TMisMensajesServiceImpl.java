package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.TMisMensajesDao;
import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.TMisMensajesService;

@Service(value = "TMisMensajesService")
public class TMisMensajesServiceImpl implements TMisMensajesService {
	
	@Autowired
	private TMisMensajesDao tMisMensajesDao;

	@Override
	public Aviso obtenerAviso(Aviso aviso) throws Exception {
		// TODO Auto-generated method stub
		return this.tMisMensajesDao.findOne(aviso);
	}

	@Override
	public Boolean existeAviso(Aviso aviso) throws Exception {
		// TODO Auto-generated method stub
		Boolean existe = false;
		if (this.tMisMensajesDao.findCont(aviso)>0){
			existe = true;
		}
		return existe;
	}
	
	//Mirar si existe el aviso
	public Boolean existenAvisos(User user, boolean isPda) throws Exception{
		Aviso aviso = new Aviso();
		List<Aviso> listAviso = new ArrayList<Aviso>();
		try {
			//Indicamos que se trata de un mensaje para pda
			aviso.setPda(isPda);
			
			//Mirar si el centro 0 tiene aviso
			aviso.setCodCentro(Long.valueOf(0));
			if(existeAviso(aviso)){
				return true;
			}
			if (null != user.getCentro()){//Si el centro no está seleccionado
				aviso.setCodCentro(user.getCentro().getCodCentro());
				// Mirar si el propio centro tiene aviso
				if (existeAviso(aviso)){		
					return true;
				} 
			}
		} catch (Exception e) {
				// TODO: handle exception
			throw e;
		}
		return false;
	}
}
