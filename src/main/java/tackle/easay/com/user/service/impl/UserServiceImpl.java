package tackle.easay.com.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tackle.easay.com.user.entity.Menu;
import tackle.easay.com.user.entity.User;
import tackle.easay.com.user.entity.UserRoleMenu;
import tackle.easay.com.user.exception.MenuNotFoundException;
import tackle.easay.com.user.exception.UserCreateException;
import tackle.easay.com.user.repository.UserRepository;
import tackle.easay.com.user.repository.UserRoleMenuRepository;
import tackle.easay.com.user.service.UserService;
import tackle.easay.com.user.util.SystemConstants;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRoleMenuRepository userRoleMenuRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public List<Menu> menuByUser(Long userId) throws Exception {
		List<UserRoleMenu> roleMenus = userRoleMenuRepository.findAllByUserId(new User(userId));
		List<Menu> menus = new ArrayList<>();
		if(roleMenus!=null) {
			for(UserRoleMenu roleMenu : roleMenus) {
				menus.add(roleMenu.getMenuId());
			}
		}else {
			throw new MenuNotFoundException("Actions are not configured.! Please contact to Admin");
		}
		return menus;
	}


	 
}
