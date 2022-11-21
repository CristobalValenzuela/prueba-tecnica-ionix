package cl.ionix.web.controllers;

import org.springframework.http.HttpStatus;

import cl.ionix.web.to.ResponseApiTO;

public class BaseController {

	public <T> ResponseApiTO<T> generateRespuesta(T object, long start) {
		long finish = System.currentTimeMillis();
		ResponseApiTO<T> response = new ResponseApiTO<>();
		response.setElapsedTime(finish - start);
		response.setHttpStatus(HttpStatus.OK);
		response.setResult(object);
		return response;
	}
}
