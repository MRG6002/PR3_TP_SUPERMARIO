// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Box extends GameObject{
	private static final String NAME = Messages.BOX_NAME;
	private static final String SHORTCUT = Messages.BOX_SHORTCUT;
	private boolean isOpen;

	public Box(Position position, GameWorld game) {
		this(position, game, false);
	}
	
	private Box(Position position, GameWorld game, boolean open) {
		super(position, game, NAME, SHORTCUT);
		this.isOpen = open;
	}
	
	Box(){
		this(null, null);
	}
	
	@Override
	public GameObject newCopy(Position pos, GameWorld game) {
		return new Box(pos, game);
	}
	
	@Override
	public boolean isSolid() {return true;}
	
	@Override
	public boolean interactWith(GameItem item) {
		boolean interaction = item.isInPosition(this.position.go(Action.DOWN)) && !this.isOpen;
		if(interaction) {item.receiveInteraction(this);}
		return interaction;
	}
	
	@Override 
	public boolean receiveInteraction(Mario mario) {
		this.isOpen = true;
		this.game.add(new Mushroom(this.position.go(Action.UP), this.game));
		return false;
	}
	
	@Override 
	public GameObject parse(String objWords[], GameWorld game) {
		GameObject box = super.parse(objWords, game);	
		if(box == null && objWords.length == 3 && matchObjectName(objWords[1])) {
			Position pos = Position.stringToPosition(objWords[0]);
			if(pos != null && correctWord(objWords[2])) box = new Box(pos, game, emptyBox(objWords[2]));
		}
	return box;
	}
	
	private boolean correctWord(String string) {
		return string.equalsIgnoreCase("full") || string.equalsIgnoreCase("f") || emptyBox(string);
	}
	
	private boolean emptyBox(String string) {
		return string.equalsIgnoreCase("empty") || string.equalsIgnoreCase("e");
	}
	
	@Override
	public String getIcon() {
		return this.isOpen ? Messages.EMPTY_BOX:Messages.BOX;
		
	}

	@Override
	public String toString() {
	return "BOX: " + this.position.toString() + " SOLID";
	}

}
