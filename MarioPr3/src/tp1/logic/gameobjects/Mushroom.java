// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject{
	private static final String NAME = Messages.MUSHROOM_NAME;
	private static final String SHORTCUT = Messages.MUSHROOM_SHORTCUT;

	public Mushroom(Position position, GameWorld game) {
		this(position, game, Action.RIGHT);
	}
	
	private Mushroom(Position pos, GameWorld game, Action dir) {
		super(pos, game, dir, NAME, SHORTCUT);
	}
	
	Mushroom (){
		this(null, null);
	}
	
	@Override
	public GameObject newCopy(Position pos, GameWorld game) {
		return new Mushroom(pos, game);
	}
	
	@Override
	protected GameObject newCopy(Position pos, GameWorld game, Action dir) {
		return new Mushroom(pos, game, dir);
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
