// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

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
	
	public abstract GameObject newObject(Position pos, GameWorld game);
	
	public GameObject newCopy() {
		return this.newCopy(this.position.copy(), this.game);
	}
	
	public GameObject newCopy(Position pos, GameWorld game) {
		return this.newObject(pos, game);
	}
	
	public void update() {};
	public boolean isSolid() {return false;}
	public boolean receiveInteraction(Land obj) {return false;}
	public boolean receiveInteraction(ExitDoor obj) {return false;}
	public boolean receiveInteraction(Mario obj) {return false;}
	public boolean receiveInteraction(Goomba obj) {return false;}
	public boolean receiveInteraction(Mushroom obj) {return false;}
	public boolean receiveInteraction(Box obj) {return false;}
	
	public boolean bothAlive(GameItem item) { return this.isAlive() && item.isAlive();}

	public String toString() {
		return this.position.toString() +Messages.SPACE+ this.getName();
	}
	
	public GameObject parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		if (objWords.length > 2 && matchObjectName(objWords[1]))
	 		throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objWords)));
		
		try {
			
			GameObject obj = null;
			if(objWords.length == 2 && matchObjectName(objWords[1])) {
				Position pos = Position.stringToPosition(objWords[0]);
				obj = this.newObject(pos, game);
			}
			return obj; 
			
		} catch (OffBoardException obe){
			throw new OffBoardException(Messages.POSITION_OUT_OF_BOUNDS.formatted(String.join(" ", objWords)));
		} catch (PositionParseException ppe) {
			throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe);
		}
		
	}

	public void connect() {}
}
