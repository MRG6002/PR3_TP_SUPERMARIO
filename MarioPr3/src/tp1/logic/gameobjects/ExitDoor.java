// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class ExitDoor extends GameObject {
	private static final String NAME = Messages.EXIT_DOOR_NAME;
	private static final String SHORTCUT = Messages.EXIT_DOOR_SHORTCUT;

	public ExitDoor(Position position, GameWorld game) {
		super(position, game,  NAME, SHORTCUT);
	}
	
	ExitDoor() {
		this(null, null);
	}
	
	@Override
	public String getIcon() {
	return Messages.EXIT_DOOR;
	}
	
	public boolean interactWith(GameItem item) {
		boolean interaction = item.isInPosition(this.position) && this.bothAlive(item);
		if(interaction) {
			item.receiveInteraction(this);
		}
		return interaction;
	}
	
	@Override
	public GameObject newObject(Position pos, GameWorld game){
		return new ExitDoor(pos, game);
	}
}
