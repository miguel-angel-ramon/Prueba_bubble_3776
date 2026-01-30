package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.User;

public interface UserDao {
	public User find(User user) throws Exception ;
}
