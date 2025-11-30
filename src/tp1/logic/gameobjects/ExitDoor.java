// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class ExitDoor extends GameObject {
	private static final String NAME = Messages.GAMEOBJECT_EXITDOOR_NAME;
	private static final String SHORTCUT = Messages.GAMEOBJECT_EXITDOOR_SHORTCUT;
	
	public ExitDoor(Position position, GameWorld game) {
		super(position, NAME, SHORTCUT, game);
	}
	
	ExitDoor() {
		this(null, null);
	}
	
	@Override
	public boolean isSolid() {
	return false;
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public String getIcon() {
	return Messages.EXIT_DOOR;
	}
	
	@Override
	public String toString() {
	return "EXITDOOR: " + this.position.toString() + " NOT SOLID";
	}
	
	@Override
	public boolean interactWith(GameItem gameItem) {
		boolean canInteract = this.isAlive() && gameItem.isAlive() && gameItem.isInPosition(this.position) && gameItem.receiveInteraction(this);
		
		if(canInteract) this.game.marioExited();
	return canInteract;
	}
	
	@Override
	ExitDoor newInstance(Position position, GameWorld game) {
	return new ExitDoor(position, game);
	}
}
