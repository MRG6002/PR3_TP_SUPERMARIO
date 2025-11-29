// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.Action;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Goomba extends MovingObject {
	private static final String NAME = Messages.GOOMBA_NAME;
	private static final String SHORTCUT = Messages.GOOMBA_SHORTCUT;
	
	public Goomba(Position position, GameWorld game) {
		this(position, game, Action.LEFT);
	}
	
	private Goomba(Position position, GameWorld game, Action dir) {
		super(position, game, dir, NAME, SHORTCUT);
	}

	Goomba() {
		this(null, null);
	}
	
	@Override
	public GameObject newCopy(Position pos, GameWorld game) {
		return new Goomba(pos, game);
	}
	
	@Override
	protected GameObject newCopy(Position pos, GameWorld game, Action dir) {
		return new Goomba(pos, game, dir);
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
}
