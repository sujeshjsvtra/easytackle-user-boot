package tackle.easay.com.user.repository;

import org.springframework.data.repository.CrudRepository;

import tackle.easay.com.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
