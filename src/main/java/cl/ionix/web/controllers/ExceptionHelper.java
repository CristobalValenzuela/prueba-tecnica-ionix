package cl.ionix.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cl.ionix.web.execptions.ExternalApiException;
import cl.ionix.web.execptions.UserException;
import cl.ionix.web.to.ResponseApiTO;

@ControllerAdvice
public class ExceptionHelper {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);
	
	@ExceptionHandler(value = { ExternalApiException.class })
	public ResponseEntity<ResponseApiTO<Object>> handleException(ExternalApiException ex) {
		logger.error("External Api Exception: ", ex.getLocalizedMessage());
		ResponseApiTO<Object> response = new ResponseApiTO<>();
		response.setDescription(ex.getMessage());
		response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseApiTO<Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { UserException.class })
	public ResponseEntity<ResponseApiTO<Object>> handleException(UserException ex) {
		logger.error("User Exception: ", ex.getLocalizedMessage());
		ResponseApiTO<Object> response = new ResponseApiTO<>();
		response.setDescription(ex.getMessage());
		response.setResponseCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseApiTO<Object>>(response, HttpStatus.BAD_REQUEST);
	}
}
