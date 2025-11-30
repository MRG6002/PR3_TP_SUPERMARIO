// Grupo 13: XiangLin - MarioRosellGarcia

package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameWorld;

import tp1.view.Messages;

public class Mario extends MovingObject {
	private static final String NAME = Messages.MARIO_NAME;
	private static final String SHORTCUT = Messages.MARIO_SHORTCUT;
	private boolean big;
	private boolean headCollision;
	private ActionList actionList;

	public Mario(Position position, GameWorld game) {
		this(position, game, Action.RIGHT, true);
	}
	
	private Mario(Position position, GameWorld game, Action dir, boolean big) {
		super(position, game, dir, NAME, SHORTCUT);
		this.headCollision = false;
		this.big = big;
		this.actionList = new ActionList();
	}
	
	Mario() {
		this(null, null);
	}
	
	@Override
	public GameObject newCopy(Position pos, GameWorld game) {
		return new Mario(pos, game);
	}
	
	@Override
	protected GameObject newCopy(Position pos, GameWorld game, Action dir) {
		return new Mario(pos, game, dir, true);
	}
	
	public void connect() {
		this.game.connect(this);
	}
	
	//movimiento de Mario
	@Override
	public boolean isInPosition(Position position) {
	return super.isInPosition(position) || (this.big && (this.position.go(Action.UP).equals(position)));
	}
	
	@Override
	public void update() {
		Position position = this.position.go(Action.STOP); 
		this.playerMovement();
		if(this.position.equals(position) && (!super.isInDirection(Action.STOP) || !this.game.isSolid(this.position.go(Action.DOWN)))) { 
			super.update();
			if(super.fallen()) this.game.marioDead(); 
		}
	}
	
	private void playerMovement() {
		for(Action action: this.actionList) {
			this.headCollision = false;
			if(action == Action.DOWN) {
				if(this.game.isSolid(this.position.go(Action.DOWN))) super.stop();
				else {
					while(super.freeFalling()) 
					if(!super.isAlive()) this.game.marioDead();
				}
			}
			else if(action == Action.UP) super.up();
			else if (action == Action.STOP) super.stop();
			else super.doAction(action);
		}
		this.actionList.clear();
	}

	public void addAction(Action action) {
		if(isValidAction(action)) this.actionList.addLast(action); 
	}
	
	private boolean isValidAction(Action action) {
		return action != null && 
				(action == Action.STOP ||
				!this.actionList.containsOpposite(action) && this.actionList.times(action) < 4);
	}
	
	@Override
	protected boolean headCollision() {
		this.headCollision = super.headCollision();
		if(big) {
			Position pos = this.position.go(Action.UP).go(Action.UP);
			this.headCollision = this.game.isSolid(pos) || position.isBorder();
		}
		return this.headCollision;
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
		if(interaction) {
			this.big = true;
		}
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
	public GameObject parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		if (objWords.length > 4 && matchObjectName(objWords[1]))
	 		throw new ObjectParseException(Messages.OBJECT_TOO_MUCH_ARGS.formatted(String.join(" ", objWords)));
		
		try {
			GameObject mario = null;
			if(objWords.length < 4)	super.parse(objWords, game);	
			else if(matchObjectName(objWords[1])) {
				Position pos = Position.stringToPosition(objWords[0]);
				Action dir = Action.parseAction(objWords[2]);
				
				if(!validDirection(dir)) throw new ObjectParseException(Messages.INVALID_MO_DIRECTION.formatted(String.join(" ", objWords)));
				else if (!validSize(objWords[3])) throw new ObjectParseException(Messages.INVALID_MARIO_STATUS.formatted(String.join(" ", objWords)));
				else return new Mario(pos, game, dir, isBig(objWords[3]));
			}
			return mario;
		} catch (OffBoardException obe){
			throw new OffBoardException(Messages.POSITION_OUT_OF_BOUNDS.formatted(String.join(" ", objWords)), obe);
		} catch (PositionParseException ppe) {
			throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), ppe);
		} catch (ActionParseException ape) {
			throw new ObjectParseException(Messages.UNKNOWN_MO_DIRECTION.formatted(String.join(" ", objWords)), ape);
		}
		
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
