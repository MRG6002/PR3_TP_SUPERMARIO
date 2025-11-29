// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Land extends GameObject {
	private static final String NAME = Messages.LAND_NAME;
	private static final String SHORTCUT = Messages.LAND_SHORTCUT;
	
	public Land(Position position, GameWorld game) {
		super(position, game, NAME, SHORTCUT);
	}
	
	Land() {
		this(null, null);
	}

	@Override
	public boolean isSolid() {return true; }
	
	@Override
	public String getIcon() {
	return Messages.LAND;
	}
	
	@Override
	public String toString() {
	return "LAND: " + this.position.toString() + " SOLID";
	}
	
	public  boolean interactWith(GameItem item) {return false;}
	
	@Override
	public GameObject newCopy(Position pos, GameWorld game){
		return new Land(pos, game);
	}
	
	
}
