// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;

import tp1.view.Messages;

public class Mushroom extends MovingObject {
	private static final String NAME = Messages.GAMEOBJECT_MUSHROOM_NAME;
	private static final String SHORTCUT = Messages.GAMEOBJECT_MUSHROOM_SHORTCUT;
	
	private Mushroom(Position position, GameWorld game, Action direction) {
		super(position, NAME, SHORTCUT, game, direction);
	}
	
	public Mushroom(Position position, GameWorld game) {
		this(position, game, Action.RIGHT);
	}
	
	Mushroom() {
		this(null, null, null);
	}

	@Override
	public String getIcon() {
	return Messages.MUSHROOM;
	}
	
	@Override
	protected Position headPosition() {
	return this.position.go(Action.STOP);
	}
	
	@Override
	public boolean interactWith(GameItem gameItem) {
		boolean canInteract = this.isAlive() && gameItem.isAlive() && gameItem.isInPosition(this.position) && gameItem.receiveInteraction(this);
		
		if(canInteract) super.dead();
	return canInteract;
	}

	@Override
	Mushroom newInstance(Position position, GameWorld game) {
	return new Mushroom(position, game, Action.RIGHT);
	}
	
	@Override
	public Mushroom parse(String[] objectWords, GameWorld game) throws OffBoardException, ObjectParseException {
		Mushroom mushroom = (Mushroom) super.parse(objectWords, game);
		
		if(mushroom != null && 3 < objectWords.length) throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objectWords)));
	return mushroom;
	}
}
