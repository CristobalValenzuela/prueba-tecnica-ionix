package cl.ionix.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ionix.web.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	User findByUserId(Integer userId);

	User findByUserName(String username);
	
}
