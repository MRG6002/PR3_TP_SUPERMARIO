// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.exceptions;

public class ActionParseException extends GameParseException {
	private static final long serialVersionUID = 1L;

	public ActionParseException() {
	}

	public ActionParseException(String message) {
		super(message);
	}

	public ActionParseException(Throwable cause) {
		super(cause);
	}

	public ActionParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
