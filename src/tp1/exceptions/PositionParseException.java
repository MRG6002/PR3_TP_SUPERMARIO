package tp1.exceptions;

public class PositionParseException extends GameParseException {
	private static final long serialVersionUID = 1L;

	public PositionParseException() {
	}

	public PositionParseException(String message) {
		super(message);
	}

	public PositionParseException(Throwable cause) {
		super(cause);
	}

	public PositionParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public PositionParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
