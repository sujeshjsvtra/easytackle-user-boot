package tackle.easay.com.user.endpoint;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import tackle.easay.com.user.entity.Menu;
import tackle.easay.com.user.service.UserService;
import tackle.easay.com.user.util.AuthComponent;

@RestController
@RequestMapping(value = "/user-client")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {
	@Autowired
	private AuthComponent authComponent;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/sidemenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Menu>> userMenu(HttpServletRequest request) throws Exception {
		HttpStatus status = HttpStatus.OK;
		List<Menu> menuList = null;
		try {
			Long userId = authComponent.authenticate(request);
			menuList = userService.menuByUser(userId);
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		return new ResponseEntity<List<Menu>>(menuList, status);
	}


}
