// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;

public abstract class GameObject implements GameItem {
	protected Position position;
	private final String name;
	private final String shortcut;
	protected GameWorld game; 
	private boolean isAlive;
	
	public GameObject(Position position, String name, String shortcut, GameWorld game) {
		this.position = position;
		this.name = name;
		this.shortcut = shortcut;
		this.game = game;
		this.isAlive = true;
	}
	
	protected String getName() {
	return this.name;
	}
		
	protected String getShortcut() {
	return this.shortcut; 
	}
	
	protected boolean matchObjectName(String name) {
	return getShortcut().equalsIgnoreCase(name) || getName().equalsIgnoreCase(name);
	}
	
	public GameObject parse(String[] objectWords, GameWorld game) {
		GameObject gameObject = null;
		
		if(objectWords.length == 2 && this.matchObjectName(objectWords[1])) {
			Position position = Position.parseString(objectWords[0]);
			
			if(position != null) gameObject = newInstance(position, game);
		}
	return gameObject;
	}
	
	public boolean isInPosition(Position position) {
	return this.isAlive && this.position.equals(position);
	}
 	
	public boolean isAlive() {
	return this.isAlive;
	}
	
	public void dead(){
		this.isAlive = false;
	}
	
	// Not mandatory but recommended
	protected void move(Action direction) {
		this.position = this.position.go(direction);
		this.game.doInteractionsFrom(this);
	}
	
	@Override
	public boolean interactWith(GameItem gameItem) {
	return false;
	}
	
	@Override
	public boolean receiveInteraction(Land land) {
	return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor exitDoor) {
	return false;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
	return false;
	}

	@Override
	public boolean receiveInteraction(Goomba goomba) {
	return false;
	}
	
	@Override
	public boolean receiveInteraction(Mushroom mushroom) {
	return false;
	}
	
	@Override
	public boolean receiveInteraction(Box box) {
	return false;
	}
	
	public abstract void update();
	public abstract String getIcon();
	public abstract String toString();
	abstract GameObject newInstance(Position position, GameWorld game); 
}
