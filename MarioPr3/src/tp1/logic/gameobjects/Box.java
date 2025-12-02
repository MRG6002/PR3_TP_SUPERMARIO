// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
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
	public GameObject newObject(Position pos, GameWorld game) {
		return new Box(pos, game);
	}
	
	@Override
	public GameObject newCopy(Position pos, GameWorld game) {
		return new Box(pos, game, this.isOpen);
	}
	
	@Override
	public boolean isSolid() {return true;}
	
	@Override
	public boolean interactWith(GameItem item) {
		boolean interaction = !this.isOpen && item.isInPosition(this.position.go(Action.DOWN)) && this.bothAlive(item);
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
	public Box parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException{
		if (objWords.length > 3 && matchObjectName(objWords[1]))
	 		throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objWords)));

		Box box = (Box) super.parse(objWords, game);
		if(box != null && objWords.length >= 2){
			if(correctWord(objWords[2])) this.isOpen = emptyBox(objWords[2]);
			else throw new ObjectParseException(Messages.INVALID_BOX_STATUS.formatted(String.join(" ", objWords)));
		}
		return box;
	}
	
	private boolean correctWord(String string) {
		return string.equalsIgnoreCase(Messages.BOX_STATUS_FULL) 
				|| string.equalsIgnoreCase(Messages.BOX_STATUS_FULL_SC) || emptyBox(string);
	}
	
	private boolean emptyBox(String string) {
		return string.equalsIgnoreCase(Messages.BOX_STATUS_EMPTY) 
				|| string.equalsIgnoreCase(Messages.BOX_STATUS_EMPTY_SC);
	}
	
	@Override
	public String getIcon() {
		return this.isOpen ? Messages.EMPTY_BOX:Messages.BOX;
		
	}

	@Override
	public String toString() {
	String status = "";
	if(this.isOpen) status = Messages.SPACE + Messages.BOX_STATUS_EMPTY;
	return super.toString() + status;
	}
}
