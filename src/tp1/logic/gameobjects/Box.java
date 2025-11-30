// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import java.util.Arrays;

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
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("BOX: ").append(this.position.toString()).append(" SOLID ");
		if(this.full) stringBuilder.append("FULL");
		else stringBuilder.append("EMPTY");
	return stringBuilder.toString();
	}
	
	@Override
	public boolean interactWith(GameItem gameItem) {
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
	
	private int sizedTo(String[] objectWords) {
		int to = objectWords.length;
		
		if(to == 3) to--;
	return to;
	}
	
	private boolean validState(String state) {
	return state.equalsIgnoreCase("full") || state.equalsIgnoreCase("f") || state.equalsIgnoreCase("empty") || state.equalsIgnoreCase("e");
	}
		
	private void updateState(String state) {
		if(state.equalsIgnoreCase("empty") || state.equalsIgnoreCase("e")) this.full = false;
	}
	
	@Override
	public Box parse(String[] objectWords, GameWorld game) {
		int to = this.sizedTo(objectWords);
		Box box = (Box) super.parse(Arrays.copyOfRange(objectWords, 0, to), game);
		
		if(box != null && objectWords.length == 3) {
			if(this.validState(objectWords[2])) box.updateState(objectWords[2]);
			else box = null;
		}
	return box;
	}
}
