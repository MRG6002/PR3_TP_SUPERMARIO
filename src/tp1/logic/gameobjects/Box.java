// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;

import tp1.view.Messages;

public class Box extends GameObject {
	private static final String NAME = Messages.GAMEOBJECT_BOX_NAME;
	private static final String SHORTCUT = Messages.GAMEOBJECT_BOX_SHORTCUT;
	private static final int POINTS = 50;
	
	private boolean full;
	
	private Box(Position position, GameWorld game, boolean full) {
		super(position, NAME, SHORTCUT, game);
		this.full = full;
	}
	
	public Box(Position position, GameWorld game) {
		this(position, game, true);
	}
	
	Box() {
		this(null, null, true);
	}
	
	@Override
	public boolean isSolid() {
	return true;
	}

	@Override
	public void update() {
	}

	@Override
	public String getIcon() {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(this.full) stringBuilder.append(Messages.BOX);
		else stringBuilder.append(Messages.EMPTY_BOX);
	return stringBuilder.toString();
	}
	
	@Override
	public boolean interactWith(GameItem gameItem) { // MODIFICAR
		boolean canInteract = this.isAlive() && gameItem.isAlive() && this.full && gameItem.isInPosition(this.position.go(Action.DOWN)) && gameItem.receiveInteraction(this);
		boolean doInteract = false;
		
		if(canInteract) {
			Position position = this.position.go(Action.UP);
			
			if(!this.game.isSolid(position) && position.isValid()) {
				doInteract = true;
				this.game.add(new Mushroom(position, this.game));
				this.full = false; 
				this.game.addPoints(POINTS);
			}
		}
	return canInteract && doInteract;
	}
	
	@Override
	Box newInstance(Position position, GameWorld game) {
	return new Box(position, game, true);
	}
	
	private boolean validState(String state) {
	return state.equalsIgnoreCase("full") || state.equalsIgnoreCase("f") || state.equalsIgnoreCase("empty") || state.equalsIgnoreCase("e");
	}
		
	private void updateState(String state) {
		if(state.equalsIgnoreCase("empty") || state.equalsIgnoreCase("e")) this.full = false;
	}
	
	@Override
	public Box parse(String[] objectWords, GameWorld game) throws OffBoardException, ObjectParseException {
		Box box = (Box) super.parse(objectWords, game);
		
		if(box != null && 2 < objectWords.length) {
			if(objectWords.length == 3) {
				if(this.validState(objectWords[2])) box.updateState(objectWords[2]);
				else throw new ObjectParseException(Messages.INVALID_BOX_STATUS.formatted(String.join(" ", objectWords)));
			}
			else throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objectWords)));
		}
	return box;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(super.toString()).append(Messages.SPACE);
		if(this.full) stringBuilder.append("FULL");
		else stringBuilder.append("EMPTY");
	return stringBuilder.toString();
	}
	
	private void updateState(boolean full) {
		this.full = full;
	}
	
	@Override
	public Box newCopy() {
		Box box = (Box) super.newCopy();
		
		box.updateState(this.full);
	return box;
	}
}
