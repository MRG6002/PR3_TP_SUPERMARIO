// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(0 ,-1), RIGHT(0, 1), DOWN(1, 0), UP(-1, 0), STOP(0, 0);
	
	private int x;
	private int y;
	
	private Action(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
	return x;
	}

	public int getY() {
	return y;
	}
	
	public static Action parseAction(String commandWord) throws ActionParseException {
		Action action = null;
		
		if(commandWord.equalsIgnoreCase("l") || commandWord.equalsIgnoreCase("left")) action = Action.LEFT;
		else if(commandWord.equalsIgnoreCase("r") || commandWord.equalsIgnoreCase("right")) action = Action.RIGHT;
		else if(commandWord.equalsIgnoreCase("d") || commandWord.equalsIgnoreCase("down")) action = Action.DOWN;
		else if(commandWord.equalsIgnoreCase("u") || commandWord.equalsIgnoreCase("up")) action = Action.UP;
		else if(commandWord.equalsIgnoreCase("s") || commandWord.equalsIgnoreCase("stop")) action = Action.STOP;
		else throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(commandWord));
	return action;
	}
	
	public static Action opposite(Action action) {
		Action aux = Action.STOP;
		
		if(action == Action.LEFT) aux = Action.RIGHT;
		else if(action == Action.RIGHT) aux = Action.LEFT;
		else if(action == Action.UP) aux = Action.DOWN;
		else if(action == Action.DOWN) aux = Action.UP;
	return aux;
	}
}
