package cl.ionix.web.execptions;

public class ExternalApiException extends Exception {
	
	private static final long serialVersionUID = -551117610157691762L;

	public ExternalApiException(Throwable ex) {
		super(ex);
	}

}
