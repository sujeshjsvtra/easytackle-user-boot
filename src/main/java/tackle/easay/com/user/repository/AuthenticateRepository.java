package tackle.easay.com.user.repository;

import org.springframework.data.repository.CrudRepository;

import tackle.easay.com.user.entity.Authenticate;
import tackle.easay.com.user.entity.User;

public interface AuthenticateRepository extends CrudRepository<Authenticate, Long>{

	Authenticate findByUsername(String username);
	
	Authenticate findByUserId(User userId);
}
