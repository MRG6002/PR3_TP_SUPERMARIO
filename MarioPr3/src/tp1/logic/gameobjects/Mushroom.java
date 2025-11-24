// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject{

	public Mushroom(Position position, GameWorld game) {
		super(position, game, Action.RIGHT, "mushroom", "mu");
	}
	
	private Mushroom(Position pos, GameWorld game, Action dir) {
		super(pos, game, dir, "mushroom", "mu");
	}
	
	Mushroom (){
		super(null, null, Action.RIGHT, "mushroom", "mu");
	}

	@Override
	public boolean interactWith(GameItem item) {
		boolean interaction = item.isInPosition(this.position);
		if(interaction) { item.receiveInteraction(this);}
		return interaction;
	}
	
	@Override
	public boolean receiveInteraction(Mario mario) {
		this.dead();
		return true;
	}
	
	@Override 
	public Mushroom parse(String objWords[], GameWorld game) {
		Mushroom goomba = null;	
		if(objWords.length >= 2 && matchObjectName(objWords[1])) {
			Position pos = Position.stringToPosition(objWords[0]);
			if(pos != null) {
				if(objWords.length == 2) goomba = new Mushroom(pos, game);
				else if(objWords.length == 3) {
					Action dir = Action.parseAction(objWords[2]);
					boolean correctDir = (dir == Action.RIGHT || dir == Action.LEFT);
					if(correctDir) goomba = new Mushroom(pos, game, dir);
				}
			}
		}
	return goomba;
	}
	
	@Override
	public String getIcon() {
		return Messages.MUSHROOM;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("MUSHROOM: ").append(super.toString());
	return stringBuilder.toString();
	}
}
