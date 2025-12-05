// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

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
	
	public GameObject parse(String[] objectWords, GameWorld game) throws OffBoardException, ObjectParseException {
		try {
			GameObject gameObject = null;
			
			if(2 <= objectWords.length && this.matchObjectName(objectWords[1])) {
				Position position = Position.parseString(objectWords[0]);
				
				if(position.isValid()) gameObject = newInstance(position, game);
				else throw new OffBoardException(Messages.OBJECT_POSITION_OFF_BOARD.formatted(String.join(" ", objectWords)));
			}
			return gameObject;
		}
		catch(PositionParseException ppe) {
			throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objectWords)), ppe);
		}
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
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(this.position.toString()).append(Messages.SPACE).append(this.name);
	return stringBuilder.toString();
	};
	
	public GameObject newCopy() {
	return newInstance(this.position.newCopy(), game);	
	}
	
	public abstract void update();
	public abstract String getIcon();
	abstract GameObject newInstance(Position position, GameWorld game); 
}
