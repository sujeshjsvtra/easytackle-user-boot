package tackle.easay.com.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tackle.easay.com.user.entity.User;
import tackle.easay.com.user.entity.UserRoleMenu;

public interface UserRoleMenuRepository extends CrudRepository<UserRoleMenu, Long> {

	List<UserRoleMenu> findAllByUserId(User userId);
}
