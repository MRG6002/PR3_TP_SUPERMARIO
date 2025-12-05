package tp1.exceptions;

public class ObjectParseException extends GameParseException {
	private static final long serialVersionUID = 1L;

	public ObjectParseException() {
	}

	public ObjectParseException(String message) {
		super(message);
	}

	public ObjectParseException(Throwable cause) {
		super(cause);
	}

	public ObjectParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
