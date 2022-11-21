package cl.ionix.web.execptions;

public class EncryptedException extends Exception {

	private static final long serialVersionUID = 1397219428961454366L;
	
	public EncryptedException( Throwable ex) {
		super(ex);
	}

}
