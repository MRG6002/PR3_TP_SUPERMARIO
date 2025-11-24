// Grupo 13: XiangLin - MarioRosellGarcia 

package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Box extends GameObject{
	private boolean isOpen;

	public Box(Position position, GameWorld game) {
		super(position, game, "box", "b");
		this.isOpen = false;
	}
	
	private Box(Position position, GameWorld game, boolean open) {
		super(position, game, "box", "b");
		this.isOpen = open;
	}
	
	Box(){
		super(null, null, "box", "b");
		this.isOpen = false;
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
		this.game.addDelayed(new Mushroom(this.position.go(Action.UP), this.game));
		return false;
	}
	
	@Override 
	public Box parse(String objWords[], GameWorld game) {
		Box box = null;	
		if(objWords.length >= 2 && matchObjectName(objWords[1])) {
			Position pos = Position.stringToPosition(objWords[0]);
			if(pos != null) {
				if(objWords.length == 2) box = new Box(pos, game);
				else if(objWords.length == 3) {
					if (fullBox(objWords[3])) box = new Box(pos, game);
					else if(emptyBox(objWords[3])) box = new Box(pos, game, true);
				}
			}
		}
	return box;
	}
	
	private boolean fullBox(String string) {
		return string.equalsIgnoreCase("full") || string.equalsIgnoreCase("f");
	}
	
	private boolean emptyBox(String string) {
		return string.equalsIgnoreCase("empty") || string.equalsIgnoreCase("e");
	}
	
	@Override
	public String getIcon() {
		String string = "";
		if(this.isOpen) string = Messages.EMPTY_BOX;
		else string = Messages.BOX;
		return string;
	}

	@Override
	public String toString() {
	return "BOX: " + this.position.toString() + " SOLID";
	}

}
