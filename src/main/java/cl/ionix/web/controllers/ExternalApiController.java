package cl.ionix.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ionix.web.execptions.ExternalApiException;
import cl.ionix.web.services.ExternalApiService;
import cl.ionix.web.to.ExternalApiTO;
import cl.ionix.web.to.ResponseApiTO;

@RestController
@RequestMapping(path = "/externalapi")
public class ExternalApiController extends BaseController {

	@Autowired
	private ExternalApiService externalApiService;
	
	@PostMapping(path = "", produces = "application/json")
	public ResponseApiTO<ExternalApiTO> callExternalApi(@RequestParam String rut) throws ExternalApiException {
		long start = System.currentTimeMillis();
		ExternalApiTO externalApiTO = externalApiService.callExternalApi(rut);
		return generateRespuesta(externalApiTO, start);
	}
}
