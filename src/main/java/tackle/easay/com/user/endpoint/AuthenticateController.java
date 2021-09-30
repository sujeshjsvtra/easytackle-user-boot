package tackle.easay.com.user.endpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import tackle.easay.com.user.entity.Authenticate;
import tackle.easay.com.user.pojo.Tokens;
import tackle.easay.com.user.service.AuthenticateService;
import tackle.easay.com.user.util.AuthComponent;
import tackle.easay.com.user.util.SystemConstants;
import tackle.easay.com.user.view.UserAccount;

@RestController
@RequestMapping(value = "/authenticate-client")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthenticateController {
	@Autowired
	private AuthenticateService authenticateService;
	@Autowired
	private AuthComponent authComponent;

	public AuthenticateController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> createAccount(@RequestBody UserAccount userAccount) {
		HttpStatus status = HttpStatus.OK;
		Boolean flag = false;
		try {
			flag = authenticateService.createUserAccount(userAccount);
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(flag, status);
	}

	@PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tokens> login(@RequestBody UserAccount userAccount) throws Exception {
		HttpStatus status = HttpStatus.OK;
		String email = null;
		Tokens token = new Tokens();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			 TimeUnit.SECONDS.sleep(5);
			map = authenticateService.userLogin(userAccount, map);
			if (map != null && map.get("email") != null) {
				token = authComponent.createToken(email, Long.parseLong(map.get("userId").toString()));
			}
		} catch (Exception e) {
//			status=HttpStatus.INTERNAL_SERVER_ERROR;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		return new ResponseEntity<Tokens>(token, status);
	}

	@PutMapping(value = "/resetpassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> resetPassword(@RequestBody UserAccount userAccount) {
		HttpStatus status = HttpStatus.OK;
		Boolean flag = false;
		try {
			flag = authenticateService.resetPassword(userAccount);
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Boolean>(flag, status);
	}

	@GetMapping(value = "/temporary/{email}")
	public ResponseEntity<?> createDummyUser(@PathVariable String email) throws Exception{
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> authenticate = null;
		try {
			authenticate = authenticateService.createTemporaryAuthentication(email);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
		return new ResponseEntity<Map<String, Object>>(authenticate,status);
	}

	@DeleteMapping(value = "/delete/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAuthentication(@PathVariable("userId") Long userId) {
		boolean flag = false;
		try {
			flag = authenticateService.deleteAuthenticationByUserId(userId);
		}catch(Exception e) {
			
		}
	}
	
}
