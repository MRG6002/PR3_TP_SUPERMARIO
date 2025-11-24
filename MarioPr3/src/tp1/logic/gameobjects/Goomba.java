// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.Action;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Goomba extends MovingObject {
	
	public Goomba(Position position, GameWorld game) {
		super(position, game, Action.LEFT, "goomba", "g");
	}
	
	private Goomba(Position position, GameWorld game, Action dir) {
		super(position, game, dir, "goomba", "g");
	}

	Goomba() {
		super(null, null, Action.LEFT, "goomba", "g");
	}

	@Override
	public String getIcon() {
	return Messages.GOOMBA;
	}
	
	@Override
	public String toString() {
	return "GOOMBA: " + super.toString() + " NOT SOLID";
	}
	
	public boolean interactWith(GameItem item) {return true;}
	
	@Override
	public  boolean receiveInteraction(Mario obj) {
		super.dead();
		obj.receiveInteraction(this);
	return false;
	}
	
	@Override
	public Goomba parse(String objWords[], GameWorld game) {
		Goomba goomba = null;	
		if(objWords.length >= 2 && matchObjectName(objWords[1])) {
			Position pos = Position.stringToPosition(objWords[0]);
			if(pos != null) {
				if(objWords.length == 2) goomba = new Goomba(pos, game);
				else if(objWords.length == 3) {
					Action dir = Action.parseAction(objWords[2]);
					if(dir == Action.RIGHT || dir == Action.LEFT) goomba = new Goomba(pos, game, dir);
				}
			}
		}
	return goomba;
	}
}
