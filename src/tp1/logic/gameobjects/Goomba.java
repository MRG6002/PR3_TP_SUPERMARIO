// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Goomba extends MovingObject {
	private static final String NAME = Messages.GAMEOBJECT_GOOMBA_NAME;
	private static final String SHORTCUT = Messages.GAMEOBJECT_GOOMBA_SHORTCUT;
	private static final int POINTS = 100;

	private Goomba(Position position, GameWorld game, Action direction) {
		super(position, NAME, SHORTCUT, game, direction);
	}
	
	public Goomba(Position position, GameWorld game) {
		this(position, game, Action.LEFT);
	}
	
	Goomba() {
		this(null, null, null);
	}
	
	@Override
	public String getIcon() {
	return Messages.GOOMBA;
	}
	
	@Override
	protected Position headPosition() {
	return this.position.go(Action.STOP);
	}
	
	@Override
	public boolean interactWith(GameItem gameItem) {
		boolean canInteract = this.isAlive() && gameItem.isAlive() && gameItem.isInPosition(this.position) && gameItem.receiveInteraction(this);
		
		if(canInteract) {
			super.dead();
			this.game.addPoints(POINTS);
		}
	return canInteract;
	}

	@Override
	Goomba newInstance(Position position, GameWorld game) {
	return new Goomba(position, game, Action.LEFT);
	}
	
	@Override
	public Goomba parse(String[] objectWords, GameWorld game) throws OffBoardException, ObjectParseException {
		Goomba goomba = (Goomba) super.parse(objectWords, game);
		
		if(goomba != null && 3 < objectWords.length) throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objectWords)));
	return goomba;
	}
}
