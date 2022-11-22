package cl.ionix.web.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.ionix.web.config.JwtAuthenticationEntryPoint;
import cl.ionix.web.config.JwtRequestFilter;
import cl.ionix.web.entities.User;
import cl.ionix.web.repositories.UserRepository;
import cl.ionix.web.services.JwtUserDetailsService;
import cl.ionix.web.services.UserService;
import cl.ionix.web.to.UserTO;
import cl.ionix.web.util.JwtTokenUtil;

@RunWith(SpringRunner.class)
@TestPropertySource(
  locations = "classpath:application.properties")
@WebMvcTest({UserController.class})
@AutoConfigureMockMvc(addFilters = false)
@Import({UserService.class, JwtRequestFilter.class, JwtAuthenticationEntryPoint.class, JwtUserDetailsService.class, JwtTokenUtil.class })
public class UserControllerTest {
	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void init() throws Exception {
		when(userRepository.findAll()).thenReturn(listUsers());
		when(userRepository.findByEmail(anyString())).thenReturn(getUser());
		when(userRepository.save(any())).thenReturn(getUser());
		when(userRepository.findByUserId(any())).thenReturn(getUser());
		doNothing().when(userRepository).delete(any());
	}

	@Test
	public void listAllUsersTest() throws Exception {
		mockMvc.perform(get("/user"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.responseCode", is(200)))
	    	.andExpect(jsonPath("$.description", is("OK")))
	    	.andExpect(jsonPath("$.result", hasSize(1)));
	}

	@Test
	public void findUserByEmailTest() throws Exception {
		mockMvc.perform(
				post("/user/search/email")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(getUserSearch())
			)
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.responseCode", is(200)))
	    	.andExpect(jsonPath("$.description", is("OK")))
	    	.andExpect(jsonPath("$.result.userId", is(1)));
	}

	@Test
	public void findUserByEmailErrorTest() throws Exception {
		mockMvc.perform(
				post("/user/search/email")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(getUserSearchError())
			)
			.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        	.andExpect(jsonPath("$.responseCode", is(400)))
	    	.andExpect(jsonPath("$.description", is("The email field must not be empty.")));
	}

	@Test
	public void createUserTest() throws Exception {
		mockMvc.perform(
				post("/user")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(getNewUser())
			)
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.responseCode", is(200)))
	    	.andExpect(jsonPath("$.description", is("OK")))
	    	.andExpect(jsonPath("$.result.userId", is(1)));
	}

	@Test
	public void createUserErrorIdTest() throws Exception {
		mockMvc.perform(
				post("/user")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(getUserErrorId())
			)
    		.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        	.andExpect(jsonPath("$.responseCode", is(400)))
	    	.andExpect(jsonPath("$.description", is("The id filed must be empty.")));
	}
	
	@Test
	public void createUserErrorUserNameTest() throws Exception {
		mockMvc.perform(
				post("/user")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(getUserErrorUserName())
			)
        	.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
        	.andExpect(jsonPath("$.responseCode", is(400)))
	    	.andExpect(jsonPath("$.description", is("The username field must not be empty.")));
	}
	
	@Test
	public void deleteUserByIdTest() throws Exception {
		mockMvc.perform(
				delete("/user")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(getUserString())
			)
    		.andExpect(status().isOk())
        	.andExpect(jsonPath("$.responseCode", is(200)))
	    	.andExpect(jsonPath("$.description", is("OK")))
	    	.andExpect(jsonPath("$.result", is("User deleted")));
	}
	
	@Test
	public void deleteUserByIdErrorTest() throws Exception {
		when(userRepository.findByUserId(any())).thenReturn(null);
		mockMvc.perform(
				delete("/user")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(getUserString())
			)
    		.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
    		.andExpect(jsonPath("$.responseCode", is(400)))
	    	.andExpect(jsonPath("$.description", is("The user does not exist.")));
	}
	
	private String getUserSearch() throws JsonProcessingException {
		UserTO user = new UserTO();
		user.setEmail("prueba@prueba.cl");
		return objectMapper.writeValueAsString(user);
	}
	
	private String getUserSearchError() throws JsonProcessingException {
		UserTO user = new UserTO();
		return objectMapper.writeValueAsString(user);
	}
	
	private String getNewUser() throws JsonProcessingException {
		return objectMapper.writeValueAsString(getUser(null));
	}
	
	private String getUserErrorId() throws JsonProcessingException {
		return objectMapper.writeValueAsString(getUser());
	}
	
	private String getUserErrorUserName() throws JsonProcessingException {
		User user = getUser(null);
		user.setUserName(null);
		return objectMapper.writeValueAsString(user);
	}
	
	private String getUserString() throws JsonProcessingException {
		return objectMapper.writeValueAsString(getUser());
	}
	
	private User getUser() {
		return getUser(1);
	}
	
	private User getUser(Integer id) {
		User user = new User();
		user.setUserId(id);
		user.setUserName("prueba");
		user.setName("Prueba");
		user.setPhone(987654321);
		user.setEmail("prueba@prueba.cl");
		return user;
	}

	private List<User> listUsers(){
		List<User> users = new ArrayList<>();
		users.add(getUser());
		return users;
	}
}
