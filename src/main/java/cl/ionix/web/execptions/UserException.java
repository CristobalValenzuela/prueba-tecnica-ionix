package cl.ionix.web.execptions;

public class UserException extends Exception {
	
	private static final long serialVersionUID = -5927962301389480658L;

	public UserException(Throwable ex) {
		super(ex);
	}
	
	public UserException(String error) {
		super(error);
	}
}
