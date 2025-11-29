// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;

public abstract class GameObject implements GameItem{
	protected Position position; // If you can, make it private
	protected GameWorld game; 
	private boolean isAlive;
	private String name;
	private String shortcut;
	
	public GameObject(Position position, GameWorld game, String name, String shortcut) {
		this.position = position;
		this.game = game;
		this.isAlive = true;
		this.name = name;
		this.shortcut = shortcut;
	}
	
	public boolean isInPosition(Position position) {
	return this.position.equals(position);
	}
	
	protected boolean fallen() {
		return this.position.isBorder();
	}
 	
	public boolean isAlive() {
	return this.isAlive;
	}
	
	public void dead(){
		this.isAlive = false;
	}
	
	protected void move(Action direction) {
		this.position = this.position.go(direction);
		this.game.doInteractionsFrom(this);
	}
	
	protected String getName() {
	return name;
	}
		
	protected String getShortcut() {
	return shortcut; 
	}
	
	protected boolean matchObjectName(String name) {
	return getShortcut().equalsIgnoreCase(name) || getName().equalsIgnoreCase(name);
	}
	
	public abstract String getIcon();
	public abstract String toString();
	public abstract GameObject newCopy(Position pos, GameWorld game);
	
	public void update() {};
	public boolean isSolid() {return false;}
	public boolean receiveInteraction(Land obj) {return false;}
	public boolean receiveInteraction(ExitDoor obj) {return false;}
	public boolean receiveInteraction(Mario obj) {return false;}
	public boolean receiveInteraction(Goomba obj) {return false;}
	public boolean receiveInteraction(Mushroom obj) {return false;}
	public boolean receiveInteraction(Box obj) {return false;}

	public GameObject parse(String objWords[], GameWorld game) {
		GameObject obj = null;
		if(objWords.length == 2 && matchObjectName(objWords[1])) {
			Position pos = Position.stringToPosition(objWords[0]);
			if(pos != null) obj = this.newCopy(pos, game);
		}
	return obj;
	}

	public void connect() {};
}
