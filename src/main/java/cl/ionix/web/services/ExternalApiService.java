package cl.ionix.web.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.ionix.web.execptions.EncryptedException;
import cl.ionix.web.execptions.ExternalApiException;
import cl.ionix.web.to.ExternalApiTO;
import cl.ionix.web.to.ResponseApiTO;
import cl.ionix.web.to.ResultTO;

@Service
public class ExternalApiService {
	
	private Logger logger = LoggerFactory.getLogger(ExternalApiService.class);

	@Value("${key.ionix}")
	private String keyIonix;
	
	@Value("${url.api.externa}")
	private String URL_EXTERNA;
	
	@Value("${url.api.key}")
	private String API_KEY;

	public ExternalApiTO callExternalApi(String param) throws ExternalApiException {
		try {
			String encryptedParam = getEncryptedParam(param);
			RestTemplate restTemplate = new RestTemplate();
			String url = String.format("%s%s", URL_EXTERNA, encryptedParam);
			logger.info(String.format("URL de peticion externa [%s]", url));
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-API-Key", API_KEY);
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			ResponseEntity<ResponseApiTO<ResultTO>> response = restTemplate.exchange(
				    url, HttpMethod.GET, requestEntity,  new ParameterizedTypeReference<ResponseApiTO<ResultTO>>() {});
			logger.info(response.toString());
			ExternalApiTO externalApiTO = new ExternalApiTO();
			externalApiTO.setRegisterCount(response.getBody().getResult().getItems().size());
			return externalApiTO;
		}catch (Exception e) {
			logger.error("Error al llamar la API externa");
			throw new ExternalApiException(e);
		}
	}

	private String getEncryptedParam(String param) throws EncryptedException {
		try {
			DESKeySpec keySpec = new DESKeySpec(keyIonix.getBytes("UTF8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(keySpec);
			byte[] cleartext = param.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			String encryptedParam = Base64.getEncoder().encodeToString(cipher.doFinal(cleartext));
			logger.info(String.format("RUT a cifrar [%s]", param));
			logger.info(String.format("Llave de cifrado [%s]", keyIonix));
			logger.info(String.format("Parametro cifgrado [%s]", encryptedParam));
			return encryptedParam;
		} catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException
				| UnsupportedEncodingException | NoSuchPaddingException | InvalidKeySpecException e) {
			throw new EncryptedException(e);
		}
	}
}
