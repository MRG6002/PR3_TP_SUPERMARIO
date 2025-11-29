// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic;

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
	
	public static Action parseAction(String commandWord) {
		Action action = null;
		if(commandWord.equalsIgnoreCase("l") || commandWord.equalsIgnoreCase("left")) action = Action.LEFT;
		else if(commandWord.equalsIgnoreCase("r") || commandWord.equalsIgnoreCase("right")) action = Action.RIGHT;
		else if(commandWord.equalsIgnoreCase("d") || commandWord.equalsIgnoreCase("down")) action = Action.DOWN;
		else if(commandWord.equalsIgnoreCase("u") || commandWord.equalsIgnoreCase("up")) action = Action.UP;
		else if(commandWord.equalsIgnoreCase("s") || commandWord.equalsIgnoreCase("stop")) action = Action.STOP;
	return action;
	}
	
	public Action opposite() {
		Action aux = null;
		
		if(this == Action.LEFT) aux = Action.RIGHT;
		else if(this == Action.RIGHT) aux = Action.LEFT;
		else if(this == Action.UP) aux = Action.DOWN;
		else if(this == Action.DOWN) aux = Action.UP;
	return aux;
	}
}
