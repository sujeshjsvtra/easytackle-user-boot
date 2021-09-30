package tackle.easay.com.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tackle.easay.com.user.entity.Authenticate;
import tackle.easay.com.user.entity.User;
import tackle.easay.com.user.exception.AlreadyExistException;
import tackle.easay.com.user.exception.AuthenticateDeleteException;
import tackle.easay.com.user.exception.InvalidLoginCredentialsException;
import tackle.easay.com.user.exception.NotValidUserException;
import tackle.easay.com.user.exception.UserInActiveException;
import tackle.easay.com.user.repository.AuthenticateRepository;
import tackle.easay.com.user.repository.UserRepository;
import tackle.easay.com.user.service.AuthenticateService;
import tackle.easay.com.user.util.CryptoUtils;
import tackle.easay.com.user.util.SystemConstants;
import tackle.easay.com.user.view.UserAccount;

@Service
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticateRepository authenticateRepository;

	public AuthenticateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean createUserAccount(UserAccount userAccount) throws Exception {
		if (userAccount != null) {

			User user = userAccount.getUser();
			Authenticate authenticate = userAccount.getAuthenticate();
			user = userRepository.save(user);
			Long salt = CryptoUtils.nDigitRandomNumber(10);
			String encryptPassword = CryptoUtils.encrypt(authenticate.getPassword(), authenticate.getUsername(),
					String.valueOf(salt));
			authenticate.setPassword(encryptPassword);
			authenticate.setUserId(user);
			authenticate.setStatus(SystemConstants.ACTIVE);
			authenticate.setSalt(String.valueOf(salt));

			authenticate = authenticateRepository.save(authenticate);
			if (authenticate.getId() != null) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public Map<String, Object> userLogin(UserAccount userAccount, Map<String, Object> map) throws Exception {
		Authenticate authenticate = authenticateRepository.findByUsername(userAccount.getEmail());
		if (authenticate != null) {
			if (authenticate.getStatus() == 1) {
				try {
					String password = CryptoUtils.decrypt(authenticate.getPassword(), userAccount.getEmail(),
							authenticate.getSalt());
					if (password.equals(userAccount.getPassword())) {
						if (userAccount.getSecretCode().intValue() == authenticate.getSecretCode().intValue()) {
							map.put("email", authenticate.getUsername());
							map.put("userId", authenticate.getUserId().getId());
							return map;
						}
					} else {
						return null;
					}
				} catch (Exception e) {
					throw new InvalidLoginCredentialsException("Invalid Username or Password");
				}
			} else {
				throw new UserInActiveException("User Access Is Disabled..");
			}
		}
		throw new InvalidLoginCredentialsException("Invalid Username or Password");
	}

	@Override
	public boolean resetPassword(UserAccount userAccount) throws Exception {
		Authenticate authenticate = authenticateRepository.findByUsername(userAccount.getEmail());
		if (authenticate != null) {
			Long salt = CryptoUtils.nDigitRandomNumber(10);
			String encryptPassword = CryptoUtils.encrypt(CryptoUtils.nDigitRandomNumber(12).toString(),
					authenticate.getUsername(), String.valueOf(salt));
			authenticate.setSalt(String.valueOf(salt));
			authenticate.setPassword(encryptPassword);
			authenticate = authenticateRepository.save(authenticate);
			if (authenticate != null && authenticate.getId() != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> createTemporaryAuthentication(String email) throws Exception {
		Map<String, Object> map = new HashMap<>();
			Authenticate authenticate = authenticateRepository.findByUsername(email);
			if (authenticate == null || authenticate.getId() == null) {
				User user = User.builder().email(email).status(SystemConstants.TEMPORARY).build();
				user = userRepository.save(user);
				Long salt = CryptoUtils.nDigitRandomNumber(10);
				String password = CryptoUtils.randomString(8);
				String encryptPassword = CryptoUtils.encrypt(password, email, String.valueOf(salt));
				authenticate = Authenticate.builder().username(email).password(encryptPassword)
						.status(SystemConstants.TEMPORARY).secretCode(CryptoUtils.nDigitRandomNumber(4).intValue())
						.userId(user).build();
				authenticate.setSalt(String.valueOf(salt));
				authenticate = authenticateRepository.save(authenticate);
				map.put("id", authenticate.getId());
				map.put("username", email);
				map.put("password", password);
				map.put("secretCode", authenticate.getSecretCode());
				map.put("userId", user.getId());
			}else {
				throw new AlreadyExistException("Username already exist");
			}
		return map;
	}

	@Override
	@Transactional
	public boolean deleteAuthenticationByUserId(Long userId) throws Exception {
		 Authenticate authenticate = authenticateRepository.findByUserId(new User(userId));
		 if(authenticate!=null) {
			 try {
			  authenticateRepository.delete(authenticate);
			 }catch(Exception e) {
				 throw new AuthenticateDeleteException("Failed To Delete");
			 }
		 }else {
			 throw new NotValidUserException("User is Not Found");
		 }
		return true;
	}

}
