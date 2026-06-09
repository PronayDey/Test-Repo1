package carpool_portal.service;

import carpool_portal.entity.User;

public interface UserService {
	
	void registerUser(User user);

    User findByEmail(String email);

}
