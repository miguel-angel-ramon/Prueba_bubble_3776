package es.eroski.misumi.util.iface;

import es.eroski.misumi.model.UserLdap;

public interface LDAPManager {
	 public  UserLdap findByPrimaryKey(String user, String pwd) throws Exception ;
		 
}
