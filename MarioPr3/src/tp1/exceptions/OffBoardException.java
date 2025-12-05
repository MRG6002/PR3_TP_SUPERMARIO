// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.exceptions;

public class OffBoardException extends GameModelException {
	private static final long serialVersionUID = 1L;

	public OffBoardException() {
	}

	public OffBoardException(String message) {
		super(message);
	}

	public OffBoardException(Throwable cause) {
		super(cause);
	}

	public OffBoardException(String message, Throwable cause) {
		super(message, cause);
	}

	public OffBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
