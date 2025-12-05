// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
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
	public GameObject newObject(Position pos, GameWorld game) {
		return new Goomba(pos, game);
	}
	
	@Override
	public Goomba newCopy() {
		return (Goomba)super.newCopy();
	}

	@Override
	public String getIcon() {
	return Messages.GOOMBA;
	}
	
	public boolean interactWith(GameItem item) {return true;}
	
	@Override
	public  boolean receiveInteraction(Mario obj) {
		super.dead();
		obj.receiveInteraction(this);
	return false;
	}
	
	@Override 
	public Goomba parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		Goomba goomba = (Goomba) super.parse(objWords, game);
		if (goomba != null && objWords.length > 3)
	 		throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objWords)));
		return goomba;
	}
}
