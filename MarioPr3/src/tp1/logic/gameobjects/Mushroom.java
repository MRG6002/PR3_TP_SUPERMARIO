// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject{
	private static final String NAME = Messages.MUSHROOM_NAME;
	private static final String SHORTCUT = Messages.MUSHROOM_SHORTCUT;

	public Mushroom(Position position, GameWorld game) {
		super(position, game, Action.RIGHT, NAME, SHORTCUT);
	}
	
	Mushroom (){
		this(null, null);
	}
	
	@Override
	GameObject newObject(Position pos, GameWorld game) {
		return new Mushroom(pos, game);
	}
	
	@Override
	public Mushroom newCopy() {
		return (Mushroom)super.newCopy();
	}
	
	@Override 
	public Mushroom parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		Mushroom mushroom = (Mushroom) super.parse(objWords, game);
		if (mushroom != null && objWords.length > 3)
	 		throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objWords)));
		return mushroom;
	}

	@Override
	public boolean interactWith(GameItem item) {return true;}
	
	@Override
	public boolean receiveInteraction(Mario mario) {
		this.dead();
		mario.receiveInteraction(this);
		return true;
	}
	
	@Override
	public String getIcon() {
		return Messages.MUSHROOM;
	}
}
