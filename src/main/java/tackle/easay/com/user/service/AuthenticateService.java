package tackle.easay.com.user.service;

import java.util.Map;

import tackle.easay.com.user.entity.Authenticate;
import tackle.easay.com.user.view.UserAccount;

public interface AuthenticateService {
	boolean createUserAccount(UserAccount userAccount) throws Exception;
	
	Map<String, Object> userLogin(UserAccount userAccount, Map<String, Object> map) throws Exception;
	
	boolean resetPassword(UserAccount userAccount)throws Exception;
	
	Map<String, Object> createTemporaryAuthentication(String email)throws Exception;
	
	boolean deleteAuthenticationByUserId(Long userId)throws Exception;
}
