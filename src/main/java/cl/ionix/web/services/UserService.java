package cl.ionix.web.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ionix.web.entities.User;
import cl.ionix.web.execptions.UserException;
import cl.ionix.web.repositories.UserRepository;
import cl.ionix.web.to.UserTO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<UserTO> listAllUsers() {
		List<UserTO> listUsers = new ArrayList<>();
		List<User> listUsersDB = userRepository.findAll();
		for(User user : listUsersDB) {
			listUsers.add(userToUserTO(user));
		}
		return listUsers;
	}
	
	public UserTO findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user != null) {
			return userToUserTO(user);
		}
		return null;
	}
	
	public boolean deleteUserById(Integer userId) throws UserException {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			throw new UserException("The user does not exist.");
		}
		userRepository.deleteById(userId);
		return true;
	}
	
	//TODO: Mejorar agregar @Valid dentro de la entidad
	public UserTO createUser(UserTO userTO) throws UserException {
		if(userTO.getUserId() != null) {
			throw new UserException("The id filed must be empty.");
		}
		if(userTO.getUserName() == null) {
			throw new UserException("The username field must not be empty.");
		}
		User user = userTOtoUser(userTO);
		user = userRepository.save(user);
		return userToUserTO(user);
	}
	
	//TODO: Mejorar remplazar este metodo por un Mapper
	public UserTO userToUserTO(User user) {
		UserTO userTO = new UserTO();
		userTO.setUserId(user.getUserId());
		userTO.setName(user.getName());
		userTO.setUserName(user.getUserName());
		userTO.setEmail(user.getEmail());
		userTO.setPhone(user.getPhone());
		return userTO;
	}
	//TODO: Mejorar remplazar este metodo por un Mapper
	public User userTOtoUser(UserTO userTO) {
		User user = new User();
		user.setName(userTO.getName());
		user.setUserName(userTO.getUserName());
		user.setEmail(userTO.getEmail());
		user.setPhone(userTO.getPhone());
		return user;
	}
}
