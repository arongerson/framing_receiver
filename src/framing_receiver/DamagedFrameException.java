package framing_receiver;

public class DamagedFrameException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	public DamagedFrameException(String message) {
		super(message);
	}
}
