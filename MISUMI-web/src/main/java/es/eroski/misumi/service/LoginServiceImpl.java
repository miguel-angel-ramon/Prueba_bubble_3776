package es.eroski.misumi.service;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.User;
import es.eroski.misumi.model.UserLdap;
import es.eroski.misumi.service.iface.LoginService;
import es.eroski.misumi.util.iface.LDAPManager;

@Service(value = "LoginService")
public class LoginServiceImpl implements LoginService {
	// private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	@Autowired
	private LDAPManager lDAPManager;
	
	@Override
	public User validateLogin(User user) throws Exception {
		UserLdap usuarioLdap=null;
		usuarioLdap=this.lDAPManager.findByPrimaryKey(user.getCode(),user.getPassword());
		
		
		User usuario =null;
		if (usuarioLdap!=null){
			usuario= new User();
			usuario.setUserName(usuarioLdap.getName() +' '+usuarioLdap.getSurname());
			usuario.setCode(usuarioLdap.getCode());
		}

		return usuario;
	}
	
	
}
