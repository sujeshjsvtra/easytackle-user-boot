package tackle.easay.com.user.service;

import java.util.List;

import tackle.easay.com.user.entity.Menu;

public interface UserService {
	List<Menu> menuByUser(Long userId) throws Exception;
	
	
}
