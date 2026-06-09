package carpool_portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carpool_portal.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	    User findByEmail(String email);

	    boolean existsByEmail(String email);
}