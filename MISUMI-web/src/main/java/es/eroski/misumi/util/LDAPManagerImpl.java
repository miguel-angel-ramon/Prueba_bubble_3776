package es.eroski.misumi.util;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.CollectingAuthenticationErrorCallback;
import org.springframework.stereotype.Service;

import es.eroski.misumi.exception.LDAPException;
import es.eroski.misumi.model.UserLdap;
import es.eroski.misumi.util.iface.LDAPManager;
@Service("LDAPManager")
public class LDAPManagerImpl implements LDAPManager {
	//private static Logger logger = Logger.getLogger(LDAPManagerImpl.class);

   private static LdapTemplate ldapTemplate;
   @Autowired
   public void setLdapTemplate(LdapTemplate ldapTemplate) {
      LDAPManagerImpl.ldapTemplate = ldapTemplate;
   }

     
	   public  UserLdap findByPrimaryKey(String user, String pwd) throws Exception {
	      CollectingAuthenticationErrorCallback errorCallback = new CollectingAuthenticationErrorCallback();
	      try{
	    	  boolean authenticated = ldapTemplate.authenticate("", "(uid="+user+")", pwd ,errorCallback);
	      
		      if (authenticated){
		    	   Name dn = buildDn(user);
		    	  return (UserLdap) ldapTemplate.lookup(dn, getContextMapper());
		      }else{
		    	  Exception error = errorCallback.getError();
		    	  if (error!=null){
		    		  throw new LDAPException(error.getMessage());
		    	  } else{
		    		  return null;
		    	  }
		      }
	      }catch (Exception e) {
	    	  //Se devuelve el mismo control que cuando no está autenticado porque falla algo en LDAP
	    	  Exception error = errorCallback.getError();
	    	  if (error!=null){
	    		  throw new LDAPException(error.getMessage());
	    	  } else{
	    		  return null;
	    	  }
	      }
	   }
	   
	 
	   protected static ContextMapper getContextMapper() {
		      return new UserLdapContextMapper();
		   }

	  
	  protected static Name buildDn(String user ) {
	      DistinguishedName dn = new DistinguishedName();
	      dn.add("cn", user);
	      return dn;
	   }
	  private static class UserLdapContextMapper implements ContextMapper {
	      public Object mapFromContext(Object ctx) {
	         DirContextAdapter context = (DirContextAdapter)ctx;
	         UserLdap userLdap = new UserLdap();
	         userLdap.setCode(context.getStringAttribute("cn"));
	         userLdap.setName(context.getStringAttribute("givenName"));
	         userLdap.setSurname(context.getStringAttribute("sn"));
	         return userLdap;
	      }
	   }
}
