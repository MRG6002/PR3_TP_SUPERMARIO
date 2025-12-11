// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Mario extends MovingObject {
	private static final String NAME = Messages.MARIO_NAME;
	private static final String SHORTCUT = Messages.MARIO_SHORTCUT;
	private boolean big;
	private boolean headCollision;
	private boolean jumpedFromFloor;
	private ActionList actionList;

	public Mario(Position position, GameWorld game) {
		super(position, game, Action.RIGHT, NAME, SHORTCUT);
		this.headCollision = false;
		this.big = true;
		this.jumpedFromFloor = false;
		this.actionList = new ActionList();
	}
	
	Mario() {
		this(null, null);
	}
	
	@Override
	Mario newObject(Position pos, GameWorld game) {
		return new Mario(pos, game);
	}
	
	@Override
	public Mario newCopy() {
		Mario mario = (Mario)super.newCopy();
		mario.big = this.big;
		mario.headCollision = this.headCollision;
		mario.jumpedFromFloor = this.jumpedFromFloor;
		return mario;
	}
	
	//movimiento de Mario
	@Override
	public boolean isInPosition(Position position) {
	return (this.big && (this.position.go(Action.UP).equals(position))) || super.isInPosition(position);
	}
	
	@Override
	public void update() {
		Position position = this.position; 
		this.playerMovement();
		if(this.position.equals(position) && (!super.isInDirection(Action.STOP) || !this.game.isSolid(this.position.go(Action.DOWN)))) { 
			super.update();
			if(super.fallen()) this.game.marioDead(); 
		}
	}
	
	private void playerMovement() {
		this.jumpedFromFloor = false;
		for(Action action: this.actionList) {
			this.headCollision = false;
			if(action == Action.DOWN) actionDown();
			else if(action == Action.UP) actionUp();
			else if (action == Action.STOP) super.stop();
			else super.doAction(action);
		}
		this.actionList.clear();
	}
	
	private void actionDown() {
		if(this.game.isSolid(this.position.go(Action.DOWN))) super.stop();
		else {
			while(super.freeFalling()) 
			if(!super.isAlive()) this.game.marioDead();
		}
	}
	
	private void actionUp() {
		if(flightRestriction()) {
			super.up(); 
			this.jumpedFromFloor = true;
		}
	}
	
	private boolean flightRestriction() {
		return jumpedFromFloor || this.game.isSolid(this.position.go(Action.DOWN));
	}

	public void addAction(Action action) {
		this.actionList.addLast(action); 
	}
	
	@Override
	protected boolean headCollision() {
		if(big) {
			this.headCollision = this.game.isSolid(this.position.go(Action.UP).go(Action.UP)) 
					|| position.isBorder();
		}
		else this.headCollision = super.headCollision();
		return this.headCollision;
	}
	
	@Override
	protected boolean lateralCollision(Action action) {
		Position pos = this.position.go(Action.UP).go(action);
		return super.lateralCollision(action) || (this.big && (this.game.isSolid(pos) || pos.isBorder()));
	}
	
	//interacciones de Mario
	public  boolean interactWith(GameItem item) {
		boolean interaction = item.isInPosition(this.position) || (this.big && item.isInPosition(this.position.go(Action.UP)));
		if(interaction && this.bothAlive(item)) {item.receiveInteraction(this);}
		return interaction && this.bothAlive(item);
	}
	
	@Override
	public  boolean receiveInteraction(ExitDoor obj) {
		this.game.marioExited();
	return true;
	}
	
	@Override
	public  boolean receiveInteraction(Mushroom obj) {
		boolean interaction = obj.isInPosition(this.position) || (this.big && obj.isInPosition(this.position.go(Action.UP)));
		if(interaction) this.big = true;
	return interaction;
	}
	
	@Override
	public  boolean receiveInteraction(Box obj) {
		boolean interaction = this.headCollision && (obj.isInPosition(this.position.go(Action.UP)) 
				|| (this.big && obj.isInPosition(this.position.go(Action.UP).go(Action.UP))));
		if(interaction) { 
			obj.receiveInteraction(this);
			this.game.addPoints(50);
		}
	return interaction;
	}
	
	@Override
	public  boolean receiveInteraction(Goomba obj) {
		boolean interaction = obj.isInPosition(this.position) 
				|| (this.big && obj.isInPosition(this.position.go(Action.UP)));
		if(interaction) {
			if(this.big) {
				if(!super.isFalling()) this.big = false;
			}
			else {
				if(!super.isFalling()) {
					this.dead();
					this.game.marioDead();
				}
			}
			this.game.addPoints(100);
		}
	return interaction;
	}
	
	//strings y parse de Mario
	@Override
	public Mario parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		if (objWords.length > 4 && matchObjectName(objWords[1]))
	 		throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objWords)));

		Mario mario = (Mario) super.parse(objWords, game);
		if(mario != null && objWords.length > 3)	{
			if (validSize(objWords[3])) mario.big = isBig(objWords[3]);
			else throw new ObjectParseException(Messages.INVALID_MARIO_STATUS.formatted(String.join(" ", objWords)));
		}
		return mario;
	}
	
	@Override 
	protected boolean validDirection(Action dir) {
		return super.validDirection(dir) || dir == Action.STOP;
	}
	
	private boolean validSize(String string) {
		return string.equalsIgnoreCase(Messages.MARIO_SIZE_SMALL) 
				|| string.equalsIgnoreCase(Messages.MARIO_SIZE_SMALL_SC) || isBig(string);
	}
	
	private boolean isBig(String string) {
		return string.equalsIgnoreCase(Messages.MARIO_SIZE_BIG) 
				|| string.equalsIgnoreCase(Messages.MARIO_SIZE_BIG_SC);
	}
	
	@Override
	public String getIcon() {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(this.isInDirection(Action.STOP)) stringBuilder.append(Messages.MARIO_STOP);
		else if(this.isInDirection(Action.LEFT)) stringBuilder.append(Messages.MARIO_LEFT);
		else stringBuilder.append(Messages.MARIO_RIGHT);
	return stringBuilder.toString(); 
	}
	
	@Override
	public String toString() {
		String size = "";
		if(!big) size = Messages.SPACE+ Messages.MARIO_SIZE_SMALL;
		return super.toString() + size;
	}
}
