package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.User;


public interface LoginService {

	public static final int ERR_LOGIN_INCORRECT = 1;
	public static final int INF_LOGIN_CORRECT = 0;
	
	public User validateLogin(User user) throws Exception;

	
}
