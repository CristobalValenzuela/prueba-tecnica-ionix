package cl.ionix.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ionix.web.execptions.UserException;
import cl.ionix.web.services.UserService;
import cl.ionix.web.to.ResponseApiTO;
import cl.ionix.web.to.UserTO;

@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;

	@GetMapping(path = "", produces = "application/json")
	public ResponseApiTO<List<UserTO>> listAllUsers(){
		long start = System.currentTimeMillis();
		List<UserTO> listUsers = userService.listAllUsers();
		return generateRespuesta(listUsers, start);
	}
	
	@PostMapping(path = "/search/email", produces = "application/json")
	public ResponseApiTO<UserTO> findUserByEmail(@RequestBody UserTO userTO){
		long start = System.currentTimeMillis();
		UserTO user = userService.findUserByEmail(userTO.getEmail());
		return generateRespuesta(user, start);
	}
	
	@PostMapping(path ="", produces = "application/json")
	public ResponseApiTO<UserTO> createUser(@RequestBody UserTO userTO) throws UserException {
		long start = System.currentTimeMillis();
		UserTO user = userService.createUser(userTO);
		return generateRespuesta(user, start);
	}
	
	@DeleteMapping(path ="", produces = "application/json")
	public ResponseApiTO<String> deleteUserById(@RequestBody UserTO userTO) throws UserException {
		long start = System.currentTimeMillis();
		if(userService.deleteUserById(userTO.getUserId())) {
			return generateRespuesta("User deleted", start);
		}
		return generateRespuesta("Failed to delete user", start);
	}
}
