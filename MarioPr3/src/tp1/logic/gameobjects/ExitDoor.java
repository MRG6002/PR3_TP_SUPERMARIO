// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class ExitDoor extends GameObject {

	public ExitDoor(Position position, GameWorld game) {
		super(position, game, "exitdoor", "ed");
	}
	
	ExitDoor() {
		super(null, null, "exitdoor", "ed");
	}
	
	@Override
	public String getIcon() {
	return Messages.EXIT_DOOR;
	}
	
	@Override
	public String toString() {
	return "EXITDOOR: " + this.position.toString() + " NOT SOLID";
	}
	
	public boolean interactWith(GameItem item) {
		boolean interaction = item.isInPosition(this.position);
		if(interaction) {
			item.receiveInteraction(this);
		}
		return interaction;
	}
	
	@Override
	public GameObject newCopy(Position pos, GameWorld game){
		return new ExitDoor(pos, game);
	}
}
